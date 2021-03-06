/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge.tachometer;

import java.awt.Color;
import java.awt.Font;

import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.glyphmetrics.GlyphMetricsNature;
import org.jensoft.core.glyphmetrics.Side;
import org.jensoft.core.glyphmetrics.StylePosition;
import org.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import org.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import org.jensoft.core.palette.InputFonts;
import org.jensoft.core.palette.color.TangoPalette;
import org.jensoft.core.plugin.gauge.core.GaugeBackground;
import org.jensoft.core.plugin.gauge.core.GaugeBody;
import org.jensoft.core.plugin.gauge.core.GaugeEnvelope;
import org.jensoft.core.plugin.gauge.core.GaugeGlass;
import org.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import org.jensoft.core.plugin.gauge.core.RadialGauge;
import org.jensoft.core.plugin.gauge.core.binder.AnchorBinder;
import org.jensoft.core.plugin.gauge.core.binder.anchor.AnchorBaseBinder;
import org.jensoft.core.plugin.gauge.core.binder.anchor.AnchorValueBinder;
import org.jensoft.core.plugin.gauge.core.binder.path.PathArcManualBinder;
import org.jensoft.core.plugin.gauge.core.needle.GaugeNeedleClassicPainter;

/**
 * <code>Tachometer</code> base model helps developer to learn gauge modeling.
 * 
 * @since1.0
 * @author sebastien janaud
 * 
 */
public class Tachometer extends RadialGauge {

	/** tacho meter gauge metrics */
	private GaugeMetricsPath metricsManager;

	/**gauge radius*/
	private static int gaugeRadius = 90;
	
	/**gauge center x in user system coodinate*/
	private static int centerUserX = 0;
	
	/**gauge center y in user system coordinate*/
	private static int centerUserY = 0;

	public Tachometer() {
		super(centerUserX, centerUserY, gaugeRadius);

		GaugeEnvelope cisero = new GaugeEnvelope.Cisero();
		setEnvelop(cisero);

		GaugeBackground bg = new GaugeBackground.Circular.RadialGradient();
		addBackground(bg);

		GaugeGlass g3 = new GaugeGlass.GlassIncubator();
		GaugeGlass g5 = new GaugeGlass.Donut2DGlass();
		GaugeGlass g6 = new GaugeGlass.JenSoftAPILabel();

		addGlass(g5);

		createBody();
	}

	public void createBody() {
		
		GaugeBody body = new GaugeBody();
		addBody(body);
		
		metricsManager = new GaugeMetricsPath();
		metricsManager.setMin(0);
		metricsManager.setMax(8);
		metricsManager.setCurrentValue(5.7);

		metricsManager.setGaugeNeedlePainter(new GaugeNeedleClassicPainter());
		metricsManager.setPathBinder(new PathArcManualBinder(gaugeRadius - 10, 210, -240));


		AnchorBinder baseNeedleBinder = new AnchorBaseBinder();
		AnchorValueBinder valueNeedleBinder = new AnchorValueBinder(40, Side.SideRight);
		metricsManager.setNeedleBaseAnchorBinder(baseNeedleBinder);
		metricsManager.setNeedleValueAnchorBinder(valueNeedleBinder);


		body.registerGaugeMetricsPath(metricsManager);

		Font f = InputFonts.getFont(InputFonts.ELEMENT, 24);
		GlyphMetric metric = new GlyphMetric();
		metric.setValue(0);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("0");
		metric.setDivergence(20);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(f);
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
		metric.setFont(f);
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
		metric.setFont(f);
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
		metric.setFont(f);
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
		metric.setFont(f);
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
		metric.setFont(f);
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
		metric.setFont(f);
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
		metric.setFont(f);
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
		metric.setFont(f);
		metricsManager.addMetric(metric);

	}
}
