/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.legend.title.painter.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.util.List;

import org.jensoft.core.glyphmetrics.GlyphGeometry;
import org.jensoft.core.plugin.legend.title.TitleLegend;
import org.jensoft.core.plugin.legend.title.TitleLegendConstraints;
import org.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendPosition;
import org.jensoft.core.plugin.legend.title.painter.AbstractTitleLegendDraw;

/**
 * <code>TitleLegendDefaultDraw</code>
 * 
 * @author Sebastien Janaud
 */
public class TitleLegendDefaultDraw extends AbstractTitleLegendDraw {

    private Color color1 = Color.BLACK;
    private Color color2 = Color.BLACK;

    /**
     * create default draw legend painter with black legend draw
     */
    public TitleLegendDefaultDraw() {
    }
    
    /**
     * create draw legend with given color
     * @param color
     */
    public TitleLegendDefaultDraw(Color color) {
    	this.color1 = color;
        this.color2 = color;
    }

    /**
     * create draw legend with given gradient colors
     * @param color1
     * @param color2
     */
    public TitleLegendDefaultDraw(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
    }

    /**
     * get color 1
     * @return color1
     */
    public Color getColor1() {
        return color1;
    }

    /**
     * set color 1
     * @param color1
     */
    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    /**
     * get color 2
     * @return color2
     */
    public Color getColor2() {
        return color2;
    }

    /**
     * set color 2
     * @param color2
     */
    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    
    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.legend.title.painter.AbstractTitleLegendDraw#paintLegendDraw(java.awt.Graphics2D, org.jensoft.core.plugin.legend.title.TitleLegend)
     */
    @Override
    protected void paintLegendDraw(Graphics2D g2d, TitleLegend legend) {

        TitleLegendConstraints contraints = legend.getConstraints();

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
