/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.stock;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.stock.geom.BollingerMovingAverageStockGeom;
import org.jensoft.core.plugin.stock.geom.CandleStickGeom;
import org.jensoft.core.plugin.stock.geom.CurveStockGeom;
import org.jensoft.core.plugin.stock.geom.ExponentialMovingAverageStockGeom;
import org.jensoft.core.plugin.stock.geom.FixingStockGeom;
import org.jensoft.core.plugin.stock.geom.MovingAverageStockGeom;
import org.jensoft.core.plugin.stock.geom.OhlcGeom;
import org.jensoft.core.plugin.stock.geom.StockGeometry;
import org.jensoft.core.plugin.stock.geom.StockItemGeometry;
import org.jensoft.core.plugin.stock.geom.VolumeBarGeometry;
import org.jensoft.core.plugin.stock.geom.WeightedMovingAverageStockGeom;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>StockLayer</code> defines an abstract representation of stocks values.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public abstract class StockLayer<G extends StockGeometry> {

	/** layer host plug in */
	private StockPlugin host;

	/** geometry collection that reflect this layer */
	private List<G> geometries = new ArrayList<G>();

	/**
	 * create abstract stock layer
	 */
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
		 * @see org.jensoft.core.plugin.stock.StockLayer#solveLayer()
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

	
		/* (non-Javadoc)
		 * @see org.jensoft.core.plugin.stock.StockLayer#paintLayer(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
		 */
		@Override
		protected void paintLayer(View view, Graphics2D g2d, ViewPart viewPart) {
			if (viewPart == ViewPart.Device) {
				for (CandleStickGeom geom : getGeometries()) {
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
		 * @see org.jensoft.core.plugin.stock.StockLayer#solveLayer()
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

	
		/* (non-Javadoc)
		 * @see org.jensoft.core.plugin.stock.StockLayer#paintLayer(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
		 */
		@Override
		protected void paintLayer(View view, Graphics2D g2d, ViewPart viewPart) {
			if (viewPart == ViewPart.Device) {
				for (OhlcGeom geom : getGeometries()) {
					g2d.setColor(geom.getLowHighColor());
					g2d.draw(geom.getDeviceLowHighGap());

					g2d.draw(geom.getDeviceOpenTick());
					g2d.draw(geom.getDeviceCloseTick());
				}
			}

		}

	}

	/***
	 * define volume layer for stocks
	 * 
	 */
	public static class Volume extends StockLayer<VolumeBarGeometry> {

		private Color volumeFillColor = NanoChromatique.BLUE;

		public enum VolumeType {
			Curve, Area, Bar,
		}

		/**
		 * create default stock volume layer
		 */
		public Volume() {
		}

		/**
		 * create stock volume layer with fill color
		 * @param volumeFillColor
		 */
		public Volume(Color volumeFillColor) {
			super();
			this.volumeFillColor = volumeFillColor;
		}

		/**
		 * get volume fill color
		 * 
		 * @return fill color
		 */
		public Color getVolumeFillColor() {
			return volumeFillColor;
		}

		/**
		 * set volume fill color
		 * 
		 * @param volumeFillColor
		 */
		public void setVolumeFillColor(Color volumeFillColor) {
			this.volumeFillColor = volumeFillColor;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jensoft.core.plugin.stock.StockLayer#solveLayer()
		 */
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

		/* (non-Javadoc)
		 * @see org.jensoft.core.plugin.stock.StockLayer#paintLayer(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
		 */
		@Override
		protected void paintLayer(View v2d, Graphics2D g2d, ViewPart viewPart) {
			if (viewPart == ViewPart.Device) {
				for (VolumeBarGeometry geom : getGeometries()) {
					g2d.setColor(volumeFillColor);
					g2d.fill(geom.getDeviceVolumeGap());
				}
			}
		}

	}

	/***
	 * define abstract curve layer that shows stock line.
	 * 
	 */
	public static abstract class Curve<C extends CurveStockGeom> extends StockLayer<CurveStockGeom> {

		private Color curveColor = NanoChromatique.BLUE;

		/**
		 * create curve layer
		 */
		public Curve() {
		}

		/**
		 * create stocks curve with given color
		 * 
		 * @param curveColor
		 */
		public Curve(Color curveColor) {
			super();
			this.curveColor = curveColor;
		}

		/**
		 * get curve color
		 * 
		 * @return curve color
		 */
		public Color getCurveColor() {
			return curveColor;
		}

		/**
		 * set curve color
		 * 
		 * @param curveColor
		 *            color to set
		 */
		public void setCurveColor(Color curveColor) {
			this.curveColor = curveColor;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jensoft.core.plugin.stock.StockLayer#solveLayer()
		 */
		@Override
		protected void solveLayer() {
			getGeometries().clear();
			CurveStockGeom geom = getGeomInstance();
			geom.setLayer(this);
			for (Stock stock : getHost().getStocks()) {
				StockItemGeometry itemGeom = new StockItemGeometry();
				itemGeom.setStock(stock);
				itemGeom.setLayer(this);
				geom.addStockItemGeometries(itemGeom);
			}
			geom.solveGeometry();
			addGeometry(geom);
		}

		/**
		 * return a new instance of the generic geometry
		 * 
		 * @return generic geometry
		 */
		protected abstract C getGeomInstance();

		
		/* (non-Javadoc)
		 * @see org.jensoft.core.plugin.stock.StockLayer#paintLayer(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
		 */
		@Override
		protected void paintLayer(View view, Graphics2D g2d, ViewPart viewPart) {
			if (viewPart == ViewPart.Device) {
				for (CurveStockGeom geom : getGeometries()) {

					g2d.setColor(curveColor);

					geom.getPathFunction().setSolveGeometryRequest(true);
					geom.getPathFunction().setProjection(getHost().getProjection());
					geom.getPathFunction().setFontRenderContext(g2d.getFontRenderContext());

					Shape s = geom.getPathFunction().getOrCreateGeometry().getPath();
					g2d.draw(s);
				}
			}
		}

	}

	/**
	 * <code>FixingCurve</code> reflect the close values for stocks fixing times
	 * 
	 */
	public static class FixingCurve extends Curve<FixingStockGeom> {

		public FixingCurve() {
			super();
		}

		public FixingCurve(Color curveColor) {
			super(curveColor);
		}

		@Override
		protected FixingStockGeom getGeomInstance() {
			return new FixingStockGeom();
		}

	}

	/**
	 * <code>MovingAverageCurve</code> reflect the moving average for stocks
	 * 
	 */
	public static class MovingAverageCurve extends Curve<MovingAverageStockGeom> {

		/** moving average count */
		private int moveCount = 20;

		/**
		 * create moving average with given move counts
		 * 
		 * @param moveCount
		 */
		public MovingAverageCurve(int moveCount) {
			super();
			this.moveCount = moveCount;
		}

		/**
		 * create moving average layer with color and move count
		 * 
		 * @param curveColor
		 * @param moveCount
		 */
		public MovingAverageCurve(Color curveColor, int moveCount) {
			super(curveColor);
			this.moveCount = moveCount;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jensoft.core.plugin.stock.StockLayer.Curve#getGeomInstance()
		 */
		@Override
		protected MovingAverageStockGeom getGeomInstance() {
			return new MovingAverageStockGeom(moveCount);
		}

	}

	/**
	 * <code>WeightedMovingAverageCurve</code> reflect the moving average for
	 * stocks
	 * 
	 */
	public static class WeightedMovingAverageCurve extends Curve<WeightedMovingAverageStockGeom> {

		/** moving average count */
		private int moveCount = 20;

		/**
		 * create moving average with given move counts
		 * 
		 * @param moveCount
		 */
		public WeightedMovingAverageCurve(int moveCount) {
			super();
			this.moveCount = moveCount;
		}

		/**
		 * create moving average layer with color and move count
		 * 
		 * @param curveColor
		 * @param moveCount
		 */
		public WeightedMovingAverageCurve(Color curveColor, int moveCount) {
			super(curveColor);
			this.moveCount = moveCount;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jensoft.core.plugin.stock.StockLayer.Curve#getGeomInstance()
		 */
		@Override
		protected WeightedMovingAverageStockGeom getGeomInstance() {
			return new WeightedMovingAverageStockGeom(moveCount);
		}

	}

	/**
	 * <code>ExponentialdMovingAverageCurve</code> reflect the moving average
	 * for stocks
	 * 
	 */
	public static class ExponentialdMovingAverageCurve extends Curve<ExponentialMovingAverageStockGeom> {

		/** moving average count */
		private int moveCount = 20;

		/**
		 * create moving average with given move counts
		 * 
		 * @param moveCount
		 */
		public ExponentialdMovingAverageCurve(int moveCount) {
			super();
			this.moveCount = moveCount;
		}

		/**
		 * create moving average layer with color and move count
		 * 
		 * @param curveColor
		 * @param moveCount
		 */
		public ExponentialdMovingAverageCurve(Color curveColor, int moveCount) {
			super(curveColor);
			this.moveCount = moveCount;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jensoft.core.plugin.stock.StockLayer.Curve#getGeomInstance()
		 */
		@Override
		protected ExponentialMovingAverageStockGeom getGeomInstance() {
			return new ExponentialMovingAverageStockGeom(moveCount);
		}

	}

	/**
	 * <code>ExponentialdMovingAverageCurve</code> reflect the moving average
	 * for stocks
	 * 
	 */
	public static class BollingerBands extends Curve<BollingerMovingAverageStockGeom> {

		/** moving average count */
		private int moveCount = 20;

		private Color bollingerUpLineColor = RosePalette.INDIGO.brighter();
		private Color bollingerBottomLineColor = RosePalette.INDIGO.brighter();

		/**
		 * create Bollinger with given move counts and defaults colors
		 * 
		 * @param moveCount
		 */
		public BollingerBands(int moveCount) {
			super();
			this.moveCount = moveCount;
		}

		/**
		 * create Bollinger layer with color for median curve with is a simple
		 * moving average and move count. defaults colors for upper and lower
		 * curves
		 * 
		 * @param curveColor
		 * @param moveCount
		 */
		public BollingerBands(Color curveColor, int moveCount) {
			super(curveColor);
			this.moveCount = moveCount;
		}

		/**
		 * create Bollinger layer with colors and move count
		 * 
		 * @param moveCount
		 * @param curveColor
		 * @param bollingerUpLineColor
		 * @param bollingerBottomLineColor
		 */
		public BollingerBands(int moveCount, Color curveColor, Color bollingerUpLineColor, Color bollingerBottomLineColor) {
			super(curveColor);
			this.moveCount = moveCount;
			this.bollingerUpLineColor = bollingerUpLineColor;
			this.bollingerBottomLineColor = bollingerBottomLineColor;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.jensoft.core.plugin.stock.StockLayer.Curve#getGeomInstance()
		 */
		@Override
		protected BollingerMovingAverageStockGeom getGeomInstance() {
			return new BollingerMovingAverageStockGeom(moveCount);
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.plugin.stock.StockLayer.Curve#paintLayer(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
		 */
		@Override
		protected void paintLayer(View view, Graphics2D g2d, ViewPart viewPart) {
			super.paintLayer(view, g2d, viewPart);
			if (viewPart == ViewPart.Device) {

				for (CurveStockGeom geom : getGeometries()) {

					BollingerMovingAverageStockGeom bollingerGeom = (BollingerMovingAverageStockGeom) geom;

					bollingerGeom.getPathFunctionUp().setSolveGeometryRequest(true);
					bollingerGeom.getPathFunctionUp().setProjection(getHost().getProjection());
					bollingerGeom.getPathFunctionUp().setFontRenderContext(g2d.getFontRenderContext());

					bollingerGeom.getPathFunctionBottom().setSolveGeometryRequest(true);
					bollingerGeom.getPathFunctionBottom().setProjection(getHost().getProjection());
					bollingerGeom.getPathFunctionBottom().setFontRenderContext(g2d.getFontRenderContext());

					g2d.setColor(bollingerUpLineColor);
					Shape upFonction = bollingerGeom.getPathFunctionUp().getOrCreateGeometry().getPath();
					g2d.draw(upFonction);

					g2d.setColor(bollingerBottomLineColor);
					Shape bottomFonction = bollingerGeom.getPathFunctionBottom().getOrCreateGeometry().getPath();
					g2d.draw(bottomFonction);
				}
			}
		}

	}

	/**
	 * solve layer geometry.
	 * <p>
	 * process projection of stock values from user system coordinates to device
	 * pixel system coordinates and create geometry collection.
	 * </p>
	 */
	protected abstract void solveLayer();

	/**
	 * paint stock layer
	 * 
	 * @param v2d
	 *            view
	 * @param g2d
	 *            graphics context
	 * @param viewPart
	 *            part to paint
	 */
	protected abstract void paintLayer(View v2d, Graphics2D g2d, ViewPart viewPart);

}
