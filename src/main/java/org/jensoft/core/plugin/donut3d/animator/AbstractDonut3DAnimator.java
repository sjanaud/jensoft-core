/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut3d.animator;

import org.jensoft.core.plugin.donut3d.Donut3DEvent;
import org.jensoft.core.plugin.donut3d.Donut3DListener;
import org.jensoft.core.plugin.donut3d.Donut3DSlice;
import org.jensoft.core.view.View;

/**
 * <code>AbstractDonut3DAnimator</code> Abstract base definition to animate donut3D
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractDonut3DAnimator implements Donut3DListener {

    /**
     * create animator
     */
    public AbstractDonut3DAnimator() {
    }

    /**
     * call when the mouse enter in the specified slice
     * 
     * @param slice
     */
    protected void onEntered(Donut3DSlice slice) {
    }

    /**
     * call when the mouse exited of the specified slice
     * 
     * @param slice
     */

    protected void onExited(Donut3DSlice slice) {
    }

    /**
     * call when the mouse clicked (pressed and released) in the specified
     * slice
     * 
     * @param slice
     */
    protected void onClicked(Donut3DSlice slice) {
    }

    /**
     * call when the mouse pressed in the specified slice
     * 
     * @param slice
     */
    protected void onPressed(Donut3DSlice slice) {
    }

    /**
     * call when the mouse pressed in the specified slice
     * 
     * @param slice
     */
    protected void onReleased(Donut3DSlice slice) {
    }

    /**
     * <p>
     * helper method to create animator.
     * </p>
     * <p>
     * imagine that you have an animator that make something on slice or donut, you can return this animator with the
     * slice argument if you want animate slice or null if the animator is for all donut. it's just an helper method
     * </p>
     * 
     * @param slice
     *            the slice
     * @return runnable animator
     */
    protected abstract Runnable getAnimator(Donut3DSlice slice);


    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut3d.Donut3DListener#donut3DSliceClicked(org.jensoft.core.plugin.donut3d.Donut3DEvent)
     */
    @Override
    public final void donut3DSliceClicked(Donut3DEvent e) {
        Donut3DSlice slice = e.getDonut3DSlice();
        onClicked(slice);
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut3d.Donut3DListener#donut3DSlicePressed(org.jensoft.core.plugin.donut3d.Donut3DEvent)
     */
    @Override
    public final void donut3DSlicePressed(Donut3DEvent e) {
        Donut3DSlice slice = e.getDonut3DSlice();
        onPressed(slice);
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut3d.Donut3DListener#donut3DSliceReleased(org.jensoft.core.plugin.donut3d.Donut3DEvent)
     */
    @Override
    public final void donut3DSliceReleased(Donut3DEvent e) {
        Donut3DSlice slice = e.getDonut3DSlice();
        onReleased(slice);
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut3d.Donut3DListener#donut3DSliceEntered(org.jensoft.core.plugin.donut3d.Donut3DEvent)
     */
    @Override
    public final void donut3DSliceEntered(Donut3DEvent e) {
        Donut3DSlice slice = e.getDonut3DSlice();
        onEntered(slice);
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut3d.Donut3DListener#donut3DSliceExited(org.jensoft.core.plugin.donut3d.Donut3DEvent)
     */
    @Override
    public final void donut3DSliceExited(Donut3DEvent e) {
        Donut3DSlice slice = e.getDonut3DSlice();
        onExited(slice);
    }

    /**
     * get view that host this slice
     * 
     * @param slice
     * @return host view
     */
    public View getView(Donut3DSlice slice) {
        return slice.getHost().getHostPlugin().getProjection().getView();
    }

}
