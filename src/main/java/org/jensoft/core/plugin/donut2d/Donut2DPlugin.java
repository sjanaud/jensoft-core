/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.graphics.Antialiasing;
import org.jensoft.core.graphics.Dithering;
import org.jensoft.core.graphics.TextAntialiasing;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.plugin.donut2d.painter.label.AbstractDonut2DSliceLabel;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>Donut2DPlugin</code>
 * 
 * @author sebastien janaud
 */
public class Donut2DPlugin extends AbstractPlugin implements
        AbstractPlugin.OnClickListener, AbstractPlugin.OnEnterListener,
        AbstractPlugin.OnExitListener, AbstractPlugin.OnPressListener,
        AbstractPlugin.OnReleaseListener, AbstractPlugin.OnMoveListener,
        AbstractPlugin.OnDragListener {

    /** donut registry */
    private List<Donut2D> donuts;

    /**
     * create a new Donut2DPlugin
     */
    public Donut2DPlugin() {
        setName("Donut2DPlugin");
       
        setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        setDithering(Dithering.On);
       
        
        setPriority(100);
        donuts = new ArrayList<Donut2D>();
        setOnMoveListener(this);
        setOnClickListener(this);
        setOnReleaseListener(this);
        setOnPressListener(this);
        setOnDragListener(this);
    }

    /**
     * add donut2D
     * 
     * @param donut
     *            the donut to add
     */
    public void addDonut(Donut2D donut) {
        donut.setHostPlugin(this);
        donuts.add(donut);
    }

    /**
     * remove donut
     * 
     * @param donut
     *            the donut to remove
     */
    public void removeDonut(Donut2D donut) {
        donut.setHostPlugin(null);
        donuts.remove(donut);
    }

    /**
     * get donuts
     * 
     * @return donuts
     */
    public List<Donut2D> getDonuts() {
        return donuts;
    }

    /**
     * solve plugin geometry
     */
    private void solvePluginGeometry() {
        for (int i = 0; i < donuts.size(); i++) {
            Donut2D d = donuts.get(i);
            d.solveGeometry();
        }
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {

        if (viewPart != ViewPart.Device) {
            return;
        }

        solvePluginGeometry();

        for (int i = 0; i < donuts.size(); i++) {
            Donut2D donut2D = donuts.get(i);
           
            if (donut2D.getDonut2DFill() != null) {
                donut2D.getDonut2DFill().paintDonut2D(g2d, donut2D);
            }
            if (donut2D.getDonut2DDraw() != null) {
                donut2D.getDonut2DDraw().paintDonut2D(g2d, donut2D);
            }
            if (donut2D.getDonut2DEffect() != null) {
                donut2D.getDonut2DEffect().paintDonut2D(g2d, donut2D);
            }

            List<Donut2DSlice> slices = donut2D.getSlices();
            for (Donut2DSlice donut2DSlice : slices) {
                for (AbstractDonut2DSliceLabel label : donut2DSlice.getSliceLabels()) {
                    label.paintDonut2DSlice(g2d, donut2D, donut2DSlice);
                }
            }
        }

    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        for (Donut2D donut2d : donuts) {
            for (Donut2DSlice slice : donut2d.getSlices()) {
                if (slice.getSlicePath() != null
                        && slice.getSlicePath().contains(me.getX(),
                                                         me.getY()) && slice.isLockEnter()) {
                    fireDonut2DReleased(slice);
                }
            }
        }
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {
        for (Donut2D donut2d : donuts) {
            // List<Donut3DSlice> slices = donut3d.getPaintOrder();
            // Collections.reverse(slices);
            for (Donut2DSlice slice : donut2d.getSlices()) {
                if (slice.getSlicePath() != null
                        && slice.getSlicePath().contains(me.getX(),
                                                         me.getY()) && slice.isLockEnter()) {
                    fireDonut2DPressed(slice);
                    return;
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnExitListener#onExit(java.awt.event.MouseEvent)
     */
    @Override
    public void onExit(MouseEvent me) {
    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnEnterListener#onEnter(java.awt.event.MouseEvent)
     */
    @Override
    public void onEnter(MouseEvent me) {
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnClickListener#onClick(java.awt.event.MouseEvent)
     */
    @Override
    public void onClick(MouseEvent me) {
        for (Donut2D donut2d : donuts) {
            // List<Donut3DSlice> slices = donut3d.getPaintOrder();
            // Collections.reverse(slices);

            for (Donut2DSlice slice : donut2d.getSlices()) {
                if (slice.getSlicePath() != null
                        && slice.getSlicePath().contains(me.getX(),
                                                         me.getY()) && slice.isLockEnter()) {
                    fireDonut2DClicked(slice);
                }
            }
        }
    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {
        for (Donut2D donut2d : donuts) {
            for (Donut2DSlice slice : donut2d.getSlices()) {
                donut2DEnterExitTracker(slice, me.getX(), me.getY());
            }
        }
    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        for (Donut2D donut2d : donuts) {
            for (Donut2DSlice slice : donut2d.getSlices()) {
                donut2DEnterExitTracker(slice, me.getX(), me.getY());
            }
        }
    }

    /**
     * track donut2D enter or exit for the specified donut3d slice
     * 
     * @param slice
     *            the slice to track
     * @param x
     *            the x in device coordinate
     * @param y
     *            the y in device coordinate
     */
    private void donut2DEnterExitTracker(Donut2DSlice slice, int x, int y) {

        if (slice == null || slice.getSlicePath() == null) {
            return;
        }

        if (slice.getSlicePath().contains(x, y) && !slice.isLockEnter()) {
            slice.lockEnter();
            fireDonut2DEntered(slice);
        }
        else if (!slice.getSlicePath().contains(x, y)
                && slice.isLockEnter()) {
            slice.unlockEnter();
            fireDonut2DExited(slice);
        }
    }

    /**
     * fire donut2d entered
     * 
     * @param slice
     *            the donut2d slice entered to fire
     */
    public void fireDonut2DEntered(Donut2DSlice slice) {
        Object[] listeners = slice.getHost().getDonut2DListenerList().getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == Donut2DListener.class) {
                    ((Donut2DListener) listeners[i + 1])
                            .donut2DSliceEntered(new Donut2DEvent(slice));
                }
            }
        }
    }

    /**
     * fire donut2d exited
     * 
     * @param slice
     *            the donut2d slice exited to fire
     */
    public void fireDonut2DExited(Donut2DSlice slice) {
        Object[] listeners = slice.getHost().getDonut2DListenerList().getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == Donut2DListener.class) {
                    ((Donut2DListener) listeners[i + 1])
                            .donut2DSliceExited(new Donut2DEvent(slice));
                }
            }
        }
    }

    /**
     * fire donut2d clicked
     * 
     * @param slice
     *            the donut2d slice clicked to fire
     */
    public void fireDonut2DClicked(Donut2DSlice slice) {
        Object[] listeners = slice.getHost().getDonut2DListenerList().getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == Donut2DListener.class) {
                    ((Donut2DListener) listeners[i + 1])
                            .donut2DSliceClicked(new Donut2DEvent(slice));
                }
            }
        }
    }

    /**
     * fire donut2d pressed
     * 
     * @param slice
     *            the donut2D slice pressed to fire
     */
    public void fireDonut2DPressed(Donut2DSlice slice) {
        Object[] listeners = slice.getHost().getDonut2DListenerList().getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == Donut2DListener.class) {
                    ((Donut2DListener) listeners[i + 1])
                            .donut2DSlicePressed(new Donut2DEvent(slice));
                }
            }
        }
    }

    /**
     * fire donut2D released
     * 
     * @param slice
     *            the donut2D slice released to fire
     */
    public void fireDonut2DReleased(Donut2DSlice slice) {
        Object[] listeners = slice.getHost().getDonut2DListenerList().getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == Donut2DListener.class) {
                    ((Donut2DListener) listeners[i + 1])
                            .donut2DSliceReleased(new Donut2DEvent(slice));
                }
            }
        }
    }

}
