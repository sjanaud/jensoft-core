/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.tools.peaktracker;

import java.util.EventObject;

import com.jensoft.core.plugin.function.source.SourceFunction;

/**
 * <code>SerieTrackerEvent<code>
 * 
 * @author sebastien janaud
 */
public class PeakTrackerEvent extends EventObject {

    /** uid */
    private static final long serialVersionUID = 1225330748067264039L;

    /**
     * Create serie tracker event for given serie
     * 
     * @param serie
     */
    public PeakTrackerEvent(SourceFunction serie) {
        super(serie);
    }

    /**
     * return the serie
     * 
     * @return serie
     */
    public SourceFunction getSerie() {
        return (SourceFunction) getSource();
    }
}
