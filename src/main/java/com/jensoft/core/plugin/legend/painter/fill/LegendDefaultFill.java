/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.GlyphGeometry;
import com.jensoft.core.plugin.legend.Legend;
import com.jensoft.core.plugin.legend.painter.LegendFill;

/**
 * LegendDefaultFill would be fill legend with {@link #fillColor}, but if this
 * property is not set , this fill would be fill legend with legend theme color.
 * if legend theme color is null, window theme color will be used.
 * 
 * @author Sebastien Janaud
 */
public class LegendDefaultFill extends LegendFill {

    /** default fill color */
    private Color fillColor;

    /**
     * create default fill using window theme color as legend fill color
     */
    public LegendDefaultFill() {
    }

    /**
     * @param fillColor
     */
    public LegendDefaultFill(Color fillColor) {
        super();
        this.fillColor = fillColor;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.legend.painter.LegendFill#paintLegendFill
     * (java.awt.Graphics2D, com.jensoft.sw2d.core.plugin.legend.Legend)
     */
    @Override
    protected void paintLegendFill(Graphics2D g2d, Legend legend) {

        g2d.setFont(legend.getFont());
        if (fillColor != null) {
            g2d.setColor(fillColor);
        }
        else {
            if (legend.getThemeColor() != null) {
                g2d.setColor(legend.getThemeColor());
            }
            else {
                g2d.setColor(legend.getHost().getWindow2D().getThemeColor());
            }
        }
        List<GlyphGeometry> legendGlyphs = legend.getLegendGlyphs();
        for (GlyphGeometry legendGlyph : legendGlyphs) {

            g2d.fill(legendGlyph.getGlyphShape());
        }

    }

}
