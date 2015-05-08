/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.zoom.lens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.zoom.lens.ZoomLensPlugin.ZoomNature;
import org.jensoft.core.widget.bar.AbstractPlusMinusBarWidget;
import org.jensoft.core.widget.bar.AbstractBarGeometry.BarWidgetOrientation;

/**
 * <code>LensX</code> widget for zoom x dimension
 * 
 * @author sebastien janaud
 */
public class LensX extends AbstractPlusMinusBarWidget<ZoomLensPlugin> {

    /** the widget id */
    public final static String widgetBarXObjectifID = "@widget/objectif/x";

    /** default bar width */
    private final static double defaultBarWidth = 80;

    /** default bar height */
    private final static double defaultBarHeight = 16;

    /** default x folder index */
    private final static int defaultXFolderIndex = 1;

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
    * create X Objectif
    * @param width
    * @param height
    */
    public LensX(double width, double height) {
        super(widgetBarXObjectifID, width, height, defaultXFolderIndex,
              defaultYFolderIndex, BarWidgetOrientation.Horizontal);
        setShader(fractions, colors);
        setOutlineStroke(basicStroke);
        setOutlineColor(outlineColor);
        setButtonDrawColor(buttonDrawColor);
        setButtonRolloverDrawColor(buttonRolloverDrawColor);
        setOrphanLock(true);
    }

   /**
    * create X Objectif
    * @param width
    * @param height
    * @param xIndex
    * @param yIndex
    */
    public LensX(double width, double height, int xIndex, int yIndex) {
        super(widgetBarXObjectifID, width, height, xIndex, yIndex,
              BarWidgetOrientation.Horizontal);
        setShader(fractions, colors);
        setOutlineStroke(basicStroke);
        setOutlineColor(outlineColor);
        setButtonDrawColor(buttonDrawColor);
        setButtonRolloverDrawColor(buttonRolloverDrawColor);
        setOrphanLock(true);
    }

    /**
     * create default objectif x widget
     */
    public LensX() {
        super(widgetBarXObjectifID, defaultBarWidth, defaultBarHeight,
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
     * @see org.jensoft.core.widget.Widget#isCompatiblePlugin()
     */
    @Override
    public final boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof ZoomLensPlugin) {
            return true;
        }
        return false;
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.bar.AbstractBarWidget#onButton1Press()
     */
    @Override
    public void onButton1Press() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().startZoomOut(ZoomNature.ZoomX);
        super.onButton1Press();

    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.bar.AbstractBarWidget#onButton2Press()
     */
    @Override
    public void onButton2Press() {
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().startZoomIn(ZoomNature.ZoomX);
        super.onButton2Press();
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.bar.AbstractBarWidget#onButton1Released()
     */
    @Override
    public void onButton1Released() {
    getHost();
        if (!getHost().isLockSelected()) {
            return;
        }
        getHost().stopZoomIn();
        getHost().stopZoomOut();
        // call a repaint on button framing rectangle
        super.onButton1Released();

    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.bar.AbstractBarWidget#onButton2Released()
     */
    @Override
    public void onButton2Released() {

        ZoomLensPlugin objectif = (ZoomLensPlugin) getHost();
        objectif.stopZoomIn();
        objectif.stopZoomOut();
        // call a repaint on button framing rectangle
        super.onButton2Released();
    }

}
