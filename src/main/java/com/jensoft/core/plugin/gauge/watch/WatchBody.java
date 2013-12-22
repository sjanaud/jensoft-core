/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.watch;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.device.PartBuffer;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
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
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.BodyGaugePainter;

public class WatchBody extends BodyGaugePainter {

	private int startAngleDegreee = 90;
	private int extendsAngleDegree = 220;
	private PartBuffer metricsPart;

	private GeneralMetricsPath hourMetricsManager;
	private GeneralMetricsPath minuteMetricsManager;
	private GeneralMetricsPath secondMetricsManager;

	private GeneralMetricsPath metricsManager;
	private WatchNeedle needle;

	public WatchBody() {

		hourMetricsManager = new GeneralMetricsPath();
		hourMetricsManager.setProjectionNature(ProjectionNature.DEVICE);
		hourMetricsManager.setRange(0, 12);

		minuteMetricsManager = new GeneralMetricsPath();
		minuteMetricsManager.setProjectionNature(ProjectionNature.DEVICE);
		minuteMetricsManager.setRange(0, 60);

		secondMetricsManager = new GeneralMetricsPath();
		secondMetricsManager.setProjectionNature(ProjectionNature.DEVICE);
		secondMetricsManager.setRange(0, 60);

		metricsManager = new GeneralMetricsPath();
		metricsManager.setProjectionNature(ProjectionNature.DEVICE);
		metricsManager.setMin(0);
		metricsManager.setMax(12);

		createMainTicks();
		createMainMetrics();

		// add legend
		GlyphMetric metric = new GlyphMetric();
		metric.setValue(6);
		metric.setStylePosition(StylePosition.Default);
		// metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("JenSoft API");

		metric.setDivergence(50);
		GlyphFill legendFill = new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter());
		metric.setGlyphMetricFill(legendFill);
		metric.setGlyphMetricMarkerPainter(null);
		metric.setFont(InputFonts.getFont(InputFonts.SANSATION_REGULAR, 10));

		metricsManager.addMetric(metric);

		needle = new WatchNeedle();
		needle.setPathManager(getMetricsManager());
		needle.setHourMetricsManager(hourMetricsManager);
		needle.setMinuteMetricsManager(minuteMetricsManager);
		needle.setSecondMetricsManager(secondMetricsManager);

		needle.setCurentValue(10);
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

		// triangle marker
		TriangleMarker triangle = new TriangleMarker(Color.WHITE, NanoChromatique.GREEN);
		triangle.setDirection(TriangleDirection.Out);
		triangle.setGlobalRadialShift(-20);
		metric.setGlyphMetricMarkerPainter(triangle);
		metric.setFont(f);
		metricsManager.addMetric(metric);

		// rectangle
		metric = new GlyphMetric();
		metric.setValue(8.5);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsLabel("3");
		metric.setDivergence(5);
		// metric.setGlyphMetricFill(gf);

		RectangleMarker rectangle = new RectangleMarker(Color.WHITE, NanoChromatique.GREEN, 3, 8);
		metric.setGlyphMetricMarkerPainter(rectangle);
		metric.setFont(f);
		metricsManager.addMetric(metric);

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

	public GeneralMetricsPath getMetricsManager() {
		return metricsManager;
	}

	public GeneralMetricsPath getHourMetricsManager() {
		return hourMetricsManager;
	}

	public GeneralMetricsPath getMinuteMetricsManager() {
		return minuteMetricsManager;
	}

	public GeneralMetricsPath getSecondMetricsManager() {
		return secondMetricsManager;
	}

	private void paintMetrics(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();

		int radius = getGauge().getRadius() - 10;

		startAngleDegreee = 90;
		extendsAngleDegree = -360;

		Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, extendsAngleDegree, Arc2D.OPEN);
		Arc2D arc2d1 = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, extendsAngleDegree, Arc2D.OPEN);
		Arc2D arc2d2 = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, extendsAngleDegree, Arc2D.OPEN);
		Arc2D arc2d3 = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, extendsAngleDegree, Arc2D.OPEN);

		metricsManager.setWindow2d(getGauge().getWindow2D());
		metricsManager.resetPath();
		metricsManager.append(arc2d);

		hourMetricsManager.setWindow2d(getGauge().getWindow2D());
		hourMetricsManager.resetPath();
		hourMetricsManager.append(arc2d1);

		minuteMetricsManager.setWindow2d(getGauge().getWindow2D());
		minuteMetricsManager.resetPath();
		minuteMetricsManager.append(arc2d2);

		secondMetricsManager.setWindow2d(getGauge().getWindow2D());
		secondMetricsManager.resetPath();
		secondMetricsManager.append(arc2d3);

		radius = getGauge().getRadius();
		if (metricsPart == null) {

			metricsPart = new PartBuffer(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

			Graphics2D g2dPart = metricsPart.getBuffer().createGraphics();
			g2dPart.setRenderingHints(g2d.getRenderingHints());
			g2dPart.translate(-centerX + radius, -centerY + radius);

			// g2dPart.setStroke(new BasicStroke(0.4f));
			// g2dPart.setColor(Color.BLACK);

			//
			minuteMetricsManager.setFontRenderContext(g2dPart.getFontRenderContext());
			List<GlyphMetric> metrics2 = minuteMetricsManager.getMetrics();
			for (GlyphMetric m : metrics2) {

				if (m.getGlyphMetricMarkerPainter() != null) {
					m.getGlyphMetricMarkerPainter().paintGlyphMetric(g2dPart, m);
				}

			}

			metricsManager.setFontRenderContext(g2dPart.getFontRenderContext());

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

			g2d.drawImage(metricsPart.getBuffer(), (int) centerX - radius, (int) centerY - radius, 2 * radius, 2 * radius, null);

		} else {

			g2d.drawImage(metricsPart.getBuffer(), (int) centerX - radius, (int) centerY - radius, 2 * radius, 2 * radius, null);
		}

	}

	private void paintBase(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
		int radius = getGauge().getRadius();

		Ellipse2D baseShape = new Ellipse2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

		g2d.setPaint(TexturePalette.getT3());
		g2d.fill(baseShape);

	}

	@Override
	public void paintBody(Graphics2D g2d, RadialGauge radialGauge) {
		paintBase(g2d);
		paintMetrics(g2d);
		paintNeedle(g2d);
	}

	private void paintNeedle(Graphics2D g2d) {
		needle.setGauge(getGauge());
		needle.paintGauge(g2d, getGauge());
	}

}
