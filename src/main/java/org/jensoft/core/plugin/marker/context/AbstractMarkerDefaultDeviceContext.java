/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.marker.context;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.jensoft.core.device.ContextEntry;
import org.jensoft.core.plugin.PluginEvent;
import org.jensoft.core.plugin.PluginListener;
import org.jensoft.core.plugin.marker.MarkerPlugin;
import org.jensoft.core.sharedicon.SharedIcon;
import org.jensoft.core.sharedicon.common.Common;
import org.jensoft.core.sharedicon.marker.Marker;

public abstract class AbstractMarkerDefaultDeviceContext extends ContextEntry {

    /** the root marker menu */
    private JMenu markerMenu;

    /** lock unlock item */
    private JMenuItem activeItem;

    /** paste new marker */
    private JMenu addMarkerMenu;

    /** delete all marker */
    private JMenuItem deleteAllMarker;

    /** add item list */
    private List<AbstractMarkerCreator> markerCreators;

    private ImageIcon markerIcon = SharedIcon
            .getMarker(Marker.MARKER_ROUNDED_LIGHTBLUE);
    private ImageIcon markerAdd = SharedIcon
            .getMarker(Marker.MARKER_ROUNDED_BLUE_ADD);
    private ImageIcon markerRemove = SharedIcon
            .getMarker(Marker.MARKER_ROUNDED_BLUE_REMOVE);
    private ImageIcon itemIcon = SharedIcon.getCommon(Common.ITEM);
    private ImageIcon lockIcon = SharedIcon.getCommon(Common.LOCK);
    private ImageIcon unlockIcon = SharedIcon.getCommon(Common.UNLOCK);

    /**
     * create a default device menu context for the marker plugin
     */
    public AbstractMarkerDefaultDeviceContext() {
        markerCreators = new ArrayList<AbstractMarkerCreator>();
    }

    /**
     * add marker creator item
     * 
     * @param creator
     *            the creator item to add marker menu
     */
    public void registerMarkerCreator(AbstractMarkerCreator creator) {
        markerCreators.add(creator);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#buildContext()
     */
    @Override
    public void buildContext() {
        for (AbstractMarkerCreator creator : markerCreators) {
            creator.setHost((MarkerPlugin) getHost());
            creator.registerListener();
        }

        markerMenu = new JMenu("Marker");
        markerMenu.setIcon(markerIcon);

        MarkerPlugin markerPlugin = (MarkerPlugin) getHost();

        activeItem = new JMenuItem();

        activeItem.addActionListener(markerPlugin.getMarkerLockUnlockAction());
        markerPlugin.addPluginListener(new PluginListener() {

            @Override
            public void pluginUnlockSelected(PluginEvent pluginEvent) {
                activeItem.setText("lock");
                activeItem.setIcon(unlockIcon);

                addMarkerMenu.setEnabled(false);
                deleteAllMarker.setEnabled(false);
            }

            @Override
            public void pluginSelected(PluginEvent pluginEvent) {
                activeItem.setText("unlock");
                activeItem.setIcon(lockIcon);

                addMarkerMenu.setEnabled(true);
                deleteAllMarker.setEnabled(true);
            }
        });

        markerMenu.add(activeItem);

        addMarkerMenu = new JMenu("Add");
        addMarkerMenu.setIcon(markerAdd);

        for (AbstractMarkerCreator creator : markerCreators) {
            addMarkerMenu.add(creator);
        }

        markerMenu.add(addMarkerMenu);

        deleteAllMarker = new JMenuItem("Remove All");
        deleteAllMarker.setIcon(markerRemove);
        deleteAllMarker.addActionListener(markerPlugin
                .getMarkerDeleteAllAction());
        markerMenu.add(deleteAllMarker);

        if (!getHost().isLockSelected()) {
            activeItem.setSelected(false);
            activeItem.setText("lock");
            activeItem.setIcon(unlockIcon);
            addMarkerMenu.setEnabled(false);
            deleteAllMarker.setEnabled(false);

        }
        else {

            activeItem.setSelected(true);
            activeItem.setText("unlock");
            activeItem.setIcon(lockIcon);
            addMarkerMenu.setEnabled(true);
            deleteAllMarker.setEnabled(true);
        }
        setGroup("marker");
        setItem(markerMenu);

    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof MarkerPlugin) {
            return true;
        }
        return false;
    }

}
