/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol.painter.point;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import org.jensoft.core.palette.color.TangoPalette;
import org.jensoft.core.plugin.symbol.PointSymbol;
import org.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;

/**
 * create a point symbol default painter this painter paints point symbol with
 * Line shape that represent the point<br>
 * point start is the symbol location, the point end is location + thickness
 * 
 * @author Sebastien Janaud
 */
public class PointSymbolDebugGeometryPainter extends AbstractPointSymbolPainter {

    private Color pointColor = TangoPalette.SCARLETRED1;

    /**
     * create default point symbol painter
     */
    public PointSymbolDebugGeometryPainter() {
    }

    /**
     * create point symbol painter
     * 
     * @param pointColor
     *            the color to set
     */
    public PointSymbolDebugGeometryPainter(Color pointColor) {
        this.pointColor = pointColor;
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.painter.point.AbstractPointSymbolPainter#paintPointSymbol(java.awt.Graphics2D, com.jensoft.core.plugin.symbol.PointSymbol)
     */
    @Override
    protected void paintPointSymbol(Graphics2D g2d, PointSymbol point) {

        if (point.isFiller()) {
            return;
        }

        g2d.setColor(pointColor);

        Ellipse2D loc = new Ellipse2D.Double(point.getDevicePoint().getX() - 2,
                                             point.getDevicePoint().getY() - 2, 4, 4);
        g2d.draw(loc);
        if (point.getNature() == SymbolNature.Vertical) {
            Line2D e = new Line2D.Double(point.getDevicePoint().getX(), point
                    .getDevicePoint().getY(), point.getDevicePoint().getX()
                    + point.getThickness(), point.getDevicePoint().getY());
            g2d.draw(e);
        }
        if (point.getNature() == SymbolNature.Horizontal) {
            Line2D e = new Line2D.Double(point.getDevicePoint().getX(), point
                    .getDevicePoint().getY(), point.getDevicePoint().getX(),
                                         point.getDevicePoint().getY() - point.getThickness());
            g2d.draw(e);
        }
    }

}
