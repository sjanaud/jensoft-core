/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.window;

import java.util.EventListener;

/**
 * window listener
 * 
 * @author Sebastien Janaud
 */
public interface Window2DListener extends EventListener {

    /**
     * window has lock active
     * 
     * @param w2dEvent
     *            the window event
     */
    public void window2DLockActive(Window2DEvent w2dEvent);

    /**
     * window bound has changed
     * 
     * @param w2dEvent
     *            the window event
     */
    public void window2DBoundChanged(Window2DEvent w2dEvent);

    /**
     * window has unlock active
     * 
     * @param w2dEvent
     */
    public void window2DUnlockActive(Window2DEvent w2dEvent);
    
    /**
     * window has resized
     * 
     * @param w2dEvent
     */
    public void window2DResized(Window2DEvent w2dEvent);

}
