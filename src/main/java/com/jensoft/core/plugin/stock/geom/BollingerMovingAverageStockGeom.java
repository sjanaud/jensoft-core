package com.jensoft.core.plugin.stock.geom;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jensoft.core.plugin.function.MetricsPathFunction;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.plugin.function.source.UserSourceFunction;
import com.jensoft.core.plugin.stock.Stock;
import com.jensoft.core.plugin.stock.geom.MovingAverageStockGeom.StockComparator;

/**
 * <code>BollingerMovingAverageStockGeom</code> defines a Bollinger stripe
 * for a set of stock items.
 * <p>
 * move count property define the moving average session count.
 * </p>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class BollingerMovingAverageStockGeom extends CurveStockGeom {

	/** up bollinger curve metrics path */
	private MetricsPathFunction pathFunctionUp;
	
	/** bottom bollinger curve metrics path */
	private MetricsPathFunction pathFunctionBottom;
	
	/** move count */
	private int moveCount = 20;

	/**
	 * create stock fixing geometry
	 */
	public BollingerMovingAverageStockGeom() {
	}

	/**
	 * create moving geometry with given average count
	 * @param moveCount
	 */
	public BollingerMovingAverageStockGeom(int moveCount) {
		super();
		this.moveCount = moveCount;
	}

	/**
	 * get the move count for this average
	 * 
	 * @return move count
	 */
	public int getMoveCount() {
		return moveCount;
	}

	public class StockComparator implements Comparator<Stock> {

		public int compare(Stock s1, Stock s2) {
			return s1.getFixing().compareTo(s2.getFixing());
		}
	}

	/**
	 * set move count for averge
	 * 
	 * @param moveCount
	 */
	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.stock.geom.StockGeometry#solveGeometry()
	 */
	@Override
	public void solveGeometry() {
		
		
		
		//simple moving average on stocks for rank n.
		List<Point2D> upperPoint = new ArrayList<Point2D>();
		List<Point2D> bottomPoint = new ArrayList<Point2D>();
		List<Point2D> stockMAs = new ArrayList<Point2D>();
		List<Stock> stocks = getLayer().getHost().getStocks();
		Collections.sort(stocks, new StockComparator());
		
		for (int i = moveCount; i < stocks.size(); i++) {
			Stock root = stocks.get(i);
			
			double sum = 0;
			for (int j = 0; j < moveCount; j++) {
				Stock s = stocks.get(i - j);
				sum = sum + s.getClose();
			}
			double movingAverage = sum / moveCount;
			stockMAs.add(new Point2D.Double(new Long(root.getFixing().getTime()).doubleValue(), movingAverage));
			
			double squarred = 0;
			for (int j = 0; j < moveCount; j++) {
				Stock s = stocks.get(i - j);
				squarred = squarred + Math.pow((s.getClose()-movingAverage), 2);
			}
			double deviation = Math.floor(squarred/moveCount);
			upperPoint.add(new Point2D.Double(new Long(root.getFixing().getTime()).doubleValue(), movingAverage + 2*deviation));
			bottomPoint.add(new Point2D.Double(new Long(root.getFixing().getTime()).doubleValue(), movingAverage-2*deviation));
		}
		
		SourceFunction averageSourceFunction = new UserSourceFunction.LineSource(stockMAs);
		getPathFunction().setSource(averageSourceFunction);
		
		SourceFunction upperSourceFunction = new UserSourceFunction.LineSource(upperPoint);
		getPathFunctionUp().setSource(upperSourceFunction);
		

		SourceFunction bottomSourceFunction = new UserSourceFunction.LineSource(bottomPoint);
		getPathFunctionUp().setSource(bottomSourceFunction);
		
	}
	
	

	/**
	 * get bollinger up curve
	 * @return up curve
	 */
	public MetricsPathFunction getPathFunctionUp() {
		return pathFunctionUp;
	}

	/**
	 * get bollinger bottonm curve
	 * @return bottom curve
	 */
	public MetricsPathFunction getPathFunctionBottom() {
		return pathFunctionBottom;
	}
	
	

}
