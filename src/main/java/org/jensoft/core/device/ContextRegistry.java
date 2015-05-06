/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.device;

import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.AbstractPlugin;

/**
 * <code>ContextRegistry</code> for all context entry that have been register for the host plugin
 * 
 * @author Sebastien Janaud
 */
public class ContextRegistry {

    /** context entries */
    private List<ContextEntry<? extends AbstractPlugin>> contextEntries;

    /** host plugin for this registry */
    private AbstractPlugin host;

    /**
     * create context registry
     */
    public ContextRegistry() {
        contextEntries = new ArrayList<ContextEntry<? extends AbstractPlugin>>();
    }

    /**
     * @return the host
     */
    public AbstractPlugin getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(AbstractPlugin host) {
        this.host = host;
    }

    /**
     * register specified entry in this context registry
     * 
     * @param contextEntry
     */
    public void register(ContextEntry<? extends AbstractPlugin> contextEntry) {
        contextEntries.add(contextEntry);
    }

    /**
     * @return the contextEntries
     */
    public List<ContextEntry<? extends AbstractPlugin>> getContextEntries() {
        return contextEntries;
    }

    /**
     * build context
     */
    public void buildContext() {
        for (ContextEntry<? extends AbstractPlugin> contextEntry : contextEntries) {
            contextEntry.buildContext();
        }
    }

}
