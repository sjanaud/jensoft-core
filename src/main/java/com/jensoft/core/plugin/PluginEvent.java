/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin;

import java.util.EventObject;

/**
 * <code>PluginEvent</code> is the superclass of plug in events
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public class PluginEvent<P extends AbstractPlugin> extends EventObject {

    private static final long serialVersionUID = -2835863948996959233L;

    private P plugin;
    /**
     * create plugin event with the given plug in
     * @param source
     */
    public PluginEvent(P plugin) {
        super(plugin);
        this.plugin = plugin;
    }
    
    /**
     * get the plugin evgent source
     * @return plugin
     */
   public P getPlugin(){
       return plugin;
   }

}
