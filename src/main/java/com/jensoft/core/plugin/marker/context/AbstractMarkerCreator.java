/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.marker.context;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

import com.jensoft.core.plugin.marker.MarkerPlugin;
import com.jensoft.core.plugin.marker.MarkerPlugin.CreateMarkerAction;
import com.jensoft.core.plugin.marker.marker.AbstractMarker;

public abstract class AbstractMarkerCreator extends JMenuItem {

    /** marker to create on item click action */
    private AbstractMarker marker;

    /** host plugin in */
    private MarkerPlugin host;

    /** create marker action */
    private CreateMarkerAction markerAction;

    /**
     * @return the host
     */
    public MarkerPlugin getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(MarkerPlugin host) {
        this.host = host;
    }

    /**
     * @param marker
     */
    public AbstractMarkerCreator(String label, ImageIcon icon,
            AbstractMarker marker) {
        super();
        setText(label);
        setIcon(icon);
        this.marker = marker;

    }

    public void registerListener() {
        if (markerAction == null) {
            markerAction = getHost().getCreateMarkerAction(marker);
            addActionListener(markerAction);
        }
    }

    /**
     * @return the marker
     */
    public AbstractMarker getMarker() {
        return marker;
    }

    /**
     * @param marker
     *            the marker to set
     */
    public void setMarker(AbstractMarker marker) {
        this.marker = marker;
    }

}
