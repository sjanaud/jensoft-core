/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.speedometer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.palette.TexturePalette;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.BodyGaugePainter;
import com.jensoft.core.plugin.gauge.core.GaugePartBuffer;

/**
 * <code>SpeedometerBody</code>
 * 
 * @author sebastien janaud
 * 
 */
public class SpeedometerBody extends BodyGaugePainter {

	/**gauge part buffer to paint reuse*/
	private GaugePartBuffer metricsPart;
	
	/**metrics manager to manage value and metrics*/
	private GeneralMetricsPath metricsManager;
	
	/**delegate needle*/
	private SpeedometerNeedle needle;

	/**
	 * create speedometer body
	 */
	public SpeedometerBody() {
		metricsManager = new GeneralMetricsPath();
		metricsManager.setProjectionNature(ProjectionNature.DEVICE);

		metricsManager.setMin(0);
		metricsManager.setMax(280);

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

		needle = new SpeedometerNeedle();
		needle.setPathManager(metricsManager);
		needle.setCurentValue(180);
	}

	
	/**
	 * paint metrics
	 * @param g2d
	 */
	private void paintMetrics(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();

		int radius = getGauge().getRadius() - 10;

		int startAngleDegreee = 260;
		int extendsAngleDegree = -340;

		Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, extendsAngleDegree, Arc2D.OPEN);

		// DEBUG ARC
		// g2d.setColor(Color.RED);
		// g2d.draw(arc2d);

		metricsManager.setWindow2d(getGauge().getWindow2D());
		metricsManager.resetPath();
		metricsManager.append(arc2d);

		radius = getGauge().getRadius();
		if (metricsPart == null) {

			metricsPart = new GaugePartBuffer(getGauge());

			Graphics2D g2dPart = metricsPart.getGraphics();
			g2dPart.setRenderingHints(g2d.getRenderingHints());
			metricsManager.setFontRenderContext(g2d.getFontRenderContext());

			List<GlyphMetric> metrics = metricsManager.getMetrics();
			for (GlyphMetric m : metrics) {

				if (m.getGlyphMetricMarkerPainter() != null) {
					m.getGlyphMetricMarkerPainter().paintGlyphMetric(g2dPart, m);
				}

				if (m.getGlyphMetricFill() != null) {
					m.getGlyphMetricFill().paintGlyphMetric(g2dPart, m);
				}
				if (m.getGlyphMetricDraw() != null) {
					m.getGlyphMetricDraw().paintGlyphMetric(g2dPart, m);
				}
				if (m.getGlyphMetricEffect() != null) {
					m.getGlyphMetricEffect().paintGlyphMetric(g2dPart, m);
				}
			}
		}
		g2d.drawImage(metricsPart.getBuffer(), (int) centerX - radius, (int) centerY - radius, 2 * radius, 2 * radius, null);
	}

	/**
	 * paint textured body background
	 * 
	 * @param g2d
	 */
	private void paintBase(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
		int radius = getGauge().getRadius();

		Ellipse2D baseShape = new Ellipse2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

		g2d.setPaint(TexturePalette.getT3());
		g2d.fill(baseShape);

	}
	
	/**
	 * paint delegate needle
	 * @param g2d
	 */
	private void paintNeedle(Graphics2D g2d) {
		needle.setGauge(getGauge());
		needle.paintGauge(g2d, getGauge());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.BodyGaugePainter#paintBody(java.awt
	 * .Graphics2D, com.jensoft.core.plugin.gauge.RadialGauge)
	 */
	@Override
	public void paintBody(Graphics2D g2d, RadialGauge radialGauge) {
		paintBase(g2d);
		paintMetrics(g2d);
		paintNeedle(g2d);
	}

	

}
