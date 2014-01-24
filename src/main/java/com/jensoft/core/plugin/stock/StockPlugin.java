/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stock;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code></code> Stock plug-in takes the responsibility to paint commons stock
 * charts
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class StockPlugin extends AbstractPlugin {

	/** stock items */
	protected List<Stock> stocks = new ArrayList<Stock>();

	/** stock layers */
	private List<StockLayer<?>> stockLayers = new ArrayList<StockLayer<?>>();

	/**
	 * create stock plug-in
	 */
	public StockPlugin() {
		setAntialiasing(Antialiasing.On);
		setTextAntialising(TextAntialiasing.On);
		setDithering(Dithering.On);
		setPriority(100);
	}

	/**
	 * get the registered stock layers
	 * 
	 * @return layers
	 */
	public List<StockLayer<?>> getLayers() {
		return stockLayers;
	}

	/**
	 * set stock layers
	 * 
	 * @param layers
	 *            layers to set
	 */
	public void setLayers(List<StockLayer<?>> layers) {
		for (int i = 0; i < layers.size(); i++) {
			layers.get(i).setHost(this);
		}
		this.stockLayers = layers;
	}

	/**
	 * add the given stock layer
	 * 
	 * @param layer
	 *            the layer to add
	 * 
	 */
	public void addLayer(StockLayer<?> layer) {
		layer.setHost(this);
		stockLayers.add(layer);
	}

	/**
	 * add array of stock layer
	 * 
	 * @param layers
	 *            layers to add
	 */
	public void addLayer(StockLayer<?>... layers) {
		for (int i = 0; i < layers.length; i++) {
			layers[i].setHost(this);
			stockLayers.add(layers[i]);
		}
	}

	/**
	 * get stock items
	 * 
	 * @return stocks
	 */
	public List<Stock> getStocks() {
		return stocks;
	}

	/**
	 * set stock items
	 * 
	 * @param stocks
	 *            the stocks items to add
	 */
	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	/**
	 * add stock item
	 * 
	 * @param stock
	 */
	public void addStocks(Stock stock) {
		this.stocks.add(stock);
	}

	/**
	 * add array of stocks
	 * 
	 * @param stocks
	 *            stocks to add
	 */
	public void addStock(Stock... stocks) {
		for (int i = 0; i < stocks.length; i++) {
			this.stocks.add(stocks[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view
	 * .View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
	 */
	@Override
	protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
		//System.out.println("paint layer "+windowPart.name());
		for (StockLayer<?> layer : stockLayers) {
			layer.solveLayer();
			layer.paintLayer(v2d, g2d, windowPart);
		}

	}

}
