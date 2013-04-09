/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.tools.sourcetracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricFill;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.DefaultMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.palette.Spectral;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.function.core.MetricsPathFunction;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>CurveTrackerPlugin</code> takes the responsibility
 * to draw curve label for the current mouse point coordinate.
 * 
 * @see MetricsPathFunction
 * @see AbstractPlugin
 * @author Sebastien Janaud
 */
public class SourceTrackerPlugin extends AbstractPlugin implements
        AbstractPlugin.OnMoveListener,
        AbstractPlugin.OnPressListener,
        AbstractPlugin.OnDragListener,
        AbstractPlugin.OnReleaseListener {

    /** the curve source */
    private SourceFunction trackedSerie;

    /** tracked point in device projection system */
    private Point2D currentTrackDevice;

    /** tracked point in user projection system */
    private Point2D currentTrackUser;

    /** curve metrics path */
    private MetricsPathFunction curveMetricsPath;

    /** registered series */
    private List<SourceFunction> series;

    /** listeners */
    private EventListenerList trackerListenerList;

    /** default style position */
    private StylePosition defaultStylePosition = StylePosition.Default;

    /** label divergence from path */
    private int divergence = 20;

    /** default glyph metrics fill */
    private GlyphMetricFill glyphMetricsFill = new GlyphFill(Spectral.SPECTRAL_RED, Color.DARK_GRAY);

    /** default marker */
    private DefaultMarker glyphMarker = new DefaultMarker(NanoChromatique.RED);

    /** default glyph font */
    private Font defaultGlyphFont = InputFonts.getFont(InputFonts.NEUROPOL, 12);

    /**
     * create a curve tracker for the specified serie parameter
     * 
     * @param trackedSerie
     *            the serie to track
     */
    public SourceTrackerPlugin() {
        series = new ArrayList<SourceFunction>();
        curveMetricsPath = new MetricsPathFunction();
        trackerListenerList = new EventListenerList();
        setOnMoveListener(this);
        setOnPressListener(this);
        setOnDragListener(this);
        setOnReleaseListener(this);
        setSelectable(true);
        setPriority(10000);
    }

    /**
     * add serie tracker listener
     * 
     * @param listener
     */
    public void addSerieTrackerListener(SourceTrackerListener listener) {
        trackerListenerList.add(SourceTrackerListener.class, listener);
    }

    /**
     * remove serie tracker listener
     * 
     * @param listener
     */
    public void removeSerieTrackerListener(SourceTrackerListener listener) {
        trackerListenerList.remove(SourceTrackerListener.class, listener);
    }

    /**
     * fire serie being tracked started
     */
    public void fireSerieTracked() {
        Object[] listeners = trackerListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == SourceTrackerListener.class) {
                    ((SourceTrackerListener) listeners[i + 1])
                            .serieTracked(new SourceTrackerEvent(trackedSerie));
                }
            }
        }
    }

    /**
     * fire serie registered
     * 
     * @param serie
     *            the serie that has just been registered
     */
    public void fireSerieRegistered(SourceFunction serie) {
        Object[] listeners = trackerListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == SourceTrackerListener.class) {
                    ((SourceTrackerListener) listeners[i + 1])
                            .serieRegistered(new SourceTrackerEvent(serie));
                }
            }
        }
    }

    /**
     * fire serie current track
     * 
     * @param serie
     *            the serie that has just been tracked
     */
    public void fireCurrentTrack() {
        Object[] listeners = trackerListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == SourceTrackerListener.class) {
                    ((SourceTrackerListener) listeners[i + 1])
                            .currentTrack(new SourceTrackerEvent(trackedSerie));
                }
            }
        }
    }

    /**
     * @return the series
     */
    public List<SourceFunction> getSeries() {
        return series;
    }

    /**
     * @param series
     *            the series to set
     */
    public void setSeries(List<SourceFunction> series) {
        this.series = series;
    }

    /**
     * @return the trackedSerie
     */
    public SourceFunction getTrackedSerie() {
        return trackedSerie;
    }

    /**
     * register serie in this tracker
     * 
     * @param serie
     *            the serie to register
     */
    public void registerSerie(SourceFunction serie) {
        if (!series.contains(serie)) {
            series.add(serie);
            fireSerieRegistered(serie);
        }
    }

    /**
     * track the given serie, register serie if i does not
     * have been registered in curve metrics path
     * 
     * @param serie
     *            the serie to track
     */
    public void trackSerie(SourceFunction serie) {
        registerSerie(serie);
        trackedSerie = serie;
        curveMetricsPath.setSource(trackedSerie);
        fireSerieTracked();
    }

    /**
     * get a lock/unlock action
     * 
     * @return lock unlock action
     */
    public SerieTrackerLockUnlockAction getSerieTrackerLockUnlockAction() {
        return new SerieTrackerLockUnlockAction();
    }

    /**
     * translate select action
     */
    class SerieTrackerLockUnlockAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (isLockSelected()) {
                        unlockSelected();
                    }
                    else {
                        lockSelected();
                    }
                }
            });
        }

    }

    /**
     * add metric glyph label
     * 
     * @param metric
     *            the glyph metric to add
     */
    private void addMetrics(GlyphMetric metric) {
        if (trackedSerie == null) {
            return;
        }
        if (metric.getValue() >= trackedSerie.first().getX()
                && metric.getValue() <= trackedSerie.last().getX()) {
            curveMetricsPath.addMetrics(metric);
        }
        else {
            throw new IllegalArgumentException(
                                               "metric value out of serie bound");
        }
    }

    /**
     * @return the glyphMetricsFill
     */
    public GlyphMetricFill getGlyphMetricsFill() {
        return glyphMetricsFill;
    }

    /**
     * @param glyphMetricsFill
     *            the glyphMetricsFill to set
     */
    public void setGlyphMetricsFill(GlyphMetricFill glyphMetricsFill) {
        this.glyphMetricsFill = glyphMetricsFill;
    }

    /**
     * @return the glyphMarker
     */
    public DefaultMarker getGlyphMarker() {
        return glyphMarker;
    }

    /**
     * @param glyphMarker
     *            the glyphMarker to set
     */
    public void setGlyphMarker(DefaultMarker glyphMarker) {
        this.glyphMarker = glyphMarker;
    }

    /**
     * remove all glyphs
     */
    private void clearMetrics() {
        curveMetricsPath.clearMetrics();
    }

    /**
     * return the current tracked point in the device projection system
     * 
     * @return the currentTrackDevice
     */
    public Point2D getCurrentTrackDevice() {
        return currentTrackDevice;
    }

    /**
     * return the current tracked point in the user projection system
     * 
     * @return the currentTrackUser
     */
    public Point2D getCurrentTrackUser() {
        return currentTrackUser;
    }

    /**
     * process the metrics
     * <p>
     * process the related metrics for the specified device mouse point coordinate into a point in the user window
     * projection and append it to the curve metrics path.
     * </p>
     * 
     * @param me
     *            the mouse event to transform in user metrics.
     */
    private void processMetrics(MouseEvent me) {
        int currentX = me.getX();
        int currentY = me.getY();
        Window2D w2d = getWindow2D();

        currentTrackDevice = new Point2D.Double(currentX, currentY);
        currentTrackUser = w2d.pixelToUser(currentTrackDevice);

        clearMetrics();

        GlyphMetric metric = new GlyphMetric();
        metric.setValue(currentTrackUser.getX());
        metric.setStylePosition(defaultStylePosition);
        metric.setMetricsNature(GlyphMetricsNature.Median);
        metric.setMetricsLabel(currentTrackUser.getY() + "");
        metric.setDivergence(divergence);
        metric.setGlyphMetricFill(glyphMetricsFill);
        metric.setGlyphMetricMarkerPainter(glyphMarker);
        metric.setFont(defaultGlyphFont);

        try {
            addMetrics(metric);
            fireCurrentTrack();
        }
        catch (IllegalArgumentException e) {
        }
        getWindow2D().getDevice2D().repaintDevice();
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }

        processMetrics(me);
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }

        processMetrics(me);
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }

        clearMetrics();
        getWindow2D().getDevice2D().repaintDevice();
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.sw2d.core.view.View2D,
     * java.awt.Graphics2D, com.jensoft.sw2d.core.window.WindowPart)
     */
    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
        curveMetricsPath.setWindow2d(getWindow2D());
        curveMetricsPath.setFontRenderContext(g2d.getFontRenderContext());
        List<GlyphMetric> metrics = curveMetricsPath.getMetrics();
        for (GlyphMetric glyphMetric : metrics) {

            if (glyphMetric.getGlyphMetricMarkerPainter() != null) {
                glyphMetric.getGlyphMetricMarkerPainter().paintGlyphMetric(
                                                                           g2d, glyphMetric);
            }

            if (glyphMetric.getGlyphMetricFill() != null) {
                glyphMetric.getGlyphMetricFill().paintGlyphMetric(g2d,
                                                                  glyphMetric);
            }

            if (glyphMetric.getGlyphMetricDraw() != null) {
                glyphMetric.getGlyphMetricDraw().paintGlyphMetric(g2d,
                                                                  glyphMetric);
            }

        }

    }

}
