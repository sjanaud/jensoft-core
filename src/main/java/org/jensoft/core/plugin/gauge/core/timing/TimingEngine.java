/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge.core.timing;

import java.util.ArrayList;
import java.util.List;

public class TimingEngine {

    private long duration;
    private long resolution;

    private Engine engine;
    private Timing timing;

    private long startTime;

    private boolean infinite = false;

    public long getResolution() {
        return resolution;
    }

    public void setResolution(long resolution) {
        this.resolution = resolution;
    }

    public static void main(String[] args) {
        TimingEngine te = new TimingEngine(10000);
        te.setResolution(1000);

        te.addTimingListener(new TimingListener() {

            @Override
            public void timingEvent(float fraction) {
                System.out.println("timing event fraction +" + fraction);

            }

            @Override
            public void stopCycle() {
                // TODO Auto-generated method stub
                System.out.println("stop cycle");

            }

            @Override
            public void startCycle() {
                // TODO Auto-generated method stub
                System.out.println("start cycle");

            }

            @Override
            public void engineStop() {
                System.out.println("engine stop");

            }

            @Override
            public void engineStart() {

                System.out.println("engine start");

            }
        });
        te.startEngine();
        try {
            Thread.sleep(6000);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        te.stopEngine();

    }

    public TimingEngine(long duration) {
        this.duration = duration;
    }

    public void startEngine() {
        engine = new Engine();
        engine.start();
    }

    public void stopEngine() {
        engine.interrupt();
    }

    class Engine extends Thread {

        public Engine() {

        }

        @Override
        public void run() {
            fireEngineStart();
            boolean einterupt = false;

            try {
                startTime = System.currentTimeMillis();
                timing = new Timing(this);

                timing.start();
                if (infinite) {
                    while (true) {
                        sleep(duration);
                    }
                }
                else {
                    sleep(duration);
                    throw new InterruptedException("TimingEngine interrupt");
                }

            }
            catch (InterruptedException e) {
                timing.interrupt();

                einterupt = true;
                // Thread.currentThread().interrupt(); // tres important

                fireEngineStop();

                if (einterupt) {
                    Thread.currentThread().interrupt();
                }
            }

        }

    }

    class Timing extends Thread {

        private Engine e;

        public Timing(Engine e) {
            this.e = e;
        }

        @Override
        public void run() {

            // boolean armedStop = false;
            while (!interrupted()) {
                try {

                    if (armedCycle) {
                        fireStartCycle();
                    }

                    sleep(resolution);

                    long t = System.currentTimeMillis();
                    double delta = new Double(t) - new Double(startTime);
                    double fraction = new Double(delta) / new Double(duration);
                    // System.out.println("TimeFrac :"+fraction);
                    if (fraction >= 1) {
                        fireTimingEvent((float) fraction);
                        fireStopCycle();
                        fraction = 0;
                        startTime = t;
                        // armedStop = true;
                    }
                    else {
                        fireTimingEvent((float) fraction);
                    }

                }
                catch (InterruptedException e) {

                    // if(isInterrupted()){

                    long t = System.currentTimeMillis();
                    double delta = new Double(t) - new Double(startTime);
                    double fraction = new Double(delta) / new Double(duration);
                    if (fraction > 1) {
                        fireTimingEvent(1f);
                    }
                    else {
                        fireTimingEvent((float) fraction);
                    }

                    fireStopCycle();

                    Thread.currentThread().interrupt();

                }

            }

        }

    }

    private List<TimingListener> timingsListeners = new ArrayList<TimingListener>();

    public void addTimingListener(TimingListener listener) {
        timingsListeners.add(listener);
    }

    private void fireEngineStop() {

        synchronized (timingsListeners) {
            for (TimingListener l : timingsListeners) {
                l.engineStop();
            }
        }
    }

    private void fireEngineStart() {

        synchronized (timingsListeners) {
            for (TimingListener l : timingsListeners) {
                l.engineStart();
            }
        }
    }

    private void fireTimingEvent(float fraction) {

        synchronized (timingsListeners) {
            for (TimingListener l : timingsListeners) {
                l.timingEvent(fraction);
            }
        }
    }

    private boolean armedCycle = true;

    private void fireStopCycle() {

        synchronized (timingsListeners) {
            armedCycle = true;
            for (TimingListener l : timingsListeners) {
                l.stopCycle();
            }
        }
    }

    private void fireStartCycle() {

        synchronized (timingsListeners) {
            armedCycle = false;
            for (TimingListener l : timingsListeners) {
                l.startCycle();
            }
        }
    }

}
