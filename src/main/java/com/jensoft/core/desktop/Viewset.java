/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.desktop;

import javax.swing.JComponent;

/**
 * Viewset sub class knows how to lay out views in the parent perspective
 * 
 * @author SJD
 */
public abstract class Viewset extends JComponent {

    /** the parent perspective */
    private Perspective perspective;

    /** close state value */
    public static String CLOSE_STATE = "close";

    /** open state value */
    public static String OPEN_STATE = "open";

    /** state, default is the close state */
    private String state = CLOSE_STATE;

    /**
     * get the parent perspective where this viewset has been registered
     * 
     * @return the perspective
     */
    public Perspective getPerspective() {
        return perspective;
    }

    /**
     * set the perspective where this viewset has been registered
     * 
     * @param perspective
     */
    public void setPerspective(Perspective perspective) {
        this.perspective = perspective;
    }

    /***
     * set open state
     */
    public void openViewset() {
        state = OPEN_STATE;
    }

    /**
     * set close state
     */
    public void closeViewset() {
        state = CLOSE_STATE;
    }

    /**
     * get the viewset state
     * 
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * @return true if the perspective is open, false otherwise
     */
    public boolean isOpen() {
        return state.equals(OPEN_STATE);
    }

    /**
     * @return true if the perspective is close, false otherwise
     */
    public boolean isClose() {
        return state.equals(CLOSE_STATE);
    }

    /**
     * lay out views into this viewset component
     */
    public abstract void packViews();

}
