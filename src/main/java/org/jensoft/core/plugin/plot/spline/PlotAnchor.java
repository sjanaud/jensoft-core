package org.jensoft.core.plugin.plot.spline;

import java.awt.geom.Point2D;

public class PlotAnchor {
	
	private Point2D userPoint;
	private Point2D devicePoint;

	public PlotAnchor() {
	}

	/**
	 * @return the userPoint
	 */
	public Point2D getUserPoint() {
		return userPoint;
	}

	/**
	 * @param userPoint the userPoint to set
	 */
	public void setUserPoint(Point2D userPoint) {
		this.userPoint = userPoint;
	}

	/**
	 * @return the devicePoint
	 */
	public Point2D getDevicePoint() {
		return devicePoint;
	}

	/**
	 * @param devicePoint the devicePoint to set
	 */
	public void setDevicePoint(Point2D devicePoint) {
		this.devicePoint = devicePoint;
	}
	
	

}
