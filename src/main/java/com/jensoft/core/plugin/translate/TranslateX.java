/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.translate;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.plugin.translate.TranslatePlugin.ShiftDirection;
import com.jensoft.core.widget.bar.AbstractBackwardForwardBarWidget;
import com.jensoft.core.widget.bar.AbstractBarGeometry.BarWidgetOrientation;

/**
 * <code>TranslateX</code>
 * 
 * @author sebastien janaud
 */
public class TranslateX extends AbstractBackwardForwardBarWidget<TranslatePlugin> {

    /** the widget id */
    public final static String widgetBarTranslateXID = "@widget/translate/x";

    /** default bar width */
    private final static double defaultBarWidth = 80;

    /** default bar height */
    private final static double defaultBarHeight = 18;

    /** default x folder index */
    private final static int defaultXFolderIndex = 2;

    /** default y folder index */
    private final static int defaultYFolderIndex = 100;

    /** default shading fractions */
    private float[] fractions = { 0f, 0.2f, 0.5f, 0.8f, 1f };

    /** default shading colors */
    private Color[] colors = { new Color(0, 0, 0, 20), new Color(0, 0, 0, 150),
            new Color(0, 0, 0, 200), new Color(0, 0, 0, 150),
            new Color(0, 0, 0, 20) };

    /** default outline color */
    private Color outlineColor = RosePalette.LEMONPEEL;

    /** basic stroke */
    private Stroke basicStroke = new BasicStroke(1.2f);

    /** button rollover draw color */
    private Color buttonRolloverDrawColor = RosePalette.LEMONPEEL;

    /** button draw color */
    private Color buttonDrawColor = RosePalette.EMERALD;

    /**
     * create X Translate
     * 
     * @param id
     * @param width
     * @param height
     * @param xIndex
     * @param yIndex
     * @param barWidgetOrientation
     */
    public TranslateX(double width, double height) {
        super(widgetBarTranslateXID, width, height, defaultXFolderIndex,
              defaultYFolderIndex, BarWidgetOrientation.Horizontal);
        setShader(fractions, colors);
        setOutlineStroke(basicStroke);
        setOutlineColor(outlineColor);
        setButtonDrawColor(buttonDrawColor);
        setButtonRolloverDrawColor(buttonRolloverDrawColor);
        setOrphanLock(true);
    }

    /**
     * create X Translate
     * 
     * @param id
     * @param width
     * @param height
     * @param xIndex
     * @param yIndex
     * @param barWidgetOrientation
     */
    public TranslateX(double width, double height, int xIndex, int yIndex) {
        super(widgetBarTranslateXID, width, height, xIndex, yIndex,
              BarWidgetOrientation.Horizontal);
        setShader(fractions, colors);
        setOutlineStroke(basicStroke);
        setOutlineColor(outlineColor);
        setButtonDrawColor(buttonDrawColor);
        setButtonRolloverDrawColor(buttonRolloverDrawColor);
        setOrphanLock(true);
    }

    /**
     * create default translate x widget
     */
    public TranslateX() {
        super(widgetBarTranslateXID, defaultBarWidth, defaultBarHeight,
              defaultXFolderIndex, defaultYFolderIndex,
              BarWidgetOrientation.Horizontal);
        setShader(fractions, colors);
        setOutlineStroke(basicStroke);
        setOutlineColor(outlineColor);
        setButtonDrawColor(buttonDrawColor);
        setButtonRolloverDrawColor(buttonRolloverDrawColor);
        setOrphanLock(true);
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#isCompatiblePlugin()
     */
    @Override
    public boolean isCompatiblePlugin() {
        try {
            getHost().getClass(); // throws exception if host is not parameterized type
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onButton1Press()
     */
    @Override
    public void onButton1Press() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().shift(ShiftDirection.West);
        super.onButton1Press();
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onButton2Press()
     */
    @Override
    public void onButton2Press() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().shift(ShiftDirection.East);
        super.onButton2Press();
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onButton1Released()
     */
    @Override
    public void onButton1Released() {
        super.onButton1Released();
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onButton2Released()
     */
    @Override
    public void onButton2Released() {
        super.onButton2Released();
    }

}
