/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.area;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.plugin.function.FunctionPlugin.AreaFunctionPlugin;
import com.jensoft.core.plugin.function.area.painter.AreaEffect;
import com.jensoft.core.plugin.function.area.painter.draw.AbstractAreaDraw;
import com.jensoft.core.plugin.function.area.painter.fill.AbstractAreaFill;
import com.jensoft.core.plugin.function.core.Function;
import com.jensoft.core.plugin.function.source.AffineSourceFunction;
import com.jensoft.core.plugin.function.source.LinearRegressionSourceFunction;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.plugin.function.source.SplineSourceFunction;

/**
 * <code>AreaFunction</code> defines an area function that can be added in {@link AreaFunctionPlugin}<br>
 * <br>
 * <center><img src="doc-files/simpleAreaCurve.png"/></center> <br>
 * <br>
 * <br>
 * <center><img src="doc-files/multipleAreaCurve.png"/></center> <br>
 * <br>
 * <p>
 * to add a new area curve into AreaCurvePlugin, Create {@link AreaFunction} and add them to plugin with this method
 * {@link AreaFunctionPlugin#addFunction(AreaFunction)}
 * </p>
 * <p>
 * AreaCurve use {@link SourceFunction} to define curve points.
 * </p>
 * 
 * @see SourceFunction
 * @see AffineSourceFunction
 * @see SplineSourceFunction
 * @see LinearRegressionSourceFunction
 * @see AreaFunctionToolkit
 * @see AreaFunctionPlugin
 * @see AbstractAreaFill
 * @see AbstractAreaDraw
 * @see GlyphMetric
 * @author Sebastien Janaud
 */
public class AreaFunction extends Function {

    /** area base */
    private double areaBase;

    /** area base flag set */
    private boolean areaBaseSet = false;

    /** area geometry path */
    private GeneralPath areaPath;

    /** area */
    private java.awt.geom.Area area;

    /** area base line */
    private Line2D baseLine;

    /** area draw */
    private AbstractAreaDraw areaDraw;

    /** area fill */
    private AbstractAreaFill areaFill;

    /** area effect */
    private AreaEffect areaEffect;

    /**
     * create empty Area
     */
    public AreaFunction() {
        super();
    }

    /**
     * create Area with specified serie
     * 
     * @param source
     *            the source to set
     */
    public AreaFunction(SourceFunction source) {
        super("area", source);
    }

    /**
     * @return the areaPath
     */
    public GeneralPath getAreaPath() {
        return areaPath;
    }

    /**
     * @param areaPath
     *            the areaPath to set
     */
    public void setAreaPath(GeneralPath areaPath) {
        this.areaPath = areaPath;
    }

    /**
     * @return the area
     */
    public Area getArea() {
        return area;
    }

    /**
     * @param area
     *            the area to set
     */
    public void setArea(java.awt.geom.Area area) {
        this.area = area;
    }

    /**
     * @return the areaDraw
     */
    public AbstractAreaDraw getAreaDraw() {
        return areaDraw;
    }

    /**
     * @param areaDraw
     *            the areaDraw to set
     */
    public void setAreaDraw(AbstractAreaDraw areaDraw) {
        this.areaDraw = areaDraw;
    }

    /**
     * @return the areaFill
     */
    public AbstractAreaFill getAreaFill() {
        return areaFill;
    }

    /**
     * @param areaFill
     *            the areaFill to set
     */
    public void setAreaFill(AbstractAreaFill areaFill) {
        this.areaFill = areaFill;
    }

    /**
     * @return the areaEffect
     */
    public AreaEffect getAreaEffect() {
        return areaEffect;
    }

    /**
     * @param areaEffect
     *            the areaEffect to set
     */
    public void setAreaEffect(AreaEffect areaEffect) {
        this.areaEffect = areaEffect;
    }

    /**
     * @return the areaBase
     */
    public double getAreaBase() {
        return areaBase;
    }

    /**
     * @param areaBase
     *            the areaBase to set
     */
    public void setAreaBase(double areaBase) {
        this.areaBase = areaBase;
        areaBaseSet = true;
    }

    /**
     * @return the baseLine
     */
    public Line2D getBaseLine() {
        return baseLine;
    }

    /**
     * @param baseLine
     *            the baseLine to set
     */
    public void setBaseLine(Line2D baseLine) {
        this.baseLine = baseLine;
    }

    /**
     * solve area geometry
     */
    public void solveGeometry() {
        if (!areaBaseSet) {
            setAreaBase(getSourceFunction().minFunction().getY());
        }
        Shape curvePath = getPathFunction().getOrCreateGeometry().getPath();
        Point2D minSource = getSourceFunction().first();
        Point2D maxSource = getSourceFunction().last();
        Point2D deviceAreaMin = getHost().getWindow2D().userToPixel(minSource);
        Point2D deviceAreaMax = getHost().getWindow2D().userToPixel(maxSource);

        double areaXMin = minSource.getX();
        double areaXMax = maxSource.getX();

        Point2D deviceBaseMin = getHost().getWindow2D().userToPixel(new Point2D.Double(areaXMin, getAreaBase()));
        Point2D deviceBaseMax = getHost().getWindow2D().userToPixel(new Point2D.Double(areaXMax, getAreaBase()));

        setBaseLine(new Line2D.Double(deviceBaseMin, deviceBaseMax));

        GeneralPath ap = new GeneralPath();
        ap.append(curvePath, false);
        ap.append(new Line2D.Double(deviceAreaMax, deviceBaseMax), true);
        ap.append(new Line2D.Double(deviceBaseMax, deviceBaseMin), true);
        ap.append(new Line2D.Double(deviceBaseMin, deviceAreaMin), true);
        ap.closePath();

        setAreaPath(ap);

        int width = getHost().getWindow2D().getDevice2D().getDeviceWidth();
        int height = getHost().getWindow2D().getDevice2D().getDeviceHeight();
        java.awt.geom.Area area = new java.awt.geom.Area(new Rectangle(0, 0, width, height));
        area.intersect(new java.awt.geom.Area(ap));

        setArea(area);
    }

}
