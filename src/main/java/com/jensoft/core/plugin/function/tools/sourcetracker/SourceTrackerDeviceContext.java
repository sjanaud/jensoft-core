/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.tools.sourcetracker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jensoft.core.device.ContextEntry;
import com.jensoft.core.plugin.PluginEvent;
import com.jensoft.core.plugin.PluginListener;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.sharedicon.SharedIcon;
import com.jensoft.core.sharedicon.common.Common;

/**
 * <code>SerieTrackerDeviceContext</code> provides the
 * lock selected entry and a serie selector.
 * 
 * @author Sebastien Janaud
 */
public class SourceTrackerDeviceContext extends ContextEntry<SourceTrackerPlugin> {

    /** tracker root menu */
    private JMenu rootMenu;

    /** check for active translate */
    private JMenuItem trackerLocker;

    /** tracker selecter */
    private JMenu serieSelecterMenu;

    /** map serie item */
    private Map<SourceFunction, JMenuItem> sourceSelectersMap;

    private List<JMenuItem> sourceSelecters;

    /** serie tracker icon */
    private ImageIcon trackerrootIcon = SharedIcon.getCommon(Common.TAG_LABEL);
    private ImageIcon lockIcon = SharedIcon.getCommon(Common.LOCK);
    private ImageIcon unlockIcon = SharedIcon.getCommon(Common.UNLOCK);

    /** listener */
    private ContextSourceTrackerListener listener = new ContextSourceTrackerListener();

    class ContextSourceTrackerListener implements SourceTrackerListener {

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerListener#sourceTracked(com.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerEvent)
         */
        @Override
        public void sourceTracked(SourceTrackerEvent event) {
            for (JMenuItem item : sourceSelecters) {
                item.setIcon(unlockIcon);
            }
            JMenuItem item = sourceSelectersMap.get(event.getSourceFunction());
            item.setIcon(lockIcon);
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerListener#sourceRegistered(com.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerEvent)
         */
        @Override
        public void sourceRegistered(SourceTrackerEvent event) {
        }

        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerListener#currentTrack(com.jensoft.core.plugin.function.tools.sourcetracker.SourceTrackerEvent)
         */
        @Override
        public void currentTrack(SourceTrackerEvent event) {
        }
    }

    /**
     * create a default device menu context for the capture plugin
     */
    public SourceTrackerDeviceContext() {
        sourceSelecters = new ArrayList<JMenuItem>();
        sourceSelectersMap = new HashMap<SourceFunction, JMenuItem>();
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#buildContext()
     */
    @Override
    public void buildContext() {

        if (getHost() == null) {
            return;
        }

        getHost().removeSourceTrackerListener(listener);
        getHost().addSourceTrackerListener(listener);

        sourceSelecters.clear();
        sourceSelectersMap.clear();

        rootMenu = new JMenu("Source Tracker");
        rootMenu.setIcon(trackerrootIcon);

        trackerLocker = new JMenuItem("Lock");
        rootMenu.add(trackerLocker);
        if (getHost().isLockSelected()) {
            trackerLocker.setIcon(lockIcon);
            trackerLocker.setText("unlock");
        }
        else {
            trackerLocker.setIcon(unlockIcon);
            trackerLocker.setText("lock");
        }
        trackerLocker.addActionListener(getHost().getSerieTrackerLockUnlockAction());

        getHost()
                .addPluginListener(new PluginListener() {

                    @Override
                    public void pluginUnlockSelected(PluginEvent pluginEvent) {
                        trackerLocker.setText("lock");
                        trackerLocker.setIcon(unlockIcon);
                    }

                    @Override
                    public void pluginSelected(PluginEvent pluginEvent) {
                        trackerLocker.setText("unlock");
                        trackerLocker.setIcon(lockIcon);
                    }
                });

        serieSelecterMenu = new JMenu("Sources");
        List<SourceFunction> sourceList = getHost().getSources();
        int count = 1;
        for (final SourceFunction source : sourceList) {
            String name = source.getName();
            if (name == null) {
                name = "src-" + count;
                source.setName(name);
            }
            JMenuItem serieSelecter = new JMenuItem(name);
            serieSelecterMenu.add(serieSelecter);
            sourceSelectersMap.put(source, serieSelecter);
            sourceSelecters.add(serieSelecter);
            serieSelecter.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    getHost().trackSource(source);
                }
            });
            count++;

            getHost().trackSource(source);
        }
        rootMenu.add(serieSelecterMenu);
        setGroup("Tracker");
        setItem(rootMenu);
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof SourceTrackerPlugin) {
            return true;
        }
        return false;
    }

}
