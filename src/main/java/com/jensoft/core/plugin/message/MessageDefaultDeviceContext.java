/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.message;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jensoft.core.device.ContextEntry;
import com.jensoft.core.plugin.PluginEvent;
import com.jensoft.core.plugin.PluginListener;
import com.jensoft.core.sharedicon.SharedIcon;
import com.jensoft.core.sharedicon.common.Common;

/**
 * <code>ObjectifDefaultDeviceContext</code>
 * 
 * @author Sebastien Janaud
 */
public class MessageDefaultDeviceContext extends ContextEntry<MessagePlugin> {

    /** message root menu */
    private JMenu rootMenu;

    /** check for active message plugin */
    private JMenuItem messageLocker;

    /** objectif icon */
    private ImageIcon messageRootIcon = SharedIcon.getCommon(Common.MESSAGE);
    private ImageIcon lockIcon = SharedIcon.getCommon(Common.LOCK);
    private ImageIcon unlockIcon = SharedIcon.getCommon(Common.UNLOCK);

    /**
     * create a default device menu context for the capture plugin
     */
    public MessageDefaultDeviceContext() {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#buildContext()
     */
    @Override
    public void buildContext() {

        if (getHost() == null) {
            return;
        }

        rootMenu = new JMenu("Message");
        rootMenu.setIcon(messageRootIcon);

        messageLocker = new JMenuItem("Lock");
        rootMenu.add(messageLocker);
        if (getHost().isLockSelected()) {
            messageLocker.setIcon(lockIcon);
            messageLocker.setText("unlock");
        }
        else {
            messageLocker.setIcon(unlockIcon);
            messageLocker.setText("lock");
        }

        messageLocker.addActionListener(getHost().getMessageLockUnlockAction());

        getHost().addPluginListener(new PluginListener() {

            @Override
            public void pluginUnlockSelected(PluginEvent pluginEvent) {
                messageLocker.setText("lock");
                messageLocker.setIcon(unlockIcon);
            }

            @Override
            public void pluginSelected(PluginEvent pluginEvent) {
                messageLocker.setText("unlock");
                messageLocker.setIcon(lockIcon);
            }
        });

        setGroup("message");
        setItem(rootMenu);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof MessagePlugin) {
            return true;
        }
        return false;
    }

}
