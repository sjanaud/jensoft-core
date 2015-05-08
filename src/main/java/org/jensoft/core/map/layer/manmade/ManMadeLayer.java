/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.manmade;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.map.layer.AbstractMapLayer;
import org.jensoft.core.map.projection.Map2D;

public class ManMadeLayer extends AbstractMapLayer {

    private BuildingRenderer buildingRenderer;

    private List<ManMade> registeredManMade;

    public ManMadeLayer() {
        registeredManMade = new ArrayList<ManMade>();
        buildingRenderer = new BuildingRenderer();

    }

    public void registerManMade(ManMade manMade) {
        if (!isAlreadyRegister(manMade)) {
            registeredManMade.add(manMade);
            super.registerPrimitive(manMade.getPrimitive());
        }
    }

    public void registerManMades(List<ManMade> manMades) {
        for (ManMade mm : manMades) {
            registerManMade(mm);
        }
    }

    private boolean isAlreadyRegister(ManMade mm) {
        for (ManMade manMade : registeredManMade) {
            if (manMade.equals(mm)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doPaintMap(Map2D map2D) {
        project();
        for (ManMade rmm : registeredManMade) {
            if (rmm.getNature().equals(ManMadeNature.BUILDING)) {
                buildingRenderer.paintManMade(map2D.getGraphics2D(), rmm);
            }

        }
    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.map.layer.AbstractMapLayer#doPaint(java.awt.Graphics2D)
     */
    @Override
    public void doPaint(Graphics2D g2d) {
        project();
        for (ManMade rmm : registeredManMade) {
            if (rmm.getNature().equals(ManMadeNature.BUILDING)) {
                buildingRenderer.paintManMade(g2d, rmm);
            }

        }
    }

}
