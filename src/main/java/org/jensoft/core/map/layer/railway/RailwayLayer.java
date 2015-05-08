/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.railway;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.map.layer.AbstractMapLayer;
import org.jensoft.core.map.layer.railway.tramway.TramRailwayRenderer;
import org.jensoft.core.map.projection.Map2D;

public class RailwayLayer extends AbstractMapLayer {

    private RailRenderer railRenderer;
    private TramRailwayRenderer tramRailRenderer;
    private List<Railway> registeredRailway;

    public RailwayLayer() {
        registeredRailway = new ArrayList<Railway>();
        railRenderer = new RailRenderer();
        tramRailRenderer = new TramRailwayRenderer();

    }

    public void registerRailway(Railway railway) {
        if (!isAlreadyRegister(railway)) {
            registeredRailway.add(railway);
            super.registerPrimitive(railway.getPrimitive());
        }
    }

    public void registerRailways(List<Railway> railways) {
        for (Railway rw : railways) {
            registerRailway(rw);
        }
    }

    private boolean isAlreadyRegister(Railway rw) {
        for (Railway railway : registeredRailway) {
            if (railway.equals(rw)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doPaintMap(Map2D map2D) {
        project();
        for (Railway rmm : registeredRailway) {
            // if(rmm.getNature().equals(RailwayNature.RAIL))
            // railRenderer.paintRailway(g2d, rmm);
            if (rmm.getNature().equals(RailwayNature.TRAM)) {
                tramRailRenderer.paintRailway(map2D.getGraphics2D(), rmm);
            }
        }
    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.map.layer.AbstractMapLayer#doPaint(java.awt.Graphics2D)
     */
    @Override
    public void doPaint(Graphics2D g2d) {
        project();
        for (Railway rmm : registeredRailway) {
            // if(rmm.getNature().equals(RailwayNature.RAIL))
            // railRenderer.paintRailway(g2d, rmm);
            if (rmm.getNature().equals(RailwayNature.TRAM)) {
                tramRailRenderer.paintRailway(g2d, rmm);
            }
        }
    }

}
