/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol.painter.polyline;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.util.List;

import org.jensoft.core.palette.color.TangoPalette;
import org.jensoft.core.plugin.symbol.PointSymbol;
import org.jensoft.core.plugin.symbol.PolylinePointSymbol;

/**
 * @author Sebastien Janaud
 */
public class PolylinePointSymbolDefaultPainter extends AbstractPolylinePointSymbolPainter {

    private Color polylineColor = TangoPalette.SCARLETRED1;
    private Stroke polylineStroke = new BasicStroke();

    /**
     * create default polyline symbol painter
     */
    public PolylinePointSymbolDefaultPainter() {
    }

    /**
     * create polyline symbol painter
     * 
     * @param polylineColor
     *            the polyline color to set
     */
    public PolylinePointSymbolDefaultPainter(Color polylineColor) {
        this.polylineColor = polylineColor;
    }

    /**
     * @return the polylineColor
     */
    public Color getPolylineColor() {
        return polylineColor;
    }

    /**
     * @param polylineColor
     *            the polylineColor to set
     */
    public void setPolylineColor(Color polylineColor) {
        this.polylineColor = polylineColor;
    }

    /**
     * @return the polylineStroke
     */
    public Stroke getPolylineStroke() {
        return polylineStroke;
    }

    /**
     * @param polylineStroke
     *            the polylineStroke to set
     */
    public void setPolylineStroke(Stroke polylineStroke) {
        this.polylineStroke = polylineStroke;
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.symbol.painter.polyline.AbstractPolylinePointSymbolPainter#paintPolylinePointSymbol(java.awt.Graphics2D, org.jensoft.core.plugin.symbol.PolylinePointSymbol)
     */
    @Override
    protected void paintPolylinePointSymbol(Graphics2D g2d, PolylinePointSymbol polyline) {

        g2d.setColor(polylineColor);
        GeneralPath path = new GeneralPath();
        List<PointSymbol> points = polyline.getSymbolComponents();

        for (int i = 0; i < points.size(); i++) {
            PointSymbol point = points.get(i);
            if (point.isFiller()) {
                continue;
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
