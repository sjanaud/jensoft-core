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
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.GlyphUtil;
import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.MetricsRenderContext;
import com.jensoft.core.plugin.metrics.geom.Metrics.Gravity;
import com.jensoft.core.plugin.metrics.geom.Metrics.MarkerPosition;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;

/**
 * <code>MetricsTimingPainter<code> takes the responsibility to paint timing metrics
 * 
 * @author sebastien janaud
 */
public class MetricsGlyphPainter extends AbstractMetricsPainter {

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
     * paint metrics indicator
     * 
     * @param g2d
     * @param metric
     */
    public void paintMetricsIndicator(Graphics2D g2d, Metrics metric) {
        MetricsRenderContext renderContext = getMetricsRenderContext();
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
    
    protected void paintNorthMetricsLabel(Graphics2D g2d, Metrics metric){
        MetricsRenderContext renderContext = getMetricsRenderContext();
        Point2D position = metric.getMarkerLocation();
        int markerSize = renderContext.getMarkerSize(metric);
        int tickLabelWidth = renderContext.metricsAbsoluteWidth(metric);
        g2d.drawString(
                       metric.getMetricsLabel(),
                       (int) (position.getX() - tickLabelWidth / 2),
                       (int) (position.getY() - markerSize - 4));
    }
    
    protected void paintSouthMetricsLabel(Graphics2D g2d, Metrics metric){
        MetricsRenderContext renderContext = getMetricsRenderContext();
        Point2D position = metric.getMarkerLocation();
        int markerSize = renderContext.getMarkerSize(metric);
        int tickLabelWidth = renderContext.metricsAbsoluteWidth(metric);
        g2d.drawString(
                       metric.getMetricsLabel(),
                       (int) (position.getX() - tickLabelWidth / 2),
                       (int) (position.getY() + markerSize
                               + renderContext.metricsHeight(metric) + 2));
    }
    
    protected void paintWestMetricsLabel(Graphics2D g2d, Metrics metric){
        MetricsRenderContext renderContext = getMetricsRenderContext();
        Point2D position = metric.getMarkerLocation();
        int markerSize = renderContext.getMarkerSize(metric);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector legendGlyphVector = renderContext.getFont(metric)
                .createGlyphVector(frc,
                                   metric.getMetricsLabel());
        float legendWidth = GlyphUtil.getGlyphWidth(legendGlyphVector);
        AffineTransform af = new AffineTransform();
        for (int g = 0; g < legendGlyphVector.getNumGlyphs(); g++) {

            Point2D p = legendGlyphVector.getGlyphPosition(g);
            float px = (float) p.getX();
            float py = (float) p.getY();

            Point2D pointGlyph = null;
           
            
            pointGlyph = new Point2D.Double(position.getX() - markerSize - renderContext.metricsAbsoluteHeight(metric)/2, position.getY()

                    + legendWidth
                    / 2
                    - GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,
                                                     g));

            Shape glyph = legendGlyphVector.getGlyphOutline(g);
            af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
            af.rotate(-Math.PI / 2);
            af.translate(-px, -py);
            Shape ts = af.createTransformedShape(glyph);
            g2d.fill(ts);
        }
    }
    
    protected void paintEastMetricsLabel(Graphics2D g2d, Metrics metric){
        MetricsRenderContext renderContext = getMetricsRenderContext();
        Point2D position = metric.getMarkerLocation();
        int markerSize = renderContext.getMarkerSize(metric);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector legendGlyphVector = renderContext.getFont(metric)
                .createGlyphVector(frc,
                                   metric.getMetricsLabel());
        float legendWidth = GlyphUtil.getGlyphWidth(legendGlyphVector);
        AffineTransform af = new AffineTransform();
        for (int g = 0; g < legendGlyphVector.getNumGlyphs(); g++) {

            Point2D p = legendGlyphVector.getGlyphPosition(g);
            float px = (float) p.getX();
            float py = (float) p.getY();

            Point2D pointGlyph = null;

            pointGlyph = new Point2D.Double(
                                            position.getX() + markerSize
                                                    + renderContext.metricsAbsoluteHeight(metric),
                                            position.getY()
                                                    + legendWidth
                                                    / 2
                                                    - GlyphUtil
                                                            .getGlyphWidthAtToken(legendGlyphVector,
                                                                                  g));

            Shape glyph = legendGlyphVector.getGlyphOutline(g);
            af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
            af.rotate(-Math.PI / 2);
            af.translate(-px, -py);
            Shape ts = af.createTransformedShape(glyph);
            g2d.fill(ts);
        }
    }

    /**
     * paint timing metrics label
     * 
     * @param g2d
     * @param metric
     */
    public void paintMetricsLabel(Graphics2D g2d, Metrics metric) {
       // System.out.println("paint metrics : "+metric);
        MetricsRenderContext renderContext = getMetricsRenderContext();
        g2d.setFont(renderContext.getFont(metric));
        Point2D position = metric.getMarkerLocation();
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
                       paintSouthMetricsLabel(g2d, metric);
                    }
                    if (metric.getMarkerPosition() == MarkerPosition.N) {
                       paintNorthMetricsLabel(g2d, metric);
                    }

                }
                if (metric.getMetricsType() == MetricsType.YMetrics) {
                    if (metric.getGravity() == Gravity.Neutral) {
                        if (metric.getMarkerPosition() == MarkerPosition.W) {
                           paintWestMetricsLabel(g2d, metric);
                        }
                        if (metric.getMarkerPosition() == MarkerPosition.E) {                            
                            paintEastMetricsLabel(g2d, metric);
                        }
                    }
                    else {

                        boolean paintFlag = false;
                        
                        int height = getMetricsRenderContext().getWindow2D().getDevice2D().getDeviceHeight();
                        if (metric.getGravity() == Gravity.First) {
                            if (position.getY() < height - tickLabelWidth / 2) {
                                paintFlag = true;
                            }
                        }
                        if (metric.getGravity() == Gravity.Last) {
                            if (position.getY() > tickLabelWidth / 2) {
                                paintFlag = true;
                            }
                        }

                        if (paintFlag) {
                            if (metric.getMarkerPosition() == MarkerPosition.W) {                        
                                paintWestMetricsLabel(g2d, metric);
                            }
                            if (metric.getMarkerPosition() == MarkerPosition.E) {
                                 paintEastMetricsLabel(g2d, metric);
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

            Metrics metric = metrics.get(i);

            if (!metric.isVisible()) {
                continue;
            }

            paintMetricsIndicator(g2d, metric);
            paintMetricsLabel(g2d, metric);
        }

        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
