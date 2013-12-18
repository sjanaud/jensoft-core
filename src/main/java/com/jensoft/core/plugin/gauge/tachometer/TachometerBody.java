/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.tachometer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.device.PartBuffer;
import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.core.painter.BodyGaugePainter;
import com.jensoft.core.gauge.core.painter.NeedleGaugePainter;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.palette.TexturePalette;

public class TachometerBody extends BodyGaugePainter {

	public TachometerBody() {
		metricsManager = new GeneralMetricsPath();
		metricsManager.setProjectionNature(ProjectionNature.DEVICE);

		metricsManager.setMin(0);
		metricsManager.setMax(280);
		
		GlyphFill gf = new GlyphFill(Color.WHITE, NanoChromatique.RED.brighter());
		TicTacMarker ttm = new TicTacMarker(NanoChromatique.GREEN);
		ttm.setSize(3);
		ttm.setDivergence(3);
		Font f= InputFonts.getFont(InputFonts.ELEMENT, 14);
		
		for(int i = 20;i < 250;i=i+20){
			GlyphMetric metric = new GlyphMetric();
			metric.setValue(i);
			metric.setStylePosition(StylePosition.Default);
			//metric.setMetricsNature(GlyphMetricsNature.Major);
			metric.setMetricsLabel(i+"");
	
			metric.setDivergence(16);
			metric.setGlyphMetricFill(gf);
			metric.setGlyphMetricMarkerPainter(ttm);
			metric.setFont(f);
			
			metricsManager.addMetric(metric);
		}
		
		
		//add legend
		GlyphMetric metric = new GlyphMetric();
		metric.setValue(280);
		metric.setStylePosition(StylePosition.Default);
		//metric.setMetricsNature(GlyphMetricsNature.Major);
		metric.setMetricsLabel("Km/h");

		metric.setDivergence(30);
		GlyphFill legendFill = new GlyphFill(Color.WHITE, NanoChromatique.RED);
		metric.setGlyphMetricFill(legendFill);
		metric.setGlyphMetricMarkerPainter(null);
		metric.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 16));
		
		metricsManager.addMetric(metric);



		needle = new TachometerNeedle();
		needle.setPathManager(getMetricsManager());

		needle.setCurentValue(180);
	}

	private Arc2D arc2d;
	private int startAngleDegreee = 0;
	private int extendsAngleDegree = 220;
	private PartBuffer metricsPart;
	private GeneralMetricsPath metricsManager;
	private NeedleGaugePainter needle;

	public GeneralMetricsPath getMetricsManager() {
		return metricsManager;
	}

	private void paintMetrics(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
		
		int radius = getGauge().getRadius() - 10;

		// startAngleDegreee = 210;
		// endAngleDegree = -30;

		// startAngleDegreee = 260;
		// endAngleDegree = 0;

		startAngleDegreee = 260;
		extendsAngleDegree = -340;

		GeneralMetricsPath pathManager = getMetricsManager();
		pathManager.setWindow2d(getGauge().getWindow2D());

		pathManager.resetPath();

		arc2d = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, extendsAngleDegree, Arc2D.OPEN);

		g2d.setColor(Color.RED);
		//g2d.draw(arc2d);

		pathManager.append(arc2d);

		radius = getGauge().getRadius();
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
