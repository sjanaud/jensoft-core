/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.deflater;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.AbstractPlugin;

/**
 * <code>AbstractX2DPluginDeflater</code>
 * Abstract inflate plug in operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractX2DPluginDeflater<T extends AbstractPlugin> {

    /** inflate plug in */
    private T plugin;
    
    /**
     * create inflater
     */
    public AbstractX2DPluginDeflater(){
    }
    
    /**
     * create inflater for the given plugin
     * @param plugin
     */
    public AbstractX2DPluginDeflater(T plugin){
        this.plugin=plugin;
    }

    /**
     * @return the plug in
     */
    public T getPlugin() {
        return plugin;
    }

    /**
     * trim specified children element value from specified parent
     * 
     * @param parent
     *            the parent element
     * @param childName
     *            the children name
     * @return children element string trim value
     */
    public String elementTextTrim(Element parent, String childName) {
        Element childElement = (Element) parent.getElementsByTagName(childName).item(0);
        if (childElement != null) {
            return childElement.getTextContent();
        }
        return null;
    }

    /**
     * @param plugin
     *            the plug in to set
     */
    public void setPlugin(T plugin) {
        this.plugin = plugin;
    }

    /**
     * deflate the T plugin mean read the given plugin root element and configure plugin
     * @param  plugin
     *             the element to inflate
     */
    public abstract Element deflate();


}
