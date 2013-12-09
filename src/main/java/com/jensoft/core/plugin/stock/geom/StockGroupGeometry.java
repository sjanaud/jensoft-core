package com.jensoft.core.plugin.stock.geom;

import java.util.ArrayList;
import java.util.List;

public abstract class StockGroupGeometry extends StockGeometry{

	private List<StockItemGeometry> stockItemGeometries = new ArrayList<StockItemGeometry>();
	
	public StockGroupGeometry() {
	}

	public List<StockItemGeometry> getStockItemGeometries() {
		return stockItemGeometries;
	}

	public void setStockItemGeometries(List<StockItemGeometry> stockItemGeometries) {
		this.stockItemGeometries = stockItemGeometries;
	}
	
	public void addStockItemGeometries(List<StockItemGeometry> stockItemGeometries) {
		this.stockItemGeometries = stockItemGeometries;
	}
	
	
	

}
