/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.tools.sourcetracker;

import java.util.EventObject;

import org.jensoft.core.plugin.function.source.SourceFunction;

/**
 * <code>SourceTrackerEvent<code>
 * 
 * @author sebastien janaud
 */
public class SourceTrackerEvent extends EventObject {

    /** uid */
    private static final long serialVersionUID = 1225330748067264039L;

    /**
     * Create source tracker event for given source
     * 
     * @param source
     */
    public SourceTrackerEvent(SourceFunction source) {
        super(source);
    }

    /**
     * return source function
     * 
     * @return source
     */
    public SourceFunction getSourceFunction() {
        return (SourceFunction) getSource();
    }
}
