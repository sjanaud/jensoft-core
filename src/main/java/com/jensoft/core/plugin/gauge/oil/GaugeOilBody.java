/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.oil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.drawable.text.TextPath;
import com.jensoft.core.drawable.text.TextPath.PathSide;
import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.DefaultMarker;
import com.jensoft.core.glyphmetrics.painter.marker.TriangleMarker;
import com.jensoft.core.glyphmetrics.painter.marker.TriangleMarker.TriangleDirection;
import com.jensoft.core.palette.Alpha;
import com.jensoft.core.palette.ColorPalette;
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
public class GaugeOilBody extends BodyGaugePainter {

	/** gauge part buffer to paint reuse */
	private GaugePartBuffer metricsPart1;

	/** gauge part buffer to paint reuse */
	private GaugePartBuffer metricsPart2;

	/** metrics manager to manage value and metrics */
	private GeneralMetricsPath metricsPath1;

	/** metrics manager to manage value and metrics */
	private GeneralMetricsPath metricsPath2;

	private TextPath legendTop;
	
	private TextPath legendBottom;
	
	private TextPath legend1;

	private TextPath legend2;

	/**
	 * create speedometer body
	 */
	public GaugeOilBody() {
		createStyle();
		createPath1();
		createPath2();
		
		legendTop = new TextPath();
		float[] fractions = new float[] { 0f, 1f };
		Color[] colors = new Color[] { NanoChromatique.WHITE, NanoChromatique.PINK };
		legendTop.setLabelFont(InputFonts.getNeuropol(12));
		legendTop.setTextPosition(TextPosition.Middle);
		legendTop.setPathSide(PathSide.Below);
		legendTop.setDivergence(0);
		legendTop.setLockReverse(true);
		legendTop.setOffsetLeft(0);
		legendTop.setOffsetRight(0);
		legendTop.setShader(fractions, colors);
		legendTop.setLabel("*** jensoft avionics ***");
		
		legendBottom = new TextPath();
		float[] fractions2 = new float[] { 0f, 1f };
		Color[] colors2 = new Color[] { NanoChromatique.WHITE, NanoChromatique.PURPLE };
		legendBottom.setLabelFont(InputFonts.getNeuropol(20));
		legendBottom.setDivergence(0);
		legendBottom.setPathSide(PathSide.Over);
		legendBottom.setTextPosition(TextPosition.Middle);
		legendBottom.setOffsetLeft(0);
		legendBottom.setOffsetRight(0);
		legendBottom.setShader(fractions2, colors2);
		legendBottom.setLabel("OIL");
	}
	
	GlyphFill gfblue;
	GlyphFill gfred;
	GlyphFill gforange;
	GlyphFill gfyellow;
	
	TriangleMarker rmblue;
	TriangleMarker rmred;
	TriangleMarker rmyellow ;
	TriangleMarker rmorange;
	
	TriangleMarker rmblue2;
	TriangleMarker rmred2;
	TriangleMarker rmyellow2 ;
	TriangleMarker rmorange2;
	
	Font f12;
	Font f10;
	private void createStyle(){
		
		gfblue = new GlyphFill(Color.WHITE, NanoChromatique.BLUE.brighter());
		gfred = new GlyphFill(Color.WHITE, NanoChromatique.RED.brighter());
		gforange = new GlyphFill(Color.WHITE, NanoChromatique.ORANGE.brighter());
		gfyellow = new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter());
		// TicTacMarker ttm = new TicTacMarker(NanoChromatique.GREEN);
		DefaultMarker dmred = new DefaultMarker(NanoChromatique.RED.brighter());
		DefaultMarker dmorange = new DefaultMarker(NanoChromatique.ORANGE.brighter());
		DefaultMarker dmyellow = new DefaultMarker(NanoChromatique.YELLOW.brighter());
		DefaultMarker dmblue = new DefaultMarker(NanoChromatique.BLUE.brighter());
		
		
		rmblue = new TriangleMarker(Color.WHITE, NanoChromatique.BLUE);
		rmblue.setGlobalRadialShift(5);
		rmblue.setDivergenceRadial(6);
		rmblue.setDirection(TriangleDirection.Out);
		
		rmblue2 = new TriangleMarker(Color.WHITE, NanoChromatique.BLUE);
		rmblue2.setGlobalRadialShift(-5);
		rmblue2.setDivergenceRadial(6);
		rmblue2.setDirection(TriangleDirection.In);
		
		rmred = new TriangleMarker(Color.WHITE, NanoChromatique.RED);
		rmred.setGlobalRadialShift(5);
		rmred.setDivergenceRadial(6);
		rmred.setDirection(TriangleDirection.Out);
		
		rmred2 = new TriangleMarker(Color.WHITE, NanoChromatique.RED);
		rmred2.setGlobalRadialShift(-5);
		rmred2.setDivergenceRadial(6);
		rmred2.setDirection(TriangleDirection.In);
		
		rmorange = new TriangleMarker(Color.WHITE, NanoChromatique.ORANGE);
		rmorange.setGlobalRadialShift(5);
		rmorange.setDivergenceRadial(6);
		rmorange.setDirection(TriangleDirection.Out);
		
		rmorange2 = new TriangleMarker(Color.WHITE, NanoChromatique.ORANGE);
		rmorange2.setGlobalRadialShift(-5);
		rmorange2.setDivergenceRadial(6);
		rmorange2.setDirection(TriangleDirection.In);
		
		rmyellow = new TriangleMarker(Color.WHITE, NanoChromatique.YELLOW);
		rmyellow.setGlobalRadialShift(5);
		rmyellow.setDivergenceRadial(6);
		rmyellow.setDirection(TriangleDirection.Out);
		
		rmyellow2 = new TriangleMarker(Color.WHITE, NanoChromatique.YELLOW);
		rmyellow2.setGlobalRadialShift(-5);
		rmyellow2.setDivergenceRadial(6);
		rmyellow2.setDirection(TriangleDirection.In);

		f12 = InputFonts.getFont(InputFonts.ELEMENT, 12);
		f10 = InputFonts.getFont(InputFonts.ELEMENT, 10);
	}

	private void createPath1() {
		metricsPath1 = new GeneralMetricsPath();
		metricsPath1.setProjectionNature(ProjectionNature.DEVICE);

		metricsPath1.setMin(0);
		metricsPath1.setMax(120);

		

		GlyphMetric metric;
		
		metric = new GlyphMetric();
		metric.setValue(20);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(20 + "");
		metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gfblue);
		metric.setGlyphMetricMarkerPainter(rmblue);
		metric.setFont(f10);
		metricsPath1.addMetric(metric);
		
		metric = new GlyphMetric();
		metric.setValue(40);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(40 + "");
		metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gfyellow);
		metric.setGlyphMetricMarkerPainter(rmyellow);
		metric.setFont(f10);
		metricsPath1.addMetric(metric);
		
		metric = new GlyphMetric();
		metric.setValue(80);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(80 + "");
		metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gforange);
		metric.setGlyphMetricMarkerPainter(rmorange);
		metric.setFont(f10);
		metricsPath1.addMetric(metric);


		metric = new GlyphMetric();
		metric.setValue(100);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(100 + "");
		metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gfred);
		metric.setGlyphMetricMarkerPainter(rmred);
		metric.setFont(f10);
		metricsPath1.addMetric(metric);

		// add legend
//		GlyphMetric legend = new GlyphMetric();
//		legend.setValue(150);
//		legend.setStylePosition(StylePosition.Tangent);
//		legend.setMetricsLabel("PSI");
//		legend.setDivergence(0);
//		legend.setGlyphMetricFill(new GlyphFill(Color.WHITE, NanoChromatique.RED));
//		legend.setFont(InputFonts.getFont(InputFonts.NEUROPOL, 14));
//		metricsPath1.addMetric(legend);

		legend1 = new TextPath();
		float[] fractions = new float[] { 0f, 1f };
		Color[] colors = new Color[] { NanoChromatique.WHITE, NanoChromatique.GREEN };
		legend1.setLabelFont(InputFonts.getElements(12));
		legend1.setDivergence(0);
		legend1.setOffsetLeft(0);
		legend1.setOffsetRight(0);
		legend1.setShader(fractions, colors);
		legend1.setLabel("PSI");

	}

	private void createPath2() {
		metricsPath2 = new GeneralMetricsPath();
		metricsPath2.setProjectionNature(ProjectionNature.DEVICE);

		metricsPath2.setMin(0);
		metricsPath2.setMax(120);

		

		GlyphMetric metric;
		
		metric = new GlyphMetric();
		metric.setValue(20);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(20 + "");
		//metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gfblue);
		metric.setGlyphMetricMarkerPainter(rmblue2);
		metric.setFont(f10);
		metricsPath2.addMetric(metric);
		
		metric = new GlyphMetric();
		metric.setValue(40);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(40 + "");
		//metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gfyellow);
		metric.setGlyphMetricMarkerPainter(rmyellow2);
		metric.setFont(f10);
		metricsPath2.addMetric(metric);
		
		metric = new GlyphMetric();
		metric.setValue(80);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(80 + "");
		//metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gforange);
		metric.setGlyphMetricMarkerPainter(rmorange2);
		metric.setFont(f10);
		metricsPath2.addMetric(metric);


		metric = new GlyphMetric();
		metric.setValue(100);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(100 + "");
		//metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(gfred);
		metric.setGlyphMetricMarkerPainter(rmred2);
		metric.setFont(f10);
		metricsPath2.addMetric(metric);
		
		
		legend2 = new TextPath();
		float[] fractions = new float[] { 0f, 1f };
		Color[] colors = new Color[] { NanoChromatique.WHITE, NanoChromatique.GREEN };
		legend2.setLabelFont(InputFonts.getElements(10));
		legend2.setLabel("CELSIUS");
		legend2.setPathSide(PathSide.Below);
		legend2.setShader(fractions, colors);
		legend2.setDivergence(0);
		legend2.setOffsetLeft(0);
		legend2.setOffsetRight(0);

	}

	/**
	 * paint metrics
	 * 
	 * @param g2d
	 */
	private void paintMetrics(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();

		int radius = getGauge().getRadius() - 10;

		Arc2D arc2d = new Arc2D.Double(centerX - 2 * radius - 2 * radius - radius / 4, centerY - 2 * radius, 4 * radius, 4 * radius, -30, 60, Arc2D.OPEN);
		Arc2D arc2d2 = new Arc2D.Double(centerX - 2 * radius + 2 * radius + radius / 4, centerY - 2 * radius, 4 * radius, 4 * radius, 210, -60, Arc2D.OPEN);

		
		Arc2D arc2TextPathTop = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 0, 180, Arc2D.OPEN);
		Arc2D arc2TextPathBottom = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 0, -180, Arc2D.OPEN);
		
		Arc2D arc2TextPath2 = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 300, 60, Arc2D.OPEN);
		Arc2D arc2TextPath1 = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 240, -60, Arc2D.OPEN);

		// DEBUG ARC
		g2d.setColor(Color.BLUE);
//		g2d.draw(arc2TextPathTop);
//		g2d.draw(arc2TextPathBottom);
		
//		g2d.draw(arc2TextPath1);
//		g2d.draw(arc2TextPath2);
//		g2d.setColor(Color.RED);
		g2d.setColor(new Alpha(NanoChromatique.GRAY,250));
		g2d.draw(arc2d);
		g2d.draw(arc2d2);

		legendTop.setPath(arc2TextPathTop);
		legendBottom.setPath(arc2TextPathBottom);
		legend1.setPath(arc2TextPath1);
		legend2.setPath(arc2TextPath2);

		metricsPath1.setWindow2d(getGauge().getWindow2D());
		metricsPath1.resetPath();
		metricsPath1.append(arc2d);

		metricsPath2.setWindow2d(getGauge().getWindow2D());
		metricsPath2.resetPath();
		metricsPath2.append(arc2d2);

		radius = getGauge().getRadius();
		if (metricsPart1 == null) {

			metricsPart1 = new GaugePartBuffer(getGauge());

			Graphics2D g2dPart = metricsPart1.getGraphics();
			g2dPart.setRenderingHints(g2d.getRenderingHints());
			metricsPath1.setFontRenderContext(g2d.getFontRenderContext());

			legend1.draw(g2dPart);
			
			legendTop.draw(g2dPart);
			legendBottom.draw(g2dPart);

			List<GlyphMetric> metrics = metricsPath1.getMetrics();
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
		if (metricsPart2 == null) {

			metricsPart2 = new GaugePartBuffer(getGauge());

			Graphics2D g2dPart = metricsPart2.getGraphics();
			g2dPart.setRenderingHints(g2d.getRenderingHints());
			metricsPath2.setFontRenderContext(g2d.getFontRenderContext());

			legend2.draw(g2dPart);

			List<GlyphMetric> metrics = metricsPath2.getMetrics();
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
		g2d.drawImage(metricsPart1.getBuffer(), (int) centerX - radius, (int) centerY - radius, 2 * radius, 2 * radius, null);
		g2d.drawImage(metricsPart2.getBuffer(), (int) centerX - radius, (int) centerY - radius, 2 * radius, 2 * radius, null);
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

		g2d.setPaint(TexturePalette.getInterlacedCarbon1());
		g2d.fill(baseShape);

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
		paintNeedle1(g2d);
		paintNeedle2(g2d);
	}

	private void paintNeedle1(Graphics2D g2d) {

		int radius = getGauge().getRadius();
		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX() - radius + radius / 4;// (int)getGauge().getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();

		metricsPath1.setFontRenderContext(g2d.getFontRenderContext());

		metricsPath1.setWindow2d(getGauge().getWindow2D());

		Point2D center = new Point2D.Double(centerX, centerY);

		Point2D pNeedle2 = metricsPath1.getRadialPoint(50, 20, Side.SideLeft);
		// Point2D pNeedle2TT = getGauge().getWindow2D().userToPixel(pNeedle2);
		Line2D lNeedle = new Line2D.Double(center.getX(), center.getY(), pNeedle2.getX(), pNeedle2.getY());

		BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke stroke2 = new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		Shape tShape = stroke.createStrokedShape(lNeedle);
		Shape tShape2 = stroke2.createStrokedShape(lNeedle);

		Area area = new Area(tShape);
		Area area2 = new Area(tShape2);

		int w = 10;
		Ellipse2D cacheCenter = new Ellipse2D.Double(centerX - w, centerY - w, 2 * w, 2 * w);
		Area area3 = new Area(cacheCenter);

		g2d.setColor(ColorPalette.alpha(NanoChromatique.RED, 120));

		g2d.fill(area2);

		g2d.setColor(NanoChromatique.RED);

		g2d.fill(area);

		// deco gradient
		int w0 = 25;
		int r = NanoChromatique.PINK.getRed();
		int g = NanoChromatique.PINK.getGreen();
		int b = NanoChromatique.PINK.getBlue();

		Ellipse2D cacheCenter0 = new Ellipse2D.Double(centerX - w0, centerY - w0, 2 * w0, 2 * w0);

		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(centerX, centerY), 20, new float[] { 0f, 1f }, new Color[] { Color.BLACK, Color.BLACK.darker() });
		g2d.setPaint(rgp);
		g2d.fill(cacheCenter);
		g2d.setStroke(new BasicStroke(2f));
		// g2d.setColor(FilPalette.FIL_GRIS4);
		Color PINNACLE4 = new Color(105, 90, 168);
		g2d.setColor(PINNACLE4);

		// g2d.draw(cacheCenter);

	}

	private void paintNeedle2(Graphics2D g2d) {

		int radius = getGauge().getRadius();
		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX() + radius - radius / 4;
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();

		metricsPath2.setFontRenderContext(g2d.getFontRenderContext());

		metricsPath2.setWindow2d(getGauge().getWindow2D());

		Point2D center = new Point2D.Double(centerX, centerY);

		// for metrics path 1
		// Point2D pNeedle2 = metricsPath2.getRadialPoint(200, 10,
		// Side.SideLeft);

		// side or negative divergence, that is the question! idempotent
		// Point2D pNeedle2 = metricsPath2.getRadialPoint(200, 10,
		// Side.SideRight);
		Point2D pNeedle2 = metricsPath2.getRadialPoint(30, 20, Side.SideRight);

		Line2D lNeedle = new Line2D.Double(center.getX(), center.getY(), pNeedle2.getX(), pNeedle2.getY());

		BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke stroke2 = new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		Shape tShape = stroke.createStrokedShape(lNeedle);
		Shape tShape2 = stroke2.createStrokedShape(lNeedle);

		Area area = new Area(tShape);
		Area area2 = new Area(tShape2);

		int w = 10;
		Ellipse2D cacheCenter = new Ellipse2D.Double(centerX - w, centerY - w, 2 * w, 2 * w);
		Area area3 = new Area(cacheCenter);

		g2d.setColor(ColorPalette.alpha(NanoChromatique.BLUE, 120));

		g2d.fill(area2);

		g2d.setColor(NanoChromatique.BLUE);

		g2d.fill(area);

		// deco gradient
		int w0 = 25;
		int r = NanoChromatique.PINK.getRed();
		int g = NanoChromatique.PINK.getGreen();
		int b = NanoChromatique.PINK.getBlue();

		Ellipse2D cacheCenter0 = new Ellipse2D.Double(centerX - w0, centerY - w0, 2 * w0, 2 * w0);

		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(centerX, centerY), 20, new float[] { 0f, 1f }, new Color[] { Color.BLACK, Color.BLACK.darker() });
		g2d.setPaint(rgp);
		g2d.fill(cacheCenter);
		g2d.setStroke(new BasicStroke(2f));
		// g2d.setColor(FilPalette.FIL_GRIS4);
		Color PINNACLE4 = new Color(105, 90, 168);
		g2d.setColor(PINNACLE4);

		// g2d.draw(cacheCenter);

	}
}
