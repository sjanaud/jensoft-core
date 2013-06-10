/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import com.jensoft.core.drawable.text.TextPath.PathSide;
import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.plugin.pie.Pie.PieNature;
import com.jensoft.core.plugin.pie.painter.effect.AbstractPieEffect;
import com.jensoft.core.plugin.pie.painter.effect.AbstractPieSliceEffect;
import com.jensoft.core.plugin.pie.painter.fill.AbstractPieFill;
import com.jensoft.core.plugin.pie.painter.fill.AbstractPieSliceFill;
import com.jensoft.core.plugin.pie.painter.fill.PieDefaultFill;
import com.jensoft.core.plugin.pie.painter.label.PieBorderLabel;
import com.jensoft.core.plugin.pie.painter.label.PieBoundLabel;
import com.jensoft.core.plugin.pie.painter.label.PiePathLabel;
import com.jensoft.core.plugin.pie.painter.label.PieRadialLabel;

/**
 * <code>PieToolkit</code> provides some static method to create pie related objects.
 * 
 * @see Pie
 * @see PieSlice
 * @see PieRadialLabel
 * @see PieBorderLabel
 * @see PiePathLabel
 * @see PieBoundLabel
 * @author Sebastien Janaud
 */
public class PieToolkit {

   
    /**
     * create pie with specified parameters
     * 
     * @param name
     *            the pie name
     * @param radius
     *            the pie radius
     * @return the pie
     */
    public static Pie createPie(String name, double radius) {
        Pie pie = new Pie(name, radius);
        pie.setPieNature(PieNature.User);
        pie.setStartAngleDegree(0);
        pie.setCenterX(0);
        pie.setCenterY(0);
        pie.setPieFill(new PieDefaultFill());
        return pie;
    }

    /**
     * create pie with specified parameters
     * 
     * @param name
     *            the pie name
     * @param radius
     *            the pie radius
     * @param startAngleDegree
     *            the start angle degree
     * @return the pie
     */
    public static Pie createPie(String name, int radius, double startAngleDegree) {
        Pie pie = new Pie(name, radius);
        pie.setPieNature(PieNature.User);
        pie.setStartAngleDegree(startAngleDegree);
        pie.setCenterX(0);
        pie.setCenterY(0);
        pie.setPieFill(new PieDefaultFill());
        return pie;
    }

    /**
     * create pie with specified parameters
     * 
     * @param name
     *            the pie name
     * @param radius
     *            the pie radius
     * @param startAngleDegree
     *            the start angle degree
     * @return the pie
     */
    public static Pie createPie(String name, int radius,
            double startAngleDegree, AbstractPieEffect pieEffect) {
        Pie pie = new Pie(name, radius);
        pie.setPieNature(PieNature.User);
        pie.setStartAngleDegree(startAngleDegree);
        pie.setCenterX(0);
        pie.setCenterY(0);
        pie.setPieFill(new PieDefaultFill());
        pie.setPieEffect(pieEffect);
        return pie;
    }

    /**
     * create pie with specified parameters
     * 
     * @param name
     *            the pie name
     * @param radius
     *            the pie radius
     * @param startAngleDegree
     *            the start angle degree
     * @param pieFill
     *            the painter to fill pie
     * @return the pie
     */
    public static Pie createPie(String name, int radius,
            double startAngleDegree, AbstractPieFill pieFill) {
        Pie pie = new Pie(name, radius);
        pie.setPieNature(PieNature.User);
        pie.setStartAngleDegree(startAngleDegree);
        pie.setCenterX(0);
        pie.setCenterY(0);
        pie.setPieFill(pieFill);
        return pie;
    }

    /**
     * create pie with specified parameters
     * 
     * @param name
     *            the pie name
     * @param radius
     *            the pie radius
     * @param startAngleDegree
     *            the start angle degree
     * @param pieFill
     *            the painter to fill pie
     * @param pieEffect
     *            the painter to make pie effect
     * @return the pie
     */
    public static Pie createPie(String name, int radius,
            double startAngleDegree, AbstractPieFill pieFill, AbstractPieEffect pieEffect) {
        Pie pie = new Pie(name, radius);
        pie.setPieNature(PieNature.User);
        pie.setStartAngleDegree(startAngleDegree);
        pie.setCenterX(0);
        pie.setCenterY(0);
        pie.setPieFill(pieFill);
        pie.setPieEffect(pieEffect);
        return pie;
    }

    /**
     * create pie slice with specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice color
     * @param value
     *            the slice value
     * @return the pie slice
     */
    public static PieSlice createSlice(String name, Color color, double value) {
        PieSlice slice = new PieSlice(name, color);
        slice.setValue(value);
        return slice;
    }

    /**
     * create pie slice with specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice color
     * @param value
     *            the slice value
     * @param sliceFill
     *            the painter to fill slice
     * @return the pie slice
     */
    public static PieSlice createSlice(String name, Color color, double value,
            AbstractPieSliceFill sliceFill) {
        PieSlice pieslice = new PieSlice(name, color);
        pieslice.setValue(value);
        pieslice.setSliceFill(sliceFill);
        return pieslice;
    }

    /**
     * create pie slice with specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice color
     * @param value
     *            the slice value
     * @param sliceFill
     *            the painter to fill slice
     * @param sliceEffect
     *            the painter to make pie effect
     * @return the pie slice
     */
    public static PieSlice createSlice(String name, Color color, double value,
            AbstractPieSliceFill sliceFill, AbstractPieSliceEffect sliceEffect) {
        PieSlice pieslice = new PieSlice(name, color);
        pieslice.setValue(value);
        pieslice.setSliceFill(sliceFill);
        pieslice.setSliceEffect(sliceEffect);
        return pieslice;
    }

    /**
     * create pie slice with specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice color
     * @param value
     *            the slice value
     * @param divergence
     *            the slice divergence
     * @return the pie slice
     */
    public static PieSlice createSlice(String name, Color color, double value,
            int divergence) {
        PieSlice pieslice = new PieSlice(name, color);
        pieslice.setValue(value);
        pieslice.setDivergence(divergence);
        return pieslice;
    }

    /**
     * create pie slice with specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice color
     * @param value
     *            the slice value
     * @param divergence
     *            the slice divergence
     * @param sliceFill
     *            the painter to fill slice
     * @return the pie slice
     */
    public static PieSlice createSlice(String name, Color color, double value,
            int divergence, AbstractPieSliceFill sliceFill) {
        PieSlice pieslice = new PieSlice(name, color);
        pieslice.setValue(value);
        pieslice.setDivergence(divergence);
        pieslice.setSliceFill(sliceFill);
        return pieslice;
    }

    /**
     * create pie slice with specified parameters
     * 
     * @param name
     *            the slice name
     * @param color
     *            the slice color
     * @param value
     *            the slice value
     * @param divergence
     *            the slice divergence
     * @param sliceFill
     *            the painter to fill slice
     * @param sliceEffect
     *            the painter to make pie effect
     * @return the pie slice
     */
    public static PieSlice createSlice(String name, Color color, double value,
            int divergence, AbstractPieSliceFill sliceFill, AbstractPieSliceEffect sliceEffect) {
        PieSlice pieslice = new PieSlice(name, color);
        pieslice.setValue(value);
        pieslice.setDivergence(divergence);
        pieslice.setSliceFill(sliceFill);
        pieslice.setSliceEffect(sliceEffect);
        return pieslice;
    }

    /**
     * create border label with specified parameters
     * 
     * @param label
     *            the label display text
     * @return border label
     */
    public static PieBorderLabel createBorderLabel(String label) {
        PieBorderLabel pieBorderLabel = new PieBorderLabel();
        pieBorderLabel.setLabel(label);
        return pieBorderLabel;
    }

    /**
     * create border label with specified parameters
     * 
     * @param label
     *            the label display text
     * @param labelColor
     *            the label display color
     * @return border label
     */
    public static PieBorderLabel createBorderLabel(String label,
            Color labelColor) {
        PieBorderLabel pieBorderLabel = new PieBorderLabel();
        pieBorderLabel.setLabel(label);
        pieBorderLabel.setLabelColor(labelColor);
        return pieBorderLabel;
    }

    /**
     * create border label with specified parameters
     * 
     * @param label
     *            the label display text
     * @param labelColor
     *            the label display color
     * @param labelFont
     *            the label font
     * @return border label
     */
    public static PieBorderLabel createBorderLabel(String label,
            Color labelColor, Font labelFont) {
        PieBorderLabel pieBorderLabel = new PieBorderLabel();
        pieBorderLabel.setLabel(label);
        pieBorderLabel.setLabelColor(labelColor);
        pieBorderLabel.setLabelFont(labelFont);
        return pieBorderLabel;
    }

    /**
     * create border label with specified parameters
     * 
     * @param label
     *            the label display text
     * @param labelColor
     *            the label display color
     * @param labelFont
     *            the label font
     * @param margin
     *            the border label margin
     * @return border label
     */
    public static PieBorderLabel createBorderLabel(String label,
            Color labelColor, Font labelFont, int margin) {
        PieBorderLabel pieBorderLabel = new PieBorderLabel();
        pieBorderLabel.setLabel(label);
        pieBorderLabel.setLabelColor(labelColor);
        pieBorderLabel.setLabelFont(labelFont);
        pieBorderLabel.setMargin(margin);
        return pieBorderLabel;
    }

    /**
     * create slice bound label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @return slice bound label
     */
    public static PieBoundLabel createBoundLabel(String label, Color labelColor) {
        PieBoundLabel piesliceBoundLabel = new PieBoundLabel(label, labelColor);
        return piesliceBoundLabel;
    }

    /**
     * create slice bound label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param labelFont
     *            the label font
     * @return slice bound label
     */
    public static PieBoundLabel createBoundLabel(String label,
            Color labelColor, Font labelFont) {
        PieBoundLabel piesliceBoundLabel = new PieBoundLabel(label, labelColor,
                                                             labelFont);
        return piesliceBoundLabel;
    }

    /**
     * create pie slice radial label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @return slice radial label
     */
    public static PieRadialLabel createRadialLabel(String label,
            Color labelColor) {
        PieRadialLabel pieRadialLabel = new PieRadialLabel(label, labelColor);
        return pieRadialLabel;
    }

    /**
     * create pie slice radial label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param offsetRadius
     *            the label offset radius
     * @return slice radial label
     */
    public static PieRadialLabel createRadialLabel(String label,
            Color labelColor, int offsetRadius) {
        PieRadialLabel pieRadialLabel = new PieRadialLabel(label, labelColor);
        pieRadialLabel.setOffsetRadius(offsetRadius);
        return pieRadialLabel;
    }

    /**
     * create pie slice radial label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param offsetRadius
     *            the label offset radius
     * @param outlineColor
     *            the color to draw bound label
     * @param fillColor
     *            the color to fill bound label
     * @return slice radial label
     */
    public static PieRadialLabel createRadialLabel(String label,
            Color labelColor, int offsetRadius, Color outlineColor,
            Color fillColor) {
        PieRadialLabel pieRadialLabel = new PieRadialLabel(label, labelColor);
        pieRadialLabel.setOffsetRadius(offsetRadius);
        pieRadialLabel.setOutlineColor(outlineColor);
        pieRadialLabel.setFillColor(fillColor);
        return pieRadialLabel;
    }

    /**
     * create pie slice radial label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param labelFont
     *            the text label font
     * @return slice radial label
     */
    public static PieRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont) {
        PieRadialLabel pieRadialLabel = new PieRadialLabel(label, labelColor,
                                                           labelFont);
        return pieRadialLabel;
    }

    /**
     * create pie slice radial label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param labelFont
     *            the text label font
     * @param offsetRadius
     *            the label offset radius
     * @return slice radial label
     */
    public static PieRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont, int offsetRadius) {
        PieRadialLabel pieRadialLabel = new PieRadialLabel(label, labelColor,
                                                           labelFont);
        pieRadialLabel.setOffsetRadius(offsetRadius);
        return pieRadialLabel;
    }

    /**
     * create pie slice radial label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @param labelFont
     *            the text label font
     * @param offsetRadius
     *            the label offset radius
     * @param outlineColor
     *            the color to draw bound label
     * @param fillColor
     *            the color to fill bound label
     * @return slice radial label
     */
    public static PieRadialLabel createRadialLabel(String label,
            Color labelColor, Font labelFont, int offsetRadius,
            Color outlineColor, Color fillColor) {
        PieRadialLabel pieRadialLabel = new PieRadialLabel(label, labelColor,
                                                           labelFont);
        pieRadialLabel.setOffsetRadius(offsetRadius);
        pieRadialLabel.setOutlineColor(outlineColor);
        pieRadialLabel.setFillColor(fillColor);
        return pieRadialLabel;
    }

    /**
     * create slice path label with specified parameters
     * 
     * @param label
     *            the text label
     * @param labelColor
     *            the text label color
     * @return the pie slice path label
     */
    public static PiePathLabel createPathLabel(String label, Color labelColor) {
        PiePathLabel piePathLabel = new PiePathLabel(label, labelColor);
        return piePathLabel;
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
     * @return the pie slice path label
     */
    public static PiePathLabel createPathLabel(String label, Color labelColor,
            int divergence) {
        PiePathLabel piePathLabel = new PiePathLabel(label, labelColor);
        piePathLabel.setDivergence(divergence);
        return piePathLabel;
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
     * @return the pie slice path label
     */
    public static PiePathLabel createPathLabel(String label, Color labelColor,
            Font labelFont) {
        PiePathLabel piePathLabel = new PiePathLabel(label, labelColor,
                                                     labelFont);
        return piePathLabel;
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
     * @return the pie slice path label
     */
    public static PiePathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, int divergence) {
        PiePathLabel piePathLabel = new PiePathLabel(label, labelColor,
                                                     labelFont);
        piePathLabel.setDivergence(divergence);
        return piePathLabel;
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
     * @return the pie slice path label
     */
    public static PiePathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, TextPosition textPosition) {
        PiePathLabel piePathLabel = new PiePathLabel(label, labelColor,
                                                     labelFont);
        piePathLabel.setTextPosition(textPosition);
        return piePathLabel;
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
     * @return the pie slice path label
     */
    public static PiePathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, TextPosition textPosition, PathSide pathSide) {
        PiePathLabel piePathLabel = new PiePathLabel(label, labelColor,
                                                     labelFont);
        piePathLabel.setTextPosition(textPosition);
        piePathLabel.setPathSide(pathSide);
        return piePathLabel;
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
     * @return the pie slice path label
     */
    public static PiePathLabel createPathLabel(String label, Color labelColor,
            Font labelFont, TextPosition textPosition, int divergence) {
        PiePathLabel piePathLabel = new PiePathLabel(label, labelColor,
                                                     labelFont);
        piePathLabel.setTextPosition(textPosition);
        piePathLabel.setDivergence(divergence);
        return piePathLabel;
    }

    /**
     * push specified slices into specified pie
     * 
     * @param pie
     *            the host pie
     * @param slices
     *            the slices to push
     */
    public static void pushSlices(Pie pie, PieSlice... slices) {
        for (int i = 0; i < slices.length; i++) {
            pie.addSlice(slices[i]);
        }
    }

    /**
     * push specified slices into specified pie
     * 
     * @param pie
     *            the host pie
     * @param slices
     *            the slices to push
     */
    public static void pushSlices(Pie pie, List<PieSlice> slices) {
        for (PieSlice pieslice : slices) {
            pie.addSlice(pieslice);
        }
    }

}
