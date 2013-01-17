/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.tools.peaktracker;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerEvent;
import com.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerListener;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>PeakTrackerPlugin</code> takes the responsibility to track peak in serie
 * 
 * @author Sebastien Janaud
 */
public class PeakTrackerPlugin extends AbstractPlugin implements
        AbstractPlugin.OnMoveListener,
        AbstractPlugin.OnPressListener,
        AbstractPlugin.OnDragListener,
        AbstractPlugin.OnReleaseListener {

    /** registered series */
    private List<SourceFunction> series;

    /** registered series */
    private List<SourceFunction> trackedSeries;

    /** listeners */
    private EventListenerList trackerListenerList;

    /** track frame width */
    private int trackFrameWidth = 10;

    /** track frame height */
    private int trackFrameHeight = 10;

    /**
     * create peak tracker
     */
    public PeakTrackerPlugin() {
        setName("PeakTrackerPlugin");
        series = new ArrayList<SourceFunction>();
        trackedSeries = new ArrayList<SourceFunction>();
        trackerListenerList = new EventListenerList();
        setOnMoveListener(this);
        setOnPressListener(this);
        setOnDragListener(this);
        setOnReleaseListener(this);
        setSelectable(true);
        setPriority(10000);
    }

    /**
     * add peak tracker listener
     * 
     * @param listener
     */
    public void addPeakTrackerListener(PeakTrackerListener listener) {
        trackerListenerList.add(PeakTrackerListener.class, listener);
    }

    /**
     * remove peak tracker listener
     * 
     * @param listener
     */
    public void removePeakTrackerListener(PeakTrackerListener listener) {
        trackerListenerList.remove(PeakTrackerListener.class, listener);
    }

    /**
     * fire serie being tracked started
     */
    public void firePeakTracked(SourceFunction serie) {
        Object[] listeners = trackerListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PeakTrackerListener.class) {
                    ((PeakTrackerListener) listeners[i + 1])
                            .peakTracked(new PeakTrackerEvent(serie));
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
     * @return the trackFrameWidth
     */
    public int getTrackFrameWidth() {
        return trackFrameWidth;
    }

    /**
     * @param trackFrameWidth
     *            the trackFrameWidth to set
     */
    public void setTrackFrameWidth(int trackFrameWidth) {
        this.trackFrameWidth = trackFrameWidth;
    }

    /**
     * @return the trackFrameHeight
     */
    public int getTrackFrameHeight() {
        return trackFrameHeight;
    }

    /**
     * @param trackFrameHeight
     *            the trackFrameHeight to set
     */
    public void setTrackFrameHeight(int trackFrameHeight) {
        this.trackFrameHeight = trackFrameHeight;
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
     * have been registered.
     * 
     * @param serie
     *            the serie to track
     */
    public void trackSerie(SourceFunction serie) {
        registerSerie(serie);
        if (!trackedSeries.contains(serie)) {
            trackedSeries.add(serie);
        }
        firePeakTracked(serie);
    }

    /**
     * untrack the given serie
     * 
     * @param serie
     *            the serie to track
     */
    public void untrackSerie(SourceFunction serie) {
        if (trackedSeries.contains(serie)) {
            trackedSeries.remove(serie);
        }
        firePeakTracked(serie);
    }

    /**
     * return true if the serie is tracked, false otherwise
     * 
     * @param serie
     * @return true if the serie is tracked, false otherwise
     */
    public boolean isTracked(SourceFunction serie) {
        return trackedSeries.contains(serie);
    }

    /**
     * get a lock/unlock action
     * 
     * @return lock unlock action
     */
    public PeakTrackerLockUnlockAction getPeakTrackerLockUnlockAction() {
        return new PeakTrackerLockUnlockAction();
    }

    /**
     * translate select action
     */
    class PeakTrackerLockUnlockAction implements ActionListener {

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

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }

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

    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }
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

        getWindow2D().getDevice2D().repaintDevice();
    }

    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (windowPart != WindowPart.Device) {
            return;
        }

        for (SourceFunction trackedSerie : trackedSeries) {
            if (trackedSerie == null) {
                return;
            }

            int indexMax = 0;
            int indexMin = 0;
            double max = trackedSerie.getSource().get(0).getY();
            double min = trackedSerie.getSource().get(0).getY();
            for (int i = 0; i < trackedSerie.getSource().size(); i++) {

                Point2D p = trackedSerie.getSource().get(i);

                if (p.getY() > max) {
                    indexMax = i;
                    max = p.getY();
                }
                if (p.getY() < min) {
                    indexMin = i;
                    min = p.getY();
                }
            }

            Point2D p2dUserMax = trackedSerie.getSource().get(indexMax);
            Point2D p2dUserMin = trackedSerie.getSource().get(indexMin);

            Point2D p2dDeviceMax = getWindow2D().userToPixel(p2dUserMax);
            Point2D p2dDeviceMin = getWindow2D().userToPixel(p2dUserMin);

            Rectangle2D rect2DMax = new Rectangle2D.Double(
                                                           p2dDeviceMax.getX() - trackFrameWidth / 2,
                                                           p2dDeviceMax.getY() - trackFrameHeight / 2, trackFrameWidth,
                                                           trackFrameHeight);
            Rectangle2D rect2DMin = new Rectangle2D.Double(
                                                           p2dDeviceMin.getX() - trackFrameWidth / 2,
                                                           p2dDeviceMin.getY() - trackFrameHeight / 2, trackFrameWidth,
                                                           trackFrameHeight);

            g2d.setColor(Color.WHITE);
            g2d.draw(rect2DMax);
            g2d.draw(rect2DMin);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                        0.3f));
            g2d.setColor(NanoChromatique.RED);
            g2d.fill(rect2DMax);
            g2d.setColor(NanoChromatique.BLUE);
            g2d.fill(rect2DMin);
            g2d.setComposite(AlphaComposite
                    .getInstance(AlphaComposite.SRC_OVER, 1f));

            // g2d.setFont(new Font("Tahoma", Font.PLAIN, 10));
            //
            // String messageMax = "PEAK MAX :" + p2dUserMax.getY();
            // String messageMin = "PEAK MIN :" + p2dUserMin.getY();
            //
            // g2d.setColor(Color.RED);
            // g2d.drawString(messageMax,
            // getWindow2D().getDevice2D().getDeviceWidth() - 100, 30);
            // g2d.drawString(messageMin,
            // getWindow2D().getDevice2D().getDeviceWidth() - 100, 40);
        }

    }

}
