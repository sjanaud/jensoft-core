/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.zoom.box;

import org.jensoft.core.plugin.PluginListener;

/**
 * <code>ZoomBoxListener</code>
 * 
 * @author sebastien janaud
 */
public interface ZoomBoxListener extends PluginListener<ZoomBoxPlugin> {

    /**
     * call when zoom box started
     * 
     * @param pluginEvent
     */
    public void zoomStart(ZoomBoxEvent pluginEvent);

    /**
     * call when zoom box is bounding
     * 
     * @param pluginEvent
     */
    public void zoomBounded(ZoomBoxEvent pluginEvent);

    /**
     * call when zoom in
     * 
     * @param pluginEvent
     */
    public void zoomIn(ZoomBoxEvent pluginEvent);

    /**
     * call when zoom out
     * 
     * @param pluginEvent
     */
    public void zoomOut(ZoomBoxEvent pluginEvent);

    /**
     * call when new zoom history
     * 
     * @param pluginEvent
     */
    public void zoomHistory(ZoomBoxEvent pluginEvent);

    /**
     * call when clear zoom history
     * 
     * @param pluginEvent
     */
    public void zoomClearHistory(ZoomBoxEvent pluginEvent);
    
    
}
