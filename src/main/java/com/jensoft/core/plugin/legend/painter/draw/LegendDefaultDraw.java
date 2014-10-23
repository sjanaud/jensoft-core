/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.painter.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.GlyphGeometry;
import com.jensoft.core.plugin.legend.Legend;
import com.jensoft.core.plugin.legend.LegendConstraints;
import com.jensoft.core.plugin.legend.LegendConstraints.LegendPosition;
import com.jensoft.core.plugin.legend.painter.LegendDraw;

/**
 * <code>LegendDefaultDraw</code>
 * 
 * @author Sebastien Janaud
 */
public class LegendDefaultDraw extends LegendDraw {

    private Color color1 = Color.BLACK;
    private Color color2 = Color.BLACK;

    public LegendDefaultDraw() {
    }

    public LegendDefaultDraw(Color color1, Color color2) {
        super();
        this.color1 = color1;
        this.color2 = color2;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.legend.painter.LegendDraw#paintLegendDraw(java.awt.Graphics2D, com.jensoft.core.plugin.legend.Legend)
     */
    @Override
    protected void paintLegendDraw(Graphics2D g2d, Legend legend) {

        LegendConstraints contraints = legend.getConstraints();

        g2d.setFont(legend.getFont());
        if (contraints.getPosition() == LegendPosition.West
                || contraints.getPosition() == LegendPosition.East) {

            g2d.setColor(legend.getThemeColor());
            List<GlyphGeometry> legendGlyphs = legend.getLegendGlyphs();
            for (GlyphGeometry legendGlyph : legendGlyphs) {

                Point2D start = new Point2D.Double(legendGlyph
                        .getNorthTransform().getX(), legendGlyph
                        .getNorthTransform().getY());
                Point2D end = new Point2D.Double(legendGlyph
                        .getSouthTransform().getX(), legendGlyph
                        .getSouthTransform().getY());

                if (!start.equals(end)) {

                    float[] dist = { 0.0f, 1.0f };

                    Color[] colors = { color1, color2 };

                    LinearGradientPaint pg = new LinearGradientPaint(start,
                                                                     end, dist, colors);
                    g2d.setPaint(pg);
                    g2d.draw(legendGlyph.getGlyphShape());
                }
            }

        }

        if (contraints.getPosition() == LegendPosition.South
                || contraints.getPosition() == LegendPosition.North) {

            g2d.setColor(legend.getThemeColor());
            List<GlyphGeometry> legendGlyphs = legend.getLegendGlyphs();
            for (GlyphGeometry legendGlyph : legendGlyphs) {

                Point2D start = new Point2D.Double(legendGlyph
                        .getNorthTransform().getX(), legendGlyph
                        .getNorthTransform().getY());
                Point2D end = new Point2D.Double(legendGlyph
                        .getSouthTransform().getX(), legendGlyph
                        .getSouthTransform().getY());

                if (!start.equals(end)) {
                    float[] dist = { 0.0f, 1.0f };

                    Color[] colors = { color1, color2 };

                    LinearGradientPaint pg = new LinearGradientPaint(start,
                                                                     end, dist, colors);
                    g2d.setPaint(pg);
                    g2d.draw(legendGlyph.getGlyphShape());
                }
            }

        }

    }

}
