/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.title.painter.fil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.GlyphGeometry;
import com.jensoft.core.plugin.legend.title.TitleLegend;
import com.jensoft.core.plugin.legend.title.painter.AbstractTitleLegendFill;

/**
 * <code>TitleLegendGradientFill</code> fill legend with gradient given colors
 * 
 * @author Sebastien Janaud
 * @since 1.0
 */
public class TitleLegendGradientFill extends AbstractTitleLegendFill {

    private Color startColor;
    private Color endColor;

    /**
     * create default fill 1 legend
     */
    public TitleLegendGradientFill() {
    }

    /**
     * create fill 1 with specified shading color
     * 
     * @param startColor
     *            start color to set
     * @param endColor
     *            end color to set
     */
    public TitleLegendGradientFill(Color startColor, Color endColor) {
        super();
        this.startColor = startColor;
        this.endColor = endColor;
    }

    /**
     * @return the startColor
     */
    public Color getStartColor() {
        return startColor;
    }

    /**
     * @param startColor
     *            the startColor to set
     */
    public void setStartColor(Color startColor) {
        this.startColor = startColor;
    }

    /**
     * @return the endColor
     */
    public Color getEndColor() {
        return endColor;
    }

    /**
     * @param endColor
     *            the endColor to set
     */
    public void setEndColor(Color endColor) {
        this.endColor = endColor;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.legend.painter.LegendFill#paintLegendFill(java.awt.Graphics2D, com.jensoft.core.plugin.legend.Legend)
     */
    @Override
    protected void paintLegendFill(Graphics2D g2d, TitleLegend legend) {

        if (legend.getFont() != null) {
            g2d.setFont(legend.getFont());
        }

        g2d.setColor(legend.getThemeColor());
        List<GlyphGeometry> legendGlyphs = legend.getLegendGlyphs();
        for (GlyphGeometry legendGlyph : legendGlyphs) {

            Point2D start = new Point2D.Double(legendGlyph.getNorthTransform()
                    .getX(), legendGlyph.getNorthTransform().getY());
            Point2D end = new Point2D.Double(legendGlyph.getSouthTransform()
                    .getX(), legendGlyph.getSouthTransform().getY());

            if (!start.equals(end)) {

                if (startColor != null && endColor != null) {
                    float[] dist = { 0.0f, 1.0f };
                    Color[] colors = { startColor, endColor };
                    LinearGradientPaint pg = new LinearGradientPaint(start,
                                                                     end, dist, colors);
                    g2d.setPaint(pg);
                    g2d.fill(legendGlyph.getGlyphShape());
                }
                else {
                    if (legend.getThemeColor() != null) {
                        float[] dist = { 0.0f, 1.0f };
                        Color[] colors = { legend.getThemeColor().brighter(),
                                legend.getThemeColor().darker() };
                        LinearGradientPaint pg = new LinearGradientPaint(start,
                                                                         end, dist, colors);
                        g2d.setPaint(pg);
                        g2d.fill(legendGlyph.getGlyphShape());
                    }
                    else {
                        float[] dist = { 0.0f, 1.0f };
                        Color[] colors = {
                                legend.getHost().getWindow2D().getThemeColor()
                                        .brighter(),
                                legend.getHost().getWindow2D().getThemeColor()
                                        .darker() };
                        LinearGradientPaint pg = new LinearGradientPaint(start,
                                                                         end, dist, colors);
                        g2d.setPaint(pg);
                        g2d.fill(legendGlyph.getGlyphShape());
                    }
                }

            }
        }

    }

}
