/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.tools.peaktracker;

import java.util.EventObject;

import org.jensoft.core.plugin.function.source.SourceFunction;

/**
 * <code>SerieTrackerEvent<code>
 * 
 * @author sebastien janaud
 */
public class PeakTrackerEvent extends EventObject {

    /** uid */
    private static final long serialVersionUID = 1225330748067264039L;

    /**
     * Create source peak tracker event for given source
     * 
     * @param source
     */
    public PeakTrackerEvent(SourceFunction source) {
        super(source);
    }

    /**
     * return the source function
     * 
     * @return source function
     */
    public SourceFunction getSourceFunction() {
        return (SourceFunction) getSource();
    }
}
