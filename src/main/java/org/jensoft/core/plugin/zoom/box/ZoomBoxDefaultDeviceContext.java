/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.zoom.box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.jensoft.core.device.ContextEntry;
import org.jensoft.core.plugin.PluginEvent;
import org.jensoft.core.plugin.PluginListener;
import org.jensoft.core.sharedicon.SharedIcon;
import org.jensoft.core.sharedicon.common.Common;
import org.jensoft.core.sharedicon.media.Media;

/**
 * <code>ZoomBoxDefaultDeviceContext</code>
 * 
 * @author sebastien janaud
 */
public class ZoomBoxDefaultDeviceContext extends ContextEntry<ZoomBoxPlugin> {

    private JMenu boxMenu;
    private JCheckBoxMenuItem lockItem;
    private JMenuItem clearItem;
    private JMenuItem forwardItem;
    private JMenuItem backwardItem;
    private JMenuItem replayItem;

    private ImageIcon boxIcon;
    private ImageIcon lockIcon;
    private ImageIcon unlockIcon;
    private ImageIcon backwardIcon;
    private ImageIcon forwardIcon;
    private ImageIcon replayIcon;
    private ImageIcon clearIcon;

   

    /**
     * create a default device menu context for the zoom box plugin
     */
    public ZoomBoxDefaultDeviceContext() {
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.device.ContextEntry#buildContext()
     */
    @Override
    public void buildContext() {

        boxMenu = new JMenu(getHost().getProperties().getProperty("zoom.box.context"));

        lockItem = new JCheckBoxMenuItem(getHost().getProperties().getProperty("zoom.box.lock"));

        boxIcon = SharedIcon.getCommon(Common.BOX3);
        lockIcon = SharedIcon.getCommon(Common.LOCK);
        unlockIcon = SharedIcon.getCommon(Common.UNLOCK);
        backwardIcon = SharedIcon.getMedia(Media.BACKWARD_MEDIA_SKIP16);
        forwardIcon = SharedIcon.getMedia(Media.FORWARD_MEDIA_SKIP16);
        replayIcon = SharedIcon.getCommon(Common.RUN);
        clearIcon = SharedIcon.getCommon(Common.BOX_CLEAR);

        boxMenu.setIcon(boxIcon);

        if (!getHost().isLockSelected()) {
            lockItem.setSelected(false);
            lockItem.setIcon(unlockIcon);
        }
        else {
            lockItem.setSelected(true);
            lockItem.setIcon(lockIcon);
        }

        lockItem.addActionListener(getHost().getZoomLockAction());
        
        clearItem = new JMenuItem(getHost().getProperties().getProperty("zoom.box.history.clear"));
        clearItem.setIcon(clearIcon);
        clearItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getHost().processZoomClearHistory();
            }
        });

        forwardItem = new JMenuItem(getHost().getProperties().getProperty("zoom.box.history.forward"));
        forwardItem.setIcon(forwardIcon);
        forwardItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getHost().historyNext();
                getHost().fireZoomHistory();
            }
        });

        backwardItem = new JMenuItem(getHost().getProperties().getProperty("zoom.box.history.backward"));
        backwardItem.setIcon(backwardIcon);
        backwardItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getHost().historyPrevious();
                getHost().fireZoomHistory();
            }
        });

        boxMenu.add(lockItem);
        boxMenu.add(clearItem);
        boxMenu.add(backwardItem);
        boxMenu.add(forwardItem);
        boxMenu.addSeparator();

        replayItem = new JMenuItem(getHost().getProperties().getProperty("zoom.box.history.play"));
        replayItem.setIcon(replayIcon);

        replayItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getHost().playCurrentSequence(null);
            }
        });

        boxMenu.add(replayItem);

        getHost().addPluginListener(new PluginListener() {

            @Override
            public void pluginUnlockSelected(PluginEvent pluginEvent) {
                lockItem.setText(getHost().getProperties().getProperty("zoom.box.lock"));
                lockItem.setIcon(unlockIcon);
                clearItem.setEnabled(false);
                forwardItem.setEnabled(false);
                backwardItem.setEnabled(false);
                replayItem.setEnabled(false);
            }

            @Override
            public void pluginSelected(PluginEvent pluginEvent) {
                lockItem.setText(getHost().getProperties().getProperty("zoom.box.unlock"));
                lockItem.setIcon(lockIcon);
                clearItem.setEnabled(true);
                forwardItem.setEnabled(true);
                backwardItem.setEnabled(true);
                replayItem.setEnabled(true);
            }
        });

        if (!getHost().isLockSelected()) {
            lockItem.setSelected(false);
            lockItem.setIcon(unlockIcon);
            clearItem.setEnabled(false);
            forwardItem.setEnabled(false);
            backwardItem.setEnabled(false);
            replayItem.setEnabled(false);
        }
        else {
            lockItem.setSelected(true);
            lockItem.setIcon(lockIcon);
            clearItem.setEnabled(true);
            forwardItem.setEnabled(true);
            backwardItem.setEnabled(true);
            replayItem.setEnabled(true);
        }

        setGroup("zoom");
        setItem(boxMenu);

    }



    
    /* (non-Javadoc)
     * @see org.jensoft.core.device.ContextEntry#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof ZoomBoxPlugin) {
            return true;
        }
        return false;
    }

}
