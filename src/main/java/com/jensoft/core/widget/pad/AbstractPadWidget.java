/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.widget.pad;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;
import com.jensoft.core.widget.Widget;
import com.jensoft.core.widget.WidgetFolder;

/**
 * AbstractPadWidget
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractPadWidget<P extends AbstractPlugin> extends Widget<P> {

    /** theme color to fill pad base */
    private Color fillBaseColor;

    /** theme color to fill pad control */
    private Color fillControlColor;

    /** theme color to draw pad base */
    private Color drawBaseColor;

    /** theme color to draw pad control */
    private Color drawControlColor;

    /** stroke to draw pad base */
    private Color drawBaseStroke;

    /** stroke color to draw pad control */
    private Color drawControlStroke;

    /** north fill color */
    private Color fillNorthColor;

    /** north rollover fill color */
    private Color fillNorthRolloverColor;

    /** north stroke */
    private Stroke drawNorthStroke;

    /** north draw color */
    private Color drawNorthColor;

    /** north draw color */
    private Color drawNorthRolloverColor;

    /** south fill color */
    private Color fillSouthColor;

    /** south rollover fill color */
    private Color fillSouthRolloverColor;

    /** south stroke */
    private Stroke drawSouthStroke;

    /** south draw color */
    private Color drawSouthColor;

    /** south draw color */
    private Color drawSouthRolloverColor;

    /** west fill color */
    private Color fillWestColor;

    /** west rollover fill color */
    private Color fillWestRolloverColor;

    /** west stroke */
    private Stroke drawWestStroke;

    /** west draw color */
    private Color drawWestColor;

    /** west draw color */
    private Color drawWestRolloverColor;

    /** east fill color */
    private Color fillEastColor;

    /** east rollover fill color */
    private Color fillEastRolloverColor;

    /** east stroke */
    private Stroke drawEastStroke;

    /** east draw color */
    private Color drawEastColor;

    /** east draw color */
    private Color drawEastRolloverColor;

    /** basic stroke */
    private Stroke basicStroke = new BasicStroke();

    /** abstract pad geometry */
    private AbstractPadGeometry padGeometry;


    /**
     * create abstract pad geometry with specified parameters
     * @param id
     * @param padSquare
     * @param xIndex
     * @param yIndex
     * @param geometry
     */
    public AbstractPadWidget(String id, double padSquare, int xIndex,
            int yIndex, AbstractPadGeometry geometry) {
        super(id, padSquare, padSquare, xIndex, yIndex);
        padGeometry = geometry;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#interceptMove(int, int)
     */
    @Override
    public void interceptMove(int x, int y) {
        super.interceptMove(x, y);

        if (getWidgetFolder() == null) {
            return;
        }

        if (!getWidgetFolder().getBounds2D().contains(x, y)) {
            padGeometry.setNorthRollover(false);
            padGeometry.setSouthRollover(false);
            padGeometry.setWestRollover(false);
            padGeometry.setEastRollover(false);
            onNorthButtonRolloverOff();
            onSouthButtonRolloverOff();
            onWestButtonRolloverOff();
            onEastButtonRolloverOff();
            return;
        }

        checkMoveOperation(x, y);
        trackRollover(x, y);

    }

    /**
     * track roll over on button 1 and button 2
     * 
     * @param x
     * @param y
     */
    private void trackRollover(int x, int y) {
        if (padGeometry.getRectNorth() != null
                && padGeometry.getRectNorth().contains(x, y)) {
            if (!padGeometry.isNorthRollover()) {
                padGeometry.setNorthRollover(true);
                onNorthButtonRolloverOn();
            }
        }
        else {
            if (padGeometry.isNorthRollover()) {
                padGeometry.setNorthRollover(false);
                onNorthButtonRolloverOff();
            }
        }

        if (padGeometry.getRectSouth() != null
                && padGeometry.getRectSouth().contains(x, y)) {
            if (!padGeometry.isSouthRollover()) {
                padGeometry.setSouthRollover(true);
                onSouthButtonRolloverOn();
            }
        }
        else {
            if (padGeometry.isSouthRollover()) {
                padGeometry.setSouthRollover(false);
                onSouthButtonRolloverOff();
            }
        }

        if (padGeometry.getRectWest() != null
                && padGeometry.getRectWest().contains(x, y)) {
            if (!padGeometry.isWestRollover()) {
                padGeometry.setWestRollover(true);
                onWestButtonRolloverOn();
            }
        }
        else {
            if (padGeometry.isWestRollover()) {
                padGeometry.setWestRollover(false);
                onWestButtonRolloverOff();
            }
        }

        if (padGeometry.getRectEast() != null
                && padGeometry.getRectEast().contains(x, y)) {
            if (!padGeometry.isEastRollover()) {
                padGeometry.setEastRollover(true);
                onEastButtonRolloverOn();
            }
        }
        else {
            if (padGeometry.isEastRollover()) {
                padGeometry.setEastRollover(false);
                onEastButtonRolloverOff();
            }
        }

    }

    /**
     * call when button north is roll over
     */
    public void onNorthButtonRolloverOn() {
        repaintNorthButton();
    }

    /**
     * call when button north is no longer roll over
     */
    public void onNorthButtonRolloverOff() {
        repaintNorthButton();
    }

    /**
     * call when button south is roll over
     */
    public void onSouthButtonRolloverOn() {
        repaintSouthButton();
    }

    /**
     * call when button south is no longer roll over
     */
    public void onSouthButtonRolloverOff() {
        repaintSouthButton();
    }

    /**
     * call when button west is roll over
     */
    public void onWestButtonRolloverOn() {
        repaintWestButton();
    }

    /**
     * call when button west is no longer roll over
     */
    public void onWestButtonRolloverOff() {
        repaintWestButton();
    }

    /**
     * call when button east is roll over
     */
    public void onEastButtonRolloverOn() {
        repaintEastButton();
    }

    /**
     * call when button east is no longer roll over
     */
    public void onEastButtonRolloverOff() {
        repaintEastButton();
    }

    /**
     * override this method to handle button north pressed
     */
    public void onNorthButtonPress() {
        repaintNorthButton();
    }

    /**
     * override this method to handle button south pressed
     */
    public void onSouthButtonPress() {
        repaintSouthButton();
    }

    /**
     * override this method to handle button west pressed
     */
    public void onWestButtonPress() {
        repaintWestButton();
    }

    /**
     * override this method to handle button east pressed
     */
    public void onEastButtonPress() {
        repaintEastButton();
    }

    /**
     * override this method to handle button north released
     */
    public void onNorthButtonReleased() {
        repaintNorthButton();
    }

    /**
     * override this method to handle button south released
     */
    public void onSouthButtonReleased() {
        repaintSouthButton();
    }

    /**
     * override this method to handle button west released
     */
    public void onWestButtonReleased() {
        repaintWestButton();
    }

    /**
     * override this method to handle button east released
     */
    public void onEastButtonReleased() {
        repaintEastButton();
    }

    /**
     * repaint only the north button bounding frame
     */
    public void repaintNorthButton() {
        if (padGeometry == null || padGeometry.getRectNorth() == null) {
            return;
        }
        getHost().getProjection().getView2D()
                .repaintDevice(padGeometry.getRectNorth().getBounds());
    }

    /**
     * repaint only the south button bounding frame
     */
    public void repaintSouthButton() {
        if (padGeometry == null || padGeometry.getRectSouth() == null) {
            return;
        }
        getHost().getProjection().getView2D()
                .repaintDevice(padGeometry.getRectSouth().getBounds());
    }

    /**
     * repaint only the west button bounding frame
     */
    public void repaintWestButton() {
        if (padGeometry == null || padGeometry.getRectWest() == null) {
            return;
        }
        getHost().getProjection().getView2D()
                .repaintDevice(padGeometry.getRectWest().getBounds());
    }

    /**
     * repaint only the east button bounding frame
     */
    public void repaintEastButton() {
        if (padGeometry == null || padGeometry.getRectEast() == null) {
            return;
        }
        getHost().getProjection().getView2D()
                .repaintDevice(padGeometry.getRectEast().getBounds());
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#interceptPress(int, int)
     */
    @Override
    public void interceptPress(int x, int y) {
        super.interceptPress(x, y);

        if (padGeometry.getRectNorth() != null
                && padGeometry.getRectNorth().contains(x, y)) {
            onNorthButtonPress();
        }
        else {

        }

        if (padGeometry.getRectSouth() != null
                && padGeometry.getRectSouth().contains(x, y)) {
            onSouthButtonPress();
        }
        else {

        }

        if (padGeometry.getRectWest() != null
                && padGeometry.getRectWest().contains(x, y)) {
            onWestButtonPress();
        }
        else {

        }

        if (padGeometry.getRectEast() != null
                && padGeometry.getRectEast().contains(x, y)) {
            onEastButtonPress();
        }
        else {

        }

    }

    /**
     * @return the fillBaseColor
     */
    public Color getFillBaseColor() {
        return fillBaseColor;
    }

    /**
     * @param fillBaseColor
     *            the fillBaseColor to set
     */
    public void setFillBaseColor(Color fillBaseColor) {
        this.fillBaseColor = fillBaseColor;
    }

    /**
     * @return the fillControlColor
     */
    public Color getFillControlColor() {
        return fillControlColor;
    }

    /**
     * @param fillControlColor
     *            the fillControlColor to set
     */
    public void setFillControlColor(Color fillControlColor) {
        this.fillControlColor = fillControlColor;
    }

    /**
     * @return the drawBaseColor
     */
    public Color getDrawBaseColor() {
        return drawBaseColor;
    }

    /**
     * @param drawBaseColor
     *            the drawBaseColor to set
     */
    public void setDrawBaseColor(Color drawBaseColor) {
        this.drawBaseColor = drawBaseColor;
    }

    /**
     * @return the drawControlColor
     */
    public Color getDrawControlColor() {
        return drawControlColor;
    }

    /**
     * @param drawControlColor
     *            the drawControlColor to set
     */
    public void setDrawControlColor(Color drawControlColor) {
        this.drawControlColor = drawControlColor;
    }

    /**
     * @return the drawBaseStroke
     */
    public Color getDrawBaseStroke() {
        return drawBaseStroke;
    }

    /**
     * @param drawBaseStroke
     *            the drawBaseStroke to set
     */
    public void setDrawBaseStroke(Color drawBaseStroke) {
        this.drawBaseStroke = drawBaseStroke;
    }

    /**
     * @return the drawControlStroke
     */
    public Color getDrawControlStroke() {
        return drawControlStroke;
    }

    /**
     * @param drawControlStroke
     *            the drawControlStroke to set
     */
    public void setDrawControlStroke(Color drawControlStroke) {
        this.drawControlStroke = drawControlStroke;
    }

    /**
     * fill identical color for all pad button
     * 
     * @param fillButtonColor
     *            the color for all button to set on fill operation
     */
    public void setFillButtonColor(Color fillButtonColor) {
        fillNorthColor = fillButtonColor;
        fillSouthColor = fillButtonColor;
        fillWestColor = fillButtonColor;
        fillEastColor = fillButtonColor;
    }

    /**
     * fill identical color for all pad button
     * 
     * @param fillRolloverButtonColor
     *            the color for all button to set on fill operation when button
     *            is roll over
     */
    public void setFillButtonRolloverColor(Color fillRolloverButtonColor) {
        fillNorthRolloverColor = fillRolloverButtonColor;
        fillSouthRolloverColor = fillRolloverButtonColor;
        fillWestRolloverColor = fillRolloverButtonColor;
        fillEastRolloverColor = fillRolloverButtonColor;
    }

    /**
     * stroke identical for all pad button
     * 
     * @param drawButtonStroke
     *            the stroke for all button to set on draw operation
     */
    public void setDrawButtonStroke(Stroke drawButtonStroke) {
        drawNorthStroke = drawButtonStroke;
        drawSouthStroke = drawButtonStroke;
        drawWestStroke = drawButtonStroke;
        drawEastStroke = drawButtonStroke;

    }

    /**
     * @param drawButtonColor
     *            the color for all button to set on draw operation
     */
    public void setDrawButtonColor(Color drawButtonColor) {
        drawNorthColor = drawButtonColor;
        drawSouthColor = drawButtonColor;
        drawWestColor = drawButtonColor;
        drawEastColor = drawButtonColor;
    }

    /**
     * @param drawButtonRolloverColor
     *            the color for all button to set on draw operation when button
     *            is roll over
     */
    public void setDrawButtonRolloverColor(Color drawButtonRolloverColor) {
        drawNorthRolloverColor = drawButtonRolloverColor;
        drawSouthRolloverColor = drawButtonRolloverColor;
        drawWestRolloverColor = drawButtonRolloverColor;
        drawEastRolloverColor = drawButtonRolloverColor;

    }

    /**
     * @return the fillNorthColor
     */
    public Color getFillNorthColor() {
        return fillNorthColor;
    }

    /**
     * @param fillNorthColor
     *            the fillNorthColor to set
     */
    public void setFillNorthColor(Color fillNorthColor) {
        this.fillNorthColor = fillNorthColor;
    }

    /**
     * @return the fillNorthRolloverColor
     */
    public Color getFillNorthRolloverColor() {
        return fillNorthRolloverColor;
    }

    /**
     * @param fillNorthRolloverColor
     *            the fillNorthRolloverColor to set
     */
    public void setFillNorthRolloverColor(Color fillNorthRolloverColor) {
        this.fillNorthRolloverColor = fillNorthRolloverColor;
    }

    /**
     * @return the drawNorthStroke
     */
    public Stroke getDrawNorthStroke() {
        return drawNorthStroke;
    }

    /**
     * @param drawNorthStroke
     *            the drawNorthStroke to set
     */
    public void setDrawNorthStroke(Stroke drawNorthStroke) {
        this.drawNorthStroke = drawNorthStroke;
    }

    /**
     * @return the drawNorthColor
     */
    public Color getDrawNorthColor() {
        return drawNorthColor;
    }

    /**
     * @param drawNorthColor
     *            the drawNorthColor to set
     */
    public void setDrawNorthColor(Color drawNorthColor) {
        this.drawNorthColor = drawNorthColor;
    }

    /**
     * @return the drawNorthRolloverColor
     */
    public Color getDrawNorthRolloverColor() {
        return drawNorthRolloverColor;
    }

    /**
     * @param drawNorthRolloverColor
     *            the drawNorthRolloverColor to set
     */
    public void setDrawNorthRolloverColor(Color drawNorthRolloverColor) {
        this.drawNorthRolloverColor = drawNorthRolloverColor;
    }

    /**
     * @return the fillSouthColor
     */
    public Color getFillSouthColor() {
        return fillSouthColor;
    }

    /**
     * @param fillSouthColor
     *            the fillSouthColor to set
     */
    public void setFillSouthColor(Color fillSouthColor) {
        this.fillSouthColor = fillSouthColor;
    }

    /**
     * @return the fillSouthRolloverColor
     */
    public Color getFillSouthRolloverColor() {
        return fillSouthRolloverColor;
    }

    /**
     * @param fillSouthRolloverColor
     *            the fillSouthRolloverColor to set
     */
    public void setFillSouthRolloverColor(Color fillSouthRolloverColor) {
        this.fillSouthRolloverColor = fillSouthRolloverColor;
    }

    /**
     * @return the drawSouthStroke
     */
    public Stroke getDrawSouthStroke() {
        return drawSouthStroke;
    }

    /**
     * @param drawSouthStroke
     *            the drawSouthStroke to set
     */
    public void setDrawSouthStroke(Stroke drawSouthStroke) {
        this.drawSouthStroke = drawSouthStroke;
    }

    /**
     * @return the drawSouthColor
     */
    public Color getDrawSouthColor() {
        return drawSouthColor;
    }

    /**
     * @param drawSouthColor
     *            the drawSouthColor to set
     */
    public void setDrawSouthColor(Color drawSouthColor) {
        this.drawSouthColor = drawSouthColor;
    }

    /**
     * @return the drawSouthRolloverColor
     */
    public Color getDrawSouthRolloverColor() {
        return drawSouthRolloverColor;
    }

    /**
     * @param drawSouthRolloverColor
     *            the drawSouthRolloverColor to set
     */
    public void setDrawSouthRolloverColor(Color drawSouthRolloverColor) {
        this.drawSouthRolloverColor = drawSouthRolloverColor;
    }

    /**
     * @return the fillWestColor
     */
    public Color getFillWestColor() {
        return fillWestColor;
    }

    /**
     * @param fillWestColor
     *            the fillWestColor to set
     */
    public void setFillWestColor(Color fillWestColor) {
        this.fillWestColor = fillWestColor;
    }

    /**
     * @return the fillWestRolloverColor
     */
    public Color getFillWestRolloverColor() {
        return fillWestRolloverColor;
    }

    /**
     * @param fillWestRolloverColor
     *            the fillWestRolloverColor to set
     */
    public void setFillWestRolloverColor(Color fillWestRolloverColor) {
        this.fillWestRolloverColor = fillWestRolloverColor;
    }

    /**
     * @return the drawWestStroke
     */
    public Stroke getDrawWestStroke() {
        return drawWestStroke;
    }

    /**
     * @param drawWestStroke
     *            the drawWestStroke to set
     */
    public void setDrawWestStroke(Stroke drawWestStroke) {
        this.drawWestStroke = drawWestStroke;
    }

    /**
     * @return the drawWestColor
     */
    public Color getDrawWestColor() {
        return drawWestColor;
    }

    /**
     * @param drawWestColor
     *            the drawWestColor to set
     */
    public void setDrawWestColor(Color drawWestColor) {
        this.drawWestColor = drawWestColor;
    }

    /**
     * @return the drawWestRolloverColor
     */
    public Color getDrawWestRolloverColor() {
        return drawWestRolloverColor;
    }

    /**
     * @param drawWestRolloverColor
     *            the drawWestRolloverColor to set
     */
    public void setDrawWestRolloverColor(Color drawWestRolloverColor) {
        this.drawWestRolloverColor = drawWestRolloverColor;
    }

    /**
     * @return the fillEastColor
     */
    public Color getFillEastColor() {
        return fillEastColor;
    }

    /**
     * @param fillEastColor
     *            the fillEastColor to set
     */
    public void setFillEastColor(Color fillEastColor) {
        this.fillEastColor = fillEastColor;
    }

    /**
     * @return the fillEastRolloverColor
     */
    public Color getFillEastRolloverColor() {
        return fillEastRolloverColor;
    }

    /**
     * @param fillEastRolloverColor
     *            the fillEastRolloverColor to set
     */
    public void setFillEastRolloverColor(Color fillEastRolloverColor) {
        this.fillEastRolloverColor = fillEastRolloverColor;
    }

    /**
     * @return the drawEastStroke
     */
    public Stroke getDrawEastStroke() {
        return drawEastStroke;
    }

    /**
     * @param drawEastStroke
     *            the drawEastStroke to set
     */
    public void setDrawEastStroke(Stroke drawEastStroke) {
        this.drawEastStroke = drawEastStroke;
    }

    /**
     * @return the drawEastColor
     */
    public Color getDrawEastColor() {
        return drawEastColor;
    }

    /**
     * @param drawEastColor
     *            the drawEastColor to set
     */
    public void setDrawEastColor(Color drawEastColor) {
        this.drawEastColor = drawEastColor;
    }

    /**
     * @return the drawEastRolloverColor
     */
    public Color getDrawEastRolloverColor() {
        return drawEastRolloverColor;
    }

    /**
     * @param drawEastRolloverColor
     *            the drawEastRolloverColor to set
     */
    public void setDrawEastRolloverColor(Color drawEastRolloverColor) {
        this.drawEastRolloverColor = drawEastRolloverColor;
    }

    /**
     * @return the padGeometry
     */
    public AbstractPadGeometry getPadGeometry() {
        return padGeometry;
    }

    /**
     * @param padGeometry
     *            the padGeometry to set
     */
    public void setPadGeometry(AbstractPadGeometry padGeometry) {
        this.padGeometry = padGeometry;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#interceptDrag(int, int)
     */
    @Override
    public void interceptDrag(int x, int y) {
        super.interceptDrag(x, y);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#interceptReleased(int, int)
     */
    @Override
    public void interceptReleased(int x, int y) {
        super.interceptReleased(x, y);
        onNorthButtonReleased();
        onSouthButtonReleased();
        onWestButtonReleased();
        onEastButtonReleased();
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#paintWidget(com.jensoft.core.view.View2D, java.awt.Graphics2D)
     */
    @Override
    protected void paintWidget(View v2d, Graphics2D g2d) {

        // paintDebug(v2d, g2d);

        if (!getHost().isLockSelected()) {
            return;
        }

        if (getWidgetFolder() == null || padGeometry == null) {
            return;
        }

        WidgetFolder currentFolder = getWidgetFolder();
        Rectangle2D boundFolder = currentFolder.getBounds2D();

        padGeometry.setSolveRequest(true);
        padGeometry.solveGeometry(boundFolder);
        setSensibleShapes(padGeometry.getSensibleShapes());

        // BASE(fill & draw)
        if (fillBaseColor != null) {
            g2d.setColor(fillBaseColor);
            g2d.fill(padGeometry.getBaseShape());
        }
        if (drawBaseColor != null) {
            g2d.setColor(drawBaseColor);
            g2d.setStroke(basicStroke);
            g2d.draw(padGeometry.getBaseShape());
        }

        // CONTROL(fill & draw)
        if (fillControlColor != null) {
            g2d.setColor(fillControlColor);
            g2d.fill(padGeometry.getControlShape());
        }
        if (drawControlColor != null) {
            g2d.setColor(drawControlColor);
            g2d.setStroke(basicStroke);
            g2d.draw(padGeometry.getControlShape());
        }

        // NORTH(fill & draw)

        if (!padGeometry.isNorthRollover()) {
            if (fillNorthColor != null) {
                g2d.setColor(fillNorthColor);
                g2d.fill(padGeometry.getNorthButton());
            }
            if (drawNorthColor != null) {
                g2d.setColor(drawNorthColor);
                if (drawNorthStroke != null) {
                    g2d.setStroke(drawNorthStroke);
                }
                g2d.draw(padGeometry.getNorthButton());
                g2d.setStroke(basicStroke);
            }
        }
        else {
            if (fillNorthRolloverColor != null) {
                g2d.setColor(fillNorthRolloverColor);
                g2d.fill(padGeometry.getNorthButton());
            }
            if (drawNorthRolloverColor != null) {
                g2d.setColor(drawNorthRolloverColor);
                if (drawNorthStroke != null) {
                    g2d.setStroke(drawNorthStroke);
                }
                g2d.draw(padGeometry.getNorthButton());
                g2d.setStroke(basicStroke);
            }
        }

        // SOUTH(fill & draw)
        if (!padGeometry.isSouthRollover()) {
            if (fillSouthColor != null) {
                g2d.setColor(fillSouthColor);
                g2d.fill(padGeometry.getSouthButton());
            }
            if (drawSouthColor != null) {
                g2d.setColor(drawSouthColor);
                if (drawSouthStroke != null) {
                    g2d.setStroke(drawSouthStroke);
                }
                g2d.draw(padGeometry.getSouthButton());
                g2d.setStroke(basicStroke);
            }
        }
        else {
            if (fillSouthRolloverColor != null) {
                g2d.setColor(fillSouthRolloverColor);
                g2d.fill(padGeometry.getSouthButton());
            }
            if (drawSouthRolloverColor != null) {
                g2d.setColor(drawSouthRolloverColor);
                if (drawSouthStroke != null) {
                    g2d.setStroke(drawSouthStroke);
                }
                g2d.draw(padGeometry.getSouthButton());
                g2d.setStroke(basicStroke);
            }
        }

        // WEST(fill & draw)
        if (!padGeometry.isWestRollover()) {
            if (fillWestColor != null) {
                g2d.setColor(fillWestColor);
                g2d.fill(padGeometry.getWestButton());
            }
            if (drawWestColor != null) {
                g2d.setColor(drawWestColor);
                if (drawWestStroke != null) {
                    g2d.setStroke(drawWestStroke);
                }
                g2d.draw(padGeometry.getWestButton());
                g2d.setStroke(basicStroke);
            }
        }
        else {
            if (fillWestRolloverColor != null) {
                g2d.setColor(fillWestRolloverColor);
                g2d.fill(padGeometry.getWestButton());
            }
            if (drawWestRolloverColor != null) {
                g2d.setColor(drawWestRolloverColor);
                if (drawWestStroke != null) {
                    g2d.setStroke(drawWestStroke);
                }
                g2d.draw(padGeometry.getWestButton());
                g2d.setStroke(basicStroke);
            }
        }

        // EAST(fill & draw)
        if (!padGeometry.isEastRollover()) {
            if (fillEastColor != null) {
                g2d.setColor(fillEastColor);
                g2d.fill(padGeometry.getEastButton());
            }
            if (drawEastColor != null) {
                g2d.setColor(drawEastColor);
                if (drawEastStroke != null) {
                    g2d.setStroke(drawEastStroke);
                }
                g2d.draw(padGeometry.getEastButton());
                g2d.setStroke(basicStroke);
            }
        }
        else {
            if (fillEastRolloverColor != null) {
                g2d.setColor(fillEastRolloverColor);
                g2d.fill(padGeometry.getEastButton());
            }
            if (drawEastRolloverColor != null) {
                g2d.setColor(drawEastRolloverColor);
                if (drawEastStroke != null) {
                    g2d.setStroke(drawEastStroke);
                }
                g2d.draw(padGeometry.getEastButton());
                g2d.setStroke(basicStroke);
            }
        }
    }

    /**
     * paint debug
     * 
     * @param v2d
     *            view
     * @param g2d
     *            graphics context
     */
    private void paintDebug(View v2d, Graphics2D g2d) {
        if (getHost().isLockSelected()) {

            if (getWidgetFolder() == null || padGeometry == null) {
                return;
            }

            WidgetFolder currentFolder = getWidgetFolder();
            Rectangle2D boundFolder = currentFolder.getBounds2D();

            padGeometry.setSolveRequest(true);
            padGeometry.solveGeometry(boundFolder);
            setSensibleShapes(padGeometry.getSensibleShapes());

            g2d.setColor(Color.RED);
            g2d.draw(padGeometry.getBaseShape());
            g2d.draw(padGeometry.getControlShape());

            g2d.draw(padGeometry.getNorthButton());
            g2d.draw(padGeometry.getSouthButton());
            g2d.draw(padGeometry.getWestButton());
            g2d.draw(padGeometry.getEastButton());

            // g2d.draw(padGeometry.getRectNorth());
            // g2d.draw(padGeometry.getRectSouth());
            // g2d.draw(padGeometry.getRectWest());
            // g2d.draw(padGeometry.getRectEast());

        }
    }

}
