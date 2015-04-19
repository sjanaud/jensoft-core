/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.JComponent;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.metrics.format.IMetricsFormat;
import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.Metrics.Gravity;
import com.jensoft.core.plugin.metrics.geom.Metrics.MarkerPosition;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.plugin.metrics.manager.AbstractMetricsManager;
import com.jensoft.core.plugin.metrics.manager.FlowMetricsManager;
import com.jensoft.core.plugin.metrics.manager.FreeMetricsManager;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManager;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManager.MetricsModel;
import com.jensoft.core.plugin.metrics.manager.StaticMetricsManager;
import com.jensoft.core.plugin.metrics.manager.TimeMetricsManager;
import com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeDurationMetrics;
import com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel;
import com.jensoft.core.plugin.metrics.painter.MetricsGlyphPainter;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>AxisMetricsPlugin</code> takes the responsibility to draw metrics along the given  {@link Axis}
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public abstract class AxisMetricsPlugin<M extends AbstractMetricsManager> extends MetricsPlugin<M> {


	/** the accessible zone */
	private Axis axis;

	/** the axis spacing */
	private int axisSpacing = 0;

	

	/**
	 * <code>StaticMetrics</code> takes the responsibility to manage static
	 * metrics
	 * 
	 * @author sebastien janaud
	 */
	public static class StaticMetrics extends AxisMetricsPlugin<StaticMetricsManager> {

		/**
		 * <code>W</code> manages {@link StaticMetrics} for
		 * {@link Axis#AxisWest}
		 */
		public static class W extends StaticMetrics {

			/**
			 * Create {@link StaticMetrics} for {@link Axis#AxisWest}
			 * 
			 * @param metricsCount
			 */
			public W(int metricsCount) {
				super(metricsCount, Axis.AxisWest);
			}
		}

		/**
		 * <code>E</code> manages {@link StaticMetrics} for
		 * {@link Axis#AxisEast}
		 */
		public static class E extends StaticMetrics {

			/**
			 * Create {@link StaticMetrics} for {@link Axis#AxisEast}
			 * 
			 * @param metricsCount
			 */
			public E(int metricsCount) {
				super(metricsCount, Axis.AxisEast);
			}
		}

		/**
		 * <code>N</code> manages {@link StaticMetrics} for
		 * {@link Axis#AxisNorth}
		 */
		public static class N extends StaticMetrics {

			/**
			 * Create {@link StaticMetrics} for {@link Axis#AxisNorth}
			 * 
			 * @param metricsCount
			 */
			public N(int metricsCount) {
				super(metricsCount, Axis.AxisNorth);
			}
		}

		/**
		 * <code>S</code> manages {@link StaticMetrics} for
		 * {@link Axis#AxisSouth}
		 */
		public static class S extends StaticMetrics {

			/**
			 * Create {@link StaticMetrics} for {@link Axis#AxisSouth}
			 * 
			 * @param metricsCount
			 */
			public S(int metricsCount) {
				super(metricsCount, Axis.AxisSouth);
			}
		}

		/**
		 * Create {@link StaticMetrics} with the given metrics count and
		 * specified {@link Axis}
		 * 
		 * @param metricsCount
		 * @param axis
		 */
		public StaticMetrics(int metricsCount, Axis axis) {
			super(new StaticMetricsManager(metricsCount), axis);
		}

	}

	/**
	 * <code>FreeMetrics</code> takes the responsibility to manage free metrics
	 * 
	 * @author Sebastien Janaud
	 */
	public static class FreeMetrics extends AxisMetricsPlugin<FreeMetricsManager> {

		/**
		 * <code>W</code> manages {@link FreeMetrics} for {@link Axis#AxisWest}
		 */
		public static class W extends FreeMetrics {

			/**
			 * Create {@link FreeMetrics} for {@link Axis#AxisWest}
			 */
			public W() {
				super(Axis.AxisWest);
			}
		}

		/**
		 * <code>E</code> manages {@link FreeMetrics} for {@link Axis#AxisEast}
		 */
		public static class E extends FreeMetrics {

			/**
			 * Create {@link FreeMetrics} for {@link Axis#AxisEast}
			 */
			public E() {
				super(Axis.AxisEast);
			}
		}

		/**
		 * <code>N</code> manages {@link FreeMetrics} for {@link Axis#AxisNorth}
		 */
		public static class N extends FreeMetrics {

			/**
			 * Create {@link FreeMetrics} for {@link Axis#AxisNorth}
			 */
			public N() {
				super(Axis.AxisNorth);
			}
		}

		/**
		 * <code>S</code> manages {@link FreeMetrics} for {@link Axis#AxisSouth}
		 */
		public static class S extends FreeMetrics {

			/**
			 * Create {@link FreeMetrics} for {@link Axis#AxisSouth}
			 */
			public S() {
				super(Axis.AxisSouth);
			}
		}

		/**
		 * create a new FreeMetrics layout for the specified projection zone
		 * 
		 * @param axis
		 *            the metrics axis
		 */
		public FreeMetrics(Axis axis) {
			super(new FreeMetricsManager(), axis);
		}

		/**
		 * add the specified metrics to the manager
		 * 
		 * @param m
		 *            the metrics to add.
		 */
		public void addMetrics(double m) {
			getMetricsManager().addMetrics(m);
		}

		/**
		 * add the specified metrics as a symbol
		 * 
		 * @param m
		 *            the metrics to add
		 * @param label
		 *            the symbol associate to this metrics
		 */
		public void addMetrics(double m, String label) {
			getMetricsManager().addMetrics(m, label);
		}

	}

	/**
	 * <code>FlowMetrics</code> takes the responsibility to manage flow metrics
	 * 
	 * @author Sebastien Janaud
	 */
	public static class FlowMetrics extends AxisMetricsPlugin<FlowMetricsManager> {

		/**
		 * <code>W</code> manages {@link FlowMetrics} for {@link Axis#AxisWest}
		 */
		public static class W extends FlowMetrics {

			/**
			 * Create {@link FlowMetrics} for {@link Axis#AxisWest} with the
			 * given flow parameters
			 * 
			 * @param flowStart
			 * @param flowEnd
			 * @param flowInterval
			 */
			public W(double flowStart, double flowEnd, double flowInterval) {
				super(flowStart, flowEnd, flowInterval, Axis.AxisWest);
			}
		}

		/**
		 * <code>E</code> manages {@link FlowMetrics} for {@link Axis#AxisEast}
		 */
		public static class E extends FlowMetrics {

			/**
			 * Create {@link FlowMetrics} for {@link Axis#AxisEast} with the
			 * given flow parameters
			 * 
			 * @param flowStart
			 * @param flowEnd
			 * @param flowInterval
			 */
			public E(double flowStart, double flowEnd, double flowInterval) {
				super(flowStart, flowEnd, flowInterval, Axis.AxisEast);
			}
		}

		/**
		 * <code>N</code> manages {@link FlowMetrics} for {@link Axis#AxisNorth}
		 */
		public static class N extends FlowMetrics {

			/**
			 * Create {@link FlowMetrics} for {@link Axis#AxisNorth} with the
			 * given flow parameters
			 * 
			 * @param flowStart
			 * @param flowEnd
			 * @param flowInterval
			 */
			public N(double flowStart, double flowEnd, double flowInterval) {
				super(flowStart, flowEnd, flowInterval, Axis.AxisNorth);
			}
		}

		/**
		 * <code>S</code> manages {@link FlowMetrics} for {@link Axis#AxisSouth}
		 */
		public static class S extends FlowMetrics {

			/**
			 * Create {@link FlowMetrics} for {@link Axis#AxisSouth} with the
			 * given flow parameters
			 * 
			 * @param flowStart
			 * @param flowEnd
			 * @param flowInterval
			 */
			public S(double flowStart, double flowEnd, double flowInterval) {
				super(flowStart, flowEnd, flowInterval, Axis.AxisSouth);
			}
		}

		/**
		 * create a new Flow metrics from start to end values with the specified
		 * interval and the specified axis
		 * 
		 * @param flowStart
		 *            the start flow coordinate
		 * @param flowEnd
		 *            the end flow coordinate
		 * @param flowInterval
		 *            the flow interval
		 * @param axis
		 *            the metrics axis
		 */
		public FlowMetrics(double flowStart, double flowEnd, double flowInterval, Axis axis) {
			super(new FlowMetricsManager(flowStart, flowEnd, flowInterval), axis);

		}

		/**
		 * set the flow start value
		 * 
		 * @param flowStart
		 */
		public void setFlowStart(double flowStart) {
			getMetricsManager().setFlowStart(flowStart);
		}

		/**
		 * set the flow end value
		 * 
		 * @param flowEnd
		 */
		public void setFlowEnd(double flowEnd) {
			getMetricsManager().setFlowEnd(flowEnd);
		}

		/**
		 * set the flow interval value
		 * 
		 * @param flowInterval
		 */
		public void setFlowInterval(double flowInterval) {
			getMetricsManager().setFlowInterval(flowInterval);
		}

		/**
		 * get the flow start value
		 * 
		 * @return the flow start
		 */
		public double getFlowStart() {
			return getMetricsManager().getFlowStart();
		}

		/**
		 * get the flow end value
		 * 
		 * @return the flow end
		 */
		public double getFlowEnd() {
			return getMetricsManager().getFlowEnd();
		}

		/**
		 * get the flow interval value
		 * 
		 * @return the flow interval
		 */
		public double getFlowInterval() {
			return getMetricsManager().getFlowInterval();
		}

	}

	



	/**
	 * <code>TimeMetrics</code> takes the responsibility to manage timing
	 * metrics on axis
	 * 
	 * @author sebastien janaud
	 */
	public static class TimeMetrics extends AxisMetricsPlugin<TimeMetricsManager> {

		/**
		 * <code>W</code> manages {@link TimeMetrics} for {@link Axis#AxisWest}
		 */
		public static class W extends TimeMetrics {

			/**
			 * Create {@link TimeMetrics} for {@link Axis#AxisWest}
			 */
			public W() {
				super(Axis.AxisWest);
			}
		}

		/**
		 * <code>E</code> manages {@link TimeMetrics} for {@link Axis#AxisEast}
		 */
		public static class E extends TimeMetrics {

			/**
			 * Create {@link TimeMetrics} for {@link Axis#AxisEast}
			 */
			public E() {
				super(Axis.AxisEast);
			}
		}

		/**
		 * <code>N</code> manages {@link TimeMetrics} for {@link Axis#AxisNorth}
		 */
		public static class N extends TimeMetrics {

			/**
			 * Create {@link TimeMetrics} for {@link Axis#AxisNorth}
			 */
			public N() {
				super(Axis.AxisNorth);
			}
		}

		/**
		 * <code>S</code> manages {@link TimeMetrics} for {@link Axis#AxisSouth}
		 */
		public static class S extends TimeMetrics {

			/**
			 * Create {@link TimeMetrics} for {@link Axis#AxisSouth}
			 */
			public S() {
				super(Axis.AxisSouth);
			}
		}

		/**
		 * create time metrics plug-in
		 */
		public TimeMetrics(Axis axis) {
			super(new TimeMetricsManager(), axis);
			setName(TimeMetrics.class.getCanonicalName());
		}

		/**
		 * register the given {@link TimeModel}
		 * 
		 * @param model
		 *            the model to register
		 */
		public void registerTimeModel(TimeModel model) {
			getMetricsManager().registerTimingModel(model);
		}

		/**
		 * register the given {@link TimeModel} array
		 * 
		 * @param models
		 *            the models to register
		 */
		public void registerTimeModels(TimeModel... models) {
			getMetricsManager().registerTimingModels(models);
		}

		/**
		 * register the given {@link TimeModel} list
		 * 
		 * @param models
		 *            the models to register
		 */
		public void registerTimeModels(List<TimeModel> models) {
			getMetricsManager().registerTimingModels(models);
		}

		/**
		 * unregister the given {@link TimeModel}
		 * 
		 * @param model
		 *            the model to remove
		 */
		public void unregisterTimeModel(TimeModel model) {
			getMetricsManager().unregisterTimingModel(model);
		}

		/**
		 * unregister the given {@link TimeModel} array
		 * 
		 * @param models
		 *            the time models to remove
		 */
		public void unregisterTimeModels(TimeModel... models) {
			getMetricsManager().unregisterTimingModels(models);
		}

		/**
		 * unregister the given {@link TimeModel} list
		 * 
		 * @param models
		 *            the time models to remove
		 */
		public void unregisterTimeModels(List<TimeModel> models) {
			getMetricsManager().unregisterTimingModels(models);
		}

		/**
		 * get all registered {@link TimeModel}
		 * 
		 * @return models
		 */
		public List<TimeModel> getTimingModels() {
			return getMetricsManager().getTimingModels();
		}

		
		/* (non-Javadoc)
		 * @see com.jensoft.core.plugin.metrics.AxisMetricsPlugin#paintMetrics(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
		 */
		@Override
		public void paintMetrics(View view, Graphics2D g2d, ViewPart viewPart) {
			if (!super.isAccessible(viewPart)) {
				return;
			}
			super.assignType();
			getMetricsManager().setMetricsPlugin(this);
			getMetricsPainter().setMetricsPlugin(this);
			int axisSpacing = 0;
			List<TimeModel> sequence = ((TimeMetricsManager) getMetricsManager()).getTimingSequence();
			for (TimeModel timingManager : sequence) {


				List<Metrics> metrics = timingManager.generateMetrics();

				Collections.sort(metrics, Metrics.getComparator());

				for (int i = 0; i < metrics.size(); i++) {
					Metrics m = metrics.get(i);

					m.setLockMarker(true);
					if (axisSpacing > 0) {
						m.setLockMarker(false);
					}
					if (m instanceof TimeDurationMetrics) {
						m.setLockMarker(false);
					}

					Point2D markerLocation = new Point2D.Double();
					if (viewPart == ViewPart.South) {
						markerLocation = new Point2D.Double(view.getPlaceHolderAxisWest() + m.getDeviceValue(), axisSpacing);
						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.S);
					}
					if (viewPart == ViewPart.West) {
						JComponent component = view.getViewPartComponent(ViewPart.West);
						markerLocation = new Point2D.Double(component.getWidth() - 1 - axisSpacing, m.getDeviceValue());
						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.W);
					}
					if (viewPart == ViewPart.East) {
						markerLocation = new Point2D.Double(axisSpacing, m.getDeviceValue());
						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.E);
					}
					if (viewPart == ViewPart.North) {
						JComponent component = view.getViewPartComponent(ViewPart.North);
						markerLocation = new Point2D.Double(view.getPlaceHolderAxisWest() + m.getDeviceValue(), component.getHeight() - 1 - axisSpacing);
						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.N);
					}
				}

				getMetricsPainter().doPaintMetrics(g2d, metrics);
				axisSpacing = axisSpacing + timingManager.getPixelAxisHolder();

			}
		}
	}
	
	/**
	 * <code>ModeledMetrics</code> takes the responsibility to manage metrics
	 * from models
	 * 
	 * @author sebastien janaud
	 */
	public static class ModeledMetrics extends AxisMetricsPlugin<ModeledMetricsManager> {

		/**
		 * <code>W</code> manages {@link ModeledMetrics} for
		 * {@link Axis#AxisWest}
		 */
		public static class W extends ModeledMetrics {

			/**
			 * Create {@link ModeledMetrics} for {@link Axis#AxisWest}
			 */
			public W() {
				super(Axis.AxisWest);
			}
		}

		/**
		 * <code>E</code> manages {@link ModeledMetrics} for
		 * {@link Axis#AxisEast}
		 */
		public static class E extends ModeledMetrics {

			/**
			 * Create {@link ModeledMetrics} for {@link Axis#AxisEast}
			 */
			public E() {
				super(Axis.AxisEast);
			}
		}

		/**
		 * <code>N</code> manages {@link ModeledMetrics} for
		 * {@link Axis#AxisNorth}
		 */
		public static class N extends ModeledMetrics {

			/**
			 * Create {@link ModeledMetrics} for {@link Axis#AxisNorth}
			 */
			public N() {
				super(Axis.AxisNorth);
			}
		}

		/**
		 * <code>S</code> manages {@link ModeledMetrics} for
		 * {@link Axis#AxisSouth}
		 */
		public static class S extends ModeledMetrics {

			/**
			 * Create {@link ModeledMetrics} for {@link Axis#AxisSouth}
			 */
			public S() {
				super(Axis.AxisSouth);
			}
		}

		/**
		 * create ModeledMetrics with the given {@link Axis}
		 */
		public ModeledMetrics(Axis axis) {
			super(new ModeledMetricsManager(), axis);
			setName(ModeledMetrics.class.getCanonicalName());
		}

		
		/**
		 * set interval density factor, ideal value is O, 10, 20 pixel for condensed to more and more low density.
		 * this interval is take in account on metrics solving algorithm.
		 * 
		 * you can also use {@link #setMetricsDensity(MetricsDensity)} with Low or Condensed strategy
		 * 
		 * @param intervalDensity
		 */
		public void setMetricsIntervalDensity(int intervalDensity){
			getMetricsManager().setIntervalDensity(intervalDensity);
		}
		
		
		/**
		 * set median metrics option
		 * @param medianOption
		 */
		public void setMedianMetricsOption(boolean medianOption){
			getMetricsManager().setMedianMetricsOption(medianOption);
		}
		
		/**
		 * set minor metrics option
		 * @param minorOption
		 */
		public void setMinorMetricsOption(boolean minorOption){
			getMetricsManager().setMinorMetricsOption(minorOption);
		}
		
		/**
		 * set median density threshold
		 * @param medianMetricsDensityThreshold
		 */
		public void setMedianMetricsDensityThreshold(double medianMetricsDensityThreshold){
			getMetricsManager().setMedianMetricsDensityThreshold(medianMetricsDensityThreshold);
		}
		
		/**
		 * set minor density threshold
		 * @param minorMetricsDensityThreshold
		 */
		public void setMinorMetricsDensityThreshold(double minorMetricsDensityThreshold){
			getMetricsManager().setMinorMetricsDensityThreshold(minorMetricsDensityThreshold);
		}
		

		/**
		 * get all registered {@link MetricsModel}
		 * 
		 * @return models
		 */
		public List<MetricsModel> getMetricsModels() {
			return getMetricsManager().getMetricsModels();
		}	
	}



	/**
	 * Axis Nature
	 * 
	 * @author sebastien janaud
	 */
	public enum Axis {
		AxisSouth("AxisSouth"), AxisNorth("AxisNorth"), AxisWest("AxisWest"), AxisEast("AxisEast");

		private String name;

		private Axis(String name) {
			this.name = name;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * parse string representation of this axis constant
		 * 
		 * @param axisName
		 * @return axis
		 */
		public static Axis parse(String axisName) {
			if (axisName == null) {
				return null;
			}
			if (axisName.equalsIgnoreCase(AxisSouth.getName())) {
				return AxisSouth;
			}
			if (axisName.equalsIgnoreCase(AxisNorth.getName())) {
				return AxisNorth;
			}
			if (axisName.equalsIgnoreCase(AxisWest.getName())) {
				return AxisWest;
			}
			if (axisName.equalsIgnoreCase(AxisEast.getName())) {
				return AxisEast;
			}
			return null;
		}
	}

	/**
	 * Create a new
	 * <code>AxisMetricsPlugin<code> with the specified manager and axis
	 * 
	 * @param manager
	 *            the manager
	 * @param axis
	 *            the axis
	 */
	public AxisMetricsPlugin(M manager, Axis axis) {
		super(manager);
		this.axis = axis;
		setPriority(1000);
		setTextAntialising(TextAntialiasing.On);
		setAntialiasing(Antialiasing.On);
	}


	/**
	 * delegate method to set the metrics format
	 * 
	 * @param format
	 *            the IMetricsFormat to use for format metrics value
	 */
	public void setMetricsFormat(IMetricsFormat format) {
		getMetricsManager().setMetricsFormat(format);
	}

	/**
	 * delegate method to get the metrics format
	 * 
	 * @return format the IMetricsFormat use for format metrics value
	 */
	public IMetricsFormat getMetricsFormat() {
		return getMetricsManager().getMetricsFormat();
	}

	/**
	 * get the axis spacing from device border
	 * 
	 * @return the axis spacing
	 */
	public int getAxisSpacing() {
		return axisSpacing;
	}

	/**
	 * the axis spacing
	 * 
	 * @param axisSpacing
	 */
	public void setAxisSpacing(int axisSpacing) {
		this.axisSpacing = axisSpacing;
	}

	/**
	 * get the axis holder
	 * 
	 * @return the axis
	 */
	public Axis getAxis() {
		return axis;
	}

	/**
	 * set the axis holder
	 * 
	 * @param axis
	 */
	public void setAxis(Axis axis) {
		this.axis = axis;
	}

	/**
	 * true if the context is accessible, false otherwise
	 * 
	 * @param viewPart
	 * @return true if the context is accessible, false otherwise
	 */
	protected boolean isAccessible(ViewPart viewPart) {
		if(viewPart == ViewPart.View)
			return true;
		if (axis == Axis.AxisSouth && viewPart != ViewPart.South) {
			return false;
		}
		if (axis == Axis.AxisNorth && viewPart != ViewPart.North) {
			return false;
		}
		if (axis == Axis.AxisWest && viewPart != ViewPart.West) {
			return false;
		}
		if (axis == Axis.AxisEast && viewPart != ViewPart.East) {
			return false;
		}
		if (viewPart == ViewPart.Device) {
			return false;
		}

		return true;
	}

	/**
	 * paint the base line
	 * 
	 * @param view
	 * @param g2d
	 * @param viewPart
	 */
	protected void paintBaseLine(View view, Graphics2D g2d, ViewPart viewPart) {
		if (isBaseLinePaint()) {
			Point2D axisStartLocation = new Point2D.Double();
			Point2D axisEndLocation = new Point2D.Double();
			if (viewPart == ViewPart.South) {
				JComponent component = view.getViewPartComponent(ViewPart.South);
				axisStartLocation = new Point2D.Double(view.getPlaceHolderAxisWest(), axisSpacing);
				axisEndLocation = new Point2D.Double(component.getWidth() - view.getPlaceHolderAxisEast(), axisSpacing);
			}
			if (viewPart == ViewPart.West) {
				JComponent component = view.getViewPartComponent(ViewPart.West);
				axisStartLocation = new Point2D.Double(component.getWidth() - 1 - axisSpacing, 0);
				axisEndLocation = new Point2D.Double(component.getWidth() - 1 - axisSpacing, component.getHeight());
			}
			if (viewPart == ViewPart.East) {
				JComponent component = view.getViewPartComponent(ViewPart.East);
				axisStartLocation = new Point2D.Double(axisSpacing, 0);
				axisEndLocation = new Point2D.Double(axisSpacing, component.getHeight());
			}
			if (viewPart == ViewPart.North) {
				JComponent component = view.getViewPartComponent(ViewPart.North);
				axisStartLocation = new Point2D.Double(view.getPlaceHolderAxisWest(), component.getHeight() - 1 - axisSpacing);
				axisEndLocation = new Point2D.Double(component.getWidth() - view.getPlaceHolderAxisEast(), component.getHeight() - 1 - axisSpacing);
			}

			Color axisBaseLineColor;
			if (getBaseLineColor() != null) {
				axisBaseLineColor = getBaseLineColor();
			} else {
				axisBaseLineColor = getProjection().getThemeColor();
			}
			getMetricsPainter().doPaintLineMetrics(g2d, axisStartLocation, axisEndLocation, axisBaseLineColor);
		}
	}

	/**
	 * paint metrics
	 * 
	 * @param v2d
	 * @param g2d
	 * @param viewPart
	 */
	protected void paintMetricsLabelIndicator(View v2d, Graphics2D g2d, ViewPart viewPart) {
		
		List<Metrics> metrics = null;
		try {
			metrics = getMetricsManager().getDeviceMetrics();
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}

		Collections.sort(metrics, Metrics.getComparator());

		for (int i = 0; i < metrics.size(); i++) {

			Metrics m = metrics.get(i);		
			Point2D markerLocation = new Point2D.Double();

			if (viewPart == ViewPart.South) {
				markerLocation = new Point2D.Double(v2d.getPlaceHolderAxisWest() + m.getDeviceValue(), axisSpacing);
				m.setMarkerLocation(markerLocation);
				m.setMarkerPosition(MarkerPosition.S);
			}
			if (viewPart == ViewPart.West) {
				JComponent component = v2d.getViewPartComponent(ViewPart.West);
				markerLocation = new Point2D.Double(component.getWidth() - 1 - axisSpacing, m.getDeviceValue());
				m.setMarkerLocation(markerLocation);
				m.setMarkerPosition(MarkerPosition.W);
			}
			if (viewPart == ViewPart.East) {
				markerLocation = new Point2D.Double(axisSpacing, m.getDeviceValue());
				m.setMarkerLocation(markerLocation);
				m.setMarkerPosition(MarkerPosition.E);
			}
			if (viewPart == ViewPart.North) {
				JComponent component = v2d.getViewPartComponent(ViewPart.North);
				markerLocation = new Point2D.Double(v2d.getPlaceHolderAxisWest() + m.getDeviceValue(), component.getHeight() - 1 - axisSpacing);
				m.setMarkerLocation(markerLocation);
				m.setMarkerPosition(MarkerPosition.N);
			}
		}
		getMetricsPainter().doPaintMetrics(g2d, metrics);
	}

	

	/**
	 * assign manager type x or y given by given axis.
	 */
	protected void assignType() {
		if (axis == Axis.AxisSouth || axis == Axis.AxisNorth) {
			getMetricsManager().setMetricsType(MetricsType.XMetrics);
		}
		if (axis == Axis.AxisEast || axis == Axis.AxisWest) {
			getMetricsManager().setMetricsType(MetricsType.YMetrics);
		}
	}

	/**
	 * paint metrics
	 * @param view
	 * @param g2d
	 * @param viewPart
	 */
	protected void paintMetrics(View view, Graphics2D g2d, ViewPart viewPart) {
		if (!isAccessible(viewPart)) {
			return;
		}
		assignType();
		getMetricsManager().setMetricsPlugin(this);
		getMetricsPainter().setMetricsPlugin(this);
		
		paintMetricsLabelIndicator(view, g2d, viewPart);
		paintBaseLine(view, g2d, viewPart);
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
	 */
	@Override
	public final void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
		paintMetrics(view, g2d, viewPart);
	}

}
