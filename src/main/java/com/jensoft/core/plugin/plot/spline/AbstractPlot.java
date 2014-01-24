/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.plot.spline;

import java.awt.Color;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.plot.PlotPlugin;
import com.jensoft.core.plugin.plot.painter.anchor.AbstractPlotAnchorPainter;
import com.jensoft.core.plugin.plot.painter.anchor.PlotAnchorDefaultPainter;
import com.jensoft.core.plugin.plot.painter.draw.AbstractPlotDraw;
import com.jensoft.core.plugin.plot.painter.draw.PlotDefaultDraw;
import com.jensoft.core.plugin.plot.painter.label.AbstractPlotLabel;
import com.jensoft.core.plugin.plot.painter.label.PlotDefaultLabel;
import com.jensoft.core.window.Window2D;

/**
 * <code>AbstractPlot</code> defines an abstract plot.
 * 
 * @author sebastien janaud
 * 
 */
public abstract class AbstractPlot {

	/** plot points in the user coordinates system */
	private List<Point2D> userPoints;

	/** plot points in the pixel coordinates system */
	private List<Point2D> devicePoints;

	/** plot anchors */
	private List<PlotAnchor> anchorsPoints;

	/** plot path */
	private GeneralPath plotPath;

	/** index of selected point */
	// private int selection = -1;
	private PlotAnchor selectedAnchor = null;

	/** plot host plugin for this plot */
	private PlotPlugin host;

	/** plot draw color */
	private Color plotDrawColor;

	/** plot draw */
	private AbstractPlotDraw plotDraw = new PlotDefaultDraw();

	/** plot label */
	private AbstractPlotLabel plotLabel = new PlotDefaultLabel();

	/** plot anchor painter */
	private AbstractPlotAnchorPainter plotAnchorsPainter = new PlotAnchorDefaultPainter();

	/** square of distance for picking */
	private final int EPSILON = 36;

	/**
	 * create abstract plot
	 */
	public AbstractPlot() {
		userPoints = new ArrayList<Point2D>();
	}

	/**
	 * {@link #solvePlot()} method is responsible to solve plot in the pixel
	 * system coordinates by filling {@link #devicePoints} and set
	 * {@link #plotPath}
	 */
	public abstract void solvePlot();

	/**
	 * solve plot anchors
	 */
	public void solvePlotAnchors() {
		Window2D w2d = getHost().getWindow2D();

		List<PlotAnchor> anchors = new ArrayList<PlotAnchor>();
		setAnchorsPoints(anchors);
		for (int i = 0; i < getUserPoints().size(); i++) {
			PlotAnchor anchor = new PlotAnchor();
			anchor.setUserPoint(getUserPoints().get(i));
			anchor.setDevicePoint(w2d.userToPixel(getUserPoints().get(i)));
			anchors.add(anchor);
		}
	}

	/**
	 * @return the plotAnchorsPainter
	 */
	public AbstractPlotAnchorPainter getPlotAnchorsPainter() {
		return plotAnchorsPainter;
	}

	/**
	 * @param plotAnchorsPainter
	 *            the plotAnchorsPainter to set
	 */
	public void setPlotAnchorsPainter(AbstractPlotAnchorPainter plotAnchorsPainter) {
		this.plotAnchorsPainter = plotAnchorsPainter;
	}

	/**
	 * @return the anchorsPoints
	 */
	public List<PlotAnchor> getAnchorsPoints() {
		return anchorsPoints;
	}

	/**
	 * @param anchorsPoints
	 *            the anchorsPoints to set
	 */
	public void setAnchorsPoints(List<PlotAnchor> anchorsPoints) {
		this.anchorsPoints = anchorsPoints;
	}

	/**
	 * @return the plotDraw
	 */
	public AbstractPlotDraw getPlotDraw() {
		return plotDraw;
	}

	/**
	 * @param plotDraw
	 *            the plotDraw to set
	 */
	public void setPlotDraw(AbstractPlotDraw plotDraw) {
		this.plotDraw = plotDraw;
	}

	/**
	 * @return the plotLabel
	 */
	public AbstractPlotLabel getPlotLabel() {
		return plotLabel;
	}

	/**
	 * @param plotLabel
	 *            the plotLabel to set
	 */
	public void setPlotLabel(AbstractPlotLabel plotLabel) {
		this.plotLabel = plotLabel;
	}

	/**
	 * @return the userPoints
	 */
	public List<Point2D> getUserPoints() {
		return userPoints;
	}

	/**
	 * @param userPoints
	 *            the userPoints to set
	 */
	public void setUserPoints(List<Point2D> userPoints) {
		this.userPoints = userPoints;
	}

	/**
	 * @return the devicePoints
	 */
	public List<Point2D> getDevicePoints() {
		return devicePoints;
	}

	/**
	 * @param devicePoints
	 *            the devicePoints to set
	 */
	public void setDevicePoints(List<Point2D> devicePoints) {
		this.devicePoints = devicePoints;
	}

	/**
	 * @return the plotPath
	 */
	public GeneralPath getPlotPath() {
		return plotPath;
	}

	/**
	 * @param plotPath
	 *            the plotPath to set
	 */
	public void setPlotPath(GeneralPath plotPath) {
		this.plotPath = plotPath;
	}

	// /**
	// * @return the selection
	// */
	// public int getSelection() {
	// return selection;
	// }
	//
	// /**
	// * @param selection
	// * the selection to set
	// */
	// public void setSelection(int selection) {
	// this.selection = selection;
	// }

	/**
	 *return anchor point near to (x,y) or -1 if nothing near
	 * 
	 * @param x
	 * @param y
	 * @return plot anchor
	 */
	public PlotAnchor getPlotAnchor(int x, int y) {
		int mind = Integer.MAX_VALUE;

		PlotAnchor selectedAnchor = null;
		for (PlotAnchor anchor : anchorsPoints) {

			int d = sqr((int) (anchor.getDevicePoint().getX() - x)) + sqr((int) (anchor.getDevicePoint().getY() - y));
			if (d < mind && d < EPSILON) {
				mind = d;				
				selectedAnchor = anchor;
			}
		}
		//System.out.println("select root anchor : "+selectedAnchor);
		return selectedAnchor;
	}
	
	/**
	 * select and return anchor point near to (x,y) or -1 if nothing near
	 * 
	 * @param x
	 * @param y
	 * @return selected plot anchor
	 */
	public PlotAnchor selectPlotAnchor(int x, int y) {
		selectedAnchor = getPlotAnchor(x, y);
		return selectedAnchor;
	}
	
	public PlotAnchor getSelectedAnchor(){
		return selectedAnchor;
	}

	/**
	 * square
	 * 
	 * @param x
	 * @return square value of the given value
	 */
	private int sqr(int x) {
		return x * x;
	}

	/**
	 * add a control point in this plot
	 * 
	 * @param x
	 * @param y
	 */
	public void addPoint(double x, double y) {
		userPoints.add(new Point2D.Double(x, y));
	}

	/**
	 * set selected anchor point
	 * 
	 * @param x
	 * @param y
	 */
	public void updateAnchorPoint(double x, double y) {
		if (selectedAnchor != null) {
			Point2D newPoint = getHost().getWindow2D().pixelToUser(new Point2D.Double(x, y));
			int index = getUserPointIndex(selectedAnchor.getUserPoint());
			if (index != -1) {
				userPoints.get(index).setLocation(newPoint);
			}
		}else{
			//System.out.println("no selection, no update");
		}
	}

	/**
	 * get given point index in the {@link #userPoints} list
	 * 
	 * @param point
	 * @return point index
	 */
	public int getUserPointIndex(Point2D point) {
		for (int i = 0; i < userPoints.size(); i++) {
			if (userPoints.get(i).equals(point)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * remove selected control point
	 */
	public void removePoint() {
		if (selectedAnchor != null) {
			int index = getUserPointIndex(selectedAnchor.getUserPoint());
			if (index != -1) {
				userPoints.remove(index);
			}

		}
	}

	/**
	 * @return the host
	 */
	public PlotPlugin getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(PlotPlugin host) {
		this.host = host;
	}

	/**
	 * @return the plotDrawColor
	 */
	public Color getPlotDrawColor() {
		return plotDrawColor;
	}

	/**
	 * @param plotDrawColor
	 *            the plotDrawColor to set
	 */
	public void setPlotDrawColor(Color plotDrawColor) {
		this.plotDrawColor = plotDrawColor;
	}

}
