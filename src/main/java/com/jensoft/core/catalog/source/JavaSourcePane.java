/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.catalog.source;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import com.jensoft.core.catalog.styles.SectionStyle;
import com.jensoft.core.catalog.styles.SourceStyle;
import com.jensoft.core.catalog.styles.WordStyle;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.palette.TangoPalette;

/**
 * <code>SourcePane</code>
 * 
 * @author sebastien janaud
 */
public class JavaSourcePane extends JComponent {

	private static final long serialVersionUID = 3845341077628968936L;

	/** styled document */
	private DefaultStyledDocument styledDocument;

	/** rich editor */
	private JTextPane sourceTextPane;

	private StyleContext styleContext;

	public JavaSourcePane() {

		setLayout(new BorderLayout());
		setOpaque(false);
		createStyle();

		styledDocument = new DefaultStyledDocument(styleContext);

		sourceTextPane = new JTextPane(styledDocument) {
			private static final long serialVersionUID = -1726266447933631743L;

			@Override
			public Insets getInsets() {
				return new Insets(10, 10, 10, 10);
			};
		};
		sourceTextPane.setOpaque(false);
		sourceTextPane.setMargin(new Insets(10, 10, 10, 10));
		sourceTextPane.setEditable(false);

		SourceScrollPane scroll = new SourceScrollPane(sourceTextPane);
		scroll.setWheelScrollingEnabled(true);

		try {
			add(new ControlPanel(sourceTextPane), BorderLayout.NORTH);
		} catch (Error e) {
			//java.lang.NoClassDefFoundError: javax/jnlp/UnavailableServiceException
		}
		

		add(scroll, BorderLayout.CENTER);
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
	private void applyStyles() {

		final SourceStyle uijavaStyle = new SourceStyle(sourceTextPane, styledDocument.getStyle("java-source"));
		uijavaStyle.apply();

		final WordStyle uiworldStyle = new WordStyle(sourceTextPane, styledDocument.getStyle("java-modifier"), " new ", " super", "\tsuper", " private ", "\nprivate ", "\tprivate ", " void ", "\nvoid ", "\npublic ", " public ", "\tpublic ", " class ", "\nclass ", "\npackage ", "\tpackage ", " package ", "\nimport ", "\timport ", " import ", " extends ", " return ", "\nreturn ", "\treturn ", "\nfinal ", " final ", "\nfloat ", " float ", "\ndouble ", " double ", "\nint ", " int ", "\nlong ", " long ", "\nshort ", " short ");
		uiworldStyle.apply();

		final SectionStyle uicommentStyle1 = new SectionStyle(sourceTextPane, "/**", "*/", styledDocument.getStyle("java-comment2"));
		final SectionStyle uicommentStyle2 = new SectionStyle(sourceTextPane, "/*", "*/", styledDocument.getStyle("java-comment1"));
		final SectionStyle uicommentStyle3 = new SectionStyle(sourceTextPane, "//", "\n", styledDocument.getStyle("java-comment3"));

		final SectionStyle uistringStyleSection = new SectionStyle(sourceTextPane, "\"", "\"", styledDocument.getStyle("java-string"));

		uistringStyleSection.apply();
		uicommentStyle3.apply();
		uicommentStyle2.apply();
		uicommentStyle1.apply();

		final WordStyle uiannotationStyle = new WordStyle(sourceTextPane, styledDocument.getStyle("java-annotation"), "@JenSoftAPIDemo", "@Generated", "@FrameUI", "@AppletUI", "@JensoftView", "@JensoftDashboard", "@Override", "@Portfolio");
		uiannotationStyle.apply();
	}

	/**
	 * load the view source file in the current class loader
	 */
	public void loadSource(Class source) {
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
					styledDocument.insertString(styledDocument.getLength(), line + "\n", styledDocument.getStyle("java-default"));
				} catch (Exception e) {
				}
			}

			applyStyles();
			sourceTextPane.setCaretPosition(0);

		} catch (Exception e) {
			System.err.println("JenSoft API - Load source of demo failed " + inputSourceName);
		}
	}

}
