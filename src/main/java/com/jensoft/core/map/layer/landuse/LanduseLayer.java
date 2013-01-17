/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.landuse;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.layer.AbstractMapLayer;
import com.jensoft.core.map.projection.Map2D;

public class LanduseLayer extends AbstractMapLayer {

    private CimeteryLanduseRenderer cimeteryLanduseRenderer;

    private List<Landuse> registeredLanduse;

    public LanduseLayer() {
        registeredLanduse = new ArrayList<Landuse>();

        cimeteryLanduseRenderer = new CimeteryLanduseRenderer();

    }

    public void registerLanduse(Landuse landuse) {
        if (!isAlreadyRegister(landuse)) {
            registeredLanduse.add(landuse);
            super.registerPrimitive(landuse.getPrimitive());
        }
    }

    @Override
    public void clearLayer() {

    }

    public void registerLanduses(List<Landuse> landuse) {
        for (Landuse l : landuse) {
            registerLanduse(l);
        }
    }

    private boolean isAlreadyRegister(Landuse l) {
        for (Landuse landuse : registeredLanduse) {
            if (landuse.equals(l)) {
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.map.layer.AbstractMapLayer#doPaintMap(com.jensoft.sw2d.core.map.projection.Map2D)
     */
    @Override
    public void doPaintMap(Map2D map2D) {
        project();
        for (Landuse l : registeredLanduse) {
            if (l.getNature().equals(LanduseNature.CIMETERY)) {
                cimeteryLanduseRenderer.paintLanduse(map2D.getGraphics2D(), l);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.map.layer.AbstractMapLayer#doPaint(java.awt.Graphics2D)
     */
    @Override
    public void doPaint(Graphics2D g2d) {
        project();
        for (Landuse l : registeredLanduse) {
            if (l.getNature().equals(LanduseNature.CIMETERY)) {
                cimeteryLanduseRenderer.paintLanduse(g2d, l);
            }
        }

    }

}
