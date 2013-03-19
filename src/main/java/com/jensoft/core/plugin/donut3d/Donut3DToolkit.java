/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import com.jensoft.core.drawable.text.TextPath.PathSide;
import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.plugin.Toolkit;
import com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel;
import com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel.Style;
import com.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel;
import com.jensoft.core.plugin.donut3d.painter.label.Donut3DPathLabel;
import com.jensoft.core.plugin.donut3d.painter.label.Donut3DRadialLabel;
import com.jensoft.core.plugin.donut3d.painter.paint.Donut3DDefaultPaint;

/**
 * <code>Donut3DToolkit</code>
 * <p>
 * Toolkit helper to create Donut 3D related objects
 * </p>
 * <br>
 * <img src="doc-files/Donut3D_1.png">
 * <img src="doc-files/Donut3D_2.png"> <br>
 * 
 * @see Donut3D
 * @see Donut3DSlice
 * @see Donut3DPlugin
 * @see Donut3DRadialLabel
 * @see Donut3DBorderLabel
 * @see Donut3DListener
 * @author Sebastien Janaud
 */
public class Donut3DToolkit extends Toolkit {

   

    /**
     * create empty donut 3d with the specified parameters
     * 
     * @param name
     *            the name
     * @param innerRadius
     *            the inner radius
     * @param outerRadius
     *            the outer radius
     * @param thickness
     *            the thickness
     * @return the empty donut 3D
     */
    public static Donut3D createDonut3D(String name, double innerRadius,
            double outerRadius, double thickness) {
        Donut3D donut3d = new Donut3D();
        donut3d.setName(name);
        donut3d.setCenterX(0);
        donut3d.setCenterY(0);

        donut3d.setInnerRadius(innerRadius);
        donut3d.setOuterRadius(outerRadius);
        donut3d.setThickness(thickness);

        donut3d.setStartAngleDegree(0);
        donut3d.setTilt(40);
        donut3d.setDonut3DPaint(new Donut3DDefaultPaint(90));

        return donut3d;
    }

    /**
     * create empty donut 3d with the specified parameters
     * 
     * @param name
     *            the name
     * @param innerRadius
     *            the inner radius
     * @param outerRadius
     *            the outer radius
     * @param thickness
     *            the thickness
     * @param startAngleDegree
     *            the start angle degree for the first slice
     * @return the empty donut 3D
     */
    public static Donut3D createDonut3D(String name, double innerRadius,
            double outerRadius, double thickness, double startAngleDegree) {
        Donut3D donut3d = new Donut3D();
        donut3d.setName(name);
        donut3d.setCenterX(0);
        donut3d.setCenterY(0);

        donut3d.setInnerRadius(innerRadius);
        donut3d.setOuterRadius(outerRadius);
        donut3d.setThickness(thickness);
        donut3d.setStartAngleDegree(startAngleDegree);

        donut3d.setTilt(40);
        donut3d.setDonut3DPaint(new Donut3DDefaultPaint(90));

        return donut3d;
    }

    /**
     * create empty donut 3d with the specified parameters
     * 
     * @param name
     *            the name
     * @param innerRadius
     *            the inner radius
     * @param outerRadius
     *            the outer radius
     * @param thickness
     *            the thickness
     * @param startAngleDegree
     *            the start angle degree for the first slice
     * @param tilt
     *            the tilt angle
     * @return the empty donut 3D
     */
    public static Donut3D createDonut3D(String name, double innerRadius,
            double outerRadius, double thickness, double startAngleDegree,
            double tilt) {
        Donut3D donut3d = new Donut3D();
        donut3d.setName(name);
        donut3d.setCenterX(0);
        donut3d.setCenterY(0);

        donut3d.setInnerRadius(innerRadius);
        donut3d.setOuterRadius(outerRadius);
        donut3d.setThickness(thickness);
        donut3d.setStartAngleDegree(startAngleDegree);
        donut3d.setTilt(tilt);

        donut3d.setDonut3DPaint(new Donut3DDefaultPaint(90));

        return donut3d;
    }

    /**
     * create the donut 3D slice with the specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice theme color
     * @param value
     *            the slice value
     * @return the donut 3d slice
     */
    public static Donut3DSlice createDonut3DSlice(String name, Color color,
            double value) {
        Donut3DSlice slice = new Donut3DSlice(name, color);
        slice.setValue(value);
        slice.setDivergence(0);
        return slice;
    }

    /**
     * create the donut 3D slice with the specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice theme color
     * @param value
     *            the slice value
     * @param sliceLabel
     *            the slice label
     * @return the donut 3d slice
     */
    public static Donut3DSlice createDonut3DSlice(String name, Color color,
            double value, AbstractDonut3DSliceLabel sliceLabel) {
        Donut3DSlice slice = new Donut3DSlice(name, color);
        slice.setValue(value);
        slice.addSliceLabel(sliceLabel);
        slice.setDivergence(0);
        return slice;
    }

    /**
     * create the donut 3D slice with the specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice theme color
     * @param value
     *            the slice value
     * @param divergence
     *            the slice divergence
     * @return the donut 3d slice
     */
    public static Donut3DSlice createDonut3DSlice(String name, Color color,
            double value, double divergence) {
        Donut3DSlice slice = new Donut3DSlice(name, color);
        slice.setValue(value);
        slice.setDivergence(divergence);
        return slice;
    }

    /**
     * create the donut 3D slice with the specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice theme color
     * @param value
     *            the slice value
     * @param sliceLabel
     *            the slice label
     * @param divergence
     *            the slice divergence
     * @return the donut 3d slice
     */
    public static Donut3DSlice createDonut3DSlice(String name, Color color,
            double value, AbstractDonut3DSliceLabel sliceLabel, double divergence) {
        Donut3DSlice slice = new Donut3DSlice(name, color);
        slice.setValue(value);
        slice.addSliceLabel(sliceLabel);
        slice.setDivergence(divergence);
        return slice;
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @return radial label
     */
    public static Donut3DRadialLabel createRadialLabel(String label) {
        return new Donut3DRadialLabel(label);
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @param offsetRadius
     * @return radial label
     */
    public static Donut3DRadialLabel createRadialLabel(String label,
            int offsetRadius) {
        Donut3DRadialLabel radialLabel = new Donut3DRadialLabel(label);
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
    public static Donut3DRadialLabel createRadialLabel(String label,
            Color labelColor) {
        return new Donut3DRadialLabel(label, labelColor);
    }

    /**
     * create radial slice label
     * 
     * @param label
     * @param labelColor
     * @param offsetRadius
     * @return radial label
     */
    public static Donut3DRadialLabel createRadialLabel(String label,
            Color labelColor, int offsetRadius) {
        Donut3DRadialLabel radialLabel = new Donut3DRadialLabel(label,
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
    public static Donut3DRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont) {
        return new Donut3DRadialLabel(label, labelColor, labelFont);
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
    public static Donut3DRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont, int offsetRadius) {
        Donut3DRadialLabel radialLabel = new Donut3DRadialLabel(label,
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
    public static Donut3DRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont, int offsetRadius, Style style) {
        Donut3DRadialLabel radialLabel = new Donut3DRadialLabel(label,
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
    public static Donut3DRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont, int offsetRadius,
            int outlineRound, Style style) {
        Donut3DRadialLabel radialLabel = new Donut3DRadialLabel(label,
                                                                labelColor, labelFont);
        radialLabel.setOffsetRadius(offsetRadius);
        radialLabel.setOutlineRound(outlineRound);
        radialLabel.setStyle(style);
        return radialLabel;
    }

    /**
     * create slice path label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @return the donut3D slice path label
     */
    public static Donut3DPathLabel createPathLabel(String label, Color labelColor) {
        Donut3DPathLabel Donut3DPathLabel = new Donut3DPathLabel(label, labelColor);
        return Donut3DPathLabel;
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
     * @return the donut3D slice path label
     */
    public static Donut3DPathLabel createPathLabel(String label, Color labelColor,
            int divergence) {
        Donut3DPathLabel Donut3DPathLabel = new Donut3DPathLabel(label, labelColor);
        Donut3DPathLabel.setDivergence(divergence);
        return Donut3DPathLabel;
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
     * @return the donut3D slice path label
     */
    public static Donut3DPathLabel createPathLabel(String label, Color labelColor,
            Font labelFont) {
        Donut3DPathLabel Donut3DPathLabel = new Donut3DPathLabel(label, labelColor,
                                                                 labelFont);
        return Donut3DPathLabel;
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
     * @return the donut3D slice path label
     */
    public static Donut3DPathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, int divergence) {
        Donut3DPathLabel Donut3DPathLabel = new Donut3DPathLabel(label, labelColor,
                                                                 labelFont);
        Donut3DPathLabel.setDivergence(divergence);
        return Donut3DPathLabel;
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
     * @return the donut3D slice path label
     */
    public static Donut3DPathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, TextPosition textPosition) {
        Donut3DPathLabel Donut3DPathLabel = new Donut3DPathLabel(label, labelColor,
                                                                 labelFont);
        Donut3DPathLabel.setTextPosition(textPosition);
        return Donut3DPathLabel;
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
     * @return the donut3D slice path label
     */
    public static Donut3DPathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, TextPosition textPosition, PathSide pathSide) {
        Donut3DPathLabel Donut3DPathLabel = new Donut3DPathLabel(label, labelColor,
                                                                 labelFont);
        Donut3DPathLabel.setTextPosition(textPosition);
        Donut3DPathLabel.setPathSide(pathSide);
        return Donut3DPathLabel;
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
     * @return the donut3D slice path label
     */
    public static Donut3DPathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, TextPosition textPosition, int divergence) {
        Donut3DPathLabel Donut3DPathLabel = new Donut3DPathLabel(label, labelColor,
                                                                 labelFont);
        Donut3DPathLabel.setTextPosition(textPosition);
        Donut3DPathLabel.setDivergence(divergence);
        return Donut3DPathLabel;
    }

    /**
     * create border slice label
     * 
     * @param label
     * @return border label
     */
    public static Donut3DBorderLabel createBorderLabel(String label) {
        return new Donut3DBorderLabel(label);
    }

    /**
     * create border slice label
     * 
     * @param label
     * @param labelColor
     * @return border label
     */
    public static Donut3DBorderLabel createBorderLabel(String label,
            Color labelColor) {
        return new Donut3DBorderLabel(label, labelColor);
    }

    /**
     * create border slice label
     * 
     * @param label
     * @param labelColor
     * @param labelFont
     * @return border label
     */
    public static Donut3DBorderLabel createBorderLabel(String label,
            Color labelColor, Font labelFont) {
        return new Donut3DBorderLabel(label, labelColor, labelFont);
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
    public static Donut3DBorderLabel createBorderLabel(String label,
            Color labelColor, Font labelFont, int outlineRound) {
        Donut3DBorderLabel l = new Donut3DBorderLabel(label, labelColor,
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
    public static Donut3DBorderLabel createBorderLabel(String label,
            Color labelColor, Font labelFont, int outlineRound, int margin) {
        Donut3DBorderLabel l = new Donut3DBorderLabel(label, labelColor,
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
    public static Donut3DBorderLabel createBorderLabel(String label,
            Color labelColor, Font labelFont, int outlineRound, Style style) {
        Donut3DBorderLabel l = new Donut3DBorderLabel(label, labelColor,
                                                      labelFont);
        l.setOutlineRound(outlineRound);
        l.setStyle(style);
        return l;
    }

    /**
     * push specified slices into specified donut
     * 
     * @param donut3D
     *            the host donut
     * @param slices
     *            the slices to push
     */
    public static void pushSlices(Donut3D donut3D, Donut3DSlice... slices) {
        for (int i = 0; i < slices.length; i++) {
            donut3D.addSlice(slices[i]);
        }
    }

    /**
     * push specified sections into specified donut
     * 
     * @param donut3D
     *            the host donut
     * @param slices
     *            the slices to push
     */
    public static void pushSlices(Donut3D donut3D,
            List<Donut3DSlice> slices) {
        for (Donut3DSlice slice : slices) {
            donut3D.addSlice(slice);
        }
    }

}
