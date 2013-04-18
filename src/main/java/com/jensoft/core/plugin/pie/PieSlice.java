/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.pie.painter.draw.AbstractPieSliceDraw;
import com.jensoft.core.plugin.pie.painter.effect.AbstractPieSliceEffect;
import com.jensoft.core.plugin.pie.painter.fill.AbstractPieSliceFill;
import com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel;
import com.jensoft.core.view.View2D;

/**
 * definition of the pie slice
 */
public class PieSlice {

    /** the host pie */
    private Pie host;

    /** slice name */
    private String name;

    /** slice value */
    private double value;

    /** slice percent value */
    private double percent;

    /** divergence */
    private int divergence;

    /** start angle degree of this pie slice */
    private double startAngleDegree;

    /** end angle degree of this pie slice */
    private double endAngleDegree;

    /** theme color of this pie slice */
    private Color themeColor;

    /** slice path */
    private GeneralPath slicePath;

    /** external arc */
    private Arc2D externalArc;

    /** line start */
    private Line2D lineStart;

    /** line start */
    private Line2D lineEnd;

    /** slice draw */
    private AbstractPieSliceDraw sliceDraw;

    /** slice fill */
    private AbstractPieSliceFill sliceFill;

    /** slice effect */
    private AbstractPieSliceEffect sliceEffect;

    /** slice label */
    private List<AbstractPieSliceLabel> sliceLabels;

    /** enter flag */
    private boolean lockEnter = false;

    /** alpha slice */
    private float alpha = 1;

    /**
     * create pie slice with the given parameters
     * 
     * @param name
     *            the pie slice name
     * @param themeColor
     *            the pie theme color
     */
    public PieSlice(String name, Color themeColor) {
        this.name = name;
        this.themeColor = themeColor;
        divergence = 0;
        sliceLabels = new ArrayList<AbstractPieSliceLabel>();
    }
    
    /**
     * create pie slice with the given parameters
     * 
     * @param name
     *            the pie slice name
     * @param themeColor
     *            the pie theme color
     */
    public PieSlice(String name, Color themeColor,double value) {
        this.name = name;
        this.themeColor = themeColor;
        this.value=value;
        divergence = 0;
        sliceLabels = new ArrayList<AbstractPieSliceLabel>();
    }

    /**
     * get the pie host
     * 
     * @return the host
     */
    public Pie getHost() {
        return host;
    }

    /**
     * get the host view
     * 
     * @return the host view
     */
    public View2D getView2D() {
        return getHost().getHostPlugin().getWindow2D().getView2D();
    }

    /**
     * set the pie host
     * 
     * @param host
     *            the host to set
     */
    public void setHost(Pie host) {
        this.host = host;
    }

    /**
     * get external arc of this slice
     * 
     * @return the external arc
     */
    public Arc2D getExternalArc() {
        return externalArc;
    }

    /**
     * set the external arc of this slice
     * 
     * @param externalArc
     */
    public void setExternalArc(Arc2D externalArc) {
        this.externalArc = externalArc;
    }

    /**
     * @return the lineStart
     */
    public Line2D getLineStart() {
        return lineStart;
    }

    /**
     * @param lineStart
     *            the lineStart to set
     */
    public void setLineStart(Line2D lineStart) {
        this.lineStart = lineStart;
    }

    /**
     * @return the lineEnd
     */
    public Line2D getLineEnd() {
        return lineEnd;
    }

    /**
     * @param lineEnd
     *            the lineEnd to set
     */
    public void setLineEnd(Line2D lineEnd) {
        this.lineEnd = lineEnd;
    }

    /**
     * get the slice path
     * 
     * @return the slice path
     */
    public GeneralPath getSlicePath() {
        return slicePath;
    }

    /**
     * get slice bound
     * 
     * @return slice bound
     */
    public Rectangle2D getBounds2D() {
        if (slicePath != null) {
            return slicePath.getBounds2D();
        }
        return null;
    }

    /**
     * get slice bound
     * 
     * @return slice bound
     */
    public Rectangle getBounds() {
        if (slicePath != null) {
            return slicePath.getBounds();
        }
        return null;
    }

    /**
     * set the slice path
     * 
     * @param path
     *            the slice path to set
     */
    public void setSlicePath(GeneralPath path) {
        slicePath = path;
    }

    /**
     * get start angle degree of this slice
     * 
     * @return start angle degree of this slice
     */
    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    /**
     * set start angle degree of this slice
     * 
     * @param startAngleDegree
     *            of this slice start angle degree to set
     */
    public void setStartAngleDegree(double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    /**
     * get end angle degree of this slice
     * 
     * @return end angle degree of this slice
     */
    public double getEndAngleDegree() {
        return endAngleDegree;
    }

    /**
     * set end angle degree of this slice
     * 
     * @param endAngleDegree
     *            the end angle degree to set
     */
    public void setEndAngleDegree(double endAngleDegree) {
        this.endAngleDegree = endAngleDegree;
    }

    /**
     * get median angle degree of this slice
     * 
     * @return end median degree of this slice
     */
    public double getMedianAngleDegree() {
        return getStartAngleDegree() + getExtendsDegree() / 2;
    }

    /**
     * get extends degree of this slice
     * 
     * @return extends degree of this slice
     */
    public double getExtendsDegree() {
        return Math.abs(getEndAngleDegree() - getStartAngleDegree());
    }

    /**
     * get theme color of this slice
     * 
     * @return theme color of this slice
     */
    public Color getThemeColor() {
        return themeColor;
    }

    /**
     * set theme color of this slice
     * 
     * @param themeColor
     *            the theme color to set
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * get value of this slice
     * 
     * @return the pie value
     */
    public double getValue() {
        return value;
    }

    /**
     * set pie value
     * 
     * @param value
     *            the pie value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * get slice name
     * 
     * @return slice name
     */
    public String getName() {
        return name;
    }

    /**
     * set slice name
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get slice draw
     * 
     * @return slice draw
     */
    public AbstractPieSliceDraw getSliceDraw() {
        return sliceDraw;
    }

    /**
     * set slice draw
     * 
     * @param sliceDraw
     *            the slice draw to set
     */
    public void setSliceDraw(AbstractPieSliceDraw sliceDraw) {
        this.sliceDraw = sliceDraw;
    }

    /**
     * get slice fill
     * 
     * @return the slice fill
     */
    public AbstractPieSliceFill getSliceFill() {
        return sliceFill;
    }

    /**
     * set the slice fill
     * 
     * @param sliceFill
     *            the slice fill to set
     */
    public void setSliceFill(AbstractPieSliceFill sliceFill) {
        this.sliceFill = sliceFill;
    }

    /**
     * get slice effect
     * 
     * @return slice effect
     */
    public AbstractPieSliceEffect getSliceEffect() {
        return sliceEffect;
    }

    /**
     * set slice effect
     * 
     * @param sliceEffect
     *            the slice effect to set
     */
    public void setSliceEffect(AbstractPieSliceEffect sliceEffect) {
        this.sliceEffect = sliceEffect;
    }

    /**
     * @return the sliceLabel
     */
    public List<AbstractPieSliceLabel> getSliceLabels() {
        return sliceLabels;
    }

    /**
     * @param sliceLabels
     *            the sliceLabel list to set
     */
    public void setSliceLabels(List<AbstractPieSliceLabel> sliceLabels) {
        this.sliceLabels = sliceLabels;
    }

    /**
     * @param sliceLabel
     *            the sliceLabel to add
     */
    public void addSliceLabel(AbstractPieSliceLabel sliceLabel) {
        sliceLabels.add(sliceLabel);
    }

    /**
     * @param sliceLabel
     *            the sliceLabel to remove
     */
    public void removeSliceLabel(AbstractPieSliceLabel sliceLabel) {
        sliceLabels.remove(sliceLabel);
    }

    /**
     * return true if the specified slice label is already register in this
     * slice, false otherwise
     * 
     * @param sliceLabel
     *            the sliceLabel to add
     */
    public boolean containsSliceLabel(AbstractPieSliceLabel sliceLabel) {
        return sliceLabels.contains(sliceLabel);
    }

    /**
     * remove all registered slice labels
     */
    public void removeAllSliceLabels() {
        sliceLabels.clear();
    }

    /**
     * get the normalized value of this slice
     * 
     * @return the normalized value
     */
    public double getPercent() {
        return percent;
    }

    /**
     * set normalized value of this slice
     * 
     * @param percent
     *            the normalized value to set
     */
    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * get divergence of this slice
     * 
     * @return the divergence of this slice
     */
    public int getDivergence() {
        return divergence;
    }

    /**
     * set divergence to this slice
     * 
     * @param divergence
     *            the divergence to set
     */
    public void setDivergence(int divergence) {
        this.divergence = divergence;
    }

    /**
     * return true if mouse has just enter in this slice, false otherwise
     * 
     * @return enter flag
     */
    public boolean isLockEnter() {
        return lockEnter;
    }

    /**
     * lock slice enter flag
     */
    public void lockEnter() {
        if (!isLockEnter()) {
            lockEnter = true;
        }
    }

    /**
     * unlock slice enter
     */
    public void unlockEnter() {
        if (isLockEnter()) {
            lockEnter = false;
        }
    }

    /**
     * @return the alpha
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * @param alpha
     *            the alpha to set
     */
    public void setAlpha(float alpha) {
        if (alpha < 0 || alpha > 1) {
            throw new IllegalArgumentException("slice alpha out of range [0,1]");
        }

        this.alpha = alpha;
    }

}
