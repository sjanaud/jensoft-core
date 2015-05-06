/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut3d;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.jensoft.core.graphics.Antialiasing;
import org.jensoft.core.graphics.Dithering;
import org.jensoft.core.graphics.TextAntialiasing;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.plugin.donut3d.animator.AbstractDonut3DAnimator;
import org.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel;
import org.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel;
import org.jensoft.core.plugin.donut3d.painter.label.Donut3DRadialLabel;
import org.jensoft.core.plugin.donut3d.painter.paint.Donut3DDefaultPaint;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>Donut3DPlugin</code>
 * <p>
 * Donut3D Plugin takes the responsibility to manage Donut3D related objects.
 * </p>
 * <br>
 * <img src="doc-files/Donut3D_1.png">
 * <img src="doc-files/Donut3D_2.png"> <br>
 * 
 * @see Donut3D
 * @see Donut3DBorderLabel
 * @see Donut3DRadialLabel
 * @see Donut3DToolkit
 * @see Donut3DDefaultPaint
 * @author Sebastien Janaud
 */
public class Donut3DPlugin extends AbstractPlugin implements
        AbstractPlugin.OnClickListener, AbstractPlugin.OnEnterListener,
        AbstractPlugin.OnExitListener, AbstractPlugin.OnPressListener,
        AbstractPlugin.OnReleaseListener, AbstractPlugin.OnMoveListener,
        AbstractPlugin.OnDragListener {

    /** donut 3D registry */
    private List<Donut3D> donuts3D = new ArrayList<Donut3D>();

    /**
	 * @return the donuts3D
	 */
	public List<Donut3D> getDonuts3D() {
		return donuts3D;
	}

	/** listeners */
    private EventListenerList donut3DListenerList;

    /**
     * Create new donut3D plugin
     */
    public Donut3DPlugin() {
        setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        setDithering(Dithering.On);
        donut3DListenerList = new EventListenerList();

        setOnMoveListener(this);
        setOnClickListener(this);
        setOnReleaseListener(this);
        setOnPressListener(this);
        setOnDragListener(this);

    }

    /**
     * add donut3D listener
     * 
     * @param listener
     *            the donut3D listener to add
     */
    public void addDonut3DListener(Donut3DListener listener) {
        donut3DListenerList.add(Donut3DListener.class, listener);
    }

    /**
     * remove donut3D listener
     * 
     * @param listener
     *            the donut3D listener to remove
     */
    public void removeDonut3DListener(Donut3DListener listener) {
        donut3DListenerList.remove(Donut3DListener.class, listener);
    }

    /**
     * add donut3D
     * 
     * @param donut3D
     *            the donut3D to add
     */
    public void addDonut(Donut3D donut3D) {
        donuts3D.add(donut3D);
    }

    /**
     * add donut3D animator
     * 
     * @param animator
     *            the donut3D animator to add
     */
    public void addDonutAnimator(AbstractDonut3DAnimator animator) {
        addDonut3DListener(animator);
    }

    /**
     * solve donut 3D geometry
     */
    private void solveDonut3D() {
        for (Donut3D donut3D : donuts3D) {
            donut3D.setHostPlugin(this);
            donut3D.solveDonut3D();
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }
        solveDonut3D();
        for (Donut3D donut3D : donuts3D) {
            if (donut3D.getDonut3DPaint() != null) {
                donut3D.getDonut3DPaint().paintDonut3D(g2d, donut3D);
            }
            List<Donut3DSlice> slices = donut3D.getSlices();
            for (Donut3DSlice donut3DSlice : slices) {
                for (AbstractDonut3DSliceLabel label : donut3DSlice.getSliceLabels()) {
                    label.paintDonut3DSlice(g2d, donut3D, donut3DSlice);
                }
            }
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        for (Donut3D donut3d : donuts3D) {
            for (Donut3DSlice slice : donut3d.getSlices()) {
                if (slice.getSensibleArea() != null
                        && slice.getSensibleArea().contains(me.getX(),
                                                            me.getY()) && slice.isLockEnter()) {
                    fireDonut3DReleased(slice);
                }
            }
        }
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {
        for (Donut3D donut3d : donuts3D) {
            List<Donut3DSlice> slices = donut3d.getPaintOrder();
            Collections.reverse(slices);
            for (Donut3DSlice slice : slices) {
                if (slice.getSensibleArea() != null
                        && slice.getSensibleArea().contains(me.getX(),
                                                            me.getY()) && slice.isLockEnter()) {
                    fireDonut3DPressed(slice);
                    return;
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
        for (Donut3D donut3d : donuts3D) {
            List<Donut3DSlice> slices = donut3d.getPaintOrder();
            Collections.reverse(slices);
            for (Donut3DSlice slice : slices) {
                if (slice.getSensibleArea() != null
                        && slice.getSensibleArea().contains(me.getX(),
                                                            me.getY()) && slice.isLockEnter()) {
                    fireDonut3DClicked(slice);
                }
            }
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {
        for (Donut3D donut3d : donuts3D) {
            for (Donut3DSlice slice : donut3d.getSlices()) {
                donut3DEnterExitTracker(slice, me.getX(), me.getY());
            }
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        for (Donut3D donut3d : donuts3D) {
            for (Donut3DSlice slice : donut3d.getSlices()) {
                donut3DEnterExitTracker(slice, me.getX(), me.getY());
            }
        }
    }

    /**
     * track donut3D enter or exit for the specified donut3d slice
     * 
     * @param slice
     *            the slice to track
     * @param x
     *            the x in device coordinate
     * @param y
     *            the y in device coordinate
     */
    private void donut3DEnterExitTracker(Donut3DSlice slice, int x, int y) {

        if (slice == null || slice.getSensibleArea() == null) {
            return;
        }

        if (slice.getSensibleArea().contains(x, y) && !slice.isLockEnter()) {
            slice.lockEnter();
            fireDonut3DEntered(slice);
        }
        else if (!slice.getSensibleArea().contains(x, y)
                && slice.isLockEnter()) {
            slice.unlockEnter();
            fireDonut3DExited(slice);
        }
    }

    /**
     * fire donut3d entered
     * 
     * @param slice
     *            the donut3d slice entered to fire
     */
    public void fireDonut3DEntered(Donut3DSlice slice) {
        Object[] listeners = donut3DListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == Donut3DListener.class) {
                    ((Donut3DListener) listeners[i + 1])
                            .donut3DSliceEntered(new Donut3DEvent(slice));
                }
            }
        }
    }

    /**
     * fire donut3d exited
     * 
     * @param slice
     *            the donut3d slice exited to fire
     */
    public void fireDonut3DExited(Donut3DSlice slice) {
        Object[] listeners = donut3DListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == Donut3DListener.class) {
                    ((Donut3DListener) listeners[i + 1])
                            .donut3DSliceExited(new Donut3DEvent(slice));
                }
            }
        }
    }

    /**
     * fire donut3d clicked
     * 
     * @param slice
     *            the donut3d slice clicked to fire
     */
    public void fireDonut3DClicked(Donut3DSlice slice) {
        Object[] listeners = donut3DListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == Donut3DListener.class) {
                    ((Donut3DListener) listeners[i + 1])
                            .donut3DSliceClicked(new Donut3DEvent(slice));
                }
            }
        }
    }

    /**
     * fire donut3d pressed
     * 
     * @param slice
     *            the donut3D slice pressed to fire
     */
    public void fireDonut3DPressed(Donut3DSlice slice) {
        Object[] listeners = donut3DListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == Donut3DListener.class) {
                    ((Donut3DListener) listeners[i + 1])
                            .donut3DSlicePressed(new Donut3DEvent(slice));
                }
            }
        }
    }

    /**
     * fire donut3D released
     * 
     * @param slice
     *            the donut3D slice released to fire
     */
    public void fireDonut3DReleased(Donut3DSlice slice) {
        Object[] listeners = donut3DListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == Donut3DListener.class) {
                    ((Donut3DListener) listeners[i + 1])
                            .donut3DSliceReleased(new Donut3DEvent(slice));
                }
            }
        }
    }

}
