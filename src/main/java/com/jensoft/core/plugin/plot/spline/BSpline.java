/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.plot.spline;

import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.window.Window2D;

/**
 * <code>BSpline</code>
 * 
 * @author Sebastien Janaud
 * 
 */
public class BSpline extends AbstractPlot {

	private final int STEPS = 40;

	// the basis function for a cubic B spline
	float b(int i, float t) {
		switch (i) {
		case -2:
			return (((-t + 3) * t - 3) * t + 1) / 6;
		case -1:
			return ((3 * t - 6) * t * t + 4) / 6;
		case 0:
			return (((-3 * t + 3) * t + 3) * t + 1) / 6;
		case 1:
			return t * t * t / 6;
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
	private Point2D p(int i, float t) {
		float px = 0;
		float py = 0;
		for (int j = -2; j <= 1; j++) {
			px += b(j, t) * getUserPoints().get(i + j).getX();
			py += b(j, t) * getUserPoints().get(i + j).getY();
		}
		return new Point2D.Double(px, py);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.plot.spline.AbstractPlot#solvePlot()
	 */
	@Override
	public void solvePlot() {
		Window2D w2d = getHost().getWindow2D();
		
		GeneralPath path = new GeneralPath();
		List<Point2D> devicePoints = new ArrayList<Point2D>();

		Point2D q = p(2, 0);
		
		Point2D moveTo = w2d.userToPixel(q);
		devicePoints.add(moveTo);
		path.moveTo(moveTo.getX(), moveTo.getY());
		for (int i = 2; i < getUserPoints().size() - 1; i++) {
			for (int j = 1; j <= STEPS; j++) {
				q = p(i, j / (float) STEPS);
				Point2D up = w2d.userToPixel(q);
				devicePoints.add(up);
				path.lineTo(up.getX(), up.getY());
			}
		}

		setPlotPath(path);
		setDevicePoints(devicePoints);
	}

}