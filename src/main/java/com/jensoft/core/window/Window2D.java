/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.window;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.jensoft.core.device.Device2D;
import com.jensoft.core.map.projection.DalleProjection;
import com.jensoft.core.map.projection.GeoPosition;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.PluginEvent;
import com.jensoft.core.plugin.PluginListener;
import com.jensoft.core.plugin.copyright.CopyrightPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.view.View2DAdapter;
import com.jensoft.core.view.View2DEvent;

/**
 * <code>Window2D</code> takes the responsibility to make user projection for
 * hosted plug-ins.
 * <p>
 * The window projection nature are {@link Linear},{@link LogX}, {@link LogY},
 * {@link Log}, {@link Map} <br>
 * </p>
 * <ul>
 * <li>a window2D has to be registered in a {@link #view2D} with the
 * {@link View2D#registerWindow2D(Window2D)}
 * <li>a window2D should be register plug-ins with
 * {@link #registerPlugin(AbstractPlugin)} method</li>
 * <li>a plug-in developer can be use window projection method
 * {@link #userToPixel(Point2D)} or {@link #pixelToUser(Point2D)} to create
 * plug-ins for himself</li>
 * </ul>
 * 
 * @author sebastien janaud
 */
public abstract class Window2D implements PluginListener {

	/**
	 * The <code>Linear</code> class defines a window linear projection.
	 */
	public static class Linear extends Window2D implements Serializable {

		/** the initial minimum x */
		private double initialMinX = 0;

		/** the initial maximum x */
		private double initialMaxX = 0;

		/** the initial minimum y */
		private double initialMinY = 0;

		/** the initial maximum y */
		private double initialMaxY = 0;

		/** the current minimum x */
		private double minX = 0;

		/** the current maximum x */
		private double maxX = 0;

		/** the current minimum y */
		private double minY = 0;

		/** the current maximum y */
		private double maxY = 0;

		/** initial bound flag */
		private boolean initial = true;
		
		/** the current scale x */
		protected Double scaleX;

		/** the current scale y */
		protected Double scaleY;
		
		

		/**
		 * Constructs and initializes a <code>Linear</code> window projection
		 * with coordinates (0,0,0,0).
		 */
		public Linear() {
		}

		/**
		 * <code>Identity</code> defines the -1,1,-1,1 system projection coordinate
		 * @author sebastien janaud
		 *
		 */
		public static class Identity extends Linear {

			private static final long serialVersionUID = -3789513878376535416L;

			/**
			 * create identity projection
			 */
			public Identity() {
				super(-1, 1, -1, 1);
			}
		}

		/**
		 * build a new window2d with specified user metrics parameters.<br>
		 * the {@link #bound(double, double, double, double)} method is called.
		 * 
		 * @param minx
		 *            the window minimum x to set
		 * @param maxx
		 *            the window maximum x to set
		 * @param miny
		 *            the window minimum y to set
		 * @param maxy
		 *            the window maximum y to set
		 */
		public Linear(double minx, double maxx, double miny, double maxy) {
			bound(minx, maxx, miny, maxy);
		}

		/**
		 * bound this {@link Linear} window with the specified double
		 * parameters.<br>
		 * <p>
		 * set the all given parameters as window range. If {@link #initial} is
		 * true, initials variables for range are referenced as the window
		 * origin bound.
		 * </p>
		 * 
		 * @param minx
		 * @param maxx
		 * @param miny
		 * @param maxy
		 * @throws IllegalArgumentException
		 *             if minx is greater than maxx or if miny is greater than
		 *             maxy
		 */
		public void bound(double minx, double maxx, double miny, double maxy) {
			if (minx > maxx) {
				throw new IllegalArgumentException("maxx argument should be greater than minx");
			}
			if (miny > maxy) {
				throw new IllegalArgumentException("maxy argument should be greater than miny");
			}
			if (initial) {
				setInitialMinX(minx);
				setInitialMaxX(maxx);
				setInitialMinY(miny);
				setInitialMaxY(maxy);
				initial = false;
			}
			setMinX(minx);
			setMaxX(maxx);
			setMinY(miny);
			setMaxY(maxy);
			
//			if(getView2D() != null && getView2D().getDevice2D() != null){
//				this.scaleX = this.getPixelWidth() / this.getUserWidth();
//				this.scaleY = this.getPixelHeight() / this.getUserHeight();
//			}
			
			//reset scale
			this.scaleX = null;
			this.scaleY = null;

			// fire listeners about this window bound
			super.fireWindow2DBoundChanged();

		}
		
		/**
		 * reset scaling parameters
		 */
		protected void  resetScale(){
			this.scaleX = null;
			this.scaleY = null;
		}
		
		/* (non-Javadoc)
		 * @see com.jensoft.core.window.Window2D#onView2DRegister()
		 */
		@Override
		public void onView2DRegister() {
			super.onView2DRegister();
			
			getView2D().addView2DListener(new View2DAdapter() {

				/* (non-Javadoc)
				 * @see com.jensoft.core.view.View2DAdapter#viewResized(com.jensoft.core.view.View2DEvent)
				 */
				@Override
				public void viewResized(View2DEvent view2dEvent) {
					resetScale();
				}
			});
		}

		/**
		 * this method set the initial. on initial, when the
		 * {@link #bound(double, double, double, double)} method is call, min
		 * and max are copy in initial variables to have a origin range of the
		 * initialized window
		 * 
		 * @param initial
		 *            the initial to set
		 */
		public void setInitial(boolean initial) {
			this.initial = initial;
		}

		/**
		 * return the window center in the user coordinate projection
		 * 
		 * @return the window center
		 */
		public Point2D getUserCenter() {
			return new Point2D.Double(minX + (maxX - minX) / 2, minY + (maxY - minY) / 2);
		}

		/**
		 * get the window initial minimum X
		 * 
		 * @return minimum x
		 */
		public double getInitialMinX() {
			return initialMinX;
		}

		/**
		 * get the window initial maximum x
		 * 
		 * @return maximum x
		 */
		public double getInitialMaxX() {
			return initialMaxX;
		}

		/**
		 * get the window initial minimum y
		 * 
		 * @return minimum y
		 */
		public double getInitialMinY() {
			return initialMinY;
		}

		/**
		 * get the window initial maximum y
		 * 
		 * @return maximum y
		 */
		public double getInitialMaxY() {
			return initialMaxY;
		}

		/**
		 * the the window initial width
		 * 
		 * @return initial width
		 */
		public double getInitialWidth() {
			return initialMaxX - initialMinX;
		}

		/**
		 * get the window initial height
		 * 
		 * @return initial height
		 */
		public double getInitialHeight() {
			return initialMaxY - initialMinY;
		}

		/**
		 * get the window minimum x
		 * 
		 * @return minimum x
		 */
		@Override
		public double getMinX() {
			return minX;
		}

		/**
		 * get the window maximum x
		 * 
		 * @return maximum x
		 */
		@Override
		public double getMaxX() {
			return maxX;
		}

		/**
		 * get the window minimum y
		 * 
		 * @return minimum y
		 */
		@Override
		public double getMinY() {
			return minY;
		}

		/**
		 * get the window maximum y
		 * 
		 * @return maximum y
		 */
		@Override
		public double getMaxY() {
			return maxY;
		}

		/**
		 * set initial mix x
		 * 
		 * @param initialMinX
		 */
		private void setInitialMinX(double initialMinX) {
			this.initialMinX = initialMinX;
		}

		/**
		 * set initial max x
		 * 
		 * @param initialMaxX
		 */
		private void setInitialMaxX(double initialMaxX) {
			this.initialMaxX = initialMaxX;
		}

		/**
		 * set initial min y
		 * 
		 * @param initialMinY
		 */
		private void setInitialMinY(double initialMinY) {
			this.initialMinY = initialMinY;
		}

		/**
		 * set initial max y
		 * 
		 * @param initialMaxY
		 */
		private void setInitialMaxY(double initialMaxY) {
			this.initialMaxY = initialMaxY;
		}

		/**
		 * set min x
		 * 
		 * @param minX
		 */
		private void setMinX(double minX) {
			this.minX = minX;
		}

		/**
		 * set max x
		 * 
		 * @param maxX
		 */
		private void setMaxX(double maxX) {
			this.maxX = maxX;
		}

		/**
		 * set min y
		 * 
		 * @param minY
		 */
		private void setMinY(double minY) {
			this.minY = minY;
		}

		/**
		 * set max y
		 * 
		 * @param maxY
		 */
		private void setMaxY(double maxY) {
			this.maxY = maxY;
		}
		
		/**
		 * get scale X, new solve if null
		 * @return scale x
		 */
		public Double getScaleX(){
			if(this.scaleX == null)
				this.scaleX = this.getPixelWidth() / this.getUserWidth();
			return this.scaleX;
		}
		
		/**
		 * get scale Y, new solve if null
		 * @return
		 */
		public Double getScaleY(){
			if(this.scaleY == null)
				this.scaleY = this.getPixelHeight() / this.getUserHeight();
			return this.scaleY;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#userToPixelX(double)
		 */
		@Override
		public double userToPixelX(double userX) {
			//double scaleX = getDevice2D().getDeviceWidth() / (getMaxX() - getMinX());
			//return scaleX * (userX - getMinX());
			return getScaleX() * (userX - getMinX());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#userToPixelY(double)
		 */
		@Override
		public double userToPixelY(double userY) {
			//double scaleY = getDevice2D().getDeviceHeight() / (getMaxY() - getMinY());
			//return -scaleY * (userY - getMaxY());
			return -getScaleY() * (userY - getMaxY());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#pixelToUserX(double)
		 */
		@Override
		public double pixelToUserX(double pixelX) {
			//double scaleX = getDevice2D().getDeviceWidth() / (getMaxX() - getMinX());
			//return pixelX / scaleX + getMinX();
			return pixelX / getScaleX() + getMinX();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#pixelToUserY(double)
		 */
		@Override
		public double pixelToUserY(double pixelY) {
			//double scaleY = getDevice2D().getDeviceHeight() / (getMaxY() - getMinY());
			//return -(pixelY / scaleY - getMaxY());
			return -(pixelY / getScaleY() - getMaxY());
		}

		private static final long serialVersionUID = 5202790832488044493L;

	}

	/**
	 * The <code>LogX</code> class defines a composite logarithmic linear window
	 * with logarithmic x and linear y projection.
	 */
	public static class LogX extends Linear implements Serializable {

		/**
		 * Constructs a new window2d with logarithmic on x dimension and linear
		 * on y dimension with specified user metrics parameters.
		 * 
		 * @param minx
		 *            the window minimum x to set, should be greater than 0
		 * @param maxx
		 *            the window maximum x to set
		 * @param miny
		 *            the window minimum y to set
		 * @param maxy
		 *            the window maximum y to set
		 * @throws IllegalArgumentException
		 *             if the minx argument is not greater than 0
		 */
		public LogX(double minx, double maxx, double miny, double maxy) {
			if (minx <= 0) {
				throw new IllegalArgumentException("for LogX window projection, minx should be greater than 0.");
			}
			bound(minx, maxx, miny, maxy);
		}
		
		
		/* (non-Javadoc)
		 * @see com.jensoft.core.window.Window2D.Linear#getScaleX()
		 */
		@Override
		public Double getScaleX() {
			if(this.scaleX == null)
				this.scaleX = getDevice2D().getDeviceWidth() / (Math.log10(getMaxX()) - Math.log10(getMinX()));
			return this.scaleX;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Linear#userToPixelX(double)
		 */
		@Override
		public double userToPixelX(double userX) {
			//double scaleXLog = getDevice2D().getDeviceWidth() / (Math.log10(getMaxX()) - Math.log10(getMinX()));
			//return scaleXLog * (Math.log10(userX) - Math.log10(getMinX()));
			return getScaleX() * (Math.log10(userX) - Math.log10(getMinX()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Linear#pixelToUserX(double)
		 */
		@Override
		public double pixelToUserX(double pixelX) {
			//double scaleXLog = getDevice2D().getDeviceWidth() / (Math.log10(getMaxX()) - Math.log10(getMinX()));
			//return Math.pow(10, pixelX / scaleXLog + Math.log10(getMinX()));
			return Math.pow(10, pixelX / getScaleX() + Math.log10(getMinX()));
		}

		private static final long serialVersionUID = -2274590506013668597L;

	}

	/**
	 * The <code>LogY</code> class defines a composite logarithmic linear window
	 * with logarithmic y and linear x projection.
	 */
	public static class LogY extends Linear implements Serializable {

		/**
		 * Constructs a new window2d with logarithmic on y dimension and linear
		 * on x dimension with specified user metrics parameters.
		 * 
		 * @param minx
		 *            the window minimum x to set
		 * @param maxx
		 *            the window maximum x to set
		 * @param miny
		 *            the window minimum y to set
		 * @param maxy
		 *            the window maximum y to set
		 */
		public LogY(double minx, double maxx, double miny, double maxy) {
			if (miny <= 0) {
				throw new IllegalArgumentException("for LogY window projection, miny should be greater than 0.");
			}
			bound(minx, maxx, miny, maxy);
		}
		
		
		/* (non-Javadoc)
		 * @see com.jensoft.core.window.Window2D.Linear#getScaleY()
		 */
		@Override
		public Double getScaleY() {
			if(this.scaleY == null)
				this.scaleY = getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
			return this.scaleY;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Linear#userToPixelY(double)
		 */
		@Override
		public double userToPixelY(double userY) {
			//double scaleYLog = getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
			//return -scaleYLog * (Math.log10(userY) - Math.log10(getMaxY()));
			return -getScaleY() * (Math.log10(userY) - Math.log10(getMaxY()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Linear#pixelToUserY(double)
		 */
		@Override
		public double pixelToUserY(double pixelY) {
			//double scaleYLog = getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
			//return Math.pow(10, -(pixelY / scaleYLog - Math.log10(getMaxY())));
			return Math.pow(10, -(pixelY / getScaleY() - Math.log10(getMaxY())));
		}

		private static final long serialVersionUID = -2268519178555649250L;

	}

	/**
	 * The <code>Log</code> class defines a logarithmic projection window on x
	 * and y dimension
	 */
	public static class Log extends Linear implements Serializable {

		/**
		 * Constructs a new window2d with logarithmic on x and y dimension with
		 * specified user metrics parameters.
		 * 
		 * @param minx
		 *            the window minimum x to set
		 * @param maxx
		 *            the window maximum x to set
		 * @param miny
		 *            the window minimum y to set
		 * @param maxy
		 *            the window maximum y to set
		 */
		public Log(double minx, double maxx, double miny, double maxy) {
			if (minx <= 0) {
				throw new IllegalArgumentException("for Log window projection, minx should be greater than 0.");
			}
			if (miny <= 0) {
				throw new IllegalArgumentException("for Log window projection, miny should be greater than 0.");
			}
			bound(minx, maxx, miny, maxy);
		}
		
		/* (non-Javadoc)
		 * @see com.jensoft.core.window.Window2D.Linear#getScaleX()
		 */
		@Override
		public Double getScaleX() {
			if(this.scaleX == null)
				this.scaleX = getDevice2D().getDeviceWidth() / (Math.log10(getMaxX()) - Math.log10(getMinX()));
			return this.scaleX;
		}
		
		/* (non-Javadoc)
		 * @see com.jensoft.core.window.Window2D.Linear#getScaleY()
		 */
		@Override
		public Double getScaleY() {
			if(this.scaleY == null)
				this.scaleY = getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
			return this.scaleY;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Linear#userToPixelX(double)
		 */
		@Override
		public double userToPixelX(double userX) {
			//double scaleXLog = getDevice2D().getDeviceWidth() / (Math.log10(getMaxX()) - Math.log10(getMinX()));
			//return scaleXLog * (Math.log10(userX) - Math.log10(getMinX()));
			return getScaleX() * (Math.log10(userX) - Math.log10(getMinX()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Linear#pixelToUserX(double)
		 */
		@Override
		public double pixelToUserX(double pixelX) {
			//double scaleXLog = getDevice2D().getDeviceWidth() / (Math.log10(getMaxX()) - Math.log10(getMinX()));
			//return Math.pow(10, pixelX / scaleXLog + Math.log10(getMinX()));
			return Math.pow(10, pixelX / getScaleX() + Math.log10(getMinX()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Linear#userToPixelY(double)
		 */
		@Override
		public double userToPixelY(double userY) {
			//double scaleYLog = getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
			//return -scaleYLog * (Math.log10(userY) - Math.log10(getMaxY()));
			return -getScaleY() * (Math.log10(userY) - Math.log10(getMaxY()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Linear#pixelToUserY(double)
		 */
		@Override
		public double pixelToUserY(double pixelY) {
			//double scaleYLog = getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
			//return Math.pow(10, -(pixelY / scaleYLog - Math.log10(getMaxY())));
			return Math.pow(10, -(pixelY / getScaleY() - Math.log10(getMaxY())));
		}

		private static final long serialVersionUID = 6040320270911080943L;

	}

	/**
	 * The <code>Map</code> class defines a Mercator projection window.
	 */
	public static class Map extends Window2D implements Serializable {

		/** dalle projection */
		private DalleProjection projection;

		/** the center projection */
		private GeoPosition centerPosition;

		/** zoom level */
		private int level;

		/**
		 * create a map projection with default parameters :<br>
		 * <ul>
		 * <li>zoom level 1</li>
		 * <li>256 pixel square tile pixel</li>
		 * <li>centered on lat/long (0,0) degrees.</li>
		 * </ul>
		 */
		public Map() {
			projection = new DalleProjection(16, 128);
			centerPosition = new GeoPosition(25.854, -80.134);
			// projection = new DalleProjection(1, 256);
			// centerPosition = new GeoPosition(0, -0);
		}

		/**
		 * create a map projection for the specified parameters
		 * 
		 * @param centerPosition
		 *            the center geo position
		 * @param level
		 *            the zoom level
		 * @param squareTile
		 *            the square tile unit in pixel
		 */
		public Map(GeoPosition centerPosition, int level, int squareTile) {
			this.centerPosition = centerPosition;
			projection = new DalleProjection(level, squareTile);
		}

		/**
		 * get the center position of this window map
		 * 
		 * @return the centerPosition
		 */
		public GeoPosition getCenterPosition() {
			return centerPosition;
		}

		/**
		 * @param centerPosition
		 *            the centerPosition to set
		 */
		public void setCenterPosition(GeoPosition centerPosition) {
			this.centerPosition = centerPosition;
		}

		/**
		 * @return the projection
		 */
		public DalleProjection getProjection() {
			return projection;
		}

		/**
		 * @return the level
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * @param level
		 *            the level to set
		 */
		public void setLevel(int level) {
			this.level = level;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#getMinX()
		 */
		@Override
		public double getMinX() {
			double centerXPixel = projection.longitudeToPixel(getCenterPosition().getLongitude());
			return projection.pixelToLongitude(centerXPixel - getPixelWidth() / 2);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#getMaxX()
		 */
		@Override
		public double getMaxX() {
			double centerXPixel = projection.longitudeToPixel(getCenterPosition().getLongitude());
			return projection.pixelToLongitude(centerXPixel + getPixelWidth() / 2);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#getMinY()
		 */
		@Override
		public double getMinY() {
			double centerYPixel = projection.latitudeToPixel(getCenterPosition().getLatitude());
			return projection.pixelToLatitude(centerYPixel + getPixelHeight() / 2);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#getMaxY()
		 */
		@Override
		public double getMaxY() {
			double centerYPixel = projection.latitudeToPixel(getCenterPosition().getLatitude());
			return projection.pixelToLatitude(centerYPixel - getPixelHeight() / 2);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#userToPixelX(double)
		 */
		@Override
		public double userToPixelX(double userX) {
			double centerXPixel = projection.longitudeToPixel(getCenterPosition().getLongitude());
			return -centerXPixel + getPixelWidth() / 2 + projection.longitudeToPixel(userX);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#userToPixelY(double)
		 */
		@Override
		public double userToPixelY(double userY) {
			double centerYPixel = projection.latitudeToPixel(getCenterPosition().getLatitude());
			return -centerYPixel + getPixelHeight() / 2 + projection.latitudeToPixel(userY);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#pixelToUserX(double)
		 */
		@Override
		public double pixelToUserX(double pixelX) {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D#pixelToUserY(double)
		 */
		@Override
		public double pixelToUserY(double pixelY) {
			return 0;
		}

		private static final long serialVersionUID = -7328711106680920578L;

	}

	/**
	 * Time window define a window with time definition on one x or y dimension
	 * 
	 * @author sebastien janaud
	 */
	public static abstract class Time extends Linear implements Serializable {

		private static final long serialVersionUID = 5908781043325467240L;

		/**
		 * create a time window
		 * 
		 * @param minx
		 * @param maxx
		 * @param miny
		 * @param maxy
		 */
		public Time(double minx, double maxx, double miny, double maxy) {
			super(minx, maxx, miny, maxy);
		}

		/**
		 * return the minimum date of this time window
		 * 
		 * @return minimum date
		 */
		public abstract Date getMinDate();

		/**
		 * return the maximum date of this time window
		 * 
		 * @return maximum
		 */
		public abstract Date getMaxDate();

		/**
		 * transform the given pixel position in the date
		 * 
		 * @param pixel
		 * @return date
		 */
		public abstract Date pixelToTime(double pixel);

		/**
		 * transform the given time in pixel
		 * 
		 * @param time
		 * @return pixel position
		 */
		public abstract double timeToPixel(Date time);

		/**
		 * get the pixel size of the time dimension
		 * 
		 * @return bound time duration
		 */
		public abstract int getTimeDurationPixel();

		/**
		 * get the time duration in millisecond of this time window between min
		 * date and max date
		 * 
		 * @return duration millisecond
		 */
		public long durationMillis() {
			return getMaxDate().getTime() - getMinDate().getTime();
		}

		/**
		 * get the time duration in minutes of this time window between min date
		 * and max date
		 * 
		 * @return duration minutes
		 */
		public long durationMinutes() {
			long minutesMillis = 1000L * 60L;
			long minutes = durationMillis() / minutesMillis;
			return minutes;
		}

		/**
		 * get the time duration in hours of this time window between min date
		 * and max date
		 * 
		 * @return duration hours
		 */
		public long durationHours() {
			long hourMillis = 1000L * 60L * 60L;
			long hours = durationMillis() / hourMillis;
			return hours;
		}

		/**
		 * get the time duration in days of this time window between min date
		 * and max date
		 * 
		 * @return duration days
		 */
		public long durationDays() {
			long dayMillis = 1000L * 60L * 60L * 24L;
			long days = durationMillis() / dayMillis;
			return days;
		}

		/**
		 * get the time duration in weeks of this time window between min date
		 * and max date
		 * 
		 * @return duration weeks
		 */
		public long durationWeeks() {
			long weekMillis = 1000L * 60L * 60L * 24L * 7;
			long weeks = durationMillis() / weekMillis;
			return weeks;
		}

		/**
		 * get the time duration in month of this time window between min date
		 * and max date
		 * 
		 * @return duration month
		 */
		public long durationMonth() {
			long monthMillis = 1000L * 60L * 60L * 24L * 7L * 4L;
			long months = durationMillis() / monthMillis;
			return months;
		}

	}

	/**
	 * The <code>TimeX</code> class defines a {@link Linear} window projection
	 * with timing bounds definition on x dimension
	 */
	public static class TimeX extends Time implements Serializable {

		/**
		 * create a {@link Linear} window with typed time definition on x
		 * dimension
		 * 
		 * @param minXDate
		 * @param maxXDate
		 * @param miny
		 * @param maxy
		 */
		public TimeX(Date minXDate, Date maxXDate, double miny, double maxy) {
			super(minXDate.getTime(), maxXDate.getTime(), miny, maxy);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Time#getMinDate()
		 */
		@Override
		public Date getMinDate() {
			return getMinXAsDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Time#getMaxDate()
		 */
		@Override
		public Date getMaxDate() {
			return getMaxXAsDate();
		}

		/**
		 * get min x as a date value
		 * 
		 * @return min date
		 */
		public Date getMinXAsDate() {
			return new Date(new Double(getMinX()).longValue());
		}

		/**
		 * get max x as date value
		 * 
		 * @return max date
		 */
		public Date getMaxXAsDate() {
			return new Date(new Double(getMaxX()).longValue());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Time#pixelToTime(double)
		 */
		@Override
		public Date pixelToTime(double pixel) {
			double dateMillis = pixelToUserX(pixel);
			return new Date(new Double(dateMillis).longValue());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.window.Window2D.Time#timeToPixel(java.util.Date)
		 */
		@Override
		public double timeToPixel(Date time) {
			double userValue = new Long(time.getTime()).doubleValue();
			return userToPixelX(userValue);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Time#getTimeDurationPixel()
		 */
		@Override
		public int getTimeDurationPixel() {
			return getDevice2D().getDeviceWidth();
		}

		/**
		 * bound this {@link TimeX} window with given times min and max date for
		 * x dimension
		 * 
		 * @param minXDate
		 * @param maxXDate
		 * @param miny
		 * @param maxy
		 */
		public void bound(Date minXDate, Date maxXDate, double miny, double maxy) {
			super.bound(minXDate.getTime(), maxXDate.getTime(), miny, maxy);
		}

		private static final long serialVersionUID = -1604008751024713864L;
	}

	/**
	 * The <code>TimeY</code> class defines a {@link Linear} window projection
	 * with timing bounds definition on y dimension
	 */
	public static class TimeY extends Time implements Serializable {

		/**
		 * create a window with explicit time on y dimension
		 * 
		 * @param minx
		 * @param maxx
		 * @param minYDate
		 * @param maxYDate
		 */
		public TimeY(double minx, double maxx, Date minYDate, Date maxYDate) {
			super(minx, maxx, minYDate.getTime(), maxYDate.getTime());
		}

		/**
		 * get min y as a date value
		 * 
		 * @return min date
		 */
		public Date getMinYAsDate() {
			return new Date(new Double(getMinY()).longValue());
		}

		/**
		 * get max y as date value
		 * 
		 * @return max date
		 */
		public Date getMaxYAsDate() {
			return new Date(new Double(getMaxY()).longValue());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Time#getMinDate()
		 */
		@Override
		public Date getMinDate() {
			return getMinYAsDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Time#getMaxDate()
		 */
		@Override
		public Date getMaxDate() {
			return getMaxYAsDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Time#pixelToTime(double)
		 */
		@Override
		public Date pixelToTime(double pixel) {
			double dateMillis = pixelToUserY(pixel);
			return new Date(new Double(dateMillis).longValue());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.window.Window2D.Time#timeToPixel(java.util.Date)
		 */
		@Override
		public double timeToPixel(Date time) {
			double userValue = new Long(time.getTime()).doubleValue();
			return userToPixelY(userValue);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.jensoft.core.window.Window2D.Time#getTimeDurationPixel()
		 */
		@Override
		public int getTimeDurationPixel() {
			return getDevice2D().getDeviceHeight();
		}

		/**
		 * bound this {@link TimeY} window with given times min and max date for
		 * y dimension
		 * 
		 * @param minx
		 * @param maxx
		 * @param minYDate
		 * @param maxYDate
		 */
		public void bound(double minx, double maxx, Date minYDate, Date maxYDate) {
			super.bound(minx, maxx, minYDate.getTime(), maxYDate.getTime());
		}

		/**
		 * get the y time frame as number of a minutes
		 * 
		 * @return number of minutes
		 */
		public long getHeightAsMinutes() {
			long startMillis = new Double(getMinY()).longValue();
			long endMillis = new Double(getMaxY()).longValue();
			long width = endMillis - startMillis;
			long minutesMillis = 1000 * 60;
			long heightAsMinutes = width / minutesMillis;
			return heightAsMinutes;
		}

		private static final long serialVersionUID = -7000203845075240349L;
	}

	/**
	 * The <code>SymbolX</code> class defines a composite symbol and linear
	 * window with no scalar on x and linear y projection.
	 */
	public static class SymbolX extends Linear implements Serializable {

		/**
		 * Create a symbol on x dimension and linear on y dimension
		 * 
		 * @param miny
		 * @param maxy
		 */
		public SymbolX(double miny, double maxy) {
			super(0, 0, miny, maxy);
		}

		private static final long serialVersionUID = 5596190230123838729L;
	}

	/**
	 * The <code>SymbolX</code> class defines a composite with symbol and linear
	 * window with no scalar on x and linear y projection.
	 */
	public static class SymbolY extends Linear implements Serializable {

		/**
		 * Create a linear on x and symbol on y dimension
		 * 
		 * @param minx
		 * @param maxx
		 */
		public SymbolY(double minx, double maxx) {
			super(minx, maxx, 0, 0);
		}

		private static final long serialVersionUID = 5596190230123838729L;
	}

	/**
	 * This is an abstract class that cannot be instantiated directly.
	 * Type-specific implementation subclasses are available for instantiation
	 * and provide a number of formats for storing the information necessary to
	 * satisfy the various projection methods below.
	 */
	protected Window2D() {
		registerPlugin(new CopyrightPlugin());
	}

	/** device component */
	private Device2D device2D;

	/** parent view */
	private View2D view2D;

	/** window2d id */
	private String windowID;

	/** window2d name */
	private String name;

	/** window color theme */
	private Color themeColor;

	/** visible flag */
	private boolean visible = true;

	/** lock active */
	private boolean lockActive;

	/** registered window listener */
	protected EventListenerList listenerList = new EventListenerList();

	/** plug in registry */
	private List<AbstractPlugin> plugins = new ArrayList<AbstractPlugin>();

	/**
	 * @return the windowID
	 */
	public String getWindowID() {
		return windowID;
	}

	/**
	 * @param windowID
	 *            the windowID to set
	 */
	public void setWindowID(String windowID) {
		this.windowID = windowID;
	}

	/**
	 * get the window name
	 * 
	 * @return the window name
	 */
	public String getName() {
		return name;
	}

	/***
	 * set the window name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get the registered device2d of this window
	 * 
	 * @return the device2D
	 */
	public Device2D getDevice2D() {
		return device2D;
	}

	/**
	 * set the device2D for this window
	 * 
	 * @param device2D
	 */
	public void setDevice2D(Device2D device2D) {
		this.device2D = device2D;
	}

	/**
	 * get the view that host this window
	 * 
	 * @return window host.
	 */
	public View2D getView2D() {
		return view2D;
	}

	/**
	 * set view 2D that host this window
	 * 
	 * @param view2d
	 *            the host view 2D
	 */
	public void setView2D(View2D view2d) {
		view2D = view2d;
	}

	/**
	 * get the theme red
	 * 
	 * @return the theme color red
	 */
	public int getThemeRed() {
		return getThemeColor().getRed();
	}

	/**
	 * get the theme green
	 * 
	 * @return the theme color green
	 */
	public int getThemeGreen() {
		return getThemeColor().getGreen();
	}

	/**
	 * get the theme blue
	 * 
	 * @return the theme color blue
	 */
	public int getThemeBlue() {
		return getThemeColor().getBlue();
	}

	/**
	 * get the theme color, theme color can be use by tool or device.
	 * 
	 * @return the theme color
	 */
	public Color getThemeColor() {
		if (themeColor == null) {
			themeColor = ColorPalette.getRandomColor();
		}
		return themeColor;
	}

	/**
	 * set the theme color
	 * 
	 * @param themeColor
	 */
	public void setThemeColor(Color themeColor) {
		this.themeColor = themeColor;
	}

	/**
	 * rteurn true if this window is visible, false otherwise
	 * 
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * set window visible
	 * 
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * lock this window
	 */
	public void lockActive() {
		lockActive = true;
		fireWindow2DActivate();
	}

	/**
	 * unlock this window
	 */
	public void unlockActive() {
		lockActive = false;
		fireWindow2DPassivate();
	}

	/**
	 * return true if this window is locked, false otherwise
	 * 
	 * @return the window lock
	 */
	public boolean isLockActive() {
		return lockActive;
	}

	/**
	 * add a window2D listener
	 * 
	 * @param listener
	 */
	public void addWindow2DListener(Window2DListener listener) {
		listenerList.add(Window2DListener.class, listener);
	}

	/**
	 * remove a window2D listener
	 * 
	 * @param listener
	 */
	public void removeWindow2DListener(Window2DListener listener) {
		listenerList.remove(Window2DListener.class, listener);
	}

	/**
	 * return the plugin registry
	 * 
	 * @return plugins
	 */
	public List<AbstractPlugin> getPluginRegistry() {
		return plugins;
	}

	/**
	 * return the selectable plug in
	 * 
	 * @return selectable plugins
	 */
	public List<AbstractPlugin> getSelectablePlugins() {
		List<AbstractPlugin> selectablePlugins = new ArrayList<AbstractPlugin>();
		for (AbstractPlugin plugin : plugins) {
			if (plugin.isSelectable()) {
				selectablePlugins.add(plugin);
			}
		}
		return selectablePlugins;
	}

	/**
	 * register the specified plug in
	 * 
	 * @param plugin
	 *            the plug in to register
	 */
	public void registerPlugin(AbstractPlugin plugin) {
		if (plugins.contains(plugin)) {
			return;
		}
		plugin.setWindow2D(this);
		plugin.addPluginListener(this);
		plugins.add(plugin);
		plugin.onWindowRegister();
	}

	/**
	 * call on {@link View2D} register this window
	 */
	public void onView2DRegister() {
		getView2D().addView2DListener(new View2DAdapter() {

			@Override
			public void viewResized(View2DEvent view2dEvent) {
				fireWindow2DResized();
			}
		});
	}

	/**
	 * unregister all plug ins
	 */
	public void unregisterAll() {
		plugins.clear();
	}

	/**
	 * unregister the specified plug in
	 * 
	 * @param plugin
	 *            the plug in to register
	 */
	public void unregisterPlugin(AbstractPlugin plugin) {
		plugin.removePluginListener(this);
		plugins.remove(plugin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.PluginListener#pluginSelected(com.jensoft.core
	 * .plugin.PluginEvent)
	 */
	@Override
	public void pluginSelected(PluginEvent te) {
		AbstractPlugin selectedPlugin = (AbstractPlugin) te.getSource();
		for (AbstractPlugin plugin : plugins) {
			if (!plugin.equals(selectedPlugin) && plugin.isLockSelected()) {
				plugin.unlockSelected();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.PluginListener#pluginUnlockSelected(com.jensoft
	 * .core.plugin.PluginEvent)
	 */
	@Override
	public void pluginUnlockSelected(PluginEvent te) {
	}

	/**
	 * fire listener that the window became active
	 */
	private void fireWindow2DActivate() {
		Window2DEvent w2dEvent = new Window2DEvent(this);

		Object[] listeners = listenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == Window2DListener.class) {
					((Window2DListener) listeners[i + 1]).window2DLockActive(w2dEvent);
				}
			}
		}
	}

	/**
	 * fire listener that the window became passive
	 */
	private void fireWindow2DPassivate() {
		Window2DEvent w2dEvent = new Window2DEvent(this);
		Object[] listeners = listenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == Window2DListener.class) {
					((Window2DListener) listeners[i + 1]).window2DUnlockActive(w2dEvent);
				}
			}
		}
	}

	/**
	 * fire listener that the window has changed
	 */
	private void fireWindow2DBoundChanged() {
		Window2DEvent w2dEvent = new Window2DEvent(this);
		Object[] listeners = listenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == Window2DListener.class) {
					((Window2DListener) listeners[i + 1]).window2DBoundChanged(w2dEvent);
				}
			}
		}
	}

	/**
	 * fire listener that the window has changed
	 */
	private void fireWindow2DResized() {
		Window2DEvent w2dEvent = new Window2DEvent(this);
		Object[] listeners = listenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == Window2DListener.class) {
					((Window2DListener) listeners[i + 1]).window2DResized(w2dEvent);
				}
			}
		}
	}

	/**
	 * check if point is include in user window
	 * 
	 * @param p2dUser
	 * @return true if the user point intercept window, false otherwise
	 */
	public boolean intercept(Point2D p2dUser) {
		return interceptX(p2dUser.getX()) && interceptY(p2dUser.getY());
	}

	/**
	 * check if point is include in window x user range
	 * 
	 * @param userX
	 *            the x value in user coordinate system
	 * @return true if the user x range intercept window, false otherwise
	 */
	public boolean interceptX(double userX) {
		if (userX > getMaxX() || userX < getMinX()) {
			return false;
		}
		return true;
	}

	/**
	 * check if point is include in window y user range
	 * 
	 * @param userY
	 *            the y value in user coordinate system
	 * @return true if the user y range intercept window, false otherwise
	 */
	public boolean interceptY(double userY) {
		if (userY > getMaxY() || userY < getMinY()) {
			return false;
		}
		return true;
	}

	/**
	 * return the user width of this window
	 * 
	 * @return user width
	 */
	public double getUserWidth() {
		return getMaxX() - getMinX();
	}

	/**
	 * get the pixel with of this window
	 * 
	 * @return pixel width
	 */
	public double getPixelWidth() {
		return getDevice2D().getDeviceWidth();
	}

	/**
	 * return the user height of this window
	 * 
	 * @return user height
	 */
	public double getUserHeight() {
		return getMaxY() - getMinY();
	}

	/**
	 * get the pixel height of this window
	 * 
	 * @return pixel height
	 */
	public double getPixelHeight() {
		return getDevice2D().getDeviceHeight();
	}
	

	/**
	 * Returns the user minimum X coordinate of this <code>Window2D</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the user minimum X coordinate of this <code>Window2D</code>.
	 */
	public abstract double getMinX();

	/**
	 * Returns the user minimum Y coordinate of this <code>Window2D</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the user minimum Y coordinate of this <code>Window2D</code>.
	 */
	public abstract double getMinY();

	/**
	 * Returns the user maximum X coordinate of this <code>Window2D</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the user maximum X coordinate of this <code>Window2D</code>.
	 */
	public abstract double getMaxX();

	/**
	 * Returns the user maximum Y coordinate of this <code>Window2D</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the user maximum Y coordinate of this <code>Window2D</code>.
	 */
	public abstract double getMaxY();

	/**
	 * Returns the pixel coordinate of this user <code>Point2D</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the pixel coordinate of this user <code>Point2D</code>.
	 */
	public final Point2D userToPixel(Point2D userPoint) {
		return new Point2D.Double(userToPixelX(userPoint.getX()), userToPixelY(userPoint.getY()));
	}

	/**
	 * Returns the user coordinate of this pixel <code>Point2D</code> in
	 * <code>double</code> precision.
	 * 
	 * @param pixelPoint
	 *            the pixel point to transform
	 * @return the user coordinate of this pixel <code>Point2D</code>.
	 */
	public final Point2D pixelToUser(Point2D pixelPoint) {
		return new Point2D.Double(pixelToUserX(pixelPoint.getX()), pixelToUserY(pixelPoint.getY()));
	}

	/**
	 * Returns the pixel x coordinate of this x user in <code>double</code>
	 * precision.
	 * 
	 * @return the pixel x coordinate of this x user.
	 */
	public abstract double userToPixelX(double userX);

	/**
	 * Returns the pixel y coordinate of this y user in <code>double</code>
	 * precision.
	 * 
	 * @return the pixel y coordinate of this y user.
	 */
	public abstract double userToPixelY(double userY);

	/**
	 * Returns the pixel x coordinate of this x user in <code>double</code>
	 * precision.
	 * 
	 * @return the pixel x coordinate of this x user.
	 */
	public abstract double pixelToUserX(double pixelX);

	/**
	 * Returns the pixel y coordinate of this y user in <code>double</code>
	 * precision.
	 * 
	 * @return the pixel y coordinate of this y user.
	 */
	public abstract double pixelToUserY(double pixelY);

}
