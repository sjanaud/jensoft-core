/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics.painter.marker;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;

public class TriangleMarker extends GlyphMetricMarkerPainter {


    /** divergence ortho */
    private int divergenceOrtho = 10;


    @Override
    public void paintGlyphMetricMarker(Graphics2D g2d, GlyphMetric glyphMetric) {

       

        Point2D pLeft = glyphMetric.getOrthoLeftPoint(divergenceOrtho);
        if (pLeft == null) {
            return;
        }
        
        Point2D pRight = glyphMetric.getOrthoRightPoint(divergenceOrtho);
        if (pRight == null) {
            return;
        }
        
        Point2D pAnchor = glyphMetric.getRadialPoint(10, Side.SideRight);
        if (pAnchor == null) {
            return;
        }
        
        g2d.setColor(Color.RED);
        g2d.drawRect((int)pLeft.getX(),(int)pLeft.getY(),4,4);
        
        g2d.setColor(Color.BLUE);
        g2d.drawRect((int)pRight.getX(),(int)pRight.getY(),4,4);
        
        g2d.setColor(Color.YELLOW);
        g2d.drawRect((int)pAnchor.getX(),(int)pAnchor.getY(),4,4);
        
        GeneralPath path = new GeneralPath();
        path.moveTo(pLeft.getX(), pLeft.getY());
        path.lineTo(pRight.getX(), pRight.getY());
        path.lineTo(pAnchor.getX(),pAnchor.getY());
        path.closePath();
        
        g2d.setColor(Color.RED);
        g2d.fill(path);
    }

}
