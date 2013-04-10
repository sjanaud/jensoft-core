/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.projection.GeoPosition;
import com.jensoft.core.map.projection.Map2D;

public class TraceLayer extends AbstractMapLayer {

    public TraceLayer() {
    }

    private List<GeoPosition> geoPositions;

    public void addGeoPosition(GeoPosition geoPosition) {
        if (geoPositions == null) {
            geoPositions = new ArrayList<GeoPosition>();
        }

        geoPositions.add(geoPosition);
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.map.layer.AbstractMapLayer#doPaintMap(com.jensoft.core.map.projection.Map2D)
     */
    @Override
    public void doPaintMap(Map2D map2d) {
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.map.layer.AbstractMapLayer#doPaint(java.awt.Graphics2D)
     */
    @Override
    public void doPaint(Graphics2D g2d) {
    }

}
