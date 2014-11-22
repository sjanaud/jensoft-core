/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stock.geom;

import java.awt.geom.Point2D;

import com.jensoft.core.plugin.stock.Stock;
import com.jensoft.core.projection.Projection;

public class StockItemGeometry extends StockGeometry {

	private Stock stock;

	private Point2D deviceLow;
	private Point2D deviceHigh;

	private Point2D deviceOpen;
	private Point2D deviceClose;

	private Point2D deviceVolume;
	private Point2D deviceVolumeBase;

	private double deviceFixing;
	private double deviceFixingStart;
	private double deviceFixingEnd;
	private double deviceFixingDuration;

	public StockItemGeometry() {
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Point2D getDeviceLow() {
		return deviceLow;
	}

	public void setDeviceLow(Point2D deviceLow) {
		this.deviceLow = deviceLow;
	}

	public Point2D getDeviceHigh() {
		return deviceHigh;
	}

	public void setDeviceHigh(Point2D deviceHigh) {
		this.deviceHigh = deviceHigh;
	}

	public Point2D getDeviceOpen() {
		return deviceOpen;
	}

	public void setDeviceOpen(Point2D deviceOpen) {
		this.deviceOpen = deviceOpen;
	}

	public Point2D getDeviceClose() {
		return deviceClose;
	}

	public void setDeviceClose(Point2D deviceClose) {
		this.deviceClose = deviceClose;
	}

	public Point2D getDeviceVolume() {
		return deviceVolume;
	}

	public void setDeviceVolume(Point2D deviceVolume) {
		this.deviceVolume = deviceVolume;
	}

	public Point2D getDeviceVolumeBase() {
		return deviceVolumeBase;
	}

	public void setDeviceVolumeBase(Point2D deviceVolumeBase) {
		this.deviceVolumeBase = deviceVolumeBase;
	}

	public double getDeviceFixing() {
		return deviceFixing;
	}

	public void setDeviceFixing(double deviceFixing) {
		this.deviceFixing = deviceFixing;
	}

	public double getDeviceFixingStart() {
		return deviceFixingStart;
	}

	public void setDeviceFixingStart(double deviceFixingStart) {
		this.deviceFixingStart = deviceFixingStart;
	}

	public double getDeviceFixingEnd() {
		return deviceFixingEnd;
	}

	public void setDeviceFixingEnd(double deviceFixingEnd) {
		this.deviceFixingEnd = deviceFixingEnd;
	}

	public double getDeviceFixingDuration() {
		return deviceFixingDuration;
	}

	public void setDeviceFixingDuration(double deviceFixingDuration) {
		this.deviceFixingDuration = deviceFixingDuration;
	}

	@Override
	public void solveGeometry() {
		// stock session
		Point2D deviceLow = getWindow2D().userToPixel(new Point2D.Double(new Long(stock.getFixing().getTime()).doubleValue(), stock.getLow()));
		Point2D deviceHigh = getWindow2D().userToPixel(new Point2D.Double(new Long(stock.getFixing().getTime()).doubleValue(), stock.getHigh()));
		Point2D deviceOpen = getWindow2D().userToPixel(new Point2D.Double(new Long(stock.getFixing().getTime()).doubleValue(), stock.getOpen()));
		Point2D deviceClose = getWindow2D().userToPixel(new Point2D.Double(new Long(stock.getFixing().getTime()).doubleValue(), stock.getClose()));

		setDeviceLow(deviceLow);
		setDeviceHigh(deviceHigh);
		setDeviceClose(deviceClose);
		setDeviceOpen(deviceOpen);

		// volume
		Point2D deviceVolume = getWindow2D().userToPixel(new Point2D.Double(new Long(stock.getFixing().getTime()).doubleValue(), stock.getVolume()));
		setDeviceVolume(deviceVolume);

		Point2D deviceVolumeBase = getWindow2D().userToPixel(new Point2D.Double(new Long(stock.getFixing().getTime()).doubleValue(), 0));
		setDeviceVolumeBase(deviceVolumeBase);

		// fixing
		double deviceFixingStart = getWindow2D().userToPixelX(new Long(stock.getFixing().getTime() - stock.getFixingDurationMillis() / 2).doubleValue());
		double deviceFixingEnd = getWindow2D().userToPixelX(new Long(stock.getFixing().getTime() + stock.getFixingDurationMillis() / 2).doubleValue());

		double deviceFixingDuration = Math.abs(deviceFixingEnd - deviceFixingStart);
		double deviceTimingX = getWindow2D().userToPixelX(new Long(stock.getFixing().getTime()).doubleValue());

		setDeviceFixing(deviceTimingX);
		setDeviceFixingDuration(deviceFixingDuration);
		setDeviceFixingStart(deviceFixingStart);
		setDeviceFixingEnd(deviceFixingEnd);

	}

	private Projection getWindow2D() {
		return getLayer().getHost().getProjection();
	}

}
