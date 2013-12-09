package com.jensoft.core.plugin.stock.geom;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.function.MetricsPathFunction;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.plugin.function.source.UserSourceFunction;
import com.jensoft.core.plugin.stock.Stock;


public class CurveStockGeom extends StockGroupGeometry {

	
	
	/** source function */
	private SourceFunction lowSourceFunction;
	private SourceFunction highSourceFunction;
	private SourceFunction openSourceFunction;
	private SourceFunction closeSourceFunction;

	/** curve metrics path */
	private MetricsPathFunction lowPathFunction;
	private MetricsPathFunction highPathFunction;
	private MetricsPathFunction openPathFunction;
	private MetricsPathFunction closePathFunction;

	/**
	 * create stock group geometry
	 */
	public CurveStockGeom() {
		lowPathFunction = new MetricsPathFunction();
		highPathFunction = new MetricsPathFunction();
		openPathFunction = new MetricsPathFunction();
		closePathFunction = new MetricsPathFunction();
	}

	public MetricsPathFunction getLowPathFunction() {
		return lowPathFunction;
	}

	public MetricsPathFunction getHighPathFunction() {
		return highPathFunction;
	}

	public MetricsPathFunction getOpenPathFunction() {
		return openPathFunction;
	}

	public MetricsPathFunction getClosePathFunction() {
		return closePathFunction;
	}



	@Override
	public void solveGeometry() {
		List<Point2D> lowPoints = new ArrayList<Point2D>();
		List<Point2D> highPoints = new ArrayList<Point2D>();
		List<Point2D> openPoints = new ArrayList<Point2D>();
		List<Point2D> closePoints = new ArrayList<Point2D>();

		for (StockItemGeometry geometry : getStockItemGeometries()) {
			//not need solve path function solve itself based on source function
			// geometry.solveGeometry();
			
			Stock s = geometry.getStock();
			lowPoints.add(new Point2D.Double(s.getFixing().getTime(), s.getLow()));
			highPoints.add(new Point2D.Double(s.getFixing().getTime(), s.getHigh()));
			openPoints.add(new Point2D.Double(s.getFixing().getTime(), s.getOpen()));
			closePoints.add(new Point2D.Double(s.getFixing().getTime(), s.getClose()));
		}

		lowSourceFunction = new UserSourceFunction.LineSource(lowPoints);
		lowPathFunction.setSource(lowSourceFunction);

		highSourceFunction = new UserSourceFunction.LineSource(highPoints);
		highPathFunction.setSource(highSourceFunction);

		openSourceFunction = new UserSourceFunction.LineSource(openPoints);
		openPathFunction.setSource(openSourceFunction);

		closeSourceFunction = new UserSourceFunction.LineSource(closePoints);
		closePathFunction.setSource(closeSourceFunction);
	}

}
