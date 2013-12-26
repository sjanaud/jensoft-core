/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.compass;

import java.awt.Color;
import java.awt.Font;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.palette.TexturePalette;
import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.bg.TextureBackground;
import com.jensoft.core.plugin.gauge.core.binder.path.ArcPathBinder;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlass;

public class Compass extends RadialGauge {

	private GaugeMetricsPath secondaryPathManager;
	private GaugeMetricsPath primaryPathManager;
	
	private static int gaugeRadius = 110;
	private static int centerUserX = 0;
	private static int centerUserY = 0;
	
	public Compass() {
		super(centerUserX, centerUserY, gaugeRadius);

		CiseroEnvelop cisero = new CiseroEnvelop();
		setEnvelop(cisero);

		TextureBackground textureBackground = new TextureBackground(TexturePalette.getSquareCarbonFiber());
		addGaugeBackground(textureBackground);
		
		SailCompassBackground compass = new SailCompassBackground(0, 0, 150);
		addGaugeBackground(compass);
		
		// GaugeGlass glass = new GaugeGlass.GlassCubicEffect();
		// GaugeGlass glass = new GaugeGlass.GlassLinearEffect();
		// GaugeGlass glass = new GaugeGlass.GlassRadialEffect();
		// GaugeGlass glass = new GaugeGlass.Donut2DGlass();
		GaugeGlass glass = new GaugeGlass.GlassLabel();
		addEffect(glass);
		
		createPrimaryMetrics();
		createSecondaryMetrics();
	}
	
	
	private void createPrimaryMetrics() {
		primaryPathManager = new GaugeMetricsPath();
		primaryPathManager.setAutoReverseGlyph(false);
		primaryPathManager.setReverseAll(true);
		primaryPathManager.setRange(0,360);
		primaryPathManager.setPathBinder(new ArcPathBinder(gaugeRadius-10, 0, 360));
		registerGaugeMetricsPath(primaryPathManager);
		
		GlyphMetric metric;
		Font f = InputFonts.getFont(InputFonts.ELEMENT, 40);

		//east
		metric = new GlyphMetric();
		metric.setValue(0);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("E");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter()));
		metric.setFont(f);
		primaryPathManager.addMetric(metric);

		//north
		metric = new GlyphMetric();
		metric.setValue(90);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("N");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		primaryPathManager.addMetric(metric);

		//west
		metric = new GlyphMetric();
		metric.setValue(180);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("W");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.ORANGE));
		metric.setFont(f);
		primaryPathManager.addMetric(metric);

		// south
		metric = new GlyphMetric();
		metric.setValue(270);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("S");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
		metric.setFont(f);
		primaryPathManager.addMetric(metric);
	}

	private void createSecondaryMetrics() {

		secondaryPathManager = new GaugeMetricsPath();
		secondaryPathManager.setAutoReverseGlyph(false);
		secondaryPathManager.setReverseAll(true);
		secondaryPathManager.setRange(0,360);
		secondaryPathManager.setPathBinder(new ArcPathBinder(gaugeRadius-50, 0, 360));
		registerGaugeMetricsPath(secondaryPathManager);
		
		GlyphMetric metric;
		Font f = InputFonts.getElements(12);
		metric = new GlyphMetric();
		metric.setValue(30);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("30");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter()));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(60);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("60");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(120);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("120");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(150);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("150");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.ORANGE.brighter()));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(210);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("210");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.ORANGE.brighter()));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(240);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("240");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(300);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("300");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(330);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("330");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter()));
		metric.setFont(f);
		secondaryPathManager.addMetric(metric);
	}
	

}
