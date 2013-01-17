package com.jensoft.core.temp;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class ClassSearchUtils {

    private static final Logger log = Logger.getAnonymousLogger();

    /**
     * Classloader to be used to obtain resources from file system.
     */
    private ClassLoader classloader;

    /**
     * List of the resource found in the classpath.
     */
    private List<ClassPathEntry> list;

    /**
     * Extension of the resource to be found in the classpath.
     */
    private String extension;

    private String prefix;
    
    public static class ClassPathEntry{
    	public Object entry;
    	public File entryFile;
		public ClassPathEntry(Object entry, File classFile) {
			super();
			this.entry = entry;
			this.entryFile = classFile;
		}  	
    	
    }
     

    /**
     * Search for the resource with the extension in the classpath. Method
     * self-instantiate factory for every call to ensure thread safety.
     * @param extension Mandatory extension of the resource. If all resources
     * are required extension should be empty string. Null extension is not
     * allowed and will cause method to fail.
     * @return List of all resources with specified extension.
     */
    public static List<ClassPathEntry> searchClassPath(String prefix) {
        return searchClassPath(prefix, ".class");
    }
    /**
     * Search for the resource with the extension in the classpath. Method
     * self-instantiate factory for every call to ensure thread safety.
     * @param extension Mandatory extension of the resource. If all resources
     * are required extension should be empty string. Null extension is not
     * allowed and will cause method to fail.
     * @return List of all resources with specified extension.
     */
    public static List<ClassPathEntry> searchClassPath(String prefix, String extension) {
        ClassSearchUtils factory = new ClassSearchUtils();
        factory.prefix = prefix;
        return factory.find(extension);
    }

    /**
     * Search for the resource with the extension in the classpath.
     * @param extension Mandatory extension of the resource. If all resources
     * are required extension should be empty string. Null extension is not
     * allowed and will cause method to fail.
     * @return List of all resources with specified extension.
     */
    private List<ClassPathEntry> find(String extension) {
        this.extension = extension;
        this.list = new ArrayList<ClassPathEntry>();
        this.classloader = this.getClass().getClassLoader();
        String classpath = System.getProperty("java.class.path");

        try {
            Method method =
                this.classloader.getClass().getMethod("getClassPath", (Class<?>) null);
            if (method != null) {
                classpath = (String) method.invoke(this.classloader, (Object) null);
            }
        } catch (Exception e) {
            // ignore
        }
        if (classpath == null) {
            classpath = System.getProperty("java.class.path");
        }

        StringTokenizer tokenizer =
            new StringTokenizer(classpath, File.pathSeparator);
        String token;
        File dir;
        String name;
        while (tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            dir = new File(token);
            if (dir.isDirectory()) {
                lookInDirectory("", dir);
            }
            if (dir.isFile()) {
                name = dir.getName().toLowerCase();
                if (name.endsWith(".zip") || name.endsWith(".jar")) {
                    this.lookInArchive(dir);
                }
            }
        }
        return this.list;
    }

    /**
     * @param name Name of to parent directories in java class notation (dot
     * separator)
     * @param dir Directory to be searched for classes.
     */
    private void lookInDirectory(String name, File dir) {
        log.fine( "Looking in directory [" + dir.getName() + "].");
        File[] files = dir.listFiles();
        File file;
        String fileName;
        final int size = files.length;
        for (int i = 0; i < size; i++) {
            file = files[i];
            fileName = file.getName();
            if (file.isFile()
                && fileName.toLowerCase().endsWith(this.extension)) {
                try {
                    if (this.extension.equalsIgnoreCase(".class")) {
                        fileName = fileName.substring(0, fileName.length() - 6);
                        // filter ignored resources
                        if (!(name + fileName).startsWith(this.prefix)) {
                            continue;
                        }
                        log.fine("Found class: [" + name + fileName + "].");                            
                        ClassPathEntry entry = new ClassPathEntry(Class.forName(name + fileName),file);
                        this.list.add(entry);
                    } else {
                    	ClassPathEntry entry = new ClassPathEntry(this.classloader.getResource(name.replace('.', File.separatorChar) + fileName), file);     
                        this.list.add(entry);
                    }
                } catch (ClassNotFoundException e) {
                    // ignore
                } catch (NoClassDefFoundError e) {
                        //ignore too
                } catch (ExceptionInInitializerError e) {
                    if (e.getCause() instanceof HeadlessException) {
                        // running in headless env ... ignore 
                    } else {
                        throw e;
                    }
                }
            }
            // search recursively.
            // I don't like that but we will see how it will work.
            if (file.isDirectory()) {
                lookInDirectory(name + fileName + ".", file);
            }
        }

    }

    /**
     * Search archive files for required resource.
     * @param archive Jar or zip to be searched for classes or other resources.
     */
    private void lookInArchive(File archive) {
        log.fine(
            "Looking in archive ["
                + archive.getName()
                + "] for extension ["
                + this.extension
                + "].");
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(archive);
        } catch (IOException e) {
            log.warning(
                "Non fatal error. Unable to read jar item.");
            return;
        }
        Enumeration entries = jarFile.entries();
        JarEntry entry;
        String entryName;
        while (entries.hasMoreElements()) {
            entry = (JarEntry) entries.nextElement();
            entryName = entry.getName();
            if (entryName.toLowerCase().endsWith(this.extension)) {
                try {
                    if (this.extension.equalsIgnoreCase(".class")) {
                        // convert name into java classloader notation
                        entryName =
                            entryName.substring(0, entryName.length() - 6);
                        entryName = entryName.replace('/', '.');

                        // filter ignored resources
                        if (!entryName.startsWith(this.prefix)) {
                            continue;
                        }

                        log.fine(
                            "Found class: [" + entryName + "]. ");
                        ClassPathEntry classPathEntry = new ClassPathEntry(Class.forName(entryName), archive);
                        this.list.add(classPathEntry);
                    } else {
                    	ClassPathEntry classPathEntry = new ClassPathEntry(this.classloader.getResource(entryName), archive);
                        this.list.add(classPathEntry);
                        log.fine(
                            "Found appropriate resource with name ["
                                + entryName
                                + "]. Resource instance:"
                                + this.classloader.getResource(entryName));
                    }
                } catch (Throwable e) {
                    // ignore
                    log.warning(
                        "Unable to load resource ["
                            + entryName
                            + "] form file ["
                            + archive.getAbsolutePath()
                            + "].");
                }
            }
        }
    }
    
    public static void main(String[] args) {
		List<ClassPathEntry> entries = ClassSearchUtils.searchClassPath("com.jensoft.demo.x2d.donut3d",".class");
		System.out.println("classes list size : "+entries.size());
		for (ClassPathEntry cpe : entries) {
			try {
				if(cpe.entry instanceof Class){
					System.out.println("entry object  : "+cpe.entry);
					System.out.println("entry class : "+((Class)cpe.entry).getCanonicalName());
					System.out.println("entry file : "+cpe.entryFile.getCanonicalPath());
				}
				else{
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}