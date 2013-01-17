/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.drawable.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.jensoft.core.drawable.Drawable;
import com.jensoft.core.graphics.Shader;

/**
 * label defines a text label with in a box. box outline is draw with a color
 * and a stroke. box can be fill with a single color or fill with the given
 * shader.
 * label is draw with the specified text, font and text color label box outline
 * is draw with the specified outline color and stroke
 * 
 * @author Sebastien janaud
 */
public class Label implements Drawable {

    private String text;
    private Font font;
    private Color textColor;
    private int textPaddingX = 5;
    private int textPaddingY = 2;

    private Color outlineColor;
    private Stroke outlineStroke;

    private Color fillColor;
    private Shader shader;

    private double x;
    private double y;

    private Anchor anchor = Anchor.Center;

    public enum Anchor {
        North("North"), South("South"), West("West"), East("East"), Center(
                "Center"), Text("Text");

        private String anchor;

        private Anchor(String anchor) {
            this.anchor = anchor;
        }

        /**
         * @return the anchor
         */
        public String getAnchor() {
            return anchor;
        }

        /**
         * @param anchor
         *            the anchor to set
         */
        public void setAnchor(String anchor) {
            this.anchor = anchor;
        }

    }

    @Override
    public void draw(Graphics2D g2d) {

        if (text == null || text.equals("")) {
            return;
        }

        // tout definir a partir de ce point
        double xText;
        double yText;

        if (font != null) {
            g2d.setFont(font);
        }

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int textAscent = fm.getAscent();
        int textDescent = fm.getDescent();

        if (anchor == Anchor.Center) {
            xText = x - textWidth / 2;
            yText = y;
        }
        else if (anchor == Anchor.South) {
            xText = x - textWidth / 2;
            yText = y - textPaddingY - textHeight;
        }
        else if (anchor == Anchor.North) {
            xText = x - textWidth / 2;
            yText = y + textPaddingY + textDescent;
        }
        else if (anchor == Anchor.West) {
            xText = x - textWidth / 2;
            yText = y;
        }
        else if (anchor == Anchor.East) {
            xText = x - textWidth / 2;
            yText = y;
        }
        else if (anchor == Anchor.Text) {
            xText = x - textWidth / 2;
            yText = y;
        }
    }

}
