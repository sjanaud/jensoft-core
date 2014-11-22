/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.projection;

import java.util.EventListener;

/**
 * <code>ProjectionListener</code>
 * 
 * @author Sebastien Janaud
 */
public interface ProjectionListener extends EventListener {

    /**
     * window has lock active
     * 
     * @param event
     *            the proj event
     */
    public void projectionLockActive(ProjectionEvent event);

    /**
     * window bound has changed
     * 
     * @param event
     *            the proj event
     */
    public void projectionBoundChanged(ProjectionEvent event);

    /**
     * projection has unlock active
     * 
     * @param event
     */
    public void projectionUnlockActive(ProjectionEvent event);
    
    /**
     * projection has resized
     * 
     * @param event
     */
    public void projectionResized(ProjectionEvent event);

}
