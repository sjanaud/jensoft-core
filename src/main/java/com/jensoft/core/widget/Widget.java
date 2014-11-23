/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.widget;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;

/***
 * <code>Widget</code>
 * 
 * @author Sebastien Janaud
 */
public abstract class Widget<P extends AbstractPlugin> implements WidgetFolder.OnPostWidgetListener {

    /** widget name */
    private String name;

    /** the widget id */
    private String id;

    /** the host plugin of this widget */
    private P host;

    /** the widget folder */
    private WidgetFolder widgetFolder;

    /** widget width */
    private double width;

    /** widget height */
    private double height;

    /** x index */
    private int xIndex;

    /** y index */
    private int yIndex;

    /** lock widget */
    private boolean lockWidget = true;

    /** sensible shape on this widget */
    private List<Shape> sensibleShapes = new ArrayList<Shape>();

    /** lock move operation */
    private boolean noMoveOperation = false;

    /** movable widget flag */
    private boolean isMovable = true;

    private boolean orphanLock = false;
    
    

    /**
     * Widget constructor
     */
    public Widget() {
        this("widget", 0, 0, 0, 0);
    }

    /**
     * Widget constructor
     */
    public Widget(String id) {
        this(id, 0, 0, 0, 0);
    }

    /**
     * Widget constructor with specified parameters
     */
    public Widget(String id, double width, double height, int xIndex, int yIndex) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
    }

    /**
     * callback method call on widget host registering.
     */
    public void onRegister() {
    }

    /**
     * get widget name
     * 
     * @return widget name
     */
    public String getName() {
        return name;
    }

    /**
     * set widget name
     * 
     * @param name
     *            the widget name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get widget id
     * 
     * @return widget id
     */
    public String getId() {
        return id;
    }

    /**
     * set widget id
     * 
     * @param id
     *            the widget id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the noMoveOperation
     */
    public boolean isNoMoveOperation() {
        return noMoveOperation;
    }

    /**
     * @param noMoveOperation
     *            the noMoveOperation to set
     */
    public void setNoMoveOperation(boolean noMoveOperation) {
        this.noMoveOperation = noMoveOperation;
    }

    /**
     * return true if the point defines by x and y coordinates is contains in
     * one of the sensible shape, false otherwise
     * 
     * @param x
     *            the x point coordinate
     * @param y
     *            the y point coordinate
     * @return true if specified coordinate is a sensible point, false otherwise
     */
    public boolean isSensible(int x, int y) {
        for (Shape sensibleShape : sensibleShapes) {
            if (sensibleShape.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the sensibleShapes
     */
    public List<Shape> getSensibleShapes() {
        return sensibleShapes;
    }

    /**
     * clear sensible shape
     */
    public void clearSensibleShape() {
        sensibleShapes.clear();
    }

    /**
     * @param sensibleShapes
     *            the sensibleShapes to set
     */
    public void setSensibleShapes(List<Shape> sensibleShapes) {
        this.sensibleShapes = sensibleShapes;
    }

    /**
     * add sensible shape
     * 
     * @param sensibleShape
     *            the sensible shape to add
     */
    public void addSensibleShape(Shape sensibleShape) {
        sensibleShapes.add(sensibleShape);
    }

    /**
     * remove sensible shape
     * 
     * @param sensibleShape
     *            the sensible shape to remove
     */
    public void removeSensibleShape(Shape sensibleShape) {
        sensibleShapes.remove(sensibleShape);
    }

    /**
     * override this method in subclass widget to intercept move very important
     * to call this method in subclass method override to manage move operation
     * or call {@link #checkMoveOperation(int, int)} in method override
     * 
     * @param x
     *            the x location
     * @param y
     *            the y location
     */
    public void interceptMove(int x, int y) {
        checkMoveOperation(x, y);
    }

    /**
     * override this method in subclass widget to intercept press
     * 
     * @param x
     *            the x location
     * @param y
     *            the y location
     */
    public void interceptPress(int x, int y) {
    }

    /**
     * override this method in subclass widget to intercept drag
     * 
     * @param x
     *            the x location
     * @param y
     *            the y location
     */
    public void interceptDrag(int x, int y) {
    }

    /**
     * override this method in subclass widget to intercept released very
     * important to call this method in subclass method override to manage move
     * operation or call {@link #setNoMoveOperation(boolean)} in method override
     * with false parameter, move operation are now available after released.
     * 
     * @param x
     *            the x location
     * @param y
     *            the y location
     */
    public void interceptReleased(int x, int y) {
        setNoMoveOperation(false);
    }

    /**
     * override this method in subclass widget to intercept wheel rotation
     * 
     * @param rotation
     */
    public void interceptWheel(int rotation) {
    }

    /**
     * get widget width
     * 
     * @return widget width
     */
    public double getWidth() {
        return width;
    }

    /**
     * set widget width
     * 
     * @param width
     *            the widget width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * get the widget height
     * 
     * @return widget height
     */
    public double getHeight() {
        return height;
    }

    /**
     * set the widget height
     * 
     * @param height
     *            the widget height to set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * get x index in folder system
     * 
     * @return x index
     */
    public int getxIndex() {
        return xIndex;
    }

    /**
     * set x index in folder system
     * 
     * @param xIndex
     *            the x index to set
     */
    public void setxIndex(int xIndex) {
        this.xIndex = xIndex;
    }

    /**
     * get y index in folder system
     * 
     * @return y index
     */
    public int getyIndex() {
        return yIndex;
    }

    /**
     * set y index in folder system
     * 
     * @param yIndex
     */
    public void setyIndex(int yIndex) {
        this.yIndex = yIndex;
    }

    /**
     * return the plugin that host this widget
     * 
     * @return the widget host
     */
    public P getHost() {
        return host;
    }

    /**
     * set the plugin that host this widget
     * 
     * @param host
     */
    public void setHost(P host) {
        this.host = host;
    }

    /**
     * get the widget folder
     * 
     * @return the widget folder
     */
    public WidgetFolder getWidgetFolder() {
        return widgetFolder;
    }

    /**
     * set the widget folder
     * 
     * @param widgetFolder
     */
    public void setWidgetFolder(WidgetFolder widgetFolder) {
        this.widgetFolder = widgetFolder;
    }

    /**
     * get theme color
     * 
     * @return widget theme color
     */
    public Color getThemeColor() {
        return getHost().getThemeColor();
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.WidgetFolder.OnPostWidgetListener#onPostWidget()
     */
    @Override
    public void onPostWidget() {
        xIndex = widgetFolder.getTargetFolder().getxIndex();
        yIndex = widgetFolder.getTargetFolder().getyIndex();
    }

    /**
     * sub class this for painting widget
     * 
     * @param v2d
     * @param g2d
     */
    protected abstract void paintWidget(View v2d, Graphics2D g2d);

    /**
     * lay out widget folder
     * 
     * @param view
     *            the view
     */
    private void layoutFolder(View view) {

        if (getWidgetFolder() == null) {
            setWidgetFolder(view.newWidgetFolderIntance(getId(), getWidth(),
                                                       getHeight(), getxIndex(), getyIndex()));
            getWidgetFolder().setOnPostListener(this);

        }
        else {
            WidgetFolder vdf = view.newWidgetFolderIntance(getId(), getWidth(),
                                                          getHeight(), getxIndex(), getyIndex());

            getWidgetFolder().updateFrame(vdf.getX(), vdf.getY(),
                                          vdf.getWidth(), vdf.getHeight());
        }
    }

    /**
     * repaint the widget for the clipping widget area
     */
    public void repaintWidget() {
        if (getHost() != null && getHost().getProjection() != null && getHost().getProjection().getView() != null
                && getWidgetFolder() != null && getWidgetFolder().getBounds() != null) {
            getHost().getProjection().getView().repaintDevice(getWidgetFolder().getBounds());
        }
    }

    /**
     * paint widget
     * 
     * @param view
     *            the view
     * @param g2d
     *            the graphics context
     */
    public final void paint(View view, Graphics2D g2d) {
        if (!isLockWidget()) {
            return;
        }

        if (!getHost().isLockSelected() && isOrphanLock()) {
            return;
        }

        layoutFolder(view);
        paintWidget(view, g2d);

    }

    /**
     * return true if widget is locked, false otherwise
     * 
     * @return widget lock
     */
    public boolean isLockWidget() {
        return lockWidget;
    }

    /**
     * set widget lock
     * 
     * @param lockWidget
     *            the lock to set
     */
    public void setLockWidget(boolean lockWidget) {
        this.lockWidget = lockWidget;
    }

    /**
     * lock widget
     */
    public void lockWidget() {
        lockWidget = true;
    }

    /**
     * unlock widget
     */
    public void unlockWidget() {
        lockWidget = false;
    }

    /**
     * prevent move operation if sensible shape are intercept
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     */
    public void checkMoveOperation(int x, int y) {

        if (!getHost().isLockSelected() && isOrphanLock())
        {
            return;
            // System.out.println("widget folder ooo"+getWidgetFolder().getBounds());
        }


        if (!isMovable) {
            setNoMoveOperation(true);
            // System.out.println("set default cursor");
            return;
        }

        if (isSensible(x, y)) {
            setNoMoveOperation(true);
            // System.out.println("set default cursor");

        }
        else {
            setNoMoveOperation(false);
            // System.out.println("set hand cursor");
        }
    }

    /**
     * @return the isMovable
     */
    public boolean isMovable() {
        return isMovable;
    }

    /**
     * @param isMovable
     *            the isMovable to set
     */
    public void setMovable(boolean isMovable) {
        this.isMovable = isMovable;
    }

    /**
     * @return the orphanLock
     */
    public boolean isOrphanLock() {
        return orphanLock;
    }

    /**
     * @param orphanLock
     *            the orphanLock to set
     */
    public void setOrphanLock(boolean orphanLock) {
        this.orphanLock = orphanLock;
    }

    public void isCompatible() {
        // a instanceof B
        // B.class.isAssignableFrom(a.getClass())
    }

    /**
     * return true if this widget is compatible with host plugin, false
     * otherwise
     * 
     * @return true if this widget is compatible with host plugin, false
     *         otherwise
     */
    public abstract boolean isCompatiblePlugin();

    
    //
    // public boolean isCompatiblePlugin() {
    // try{
    // System.out.println(getHost());
    // ((P)getHost()).getClass(); //throws exception if host is not parameterized type
    // return true;
    // }
    // catch (Exception e) {
    // e.printStackTrace();
    // return false;
    // }
    // }

}
