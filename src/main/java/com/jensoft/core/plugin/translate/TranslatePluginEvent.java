/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.translate;

import com.jensoft.core.plugin.PluginEvent;

/**
 * <code>TranslatePluginEvent</code>
 * 
 * @author sebastien janaud
 *
 */
public class TranslatePluginEvent extends PluginEvent<TranslatePlugin> {

    private static final long serialVersionUID = 825936948019590940L;

    /**
     * create an event for the given translate plug in
     * @param plugin
     */
    public TranslatePluginEvent(TranslatePlugin plugin) {
        super(plugin);
    }

}
