package com.jensoft.core.plugin.plot.catalog;

import com.jensoft.core.catalog.nature.JenSoftView;
import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.Axis;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManagerOLD.MetricsModelRangeCollections;
import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.plugin.plot.PlotAnchorsPlugin;
import com.jensoft.core.plugin.plot.PlotPlugin;
import com.jensoft.core.plugin.plot.spline.NatCubicClosed;
import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

@JenSoftView
public class NaturalClosedCubicPlotView extends View2D {

	public NaturalClosedCubicPlotView() {
		super(80);
		
		
		Window2D w2d = new Window2D.Linear(-100, 100, -100, 100);
		w2d.setThemeColor(NanoChromatique.GREEN);
		registerWindow2D(w2d);
		
		w2d.registerPlugin(new OutlinePlugin());
		
		AxisMetricsPlugin.ModeledMetrics metrics = new AxisMetricsPlugin.ModeledMetrics(Axis.AxisSouth);
		metrics.registerMetricsModels(MetricsModelRangeCollections.FemptoPeta);
		w2d.registerPlugin(metrics);
		
		AxisMetricsPlugin.ModeledMetrics metrics2 = new AxisMetricsPlugin.ModeledMetrics(Axis.AxisWest);
		metrics2.registerMetricsModels(MetricsModelRangeCollections.FemptoPeta);
		w2d.registerPlugin(metrics2);
		
		
		PlotPlugin plotsPlugin = new PlotPlugin();
		w2d.registerPlugin(plotsPlugin);
		
		
		
		
		//Natural closed Cubic
		NatCubicClosed plot = new NatCubicClosed();
		plot.addPoint(-80, 30);
		plot.addPoint(-60, -20);
		plot.addPoint(-40, 70);
		plot.addPoint(-20, 20);
		
		plot.addPoint(20, -20);
		plot.addPoint(40, 50);
		plot.addPoint(60, -20);
		plot.addPoint(80, 90);
		
		plot.setPlotDrawColor(NanoChromatique.PURPLE);
		plotsPlugin.addPlot(plot);
		
		
		//Plot point Handler
		PlotAnchorsPlugin anchorsPlugin = new PlotAnchorsPlugin();
		w2d.registerPlugin(anchorsPlugin);
		
		
		anchorsPlugin.addPlot(plot);
		
		
		//Zoom wheel
		ZoomWheelPlugin wheel = new ZoomWheelPlugin();
		w2d.registerPlugin(wheel);
		
	}


	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new NaturalClosedCubicPlotView());
	}
	

}
