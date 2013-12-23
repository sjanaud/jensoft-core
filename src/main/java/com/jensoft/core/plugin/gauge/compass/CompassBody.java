/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.compass;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TriangleMarker;
import com.jensoft.core.glyphmetrics.painter.marker.TriangleMarker.TriangleDirection;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.BodyGaugePainter;
import com.jensoft.core.plugin.gauge.core.GaugePartBuffer;

/**
 * <code>CompassBody</code>
 * 
 * @author sebastien janaud
 * 
 */
public class CompassBody extends BodyGaugePainter {

	private GeneralMetricsPath pathManagerLabel;
	private GeneralMetricsPath pathManager;
	private GeneralMetricsPath pathManagerNeedle;

	private double capDegree = 90;
	
	private GaugePartBuffer metricsPart;
	private SailCompassTick compass;

	/**
	 * create compass body
	 */
	public CompassBody() {

		pathManager = new GeneralMetricsPath();
		pathManager.setProjectionNature(ProjectionNature.DEVICE);
		pathManager.setAutoReverseGlyph(false);
		pathManager.setReverseAll(true);
		pathManager.setMin(0);
		pathManager.setMax(360);

		pathManagerLabel = new GeneralMetricsPath();
		pathManagerLabel.setProjectionNature(ProjectionNature.DEVICE);
		pathManagerLabel.setAutoReverseGlyph(false);
		pathManagerLabel.setReverseAll(true);
		pathManagerLabel.setMin(0);
		pathManagerLabel.setMax(360);

		pathManagerNeedle = new GeneralMetricsPath();
		pathManagerNeedle.setProjectionNature(ProjectionNature.DEVICE);
		pathManagerNeedle.setAutoReverseGlyph(false);
		pathManagerNeedle.setReverseAll(true);
		pathManagerNeedle.setMin(0);
		pathManagerNeedle.setMax(360);

		createLabel1();
		createLabel2();
		createCompasse();
	}

	public double getCapDegree() {
		return capDegree;
	}

	public void setCapDegree(double capDegree) {
		this.capDegree = capDegree;
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
		Font f = InputFonts.getFont(InputFonts.ELEMENT, 12);

		metric = new GlyphMetric();
		metric.setValue(30);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("3O");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(60);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("6O");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(120);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("12O");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(150);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("15O");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(210);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("210");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(240);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("240");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(300);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("300");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(330);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsNature(GlyphMetricsNature.Median);
		metric.setMetricsLabel("330");
		metric.setDivergence(0);
		metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.BLUE));
		metric.setFont(f);
		pathManagerLabel.addMetric(metric);
	}

	public void createCompasse() {

		compass = new SailCompassTick(0, 0, 150);
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

	}

	
	/**
	 * paint compass ticker
	 * @param g2d
	 */
	protected void paintCompass(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
		int radius = getGauge().getRadius() - 10;

		compass.setCenterX((int) centerX);
		compass.setCenterY((int) centerY);
		compass.setBaseRadius(radius - 30);
		compass.builCompass();
		

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		List<CompassCapTicker> needles = compass.getNeedles();

		g2d.setColor(getGauge().getWindow2D().getThemeColor().darker());
		g2d.setStroke(new BasicStroke(1f));
		Color blue = new Color(68, 155, 180);
		

		for (CompassCapTicker n : needles) {

			Line2D needlePath = n.getNeedlePath();

			if (n.getNature() == CompassCapTicker.MAJOR) {
				g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
				g2d.setColor(blue);
			}
			if (n.getNature() == CompassCapTicker.MEDIAN) {
				g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
				g2d.setColor(ColorPalette.brighter(TangoPalette.CHAMELEON1, 0.8f));
			}
			if (n.getNature() == CompassCapTicker.MINOR) {
				g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
				g2d.setColor(TangoPalette.CHAMELEON3);
			}
			if (n.getNature() == CompassCapTicker.MILI) {
				g2d.setColor(TangoPalette.CHAMELEON3);
				g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
			}

			g2d.draw(needlePath);
		}

	}

	
	/**
	 * paint metrics
	 * @param g2d
	 */
	private void paintMetrics(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();

		int startAngleDegreee = 0;
		int extendsDegree = 360;
		
		int radius1 = getGauge().getRadius() - 10;
		Arc2D arc2d = new Arc2D.Double(centerX - radius1, centerY - radius1, 2 * radius1, 2 * radius1, startAngleDegreee, extendsDegree, Arc2D.OPEN);

		int radius2 = getGauge().getRadius() - 50;
		Arc2D arc2d2 = new Arc2D.Double(centerX - radius2, centerY - radius2, 2 * radius2, 2 * radius2, startAngleDegreee, extendsDegree, Arc2D.OPEN);

		int radius = getGauge().getRadius();
		pathManager.setWindow2d(getGauge().getWindow2D());
		pathManager.resetPath();
		pathManager.setFontRenderContext(g2d.getFontRenderContext());
		pathManager.append(arc2d);
		pathManager.setSolveGeometryRequest(true);

		pathManagerLabel.setWindow2d(getGauge().getWindow2D());
		pathManagerLabel.resetPath();
		pathManagerLabel.setFontRenderContext(g2d.getFontRenderContext());
		pathManagerLabel.append(arc2d2);
		pathManagerLabel.setSolveGeometryRequest(true);

		g2d.setColor(Color.RED);
		// g2d.draw(arc2d2);

		if (metricsPart == null) {

			
			metricsPart = new GaugePartBuffer(getGauge());
			Graphics2D g2dPart = metricsPart.getGraphics();
			g2dPart.setRenderingHints(g2d.getRenderingHints());
			g2dPart.setStroke(new BasicStroke(0.4f));
			g2dPart.setColor(Color.BLACK);

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

			List<GlyphMetric> metrics2 = pathManagerLabel.getMetrics();
			for (GlyphMetric m : metrics2) {

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
	
	public void paintNeedle(Graphics2D g2d) {
		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();

		
		int startAngleDegreee = 0;
		int extendsDegree = 360;
		
		int radius2 = getGauge().getRadius() - 80;
		Arc2D arc2d2 = new Arc2D.Double(centerX - radius2, centerY - radius2, 2 * radius2, 2 * radius2, startAngleDegreee,extendsDegree, Arc2D.OPEN);

		pathManagerNeedle.clearMetric();
		pathManagerNeedle.setWindow2d(getGauge().getWindow2D());
		pathManagerNeedle.resetPath();
		pathManagerNeedle.setFontRenderContext(g2d.getFontRenderContext());
		pathManagerNeedle.append(arc2d2);
		pathManagerNeedle.setSolveGeometryRequest(true);

		GlyphMetric metric = new GlyphMetric();
		metric.setValue(capDegree);

		// triangle marker
		TriangleMarker triangle = new TriangleMarker(Color.WHITE, NanoChromatique.BLUE, 15, 20);
		triangle.setDirection(TriangleDirection.In);
		metric.setGlyphMetricMarkerPainter(triangle);

		pathManagerNeedle.addMetric(metric);

		pathManagerNeedle.setFontRenderContext(g2d.getFontRenderContext());
		pathManagerNeedle.setSolveGeometryRequest(true);
		List<GlyphMetric> metrics2 = pathManagerNeedle.getMetrics();
		for (GlyphMetric m : metrics2) {

			if (m.getGlyphMetricMarkerPainter() != null) {
				m.getGlyphMetricMarkerPainter().paintGlyphMetric(g2d, m);
			}

		}
	}
	

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.gauge.core.BodyGaugePainter#paintBody(java.awt.Graphics2D, com.jensoft.core.plugin.gauge.RadialGauge)
	 */
	@Override
	public void paintBody(Graphics2D g2d, RadialGauge radialGauge) {
		paintCompass(g2d);
		paintMetrics(g2d);
		paintNeedle(g2d);
	}

	

}
