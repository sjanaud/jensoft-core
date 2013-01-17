/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.widget.button;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.widget.Widget;
import com.jensoft.core.widget.WidgetFolder;

/**
 * AbstractButtonWidget
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractButtonWidget<P extends AbstractPlugin> extends Widget<P> {

    private boolean lockEnter = false;

    public AbstractButtonWidget(String id, double width, double height,
            int xIndex, int yIndex) {
        super(id, width, height, xIndex, yIndex);
    }

    /**
     * @return the lockEnter
     */
    public boolean isLockEnter() {
        return lockEnter;
    }

    /**
     * lock enter
     */
    public void lockEnter() {
        lockEnter = true;
    }

    /**
     * unlock enter
     */
    public void unlockEnter() {
        lockEnter = false;
    }

    /**
     * call on button press
     */
    public void onPress() {
    }

    /**
     * call on button release
     */
    public void onRelease() {
    }

    /**
     * call on button enter
     */
    public void onEnter() {
    }

    /**
     * call on button exit
     */
    public void onExit() {
    }

    /**
     * track roll over on button
     * 
     * @param x
     *            x coordinate
     * @param y
     *            y coordinate
     */
    private void trackRollover(int x, int y) {

        WidgetFolder folder = getWidgetFolder();
        if (folder == null) {
            return;
        }

        Rectangle2D bound = folder.getBounds2D();
        if (bound != null && bound.contains(x, y)) {
            if (!isLockEnter()) {
                lockEnter();
                onEnter();
            }
        }
        else {
            if (isLockEnter()) {
                unlockEnter();
                onExit();
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.widget.Widget#interceptMove(int, int)
     */
    @Override
    public void interceptMove(int x, int y) {
        super.interceptMove(x, y);
        trackRollover(x, y);
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.widget.Widget#interceptPress(int, int)
     */
    @Override
    public void interceptPress(int x, int y) {
        super.interceptPress(x, y);

        if (!isLockEnter()) {
            return;
        }

        WidgetFolder folder = getWidgetFolder();
        if (folder == null) {
            return;
        }

        Rectangle2D bound = folder.getBounds2D();
        if (bound != null && bound.contains(x, y)) {
            onPress();
        }
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.widget.Widget#interceptDrag(int, int)
     */
    @Override
    public void interceptDrag(int x, int y) {
        super.interceptDrag(x, y);
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.widget.Widget#interceptReleased(int, int)
     */
    @Override
    public void interceptReleased(int x, int y) {
        super.interceptReleased(x, y);

        if (!isLockEnter()) {
            return;
        }
        onRelease();
    }

    /**
     * override this method to draw button in the specified drawing bounding
     * rectangle
     * 
     * @param v2d
     *            the view
     * @param g2d
     *            the graphics context
     * @param buttonDrawingRegion
     *            the button bouding region
     */
    public abstract void paintButton(View2D v2d, Graphics2D g2d,
            Rectangle2D buttonDrawingRegion);

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.widget.Widget#paintWidget(com.jensoft.sw2d.core
     * .view.View2D, java.awt.Graphics2D)
     */
    @Override
    public final void paintWidget(View2D v2d, Graphics2D g2d) {
        Rectangle2D rect = getWidgetFolder().getBounds2D();
        if (rect != null) {
            paintButton(v2d, g2d, rect);
        }
    }

//    @Override
//    public boolean isCompatiblePlugin() {
//        return true;
//    }

}
