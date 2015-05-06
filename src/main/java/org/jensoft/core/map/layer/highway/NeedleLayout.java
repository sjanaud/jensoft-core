/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.highway;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class NeedleLayout {

    private GeneralPath path;
    private HighwayPath pathLength;
    private int needlePeriode = 250;
    private Color needleColor = Color.LIGHT_GRAY;

    public NeedleLayout(GeneralPath path, Color needleColor) {
        this.path = path;
        this.needleColor = needleColor;
        pathLength = new HighwayPath(path);
    }

    public void drawNeedle(Graphics2D g2d) {
        float lengthOfPath = pathLength.lengthOfPath();
        float curentLength = 10;
        while (curentLength <= lengthOfPath) {
            curentLength = curentLength + needlePeriode / 2;

            if (curentLength >= lengthOfPath - 50) {
                break;
            }

            float angle = pathLength.angleAtLength(curentLength);

            Point2D p2dA = pathLength.pointAtLength(curentLength);
            Point2D p2dB = pathLength.pointAtLength(curentLength + 5);

            GeneralPath needlePath = new GeneralPath();
            int deltaWidth = 1;
            needlePath.moveTo(-deltaWidth, 0);
            needlePath.lineTo(-deltaWidth, -8);
            needlePath.lineTo(-2 * deltaWidth, -8);
            needlePath.lineTo(0, -12);
            needlePath.lineTo(2 * deltaWidth, -8);
            needlePath.lineTo(deltaWidth, -8);
            needlePath.lineTo(deltaWidth, 0);
            needlePath.closePath();

            AffineTransform afTest = new AffineTransform();
            afTest.translate(50, 50);

            // g2d.setColor(Color.RED);
            // g2d.draw(afTest.createTransformedShape(needlePath));

            AffineTransform af = new AffineTransform();
            af.translate(p2dA.getX(), p2dA.getY());
            af.rotate(angle + Math.PI / 2);

            Shape s = af.createTransformedShape(needlePath);
            Area a = new Area(s);
            g2d.setColor(needleColor);
            g2d.fill(a);

        }
    }

}
