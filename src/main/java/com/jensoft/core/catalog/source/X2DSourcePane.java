/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.catalog.source;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.io.StringWriter;

import javax.swing.JComponent;
import javax.swing.text.StyledDocument;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.jensoft.core.catalog.xml.XmlTextPane;
import com.jensoft.core.x2d.X2D;

/**
 * <code>XMLSourcePane</code>
 * 
 * @author sebastien janaud
 */
public class X2DSourcePane extends JComponent {

	private static final long serialVersionUID = 3845341077628968936L;

	private XmlTextPane sourceTextPane;

	public X2DSourcePane() {

		setLayout(new BorderLayout());
		setOpaque(false);

		sourceTextPane = new XmlTextPane() {

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
			// java.lang.NoClassDefFoundError: javax/jnlp/UnavailableServiceException
		}
		

		add(scroll, BorderLayout.CENTER);
	}

	/**
	 * load the source file in the current class loader
	 */
	public void loadX2DSource(X2D x2d) {
		try {
			Document doc = x2d.getX2dDocument();

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();

			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");

			StringWriter sw = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(sw));

			StyledDocument styledDoc = sourceTextPane.getStyledDocument();
			styledDoc.insertString(0, sw.toString(), null);
		} catch (Exception e) {
			System.err.println("JenSoft API - Load x2d source of demo failed with error :" + e.getMessage());
		}
	}

}
