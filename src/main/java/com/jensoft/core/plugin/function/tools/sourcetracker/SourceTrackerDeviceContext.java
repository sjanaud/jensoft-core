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
    private ContextSerieTrackerListener listener = new ContextSerieTrackerListener();

    class ContextSerieTrackerListener implements SourceTrackerListener {

        /*
         * (non-Javadoc)
         * @see
         * com.jensoft.sw2d.core.plugin.plottools.serietracker.SerieTrackerListener#serieTracked(com.jensoft.sw2d.core
         * .plugin.plottools.serietracker.SerieTrackerEvent)
         */
        @Override
        public void sourceTracked(SourceTrackerEvent event) {
            System.out.println("sourceSelecters size " + sourceSelecters.size());
            for (JMenuItem item : sourceSelecters) {
                System.out.println("unlock icon set on item " + item.getText());
                item.setIcon(unlockIcon);
            }

            System.out.println("context source tracked :" + event.getSourceFunction().getName());
            JMenuItem item = sourceSelectersMap.get(event.getSourceFunction());
            item.setIcon(lockIcon);

        }

        /*
         * (non-Javadoc)
         * @see
         * com.jensoft.sw2d.core.plugin.plottools.serietracker.SerieTrackerListener#serieRegistered(com.jensoft.sw2d
         * .core.plugin.plottools.serietracker.SerieTrackerEvent)
         */
        @Override
        public void sourceRegistered(SourceTrackerEvent event) {
            System.out.println("context source registered :" + event.getSourceFunction().getName());
        }

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

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.device.ContextEntry#buildContext()
     */
    @Override
    public void buildContext() {

        if (getHost() == null) {
            return;
        }

        getHost().removeSerieTrackerListener(listener);
        getHost().addSerieTrackerListener(listener);

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
        List<SourceFunction> series = getHost().getSources();
        int count = 1;
        for (final SourceFunction iSerie2D : series) {
            String name = iSerie2D.getName();
            if (name == null) {
                name = "src-" + count;
                iSerie2D.setName(name);
            }
            JMenuItem serieSelecter = new JMenuItem(name);
            serieSelecterMenu.add(serieSelecter);
            sourceSelectersMap.put(iSerie2D, serieSelecter);
            sourceSelecters.add(serieSelecter);
            serieSelecter.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    getHost().trackSource(iSerie2D);
                }
            });
            count++;

            getHost().trackSource(iSerie2D);
        }
        rootMenu.add(serieSelecterMenu);
        setGroup("Tracker");
        setItem(rootMenu);
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.device.ContextEntry#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof SourceTrackerPlugin) {
            return true;
        }
        return false;
    }

}
