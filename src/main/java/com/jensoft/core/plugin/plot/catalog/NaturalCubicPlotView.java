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
import com.jensoft.core.plugin.plot.spline.NatCubic;
import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

@JenSoftView
public class NaturalCubicPlotView extends View2D {

	public NaturalCubicPlotView() {
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
		
		
		
		//Natural Cubic
		NatCubic natCubic = new NatCubic();
		natCubic.addPoint(-80, 30);
		natCubic.addPoint(-60, -20);
		natCubic.addPoint(-40, 70);
		natCubic.addPoint(-20, 20);
		
		natCubic.addPoint(20, -20);
		natCubic.addPoint(40, 50);
		natCubic.addPoint(60, -20);
		natCubic.addPoint(80, 90);
		
		natCubic.setPlotDrawColor(NanoChromatique.YELLOW);
		plotsPlugin.addPlot(natCubic);
		
		
		
		
		
		//Plot point Handler
		PlotAnchorsPlugin anchorsPlugin = new PlotAnchorsPlugin();
		w2d.registerPlugin(anchorsPlugin);
		
		anchorsPlugin.addPlot(natCubic);
		
		
		//Zoom wheel
		ZoomWheelPlugin wheel = new ZoomWheelPlugin();
		w2d.registerPlugin(wheel);
		
	}


	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new NaturalCubicPlotView());
	}
	

}
