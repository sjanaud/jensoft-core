/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stock;

import java.awt.Color;
import java.util.Date;

import com.jensoft.core.palette.NanoChromatique;

/**
 * <code>Stock</code>defines a stock market with low, high, open, close, volume,
 * with fixing and fixing duration values for a fixing date.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class Stock {

	private Color bearishColor = NanoChromatique.RED.brighter();
	private Color bullishColor = NanoChromatique.GREEN.brighter();
	private Color volumeColor = NanoChromatique.BLUE.brighter();

	private Date fixing;
	private long fixingDurationMillis;

	private double open;
	private double close;
	private double low;
	private double high;
	private double volume;

	/**
	 * create empty stock candle
	 */
	public Stock() {
	}

	/**
	 * create stock candle with given stock values
	 * 
	 * @param fixing
	 * @param open
	 * @param close
	 * @param low
	 * @param high
	 * @param volume
	 */
	public Stock(Date fixing, double open, double close, double low, double high, double volume) {
		super();
		this.fixing = fixing;
		this.open = open;
		this.close = close;
		this.low = low;
		this.high = high;
		this.volume = volume;
	}

	public Date getFixing() {
		return fixing;
	}

	public void setFixing(Date fixing) {
		this.fixing = fixing;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public long getFixingDurationMillis() {
		return fixingDurationMillis;
	}

	public void setFixingDurationMillis(long fixingDurationMillis) {
		this.fixingDurationMillis = fixingDurationMillis;
	}

	public boolean isBearish() {
		return (open > close);
	}

	public boolean isBullish() {
		return (close > open);
	}

	public Color getBearishColor() {
		return bearishColor;
	}

	public void setBearishColor(Color bearishColor) {
		this.bearishColor = bearishColor;
	}

	public Color getBullishColor() {
		return bullishColor;
	}

	public void setBullishColor(Color bullishColor) {
		this.bullishColor = bullishColor;
	}

	public Color getVolumeColor() {
		return volumeColor;
	}

	public void setVolumeColor(Color volumeColor) {
		this.volumeColor = volumeColor;
	}

}
