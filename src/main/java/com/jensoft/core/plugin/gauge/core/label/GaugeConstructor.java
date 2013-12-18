/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.label;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.glyphmetrics.GlyphUtil;
import com.jensoft.core.palette.PetalPalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.ConstructorGaugePainter;

public class GaugeConstructor extends ConstructorGaugePainter {

    public GaugeConstructor() {

    }

    @Override
    public void paintConstructor(Graphics2D g2d, RadialGauge radialGauge) {

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
        int radius = getGauge().getRadius();
        int radius2 = getGauge().getRadius() + 5;

        Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius,
                                       2 * radius, 2 * radius, 90, 360, Arc2D.OPEN);

        Arc2D arc2d2 = new Arc2D.Double(centerX - radius2, centerY - radius2,
                                        2 * radius2, 2 * radius2, 90, 360, Arc2D.OPEN);

        // g2d.setStroke(new BasicStroke(0.8f));
        g2d.setColor(TangoPalette.PLUM2);
        // g2d.draw(arc2d);

        GeometryPath geometry = new GeometryPath(arc2d);
        GeometryPath geometry2 = new GeometryPath(arc2d2);

        Font f = new Font("Dialog", Font.PLAIN, 10);
        Font f2 = new Font("Dialog", Font.PLAIN, 8);
        String copyright = "- Compass Carbon  *** Sail Instrument ***  all right reserved SW2D -";
        String madein = "Stainless steel - Made in France";
        GlyphVector glyphVector = f.createGlyphVector(
                                                      g2d.getFontRenderContext(), copyright);
        GlyphVector glyphVector2 = f2.createGlyphVector(
                                                        g2d.getFontRenderContext(), madein);
        AffineTransform af = new AffineTransform();
        AffineTransform af2 = new AffineTransform();

        float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);
        float gvWidth2 = GlyphUtil.getGlyphWidth(glyphVector2);

        float startLength = geometry.lengthOfPath() / 2 - gvWidth / 2;
        float startLength2 = geometry2.lengthOfPath() / 2 - gvWidth2 / 2;

        // int c_p = 49;
        int c_p = copyright.indexOf("SW2D");
        int c_p2 = copyright.lastIndexOf("-");

        for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

            Point2D p = glyphVector.getGlyphPosition(j);
            float px = (float) p.getX();
            float py = (float) p.getY();
            Point2D pointGlyph;

            pointGlyph = geometry.pointAtLength(startLength
                    + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));

            if (pointGlyph == null) {
                continue;
            }
            Shape glyph = glyphVector.getGlyphOutline(j);

            float angle = geometry.angleAtLength(startLength
                    + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
            af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
            af.rotate(angle);
            af.translate(-px, -py + glyphVector.getVisualBounds().getHeight()
                    / 2 - 10);

            Shape ts = af.createTransformedShape(glyph);

            g2d.setColor(Color.WHITE);

            if (j == c_p2) {
                g2d.setColor(Color.RED);
            }
            if (j == c_p2 + 1) {
                g2d.setColor(Color.RED);
            }

            if (j == c_p - 6 || j == c_p - 7 || j == c_p - 8 || j == c_p - 9) {
                g2d.setColor(Color.WHITE);
            }
            if (j == c_p - 1 || j == c_p - 2 || j == c_p - 3 || j == c_p - 4
                    || j == c_p - 5) {
                g2d.setColor(PetalPalette.PETAL4_HC);
            }
            if (j == c_p) {
                g2d.setColor(PetalPalette.PETAL5_HC);
            }
            if (j == c_p + 1) {
                g2d.setColor(PetalPalette.PETAL6_HC);
            }
            if (j == c_p + 2) {
                g2d.setColor(PetalPalette.PETAL7_HC);
            }
            if (j == c_p + 3) {
                g2d.setColor(PetalPalette.PETAL8_HC);
            }
            if (j == c_p + 4) {
                g2d.setColor(PetalPalette.PETAL1_HC);
            }
            if (j == c_p + 5) {
                g2d.setColor(PetalPalette.PETAL2_HC);
            }
            if (j == c_p + 6) {
                g2d.setColor(PetalPalette.PETAL3_HC);
            }
            if (j == c_p + 7 || j == c_p + 8 || j == c_p + 9 || j == c_p + 10) {
                g2d.setColor(Color.WHITE);
            }

            g2d.fill(ts);

        }

        for (int j = 0; j < glyphVector2.getNumGlyphs(); j++) {

            Point2D p = glyphVector2.getGlyphPosition(j);
            float px = (float) p.getX();
            float py = (float) p.getY();
            Point2D pointGlyph;

            pointGlyph = geometry2.pointAtLength(startLength2
                    + GlyphUtil.getGlyphWidthAtToken(glyphVector2, j));

            if (pointGlyph == null) {
                continue;
            }
            Shape glyph = glyphVector2.getGlyphOutline(j);

            float angle = geometry2.angleAtLength(startLength2
                    + GlyphUtil.getGlyphWidthAtToken(glyphVector2, j));
            af2.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
            af2.rotate(angle);
            af2.translate(-px, -py + glyphVector2.getVisualBounds().getHeight()
                    / 2 - 10);

            Shape ts = af2.createTransformedShape(glyph);

            g2d.setColor(PetalPalette.PETAL8_HC);

            g2d.fill(ts);

        }

    }

}
