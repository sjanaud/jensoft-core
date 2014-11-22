/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.area;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.plugin.function.Function;
import com.jensoft.core.plugin.function.FunctionPlugin.AreaFunction;
import com.jensoft.core.plugin.function.area.painter.AreaEffect;
import com.jensoft.core.plugin.function.area.painter.draw.AbstractAreaDraw;
import com.jensoft.core.plugin.function.area.painter.fill.AbstractAreaFill;
import com.jensoft.core.plugin.function.source.FunctionNature;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.plugin.function.source.UserSourceFunction.LineSource;
import com.jensoft.core.plugin.function.source.UserSourceFunction.RegressionSource;
import com.jensoft.core.plugin.function.source.UserSourceFunction.SplineSource;

/**
 * <code>Area</code> defines an area function 
 * <br>
 * <center><img src="doc-files/simpleAreaCurve.png"/></center> <br>
 * <br>
 * <br>
 * <center><img src="doc-files/multipleAreaCurve.png"/></center> <br>
 * <br>
 * 
 * @see SourceFunction
 * @see LineSource
 * @see SplineSource
 * @see RegressionSource
 * @see AreaFunction
 * @see AbstractAreaFill
 * @see AbstractAreaDraw
 * @see GlyphMetric
 * @author Sebastien Janaud
 */
public class Area extends Function {

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
	public Area() {
		super();
	}

	/**
	 * create Area with specified serie
	 * 
	 * @param source
	 *            the source to set
	 */
	public Area(SourceFunction source) {
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
	public java.awt.geom.Area getArea() {
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

	
	private Point2D minFunction() {
		Point2D minFunction = getSourceFunction().getCurrentFunction().get(0);
		if (FunctionNature.XFunction == getSourceFunction().getNature()) {
			for (Point2D p : getSourceFunction().getCurrentFunction()) {
				if (p.getY() < minFunction.getY()) {
					minFunction = p;
				}
			}

		}
		if (FunctionNature.YFunction == getSourceFunction().getNature()) {
			for (Point2D p : getSourceFunction().getCurrentFunction()) {
				if (p.getX() < minFunction.getX()) {
					minFunction = p;
				}
			}
		}
		return minFunction;
	}

	/**
	 * solve area geometry
	 */
	public void solveGeometry() {
		if (!areaBaseSet) {
			if (getSourceFunction().getNature() == FunctionNature.XFunction) {
				setAreaBase(minFunction().getY());
			} else {
				setAreaBase(minFunction().getX());
			}
		}
		Shape curvePath = getPathFunction().getOrCreateGeometry().getPath();
		//Point2D minSource = getSourceFunction().first();
		//Point2D maxSource = getSourceFunction().last();
		Point2D minSource = getSourceFunction().getCurrentFunction().get(0);
		Point2D maxSource = getSourceFunction().getCurrentFunction().get(getSourceFunction().getCurrentFunction().size()-1);
		Point2D deviceAreaMin = getHost().getProjection().userToPixel(minSource);
		Point2D deviceAreaMax = getHost().getProjection().userToPixel(maxSource);

		double areaMin;
		double areaMax;

		Point2D deviceBaseMin;
		Point2D deviceBaseMax;
		if (getSourceFunction().getNature() == FunctionNature.XFunction) {
			areaMin = minSource.getX();
			areaMax = maxSource.getX();

			deviceBaseMin = getHost().getProjection().userToPixel(new Point2D.Double(areaMin, getAreaBase()));
			deviceBaseMax = getHost().getProjection().userToPixel(new Point2D.Double(areaMax, getAreaBase()));
		} else {
			areaMin = minSource.getY();
			areaMax = maxSource.getY();

			deviceBaseMin = getHost().getProjection().userToPixel(new Point2D.Double(getAreaBase(), areaMin));
			deviceBaseMax = getHost().getProjection().userToPixel(new Point2D.Double(getAreaBase(), areaMax));
		}

		setBaseLine(new Line2D.Double(deviceBaseMin, deviceBaseMax));

		GeneralPath ap = new GeneralPath();
		ap.append(curvePath, false);
		ap.append(new Line2D.Double(deviceAreaMax, deviceBaseMax), true);
		ap.append(new Line2D.Double(deviceBaseMax, deviceBaseMin), true);
		ap.append(new Line2D.Double(deviceBaseMin, deviceAreaMin), true);
		ap.closePath();

		setAreaPath(ap);

		int width = getHost().getProjection().getDevice2D().getDeviceWidth();
		int height = getHost().getProjection().getDevice2D().getDeviceHeight();
		java.awt.geom.Area area = new java.awt.geom.Area(new Rectangle(0, 0, width, height));
		area.intersect(new java.awt.geom.Area(ap));

		setArea(area);
	}

}
