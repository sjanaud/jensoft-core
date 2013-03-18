/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.demo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.jnlp.ClipboardService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.jensoft.core.demo.component.DemoTab;
import com.jensoft.core.demo.component.DemoTabSet;
import com.jensoft.core.demo.nature.JenSoftDashboard;
import com.jensoft.core.demo.source.SourcePane;
import com.jensoft.core.demo.styles.SectionStyle;
import com.jensoft.core.demo.styles.SourceStyle;
import com.jensoft.core.demo.styles.WordStyle;
import com.jensoft.core.palette.FilPalette;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.view.View2D;

/**
 * <code>DashboardAppletUI</code>
 * 
 * @author sebastien janaud
 * 
 */
public abstract class DashboardAppletUI extends JApplet {

	private static final long serialVersionUID = 156889765687899L;


	/** style context */
	private StyleContext styleContext;

	/** JNLP clip board service */
	private ClipboardService cs = null;

	private String inset;
	private String drawOutline;
	private String cornerRadius;

	/**
	 * in Applet UI
	 */
	public void init() {
		inset = getParameter("inset");
		drawOutline = getParameter("drawOutline");
		cornerRadius = getParameter("cornerRadius");
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					create();
				}
			});
		} catch (Exception e) {
			System.err.println("Applet UI didn't successfully complete");
		}
	}

	/**
	 * get the view demo class
	 * 
	 * @return view class
	 */
	public abstract Class getDemoClass();

	private void create() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		try {
			cs = (ClipboardService) ServiceManager.lookup("javax.jnlp.ClipboardService");
		} catch (UnavailableServiceException e) {
		}

		createStyle();

		ImageIcon iconFrame = ImageResource.getInstance().createImageIcon("jensoft.png", "");		
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());

		JPanel masterPane = new JPanel();
		masterPane.setBackground(Color.WHITE);

		try {
			StringTokenizer tokenizer = new StringTokenizer(inset, ",");
			masterPane.setBorder(BorderFactory.createEmptyBorder(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
		} catch (Throwable e) {
			masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		}

		masterPane.setLayout(new BorderLayout());

		DemoTabSet tabSet = new DemoTabSet();

		tabSet.setTitle("JenSoft - API");
		if (cornerRadius != null) {
			tabSet.setCornerRadius(Integer.parseInt(cornerRadius));
		}
		if (drawOutline != null) {
			tabSet.setDrawOutline(Boolean.parseBoolean(drawOutline));
		}

		tabSet.setTitle("JenSoft");
		
		DemoTab demoTab = new DemoTab("Dashboard");
		demoTab.setTabColor(Color.DARK_GRAY);
		ImageIcon icon1 = ImageResource.getInstance().createImageIcon("demo.png", "");
		demoTab.setTabIcon(icon1);

		Dashboard dashboard = null;
		try {
			dashboard = (Dashboard) getDemoClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		tabSet.addComandTab(demoTab, dashboard);

		DemoTab uiTab = new DemoTab("UI");
		uiTab.setTabColor(FilPalette.GREEN3);
		ImageIcon icon2 = ImageResource.getInstance().createImageIcon("source.png", "");
		uiTab.setTabIcon(icon2);
		SourcePane uiSourcePane = new SourcePane(styleContext, cs);
		tabSet.addComandTab(uiTab, uiSourcePane);
		loadSource(uiSourcePane, this.getClass());
		applyStyles(uiSourcePane);

		DemoTab dashboardTab = new DemoTab("Dashboard");
		dashboardTab.setTabColor(JennyPalette.JENNY6);
		ImageIcon icon = ImageResource.getInstance().createImageIcon("source.png", "");
		dashboardTab.setTabIcon(icon);
		SourcePane dashboardSourcePane = new SourcePane(styleContext, cs);
		tabSet.addComandTab(dashboardTab, dashboardSourcePane);
		loadSource(dashboardSourcePane, dashboard.getClass());
		applyStyles(dashboardSourcePane);

		dashboardSourcePane.getSourceTextPane().setCaretPosition(0);
		uiSourcePane.getSourceTextPane().setCaretPosition(0);

		if (dashboard.getClass().isAnnotationPresent(JenSoftDashboard.class)) {
			JenSoftDashboard dashboardAnnotation = dashboard.getClass().getAnnotation(JenSoftDashboard.class);
			Class[] views = dashboardAnnotation.views();
			for (int i = 0; i < views.length; i++) {
				if (View2D.class.isAssignableFrom(views[i])) {
					DemoTab viewContributorTab = new DemoTab(views[i].getSimpleName());
					viewContributorTab.setTabColor(JennyPalette.JENNY6);
					ImageIcon vIcon = ImageResource.getInstance().createImageIcon("source.png", "");
					viewContributorTab.setTabIcon(vIcon);
					SourcePane viewSourcePane = new SourcePane(styleContext, cs);
					tabSet.addComandTab(viewContributorTab, viewSourcePane);
					loadSource(viewSourcePane, views[i]);
					applyStyles(viewSourcePane);
				}
			}
		}

		demoTab.setSelected(true);

		masterPane.add(tabSet, BorderLayout.CENTER);

		getContentPane().add(masterPane, BorderLayout.CENTER);
		setVisible(true);
	}

	private void createStyle() {
		styleContext = new StyleContext();

		final Style javaSourceStyle = styleContext.addStyle("java-source", null);
		StyleConstants.setLeftIndent(javaSourceStyle, 60);
		StyleConstants.setRightIndent(javaSourceStyle, 16);
		StyleConstants.setFirstLineIndent(javaSourceStyle, 60);
		StyleConstants.setFontFamily(javaSourceStyle, "lucida console");
		StyleConstants.setFontSize(javaSourceStyle, 11);
		StyleConstants.setForeground(javaSourceStyle, TangoPalette.ALUMINIUM6);

		final Style wordJavaStyle = styleContext.addStyle("java-modifier", null);
		StyleConstants.setFontFamily(wordJavaStyle, "lucida console");
		StyleConstants.setFontSize(wordJavaStyle, 11);
		StyleConstants.setForeground(wordJavaStyle, RosePalette.COALBLACK);
		StyleConstants.setBold(wordJavaStyle, true);

		final Style wordJavaComment = styleContext.addStyle("java-comment1", null);
		StyleConstants.setFontFamily(wordJavaComment, "lucida console");
		StyleConstants.setFontSize(wordJavaComment, 11);
		StyleConstants.setForeground(wordJavaComment, RosePalette.CORALRED);

		final Style wordJavaComment2 = styleContext.addStyle("java-comment2", null);
		StyleConstants.setFontFamily(wordJavaComment2, "lucida console");
		StyleConstants.setFontSize(wordJavaComment2, 11);
		StyleConstants.setForeground(wordJavaComment2, new Color(63, 127, 95));
		StyleConstants.setForeground(wordJavaComment2, RosePalette.COBALT);

		final Style wordJavaComment3 = styleContext.addStyle("java-comment3", null);
		StyleConstants.setFontFamily(wordJavaComment3, "lucida console");
		StyleConstants.setFontSize(wordJavaComment3, 11);
		StyleConstants.setForeground(wordJavaComment3, new Color(63, 127, 95));
		StyleConstants.setForeground(wordJavaComment3, TangoPalette.ALUMINIUM2);

		final Style stringStyle = styleContext.addStyle("java-string", null);
		StyleConstants.setFontFamily(stringStyle, "lucida console");
		StyleConstants.setFontSize(stringStyle, 11);
		StyleConstants.setForeground(stringStyle, new Color(63, 127, 95));
		StyleConstants.setForeground(stringStyle, TangoPalette.ORANGE1);

		final Style wordJavaAnnotation = styleContext.addStyle("java-annotation", null);
		StyleConstants.setFontFamily(wordJavaAnnotation, "lucida console");
		StyleConstants.setFontSize(wordJavaAnnotation, 11);
		StyleConstants.setForeground(wordJavaAnnotation, TangoPalette.BUTTER1);

	}

	/**
	 * apply attributes style to demo source
	 */
	protected void applyStyles(SourcePane sourcePane) {

		final SourceStyle uijavaStyle = new SourceStyle(sourcePane.getSourceTextPane(), sourcePane.getStyledDocument().getStyle("java-source"));
		uijavaStyle.apply();

		final WordStyle uiworldStyle = new WordStyle(sourcePane.getSourceTextPane(), sourcePane.getStyledDocument().getStyle("java-modifier"), " new ", " super", "\tsuper", " private ", "\nprivate ", "\tprivate ", " void ", "\nvoid ", "\npublic ", " public ", "\tpublic ", " class ", "\nclass ", "\npackage ", "\tpackage ", " package ", "\nimport ", "\timport ", " import ", " extends ", " return ", "\nreturn ", "\treturn ", "\nfinal ", " final ", "\nfloat ", " float ", "\ndouble ", " double ", "\nint ", " int ", "\nlong ", " long ", "\nshort ", " short ");
		uiworldStyle.apply();

		final SectionStyle uicommentStyle1 = new SectionStyle(sourcePane.getSourceTextPane(), "/**", "*/", sourcePane.getStyledDocument().getStyle("java-comment2"));
		final SectionStyle uicommentStyle2 = new SectionStyle(sourcePane.getSourceTextPane(), "/*", "*/", sourcePane.getStyledDocument().getStyle("java-comment1"));
		final SectionStyle uicommentStyle3 = new SectionStyle(sourcePane.getSourceTextPane(), "//", "\n", sourcePane.getStyledDocument().getStyle("java-comment3"));

		final SectionStyle uistringStyleSection = new SectionStyle(sourcePane.getSourceTextPane(), "\"", "\"", sourcePane.getStyledDocument().getStyle("java-string"));

		uistringStyleSection.apply();
		uicommentStyle3.apply();
		uicommentStyle2.apply();
		uicommentStyle1.apply();

		final WordStyle uiannotationStyle = new WordStyle(sourcePane.getSourceTextPane(), sourcePane.getStyledDocument().getStyle("java-annotation"), "@JenSoftAPIDemo", "@Generated", "@FrameUI", "@AppletUI", "@JensoftView", "@Override", "@Portfolio");
		uiannotationStyle.apply();
	}

	/**
	 * load the view source file in the current class loader
	 */
	private void loadSource(SourcePane sourcePane, Class source) {
		System.out.println("JenSoft API - Load View source");
		String inputSourceName = "NA";
		try {
			ClassLoader cloader = source.getClassLoader();
			String packageName = source.getPackage().getName();
			inputSourceName = packageName.replace('.', '/') + "/" + source.getSimpleName() + ".java";

			InputStream is = cloader.getResourceAsStream(inputSourceName);
			InputStreamReader isreader = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isreader);
			String line = null;

			while ((line = in.readLine()) != null) {
				try {
					StyledDocument doc = sourcePane.getStyledDocument();
					doc.insertString(doc.getLength(), line + "\n", doc.getStyle("java-default"));
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
			System.err.println("JenSoft API - Load source of demo failed " + inputSourceName);
		}
	}

}
