/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.marker.context;

import org.jensoft.core.sharedicon.SharedIcon;
import org.jensoft.core.sharedicon.marker.Marker;

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
