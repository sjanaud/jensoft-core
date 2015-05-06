/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jensoft.core.graphics.Shader;

/**
 * <code>InflaterUtil</code>
 * 
 * @author Sebastien Janaud
 */
public class InflaterUtil {

    public static String format(Color c) {
        return c.getRed() + "." + c.getGreen() + "." + c.getBlue() + "."
                + c.getAlpha();
    }
    
    /**
     * parse the child element from parent as {@link Color}
     * @param parent
     *            the parent element
     * @param childName
     *            the color child name
     * @return color
     */
    public static Color elementColor(Element parent, String childName) {

        Element colorElement = (Element) parent.getElementsByTagName(childName).item(0);
        return elementColor(colorElement);
    }

    /**
     * parse the color element into {@link Color}
     * 
     * @param colorElement
     *            the color element to parse
     * @return color
     */
    public static Color elementColor(Element colorElement) {

        if (colorElement == null || colorElement.equals("undefined")) {
            return null;
        }
        Color black = Color.black;
        Color themeColor = null;
        if (colorElement != null) {
            try {
                int r = Integer.parseInt(elementText(colorElement, "r"));
                int g = Integer.parseInt(elementText(colorElement, "g"));
                int b = Integer.parseInt(elementText(colorElement, "b"));
                int a = Integer.parseInt(elementText(colorElement, "a"));
                themeColor = new Color(r, g, b, a);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return themeColor;
    }
    
    /**
     * parse the child element from parent as {@link Font}
     * @param parent
     *            the parent element
     * @param childName
     *            the font child name
     * @return font
     */
    public static Font elementFont(Element parent, String childName) {
        Element fontElement = (Element) parent.getElementsByTagName(childName).item(0);
        Font f = new Font("Dialog", Font.PLAIN, 12);
        return f;
    }

    /**
     * parse the specified font string into {@link Font}
     * 
     * @param font
     *            the element to parse
     * @return font
     */
    public static Font parseFont(String font) {
    	Font f = new Font("Dialog", Font.PLAIN, 12);
        return f;
    }

    
    /**
     * parse the child element from parent as {@link Stroke}
     * @param parent
     *            the parent element
     * @param childName
     *            the stroke child name
     * @return stroke
     */
    public static Stroke elementStroke(Element parent, String childName) {
        Element strokeElement = (Element) parent.getElementsByTagName(childName).item(0);
        return elementStroke(strokeElement);
    }
    
    /**
     * parse stroke element into {@link Stroke}
     * 
     * @param strokeElement
     *            the stroke element to parse
     * @return stroke
     */
    public static Stroke elementStroke(Element strokeElement) {

        if (strokeElement == null) {
            return new BasicStroke();
        }

        Element widthElement = (Element) strokeElement.getElementsByTagName("width").item(0);
        Element joinElement = (Element) strokeElement.getElementsByTagName("join").item(0);
        Element capElement = (Element) strokeElement.getElementsByTagName("cap").item(0);
        Element miterLimitElement = (Element) strokeElement.getElementsByTagName("miterlimit").item(0);
        Element dashElement = (Element) strokeElement.getElementsByTagName("dash").item(0);
        Element dashPhaseElement = (Element) strokeElement.getElementsByTagName("dashphase").item(0);

        String w = widthElement.getTextContent();
        String j = null;
        String c = null;
        String ml = null;
        NodeList d = null;
        String dp = null;

        if (joinElement != null) {
            j = joinElement.getTextContent();
        }
        if (capElement != null) {
            c = capElement.getTextContent();
        }
        if (miterLimitElement != null) {
            ml = miterLimitElement.getTextContent();
        }
        if (dashElement != null) {
            d = dashElement.getElementsByTagName("dashEntry");
        }
        if (dashPhaseElement != null) {
            dp = dashPhaseElement.getTextContent();
        }

        float width = 1;
        int join = 0;
        int cap = 2;
        float miterlimit = 10.0f;
        float[] dash = null;
        float dash_phase = 0.0f;

        if (d != null && !d.equals("undefined")) {
            try {

                dash = new float[d.getLength()];
                for (int i = 0; i < d.getLength(); i++) {
                    Element e = (Element) d.item(i);
                    dash[i] = Float.parseFloat(e.getNodeValue());
                }
            }
            catch (NumberFormatException e) {
                dash = null;
            }
        }

        if (w != null && !w.equals("undefined")) {
            try {
                width = Float.parseFloat(w);
            }
            catch (NumberFormatException e) {
                width = 1;
            }
        }

        if (ml != null && !ml.equals("undefined")) {
            try {
                miterlimit = Float.parseFloat(ml);
            }
            catch (NumberFormatException e) {
                miterlimit = 10.0f;
            }
        }

        if (dp != null && !dp.equals("undefined")) {
            try {
                dash_phase = Float.parseFloat(dp);
            }
            catch (NumberFormatException e) {
                dash_phase = 0.0f;
            }
        }

        if (j != null && !j.equals("undefined")) {
            if (j.equals("0") || j.equals("miter")) {
                join = 0;
            }
            if (j.equals("1") || j.equals("round")) {
                join = 1;
            }
            if (j.equals("2") || j.equals("bevel")) {
                join = 2;
            }
        }

        if (c != null && !c.equals("undefined")) {
            if (c.equals("0") || c.equals("butt")) {
                cap = 0;
            }
            if (c.equals("1") || c.equals("round")) {
                cap = 1;
            }
            if (c.equals("2") || c.equals("square")) {
                cap = 2;
            }
        }

        return new BasicStroke(width, cap, join, miterlimit, dash, dash_phase);
    }

    /**
     * parse the child element from parent as {@link Shader}
     * @param parent
     *            the parent element
     * @param childName
     *            the shader child name
     * @return shader
     */
    public static Shader elementShader(Element parent, String childName) {
        Element shaderElement = (Element) parent.getElementsByTagName(childName).item(0);
        return elementShader(shaderElement);
    }

    /**
     * parse the specified shader element into {@link Shader}
     * 
     * @param shaderElement
     *            the shader element to parse
     * @return shader
     */
    public static Shader elementShader(Element shaderElement) {

        if (shaderElement == null) {
            return null;
        }

        NodeList sElements = shaderElement.getElementsByTagName("shaderEntry");
        int length = sElements.getLength();

        float[] shaderFractions = new float[length];
        Color[] shaderColors = new Color[length];

        for (int i = 0; i < sElements.getLength(); i++) {
            Element sElement = (Element) sElements.item(i);

            Element fractionElement = (Element) sElement.getElementsByTagName("f").item(0);
            Element colorElement = (Element) sElement.getElementsByTagName("c").item(0);

            float fraction = Float.parseFloat(fractionElement.getTextContent());
            Color color = elementColor(colorElement);

            shaderFractions[i] = fraction;
            shaderColors[i] = color;

        }

        Shader shader = new Shader(shaderFractions, shaderColors);
        return shader;
    }
    
   
    /**
     * trim specified children element value from specified parent
     * 
     * @param parent
     *            the parent element
     * @param childName
     *            the children name
     * @return children element string trim value
     */
    public static String elementText(Element parent, String childName) {
        if(parent == null || childName == null)
            return null;
        Element childElement = (Element) parent.getElementsByTagName(childName).item(0);
        if(childElement == null)
            return null;
        return childElement.getTextContent();
    }

    /**
     * trim and parse as double specified text contains in children element value from specified parent
     * 
     * @param parent
     *            the parent element
     * @param childName
     *            the children name
     * @return children element double value
     */
    public static Double elementDouble(Element parent, String childName) {
        Element childElement = (Element) parent.getElementsByTagName(childName).item(0);
        if (childElement != null) {
            return Double.parseDouble(childElement.getTextContent());
        }
        return null;
    }

    /**
     * trim and parse as integer specified text contains in children element value from specified parent
     * 
     * @param parent
     *            the parent element
     * @param childName
     *            the children name
     * @return children element integer value
     */
    public static Integer elementInteger(Element parent, String childName) {
        Element childElement = (Element) parent.getElementsByTagName(childName).item(0);
        if (childElement != null) {
            return Integer.parseInt(childElement.getTextContent());
        }
        return null;
    }
    
    /**
     * trim and parse as boolean specified text contains in children element value from specified parent
     * 
     * @param parent
     *            the parent element
     * @param childName
     *            the children name
     * @return children element boolean value
     */
    public static Boolean elementBoolean(Element parent, String childName) {
        Element childElement = (Element) parent.getElementsByTagName(childName).item(0);
        if (childElement != null) {
            return Boolean.parseBoolean(childElement.getTextContent());
        }
        return null;
    }
    
    /**
     * trim and parse as float specified text contains in children element value from specified parent
     * 
     * @param parent
     *            the parent element
     * @param childName
     *            the children name
     * @return children element float value
     */
    public static Float elementFloat(Element parent, String childName) {
        Element childElement = (Element) parent.getElementsByTagName(childName).item(0);
        if (childElement != null) {
            return Float.parseFloat(childElement.getTextContent());
        }
        return null;
    }

    /**
     * get XSI type attribute
     * 
     * @return xsi type
     */
    public static String getType(Element elementType) {
        return elementType.getAttribute("xsi:type");
    }

}
