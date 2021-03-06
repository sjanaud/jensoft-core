/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.tools.peaktracker;

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

import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.plugin.function.source.FunctionNature;
import org.jensoft.core.plugin.function.source.SourceFunction;
import org.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerEvent;
import org.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerListener;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

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

    /** registered sources function */
    private List<SourceFunction> sources;

    /** registered sources */
    private List<SourceFunction> trackedSources;

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
        sources = new ArrayList<SourceFunction>();
        trackedSources = new ArrayList<SourceFunction>();
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
     * fire source being tracked started
     */
    public void firePeakTracked(SourceFunction source) {
        Object[] listeners = trackerListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PeakTrackerListener.class) {
                    ((PeakTrackerListener) listeners[i + 1])
                            .peakTracked(new PeakTrackerEvent(source));
                }
            }
        }
    }

    /**
     * fire source registered
     * 
     * @param source
     *            the source that has just been registered
     */
    public void fireSourceRegistered(SourceFunction source) {
        Object[] listeners = trackerListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == SourceTrackerListener.class) {
                    ((SourceTrackerListener) listeners[i + 1])
                            .sourceRegistered(new SourceTrackerEvent(source));
                }
            }
        }
    }

    /**
     * @return the sources functions
     */
    public List<SourceFunction> getSources() {
        return sources;
    }

    /**
     * @param sources
     *            the sources to set
     */
    public void setSources(List<SourceFunction> sources) {
        this.sources = sources;
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
     * register source in this tracker
     * 
     * @param source
     *            the source to register
     */
    public void registerSource(SourceFunction source) {
        if (!sources.contains(source)) {
            sources.add(source);
            fireSourceRegistered(source);
        }
    }

    /**
     * track the given source, register source if it does not
     * have been registered.
     * 
     * @param source
     *            the source to track
     */
    public void trackSource(SourceFunction source) {
        registerSource(source);
        if (!trackedSources.contains(source)) {
            trackedSources.add(source);
        }
        firePeakTracked(source);
    }

    /**
     * untrack the given source
     * 
     * @param source
     *            the source to track
     */
    public void untrackSource(SourceFunction source) {
        if (trackedSources.contains(source)) {
            trackedSources.remove(source);
        }
        firePeakTracked(source);
    }

    /**
     * return true if the source is tracked, false otherwise
     * 
     * @param source
     * @return true if the source is tracked, false otherwise
     */
    public boolean isTracked(SourceFunction source) {
        return trackedSources.contains(source);
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

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }

    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }

    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }
        getProjection().getDevice2D().repaintDevice();
    }
    
    

    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {

        if (viewPart != ViewPart.Device) {
            return;
        }

        for (SourceFunction trackedSource : trackedSources) {
            if (trackedSource == null) {
                return;
            }
            if (trackedSource.getCurrentFunction() == null) {
                return;
            }
            if (trackedSource.getCurrentFunction().size() == 0) {
                return;
            }
            

            int indexMax = 0;
            int indexMin = 0;
            if(trackedSource.getNature() == FunctionNature.XFunction) {
            	double max = trackedSource.getCurrentFunction().get(0).getY();
                double min = trackedSource.getCurrentFunction().get(0).getY();
                for (int i = 0; i < trackedSource.getCurrentFunction().size(); i++) {

                    Point2D p = trackedSource.getCurrentFunction().get(i);

                    if (p.getY() > max) {
                        indexMax = i;
                        max = p.getY();
                    }
                    if (p.getY() < min) {
                        indexMin = i;
                        min = p.getY();
                    }
                }
            }
            else{
            	double max = trackedSource.getCurrentFunction().get(0).getX();
                double min = trackedSource.getCurrentFunction().get(0).getX();
                for (int i = 0; i < trackedSource.getCurrentFunction().size(); i++) {

                    Point2D p = trackedSource.getCurrentFunction().get(i);

                    if (p.getX() > max) {
                        indexMax = i;
                        max = p.getX();
                    }
                    if (p.getX() < min) {
                        indexMin = i;
                        min = p.getX();
                    }
                }
            }
            
            Point2D p2dUserMax = trackedSource.getCurrentFunction().get(indexMax);
            Point2D p2dUserMin = trackedSource.getCurrentFunction().get(indexMin);

            Point2D p2dDeviceMax = getProjection().userToPixel(p2dUserMax);
            Point2D p2dDeviceMin = getProjection().userToPixel(p2dUserMin);

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

        }

    }

}
