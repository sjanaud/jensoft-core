/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.highway;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class MotorwayGroupRenderer implements HighwayGroupRenderer {

    public MotorwayGroupRenderer() {

    }

    @Override
    public boolean paintHighwayGroup(Graphics2D g2d, HighwayGroup highwayGroup) {

        paintMotorwayHighway(g2d, highwayGroup);

        return true;
    }

    public boolean paintMotorwayHighway(Graphics2D g2d,
            HighwayGroup highwayGroup) {

        //
        // GeneralPath groupPath = new GeneralPath();
        //
        //
        //
        // for (int i = 0; i < highwayGroup.countHighway(); i++) {
        //
        // Highway motorway = highwayGroup.getHighway(i);
        //
        //
        // GeneralPath path = new GeneralPath();
        // int count = 0;
        // for(Node n : motorway.getPrimitive().getNodes()) {
        //
        // Point2D p2d = n.getProjection();
        // if(count ==0)
        // path.moveTo(p2d.getX(), p2d.getY());
        // else{
        // path.lineTo(p2d.getX(), p2d.getY());
        // }
        // count++;
        // }
        //
        // groupPath.append(path, false);
        //
        // }
        //
        //
        // BasicStroke bs1 = new
        // BasicStroke(16f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        // Shape s1 = bs1.createStrokedShape(groupPath);
        // Area area1 = new Area(s1);
        //
        // GeneralPath primaryHighway = new GeneralPath();
        //
        //
        // primaryHighway.append(area1.getPathIterator(null), false);

        g2d.setColor(new Color(128, 155, 192));
        // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
        // 0.7f));
        // g2d.fill(primaryHighway);
        g2d.fill(highwayGroup.getSkelet());

        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));

        return true;
    }

}
