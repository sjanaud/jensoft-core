/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.title;

import java.awt.Color;
import java.awt.Font;

import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.plugin.legend.title.LegendConstraints.LegendAlignment;
import com.jensoft.core.plugin.legend.title.LegendConstraints.LegendPosition;
import com.jensoft.core.plugin.legend.title.painter.fil.LegendGradientFill;

/**
 * legend toolkit help to create legend and related objects.<br>
 * 
 * @see Legend
 * @see LegendPlugin
 * @author Sebastien Janaud
 */
public class LegendToolkit {

    /**
     * create default black legend with default constraints, south on the right
     * 
     * @param label
     *            the label legend to draw
     * @return legend
     */
    public static Legend createLegend(String label) {
        Legend legend = new Legend(label);
        legend.setLegendFill(new LegendGradientFill());
        legend.setConstraints(new LegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create default black legend with default constraints, south on the right
     * 
     * @param label
     *            the legend label
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static Legend createLegend(String label,
            LegendConstraints constraints) {
        Legend legend = new Legend(label);
        legend.setLegendFill(new LegendGradientFill());
        legend.setConstraints(constraints);
        return legend;
    }

    /**
     * create default black legend with default constraints, south on the right
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @return legend
     */
    public static Legend createLegend(String label, Font font) {
        Legend legend = new Legend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill());
        legend.setConstraints(new LegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create default black legend with default constraints, south on the right
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static Legend createLegend(String label, Font font,
            LegendConstraints constraints) {
        Legend legend = new Legend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill());
        legend.setConstraints(constraints);
        return legend;
    }

    /**
     * create default black legend with default constraints, south on the right
     * 
     * @param label
     *            the legend label
     * @param color
     *            the legend color
     * @return legend
     */
    public static Legend createLegend(String label, Color color) {
        Legend legend = new Legend(label);
        legend.setLegendFill(new LegendGradientFill(color, color));
        legend.setConstraints(new LegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param color
     *            the legend color
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static Legend createLegend(String label, Color color,
            LegendConstraints constraints) {
        Legend legend = new Legend(label);
        legend.setLegendFill(new LegendGradientFill(color, color));
        legend.setConstraints(constraints);
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @param color
     *            the legend color
     * @return legend
     */
    public static Legend createLegend(String label, Font font, Color color) {
        Legend legend = new Legend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill(color, color));
        legend.setConstraints(new LegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @param color
     *            the legend color
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static Legend createLegend(String label, Font font, Color color,
            LegendConstraints constraints) {
        Legend legend = new Legend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill(color, color));
        legend.setConstraints(constraints);
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param color1
     *            the legend shading start color
     * @param color2
     *            the legend shading end color
     * @return legend
     */
    public static Legend createLegend(String label, Color color1, Color color2) {
        Legend legend = new Legend(label);
        legend.setLegendFill(new LegendGradientFill(color1, color2));
        legend.setFont(InputFonts.getNeuropol(12));
        legend.setConstraints(new LegendConstraints(LegendPosition.North, 0.1f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param color1
     *            the legend shading start color
     * @param color2
     *            the legend shading end color
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static Legend createLegend(String label, Color color1, Color color2,
            LegendConstraints constraints) {
        Legend legend = new Legend(label);
        legend.setLegendFill(new LegendGradientFill(color1, color2));
        legend.setConstraints(constraints);
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @param color1
     *            the legend shading start color
     * @param color2
     *            the legend shading end color
     * @return legend
     */
    public static Legend createLegend(String label, Font font, Color color1,
            Color color2) {
        Legend legend = new Legend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill(color1, color2));
        legend.setConstraints(new LegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @param color1
     *            the legend shading start color
     * @param color2
     *            the legend shading end color
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static Legend createLegend(String label, Font font, Color color1,
            Color color2, LegendConstraints constraints) {
        Legend legend = new Legend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill(color1, color2));
        legend.setConstraints(constraints);
        return legend;
    }

}
