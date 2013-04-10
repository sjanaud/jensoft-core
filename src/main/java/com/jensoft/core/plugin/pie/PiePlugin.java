/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.graphics.AlphaInterpolation;
import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.Fractional;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>PiePlugin</code>
 * <br>
 * <img src="doc-files/pie-radial-label.png"> <br>
 * <br>
 * <img src="doc-files/pie-border-label.png"> <br>
 * 
 * @author Sebastien Janaud
 */
public class PiePlugin extends AbstractPlugin implements
        AbstractPlugin.OnClickListener, AbstractPlugin.OnEnterListener,
        AbstractPlugin.OnExitListener, AbstractPlugin.OnPressListener,
        AbstractPlugin.OnReleaseListener, AbstractPlugin.OnMoveListener,
        AbstractPlugin.OnDragListener {

    /** pie registry */
    private List<Pie> pies;

    /**
     * create pie plugin
     */
    public PiePlugin() {
        setName("PiePlugin");
        pies = new ArrayList<Pie>();

        setOnMoveListener(this);
        setOnClickListener(this);
        setOnReleaseListener(this);
        setOnPressListener(this);
        setOnDragListener(this);

        setTextAntialising(TextAntialiasing.On);
        setAntialiasing(Antialiasing.On);
        setFractionalMetrics(Fractional.On);
        setDithering(Dithering.On);
        setAlphaInterpolation(AlphaInterpolation.Quality);
        setPriority(100);
       
    }

    /**
     * add pie
     * 
     * @param pie
     *            the pie to add
     */
    public void addPie(Pie pie) {
        pie.setHostPlugin(this);
        pies.add(pie);
    }

    /**
     * @return the pies
     */
    public List<Pie> getPies() {
        return pies;
    }

    /**
     * @param pies
     *            the pies to set
     */
    public void setPies(List<Pie> pies) {
        this.pies = pies;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
     */
    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (windowPart != WindowPart.Device) {
            return;
        }

        // be careful slice can be paint twice !
        for (Pie pie : pies) {

            pie.build();
            // pie
            paintPie(v2d, g2d, pie);
            // slice
            paintSlices(v2d, g2d, pie);

        }

    }

    /**
     * paint the pie
     * 
     * @param v2d
     *            the view2D
     * @param g2d
     *            the graphics context
     * @param pie
     *            the pie to paint
     */
    private void paintPie(View2D v2d, Graphics2D g2d, Pie pie) {
        if (pie.getPieFill() != null) {
            pie.getPieFill().paintPie(g2d, pie);
        }
        if (pie.getPieDraw() != null) {
            pie.getPieDraw().paintPie(g2d, pie);
        }
        if (pie.getPieEffect() != null) {
            pie.getPieEffect().paintPie(g2d, pie);
        }
    }

    /**
     * paint slices of specified pie
     * 
     * @param v2d
     *            the view2D
     * @param g2d
     *            the graphics context
     * @param pie
     *            the pie to paint
     */
    private void paintSlices(View2D v2d, Graphics2D g2d, Pie pie) {
        List<PieSlice> slices = pie.getSlices();
        for (PieSlice slice : slices) {
            if (slice.getSliceFill() != null) {
                slice.getSliceFill().paintPieSlice(g2d, pie, slice);
            }
            if (slice.getSliceDraw() != null) {
                slice.getSliceDraw().paintPieSlice(g2d, pie, slice);
            }
            if (slice.getSliceEffect() != null) {
                slice.getSliceEffect().paintPieSlice(g2d, pie, slice);
            }

            for (AbstractPieSliceLabel label : slice.getSliceLabels()) {
                label.paintPieSlice(g2d, pie, slice);
            }

        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        for (Pie pie : pies) {
            for (PieSlice slice : pie.getSlices()) {
                if (slice.getSlicePath() != null
                        && slice.getSlicePath().contains(me.getX(), me.getY())
                        && slice.isLockEnter()) {
                    firePieReleased(slice);
                }
            }
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {
        for (Pie pie : pies) {
            for (PieSlice slice : pie.getSlices()) {
                if (slice.getSlicePath() != null
                        && slice.getSlicePath().contains(me.getX(), me.getY())
                        && slice.isLockEnter()) {
                    firePiePressed(slice);
                }
            }
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnExitListener#onExit(java.awt.event.MouseEvent)
     */
    @Override
    public void onExit(MouseEvent me) {
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnEnterListener#onEnter(java.awt.event.MouseEvent)
     */
    @Override
    public void onEnter(MouseEvent me) {
    }


    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnClickListener#onClick(java.awt.event.MouseEvent)
     */
    @Override
    public void onClick(MouseEvent me) {
        for (Pie pie : pies) {
            for (PieSlice slice : pie.getSlices()) {
                if (slice.getSlicePath() != null
                        && slice.getSlicePath().contains(me.getX(), me.getY())
                        && slice.isLockEnter()) {
                    firePieClicked(slice);
                }
            }
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {
        for (Pie pie : pies) {
            for (PieSlice slice : pie.getSlices()) {
                pieEnterExitTracker(slice, me.getX(), me.getY());
            }
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        for (Pie pie : pies) {
            for (PieSlice slice : pie.getSlices()) {
                pieEnterExitTracker(slice, me.getX(), me.getY());
            }
        }
    }

    /**
     * track pie enter or exit for the specified pie slice
     * 
     * @param slice
     *            the slice to track
     * @param x
     *            the x in device coordinate
     * @param y
     *            the y in device coordinate
     */
    private void pieEnterExitTracker(PieSlice slice, int x, int y) {

        if (slice == null || slice.getSlicePath() == null) {
            return;
        }

        if (slice.getSlicePath().contains(x, y) && !slice.isLockEnter()) {
            slice.lockEnter();
            firePieEntered(slice);
        }
        else if (!slice.getSlicePath().contains(x, y) && slice.isLockEnter()) {
            slice.unlockEnter();
            firePieExited(slice);
        }
    }

    /**
     * fire pie entered
     * 
     * @param pie
     *            the pie slice entered to fire
     */
    public void firePieEntered(PieSlice slice) {
        Object[] listeners = slice.getHost().getPieListenerList().getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PieListener.class) {
                    ((PieListener) listeners[i + 1])
                            .pieSliceEntered(new PieEvent(slice));
                }
            }
        }
    }

    /**
     * fire pie exited
     * 
     * @param slice
     *            the pie slice exited to fire
     */
    public void firePieExited(PieSlice slice) {
        Object[] listeners = slice.getHost().getPieListenerList().getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PieListener.class) {
                    ((PieListener) listeners[i + 1])
                            .pieSliceExited(new PieEvent(slice));
                }
            }
        }
    }

    /**
     * fire pie clicked
     * 
     * @param slice
     *            the pie slice clicked to fire
     */
    public void firePieClicked(PieSlice slice) {
        Object[] listeners = slice.getHost().getPieListenerList().getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PieListener.class) {
                    ((PieListener) listeners[i + 1])
                            .pieSliceClicked(new PieEvent(slice));
                }
            }
        }
    }

    /**
     * fire pie pressed
     * 
     * @param slice
     *            the pie slice pressed to fire
     */
    public void firePiePressed(PieSlice slice) {
        Object[] listeners = slice.getHost().getPieListenerList().getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PieListener.class) {
                    ((PieListener) listeners[i + 1])
                            .pieSlicePressed(new PieEvent(slice));
                }
            }
        }
    }

    /**
     * fire pie released
     * 
     * @param slice
     *            the pie slice released to fire
     */
    public void firePieReleased(PieSlice slice) {
        Object[] listeners = slice.getHost().getPieListenerList().getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PieListener.class) {
                    ((PieListener) listeners[i + 1])
                            .pieSliceReleased(new PieEvent(slice));
                }
            }
        }
    }

}
