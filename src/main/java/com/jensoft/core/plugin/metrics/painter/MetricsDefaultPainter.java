/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.painter;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import javax.swing.JComponent;

import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.MetricsRenderContext;
import com.jensoft.core.plugin.metrics.geom.Metrics.Gravity;
import com.jensoft.core.plugin.metrics.geom.Metrics.MarkerPosition;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.window.WindowPart;

/**
 * MetricsDefaultPainter
 * 
 * @author sebastien janaud
 */
public class MetricsDefaultPainter extends AbstractMetricsPainter {

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.painter.AbstractMetricsPainter#
     * doPaintLineMetrics(java.awt.Graphics2D, java.awt.geom.Point2D,
     * java.awt.geom.Point2D, java.awt.Color)
     */
    @Override
    public void doPaintLineMetrics(Graphics2D g2d, Point2D start, Point2D end,
            Color axisBaseColor) {
        g2d.setColor(axisBaseColor);
        Line2D lineMetrics = new Line2D.Double(start, end);
        g2d.draw(lineMetrics);
    }

    /**
     * paint metric indicator
     * 
     * @param g2d
     * @param metric
     */
    private void paintMetricsIndicator(Graphics2D g2d, Metrics metric) {
        MetricsRenderContext renderContext = getMetricsRenderContext();

        if (!metric.isVisible()) {
            return;
        }

        g2d.setFont(renderContext.getFont(metric));
        int markerSize = renderContext.getMarkerSize(metric);

        if (metric.getMetricsMarkerColor() != null) {
            g2d.setColor(metric.getMetricsMarkerColor());
        }
        else {
            g2d.setColor(renderContext.getWindow2D().getThemeColor());
        }

        Shape metricsShapeIndicator = null;

        Point2D position = metric.getMarkerLocation();
        if (metric.getMetricsType() == MetricsType.XMetrics) {

            if (metric.getMarkerPosition() == MarkerPosition.S) {
                metricsShapeIndicator = new Line2D.Double(position.getX(),
                                                          position.getY() + 2, position.getX(),
                                                          position.getY() + markerSize + 2);
            }
            if (metric.getMarkerPosition() == MarkerPosition.N) {
                metricsShapeIndicator = new Line2D.Double(position.getX(),
                                                          position.getY() - 2, position.getX(),
                                                          position.getY() - markerSize - 2);
            }
          
        }
        if (metric.getMetricsType() == MetricsType.YMetrics) {
            if (metric.getMarkerPosition() == MarkerPosition.W) {
                metricsShapeIndicator = new Line2D.Double(position.getX()
                        - markerSize - 2, position.getY(),
                                                          position.getX() - 2, position.getY());
            }
            if (metric.getMarkerPosition() == MarkerPosition.E) {
                metricsShapeIndicator = new Line2D.Double(
                                                          position.getX() + 2, position.getY(),
                                                          position.getX() + markerSize + 2, position.getY());
            }
            
        }

        if (metricsShapeIndicator != null && metric.isLockMarker()) {
            g2d.draw(metricsShapeIndicator);
        }
    }

    /**
     * paint metric label
     * 
     * @param g2d
     * @param metric
     */
    private void paintMetricsLabel(Graphics2D g2d, Metrics metric) {
        MetricsRenderContext renderContext = getMetricsRenderContext();
        Point2D position = metric.getMarkerLocation();
        int markerSize = renderContext.getMarkerSize(metric);
        if ((metric.getNature() == Metrics.MAJOR || metric.getNature() == Metrics.MEDIAN)
                && metric.isLockLabel()) {

            if (metric.getMetricsLabelColor() != null) {
                g2d.setColor(metric.getMetricsLabelColor());
            }
            else {
                g2d.setColor(renderContext.getWindow2D().getThemeColor());
            }

            if (metric.getMetricsLabel() != null) {

                int tickLabelWidth = renderContext.metricsWidth(metric);
                if (metric.getMetricsType() == MetricsType.XMetrics) {

                    if (metric.getMarkerPosition() == MarkerPosition.S) {
                        g2d.drawString(
                                       metric.getMetricsLabel(),
                                       (int) (position.getX() - tickLabelWidth / 2),
                                       (int) (position.getY() + markerSize
                                               + renderContext.metricsHeight(metric) + 2));
                    }
                    if (metric.getMarkerPosition() == MarkerPosition.N) {
                        g2d.drawString(
                                       metric.getMetricsLabel(),
                                       (int) (position.getX() - tickLabelWidth / 2),
                                       (int) (position.getY() - markerSize - 4));
                    }

                }
                if (metric.getMetricsType() == MetricsType.YMetrics) {
                    if (metric.getGravity() == Gravity.Neutral) {
                        if (metric.getMarkerPosition() == MarkerPosition.W) {

                            g2d.drawString(metric.getMetricsLabel(),
                                           (int) (metric.getMarkerLocation()
                                                   .getX()
                                                   - tickLabelWidth
                                                   - markerSize - 4),
                                           (int) (position.getY() + renderContext
                                                   .metricsHeight(metric) / 3));
                        }
                        if (metric.getMarkerPosition() == MarkerPosition.E) {
                            g2d.drawString(
                                           metric.getMetricsLabel(),
                                           (int) (position.getX() + markerSize + 4),
                                           (int) (position.getY() + renderContext
                                                   .metricsHeight(metric) / 3));
                        }
                    }
                    else {

                        boolean paintFlag = false;
                        JComponent westPart = getMetricsRenderContext().getView2D()
                                .getWindowComponent(WindowPart.West);
                        if (metric.getGravity() == Gravity.First) {
                            if (position.getY() < westPart.getHeight() - 6) {
                                paintFlag = true;
                            }
                        }
                        if (metric.getGravity() == Gravity.Last) {
                            if (position.getY() > 6) {
                                paintFlag = true;
                            }
                        }

                        if (paintFlag) {
                            if (metric.getMarkerPosition() == MarkerPosition.W) {
                                g2d.drawString(metric.getMetricsLabel(),
                                               (int) (metric.getMarkerLocation()
                                                       .getX()
                                                       - tickLabelWidth
                                                       - markerSize - 4),
                                               (int) (position.getY() + renderContext
                                                       .metricsHeight(metric) / 3));
                            }
                            if (metric.getMarkerPosition() == MarkerPosition.E) {
                                g2d.drawString(
                                               metric.getMetricsLabel(),
                                               (int) (position.getX() + markerSize + 4),
                                               (int) (position.getY() + renderContext
                                                       .metricsHeight(metric) / 3));
                            }
                        }

                    }
                }

            }

        }
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.painter.AbstractMetricsPainter#
     * doPaintMetrics(java.awt.Graphics2D, java.util.List)
     */
    @Override
    public void doPaintMetrics(Graphics2D g2d, List<Metrics> metrics) {

        g2d.setStroke(new BasicStroke());
        for (int i = 0; i < metrics.size(); i++) {
            System.out.println("paint metrics : "+metrics.get(i));
            paintMetricsIndicator(g2d, metrics.get(i));
            paintMetricsLabel(g2d, metrics.get(i));
        }

        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
