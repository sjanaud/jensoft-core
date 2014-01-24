/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.message;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.palette.Spectral;
import com.jensoft.core.plugin.message.MessagePlugin.Slide;
import com.jensoft.core.widget.bar.AbstractBackwardForwardBarWidget;
import com.jensoft.core.widget.bar.AbstractBarGeometry.BarWidgetOrientation;

public class MessageSelecterWidget extends AbstractBackwardForwardBarWidget {

    /** the widget id */
    public final static String widgetBarXObjectifID = "@widget/message";

    /** default bar width */
    private final static double defaultBarWidth = 180;

    /** default bar height */
    private final static double defaultBarHeight = 16;

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
    private Color outlineColor = Color.BLACK;

    /** buttons strokes */
    private Stroke buttonStroke = new BasicStroke(2f, BasicStroke.CAP_ROUND,
                                                  BasicStroke.JOIN_BEVEL);

    /** basic stroke */
    private Stroke basicStroke = new BasicStroke(0.8f);

    /** button rollover draw color */
    private Color buttonRolloverDrawColor = RosePalette.LEMONPEEL;

    /** button draw color */
    private Color buttonDrawColor = Spectral.SPECTRAL_BLUE1;

 
    /**
     * create message selecter widget
     * @param width
     * @param height
     */
    public MessageSelecterWidget(double width, double height) {
        super(widgetBarXObjectifID, width, height, defaultXFolderIndex,
              defaultYFolderIndex, BarWidgetOrientation.Horizontal);
        setShader(fractions, colors);
        setOutlineStroke(basicStroke);
        setOutlineColor(outlineColor);
        // setButtonDrawColor(buttonDrawColor);
        setButtonFillColor(Color.BLACK);
        setButtonRolloverFillColor(Spectral.SPECTRAL_RED);
        setOrphanLock(true);
    }

    /**
     * create X Translate
     * 
     * @param width
     * @param height
     * @param xIndex
     * @param yIndex
     */
    public MessageSelecterWidget(double width, double height, int xIndex, int yIndex) {
        super(widgetBarXObjectifID, width, height, xIndex, yIndex,
              BarWidgetOrientation.Horizontal);
        setShader(fractions, colors);
        setOutlineStroke(basicStroke);
        setOutlineColor(outlineColor);
        setButtonDrawColor(buttonDrawColor);
        setButtonFillColor(Color.BLACK);
        setButtonRolloverFillColor(Spectral.SPECTRAL_RED);
        setOrphanLock(true);
    }

    /**
     * create default translate x widget
     */
    public MessageSelecterWidget() {
        super(widgetBarXObjectifID, defaultBarWidth, defaultBarHeight,
              defaultXFolderIndex, defaultYFolderIndex,
              BarWidgetOrientation.Horizontal);
        setShader(fractions, colors);
        setOutlineStroke(basicStroke);
        setOutlineColor(outlineColor);
        // setButtonDrawColor(buttonDrawColor);
        setButtonFillColor(Spectral.SPECTRAL_RED);
        setButtonRolloverFillColor(Spectral.SPECTRAL_RED.brighter().brighter());
        setOrphanLock(true);
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#isCompatiblePlugin()
     */
    @Override
    public final boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof MessagePlugin) {
            return true;
        }
        return false;
    }

    /**
     * get host as translate plugin
     * 
     * @return translate
     */
    private MessagePlugin getMessagePlugin() {
        return (MessagePlugin) getHost();
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onPaintStart()
     */
    @Override
    public void onPaintStart() {
        setButton1Visible(false);
        setButton2Visible(false);
        if (getMessagePlugin().getCurrentIndex() >= 0) {
            setButton1Visible(true);
        }
        if (getMessagePlugin().getCurrentIndex() <= getMessagePlugin().messageCount() - 1) {
            setButton2Visible(true);
        }
        super.onPaintStart();

    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onButton1Press()
     */
    @Override
    public void onButton1Press() {

        if (!isButton1Visible()) {
            return;
        }

        if (!getMessagePlugin().isLockSelected()) {
            return;
        }

        if (getMessagePlugin().isSliding()) {
            return;
        }

        if (getMessagePlugin().getCurrentIndex() == 0) {
            getMessagePlugin().showMessage(getMessagePlugin().messageCount(), Slide.PreviousMessage);
            getMessagePlugin().setCurrentIndex(-1);
        }
        else {
            getMessagePlugin().previousMessage();
        }
        //System.out.println("onButton1Press index message : " + getMessagePlugin().getCurrentIndex());
        super.onButton1Press();
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.bar.AbstractBarWidget#onButton2Press()
     */
    @Override
    public void onButton2Press() {

        if (!isButton2Visible()) {
            return;
        }

        if (!getMessagePlugin().isLockSelected()) {
            return;
        }

        if (getMessagePlugin().isSliding()) {
            return;
        }

        if (getMessagePlugin().getCurrentIndex() == getMessagePlugin().messageCount() - 1) {
            getMessagePlugin().showMessage(-1, Slide.NextMessage);
            getMessagePlugin().setCurrentIndex(getMessagePlugin().messageCount());
        }
        else {
            getMessagePlugin().nextMessage();
        }
        //System.out.println("onButton2Press index message : " + getMessagePlugin().getCurrentIndex());
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
