/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.animator;

import java.util.HashMap;

import org.jensoft.core.plugin.donut2d.Donut2DSlice;
import org.jensoft.core.view.View;

/**
 * <code>Donut2DFlashAnimator</code> creates flash animation on pie slice
 * 
 * @see AbstractDonut2DAnimator
 * @see Donut2D
 * @see Donut2DSlice
 * @see Donut2DPlugin
 * @author Sebastien Janaud
 */
/**
 * @author sebastien
 *
 */
public class Donut2DFlashAnimator extends AbstractDonut2DAnimator {

    /** host view */
    private View view2D;

    /** cache slice animator */
    private HashMap<Donut2DSlice, AlphaRunner> cacheAnimator = new HashMap<Donut2DSlice, AlphaRunner>();

    /** minimum alpha to obtain in on state */
    public float alphaMin = 0f;

    /** maximum alpha to obtain in off state */
    public float alphaMax = 1f;

    /** flash speed */
    private FlashSpeed flashSpeed = FlashSpeed.Default;

    /** Flash speed */
    public enum FlashSpeed {
        VeryFast, Fast, Slow, VerySlow, Default;
    }

    /**
     * Flash behavior
     * 
     * @author Sebastien Janaud
     */
    public enum ShowFlashBehavior {
        ShowOnSlicePressed,
        ShowOnSliceRollover
    }

    /** label behavior */
    private ShowFlashBehavior showFlashBehavior = ShowFlashBehavior.ShowOnSliceRollover;

    /**
     * create default alpha animator
     */
    public Donut2DFlashAnimator() {
    }

    /**
     * create animator for specified given parameters
     * 
     * @param alphaMin
     *            the minimum alpha to set
     * @param alphaMax
     *            the maximum alpha to set
     */
    public Donut2DFlashAnimator(float alphaMin, float alphaMax) {
        super();
        this.alphaMin = alphaMin;
        this.alphaMax = alphaMax;
    }

    /**
     * create animator for specified given parameters
     * 
     * @param alphaMin
     *            the minimum alpha to set
     * @param alphaMax
     *            the maximum alpha to set
     * @param flashSpeed
     *            the flash speed to set
     */
    public Donut2DFlashAnimator(float alphaMin, float alphaMax, FlashSpeed flashSpeed) {
        super();
        this.alphaMin = alphaMin;
        this.alphaMax = alphaMax;
        this.flashSpeed = flashSpeed;
    }

    /**
     * @return the alphaMin
     */
    public float getAlphaMin() {
        return alphaMin;
    }

    /**
     * @return the showFlashBehavior
     */
    public ShowFlashBehavior getShowFlashBehavior() {
        return showFlashBehavior;
    }

    /**
     * @param showFlashBehavior
     *            the showFlashBehavior to set
     */
    public void setShowFlashBehavior(ShowFlashBehavior showFlashBehavior) {
        this.showFlashBehavior = showFlashBehavior;
    }

    /**
     * @param alphaMin
     *            the alphaMin to set
     */
    public void setAlphaMin(float alphaMin) {
        this.alphaMin = alphaMin;
    }

    /**
     * @return the alphaMax
     */
    public float getAlphaMax() {
        return alphaMax;
    }

    /**
     * @param alphaMax
     *            the alphaMax to set
     */
    public void setAlphaMax(float alphaMax) {
        this.alphaMax = alphaMax;
    }

    @Override
    protected void onPressed(Donut2DSlice slice) {
        if (showFlashBehavior == ShowFlashBehavior.ShowOnSlicePressed) {
            view2D = slice.getHost().getHostPlugin().getProjection().getView();
            AlphaRunner runner = (AlphaRunner) getAnimator(slice);
            if (!runner.isRunning()) {
                Thread t = new Thread(getAnimator(slice));
                runner.setHost(t);
                t.start();
            }
            else {
                runner.getHost().interrupt();
                slice.setAlpha(runner.getInitialAlpha());
            }
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.animator.AbstractDonut2DAnimator#onEntered(com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    protected void onEntered(Donut2DSlice slice) {
        if (showFlashBehavior == ShowFlashBehavior.ShowOnSliceRollover) {
            view2D = slice.getHost().getHostPlugin().getProjection().getView();
            AlphaRunner runner = (AlphaRunner) getAnimator(slice);
            if (!runner.isRunning()) {
                Thread t = new Thread(getAnimator(slice));
                runner.setHost(t);
                t.start();
            }
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.animator.AbstractDonut2DAnimator#onExited(com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    protected void onExited(Donut2DSlice slice) {
        if (showFlashBehavior == ShowFlashBehavior.ShowOnSliceRollover) {
            AlphaRunner runner = (AlphaRunner) getAnimator(slice);
            if (runner.isRunning()) {
                runner.getHost().interrupt();
                slice.setAlpha(runner.getInitialAlpha());
                getView(slice).repaintDevice();
            }
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.animator.AbstractDonut2DAnimator#getAnimator(com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    public Runnable getAnimator(Donut2DSlice slice) {
        AlphaRunner runner = null;
        if (cacheAnimator.get(slice) != null) {
            runner = cacheAnimator.get(slice);
        }
        else {
            runner = new AlphaRunner(slice);
            cacheAnimator.put(slice, runner);
        }
        return runner;
    }

    /***
     * Alpha runner to run transition between two state of transition
     * 
     * @author sebastien janaud
     */
    public class AlphaRunner implements Runnable {

        /** initial alpha */
        private float initialAlpha;

        /** the slice to flash */
        private Donut2DSlice slice;

        /** running flag */
        private boolean running = false;

        /** host thread for this runnable */
        private Thread host;

        /**
         * @return the running
         */
        public boolean isRunning() {
            return running;
        }

        /**
         * @param running
         *            the running to set
         */
        public void setRunning(boolean running) {
            this.running = running;
        }

        /**
         * @return the host
         */
        public Thread getHost() {
            return host;
        }

        /**
         * @param host
         *            the host to set
         */
        public void setHost(Thread host) {
            this.host = host;
        }

        /**
         * @return the initialAlpha
         */
        public float getInitialAlpha() {
            return initialAlpha;
        }

        /**
         * @param initialAlpha
         *            the initialAlpha to set
         */
        public void setInitialAlpha(float initialAlpha) {
            this.initialAlpha = initialAlpha;
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
         * create alpha runner
         * 
         * @param slice
         */
        public AlphaRunner(Donut2DSlice slice) {
            this.slice = slice;
            initialAlpha = slice.getAlpha();
        }

        @Override
        public void run() {
            running = true;
            int sleep = 300;
            if (flashSpeed == FlashSpeed.VeryFast) {
                sleep = 100;
            }
            if (flashSpeed == FlashSpeed.Fast) {
                sleep = 200;
            }
            if (flashSpeed == FlashSpeed.Slow) {
                sleep = 400;
            }
            if (flashSpeed == FlashSpeed.VerySlow) {
                sleep = 500;
            }
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    slice.setAlpha(alphaMin);
                    view2D.repaintDevice();
                    Thread.sleep(sleep);
                    slice.setAlpha(alphaMax);
                    view2D.repaintDevice();
                    Thread.sleep(sleep);
                }

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            running = false;
        }
    }

}
