/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.point.manager;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.point.Point;

/**
 *
 */
public class FlowPointManager extends AbstractPointManager {

    private Point2D pointStart;
    private double widthMax;
    private double flowWeigh;

    public FlowPointManager(int type, Point2D pointRef, double pointMax,
            double flowWidth) {
        super.setType(type);
        pointStart = pointRef;
        widthMax = pointMax;
        flowWeigh = flowWidth;

    }

    private List<Point> devicePoints = new ArrayList<Point>();

    @Override
    public List<Point> getPoints() {
        devicePoints.clear();

        double interval = widthMax - pointStart.getX();
        int gridNumber = (int) (interval / flowWeigh);

        if (gridNumber > 0) {
            for (int i = 0; i <= gridNumber; i++) {

                double g = pointStart.getX() + i * flowWeigh;
                if (getType() == POINT_X) {
                    g = pointStart.getX() + i * flowWeigh;
                    Point2D p2dUser = new Point2D.Double(g, pointStart.getY());
                    Point2D p2ddevice = getProjection().userToPixel(p2dUser);

                    Point p = new Point(POINT_X);
                    p.value = p2ddevice;
                    p.setColor(getPointColor());

                    devicePoints.add(p);
                }
                else if (getType() == POINT_Y) {
                    g = pointStart.getY() + i * flowWeigh;
                    Point2D p2dUser = new Point2D.Double(pointStart.getX(), g);
                    Point2D p2ddevice = getProjection().userToPixel(p2dUser);

                    Point p = new Point(POINT_Y);
                    p.value = p2ddevice;
                    p.setColor(getPointColor());

                    devicePoints.add(p);
                }

            }
        }
        return devicePoints;
    }

}
