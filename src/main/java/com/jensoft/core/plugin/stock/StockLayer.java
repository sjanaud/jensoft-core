/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stock;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.stock.geom.CandleStickGeom;
import com.jensoft.core.plugin.stock.geom.OhlcGeom;
import com.jensoft.core.plugin.stock.geom.StockGeometry;
import com.jensoft.core.plugin.stock.geom.VolumeBarGeometry;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>StockLayer</code> defines an abstract representation of stocks items
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public abstract class StockLayer<G extends StockGeometry> {

	private StockPlugin host;
	private List<G> geometries = new ArrayList<G>();

	public StockLayer() {
	}

	/**
	 * get the layer plug in host
	 * 
	 * @return host
	 */
	public StockPlugin getHost() {
		return host;
	}

	/**
	 * set the plug in host for this stock layer
	 * 
	 * @param host
	 */
	public void setHost(StockPlugin host) {
		this.host = host;
	}

	/**
	 * get stock geometries of this layer
	 * 
	 * @return geometries
	 */
	public List<G> getGeometries() {
		return geometries;
	}

	/**
	 * set geometries of this layer
	 * 
	 * @param geometries
	 */
	public void setGeometries(List<G> geometries) {
		this.geometries = geometries;
	}

	/**
	 * add stock geometry in this layer
	 * 
	 * @param geometries
	 */
	public void addGeometry(G geometry) {
		this.geometries.add(geometry);
	}

	/**
	 * define candle stick trend layer for the stocks items
	 * 
	 */
	public static class CandleStick extends StockLayer<CandleStickGeom> {

		/**
		 * create default candle trend type
		 */
		public CandleStick() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.plugin.stock.StockLayer#solveLayer()
		 */
		@Override
		protected void solveLayer() {
			getGeometries().clear();
			for (Stock stock : getHost().getStocks()) {
				CandleStickGeom geom = new CandleStickGeom();
				geom.setLayer(this);
				geom.setStock(stock);
				geom.solveGeometry();
				addGeometry(geom);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.stock.StockLayer#paintLayer(com.jensoft.core
		 * .view.View2D, java.awt.Graphics2D,
		 * com.jensoft.core.window.WindowPart)
		 */
		@Override
		protected void paintLayer(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
			if (windowPart == WindowPart.Device) {
				for(CandleStickGeom geom : getGeometries()){
					g2d.setColor(geom.getLowHighColor());
					g2d.draw(geom.getDeviceLowHighGap());
					
					if (geom.getStock().isBearish()) {
						g2d.setColor(geom.getStock().getBearishColor());
					} else {
						g2d.setColor(geom.getStock().getBullishColor());
					}
					g2d.fill(geom.getDeviceLowOpenCloseGap());
				}
			}

		}

	}
	
	

	/**
	 * define OHLC trend layer for the stocks items
	 * 
	 */
	public static class Ohlc extends StockLayer<OhlcGeom> {

		/**
		 * create ohlc trend type
		 */
		public Ohlc() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.plugin.stock.StockLayer#solveLayer()
		 */
		@Override
		protected void solveLayer() {
			getGeometries().clear();
			for (Stock stock : getHost().getStocks()) {
				OhlcGeom geom = new OhlcGeom();
				geom.setLayer(this);
				geom.setStock(stock);
				geom.solveGeometry();
				addGeometry(geom);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.stock.StockLayer#paintLayer(com.jensoft.core
		 * .view.View2D, java.awt.Graphics2D,
		 * com.jensoft.core.window.WindowPart)
		 */
		@Override
		protected void paintLayer(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
			if (windowPart == WindowPart.Device) {
				for(OhlcGeom geom : getGeometries()){
					g2d.setColor(geom.getLowHighColor());
					g2d.draw(geom.getDeviceLowHighGap());
					
					g2d.draw(geom.getDeviceOpenTick());
					g2d.draw(geom.getDeviceCloseTick());
				}
			}

		}

	}

	public static class Volume extends StockLayer<VolumeBarGeometry> {

		public enum VolumeType {
			Curve, Area, Bar,
		}

		public Volume() {
		}

		@Override
		protected void solveLayer() {
			getGeometries().clear();
			for (Stock stock : getHost().getStocks()) {
				VolumeBarGeometry geom = new VolumeBarGeometry();
				geom.setStock(stock);
				geom.setLayer(this);
				geom.solveGeometry();
				addGeometry(geom);
			}
		}

		@Override
		protected void paintLayer(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
			if (windowPart == WindowPart.Device) {
				for(VolumeBarGeometry geom : getGeometries()){
					g2d.setColor(geom.getStock().getVolumeColor());
					g2d.fill(geom.getDeviceVolumeGap());
				}
			}
		}

	}

	/**
	 * solve projection of stock values from user system coordinates to device
	 * pixel system coordinates
	 */
	protected abstract void solveLayer();

	/**
	 * paint this stock layer
	 * 
	 * @param v2d
	 * @param g2d
	 * @param windowPart
	 */
	protected abstract void paintLayer(View2D v2d, Graphics2D g2d, WindowPart windowPart);

}
