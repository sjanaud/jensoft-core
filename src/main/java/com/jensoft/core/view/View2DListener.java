/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view;

import java.util.EventListener;

/**
 * View2DListener
 * 
 * @author Sebastien Janaud
 */
public interface View2DListener extends EventListener {

    /**
     * call when a window is selected in view
     * 
     * @param view2DEvent
     */
    public void viewWindow2DSelected(View2DEvent view2DEvent);

    /**
     * call when view is moved
     * 
     * @param view2DEvent
     */
    public void viewMoved(View2DEvent view2DEvent);

    /**
     * call when the view is resized
     * 
     * @param view2DEvent
     */
    public void viewResized(View2DEvent view2DEvent);

    /**
     * call when the view is hidden
     * 
     * @param view2DEvent
     */
    public void viewHidden(View2DEvent view2DEvent);

    /**
     * call when the view is shown
     * 
     * @param view2DEvent
     */
    public void viewShown(View2DEvent view2DEvent);

    /**
     * call when view gained focus
     * 
     * @param view2DEvent
     */
    public void viewFocusGained(View2DEvent view2DEvent);

    /**
     * call when the view lost focus
     * 
     * @param view2DEvent
     */
    public void viewFocusLost(View2DEvent view2DEvent);

}
