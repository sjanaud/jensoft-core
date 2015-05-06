/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.plot.spline;

import java.awt.geom.Point2D;

/**
 * <code>BezierG1</code> spline with G1 continuity between control points
 * 
 * @author sebastien janaud
 * 
 */
public class BezierG1 extends Bezier {

	/**
	 * Ensure G1 continuity by forcing control points to be collinear. If 0 1 2
	 * 3 4 5 6 are the the control points, then for the Beziers 0123 and 3456 to
	 * be G1 continuous at their join, 2, 3 and 4 must be collinear
	 */

	private double deltax;
	private double deltay;

	/**
	 * adjust positions of points so that points are collinear
	 * 
	 * @param i
	 */
	private void forceCollinear(int i) {
		if (i % 3 == 0 && i < getUserPoints().size() - 1 && i > 0) { // interpolating
															// control point

			getUserPoints().get(i - 1).setLocation(getUserPoints().get(i - 1).getX() + deltax, getUserPoints().get(i - 1).getY() + deltay);
			getUserPoints().get(i + 1).setLocation(getUserPoints().get(i + 1).getX() + deltax, getUserPoints().get(i + 1).getY() + deltay);

			
		} else if (i % 3 == 1 && i > 1) {
			forceCollinear(i, i - 1, i - 2);
		} else if (i % 3 == 2 && i < getUserPoints().size() - 2) {
			forceCollinear(i, i + 1, i + 2);
		}
	}

	/**
	 * move k such that it is collinear with i and j
	 * 
	 * @param i
	 * @param j
	 * @param k
	 */
	private void forceCollinear(int i, int j, int k) {
		float ij = distance(getUserPoints().get(i).getX(), getUserPoints().get(i).getY(), getUserPoints().get(j).getX(), getUserPoints().get(j).getY());
		float jk = distance(getUserPoints().get(j).getX(), getUserPoints().get(j).getY(), getUserPoints().get(k).getX(), getUserPoints().get(k).getY());
		float r = jk / ij;
		double kx = getUserPoints().get(j).getX() + r * (getUserPoints().get(j).getX() - getUserPoints().get(i).getX());
		double ky = getUserPoints().get(j).getY() + r * (getUserPoints().get(j).getY() - getUserPoints().get(i).getY());

		getUserPoints().get(k).setLocation(kx, ky);
	}

	/**
	 * get distance between given points coordinates
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return distance
	 */
	private float distance(double x1, double y1, double x2, double y2) {
		return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.plot.spline.AbstractPlot#addPoint(double,
	 * double)
	 */
//	@Override
//	public void addPoint(double x, double y) {
//		int i = super.addPoint(x, y);
//		forceCollinear(i);
//		//return i;
//	}
	
	@Override
	public void solvePlot() {
//		for (int i = 0; i < getUserPoints().size(); i++) {
//			forceCollinear(i);
//		}
		for (int i = 4; i < getUserPoints().size(); i += 3) {
			forceCollinear(i);
		}
		super.solvePlot();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.plot.spline.AbstractPlot#setPoint(double,
	 * double)
	 */
	@Override
	public void updateAnchorPoint(double x, double y) {
		Point2D pixelUser = getHost().getProjection().pixelToUser(new Point2D.Double(x,y));
		deltax = pixelUser.getX() - getSelectedAnchor().getUserPoint().getX(); // save previous value
		deltay = pixelUser.getY() - getSelectedAnchor().getUserPoint().getY(); // save previous value
		//System.out.println("delta x = "+deltax);
		//System.out.println("delta y = "+deltay);
		super.updateAnchorPoint(x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.plugin.plot.spline.AbstractPlot#removePoint()
	 */
	@Override
	public void removePoint() {
		super.removePoint();
		for (int i = 4; i < getUserPoints().size(); i += 3) {
			forceCollinear(i);
		}
	}
	
	

}
