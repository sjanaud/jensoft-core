/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.fill;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.palette.color.ColorPalette;
import com.jensoft.core.plugin.symbol.BarSymbol;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;

public class BarFill2 extends AbstractBarFill {

    public BarFill2() {

    }

    @Override
    public void paintBarFill(Graphics2D g2d, BarSymbol bar) {
        // ((BarSymbolLayer)bar.getLayer()).solveSymbolComponent(bar);
        if (bar.getNature() == SymbolNature.Vertical) {
            paintBackgroundVBar_bg1(g2d, bar);
        }
        if (bar.getNature() == SymbolNature.Horizontal) {
            paintBackgroundHBar_bg1(g2d, bar);
        }
    }

    private void paintBackgroundVBar_bg1(Graphics2D g2d, BarSymbol bar) {

        // Graphics2D partGraphics =bar.getPart().getGraphics2D();

        Rectangle2D boun2D = bar.getBarShape().getBounds2D();

        // g1
        Point2D start = new Point2D.Double(boun2D.getX(), boun2D.getCenterY());
        Point2D end = new Point2D.Double(boun2D.getX() + boun2D.getWidth(),
                                         boun2D.getCenterY());
        float[] dist = { 0.0f, 0.5f, 1.0f };

        Color cBase = bar.getThemeColor();
        Color brighther1 = ColorPalette.brighter(cBase, 0.8f);

        Color[] colors = { cBase, brighther1, cBase };

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
                                                        colors);

        g2d.setPaint(p);
        // partGraphics.setPaint(p);

        g2d.fill(bar.getBarShape());
        // partGraphics.fill(bar.getBarShape());
    }

    private void paintBackgroundHBar_bg1(Graphics2D g2d, BarSymbol bar) {

        Color cBase = bar.getThemeColor();
        Color brighther1 = ColorPalette.brighter(cBase, 0.8f);

        Rectangle2D boun2D = bar.getBarShape().getBounds2D();

        Point2D start = new Point2D.Double(boun2D.getCenterX(), boun2D.getY());
        Point2D end = new Point2D.Double(boun2D.getCenterX(), boun2D.getY()
                + boun2D.getHeight());
        float[] dist = { 0.0f, 0.5f, 1.0f };
        Color[] colors = { cBase, brighther1, cBase };

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
                                                        colors);

        g2d.setPaint(p);

        g2d.fill(bar.getBarShape());
    }

}
