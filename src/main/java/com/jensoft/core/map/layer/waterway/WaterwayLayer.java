/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.waterway;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.layer.AbstractMapLayer;
import com.jensoft.core.map.projection.Map2D;

public class WaterwayLayer extends AbstractMapLayer {

    private RiverbankWaterwayRenderer riverBankRenderer;
    private RiverWaterwayRenderer riverenderer;

    private List<Waterway> registeredWarterways;

    public WaterwayLayer() {
        registeredWarterways = new ArrayList<Waterway>();
        riverBankRenderer = new RiverbankWaterwayRenderer();
        riverenderer = new RiverWaterwayRenderer();

    }

    public void registerWaterway(Waterway waterWay) {
        if (!isAlreadyRegister(waterWay)) {
            registeredWarterways.add(waterWay);
            super.registerPrimitive(waterWay.getPrimitive());
        }
    }

    public void registerWaterway(List<Waterway> waterWays) {
        for (Waterway ww : waterWays) {
            registerWaterway(ww);
        }
    }

    private boolean isAlreadyRegister(Waterway ww) {
        for (Waterway waterway : registeredWarterways) {
            if (waterway.equals(ww)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doPaintMap(Map2D map2D) {
        project();
        for (Waterway ww : registeredWarterways) {
            if (ww.getNature().equals(WaterwayNature.RIVERBANK)) {
                riverBankRenderer.paintWaterway(map2D.getGraphics2D(), ww);
            }

        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.map.layer.AbstractMapLayer#doPaint(java.awt.Graphics2D)
     */
    @Override
    public void doPaint(Graphics2D g2d) {
        project();
        for (Waterway ww : registeredWarterways) {
            if (ww.getNature().equals(WaterwayNature.RIVERBANK)) {
                riverBankRenderer.paintWaterway(g2d, ww);
            }

        }

    }

}
