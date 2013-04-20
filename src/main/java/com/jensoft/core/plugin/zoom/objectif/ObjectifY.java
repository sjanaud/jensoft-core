/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.objectif;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.plugin.zoom.objectif.ZoomObjectifPlugin.ZoomNature;
import com.jensoft.core.widget.bar.AbstractBarGeometry.BarWidgetOrientation;
import com.jensoft.core.widget.bar.AbstractPlusMinusBarWidget;

/**
 * <code>ObjectifY</code>
 * 
 * @author sebastien janaud
 */
public class ObjectifY extends AbstractPlusMinusBarWidget<ZoomObjectifPlugin> {

    /** the widget id */
    public final static String widgetBarYObjectifID = "@widget/objectif/y";

    /** default bar width */
    private final static double defaultBarWidth = 16;

    /** default bar height */
    private final static double defaultBarHeight = 80;

    /** default x folder index */
    private final static int defaultXFolderIndex = 1000;

    /** default y folder index */
    private final static int defaultYFolderIndex = 1;

    /** default shading fractions */
    private float[] fractions = { 0f, 0.2f, 0.5f, 0.8f, 1f };

    /** default shading colors */
    private Color[] colors = { new Color(0, 0, 0, 20), new Color(0, 0, 0, 150),
            new Color(0, 0, 0, 200), new Color(0, 0, 0, 150),
            new Color(0, 0, 0, 20) };

    /** default outline color */
    private Color outlineColor = RosePalette.LEMONPEEL;

    /** buttons strokes */
    private Stroke buttonStroke = new BasicStroke(2f, BasicStroke.CAP_ROUND,
                                                  BasicStroke.JOIN_BEVEL);

    /** basic stroke */
    private Stroke basicStroke = new BasicStroke(1.2f);

    /** button rollover draw color */
    private Color buttonRolloverDrawColor = RosePalette.LEMONPEEL;

    /** button draw color */
    private Color buttonDrawColor = RosePalette.EMERALD;

   /**
    * create Y Objectif
    * @param width
    * @param height
    */
    public ObjectifY(double width, double height) {
        super(widgetBarYObjectifID, width, height, defaultXFolderIndex,
              defaultYFolderIndex, BarWidgetOrientation.Vertical);
        setShader(fractions, colors);
        setOutlineStroke(basicStroke);
        setOutlineColor(outlineColor);
        setButtonDrawColor(buttonDrawColor);
        setButtonRolloverDrawColor(buttonRolloverDrawColor);
        setOrphanLock(true);
    }

    /**
     * create Y Objectif
     * @param width
     * @param height
     * @param xIndex
     * @param yIndex
     */
    public ObjectifY(double width, double height, int xIndex, int yIndex) {
        super(widgetBarYObjectifID, width, height, xIndex, yIndex,
              BarWidgetOrientation.Vertical);
        setShader(fractions, colors);
        setOutlineStroke(basicStroke);
        setOutlineColor(outlineColor);
        setButtonDrawColor(buttonDrawColor);
        setButtonRolloverDrawColor(buttonRolloverDrawColor);
        setOrphanLock(true);
    }

    /**
     * create default Y Objectif widget
     * 
     * @param id
     */
    public ObjectifY() {
        super(widgetBarYObjectifID, defaultBarWidth, defaultBarHeight,
              defaultXFolderIndex, defaultYFolderIndex,
              BarWidgetOrientation.Vertical);
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
    public final boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof ZoomObjectifPlugin) {
            return true;
        }
        return false;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onButton1Press()
     */
    @Override
    public void onButton1Press() {
        ZoomObjectifPlugin objectif = (ZoomObjectifPlugin) getHost();
        if (!objectif.isLockSelected()) {
            return;
        }
        objectif.startZoomIn(ZoomNature.ZoomY);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onButton2Press()
     */
    @Override
    public void onButton2Press() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().startZoomOut(ZoomNature.ZoomY);
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onButton1Released()
     */
    @Override
    public void onButton1Released() {
        getHost().stopZoomIn();
        getHost().stopZoomOut();
        // call a repaint on button framing rectangle
        super.onButton1Released();

    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onButton2Released()
     */
    @Override
    public void onButton2Released() {
        getHost().stopZoomIn();
        getHost().stopZoomOut();
        // call a repaint on button framing rectangle
        super.onButton2Released();
    }

}
