package com.jensoft.core.demo.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import com.jensoft.core.view.View2D;

public class ViewUITask extends Task {

	private String src;
	private String packageRoot;
	private String frameUITemplate;
	private String appletUITemplate;
	

	@Override
	public void execute() throws BuildException {
		log("ViewUITask work src : " + src);
		log("ViewUITask work package root : " + packageRoot);
		String srcSegment = src + File.separator + packageRoot.replace(".", File.separator);
		log("ViewUITask work with segment : " + srcSegment);
		log("ViewUITask load template...");
		frameUITemplate = loadTemplate( "view-frame-ui.java.template");
		appletUITemplate = loadTemplate("view-applet-ui.java.template");
		
	
		log("-------------------FRAME UI TEMPLATE START-----------------");
		log(frameUITemplate);
		log("-------------------FRAME UI TEMPLATE END-----------------");
		
		log("\n\n");
		
		log("-------------------APPLET UI TEMPLATE START-----------------");
		log(appletUITemplate);
		log("-------------------APPLET UI TEMPLATE END-----------------");
		
		File srcRoot = new File(srcSegment);
		if (srcRoot.isDirectory()) {
			File[] files = srcRoot.listFiles();
			for (int i = 0; i < files.length; i++) {
				processFile(files[i]);
			}
		} else {
			throw new BuildException("src is not a directory");
		}
	}

	private String loadTemplate(String template) throws BuildException {
		try {
			InputStream is = getClass().getResourceAsStream(template);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			throw new BuildException("load template failed with exception " + e.getMessage(),e);
		}

	}

	/**
	 * process recursive execution and call {@link #generateUI(File)} 
	 * if the given file is not a directory, else re-process each children.
	 * @param f
	 * 		the file to process
	 */
	private void processFile(File f) {
		if (f.isFile()) {
			generateUI(f);
		} else if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				processFile(files[i]);
			}
		} else {
		}
	}

	
	/**
	 * generate the Frame UI for the given view class file and view class. 
	 * @param viewFile
	 * @param viewClass
	 * @throws BuildException
	 */
	private void generateFrameUI(File viewFile,Class<View2D> viewClass) throws BuildException{
		
		log("\n");	
		log("ViewUITask create Frame UI : "+viewFile.getAbsolutePath());
		String viewClassName = viewFile.getName().substring(0,viewFile.getName().lastIndexOf("."));
		//log("view class simple name : "+viewClassName);		
		String viewFileSegment = viewFile.getAbsolutePath().substring(src.length()+1);
		viewFileSegment = viewFileSegment.substring(0,viewFileSegment.lastIndexOf(File.separator));
		//log("view file segment : "+viewFileSegment);			
		String viewPackage = viewFileSegment.replace(File.separator, ".");
		//log("view package : "+viewPackage);						
		String lookUpClass = viewPackage+"."+viewClassName;
		log("view class lookup : "+lookUpClass);
		
		String uiFileSegmentURI = viewFileSegment.substring(viewFileSegment.lastIndexOf("view")+4);
		//log("segment URI :"+uiFileSegmentURI);
		String uiPackageURI = uiFileSegmentURI.replace(File.separator, ".");
		//log("package URI :"+uiPackageURI);
		
		String uiRootPackage = viewPackage.substring(0, viewPackage.lastIndexOf(".view"));
		log("UI package root :"+uiRootPackage);
		
		String uiPackage = uiRootPackage+".ui.frame"+uiPackageURI;
		log("UI package :"+uiPackage);
		
		String uiJnlpPackage = uiRootPackage+".ui.jnlp";
		log("UI jnlp :"+uiJnlpPackage);
		
		String uiRootSegment = viewPackage.substring(0, viewPackage.lastIndexOf(".view")).replace(".", File.separator);
		log("UI segment root :"+uiRootSegment);
		String uiSourceSegment = uiRootSegment+File.separator+"ui"+File.separator+"frame"+uiPackageURI.replace(".", File.separator);
		log("UI source segment :"+uiSourceSegment);
		String uiJnlpSegment = uiRootSegment+File.separator+"ui"+File.separator+"jnlp";
		log("UI jnlp segment :"+uiJnlpSegment);
		
		//FRAME UI
		String frameUIClassName = viewClassName + "ViewFrameUI";
		
		log("Generate Frame UI : " + frameUIClassName);
		String frameUI = new String(frameUITemplate);
		frameUI = frameUI.replace("${ui-package}", uiPackage);
		frameUI = frameUI.replace("${view-import}", lookUpClass);
		frameUI = frameUI.replace("${ui-class}", frameUIClassName);
		frameUI = frameUI.replace("${view-class}", viewClassName);

		
		String frameUiFileName =  frameUIClassName + ".java";
		String frameUILocation  = src + File.separator + uiSourceSegment;
		String frameUIAbsolutePath = src + File.separator + uiSourceSegment + File.separator + frameUiFileName;
		log("Frame UI File name: " + frameUiFileName);
		log("Frame UI File location: "+ frameUILocation);
		File frameUILocationDirectory = new File(frameUILocation);
		if(!frameUILocationDirectory.exists()){
			log("Frame UI File location does not exist ...created!: " + frameUILocationDirectory.mkdirs());
		}
		log("Frame UI File absolute path: "+ frameUIAbsolutePath);						
		log("generate source...");
		generateSource(frameUI, new File(frameUIAbsolutePath));
		
		String frameUIJnlpLocation  = src + File.separator + uiJnlpSegment;
		File frameUIJnlpLocationDirectory = new File(frameUIJnlpLocation);
		if(!frameUIJnlpLocationDirectory.exists()){
			log("Frame UI JNLP File location does not exist ...created!: " + frameUIJnlpLocationDirectory.mkdirs());
		}	
		
	}
	
	
	private void generateAppletUI(File viewFile,Class<View2D> viewClass){
		
		log("\n");	
		log("ViewUITask create Applet UI : "+viewFile.getAbsolutePath());
		String viewClassName = viewFile.getName().substring(0,viewFile.getName().lastIndexOf("."));
		//log("view class simple name : "+viewClassName);
		
		String viewFileSegment = viewFile.getAbsolutePath().substring(src.length()+1);
		viewFileSegment = viewFileSegment.substring(0,viewFileSegment.lastIndexOf(File.separator));
		//log("view file segment : "+viewFileSegment);			
		String viewPackage = viewFileSegment.replace(File.separator, ".");
		//log("view package: "+viewPackage);		
						
		String lookUpClass = viewPackage+"."+viewClassName;
		log("view class lookup : "+lookUpClass);
		
		String uiFileSegmentURI = viewFileSegment.substring(viewFileSegment.lastIndexOf("view")+4);
		//log("segment URI :"+uiFileSegmentURI);
		String uiPackageURI = uiFileSegmentURI.replace(File.separator, ".");
		//log("package URI :"+uiPackageURI);
		
		String uiRootPackage = viewPackage.substring(0, viewPackage.lastIndexOf(".view"));
		log("UI package root : "+uiRootPackage);
		String uiPackage = uiRootPackage+".ui.applet"+uiPackageURI;
		log("UI package :"+uiPackage);
		
		String uiRootFileSegment = viewPackage.substring(0, viewPackage.lastIndexOf(".view")).replace(".", File.separator);
		log("UI segment root:"+uiRootFileSegment);
		String uiViewFileSegment = uiRootFileSegment+File.separator+"ui"+File.separator+"applet"+uiPackageURI.replace(".", File.separator);
		log("UI segment :"+uiViewFileSegment);
		
		//APPLET UI
		String appletUIClassName = viewClassName + "ViewAppletUI";
		log("Generate Applet UI : " + appletUIClassName);
		String appletUI = new String(appletUITemplate);
		appletUI = appletUI.replace("${ui-package}", uiPackage);
		appletUI = appletUI.replace("${view-import}", lookUpClass);
		appletUI = appletUI.replace("${ui-class}", appletUIClassName);
		appletUI = appletUI.replace("${view-class}", viewClassName);
		
		
		String appletUIFileName =  appletUIClassName + ".java";
		String appletUILocation  = src + File.separator + uiViewFileSegment;
		String appletUIAbsolutePath = src + File.separator + uiViewFileSegment + File.separator + appletUIFileName;
		log("Applet UI File name: " + appletUIFileName);
		log("Applet UI File location: "+ appletUILocation);
		File appletUILocationDirectory = new File(appletUILocation);
		if(!appletUILocationDirectory.exists()){
			log("Applet UI File location does not exist ...created!: " + appletUILocationDirectory.mkdirs());
		}
		log("Applet UI File absolute path: "+ appletUIAbsolutePath);						
		
		generateSource(appletUI, new File(appletUIAbsolutePath));
		
	}

	/**
	 * generate UI for x2d file
	 * 
	 * @param viewFile
	 * @throws BuildException
	 */
	private void generateUI(File viewFile) throws BuildException {		
		
			try {
				
				if (viewFile.getName().endsWith(".java")) {
					//log("\n");	
					//log("ViewFrameUITask register view file : "+viewFile.getAbsolutePath());
					String viewClassName = viewFile.getName().substring(0,viewFile.getName().lastIndexOf("."));
					//log("view class simple name : "+viewClassName);
					
					String viewFileSegment = viewFile.getAbsolutePath().substring(src.length()+1);
					viewFileSegment = viewFileSegment.substring(0,viewFileSegment.lastIndexOf(File.separator));
					//log("view file segment : "+viewFileSegment);			
					String viewPackage = viewFileSegment.replace(File.separator, ".");
					//log("view package : "+viewPackage);
					
									
					String lookUpClass = viewPackage+"."+viewClassName;
					//log("view lookup : "+lookUpClass);
					
					
					Class viewClass = null;
					try {
						viewClass = Class.forName(lookUpClass);
					} catch (Exception e) {
						//log("Class not found :"+lookUpClass, Project.MSG_WARN);
					}
					
				
					if(viewClass != null && View2D.class.isAssignableFrom(viewClass)){
//						log("Assignable view resource : "+viewClass);						
//						
//						String uiFileSegmentURI = viewFileSegment.substring(viewFileSegment.lastIndexOf("view")+4);
//						log("segment URI :"+uiFileSegmentURI);
//						String uiPackageURI = uiFileSegmentURI.replace(File.separator, ".");
//						log("package URI :"+uiPackageURI);
//						
//						String uiRootPackage = viewPackage.substring(0, viewPackage.lastIndexOf(".view"));
//						log("UI Root package :"+uiRootPackage);
//						String uiViewPackage = uiRootPackage+uiPackageURI;
//						log("UI View package :"+uiViewPackage);
//						
//						String uiRootFileSegment = viewPackage.substring(0, viewPackage.lastIndexOf(".view")).replace(".", File.separator);
//						log("UI Root file segment :"+uiRootFileSegment);
//						String uiViewFileSegment = uiRootFileSegment+uiPackageURI.replace(".", File.separator);
//						log("UI View file segment :"+uiViewFileSegment);
						
						generateFrameUI(viewFile,viewClass);
						generateAppletUI(viewFile,viewClass);

						
						
						
						

					}
					else{
						//log("Invalid View resource, is not assignable from "+View2D.class+" class :" + viewFile.getAbsolutePath(), Project.MSG_WARN);
						return;
					}

					
				} else {						
				}

			} catch (Exception e) {
				e.printStackTrace();
				log("Failed to generate UI " + e.getMessage(), Project.MSG_WARN);

			}

		
	

	}

	private void generateSource(String content, File file) throws BuildException {
		FileOutputStream fop = null;
		try {

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
			throw new BuildException("generate source failed with error :" + e.getMessage());
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * @return the packageRoot
	 */
	public String getPackageRoot() {
		return packageRoot;
	}

	/**
	 * @param packageRoot
	 *            the packageRoot to set
	 */
	public void setPackageRoot(String packageRoot) {
		this.packageRoot = packageRoot;
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


}
