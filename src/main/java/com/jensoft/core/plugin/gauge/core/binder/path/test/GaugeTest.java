package com.jensoft.core.plugin.gauge.core.binder.path.test;

import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.binder.path.PathArcAutoBinder;
import com.jensoft.core.plugin.gauge.core.binder.path.PathAutoBinder;

public class GaugeTest extends RadialGauge {

	public GaugeTest() {
		super(0, 0, 90);
		
				
		GaugeMetricsPath path = new GaugeMetricsPath();
		//path.setPathPainter(new MetricsPathDefaultDraw(NanoChromatique.LIGHT_GRAY));
		
		//path on 0°
		//PathAutoArcBinder autoArcBinder = new PathAutoArcBinder(120, 140, 0);
		
		//path on 180°
		PathAutoBinder autoArcBinder = new PathArcAutoBinder(60, 60, 180);
		
		//quart 1 : 0 à 90
		//PathAutoArcBinder autoArcBinder = new PathAutoArcBinder(120, 140, 35);
		
		//quart 2 : 90 à 180
		//PathAutoArcBinder autoArcBinder = new PathAutoArcBinder(50, 80, 135);
		
		//quart 3 : 180 à 270
		//PathAutoArcBinder autoArcBinder = new PathAutoArcBinder(90, 80, 230);
		
		//quart 4 : 270 à 360
		//PathAutoArcBinder autoArcBinder = new PathAutoArcBinder(90, 80, 320);
		
		path.setPathBinder(autoArcBinder);
		
		registerGaugeMetricsPath(path);
		
		
	}

}
