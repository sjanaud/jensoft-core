/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.objectif;

import com.jensoft.core.plugin.PluginListener;

/**
 * <code>ZoomObjectifListener</code>
 * 
 * @author sebastien janaud
 */
public interface ZoomObjectifListener extends PluginListener<ZoomObjectifPlugin> {

    /**
     * call on zoom in
     * @param pluginEvent
     */
    public void zoomIn(ZoomObjectifEvent pluginEvent);

    /**
     * call on zoom out
     * @param pluginEvent
     */
    public void zoomOut(ZoomObjectifEvent pluginEvent);

}
