package com.jensoft.core.demo.source;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.jnlp.ClipboardService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;

import com.jensoft.core.desktop.viewsbase.SScrollPane;

/**
 * <code>SourcePane</code>
 * 
 * @author sebastien janaud
 */
public class SourcePane extends JComponent {

	private static final long serialVersionUID = 3845341077628968936L;
	
	/** styled document */
	private DefaultStyledDocument styledDocument;
	
	/** rich editor */
	private JTextPane sourceTextPane;

	public SourcePane(StyleContext styleContext,final ClipboardService cs) {		

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
			add(new ControlPanel(sourceTextPane,cs), BorderLayout.NORTH);
		}
		add(scroll, BorderLayout.CENTER);
	}

	/**
	 * @return the styledDocument
	 */
	public DefaultStyledDocument getStyledDocument() {
		return styledDocument;
	}

	/**
	 * @return the sourceTextPane
	 */
	public JTextPane getSourceTextPane() {
		return sourceTextPane;
	}
	
	

}
