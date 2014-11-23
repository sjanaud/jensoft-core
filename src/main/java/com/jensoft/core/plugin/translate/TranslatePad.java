/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.translate;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.plugin.translate.TranslatePlugin.ShiftDirection;
import com.jensoft.core.view.View;
import com.jensoft.core.widget.pad.AbstractBackwardForwardPadWidget;

/**
 * <code>TranslatePad</code>
 * 
 * @author sebastien janaud
 */
public class TranslatePad extends AbstractBackwardForwardPadWidget<TranslatePlugin> {

    /** the widget id */
    public final static String translatePadID = "@widget/translate/pad";

    /** the widget radius */
    private final static int widgetRadius = 32;

    /** default base draw color */
    private Color baseDrawColor;

    /** default control draw color */
    private Color controlDrawColor;

    /** button draw color */
    private Color buttonDrawColor;

    /** button rollover draw color */
    private Color buttonRolloverDrawColor = RosePalette.LEMONPEEL;

    /** buttons strokes */
    private Stroke buttonStroke = new BasicStroke(1.6f, BasicStroke.CAP_SQUARE,
                                                  BasicStroke.JOIN_MITER);

    /**
     * create translate pad widget with specified parameters
     * 
     * @param id
     *            translate pad id
     * @param squarePad
     *            objectif pad square size
     * @param xIndex
     *            x folder index
     * @param yIndex
     *            y folder index
     */
    public TranslatePad(String id, double squarePad, int xIndex, int yIndex) {
        super(translatePadID, squarePad, xIndex, yIndex);
        init();
    }

    /**
     * create default pad widget
     */
    public TranslatePad() {
        super(translatePadID, 2 * widgetRadius, 60, 100);
        init();
    }

    /**
     * initialize pad with default colors and strokes
     */
    private void init() {
        setFillBaseColor(Color.BLACK);
        setFillControlColor(ColorPalette.BLACK);
        setDrawBaseColor(baseDrawColor);
        setDrawControlColor(controlDrawColor);
        setDrawButtonColor(buttonDrawColor);
        setDrawButtonStroke(buttonStroke);
        setDrawButtonRolloverColor(buttonRolloverDrawColor);
        getPadGeometry().setInset(6);
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadWidget#paintWidget(com.jensoft.core.view.View, java.awt.Graphics2D)
     */
    @Override
    protected void paintWidget(View v2d, Graphics2D g2d) {
        super.paintWidget(v2d, g2d);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof TranslatePlugin) {
            return true;
        }
        return false;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadWidget#onNorthButtonPress()
     */
    @Override
    public void onNorthButtonPress() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().shift(ShiftDirection.North);
        super.onNorthButtonPress();
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadWidget#onNorthButtonReleased()
     */
    @Override
    public void onNorthButtonReleased() {
        super.onNorthButtonReleased();
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadWidget#onSouthButtonPress()
     */
    @Override
    public void onSouthButtonPress() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().shift(ShiftDirection.South);
        super.onSouthButtonPress();
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadWidget#onSouthButtonReleased()
     */
    @Override
    public void onSouthButtonReleased() {
        super.onSouthButtonReleased();
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadWidget#onWestButtonPress()
     */
    @Override
    public void onWestButtonPress() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().shift(ShiftDirection.West);
        super.onWestButtonPress();
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadWidget#onWestButtonReleased()
     */
    @Override
    public void onWestButtonReleased() {
        super.onWestButtonReleased();
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadWidget#onEastButtonPress()
     */
    @Override
    public void onEastButtonPress() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().shift(ShiftDirection.East);
        super.onEastButtonPress();
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadWidget#onEastButtonReleased()
     */
    @Override
    public void onEastButtonReleased() {
        super.onEastButtonReleased();
    }

}
