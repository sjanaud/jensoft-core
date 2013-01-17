/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.point.painter;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.plugin.point.Point;
import com.jensoft.core.plugin.point.manager.PointLayoutManager;

public class PointPainter extends AbstractPointPainter {

    @Override
    public void doPaintPoint(Graphics2D g2d) {

        PointLayoutManager manager = getLayout();

        if (manager == null) {
            return;
        }

        List<Point> grids = manager.getPoints();

        for (int i = 0; i < grids.size(); i++) {
            Point p = grids.get(i);
            Point2D gd = p.value;

            g2d.setPaint(p.getColor());
            Shape grid2DShape = new Line2D.Double(gd.getX(), gd.getY(),
                                                  gd.getX(), gd.getY());

            g2d.draw(grid2DShape);

        }

        // if(manager.getType() == PointLayoutManager.POINT_X){
        //
        //
        // Vector<Point> points = manager.getPoints();
        // for (int i = 0; i < points.size(); i++) {
        // Point grid =points.get(i);
        // Point2D gd = grid.value;
        // //g2d.setStroke(grid.getStrock());
        // g2d.setPaint(grid.getPaint());
        // Shape grid2DShape = new Line2D.Double(gd.getX(), gd.getY(),
        // gd.getX(), gd.getY());
        //
        // g2d.draw(grid2DShape);
        //
        //
        //
        // }
        //
        //
        // }
        //
        // if(manager.getType() == PointLayoutManager.POINT_Y){
        //
        // Vector<Point> grids = manager.getPoints();
        //
        // for (int i = 0; i < grids.size(); i++) {
        // Point p =grids.get(i);
        // Point2D gd = p.value;
        // g2d.setStroke(p.getStrock());
        // g2d.setPaint(p.getPaint());
        // Shape grid2DShape = new Line2D.Double(gd.getX(), gd.getY(),
        // gd.getX(), gd.getY());
        //
        // g2d.draw(grid2DShape);
        //
        // }
        //
        // }
        //
        // if(manager.getType() == PointLayoutManager.AREA){
        //
        // Vector<Point> grids = manager.getPoints();
        //
        // for (int i = 0; i < grids.size(); i++) {
        // Point p =grids.get(i);
        // Point2D gd = p.value;
        // g2d.setStroke(p.getStrock());
        // g2d.setPaint(p.getPaint());
        // Shape grid2DShape = new Line2D.Double(gd.getX(), gd.getY(),
        // gd.getX(), gd.getY());
        //
        // g2d.draw(grid2DShape);
        //
        // }
        //
        // }

    }

}
