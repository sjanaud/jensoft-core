/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.widget;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * <code>WidgetFolder</code><br>
 * <p>
 * WidgetFolder represents a bound on the device represent by xIndex and yIndex
 * <p>
 * @author Sebastien Janaud
 */
public class WidgetFolder {

    /** widget folder id */
    private String id;

    /** x folder index */
    private int xIndex;

    /** y folder index */
    private int yIndex;

    /** lock roll over for this folder */
    private boolean lockRollover;

    /** lock press for this folder */
    private boolean lockPress;

    /** folder x coordinate */
    private double x;

    /** folder y coordinate */
    private double y;

    /** folder width */
    private double width;

    /** folder height */
    private double height;

    /** guard interval */
    private int guardInterval;

    /** press timer */
    private PressTimer pressTimer;

    /** current drag x coordinate */
    private int currentDragX;

    /** current drag y coordinate */
    private int currentDragY;

    /** current drag bound */
    private Rectangle currentDragBound;

    /** potential folder */
    private WidgetFolder potentialFolder;

    /** target folder */
    private WidgetFolder targetFolder;

    /** the on post listener for this folder */
    private OnPostWidgetListener onPostListener;

    /** asynchronous press call back */
    private AsyncPressWidgetCallback asyncCallback;

    /**
     * defines folder post listener
     */
    public interface OnPostWidgetListener {

        void onPostWidget();
    }

    /**
     * define asynchronous call back for press notification
     */
    public interface AsyncPressWidgetCallback {

        void folderPress();
    }

    /**
     * create widget folder
     */
    public WidgetFolder() {
    }

    /**
     * get target folder
     * 
     * @return target folder
     */
    public WidgetFolder getTargetFolder() {
        return targetFolder;
    }

    /**
     * set target folder
     * 
     * @param targetFolder
     *            the target folder to set
     */
    public void setTargetFolder(WidgetFolder targetFolder) {
        this.targetFolder = targetFolder;
    }

    /**
     * get current drag x coordinate
     * 
     * @return drag x coordinate
     */
    public int getCurrentDragX() {
        return currentDragX;
    }

    /**
     * set current drag x coordinate
     * 
     * @param currentDragX
     *            the current drag x coordinate to set
     */
    public void setCurrentDragX(int currentDragX) {
        this.currentDragX = currentDragX;
    }

    /**
     * get current drag y coordinate
     * 
     * @return drag y coordinate
     */
    public int getCurrentDragY() {
        return currentDragY;
    }

    /**
     * set current drag y coordinate
     * 
     * @param currentDragY
     *            the current drag y coordinate to set
     */
    public void setCurrentDragY(int currentDragY) {
        this.currentDragY = currentDragY;
    }

    /**
     * get folder bounding 2D rectangle
     * 
     * @return folder bounding 2D rectangle
     */
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * get folder bounding rectangle
     * 
     * @return folder bounding rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle((int) getX(), (int) getY(), (int) getWidth(),
                             (int) getHeight());
    }

    /**
     * @return the current drag bounding rectangle
     */
    public Rectangle getCurentDragBound() {
        return currentDragBound;
    }

    /**
     * @param currentDragBound
     *            the curentDragBound to set
     */
    public void setCurentDragBound(Rectangle currentDragBound) {
        this.currentDragBound = currentDragBound;
    }

    /**
     * @return the potentialFolder
     */
    public WidgetFolder getPotentialFolder() {
        return potentialFolder;
    }

    /**
     * @param potentialFolder
     *            the potentialFolder to set
     */
    public void setPotentialFolder(WidgetFolder potentialFolder) {
        this.potentialFolder = potentialFolder;
    }

    /**
     * start press this folder
     * 
     * @param delay
     *            the delay to obtain effective folder pressed
     * @param asyncPressCallback
     *            the asynchronous call back where notify pressed
     */
    public void startPress(int delay,
            AsyncPressWidgetCallback asyncPressCallback) {
        setAsyncPressWidgetCallback(asyncPressCallback);
        interruptPress();
        pressTimer = new PressTimer(delay);
        pressTimer.start();
    }

    /**
     * get on post listener for this folder
     * 
     * @return post listener
     */
    public OnPostWidgetListener getOnPostListener() {
        return onPostListener;
    }

    /**
     * set on post listener
     * 
     * @param onPostListener
     *            the post listener to set
     */
    public void setOnPostListener(OnPostWidgetListener onPostListener) {
        this.onPostListener = onPostListener;
    }

    /**
     * get asynchronous call back
     * 
     * @return asynchronous call back
     */
    public AsyncPressWidgetCallback getAsyncPressWidgetCallback() {
        return asyncCallback;
    }

    /**
     * set asynchronous call back
     * 
     * @param asyncCallback
     *            asynchronous press call back to set
     */
    public void setAsyncPressWidgetCallback(
            AsyncPressWidgetCallback asyncCallback) {
        this.asyncCallback = asyncCallback;
    }

    /**
     * interrupt press
     */
    public void interruptPress() {
        setLockPress(false);
        if (pressTimer != null && pressTimer.isAlive()) {
            pressTimer.interrupt();
        }

    }

    /**
     * press timer
     */
    class PressTimer extends Thread {

        /** press duration */
        private long timeInMillis;

        /** press start time */
        private long startTime;

        /** press validation delay */
        private int delay;

        /** press flag */
        private boolean press;

        /**
         * create press timer
         * 
         * @param delay
         *            the delay to obtain press validation
         */
        public PressTimer(int delay) {
            this.delay = delay;
        }

        @Override
        public void run() {

            startTime = System.currentTimeMillis();
            try {
                while (!interrupted() && !press) {
                    Thread.sleep(100);
                    timeInMillis = System.currentTimeMillis();
                    if (timeInMillis - startTime > delay) {
                        setLockPress(true);
                        asyncCallback.folderPress();
                        press = true;
                    }
                }
            }
            catch (InterruptedException e) {
                setLockPress(false);
                setLockRollover(false);
                Thread.currentThread().interrupt();
            }

        }

    }

    /**
     * update widget folder frame
     * 
     * @param x
     *            the new folder x coordinate
     * @param y
     *            the new folder y coordinate
     * @param width
     *            the new folder width
     * @param height
     *            the new folder height
     */
    public void updateFrame(double x, double y, double width, double height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    /**
     * get id for this widget folder
     * 
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * set id for this widget folder
     * 
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get guard interval
     * 
     * @return guard interval
     */
    public int getGuardInterval() {
        return guardInterval;
    }

    /**
     * set guard interval
     * 
     * @param guardInterval
     *            the guard interval to set
     */
    public void setGuardInterval(int guardInterval) {
        this.guardInterval = guardInterval;
    }

    @Override
    public String toString() {
        return "WidgetFolder [xIndex=" + xIndex + ", yIndex=" + yIndex + ", x="
                + x + ", y=" + y + ", width=" + width + ", height=" + height
                + "]";
    }

    /**
     * get x folder index
     * 
     * @return x folder index
     */
    public int getxIndex() {
        return xIndex;
    }

    /**
     * set x folder index
     * 
     * @param xIndex
     *            the x folder index to set
     */
    public void setxIndex(int xIndex) {
        this.xIndex = xIndex;
    }

    /**
     * get y folder index
     * 
     * @return y folder index
     */
    public int getyIndex() {
        return yIndex;
    }

    /**
     * set y folder index
     * 
     * @param yIndex
     *            the y folder index to set
     */
    public void setyIndex(int yIndex) {
        this.yIndex = yIndex;
    }

    /**
     * get sensible folder area
     * 
     * @return sensible folder area
     */
    public Rectangle2D getSensible() {
        return new Rectangle2D.Double(x - guardInterval, y - guardInterval,
                                      width + 2 * guardInterval, height + 2 * guardInterval);
    }

    /**
     * return true if this folder intercept the specified coordinate, false
     * otherwise
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     * @return true if this folder intercept the specified coordinate, false
     *         otherwise
     */
    public boolean intercept(int x, int y) {
        if (x > this.x && x < this.x + width && y > this.y
                && y < this.y + height) {
            return true;
        }
        return false;
    }

    /**
     * true if the folder is roll over, false otherwise
     * 
     * @return true if the folder is roll over, false otherwise
     */
    public boolean isLockRollover() {
        return lockRollover;
    }

    /**
     * set lock roll over
     * 
     * @param lockRollover
     *            the lock roll over to set
     */
    public void setLockRollover(boolean lockRollover) {
        this.lockRollover = lockRollover;
    }

    /**
     * true if the folder is lock pressed, false otherwise
     * 
     * @return true if the folder is lock pressed, false otherwise
     */
    public boolean isLockPress() {
        return lockPress;
    }

    /**
     * set lock press
     * 
     * @param lockPress
     *            the lock press to set
     */
    public void setLockPress(boolean lockPress) {
        this.lockPress = lockPress;
    }

    /**
     * return the folder x coordinate
     * 
     * @return the folder x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * set x folder coordinate
     * 
     * @param x
     *            the x folder coordinate to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * return the folder y coordinate
     * 
     * @return the folder y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * set y folder coordinate
     * 
     * @param y
     *            the y folder coordinate to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * get folder width
     * 
     * @return folder width
     */
    public double getWidth() {
        return width;
    }

    /**
     * set folder width
     * 
     * @param width
     *            the folder width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * get folder height
     * 
     * @return folder height
     */
    public double getHeight() {
        return height;
    }

    /**
     * set folder height
     * 
     * @param height
     *            the folder height to set
     */
    public void setHeight(double height) {
        this.height = height;
    }
}
