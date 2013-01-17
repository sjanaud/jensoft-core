/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.highway;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class PrimaryGroupRenderer implements HighwayGroupRenderer {

    public PrimaryGroupRenderer() {

    }

    @Override
    public boolean paintHighwayGroup(Graphics2D g2d, HighwayGroup highwayGroup) {

        paintPrimaryHighway(g2d, highwayGroup);

        return true;
    }

    public boolean paintPrimaryHighway(Graphics2D g2d, HighwayGroup highwayGroup) {

        // GeneralPath groupPath = new GeneralPath();
        //
        //
        //
        // for (int i = 0; i < highwayGroup.countHighway(); i++) {
        //
        // Highway residentialHighway = highwayGroup.getHighway(i);
        //
        //
        // GeneralPath path = new GeneralPath();
        // Point2D ptInitial =
        // residentialHighway.getHighwayProjectionPoints().get(0);
        // path.moveTo(ptInitial.getX(), ptInitial.getY());
        // for(Point2D pt : residentialHighway.getHighwayProjectionPoints()){
        //
        // path.lineTo(pt.getX(), pt.getY());
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
        // g2d.setColor(new Color(236,152,154));
        g2d.setColor(new Color(244, 69, 49));
        //g2d.setColor(Color.RED);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    0.7f));
        g2d.fill(highwayGroup.getSkelet());
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));

        return true;
    }

}
