/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.marker.context;

import javax.swing.ImageIcon;

import org.jensoft.core.plugin.marker.marker.MarkerIcon;

public class MarkerIconCreator extends AbstractMarkerCreator {

    public MarkerIconCreator(String text, ImageIcon markerIcon) {
        super(text, markerIcon, new MarkerIcon(markerIcon));
    }
}
