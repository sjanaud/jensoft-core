/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.linesymbol;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.plugin.linesymbol.LineSymbolPlugin.LineNature;
import com.jensoft.core.view.View2D;
import com.jensoft.core.widget.Widget;
import com.jensoft.core.widget.WidgetFolder;

/**
 * LineSymbolWidget allow to scroll line symbol
 */
public class LineSymbolWidget extends Widget {

    /** the widget id */
    public final static String ID = "@widget/objectif";

    /** the widget radius */
    private final static int widgetRadius = 32;

    /** the move control widget */
    private LineSymbolWidgetGeometry objectifControl;

    public LineSymbolWidget() {
        super(ID, 2 * widgetRadius, 2 * widgetRadius, 100, 100);
        objectifControl = new LineSymbolWidgetGeometry(0, 0, widgetRadius);
    }

    @Override
    public final boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof LineSymbolPlugin) {
            return true;
        }
        return false;
    }

    /**
     * return control geometry
     * 
     * @return
     */
    public LineSymbolWidgetGeometry getControl() {
        return objectifControl;
    }

    /**
     * paint East West Widget for Line Y which can be scrolled along East West
     * symbol dimension
     * 
     * @param g2d
     *            the graphics context to paint
     */
    private void paintEWLineSymbolWidget(Graphics2D g2d) {

        if (!getHost().isLockSelected()) {
            return;
        }
        setWidth(64);
        setHeight(32);
        WidgetFolder currentFolder = getWidgetFolder();

        int tcx = (int) (currentFolder.getX() + currentFolder.getWidth() / 2);
        int tcy = (int) (currentFolder.getY() + currentFolder.getHeight() / 2);

        objectifControl.setCenterX(tcx);
        objectifControl.setCenterY(tcy);
        objectifControl.buildEastWest();

        g2d.setColor(getThemeColor().darker());

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    0.3f));

        g2d.setColor(getThemeColor().darker());

        g2d.setStroke(new BasicStroke(0.8f));
        g2d.fill(objectifControl.getControlShape());
        g2d.setColor(Color.WHITE);
        g2d.draw(objectifControl.getControlShape());

        if (objectifControl.isLockEastSensible()) {
            g2d.setColor(getThemeColor().brighter());
            g2d.setComposite(AlphaComposite.getInstance(
                                                        AlphaComposite.SRC_OVER, 1f));
        }
        else {
            g2d.setColor(getThemeColor().darker());
            g2d.setComposite(AlphaComposite.getInstance(
                                                        AlphaComposite.SRC_OVER, 0.6f));
        }
        g2d.fill(objectifControl.getEastSensibleShape());
        g2d.setColor(Color.WHITE);
        g2d.draw(objectifControl.getEastSensibleShape());

        if (objectifControl.isLockWestSensible()) {
            g2d.setColor(getThemeColor().brighter());
            g2d.setComposite(AlphaComposite.getInstance(
                                                        AlphaComposite.SRC_OVER, 1f));
        }
        else {
            g2d.setColor(getThemeColor().darker());
            g2d.setComposite(AlphaComposite.getInstance(
                                                        AlphaComposite.SRC_OVER, 0.6f));
        }
        g2d.fill(objectifControl.getWestSensibleShape());
        g2d.setColor(Color.WHITE);
        g2d.draw(objectifControl.getWestSensibleShape());
    }

    /**
     * paint North South Widget for Line X (Symbol on Y, Metrics along X) which
     * can be scrolled along North South symbol dimension
     * 
     * @param g2d
     *            the graphics context to paint
     */
    private void paintNSLineSymbolWidget(Graphics2D g2d) {

        if (!getHost().isLockSelected()) {
            return;
        }

        setWidth(32);
        setHeight(64);
        WidgetFolder currentFolder = getWidgetFolder();

        int tcx = (int) (currentFolder.getX() + currentFolder.getWidth() / 2);
        int tcy = (int) (currentFolder.getY() + currentFolder.getHeight() / 2);

        objectifControl.setCenterX(tcx);
        objectifControl.setCenterY(tcy);
        objectifControl.buildNorthSouth();

        g2d.setColor(getThemeColor().darker());

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    0.3f));

        g2d.setColor(getThemeColor().darker());

        g2d.setStroke(new BasicStroke(0.8f));
        g2d.fill(objectifControl.getControlShape());
        g2d.setColor(Color.WHITE);
        g2d.draw(objectifControl.getControlShape());

        if (objectifControl.isLockNorthSensible()) {
            g2d.setColor(getThemeColor().brighter());
            g2d.setComposite(AlphaComposite.getInstance(
                                                        AlphaComposite.SRC_OVER, 1f));
        }
        else {
            g2d.setColor(getThemeColor().darker());
            g2d.setComposite(AlphaComposite.getInstance(
                                                        AlphaComposite.SRC_OVER, 0.6f));
        }
        g2d.fill(objectifControl.getNorthSensibleShape());
        g2d.setColor(Color.WHITE);
        g2d.draw(objectifControl.getNorthSensibleShape());

        if (objectifControl.isLockSouthSensible()) {
            g2d.setColor(getThemeColor().brighter());
            g2d.setComposite(AlphaComposite.getInstance(
                                                        AlphaComposite.SRC_OVER, 1f));
        }
        else {
            g2d.setColor(getThemeColor().darker());
            g2d.setComposite(AlphaComposite.getInstance(
                                                        AlphaComposite.SRC_OVER, 0.6f));
        }
        g2d.fill(objectifControl.getSouthSensibleShape());
        g2d.setColor(Color.WHITE);
        g2d.draw(objectifControl.getSouthSensibleShape());

    }

    @Override
    protected void paintWidget(View2D v2d, Graphics2D g2d) {

        if (((LineSymbolPlugin) getHost()).isLockSelected()) {
            if (((LineSymbolPlugin) getHost()).getLineNature() == LineNature.LineX) {
                paintNSLineSymbolWidget(g2d);
            }
        }
        if (((LineSymbolPlugin) getHost()).getLineNature() == LineNature.LineY) {
            paintEWLineSymbolWidget(g2d);
        }

    }

}
