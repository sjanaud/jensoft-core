/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.lens;

import com.jensoft.core.plugin.PluginEvent;

/**
 * <code>ZoomLensEvent</code>
 * 
 * @author sebastien janaud
 */
public class ZoomLensEvent extends PluginEvent<ZoomLensPlugin> {

    private static final long serialVersionUID = -8600817451113657609L;

    /**
     * create zoom objectif event
     * @param plugin
     */
    public ZoomLensEvent(ZoomLensPlugin plugin) {
        super(plugin);
    }

}
