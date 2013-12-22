/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.metrics;

import java.awt.Color;
import java.awt.Font;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.Axis;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.FlowMetrics;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.FreeMetrics;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.ModeledMetrics;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.Multiplier3Metrics;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.MultiplierMetrics;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.StaticMetrics;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.TimeMetrics;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManager.MetricsModelRangeCollections;
import com.jensoft.core.plugin.metrics.manager.TimeMetricsManager;
import com.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.binding.X2DBinding;

/**
 * <code>AxisMetricsInflater</code>
 * 
 * @author Sebastien Janaud
 */
public abstract class AxisMetricsInflater<A extends AxisMetricsPlugin<?>> extends AbstractX2DPluginInflater<A> implements X2DMetricsElement {

	/**
	 * <code>FlowMetricsInflater</code>
	 * 
	 * @author sebastien janaud
	 */
	@X2DBinding(xsi = "FlowMetricsInflater", plugin = FlowMetrics.class)
	public static class FlowMetricsInflater extends AxisMetricsInflater<FlowMetrics> {

		
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org
		 * .w3c.dom.Element)
		 */
		@Override
		public AxisMetricsPlugin.FlowMetrics inflate(Element plugin) {
			Double flowStart = elementDouble(plugin, ELEMENT_METRICS_FLOW_START);
			Double flowEnd = elementDouble(plugin, ELEMENT_METRICS_FLOW_END);
			Double flowInterval = elementDouble(plugin, ELEMENT_METRICS_FLOW_INTERVAL);
			String axisName = elementText(plugin, ELEMENT_METRICS_AXIS);
			Axis axis = Axis.parse(axisName);
			AxisMetricsPlugin.FlowMetrics flow = new FlowMetrics(flowStart, flowEnd, flowInterval, axis);
			completeFromAbstract(flow, plugin);
			return flow;
		}

	}

	/**
	 * <code>FreeMetricsInflater</code>
	 * 
	 * @author sebastien janaud
	 */
	@X2DBinding(xsi = "FreeMetricsInflater", plugin = FreeMetrics.class)
	public static class FreeMetricsInflater extends AxisMetricsInflater<FreeMetrics> {

		

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org
		 * .w3c.dom.Element)
		 */
		@Override
		public AxisMetricsPlugin.FreeMetrics inflate(Element plugin) {
			String axisName = elementText(plugin, ELEMENT_METRICS_AXIS);
			Axis axis = Axis.parse(axisName);

			AxisMetricsPlugin.FreeMetrics free = new FreeMetrics(axis);
			NodeList freeMetricsElements = plugin.getElementsByTagName(ELEMENT_METRICS_FREE);
			for (int i = 0; i < freeMetricsElements.getLength(); i++) {
				Element element = (Element) freeMetricsElements.item(i);
				Double value = elementDouble(element, ELEMENT_METRICS_FREE_VALUE);
				String text = elementText(element, ELEMENT_METRICS_FREE_TEXT);

				if (text == null) {
					free.addMetrics(value);
				} else {
					free.addMetrics(value, text);
				}

			}

			completeFromAbstract(free, plugin);
			return free;
		}

	}

	/**
	 * <code>MultiplierMetricsInflater</code>
	 * 
	 * @author sebastien janaud
	 */
	@X2DBinding(xsi = "AxisMultiplierMetrics", plugin = MultiplierMetrics.class)
	public static class MultiplierMetricsInflater extends AxisMetricsInflater<MultiplierMetrics> {

		
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org
		 * .w3c.dom.Element)
		 */
		@Override
		public AxisMetricsPlugin.MultiplierMetrics inflate(Element plugin) {
			Double ref = elementDouble(plugin, ELEMENT_METRICS_MULTIPLIER_REF);
			Double mul = elementDouble(plugin, ELEMENT_METRICS_MULTIPLIER_MULTIPLIER);
			AxisMetricsPlugin.MultiplierMetrics multiplier = new AxisMetricsPlugin.MultiplierMetrics(ref, mul, Axis.AxisSouth);
			completeFromAbstract(multiplier, plugin);
			return multiplier;
		}

	}

	/**
	 * <code>MultiMultiplierMetricsInflater</code>
	 * 
	 * @author sebastien janaud
	 */
	@X2DBinding(xsi = "AxisMultiMultiplierMetrics", plugin = Multiplier3Metrics.class)
	public static class MultiMultiplierMetricsInflater extends AxisMetricsInflater<Multiplier3Metrics> {

		
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org
		 * .w3c.dom.Element)
		 */
		@Override
		public AxisMetricsPlugin.Multiplier3Metrics inflate(Element plugin) {
			Double ref = elementDouble(plugin, ELEMENT_METRICS_MULTI_MULTIPLIER_REF);
			Double majorMultiplier = elementDouble(plugin, ELEMENT_METRICS_MULTI_MULTIPLIER_MAJOR_MULTIPLIER);
			Double medianMultiplier = elementDouble(plugin, ELEMENT_METRICS_MULTI_MULTIPLIER_MEDIAN_MULTIPLIER);
			Double minorMultiplier = elementDouble(plugin, ELEMENT_METRICS_MULTI_MULTIPLIER_MINOR_MULTIPLIER);

			AxisMetricsPlugin.Multiplier3Metrics multiMultiplier = new AxisMetricsPlugin.Multiplier3Metrics(ref, getAxis(plugin));
			if (majorMultiplier != null) {
				multiMultiplier.setMajor(majorMultiplier);
			}
			if (medianMultiplier != null) {
				multiMultiplier.setMedian(medianMultiplier);
			}
			if (minorMultiplier != null) {
				multiMultiplier.setMinor(minorMultiplier);
			}
			completeFromAbstract(multiMultiplier, plugin);
			return multiMultiplier;
		}
	}

	/**
	 * <code>StaticMetricsInflater</code>
	 * 
	 * @author sebastien janaud
	 */
	@X2DBinding(xsi = "AxisStaticMetrics", plugin = StaticMetrics.class)
	public static class StaticMetricsInflater extends AxisMetricsInflater<StaticMetrics> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org
		 * .w3c.dom.Element)
		 */
		@Override
		public AxisMetricsPlugin.StaticMetrics inflate(Element plugin) {
			int metricsCount = elementInteger(plugin, ELEMENT_METRICS_STATIC_COUNT);
			AxisMetricsPlugin.StaticMetrics staticMetrics = new AxisMetricsPlugin.StaticMetrics(metricsCount, getAxis(plugin));
			completeFromAbstract(staticMetrics, plugin);
			return staticMetrics;
		}

	}

	/**
	 * <code>ModeledMetricsInflater</code>
	 * 
	 * @author sebastien janaud
	 */
	@X2DBinding(xsi = "AxisModeledMetrics", plugin = ModeledMetrics.class)
	public static class ModeledMetricsInflater extends AxisMetricsInflater<ModeledMetrics> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org
		 * .w3c.dom.Element)
		 */
		@Override
		public AxisMetricsPlugin.ModeledMetrics inflate(Element plugin) {
			AxisMetricsPlugin.ModeledMetrics modeledMetrics = new ModeledMetrics(getAxis(plugin));
			modeledMetrics.registerMetricsModels(MetricsModelRangeCollections.YoctoYotta);
			completeFromAbstract(modeledMetrics, plugin);
			return modeledMetrics;
		}

	}

	/**
	 * <code>TimeMetricsInflater</code>
	 * 
	 * @author sebastien janaud
	 */
	@X2DBinding(xsi = "AxisTimeMetrics", plugin = TimeMetrics.class)
	public static class TimeMetricsInflater extends AxisMetricsInflater<TimeMetrics> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org
		 * .w3c.dom.Element)
		 */
		@Override
		public AxisMetricsPlugin.TimeMetrics inflate(Element plugin) {

			AxisMetricsPlugin.TimeMetrics timingMetrics = new AxisMetricsPlugin.TimeMetrics(getAxis(plugin));

			timingMetrics.registerTimeModel(new TimeMetricsManager.Minute1Model());
			timingMetrics.registerTimeModel(new TimeMetricsManager.Minute15Model());

			timingMetrics.registerTimeModel(new TimeMetricsManager.HourModel());
			timingMetrics.registerTimeModel(new TimeMetricsManager.Hour3Model());

			timingMetrics.registerTimeModel(new TimeMetricsManager.DayNumberModel());
			timingMetrics.registerTimeModel(new TimeMetricsManager.DayShortTextModel());
			timingMetrics.registerTimeModel(new TimeMetricsManager.DayLongTextModel());

			timingMetrics.registerTimeModel(new TimeMetricsManager.WeekModel());
			timingMetrics.registerTimeModel(new TimeMetricsManager.WeekDurationDurationModel());

			timingMetrics.registerTimeModel(new TimeMetricsManager.MonthModel());
			timingMetrics.registerTimeModel(new TimeMetricsManager.MonthDurationModel());

			completeFromAbstract(timingMetrics, plugin);

			return timingMetrics;
		}

	}

	protected Axis getAxis(Element plugin) {
		String axisName = elementText(plugin, ELEMENT_METRICS_AXIS);
		Axis axis = Axis.parse(axisName);
		return axis;
	}

	/**
	 * Complete the axis plug-in by property of super class
	 * 
	 * @param abstratPlugin
	 * @param pluginElement
	 */
	protected void completeFromAbstract(AxisMetricsPlugin<?> abstratPlugin, Element pluginElement) {
		Integer axisSpacing = elementInteger(pluginElement, ELEMENT_METRICS_AXIS_SPACING);
		Boolean linePaint = elementBoolean(pluginElement, ELEMENT_METRICS_AXIS_LINE_PAINT);
		Color lineColor = elementColor(pluginElement, ELEMENT_METRICS_AXIS_LINE_COLOR);
		Color markerColor = elementColor(pluginElement, ELEMENT_METRICS_AXIS_MARKER_COLOR);
		Color textColor = elementColor(pluginElement, ELEMENT_METRICS_AXIS_TEXT_COLOR);
		Font f = elementFont(pluginElement, ELEMENT_METRICS_AXIS_TEXT_FONT);

		if (axisSpacing != null && axisSpacing > 0) {
			abstratPlugin.setAxisSpacing(axisSpacing);
		}
		if (linePaint != null) {
			abstratPlugin.setPaintAxisBaseLine(linePaint);
		}
		if (lineColor != null) {
			abstratPlugin.setMetricsBaseLineColor(lineColor);
		}
		if (markerColor != null) {
			abstratPlugin.setMetricsMarkerColor(markerColor);
		}
		if (textColor != null) {
			abstratPlugin.setMetricsLabelColor(textColor);
		}
		if (f != null) {
			abstratPlugin.setMetricsFont(f);
		}
	}

}
