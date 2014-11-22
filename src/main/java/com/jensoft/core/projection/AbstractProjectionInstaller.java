/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.projection;

/**
 * <code>AbstractProjectionInstaller</code> takes the responsibility to 'install' or 'uninstall' a projection
 * within a view.
 * For a specified given view, this installer defines the {@link #install()} and {@link #uninstall()} method that should
 * be overridden to specify a particular
 * behavior to install or uninstall projection within a given view.
 * 
 * @author sebastien janaud
 */
public abstract class AbstractProjectionInstaller {

    /** window to install */
    private Projection projection;

    /**
     * create empty installer
     */
    public AbstractProjectionInstaller() {
    }

    /**
     * create the abstract installer for the specified projection
     * 
     * @param projection
     */
    public AbstractProjectionInstaller(Projection projection) {
        this.projection = projection;
    }

    /**
     * @return the projection
     */
    public Projection getProjection() {
        return projection;
    }

    /**
     * @param projection
     *            the projection to set
     */
    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    /**
     * install the projection
     */
    abstract void install();

    /**
     * uninstall the projection
     */
    abstract void uninstall();

}
