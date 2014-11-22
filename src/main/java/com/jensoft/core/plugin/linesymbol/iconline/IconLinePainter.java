/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.linesymbol.iconline;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;

import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.linesymbol.LineSymbolPlugin.LineNature;
import com.jensoft.core.plugin.linesymbol.core.LineSymbolComponent;
import com.jensoft.core.plugin.linesymbol.painter.AbstractLinePainter;
import com.jensoft.core.projection.Projection;

public class IconLinePainter extends AbstractLinePainter {

    @Override
    public void paintLineSymbol(Graphics2D g2d,
            LineSymbolComponent lineSymbolComponent) {

        if (lineSymbolComponent.getLineNature() == LineNature.LineX) {
            paintXLineSymbol(g2d, lineSymbolComponent);
        }
        else if (lineSymbolComponent.getLineNature() == LineNature.LineY) {
            paintYLineSymbol(g2d, lineSymbolComponent);
        }

    }

    private void paintXLineSymbol(Graphics2D g2d,
            LineSymbolComponent lineSymbolComponent) {

        IconLineSymbol ils = (IconLineSymbol) lineSymbolComponent;
        for (LineEntry entry : ils.getEntries()) {
            double value = entry.getValue();

            Projection w2d = lineSymbolComponent.getHost().getProjection();
            Point2D devicePoint = w2d.userToPixel(new Point2D.Double(value, 0));
            double y = ils.getGeometry().getBaseLine().getY1();

            ImageIcon image = null;
            if (entry.getIconSymbol() != null) {
                image = entry.getIconSymbol();
            }
            else {
                image = ils.getIconSymbol();
            }
            g2d.drawImage(image.getImage(),
                          (int) (devicePoint.getX() - image.getIconWidth() / 2),
                          (int) (y - image.getIconHeight() / 2),
                          image.getIconWidth(), image.getIconHeight(), null);

        }

        Line2D baseLine = lineSymbolComponent.getGeometry().getBaseLine();
        double ystart = baseLine.getY1();

        g2d.setColor(TangoPalette.SKYBLUE2);
        if (ils.getSymbol() != null) {
            g2d.drawString(ils.getSymbol(), 20, (int) (ystart - 4));
        }

    }

    private void paintYLineSymbol(Graphics2D g2d,
            LineSymbolComponent lineSymbolComponent) {
        IconLineSymbol ils = (IconLineSymbol) lineSymbolComponent;
        for (LineEntry entry : ils.getEntries()) {
            double value = entry.getValue();

            Projection w2d = lineSymbolComponent.getHost().getProjection();
            Point2D devicePoint = w2d.userToPixel(new Point2D.Double(0, value));
            double x = lineSymbolComponent.getLocation();

            ImageIcon image = null;
            if (entry.getIconSymbol() != null) {
                image = entry.getIconSymbol();
            }
            else {
                image = ils.getIconSymbol();
            }

            g2d.drawImage(image.getImage(),
                          (int) (x - image.getIconWidth() / 2),
                          (int) (devicePoint.getY() - image.getIconHeight() / 2),
                          image.getIconWidth(), image.getIconHeight(), null);

        }
    }

}
