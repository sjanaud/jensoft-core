/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.morphe;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.jensoft.core.projection.Projection;

public class Line extends Primitive {

    private double startX;
    private double startY;
    private double endX;
    private double endY;

    public Line(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    @Override
    public void draw(Graphics2D g2d) {

        if (getNature() == PrimitiveNature.DEVICE) {
            Line2D line2DDevice = new Line2D.Double(startX, endX, startY, endY);
            g2d.setColor(getHost().getThemeColor());
            g2d.draw(line2DDevice);
        }
        else if (getNature() == PrimitiveNature.USER) {
            Projection w2d = getHost().getProjection();

            Point2D start = w2d.userToPixel(new Point2D.Double(startX, startY));
            Point2D end = w2d.userToPixel(new Point2D.Double(endX, endY));

            Line2D line2DUser = new Line2D.Double(start.getX(), start.getY(),
                                                  end.getX(), end.getY());

            g2d.setColor(getHost().getThemeColor());
            g2d.draw(line2DUser);
        }

    }

}
