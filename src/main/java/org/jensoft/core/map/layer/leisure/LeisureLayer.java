/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.leisure;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.map.layer.AbstractMapLayer;
import org.jensoft.core.map.projection.Map2D;

public class LeisureLayer extends AbstractMapLayer {

    private WaterParkLeisureRenderer waterParkLeisureRenderer;
    private StadiumLeisureRenderer stadiumLeisureRenderer;
    private ParkLeisureRenderer parkLeisureRenderer;
    private GardenLeisureRenderer gardenLeisureRenderer;

    private List<Leisure> registeredLeisures;

    public LeisureLayer() {
        registeredLeisures = new ArrayList<Leisure>();

        waterParkLeisureRenderer = new WaterParkLeisureRenderer();
        stadiumLeisureRenderer = new StadiumLeisureRenderer();
        parkLeisureRenderer = new ParkLeisureRenderer();
        gardenLeisureRenderer = new GardenLeisureRenderer();

    }

    public void registerLeisure(Leisure leisure) {
        if (!isAlreadyRegister(leisure)) {
            registeredLeisures.add(leisure);
            super.registerPrimitive(leisure.getPrimitive());
        }
    }

    public void registerLeisures(List<Leisure> leisures) {
        for (Leisure l : leisures) {
            registerLeisure(l);
        }
    }

    private boolean isAlreadyRegister(Leisure l) {
        for (Leisure leisure : registeredLeisures) {
            if (leisure.equals(l)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doPaintMap(Map2D map2D) {
        project();
        Graphics2D g2d = map2D.getGraphics2D();
        for (Leisure l : registeredLeisures) {
            if (l.getNature().equals(LeisureNature.PARK)) {
                parkLeisureRenderer.paintLeisure(g2d, l);
            }
            if (l.getNature().equals(LeisureNature.WATER_PARK)) {
                waterParkLeisureRenderer.paintLeisure(g2d, l);
            }
            if (l.getNature().equals(LeisureNature.STADIUM)) {
                stadiumLeisureRenderer.paintLeisure(g2d, l);
            }
            if (l.getNature().equals(LeisureNature.GARDEN)) {
                gardenLeisureRenderer.paintLeisure(g2d, l);
            }
        }
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.map.layer.AbstractMapLayer#doPaint(java.awt.Graphics2D)
     */
    @Override
    public void doPaint(Graphics2D g2d) {
        project();
        for (Leisure l : registeredLeisures) {
            if (l.getNature().equals(LeisureNature.PARK)) {
                parkLeisureRenderer.paintLeisure(g2d, l);
            }
            if (l.getNature().equals(LeisureNature.WATER_PARK)) {
                waterParkLeisureRenderer.paintLeisure(g2d, l);
            }
            if (l.getNature().equals(LeisureNature.STADIUM)) {
                stadiumLeisureRenderer.paintLeisure(g2d, l);
            }
            if (l.getNature().equals(LeisureNature.GARDEN)) {
                gardenLeisureRenderer.paintLeisure(g2d, l);
            }
        }
    }

}
