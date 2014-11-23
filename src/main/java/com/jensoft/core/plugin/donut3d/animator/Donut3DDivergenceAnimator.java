/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d.animator;

import com.jensoft.core.plugin.donut3d.Donut3DSlice;

/**
 * <code>Donut3DDivergenceAnimator</code> slice divergence animator
 * 
 * @author Sebastien Janaud
 */
public class Donut3DDivergenceAnimator extends AbstractDonut3DAnimator {

    /** inflate divergence from initial section divergence */
    private int inflate = 30;

    /** sleep between transition step */
    private int sleep = 5;

    /**
     * create default divergence animator
     * default inflate 30 pixel default sleep step increment 5 millisecond
     */
    public Donut3DDivergenceAnimator() {
    }

    /**
     * create divergence animator with the specified divergence in pixel default
     * sleep step increment 5 millisecond
     * 
     * @param inflate
     *            divergence in pixel
     */
    public Donut3DDivergenceAnimator(int inflate) {
        super();
        this.inflate = inflate;
    }

    /**
     * create divergence animator with specified parameters
     * 
     * @param inflate
     *            divergence in pixel
     * @param sleep
     *            sleep in millisecond into to inflate increment, default is 5
     *            millisecond
     */
    public Donut3DDivergenceAnimator(int inflate, int sleep) {
        super();
        this.inflate = inflate;
        this.sleep = sleep;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut3d.animator.AbstractDonut3DAnimator#onPressed(com.jensoft.core.plugin.donut3d.Donut3DSlice)
     */
    @Override
    protected void onPressed(Donut3DSlice section) {
        Thread t = new Thread(getAnimator(section));
        t.start();
    };

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

    @Override
    public Runnable getAnimator(Donut3DSlice slice) {
        return new DivergenceR(slice);
    }

    public class DivergenceR implements Runnable {

        private Donut3DSlice donutSlice;

        public DivergenceR(Donut3DSlice pieSection) {
            donutSlice = pieSection;
        }

        @Override
        public void run() {
            try {
                if (donutSlice.getDivergence() == inflate) {
                    for (int i = 0; i <= inflate; i++) {
                        donutSlice
                                .setDivergence(donutSlice.getDivergence() - 1);
                        Thread.sleep(sleep);
                        getView(donutSlice).repaintDevice();
                    }
                    donutSlice.setDivergence(0);
                    getView(donutSlice).repaintDevice();
                }
                else {
                    for (int i = 0; i <= inflate; i++) {
                        donutSlice.setDivergence(i);
                        Thread.sleep(sleep);
                        getView(donutSlice).repaintDevice();
                    }
                    donutSlice.setDivergence(inflate);
                    getView(donutSlice).repaintDevice();
                }

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
