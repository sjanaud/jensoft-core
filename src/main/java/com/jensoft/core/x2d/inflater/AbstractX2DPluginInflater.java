/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.inflater;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.AbstractPlugin;

/**
 * <code>AbstractX2DPluginInflater</code> Abstract inflate plug in operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractX2DPluginInflater<P extends AbstractPlugin> extends InflaterUtil{

    /** inflated plug in */
    private P plugin;

    /** xsi type */
    private String XSIType;

    /**
     * create inflater
     */
    public AbstractX2DPluginInflater() {
    }

    /**
     * create inflater with given XSI type
     */
    public AbstractX2DPluginInflater(String XSIType) {
        this.XSIType = XSIType;
    }

   

    /**
     * @return the plug in
     */
    public P getPlugin() {
        return plugin;
    }

    /**
     * @return the xSIType
     */
    public String getXSIType() {
        return XSIType;
    }

    /**
     * @param xSIType
     *            the xSIType to set
     */
    public void setXSIType(String xSIType) {
        XSIType = xSIType;
    }

    /**
     * @param plugin
     *            the plug in to set
     */
    public void setPlugin(P plugin) {
        this.plugin = plugin;
    }

    /**
     * inflate the T plugin mean read the given plugin root element and configure plugin
     * 
     * @param plugin
     *            the element to inflate
     */
    public abstract void inflate(Element plugin);

}
