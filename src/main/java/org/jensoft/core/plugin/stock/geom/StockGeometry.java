/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.stock.geom;

import org.jensoft.core.plugin.stock.StockLayer;

public abstract class StockGeometry {

	private StockLayer<?> layer;

	public StockGeometry() {
	}

	public StockLayer<?> getLayer() {
		return layer;
	}

	public void setLayer(StockLayer<?> layer) {
		this.layer = layer;
	}

	public abstract void solveGeometry();

}
