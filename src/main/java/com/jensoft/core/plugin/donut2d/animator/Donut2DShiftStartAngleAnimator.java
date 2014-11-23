/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.animator;

import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>Donut2DShiftAnimator</code>
 * 
 * @author sebastien
 */
public class Donut2DShiftStartAngleAnimator extends AbstractDonut2DAnimator {

    /** the donut 2D shifting thread */
    private Donut2DShifting donut2DShifting;

    /**
     * shifting velocity
     * 
     * @author sebastien
     */
    public enum ShiftVelocity {
        Slow(40),
        Default(20),
        Speed(10);

        private int velocity;

        private ShiftVelocity(int velocity) {
            this.velocity = velocity;
        }

        /**
         * @return the velocity
         */
        public int getVelocity() {
            return velocity;
        }

    }

    /**
     * create shift animator
     */
    public Donut2DShiftStartAngleAnimator() {
    }

    /**
     * shift the donut2D with the specified velocity
     * 
     * @param velocity
     */
    public void shift(ShiftVelocity velocity) {
        interrupt();
        donut2DShifting = new Donut2DShifting(velocity);
        donut2DShifting.start();
    }

    /**
     * interrupt shifting
     */
    public void interrupt() {
        if (donut2DShifting != null && donut2DShifting.isAlive()) {
            donut2DShifting.interrupt();
        }
    }

    /**
     * interrupt shifting after specified millis
     */
    public void interruptShiftingAfter(final int millis) {
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(millis);
                    interrupt();
                }
                catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    /**
     * shift the donut with the specified velocity and interrupt the shifting
     * after the specified millis
     * 
     * @param velocity
     *            the shift velocity
     * @param millis
     *            the time millis after the shifting will be interrupted
     */
    public void shiftAndInterrupt(ShiftVelocity velocity, int millis) {
        shift(velocity);
        interruptShiftingAfter(millis);
    }

    /**
     * shift thread
     * 
     * @author sebastien janaud
     */
    class Donut2DShifting extends Thread {

        /** shift velocity */
        private ShiftVelocity velocity;

        /**
         * create a new shift thread
         * 
         * @param velocity
         *            the shift velocity
         */
        public Donut2DShifting(ShiftVelocity velocity) {
            this.velocity = velocity;
        }

        /**
         * @return the velocity
         */
        public ShiftVelocity getVelocity() {
            return velocity;
        }

        /**
         * @param velocity
         *            the velocity to set
         */
        public void setVelocity(ShiftVelocity velocity) {
            this.velocity = velocity;
        }

        @Override
        public void run() {
            Donut2D donut2D = getDonut2D();
            try {
                while (!interrupted()) {
                    donut2D.setStartAngleDegree(donut2D.getStartAngleDegree() + 1);
                    Thread.sleep(velocity.getVelocity());
                    donut2D.getHostPlugin().getProjection().getView().repaintDevice();
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected Runnable getAnimator(Donut2DSlice slice) {
        return null;
    }

}
