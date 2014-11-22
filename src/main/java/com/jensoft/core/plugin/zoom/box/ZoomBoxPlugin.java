/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.box;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.PluginException;
import com.jensoft.core.plugin.metrics.format.IMetricsFormat;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>ZoomBoxPlugin</code>
 * 
 * @author sebastien janaud
 */
public class ZoomBoxPlugin extends AbstractPlugin implements
        AbstractPlugin.OnPressListener, AbstractPlugin.OnReleaseListener,
        AbstractPlugin.OnDragListener {

    /** zoom box draw color */
    private Color zoomBoxDrawColor = RosePalette.LEMONPEEL;

    /** zoom box fill color */
    private Color zoomBoxFillColor = RosePalette.CALYPSOBLUE;

    /** zoom box transaction lock */
    private boolean lockZoomingTransaction = false;

    /** zoom box start x coordinate */
    private int zoomBoxStartX;

    /** zoom box start y coordinate */
    private int zoomBoxStartY;

    /** zoom box current x coordinate */
    private int zoomBoxCurrentX;

    /** zoom box current y coordinate */
    private int zoomBoxCurrentY;

    /** zoom box width */
    private int zoomBoxWidth;

    /** zoom box height */
    private int zoomBoxHeight;

    /** dynamic effect option */
    private boolean dynEffect = true;

    /** lock/unlock tool action */
    private ActionListener zoomLockAction;

    /** zoom history */
    private List<BoundBox> zoomHistory = new Vector<BoundBox>();

    /** zoom box shape */
    private Shape shapeZoomBox;

    /** current x coordinate for zoom effect */
    private double zoomEffectX;

    /** current y coordinate for zoom effect */
    private double zoomEffectY;

    /** zoom effect width */
    private double zoomEffectWidth;

    /** zoom effect height */
    private double zoomEffectHeight;

    /** user window coordinate of the corner top left */
    private Point2D userWindowMinxMaxY;

    /** user window coordinate of the corner bottom right */
    private Point2D userWindowMaxXMinY;

    /** user start zoom point */
    private Point2D userWindowStartPoint;

    /** user end zoom point */
    private Point2D userWindowCurrentPoint;

    /** zoom box lock for effect transaction */
    private boolean lockEffect = false;

    /** zoom box current history index */
    private int curentBoxIndex = -1;

    /** dynamic zoom sequence */
    private Vector<DynamicSequenceItem> dynSequence = new Vector<DynamicSequenceItem>();

    /** zoom history sequence player */
    private SequencePlayer sequencePlayer;

    /** listeners */
    private EventListenerList zoomBoxListenerList;

    /** default box type BoxXY, zoom into X and Y dimension */
    private BoxType boxType = BoxType.BoxXY;

    /** minimal acceptance criteria is that minimal frame on x */
    private int minimalDelatX = 16;

    /** minimal acceptance criteria is that minimal frame on y */
    private int minimalDeltaY = 16;

    /** i metrics format to format zoom box info */
    private IMetricsFormat zoomBoxInfoMetricsFormat;

    /** font for display info */
    private Font font = new Font("Tahoma", Font.PLAIN, 10);

    /** zoom transaction type */
    private TransactionType transactionType = TransactionType.DeviceTransaction;

    /** max history */
    private int maxHistory = 8;

    /** zoom in thread */
    private ZoomIn zoomIn;

    /** zoom out thread */
    private ZoomOut zoomOut;

    /** BoxType behavior */
    public enum BoxType {
        BoxXY, BoxX, BoxY;
    }

    /**
     * transaction type indicates the system coordinate to use to make
     * transaction
     * 
     * @author sebastien Janaud
     */
    public enum TransactionType {
        DeviceTransaction, UserTransaction;
    }
    


    /**
     * create a new ZoomBox
     */
    public ZoomBoxPlugin() {
        setSelectable(true);
        setName(ZoomBoxPlugin.class.getCanonicalName());
        zoomBoxListenerList = new EventListenerList();
        zoomLockAction = new ZoomLockAction();
        setOnDragListener(this);
        setOnPressListener(this);
        setOnReleaseListener(this);
        setTextAntialising(TextAntialiasing.On);
        setPriority(10000);
        
        if(getPropertyFileName() != null){
            
        }
        else{
            try {
                Properties p = getProperties("plugin-zoombox.properties");
                setProperties(p);
               // String lockLabel = p.getProperty("zoom.box.lock");
               // System.out.println("label found :"+lockLabel);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
      
    }

    /**
     * <code>UserZoomBox</code>
     * create a user zoom
     * 
     * @author sebastien janaud
     */
    public class UserZoomBox {

        private double minX, maxX, minY, maxY;
        private int startDelay = 0;

        private Runner zoomInRunner;

        /**
         * create a user zoom with specified parameters
         * Use with caution, not thread safe.
         * 
         * @param minX
         * @param maxX
         * @param minY
         * @param maxY
         */
        public UserZoomBox(double minX, double maxX, double minY, double maxY) {
            super();
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }

        /**
         * start the user zoom and return when zoom in runner is joined
         */
        public void zoomIn() {
            zoomInRunner = new Runner();
            zoomInRunner.start();
            try {
                zoomInRunner.join();
            }
            catch (InterruptedException e) {
            }
        }

        /**
         * start the user zoom and return when zoom in runner is joined
         * 
         * @param startDelay
         *            start delay before execute zoom
         */
        public void zoomIn(int startDelay) {
            this.startDelay = startDelay;
            zoomInRunner = new Runner();
            zoomInRunner.start();
            try {
                zoomInRunner.join();
            }
            catch (InterruptedException e) {
            }
        }

        /**
         * start zoom out
         */
        public void zoomOut() {
            lockSelected();
            processZoomOut();
            joinZoomOut();
            fireZoomOut();
            unlockSelected();
        }

        /**
         * execute programatically zoom
         * 
         * @author sebastien janaud
         */
        class Runner extends Thread {

            @Override
            public void run() {
                try {

                    Thread.sleep(startDelay);
                    lockSelected();
                    setTransactionType(TransactionType.UserTransaction);
                    processZoomStart(new Point2D.Double(minX, maxY));
                    double deltaX = maxX - minX;
                    double deltaY = maxY - minY;
                    int step = 20;
                    double stepX = deltaX / step;
                    double stepY = deltaY / step;
                    for (double i = 0; i < step; i++) {
                        double w = minX + stepX * i;
                        double h = maxY - stepY * i;
                        Point2D p = new Point2D.Double(w, h);
                        processZoomBound(p);
                        getProjection().getView2D().repaintDevice();
                        Thread.sleep(10);
                    }
                    Thread.sleep(300);
                    processZoomIn();
                    // fireZoomIn();
                    joinZoomIn();

                    setTransactionType(TransactionType.DeviceTransaction);
                    unlockSelected();
                    
                }
                catch (InterruptedException e) {
                }
            }
        }

    }

    /**
     * execute zoom in the user transaction type with the specified user coordinates
     * 
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     */
    public UserZoomBox createUserZoom(final double minX, final double maxX, final double minY, final double maxY) {
        return new UserZoomBox(minX, maxX, minY, maxY);
    }

    /**
     * @return the transactionType
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType
     *            the transactionType to set
     */
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * return true if the sequence player is alive
     * <p>
     * the sequence player is thread that execute all zoom history
     * 
     * @return true if the sequence player is alive
     */
    public boolean isSequencePlaying() {
        if (sequencePlayer != null && sequencePlayer.isAlive()) {
            return true;
        }
        return false;
    }

    /**
     * get the format to use for display metrics bound info
     * 
     * @return the zoomBoxInfoMetricsFormat
     */
    public IMetricsFormat getZoomBoxInfoMetricsFormat() {
        return zoomBoxInfoMetricsFormat;
    }

    /**
     * set the format to use for display metrics bound info
     * 
     * @param zoomBoxInfoMetricsFormat
     *            the zoomBoxInfoMetricsFormat to set
     */
    public void setZoomBoxInfoMetricsFormat(
            IMetricsFormat zoomBoxInfoMetricsFormat) {
        this.zoomBoxInfoMetricsFormat = zoomBoxInfoMetricsFormat;
    }

    /**
     * add plug in listener
     * 
     * @param listener
     */
    public void addZoomBoxListener(ZoomBoxListener listener) {
        zoomBoxListenerList.add(ZoomBoxListener.class, listener);
    }

    /**
     * remove plug in listener
     * 
     * @param listener
     */
    public void removeZoomBoxListener(ZoomBoxListener listener) {
        zoomBoxListenerList.remove(ZoomBoxListener.class, listener);
    }

    /**
     * fire box started
     */
    public void fireZoomStart() {
        Object[] listeners = zoomBoxListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == ZoomBoxListener.class) {
                    ((ZoomBoxListener) listeners[i + 1])
                            .zoomStart(new ZoomBoxEvent(this));
                }
            }
        }
    }

    /**
     * fire box bounded
     */
    public void fireZoomBounded() {
        Object[] listeners = zoomBoxListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == ZoomBoxListener.class) {
                    ((ZoomBoxListener) listeners[i + 1])
                            .zoomBounded(new ZoomBoxEvent(this));
                }
            }
        }
    }

    /**
     * fire process zoom
     */
    public void fireZoomIn() {
        Object[] listeners = zoomBoxListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == ZoomBoxListener.class) {
                    ((ZoomBoxListener) listeners[i + 1])
                            .zoomIn(new ZoomBoxEvent(this));
                }
            }
        }
    }

    /**
     * fire process forward
     */
    public void fireZoomOut() {
        Object[] listeners = zoomBoxListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == ZoomBoxListener.class) {
                    ((ZoomBoxListener) listeners[i + 1])
                            .zoomOut(new ZoomBoxEvent(this));
                }
            }
        }
    }

    /**
     * fire box history
     */
    public void fireZoomHistory() {
        Object[] listeners = zoomBoxListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == ZoomBoxListener.class) {
                    ((ZoomBoxListener) listeners[i + 1])
                            .zoomHistory(new ZoomBoxEvent(this));
                }
            }
        }
    }

    /**
     * fire box history
     */
    public void fireZoomClearHistory() {
        Object[] listeners = zoomBoxListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == ZoomBoxListener.class) {
                    ((ZoomBoxListener) listeners[i + 1])
                            .zoomClearHistory(new ZoomBoxEvent(this));
                }
            }
        }
    }

    public void playCurrentSequence(ZoomPlayerCallback callback) {

        if (sequencePlayer != null && sequencePlayer.isAlive()) {
            sequencePlayer.interrupt();
        }

        sequencePlayer = new SequencePlayer(callback);
        sequencePlayer.start();
    }

    /**
     * tagging interface that defines a player bound callback
     * 
     * @author Sebastien Janaud
     */
    public interface ZoomPlayerCallback {

        public void play(BoundBox bounbdBox);
    }

    /**
     * sequence player replay zoom history in specified call back specified
     * parameters
     * 
     * @author Sebastien Janaud
     */
    class SequencePlayer extends Thread {

        private ZoomPlayerCallback playerCallback;

        public SequencePlayer(ZoomPlayerCallback playerCallback) {
            this.playerCallback = playerCallback;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10);

                int initialIndex = 0;

                processZoomHistory(0);
                fireZoomHistory();
                Thread.sleep(1000);

                int index = initialIndex + 1;
                while (index < zoomHistory.size()) {

                    historyNext();
                    getProjection().getDevice2D().repaintDevice();
                    int i = getCurentBoxIndex();

                    if (playerCallback != null) {
                        BoundBox bb = zoomHistory.get(i);
                        playerCallback.play(bb);
                    }

                    index++;

                    fireZoomHistory();
                    Thread.sleep(1000);
                }

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

    }

    /**
     * lock action, use to lock the zoom box
     */
    public class ZoomLockAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isLockSelected()) {
                unlockSelected();
            }
            else {
                lockSelected();
            }
        }

    }

    /**
     * get action to manually lock/unlock the zoom box
     * 
     * @return the lock box action
     */
    public ActionListener getZoomLockAction() {
        return zoomLockAction;
    }

   

    /**
     * synchronize boxes when new bounded box occurs in any registered box plug
     * in
     * 
     * @param boxes
     */
    public static ZoomBoxSynchronizer createSynchronizer(ZoomBoxPlugin... boxes) {
        ZoomBoxSynchronizer synchronizer = new ZoomBoxSynchronizer(boxes);
        return synchronizer;
    }

    /**
     * synchronize boxes when new bounded box occurs in any registered box plug
     * in
     * 
     * @param boxes
     */
    public static ZoomBoxSynchronizer createSynchronizer(
            List<ZoomBoxPlugin> boxes) {
        ZoomBoxSynchronizer synchronizer = new ZoomBoxSynchronizer(boxes);
        return synchronizer;
    }

    /**
     * return true if forward condition are valid , false otherwise
     * <p>
     * process zoom back is valid on released when
     * <ul>
     * <li>current x is smaller than start x in device coordinate</li>
     * <li>current y is smaller than start y in device coordinate</li>
     * <ul>
     * 
     * @return true if forward condition are valid , false otherwise
     */
    private boolean isForwardCondition() {

        if (transactionType == TransactionType.UserTransaction)
            return true;

        if (getBoxType() == BoxType.BoxXY) {
            if (zoomBoxCurrentX < zoomBoxStartX
                    && zoomBoxCurrentY < zoomBoxStartY) {
                return true;
            }
        }
        else if (getBoxType() == BoxType.BoxX) {
            if (zoomBoxCurrentX < zoomBoxStartX) {
                return true;
            }
        }
        else if (getBoxType() == BoxType.BoxY) {
            if (zoomBoxCurrentY < zoomBoxStartY) {
                return true;
            }
        }
        return false;
    }

    /**
     * join the zoom in thread
     */
    public void joinZoomIn() {
        if (zoomIn != null) {
            try {
                // System.out.println("join zoom in");
                zoomIn.join();
                // System.out.println("join zoom in ok");
            }
            catch (InterruptedException e) {
            }
        }
    }

    /**
     * join the zoom out thread
     */
    public void joinZoomOut() {
        if (zoomOut != null) {
            try {
                zoomOut.join();
            }
            catch (InterruptedException e) {
            }
        }
    }

    /**
     * process zoom in current transaction type
     * from current bounding box to new bounding box
     */
    public void processZoomIn() {

        try {
            Projection window2D = getProjection();
            if (window2D instanceof Projection.Linear) {
                Projection.Linear wl = (Projection.Linear) window2D;

                // if (transactionType == TransactionType.UserTransaction) {
                // if (getBoxType() == BoxType.BoxXY) {
                // wl.bound(startBox.getX(), currentBox.getX(),
                // currentBox.getY(), startBox.getY());
                // }
                // else if (getBoxType() == BoxType.BoxX) {
                // wl.bound(startBox.getX(), currentBox.getX(),
                // window2D.getMinY(), window2D.getMaxY());
                // }
                // else if (getBoxType() == BoxType.BoxY) {
                // wl.bound(window2D.getMinX(), window2D.getMaxX(),
                // currentBox.getY(), startBox.getY());
                // }
                //
                // getWindow2D().getView2D().repaintDevice();
                // unlockZoomTransaction();
                //
                // if (zoomHistory != null && startBox != null
                // && currentBox != null) {
                //
                // if (zoomHistory.size() >= maxHistory) {
                // clearHistory();
                // }
                //
                // if (zoomHistory.size() == 0) {
                // zoomHistory.add(new BoundBox(wl.getInitialMinX(), wl
                // .getInitialMaxX(), wl.getInitialMinY(), wl
                // .getInitialMaxY()));
                // }
                //
                // if (getBoxType() == BoxType.BoxXY) {
                // zoomHistory.add(new BoundBox(startBox.getX(),
                // currentBox.getX(), currentBox.getY(), startBox
                // .getY()));
                // }
                // else if (getBoxType() == BoxType.BoxX) {
                // zoomHistory.add(new BoundBox(startBox.getX(),
                // currentBox.getX(), window2D.getMinY(), window2D
                // .getMaxY()));
                // }
                // else if (getBoxType() == BoxType.BoxY) {
                // zoomHistory.add(new BoundBox(window2D.getMinX(),
                // window2D.getMaxX(), currentBox.getY(), startBox
                // .getY()));
                // }
                // setCurentBoxIndex(zoomHistory.size() - 1);
                //
                // }
                // }
                // else if (transactionType == TransactionType.DeviceTransaction) {

                if (isValidateBound()) {

                    Point2D deviceStart = new Point2D.Double(zoomBoxStartX,
                                                             zoomBoxStartY);
                    Point2D deviceCurrent = new Point2D.Double(zoomBoxCurrentX,
                                                               zoomBoxCurrentY);

                    userWindowStartPoint = window2D.pixelToUser(deviceStart);
                    userWindowCurrentPoint = window2D.pixelToUser(deviceCurrent);

                    if (userWindowStartPoint.getX() == Double.POSITIVE_INFINITY
                            || userWindowStartPoint.getY() == Double.POSITIVE_INFINITY
                            || userWindowCurrentPoint.getX() == Double.POSITIVE_INFINITY
                            || userWindowCurrentPoint.getY() == Double.POSITIVE_INFINITY) {
                        return;
                    }

                    if (userWindowStartPoint.getX() == Double.NEGATIVE_INFINITY
                            || userWindowStartPoint.getY() == Double.NEGATIVE_INFINITY
                            || userWindowCurrentPoint.getX() == Double.NEGATIVE_INFINITY
                            || userWindowCurrentPoint.getY() == Double.NEGATIVE_INFINITY) {
                        return;
                    }

                    if (dynEffect) {
                        zoomIn = new ZoomIn();
                        zoomIn.start();
                    }
                    else {
                        if (getBoxType() == BoxType.BoxXY) {
                            wl.bound(userWindowStartPoint.getX(),
                                     userWindowCurrentPoint.getX(),
                                     userWindowCurrentPoint.getY(),
                                     userWindowStartPoint.getY());
                        }
                        else if (getBoxType() == BoxType.BoxX) {
                            wl.bound(userWindowStartPoint.getX(),
                                     userWindowCurrentPoint.getX(),
                                     window2D.getMinY(), window2D.getMaxY());
                        }
                        else if (getBoxType() == BoxType.BoxY) {
                            wl.bound(window2D.getMinX(), window2D.getMaxX(),
                                     userWindowCurrentPoint.getY(),
                                     userWindowStartPoint.getY());
                        }

                        getProjection().getView2D().repaintDevice();
                        unlockZoomTransaction();
                    }

                    if (zoomHistory != null && userWindowStartPoint != null
                            && userWindowCurrentPoint != null) {

                        if (zoomHistory.size() >= maxHistory) {
                            processZoomClearHistory();

                        }

                        if (zoomHistory.size() == 0) {
                            zoomHistory.add(new BoundBox(wl.getInitialMinX(),
                                                         wl.getInitialMaxX(), wl.getInitialMinY(),
                                                         wl.getInitialMaxY()));
                        }

                        if (getBoxType() == BoxType.BoxXY) {
                            zoomHistory.add(new BoundBox(userWindowStartPoint
                                    .getX(), userWindowCurrentPoint.getX(),
                                                         userWindowCurrentPoint.getY(),
                                                         userWindowStartPoint.getY()));
                        }
                        else if (getBoxType() == BoxType.BoxX) {
                            zoomHistory.add(new BoundBox(userWindowStartPoint
                                    .getX(), userWindowCurrentPoint.getX(),
                                                         window2D.getMinY(), window2D.getMaxY()));
                        }
                        else if (getBoxType() == BoxType.BoxY) {
                            zoomHistory.add(new BoundBox(window2D.getMinX(),
                                                         window2D.getMaxX(), userWindowCurrentPoint
                                                                 .getY(), userWindowStartPoint
                                                                 .getY()));
                        }
                        setCurentBoxIndex(zoomHistory.size() - 1);

                    }
                }
                // }

            }
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        finally {
            fireZoomIn();
        }

    }

    /**
     * run a zoom effect back from dynamic sequence.
     */
    public class ZoomOut extends Thread {

        @Override
        public void run() {
            Projection window2D = getProjection();
            if (window2D instanceof Projection.Linear) {
                Projection.Linear wl = (Projection.Linear) window2D;
                if (dynSequence.size() > 0) {
                    try {

                        for (int j = dynSequence.size() - 1; j >= 0; j--) {

                            DynamicSequenceItem q = dynSequence.get(j);

                            zoomEffectX = q.zoomEffectX;
                            zoomEffectY = q.zoomEffectY;
                            zoomEffectWidth = q.zoomEffectWidth;
                            zoomEffectHeight = q.zoomEffectHeight;

                            if (getBoxType() == BoxType.BoxXY) {
                                wl.bound(q.up1.getX(), q.up3.getX(),
                                         q.up3.getY(), q.up1.getY());
                            }
                            else if (getBoxType() == BoxType.BoxX) {
                                wl.bound(q.up1.getX(), q.up3.getX(),
                                         window2D.getMinY(), window2D.getMaxY());
                            }
                            else if (getBoxType() == BoxType.BoxY) {
                                wl.bound(window2D.getMinX(),
                                         window2D.getMaxX(), q.up3.getY(),
                                         q.up1.getY());
                            }

                            getProjection().getView2D().repaintDevice();
                            Thread.sleep(25);
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        wl.bound(userWindowMinxMaxY.getX(),
                                 userWindowMaxXMinY.getX(),
                                 userWindowMaxXMinY.getY(),
                                 userWindowMinxMaxY.getY());
                        getProjection().getView2D().repaintDevice();
                        dynSequence.clear();
                        unlockZoomTransaction();
                    }
                }
            }
        }
    }

    /**
     * run a zoom effect from the dynamic sequence
     */
    public class ZoomIn extends Thread {

        @Override
        public void run() {
            // System.out.println("process zoom in thread-start");
            Projection window2D = getProjection();
            if (window2D instanceof Projection.Linear) {
                Projection.Linear wl = (Projection.Linear) window2D;
                try {

                    lockEffect = true;

                    userWindowMinxMaxY = new Point2D.Double(window2D.getMinX(),
                                                            window2D.getMaxY());
                    userWindowMaxXMinY = new Point2D.Double(window2D.getMaxX(),
                                                            window2D.getMinY());

                    Point2D devicepd2_1 = new Point2D.Double(zoomBoxStartX,
                                                             zoomBoxStartY);
                    userWindowStartPoint = window2D.pixelToUser(devicepd2_1);

                    Point2D devicepd2_2 = new Point2D.Double(zoomBoxCurrentX,
                                                             zoomBoxCurrentY);
                    userWindowCurrentPoint = window2D.pixelToUser(devicepd2_2);

                    int pas = 20;

                    double deltaNorth = zoomBoxStartY / pas;
                    double deltaEast = (getProjection().getView2D().getDevice2D()
                            .getDeviceWidth() - zoomBoxCurrentX)
                            / pas;
                    double deltaWest = zoomBoxStartX / pas;
                    double deltaSouth = (getProjection().getView2D()
                            .getDevice2D().getDeviceHeight() - zoomBoxCurrentY)
                            / pas;

                    double izoomEffectX;
                    double izoomEffectY;
                    double izoomEffectWidth;
                    double izoomEffectHeight;
                    dynSequence.clear();

                    for (int i = 1; i <= pas; i++) {
                        izoomEffectX = zoomBoxStartX - i * deltaWest;
                        izoomEffectY = zoomBoxStartY - i * deltaNorth;
                        izoomEffectWidth = zoomBoxWidth + i * deltaWest + i
                                * deltaEast;
                        izoomEffectHeight = zoomBoxHeight + i * deltaNorth + i
                                * deltaSouth;

                        Point2D dp1 = new Point2D.Double(i * deltaWest, i
                                * deltaNorth);
                        Point2D dp2 = new Point2D.Double(i * deltaWest,
                                                         getProjection().getView2D().getDevice2D()
                                                                 .getDeviceHeight()
                                                                 - i * deltaSouth);
                        Point2D dp3 = new Point2D.Double(getProjection()
                                .getView2D().getDevice2D().getDeviceWidth()
                                - i * deltaEast, getProjection().getView2D()
                                .getDevice2D().getDeviceHeight()
                                - i * deltaSouth);
                        Point2D dp4 = new Point2D.Double(getProjection()
                                .getView2D().getDevice2D().getDeviceWidth()
                                - i * deltaEast, i * deltaNorth);

                        Point2D up1 = window2D.pixelToUser(dp1);
                        Point2D up2 = window2D.pixelToUser(dp2);
                        Point2D up3 = window2D.pixelToUser(dp3);
                        Point2D up4 = window2D.pixelToUser(dp4);

                        DynamicSequenceItem q = null;
                        q = new DynamicSequenceItem(izoomEffectX, izoomEffectY,
                                                    izoomEffectWidth, izoomEffectHeight, up1, up2,
                                                    up3, up4);
                        dynSequence.add(q);

                    }

                    for (int j = 0; j < dynSequence.size(); j++) {

                        DynamicSequenceItem q = dynSequence.get(j);

                        zoomEffectX = q.zoomEffectX;
                        zoomEffectY = q.zoomEffectY;
                        zoomEffectWidth = q.zoomEffectWidth;
                        zoomEffectHeight = q.zoomEffectHeight;

                        if (getBoxType() == BoxType.BoxXY) {
                            wl.bound(q.up1.getX(), q.up3.getX(), q.up3.getY(),
                                     q.up1.getY());
                        }
                        else if (getBoxType() == BoxType.BoxX) {
                            wl.bound(q.up1.getX(), q.up3.getX(),
                                     window2D.getMinY(), window2D.getMaxY());
                        }
                        else if (getBoxType() == BoxType.BoxY) {
                            wl.bound(window2D.getMinX(), window2D.getMaxX(),
                                     q.up3.getY(), q.up1.getY());
                        }

                        getProjection().getView2D().getDevice2D().repaintDevice();
                        Thread.sleep(25);
                    }

                }
                catch (Exception e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                finally {
                    lockEffect = false;
                    if (getBoxType() == BoxType.BoxXY) {
                        wl.bound(userWindowStartPoint.getX(),
                                 userWindowCurrentPoint.getX(),
                                 userWindowCurrentPoint.getY(),
                                 userWindowStartPoint.getY());
                    }
                    else if (getBoxType() == BoxType.BoxX) {
                        wl.bound(userWindowStartPoint.getX(),
                                 userWindowCurrentPoint.getX(),
                                 window2D.getMinY(), window2D.getMaxY());
                    }
                    else if (getBoxType() == BoxType.BoxY) {
                        wl.bound(window2D.getMinX(), window2D.getMaxX(),
                                 userWindowCurrentPoint.getY(),
                                 userWindowStartPoint.getY());
                    }

                    getProjection().getView2D().getDevice2D().repaintDevice();
                    unlockZoomTransaction();
                    // System.out.println("process zoom in thread-end");
                }
            }

        }
    }

    /**
     * dynamic sequence item
     */
    class DynamicSequenceItem {

        double zoomEffectX;
        double zoomEffectY;
        double zoomEffectWidth;
        double zoomEffectHeight;

        Point2D up1;
        Point2D up2;
        Point2D up3;
        Point2D up4;

        public DynamicSequenceItem(double zoomEffectX, double zoomEffectY,
                double zoomEffectWidth, double zoomEffectHeight, Point2D up1,
                Point2D up2, Point2D up3, Point2D up4) {
            super();
            this.zoomEffectX = zoomEffectX;
            this.zoomEffectY = zoomEffectY;
            this.zoomEffectWidth = zoomEffectWidth;
            this.zoomEffectHeight = zoomEffectHeight;
            this.up1 = up1;
            this.up2 = up2;
            this.up3 = up3;
            this.up4 = up4;
        }
    }

    /**
     * start a zoom transaction
     */
    private void lockZoomingTransaction() {
        lockZoomingTransaction = true;
    }

    /**
     * stop zoom transaction
     */
    private void unlockZoomTransaction() {
        lockZoomingTransaction = false;
    }

    /**
     * return true is zoom transaction started, false otherwise
     * 
     * @return true if the zoom is lock execute
     */
    private boolean isLockZoomingTransaction() {
        return lockZoomingTransaction;
    }

   
    @Override
    public void onDrag(MouseEvent me) { if (!isLockSelected()) {
            return;
        }

        if (isLockPassive()) {
            return;
        }

        if (!isLockZoomingTransaction()) {
            return;
        }

        if (transactionType == TransactionType.UserTransaction) {
            return;
        }

        if (transactionType == TransactionType.DeviceTransaction) {
            processZoomBound(new Point2D.Double(me.getX(), me.getY()));
            // fireZoomBounded();
            getProjection().getView2D().repaintDevice();
        }

    }

    /**
     * bound zoom box for the current specified coordinate in the current transaction
     * 
     * @param currentBox
     *
     */
    public void processZoomBound(Point2D currentBox) {
    	
        if (transactionType == TransactionType.DeviceTransaction) {
            zoomBoxCurrentX = (int) currentBox.getX();
            zoomBoxCurrentY = (int) currentBox.getY();
        }
        else if (transactionType == TransactionType.UserTransaction) {
            Point2D deviceCurrentBox = getProjection().userToPixel(currentBox);
            if ((int) deviceCurrentBox.getY() < zoomBoxStartY) {
                throw new PluginException(
                                          "Bad User Zoom Transaction, invalid zoom user y value, y should be smaller than previous start y value");
            }
            if ((int) deviceCurrentBox.getX() < zoomBoxStartX) {
                throw new PluginException(
                                          "Bad User Zoom Transaction, invalid zoom user x value, x should be smaller than previous start x value");
            }

            zoomBoxCurrentX = (int) deviceCurrentBox.getX();
            zoomBoxCurrentY = (int) deviceCurrentBox.getY();
        }
        
        if (!isValidateBound()) {
        	
        }

        // new
        fireZoomBounded();
    }
    
    /**
     * get the zoom box start point in device coordinate
     * 
     * @return device start point
     */
    public Point2D getBoxStartDevicePoint() {
        return new Point2D.Double(zoomBoxStartX, zoomBoxStartY);
    }

    /**
     * get Bound Box current device point
     * 
     * @return bound box current device point
     */
    public Point2D getBoxCurrentDevicePoint() {
        return new Point2D.Double(zoomBoxCurrentX, zoomBoxCurrentY);
    }
    
    /**
     * get the zoom box start point in user coordinate
     * 
     * @return device start point
     */
    public Point2D getBoxStartUserPoint() {
        return getProjection().pixelToUser(getBoxStartDevicePoint());
    }

    /**
     * get Bound Box current user point
     * 
     * @return bound box current user point
     */
    public Point2D getBoxCurrentUserPoint() {
        return getProjection().pixelToUser(getBoxCurrentDevicePoint());
    }
    
    

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {

        if (!isLockSelected()) {
            return;
        }

        if (isLockPassive()) {
            return;
        }

        if (me.getModifiers() != InputEvent.BUTTON1_MASK) {
            return;
        }

        if (transactionType == TransactionType.UserTransaction) {
            return;
        }

        if (transactionType == TransactionType.DeviceTransaction) {
            processZoomStart(new Point2D.Double(me.getX(), me.getY()));
            // fireZoomStart();
        }

    }

    /**
     * start parameters for a start bound zoom box
     * 
     * @param startBox
     *            box start point coordinate in the current transaction type
     */
    public void processZoomStart(Point2D startBox) {

        if (transactionType == TransactionType.DeviceTransaction) {
            zoomBoxStartX = (int) startBox.getX();
            zoomBoxStartY = (int) startBox.getY();
            zoomBoxCurrentX = zoomBoxStartX;
            //zoomBoxCurrentY = zoomBoxStartX;
            zoomBoxCurrentY = zoomBoxStartY;

        }
        else if (transactionType == TransactionType.UserTransaction) {
            Point2D deviceStartBox = getProjection().userToPixel(startBox);
            zoomBoxStartX = (int) deviceStartBox.getX();
            zoomBoxStartY = (int) deviceStartBox.getY();
            zoomBoxCurrentX = zoomBoxStartX;
            //zoomBoxCurrentY = zoomBoxStartX;
            zoomBoxCurrentY = zoomBoxStartY;
        }

        // new
        fireZoomStart();

        lockZoomingTransaction();
    }

    

    // /**
    // * get the zoom box start x in user coordinate
    // *
    // * @return device start x
    // */
    // public double getBoxStartUserX() {
    // return getBoxStartUserPoint().getX();
    // }
    //
    // /**
    // * get the zoom box start y in user coordinate
    // *
    // * @return device start y
    // */
    // public double getBoxStartUserY() {
    // return getBoxStartUserPoint().getY();
    // }

    

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }

        if (isLockPassive()) {
            return;
        }

        if (!isLockZoomingTransaction()) {
            return;
        }

        if (transactionType == TransactionType.UserTransaction) {
            return;
        }

        if (transactionType == TransactionType.DeviceTransaction) {
            if (isForwardCondition()) {
                processZoomOut();
                fireZoomOut();
            }
            else if (isValidateBound()) {
                processZoomIn();
                // fireZoomIn();
            }
            else if (!isValidateBound()) {
                fireZoomIn();
            }
        }

    }

    /**
     * process effect backward from last zooming transaction
     */
    public void processZoomOut() {
        zoomOut = new ZoomOut();
        zoomOut.start();
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {

        if (viewPart == ViewPart.Device) {

            if (!isLockSelected()) {
                return;
            }

            if (!isLockZoomingTransaction()) {
                return;
            }

            g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                 java.awt.AlphaComposite.SRC_OVER, 1.0f));

            if (isValidateBound()) {
                if (!lockEffect) {
                    paintBox(g2d);
                    paintMetricsInfo(g2d);

                }
                else {
                    paintEffect(g2d);
                }
            }

        }

        // if (viewPart != WindowPart.Device) {
        // if (isLockZoomingTransaction()) {
        // if (viewPart == WindowPart.South) {
        // System.out.println("call paint from south !! ");
        //
        // int startXSouthCoordinate = getWindow2D().getView2D()
        // .getPlaceHolderAxisWEST() + getBoxStartDeviceX();
        // int currentXSouthCoordinate = getWindow2D().getView2D()
        // .getPlaceHolderAxisWEST() + getBoxCurrentDeviceX();
        //
        // g2d.setColor(Color.RED);
        // g2d.drawLine(startXSouthCoordinate, 10,
        // startXSouthCoordinate, 30);
        // g2d.drawLine(currentXSouthCoordinate, 10,
        // currentXSouthCoordinate, 30);
        // }
        // }
        //
        // }

    }

    /**
     * paint zooming effect
     * 
     * @param g2d
     */
    private void paintEffect(Graphics2D g2d) {

        if (zoomBoxDrawColor != null) {
            g2d.setColor(zoomBoxDrawColor);
        }
        else {
            g2d.setColor(getThemeColor());
        }

        Shape shapeEffect = null;
        if (getBoxType() == BoxType.BoxXY) {
            shapeEffect = new RoundRectangle2D.Double(zoomEffectX, zoomEffectY,
                                                      zoomEffectWidth, zoomEffectHeight, 10, 10);
        }
        else if (getBoxType() == BoxType.BoxX) {
            shapeEffect = new RoundRectangle2D.Double(zoomEffectX, 0,
                                                      zoomEffectWidth, getProjection().getDevice2D()
                                                              .getDeviceHeight(), 10, 10);
        }
        else if (getBoxType() == BoxType.BoxY) {
            shapeEffect = new RoundRectangle2D.Double(0, zoomEffectY,
                                                      getProjection().getDevice2D().getDeviceWidth(),
                                                      zoomEffectHeight, 10, 10);
        }

        if (zoomBoxFillColor != null) {
            g2d.setColor(zoomBoxFillColor);
        }
        else {
            g2d.setColor(getThemeColor());
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    0.3f));
        g2d.fill(shapeEffect);

        if (zoomBoxDrawColor != null) {
            g2d.setColor(zoomBoxDrawColor);
        }
        else {
            g2d.setColor(getThemeColor());
        }
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
        g2d.draw(shapeEffect);
    }

    /**
     * paint bound box while bounding operation.
     * 
     * @param g2d
     */
    private void paintBox(Graphics2D g2d) {

        zoomBoxWidth = zoomBoxCurrentX - zoomBoxStartX;
        zoomBoxHeight = zoomBoxCurrentY - zoomBoxStartY;

        if (getBoxType() == BoxType.BoxXY) {
            shapeZoomBox = new Rectangle2D.Double(zoomBoxStartX, zoomBoxStartY,
                                                  zoomBoxWidth, zoomBoxHeight);
        }
        else if (getBoxType() == BoxType.BoxX) {
            shapeZoomBox = new Rectangle2D.Double(zoomBoxStartX, 0,
                                                  zoomBoxWidth, getProjection().getDevice2D().getDeviceHeight());

        }
        else if (getBoxType() == BoxType.BoxY) {
            shapeZoomBox = new Rectangle2D.Double(0, zoomBoxStartY,
                                                  getProjection().getDevice2D().getDeviceWidth(), zoomBoxHeight);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        if (zoomBoxDrawColor != null) {
            g2d.setColor(zoomBoxDrawColor);
        }
        else {
            g2d.setColor(getThemeColor());
        }
        g2d.draw(shapeZoomBox);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        if (zoomBoxFillColor != null) {
            g2d.setColor(zoomBoxFillColor);
        }
        else {
            g2d.setColor(getThemeColor());
        }
        g2d.fill(shapeZoomBox);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
    

    /**
     * return true if the transaction type is a user transaction else
     * if the transaction is device type return true if the bound is valid, false otherwise
     * 
     * @return true if the bound is valid, false otherwise
     */
    private boolean isValidateBound() {
        if (transactionType == TransactionType.UserTransaction) {
            return true;
        }

        if (getBoxType() == BoxType.BoxXY) {
            if (zoomBoxCurrentX > zoomBoxStartX + minimalDelatX
                    && zoomBoxCurrentY > zoomBoxStartY + minimalDeltaY) {
                return true;
            }
        }
        else if (getBoxType() == BoxType.BoxX) {
            if (zoomBoxCurrentX > zoomBoxStartX + minimalDelatX) {
                return true;
            }
        }
        else if (getBoxType() == BoxType.BoxY) {
            if (zoomBoxCurrentY > zoomBoxStartY + minimalDeltaY) {
                return true;
            }
        }
        // System.out.println("return false");
        return false;
    }

    /**
     * paint bound limits for current bound box
     * 
     * @param g2d
     */
    private void paintMetricsInfo(Graphics2D g2d) {
        Projection w2d = getProjection().getView2D().getActiveProjection();

        if (zoomBoxDrawColor != null) {
            g2d.setColor(zoomBoxDrawColor);
        }
        else {
            g2d.setColor(getThemeColor());
        }

        Point2D deviceMinXMaxY = new Point2D.Double(zoomBoxStartX,
                                                    zoomBoxStartY);
        Point2D deviceMaxXMinY = new Point2D.Double(zoomBoxCurrentX,
                                                    zoomBoxCurrentY);

        Point2D p2dU1 = w2d.pixelToUser(deviceMinXMaxY);
        Point2D p2dU2 = w2d.pixelToUser(deviceMaxXMinY);

        Double userMinX = p2dU1.getX();
        Double userMaxY = p2dU1.getY();
        Double userMaxX = p2dU2.getX();
        Double userMinY = p2dU2.getY();

        DecimalFormat dc = new DecimalFormat("#.##");

        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int hgt = metrics.getHeight();

        // lateral symbol

        /*
         * int sminxWidth = metrics.stringWidth(dc.format(userMinX));
         * g2d.drawString(dc.format(userMinX),
         * (int)(zoomBoxStartX-sminxWidth),zoomBoxStartY +
         * (int)(zoomBoxHeight/2) );
         * g2d.drawString(dc.format(userMaxX),
         * (int)(zoomBoxCurrentX),zoomBoxStartY + (int)(zoomBoxHeight/2) );
         * int smaxYWidth = metrics.stringWidth(dc.format(userMaxY));
         * g2d.drawString(dc.format(userMaxY),
         * (int)(zoomBoxStartX+zoomBoxWidth/2-smaxYWidth/2),zoomBoxStartY-2 );
         * int sminYWidth = metrics.stringWidth(dc.format(userMinY));
         * g2d.drawString(dc.format(userMinY),
         * (int)(zoomBoxStartX+zoomBoxWidth/2
         * -sminYWidth/2),zoomBoxCurrentY+hgt+2 );
         */
        // if(getBoxType() == BoxType.BoxXY){
        // if(zoomBoxCurrentX < zoomBoxStartX && zoomBoxCurrentY <
        // zoomBoxStartY)
        // return true;
        // }
        // else if(getBoxType() == BoxType.BoxX){
        // if(zoomBoxCurrentX < zoomBoxStartX)
        // return true;
        // }
        // else if(getBoxType() == BoxType.BoxY){
        // if( zoomBoxCurrentY < zoomBoxStartY)
        // return true;
        // }
        // minx
        int deltaX = 20;
        int defilY = zoomBoxStartY - 50;

        if (getBoxType() == BoxType.BoxXY || getBoxType() == BoxType.BoxX) {

            if (userMinX > 0) {
                String metricsLabel = null;
                if (getZoomBoxInfoMetricsFormat() != null) {
                    metricsLabel = getZoomBoxInfoMetricsFormat().format(
                                                                        userMinX);
                }
                else {
                    metricsLabel = dc.format(userMinX);
                }
                g2d.drawString("min  X  = + " + metricsLabel,
                               zoomBoxCurrentX + deltaX, defilY);
            }
            else {
                String metricsLabel = null;
                if (getZoomBoxInfoMetricsFormat() != null) {
                    metricsLabel = getZoomBoxInfoMetricsFormat().format(
                                                                        Math.abs(userMinX));
                }
                else {
                    metricsLabel = dc.format(Math.abs(userMinX));
                }
                g2d.drawString("min  X  = - " + metricsLabel,
                               zoomBoxCurrentX + deltaX, defilY);
            }

            defilY = defilY + 15;

            if (userMaxX > 0) {
                String metricsLabel = null;
                if (getZoomBoxInfoMetricsFormat() != null) {
                    metricsLabel = getZoomBoxInfoMetricsFormat().format(
                                                                        userMaxX);
                }
                else {
                    metricsLabel = dc.format(userMaxX);
                }
                g2d.drawString("max X  = + " + metricsLabel,
                               zoomBoxCurrentX + deltaX, defilY);
            }
            else {
                String metricsLabel = null;
                if (getZoomBoxInfoMetricsFormat() != null) {
                    metricsLabel = getZoomBoxInfoMetricsFormat().format(
                                                                        Math.abs(userMaxX));
                }
                else {
                    metricsLabel = dc.format(Math.abs(userMaxX));
                }
                g2d.drawString("max X  = - " + metricsLabel,
                               zoomBoxCurrentX + deltaX, defilY);
            }
            defilY = defilY + 15;

        }

        if (getBoxType() == BoxType.BoxXY || getBoxType() == BoxType.BoxY) {

            if (userMaxY > 0) {
                String metricsLabel = null;
                if (getZoomBoxInfoMetricsFormat() != null) {
                    metricsLabel = getZoomBoxInfoMetricsFormat().format(
                                                                        userMaxY);
                }
                else {
                    metricsLabel = dc.format(userMaxY);
                }
                g2d.drawString("max Y  = + " + metricsLabel,
                               zoomBoxCurrentX + deltaX, defilY);
            }
            else {
                String metricsLabel = null;
                if (getZoomBoxInfoMetricsFormat() != null) {
                    metricsLabel = getZoomBoxInfoMetricsFormat().format(
                                                                        Math.abs(userMaxY));
                }
                else {
                    metricsLabel = dc.format(Math.abs(userMaxY));
                }
                g2d.drawString("max Y  = - " + metricsLabel,
                               zoomBoxCurrentX + deltaX, defilY);
            }
            defilY = defilY + 15;

            if (userMinY > 0) {
                String metricsLabel = null;
                if (getZoomBoxInfoMetricsFormat() != null) {
                    metricsLabel = getZoomBoxInfoMetricsFormat().format(
                                                                        userMinY);
                }
                else {
                    metricsLabel = dc.format(userMinY);
                }
                g2d.drawString("min  Y  = + " + metricsLabel,
                               zoomBoxCurrentX + deltaX, defilY);
            }
            else {
                String metricsLabel = null;
                if (getZoomBoxInfoMetricsFormat() != null) {
                    metricsLabel = getZoomBoxInfoMetricsFormat().format(
                                                                        Math.abs(userMinY));
                }
                else {
                    metricsLabel = dc.format(Math.abs(userMinY));
                }
                g2d.drawString("min  Y  = - " + metricsLabel,
                               zoomBoxCurrentX + deltaX, defilY);
            }
        }

    }

    /**
     * get the box type
     * 
     * @return the boxType
     */
    public BoxType getBoxType() {
        return boxType;
    }

    /**
     * set the box type
     * 
     * @param boxType
     *            the boxType to set
     */
    public void setBoxType(BoxType boxType) {
        this.boxType = boxType;
    }

    /**
     * get zoom box draw color
     * 
     * @return the outline draw color of zoom box
     */
    public Color getZoomBoxDrawColor() {
        return zoomBoxDrawColor;
    }

    /**
     * set the outline draw color for zoom box
     * 
     * @param zoomBoxDrawColor
     *            the draw outline color to set
     */
    public void setZoomBoxDrawColor(Color zoomBoxDrawColor) {
        this.zoomBoxDrawColor = zoomBoxDrawColor;
    }

    /**
     * get the zoom box fill color
     * 
     * @return the zoomBoxFillColor
     */
    public Color getZoomBoxFillColor() {
        return zoomBoxFillColor;
    }

    /**
     * set the zoom box fill color
     * 
     * @param zoomBoxFillColor
     *            the zoomBoxFillColor to set
     */
    public void setZoomBoxFillColor(Color zoomBoxFillColor) {
        this.zoomBoxFillColor = zoomBoxFillColor;
    }

    /**
     * <code>BoundBox</code>
     */
    public class BoundBox {

        public double minX;
        public double maxX;
        public double minY;
        public double maxY;

        public BoundBox(double minX, double maxX, double minY, double maxY) {

            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            BoundBox other = (BoundBox) obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (Double.doubleToLongBits(maxX) != Double
                    .doubleToLongBits(other.maxX)) {
                return false;
            }
            if (Double.doubleToLongBits(maxY) != Double
                    .doubleToLongBits(other.maxY)) {
                return false;
            }
            if (Double.doubleToLongBits(minX) != Double
                    .doubleToLongBits(other.minX)) {
                return false;
            }
            if (Double.doubleToLongBits(minY) != Double
                    .doubleToLongBits(other.minY)) {
                return false;
            }
            return true;
        }

        private ZoomBoxPlugin getOuterType() {
            return ZoomBoxPlugin.this;
        }

    }

    /**
     * get the zoom bound box history
     * 
     * @return the successive box history
     */
    public List<BoundBox> getZoomHistory() {
        return zoomHistory;
    }

    /**
     * get the current box index
     * 
     * @return the box index
     */
    public int getCurentBoxIndex() {
        return curentBoxIndex;
    }

    /**
     * set the box index with the specified parameter
     * <ul>
     * <li>no repaint</li>
     * <li>no fire</li>
     * </ul>
     * 
     * @param curentBoxIndex
     */
    public void setCurentBoxIndex(int curentBoxIndex) {
        this.curentBoxIndex = curentBoxIndex;
    }

    /**
     * bound the next zoom box and fire event
     */
    public void historyNext() {
        int index = getCurentBoxIndex();
        if (index + 1 <= 0) {
            return;
        }
        else if (index + 1 >= zoomHistory.size()) {
            return;
        }
        processZoomHistory(index + 1);
    }

    /**
     * bound the previous zoom box and fire event
     */
    public void historyPrevious() {
        int index = getCurentBoxIndex();
        if (index - 1 < 0) {
            return;
        }
        processZoomHistory(index - 1);
    }

    /**
     * return the index for the specified bound box, -1 otherwise if the bound
     * box is not found in history
     * 
     * @param boundBox
     * @return the index
     */
    public int getHistoryIndex(BoundBox boundBox) {
        int index = 0;
        for (BoundBox box : zoomHistory) {
            if (box.equals(boundBox)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * process history for the specified index of history
     * 
     * @param requestHistory
     *            the history to set
     */
    public void processZoomHistory(int requestHistory) {

        if (requestHistory != -1) {

            BoundBox box = zoomHistory.get(requestHistory);
            if (box == null) {
                return;
            }
            setCurentBoxIndex(requestHistory);

            if (getProjection() instanceof Projection.Linear) {
                Projection.Linear wl = (Projection.Linear) getProjection();
                wl.bound(box.minX, box.maxX, box.minY, box.maxY);
            }

        }
    }

    /**
     * clear zoom box bounds history
     */
    public void processZoomClearHistory() {
        zoomHistory.clear();
        curentBoxIndex = -1;
        fireZoomClearHistory();
        getProjection().getView2D().repaintDevice();
    }

    /**
     * lock the dynamic zooming effect
     */
    public void lockDynEffect() {
        dynEffect = true;
    }

    /**
     * unlock the dynamic zooming effect
     */
    public void unlockDynEffect() {
        dynEffect = false;
    }

}
