/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.animator;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel;

/**
 * <code>PieDivergenceAnimator</code> PieDivergenceAnimator make divergence animator on pie slice
 * 
 * @see Pie
 * @see PieSlice
 * @see AbstractPieAnimator
 * @see AbstractPieSliceLabel
 * @author Sebastien Janaud
 */
public class PieDivergenceAnimator extends AbstractPieAnimator {

    /** inflate divergence from initial slice divergence */
    private int inflate = 20;

    /** sleep between transition step */
    private int sleep = 5;

    /**
     * create default divergence animator
     */
    public PieDivergenceAnimator() {
    }

    /**
     * create divergence with specified parameter
     * 
     * @param inflate
     *            the total divergence inflate value
     */
    public PieDivergenceAnimator(int inflate) {
        super();
        this.inflate = inflate;
    }

    /**
     * create divergence with specified parameters
     * 
     * @param inflate
     *            the total divergence inflate value
     * @param sleep
     *            the sleep between to inflating step
     */
    public PieDivergenceAnimator(int inflate, int sleep) {
        super();
        this.inflate = inflate;
        this.sleep = sleep;
    }

    @Override
    protected void onPressed(PieSlice slice) {
        Thread t = new Thread(getAnimator(slice));
        t.start();
    }

    /**
     * @return the inflate
     */
    public int getInflate() {
        return inflate;
    }

    /**
     * @param inflate
     *            the inflate to set
     */
    public void setInflate(int inflate) {
        this.inflate = inflate;
    }

    /**
     * @return the sleep
     */
    public int getSleep() {
        return sleep;
    }

    /**
     * @param sleep
     *            the sleep to set
     */
    public void setSleep(int sleep) {
        this.sleep = sleep;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.animator.AbstractPieAnimator#getAnimator(com.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    public Runnable getAnimator(PieSlice slice) {
        return new DivergenceR(slice);
    }

    public class DivergenceR implements Runnable {

        private PieSlice slice;

        public DivergenceR(PieSlice slice) {
            this.slice = slice;
        }

        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            try {
                if (slice.getDivergence() == inflate) {
                    for (int i = 0; i <= inflate; i++) {
                        slice.setDivergence(slice.getDivergence() - 1);
                        Thread.sleep(sleep);
                        getView(slice).repaintDevice();
                    }
                    slice.setDivergence(0);
                    getView(slice).repaintDevice();
                }
                else {
                    for (int i = 0; i <= inflate; i++) {
                        slice.setDivergence(i);
                        Thread.sleep(sleep);
                        getView(slice).repaintDevice();
                    }
                    slice.setDivergence(inflate);
                    getView(slice).repaintDevice();
                }

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
