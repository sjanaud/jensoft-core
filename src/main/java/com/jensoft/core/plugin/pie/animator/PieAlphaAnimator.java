/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.animator;

import java.util.HashMap;

import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.PieToolkit;

/**
 * <code>AlphaAnimator</code> AlphaAnimator make alpha transition on pie slice
 * 
 * @see AbstractPieAnimator
 * @see Pie
 * @see PieSlice
 * @see PiePlugin
 * @see PieToolkit
 * @author Sebastien Janaud
 */
public class PieAlphaAnimator extends AbstractPieAnimator {

    /** transition step sleep */
    private int sleep = 40;

    /** minimum alpha to obtain in on state */
    private float alphaMin = 0f;

    /** maximum alpha to obtain in off state */
    private float alphaMax = 1f;

    /** cache alpha transition */
    private HashMap<PieSlice, AlphaTransition> transitions = new HashMap<PieSlice, PieAlphaAnimator.AlphaTransition>();

    /**
     * transition state
     */
    public enum TransitionState {
        On, Off;
    }

    /**
     * create default alpha animator
     */
    public PieAlphaAnimator() {

    }

    /**
     * create animator for specified given parameters
     * 
     * @param alphaMin
     *            the minimum alpha to set
     * @param alphaMax
     *            the maximum alpha to set
     */
    public PieAlphaAnimator(float alphaMin, float alphaMax) {
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
    public PieAlphaAnimator(float alphaMin, float alphaMax, int sleep) {
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

    @Override
    protected void onPressed(PieSlice slice) {
       // System.out.println("on press slice : " + slice.getName());
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
     * @see com.jensoft.core.plugin.pie.animator.AbstractPieAnimator#getAnimator(com.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    public Runnable getAnimator(PieSlice slice) {
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

        private PieSlice slice;
        private TransitionState transitionState;

        public AlphaTransition(PieSlice slice) {
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

                        getView2D(slice).repaintDevice();
                        Thread.sleep(sleep);

                    }
                    slice.setAlpha(alphaMin);
                    getView2D(slice).repaintDevice();
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
                        getView2D(slice).repaintDevice();
                        Thread.sleep(sleep);

                    }
                    slice.setAlpha(alphaMax);
                    getView2D(slice).repaintDevice();
                    transitionState = TransitionState.Off;
                }

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
