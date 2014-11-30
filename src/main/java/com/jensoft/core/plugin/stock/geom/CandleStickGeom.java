/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stock.geom;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.palette.color.NanoChromatique;

/**
 * <code>CandleStickGeom</code> defines the common candle stick market geometry
 * with line for low/high amplitude and rectangle for open/close amplitude
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class CandleStickGeom extends StockItemGeometry {

	private Color lowHighColor = NanoChromatique.BLACK.brighter();

	/**line low/high shape*/
	private Line2D deviceLowHighGap;
	
	/**rectangle open/close shape*/
	private Rectangle2D deviceLowOpenCloseGap;

	/**
	 * create candle stick geometry
	 */
	public CandleStickGeom() {
	}

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.stock.geom.StockItemGeometry#solveGeometry()
	 */
	@Override
	public void solveGeometry() {

		super.solveGeometry();
		Line2D deviceLowHighGap = new Line2D.Double(getDeviceLow(), getDeviceHigh());
		setDeviceLowHighGap(deviceLowHighGap);

		Rectangle2D deviceLowOpenCloseGap = null;
		if (getStock().getOpen() > getStock().getClose()) {
			deviceLowOpenCloseGap = new Rectangle2D.Double(getDeviceFixingStart(), getDeviceOpen().getY(), getDeviceFixingDuration(), Math.abs(getDeviceOpen().getY() - getDeviceClose().getY()));
		} else {
			deviceLowOpenCloseGap = new Rectangle2D.Double(getDeviceFixingStart(), getDeviceClose().getY(), getDeviceFixingDuration(), Math.abs(getDeviceOpen().getY() - getDeviceClose().getY()));
		}
		setDeviceLowOpenCloseGap(deviceLowOpenCloseGap);
	}

	public Line2D getDeviceLowHighGap() {
		return deviceLowHighGap;
	}

	public void setDeviceLowHighGap(Line2D deviceLowHighGap) {
		this.deviceLowHighGap = deviceLowHighGap;
	}

	public Rectangle2D getDeviceLowOpenCloseGap() {
		return deviceLowOpenCloseGap;
	}

	public void setDeviceLowOpenCloseGap(Rectangle2D deviceLowOpenCloseGap) {
		this.deviceLowOpenCloseGap = deviceLowOpenCloseGap;
	}

	public Color getLowHighColor() {
		return lowHighColor;
	}

	public void setLowHighColor(Color lowHighColor) {
		this.lowHighColor = lowHighColor;
	}

}
