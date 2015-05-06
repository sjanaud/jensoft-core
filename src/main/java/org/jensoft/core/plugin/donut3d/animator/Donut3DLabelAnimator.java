/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut3d.animator;

import org.jensoft.core.plugin.donut3d.Donut3DSlice;
import org.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel;

/**
 * <p>
 * Animator that display label on section with different behavior.
 * </p>
 * <p>
 * with {@link ShowLabelBehavior#ShowOnSlicePressed} the label appear on first click and disappear on second click and
 * with {@link ShowLabelBehavior#ShowOnSliceRollover} the label is show when slice is roll over (appear on enter,
 * disappear on exit)
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class Donut3DLabelAnimator extends AbstractDonut3DAnimator {

    /** donut slice */
    private Donut3DSlice slice;

    /** the slice label to set on event */
    private AbstractDonut3DSliceLabel sliceLabel;

    /** label behavior */
    private ShowLabelBehavior showLabelBehavior = ShowLabelBehavior.ShowOnSliceRollover;

    /**
     * Label behavior
     * 
     * @author Sebastien Janaud
     */
    public enum ShowLabelBehavior {
        ShowOnSlicePressed,
        ShowOnSliceRollover
    }

    /**
     * create a label donut animator
     * 
     * @param slice
     *            the slice on which the label should be added on event behavior
     * @param sliceLabel
     *            the label to add when behavior event occurs
     */
    public Donut3DLabelAnimator(Donut3DSlice slice, AbstractDonut3DSliceLabel sliceLabel) {
        this.slice = slice;
        this.sliceLabel = sliceLabel;
    }

    /**
     * @return the slice
     */
    public Donut3DSlice getSlice() {
        return slice;
    }

    /**
     * @param slice
     *            the slice to set
     */
    public void setSlice(Donut3DSlice slice) {
        this.slice = slice;
    }

    /**
     * @return the sliceLabel
     */
    public AbstractDonut3DSliceLabel getSliceLabel() {
        return sliceLabel;
    }

    /**
     * @param sliceLabel
     *            the sliceLabel to set
     */
    public void setSliceLabel(AbstractDonut3DSliceLabel sliceLabel) {
        this.sliceLabel = sliceLabel;
    }

    /**
     * @return the showLabelBehavior
     */
    public ShowLabelBehavior getShowLabelBehavior() {
        return showLabelBehavior;
    }

    /**
     * @param showLabelBehavior
     *            the showLabelBehavior to set
     */
    public void setShowLabelBehavior(ShowLabelBehavior showLabelBehavior) {
        this.showLabelBehavior = showLabelBehavior;
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut3d.animator.AbstractDonut3DAnimator#onPressed(com.jensoft.core.plugin.donut3d.Donut3DSlice)
     */
    @Override
    protected void onPressed(Donut3DSlice slice) {
        if (showLabelBehavior == ShowLabelBehavior.ShowOnSlicePressed && slice.equals(this.slice)) {
            if (slice.equals(this.slice)) {
                Thread t = new Thread(getAnimator(slice));
                t.start();
            }
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut3d.animator.AbstractDonut3DAnimator#onEntered(com.jensoft.core.plugin.donut3d.Donut3DSlice)
     */
    @Override
    protected void onEntered(Donut3DSlice slice) {
        if (showLabelBehavior == ShowLabelBehavior.ShowOnSliceRollover && slice.equals(this.slice)) {
            if (slice.equals(this.slice)) {
                Thread t = new Thread(getAnimator(slice));
                t.start();
            }
        }
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut3d.animator.AbstractDonut3DAnimator#onExited(com.jensoft.core.plugin.donut3d.Donut3DSlice)
     */
    @Override
    protected void onExited(Donut3DSlice slice) {
        if (showLabelBehavior == ShowLabelBehavior.ShowOnSliceRollover && slice.equals(this.slice)) {

            if (slice.equals(this.slice)) {
                Thread t = new Thread(getAnimator(slice));
                t.start();
            }
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut3d.animator.AbstractDonut3DAnimator#getAnimator(com.jensoft.core.plugin.donut3d.Donut3DSlice)
     */
    @Override
    public Runnable getAnimator(Donut3DSlice pieSection) {
        return new LabelRunner(pieSection);
    }

    /***
     * Alpha runner to run transition between two state of transition
     */
    public class LabelRunner implements Runnable {

        private Donut3DSlice animateSlice;

        public LabelRunner(Donut3DSlice pieSection) {
            animateSlice = pieSection;
        }

        @Override
        public void run() {
            if (!animateSlice.containsSliceLabel(sliceLabel)) {
                animateSlice.addSliceLabel(sliceLabel);
            }
            else {
                animateSlice.removeSliceLabel(sliceLabel);
            }

            getView(animateSlice).repaintDevice();
        }
    }

}
