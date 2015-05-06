/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.animator;

import java.util.HashMap;

import org.jensoft.core.plugin.donut2d.Donut2D;
import org.jensoft.core.plugin.donut2d.Donut2DPlugin;
import org.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>AlphaAnimator</code> AlphaAnimator make alpha transition on pie slice
 * 
 * @see AbstractDonut2DAnimator
 * @see Donut2D
 * @see Donut2DSlice
 * @see Donut2DPlugin
 * @author Sebastien Janaud
 */
public class Donut2DAlphaAnimator extends AbstractDonut2DAnimator {

    /** transition step sleep */
    private int sleep = 40;

    /** minimum alpha to obtain in on state */
    private float alphaMin = 0f;

    /** maximum alpha to obtain in off state */
    private float alphaMax = 1f;

    /** cache alpha transition */
    private HashMap<Donut2DSlice, AlphaTransition> transitions = new HashMap<Donut2DSlice, Donut2DAlphaAnimator.AlphaTransition>();

    /**
     * transition state
     */
    public enum TransitionState {
        On, Off;
    }

    /**
     * create default alpha animator
     */
    public Donut2DAlphaAnimator() {
    }

    /**
     * create animator for specified given parameters
     * 
     * @param alphaMin
     *            the minimum alpha to set
     * @param alphaMax
     *            the maximum alpha to set
     */
    public Donut2DAlphaAnimator(float alphaMin, float alphaMax) {
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
     * @param sleep
     *            the time elapsed between two alpha
     */
    public Donut2DAlphaAnimator(float alphaMin, float alphaMax, int sleep) {
        super();
        this.alphaMin = alphaMin;
        this.alphaMax = alphaMax;
        this.sleep = sleep;
    }

    /**
     * @return the alphaMin
     */
    public float getAlphaMin() {
        return alphaMin;
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

    private Thread t;

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.animator.AbstractDonut2DAnimator#onPressed(com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    protected void onPressed(Donut2DSlice slice) {
        if (t == null) {
            t = new Thread(getAnimator(slice));
            t.start();
        }
        else {
            if (!t.isAlive()) {
                t = new Thread(getAnimator(slice));
                t.start();
            }
        }
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
     * @see com.jensoft.core.plugin.donut2d.animator.AbstractDonut2DAnimator#getAnimator(com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    public Runnable getAnimator(Donut2DSlice slice) {
        AlphaTransition at = transitions.get(slice);
        if (at == null) {
            AlphaTransition transition = new AlphaTransition(slice);
            transitions.put(slice, transition);
        }
        return transitions.get(slice);
    }

    /***
     * Alpha runner to run transition between two state of transition
     */
    public class AlphaTransition implements Runnable {

        private Donut2DSlice slice;
        private TransitionState transitionState;

        public AlphaTransition(Donut2DSlice slice) {
            this.slice = slice;
            transitionState = TransitionState.Off;
        }

        @Override
        public void run() {
            float alpha;
            float step = 10f;

            try {
                if (transitionState == TransitionState.Off) {
                    float pas = (alphaMax - alphaMin) / new Float(step);
                    for (int i = 0; i <= step; i++) {
                        alpha = alphaMax - i * pas;
                        if (alpha < alphaMin) {
                            alpha = alphaMin;
                        }

                        slice.setAlpha(alpha);

                        getView(slice).repaintDevice();
                        Thread.sleep(sleep);

                    }
                    slice.setAlpha(alphaMin);
                    getView(slice).repaintDevice();
                    transitionState = TransitionState.On;

                }
                else if (transitionState == TransitionState.On) {
                    float pas = (alphaMax - alphaMin) / new Float(step);
                    for (int i = 0; i <= step; i++) {
                        alpha = alphaMin + i * pas;
                        if (alpha > alphaMax) {
                            alpha = alphaMax;
                        }

                        slice.setAlpha(alpha);
                        getView(slice).repaintDevice();
                        Thread.sleep(sleep);

                    }
                    slice.setAlpha(alphaMax);
                    getView(slice).repaintDevice();
                    transitionState = TransitionState.Off;
                }

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
