package com.jensoft.core.demo.task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import com.jensoft.core.demo.nature.AppletUI;
import com.jensoft.core.demo.nature.FrameUI;
import com.jensoft.core.demo.nature.JenSoftAPIDemo;
import com.jensoft.core.demo.ui.ViewAppletUI;
import com.jensoft.core.demo.ui.ViewFrameUI;
import com.jensoft.core.demo.ui.X2DAppletUI;
import com.jensoft.core.demo.ui.X2DFrameUI;

public class CopyOfJenSofAPIDemo_ForWebPageTask extends Task {

	private String jnlpCodeBase;
	private String jarResourcesCodeBase;
	private String jarResources;
	private String packageRoot;
	private String destJnlp;
	private String destPage;

	private String demoIndexWebUITemplate;
	private String demoIndexWebUITemplateName = "jensoft-demo-index.jsp.template";

	private String demoIndexViewFrameUIPageTemplate;
	private String demoIndexViewFrameUIPageTemplateName = "jensoft-demo-view-frameui-index.jsp.template";

	private String demoIndexViewAppletUIPageTemplate;
	private String demoIndexViewAppletUIPageTemplateName = "jensoft-demo-view-appletui-index.jsp.template";

	private String demoIndexX2DFrameUIPageTemplate;
	private String demoIndexX2DFrameUIPageTemplateName = "jensoft-demo-x2d-frameui-index.jsp.template";

	private String demoIndexX2DAppletUIPageTemplate;
	private String demoIndexX2DAppletUIPageTemplateName = "jensoft-demo-x2d-appletui-index.jsp.template";

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

	private void loadTemplates() throws BuildException{
		demoIndexWebUITemplate = loadTemplate(demoIndexWebUITemplateName);
		log("-------------------DEMO SUMMARY JSP TEMPLATE START-----------------");
		log(demoIndexWebUITemplate);
		log("-------------------DEMO SUMMARY JSP TEMPLATE END-----------------");

		demoIndexViewFrameUIPageTemplate = loadTemplate(demoIndexViewFrameUIPageTemplateName);
		log("-------------------View FRAME UI DEMO INDEX JSP TEMPLATE START-----------------");
		log(demoIndexViewFrameUIPageTemplate);
		log("-------------------View FRAME UI DEMO INDEX JSP TEMPLATE END-----------------");

		demoIndexViewAppletUIPageTemplate = loadTemplate(demoIndexViewAppletUIPageTemplateName);
		log("-------------------View APPLET UI DEMO INDEX JSP TEMPLATE START-----------------");
		log(demoIndexViewAppletUIPageTemplate);
		log("-------------------View APPLET UI DEMO INDEX JSP TEMPLATE END-----------------");

		demoIndexX2DFrameUIPageTemplate = loadTemplate(demoIndexX2DFrameUIPageTemplateName);
		log("-------------------X2D FRAME UI DEMO INDEX JSP TEMPLATE START-----------------");
		log(demoIndexX2DFrameUIPageTemplate);
		log("-------------------X2D FRAME UI DEMO INDEX JSP TEMPLATE END-----------------");

		demoIndexX2DAppletUIPageTemplate = loadTemplate(demoIndexX2DAppletUIPageTemplateName);
		log("-------------------X2D APPLET UI DEMO INDEX JSP TEMPLATE START-----------------");
		log(demoIndexX2DAppletUIPageTemplate);
		log("-------------------X2D APPLET UI DEMO INDEX JSP TEMPLATE END-----------------");

		appletUIJNLPTemplate = loadTemplate(appletUIJNLPTemplateName);
		log("-------------------APPLET UI JNLP TEMPLATE START-----------------");
		log(appletUIJNLPTemplate);
		log("-------------------APPLET UI JNLP TEMPLATE END-----------------");

		frameUIJNLPTemplate = loadTemplate(frameUIJNLPTemplateName);
		log("-------------------FRAME UI JNLP TEMPLATE START-----------------");
		log(frameUIJNLPTemplate);
		log("-------------------FRAME UI JNLP TEMPLATE END-----------------");

	}

	private void solve() throws Exception {
		Iterable<Class> classes = getClasses(packageRoot);
		for (Class c : classes) {
			if (c.isAnnotationPresent(JenSoftAPIDemo.class)) {
				if (c.isAnnotationPresent(AppletUI.class)) {

					jnlpAppletUIClasses.add(c);

					if (X2DAppletUI.class.isAssignableFrom(c)) {
						x2dAppletUIClass.add(c);
					} else if (ViewAppletUI.class.isAssignableFrom(c)) {
						viewAppletUIClass.add(c);
					}

				} else if (c.isAnnotationPresent(FrameUI.class)) {

					jnlpFrameUIClasses.add(c);

					if (X2DFrameUI.class.isAssignableFrom(c)) {
						x2dFrameUIClass.add(c);
					} else if (ViewFrameUI.class.isAssignableFrom(c)) {
						viewFrameUIClass.add(c);
					}

				}

			}
		}
	}

	private void createJNLPFiles() {
		for (Class frameUIJNLP : jnlpFrameUIClasses) {
			createFrameUIJNLP(destJnlp, frameUIJNLP);
		}
		for (Class appletUIJNLP : jnlpAppletUIClasses) {
			createAppletUIJNLP(destJnlp, appletUIJNLP);
		}
	}

	private void createDemoIndexPage() {
		int count = 1;
		StringBuffer stringBufferView = new StringBuffer();
		for (Class viewFrameUI : viewFrameUIClass) {
			String linkJNLP = jnlpCodeBase + "/" + viewFrameUI.getSimpleName() + ".jnlp";
			stringBufferView.append("<tr>" + "\n");
			stringBufferView.append("\t<td>" + count + "</td>" + "\n");
			stringBufferView.append("\t<td>" + "Frame UI" + "</td>" + "\n");
			stringBufferView.append("\t<td>View</td>" + "\n");
			stringBufferView.append("\t<td><a href=\"" + linkJNLP + "\">" + viewFrameUI.getSimpleName() + "</a></td>" + "\n");
			stringBufferView.append("</tr>" + "\n");
			count++;
		}
		for (Class viewAppletUI : viewAppletUIClass) {
			String linkJNLP = jnlpCodeBase + "/" + viewAppletUI.getSimpleName() + ".jnlp";
			stringBufferView.append("<tr>" + "\n");
			stringBufferView.append("\t<td>" + count + "</td>" + "\n");
			stringBufferView.append("\t<td>" + "Applet UI" + "</td>" + "\n");
			stringBufferView.append("\t<td>View</td>" + "\n");
			stringBufferView.append("\t<td><a href=\"" + linkJNLP + "\">" + viewAppletUI.getSimpleName() + "</a></td>" + "\n");
			stringBufferView.append("</tr>" + "\n");
			count++;
		}

		count = 0;
		StringBuffer stringBufferX2D = new StringBuffer();
		for (Class x2dFrameUI : x2dFrameUIClass) {
			String linkJNLP = jnlpCodeBase + "/" + x2dFrameUI.getSimpleName() + ".jnlp";
			stringBufferX2D.append("<tr>" + "\n");
			stringBufferX2D.append("\t<td>" + count + "</td>" + "\n");
			stringBufferX2D.append("\t<td>" + "Frame UI" + "</td>" + "\n");
			stringBufferX2D.append("\t<td>X2D</td>" + "\n");
			stringBufferX2D.append("\t<td><a href=\"" + linkJNLP + "\">" + x2dFrameUI.getSimpleName() + "</a></td>" + "\n");
			stringBufferX2D.append("</tr>" + "\n");
			count++;
		}
		for (Class x2dAppletUI : x2dAppletUIClass) {
			String linkJNLP = jnlpCodeBase + "/" + x2dAppletUI.getSimpleName() + ".jnlp";
			stringBufferX2D.append("<tr>" + "\n");
			stringBufferX2D.append("\t<td>" + count + "</td>" + "\n");
			stringBufferX2D.append("\t<td>" + "Applet UI" + "</td>" + "\n");
			stringBufferX2D.append("\t<td>X2D</td>" + "\n");
			stringBufferX2D.append("\t<td><a href=\"" + linkJNLP + "\">" + x2dAppletUI.getSimpleName() + "</a></td>" + "\n");
			stringBufferX2D.append("</tr>" + "\n");
			count++;
		}

		String pageName = demoIndexWebUITemplateName.substring(0, demoIndexWebUITemplateName.lastIndexOf(".template"));
		log("Generate WEB UI [Demo WEB UI Page Index] : " + pageName);
		String webUI = new String(demoIndexWebUITemplate);
		webUI = webUI.replace("${tr-view-demo-list}", stringBufferView.toString());
		webUI = webUI.replace("${tr-x2d-demo-list}", stringBufferX2D.toString());		
		generateSource(webUI, new File(destPage + File.separator + pageName));
	}

	private void createViewFrameUIDemoIndexPage() {
		int count = 1;
		StringBuffer stringBufferView = new StringBuffer();
		for (Class ui : viewFrameUIClass) {
			String linkJNLP = jnlpCodeBase + "/" + ui.getSimpleName() + ".jnlp";
			stringBufferView.append("<tr>" + "\n");
			stringBufferView.append("\t<td>" + count + "</td>" + "\n");
			stringBufferView.append("\t<td>" + "Frame UI" + "</td>" + "\n");
			stringBufferView.append("\t<td>View</td>" + "\n");
			stringBufferView.append("\t<td><a href=\"" + linkJNLP + "\">" + ui.getSimpleName() + "</a></td>" + "\n");
			stringBufferView.append("</tr>" + "\n");
			count++;
		}		
		String pageName = demoIndexViewFrameUIPageTemplateName.substring(0, demoIndexViewFrameUIPageTemplateName.lastIndexOf(".template"));
		log("Generate WEB UI [Demo View Frame WEB UI Page Index] : " + pageName);
		String webUI = new String(demoIndexViewFrameUIPageTemplate);
		webUI = webUI.replace("${tr-view-frame-ui-demo-list}", stringBufferView.toString());		
		generateSource(webUI, new File(destPage + File.separator + pageName));
	}

	private void createViewAppletUIDemoIndexPage() {
		int count = 1;
		StringBuffer stringBufferView = new StringBuffer();
		for (Class ui : viewAppletUIClass) {
			String linkJNLP = jnlpCodeBase + "/" + ui.getSimpleName() + ".jnlp";
			stringBufferView.append("<tr>" + "\n");
			stringBufferView.append("\t<td>" + count + "</td>" + "\n");
			stringBufferView.append("\t<td>" + "Applet UI" + "</td>" + "\n");
			stringBufferView.append("\t<td>View</td>" + "\n");
			stringBufferView.append("\t<td><a href=\"" + linkJNLP + "\">" + ui.getSimpleName() + "</a></td>" + "\n");
			stringBufferView.append("</tr>" + "\n");
			count++;
		}		
		String pageName = demoIndexViewAppletUIPageTemplateName.substring(0, demoIndexViewAppletUIPageTemplateName.lastIndexOf(".template"));
		log("Generate WEB UI [Demo View Applet WEB UI Page Index] : " + pageName);
		String webUI = new String(demoIndexViewAppletUIPageTemplate);
		webUI = webUI.replace("${tr-view-applet-ui-demo-list}", stringBufferView.toString());		
		generateSource(webUI, new File(destPage + File.separator + pageName));
	}
	
	private void createX2DFrameUIDemoIndexPage() {
		int count = 1;
		StringBuffer stringBufferView = new StringBuffer();
		for (Class ui : x2dFrameUIClass) {
			String linkJNLP = jnlpCodeBase + "/" + ui.getSimpleName() + ".jnlp";
			stringBufferView.append("<tr>" + "\n");
			stringBufferView.append("\t<td>" + count + "</td>" + "\n");
			stringBufferView.append("\t<td>" + "Frame UI" + "</td>" + "\n");
			stringBufferView.append("\t<td>X2D</td>" + "\n");
			stringBufferView.append("\t<td><a href=\"" + linkJNLP + "\">" + ui.getSimpleName() + "</a></td>" + "\n");
			stringBufferView.append("</tr>" + "\n");
			count++;
		}		
		String pageName = demoIndexX2DFrameUIPageTemplateName.substring(0, demoIndexX2DFrameUIPageTemplateName.lastIndexOf(".template"));
		log("Generate WEB UI [Demo X2D Frame WEB UI Page Index] : " + pageName);
		String webUI = new String(demoIndexX2DFrameUIPageTemplate);
		webUI = webUI.replace("${tr-x2d-frame-ui-demo-list}", stringBufferView.toString());		
		generateSource(webUI, new File(destPage + File.separator + pageName));
	}

	private void createX2DAppletUIDemoIndexPage() {
		int count = 1;
		StringBuffer stringBufferView = new StringBuffer();
		for (Class ui : x2dAppletUIClass) {
			String linkJNLP = jnlpCodeBase + "/" + ui.getSimpleName() + ".jnlp";
			stringBufferView.append("<tr>" + "\n");
			stringBufferView.append("\t<td>" + count + "</td>" + "\n");
			stringBufferView.append("\t<td>" + "Applet UI" + "</td>" + "\n");
			stringBufferView.append("\t<td>X2D</td>" + "\n");
			stringBufferView.append("\t<td><a href=\"" + linkJNLP + "\">" + ui.getSimpleName() + "</a></td>" + "\n");
			stringBufferView.append("</tr>" + "\n");
			count++;
		}		
		String pageName = demoIndexX2DAppletUIPageTemplateName.substring(0, demoIndexX2DAppletUIPageTemplateName.lastIndexOf(".template"));
		log("Generate WEB UI [Demo X2D Applet WEB UI Page Index] : " + pageName);
		String webUI = new String(demoIndexX2DAppletUIPageTemplate);
		webUI = webUI.replace("${tr-x2d-applet-ui-demo-list}", stringBufferView.toString());		
		generateSource(webUI, new File(destPage + File.separator + pageName));
	}


	@Override
	public void execute() throws BuildException {

		log("JNLP generation...", Project.MSG_DEBUG);
		File baseDir = getProject().getBaseDir();
		System.out.println("base directory : " + baseDir);

		log("jnlpCodeBase : " + jnlpCodeBase);
		log("jarResourcesCodeBase : " + jarResourcesCodeBase);
		log("jarResources : " + jarResources);
		log("dest : " + destJnlp);
		log("package : " + packageRoot);

		File destDir = new File(destJnlp);

		if (!destDir.exists()) {
			throw new BuildException("JNLP task Exception, destination dir does not exist");
		}
		if (destDir.exists() && !destDir.isDirectory()) {
			throw new BuildException("JNLP task Exception, destination exist but it is not a directory");
		}

		try {

			loadTemplates();
			solve();
			createJNLPFiles();
			createDemoIndexPage();
			createViewFrameUIDemoIndexPage();
			createViewAppletUIDemoIndexPage();
			createX2DFrameUIDemoIndexPage();
			createX2DAppletUIDemoIndexPage();

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
			log("Generate Generic Source : " + file.getAbsolutePath());
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
			if(is == null){
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
	 * @return the destJnlp
	 */
	public String getDestJnlp() {
		return destJnlp;
	}

	/**
	 * @param destJnlp
	 *            the destJnlp to set
	 */
	public void setDestJnlp(String destJnlp) {
		this.destJnlp = destJnlp;
	}

	/**
	 * @return the destPage
	 */
	public String getDestPage() {
		return destPage;
	}

	/**
	 * @param destPage
	 *            the destPage to set
	 */
	public void setDestPage(String destPage) {
		this.destPage = destPage;
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

	/**
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private Iterable<Class> getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		List<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}

		return classes;
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 * 
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				classes.addAll(findClasses(file, packageName + "." + file.getName()));

			} else if (file.getName().endsWith(".class")) {
				try {
					// System.out.println(file.getName());
					// System.out.println("-->"+Class.forName(packageName + '.'
					// + file.getName().substring(0, file.getName().length() -
					// 6)));
					Class c = Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
					classes.add(c);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return classes;
	}

}