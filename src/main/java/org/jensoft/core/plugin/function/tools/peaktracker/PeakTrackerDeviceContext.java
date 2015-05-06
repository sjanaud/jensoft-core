/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.tools.peaktracker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.jensoft.core.device.ContextEntry;
import org.jensoft.core.plugin.PluginEvent;
import org.jensoft.core.plugin.PluginListener;
import org.jensoft.core.plugin.function.source.SourceFunction;
import org.jensoft.core.sharedicon.SharedIcon;
import org.jensoft.core.sharedicon.common.Common;

/**
 * <code>SerieTrackerDeviceContext</code> provides the
 * lock selected entry and a serie selector.
 * 
 * @author Sebastien Janaud
 */
public class PeakTrackerDeviceContext extends ContextEntry<PeakTrackerPlugin> {

    /** tracker root menu */
    private JMenu rootMenu;

    /** check for active translate */
    private JMenuItem trackerLocker;

    /** tracker selector */
    private JMenu serieSelecterMenu;

    /** map serie item */
    private Map<SourceFunction, JMenuItem> serieSelectersMap;

    /** serie selectors */
    private List<JMenuItem> serieSelecters;

    /** serie tracker icon */
    private ImageIcon trackerrootIcon = SharedIcon.getCommon(Common.PEAK);
    private ImageIcon lockIcon = SharedIcon.getCommon(Common.LOCK);
    private ImageIcon unlockIcon = SharedIcon.getCommon(Common.UNLOCK);
    private ImageIcon checkIcon = SharedIcon.getCommon(Common.ITEM);

    /** listener */
    private ContextSourceTrackerListener listener = new ContextSourceTrackerListener();

    class ContextSourceTrackerListener implements PeakTrackerListener {

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.tools.peaktracker.PeakTrackerListener#peakTracked(com.jensoft.core.plugin.function.tools.peaktracker.PeakTrackerEvent)
         */
        @Override
        public void peakTracked(PeakTrackerEvent event) {
            //System.out.println("context peakTracked :" + event.getSerie().getName());

            JMenuItem item = serieSelectersMap.get(event.getSourceFunction());
            if (getHost().isTracked(event.getSourceFunction())) {
                item.setIcon(checkIcon);
            }
            else {
                item.setIcon(null);
            }

        }

     
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.function.tools.peaktracker.PeakTrackerListener#sourceRegistered(com.jensoft.core.plugin.function.tools.peaktracker.PeakTrackerEvent)
         */
        @Override
        public void sourceRegistered(PeakTrackerEvent event) {
            //System.out.println("context source registered :" + event.getSourceFunction().getName());
        }

    }

    /**
     * create a default device menu context for the capture plugin
     */
    public PeakTrackerDeviceContext() {
        serieSelecters = new ArrayList<JMenuItem>();
        serieSelectersMap = new HashMap<SourceFunction, JMenuItem>();
    }

   
  
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#buildContext()
     */
    @Override
    public void buildContext() {

        if (getHost() == null) {
            return;
        }

        getHost().removePeakTrackerListener(listener);
        getHost().addPeakTrackerListener(listener);

        serieSelecters.clear();
        serieSelectersMap.clear();

        rootMenu = new JMenu("Peak Tracker");
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
        trackerLocker.addActionListener(getHost().getPeakTrackerLockUnlockAction());

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

        serieSelecterMenu = new JMenu("Series");
        List<SourceFunction> series = getHost().getSources();
        int count = 1;
        for (final SourceFunction iSerie2D : series) {
            String name = iSerie2D.getName();
            if (name == null) {
                name = "serie-" + count;
                iSerie2D.setName(name);
            }
            JMenuItem serieSelecter = new JMenuItem(name);
            serieSelecterMenu.add(serieSelecter);
            serieSelectersMap.put(iSerie2D, serieSelecter);
            serieSelecters.add(serieSelecter);
            serieSelecter.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                   // System.out.println("actionPerformed ::iSerie2D " + iSerie2D.getName() + "is track  ?"
                          //  + getHost().isTracked(iSerie2D));
                    if (getHost().isTracked(iSerie2D)) {
                        getHost().untrackSource(iSerie2D);
                    }
                    else {
                        getHost().trackSource(iSerie2D);
                    }
                    getHost().getProjection().getView().repaintDevice();
                }
            });
            count++;

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
        if (getHost() != null && getHost() instanceof PeakTrackerPlugin) {
            return true;
        }
        return false;
    }

}
