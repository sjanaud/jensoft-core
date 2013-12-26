/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.speedometer;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.palette.TexturePalette;
import com.jensoft.core.plugin.gauge.core.AnchorBinder;
import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import com.jensoft.core.plugin.gauge.core.PathBinder;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.bg.TextureBackground;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlass;

public class Speedometer extends RadialGauge {

	
	/**speedo meter gauge metrics*/
	private GaugeMetricsPath metricsManager;
		
    public Speedometer() {
        super(0, 0, 90);

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);
        
        addGaugeBackground(new TextureBackground(TexturePalette.getSquareCarbonFiber()));


        GaugeGlass g1 = new GaugeGlass.Glass1();
        GaugeGlass g2 = new GaugeGlass.Glass2();
        GaugeGlass g3 = new GaugeGlass.Glass3();
        GaugeGlass g4 = new GaugeGlass.Glass4();
        GaugeGlass g5 = new GaugeGlass.Donut2DGlass();
        GaugeGlass g6 = new GaugeGlass.GlassLabel();
        
        //GaugeGlass g5 = new GaugeGlass.GlassLinearEffect();
       
        
        addEffect(g1,g2,g4,g5);
       
       
        
        
        createBody();
    }

   
	
	

	/**
	 * create speedometer body
	 */
	public void createBody() {
		metricsManager = new GaugeMetricsPath();
		metricsManager.setMin(0);
		metricsManager.setMax(280);
		metricsManager.setCurrentValue(186);
		
		metricsManager.setPathBinder(new PathBinder() {
			@Override
			public Arc2D bindPath(RadialGauge gauge) {
				double centerX = getCenterDevice().getX();
				double centerY = getCenterDevice().getY();
				int radius = getRadius() - 10;
				int startAngleDegreee = 260;
				int extendsAngleDegree = -340;
				Arc2D arc = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, extendsAngleDegree, Arc2D.OPEN);
				return arc;
			}
		});
		
		metricsManager.setNeedleBaseAnchorBinder(new AnchorBinder() {
			@Override
			public Point2D bindAnchor(RadialGauge gauge) {
				return getCenterDevice();
			}
		});
		
		metricsManager.setNeedleValueAnchorBinder(new AnchorBinder() {
			@Override
			public Point2D bindAnchor(RadialGauge gauge) {
				Point2D anchorValue = metricsManager.getRadialPoint(metricsManager.getCurrentValue(), 20, Side.SideRight);
				return anchorValue;
			}
		});

		registerGaugeMetricsPath(metricsManager);

		GlyphFill gf = new GlyphFill(Color.WHITE, NanoChromatique.RED.brighter());
		TicTacMarker ttm = new TicTacMarker(NanoChromatique.GREEN);
		ttm.setSize(3);
		ttm.setDivergence(3);
		Font f = InputFonts.getFont(InputFonts.ELEMENT, 14);

		for (int i = 20; i < 250; i = i + 20) {
			GlyphMetric metric = new GlyphMetric();
			metric.setValue(i);
			metric.setStylePosition(StylePosition.Default);
			metric.setMetricsLabel(i + "");
			metric.setDivergence(16);
			metric.setGlyphMetricFill(gf);
			metric.setGlyphMetricMarkerPainter(ttm);
			metric.setFont(f);
			metricsManager.addMetric(metric);
		}

		// add legend
		GlyphMetric metric = new GlyphMetric();
		metric.setValue(280);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("Km/h");
		metric.setDivergence(30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
		metric.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 16));
		metricsManager.addMetric(metric);

		
	}

}
