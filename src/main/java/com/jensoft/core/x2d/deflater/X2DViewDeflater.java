/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.deflater;

import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_API_KEY;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_BACKGROUND_BACKGROUND;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_BACKGROUND_COLOR;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_BACKGROUND_OUTLINEROUND;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_BACKGROUND_SHADER;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_BACKGROUND_STROKE;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_HEIGHT;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_HOLDER_EAST;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_HOLDER_NORTH;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_HOLDER_SOUTH;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_HOLDER_WEST;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_KEY;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_ROOT;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_WIDTH;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_WINDOW2D;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_WINDOW2D_ID;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_WINDOW2D_MAX_X;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_WINDOW2D_MAX_Y;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_WINDOW2D_MIN_X;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_WINDOW2D_MIN_Y;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_WINDOW2D_NAME;
import static com.jensoft.core.x2d.lang.X2DView2DElement.ELEMENT_VIEW_WINDOW2D_THEME_COLOR;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.view.View2D;
import com.jensoft.core.view.background.BackgroundPainter;
import com.jensoft.core.view.background.RoundViewFill;
import com.jensoft.core.view.deflater.AbstractViewDeflater;
import com.jensoft.core.window.Window2D;

/**
 * <code>X2DViewDeflater</code>
 * <p>
 * takes the responsibility to transform a view in X2D XML Document.
 * </p>
 * 
 * @author sebastien janaud
 */
public class X2DViewDeflater extends AbstractViewDeflater {

	/** X2D document */
	private Document x2dDocument;

	private List<AbstractX2DPluginDeflater<?>> deflaters = new ArrayList<AbstractX2DPluginDeflater<?>>();

	/**
	 * create default view deflater
	 */
	public X2DViewDeflater() {
	}

	/**
	 * create X2D Deflater
	 * 
	 * @param view2d
	 */
	public X2DViewDeflater(View2D view2d) {
		super(view2d);
	}

	/**
	 * @return the deflaters
	 */
	public List<AbstractX2DPluginDeflater<?>> getDeflaters() {
		return deflaters;
	}

	/**
	 * @param deflaters
	 *            the deflaters to set
	 */
	public void setDeflaters(List<AbstractX2DPluginDeflater<?>> deflaters) {
		this.deflaters = deflaters;
	}

	/**
	 * create element
	 * 
	 * @param element
	 *            the element name
	 * @return element
	 */
	private Element createSingleElement(String element, String value) {
		if (value == null)
			value = "";
		Element e = x2dDocument.createElement(element);
		e.appendChild(x2dDocument.createTextNode(value));
		return e;
	}

	/**
	 * create element
	 * 
	 * @param element
	 *            the element name
	 * @return element
	 */
	private Element createSingleElement(String element, int value) {
		Element e = x2dDocument.createElement(element);
		e.appendChild(x2dDocument.createTextNode(Integer.toString(value)));
		return e;
	}

	/**
	 * create element
	 * 
	 * @param element
	 *            the element name
	 * @return element
	 */
	private Element createSingleElement(String element, double value) {
		Element e = x2dDocument.createElement(element);
		e.appendChild(x2dDocument.createTextNode(Double.toString(value)));
		return e;
	}

	/**
	 * create element
	 * 
	 * @param element
	 *            the element name
	 * @return element
	 */
	private Element createColorElement(String element, Color value) {
		Element e = x2dDocument.createElement(element);
		e.appendChild(createSingleElement("r", Integer.toString(value.getRed())));
		e.appendChild(createSingleElement("g", Integer.toString(value.getGreen())));
		e.appendChild(createSingleElement("b", Integer.toString(value.getBlue())));
		e.appendChild(createSingleElement("a", Integer.toString(value.getAlpha())));
		return e;
	}

	/**
	 * create element
	 * 
	 * @param element
	 *            the element name
	 * @return element
	 */
	private Element createStrokeElement(String element, Stroke value) {
		Element e = x2dDocument.createElement(element);
		return e;
	}

	/**
	 * create element
	 * 
	 * @param element
	 *            the element name
	 * @return element
	 */
	private Element createShaderElement(String element, Shader value) {
		Element e = x2dDocument.createElement(element);
		return e;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.view.deflater.AbstractViewDeflater#deflate()
	 */
	@Override
	public Document deflate() {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			x2dDocument = documentBuilder.newDocument();

			Element view2DElement = x2dDocument.createElement(ELEMENT_VIEW_ROOT);
			x2dDocument.appendChild(view2DElement);

			view2DElement.setAttribute("xmlns", "http://www.jensoft.org/jensoft/schema/x2d");
			view2DElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			view2DElement.setAttribute("xsi:schemaLocation", "http://www.jensoft.org/jensoft/schema/x2d ../../schema/x2d.xsd");

			view2DElement.appendChild(createSingleElement(ELEMENT_VIEW_KEY, getView2D().getViewKey()));
			view2DElement.appendChild(createSingleElement(ELEMENT_API_KEY, getView2D().getApiKey()));
			view2DElement.appendChild(createSingleElement(ELEMENT_VIEW_WIDTH, getView2D().getWidth()));
			view2DElement.appendChild(createSingleElement(ELEMENT_VIEW_HEIGHT, getView2D().getHeight()));
			view2DElement.appendChild(createSingleElement(ELEMENT_VIEW_HOLDER_WEST, getView2D().getPlaceHolderAxisWest()));
			view2DElement.appendChild(createSingleElement(ELEMENT_VIEW_HOLDER_EAST, getView2D().getPlaceHolderAxisEast()));
			view2DElement.appendChild(createSingleElement(ELEMENT_VIEW_HOLDER_NORTH, getView2D().getPlaceHolderAxisNorth()));
			view2DElement.appendChild(createSingleElement(ELEMENT_VIEW_HOLDER_SOUTH, getView2D().getPlaceHolderAxisSouth()));

			BackgroundPainter painter = getView2D().getBackgroundPainter();
			if (painter instanceof RoundViewFill) {
				RoundViewFill rvf = (RoundViewFill) painter;
				Element view2DBackgroundElement = x2dDocument.createElement(ELEMENT_VIEW_BACKGROUND_BACKGROUND);
				view2DBackgroundElement.appendChild(createSingleElement(ELEMENT_VIEW_BACKGROUND_OUTLINEROUND, rvf.getOutlineRound()));
				view2DBackgroundElement.appendChild(createColorElement(ELEMENT_VIEW_BACKGROUND_COLOR, rvf.getOutlineColor()));
				view2DBackgroundElement.appendChild(createStrokeElement(ELEMENT_VIEW_BACKGROUND_STROKE, rvf.getOutlineStroke()));
				view2DBackgroundElement.appendChild(createShaderElement(ELEMENT_VIEW_BACKGROUND_SHADER, rvf.getShader()));
			}

			List<Window2D> windows = getView2D().getRegisterWindow();
			for (Window2D window2d : windows) {
				Element window2DElement = x2dDocument.createElement(ELEMENT_VIEW_WINDOW2D);
				view2DElement.appendChild(window2DElement);

				window2DElement.appendChild(createSingleElement(ELEMENT_VIEW_WINDOW2D_ID, window2d.getWindowID()));
				window2DElement.appendChild(createSingleElement(ELEMENT_VIEW_WINDOW2D_NAME, window2d.getName()));
				window2DElement.appendChild(createSingleElement(ELEMENT_VIEW_WINDOW2D_MIN_X, window2d.getMinX()));
				window2DElement.appendChild(createSingleElement(ELEMENT_VIEW_WINDOW2D_MAX_X, window2d.getMaxX()));
				window2DElement.appendChild(createSingleElement(ELEMENT_VIEW_WINDOW2D_MIN_Y, window2d.getMinY()));
				window2DElement.appendChild(createSingleElement(ELEMENT_VIEW_WINDOW2D_MAX_Y, window2d.getMaxY()));
				window2DElement.appendChild(createColorElement(ELEMENT_VIEW_WINDOW2D_THEME_COLOR, window2d.getThemeColor()));
			}

			return x2dDocument;
		} catch (DOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

		// // write the content into xml file
		// TransformerFactory transformerFactory =
		// TransformerFactory.newInstance();
		// Transformer transformer = transformerFactory.newTransformer();
		// DOMSource source = new DOMSource(doc);
		// StreamResult result = new StreamResult(new File("C:\\file.xml"));
		//
		// // Output to console for testing
		// // StreamResult result = new StreamResult(System.out);
		//
		// transformer.transform(source, result);
		//
		// System.out.println("File saved!");

	}

	public static void main(String[] args) {
		try {
			View2D view2D = new View2D();
			view2D.setSize(new Dimension(300, 200));

			Window2D w = new Window2D.Linear(-12.4, 22.9, -123, 234);
			w.setWindowID("myID");
			w.setName("myWindow name");

			view2D.registerWindow2D(w);

			X2DViewDeflater deflater = new X2DViewDeflater(view2D);

			Document x2d = deflater.deflate();

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			// transformerFactory.setAttribute("indent-number", 2);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(x2d);
			StreamResult result = new StreamResult(new File("C:/usr/x2d/view.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
