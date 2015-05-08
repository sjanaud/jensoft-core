/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.animator;

import org.jensoft.core.plugin.donut2d.Donut2DSlice;
import org.jensoft.core.plugin.donut2d.painter.label.AbstractDonut2DSliceLabel;

/**
 * <code>Donut2DLabelAnimator</code>
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
public class Donut2DLabelAnimator extends AbstractDonut2DAnimator {

    /** donut slice */
    private Donut2DSlice slice;

    /** the slice label to set on event */
    private AbstractDonut2DSliceLabel sliceLabel;

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
    public Donut2DLabelAnimator(Donut2DSlice slice, AbstractDonut2DSliceLabel sliceLabel) {
        this.slice = slice;
        this.sliceLabel = sliceLabel;
    }

    /**
     * @return the slice
     */
    public Donut2DSlice getSlice() {
        return slice;
    }

    /**
     * @param slice
     *            the slice to set
     */
    public void setSlice(Donut2DSlice slice) {
        this.slice = slice;
    }

    /**
     * @return the sliceLabel
     */
    public AbstractDonut2DSliceLabel getSliceLabel() {
        return sliceLabel;
    }

    /**
     * @param sliceLabel
     *            the sliceLabel to set
     */
    public void setSliceLabel(AbstractDonut2DSliceLabel sliceLabel) {
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
     * @see org.jensoft.core.plugin.donut2d.animator.AbstractDonut2DAnimator#onPressed(org.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    protected void onPressed(Donut2DSlice slice) {
        if (showLabelBehavior == ShowLabelBehavior.ShowOnSlicePressed && slice.equals(this.slice)) {
            if (slice.equals(this.slice)) {
                Thread t = new Thread(getAnimator(slice));
                t.start();
            }
        }
    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.animator.AbstractDonut2DAnimator#onEntered(org.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    protected void onEntered(Donut2DSlice slice) {
        if (showLabelBehavior == ShowLabelBehavior.ShowOnSliceRollover && slice.equals(this.slice)) {
            if (slice.equals(this.slice)) {
                Thread t = new Thread(getAnimator(slice));
                t.start();
            }
        }
    }

    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.animator.AbstractDonut2DAnimator#onExited(org.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    protected void onExited(Donut2DSlice slice) {
        if (showLabelBehavior == ShowLabelBehavior.ShowOnSliceRollover && slice.equals(this.slice)) {

            if (slice.equals(this.slice)) {
                Thread t = new Thread(getAnimator(slice));
                t.start();
            }
        }
    }

 
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.animator.AbstractDonut2DAnimator#getAnimator(org.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    public Runnable getAnimator(Donut2DSlice pieSection) {
        return new LabelRunner(pieSection);
    }

    /***
     * Alpha runner to run transition between two state of transition
     */
    public class LabelRunner implements Runnable {

        private Donut2DSlice animateSlice;

        public LabelRunner(Donut2DSlice pieSection) {
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
