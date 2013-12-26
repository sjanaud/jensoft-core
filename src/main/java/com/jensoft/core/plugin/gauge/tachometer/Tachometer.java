/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.tachometer;

import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.bg.RoundGradientBackground;
import com.jensoft.core.plugin.gauge.core.binder.AnchorBinder;
import com.jensoft.core.plugin.gauge.core.binder.ArcPathBinder;
import com.jensoft.core.plugin.gauge.core.binder.PathBinder;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlass;
import com.jensoft.core.plugin.gauge.core.needle.GaugeNeedleClassicPainter;

public class Tachometer extends RadialGauge {

	/** tacho meter gauge metrics */
	private GaugeMetricsPath metricsManager;

	private static int gaugeRadius = 90;
	private static int centerUserX = 0;
	private static int centerUserY = 0;
	
	
	public Tachometer() {
		super(centerUserX, centerUserY, gaugeRadius);

		CiseroEnvelop e1 = new CiseroEnvelop();
		setEnvelop(e1);

		RoundGradientBackground bg1 = new RoundGradientBackground();
		addGaugeBackground(bg1);

		GaugeGlass g1 = new GaugeGlass.Glass1();
		GaugeGlass g2 = new GaugeGlass.Glass2();
		GaugeGlass g3 = new GaugeGlass.Glass3();
		GaugeGlass g4 = new GaugeGlass.Glass4();
		GaugeGlass g5 = new GaugeGlass.Donut2DGlass();
		GaugeGlass g6 = new GaugeGlass.GlassLabel();

		// GaugeGlass g5 = new GaugeGlass.GlassLinearEffect();

		addEffect(g1, g2, g4, g5);

		
		createBody();

	}

	public void createBody() {
		metricsManager = new GaugeMetricsPath();
		metricsManager.setMin(0);
		metricsManager.setMax(8);
		metricsManager.setCurrentValue(5.7);

		metricsManager.setGaugeNeedlePainter(new GaugeNeedleClassicPainter());
		metricsManager.setPathBinder(new ArcPathBinder(gaugeRadius-10, 210, -240));
		
//		metricsManager.setPathBinder(new PathBinder() {
//			@Override
//			public Arc2D bindPath(RadialGauge gauge) {
//				double centerX = getCenterDevice().getX();
//				double centerY = getCenterDevice().getY();
//				int radius = getRadius() - 10;
//				int startAngleDegreee = 210;
//				int endAngleDegree = -240;
//				Arc2D arc = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, endAngleDegree - startAngleDegreee, Arc2D.OPEN);
//				return arc;
//			}
//		});

		metricsManager.setNeedleBaseAnchorBinder(new AnchorBinder() {
			@Override
			public Point2D bindAnchor(RadialGauge gauge) {
				return getCenterDevice();
			}
		});

		metricsManager.setNeedleValueAnchorBinder(new AnchorBinder() {
			@Override
			public Point2D bindAnchor(RadialGauge gauge) {
				Point2D anchorValue = metricsManager.getRadialPoint(metricsManager.getCurrentValue(), 40, Side.SideRight);
				return anchorValue;
			}
		});
		
		registerGaugeMetricsPath(metricsManager);

		GlyphMetric metric = new GlyphMetric();
		metric.setValue(0);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("0");
		metric.setDivergence(20);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
		metricsManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(1);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("1");
		metric.setDivergence(25);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON2));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON2));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
		metricsManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(2);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("2");
		metric.setDivergence(30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON3));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON3));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
		metricsManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(3);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("3");
		metric.setDivergence(30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.BUTTER1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.BUTTER1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
		metricsManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(4);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("4");
		metric.setDivergence(30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.BUTTER2));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.BUTTER2));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
		metricsManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(5);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("5");
		metric.setDivergence(30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.BUTTER3));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.BUTTER3));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
		metricsManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(6);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("6");
		metric.setDivergence(30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.SCARLETRED3));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.SCARLETRED3));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
		metricsManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(7);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("7");
		metric.setDivergence(25);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.SCARLETRED3));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.SCARLETRED3));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
		metricsManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(8);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("8");
		metric.setDivergence(20);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.SCARLETRED3));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.SCARLETRED3));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
		metricsManager.addMetric(metric);


	}
}
