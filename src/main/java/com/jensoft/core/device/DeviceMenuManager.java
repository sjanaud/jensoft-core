/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPopupMenu;

import com.jensoft.core.plugin.AbstractPlugin;

/**
 * <code>DeviceMenuManager</code>
 * 
 * @author Sebastien Janaud
 * @since 1.0
 */
public class DeviceMenuManager {

    /** device popup context menu */
    private JPopupMenu contextMenu;

    /** device component */
    private DevicePartComponent device2D;

    /** plugins */
    private List<AbstractPlugin> plugins;

    /** actions group context */
    private Map<String, List<ContextEntry<?>>> actionRegistry;

    /** plug ins state context */
    private List<StateContext> pluginsStateContext;

    /** context entries */
    private List<ContextEntry<?>> deviceContextEntries;

    /**
     * build device menu manager.
     * 
     * @param deviceComponent
     */
    public DeviceMenuManager(DevicePartComponent deviceComponent) {
        device2D = deviceComponent;
        contextMenu = new JPopupMenu();
        pluginsStateContext = new ArrayList<DeviceMenuManager.StateContext>();
        deviceContextEntries = new ArrayList<ContextEntry<?>>();
    }

    public JPopupMenu getContextMenu() {
        return contextMenu;
    }

    public List<AbstractPlugin> getPlugins() {
        return plugins;
    }

    class StateContext {

        AbstractPlugin plugin;
        boolean active;
    }

    public StateContext getStateContext(AbstractPlugin plugin) {
        for (StateContext lctx : pluginsStateContext) {
            if (lctx.plugin.equals(plugin)) {
                return lctx;
            }
        }
        return null;
    }

    private void preparePluginContext() {

        if (plugins == null) {
            return;
        }

        // passive all context;
        for (StateContext lctx : pluginsStateContext) {
            lctx.active = false;
        }

        for (AbstractPlugin plugin : plugins) {
            StateContext lctx = getStateContext(plugin);
            if (lctx == null) {
                lctx = new StateContext();
                lctx.plugin = plugin;
                plugin.getContextRegistry().buildContext();
                pluginsStateContext.add(lctx);
            }
            lctx.active = true;
        }

        // clean context
        List<StateContext> inactiveContext = new ArrayList<DeviceMenuManager.StateContext>();
        for (StateContext lctx : pluginsStateContext) {
            if (!lctx.active) {
                inactiveContext.add(lctx);
            }
        }

        for (StateContext lctx : inactiveContext) {
            pluginsStateContext.remove(lctx);
        }
        inactiveContext.clear();
        inactiveContext = null;

    }

    /**
     * merge all context entry of each plugin in device context
     */
    private void mergeContext() {
        for (StateContext lctx : pluginsStateContext) {
            deviceContextEntries.addAll(lctx.plugin.getContextRegistry()
                    .getContextEntries());
        }
    }

    public boolean isActiveContext(ContextEntry<?> agc) {
        for (StateContext lctx : pluginsStateContext) {
            if (lctx.plugin.equals(agc.getHost())) {
                return lctx.active;
            }
        }
        return false;
    }

    public void setPlugins(List<AbstractPlugin> plugins) {
        this.plugins = plugins;
    }

    public void show(int x, int y) {
        preparePluginContext();
        mergeContext();
        buildContextMenu();
        contextMenu.show(device2D, x, y);
    }

    private void resolveGroup() {
        actionRegistry = new HashMap<String, List<ContextEntry<?>>>();

        for (ContextEntry<?> agc : deviceContextEntries) {
            if (isActiveContext(agc)) {
                List<ContextEntry<?>> group = getGroup(agc.getGroup());
                group.add(agc);
            }
        }

    }

    /**
     * get context entry for the specified group
     * 
     * @param group
     * @return
     */
    private List<ContextEntry<?>> getGroup(String group) {
        List<ContextEntry<?>> agcList = actionRegistry.get(group);
        if (agcList == null) {
            actionRegistry.put(group, new ArrayList<ContextEntry<?>>());
        }
        return actionRegistry.get(group);
    }

    /**
     * build context
     */
    private void buildContextMenu() {
        contextMenu.removeAll();
        resolveGroup();
        int count = 0;
        for (Map.Entry<String, List<ContextEntry<?>>> entry : actionRegistry
                .entrySet()) {
            List<ContextEntry<?>> entryItems = entry.getValue();
            for (ContextEntry<?> agc : entryItems) {
                contextMenu.add(agc.getItem());
            }
            count++;
            if (count < actionRegistry.size()) {
                contextMenu.addSeparator();
            }

        }
    }

}
