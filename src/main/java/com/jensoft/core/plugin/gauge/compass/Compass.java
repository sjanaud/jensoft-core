/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.compass;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Arc2D;

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
import com.jensoft.core.plugin.gauge.core.binder.PathBinder;
import com.jensoft.core.plugin.gauge.core.binder.path.ArcPathBinder;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlass;

public class Compass extends RadialGauge {

	private GaugeMetricsPath pathManagerLabel;
	private GaugeMetricsPath pathManager;
	private GaugeMetricsPath pathManagerNeedle;
	
	private static int gaugeRadius = 110;
	private static int centerUserX = 0;
	private static int centerUserY = 0;
	
	public Compass() {
		super(centerUserX, centerUserY, gaugeRadius);

		CiseroEnvelop e1 = new CiseroEnvelop();
		setEnvelop(e1);

		TextureBackground textureBackground = new TextureBackground(TexturePalette.getSquareCarbonFiber());
		addGaugeBackground(textureBackground);
		
		createCompass();
		// GaugeGlass glass = new GaugeGlass.GlassCubicEffect();
		// GaugeGlass glass = new GaugeGlass.GlassLinearEffect();
		// GaugeGlass glass = new GaugeGlass.GlassRadialEffect();
		// GaugeGlass glass = new GaugeGlass.Donut2DGlass();
		GaugeGlass glass = new GaugeGlass.GlassLabel();

		addEffect(glass);
		
		pathManager = new GaugeMetricsPath();
		pathManager.setAutoReverseGlyph(false);
		pathManager.setReverseAll(true);
		pathManager.setRange(0,360);
		pathManager.setPathBinder(new ArcPathBinder(gaugeRadius-10, 0, 360));
//		pathManager.setPathBinder(new PathBinder() {
//			@Override
//			public Shape bindPath(RadialGauge gauge) {
//				double centerX = getCenterDevice().getX();
//				double centerY = getCenterDevice().getY();
//				int startAngleDegree = 0;
//				int extendsDegree = 360;
//				int radius1 = getRadius() - 10;
//				Arc2D arc = new Arc2D.Double(centerX - radius1, centerY - radius1, 2 * radius1, 2 * radius1, startAngleDegree, extendsDegree, Arc2D.OPEN);
//				return arc;
//			}
//		});
		//pathManager.setDebugPath(true);
		registerGaugeMetricsPath(pathManager);
		

		pathManagerLabel = new GaugeMetricsPath();
		pathManagerLabel.setAutoReverseGlyph(false);
		pathManagerLabel.setReverseAll(true);
		pathManagerLabel.setRange(0,360);
		pathManagerLabel.setPathBinder(new ArcPathBinder(gaugeRadius-50, 0, 360));
//		pathManagerLabel.setPathBinder(new PathBinder() {
//			@Override
//			public Shape bindPath(RadialGauge gauge) {
//				double centerX = getCenterDevice().getX();
//				double centerY = getCenterDevice().getY();
//				int startAngleDegreee = 0;
//				int extendsDegree = 360;
//				int radius2 = getRadius() - 50;
//				Arc2D arc = new Arc2D.Double(centerX - radius2, centerY - radius2, 2 * radius2, 2 * radius2, startAngleDegreee, extendsDegree, Arc2D.OPEN);
//				return arc;
//			}
//		});
		registerGaugeMetricsPath(pathManagerLabel);
		

		pathManagerNeedle = new GaugeMetricsPath();
		pathManagerNeedle.setAutoReverseGlyph(false);
		pathManagerNeedle.setReverseAll(true);
		pathManagerNeedle.setRange(0,360);
		pathManagerNeedle.setPathBinder(new ArcPathBinder(gaugeRadius-80, 0, 360));
//		pathManagerNeedle.setPathBinder(new PathBinder() {
//			@Override
//			public Shape bindPath(RadialGauge gauge) {
//				double centerX = getCenterDevice().getX();
//				double centerY = getCenterDevice().getY();
//				int startAngleDegreee = 0;
//				int extendsDegree = 360;
//				int radius2 = getRadius() - 80;
//				Arc2D arc = new Arc2D.Double(centerX - radius2, centerY - radius2, 2 * radius2, 2 * radius2, startAngleDegreee,extendsDegree, Arc2D.OPEN);
//				return arc;
//			}
//		});
		registerGaugeMetricsPath(pathManagerNeedle);
		
		
		createLabel1();
		createLabel2();

	}
	
	public void createCompass() {

		SailCompassTick compass = new SailCompassTick(0, 0, 150);
		compass.setPaint(Color.DARK_GRAY);

		for (int i = 0; i <= 360; i += 30) {
			CompassCapTicker needlenorth = new CompassCapTicker(i);
			needlenorth.setNature(CompassCapTicker.MAJOR);
			compass.addNeedle(needlenorth);
		}

		for (int i = 0; i <= 360; i += 10) {
			CompassCapTicker needlenorth = new CompassCapTicker(i);
			needlenorth.setNature(CompassCapTicker.MEDIAN);
			compass.addNeedle(needlenorth);
		}

		for (int i = 0; i <= 360; i += 5) {
			CompassCapTicker needlenorth = new CompassCapTicker(i);
			needlenorth.setNature(CompassCapTicker.MINOR);
			compass.addNeedle(needlenorth);
		}
		for (double i = 0; i <= 360; i += 2.5) {
			CompassCapTicker needlenorth = new CompassCapTicker(i);
			needlenorth.setNature(CompassCapTicker.MILI);
			compass.addNeedle(needlenorth);
		}
		
		addGaugeBackground(compass);

	}
	
	private void createLabel2() {
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
		pathManager.addMetric(metric);

		//north
		metric = new GlyphMetric();
		metric.setValue(90);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("N");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManager.addMetric(metric);

		//west
		metric = new GlyphMetric();
		metric.setValue(180);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("W");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.ORANGE));
		metric.setFont(f);
		pathManager.addMetric(metric);

		// south
		metric = new GlyphMetric();
		metric.setValue(270);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("S");
		metric.setDivergence(-15);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
		metric.setFont(f);
		pathManager.addMetric(metric);
	}

	private void createLabel1() {

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
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(60);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("60");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(120);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("120");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(150);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("150");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.ORANGE.brighter()));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(210);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("210");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.ORANGE.brighter()));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(240);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("240");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(300);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("300");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(330);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("330");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter()));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);
	}
	

}
