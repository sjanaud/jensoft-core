/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.plot.spline;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>Bezier</code>
 * 
 * @author sebastien janaud
 * 
 */
public class Bezier extends AbstractPlot {

	/**step interpolation*/
	private final int STEPS = 10;

	
	/**
	 * the basis function for a Bezier spline
	 * @param i
	 * @param t
	 * @return coefficient
	 */
	private double b(int i, double t) {
		switch (i) {
		case 0:
			return (1 - t) * (1 - t) * (1 - t);
		case 1:
			return 3 * t * (1 - t) * (1 - t);
		case 2:
			return 3 * t * t * (1 - t);
		case 3:
			return t * t * t;
		}
		return 0; // we only get here if an invalid i is specified
	}

	/**
	 * evaluate a point on the B spline
	 * 
	 * @param i
	 * @param t
	 * @return
	 */
	private Point2D p(int i, double t) {
		double px = 0;
		double py = 0;
		for (int j = 0; j <= 3; j++) {
			px += b(j, t) * getUserPoints().get(i + j).getX();
			py += b(j, t) * getUserPoints().get(i + j).getY();
		}
		return new Point2D.Double(px, py);
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.plot.spline.AbstractPlot#solvePlot()
	 */
	@Override
	public void solvePlot() {
		GeneralPath path = new GeneralPath();
		List<Point2D> devicePoints = new ArrayList<Point2D>();
		Point2D q = p(0, 0);
		Point2D uq = getHost().getWindow2D().userToPixel(q);
		devicePoints.add(uq);
		path.moveTo(uq.getX(), uq.getY());
		for (int i = 0; i < getUserPoints().size() - 3; i += 3) {
			for (int j = 1; j <= STEPS; j++) {
				q = p(i, j / (float) STEPS);
				uq = getHost().getWindow2D().userToPixel(q);				
				devicePoints.add(uq);
				path.lineTo(uq.getX(), uq.getY());
			}
		}
		
		setPlotPath(path);
		setDevicePoints(devicePoints);
	}

}
