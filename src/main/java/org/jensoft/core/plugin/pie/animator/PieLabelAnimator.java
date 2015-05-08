/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.animator;

import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.PieSlice;
import org.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel;

/**
 * <code>PieLabelAnimator</code> PieLabelAnimator make label animation on pie slice
 * 
 * @see Pie
 * @see PieSlice
 * @see AbstractPieAnimator
 * @see AbstractPieSliceLabel
 * @author Sebastien Janaud
 */
public class PieLabelAnimator extends AbstractPieAnimator {

    /** pie slice */
    private PieSlice slice;

    /** SliceLabel */
    private AbstractPieSliceLabel sliceLabel;

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
     * create LabelAnimator for the specified slice and label
     * 
     * @param slice
     *            the slice on which the label should be added
     * @param sliceLabel
     *            the label to add on slice when event occurs
     */
    public PieLabelAnimator(PieSlice slice, AbstractPieSliceLabel sliceLabel) {
        this.slice = slice;
        this.sliceLabel = sliceLabel;
    }

    /**
     * @return the sliceLabel
     */
    public AbstractPieSliceLabel getSliceLabel() {
        return sliceLabel;
    }

    /**
     * @param sliceLabel
     *            the sliceLabel to set
     */
    public void setSliceLabel(AbstractPieSliceLabel sliceLabel) {
        this.sliceLabel = sliceLabel;
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.animator.AbstractPieAnimator#onPressed(org.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    protected void onPressed(PieSlice slice) {
        if (showLabelBehavior == ShowLabelBehavior.ShowOnSlicePressed && slice.equals(this.slice)) {
            if (slice.equals(this.slice)) {
                Thread t = new Thread(getAnimator(slice));
                t.start();
            }
        }
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.animator.AbstractPieAnimator#onEntered(org.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    protected void onEntered(PieSlice slice) {
        if (showLabelBehavior == ShowLabelBehavior.ShowOnSliceRollover && slice.equals(this.slice)) {
            if (slice.equals(this.slice)) {
                Thread t = new Thread(getAnimator(slice));
                t.start();
            }
        }
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.animator.AbstractPieAnimator#onExited(org.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    protected void onExited(PieSlice slice) {
        if (showLabelBehavior == ShowLabelBehavior.ShowOnSliceRollover && slice.equals(this.slice)) {
            if (slice.equals(this.slice)) {
                Thread t = new Thread(getAnimator(slice));
                t.start();
            }
        }
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.pie.animator.AbstractPieAnimator#getAnimator(org.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    public Runnable getAnimator(PieSlice slice) {
        return new LabelRunner(slice);
    }

    /**
     * label runner
     * 
     * @author Sebastien Janaud
     */
    public class LabelRunner implements Runnable {

        private PieSlice animateSlice;

        public LabelRunner(PieSlice slice) {
            animateSlice = slice;
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
