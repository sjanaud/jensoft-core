/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.tools.peaktracker;

import java.util.EventListener;

public interface PeakTrackerListener extends EventListener {

    /**
     * call when a serie just being tracked or untracked
     * 
     * @param event
     */
    void peakTracked(PeakTrackerEvent event);

    /**
     * call when a serie just being registered into tracker plug-in
     * 
     * @param event
     */
    void serieRegistered(PeakTrackerEvent event);

}
