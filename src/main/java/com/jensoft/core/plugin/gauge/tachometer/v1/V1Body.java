/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.tachometer.v1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.device.PartBuffer;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.PetalPalette;
import com.jensoft.core.palette.TexturePalette;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.BodyGaugePainter;

public class V1Body extends BodyGaugePainter {

    public V1Body() {
    	 metricsPath = new GeneralMetricsPath();
    	 metricsPath.setProjectionNature(ProjectionNature.DEVICE);

    	 metricsPath.setMin(0);
    	 metricsPath.setMax(280);

         GlyphMetric metric = new GlyphMetric();
         metric.setValue(20);
         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("20");
        
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 PetalPalette.PETAL1_HC));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             PetalPalette.PETAL1_HC));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
         metricsPath.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(60);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("60");
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 PetalPalette.PETAL1_HC));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             PetalPalette.PETAL1_HC));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
         metricsPath.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(120);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("120");
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 PetalPalette.PETAL1_HC));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             PetalPalette.PETAL1_HC));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
         metricsPath.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(180);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("180");
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 PetalPalette.PETAL1_HC));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             PetalPalette.PETAL1_HC));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
         metricsPath.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(220);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("220");
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 PetalPalette.PETAL1_HC));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             PetalPalette.PETAL1_HC));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
         metricsPath.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(260);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("260");
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 PetalPalette.PETAL1_HC));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             PetalPalette.PETAL1_HC));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
         metricsPath.addMetric(metric);

         

         needle = new V1Needle();
         needle.setPathManager(metricsPath);
    }

    private GeneralMetricsPath metricsPath;
    private V1Needle needle;
    private Arc2D arc2d;
    private int startAngleDegreee = 0;
    private int endAngleDegree = 220;
    private PartBuffer metricsPart;

    public GeneralMetricsPath getMetricsPath() {
		return metricsPath;
	}

	public void setMetricsPath(GeneralMetricsPath metricsPath) {
		this.metricsPath = metricsPath;
	}

	private void paintMetrics(Graphics2D g2d) {

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
        int radius = getGauge().getRadius() - 10;

       // startAngleDegreee = 210;
        //endAngleDegree = -30;

       // startAngleDegreee = 260;
        //endAngleDegree = 0;
        
        startAngleDegreee = 260;
        endAngleDegree = -80;
        
        GeneralMetricsPath pathManager = getMetricsPath();
        pathManager.setWindow2d(getGauge().getWindow2D());

        pathManager.resetPath();

        arc2d = new Arc2D.Double(centerX - radius, centerY - radius,
                                 2 * radius, 2 * radius, startAngleDegreee, endAngleDegree
                                         - startAngleDegreee, Arc2D.OPEN);

        g2d.setColor(Color.RED);
        g2d.draw(arc2d);
        
        pathManager.append(arc2d);
       
        radius = getGauge().getRadius();
        if (metricsPart == null) {

            metricsPart = new PartBuffer(centerX - radius, centerY - radius,
                                         2 * radius, 2 * radius);

            Graphics2D g2dPart = metricsPart.getBuffer().createGraphics();
            g2dPart.setRenderingHints(g2d.getRenderingHints());
            g2dPart.translate(-centerX + radius, -centerY + radius);

            g2dPart.setStroke(new BasicStroke(0.4f));
            g2dPart.setColor(Color.BLACK);

            pathManager.setFontRenderContext(g2dPart.getFontRenderContext());

            List<GlyphMetric> metrics = pathManager.getMetrics();
            for (GlyphMetric m : metrics) {

                if (m.getGlyphMetricMarkerPainter() != null) {
                    m.getGlyphMetricMarkerPainter()
                            .paintGlyphMetric(g2dPart, m);
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

            g2d.drawImage(metricsPart.getBuffer(), (int) centerX - radius,
                          (int) centerY - radius, 2 * radius, 2 * radius, null);

        }
        else {

            g2d.drawImage(metricsPart.getBuffer(), (int) centerX - radius,
                          (int) centerY - radius, 2 * radius, 2 * radius, null);
        }

    }

    private void paintBase(Graphics2D g2d) {

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
        int radius = getGauge().getRadius();

        Ellipse2D baseShape = new Ellipse2D.Double(centerX - radius, centerY
                - radius, 2 * radius, 2 * radius);

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
