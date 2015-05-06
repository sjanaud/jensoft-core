/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jensoft.core.graphics.Shader;

/**
 * <code>DeflaterUtil</code>
 * 
 * @author Sebastien Janaud
 */
public class DeflaterUtil {

	/**
	 * create element
	 * 
	 * @param element
	 *            the element name
	 * @return element
	 */
	public static Element createSingleElement(Document x2dDocument, String element, String value) {
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
	public static Element createSingleElement(Document x2dDocument, String element, int value) {
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
	public static Element createSingleElement(Document x2dDocument, String element, double value) {
		Element e = x2dDocument.createElement(element);
		e.appendChild(x2dDocument.createTextNode(Double.toString(value)));
		return e;
	}

	/**
	 * create font element
	 * 
	 * @param element
	 *            the font element name
	 * @return element
	 */
	public static Element createFontElement(Document x2dDocument, String element, Font f) {
		// Element e = x2dDocument.createElement(element);
		// e.appendChild(createSingleElement(x2dDocument,"name", f.getName()));
		// e.appendChild(createSingleElement(x2dDocument,"style",
		// Integer.toString(f.getStyle())));
		// e.appendChild(createSingleElement(x2dDocument,"size",
		// Integer.toString(f.getSize())));
		// return e;
		return null;

	}

	/**
	 * create element
	 * 
	 * @param element
	 *            the element name
	 * @return element
	 */
	public static Element createColorElement(Document x2dDocument, String element, Color value) {
		Element e = x2dDocument.createElement(element);
		e.appendChild(createSingleElement(x2dDocument, "r", Integer.toString(value.getRed())));
		e.appendChild(createSingleElement(x2dDocument, "g", Integer.toString(value.getGreen())));
		e.appendChild(createSingleElement(x2dDocument, "b", Integer.toString(value.getBlue())));
		e.appendChild(createSingleElement(x2dDocument, "a", Integer.toString(value.getAlpha())));
		return e;
	}

	/**
	 * create element
	 * 
	 * @param element
	 *            the element name
	 * @return element
	 */
	public static Element createStrokeElement(Document x2dDocument, String element, Stroke value) {
		Element e = x2dDocument.createElement(element);
		e.appendChild(createSingleElement(x2dDocument, "width", "1"));
		return e;
	}

	/**
	 * create element
	 * 
	 * @param element
	 *            the element name
	 * @return element
	 */
	public static Element createShaderElement(Document x2dDocument,String element, Shader value) {
		Element e = x2dDocument.createElement(element);
		for (int i = 0; i < value.getFractions().length; i++) {
			Element entry = x2dDocument.createElement("shaderEntry");
			Element f =createSingleElement(x2dDocument, "f", value.getFractions()[i]);
			Element c =createColorElement(x2dDocument, "c", value.getColors()[i]);
			entry.appendChild(f);
			entry.appendChild(c);
			e.appendChild(entry);		
		}
		return e;
	}
}
