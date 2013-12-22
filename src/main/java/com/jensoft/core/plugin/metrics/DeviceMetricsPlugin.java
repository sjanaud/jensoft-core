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
import java.util.List;

import com.jensoft.core.device.Device2D;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.TimeMetrics;
import com.jensoft.core.plugin.metrics.format.IMetricsFormat;
import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.Metrics.MarkerPosition;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.plugin.metrics.geom.MetricsRenderContext;
import com.jensoft.core.plugin.metrics.manager.AbstractMetricsManager;
import com.jensoft.core.plugin.metrics.manager.FlowMetricsManager;
import com.jensoft.core.plugin.metrics.manager.FreeMetricsManager;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManager;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManager.MetricsModel;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManager.MetricsModelCollections;
import com.jensoft.core.plugin.metrics.manager.Multiplier3MetricsManager;
import com.jensoft.core.plugin.metrics.manager.MultiplierMetricsManager;
import com.jensoft.core.plugin.metrics.manager.StaticMetricsManager;
import com.jensoft.core.plugin.metrics.manager.TimeMetricsManager;
import com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel;
import com.jensoft.core.plugin.metrics.painter.AbstractMetricsPainter;
import com.jensoft.core.plugin.metrics.painter.MetricsGlyphPainter;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>DeviceMetricsPlugin</code> takes the responsibility to manage metrics
 * on {@link Device2D}
 * 
 * @author sebastien janaud
 */
public abstract class DeviceMetricsPlugin<M extends AbstractMetricsManager> extends AbstractPlugin {

	/** the metrics manager for x axis */
	private M metricsManager;

	/** axis base line at the constant x or y value */
	private double baseLine;

	/** device axis x or y */
	private DeviceAxis deviceAxis;

	/** device axis x or y */
	private MarkerPosition deviceMarkerPosition;

	/** the metrics painter */
	private AbstractMetricsPainter metricsPainter = new MetricsGlyphPainter();

	/** paint base line flag */
	private boolean paintLine = true;

	/**
	 * defines two type of axis on device, axis along x and axis along y
	 * 
	 * @author Sebastien Janaud
	 */
	public enum DeviceAxis {
		AxisX, AxisY;
	}

	/**
	 * <code>DeviceStaticMetrics</code> takes the responsibility to manage
	 * static metrics on device
	 * 
	 * @author Sebastien Janaud
	 */
	public static class DeviceStaticMetrics extends DeviceMetricsPlugin<StaticMetricsManager> {

		/**
		 * <code>X</code> manages {@link DeviceStaticMetrics} for
		 * {@link DeviceAxis#AxisX}
		 */
		public static class X extends DeviceStaticMetrics {

			/**
			 * Create {@link DeviceStaticMetrics} for {@link DeviceAxis#AxisX}
			 * for given parameters
			 * 
			 * @param metricsCount
			 * @param baseLine
			 */
			public X(int metricsCount, double baseLine) {
				super(metricsCount, baseLine, DeviceAxis.AxisX);
			}

			/**
			 * Create {@link DeviceStaticMetrics} for {@link DeviceAxis#AxisX}
			 * for given parameters
			 * 
			 * @param metricsCount
			 * @param baseLine
			 * @param markerPosition
			 */
			public X(int metricsCount, double baseLine, MarkerPosition markerPosition) {
				super(metricsCount, baseLine, markerPosition, DeviceAxis.AxisX);
			}

		}

		/**
		 * <code>Y</code> manages {@link DeviceStaticMetrics} for
		 * {@link DeviceAxis#AxisY}
		 */
		public static class Y extends DeviceStaticMetrics {

			/**
			 * Create {@link DeviceStaticMetrics} for {@link DeviceAxis#AxisY}
			 * for given parameters
			 * 
			 * @param metricsCount
			 * @param baseLine
			 */
			public Y(int metricsCount, double baseLine) {
				super(metricsCount, baseLine, DeviceAxis.AxisY);
			}

			/**
			 * Create {@link DeviceStaticMetrics} for {@link DeviceAxis#AxisY}
			 * for given parameters
			 * 
			 * @param metricsCount
			 * @param baseLine
			 * @param markerPosition
			 */
			public Y(int metricsCount, double baseLine, MarkerPosition markerPosition) {
				super(metricsCount, baseLine, markerPosition, DeviceAxis.AxisY);
			}

		}

		/**
		 * create static device metrics
		 * 
		 * @param metricsCount
		 * @param baseLine
		 * @param deviceAxis
		 */
		public DeviceStaticMetrics(int metricsCount, double baseLine, DeviceAxis deviceAxis) {
			super(new StaticMetricsManager(metricsCount), baseLine, deviceAxis);
		}

		/**
		 * create static device metrics
		 * 
		 * @param metricsCount
		 * @param baseLine
		 * @param markerPosition
		 * @param deviceAxis
		 */
		public DeviceStaticMetrics(int metricsCount, double baseLine, MarkerPosition markerPosition, DeviceAxis deviceAxis) {
			super(new StaticMetricsManager(metricsCount), baseLine, markerPosition, deviceAxis);
		}

		/**
		 * get the dynamic metrics layout manager for this layout
		 */
		@Override
		public StaticMetricsManager getMetricsManager() {
			return (StaticMetricsManager) super.getMetricsManager();
		}

	}

	/**
	 * <code>DeviceFreeMetrics</code> takes the responsibility to manage free
	 * metrics on device
	 * 
	 * @author Sebastien Janaud
	 */
	public static class DeviceFreeMetrics extends DeviceMetricsPlugin<FreeMetricsManager> {

		/**
		 * <code>X</code> manages {@link DeviceFreeMetrics} for
		 * {@link DeviceAxis#AxisX}
		 */
		public static class X extends DeviceFreeMetrics {

			/**
			 * Create {@link DeviceFreeMetrics} for {@link DeviceAxis#AxisX} for
			 * given parameter
			 * 
			 * @param baseLine
			 */
			public X(double baseLine) {
				super(baseLine, DeviceAxis.AxisX);
			}

			/**
			 * Create {@link DeviceFreeMetrics} for {@link DeviceAxis#AxisX} for
			 * given parameters
			 * 
			 * @param baseLine
			 * @param markerPosition
			 */
			public X(double baseLine, MarkerPosition markerPosition) {
				super(baseLine, markerPosition, DeviceAxis.AxisX);
			}

		}

		/**
		 * <code>Y</code> manages {@link DeviceFreeMetrics} for
		 * {@link DeviceAxis#AxisX}
		 */
		public static class Y extends DeviceFreeMetrics {

			/**
			 * Create {@link DeviceFreeMetrics} for {@link DeviceAxis#AxisY} for
			 * given parameter
			 * 
			 * @param baseLine
			 */
			public Y(double baseLine) {
				super(baseLine, DeviceAxis.AxisY);
			}

			/**
			 * Create {@link DeviceFreeMetrics} for {@link DeviceAxis#AxisY} for
			 * given parameters
			 * 
			 * @param baseLine
			 * @param markerPosition
			 */
			public Y(double baseLine, MarkerPosition markerPosition) {
				super(baseLine, markerPosition, DeviceAxis.AxisY);
			}

		}

		/**
		 * create a new FreeMetrics layout for the device axis
		 * 
		 * @param baseLine
		 *            the base line coordinate
		 * @param deviceAxis
		 *            the device axis x or y
		 */
		public DeviceFreeMetrics(double baseLine, DeviceAxis deviceAxis) {
			super(new FreeMetricsManager(), baseLine, deviceAxis);
		}

		/**
		 * create a new FreeMetrics layout for the device axis
		 * 
		 * @param baseLine
		 *            the base line coordinate
		 * @param deviceAxis
		 *            the device axis x or y
		 * @param markerPosition
		 *            the marker position to set
		 */
		public DeviceFreeMetrics(double baseLine, MarkerPosition markerPosition, DeviceAxis deviceAxis) {
			super(new FreeMetricsManager(), baseLine, deviceAxis);
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
		 * @param symbol
		 *            the symbol associate to this metrics
		 */
		public void addMetrics(double m, String symbol) {
			getMetricsManager().addMetrics(m, symbol);
		}

	}

	/**
	 * <code>DeviceFlowMetrics</code> takes the responsibility to manage flow
	 * metrics on device
	 * 
	 * @author Sebastien Janaud
	 */
	public static class DeviceFlowMetrics extends DeviceMetricsPlugin<FlowMetricsManager> {

		/**
		 * <code>X</code> manages {@link DeviceFlowMetrics} for
		 * {@link DeviceAxis#AxisX}
		 */
		public static class X extends DeviceFlowMetrics {

			/**
			 * Create {@link DeviceFlowMetrics} for {@link DeviceAxis#AxisX}
			 * with the given flow parameters at the specified base line
			 * 
			 * @param flowStart
			 * @param flowEnd
			 * @param flowInterval
			 * @param baseLine
			 */
			public X(double flowStart, double flowEnd, double flowInterval, double baseLine) {
				super(flowStart, flowEnd, flowInterval, baseLine, DeviceAxis.AxisX);
			}

			/**
			 * Create {@link DeviceFlowMetrics} for {@link DeviceAxis#AxisX}
			 * with the given flow parameters at the specified base line
			 * 
			 * @param flowStart
			 * @param flowEnd
			 * @param flowInterval
			 * @param baseLine
			 * @param markerPosition
			 */
			public X(double flowStart, double flowEnd, double flowInterval, double baseLine, MarkerPosition markerPosition) {
				super(flowStart, flowEnd, flowInterval, baseLine, markerPosition, DeviceAxis.AxisX);
			}

		}

		/**
		 * <code>Y</code> manages {@link DeviceFlowMetrics} for
		 * {@link DeviceAxis#AxisY}
		 */
		public static class Y extends DeviceFlowMetrics {

			/**
			 * Create {@link DeviceFlowMetrics} for {@link DeviceAxis#AxisY}
			 * with the given flow parameters at the specified base line
			 * 
			 * @param flowStart
			 * @param flowEnd
			 * @param flowInterval
			 * @param baseLine
			 */
			public Y(double flowStart, double flowEnd, double flowInterval, double baseLine) {
				super(flowStart, flowEnd, flowInterval, baseLine, DeviceAxis.AxisY);
			}

			/**
			 * Create {@link DeviceFlowMetrics} for {@link DeviceAxis#AxisY}
			 * with the given flow parameters at the specified base line and
			 * marker position
			 * 
			 * @param flowStart
			 * @param flowEnd
			 * @param flowInterval
			 * @param baseLine
			 * @param markerPosition
			 */
			public Y(double flowStart, double flowEnd, double flowInterval, double baseLine, MarkerPosition markerPosition) {
				super(flowStart, flowEnd, flowInterval, baseLine, markerPosition, DeviceAxis.AxisY);
			}

		}

		/**
		 * Create a new instance of a
		 * <code>DeviceFlowMetrics<code> with the specified reference.
		 * 
		 * @param flowStart
		 *            the start flow reference
		 * @param flowEnd
		 *            the end flow reference
		 * @param flowInterval
		 *            the flow interval
		 * @param baseLine
		 *            the axis base line on x or y
		 * @param deviceAxis
		 *            the x or y axis on device
		 */
		public DeviceFlowMetrics(double flowStart, double flowEnd, double flowInterval, double baseLine, DeviceAxis deviceAxis) {
			super(new FlowMetricsManager(flowStart, flowEnd, flowInterval), baseLine, deviceAxis);
		}

		/**
		 * Create a new instance of a
		 * <code>DeviceFlowMetrics<code> with the specified reference.
		 * 
		 * @param flowStart
		 *            the start flow reference
		 * @param flowEnd
		 *            the end flow reference
		 * @param flowInterval
		 *            the flow interval
		 * @param baseLine
		 *            the axis base line on x or y
		 * @param markerPosition
		 *            the marker position N,S,NS for x axis, W,E,WE for y axis
		 * @param deviceAxis
		 *            the x or y axis on device
		 */
		public DeviceFlowMetrics(double flowStart, double flowEnd, double flowInterval, double baseLine, MarkerPosition markerPosition, DeviceAxis deviceAxis) {
			super(new FlowMetricsManager(flowStart, flowEnd, flowInterval), baseLine, markerPosition, deviceAxis);
			super.setName(DeviceFlowMetrics.class.getCanonicalName());
		}

	}

	/**
	 * <code>DeviceMultiplierMetrics</code> takes the responsibility to manage
	 * device metrics with one multiplier
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

	public static class DeviceMultiplierMetrics extends DeviceMetricsPlugin<MultiplierMetricsManager> {

		/**
		 * <code>X</code> manages {@link DeviceMultiplierMetrics} for
		 * {@link DeviceAxis#AxisX}
		 */
		public static class X extends DeviceMultiplierMetrics {

			/**
			 * Create {@link DeviceMultiplierMetrics} for
			 * {@link DeviceAxis#AxisX} for given parameters
			 * 
			 * @param metricsRef
			 * @param metricsInterval
			 * @param baseLine
			 */
			public X(double metricsRef, double metricsInterval, double baseLine) {
				super(metricsRef, metricsInterval, baseLine, DeviceAxis.AxisX);
			}

			/**
			 * Create {@link DeviceMultiplierMetrics} for
			 * {@link DeviceAxis#AxisX} for given parameters
			 * 
			 * @param metricsRef
			 * @param metricsInterval
			 * @param baseLine
			 * @param markerPosition
			 */
			public X(double metricsRef, double metricsInterval, double baseLine, MarkerPosition markerPosition) {
				super(metricsRef, metricsInterval, baseLine, DeviceAxis.AxisX);
			}

		}

		/**
		 * <code>Y</code> manages {@link DeviceMultiplierMetrics} for
		 * {@link DeviceAxis#AxisY}
		 */
		public static class Y extends DeviceMultiplierMetrics {

			/**
			 * Create {@link DeviceMultiplierMetrics} for
			 * {@link DeviceAxis#AxisY} for given parameters
			 * 
			 * @param metricsRef
			 * @param metricsInterval
			 * @param baseLine
			 */
			public Y(double metricsRef, double metricsInterval, double baseLine) {
				super(metricsRef, metricsInterval, baseLine, DeviceAxis.AxisY);
			}

			/**
			 * Create {@link DeviceMultiplierMetrics} for
			 * {@link DeviceAxis#AxisY} for given parameters
			 * 
			 * @param metricsRef
			 * @param metricsInterval
			 * @param baseLine
			 * @param markerPosition
			 */
			public Y(double metricsRef, double metricsInterval, double baseLine, MarkerPosition markerPosition) {
				super(metricsRef, metricsInterval, baseLine, DeviceAxis.AxisY);
			}

		}

		/**
		 * create a device multiplier metrics
		 * 
		 * @param metricsRef
		 *            the reference to use for resolve metrics
		 * @param metricsInterval
		 *            the metrics interval to factor from the reference into
		 *            window bound metrics
		 * @param deviceAxis
		 *            the device axis zone to lay out this metrics
		 */
		public DeviceMultiplierMetrics(double metricsRef, double metricsInterval, double baseLine, DeviceAxis deviceAxis) {
			super(new MultiplierMetricsManager(metricsRef, metricsInterval), baseLine, deviceAxis);

		}

		/**
		 * create a device multiplier metrics
		 * 
		 * @param metricsRef
		 *            the reference to use for resolve metrics
		 * @param metricsInterval
		 *            the metrics interval to factor from the reference into
		 *            window bound metrics *
		 * @param markerPosition
		 *            the marker position
		 * @param deviceAxis
		 *            the device axis zone to lay out this metrics
		 */
		public DeviceMultiplierMetrics(double metricsRef, double metricsInterval, double baseLine, MarkerPosition markerPosition, DeviceAxis deviceAxis) {
			super(new MultiplierMetricsManager(metricsRef, metricsInterval), baseLine, markerPosition, deviceAxis);

		}

	}

	/**
	 * <code>DeviceMultiMultiplierMetrics</code> takes the responsibility to
	 * manage 3-multipliers metrics on device
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
	public static class DeviceMultiplier3Metrics extends DeviceMetricsPlugin<Multiplier3MetricsManager> {

		/**
		 * <code>X</code> manages {@link DeviceMultiplier3Metrics} for
		 * {@link DeviceAxis#AxisX}
		 */
		public static class X extends DeviceMultiplier3Metrics {

			/**
			 * Create {@link DeviceMultiplier3Metrics} for
			 * {@link DeviceAxis#AxisX}
			 * 
			 * @param ref
			 * @param baseLine
			 */
			public X(double ref, double baseLine) {
				super(ref, baseLine, DeviceAxis.AxisX);
			}

			/**
			 * Create {@link DeviceMultiplier3Metrics} for
			 * {@link DeviceAxis#AxisX}
			 * 
			 * @param ref
			 * @param baseLine
			 * @param markerPosition
			 */
			public X(double ref, double baseLine, MarkerPosition markerPosition) {
				super(ref, baseLine, markerPosition, DeviceAxis.AxisX);
			}

		}

		/**
		 * <code>Y</code> manages {@link DeviceMultiplier3Metrics} for
		 * {@link DeviceAxis#AxisY}
		 */
		public static class Y extends DeviceMultiplier3Metrics {

			/**
			 * Create {@link DeviceMultiplier3Metrics} for
			 * {@link DeviceAxis#AxisY} for given parameters
			 * 
			 * @param ref
			 * @param baseLine
			 */
			public Y(double ref, double baseLine) {
				super(ref, baseLine, DeviceAxis.AxisY);
			}

			/**
			 * Create {@link DeviceMultiplier3Metrics} for
			 * {@link DeviceAxis#AxisY} for given parameters
			 * 
			 * @param ref
			 * @param baseLine
			 * @param markerPosition
			 */
			public Y(double ref, double baseLine, MarkerPosition markerPosition) {
				super(ref, baseLine, markerPosition, DeviceAxis.AxisY);
			}

		}

		/**
		 * Create device multiple multiplier metrics plug-in
		 * 
		 * @param ref
		 *            the reference from metrics will be created
		 * @param baseLine
		 *            the axis base line on x or y
		 * @param deviceAxis
		 *            the x or y axis on device
		 */
		public DeviceMultiplier3Metrics(double ref, double baseLine, DeviceAxis deviceAxis) {
			super(new Multiplier3MetricsManager(ref), baseLine, deviceAxis);
		}

		/**
		 * Create device multiple multiplier metrics plug-in
		 * 
		 * @param ref
		 *            the reference from metrics will be created
		 * @param baseLine
		 *            the axis base line on x or y
		 * @param deviceAxis
		 *            the x or y axis on device
		 * @param markerPosition
		 *            the marker position N,S,NS for x axis, W,E,WE for y axis
		 */
		public DeviceMultiplier3Metrics(double ref, double baseLine, MarkerPosition markerPosition, DeviceAxis deviceAxis) {
			super(new Multiplier3MetricsManager(ref), baseLine, markerPosition, deviceAxis);
		}

		/***
		 * set the major metrics.
		 * 
		 * @param major
		 */
		public void setMajor(double major) {
			getMetricsManager().setMajor(major);

		}

		/**
		 * set the median metrics
		 * 
		 * @param median
		 */
		public void setMedian(double median) {
			getMetricsManager().setMedian(median);
		}

		/**
		 * set the minor metrics
		 * 
		 * @param minor
		 */
		public void setMinor(double minor) {
			getMetricsManager().setMinor(minor);
		}

	}

	/**
	 * <code>DeviceTimeMetrics</code> takes the responsibility to manage timing
	 * metrics on device
	 * 
	 * @author sebastien janaud
	 */
	public static class DeviceTimeMetrics extends DeviceMetricsPlugin<TimeMetricsManager> {

		/**
		 * <code>X</code> manages {@link DeviceTimeMetrics} for
		 * {@link DeviceAxis#AxisX}
		 */
		public static class X extends DeviceTimeMetrics {

			/**
			 * Create {@link DeviceTimeMetrics} for {@link DeviceAxis#AxisX}
			 * 
			 * @param baseLine
			 */
			public X(double baseLine) {
				super(baseLine, DeviceAxis.AxisX);
			}

			/**
			 * Create {@link DeviceTimeMetrics} for {@link DeviceAxis#AxisX}
			 * 
			 * @param baseLine
			 * @param markerPosition
			 */
			public X(double baseLine, MarkerPosition markerPosition) {
				super(baseLine, markerPosition, DeviceAxis.AxisX);
			}

		}

		/**
		 * <code>Y</code> manages {@link DeviceTimeMetrics} for
		 * {@link DeviceAxis#AxisY}
		 */
		public static class Y extends DeviceTimeMetrics {

			/**
			 * Create {@link DeviceTimeMetrics} for {@link DeviceAxis#AxisY}
			 * 
			 * @param baseLine
			 */
			public Y(double baseLine) {
				super(baseLine, DeviceAxis.AxisY);
			}

			/**
			 * Create {@link DeviceTimeMetrics} for {@link DeviceAxis#AxisY}
			 * 
			 * @param baseLine
			 * @param markerPosition
			 */
			public Y(double baseLine, MarkerPosition markerPosition) {
				super(baseLine, markerPosition, DeviceAxis.AxisY);
			}

		}

		/**
		 * create device timing axis with given parameters
		 * 
		 * @param baseLine
		 *            device axis x or y value for base line
		 * @param deviceAxis
		 *            device axis nature
		 */
		public DeviceTimeMetrics(double baseLine, DeviceAxis deviceAxis) {
			super(new TimeMetricsManager(), baseLine, deviceAxis);
			setName(TimeMetrics.class.getCanonicalName());
		}

		/**
		 * create device timing axis with given parameters
		 * 
		 * @param baseLine
		 *            device axis x or y value for base line
		 * @param markerPosition
		 *            the device marker position
		 * @param deviceAxis
		 *            device axis nature
		 */
		public DeviceTimeMetrics(double baseLine, MarkerPosition markerPosition, DeviceAxis deviceAxis) {
			super(new TimeMetricsManager(), baseLine, markerPosition, deviceAxis);
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
		 * get all registered {@link TimeModel}
		 * 
		 * @return models
		 */
		public List<TimeModel> getTimingModels() {
			return getMetricsManager().getTimingModels();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.metrics.DeviceMetricsPlugin#paintMetrics(
		 * com.jensoft.core.view.View2D, java.awt.Graphics2D,
		 * com.jensoft.core.window.WindowPart)
		 */
		@Override
		protected void paintMetrics(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
			if (!super.isAccessible(windowPart)) {
				return;
			}
			super.createRenderContext(v2d, g2d);
			super.assignType();

			int axisSpacing = 0;

			List<TimeModel> sequence = ((TimeMetricsManager) getMetricsManager()).getTimingSequence();

			for (TimeModel timingManager : sequence) {

				// TODO paint metrics method for device time metrics

				axisSpacing = axisSpacing + timingManager.getPixelAxisHolder();

			}
		}
	}

	/**
	 * <code>DeviceModeledMetrics</code> takes the responsibility to manage
	 * modeled metrics on device
	 * 
	 * @author sebastien janaud
	 */
	public static class DeviceModeledMetrics extends DeviceMetricsPlugin<ModeledMetricsManager> {

		/**
		 * <code>X</code> manages {@link DeviceModeledMetrics} for
		 * {@link DeviceAxis#AxisX}
		 */
		public static class X extends DeviceModeledMetrics {

			/**
			 * Create {@link DeviceModeledMetrics} for {@link DeviceAxis#AxisX}
			 * 
			 * @param baseLine
			 */
			public X(double baseLine) {
				super(baseLine, DeviceAxis.AxisX);
			}

			/**
			 * Create {@link DeviceModeledMetrics} for {@link DeviceAxis#AxisX}
			 * 
			 * @param baseLine
			 * @param markerPosition
			 */
			public X(double baseLine, MarkerPosition markerPosition) {
				super(baseLine, markerPosition, DeviceAxis.AxisX);
			}

		}

		/**
		 * <code>Y</code> manages {@link DeviceModeledMetrics} for
		 * {@link DeviceAxis#AxisY}
		 */
		public static class Y extends DeviceModeledMetrics {

			/**
			 * Create {@link DeviceModeledMetrics} for {@link DeviceAxis#AxisY}
			 * 
			 * @param baseLine
			 */
			public Y(double baseLine) {
				super(baseLine, DeviceAxis.AxisY);
			}

			/**
			 * Create {@link DeviceModeledMetrics} for {@link DeviceAxis#AxisX}
			 * 
			 * @param baseLine
			 * @param markerPosition
			 */
			public Y(double baseLine, MarkerPosition markerPosition) {
				super(baseLine, markerPosition, DeviceAxis.AxisY);
			}

		}

		/**
		 * Create device modeled metrics for given parameters
		 * 
		 * @param baseLine
		 * @param deviceAxis
		 */
		public DeviceModeledMetrics(double baseLine, DeviceAxis deviceAxis) {
			super(new ModeledMetricsManager(), baseLine, deviceAxis);
		}

		/**
		 * Create device modeled metrics for given parameters
		 * 
		 * @param baseLine
		 * @param deviceMarkerPosition
		 * @param deviceAxis
		 */
		public DeviceModeledMetrics(double baseLine, MarkerPosition deviceMarkerPosition, DeviceAxis deviceAxis) {
			super(new ModeledMetricsManager(), baseLine, deviceMarkerPosition, deviceAxis);
		}

		/**
		 * register the given {@link MetricsModel}
		 * 
		 * @param model
		 *            the model to register
		 */
		public void registerMetricsModel(MetricsModel model) {
			getMetricsManager().registerMetricsModel(model);
		}

		/**
		 * register the given {@link MetricsModel} array
		 * 
		 * @param models
		 *            the models to register
		 */
		public void registerMetricsModels(MetricsModel... models) {
			getMetricsManager().registerMetricsModels(models);
		}

		/**
		 * register the given {@link MetricsModelCollections}
		 * 
		 * @param modelCollections
		 *            the models to register
		 */
		public void registerMetricsModels(MetricsModelCollections modelCollections) {
			getMetricsManager().registerMetricsModels(modelCollections);
		}

		/**
		 * register the given {@link MetricsModel} list
		 * 
		 * @param models
		 *            the models to register
		 */
		public void registerMetricsModels(List<MetricsModel> models) {
			getMetricsManager().registerMetricsModels(models);
		}

		/**
		 * unregister the given {@link MetricsModel}
		 * 
		 * @param model
		 *            the model to remove
		 */
		public void unregisterMetricsModel(MetricsModel model) {
			getMetricsManager().unregisterMetricsModel(model);
		}

		/**
		 * unregister the given {@link MetricsModel} array
		 * 
		 * @param models
		 *            the metrics models to remove
		 */
		public void unregisterMetricsModels(MetricsModel... models) {
			getMetricsManager().unregisterMetricsModels(models);
		}

		/**
		 * unregister the given {@link MetricsModelCollections}
		 * 
		 * @param modelCollections
		 *            the metrics models to remove
		 */
		public void unregisterMetricsModels(MetricsModelCollections modelCollections) {
			getMetricsManager().unregisterMetricsModels(modelCollections);
		}

		/**
		 * unregister the given {@link MetricsModel} list
		 * 
		 * @param models
		 *            the metrics models to remove
		 */
		public void unregisterMetricsModels(List<MetricsModel> models) {
			getMetricsManager().unregisterMetricsModels(models);
		}

		/**
		 * get all registered {@link MetricsModel}
		 * 
		 * @return models
		 */
		public List<MetricsModel> getMetricsModels() {
			return getMetricsManager().getMetricsModels();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.metrics.DeviceMetricsPlugin#paintMetrics(
		 * com.jensoft.core.view.View2D, java.awt.Graphics2D,
		 * com.jensoft.core.window.WindowPart)
		 */
		@Override
		protected void paintMetrics(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
			if (!super.isAccessible(windowPart)) {
				return;
			}
			super.createRenderContext(v2d, g2d);
			super.assignType();
			int axisSpacing = 0;
			List<MetricsModel> sequence = getMetricsManager().getSequenceMetrics();
			//System.out.println("--SEQUENCE MODELS--");
			for (MetricsModel model : sequence) {
				if (model.getRank() > 0 && !sequence.get(0).isMinimal())
					continue;
				if (model.getRank() > 1)
					continue;
				//System.out.println("applicable model : " + model);
				List<Metrics> metrics = model.generateMetrics();
				if (super.deviceAxis == DeviceAxis.AxisX) {
					paintMetricsX(v2d, g2d, metrics, super.baseLine, axisSpacing);
				}
				if (super.deviceAxis == DeviceAxis.AxisY) {
					paintMetricsY(v2d, g2d, metrics, super.baseLine, axisSpacing);
				}
				axisSpacing = axisSpacing + model.getPixelAxisHolder();
			}

			if (super.deviceAxis == DeviceAxis.AxisX) {
				paintMetricsXBaseLine(v2d, g2d, super.baseLine);
			}
			if (super.deviceAxis == DeviceAxis.AxisY) {
				paintMetricsYBaseLine(v2d, g2d, super.baseLine);
			}
		}
	}

	/**
	 * create DeviceMetricsPlugin
	 * 
	 * @param manager
	 *            the metrics manager
	 * @param baseLine
	 *            the baseLine coordinate (x or y)
	 * @param deviceAxis
	 *            the axis type x or y on device
	 */
	public DeviceMetricsPlugin(M manager, double baseLine, DeviceAxis deviceAxis) {
		metricsManager = manager;
		this.baseLine = baseLine;
		this.deviceAxis = deviceAxis;
	}

	/**
	 * create DeviceMetricsPlugin
	 * 
	 * @param manager
	 *            the metrics manager
	 * @param baseLine
	 *            the baseLine coordinate (x or y)
	 * @param deviceAxis
	 *            the axis type x or y on device
	 * @param deviceMarkerPosition
	 *            the marker position N,S,NS for x axis, W,E,WE for y axis
	 */
	public DeviceMetricsPlugin(M manager, double baseLine, MarkerPosition deviceMarkerPosition, DeviceAxis deviceAxis) {
		metricsManager = manager;
		this.baseLine = baseLine;
		this.deviceAxis = deviceAxis;
		this.deviceMarkerPosition = deviceMarkerPosition;
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
	 * set the metrics layout manager
	 * 
	 * @param metricsLayoutManager
	 *            the manager to set
	 */
	public void setMetricsManager(M metricsLayoutManager) {
		this.metricsManager = metricsLayoutManager;
	}

	/**
	 * get the metrics painter {@link AbstractMetricsPainter}
	 * 
	 * @return the metricsPainter
	 */
	public AbstractMetricsPainter getMetricsPainter() {
		return metricsPainter;
	}

	/**
	 * @return the deviceMarkerPosition
	 */
	public MarkerPosition getDeviceMarkerPosition() {
		return deviceMarkerPosition;
	}

	/**
	 * @param deviceMarkerPosition
	 *            the deviceMarkerPosition to set
	 */
	public void setDeviceMarkerPosition(MarkerPosition deviceMarkerPosition) {
		this.deviceMarkerPosition = deviceMarkerPosition;
	}

	/**
	 * get the metrics painter
	 * 
	 * @return the painter
	 */
	public AbstractMetricsPainter getPainter() {
		return metricsPainter;
	}

	/**
	 * set the metrics painter
	 * 
	 * @param painter
	 */
	public void setPainter(AbstractMetricsPainter painter) {
		this.metricsPainter = painter;
	}

	/**
	 * true if the context is accessible, false otherwise
	 * 
	 * @param windowPart
	 * @return true if the context is accessible, false otherwise
	 */
	private boolean isAccessible(WindowPart windowPart) {
		if (windowPart != WindowPart.Device) {
			return false;
		}
		return true;
	}

	/**
	 * @return the paintLine
	 */
	public boolean isPaintLine() {
		return paintLine;
	}

	/**
	 * @param paintLine
	 *            the paintLine to set
	 */
	public void setPaintLine(boolean paintLine) {
		this.paintLine = paintLine;
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
	 * @return metrics label color
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
	 * get the axis metrics base line color
	 * 
	 * @return the base line color
	 */
	public Color getMetricsBaseLineColor() {
		return getMetricsManager().getMetricsBaseLineColor();
	}

	/**
	 * set base line color
	 * 
	 * @param baseLineColor
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
	 * create the render context
	 * 
	 * @param v2d
	 * @param g2d
	 */
	private void createRenderContext(View2D v2d, Graphics2D g2d) {
		MetricsRenderContext renderContext = new MetricsRenderContext(v2d, getWindow2D(), g2d);
		renderContext.setMetricsMedianFont(metricsManager.getMetricsMedianFont());
		renderContext.setMetricsMajorFont(metricsManager.getMetricsMajorFont());

		metricsManager.setRenderContext(renderContext);
		metricsPainter.setMetricsRenderContext(renderContext);
	}

	/**
	 * assign manager type x or y given by given device axis.
	 */
	private void assignType() {
		if (deviceAxis == DeviceAxis.AxisX) {
			metricsManager.setMetricsType(MetricsType.XMetrics);
		}
		if (deviceAxis == DeviceAxis.AxisY) {
			metricsManager.setMetricsType(MetricsType.YMetrics);
		}
	}

	/**
	 * paint X metrics for the given parameters
	 * 
	 * @param v2d
	 * @param g2d
	 */
	protected void paintMetricsX(View2D v2d, Graphics2D g2d, List<Metrics> metricsX, double baseLine, int offsetPixel) {
		Point2D deviceBaseLine = getWindow2D().userToPixel(new Point2D.Double(0, baseLine));
		for (Metrics m : metricsX) {
			if (MarkerPosition.isXCompatible(deviceMarkerPosition)) {
				m.setMarkerPosition(deviceMarkerPosition);
			} else {
				m.setMarkerPosition(MarkerPosition.S);
			}
			m.setLockMarker(true);
			if (offsetPixel > 0) {
				m.setLockMarker(false);
			}
			Point2D p = new Point2D.Double();
			if (m.getMarkerPosition() == MarkerPosition.S) {
				p = new Point2D.Double(m.getDeviceValue(), deviceBaseLine.getY() + offsetPixel);
			}
			if (m.getMarkerPosition() == MarkerPosition.N) {
				p = new Point2D.Double(m.getDeviceValue(), deviceBaseLine.getY() - offsetPixel);
			}
			// p = new Point2D.Double(m.getDeviceValue(),
			// deviceBaseLine.getY());
			m.setMarkerLocation(p);

		}
		metricsPainter.doPaintMetrics(g2d, metricsX);
	}

	/**
	 * paint the base line for x metrics
	 * 
	 * @param v2d
	 * @param g2d
	 */
	protected void paintMetricsXBaseLine(View2D v2d, Graphics2D g2d, double baseLine) {

		Point2D deviceBaseLine = getWindow2D().userToPixel(new Point2D.Double(0, baseLine));
		Color axisBaseLineColor;
		if (metricsManager.getMetricsBaseLineColor() != null) {
			axisBaseLineColor = metricsManager.getMetricsBaseLineColor();
		} else {
			axisBaseLineColor = metricsManager.getRenderContext().getWindow2D().getThemeColor();
		}
		metricsPainter.doPaintLineMetrics(g2d, new Point2D.Double(0, deviceBaseLine.getY()), new Point2D.Double(getWindow2D().getDevice2D().getDeviceWidth(), deviceBaseLine.getY()), axisBaseLineColor);

	}

	/**
	 * paint Y metrics for the given parameters
	 * 
	 * @param v2d
	 * @param g2d
	 */
	protected void paintMetricsY(View2D v2d, Graphics2D g2d, List<Metrics> metricsY, double baseLine, int offsetPixel) {
		Point2D deviceBaseLine = getWindow2D().userToPixel(new Point2D.Double(baseLine, 0));
		for (Metrics m : metricsY) {
			Point2D p = new Point2D.Double();
			p = new Point2D.Double(deviceBaseLine.getX(), m.getDeviceValue());
			m.setMarkerLocation(p);
			if (MarkerPosition.isYCompatible(deviceMarkerPosition)) {
				m.setMarkerPosition(deviceMarkerPosition);
			} else {
				m.setMarkerPosition(MarkerPosition.W);
			}
		}

		metricsPainter.doPaintMetrics(g2d, metricsY);
	}

	/**
	 * paint the base line for y metrics
	 * 
	 * @param v2d
	 * @param g2d
	 */
	protected void paintMetricsYBaseLine(View2D v2d, Graphics2D g2d, double baseLine) {

		Point2D deviceBaseLine = getWindow2D().userToPixel(new Point2D.Double(baseLine, 0));
		Color axisBaseLineColor;
		if (metricsManager.getMetricsBaseLineColor() != null) {
			axisBaseLineColor = metricsManager.getMetricsBaseLineColor();
		} else {
			axisBaseLineColor = metricsManager.getRenderContext().getWindow2D().getThemeColor();
		}
		metricsPainter.doPaintLineMetrics(g2d, new Point2D.Double(deviceBaseLine.getX(), 0), new Point2D.Double(deviceBaseLine.getX(), getWindow2D().getDevice2D().getDeviceHeight()), axisBaseLineColor);

	}

	/**
	 * Paints device metrics.
	 * 
	 * @param g2d
	 *            the graphics context
	 * @param windowPart
	 *            the window part
	 */
	protected void paintMetrics(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
		if (!isAccessible(windowPart)) {
			return;
		}
		createRenderContext(v2d, g2d);
		assignType();
		List<Metrics> metrics = metricsManager.getDeviceMetrics();
		if (deviceAxis == DeviceAxis.AxisX) {
			paintMetricsX(v2d, g2d, metrics, baseLine, 0);
			if (isPaintLine()) {
				paintMetricsXBaseLine(v2d, g2d, baseLine);
			}
		}
		if (deviceAxis == DeviceAxis.AxisY) {
			paintMetricsY(v2d, g2d, metrics, baseLine, 0);
			if (isPaintLine()) {
				paintMetricsYBaseLine(v2d, g2d, baseLine);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view
	 * .View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
	 */
	@Override
	public final void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
		paintMetrics(v2d, g2d, windowPart);
	}

}
