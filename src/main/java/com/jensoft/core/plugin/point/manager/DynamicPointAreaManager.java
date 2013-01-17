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
public class DynamicPointAreaManager extends AbstractPointManager {

    private double xRef;
    private double yRef;
    private double weighX;
    private double weighY;

    private List<Point> points = new ArrayList<Point>();

    public DynamicPointAreaManager(double xRef, double yRef, double weighX,
            double weighY) {
        super.setType(PointLayoutManager.AREA);
        this.xRef = xRef;
        this.yRef = yRef;
        this.weighX = weighX;
        this.weighY = weighY;
    }

    @Override
    public List<Point> getPoints() {

        points.clear();

        // TODO : make another implementation

        double currentX = 0;
        double currentY1 = 0;
        double currentY2 = 0;

        double deltaX1 = getWindow2D().getMaxX() - xRef;
        double deltaY11 = getWindow2D().getMaxY() - yRef;
        double deltaY12 = Math.abs(getWindow2D().getMinY() - yRef);

        int numberIterX1 = (int) (deltaX1 / weighX);
        int numberIterY11 = (int) (deltaY11 / weighY);
        int numberIterY12 = (int) (deltaY12 / weighY);

        double deltaX2 = Math.abs(getWindow2D().getMinX() - xRef);
        double numberIterX2 = (int) (deltaX2 / weighX);

        for (int x1 = 0; x1 <= numberIterX1; x1++) {

            currentX = xRef + x1 * weighX;
            for (int y1 = 0; y1 < numberIterY11; y1++) {
                currentY1 = yRef + y1 * weighY;

                Point2D p2dUser = new Point2D.Double(currentX, currentY1);
                Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                Point p = new Point(AREA);
                p.value = p2dDevice;
                p.setColor(getPointColor());

                points.add(p);

            }
            for (int y2 = 0; y2 < numberIterY12; y2++) {
                // System.out.println("add point");
                currentY2 = yRef - y2 * weighY;

                Point2D p2dUser = new Point2D.Double(currentX, currentY2);
                Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                Point p = new Point(AREA);
                p.value = p2dDevice;
                p.setColor(getPointColor());

                points.add(p);
            }

        }

        for (int x2 = 0; x2 <= numberIterX2; x2++) {

            currentX = xRef - x2 * weighX;
            for (int y1 = 0; y1 < numberIterY11; y1++) {
                currentY1 = yRef + y1 * weighY;

                Point2D p2dUser = new Point2D.Double(currentX, currentY1);
                Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                Point p = new Point(AREA);
                p.value = p2dDevice;
                p.setColor(getPointColor());

                points.add(p);

            }
            for (int y2 = 0; y2 < numberIterY12; y2++) {
                currentY2 = yRef - y2 * weighY;

                Point2D p2dUser = new Point2D.Double(currentX, currentY2);
                Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                Point p = new Point(AREA);
                p.value = p2dDevice;
                p.setColor(getPointColor());

                points.add(p);
            }

        }

        return points;
    }

}
