package org.jensoft.core.plugin.stock.geom;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jensoft.core.plugin.function.MetricsPathFunction;
import org.jensoft.core.plugin.function.source.SourceFunction;
import org.jensoft.core.plugin.function.source.UserSourceFunction;
import org.jensoft.core.plugin.stock.Stock;

/**
 * <code>BollingerMovingAverageStockGeom</code> defines a Bollinger band's for a
 * set of stock items.
 * <p>
 * 
 * Assume a 5 bar Bollinger band with 2 Deviations, and assume the last five
 * closes were 25.5, 26.75, 27.0, 26.5, and 27.25.<br>
 * 
 * <br>
 * <br>
 * Calculate the simple moving average: <br>
 * <br>
 * 25.5 + 26.75 + 27.0 + 26.5 + 27.25 = 133.0 <br>
 * <br>
 * 133.0 / 5 = 26.6 <br>
 * <br>
 * Next, for each bar, subtract 26.6 from the close and square this value: <br>
 * <br>
 * 25.5 - 26.6 = -1.1 squared = 1.21<br>
 * <br>
 * 26.75 - 26.6 = 0.15 squared = 0.023<br>
 * <br>
 * 27.0 - 26.6 = 0.4 squared = 0.16<br>
 * <br>
 * 26.5 - 26.6 = 0.1 squared = 0.01<br>
 * <br>
 * 27.25 - 26.6 = 0.65 squared = 0.423<br>
 * <br>
 * <br>
 * Add the above calculated values, divide by 5, and then get the square root of
 * this value to get the deviation value: <br>
 * <br>
 * 1.21 + 0.023 + 0.16 + 0.01 + 0.423 = 1.826 <br>
 * <br>
 * 1.826 / 5 = 0.365 <br>
 * <br>
 * Square root of .365 = 0.604 <br>
 * <br>
 * The upper Bollinger band would be 26.6 + (2 * 0.604) = 27.808 <br>
 * <br>
 * The middle Bollinger band would be 26.6 <br>
 * <br>
 * The lower Bollinger band would be 26.6 - (2 * 0.604) = 25.392 <br>
 * <br>
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
	 * create moving geometry with given average count
	 * 
	 * @param moveCount
	 */
	public BollingerMovingAverageStockGeom(int moveCount) {
		super();
		this.moveCount = moveCount;
		pathFunctionUp = new MetricsPathFunction();
		pathFunctionBottom = new MetricsPathFunction();
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
				squarred = squarred + Math.pow((s.getClose() - movingAverage), 2);
			}
			double deviation = Math.sqrt(squarred / new Double(moveCount).doubleValue());
			upperPoint.add(new Point2D.Double(new Long(root.getFixing().getTime()).doubleValue(), movingAverage + 2 * deviation));
			bottomPoint.add(new Point2D.Double(new Long(root.getFixing().getTime()).doubleValue(), movingAverage - 2 * deviation));
		}

		SourceFunction averageSourceFunction = new UserSourceFunction.LineSource(stockMAs);
		getPathFunction().setSource(averageSourceFunction);

		SourceFunction upperSourceFunction = new UserSourceFunction.LineSource(upperPoint);
		getPathFunctionUp().setSource(upperSourceFunction);

		SourceFunction bottomSourceFunction = new UserSourceFunction.LineSource(bottomPoint);
		getPathFunctionBottom().setSource(bottomSourceFunction);

	}

	/**
	 * get bollinger up curve
	 * 
	 * @return up curve
	 */
	public MetricsPathFunction getPathFunctionUp() {
		return pathFunctionUp;
	}

	/**
	 * get bollinger bottonm curve
	 * 
	 * @return bottom curve
	 */
	public MetricsPathFunction getPathFunctionBottom() {
		return pathFunctionBottom;
	}

}
