/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.plugin.radar.painter.dimension.AbstractDimensionPainter;
import com.jensoft.core.plugin.radar.painter.dimension.DimensionDefaultPainter;
import com.jensoft.core.plugin.radar.painter.label.AbstractRadarDimensionLabel;

/**
 * RadarDimension
 */
public class RadarDimension {

    /** dimension name */
    private String name = "dimension";

    /** dimension angle degree */
    private double angleDegree = 0;

    /** dimension line shape */
    private Line2D dimensionLine;

    /** dimension general metrics path */
    private GeneralMetricsPath metricsPath;

    /** dimension painter */
    private AbstractDimensionPainter dimensionPainter;

    /** dimension label */
    private AbstractRadarDimensionLabel dimensionLabel;

    /** dimension line color */
    private Color lineColor;

    /** dimension line stroke */
    private Stroke lineStroke = new BasicStroke();

    /** dimension theme color */
    private Color themeColor;

    /**
     * create empty radar dimension
     */
    public RadarDimension() {
        initDimension();
    }

    /**
     * create radar dimension with specified parameters
     * 
     * @param angleDegree
     *            angle angle degree
     * @param minDimension
     *            start value at the radar center
     * @param maxDimension
     *            end value
     */
    public RadarDimension(double angleDegree, double minDimension,
            double maxDimension) {
        initDimension();
        this.angleDegree = angleDegree;
        metricsPath.setMin(minDimension);
        metricsPath.setMax(maxDimension);
    }

    /**
     * create radar dimension with specified parameters
     * 
     * @param name
     *            dimension name
     * @param angleDegree
     *            angle angle degree
     * @param minDimension
     *            start value at the radar center
     * @param maxDimension
     *            end value
     */
    public RadarDimension(String name, double angleDegree, double minDimension,
            double maxDimension) {
        initDimension();
        this.name = name;
        this.angleDegree = angleDegree;
        metricsPath.setMin(minDimension);
        metricsPath.setMax(maxDimension);
    }

    /**
     * create radar dimension with specified parameters
     * 
     * @param name
     *            dimension name
     * @param lineColor
     *            dimension line color
     * @param angleDegree
     *            angle angle degree
     * @param minDimension
     *            start value at the radar center
     * @param maxDimension
     *            end value
     */
    public RadarDimension(String name, Color lineColor, double angleDegree,
            double minDimension, double maxDimension) {
        initDimension();
        this.name = name;
        this.lineColor = lineColor;
        this.angleDegree = angleDegree;
        metricsPath.setMin(minDimension);
        metricsPath.setMax(maxDimension);
    }

    /**
     * initialize metrics dimension
     */
    private void initDimension() {
        metricsPath = new GeneralMetricsPath();
        metricsPath.setProjectionNature(ProjectionNature.DEVICE);
        metricsPath.setAutoReverseGlyph(true);
        dimensionPainter = new DimensionDefaultPainter();
    }

    /**
     * @return the lineStroke
     */
    public Stroke getLineStroke() {
        return lineStroke;
    }

    /**
     * @param lineStroke
     *            the lineStroke to set
     */
    public void setLineStroke(Stroke lineStroke) {
        this.lineStroke = lineStroke;
    }

    /**
     * @return the dimensionPainter
     */
    public AbstractDimensionPainter getDimensionPainter() {
        return dimensionPainter;
    }

    /**
     * @param dimensionPainter
     *            the dimensionPainter to set
     */
    public void setDimensionPainter(AbstractDimensionPainter dimensionPainter) {
        this.dimensionPainter = dimensionPainter;
    }

    /**
     * @return the themeColor
     */
    public Color getThemeColor() {
        if (themeColor == null) {
            themeColor = ColorPalette.getRandomColor();
        }
        return themeColor;
    }

    /**
     * @param themeColor
     *            the themeColor to set
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * @return the lineColor
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * @param lineColor
     *            the lineColor to set
     */
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * add metric to this radar dimension
     * 
     * @param radarMetrics
     *            the radar metric to add on this dimension
     * @throws IllegalArgumentException
     *             if radarMetrics is out of dimension minimum and maximum bound
     *             segment
     */
    public void addMetrics(DimensionMetrics dimensionMetrics) {
        double metricsValue = dimensionMetrics.getValue();
        if (metricsValue < getMinDimension()
                || metricsValue > getMaxDimension()) {
            throw new IllegalArgumentException(
                                               "dimension metrics value out of dimension range.");
        }

        metricsPath.addMetric(dimensionMetrics);
    }

    /**
     * @return the angleDegree
     */
    public double getAngleDegree() {
        return angleDegree;
    }

    /**
     * @param angleDegree
     *            the angleDegree to set
     */
    public void setAngleDegree(double angleDegree) {
        this.angleDegree = angleDegree;
    }

    /**
     * @return the minDimension
     */
    public double getMinDimension() {
        return metricsPath.getMin();
    }

    /**
     * @param minDimension
     *            the minDimension to set
     */
    public void setMinDimension(double minDimension) {
        metricsPath.setMin(minDimension);
        metricsPath.resetPath();
    }

    /**
     * @return the maxDimension
     */
    public double getMaxDimension() {
        return metricsPath.getMax();
    }

    /**
     * @param maxDimension
     *            the maxDimension to set
     */
    public void setMaxDimension(double maxDimension) {
        metricsPath.setMax(maxDimension);
        metricsPath.resetPath();
    }

    /**
     * @return the dimensionLine
     */
    public Line2D getDimensionLine() {
        return dimensionLine;
    }

    /**
     * @param dimensionLine
     *            the dimensionLine to set
     */
    public void setDimensionLine(Line2D dimensionLine) {
        this.dimensionLine = dimensionLine;

        metricsPath.resetPath();
        metricsPath.append(dimensionLine);
    }

    /**
     * get metrics point projection for the specified user value
     * 
     * @param value
     *            user metrics value
     * @return metrics coordinate reference
     */
    public Point2D getMetricsPoint(double value) {
        return metricsPath.getMetricsPoint(value);
    }

    /**
     * solve radar metrics
     * 
     * @param radarMetrics
     *            the radar metrics geometry to solve
     * @return metrics coordinate reference
     */
    public void solveMetrics(RadarMetrics radarMetrics) {
        metricsPath.solveMetrics(radarMetrics);
    }

    /**
     * @return the metricsPath
     */
    public GeneralMetricsPath getMetricsPath() {
        return metricsPath;
    }

    /**
     * @return the dimensionLabel
     */
    public AbstractRadarDimensionLabel getDimensionLabel() {
        return dimensionLabel;
    }

    /**
     * @param dimensionLabel
     *            the dimensionLabel to set
     */
    public void setDimensionLabel(AbstractRadarDimensionLabel dimensionLabel) {
        this.dimensionLabel = dimensionLabel;
    }

}
