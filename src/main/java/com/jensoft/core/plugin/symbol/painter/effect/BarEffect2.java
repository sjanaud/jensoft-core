/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol.painter.effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.plugin.symbol.BarSymbol;
import com.jensoft.core.plugin.symbol.BarSymbol.MorpheStyle;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import com.jensoft.core.window.Window2D;

public class BarEffect2 extends AbstractBarEffect {

    public BarEffect2() {

    }

    @Override
    public void paintBarEffect(Graphics2D g2d, BarSymbol bar) {
        if (bar.getNature() == SymbolNature.Vertical) {
            paintEffectVBar_ef2(g2d, bar);
        }
        if (bar.getNature() == SymbolNature.Horizontal) {
            paintEffectHBar_ef2(g2d, bar);
        }
    }

    private void paintEffectVBar_ef2(Graphics2D g2d, BarSymbol bar) {

        // Graphics2D partGraphics =bar.getPart().getGraphics2D();

        Window2D w2d = bar.getHost().getWindow2D();

        Point2D p2dUser = null;
        if (bar.isAscent()) {
            p2dUser = new Point2D.Double(0, bar.getBase() + bar.getValue());
        }
        if (bar.isDescent()) {
            p2dUser = new Point2D.Double(0, bar.getBase() - bar.getValue());
        }
        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        Point2D p2dUserBase = new Point2D.Double(0, bar.getBase());
        Point2D p2ddeviceBase = w2d.userToPixel(p2dUserBase);

        double x = bar.getLocationX();
        double y = (int) p2ddevice.getY();
        if (bar.isDescent()) {
            y = (int) p2ddeviceBase.getY();
        }
        double width = bar.getThickness();
        double height = Math.abs(p2ddeviceBase.getY() - p2ddevice.getY());

        Shape shapeEffect = null;

        int inset = 2;
        x = x + inset;
        y = y + inset;
        width = width - 2 * inset;
        height = height - 2 * inset;
        if (bar.getMorpheStyle() == MorpheStyle.Round) {
            double round = bar.getRound();
            if (bar.isAscent()) {
                GeneralPath barPath = new GeneralPath();
                barPath.moveTo(x, y + round);
                barPath.lineTo(x, y + height);
                barPath.lineTo(x + width, y + height);
                barPath.lineTo(x + width, y + round);
                barPath.quadTo(x + width, y, x + width - round, y);
                barPath.lineTo(x + round, y);
                barPath.quadTo(x, y, x, y + round);
                barPath.closePath();
                shapeEffect = barPath;
            }
            else if (bar.isDescent()) {
                GeneralPath barPath = new GeneralPath();
                barPath.moveTo(x, y);
                barPath.lineTo(x, y + height - round);
                barPath.quadTo(x, y + height, x + round, y + height);
                barPath.lineTo(x + width - round, y + height);
                barPath.quadTo(x + width, y + height, x + width, y + height
                        - round);
                barPath.lineTo(x + width, y);
                barPath.closePath();
                shapeEffect = barPath;
            }
        }
        else if (bar.getMorpheStyle() == MorpheStyle.Rectangle) {
            Rectangle2D barRec = new Rectangle2D.Double(x, y, width, height);
            shapeEffect = barRec;
        }

        Rectangle2D boun2D2 = shapeEffect.getBounds2D();

        Point2D start = null;
        Point2D end = null;
        if (bar.isAscent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY()
                    + boun2D2.getHeight());
        }
        else if (bar.isDescent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY()
                    + boun2D2.getHeight());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
        }

        float[] dist = { 0.0f, 0.33f, 0.66f, 1.0f };
        Color[] colors = { new Color(255, 255, 255, 180),
                new Color(255, 255, 255, 0), new Color(40, 40, 40, 0),
                new Color(40, 40, 40, 100) };
        LinearGradientPaint p2 = new LinearGradientPaint(start, end, dist,
                                                         colors);

        g2d.setPaint(p2);
        // partGraphics.setPaint(p2);
        g2d.fill(shapeEffect);
        // partGraphics.fill(shapeEffect);

    }

    private void paintEffectHBar_ef2(Graphics2D g2d, BarSymbol bar) {

        Window2D w2d = bar.getHost().getWindow2D();

        Point2D p2dUser = null;
        if (bar.isAscent()) {
            p2dUser = new Point2D.Double(bar.getBase() + bar.getValue(), 0);
        }
        if (bar.isDescent()) {
            p2dUser = new Point2D.Double(bar.getBase() - bar.getValue(), 0);
        }

        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        Point2D p2dUserBase = new Point2D.Double(bar.getBase(), 0);
        Point2D p2ddeviceBase = w2d.userToPixel(p2dUserBase);

        double y = bar.getLocationY();
        double x = (int) p2ddeviceBase.getX();
        if (bar.isAscent()) {
            x = (int) p2ddeviceBase.getX();
        }
        if (bar.isDescent()) {
            x = (int) p2ddevice.getX();
        }

        double height = bar.getThickness();
        double width = Math.abs(p2ddevice.getX() - p2ddeviceBase.getX());

        Shape shapeEffect = null;

        int inset = 2;
        x = x + inset;
        y = y + inset;
        width = width - 2 * inset;
        height = height - 2 * inset;
        if (bar.getMorpheStyle() == MorpheStyle.Round) {
            double round = bar.getRound();
            GeneralPath barPath = new GeneralPath();
            if (bar.isAscent()) {
                barPath.moveTo(x, y);
                barPath.lineTo(x + width - round, y);
                barPath.quadTo(x + width, y, x + width, y + round);
                barPath.lineTo(x + width, y + height - round);
                barPath.quadTo(x + width, y + height, x + width - round, y
                        + height);
                barPath.lineTo(x, y + height);
                barPath.closePath();
            }
            else if (bar.isDescent()) {

                barPath.moveTo(x + round, y);
                barPath.lineTo(x + width, y);
                barPath.lineTo(x + width, y + height);
                barPath.lineTo(x + round, y + height);
                barPath.quadTo(x, y + height, x, y + height - round);
                barPath.lineTo(x, y + round);
                barPath.quadTo(x, y, x + round, y);
                barPath.closePath();
            }
            shapeEffect = barPath;
        }
        else {

            Rectangle2D barRec = new Rectangle2D.Double(x, y, width, height);
            shapeEffect = barRec;

        }

        Rectangle2D boun2D2 = shapeEffect.getBounds2D();

        Point2D start = null;
        Point2D end = null;
        if (bar.isAscent()) {
            start = new Point2D.Double(boun2D2.getX() + boun2D2.getWidth(),
                                       boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
        }
        else if (bar.isDescent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX() + boun2D2.getWidth(),
                                     boun2D2.getY());
        }

        float[] dist = { 0.0f, 0.33f, 0.66f, 1.0f };
        Color[] colors = { new Color(255, 255, 255, 180),
                new Color(255, 255, 255, 0), new Color(40, 40, 40, 0),
                new Color(40, 40, 40, 100) };
        LinearGradientPaint p2 = new LinearGradientPaint(start, end, dist,
                                                         colors);

        g2d.setPaint(p2);
        g2d.fill(shapeEffect);

    }
}
