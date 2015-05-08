/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.translate;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import org.jensoft.core.graphics.Antialiasing;
import org.jensoft.core.graphics.TextAntialiasing;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.projection.ProjectionBound;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>TranslatePlugin</code>
 * 
 * @author sebastien janaud
 */
public class TranslatePlugin extends AbstractPlugin implements
        AbstractPlugin.OnPressListener, AbstractPlugin.OnReleaseListener,
        AbstractPlugin.OnDragListener {

    /** lock translate operation */
    private boolean lockTranslate = false;

    /** passive translate operation */
    private boolean passiveTranslate = false;

    /** translation from startX */
    private int translateStartX;

    /** translation from startY */
    private int translateStartY;

    /** translation to curentX */
    private int translateCurentX;

    /** translation to curentY */
    private int translateCurentY;

    /** translate delta device x */
    private double translateDx;

    /** translate delta device y */
    private double translateDy;

    /** L2R lock translation active from left to right */
    public boolean lockL2R = true;

    /** B2T translation active from bottom to top */
    public boolean lockB2T = true;

    /** translate history sequence player */
    private SequencePlayer sequencePlayer;

    /** bound history */
    private List<TimingBoundFrame> boundHistory;

    /** listeners */
    private EventListenerList translateListenerList;

    /** Dynamic animator */
    private DynamicSmoothReleaseAnimation dynamicAnimator;

    /** Dynamic items */
    private List<DynamicItem> cinematiques;

    /** Dynamic effect */
    boolean dynamicEffect = true;

    /** timing frame counter */
    private long dynamicSequenceTime = 0;

    /** current shift if a shift is alive */
    private SmoothShift smoothShift;

    /**
     * defines shift direction for translate
     */
    public enum ShiftDirection {
        North, South, West, East;
    }

    /**
     * defines shift velocity for translate
     */
    public enum ShiftVelocity {
        VerySlow(200), Slow(90), Default(40), Fast(20), VeryFast(5);

        /** velocity */
        private int velocity;

        /**
         * create shift velocity with specified velocity
         * 
         * @param velocity
         *            the shift velocity
         */
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
     * Create TranslatePlugin
     */
    public TranslatePlugin() {
        super();
        setSelectable(true);
        setName(TranslatePlugin.class.getCanonicalName());
        translateListenerList = new EventListenerList();
        boundHistory = new ArrayList<TimingBoundFrame>();

        setOnPressListener(this);
        setOnReleaseListener(this);
        setOnDragListener(this);
        setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);

        setPriority(100);

    }

    /**
     * @return the dynamicEffect
     */
    public boolean isDynamicEffect() {
        return dynamicEffect;
    }

    /**
     * set the dynamic effect
     * 
     * @param dynamicEffect
     *            the dynamicEffect to set
     */
    public void setDynamicEffect(boolean dynamicEffect) {
        this.dynamicEffect = dynamicEffect;
    }

    /**
     * return true if L2R option is lock , false otherwise
     * 
     * @return the lock L2R
     */
    public boolean isLockL2R() {
        return lockL2R;
    }

    /**
     * lock the L2R option and fire event for translate listener
     */
    public void lockL2R() {
        if (!isLockL2R()) {
            lockL2R = true;
            fireTranslateL2RChanged();
        }
    }

    /**
     * unlock the L2R option and fire event for translate listener
     */
    public void unlockL2R() {
        if (isLockL2R()) {
            lockL2R = false;
            fireTranslateL2RChanged();
        }
    }

    /**
     * fire translate L2R changed
     */
    public void fireTranslateL2RChanged() {
        Object[] listeners = translateListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == TranslatePluginListener.class) {
                    ((TranslatePluginListener) listeners[i + 1])
                            .translateL2RChanged(new TranslatePluginEvent(this));
                }
            }
        }
    }

    /**
     * return true if B2T option is lock , false otherwise
     * 
     * @return the lock B2T
     */
    public boolean isLockB2T() {
        return lockB2T;
    }

    /**
     * lock B2T option and fire event for translate listener
     */
    public void lockB2T() {
        if (!isLockB2T()) {
            lockB2T = true;
            fireTranslateB2TChanged();
        }
    }

    /**
     * unlock the B2T option and fire event for translate listener
     */
    public void unlockB2T() {
        if (isLockB2T()) {
            lockB2T = false;
            fireTranslateB2TChanged();
        }

    }

    /**
     * fire translate B2T changed
     */
    public void fireTranslateB2TChanged() {
        Object[] listeners = translateListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == TranslatePluginListener.class) {
                    ((TranslatePluginListener) listeners[i + 1])
                            .translateB2TChanged(new TranslatePluginEvent(this));
                }
            }
        }
    }

    /**
     * synchronize translate plug in when new translate occurs in any registered
     * translate plug in
     * 
     * @param translates
     *            the translate plug ins to synchronize
     */
    public static TranslateSynchronizer createSynchronizer(
            TranslatePlugin... translates) {
        TranslateSynchronizer synchronizer = new TranslateSynchronizer(
                                                                       translates);
        return synchronizer;
    }

    /**
     * synchronize translate plug in when new translate occurs in any registered
     * translate plug in
     * 
     * @param translates
     *            the translate plug ins to synchronize
     */
    public static TranslateSynchronizer createSynchronizer(
            List<TranslatePlugin> translates) {
        TranslateSynchronizer synchronizer = new TranslateSynchronizer(
                                                                       translates);
        return synchronizer;
    }

    /**
     * start replay the last translate
     */
    public void startReplay() {
        stopReplay();

        sequencePlayer = new SequencePlayer();
        sequencePlayer.start();
    }

    /**
     * stop the current replay
     */
    public void stopReplay() {
        if (sequencePlayer != null) {
            sequencePlayer.interrupt();
        }
    }

    /**
     * sequence player for translate
     */
    public class SequencePlayer extends Thread {

        public SequencePlayer() {

        }

        @Override
        public void run() {
            try {
                fireTranslateStarted();
                if (boundHistory.size() > 3) {
                    Projection w2d = getProjection().getView().getActiveProjection();
                    if (w2d instanceof Projection.Linear) {
                        TimingBoundFrame twb0 = boundHistory.get(0);
                        Projection.Linear wl = (Projection.Linear) w2d;
                        wl.bound(twb0.getMinX(), twb0.getMaxX(), twb0.getMinY(), twb0.getMaxY());

                        for (TimingBoundFrame twb : boundHistory) {
                            processTranslate(twb.getDx(), twb.getDy());
                            fireTranslate();
                            getProjection().getView().getDevice2D().repaintDevice();
                            Thread.sleep(twb.getBoundDurationMillis());
                        }
                    }
                }

                fireTranslateStopped();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            finally {
                fireTranslateStopped();
            }

        }

    }

    /**
     * defines space/timing frame for a dynamic item
     */
    class TimingBoundFrame extends ProjectionBound {

        private long boundDurationMillis = 0;
        private double dx;
        private double dy;

        public TimingBoundFrame(double minX, double maxX, double minY,
                double maxY) {
            super(minX, maxX, minY, maxY);

        }

        /**
         * @return the dx
         */
        public double getDx() {
            return dx;
        }

        /**
         * @param dx
         *            the dx to set
         */
        public void setDx(double dx) {
            this.dx = dx;
        }

        /**
         * @return the dy
         */
        public double getDy() {
            return dy;
        }

        /**
         * @param dy
         *            the dy to set
         */
        public void setDy(double dy) {
            this.dy = dy;
        }

        public long getBoundDurationMillis() {
            return boundDurationMillis;
        }

        public void setBoundDurationMillis(long boundDurationMillis) {
            this.boundDurationMillis = boundDurationMillis;
        }

    }

    /**
     * get a lock/unlock action
     * 
     * @return lock unlock action
     */
    public TranslateLockUnlockAction getTranslateLockUnlockAction() {
        return new TranslateLockUnlockAction();
    }

    /**
     * translate select action
     */
    class TranslateLockUnlockAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (isLockSelected()) {
                        unlockSelected();
                    }
                    else {
                        lockSelected();
                    }
                }
            });
        }

    }

    public TranslateL2RAction getTranslateL2RAction() {
        return new TranslateL2RAction();
    }

    /**
     * lock or unlock translate left to right (L2R) action
     */
    class TranslateL2RAction implements ActionListener {

        public TranslateL2RAction() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {

                    if (isLockL2R()) {
                        unlockL2R();
                    }
                    else if (!isLockL2R()) {
                        lockL2R();
                    }
                }
            });
        }

    }

    public TranslateB2TAction getTranslateB2TAction() {
        return new TranslateB2TAction();
    }

    /**
     * lock or unlock translate bottom to top (B2T) action
     */
    class TranslateB2TAction implements ActionListener {

        public TranslateB2TAction() {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (isLockB2T()) {
                        unlockB2T();
                    }
                    else if (!isLockB2T()) {
                        lockB2T();
                    }

                }
            });
        }

    }

    /**
     * add translate plug in listener
     * 
     * @param listener
     */
    public void addTranslateListener(TranslatePluginListener listener) {
        translateListenerList.add(TranslatePluginListener.class, listener);
        addPluginListener(listener);
    }

    /**
     * remove translate plug in listener
     * 
     * @param listener
     */
    public void removeTranslateListener(TranslatePluginListener listener) {
        translateListenerList.remove(TranslatePluginListener.class, listener);
        removePluginListener(listener);
    }

    /**
     * fire translate started
     */
    public void fireTranslateStarted() {
        Object[] listeners = translateListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == TranslatePluginListener.class) {
                    ((TranslatePluginListener) listeners[i + 1])
                            .translateStarted(new TranslatePluginEvent(this));
                }
            }
        }
    }

    /**
     * fire translate stopped
     */
    public void fireTranslateStopped() {
        Object[] listeners = translateListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == TranslatePluginListener.class) {
                    ((TranslatePluginListener) listeners[i + 1])
                            .translateStoped(new TranslatePluginEvent(this));
                }
            }
        }
    }

    /**
     * fire translate
     */
    public void fireTranslate() {
        Object[] listeners = translateListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == TranslatePluginListener.class) {
                    ((TranslatePluginListener) listeners[i + 1])
                            .translated(new TranslatePluginEvent(this));
                }
            }
        }
    }

    /**
     * start translate operation at the specified device point
     * 
     * @param translateStart
     *            the start point of the current translate
     */
    public void startTranslate(Point2D translateStart) {

        if (dynamicAnimator != null && dynamicAnimator.isAlive()) {
            dynamicAnimator.interrupt();
        }

        lockTranslate();

        translateStartX = (int) translateStart.getX();
        translateStartY = (int) translateStart.getY();

        cinematiques = new ArrayList<DynamicItem>();
        boundHistory.clear();

    }

    /**
     * return true if this plugin is in translate operation, false otherwise
     * 
     * @return true if this plugin is in translate operation, false otherwise
     */
    public boolean isLockTranslate() {
        return lockTranslate;
    }

    /**
     * unlock translate
     */
    public void unlockTranslate() {
        lockTranslate = false;
    }

    /**
     * lock translate
     */
    public void lockTranslate() {
        lockTranslate = true;
    }

    /**
     * return true if the tool is passive translate
     * 
     * @return true is translate is passivate
     */
    public boolean isPassiveTranslate() {
        return passiveTranslate;
    }

    /**
     * passive translate operation
     */
    public void passiveTranslate() {
        passiveTranslate = true;
    }

    /**
     * unpassive translate action
     */
    public void unPassiveTranslate() {
        passiveTranslate = false;
    }

    class DynamicSmoothReleaseAnimation extends Thread {

        public DynamicSmoothReleaseAnimation(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {

                if (cinematiques.size() < 4) {
                    return;
                }

                DynamicItem itemReleased = cinematiques
                        .get(cinematiques.size() - 1);
                DynamicItem itemLastDragued = cinematiques.get(cinematiques
                        .size() - 2);

                if (itemReleased.timeMillis > itemLastDragued.timeMillis + 2) {
                    throw new InterruptedException();
                }
                // 3
                int echantillon = 3;
                DynamicItem itemEnd = cinematiques.get(cinematiques.size() - 1);
                DynamicItem itemStart = cinematiques.get(cinematiques.size()
                        - 1 - echantillon);

                double startX = itemStart.point.getX();
                double endX = itemEnd.point.getX();
                double startY = itemStart.point.getY();
                double endY = itemEnd.point.getY();

                double deltaX = endX - startX;
                double deltaY = endY - startY;

                int delatTimeMillis = (int) (itemEnd.timeMillis - itemStart.timeMillis);

                int partNumber = 10;
                double deltaXPart = deltaX / partNumber;
                double deltaYPart = deltaY / partNumber;

                double deltaDeviceX;
                double deltaDeviceY;
                for (int i = 0; i < partNumber; i++) {

                    deltaDeviceX = deltaX - deltaXPart * i;
                    deltaDeviceY = deltaY - deltaYPart * i;

                    if (!lockB2T) {
                        deltaDeviceY = 0;
                    }
                    if (!lockL2R) {
                        deltaDeviceX = 0;
                    }

                    processTranslate(deltaDeviceX, deltaDeviceY);
                    fireTranslate();
                    translateStartX = translateCurentX;
                    translateStartY = translateCurentY;

                    Thread.sleep(delatTimeMillis);

                }

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            finally {
                unlockTranslate();
                unPassiveTranslate();
                getProjection().getView().repaintDevice();
                cinematiques.clear();
            }
        }
    }

    /**
     * defines a dynamic item
     */
    class DynamicItem {

        private Point2D point;
        private long timeMillis;
        private String parent = "na";

        public DynamicItem(Point2D point, long timeMillis) {
            super();
            this.point = point;
            this.timeMillis = timeMillis;
        }

        public Point2D getPoint() {
            return point;
        }

        public void setPoint(Point2D point) {
            this.point = point;
        }

        public long getTimeMillis() {
            return timeMillis;
        }

        public void setTimeMillis(long timeMillis) {
            this.timeMillis = timeMillis;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "Item :Parent " + parent + " " + point + " Time :"
                    + timeMillis;
        }

    }

    /**
     * register a new dynamic item for the current projection bound, register this
     * bound with the current time millisecond.
     */
    private void registerTimingBoundSequence() {
        Projection w2d = getProjection().getView().getActiveProjection();
        TimingBoundFrame translateProjectionBound = new TimingBoundFrame(
                                                                     w2d.getMinX(), w2d.getMaxX(), w2d.getMinY(),
                                                                     w2d.getMaxY());

        translateProjectionBound.setDx(translateDx);
        translateProjectionBound.setDy(translateDy);
        // check previous bound duration
        int len = boundHistory.size();
        if (len > 0) {
            long time = System.currentTimeMillis() - dynamicSequenceTime;
            TimingBoundFrame previousTWB = boundHistory.get(len - 1);
            previousTWB.setBoundDurationMillis(time);
        }

        if (len > 1000) {
            boundHistory.clear();
        }

        dynamicSequenceTime = System.currentTimeMillis();
        boundHistory.add(translateProjectionBound);

    }

    /**
     * bound user window with the specified x and y projection
     * 
     * @param deltaDeviceX
     * @param deltaDeviceY
     */
    public void processTranslate(double deltaDeviceX, double deltaDeviceY) {

        translateDx = deltaDeviceX;
        translateDy = deltaDeviceY;

        Projection w2d = getProjection();
        if (w2d == null) {
            return;
        }
        int w = getProjection().getView().getDevice2D().getDeviceWidth();
        int h = getProjection().getView().getDevice2D().getDeviceHeight();

        Point2D pMinXMinYDevice = new Point2D.Double(-deltaDeviceX, h
                - deltaDeviceY);
        Point2D pMaxXMaxYDevice = new Point2D.Double(w - deltaDeviceX,
                                                     -deltaDeviceY);

        Point2D pMinXMinYUser = w2d.pixelToUser(pMinXMinYDevice);
        Point2D pMaxXMaxYUser = w2d.pixelToUser(pMaxXMaxYDevice);
        if (w2d instanceof Projection.Linear) {
            Projection.Linear wl = (Projection.Linear) w2d;
            wl.bound(pMinXMinYUser.getX(), pMaxXMaxYUser.getX(),
                     pMinXMinYUser.getY(), pMaxXMaxYUser.getY());
        }

    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent e) {

        if (!isLockSelected()) {
            return;
        }

        if (isLockPassive()) {
            return;
        }

        if (!isLockTranslate()) {
            return;
        }

        boundTranslate(e.getX(), e.getY());
        fireTranslate();
        translateStartX = translateCurentX;
        translateStartY = translateCurentY;
    }

    /**
     * @param deviceCurentX
     * @param deviceCurentY
     */
    private void boundTranslate(int deviceCurentX, int deviceCurentY) {
        translateCurentX = deviceCurentX;
        translateCurentY = deviceCurentY;

        Point2D p2d = new Point2D.Double(deviceCurentX, deviceCurentY);
        DynamicItem tc = new DynamicItem(p2d, System.currentTimeMillis());
        tc.setParent("DRAGUED");
        cinematiques.add(tc);

        double deltaDeviceX = translateCurentX - translateStartX;
        double deltaDeviceY = translateCurentY - translateStartY;

        if (!lockB2T) {
            deltaDeviceY = 0;
        }
        if (!lockL2R) {
            deltaDeviceX = 0;
        }

        processTranslate(deltaDeviceX, deltaDeviceY);
        registerTimingBoundSequence();
    }

    @Override
    public void onRelease(MouseEvent e) {

        if (!isLockSelected()) {
            return;
        }

        if (isLockPassive()) {
            return;
        }

        if (!isLockTranslate()) {
            return;
        }

        if (!isShifting()) {
            stopTranslate(e.getX(), e.getY());
            fireTranslateStopped();
            repaintDevice();
        }
    }

    /**
     * stop translate.
     * 
     * @param endDeviceX
     *            the x location of stop
     * @param endDeviceY
     *            the y location of stop
     */
    public void stopTranslate(int endDeviceX, int endDeviceY) {

        translateCurentX = endDeviceX;
        translateCurentY = endDeviceY;

        Point2D p2d = new Point2D.Double(endDeviceX, endDeviceY);
        DynamicItem tc = new DynamicItem(p2d, System.currentTimeMillis());
        tc.setParent("RELEASED");
        cinematiques.add(tc);

        if (dynamicEffect) {
            dynamicAnimator = new DynamicSmoothReleaseAnimation(getName());
            dynamicAnimator.start();
        }
        else {
            unlockTranslate();
            unPassiveTranslate();
        }

    }

    @Override
    public void onPress(MouseEvent e) {

        if (!isLockSelected()) {
            return;
        }

        if (isLockPassive()) {
            return;
        }

        if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
            return;
        }

        if (!isPassiveTranslate()) {
            startTranslate(new Point2D.Double(e.getX(), e.getY()));
            fireTranslateStarted();
        }
    }

    private boolean shifting = false;

    /**
     * @return the shifting
     */
    public boolean isShifting() {
        return shifting;
    }

    /**
     * @param shifting
     *            the shifting to set
     */
    private void setShifting(boolean shifting) {
        this.shifting = shifting;
    }

    public void shift(ShiftDirection shiftDirection) {
        if (!isShifting()) {
            new SmoothShift(shiftDirection).start();
        }
    }

    /**
     * shift with specified parameters
     * 
     * @param shiftDirection
     *            the shift direction
     * @param factor
     *            the shift count operation
     */
    public void shift(ShiftDirection shiftDirection, int factor) {
        if (isShifting()) {
            smoothShift.interrupt();
            try {
                smoothShift.join();
            }
            catch (InterruptedException e) {
            }
        }

        if (!isShifting()) {
            smoothShift = new SmoothShift(shiftDirection, factor);
            smoothShift.start();
        }
    }

    /**
     * join for current shifting.<br>
     * wait the current thread for the shifting finish (stop naturally or interrupted...)
     */
    public void joinShifting() {
        if (smoothShift != null && smoothShift.isAlive()) {
            try {
                smoothShift.join();
            }
            catch (InterruptedException e) {
            }
        }
    }

    /**
     * interrupt current shifting.<br>
     */
    public void interruptShifting() {
        if (smoothShift != null && smoothShift.isAlive()) {
            try {
                smoothShift.interrupt();
            }
            catch (Exception e) {
            }
        }
    }

    /**
     * shift with specified parameters
     * 
     * @param shiftDirection
     *            the shift direction
     * @param velocity
     *            the shift velocity
     */
    public void shift(ShiftDirection shiftDirection, ShiftVelocity velocity) {
        if (isShifting()) {
            smoothShift.interrupt();
            try {
                smoothShift.join();
            }
            catch (InterruptedException e) {
            }
        }

        if (!isShifting()) {
            smoothShift = new SmoothShift(shiftDirection, velocity);
            smoothShift.start();
        }
    }

    /**
     * run a new smooth shift
     */
    class SmoothShift extends Thread {

        /** shift direction */
        private ShiftDirection direction;

        /** factor */
        private int factor = 5;

        /** shift velocity */
        private ShiftVelocity velocity = ShiftVelocity.Slow;

        /**
         * create new smooth shift
         * 
         * @param direction
         */
        public SmoothShift(ShiftDirection direction) {
            super();
            this.direction = direction;
        }

        /**
         * create new smooth shift
         * 
         * @param direction
         */
        public SmoothShift(ShiftDirection direction, int factor) {
            this.direction = direction;
            this.factor = factor;
        }

        /**
         * create new smooth shift
         * 
         * @param direction
         * @param velocity
         */
        public SmoothShift(ShiftDirection direction, ShiftVelocity velocity) {
            this.direction = direction;
            this.velocity = velocity;
        }

        @Override
        public void run() {
            try {
                setShifting(true);
                startTranslate(new Point2D.Double(0, 0));
                fireTranslateStarted();

                int sleep = velocity.getVelocity();

                int fragment = 20;
                int deltaY = getProjection().getView().getDevice2D()
                        .getDeviceHeight()
                        / fragment;
                int deltaX = getProjection().getView().getDevice2D()
                        .getDeviceWidth()
                        / fragment;

                if (direction == ShiftDirection.North) {
                    for (int i = factor; i > 0; i--) {
                        processTranslate(0, deltaY);
                        fireTranslate();
                        getProjection().getView().repaint();
                        Thread.sleep(sleep);
                    }
                    interrupt();
                }
                else if (direction == ShiftDirection.South) {
                    for (int i = factor; i > 0; i--) {
                        processTranslate(0, -deltaY);
                        fireTranslate();
                        getProjection().getView().repaint();
                        Thread.sleep(sleep);
                    }
                    interrupt();
                }
                else if (direction == ShiftDirection.West) {
                    for (int i = factor; i > 0; i--) {
                        processTranslate(deltaX, 0);
                        fireTranslate();
                        getProjection().getView().repaint();
                        Thread.sleep(sleep);
                    }
                    interrupt();
                }
                else if (direction == ShiftDirection.East) {
                    for (int i = factor; i > 1; i--) {
                        processTranslate(-deltaX, 0);
                        fireTranslate();
                        getProjection().getView().repaint();
                        Thread.sleep(sleep);
                    }
                    interrupt();
                }

                setShifting(false);
                stopTranslate(0, 0);
                fireTranslateStopped();
                getProjection().getView().repaint();

            }
            catch (InterruptedException e) {
                setShifting(false);
                stopTranslate(0, 0);
                fireTranslateStopped();
                Thread.currentThread().interrupt();
            }
        }
    }


    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
    }

    /**
     * get the translate start x in device coordinate
     * 
     * @return device start x
     */
    public int getTranslateStartDeviceX() {
        return translateStartX;
    }

    

    /**
     * get the translate start y in device coordinate
     * 
     * @return device start y
     */
    public int getTranslateStartDeviceY() {
        return translateStartY;
    }

    
    /**
     * get the translate start point in device coordinate
     * 
     * @return device start point
     */
    public Point2D getTranslateStartDevicePoint() {
        return new Point2D.Double(translateStartX, translateStartY);
    }

    /**
     * get Translate start user point
     * 
     * @return translate start user point
     */
    public Point2D getTranslateStartUserPoint() {
        return getProjection().pixelToUser(getTranslateStartDevicePoint());
    }

    /**
     * get Translate start user x
     * 
     * @return translate start user x
     */
    public double getTranslateStartUserX() {
        return getTranslateStartUserPoint().getX();
    }

    /**
     * get Translate start user y
     * 
     * @return translate start user y
     */
    public double getTranslateStartUserY() {
        return getTranslateStartUserPoint().getY();
    }

    /**
     * get the translate current x in device coordinate
     * 
     * @return device current x
     */
    public int getTranslateCurentDeviceX() {
        return translateCurentX;
    }


    /**
     * get the translate current device y
     * 
     * @return device current y
     */
    public int getTranslateCurentDeviceY() {
        return translateCurentY;
    }

    /**
     * get Translate current device point
     * 
     * @return translate current device point
     */
    public Point2D getTranslateCurrentDevicePoint() {
        return new Point2D.Double(translateCurentX, translateCurentY);
    }

    /**
     * get Translate current user point
     * 
     * @return translate current user point
     */
    public Point2D getTranslateCurrentUserPoint() {
        return getProjection().pixelToUser(getTranslateCurrentDevicePoint());
    }

    /**
     * get Translate current user x
     * 
     * @return translate current user x
     */
    public double getTranslateCurrentUserX() {
        return getTranslateCurrentUserPoint().getX();
    }

    /**
     * get Translate current user y
     * 
     * @return translate current user y
     */
    public double getTranslateCurrentUserY() {
        return getTranslateCurrentUserPoint().getY();
    }

    /**
     * @return the translateDx
     */
    public double getTranslateDx() {
        return translateDx;
    }

    /**
     * @return the translateDy
     */
    public double getTranslateDy() {
        return translateDy;
    }

}
