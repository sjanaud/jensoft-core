/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.view;

import java.util.EventListener;

/**
 * <code>ViewListener</code> defines the base listener for view events.
 * 
 * @since 1.0
 * 
 * @author Sebastien Janaud
 */
public interface ViewListener extends EventListener {

    /**
     * call when a projection is selected in view
     * 
     * @param event
     */
    public void viewProjectionSelected(ViewEvent event);

    /**
     * call when view is moved
     * 
     * @param event
     */
    public void viewMoved(ViewEvent event);

    /**
     * call when the view is resized
     * 
     * @param event
     */
    public void viewResized(ViewEvent event);

    /**
     * call when the view is hidden
     * 
     * @param event
     */
    public void viewHidden(ViewEvent event);

    /**
     * call when the view is shown
     * 
     * @param event
     */
    public void viewShown(ViewEvent event);

    /**
     * call when view gained focus
     * 
     * @param event
     */
    public void viewFocusGained(ViewEvent event);

    /**
     * call when the view lost focus
     * 
     * @param event
     */
    public void viewFocusLost(ViewEvent event);

}
