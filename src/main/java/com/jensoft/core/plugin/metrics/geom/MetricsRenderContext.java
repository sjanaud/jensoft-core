/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.geom;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

/**
 * <code>MetricsRenderContext</code>
 * 
 * @author sebastien janaud
 */
public class MetricsRenderContext {

    /** view 2D */
    private View2D view2D;

    /** window 2D */
    private Window2D window2D;

    /** graphics context */
    private Graphics2D graphics2D;

    /** median metrics font */
    private Font metricsMedianFont = new Font("Dialog", Font.PLAIN, 10);

    /** major metrics font */
    private Font metricsMajorFont = new Font("Dialog", Font.PLAIN, 12);

    /**
     * create the metrics render context with specified rendering context
     * 
     * @param view2d
     *            the view 2D
     * @param window2d
     *            the window 2D
     * @param graphics2d
     *            the graphics context
     */
    public MetricsRenderContext(View2D view2d, Window2D window2d,
            Graphics2D graphics2d) {
        view2D = view2d;
        window2D = window2d;
        graphics2D = graphics2d;
    }

    /**
     * get the graphics context of this metrics render context
     * 
     * @return the graphics context
     */
    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    /**
     * set the graphics context for this metrics render context
     * 
     * @param graphics2d
     *            the graphics context to set
     */
    public void setGraphics2D(Graphics2D graphics2d) {
        graphics2D = graphics2d;
    }

    /**
     * get the view2D for this metrics render context
     * 
     * @return view 2D
     */
    public View2D getView2D() {
        return view2D;
    }

    /**
     * set the view2D for this metrics render context
     * 
     * @param view2d
     *            the view 2D to set for this render context
     */
    public void setView2D(View2D view2d) {
        view2D = view2d;
    }

    /**
     * get the window2D for this render context
     * 
     * @return the window 2D
     */
    public Window2D getWindow2D() {
        return window2D;
    }

    /**
     * set the window 2D for this render context
     * 
     * @param window2d
     *            the window 2D to set
     */
    public void setWindow2D(Window2D window2d) {
        window2D = window2d;
    }

    /**
     * @return the metricsMedianFont
     */
    public Font getMetricsMedianFont() {
        return metricsMedianFont;
    }

    /**
     * @param metricsMedianFont
     *            the metricsMedianFont to set
     */
    public void setMetricsMedianFont(Font metricsMedianFont) {
        this.metricsMedianFont = metricsMedianFont;
    }

    /**
     * @return the metricsMajorFont
     */
    public Font getMetricsMajorFont() {
        return metricsMajorFont;
    }

    /**
     * @param metricsMajorFont
     *            the metricsMajorFont to set
     */
    public void setMetricsMajorFont(Font metricsMajorFont) {
        this.metricsMajorFont = metricsMajorFont;
    }

    /**
     * get the metrics label width
     * 
     * @param metrics
     *            the metrics to measure
     * @return metrics label width
     */
    public int metricsWidth(Metrics metrics) {
        if (metrics.getNature() == Metrics.MINOR) {
            return 6;
        }
        else {
            if (metrics.getMetricsLabel() != null) {
                String mLabel = metrics.getMetricsLabel();
                FontMetrics fmetrics = graphics2D.getFontMetrics(getFont(metrics));
                return fmetrics.stringWidth(mLabel) + 6;
            }
            return 6;
        }
    }
    
    /**
     * get the metrics label width
     * 
     * @param metrics
     *            the metrics to measure
     * @return metrics label width
     */
    public int metricsAbsoluteWidth(Metrics metrics) {
        if (metrics.getNature() == Metrics.MINOR) {
            return 0;
        }
        else {
            if (metrics.getMetricsLabel() != null) {
                String mLabel = metrics.getMetricsLabel();
                FontMetrics fmetrics = graphics2D.getFontMetrics(getFont(metrics));
                return fmetrics.stringWidth(mLabel);
            }
            return 6;
        }
    }

    /**
     * get the absolute metrics font height
     * 
     * @return absolute metrics font height
     */
    public int metricsAbsoluteHeight(Metrics metrics) {
        if (metrics.getNature() == Metrics.MINOR) {
            return 0;
        }
        else {
            FontMetrics fmetrics = graphics2D.getFontMetrics(getFont(metrics));
            return fmetrics.getHeight();
        }
    }

    /**
     * get the metrics height with guard ihterval
     * 
     * @return metrics height
     */
    public int metricsHeight(Metrics metrics) {
        // FontMetrics fmetrics = graphics2D.getFontMetrics();
        // return fmetrics.getHeight();
        if (metrics.getNature() == Metrics.MINOR) {
            return 6;
        }
        else {
            if (metrics.getMetricsLabel() != null) {
                String mLabel = metrics.getMetricsLabel();
                FontMetrics fmetrics = graphics2D.getFontMetrics(getFont(metrics));
                return fmetrics.getHeight() + 6;
            }
            return 6;
        }
    }

    public Font getFont(Metrics m) {
        if (m.getNature() == Metrics.MAJOR) {
            return metricsMajorFont;
        }
        else if (m.getNature() == Metrics.MEDIAN) {
            return metricsMedianFont;
        }
        return null;
    }

    /**
     * get the tick marker size
     * 
     * @param metric
     *            the given metrics
     * @return the tick marker size
     */
    public int getMarkerSize(Metrics metric) {
        int markerSize = 2;
        if (metric.getNature() == Metrics.MAJOR) {
            markerSize = 8;
        }
        if (metric.getNature() == Metrics.MEDIAN) {
            markerSize = 4;
        }
        if (metric.getNature() == Metrics.MINOR) {
            markerSize = 2;
        }
        return markerSize;
    }

}
