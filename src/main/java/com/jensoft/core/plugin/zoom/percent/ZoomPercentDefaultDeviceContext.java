/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.percent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jensoft.core.device.ContextEntry;
import com.jensoft.core.plugin.zoom.percent.ZoomPercentPlugin.ZoomPercent;
import com.jensoft.core.plugin.zoom.percent.ZoomPercentPlugin.ZoomPercentAction;
import com.jensoft.core.sharedicon.SharedIcon;
import com.jensoft.core.sharedicon.common.Common;

/**
 * <code>ZoomPercentDefaultDeviceContext</code>
 * 
 * @author Sebastien Janaud
 */
public class ZoomPercentDefaultDeviceContext extends ContextEntry<ZoomPercentPlugin> {

    private ImageIcon percentIcon = SharedIcon.getCommon(Common.PERCENT);
    private ImageIcon itemIcon = SharedIcon.getCommon(Common.ITEM);

    /**
     * create a default device menu context for the capture plugin
     */
    public ZoomPercentDefaultDeviceContext() {
    }

    /**
     * get the percent action
     * @param zoomPercent
     * @return percent action
     */
    private ZoomPercentAction getPercentAction(ZoomPercent zoomPercent) {
        return getHost()
                .getZoomPercentAction(zoomPercent);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#buildContext()
     */
    @Override
    public void buildContext() {

        if (getHost() == null) {
            return;
        }

        JMenu zoomMenu = new JMenu("Zoom");
        zoomMenu.setIcon(percentIcon);

        JMenuItem zoom10 = new JMenuItem("10 %");
        zoom10.setIcon(itemIcon);
        zoom10.addActionListener(getPercentAction(ZoomPercent.Zoom25));

        JMenuItem zoom25 = new JMenuItem("25 %");
        zoom25.setIcon(itemIcon);
        zoom25.addActionListener(getPercentAction(ZoomPercent.Zoom25));

        JMenuItem zoom50 = new JMenuItem("50 %");
        zoom50.setIcon(itemIcon);
        zoom50.addActionListener(getPercentAction(ZoomPercent.Zoom50));

        JMenuItem zoom200 = new JMenuItem("200 %");
        zoom200.setIcon(itemIcon);
        zoom200.addActionListener(getPercentAction(ZoomPercent.Zoom200));

        JMenuItem zoom400 = new JMenuItem("400 %");
        zoom400.setIcon(itemIcon);
        zoom400.addActionListener(getPercentAction(ZoomPercent.Zoom400));

        JMenuItem zoom800 = new JMenuItem("800 %");
        zoom800.setIcon(itemIcon);
        zoom800.addActionListener(getPercentAction(ZoomPercent.Zoom800));

        JMenuItem zoomInit = new JMenuItem("Init");
        zoomInit.setIcon(itemIcon);
        zoomInit.addActionListener(getPercentAction(ZoomPercent.Init));

        zoomMenu.add(zoom10);
        zoomMenu.add(zoom25);
        zoomMenu.add(zoom50);
        zoomMenu.add(zoom200);
        zoomMenu.add(zoom400);
        zoomMenu.add(zoom800);
        zoomMenu.addSeparator();
        zoomMenu.add(zoomInit);

        setGroup("zoom");
        setItem(zoomMenu);

    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof ZoomPercentPlugin) {
            return true;
        }
        return false;
    }

}
