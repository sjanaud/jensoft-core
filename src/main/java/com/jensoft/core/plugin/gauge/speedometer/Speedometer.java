/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.speedometer;

import java.awt.Color;
import java.awt.Font;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.TexturePalette;
import com.jensoft.core.palette.color.NanoChromatique;
import com.jensoft.core.plugin.gauge.core.GaugeBackground;
import com.jensoft.core.plugin.gauge.core.GaugeBody;
import com.jensoft.core.plugin.gauge.core.GaugeEnvelope;
import com.jensoft.core.plugin.gauge.core.GaugeGlass;
import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.binder.AnchorBinder;
import com.jensoft.core.plugin.gauge.core.binder.anchor.AnchorBaseBinder;
import com.jensoft.core.plugin.gauge.core.binder.anchor.AnchorValueBinder;
import com.jensoft.core.plugin.gauge.core.binder.path.PathArcManualBinder;
import com.jensoft.core.plugin.gauge.core.needle.GaugeNeedleClassicPainter;

/**
 * <code>Speedometer</code> base model helps developer to learn gauge modeling.
 * 
 * @since1.0
 * @author sebastien janaud
 * 
 */
public class Speedometer extends RadialGauge {

	private static int gaugeRadius = 90;
	private static int centerUserX = 0;
	private static int centerUserY = 0;

	/** speedo meter gauge metrics */
	private GaugeMetricsPath metricsManager;

	public Speedometer() {
		super(centerUserX, centerUserY, gaugeRadius);

		GaugeEnvelope cisero = new GaugeEnvelope.Cisero();
		setEnvelop(cisero);

		GaugeBackground bg = new GaugeBackground.Circular.Texture(TexturePalette.getSquareCarbonFiber());
		addBackground(bg);

		GaugeGlass g3 = new GaugeGlass.GlassIncubator();
		GaugeGlass g5 = new GaugeGlass.Donut2DGlass();
		GaugeGlass g6 = new GaugeGlass.JenSoftAPILabel();

		addGlass(g5);

		createBody();
	}

	/**
	 * create speedometer body
	 */
	public void createBody() {
		
		GaugeBody body = new GaugeBody();
		addBody(body);
		
		metricsManager = new GaugeMetricsPath();
		metricsManager.setMin(0);
		metricsManager.setMax(280);
		metricsManager.setCurrentValue(186);

		metricsManager.setGaugeNeedlePainter(new GaugeNeedleClassicPainter());
		metricsManager.setPathBinder(new PathArcManualBinder(gaugeRadius - 10, 260, -340));


		AnchorBinder baseNeedleBinder = new AnchorBaseBinder();
		AnchorValueBinder valueNeedleBinder = new AnchorValueBinder(20, Side.SideRight);
		metricsManager.setNeedleBaseAnchorBinder(baseNeedleBinder);
		metricsManager.setNeedleValueAnchorBinder(valueNeedleBinder);


		body.registerGaugeMetricsPath(metricsManager);

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
		Font f16 = new Font("Dialog", Font.PLAIN, 16);
		metric.setFont(f16);
		metricsManager.addMetric(metric);

	}

}
