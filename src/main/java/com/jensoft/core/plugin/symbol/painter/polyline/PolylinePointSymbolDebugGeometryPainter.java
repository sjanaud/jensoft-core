/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.polyline;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.List;

import com.jensoft.core.palette.color.TangoPalette;
import com.jensoft.core.plugin.symbol.PointSymbol;
import com.jensoft.core.plugin.symbol.PolylinePointSymbol;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;

/**
 * @author Sebastien Janaud
 */
public class PolylinePointSymbolDebugGeometryPainter extends AbstractPolylinePointSymbolPainter {

    private Color pointColor = TangoPalette.SCARLETRED1;

    /**
     * create default point symbol painter
     */
    public PolylinePointSymbolDebugGeometryPainter() {
    }

    /**
     * create point symbol painter
     * 
     * @param pointColor
     *            the color to set
     */
    public PolylinePointSymbolDebugGeometryPainter(Color pointColor) {
        this.pointColor = pointColor;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.painter.polyline.AbstractPolylinePointSymbolPainter#paintPolylinePointSymbol(java.awt.Graphics2D, com.jensoft.core.plugin.symbol.PolylinePointSymbol)
     */
    @Override
    protected void paintPolylinePointSymbol(Graphics2D g2d, PolylinePointSymbol polyline) {

        g2d.setColor(pointColor);
        GeneralPath path = new GeneralPath();
        List<PointSymbol> points = polyline.getSymbolComponents();

        for (int i = 0; i < points.size(); i++) {
            PointSymbol point = points.get(i);
            if (point.isFiller()) {
                continue;
            }
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

            if (i == 0) {
                path.moveTo(point.getDevicePoint().getX(), point.getDevicePoint().getY());
            }
            else {
                path.lineTo(point.getDevicePoint().getX(), point.getDevicePoint().getY());
            }
        }

        if (path != null) {
            g2d.draw(path);
        }

    }

}
