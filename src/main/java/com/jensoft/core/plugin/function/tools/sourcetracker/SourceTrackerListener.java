/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.tools.sourcetracker;

import java.util.EventListener;

public interface SourceTrackerListener extends EventListener {

    /**
     * call when a serie just being tracked
     * 
     * @param event
     */
    void serieTracked(SourceTrackerEvent event);

    /**
     * call when a serie just being registered into tracker plug-in
     * 
     * @param event
     */
    void serieRegistered(SourceTrackerEvent event);

    /**
     * call during tracking
     * 
     * @param event
     */
    void currentTrack(SourceTrackerEvent event);

}
