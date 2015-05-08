/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.legend.title.painter.fil;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import org.jensoft.core.glyphmetrics.GlyphGeometry;
import org.jensoft.core.plugin.legend.title.TitleLegend;
import org.jensoft.core.plugin.legend.title.painter.AbstractTitleLegendFill;

/**
 * <code>TitleLegendDefaultFill</code> would be fill legend with {@link #fillColor}, but if this
 * property is not set , this fill would be fill legend with legend theme color.
 * if legend theme color is null, window theme color will be used.
 * 
 * @author Sebastien Janaud
 */
public class TitleLegendDefaultFill extends AbstractTitleLegendFill {

    /** default fill color */
    private Color fillColor;

    /**
     * create default fill using window theme color as legend fill color
     */
    public TitleLegendDefaultFill() {
    }

    /**
     * @param fillColor
     */
    public TitleLegendDefaultFill(Color fillColor) {
        super();
        this.fillColor = fillColor;
    }

   
    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.legend.title.painter.AbstractTitleLegendFill#paintLegendFill(java.awt.Graphics2D, org.jensoft.core.plugin.legend.title.TitleLegend)
     */
    @Override
    protected void paintLegendFill(Graphics2D g2d, TitleLegend legend) {

        g2d.setFont(legend.getFont());
        if (fillColor != null) {
            g2d.setColor(fillColor);
        }
        else {
            if (legend.getThemeColor() != null) {
                g2d.setColor(legend.getThemeColor());
            }
            else {
                g2d.setColor(legend.getHost().getProjection().getThemeColor());
            }
        }
        List<GlyphGeometry> legendGlyphs = legend.getLegendGlyphs();
        for (GlyphGeometry legendGlyph : legendGlyphs) {

            g2d.fill(legendGlyph.getGlyphShape());
        }

    }

}
