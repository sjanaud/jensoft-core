/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.marker.context;

import com.jensoft.core.sharedicon.SharedIcon;
import com.jensoft.core.sharedicon.marker.Marker;

public class MarkerDefaultDeviceContext extends
        AbstractMarkerDefaultDeviceContext {

    /**
     * create a default device menu context for the capture plugin
     */
    public MarkerDefaultDeviceContext() {
        super();
        registerMarkerCreator(new MarkerIconCreator("Mark as Max",
                                                    SharedIcon.getMarker(Marker.MARKER_ROUNDED_RED)));
        registerMarkerCreator(new MarkerIconCreator("Mark as Min",
                                                    SharedIcon.getMarker(Marker.MARKER_ROUNDED_BLUE)));
    }

}
