/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.watch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.DefaultMarker;
import com.jensoft.core.glyphmetrics.painter.marker.RectangleMarker;
import com.jensoft.core.glyphmetrics.painter.marker.RoundMarker;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.glyphmetrics.painter.marker.TriangleMarker;
import com.jensoft.core.glyphmetrics.painter.marker.TriangleMarker.TriangleDirection;
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
import com.jensoft.core.plugin.gauge.core.needle.GaugeNeedleClassicWatchHour;
import com.jensoft.core.plugin.gauge.core.needle.GaugeNeedleClassicWatchMinute;
import com.jensoft.core.plugin.gauge.core.needle.GaugeNeedleClassicWatchSecond;

public class Watch extends RadialGauge {

	private GaugeMetricsPath hourMetricsManager;
	private GaugeMetricsPath minuteMetricsManager;
	private GaugeMetricsPath secondMetricsManager;

	private GaugeMetricsPath metricsManager;
	
    public Watch() {
        super(0, 0, 90);

        CiseroEnvelop e1 = new CiseroEnvelop(4);
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
       
        
        createWatch();
        
        hourMetricsManager.setCurrentValue(2);
        minuteMetricsManager.setCurrentValue(30);
        secondMetricsManager.setCurrentValue(38);
        
    }
    
    private void createWatch(){
    	
    	PathBinder pathBinder = new PathBinder() {		
			@Override
			public Shape bindPath(RadialGauge gauge) {
				double centerX = getCenterDevice().getX();
				double centerY = getCenterDevice().getY();
				int radius = getRadius() - 10;
				int startAngleDegreee = 90;
				int extendsAngleDegree = -360;
				Arc2D arc = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, extendsAngleDegree, Arc2D.OPEN);
				return arc;
			}
		};
		
		AnchorBinder needleBase = new AnchorBinder() {
			@Override
			public Point2D bindAnchor(RadialGauge gauge) {
				return getCenterDevice();
			}
		};
		
		AnchorBinder needleHourAnchor = new AnchorBinder() {
			@Override
			public Point2D bindAnchor(RadialGauge gauge) {
				Point2D needle = hourMetricsManager.getRadialPoint(hourMetricsManager.getCurrentValue(), 50, Side.SideRight);
				return needle;
			}
		};
		
		AnchorBinder needleMinuteAnchor = new AnchorBinder() {
			@Override
			public Point2D bindAnchor(RadialGauge gauge) {
				Point2D needle = minuteMetricsManager.getRadialPoint(minuteMetricsManager.getCurrentValue(), 20, Side.SideRight);
				return needle;
			}
		};
		
		AnchorBinder needleSecondAnchor = new AnchorBinder() {
			@Override
			public Point2D bindAnchor(RadialGauge gauge) {
				Point2D needle = secondMetricsManager.getRadialPoint(secondMetricsManager.getCurrentValue(), 20, Side.SideRight);
				return needle;
			}
		};
		
    	
    	metricsManager = new GaugeMetricsPath();
    	metricsManager.setRange(0, 12);
    	metricsManager.setPathBinder(pathBinder);
    	//not need needle anchor, only for manage metrics
    	
		hourMetricsManager = new GaugeMetricsPath();
		hourMetricsManager.setRange(0, 12);
		hourMetricsManager.setPathBinder(pathBinder);
		hourMetricsManager.setNeedleBaseAnchorBinder(needleBase);
		hourMetricsManager.setNeedleValueAnchorBinder(needleHourAnchor);
		hourMetricsManager.setGaugeNeedlePainter(new GaugeNeedleClassicWatchHour());

		minuteMetricsManager = new GaugeMetricsPath();
		minuteMetricsManager.setRange(0, 60);
		minuteMetricsManager.setPathBinder(pathBinder);
		minuteMetricsManager.setNeedleBaseAnchorBinder(needleBase);
		minuteMetricsManager.setNeedleValueAnchorBinder(needleMinuteAnchor);
		minuteMetricsManager.setGaugeNeedlePainter(new GaugeNeedleClassicWatchMinute());

		secondMetricsManager = new GaugeMetricsPath();
		secondMetricsManager.setRange(0, 60);
		secondMetricsManager.setPathBinder(pathBinder);
		secondMetricsManager.setNeedleBaseAnchorBinder(needleBase);
		secondMetricsManager.setNeedleValueAnchorBinder(needleSecondAnchor);
		secondMetricsManager.setGaugeNeedlePainter(new GaugeNeedleClassicWatchSecond());

		registerGaugeMetricsPath(metricsManager);
		registerGaugeMetricsPath(hourMetricsManager);
		registerGaugeMetricsPath(minuteMetricsManager);
		registerGaugeMetricsPath(secondMetricsManager);
		
		createMainMetrics();
		createMainTicks();
    }

    private void createMainTicks() {
		RoundMarker rm = new RoundMarker(NanoChromatique.ORANGE, NanoChromatique.WHITE, 4);
		GlyphFill gf = new GlyphFill(Color.WHITE, NanoChromatique.BLUE);
		TicTacMarker ttm = new TicTacMarker(NanoChromatique.YELLOW);
		ttm.setSize(4);
		ttm.setDivergence(4);
		Font f = InputFonts.getFont(InputFonts.ELEMENT, 16);
		for (int i = 0; i < 12; i++) {
			GlyphMetric metric = new GlyphMetric();
			metric.setValue(i);
			metric.setStylePosition(StylePosition.Default);
			metric.setMetricsLabel(i + "");
			metric.setFont(f);
			metric.setDivergence(16);
			if (i == 0 || i == 3 || i == 6 || i == 9) {
				// metric.setGlyphMetricMarkerPainter(new
				// RoundMarker(NanoChromatique.ORANGE, NanoChromatique.WHITE,
				// 4));
				// metric.setGlyphMetricMarkerPainter(ttm);
			} else {
				metric.setGlyphMetricFill(gf);
				metric.setGlyphMetricMarkerPainter(ttm);
				metricsManager.addMetric(metric);
			}

		}

		for (int i = 0; i < 60; i++) {
			GlyphMetric metric = new GlyphMetric();
			metric.setValue(i);
			// metric.setDivergence(16);

			metric.setGlyphMetricMarkerPainter(new DefaultMarker(NanoChromatique.PURPLE.brighter(), 2));
			minuteMetricsManager.addMetric(metric);
		}
	}

	private void createMainMetrics() {
		GlyphFill gf = new GlyphFill(Color.WHITE, NanoChromatique.BLUE);
		TicTacMarker ttm = new TicTacMarker(NanoChromatique.RED);
		ttm.setSize(3);
		ttm.setDivergence(3);
		Font f = InputFonts.getFont(InputFonts.ELEMENT, 36);

		// 6 o'clock
		GlyphMetric metric = new GlyphMetric();
		metric.setValue(0);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("12");
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gf);
		metric.setFont(f);
		metricsManager.addMetric(metric);

		// 3 o'clock
		metric = new GlyphMetric();
		metric.setValue(3);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("3");
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gf);
		metric.setFont(f);
		metricsManager.addMetric(metric);

		// 3 o'clock
		metric = new GlyphMetric();
		metric.setValue(4.4);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("3");
		metric.setDivergence(5);

//		// triangle marker
//		TriangleMarker triangle = new TriangleMarker(Color.WHITE, NanoChromatique.GREEN);
//		triangle.setDirection(TriangleDirection.Out);
//		triangle.setGlobalRadialShift(-20);
//		metric.setGlyphMetricMarkerPainter(triangle);
//		metric.setFont(f);
//		metricsManager.addMetric(metric);
//
//		// rectangle
//		metric = new GlyphMetric();
//		metric.setValue(8.5);
//		metric.setStylePosition(StylePosition.Default);
//		metric.setMetricsLabel("3");
//		metric.setDivergence(5);
//		// metric.setGlyphMetricFill(gf);
//
//		RectangleMarker rectangle = new RectangleMarker(Color.WHITE, NanoChromatique.GREEN, 3, 8);
//		metric.setGlyphMetricMarkerPainter(rectangle);
//		metric.setFont(f);
//		metricsManager.addMetric(metric);

		// 6
		metric = new GlyphMetric();
		metric.setValue(6);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("6");
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gf);
		// metric.setGlyphMetricMarkerPainter(ttm);
		metric.setFont(f);
		metricsManager.addMetric(metric);

		// 9
		metric = new GlyphMetric();
		metric.setValue(9);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("9");
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gf);
		// metric.setGlyphMetricMarkerPainter(ttm);
		metric.setFont(f);

		metricsManager.addMetric(metric);
	}


}
