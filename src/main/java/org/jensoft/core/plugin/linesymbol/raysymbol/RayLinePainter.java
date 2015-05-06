/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.linesymbol.raysymbol;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jensoft.core.palette.color.TangoPalette;
import org.jensoft.core.plugin.linesymbol.LineSymbolPlugin.LineNature;
import org.jensoft.core.plugin.linesymbol.core.LineSymbolComponent;
import org.jensoft.core.plugin.linesymbol.painter.AbstractLinePainter;
import org.jensoft.core.projection.Projection;

/**
 * <code>RayLinePainter<code> takes the responsibility to paint the ray symbols along the line
 */
public class RayLinePainter extends AbstractLinePainter {

    private Color drawColor;
    private Color fillColor;

    /**
     * create RayLinePainter with the specified fill and draw color.
     * 
     * @param drawColor
     * @param fillColor
     */
    public RayLinePainter(Color drawColor, Color fillColor) {
        super();
        this.drawColor = drawColor;
        this.fillColor = fillColor;
    }

    /**
     * @return the drawColor
     */
    public Color getDrawColor() {
        return drawColor;
    }

    /**
     * @param drawColor
     *            the drawColor to set
     */
    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }

    /**
     * @return the fillColor
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * @param fillColor
     *            the fillColor to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

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

    /**
     * paint all entries registered on the specified ray x line symbol
     * 
     * @param g2d
     * @param lineSymbolComponent
     */
    private void paintXLineSymbol(Graphics2D g2d,
            LineSymbolComponent lineSymbolComponent) {
        Projection w2d = lineSymbolComponent.getHost().getProjection();
        RayLineSymbol rls = (RayLineSymbol) lineSymbolComponent;
        for (RayEntry entry : rls.getEntries()) {
            double rayStart = entry.getRayStart();
            double rayEnd = entry.getRayEnd();

            Point2D deviceStartX = w2d.userToPixel(new Point2D.Double(rayStart,
                                                                      0));
            Point2D deviceEndX = w2d.userToPixel(new Point2D.Double(rayEnd, 0));
            double y = rls.getGeometry().getBaseLine().getY1();

            Rectangle2D rayBar = new Rectangle2D.Double(deviceStartX.getX(),
                                                        y - 2, deviceEndX.getX() - deviceStartX.getX(), 4);

            g2d.setColor(fillColor);
            g2d.fill(rayBar);

            g2d.setColor(drawColor);
            g2d.draw(rayBar);

        }

        Line2D baseLine = lineSymbolComponent.getGeometry().getBaseLine();
        double ystart = baseLine.getY1();
        g2d.setColor(TangoPalette.SKYBLUE2);
        g2d.drawString(rls.getSymbol(), 20, (int) (ystart - 4));

    }

    /**
     * paint all entries registered on the specified ray y line symbol
     * 
     * @param g2d
     * @param lineSymbolComponent
     */
    private void paintYLineSymbol(Graphics2D g2d,
            LineSymbolComponent lineSymbolComponent) {
        RayLineSymbol ils = (RayLineSymbol) lineSymbolComponent;
        for (RayEntry entry : ils.getEntries()) {
            double value = entry.getRayStart();

            Projection w2d = lineSymbolComponent.getHost().getProjection();
            Point2D deviceStartY = w2d
                    .userToPixel(new Point2D.Double(0, value));
            double x = lineSymbolComponent.getLocation();

        }
    }

}
