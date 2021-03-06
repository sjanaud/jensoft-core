/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.stock.geom;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.function.source.SourceFunction;
import org.jensoft.core.plugin.function.source.UserSourceFunction;
import org.jensoft.core.plugin.stock.Stock;

/**
 * <code>FixingStockGeom</code> defines a curve geometry for a set of stock
 * items.
 * <p>
 * close value a the fixing time.
 * </p>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class FixingStockGeom extends CurveStockGeom {

	/**
	 * create stock fixing geometry
	 */
	public FixingStockGeom() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jensoft.core.plugin.stock.geom.StockGeometry#solveGeometry()
	 */
	@Override
	public void solveGeometry() {
		List<Point2D> fixingPoints = new ArrayList<Point2D>();

		for (StockItemGeometry geometry : getStockItemGeometries()) {
			// not need solve path function solve itself based on source
			// function
			// geometry.solveGeometry();

			Stock s = geometry.getStock();
			fixingPoints.add(new Point2D.Double(new Long(s.getFixing().getTime()).doubleValue(), s.getClose()));

		}

		SourceFunction sourceFunction = new UserSourceFunction.LineSource(fixingPoints);
		getPathFunction().setSource(sourceFunction);
	}

}
