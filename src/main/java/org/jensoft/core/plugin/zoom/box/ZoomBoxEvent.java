/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.zoom.box;

import org.jensoft.core.plugin.PluginEvent;

/**
 * <code>ZoomBoxEvent</code>
 * 
 * @author sebastien janaud
 *
 */
public class ZoomBoxEvent extends PluginEvent<ZoomBoxPlugin> {

    private static final long serialVersionUID = 4048435996433486519L;

    public ZoomBoxEvent(ZoomBoxPlugin plugin) {
        super(plugin);
    }    

}
