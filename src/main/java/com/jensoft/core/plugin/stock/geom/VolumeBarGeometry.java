/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stock.geom;


import java.awt.geom.Rectangle2D;

public class VolumeBarGeometry extends StockItemGeometry {

	
	private Rectangle2D deviceVolumeGap;
	
	public VolumeBarGeometry() {
	}
	
	public Rectangle2D getDeviceVolumeGap() {
		return deviceVolumeGap;
	}

	public void setDeviceVolumeGap(Rectangle2D deviceVolumeGap) {
		this.deviceVolumeGap = deviceVolumeGap;
	}

	@Override
	public void solveGeometry() {
		super.solveGeometry();
		Rectangle2D deviceVolumeGap = new Rectangle2D.Double(getDeviceFixingStart(), getDeviceVolume().getY(), getDeviceFixingDuration(), Math.abs(getDeviceVolume().getY() - getDeviceVolumeBase().getY()));
		setDeviceVolumeGap(deviceVolumeGap);
	}
	
	

}
