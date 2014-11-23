/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import com.jensoft.core.drawable.text.TextPath.PathSide;
import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.plugin.Toolkit;
import com.jensoft.core.plugin.donut2d.Donut2D.Donut2DNature;
import com.jensoft.core.plugin.donut2d.painter.fill.Donut2DDefaultFill;
import com.jensoft.core.plugin.donut2d.painter.label.AbstractDonut2DSliceLabel.Style;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DBorderLabel;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DPathLabel;
import com.jensoft.core.plugin.donut2d.painter.label.Donut2DRadialLabel;

/**
 * <code>Donut2DToolkit</code>
 * <p>
 * Toolkit helper to create Donut2D related objects
 * </p>
 * <br>
 * <img src="doc-files/donut2d.png"> <br>
 * 
 * @since 1.0
 * @author Sebastien Janaud
 */
public class Donut2DToolkit extends Toolkit {

    
    /**
     * create a new Donut2D in User projection with specified parameter
     * 
     * @param centerX
     *            the center X in user  projection
     * @param centerY
     *            the center Y in user  projection
     * @param innerRadius
     * @param outerRadius
     * @return donut2D
     */
    public static Donut2D createDonut2D(String name, double centerX, double centerY,
            double innerRadius, double outerRadius) {
        Donut2D donut2D = new Donut2D();
        donut2D.setName(name);
        donut2D.setNature(Donut2DNature.User);
        donut2D.setCenterX(centerX);
        donut2D.setCenterY(centerY);
        donut2D.setInnerRadius(innerRadius);
        donut2D.setOuterRadius(outerRadius);
        donut2D.setExplose(0);
        donut2D.setDonut2DFill(new Donut2DDefaultFill());
        return donut2D;
    }

   /**
    *  create a new Donut2D in User projection with specified parameter
    * @param name
    * @param innerRadius
    * @param outerRadius
    * @return donut2D 
    */
    public static Donut2D createDonut2D(String name, double innerRadius, double outerRadius) {
        Donut2D donut2D = new Donut2D();
        donut2D.setName(name);
        donut2D.setNature(Donut2DNature.User);
        donut2D.setCenterX(0);
        donut2D.setCenterY(0);
        donut2D.setInnerRadius(innerRadius);
        donut2D.setOuterRadius(outerRadius);
        donut2D.setExplose(0);
        donut2D.setDonut2DFill(new Donut2DDefaultFill());
        return donut2D;
    }

    /**
     * create the donut 2D slice with the specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice theme color
     * @param value
     *            the slice value
     * @param divergence
     *            the slice divergence
     * @return the donut 2d slice
     */
    public static Donut2DSlice createDonut2DSlice(String name, Color color,
            double value, double divergence) {
        Donut2DSlice slice = new Donut2DSlice(name, color);
        slice.setValue(value);
        slice.setDivergence(divergence);
        return slice;
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @return radial label
     */
    public static Donut2DRadialLabel createRadialLabel(String label) {
        return new Donut2DRadialLabel(label);
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @param offsetRadius
     * @return radial label
     */
    public static Donut2DRadialLabel createRadialLabel(String label,
            int offsetRadius) {
        Donut2DRadialLabel radialLabel = new Donut2DRadialLabel(label);
        radialLabel.setOffsetRadius(offsetRadius);
        return radialLabel;
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @param labelColor
     * @return radial label
     */
    public static Donut2DRadialLabel createRadialLabel(String label,
            Color labelColor) {
        return new Donut2DRadialLabel(label, labelColor);
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @param labelColor
     * @param offsetRadius
     * @return radial label
     */
    public static Donut2DRadialLabel createRadialLabel(String label,
            Color labelColor, int offsetRadius) {
        Donut2DRadialLabel radialLabel = new Donut2DRadialLabel(label,
                                                                labelColor);
        radialLabel.setOffsetRadius(offsetRadius);
        return radialLabel;
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @param labelColor
     * @param labelFont
     * @return radial label
     */
    public static Donut2DRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont) {
        return new Donut2DRadialLabel(label, labelColor, labelFont);
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @param labelColor
     * @param labelFont
     * @param offsetRadius
     * @return radial label
     */
    public static Donut2DRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont, int offsetRadius) {
        Donut2DRadialLabel radialLabel = new Donut2DRadialLabel(label,
                                                                labelColor, labelFont);
        radialLabel.setOffsetRadius(offsetRadius);
        return radialLabel;
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @param labelColor
     * @param labelFont
     * @param offsetRadius
     * @param style
     * @return radial label
     */
    public static Donut2DRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont, int offsetRadius, Style style) {
        Donut2DRadialLabel radialLabel = new Donut2DRadialLabel(label,
                                                                labelColor, labelFont);
        radialLabel.setOffsetRadius(offsetRadius);
        radialLabel.setStyle(style);
        return radialLabel;
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @param labelColor
     * @param labelFont
     * @param offsetRadius
     * @param outlineRound
     * @param style
     * @return radial label
     */
    public static Donut2DRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont, int offsetRadius,
            int outlineRound, Style style) {
        Donut2DRadialLabel radialLabel = new Donut2DRadialLabel(label,
                                                                labelColor, labelFont);
        radialLabel.setOffsetRadius(offsetRadius);
        radialLabel.setOutlineRound(outlineRound);
        radialLabel.setStyle(style);
        return radialLabel;
    }

    /**
     * create border slice label
     * 
     * @param label
     * @return border label
     */
    public static Donut2DBorderLabel createBorderLabel(String label) {
        return new Donut2DBorderLabel(label);
    }

    /**
     * create border slice label
     * 
     * @param label
     * @param labelColor
     * @return border label
     */
    public static Donut2DBorderLabel createBorderLabel(String label,
            Color labelColor) {
        return new Donut2DBorderLabel(label, labelColor);
    }

    /**
     * create border slice label
     * 
     * @param label
     * @param labelColor
     * @param labelFont
     * @return border label
     */
    public static Donut2DBorderLabel createBorderLabel(String label,
            Color labelColor, Font labelFont) {
        return new Donut2DBorderLabel(label, labelColor, labelFont);
    }

    /**
     * create border slice label
     * 
     * @param label
     * @param labelColor
     * @param labelFont
     * @param outlineRound
     * @return border label
     */
    public static Donut2DBorderLabel createBorderLabel(String label,
            Color labelColor, Font labelFont, int outlineRound) {
        Donut2DBorderLabel l = new Donut2DBorderLabel(label, labelColor,
                                                      labelFont);
        l.setOutlineRound(outlineRound);
        return l;
    }

    /**
     * create border slice label
     * 
     * @param label
     * @param labelColor
     * @param labelFont
     * @param outlineRound
     * @param margin
     * @return border label
     */
    public static Donut2DBorderLabel createBorderLabel(String label,
            Color labelColor, Font labelFont, int outlineRound, int margin) {
        Donut2DBorderLabel l = new Donut2DBorderLabel(label, labelColor,
                                                      labelFont);
        l.setOutlineRound(outlineRound);
        l.setMargin(margin);
        return l;
    }

    /**
     * create border slice label
     * 
     * @param label
     * @param labelColor
     * @param labelFont
     * @param outlineRound
     * @param style
     * @return border label
     */
    public static Donut2DBorderLabel createBorderLabel(String label,
            Color labelColor, Font labelFont, int outlineRound, Style style) {
        Donut2DBorderLabel l = new Donut2DBorderLabel(label, labelColor,
                                                      labelFont);
        l.setOutlineRound(outlineRound);
        l.setStyle(style);
        return l;
    }

    /**
     * create slice path label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @return the donut2D slice path label
     */
    public static Donut2DPathLabel createPathLabel(String label, Color labelColor) {
        Donut2DPathLabel Donut2DPathLabel = new Donut2DPathLabel(label, labelColor);
        return Donut2DPathLabel;
    }

    /**
     * create slice path label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param divergence
     *            the interval divergence between path and text label
     * @return the donut2D slice path label
     */
    public static Donut2DPathLabel createPathLabel(String label, Color labelColor,
            int divergence) {
        Donut2DPathLabel Donut2DPathLabel = new Donut2DPathLabel(label, labelColor);
        Donut2DPathLabel.setDivergence(divergence);
        return Donut2DPathLabel;
    }

    /**
     * create slice path label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param labelFont
     *            the label text font
     * @return the donut2D slice path label
     */
    public static Donut2DPathLabel createPathLabel(String label, Color labelColor,
            Font labelFont) {
        Donut2DPathLabel Donut2DPathLabel = new Donut2DPathLabel(label, labelColor,
                                                                 labelFont);
        return Donut2DPathLabel;
    }

    /**
     * create slice path label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param labelFont
     *            the label text font
     * @param divergence
     *            the interval divergence between path and text label
     * @return the donut2D slice path label
     */
    public static Donut2DPathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, int divergence) {
        Donut2DPathLabel Donut2DPathLabel = new Donut2DPathLabel(label, labelColor,
                                                                 labelFont);
        Donut2DPathLabel.setDivergence(divergence);
        return Donut2DPathLabel;
    }

    /**
     * create slice path label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param labelFont
     *            the label text font
     * @param textPosition
     *            the text position on path
     * @return the donut2D slice path label
     */
    public static Donut2DPathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, TextPosition textPosition) {
        Donut2DPathLabel Donut2DPathLabel = new Donut2DPathLabel(label, labelColor,
                                                                 labelFont);
        Donut2DPathLabel.setTextPosition(textPosition);
        return Donut2DPathLabel;
    }

    /**
     * create slice path label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param labelFont
     *            the label text font
     * @param textPosition
     *            the text position on path
     * @param pathSide
     *            the label path side
     * @return the donut2D slice path label
     */
    public static Donut2DPathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, TextPosition textPosition, PathSide pathSide) {
        Donut2DPathLabel Donut2DPathLabel = new Donut2DPathLabel(label, labelColor,
                                                                 labelFont);
        Donut2DPathLabel.setTextPosition(textPosition);
        Donut2DPathLabel.setPathSide(pathSide);
        return Donut2DPathLabel;
    }

    /**
     * create slice path label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param labelFont
     *            the label text font
     * @param textPosition
     *            the text position on path
     * @param divergence
     *            the interval divergence between path and text label
     * @return the donut2D slice path label
     */
    public static Donut2DPathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, TextPosition textPosition, int divergence) {
        Donut2DPathLabel Donut2DPathLabel = new Donut2DPathLabel(label, labelColor,
                                                                 labelFont);
        Donut2DPathLabel.setTextPosition(textPosition);
        Donut2DPathLabel.setDivergence(divergence);
        return Donut2DPathLabel;
    }

    /**
     * push specified slices into specified donut
     * 
     * @param donut2D
     *            the host donut
     * @param slices
     *            the slices to push
     */
    public static void pushSlices(Donut2D donut2D, Donut2DSlice... slices) {
        for (int i = 0; i < slices.length; i++) {
            donut2D.addSlice(slices[i]);
        }
    }

    /**
     * push specified sections into specified donut
     * 
     * @param donut2D
     *            the host donut
     * @param slices
     *            the slices to push
     */
    public static void pushSlices(Donut2D donut2D,
            List<Donut2DSlice> slices) {
        for (Donut2DSlice slice : slices) {
            donut2D.addSlice(slice);
        }
    }

}
