/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.translate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.jensoft.core.device.ContextEntry;
import org.jensoft.core.plugin.PluginEvent;
import org.jensoft.core.sharedicon.SharedIcon;
import org.jensoft.core.sharedicon.common.Common;

/**
 * <code>TranslateDefaultDeviceContext</code>
 * 
 * @author sebastien janaud
 */
public class TranslateDefaultDeviceContext extends ContextEntry<TranslatePlugin> {

    /** translate menu */
    private JMenu translateMenu;

    /** check for active translate */
    private JMenuItem translateActive;

    /** check control for right to left translate */
    private JMenuItem translateR2LActive;

    /** check control for bottom to top translate */
    private JMenuItem translateB2TActive;

    /** replay last translate */
    private JMenuItem replayItem;

    /** translate icon */
    private ImageIcon translateIcon;

    /** lock icon */
    private ImageIcon lockIcon;

    /** unlock icon */
    private ImageIcon unlockIcon;

    /** replay icon */
    private ImageIcon replayIcon;

    /**
     * create a default device menu context for the capture plugin
     */
    public TranslateDefaultDeviceContext() {
    }

    /**
     * update context state
     */
    private void updateState() {
        if (getHost().isLockSelected()) {
            translateActive.setIcon(lockIcon);
            translateActive.setText("release");
           
            translateR2LActive.setEnabled(true);
            translateB2TActive.setEnabled(true);
            replayItem.setEnabled(true);
        }
        else {
            translateActive.setIcon(unlockIcon);
            translateActive.setText("lock");
            
            translateR2LActive.setEnabled(false);
            translateB2TActive.setEnabled(false);
            replayItem.setEnabled(false);
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#buildContext()
     */
    @Override
    public void buildContext() {

        if (getHost() == null) {
            return;
        }

        // create icon
        translateIcon = SharedIcon.getCommon(Common.TRANSLATE);
        lockIcon = SharedIcon.getCommon(Common.LOCK);
        unlockIcon = SharedIcon.getCommon(Common.UNLOCK);
        replayIcon = SharedIcon.getCommon(Common.RUN);

        translateMenu = new JMenu("Translate");
        translateMenu.setIcon(translateIcon);

        translateActive = new JMenuItem();
        //translateActive.setEnabled(getHost().isLockSelected());
        translateActive.addActionListener(getHost().getTranslateLockUnlockAction());

        translateMenu.add(translateActive);

        translateMenu.addSeparator();

        translateR2LActive = new JMenuItem("R2L");
        translateR2LActive.addActionListener(getHost().getTranslateL2RAction());
        if (getHost().isLockL2R()) {
            translateR2LActive.setIcon(lockIcon);
        }
        else {
            translateR2LActive.setIcon(unlockIcon);
        }

        translateB2TActive = new JMenuItem("B2T");
        translateB2TActive.addActionListener(getHost().getTranslateB2TAction());
        if (getHost().isLockB2T()) {
            translateB2TActive.setIcon(lockIcon);
        }
        else {
            translateB2TActive.setIcon(unlockIcon);
        }

        translateMenu.add(translateB2TActive);
        translateMenu.add(translateR2LActive);

        
        translateMenu.addSeparator();

        replayItem = new JMenuItem("Replay");
        replayItem.setIcon(replayIcon);
        replayItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getHost().startReplay();
            }
        });
        translateMenu.add(replayItem);

        setGroup("translate");
        setItem(translateMenu);

        getHost().addTranslateListener(new TranslatePluginListener() {

           
            /**
             * @param pluginEvent
             */
            @Override
            public void pluginSelected(PluginEvent<TranslatePlugin> pluginEvent) {
                updateState();
            }

           
            /**
             * @param pluginEvent
             */
            @Override
            public void pluginUnlockSelected(PluginEvent<TranslatePlugin> pluginEvent) {
                updateState();
            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.translate.TranslatePluginListener#translated(com.jensoft.core.plugin.translate.TranslatePluginEvent)
             */
            @Override
            public void translated(TranslatePluginEvent pluginEvent) {
            }

           
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.translate.TranslatePluginListener#translateStoped(com.jensoft.core.plugin.translate.TranslatePluginEvent)
             */
            @Override
            public void translateStoped(TranslatePluginEvent pluginEvent) {
            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.translate.TranslatePluginListener#translateStarted(com.jensoft.core.plugin.translate.TranslatePluginEvent)
             */
            @Override
            public void translateStarted(TranslatePluginEvent pluginEvent) {
            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.translate.TranslatePluginListener#translateL2RChanged(com.jensoft.core.plugin.translate.TranslatePluginEvent)
             */
            @Override
            public void translateL2RChanged(TranslatePluginEvent pluginEvent) {
                TranslatePlugin t = pluginEvent.getPlugin();
                if (t.isLockL2R()) {
                    translateR2LActive.setIcon(lockIcon);
                }
                else {
                    translateR2LActive.setIcon(unlockIcon);
                }
            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.translate.TranslatePluginListener#translateB2TChanged(com.jensoft.core.plugin.translate.TranslatePluginEvent)
             */
            @Override
            public void translateB2TChanged(TranslatePluginEvent pluginEvent) {
                TranslatePlugin t = pluginEvent.getPlugin();
                if (t.isLockB2T()) {
                    translateB2TActive.setIcon(lockIcon);
                }
                else {
                    translateB2TActive.setIcon(unlockIcon);
                }
            }
        });

        updateState();
        setGroup("translate");
        setItem(translateMenu);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof TranslatePlugin) {
            return true;
        }
        return false;
    }

}
