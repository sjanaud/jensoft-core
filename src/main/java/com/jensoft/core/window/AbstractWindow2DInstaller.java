/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.window;

/**
 * <code>Window2DInstaller</code> takes the responsibility to 'install' or 'uninstall' a window
 * within a view.
 * For a specified given view, this installer defines the {@link #install()} and {@link #uninstall()} method that should
 * be overridden to specify a particular
 * behavior to install or uninstall window within a given view.
 * 
 * @author sebastien janaud
 */
public abstract class AbstractWindow2DInstaller {

    /** window to install */
    private Window2D window2D;

    /**
     * create empty installer
     */
    public AbstractWindow2DInstaller() {
    }

    /**
     * create the abstract installer for the specified window
     * 
     * @param window2d
     */
    public AbstractWindow2DInstaller(Window2D window2d) {
        window2D = window2d;
    }

    /**
     * @return the window2D
     */
    public Window2D getWindow2D() {
        return window2D;
    }

    /**
     * @param window2d
     *            the window2D to set
     */
    public void setWindow2D(Window2D window2d) {
        window2D = window2d;
    }

    /**
     * install the window
     */
    abstract void install();

    /**
     * uninstall the window
     */
    abstract void uninstall();

}
