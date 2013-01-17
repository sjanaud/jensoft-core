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

public class C1Body extends BodyGaugePainter {

    public C1Body() {

    }

    private Arc2D arc2d;
    private int startAngleDegreee = 0;
    private int endAngleDegree = 360;
    private PartBuffer metricsPart;

    private void paintMetrics(Graphics2D g2d) {

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();

        int radius = getGauge().getRadius() - 10;

        GeneralMetricsPath pathManager = getGauge().getMetricsManager();
        pathManager.setWindow2d(getGauge().getWindow2D());
        pathManager.resetPath();
        pathManager.setFontRenderContext(g2d.getFontRenderContext());
        arc2d = new Arc2D.Double(centerX - radius, centerY - radius,
                                 2 * radius, 2 * radius, startAngleDegreee, endAngleDegree
                                         - startAngleDegreee, Arc2D.OPEN);

        radius = getGauge().getRadius();
        pathManager.append(arc2d);

        g2d.setColor(Color.RED);
        g2d.drawRect((int) (centerX - radius), (int) (centerY - radius),
                     2 * radius, 2 * radius);
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

    @Override
    public void paintBody(Graphics2D g2d, RadialGauge radialGauge) {

        paintMetrics(g2d);

    }

}
