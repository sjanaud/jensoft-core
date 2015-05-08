/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.animator;

import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.PieEvent;
import org.jensoft.core.plugin.pie.PieListener;
import org.jensoft.core.plugin.pie.PiePlugin;
import org.jensoft.core.plugin.pie.PieSlice;
import org.jensoft.core.plugin.pie.PieToolkit;
import org.jensoft.core.view.View;

/**
 * <code>AbstractPieAnimator</code> abstract base class to animate pie
 * 
 * @see Pie
 * @see PieSlice
 * @see PiePlugin
 * @see PieToolkit
 * @author Sebastien Janaud
 */
public abstract class AbstractPieAnimator implements PieListener {

    /** the target pie for this animator */
    private Pie pie;

    /**
     * create animator
     */
    public AbstractPieAnimator() {
    }

    /**
     * @return the pie
     */
    public Pie getPie() {
        return pie;
    }

    /**
     * @param pie
     *            the pie to set
     */
    public void setPie(Pie pie) {
        this.pie = pie;
    }

    /**
     * call when the mouse enter in the specified slice
     * 
     * @param slice
     */
    protected void onEntered(PieSlice slice) {
    }

    /**
     * call when the mouse exited of the specified slice
     * 
     * @param slice
     */

    protected void onExited(PieSlice slice) {
    }

    /**
     * invoked when the mouse clicked (pressed and released) in the specified
     * slice
     * 
     * @param slice
     */
    protected void onClicked(PieSlice slice) {
    }

    /**
     * inkoked when the mouse pressed in the specified slice
     * 
     * @param slice
     */
    protected void onPressed(PieSlice slice) {
    }

    /**
     * invoked when the mouse released in the specified slice
     * 
     * @param slice
     */
    protected void onReleased(PieSlice slice) {
    }

    protected abstract Runnable getAnimator(PieSlice slice);

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.PieListener#pieSliceClicked(org.jensoft.core.plugin.pie.PieEvent)
     */
    @Override
    public final void pieSliceClicked(PieEvent e) {
        PieSlice slice = e.getSlice();
        onClicked(slice);
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.PieListener#pieSlicePressed(org.jensoft.core.plugin.pie.PieEvent)
     */
    @Override
    public final void pieSlicePressed(PieEvent e) {
        PieSlice slice = e.getSlice();
        onPressed(slice);
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.PieListener#pieSliceReleased(org.jensoft.core.plugin.pie.PieEvent)
     */
    @Override
    public final void pieSliceReleased(PieEvent e) {
        PieSlice slice = e.getSlice();
        onReleased(slice);
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.PieListener#pieSliceEntered(org.jensoft.core.plugin.pie.PieEvent)
     */
    @Override
    public final void pieSliceEntered(PieEvent e) {
        PieSlice slice = e.getSlice();
        onEntered(slice);
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.PieListener#pieSliceExited(org.jensoft.core.plugin.pie.PieEvent)
     */
    @Override
    public final void pieSliceExited(PieEvent e) {
        PieSlice slice = e.getSlice();
        onExited(slice);
    }

    /**
     * get view that host this slice
     * 
     * @param slice
     * @return host view
     */
    public View getView(PieSlice slice) {
        return slice.getHost().getHostPlugin().getProjection().getView();
    }

}
