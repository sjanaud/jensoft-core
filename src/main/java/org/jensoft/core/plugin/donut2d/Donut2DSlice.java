/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d;

import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.donut2d.painter.draw.AbstractDonut2DSliceDraw;
import org.jensoft.core.plugin.donut2d.painter.effect.AbstractDonut2DSliceEffect;
import org.jensoft.core.plugin.donut2d.painter.fill.AbstractDonut2DSliceFill;
import org.jensoft.core.plugin.donut2d.painter.label.AbstractDonut2DSliceLabel;

/**
 * <code>Donut2DSlice</code>
 * 
 * @author sebastien janaud
 */
public class Donut2DSlice {

    /** slice name */
    private String name;

    /** slice value */
    private double value;

    /** divergence */
    private double divergence;

    /** slice normalized value */
    private double percent;

    /** start angle degree */
    private double startAngleDegree;

    /** end angle degree */
    private double endAngleDegree;

    /** slice path */
    private GeneralPath path;

    /** line start */
    private Line2D lineStart;

    /** line start */
    private Line2D lineEnd;

    /** slice outer arc */
    private Arc2D outerArc;

    /** slice inner arc */
    private Arc2D innerArc;

    /** slice theme color */
    private Color themeColor;

    /** lock roll over */
    private boolean lockRollover = false;

    /** lock pressed */
    private boolean lockPressed = false;

    /** slice lock enter flag */
    private boolean lockEnter = false;

    /** slice draw */
    private AbstractDonut2DSliceDraw sliceDraw;

    /** slice fill */
    private AbstractDonut2DSliceFill sliceFill;

    /** slice effect */
    private AbstractDonut2DSliceEffect sliceEffect;

    /** slice labels */
    private List<AbstractDonut2DSliceLabel> sliceLabels;

    /** host donut2D of this slice */
    private Donut2D host;

    /** slice alpha */
    private float alpha = 1f;

    /**
     * create slice with specified parameters
     * 
     * @param name
     *            the slice name
     * @param themeColor
     *            the slice color
     */
    public Donut2DSlice(String name, Color themeColor) {
        this.name = name;
        this.themeColor = themeColor;
        sliceLabels = new ArrayList<AbstractDonut2DSliceLabel>();
    }

    /**
     * get all slice label that have been registered on this slice
     * 
     * @return the slice Label collection of this slice
     */
    public List<AbstractDonut2DSliceLabel> getSliceLabels() {
        return sliceLabels;
    }

    /**
     * add specified label to this slice
     * 
     * @param sliceLabel
     *            the slice label to add
     */
    public void addSliceLabel(AbstractDonut2DSliceLabel sliceLabel) {
        sliceLabels.add(sliceLabel);
    }

    /**
     * remove specified label of this slice
     * 
     * @param sliceLabel
     *            the slice label to remove
     */
    public void removeSliceLabel(AbstractDonut2DSliceLabel sliceLabel) {
        sliceLabels.remove(sliceLabel);
    }

    /**
     * return true if the specified slice label is already register in this
     * slice, false otherwise
     * 
     * @param sliceLabel
     *            the sliceLabel to add
     */
    public boolean containsSliceLabel(AbstractDonut2DSliceLabel sliceLabel) {
        return sliceLabels.contains(sliceLabel);
    }

    /**
     * remove all registered slice labels
     */
    public void removeAllSliceLabels() {
        sliceLabels.clear();
    }

    /**
     * get theme color
     * 
     * @return theme color
     */
    public Color getThemeColor() {
        return themeColor;
    }

    /**
     * set theme color
     * 
     * @param colorTheme
     *            the theme color to set
     */
    public void setThemeColor(Color colorTheme) {
        themeColor = colorTheme;
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
     * @return the host
     */
    public Donut2D getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(Donut2D host) {
        this.host = host;
    }

    /**
     * @return the divergence
     */
    public double getDivergence() {
        return divergence;
    }

    /**
     * @param divergence
     *            the divergence to set
     */
    public void setDivergence(double divergence) {
        this.divergence = divergence;
    }

    /**
     * lock slice enter
     */
    public void lockEnter() {
        lockEnter = true;
    }

    /**
     * unlock slice enter
     */
    public void unlockEnter() {
        lockEnter = false;
    }

    /**
     * return true if slice is locked enter, false otherwise
     * 
     * @return true if slice is locked enter, false otherwise
     */
    public boolean isLockEnter() {
        return lockEnter;
    }

    /**
     * lock slice roll over
     */
    public void lockRollover() {
        lockRollover = true;
    }

    /**
     * unlock slice roll over
     */
    public void unlockRollover() {
        lockRollover = false;
    }

    /**
     * return true if slice is roll over locked, false otherwise
     * 
     * @return true if slice is roll over locked, false otherwise
     */
    public boolean isLockRollover() {
        return lockRollover;
    }

    /**
     * lock slice pressed
     */
    public void lockPressed() {
        lockPressed = true;
    }

    /**
     * unlock slice pressed
     */
    public void unlockPressed() {
        lockPressed = false;
    }

    /**
     * return true if slice is pressed locked, false otherwise
     * 
     * @return true if slice is pressed locked, false otherwise
     */
    public boolean isLockPressed() {
        return lockPressed;
    }

    /**
     * return true if slice contains specified point
     * 
     * @param p2d
     *            the point
     * @return true if slice contains specified point
     */
    public boolean contains(Point2D p2d) {
        if (path != null)
            return path.contains(p2d);
        else
            return false;
    }

    /**
     * return true if slice intersect specified bounding rectangle
     * 
     * @param rect2D
     *            the rectangle
     * @return true if slice intersect specified bounding rectangle
     */
    public boolean intersects(Rectangle2D rect2D) {
        return path.intersects(rect2D);
    }

    /**
     * get slice outer arc
     * 
     * @return slice outer arc
     */
    public Arc2D getOuterArc() {
        return outerArc;
    }

    /**
     * set slice outer arc
     * 
     * @param outerArc
     *            the outer arc to set
     */
    public void setOuterArc(Arc2D outerArc) {
        this.outerArc = outerArc;
    }

    /**
     * get slice inner arc
     * 
     * @return slice inner arc
     */
    public Arc2D getInnerArc() {
        return innerArc;
    }

    /**
     * set slice inner arc
     * 
     * @param innerArc
     *            the inner arc to set
     */
    public void setInnerArc(Arc2D innerArc) {
        this.innerArc = innerArc;
    }

    /**
     * get slice path
     * 
     * @return slice path
     */
    public GeneralPath getSlicePath() {
        return path;
    }

    /**
     * set slice path
     * 
     * @param path
     */
    public void setSlicePath(GeneralPath path) {
        this.path = path;
    }

    /**
     * get slice value
     * 
     * @return slice value
     */
    public double getValue() {
        return value;
    }

    /**
     * set the slice value
     * 
     * @param value
     *            the slice value to set
     * @throws IllegalArgumentException when value is strictly negative 
     */
    public void setValue(double value) {
    	if(value < 0)
    		throw new IllegalArgumentException("Slice value should be greater than or equal to 0");
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
     *            the slice name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get normalized value
     * 
     * @return slice normalized value
     */
    public double getPercent() {
        return percent;
    }

    /**
     * set slice normalized value
     * 
     * @param percent
     *            the normalized value to set
     */
    public void setPercent(double percent) {
        this.percent = percent;
    }

    /**
     * get slice start angle degree
     * 
     * @return slice start angle degree
     */
    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    /**
     * set slice start angle degree
     * 
     * @param startAngleDegree
     *            the start angle degree to set
     */
    public void setStartAngleDegree(double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    /**
     * get end angle degree
     * 
     * @return end angle degree
     */
    public double getEndAngleDegree() {
        return endAngleDegree;
    }

    /**
     * set end angle degree
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

    /**
     * @param sliceLabels
     *            the sliceLabels to set
     */
    public void setSliceLabels(List<AbstractDonut2DSliceLabel> sliceLabels) {
        this.sliceLabels = sliceLabels;
    }

    /**
     * @return the sliceDraw
     */
    public AbstractDonut2DSliceDraw getSliceDraw() {
        return sliceDraw;
    }

    /**
     * @param sliceDraw
     *            the sliceDraw to set
     */
    public void setSliceDraw(AbstractDonut2DSliceDraw sliceDraw) {
        this.sliceDraw = sliceDraw;
    }

    /**
     * @return the sliceFill
     */
    public AbstractDonut2DSliceFill getSliceFill() {
        return sliceFill;
    }

    /**
     * @param sliceFill
     *            the sliceFill to set
     */
    public void setSliceFill(AbstractDonut2DSliceFill sliceFill) {
        this.sliceFill = sliceFill;
    }

    /**
     * @return the sliceEffect
     */
    public AbstractDonut2DSliceEffect getSliceEffect() {
        return sliceEffect;
    }

    /**
     * @param sliceEffect
     *            the sliceEffect to set
     */
    public void setSliceEffect(AbstractDonut2DSliceEffect sliceEffect) {
        this.sliceEffect = sliceEffect;
    }

}
