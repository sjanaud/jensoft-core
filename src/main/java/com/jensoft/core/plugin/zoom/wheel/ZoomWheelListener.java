/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.wheel;

import com.jensoft.core.plugin.PluginListener;

/**
 * <code>ZoomWheelListener</code>
 * 
 * @author sebastien janaud
 */
public interface ZoomWheelListener extends PluginListener<ZoomWheelPlugin> {

    /**
     * call on zoom wheel in
     * @param pluginEvent
     */
    public void zoomIn(ZoomWheelEvent pluginEvent);

    /**
     * call on zoom wheel out
     * @param pluginEvent
     */
    public void zoomOut(ZoomWheelEvent pluginEvent);

}
