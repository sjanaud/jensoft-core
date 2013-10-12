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
 * Between each pair of control points there is a cubic curve. To make sure that
 * curves join together smoothly, the first and second derivative at the end of
 * one curve must equal the the first and second derivative start of the next
 * one. Computing the natural cubic spline essentially involves solving a system
 * of simultaneous equations to make sure this happens. It is also possible to
 * create a closed natural cubic spline. Unfortunately, while the curve is
 * mathematically smooth, it can wriggle in quite unexpected ways (try moving
 * one control point close to another one in the applet above). Furthermore, we
 * do not have local control - moving one control point cause the entire curve
 * to change, not just the part near the control point.
 * 
 * @author Sebastien Janaud
 */
public class NatCubic extends AbstractPlot {

	private final int STEPS = 12;

	/*
	 * calculates the natural cubic spline that interpolates y[0], y[1], ...
	 * y[n] The first segment is returned as C[0].a + C[0].b*u + C[0].c*u^2 +
	 * C[0].d*u^3 0<=u <1 the other segments are in C[1], C[2], ... C[n-1]
	 */

	Cubic[] calcNaturalCubic(int n, double[] x) {
		double[] gamma = new double[n + 1];
		double[] delta = new double[n + 1];
		double[] D = new double[n + 1];
		int i;
		/*
		 * We solve the equation [2 1 ] [D[0]] [3(x[1] - x[0]) ] |1 4 1 | |D[1]|
		 * |3(x[2] - x[0]) | | 1 4 1 | | . | = | . | | ..... | | . | | . | | 1 4
		 * 1| | . | |3(x[n] - x[n-2])| [ 1 2] [D[n]] [3(x[n] - x[n-1])] by using
		 * row operations to convert the matrix to upper triangular and then
		 * back sustitution. The D[i] are the derivatives at the knots.
		 */

		gamma[0] = 1.0f / 2.0f;
		for (i = 1; i < n; i++) {
			gamma[i] = 1 / (4 - gamma[i - 1]);
		}
		gamma[n] = 1 / (2 - gamma[n - 1]);

		delta[0] = 3 * (x[1] - x[0]) * gamma[0];
		for (i = 1; i < n; i++) {
			delta[i] = (3 * (x[i + 1] - x[i - 1]) - delta[i - 1]) * gamma[i];
		}
		delta[n] = (3 * (x[n] - x[n - 1]) - delta[n - 1]) * gamma[n];

		D[n] = delta[n];
		for (i = n - 1; i >= 0; i--) {
			D[i] = delta[i] - gamma[i] * D[i + 1];
		}

		/* now compute the coefficients of the cubics */
		Cubic[] C = new Cubic[n];
		for (i = 0; i < n; i++) {
			C[i] = new Cubic(x[i], D[i], 3 * (x[i + 1] - x[i]) - 2 * D[i] - D[i + 1], 2 * (x[i] - x[i + 1]) + D[i] + D[i + 1]);
		}
		return C;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.plot.spline.AbstractPlot#solvePlot()
	 */
	@Override
	public void solvePlot() {
		GeneralPath path = new GeneralPath();
		List<Point2D> devicePoints = new ArrayList<Point2D>();
		Window2D w2d = getHost().getWindow2D();
		if (getUserPoints().size() >= 2) {

			double[] xvals = new double[getUserPoints().size()];
			double[] yvals = new double[getUserPoints().size()];
			int count = 0;
			for (Point2D p : getUserPoints()) {
				xvals[count] = p.getX();
				yvals[count] = p.getY();
				count++;
			}

			Cubic[] X = calcNaturalCubic(getUserPoints().size() - 1, xvals);
			Cubic[] Y = calcNaturalCubic(getUserPoints().size() - 1, yvals);

			/*
			 * very crude technique - just break each segment up into steps
			 * lines
			 */
			double x = w2d.userToPixelX(X[0].eval(0));
			double y = w2d.userToPixelY(Y[0].eval(0));

			path.moveTo(x, y);
			devicePoints.add(new Point2D.Double(x, y));

			for (int i = 0; i < X.length; i++) {
				for (int j = 1; j <= STEPS; j++) {
					double u = j / (double) STEPS;
					x = w2d.userToPixelX(X[i].eval(u));
					y = w2d.userToPixelY(Y[i].eval(u));
					devicePoints.add(new Point2D.Double(x, y));
					path.lineTo(x, y);
				}
			}
		}

		setPlotPath(path);
		setDevicePoints(devicePoints);

	}

}
