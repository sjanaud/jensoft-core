/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.title;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.glyphmetrics.GlyphGeometry;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendAlignment;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendPosition;
import com.jensoft.core.plugin.legend.title.painter.LegendDraw;
import com.jensoft.core.plugin.legend.title.painter.LegendFill;
import com.jensoft.core.plugin.legend.title.painter.LegendPainter;
import com.jensoft.core.plugin.legend.title.painter.fil.LegendGradientFill;

/**
 * Legend defines a simple text legend on the view
 * 
 * @see TitleLegendConstraints
 * @see TitleLegendPlugin
 * @see TitleLegendPlugin
 * @see LegendPainter
 * @see LegendFill
 * @see LegendDraw
 * @author Sebastien Janaud
 */
public class TitleLegend {

    /** legend label */
    private String text;

    /** legend font */
    private Font font;

    /** legend theme color */
    private Color themeColor;

    /** legend constraints */
    private TitleLegendConstraints constraints = new TitleLegendConstraints(LegendPosition.South, 0.8f, LegendAlignment.Rigth);                                                                  

    /** legend host plugin */
    private TitleLegendPlugin host;

    /** legend fill */
    private LegendFill legendFill = new LegendGradientFill();

    /** legend draw */
    private LegendDraw legendDraw;

    /** legend glyphs */
    private List<GlyphGeometry> legendGlyphs;

    
    /**
     * create legend with specified label
     * 
     * @param text
     */
    public TitleLegend(String text) {
        this.text = text;
        this.legendGlyphs = new ArrayList<GlyphGeometry>();
    }

    /**
     * return legend text
     * 
     * @return legend text
     */
    public String getText() {
        return text;
    }

    /**
     * set legend text
     * 
     * @param text
     *            the text label to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * get legend constraints
     * 
     * @return legend constraints
     */
    public TitleLegendConstraints getConstraints() {
        return constraints;
    }

    /**
     * set legend constraints
     * 
     * @param constraints
     */
    public void setConstraints(TitleLegendConstraints constraints) {
        this.constraints = constraints;
    }

    /**
     * get legend font
     * 
     * @return legend font
     */
    public Font getFont() {
        if (font == null) {
            return new Font("tahoma", Font.PLAIN, 12);
        }
        return font;
    }

    /**
     * set legend font
     * 
     * @param font
     *            the legend font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * get legend theme color
     * 
     * @return legend theme color
     */
    public Color getThemeColor() {
        return themeColor;
    }

    /**
     * set legend theme color
     * 
     * @param themeColor
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * get legend host plugin
     * 
     * @return host plugin
     */
    public TitleLegendPlugin getHost() {
        return host;
    }

    /**
     * set host plugin
     * 
     * @param host
     *            the host plugin to set
     */
    public void setHost(TitleLegendPlugin host) {
        this.host = host;
    }

    /**
     * get legend fill
     * 
     * @return legend fill
     */
    public LegendFill getLegendFill() {
        return legendFill;
    }

    /**
     * set legend fill
     * 
     * @param legendFill
     *            the legend fill to set
     */
    public void setLegendFill(LegendFill legendFill) {
        this.legendFill = legendFill;
    }

    /**
     * get legend draw
     * 
     * @return legend draw
     */
    public LegendDraw getLegendDraw() {
        return legendDraw;
    }

    /**
     * set legend draw
     * 
     * @param legendDraw
     *            the legend draw to set
     */
    public void setLegendDraw(LegendDraw legendDraw) {
        this.legendDraw = legendDraw;
    }

    /**
     * get legends glyphs
     * 
     * @return legend glyphs
     */
    public List<GlyphGeometry> getLegendGlyphs() {
        return legendGlyphs;
    }

    /**
     * set the legend glyphs
     * 
     * @param legendGlyphs
     *            the legend glyphs to set
     */
    public void setGlyphShapes(List<GlyphGeometry> legendGlyphs) {
        this.legendGlyphs = legendGlyphs;
    }

    /**
     * add legend glyph
     * 
     * @param legendGlyph
     *            the legend glyph to add
     */
    public void addLegendGlyph(GlyphGeometry legendGlyph) {
        legendGlyphs.add(legendGlyph);
    }

    /**
     * clear legend glyphs
     */
    public void clearLegendGlyph() {
        legendGlyphs.clear();
    }

}