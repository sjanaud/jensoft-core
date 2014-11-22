/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.lens;

import com.jensoft.core.plugin.PluginListener;

/**
 * <code>ZoomLensListener</code>
 * 
 * @author sebastien janaud
 */
public interface ZoomLensListener extends PluginListener<ZoomLensPlugin> {

    /**
     * call on zoom in
     * @param pluginEvent
     */
    public void zoomIn(ZoomLensEvent pluginEvent);

    /**
     * call on zoom out
     * @param pluginEvent
     */
    public void zoomOut(ZoomLensEvent pluginEvent);

}
