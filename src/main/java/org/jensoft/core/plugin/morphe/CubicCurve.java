/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.morphe;

import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;

import org.jensoft.core.projection.Projection;

public class CubicCurve extends Primitive {

    private double x1;
    private double y1;

    private double cx1;
    private double cy1;

    private double cx2;
    private double cy2;

    private double x2;
    private double y2;

    public CubicCurve(double x1, double y1, double cx1, double cy1, double cx2,
            double cy2, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.cx1 = cx1;
        this.cy1 = cy1;
        this.cx2 = cx2;
        this.cy2 = cy2;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (getNature() == PrimitiveNature.DEVICE) {
            CubicCurve2D cubic = new CubicCurve2D.Double(x1, y1, cx1, cy1, cx2,
                                                         cy2, x2, y2);
            g2d.setColor(getHost().getThemeColor());
            g2d.draw(cubic);
        }
        else if (getNature() == PrimitiveNature.USER) {
            Projection w2d = getHost().getProjection();

            Point2D ux1 = w2d.userToPixel(new Point2D.Double(x1, 0));
            Point2D uy1 = w2d.userToPixel(new Point2D.Double(0, y1));
            Point2D ucx1 = w2d.userToPixel(new Point2D.Double(cx1, 0));
            Point2D ucy1 = w2d.userToPixel(new Point2D.Double(0, cy1));
            Point2D ucx2 = w2d.userToPixel(new Point2D.Double(cx2, 0));
            Point2D ucy2 = w2d.userToPixel(new Point2D.Double(0, cy2));
            Point2D ux2 = w2d.userToPixel(new Point2D.Double(x2, 0));
            Point2D uy2 = w2d.userToPixel(new Point2D.Double(0, y2));

            CubicCurve2D cubic = new CubicCurve2D.Double(ux1.getX(),
                                                         uy1.getY(), ucx1.getX(), ucy1.getY(), ucx2.getX(),
                                                         ucy2.getY(), ux2.getX(), uy2.getY());
            g2d.setColor(getHost().getThemeColor());
            g2d.draw(cubic);

        }

    }

}
