package com.jensoft.core.plugin.stock.geom;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.plugin.function.source.UserSourceFunction;
import com.jensoft.core.plugin.stock.Stock;

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
public class MovingAverageStockGeom extends CurveStockGeom {

	/**
	 * create stock fixing geometry
	 */
	public MovingAverageStockGeom() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.stock.geom.StockGeometry#solveGeometry()
	 */
	@Override
	public void solveGeometry() {
		List<Point2D> lowPoints = new ArrayList<Point2D>();

		for (StockItemGeometry geometry : getStockItemGeometries()) {
			// not need solve path function solve itself based on source
			// function
			// geometry.solveGeometry();

			Stock s = geometry.getStock();
			lowPoints.add(new Point2D.Double(s.getFixing().getTime(), s.getLow()));

		}

		SourceFunction sourceFunction = new UserSourceFunction.LineSource(lowPoints);
		getPathFunction().setSource(sourceFunction);
	}

}
