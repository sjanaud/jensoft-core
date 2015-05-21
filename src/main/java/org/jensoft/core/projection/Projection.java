/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.projection;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.jensoft.core.device.Device;
import org.jensoft.core.map.projection.DalleProjection;
import org.jensoft.core.map.projection.GeoPosition;
import org.jensoft.core.palette.color.ColorPalette;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.plugin.PluginEvent;
import org.jensoft.core.plugin.PluginListener;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewAdapter;
import org.jensoft.core.view.ViewEvent;

/**
 * <code>Projection</code> takes the responsibility to make user projection for
 * hosted plug-ins.
 * <p>
 * The  projection nature are {@link Linear},{@link LogX}, {@link LogY},
 * {@link Log}, {@link Map} <br>
 * </p>
 * <ul>
 * <li>a projection has to be registered in a {@link #view} with the
 * {@link View#registerProjection(Projection)}
 * <li>a projection should be register plug-ins with
 * {@link #registerPlugin(AbstractPlugin)} method</li>
 * <li>a plug-in developer can be use  projection method
 * {@link #userToPixel(Point2D)} or {@link #pixelToUser(Point2D)} to create
 * plug-ins for himself</li>
 * </ul>
 * 
 * @author sebastien janaud
 */
public abstract class Projection implements PluginListener {

	/**
	 * The <code>Linear</code> class defines a linear projection.
	 */
	public static class Linear extends Projection implements Serializable {

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
		//protected Double scaleX;

		/** the current scale y */
		//protected Double scaleY;
		
		

		/**
		 * Constructs and initializes a <code>Linear</code>  projection
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
		 * build a new projection with specified user metrics parameters.<br>
		 * the {@link #bound(double, double, double, double)} method is called.
		 * 
		 * @param minx
		 *            the projection minimum x to set
		 * @param maxx
		 *            the projection maximum x to set
		 * @param miny
		 *            the projection minimum y to set
		 * @param maxy
		 *            the projection maximum y to set
		 */
		public Linear(double minx, double maxx, double miny, double maxy) {
			bound(minx, maxx, miny, maxy);
		}

		/**
		 * bound this {@link Linear} projection with the specified double
		 * parameters.<br>
		 * <p>
		 * set the all given parameters as projection range. If {@link #initial} is
		 * true, initials variables for range are referenced as the projection
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
			
			//reset scale
			//this.scaleX = null;
			//this.scaleY = null;

			// fire listeners about this projection bound
			super.fireProjectionBoundChanged();

		}
		
		
		
		
		@Override
		public void onViewRegister() {
			super.onViewRegister();
		}

		/**
		 * this method set the initial. on initial, when the
		 * {@link #bound(double, double, double, double)} method is call, min
		 * and max are copy in initial variables to have a origin range of the
		 * initialized projection
		 * 
		 * @param initial
		 *            the initial to set
		 */
		public void setInitial(boolean initial) {
			this.initial = initial;
		}

		/**
		 * return the projection center in the user coordinate projection
		 * 
		 * @return the projection center
		 */
		public Point2D getUserCenter() {
			return new Point2D.Double(minX + (maxX - minX) / 2, minY + (maxY - minY) / 2);
		}

		/**
		 * get the projection initial minimum X
		 * 
		 * @return minimum x
		 */
		public double getInitialMinX() {
			return initialMinX;
		}

		/**
		 * get the projection initial maximum x
		 * 
		 * @return maximum x
		 */
		public double getInitialMaxX() {
			return initialMaxX;
		}

		/**
		 * get the projection initial minimum y
		 * 
		 * @return minimum y
		 */
		public double getInitialMinY() {
			return initialMinY;
		}

		/**
		 * get the projection initial maximum y
		 * 
		 * @return maximum y
		 */
		public double getInitialMaxY() {
			return initialMaxY;
		}

		/**
		 * the the projection initial width
		 * 
		 * @return initial width
		 */
		public double getInitialWidth() {
			return initialMaxX - initialMinX;
		}

		/**
		 * get the projection initial height
		 * 
		 * @return initial height
		 */
		public double getInitialHeight() {
			return initialMaxY - initialMinY;
		}

		/**
		 * get the projection minimum x
		 * 
		 * @return minimum x
		 */
		@Override
		public double getMinX() {
			return minX;
		}

		/**
		 * get the projection maximum x
		 * 
		 * @return maximum x
		 */
		@Override
		public double getMaxX() {
			return maxX;
		}

		/**
		 * get the projection minimum y
		 * 
		 * @return minimum y
		 */
		@Override
		public double getMinY() {
			return minY;
		}

		/**
		 * get the projection maximum y
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
		 * get scale X
		 * @return scale x
		 */
		public Double getScaleX(){
//			if(this.scaleX == null)
//				this.scaleX = this.getPixelWidth() / this.getUserWidth();
//			return this.scaleX;
			
			return this.getPixelWidth() / this.getUserWidth();
		}
		
		/**
		 * get scale Y, new solve if null
		 * @return
		 */
		public Double getScaleY(){
//			if(this.scaleY == null)
//				this.scaleY = this.getPixelHeight() / this.getUserHeight();
//			return this.scaleY;
			
			return this.getPixelHeight() / this.getUserHeight();
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#userToPixelX(double)
		 */
		@Override
		public double userToPixelX(double userX) {
			//double scaleX = getDevice2D().getDeviceWidth() / (getMaxX() - getMinX());
			//return scaleX * (userX - getMinX());
			return getScaleX() * (userX - getMinX());
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#userToPixelY(double)
		 */
		@Override
		public double userToPixelY(double userY) {
			//double scaleY = getDevice2D().getDeviceHeight() / (getMaxY() - getMinY());
			//return -scaleY * (userY - getMaxY());
			return -getScaleY() * (userY - getMaxY());
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#pixelToUserX(double)
		 */
		@Override
		public double pixelToUserX(double pixelX) {
			//double scaleX = getDevice2D().getDeviceWidth() / (getMaxX() - getMinX());
			//return pixelX / scaleX + getMinX();
			return pixelX / getScaleX() + getMinX();
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#pixelToUserY(double)
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
	 * The <code>LogX</code> class defines a composite logarithmic linear projection
	 * with logarithmic x and linear y projection.
	 */
	public static class LogX extends Linear implements Serializable {

		/**
		 * Constructs a new projection with logarithmic on x dimension and linear
		 * on y dimension with specified user metrics parameters.
		 * 
		 * @param minx
		 *            the projection minimum x to set, should be greater than 0
		 * @param maxx
		 *            the projection maximum x to set
		 * @param miny
		 *            the projection minimum y to set
		 * @param maxy
		 *            the projection maximum y to set
		 * @throws IllegalArgumentException
		 *             if the minx argument is not greater than 0
		 */
		public LogX(double minx, double maxx, double miny, double maxy) {
			if (minx <= 0) {
				throw new IllegalArgumentException("LogX projection, minx should be greater than 0.");
			}
			bound(minx, maxx, miny, maxy);
		}
		
		
		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#getScaleX()
		 */
		@Override
		public Double getScaleX() {
//			if(this.scaleX == null)
//				this.scaleX = getDevice2D().getDeviceWidth() / (Math.log10(getMaxX()) - Math.log10(getMinX()));
//			return this.scaleX;
			
			return getDevice2D().getDeviceWidth() / (Math.log10(getMaxX()) - Math.log10(getMinX()));
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#userToPixelX(double)
		 */
		@Override
		public double userToPixelX(double userX) {
			return getScaleX() * (Math.log10(userX) - Math.log10(getMinX()));
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#pixelToUserX(double)
		 */
		@Override
		public double pixelToUserX(double pixelX) {
			return Math.pow(10, pixelX / getScaleX() + Math.log10(getMinX()));
		}

		private static final long serialVersionUID = -2274590506013668597L;

	}

	/**
	 * The <code>LogY</code> class defines a composite logarithmic linear projection
	 * with logarithmic y and linear x projection.
	 */
	public static class LogY extends Linear implements Serializable {

		/**
		 * Constructs a new projection with logarithmic on y dimension and linear
		 * on x dimension with specified user metrics parameters.
		 * 
		 * @param minx
		 *            the projection minimum x to set
		 * @param maxx
		 *            the projection maximum x to set
		 * @param miny
		 *            the projection minimum y to set
		 * @param maxy
		 *            the projection maximum y to set
		 */
		public LogY(double minx, double maxx, double miny, double maxy) {
			if (miny <= 0) {
				throw new IllegalArgumentException("LogY window projection, miny should be greater than 0.");
			}
			bound(minx, maxx, miny, maxy);
		}
		
		
		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#getScaleY()
		 */
		@Override
		public Double getScaleY() {
//			if(this.scaleY == null)
//				this.scaleY = getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
//			return this.scaleY;
			return getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#userToPixelY(double)
		 */
		@Override
		public double userToPixelY(double userY) {
			//double scaleYLog = getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
			//return -scaleYLog * (Math.log10(userY) - Math.log10(getMaxY()));
			return -getScaleY() * (Math.log10(userY) - Math.log10(getMaxY()));
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#pixelToUserY(double)
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
	 * The <code>Log</code> class defines a logarithmic projection projection on x
	 * and y dimension
	 */
	public static class Log extends Linear implements Serializable {

		/**
		 * Constructs a new projection with logarithmic on x and y dimension with
		 * specified user metrics parameters.
		 * 
		 * @param minx
		 *            the projection minimum x to set
		 * @param maxx
		 *            the projection maximum x to set
		 * @param miny
		 *            the projection minimum y to set
		 * @param maxy
		 *            the projection maximum y to set
		 */
		public Log(double minx, double maxx, double miny, double maxy) {
			if (minx <= 0) {
				throw new IllegalArgumentException("Log projection, minx should be greater than 0.");
			}
			if (miny <= 0) {
				throw new IllegalArgumentException("Log projection, miny should be greater than 0.");
			}
			bound(minx, maxx, miny, maxy);
		}
		
		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#getScaleX()
		 */
		@Override
		public Double getScaleX() {
//			if(this.scaleX == null)
//				this.scaleX = getDevice2D().getDeviceWidth() / (Math.log10(getMaxX()) - Math.log10(getMinX()));
//			return this.scaleX;
			return getDevice2D().getDeviceWidth() / (Math.log10(getMaxX()) - Math.log10(getMinX()));
		}
		
		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#getScaleY()
		 */
		@Override
		public Double getScaleY() {
//			if(this.scaleY == null)
//				this.scaleY = getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
//			return this.scaleY;
			return getDevice2D().getDeviceHeight() / (Math.log10(getMaxY()) - Math.log10(getMinY()));
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#userToPixelX(double)
		 */
		@Override
		public double userToPixelX(double userX) {
			return getScaleX() * (Math.log10(userX) - Math.log10(getMinX()));
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#pixelToUserX(double)
		 */
		@Override
		public double pixelToUserX(double pixelX) {
			return Math.pow(10, pixelX / getScaleX() + Math.log10(getMinX()));
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#userToPixelY(double)
		 */
		@Override
		public double userToPixelY(double userY) {
			return -getScaleY() * (Math.log10(userY) - Math.log10(getMaxY()));
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Linear#pixelToUserY(double)
		 */
		@Override
		public double pixelToUserY(double pixelY) {
			return Math.pow(10, -(pixelY / getScaleY() - Math.log10(getMaxY())));
		}

		private static final long serialVersionUID = 6040320270911080943L;

	}

	/**
	 * The <code>Map</code> class defines a Mercator projection.
	 */
	public static class Map extends Projection implements Serializable {

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
		 * get the center position of this projection map
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

		
		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#getMinX()
		 */
		@Override
		public double getMinX() {
			double centerXPixel = projection.longitudeToPixel(getCenterPosition().getLongitude());
			return projection.pixelToLongitude(centerXPixel - getPixelWidth() / 2);
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#getMaxX()
		 */
		@Override
		public double getMaxX() {
			double centerXPixel = projection.longitudeToPixel(getCenterPosition().getLongitude());
			return projection.pixelToLongitude(centerXPixel + getPixelWidth() / 2);
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#getMinY()
		 */
		@Override
		public double getMinY() {
			double centerYPixel = projection.latitudeToPixel(getCenterPosition().getLatitude());
			return projection.pixelToLatitude(centerYPixel + getPixelHeight() / 2);
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#getMaxY()
		 */
		@Override
		public double getMaxY() {
			double centerYPixel = projection.latitudeToPixel(getCenterPosition().getLatitude());
			return projection.pixelToLatitude(centerYPixel - getPixelHeight() / 2);
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#userToPixelX(double)
		 */
		@Override
		public double userToPixelX(double userX) {
			double centerXPixel = projection.longitudeToPixel(getCenterPosition().getLongitude());
			return -centerXPixel + getPixelWidth() / 2 + projection.longitudeToPixel(userX);
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#userToPixelY(double)
		 */
		@Override
		public double userToPixelY(double userY) {
			double centerYPixel = projection.latitudeToPixel(getCenterPosition().getLatitude());
			return -centerYPixel + getPixelHeight() / 2 + projection.latitudeToPixel(userY);
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#pixelToUserX(double)
		 */
		@Override
		public double pixelToUserX(double pixelX) {
			return 0;
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection#pixelToUserY(double)
		 */
		@Override
		public double pixelToUserY(double pixelY) {
			return 0;
		}

		private static final long serialVersionUID = -7328711106680920578L;

	}

	/**
	 * Time projection define a projection with time definition on one x or y dimension
	 * 
	 * @author sebastien janaud
	 */
	public static abstract class Time extends Linear implements Serializable {

		private static final long serialVersionUID = 5908781043325467240L;

		/**
		 * create a time projection
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
		 * return the minimum date of this time projection
		 * 
		 * @return minimum date
		 */
		public abstract Date getMinDate();

		/**
		 * return the maximum date of this time projection
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
		 * get the time duration in millisecond of this time projection between min
		 * date and max date
		 * 
		 * @return duration millisecond
		 */
		public long durationMillis() {
			return getMaxDate().getTime() - getMinDate().getTime();
		}

		/**
		 * get the time duration in minutes of this time projection between min date
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
		 * get the time duration in hours of this time projection between min date
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
		 * get the time duration in days of this time projection between min date
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
		 * get the time duration in weeks of this time projection between min date
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
		 * get the time duration in month of this time projection between min date
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
	 * The <code>TimeX</code> class defines a {@link Linear} projection projection
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

		
		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Time#getMinDate()
		 */
		@Override
		public Date getMinDate() {
			return getMinXAsDate();
		}

		
		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Time#getMaxDate()
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

		
		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Time#pixelToTime(double)
		 */
		@Override
		public Date pixelToTime(double pixel) {
			double dateMillis = pixelToUserX(pixel);
			return new Date(new Double(dateMillis).longValue());
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Time#timeToPixel(java.util.Date)
		 */
		@Override
		public double timeToPixel(Date time) {
			double userValue = new Long(time.getTime()).doubleValue();
			return userToPixelX(userValue);
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Time#getTimeDurationPixel()
		 */
		@Override
		public int getTimeDurationPixel() {
			return getDevice2D().getDeviceWidth();
		}

		/**
		 * bound this {@link TimeX} projection with given times min and max date for
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
	 * The <code>TimeY</code> class defines a {@link Linear}  projection
	 * with timing bounds definition on y dimension
	 */
	public static class TimeY extends Time implements Serializable {

		/**
		 * create a projection with explicit time on y dimension
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

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Time#getMinDate()
		 */
		@Override
		public Date getMinDate() {
			return getMinYAsDate();
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Time#getMaxDate()
		 */
		@Override
		public Date getMaxDate() {
			return getMaxYAsDate();
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Time#pixelToTime(double)
		 */
		@Override
		public Date pixelToTime(double pixel) {
			double dateMillis = pixelToUserY(pixel);
			return new Date(new Double(dateMillis).longValue());
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Time#timeToPixel(java.util.Date)
		 */
		@Override
		public double timeToPixel(Date time) {
			double userValue = new Long(time.getTime()).doubleValue();
			return userToPixelY(userValue);
		}

		/* (non-Javadoc)
		 * @see org.jensoft.core.projection.Projection.Time#getTimeDurationPixel()
		 */
		@Override
		public int getTimeDurationPixel() {
			return getDevice2D().getDeviceHeight();
		}

		/**
		 * bound this {@link TimeY} projection with given times min and max date for
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
	 * projection with no scalar on x and linear y projection.
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
	 * projection with no scalar on x and linear y projection.
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
	protected Projection() {
	}

	/** device component */
	private Device device2D;

	/** parent view */
	private View view;

	/** projection id */
	private String projID;

	/** projection name */
	private String name;

	/** projection color theme */
	private Color themeColor;

	/** visible flag */
	private boolean visible = true;

	/** lock active */
	private boolean lockActive;

	/** registered projection listener */
	protected EventListenerList listenerList = new EventListenerList();

	/** plug in registry */
	private List<AbstractPlugin> plugins = new ArrayList<AbstractPlugin>();

	/**
	 * @return the projID
	 */
	public String getProjectionId() {
		return projID;
	}

	/**
	 * @param projId
	 *            the projection id to set
	 */
	public void setProjectionId(String projID) {
		this.projID = projID;
	}

	/**
	 * get the projection name
	 * 
	 * @return the projection name
	 */
	public String getName() {
		return name;
	}

	/***
	 * set the projection name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get the registered device2d of this projection
	 * 
	 * @return  device
	 */
	public Device getDevice2D() {
		return device2D;
	}

	/**
	 * set the device2D for this projection
	 * 
	 * @param device
	 */
	public void setDevice2D(Device device) {
		this.device2D = device;
	}

	/**
	 * get the view that host this projection
	 * 
	 * @return view host.
	 */
	public View getView() {
		return view;
	}

	/**
	 * set view that host this projection
	 * 
	 * @param view
	 *            the host view
	 */
	public void setView(View view) {
		this.view = view;
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
	 * return true if this projection is visible, false otherwise
	 * 
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * set projection visible
	 * 
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * lock this projection
	 */
	public void lockActive() {
		lockActive = true;
		fireProjectionActivate();
	}

	/**
	 * unlock this projection
	 */
	public void unlockActive() {
		lockActive = false;
		fireProjectionPassivate();
	}

	/**
	 * return true if this projection is locked, false otherwise
	 * 
	 * @return the projection lock
	 */
	public boolean isLockActive() {
		return lockActive;
	}

	/**
	 * add projection listener
	 * 
	 * @param listener
	 */
	public void addProjectionListener(ProjectionListener listener) {
		listenerList.add(ProjectionListener.class, listener);
	}

	/**
	 * remove projection listener
	 * 
	 * @param listener
	 */
	public void removeProjectionListener(ProjectionListener listener) {
		listenerList.remove(ProjectionListener.class, listener);
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
		plugin.setProjection(this);
		plugin.addPluginListener(this);
		plugins.add(plugin);
		plugin.onProjectionRegister();
	}

	/**
	 * call on {@link View} register this projection
	 */
	public void onViewRegister() {
		getView().addViewListener(new ViewAdapter() {

			@Override
			public void viewResized(ViewEvent view2dEvent) {
				fireProjectionResized();
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

	/* (non-Javadoc)
	 * @see org.jensoft.core.plugin.PluginListener#pluginSelected(org.jensoft.core.plugin.PluginEvent)
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

	/* (non-Javadoc)
	 * @see org.jensoft.core.plugin.PluginListener#pluginUnlockSelected(org.jensoft.core.plugin.PluginEvent)
	 */
	@Override
	public void pluginUnlockSelected(PluginEvent te) {
	}

	/**
	 * fire listener that the projection became active
	 */
	private void fireProjectionActivate() {
		ProjectionEvent w2dEvent = new ProjectionEvent(this);

		Object[] listeners = listenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ProjectionListener.class) {
					((ProjectionListener) listeners[i + 1]).projectionLockActive(w2dEvent);
				}
			}
		}
	}

	/**
	 * fire listener that the projection became passive
	 */
	private void fireProjectionPassivate() {
		ProjectionEvent w2dEvent = new ProjectionEvent(this);
		Object[] listeners = listenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ProjectionListener.class) {
					((ProjectionListener) listeners[i + 1]).projectionUnlockActive(w2dEvent);
				}
			}
		}
	}

	/**
	 * fire listener that the projection has changed
	 */
	private void fireProjectionBoundChanged() {
		ProjectionEvent w2dEvent = new ProjectionEvent(this);
		Object[] listeners = listenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ProjectionListener.class) {
					((ProjectionListener) listeners[i + 1]).projectionBoundChanged(w2dEvent);
				}
			}
		}
	}

	/**
	 * fire listener that the projection has changed
	 */
	private void fireProjectionResized() {
		ProjectionEvent w2dEvent = new ProjectionEvent(this);
		Object[] listeners = listenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == ProjectionListener.class) {
					((ProjectionListener) listeners[i + 1]).projectionResized(w2dEvent);
				}
			}
		}
	}

	/**
	 * check if point is include in user projection
	 * 
	 * @param p2dUser
	 * @return true if the user point intercept projection, false otherwise
	 */
	public boolean intercept(Point2D p2dUser) {
		return interceptX(p2dUser.getX()) && interceptY(p2dUser.getY());
	}

	/**
	 * check if point is include in projection x user range
	 * 
	 * @param userX
	 *            the x value in user coordinate system
	 * @return true if the user x range intercept projection, false otherwise
	 */
	public boolean interceptX(double userX) {
		if (userX > getMaxX() || userX < getMinX()) {
			return false;
		}
		return true;
	}

	/**
	 * check if point is include in projection y user range
	 * 
	 * @param userY
	 *            the y value in user coordinate system
	 * @return true if the user y range intercept projection, false otherwise
	 */
	public boolean interceptY(double userY) {
		if (userY > getMaxY() || userY < getMinY()) {
			return false;
		}
		return true;
	}

	/**
	 * return the user width of this projection
	 * 
	 * @return user width
	 */
	public double getUserWidth() {
		return getMaxX() - getMinX();
	}

	/**
	 * get the pixel with of this projection
	 * 
	 * @return pixel width
	 */
	public double getPixelWidth() {
		return getDevice2D().getDeviceWidth();
	}

	/**
	 * return the user height of this projection
	 * 
	 * @return user height
	 */
	public double getUserHeight() {
		return getMaxY() - getMinY();
	}

	/**
	 * get the pixel height of this projection
	 * 
	 * @return pixel height
	 */
	public double getPixelHeight() {
		return getDevice2D().getDeviceHeight();
	}
	

	/**
	 * Returns the user minimum X coordinate of this <code>Projection</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the user minimum X coordinate of this <code>Projection</code>.
	 */
	public abstract double getMinX();

	/**
	 * Returns the user minimum Y coordinate of this <code>Projection</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the user minimum Y coordinate of this <code>Projection</code>.
	 */
	public abstract double getMinY();

	/**
	 * Returns the user maximum X coordinate of this <code>Projection</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the user maximum X coordinate of this <code>Projection</code>.
	 */
	public abstract double getMaxX();

	/**
	 * Returns the user maximum Y coordinate of this <code>Projection</code> in
	 * <code>double</code> precision.
	 * 
	 * @return the user maximum Y coordinate of this <code>Projection</code>.
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
