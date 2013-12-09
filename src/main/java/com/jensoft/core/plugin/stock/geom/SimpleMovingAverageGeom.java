package com.jensoft.core.plugin.stock.geom;


/**
 * <code>MovingAverageGeom</code> defines a curve geometry that reflects a
 * moving average for a set of stock items.
 * 
 * @author sebastien janaud
 * 
 */
public class SimpleMovingAverageGeom extends CurveStockGeom {

	/**
	 * create stock simple moving average geometry
	 */
	public SimpleMovingAverageGeom() {
	}

	@Override
	public void solveGeometry() {
//		List<Point2D> lowPoints = new ArrayList<Point2D>();
//		for (StockItemGeometry geometry : getStockItemGeometries()) {
//			// not need solve path function solve itself based on source
//			// function
//			// geometry.solveGeometry();
//
//			Stock s = geometry.getStock();
//			lowPoints.add(new Point2D.Double(s.getFixing().getTime(), s.getLow()));
//
//		}
//		getPathFunction().setSource(new UserSourceFunction.LineSource(lowPoints));
		
		//compute points
	}

}
