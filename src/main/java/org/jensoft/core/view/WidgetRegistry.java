/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.view;

import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.widget.Widget;

/**
 * <code>WidgetRegistry</code>
 * 
 * @since 1.0
 * 
 * @author Sebastien Janaud
 */
public class WidgetRegistry {

    /** plugin owner for this registry */
    private AbstractPlugin host;

    /** widget registry */
    private List<Widget> registry;

    /**
     * create widget registry
     */
    public WidgetRegistry() {
        registry = new ArrayList<Widget>();
    }

    /***
     * disable all widget for this registry
     */
    public void disableAll() {
        for (Widget widget : registry) {
            widget.unlockWidget();
        }
    }

    /**
     * get plugin registry owner
     * 
     * @return plugin registry owner
     */
    public AbstractPlugin getHost() {
        return host;
    }

    /**
     * set plugin owner for this registry
     * 
     * @param plugin
     *            the plugin to set
     */
    public void setHost(AbstractPlugin plugin) {
        host = plugin;
    }

    /**
     * register the specified widget in this regsistry
     * 
     * @param widget
     *            the widget to register
     */
    public void register(Widget widget) {
        registry.add(widget);
    }

    /**
     * remove widget rom this registry
     * 
     * @param widget
     *            the widget to remove
     */
    public void remove(Widget widget) {
        registry.remove(widget);
    }

    /**
     * return the widgets that have been register in this plug in widget
     * registry
     * 
     * @return the widgets
     */
    public List<Widget> getWidgets() {
        return registry;
    }

}
