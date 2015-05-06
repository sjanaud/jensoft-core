/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.zoom.wheel;

import org.jensoft.core.plugin.PluginEvent;

/**
 * <code>ZoomWheelEvent</code>
 * 
 * @author sebastien janaud
 */
public class ZoomWheelEvent extends PluginEvent<ZoomWheelPlugin> {

    private static final long serialVersionUID = 2662635923016876262L;

    /**
     * create zoom wheel event
     * 
     * @param plugin
     */
    public ZoomWheelEvent(ZoomWheelPlugin plugin) {
        super(plugin);
    }

}
