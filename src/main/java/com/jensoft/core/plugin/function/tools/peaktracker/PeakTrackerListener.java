/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.tools.peaktracker;

import java.util.EventListener;

/**
 * <code>PeakTrackerListener</code>
 * @author sebastien janaud
 *
 */
public interface PeakTrackerListener extends EventListener {

    /**
     * call when a source just being tracked or untracked
     * 
     * @param event
     */
    void peakTracked(PeakTrackerEvent event);

    /**
     * call when a source just being registered into tracker plug-in
     * 
     * @param event
     */
    void sourceRegistered(PeakTrackerEvent event);

}
