/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.natural;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.map.layer.AbstractMapLayer;
import org.jensoft.core.map.projection.Map2D;

/**
 * @author sebastien
 */
public class NaturalLayer extends AbstractMapLayer {

    private List<Natural> registeredNatural;

    public NaturalLayer() {
        registeredNatural = new ArrayList<Natural>();

    }

    public void registerNatural(Natural natural) {
        if (!isAlreadyRegister(natural)) {
            registeredNatural.add(natural);
            super.registerPrimitive(natural.getPrimitive());
        }
    }

    public void registerNatural(List<Natural> naturals) {
        for (Natural mm : naturals) {
            registerNatural(mm);
        }
    }

    private boolean isAlreadyRegister(Natural n) {
        for (Natural natural : registeredNatural) {
            if (natural.equals(n)) {
                return true;
            }
        }
        return false;
    }

    private void paintCoastlineSkelet(Graphics2D g2d) {

        project();

        CoastlineSkelet skelet = new CoastlineSkelet(getGeoBound(),
                                                     getProjection2D());

        for (Natural natural : registeredNatural) {
            if (natural.getNature().equals(NaturalNature.COASTLINE_NATURE)) {
                skelet.register(natural);
            }
        }

        skelet.createSkelet();

        // List<LineString> lss = skelet.getIntersection();
        // for(LineString ls : lss){
        // Coordinate[] C = ls.getCoordinates();
        //
        // Coordinate cStart = C[0];
        // Coordinate cEnd = C[C.length - 1];
        //
        // g2d.setColor(Color.GREEN);
        // Ellipse2D eStart = new
        // Ellipse2D.Double(cStart.x-10,cStart.y-10,20,20);
        // g2d.draw(eStart);
        //
        // g2d.setColor(Color.RED);
        // Rectangle2D eend = new Rectangle2D.Double(cEnd.x-15,cEnd.y-15,30,30);
        // g2d.draw(eend);
        //
        //
        // GeneralPath path = new GeneralPath();
        // path.moveTo(cStart.x,cStart.y);
        // for (int i = 0; i < C.length; i++) {
        //
        // path.lineTo(C[i].x,C[i].y);
        // }
        // g2d.setColor(Color.BLACK);
        // g2d.draw(path);
        // }

        Area closedCoastline = skelet.getCoastlineSkelet();
        g2d.setColor(new Color(181, 208, 208));
        g2d.fill(closedCoastline);

        // List<GeneralPath> gps = skelet.getClosedPathCoastlines();
        // for(GeneralPath cp : gps){
        // g2d.setColor(Color.GREEN);
        // g2d.setStroke(new BasicStroke(4f));
        // //g2d.draw(cp);
        // }
        //

        //System.out.println("coastline skelet!");

    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.map.layer.AbstractMapLayer#doPaintMap(org.jensoft.core.map.projection.Map2D)
     */
    @Override
    public void doPaintMap(Map2D map2D) {
        paintCoastlineSkelet(map2D.getGraphics2D());
    }

 
    /* (non-Javadoc)
     * @see org.jensoft.core.map.layer.AbstractMapLayer#doPaint(java.awt.Graphics2D)
     */
    @Override
    public void doPaint(Graphics2D g2d) {
        paintCoastlineSkelet(g2d);
    }

}
