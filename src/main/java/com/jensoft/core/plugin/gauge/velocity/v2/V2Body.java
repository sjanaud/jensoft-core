/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.velocity.v2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.device.PartBuffer;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.BodyGaugePainter;

public class V2Body extends BodyGaugePainter {

    public V2Body() {
    	 pathManager = new GeneralMetricsPath();
         pathManager.setProjectionNature(ProjectionNature.DEVICE);

         pathManager.setMin(0);
         pathManager.setMax(8);

         GlyphMetric metric = new GlyphMetric();
         metric.setValue(0);
         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("0");
         metric.setDivergence(20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON1));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON1));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(1);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("1");
         metric.setDivergence(25);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON2));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON2));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(2);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("2");
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(3);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("3");
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.BUTTER1));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.BUTTER1));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(4);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("4");
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.BUTTER2));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.BUTTER2));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(5);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("5");
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.BUTTER3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.BUTTER3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(6);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("6");
         metric.setDivergence(30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.SCARLETRED3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.SCARLETRED3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(7);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("7");
         metric.setDivergence(25);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.SCARLETRED3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.SCARLETRED3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(8);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("8");
         metric.setDivergence(20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.SCARLETRED3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.SCARLETRED3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
         pathManager.addMetric(metric);
         
         needle = new V2Needle();
         needle.setPathManager(pathManager);
         
    }
    private V2Needle needle;
    private GeneralMetricsPath pathManager;
    private Arc2D arc2d;
    private int startAngleDegreee = 0;
    private int endAngleDegree = 220;
    private PartBuffer metricsPart;

    @Override
    public void paintBody(Graphics2D g2d, RadialGauge radialGauge) {
        paintMetrics(g2d);
		paintNeedle(g2d);
	}

	private void paintNeedle(Graphics2D g2d) {
		needle.setGauge(getGauge());
		needle.paintGauge(g2d, getGauge());
	}


    private void paintMetrics(Graphics2D g2d) {

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
        int radius = getGauge().getRadius() - 10;

        startAngleDegreee = 210;
        endAngleDegree = -30;

        
        pathManager.setWindow2d(getGauge().getWindow2D());

        pathManager.resetPath();

        arc2d = new Arc2D.Double(centerX - radius, centerY - radius,
                                 2 * radius, 2 * radius, startAngleDegreee, endAngleDegree
                                         - startAngleDegreee, Arc2D.OPEN);

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

}
