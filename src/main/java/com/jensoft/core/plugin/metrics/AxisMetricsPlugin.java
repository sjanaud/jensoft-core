/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.swing.JComponent;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.metrics.format.IMetricsFormat;
import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.Metrics.Gravity;
import com.jensoft.core.plugin.metrics.geom.Metrics.MarkerPosition;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.plugin.metrics.geom.MetricsRenderContext;
import com.jensoft.core.plugin.metrics.manager.AbstractMetricsManager;
import com.jensoft.core.plugin.metrics.manager.FlowMetricsManager;
import com.jensoft.core.plugin.metrics.manager.FreeMetricsManager;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManager;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManager.MetricsModel;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManagerOLD;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManagerOLD.MetricsModelCollections;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManagerOLD.MetricsModelOLD;
import com.jensoft.core.plugin.metrics.manager.Multiplier3MetricsManager;
import com.jensoft.core.plugin.metrics.manager.MultiplierMetricsManager;
import com.jensoft.core.plugin.metrics.manager.StaticMetricsManager;
import com.jensoft.core.plugin.metrics.manager.TimeMetricsManager;
import com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeDurationMetrics;
import com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel;
import com.jensoft.core.plugin.metrics.painter.AbstractMetricsPainter;
import com.jensoft.core.plugin.metrics.painter.MetricsGlyphPainter;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>AxisMetricsPlugin</code> takes the responsibility to draw metrics for
 * {@link Axis}
 * 
 * @author sebastien janaud
 */
public abstract class AxisMetricsPlugin<M extends AbstractMetricsManager> extends AbstractPlugin {

	/** the metrics manager */
	private M metricsManager;

	/** the metrics painter */
	private AbstractMetricsPainter metricsPainter;

	/** the accessible zone */
	private Axis axis;

	/** the axis spacing */
	private int axisSpacing = 0;

	/** paint flag axis base line, default is false */
	private boolean paintAxisBaseLine = false;

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
		 * create a new FreeMetrics layout for the specified window zone
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
	 * <code>MultiplierMetrics</code> takes the responsibility to manage metrics
	 * with 1 multiplier
	 * <p style="color : red;">
	 * WARNING : can be reboot on to much iteration.
	 * </p>
	 * <p>
	 * You can use this manager when you build some static views without window
	 * bound change<br>
	 * it is discourage if you use tools that dynamically change the window
	 * bounds because the manager can be reboot.
	 * <p>
	 * 
	 * @author Sebastien Janaud
	 */
	public static class MultiplierMetrics extends AxisMetricsPlugin<MultiplierMetricsManager> {

		/**
		 * <code>W</code> manages {@link MultiplierMetrics} for
		 * {@link Axis#AxisWest}
		 */
		public static class W extends MultiplierMetrics {

			/**
			 * Create {@link MultiplierMetrics} for {@link Axis#AxisWest} with
			 * the given multiplier parameter
			 * 
			 * @param ref
			 * @param multiplier
			 */
			public W(double ref, double multiplier) {
				super(ref, multiplier, Axis.AxisWest);
			}
		}

		/**
		 * <code>W</code> manages {@link MultiplierMetrics} for
		 * {@link Axis#AxisEast}
		 */
		public static class E extends MultiplierMetrics {

			/**
			 * Create {@link MultiplierMetrics} for {@link Axis#AxisEast} with
			 * the given multiplier parameter
			 * 
			 * @param ref
			 */
			public E(double ref, double metricsInterval) {
				super(ref, metricsInterval, Axis.AxisEast);
			}
		}

		/**
		 * <code>W</code> manages {@link MultiplierMetrics} for
		 * {@link Axis#AxisNorth}
		 */
		public static class N extends MultiplierMetrics {

			/**
			 * Create {@link MultiplierMetrics} for {@link Axis#AxisNorth} with
			 * the given multiplier parameter
			 * 
			 * @param ref
			 */
			public N(double ref, double metricsInterval) {
				super(ref, metricsInterval, Axis.AxisNorth);
			}
		}

		/**
		 * <code>W</code> manages {@link MultiplierMetrics} for
		 * {@link Axis#AxisSouth}
		 */
		public static class S extends MultiplierMetrics {

			/**
			 * Create {@link MultiplierMetrics} for {@link Axis#AxisSouth} with
			 * the given multiplier parameter
			 * 
			 * @param ref
			 */
			public S(double ref, double metricsInterval) {
				super(ref, metricsInterval, Axis.AxisSouth);
			}
		}

		/**
		 * create metrics multiplier plug-in
		 * 
		 * @param ref
		 *            the reference to use for resolve metrics
		 * @param multiplier
		 *            the metrics interval to factor from the reference into
		 *            window bound metrics
		 * @param axis
		 *            the axis zone to lay out this metrics
		 */
		public MultiplierMetrics(double ref, double multiplier, Axis axis) {
			super(new MultiplierMetricsManager(ref, multiplier), axis);
		}

	}

	/**
	 * <code>MultiMultiplierMetrics</code> takes the responsibility to manage
	 * metrics with 3 multipliers
	 * <p style="color : red;">
	 * WARNING : can be reboot on to much iteration.
	 * </p>
	 * <p>
	 * You can use this manager when you build some static views without window
	 * bound change<br>
	 * it is discourage if you use tools that dynamically change the window
	 * bounds because the manager can be reboot.
	 * <p>
	 * 
	 * @author sebastien janaud
	 */
	public static class Multiplier3Metrics extends AxisMetricsPlugin<Multiplier3MetricsManager> {

		/**
		 * <code>W</code> manages {@link Multiplier3Metrics} for
		 * {@link Axis#AxisWest}
		 */
		public static class W extends Multiplier3Metrics {

			/**
			 * Create {@link Multiplier3Metrics} for {@link Axis#AxisWest}
			 * with the given multiplier parameter
			 * 
			 * @param ref
			 */
			public W(double ref) {
				super(ref, Axis.AxisWest);
			}
		}

		/**
		 * <code>E</code> manages {@link Multiplier3Metrics} for
		 * {@link Axis#AxisEast}
		 */
		public static class E extends Multiplier3Metrics {

			/**
			 * Create {@link Multiplier3Metrics} for {@link Axis#AxisEast}
			 * with the given multiplier parameter
			 * 
			 * @param ref
			 */
			public E(double ref) {
				super(ref, Axis.AxisEast);
			}
		}

		/**
		 * <code>N</code> manages {@link Multiplier3Metrics} for
		 * {@link Axis#AxisNorth}
		 */
		public static class N extends Multiplier3Metrics {

			/**
			 * Create {@link Multiplier3Metrics} for {@link Axis#AxisNorth}
			 * with the given multiplier parameter
			 * 
			 * @param ref
			 */
			public N(double ref) {
				super(ref, Axis.AxisNorth);
			}
		}

		/**
		 * <code>S</code> manages {@link Multiplier3Metrics} for
		 * {@link Axis#AxisSouth}
		 */
		public static class S extends Multiplier3Metrics {

			/**
			 * Create {@link Multiplier3Metrics} for {@link Axis#AxisSouth}
			 * with the given multiplier parameter
			 * 
			 * @param ref
			 */
			public S(double ref) {
				super(ref, Axis.AxisSouth);
			}
		}

		/**
		 * Create a multi multiplier metrics plug-in
		 * <p style="color : red;">
		 * WARNING : can be reboot on to much iteration.
		 * </p>
		 * <p>
		 * You can use this manager when you build some static views without
		 * window bound change<br>
		 * it is discourage if you use tools that dynamically change the window
		 * bounds because the manager can be reboot.
		 * <p>
		 * 
		 * @param ref
		 *            the reference from metrics will be created
		 * @param axis
		 *            the window axis
		 */
		public Multiplier3Metrics(double ref, Axis axis) {
			super(new Multiplier3MetricsManager(ref), axis);
			super.setName(Multiplier3Metrics.class.getCanonicalName());
		}

		/***
		 * set the major multiplier.
		 * 
		 * @param major
		 */
		public void setMajor(double major) {
			getMetricsManager().setMajor(major);
		}

		/**
		 * set the median multiplier
		 * 
		 * @param median
		 */
		public void setMedian(double median) {
			getMetricsManager().setMedian(median);
		}

		/**
		 * set the minor multiplier
		 * 
		 * @param minor
		 */
		public void setMinor(double minor) {
			getMetricsManager().setMinor(minor);
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
			setMetricsPainter(new MetricsGlyphPainter());
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
		 * @see com.jensoft.core.plugin.metrics.AxisMetricsPlugin#paintMetrics(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
		 */
		@Override
		public void paintMetrics(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
			if (!super.isAccessible(windowPart)) {
				return;
			}
			super.createRenderContext(v2d, g2d);
			super.assignType();
			int axisSpacing = 0;
			List<TimeModel> sequence = ((TimeMetricsManager) getMetricsManager()).getTimingSequence();
			for (TimeModel timingManager : sequence) {

				MetricsRenderContext renderContext = new MetricsRenderContext(v2d, getWindow2D(), g2d);
				// renderContext.setMetricsMedianFont(timeFont1);
				// renderContext.setMetricsMajorFont(timeFont1);

				// timingManager.setMetricsMajorFont(timeFont1);
				// timingManager.setMetricsMajorFont(timeFont1);

				getMetricsPainter().setMetricsRenderContext(renderContext);
				getMetricsManager().setRenderContext(renderContext);

				List<Metrics> metrics = timingManager.generateMetrics();

				Collections.sort(metrics, Metrics.getComparator());

				for (int i = 0; i < metrics.size(); i++) {
					Metrics m = metrics.get(i);

					if (i == 0) {
						m.setGravity(Gravity.First);
					}
					if (i == metrics.size() - 1) {
						m.setGravity(Gravity.Last);
					}

					m.setLockMarker(true);
					if (axisSpacing > 0) {
						m.setLockMarker(false);
					}
					if (m instanceof TimeDurationMetrics) {
						m.setLockMarker(false);
					}

					Point2D markerLocation = new Point2D.Double();
					if (windowPart == WindowPart.South) {
						markerLocation = new Point2D.Double(v2d.getPlaceHolderAxisWest() + m.getDeviceValue(), axisSpacing);
						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.S);
					}
					if (windowPart == WindowPart.West) {
						JComponent component = v2d.getWindowComponent(WindowPart.West);
						markerLocation = new Point2D.Double(component.getWidth() - 1 - axisSpacing, m.getDeviceValue());
						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.W);
					}
					if (windowPart == WindowPart.East) {
						markerLocation = new Point2D.Double(axisSpacing, m.getDeviceValue());
						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.E);
					}
					if (windowPart == WindowPart.North) {
						JComponent component = v2d.getWindowComponent(WindowPart.North);
						markerLocation = new Point2D.Double(v2d.getPlaceHolderAxisWest() + m.getDeviceValue(), component.getHeight() - 1 - axisSpacing);
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
		 * <code>W</code> manages {@link ModeledMetricsOLD} for
		 * {@link Axis#AxisWest}
		 */
		public static class W extends ModeledMetrics {

			/**
			 * Create {@link ModeledMetricsOLD} for {@link Axis#AxisWest}
			 */
			public W() {
				super(Axis.AxisWest);
			}
		}

		/**
		 * <code>E</code> manages {@link ModeledMetricsOLD} for
		 * {@link Axis#AxisEast}
		 */
		public static class E extends ModeledMetrics {

			/**
			 * Create {@link ModeledMetricsOLD} for {@link Axis#AxisEast}
			 */
			public E() {
				super(Axis.AxisEast);
			}
		}

		/**
		 * <code>N</code> manages {@link ModeledMetricsOLD} for
		 * {@link Axis#AxisNorth}
		 */
		public static class N extends ModeledMetrics {

			/**
			 * Create {@link ModeledMetricsOLD} for {@link Axis#AxisNorth}
			 */
			public N() {
				super(Axis.AxisNorth);
			}
		}

		/**
		 * <code>S</code> manages {@link ModeledMetricsOLD} for
		 * {@link Axis#AxisSouth}
		 */
		public static class S extends ModeledMetrics {

			/**
			 * Create {@link ModeledMetricsOLD} for {@link Axis#AxisSouth}
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
			setName(ModeledMetricsOLD.class.getCanonicalName());
			setMetricsPainter(new MetricsGlyphPainter());
		}

		/**
		 * set locale
		 * @param locale
		 */
		public void setLocale(Locale locale){
			getMetricsManager().applyLocalizedMetrics(locale);
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
	 * <code>ModeledMetrics</code> takes the responsibility to manage metrics
	 * from models
	 * 
	 * @author sebastien janaud
	 */
	public static class ModeledMetricsOLD extends AxisMetricsPlugin<ModeledMetricsManagerOLD> {

		/**
		 * <code>W</code> manages {@link ModeledMetricsOLD} for
		 * {@link Axis#AxisWest}
		 */
		public static class W extends ModeledMetricsOLD {

			/**
			 * Create {@link ModeledMetricsOLD} for {@link Axis#AxisWest}
			 */
			public W() {
				super(Axis.AxisWest);
			}
		}

		/**
		 * <code>E</code> manages {@link ModeledMetricsOLD} for
		 * {@link Axis#AxisEast}
		 */
		public static class E extends ModeledMetricsOLD {

			/**
			 * Create {@link ModeledMetricsOLD} for {@link Axis#AxisEast}
			 */
			public E() {
				super(Axis.AxisEast);
			}
		}

		/**
		 * <code>N</code> manages {@link ModeledMetricsOLD} for
		 * {@link Axis#AxisNorth}
		 */
		public static class N extends ModeledMetricsOLD {

			/**
			 * Create {@link ModeledMetricsOLD} for {@link Axis#AxisNorth}
			 */
			public N() {
				super(Axis.AxisNorth);
			}
		}

		/**
		 * <code>S</code> manages {@link ModeledMetricsOLD} for
		 * {@link Axis#AxisSouth}
		 */
		public static class S extends ModeledMetricsOLD {

			/**
			 * Create {@link ModeledMetricsOLD} for {@link Axis#AxisSouth}
			 */
			public S() {
				super(Axis.AxisSouth);
			}
		}

		/**
		 * create ModeledMetrics with the given {@link Axis}
		 */
		public ModeledMetricsOLD(Axis axis) {
			super(new ModeledMetricsManagerOLD(), axis);
			setName(ModeledMetricsOLD.class.getCanonicalName());
			setMetricsPainter(new MetricsGlyphPainter());
		}

		/**
		 * register the given {@link MetricsModel}
		 * 
		 * @param model
		 *            the model to register
		 */
		public void registerMetricsModel(MetricsModelOLD model) {
			getMetricsManager().registerMetricsModel(model);
		}

		/**
		 * register the given {@link MetricsModel} array
		 * 
		 * @param models
		 *            the models array to register
		 */
		public void registerMetricsModels(MetricsModelOLD... models) {
			getMetricsManager().registerMetricsModels(models);
		}

		/**
		 * register the given {@link MetricsModelCollections}
		 * 
		 * @param ModelCollections
		 *            the models collection to register
		 */
		public void registerMetricsModels(MetricsModelCollections ModelCollections) {
			getMetricsManager().registerMetricsModels(ModelCollections);
		}

		/**
		 * register the given {@link MetricsModel} list
		 * 
		 * @param models
		 *            the models list to register
		 */
		public void registerMetricsModels(List<MetricsModelOLD> models) {
			getMetricsManager().registerMetricsModels(models);
		}

		/**
		 * unregister the given {@link MetricsModel}
		 * 
		 * @param model
		 *            the model to remove
		 */
		public void unregisterMetricsModel(MetricsModelOLD model) {
			getMetricsManager().unregisterMetricsModel(model);
		}

		/**
		 * unregister the given {@link MetricsModel} array
		 * 
		 * @param models
		 *            the metrics models to remove
		 */
		public void unregisterMetricsModels(MetricsModelOLD... models) {
			getMetricsManager().unregisterMetricsModels(models);
		}

		/**
		 * unregister the given {@link MetricsModelCollections}
		 * 
		 * @param ModelCollections
		 *            the metrics models to remove
		 */
		public void unregisterMetricsModels(MetricsModelCollections ModelCollections) {
			getMetricsManager().unregisterMetricsModels(ModelCollections);
		}

		/**
		 * unregister the given {@link MetricsModel} list
		 * 
		 * @param models
		 *            the metrics models to remove
		 */
		public void unregisterMetricsModels(List<MetricsModelOLD> models) {
			getMetricsManager().unregisterMetricsModels(models);
		}

		/**
		 * get all registered {@link MetricsModel}
		 * 
		 * @return models
		 */
		public List<MetricsModelOLD> getMetricsModels() {
			return getMetricsManager().getMetricsModels();
		}

		
		/* (non-Javadoc)
		 * @see com.jensoft.core.plugin.metrics.AxisMetricsPlugin#paintMetrics(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
		 */
		@Override
		protected void paintMetrics(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
			if (!super.isAccessible(windowPart)) {
				return;
			}
			super.createRenderContext(v2d, g2d);
			super.assignType();
			int axisSpacing = 0;
			List<MetricsModelOLD> sequence = getMetricsManager().getSequenceMetrics();
			for (MetricsModelOLD model : sequence) {
				if (model.getRank() > 0 && !sequence.get(0).isMinimal())
					continue;
				if (model.getRank() > 1)
					continue;
				// System.out.println("paint metrics for :"+model);
				// MetricsRenderContext renderContext = new
				// MetricsRenderContext(v2d, getWindow2D(), g2d);
				// renderContext.setMetricsMedianFont(timeFont1);
				// renderContext.setMetricsMajorFont(timeFont1);

				// timingManager.setMetricsMajorFont(timeFont1);
				// timingManager.setMetricsMajorFont(timeFont1);

				// getMetricsPainter().setMetricsRenderContext(renderContext);
				// getMetricsManager().setRenderContext(renderContext);
				// System.out.println("model start ref : "+model.getRef());
				List<Metrics> metrics = model.generateMetrics();
				// System.out.println("paints "
				// +getMetricsManager().getType().name()+" "+ metrics.size() +
				// " metrics for metrics model : " + model.toString());

				Collections.sort(metrics, Metrics.getComparator());

				for (int i = 0; i < metrics.size(); i++) {
					Metrics m = metrics.get(i);

					if (i == 0) {
						m.setGravity(Gravity.First);
					}
					if (i == metrics.size() - 1 && i > 0) {
						m.setGravity(Gravity.Last);
					}

					m.setLockMarker(true);
					if (axisSpacing > 0) {
						m.setLockMarker(false);
					}

					Point2D markerLocation = new Point2D.Double();
					if (windowPart == WindowPart.South) {

						markerLocation = new Point2D.Double(v2d.getPlaceHolderAxisWest() + m.getDeviceValue(), axisSpacing);

						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.S);
					}
					if (windowPart == WindowPart.West) {
						JComponent component = v2d.getWindowComponent(WindowPart.West);
						markerLocation = new Point2D.Double(component.getWidth() - 1 - axisSpacing, m.getDeviceValue());

						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.W);
					}
					if (windowPart == WindowPart.East) {
						markerLocation = new Point2D.Double(axisSpacing, m.getDeviceValue());
						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.E);
					}
					if (windowPart == WindowPart.North) {
						JComponent component = v2d.getWindowComponent(WindowPart.North);
						markerLocation = new Point2D.Double(v2d.getPlaceHolderAxisWest() + m.getDeviceValue(), component.getHeight() - 1 - axisSpacing);
						m.setMarkerLocation(markerLocation);
						m.setMarkerPosition(MarkerPosition.N);
					}
				}

				getMetricsPainter().doPaintMetrics(g2d, metrics);
				axisSpacing = axisSpacing + model.getPixelAxisHolder();

			}
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
	 * Create a new <code>AxisMetricsPlugin<code>
	 */
	public AxisMetricsPlugin() {
		metricsPainter = new MetricsGlyphPainter();
		axis = Axis.AxisSouth;
		setPriority(1000);
		setTextAntialising(TextAntialiasing.On);
		setAntialiasing(Antialiasing.On);
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
		this();
		metricsManager = manager;
		this.axis = axis;
	}

	/**
	 * get the metrics layout manager
	 * 
	 * @return layout manager
	 */
	public M getMetricsManager() {
		return metricsManager;
	}

	/**
	 * set the metrics manager
	 * 
	 * @param metricsManager
	 */
	public void setMetricsManager(M metricsManager) {
		this.metricsManager = metricsManager;
	}

	/**
	 * delegate, set the metrics label color
	 * 
	 * @param metricsLabelColor
	 */
	public void setMetricsLabelColor(Color metricsLabelColor) {
		getMetricsManager().setMetricsLabelColor(metricsLabelColor);
	}

	/**
	 * delegate, get the metrics label color
	 * 
	 */
	public Color getMetricsLabelColor() {
		return getMetricsManager().getMetricsLabelColor();
	}

	/**
	 * delegate, set the metrics marker Color
	 * 
	 * @param metricsColor
	 *            the color of the marker
	 */
	public void setMetricsMarkerColor(Color metricsColor) {
		getMetricsManager().setMetricsMarkerColor(metricsColor);
	}

	/**
	 * delegate, get the metrics marker Color
	 * 
	 * @return metricsColor the color of the marker
	 */
	public Color getMetricsMarkerColor() {
		return getMetricsManager().getMetricsMarkerColor();
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
	 * @return the paintAxisBaseLine
	 */
	public boolean isPaintAxisBaseLine() {
		return paintAxisBaseLine;
	}

	/**
	 * @param paintAxisBaseLine
	 *            the paintAxisBaseLine to set
	 */
	public void setPaintAxisBaseLine(boolean paintAxisBaseLine) {
		this.paintAxisBaseLine = paintAxisBaseLine;
	}

	/**
	 * get the axis metrics base line color
	 * 
	 * @return the base line color
	 */
	public Color getMetricsBaseLineColor() {
		return getMetricsManager().getMetricsBaseLineColor();
	}

	/**
	 * set the axis base line color
	 * 
	 * @param baseLineColor
	 *            the base line color to set
	 */
	public void setMetricsBaseLineColor(Color baseLineColor) {
		getMetricsManager().setMetricsBaseLineColor(baseLineColor);
	}

	/**
	 * get the axis metrics median font
	 * 
	 * @return the metrics median font
	 */
	public Font getMetricsMedianFont() {
		return getMetricsManager().getMetricsMedianFont();
	}

	/**
	 * set the metrics font
	 * 
	 * @param metricsFont
	 *            the metrics font to set
	 */
	public void setMetricsFont(Font metricsFont) {
		setMetricsMedianFont(metricsFont);
		setMetricsMajorFont(metricsFont);
	}

	/**
	 * set the metrics median font
	 * 
	 * @param metricsMedianFont
	 *            the metrics median font to set
	 */
	public void setMetricsMedianFont(Font metricsMedianFont) {
		getMetricsManager().setMetricsMedianFont(metricsMedianFont);
	}

	/**
	 * get the axis metrics major font
	 * 
	 * @return the metrics major font
	 */
	public Font getMetricsMajorFont() {
		return getMetricsManager().getMetricsMajorFont();
	}

	/**
	 * set the metrics major font
	 * 
	 * @param metricsMajorFont
	 *            the metrics major font to set
	 */
	public void setMetricsMajorFont(Font metricsMajorFont) {
		getMetricsManager().setMetricsMajorFont(metricsMajorFont);
	}

	/**
	 * get the metrics painter
	 * 
	 * @return the painter
	 */
	public AbstractMetricsPainter getMetricsPainter() {
		return metricsPainter;
	}

	/**
	 * set the metrics painter
	 * 
	 * @param metricsPainter
	 */
	public void setMetricsPainter(AbstractMetricsPainter metricsPainter) {
		this.metricsPainter = metricsPainter;
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
		Axis old = getAxis();
		this.axis = axis;
		firePropertyChange("axis", old, getAxis());
	}

	/**
	 * true if the context is accessible, false otherwise
	 * 
	 * @param windowPart
	 * @return true if the context is accessible, false otherwise
	 */
	protected boolean isAccessible(WindowPart windowPart) {
		if (axis == Axis.AxisSouth && windowPart != WindowPart.South) {
			return false;
		}
		if (axis == Axis.AxisNorth && windowPart != WindowPart.North) {
			return false;
		}
		if (axis == Axis.AxisWest && windowPart != WindowPart.West) {
			return false;
		}
		if (axis == Axis.AxisEast && windowPart != WindowPart.East) {
			return false;
		}
		if (windowPart == WindowPart.Device) {
			return false;
		}

		return true;
	}

	/**
	 * paint the base line if the {@link #paintAxisBaseLine} is true.
	 * 
	 * @param v2d
	 * @param g2d
	 * @param windowPart
	 */
	protected void paintBaseLine(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
		if (isPaintAxisBaseLine()) {
			Point2D axisStartLocation = new Point2D.Double();
			Point2D axisEndLocation = new Point2D.Double();
			if (windowPart == WindowPart.South) {
				JComponent component = v2d.getWindowComponent(WindowPart.South);
				axisStartLocation = new Point2D.Double(v2d.getPlaceHolderAxisWest(), axisSpacing);
				axisEndLocation = new Point2D.Double(component.getWidth() - v2d.getPlaceHolderAxisEast(), axisSpacing);
			}
			if (windowPart == WindowPart.West) {
				JComponent component = v2d.getWindowComponent(WindowPart.West);
				axisStartLocation = new Point2D.Double(component.getWidth() - 1 - axisSpacing, 0);
				axisEndLocation = new Point2D.Double(component.getWidth() - 1 - axisSpacing, component.getHeight());
			}
			if (windowPart == WindowPart.East) {
				JComponent component = v2d.getWindowComponent(WindowPart.East);
				axisStartLocation = new Point2D.Double(axisSpacing, 0);
				axisEndLocation = new Point2D.Double(axisSpacing, component.getHeight());
			}
			if (windowPart == WindowPart.North) {
				JComponent component = v2d.getWindowComponent(WindowPart.North);
				axisStartLocation = new Point2D.Double(v2d.getPlaceHolderAxisWest(), component.getHeight() - 1 - axisSpacing);
				axisEndLocation = new Point2D.Double(component.getWidth() - v2d.getPlaceHolderAxisEast(), component.getHeight() - 1 - axisSpacing);
			}

			Color axisBaseLineColor;
			if (metricsManager.getMetricsBaseLineColor() != null) {
				axisBaseLineColor = metricsManager.getMetricsBaseLineColor();
			} else {
				axisBaseLineColor = metricsManager.getRenderContext().getWindow2D().getThemeColor();
			}
			metricsPainter.doPaintLineMetrics(g2d, axisStartLocation, axisEndLocation, axisBaseLineColor);
		}
	}

	/**
	 * paint metrics
	 * 
	 * @param v2d
	 * @param g2d
	 * @param windowPart
	 */
	protected void paintMetricsLabelIndicator(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
		List<Metrics> metrics = null;
		try {
			metrics = metricsManager.getDeviceMetrics();
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}

		Collections.sort(metrics, Metrics.getComparator());

		for (int i = 0; i < metrics.size(); i++) {

			Metrics m = metrics.get(i);

			if (i == 0) {
				m.setGravity(Gravity.First);
			}
			if (i == metrics.size() - 1) {
				m.setGravity(Gravity.Last);
			}

			Point2D markerLocation = new Point2D.Double();

			if (windowPart == WindowPart.South) {

				markerLocation = new Point2D.Double(v2d.getPlaceHolderAxisWest() + m.getDeviceValue(), axisSpacing);
				m.setMarkerLocation(markerLocation);
				m.setMarkerPosition(MarkerPosition.S);
			}
			if (windowPart == WindowPart.West) {
				JComponent component = v2d.getWindowComponent(WindowPart.West);
				markerLocation = new Point2D.Double(component.getWidth() - 1 - axisSpacing, m.getDeviceValue());
				m.setMarkerLocation(markerLocation);
				m.setMarkerPosition(MarkerPosition.W);
			}
			if (windowPart == WindowPart.East) {
				markerLocation = new Point2D.Double(axisSpacing, m.getDeviceValue());
				m.setMarkerLocation(markerLocation);
				m.setMarkerPosition(MarkerPosition.E);
			}
			if (windowPart == WindowPart.North) {
				JComponent component = v2d.getWindowComponent(WindowPart.North);
				markerLocation = new Point2D.Double(v2d.getPlaceHolderAxisWest() + m.getDeviceValue(), component.getHeight() - 1 - axisSpacing);
				m.setMarkerLocation(markerLocation);
				m.setMarkerPosition(MarkerPosition.N);
			}
		}
		metricsPainter.doPaintMetrics(g2d, metrics);
	}

	/**
	 * create the render context
	 * 
	 * @param v2d
	 * @param g2d
	 */
	protected void createRenderContext(View2D v2d, Graphics2D g2d) {
		MetricsRenderContext renderContext = new MetricsRenderContext(v2d, getWindow2D(), g2d);
		renderContext.setMetricsMedianFont(metricsManager.getMetricsMedianFont());
		renderContext.setMetricsMajorFont(metricsManager.getMetricsMajorFont());

		metricsManager.setRenderContext(renderContext);
		metricsPainter.setMetricsRenderContext(renderContext);
	}

	/**
	 * assign manager type x or y given by given axis.
	 */
	protected void assignType() {
		if (axis == Axis.AxisSouth || axis == Axis.AxisNorth) {
			metricsManager.setMetricsType(MetricsType.XMetrics);
		}
		if (axis == Axis.AxisEast || axis == Axis.AxisWest) {
			metricsManager.setMetricsType(MetricsType.YMetrics);
		}
	}

	/**
	 * Paints the window metrics.
	 */
	protected void paintMetrics(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
		if (!isAccessible(windowPart)) {
			return;
		}
		createRenderContext(v2d, g2d);
		assignType();
		paintMetricsLabelIndicator(v2d, g2d, windowPart);
		paintBaseLine(v2d, g2d, windowPart);

	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
	 */
	@Override
	public final void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
		paintMetrics(v2d, g2d, windowPart);
	}

}
