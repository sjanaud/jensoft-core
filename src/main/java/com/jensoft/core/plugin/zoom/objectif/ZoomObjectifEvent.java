/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.objectif;

import com.jensoft.core.plugin.PluginEvent;

/**
 * <code>ZoomObjectifEvent</code>
 * 
 * @author sebastien janaud
 */
public class ZoomObjectifEvent extends PluginEvent<ZoomObjectifPlugin> {

    private static final long serialVersionUID = -8600817451113657609L;

    /**
     * create zoom objectif event
     * @param plugin
     */
    public ZoomObjectifEvent(ZoomObjectifPlugin plugin) {
        super(plugin);
    }

}
