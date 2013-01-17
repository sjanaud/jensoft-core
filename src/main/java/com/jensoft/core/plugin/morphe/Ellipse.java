/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.morphe;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.window.Window2D;

public class Ellipse extends Primitive {

    private double centerX;
    private double centerY;
    private double width;
    private double height;

    public Ellipse(double centerX, double centerY, double width, double height) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
    }

    public Ellipse(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D g2d) {

        if (getNature() == PrimitiveNature.DEVICE) {
            Ellipse2D ellipse2DDevice = new Ellipse2D.Double(centerX - width
                    / 2, centerY - height / 2, width, height);
            g2d.setColor(getHost().getThemeColor());
            g2d.draw(ellipse2DDevice);
        }
        else if (getNature() == PrimitiveNature.USER) {
            Window2D w2d = getHost().getWindow2D();

            Point2D centerUser = w2d.userToPixel(new Point2D.Double(centerX,
                                                                    centerY));

            double left = centerX - width / 2;
            Point2D pLeft = w2d.userToPixel(new Point2D.Double(left, 0));

            double right = centerX + width / 2;
            Point2D pRight = w2d.userToPixel(new Point2D.Double(right, 0));

            double userWidth = pRight.getX() - pLeft.getX();

            double top = centerY - height / 2;
            Point2D pTop = w2d.userToPixel(new Point2D.Double(0, top));

            double bottom = centerY + height / 2;
            Point2D pBottom = w2d.userToPixel(new Point2D.Double(0, bottom));

            double userHeight = pTop.getY() - pBottom.getY();

            Ellipse2D ellipse2DUser = new Ellipse2D.Double(centerUser.getX()
                    - userWidth / 2, centerUser.getY() - userHeight / 2,
                                                           userWidth, userHeight);

            g2d.setColor(getHost().getThemeColor());
            g2d.draw(ellipse2DUser);
        }

    }

}
