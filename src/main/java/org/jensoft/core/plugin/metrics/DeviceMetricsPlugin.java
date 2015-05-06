/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.metrics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;

import org.jensoft.core.device.Device;
import org.jensoft.core.plugin.metrics.AxisMetricsPlugin.TimeMetrics;
import org.jensoft.core.plugin.metrics.Metrics.MarkerPosition;
import org.jensoft.core.plugin.metrics.Metrics.MetricsType;
import org.jensoft.core.plugin.metrics.format.IMetricsFormat;
import org.jensoft.core.plugin.metrics.manager.AbstractMetricsManager;
import org.jensoft.core.plugin.metrics.manager.FlowMetricsManager;
import org.jensoft.core.plugin.metrics.manager.FreeMetricsManager;
import org.jensoft.core.plugin.metrics.manager.ModeledMetricsManager;
import org.jensoft.core.plugin.metrics.manager.StaticMetricsManager;
import org.jensoft.core.plugin.metrics.manager.TimeMetricsManager;
import org.jensoft.core.plugin.metrics.manager.ModeledMetricsManager.MetricsModel;
import org.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>DeviceMetricsPlugin</code> takes the responsibility to manage metrics
 * on {@link Device}
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public abstract class DeviceMetricsPlugin<M extends AbstractMetricsManager> extends AbstractMetricsPlugin<M> {


	/** axis base line at the constant x or y value */
	private double baseLine;

	/** device axis x or y */
	private DeviceAxis deviceAxis;

	/** device axis x or y */
	private MarkerPosition deviceMarkerPosition;


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

		
		@Override
		protected void paintMetrics(View v2d, Graphics2D g2d, ViewPart viewPart) {
			if (!super.isAccessible(viewPart)) {
				return;
			}
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
		 * get all registered {@link MetricsModel}
		 * 
		 * @return models
		 */
		public List<MetricsModel> getMetricsModels() {
			return getMetricsManager().getMetricsModels();
		}

//		/* (non-Javadoc)
//		 * @see com.jensoft.core.plugin.metrics.DeviceMetricsPlugin#paintMetrics(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
//		 */
//		@Override
//		protected void paintMetrics(View view, Graphics2D g2d, ViewPart viewPart) {
//			if (!super.isAccessible(viewPart)) {
//				return;
//			}
//			super.createRenderContext(view, g2d);
//			super.assignType();
//			int axisSpacing = 0;
//			List<MetricsModelOLD> sequence = getMetricsManager().getSequenceMetrics();
//			//System.out.println("--SEQUENCE MODELS--");
//			for (MetricsModelOLD model : sequence) {
//				if (model.getRank() > 0 && !sequence.get(0).isMinimal())
//					continue;
//				if (model.getRank() > 1)
//					continue;
//				//System.out.println("applicable model : " + model);
//				List<Metrics> metrics = model.generateMetrics();
//				if (super.deviceAxis == DeviceAxis.AxisX) {
//					paintMetricsX(view, g2d, metrics, super.baseLine, axisSpacing);
//				}
//				if (super.deviceAxis == DeviceAxis.AxisY) {
//					paintMetricsY(view, g2d, metrics, super.baseLine, axisSpacing);
//				}
//				axisSpacing = axisSpacing + model.getPixelAxisHolder();
//			}
//
//			if (super.deviceAxis == DeviceAxis.AxisX) {
//				paintMetricsXBaseLine(view, g2d, super.baseLine);
//			}
//			if (super.deviceAxis == DeviceAxis.AxisY) {
//				paintMetricsYBaseLine(view, g2d, super.baseLine);
//			}
//		}
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
		super(manager);
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
	 *            the marker position N,S for x axis, W,E for y axis
	 */
	public DeviceMetricsPlugin(M manager, double baseLine, MarkerPosition deviceMarkerPosition, DeviceAxis deviceAxis) {
		this(manager, baseLine, deviceAxis);
		this.deviceMarkerPosition = deviceMarkerPosition;
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
	 * true if the context is accessible, false otherwise
	 * 
	 * @param viewPart
	 * @return true if the context is accessible, false otherwise
	 */
	private boolean isAccessible(ViewPart viewPart) {
		if (viewPart != ViewPart.Device) {
			return false;
		}
		return true;
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
	 * assign manager type x or y given by given device axis.
	 */
	private void assignType() {
		if (deviceAxis == DeviceAxis.AxisX) {
			getMetricsManager().setMetricsType(MetricsType.XMetrics);
		}
		if (deviceAxis == DeviceAxis.AxisY) {
			getMetricsManager().setMetricsType(MetricsType.YMetrics);
		}
	}

	/**
	 * paint X metrics for the given parameters
	 * 
	 * @param v2d
	 * @param g2d
	 */
	protected void paintMetricsX(View v2d, Graphics2D g2d, List<Metrics> metricsX, double baseLine, int offsetPixel) {
		Point2D deviceBaseLine = getProjection().userToPixel(new Point2D.Double(0, baseLine));
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
		getMetricsPainter().doPaintMetrics(g2d, metricsX);
	}

	/**
	 * paint the base line for x metrics
	 * 
	 * @param view
	 * @param g2d
	 */
	protected void paintMetricsXBaseLine(View view, Graphics2D g2d, double baseLine) {

		Point2D deviceBaseLine = getProjection().userToPixel(new Point2D.Double(0, baseLine));
		Color axisBaseLineColor;
		if (getBaseLineColor() != null) {
			axisBaseLineColor = getBaseLineColor();
		} else {
			axisBaseLineColor = getProjection().getThemeColor();
		}
		getMetricsPainter().doPaintLineMetrics(g2d, new Point2D.Double(0, deviceBaseLine.getY()), new Point2D.Double(getProjection().getDevice2D().getDeviceWidth(), deviceBaseLine.getY()), axisBaseLineColor);

	}

	/**
	 * paint Y metrics for the given parameters
	 * 
	 * @param v2d
	 * @param g2d
	 */
	protected void paintMetricsY(View v2d, Graphics2D g2d, List<Metrics> metricsY, double baseLine, int offsetPixel) {
		Point2D deviceBaseLine = getProjection().userToPixel(new Point2D.Double(baseLine, 0));
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

		getMetricsPainter().doPaintMetrics(g2d, metricsY);
	}

	/**
	 * paint the base line for y metrics
	 * 
	 * @param view
	 * @param g2d
	 */
	protected void paintMetricsYBaseLine(View view, Graphics2D g2d, double baseLine) {

		Point2D deviceBaseLine = getProjection().userToPixel(new Point2D.Double(baseLine, 0));
		Color axisBaseLineColor;
		if (getBaseLineColor() != null) {
			axisBaseLineColor = getBaseLineColor();
		} else {
			axisBaseLineColor = getProjection().getThemeColor();
		}
		getMetricsPainter().doPaintLineMetrics(g2d, new Point2D.Double(deviceBaseLine.getX(), 0), new Point2D.Double(deviceBaseLine.getX(), getProjection().getDevice2D().getDeviceHeight()), axisBaseLineColor);

	}

	/**
	 * Paints device metrics.
	 * 
	 * @param g2d
	 *            the graphics context
	 * @param viewPart
	 *            the window part
	 */
	protected void paintMetrics(View v2d, Graphics2D g2d, ViewPart viewPart) {
		if (!isAccessible(viewPart)) {
			return;
		}
		assignType();
		List<Metrics> metrics = getMetricsManager().getDeviceMetrics();
		if (deviceAxis == DeviceAxis.AxisX) {
			paintMetricsX(v2d, g2d, metrics, baseLine, 0);
			if (isBaseLinePaint()) {
				paintMetricsXBaseLine(v2d, g2d, baseLine);
			}
		}
		if (deviceAxis == DeviceAxis.AxisY) {
			paintMetricsY(v2d, g2d, metrics, baseLine, 0);
			if (isBaseLinePaint()) {
				paintMetricsYBaseLine(v2d, g2d, baseLine);
			}
		}
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
	 */
	@Override
	public final void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
		paintMetrics(view, g2d, viewPart);
	}

}
