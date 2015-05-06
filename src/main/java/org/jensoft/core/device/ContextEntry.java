/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.device;

import javax.swing.JMenuItem;

import org.jensoft.core.plugin.AbstractPlugin;

/**
 * <code>ContextEntry</code> define a menu item for the specified plug in and group
 * 
 * @author Sebastien Janaud
 * @since 1.0
 */
public abstract class ContextEntry<T extends AbstractPlugin> {

    /** host plugin for this context entry */
    private T host;

    /** menu item for this context entry */
    private JMenuItem item;

    /** group for this context entry */
    private String group;

    /**
     * create empty context entry
     */
    public ContextEntry() {
    }

    /**
     * create context entry for specified parameters
     * 
     * @param host
     *            the host plug in
     * @param item
     *            the menu item entry
     * @param group
     *            the group for this context entry
     */
    public ContextEntry(T host, JMenuItem item, String group) {
        super();
        this.host = host;
        this.item = item;
        this.group = group;
    }

    /**
     * @return the plug in
     */
    public T getHost() {
        return host;
    }

    /**
     * @param host
     *            the host plug in to set
     */
    public void setHost(T host) {
        this.host = host;
    }

    /**
     * @return the item
     */
    public JMenuItem getItem() {
        return item;
    }

    /**
     * @param item
     *            the item to set
     */
    public void setItem(JMenuItem item) {
        this.item = item;
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group
     *            the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * prepare this context to be show in the device context menu
     */
    public abstract void buildContext();

    /**
     * return true if this context is compatible with host plug in, false
     * otherwise
     * 
     * @return true if this context is compatible with host plug in, false
     *         otherwise
     */
    public abstract boolean isCompatiblePlugin();

}
