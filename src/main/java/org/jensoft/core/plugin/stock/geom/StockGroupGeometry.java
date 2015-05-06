/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.stock.geom;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>StockGroupGeometry</code> defines a geometry for a group of stocks.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public abstract class StockGroupGeometry extends StockGeometry {

	/** stock primitive geometries */
	private List<StockItemGeometry> stockItemGeometries = new ArrayList<StockItemGeometry>();


	public void setStockItemGeometries(List<StockItemGeometry> stockItemGeometries) {
		this.stockItemGeometries = stockItemGeometries;
	}

	public void addStockItemGeometries(StockItemGeometry stockItemGeometry) {
		this.stockItemGeometries.add(stockItemGeometry);
	}

	public List<StockItemGeometry> getStockItemGeometries() {
		return stockItemGeometries;
	}


}
