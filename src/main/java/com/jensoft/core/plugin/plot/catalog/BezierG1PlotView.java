package com.jensoft.core.plugin.plot.catalog;

import com.jensoft.core.catalog.nature.JenSoftView;
import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.Axis;
import com.jensoft.core.plugin.metrics.manager.ModeledMetricsManager.MetricsModelRangeCollections;
import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.plugin.plot.PlotAnchorsPlugin;
import com.jensoft.core.plugin.plot.PlotPlugin;
import com.jensoft.core.plugin.plot.spline.BezierG1;
import com.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import com.jensoft.core.plugin.translate.TranslatePlugin;
import com.jensoft.core.plugin.translate.TranslateX;
import com.jensoft.core.plugin.translate.TranslateY;
import com.jensoft.core.plugin.zoom.box.ZoomBoxDefaultDeviceContext;
import com.jensoft.core.plugin.zoom.box.ZoomBoxPlugin;
import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

@JenSoftView
public class BezierG1PlotView extends View2D {

	public BezierG1PlotView() {
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
		
		
		BezierG1 plot = new BezierG1();
		plot.addPoint(-80, 30);
		plot.addPoint(-60, -20);
		plot.addPoint(-40, 70);
		plot.addPoint(-20, 20);
		
		plot.addPoint(20, -20);
		plot.addPoint(40, 50);
		plot.addPoint(60, -20);
		plot.addPoint(80, 90);
		
		plot.setPlotDrawColor(NanoChromatique.BLUE);
		plotsPlugin.addPlot(plot);
		
		
		
		
		//Plot point Handler
		PlotAnchorsPlugin anchorsPlugin = new PlotAnchorsPlugin();
		w2d.registerPlugin(anchorsPlugin);
		
		anchorsPlugin.addPlot(plot);
		
		
		
		//Zoom wheel
		ZoomWheelPlugin wheel = new ZoomWheelPlugin();
		w2d.registerPlugin(wheel);
		
		
		//zoom box
		ZoomBoxPlugin box = new ZoomBoxPlugin();
		ZoomBoxDefaultDeviceContext ctx = new ZoomBoxDefaultDeviceContext();
		box.registerContext(ctx);
		
		//translate
		TranslatePlugin translatePlugin = new TranslatePlugin();
		TranslateDefaultDeviceContext ctxt = new TranslateDefaultDeviceContext();
		translatePlugin.registerContext(ctxt);
		w2d.registerPlugin(translatePlugin);
		TranslateX tx = new TranslateX();
		TranslateY ty = new TranslateY();
		translatePlugin.registerWidget(tx, ty);
		
		w2d.registerPlugin(box);
		
	}


	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new BezierG1PlotView());
	}
	

}
