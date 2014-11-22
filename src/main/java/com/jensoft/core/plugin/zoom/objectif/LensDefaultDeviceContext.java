/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.objectif;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jensoft.core.device.ContextEntry;
import com.jensoft.core.plugin.PluginEvent;
import com.jensoft.core.plugin.PluginListener;
import com.jensoft.core.sharedicon.SharedIcon;
import com.jensoft.core.sharedicon.common.Common;

/**
 * <code>LensDefaultDeviceContext</code>
 * 
 * @author Sebastien Janaud
 */
public class LensDefaultDeviceContext extends ContextEntry<ZoomLensPlugin> {

    /** objectif menu */
    private JMenu rootMenu;

    /** check for active objectif plugin */
    private JMenuItem objectifLocker;

    /** objectif icon */
    private ImageIcon objectifRootIcon = SharedIcon.getCommon(Common.LENS16);
    private ImageIcon lockIcon = SharedIcon.getCommon(Common.LOCK);
    private ImageIcon unlockIcon = SharedIcon.getCommon(Common.UNLOCK);

    /**
     * create a default device menu context for the capture plugin
     */
    public LensDefaultDeviceContext() {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#buildContext()
     */
    @Override
    public void buildContext() {

        if (getHost() == null) {
            return;
        }

        rootMenu = new JMenu("Zoom Lens");
        rootMenu.setIcon(objectifRootIcon);

        objectifLocker = new JMenuItem("Lock");
        rootMenu.add(objectifLocker);
        if (getHost().isLockSelected()) {
            objectifLocker.setIcon(lockIcon);
            objectifLocker.setText("unlock");
        }
        else {
            objectifLocker.setIcon(unlockIcon);
            objectifLocker.setText("lock");
        }
        objectifLocker.addActionListener(getHost()
                .getObjectifLockUnlockAction());

        getHost().addPluginListener(new PluginListener() {

            @Override
            public void pluginUnlockSelected(PluginEvent pluginEvent) {
                objectifLocker.setText("lock");
                objectifLocker.setIcon(unlockIcon);
            }

            @Override
            public void pluginSelected(PluginEvent pluginEvent) {
                objectifLocker.setText("unlock");
                objectifLocker.setIcon(lockIcon);
            }
        });

        setGroup("zoom");
        setItem(rootMenu);
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof ZoomLensPlugin) {
            return true;
        }
        return false;
    }

}
