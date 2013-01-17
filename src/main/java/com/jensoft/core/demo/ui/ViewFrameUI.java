/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.demo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.jnlp.ClipboardService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.jensoft.core.demo.ImageResource;
import com.jensoft.core.demo.component.DemoTab;
import com.jensoft.core.demo.component.DemoTabSet;
import com.jensoft.core.demo.styles.SectionStyle;
import com.jensoft.core.demo.styles.SourceStyle;
import com.jensoft.core.demo.styles.WordStyle;
import com.jensoft.core.desktop.viewsbase.SScrollPane;
import com.jensoft.core.palette.FilPalette;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.view.View2D;

/**
 * <code>ViewDemoFrameUI</code>
 * 
 * @author sebastien janaud
 */
public class ViewFrameUI extends JFrame {

	private static final long serialVersionUID = 156889765687899L;

	/** view 2D */
	private View2D view;	

	/** source flag */
	private boolean showSource = true;

	/** style context */
	private StyleContext styleContext;
	
	/** demo view source pane */
	private SourcePane viewSourcePane;

	/** styled document */
	private DefaultStyledDocument styledDocument;
	
	/** rich editor */
	private JTextPane sourceTextPane;
	
	/** demo ui source pane */
	private UISourcePane uiSourcePane;	

	/** styled document */
	private DefaultStyledDocument uistyledDocument;
	
	/** rich editor */
	private JTextPane uisourceTextPane;

	

	

	/** JNLP clip board service */
	private ClipboardService cs = null;

	/**
	 * Create and Show frame UI with the given view
	 * 
	 * @param view
	 */
	public ViewFrameUI(View2D view) {
		this.view = view;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				create();
			}
		});
		pack();
		setSize(1024, 700);
	}

	/**
	 * show the given demo in the demo frame
	 * 
	 * @param demo
	 *            the demo to show
	 */
	private void create() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}
		ImageIcon iconFrame = ImageResource.getInstance().createImageIcon("jensoft.png", "");
		setIconImage(iconFrame.getImage());
		setTitle("JenSoft API");
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel masterPane = new JPanel();
		masterPane.setBackground(Color.BLACK);

		masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		masterPane.setLayout(new BorderLayout());

		DemoTabSet tabSet = new DemoTabSet();
		tabSet.setTitle("JenSoft - API");

		DemoTab demoTab = new DemoTab("Demo");

		demoTab.setTabColor(Color.DARK_GRAY);
		ImageIcon icon1 = ImageResource.getInstance().createImageIcon("demo.png", "");
		demoTab.setTabIcon(icon1);

		DemoTab sourceTab = new DemoTab("View Source");
		sourceTab.setTabColor(JennyPalette.JENNY6);
		ImageIcon icon = ImageResource.getInstance().createImageIcon("source.png", "");
		sourceTab.setTabIcon(icon);

		DemoTab uisourceTab = new DemoTab("UI");
		uisourceTab.setTabColor(FilPalette.GREEN3);
		ImageIcon icon2 = ImageResource.getInstance().createImageIcon("source.png", "");
		uisourceTab.setTabIcon(icon2);

		tabSet.addComandTab(demoTab, view);

		if (showSource) {
			styleContext = new StyleContext();
			viewSourcePane = new SourcePane();
			tabSet.addComandTab(sourceTab, viewSourcePane);
			uiSourcePane = new UISourcePane();
			tabSet.addComandTab(uisourceTab, uiSourcePane);
			loadViewSource();
			loadUISource();
			applyStyles();

			sourceTextPane.setCaretPosition(0);
			uisourceTextPane.setCaretPosition(0);
		}

		demoTab.setSelected(true);

		masterPane.add(tabSet, BorderLayout.CENTER);

		getContentPane().add(masterPane, BorderLayout.CENTER);
		setVisible(true);
	}

	/**
	 * @return the showSource
	 */
	public boolean isShowSource() {
		return showSource;
	}

	/**
	 * @param showSource
	 *            the showSource to set
	 */
	public void setShowSource(boolean showSource) {
		this.showSource = showSource;
	}

	/**
	 * control panel
	 */
	class ControlPanel extends JComponent {
		private final JTextPane textPane;

		public ControlPanel(final JTextPane textPane) {
			this.textPane = textPane;
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setOpaque(false);

			JButton copy = new JButton("copy");
			copy.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						StringSelection data;
						data = new StringSelection(textPane.getText());
						cs.setContents(data);
					} catch (Exception e1) {
					}

				}
			});
			add(Box.createGlue());
			add(copy);
			add(Box.createHorizontalStrut(40));
		}

		private static final long serialVersionUID = -120338937746225277L;

	}

	/**
	 * <code>SourcePane</code>
	 * 
	 * @author sebastien janaud
	 */
	class SourcePane extends JComponent {

		private static final long serialVersionUID = 3845341077628968936L;

		public SourcePane() {
			try {
				cs = (ClipboardService) ServiceManager.lookup("javax.jnlp.ClipboardService");
			} catch (UnavailableServiceException e) {
				cs = null;
			}

			setLayout(new BorderLayout());
			setOpaque(false);

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

			SScrollPane scroll = new SScrollPane(sourceTextPane);
			scroll.setWheelScrollingEnabled(true);

			if (cs != null) {
				add(new ControlPanel(sourceTextPane), BorderLayout.NORTH);
			}
			add(scroll, BorderLayout.CENTER);
		}

	}

	/**
	 * <code>SourcePane</code>
	 * 
	 * @author sebastien janaud
	 */
	class UISourcePane extends JComponent {

		private static final long serialVersionUID = 3845341077628968936L;

		public UISourcePane() {
			try {
				cs = (ClipboardService) ServiceManager.lookup("javax.jnlp.ClipboardService");
			} catch (UnavailableServiceException e) {
				cs = null;
			}

			setLayout(new BorderLayout());
			setOpaque(false);

			uistyledDocument = new DefaultStyledDocument(styleContext);

			uisourceTextPane = new JTextPane(uistyledDocument) {

				private static final long serialVersionUID = -1726266447933631743L;

				@Override
				public Insets getInsets() {
					return new Insets(10, 10, 10, 10);
				};
			};
			uisourceTextPane.setOpaque(false);
			uisourceTextPane.setMargin(new Insets(10, 10, 10, 10));
			uisourceTextPane.setEditable(false);

			SScrollPane scroll = new SScrollPane(uisourceTextPane);
			scroll.setWheelScrollingEnabled(true);

			if (cs != null) {
				add(new ControlPanel(uisourceTextPane), BorderLayout.NORTH);
			}
			add(scroll, BorderLayout.CENTER);
		}

	}

	/**
	 * apply attributes style to demo source
	 */
	protected void applyStyles() {
		System.out.println("JenSoft API - Apply styles of demo source");
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

		// View
		final SourceStyle javaStyle = new SourceStyle(sourceTextPane, styledDocument.getStyle("java-source"));
		javaStyle.apply();

		final WordStyle worldStyle = new WordStyle(sourceTextPane, styledDocument.getStyle("java-modifier"), " new "," super","\tsuper", " private ", "\nprivate ","\tprivate ", " void ", "\nvoid ", "\npublic ", " public ",  "\tpublic "," class ", "\nclass ", "\npackage ","\tpackage ", " package ", "\nimport ", "\timport ", " import ", " extends ", " return ", "\nreturn ","\treturn ", "\nfinal ", " final ", "\nfloat ", " float ", "\ndouble ", " double ", "\nint ", " int ", "\nlong ", " long ", "\nshort ", " short ");
		worldStyle.apply();

		final SectionStyle commentStyle1 = new SectionStyle(sourceTextPane, "/**", "*/", styledDocument.getStyle("java-comment2"));
		final SectionStyle commentStyle2 = new SectionStyle(sourceTextPane, "/*", "*/", styledDocument.getStyle("java-comment1"));
		final SectionStyle commentStyle3 = new SectionStyle(sourceTextPane, "//", "\n", styledDocument.getStyle("java-comment3"));

		final SectionStyle stringStyleSection = new SectionStyle(sourceTextPane, "\"", "\"", styledDocument.getStyle("java-string"));

		stringStyleSection.apply();
		commentStyle3.apply();
		commentStyle2.apply();
		commentStyle1.apply();

		final WordStyle annotationStyle = new WordStyle(sourceTextPane, styledDocument.getStyle("java-annotation"), "@JenSoftAPIDemo","@Generated","@FrameUI","@AppletUI","@JensoftView", "@Override", "@Portfolio");

		annotationStyle.apply();

		// UI
		final SourceStyle uijavaStyle = new SourceStyle(uisourceTextPane, uistyledDocument.getStyle("java-source"));
		uijavaStyle.apply();	
		
		final WordStyle uiworldStyle = new WordStyle(uisourceTextPane, uistyledDocument.getStyle("java-modifier"), " new "," super","\tsuper", " private ", "\nprivate ","\tprivate ", " void ", "\nvoid ", "\npublic ", " public ",  "\tpublic "," class ", "\nclass ", "\npackage ","\tpackage ", " package ", "\nimport ", "\timport ", " import ", " extends ", " return ", "\nreturn ","\treturn ", "\nfinal ", " final ", "\nfloat ", " float ", "\ndouble ", " double ", "\nint ", " int ", "\nlong ", " long ", "\nshort ", " short ");
		uiworldStyle.apply();

		final SectionStyle uicommentStyle1 = new SectionStyle(uisourceTextPane, "/**", "*/", uistyledDocument.getStyle("java-comment2"));
		final SectionStyle uicommentStyle2 = new SectionStyle(uisourceTextPane, "/*", "*/", uistyledDocument.getStyle("java-comment1"));
		final SectionStyle uicommentStyle3 = new SectionStyle(uisourceTextPane, "//", "\n", uistyledDocument.getStyle("java-comment3"));

		final SectionStyle uistringStyleSection = new SectionStyle(uisourceTextPane, "\"", "\"", uistyledDocument.getStyle("java-string"));

		uistringStyleSection.apply();
		uicommentStyle3.apply();
		uicommentStyle2.apply();
		uicommentStyle1.apply();

		final WordStyle uiannotationStyle = new WordStyle(uisourceTextPane, uistyledDocument.getStyle("java-annotation"), "@JenSoftAPIDemo","@Generated","@FrameUI","@AppletUI","@JensoftView", "@Override", "@Portfolio");
		uiannotationStyle.apply();		

		System.out.println("JenSoft API - Styles applied successfully.");
	}

	/**
	 * load the source file in the current class loader
	 */
	private void loadUISource() {
		System.out.println("JenSoft API - Load UI source");
		String inputSourceName = "NA";
		try {
			ClassLoader cloader = getClass().getClassLoader();
			String packageName = getClass().getPackage().getName();
			System.out.println("ui package : " + packageName);
			inputSourceName = packageName.replace('.', '/') + "/" + getClass().getSimpleName() + ".java";

			InputStream is = cloader.getResourceAsStream(inputSourceName);
			InputStreamReader isreader = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isreader);
			String line = null;

			while ((line = in.readLine()) != null) {
				try {
					StyledDocument doc = uisourceTextPane.getStyledDocument();
					doc.insertString(doc.getLength(), line + "\n", doc.getStyle("java-default"));
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
			System.err.println("JenSoft API - Load source of demo failed.");
		}
	}

	/**
	 * load the view source file in the current class loader
	 */
	private void loadViewSource() {
		System.out.println("JenSoft API - Load View source");
		String inputSourceName = "NA";
		try {
			ClassLoader cloader = view.getClass().getClassLoader();
			String packageName = view.getClass().getPackage().getName();
			inputSourceName = packageName.replace('.', '/') + "/" + view.getClass().getSimpleName() + ".java";

			InputStream is = cloader.getResourceAsStream(inputSourceName);
			InputStreamReader isreader = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isreader);
			String line = null;

			while ((line = in.readLine()) != null) {
				try {
					StyledDocument doc = sourceTextPane.getStyledDocument();
					doc.insertString(doc.getLength(), line + "\n", doc.getStyle("java-default"));
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
			System.err.println("JenSoft API - Load source of demo failed.");
		}
	}

}
