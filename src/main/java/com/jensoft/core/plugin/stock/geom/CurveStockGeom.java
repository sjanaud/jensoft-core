/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stock.geom;

import com.jensoft.core.plugin.function.MetricsPathFunction;

/**
 * <code>CurveStockGeom</code> defines a abstract curve geometry for a set of
 * stock items.
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public abstract class CurveStockGeom extends StockGroupGeometry {

	/** curve metrics path */
	private MetricsPathFunction pathFunction;

	/**
	 * create stock group geometry
	 */
	public CurveStockGeom() {
		pathFunction = new MetricsPathFunction();
	}

	/**
	 * get path function for this given stock group
	 * 
	 * @return metrics path function
	 */
	public MetricsPathFunction getPathFunction() {
		return pathFunction;
	}

}
