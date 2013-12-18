/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.compass.c1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.device.PartBuffer;
import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.core.painter.BodyGaugePainter;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.TangoPalette;

public class C1Body extends BodyGaugePainter {

	public C1Body() {
		metricsPath = new GeneralMetricsPath();
		metricsPath.setProjectionNature(ProjectionNature.DEVICE);

		metricsPath.setMin(0);
		metricsPath.setMax(360);

		GlyphMetric metric = new GlyphMetric();
		metric.setValue(0);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("0");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.BUTTER1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.BUTTER1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(30);
		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("3O");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(60);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("6O");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(90);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("N");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(120);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("12O");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(150);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("15O");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(150);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("15O");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(180);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("W");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(210);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("210");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(240);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("240");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(270);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("S");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(300);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("300");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(330);

		metric.setStylePosition(StylePosition.Default);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("330");
		metric.setDivergence(-30);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, TangoPalette.CHAMELEON1));
		metric.setGlyphMetricMarkerPainter(new TicTacMarker(TangoPalette.CHAMELEON1));
		// metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
		metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
		metricsPath.addMetric(metric);

		needle = new C1Needle();
		needle.setPathManager(getMetricsPath());

	}

	private C1Needle needle;
	private Arc2D arc2d;
	private int startAngleDegreee = 0;
	private int endAngleDegree = 360;
	private PartBuffer metricsPart;
	private GeneralMetricsPath metricsPath;

	public GeneralMetricsPath getMetricsPath() {
		return metricsPath;
	}

	public void setMetricsPath(GeneralMetricsPath metricsPath) {
		this.metricsPath = metricsPath;
	}

	private void paintMetrics(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();

		int radius = getGauge().getRadius() - 10;

		GeneralMetricsPath pathManager = getMetricsPath();
		pathManager.setWindow2d(getGauge().getWindow2D());
		pathManager.resetPath();
		pathManager.setFontRenderContext(g2d.getFontRenderContext());
		arc2d = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, endAngleDegree - startAngleDegreee, Arc2D.OPEN);

		radius = getGauge().getRadius();
		pathManager.append(arc2d);

		g2d.setColor(Color.RED);
		g2d.drawRect((int) (centerX - radius), (int) (centerY - radius), 2 * radius, 2 * radius);
		if (metricsPart == null) {

			metricsPart = new PartBuffer(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

			Graphics2D g2dPart = metricsPart.getBuffer().createGraphics();
			g2dPart.setRenderingHints(g2d.getRenderingHints());
			g2dPart.translate(-centerX + radius, -centerY + radius);
			g2dPart.setStroke(new BasicStroke(0.4f));
			g2dPart.setColor(Color.BLACK);

			pathManager.setFontRenderContext(g2dPart.getFontRenderContext());

			List<GlyphMetric> metrics = pathManager.getMetrics();
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

	@Override
	public void paintBody(Graphics2D g2d, RadialGauge radialGauge) {

		paintMetrics(g2d);

		paintNeedle(g2d);
	}

	private void paintNeedle(Graphics2D g2d) {
		needle.setGauge(getGauge());
		needle.paintGauge(g2d, getGauge());
	}

}
