/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.morphe;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.jensoft.core.projection.Projection;

public class Text extends Primitive {

    private String text;
    private double x;
    private double y;

    public Text(String text, double x, double y) {
        this.text = text;
        this.x = x;
        this.y = y;

        // default user nature
        setNature(PrimitiveNature.USER);
    }

    @Override
    public void draw(Graphics2D g2d) {

        g2d.setColor(getHost().getThemeColor());
        g2d.setFont(new Font("Tahoma", Font.PLAIN, 10));
        if (getNature() == PrimitiveNature.DEVICE) {
            g2d.drawString(text, (int) x, (int) y);
        }
        else if (getNature() == PrimitiveNature.USER) {
            Projection w2d = getHost().getProjection();
            Point2D p2dTextUser = w2d.userToPixel(new Point2D.Double(x, y));
            g2d.drawString(text, (int) p2dTextUser.getX(),
                           (int) p2dTextUser.getY());
        }

    }

}
