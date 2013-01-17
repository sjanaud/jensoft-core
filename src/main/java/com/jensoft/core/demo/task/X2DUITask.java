package com.jensoft.core.demo.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import com.jensoft.core.x2d.lang.X2DError;
import com.jensoft.core.x2d.lang.X2DSchema;
import com.jensoft.core.x2d.lang.X2DSchemaErrorHandler;

public class X2DUITask extends Task {

	private String src;
	private String packageRoot;
	private String frameUITemplate;
	private String appletUITemplate;

	@Override
	public void execute() throws BuildException {
		
		log("X2DFrameUITask work src : " + src);
		log("X2DFrameUITask work package root : " + packageRoot);
		String srcSegment = src + File.separator + packageRoot.replace(".", File.separator);
		log("X2DFrameUITask work with segment : " + srcSegment);
		log("X2DFrameUITask load template...");
		frameUITemplate = loadTemplate( "x2d-frame-ui.java.template");
		appletUITemplate = loadTemplate("x2d-applet-ui.java.template");
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
			throw new BuildException("load template failed with exception " + e.getMessage());
		}

	}

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
	 * generate UI for x2d file
	 * 
	 * @param x2dFile
	 * @throws BuildException
	 */
	private void generateUI(File x2dFile) throws BuildException {

		if (x2dFile.getName().endsWith(".x2d") || x2dFile.getName().endsWith(".xml")) {

			X2DSchemaErrorHandler handler = new X2DSchemaErrorHandler();
			try {
				X2DSchema.validX2D(x2dFile, handler);
				if (!handler.hasErrors()) {
					log("\n");
					
					//x2d
					log("X2DUITask register X2D resource");
					log("X2D resource name  : " + x2dFile.getName());
					log("X2D path : " + x2dFile.getAbsolutePath().substring(0, x2dFile.getAbsolutePath().lastIndexOf(File.separator)));
					String x2dFileSegment = x2dFile.getAbsolutePath().substring(src.length() + 1);
					x2dFileSegment = x2dFileSegment.substring(0, x2dFileSegment.lastIndexOf(File.separator));
					log("X2D Resource File segment : " + x2dFileSegment);
					String x2dPackage = x2dFileSegment.replace(File.separator, ".");
					log("X2D Resource package segment : " + x2dPackage);

					String uiFileSegment = x2dFileSegment.substring(0, x2dFileSegment.lastIndexOf(File.separator));
					String uiPackage = uiFileSegment.replace(File.separator, ".");
					log("UI File segment (x2d package rank - 1) : " + uiFileSegment);
					log("UI package segment (x2d package rank - 1) : " + uiPackage);

					
					//FRAME UI
					String frameUIClassName = x2dFile.getName().substring(0, x2dFile.getName().lastIndexOf(".")) + "X2DFrameUI";
					
					log("Generate Frame UI : " + frameUIClassName);
					String frameUI = new String(frameUITemplate);
					frameUI = frameUI.replace("${package}", uiPackage);
					frameUI = frameUI.replace("${ui-class}", frameUIClassName);
					frameUI = frameUI.replace("${x2d-resource-path}", "x2d/" + x2dFile.getName());
					log("X2DFrameUITask Frame UI Source File");
					log("UI File name: " + frameUIClassName + ".java");
					log("UI File location: " + src + File.separator + uiFileSegment + File.separator);
					String frameUIAbsolutePath = src + File.separator + uiFileSegment + File.separator + frameUIClassName + ".java";
					log("generate source : " + frameUIAbsolutePath);
					generateSource(frameUI, new File(frameUIAbsolutePath));
					
					//APPLET UI
					String appletUIClassName = x2dFile.getName().substring(0, x2dFile.getName().lastIndexOf(".")) + "X2DAppletUI";
					log("Generate Applet UI : " + appletUIClassName);
					String appletUI = new String(appletUITemplate);
					appletUI = appletUI.replace("${package}", uiPackage);
					appletUI = appletUI.replace("${ui-class}", appletUIClassName);
					appletUI = appletUI.replace("${x2d-resource-path}", "x2d/" + x2dFile.getName());
					log("X2DFrameUITask Applet UI Source File");
					log("UI File name: " + appletUIClassName + ".java");
					log("UI File location: " + src + File.separator + uiFileSegment + File.separator);
					String appletUIAbsolutePath = src + File.separator + uiFileSegment + File.separator + appletUIClassName + ".java";
					log("generate source : " + appletUIAbsolutePath);
					generateSource(appletUI, new File(appletUIAbsolutePath));

				} else {
					log("Invalid X2D resource :" + x2dFile.getAbsolutePath(), Project.MSG_WARN);
					if (handler.hasErrors()) {
						List<X2DError> errors = handler.getErrors();
						for (X2DError x2dError : errors) {
							log(x2dError.getMessage(), Project.MSG_WARN);
						}
					}

					return;
				}

			} catch (Exception e) {
				e.printStackTrace();
				log("Failed to generate UI " + e.getMessage(), Project.MSG_WARN);

			}

		
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
