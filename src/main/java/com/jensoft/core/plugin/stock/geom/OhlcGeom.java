/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stock.geom;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.jensoft.core.palette.NanoChromatique;

public class OhlcGeom extends StockItemGeometry{
	
	private Color lowHighColor = NanoChromatique.BLACK.brighter();
	
	
	private Line2D deviceLowHighGap;
	private Line2D deviceOpenTick;
	private Line2D deviceCloseTick;
	
	
	
	public OhlcGeom() {
		
	}

	@Override
	public void solveGeometry() {
		
		super.solveGeometry();
		
		Line2D deviceLowHighGap = new Line2D.Double(getDeviceLow(), getDeviceHigh());
		setDeviceLowHighGap(deviceLowHighGap);

		Line2D deviceOpenTick = new Line2D.Double(new Point2D.Double(getDeviceOpen().getX()-getDeviceFixingDuration()/2,getDeviceOpen().getY()), getDeviceOpen());
		setDeviceOpenTick(deviceOpenTick);
		
		Line2D deviceCloseTick = new Line2D.Double(getDeviceClose(), new Point2D.Double(getDeviceClose().getX()+getDeviceFixingDuration()/2,getDeviceClose().getY()));
		setDeviceCloseTick(deviceCloseTick);
	}

	public Line2D getDeviceLowHighGap() {
		return deviceLowHighGap;
	}

	public void setDeviceLowHighGap(Line2D deviceLowHighGap) {
		this.deviceLowHighGap = deviceLowHighGap;
	}

	
	public Color getLowHighColor() {
		return lowHighColor;
	}

	public void setLowHighColor(Color lowHighColor) {
		this.lowHighColor = lowHighColor;
	}

	public Line2D getDeviceOpenTick() {
		return deviceOpenTick;
	}

	public void setDeviceOpenTick(Line2D deviceOpenTick) {
		this.deviceOpenTick = deviceOpenTick;
	}

	public Line2D getDeviceCloseTick() {
		return deviceCloseTick;
	}

	public void setDeviceCloseTick(Line2D deviceCloseTick) {
		this.deviceCloseTick = deviceCloseTick;
	}



	

	

}
