/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.capture;

import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jensoft.core.device.ContextEntry;
import com.jensoft.core.sharedicon.SharedIcon;
import com.jensoft.core.sharedicon.common.Common;

public class CaptureDefaultDeviceContext extends ContextEntry<CapturePlugin> {

    /** the root capture menu */
    private JMenu captureMenu;

    private ImageIcon captureIcon = SharedIcon.getCommon(Common.CAPTURE);
    private ImageIcon formatIcon = SharedIcon.getCommon(Common.ITEM);

    /**
     * create a default device menu context for the capture plugin
     */
    public CaptureDefaultDeviceContext() {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#buildContext()
     */
    @Override
    public void buildContext() {
        captureMenu = new JMenu("Capture");
        captureMenu.setIcon(captureIcon);
        String[] f = ImageIO.getWriterFormatNames();
        TreeSet<String> formatSet = new TreeSet<String>();
        for (String s : f) {
            formatSet.add(s.toLowerCase());
        }
        String[] formats = formatSet.toArray(new String[0]);
        for (int i = 0; i < formats.length; i++) {
            JMenuItem capureFormat = new JMenuItem(formats[i]);
            capureFormat.addActionListener(getHost()
                    .getCaptureAction(formats[i]));
            capureFormat.setIcon(formatIcon);
            captureMenu.add(capureFormat);
        }
        setGroup("capture");
        setItem(captureMenu);

    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.device.ContextEntry#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof CapturePlugin) {
            return true;
        }
        return false;
    }

}
