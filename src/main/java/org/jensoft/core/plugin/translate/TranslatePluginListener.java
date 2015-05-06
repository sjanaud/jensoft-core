/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.translate;

import org.jensoft.core.plugin.PluginListener;

/**
 * <code>TranslatePluginListener</code>
 * 
 * @author sebastien janaud
 */
public interface TranslatePluginListener extends PluginListener<TranslatePlugin> {

    /**
     * fire when translate plugin start a translate
     * 
     * @param pluginEvent
     */
    public void translateStarted(TranslatePluginEvent pluginEvent);

    /**
     * fire when translate plugin translate
     * 
     * @param pluginEvent
     */
    public void translated(TranslatePluginEvent pluginEvent);

    /**
     * fire when translate plugin stop a translate
     * 
     * @param pluginEvent
     */
    public void translateStoped(TranslatePluginEvent pluginEvent);

    /**
     * fire when translate plugin authorize y translation
     * 
     * @param pluginEvent
     */
    public void translateL2RChanged(TranslatePluginEvent pluginEvent);

    /**
     * fire when translate plugin authorize x translation
     * 
     * @param pluginEvent
     */
    public void translateB2TChanged(TranslatePluginEvent pluginEvent);

}
