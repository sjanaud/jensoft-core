/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.animator;

import org.jensoft.core.plugin.pie.Pie;
import org.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieShiftStartAngleAnimator</code> takes the responsibility to shift
 * start angle of the target pie.
 * 
 * @author sebastien janaud
 */
public class PieShiftStartAngleAnimator extends AbstractPieAnimator {

    private PieShifting pieShifting;

    /**
     * the shifting velocity
     * 
     * @author sebastien janaud
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
     * create pie shifting animator
     */
    public PieShiftStartAngleAnimator() {
    }

    /**
     * shift the pie with the specified velocity
     * 
     * @param velocity
     */
    public void shift(ShiftVelocity velocity) {
        interrupt();
        pieShifting = new PieShifting(velocity);
        pieShifting.start();
    }

    /**
     * interrupt shifting immediately
     */
    public void interrupt() {
        if (pieShifting != null && pieShifting.isAlive()) {
            pieShifting.interrupt();
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
     * shift the pie with the specified velocity and interrupt the shifting
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
    class PieShifting extends Thread {

        /** shift velocity */
        private ShiftVelocity velocity;

        /**
         * create a new shift thread
         * 
         * @param velocity
         *            the shift velocity
         */
        public PieShifting(ShiftVelocity velocity) {
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

        /*
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            Pie pie = getPie();
            try {
                while (!interrupted()) {
                    pie.setStartAngleDegree(pie.getStartAngleDegree() + 1);
                    Thread.sleep(velocity.getVelocity());
                    pie.getHostPlugin().getProjection().getView().repaintDevice();
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    protected Runnable getAnimator(PieSlice slice) {
        return null;
    }

}
