package com.jensoft.core.demo.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.jensoft.core.demo.nature.AppletUI;
import com.jensoft.core.demo.nature.FrameUI;
import com.jensoft.core.demo.nature.JenSoftAPIDemo;

public class JNLPTask extends Task {

	private String src;
	private String dest;
	private String packageRoot;

	private String jnlpCodeBase;
	private String jarResourcesCodeBase;
	private String jarResources;

	private String appletUIJNLPTemplate;
	private String appletUIJNLPTemplateName = "applet-ui.jnlp.template";

	private String frameUIJNLPTemplate;
	private String frameUIJNLPTemplateName = "frame-ui.jnlp.template";

	List<Class> jnlpFrameUIClasses = new ArrayList<Class>();
	List<Class> jnlpAppletUIClasses = new ArrayList<Class>();
	List<Class> viewFrameUIClass = new ArrayList<Class>();
	List<Class> viewAppletUIClass = new ArrayList<Class>();
	List<Class> x2dFrameUIClass = new ArrayList<Class>();
	List<Class> x2dAppletUIClass = new ArrayList<Class>();

	private void loadTemplates() throws BuildException {
		appletUIJNLPTemplate = loadTemplate(appletUIJNLPTemplateName);
		log("-------------------APPLET UI JNLP TEMPLATE START-----------------");
		log(appletUIJNLPTemplate);
		log("-------------------APPLET UI JNLP TEMPLATE END-----------------");

		frameUIJNLPTemplate = loadTemplate(frameUIJNLPTemplateName);
		log("-------------------FRAME UI JNLP TEMPLATE START-----------------");
		log(frameUIJNLPTemplate);
		log("-------------------FRAME UI JNLP TEMPLATE END-----------------");
	}
	
	private void processFile(File f) {
		if (f.isFile()) {
			register(f);
		} else if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				processFile(files[i]);
			}
		} else {
		}
	}
	
	private void register(File f) {
		if (f.getName().endsWith(".java")) {
		
			
			String viewClassName = f.getName().substring(0,f.getName().lastIndexOf("."));			
			
			String viewFileSegment = f.getAbsolutePath().substring(src.length()+1);
			viewFileSegment = viewFileSegment.substring(0,viewFileSegment.lastIndexOf(File.separator));			
			String viewPackage = viewFileSegment.replace(File.separator, ".");							
			String lookUpClass = viewPackage+"."+viewClassName;
			
			
			Class viewClass = null;
			try {
				viewClass = Class.forName(lookUpClass);
			} catch (Exception e) {
				//log("Class not found :"+lookUpClass, Project.MSG_WARN);
			}
			if (viewClass.isAnnotationPresent(JenSoftAPIDemo.class)) {
				
				if (viewClass.isAnnotationPresent(AppletUI.class)) {
					
					log("applet ui file : "+f.getAbsolutePath());
					log("applet ui class : "+viewClassName);
					//log("applet ui file segment : "+viewFileSegment);
					log("applet ui package : "+viewPackage);
					//log("applet ui lookup : "+lookUpClass);

					jnlpAppletUIClasses.add(viewClass);

//					if (X2DAppletUI.class.isAssignableFrom(viewClass)) {
//						x2dAppletUIClass.add(viewClass);
//					} else if (ViewAppletUI.class.isAssignableFrom(viewClass)) {
//						viewAppletUIClass.add(viewClass);
//					}

				} else if (viewClass.isAnnotationPresent(FrameUI.class)) {
					
					log("frame ui file : "+f.getAbsolutePath());
					log("frame ui class : "+viewClassName);
					//log("frame ui file segment : "+viewFileSegment);
					log("frame ui package : "+viewPackage);
					//log("frame ui lookup : "+lookUpClass);

					jnlpFrameUIClasses.add(viewClass);
//
//					if (X2DFrameUI.class.isAssignableFrom(viewClass)) {
//						x2dFrameUIClass.add(viewClass);
//					} else if (ViewFrameUI.class.isAssignableFrom(viewClass)) {
//						viewFrameUIClass.add(viewClass);
//					}

				}

			}
			
		}
	}

	private void solveTargetUI() throws Exception {
		log("Jnlp UI Task work with src : " + src);
		log("Jnlp UI Task work with package root : " + packageRoot);
		String srcSegment = src + File.separator + packageRoot.replace(".", File.separator);
		File srcRoot = new File(srcSegment);
		if (srcRoot.isDirectory()) {
			File[] files = srcRoot.listFiles();
			for (int i = 0; i < files.length; i++) {
				processFile(files[i]);
			}
		} else {
			throw new BuildException("src is not a directory");
		}
		
		log("Applet UI found : " + jnlpAppletUIClasses.size());
		log("Frame UI found : " + jnlpFrameUIClasses.size());
	}

	private void createJNLPFiles() {
		for (Class frameUIJNLP : jnlpFrameUIClasses) {
			createFrameUIJNLP(dest, frameUIJNLP);
		}
		for (Class appletUIJNLP : jnlpAppletUIClasses) {
			createAppletUIJNLP(dest, appletUIJNLP);
		}
	}

	@Override
	public void execute() throws BuildException {
		log("Jnlp UI Task work with src : " + src);
		log("Jnlp UI Task work with package root : " + packageRoot);
		String srcSegment = src + File.separator + packageRoot.replace(".", File.separator);		
		
		log("jnlpCodeBase : " + jnlpCodeBase);
		log("jarResourcesCodeBase : " + jarResourcesCodeBase);
		log("jarResources : " + jarResources);
		log("dest : " + dest);
		log("package : " + packageRoot);

		File destDir = new File(dest);

		if (!destDir.exists()) {
			throw new BuildException("JNLP task Exception, destination dir does not exist");
		}
		if (destDir.exists() && !destDir.isDirectory()) {
			throw new BuildException("JNLP task Exception, destination exist but it is not a directory");
		}

		try {

			loadTemplates();
			solveTargetUI();
			createJNLPFiles();

		} catch (Exception e) {
			// e.printStackTrace();
			throw new BuildException("JNLP task Exception :" + e.getMessage(), e);
		}

	}

	/**
	 * generate the given stream as a generic source file
	 * 
	 * @param content
	 *            source content
	 * @param file
	 *            source file
	 * @throws BuildException
	 */
	private void generateSource(String content, File file) throws BuildException {
		FileOutputStream fop = null;
		try {
			//log("Generate Generic Source : " + file.getAbsolutePath());
			fop = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			throw new BuildException("generate page failed with error :" + e.getMessage());
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
			}
		}
	}

	private void createFrameUIJNLP(String rootDir, Class demoClass) throws BuildException {
		try {
			String jnlpDemoFileName = rootDir + File.separator + demoClass.getSimpleName() + ".jnlp";
			// log("generate JNLP : " + jnlpDemoFileName);

			log("Generate FRAME UI JNLP: " + jnlpDemoFileName);
			String frameUIJNLP = new String(frameUIJNLPTemplate);
			frameUIJNLP = frameUIJNLP.replace("${jnlp-codebase}", jnlpCodeBase);
			frameUIJNLP = frameUIJNLP.replace("${jnlp-href}", demoClass.getSimpleName() + ".jnlp");
			frameUIJNLP = frameUIJNLP.replace("${frame-ui-class}", demoClass.getName());

			StringTokenizer tokenizer = new StringTokenizer(jarResources, ",");
			StringBuffer jarsRefBuffer = new StringBuffer();
			while (tokenizer.hasMoreElements()) {
				String jarName = (String) tokenizer.nextElement();
				jarsRefBuffer.append("\t" + "\t" + "<jar href=\"" + jarResourcesCodeBase + "/" + jarName + "\"" + "/>" + "\n");
			}
			frameUIJNLP = frameUIJNLP.replace("${ui-jar-resources}", jarsRefBuffer.toString());

			generateSource(frameUIJNLP, new File(jnlpDemoFileName));

			// BufferedWriter writer = new BufferedWriter(new
			// FileWriter(jnlpDemoFileName));
			//
			// writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			// writer.newLine();
			// writer.write("<jnlp codebase=\"" + jnlpCodeBase + "\"");
			// writer.newLine();
			// writer.write("\t" + "\t" + "\t" + "href=\"" +
			// demoClass.getSimpleName() + ".jnlp" + "\">");
			// writer.newLine();
			// writer.newLine();
			// writer.write("\t" + "<information>");
			// writer.newLine();
			// writer.write("\t" + "\t" + "<title>Jensoft API</title>");
			// writer.newLine();
			// writer.write("\t" + "\t" + "<vendor>JenSoft</vendor>");
			// writer.newLine();
			// writer.write("\t" + "\t" +
			// "<homepage href=\"http://www.jensoft.org\"/>");
			// writer.newLine();
			// writer.write("\t" + "\t" +
			// "<icon href=\"http://www.jensoft.org/jensoft/images/demosplash.png\" kind=\"splash\"/>");
			// writer.newLine();
			// writer.write("\t" + "\t" +
			// "<description>JenSoft API</description> ");
			// writer.newLine();
			// writer.write("\t" + "\t" +
			// "<description kind=\"short\">An application showing a variety of charts that can be generated with Jensoft chart</description>");
			// writer.newLine();
			// writer.write("\t" + "\t" +
			// "<description kind=\"tooltip\">JenSoft API</description>");
			// writer.newLine();
			// writer.write("\t" + "\t" + "<offline-allowed/>");
			// writer.newLine();
			// writer.write("\t" + "</information>");
			// writer.newLine();
			// writer.newLine();
			//
			// writer.write("\t" + "<resources>");
			// writer.newLine();
			// writer.write("\t" + "\t" +
			// "<j2se version=\"1.6+\" initial-heap-size=\"12m\" max-heap-size=\"256m\" />");
			// writer.newLine();
			// StringTokenizer tokenizer = new StringTokenizer(jarResources,
			// ",");
			// while (tokenizer.hasMoreElements()) {
			// String jarName = (String) tokenizer.nextElement();
			// writer.write("\t" + "\t" + "<jar href=\"" + jarResourcesCodeBase
			// + "/" + jarName + "\"" + "/>");
			// writer.newLine();
			// }
			//
			// writer.write("\t" + "</resources>");
			// writer.newLine();
			// writer.newLine();
			// writer.write("\t" + "<application-desc main-class=\"" +
			// demoClass.getName() + "\"/>");
			// writer.newLine();
			// writer.write(" </jnlp>");
			//
			// writer.close();
		} catch (Exception e) {
			throw new BuildException("Error create JNLP File", e);
		}
	}

	private void createAppletUIJNLP(String rootDir, Class demoClass) throws BuildException {
		try {
			String jnlpDemoFileName = rootDir + File.separator + demoClass.getSimpleName() + ".jnlp";
			// log("generate JNLP : " + jnlpDemoFileName);

			log("Generate Applet UI JNLP: " + jnlpDemoFileName);
			String appletUIJNLP = new String(appletUIJNLPTemplate);
			appletUIJNLP = appletUIJNLP.replace("${jnlp-codebase}", jnlpCodeBase);
			appletUIJNLP = appletUIJNLP.replace("${jnlp-href}", demoClass.getSimpleName() + ".jnlp");
			appletUIJNLP = appletUIJNLP.replace("${applet-ui-name}", demoClass.getSimpleName());
			appletUIJNLP = appletUIJNLP.replace("${applet-ui-class}", demoClass.getName());

			StringTokenizer tokenizer = new StringTokenizer(jarResources, ",");
			StringBuffer jarsRefBuffer = new StringBuffer();
			while (tokenizer.hasMoreElements()) {
				String jarName = (String) tokenizer.nextElement();
				jarsRefBuffer.append("\t" + "\t" + "<jar href=\"" + jarResourcesCodeBase + "/" + jarName + "\"" + "/>" + "\n");
			}
			appletUIJNLP = appletUIJNLP.replace("${ui-jar-resources}", jarsRefBuffer.toString());

			generateSource(appletUIJNLP, new File(jnlpDemoFileName));

		} catch (Exception e) {
			throw new BuildException("Error create JNLP File", e);
		}
	}

	private String loadTemplate(String template) throws BuildException {
		try {
			InputStream is = getClass().getResourceAsStream(template);
			if (is == null) {
				throw new BuildException("load template failed, not found" + template);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new BuildException("load template failed with exception " + e.getMessage());
		}

	}

	public String getPackageRoot() {
		return packageRoot;
	}

	public void setPackageRoot(String packageRoot) {
		this.packageRoot = packageRoot;
	}

	/**
	 * @return the jnlpCodeBase
	 */
	public String getJnlpCodeBase() {
		return jnlpCodeBase;
	}

	/**
	 * @param jnlpCodeBase
	 *            the jnlpCodeBase to set
	 */
	public void setJnlpCodeBase(String jnlpCodeBase) {
		this.jnlpCodeBase = jnlpCodeBase;
	}

	/**
	 * @return the jarResourcesCodeBase
	 */
	public String getJarResourcesCodeBase() {
		return jarResourcesCodeBase;
	}

	/**
	 * @param jarResourcesCodeBase
	 *            the jarResourcesCodeBase to set
	 */
	public void setJarResourcesCodeBase(String jarResourcesCodeBase) {
		this.jarResourcesCodeBase = jarResourcesCodeBase;
	}

	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * @param src
	 *            the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @return the destJnlp
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * @param destJnlp
	 *            the destJnlp to set
	 */
	public void setDest(String dest) {
		this.dest = dest;
	}

	/**
	 * @return the jarResources
	 */
	public String getJarResources() {
		return jarResources;
	}

	/**
	 * @param jarResources
	 *            the jarResources to set
	 */
	public void setJarResources(String jarResources) {
		this.jarResources = jarResources;
	}

	
}