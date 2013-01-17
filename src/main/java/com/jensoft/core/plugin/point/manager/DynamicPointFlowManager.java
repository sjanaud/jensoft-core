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
 * @version 1.0
 * @author S�bastien Janaud
 */
public class DynamicPointFlowManager extends AbstractPointManager {

    private double xRef;
    private double yRef;
    private double weigh;

    private List<Point> points = new ArrayList<Point>();

    public DynamicPointFlowManager(int type, double xRef, double yRef,
            double weigh) {
        super.setType(type);
        this.xRef = xRef;
        this.yRef = yRef;
        this.weigh = weigh;

    }

    @Override
    public List<Point> getPoints() {

        points.clear();

        if (getType() == POINT_X) {

            int count1 = 0;
            boolean flag1 = true;
            while (flag1) {
                if (xRef + count1 * weigh < getWindow2D().getMaxX()) {

                    Point2D p2dUser = new Point2D.Double(xRef + count1 * weigh,
                                                         yRef);
                    Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                    Point p = new Point(POINT_X);
                    p.value = p2dDevice;
                    p.setColor(getPointColor());

                    points.add(p);

                }
                else {
                    flag1 = false;
                }
                count1++;
            }

            int count2 = 0;
            boolean flag2 = true;
            while (flag2) {

                if (xRef - count2 * weigh > getWindow2D().getMinX()) {

                    Point2D p2dUser = new Point2D.Double(xRef - count2 * weigh,
                                                         yRef);
                    Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                    Point p = new Point(POINT_X);
                    p.value = p2dDevice;
                    p.setColor(getPointColor());

                    points.add(p);

                }
                else {
                    flag2 = false;
                }

                count2++;

            }
        }
        if (getType() == POINT_Y) {

            int count1 = 0;
            boolean flag1 = true;
            while (flag1) {
                if (yRef + count1 * weigh < getWindow2D().getMaxY()) {

                    Point2D p2dUser = new Point2D.Double(xRef, yRef + count1
                            * weigh);
                    Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                    Point grid = new Point(POINT_Y);
                    grid.value = p2dDevice;
                    grid.setColor(getPointColor());

                    points.add(grid);

                }
                else {
                    flag1 = false;
                }
                count1++;
            }

            int count2 = 0;
            boolean flag2 = true;
            while (flag2) {
                if (yRef - count2 * weigh > getWindow2D().getMinY()) {
                    Point2D p2dUser = new Point2D.Double(xRef, yRef - count2
                            * weigh);
                    Point2D p2dDevice = getWindow2D().userToPixel(p2dUser);

                    Point p = new Point(POINT_Y);
                    p.value = p2dDevice;
                    p.setColor(getPointColor());

                    points.add(p);

                }
                else {
                    flag2 = false;
                }
                count2++;

            }
        }

        return points;
    }

}
