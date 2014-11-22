/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.widget.bar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;
import com.jensoft.core.widget.Widget;
import com.jensoft.core.widget.WidgetFolder;
import com.jensoft.core.widget.bar.AbstractBarGeometry.BarWidgetOrientation;

/**
 * AbstractBarWidget
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractBarWidget<P extends AbstractPlugin> extends Widget<P> {

    /** the widget id */
    public final static String ID = "@widget/core/abstractbarwidget";

    /** widget geometry */
    private AbstractBarGeometry geometry;

    /** buttons strokes */
    private Stroke defaultStroke = new BasicStroke(2f, BasicStroke.CAP_ROUND,
                                                   BasicStroke.JOIN_BEVEL);

    /** basic stroke */
    private Stroke basicStroke = new BasicStroke(1f);

    /** plus minus orientation */
    private BarWidgetOrientation barWidgetOrientation;

    /** theme color to fill bar */
    private Color fillColor;

    /** shade fractions */
    private float[] shadeFractions;

    /** shade colors */
    private Color[] shadeColors;

    /** outline color */
    private Color outlineColor;

    /** outline bar widget stroke */
    private Stroke outlineStroke;

    /** button 1 fill color */
    private Color button1FillColor;

    /** button 2 fill color */
    private Color button2FillColor;

    /** button 1 draw color */
    private Color button1DrawColor;

    /** button 2 draw color */
    private Color button2DrawColor;

    /** button 1 rollover fill color */
    private Color button1RolloverFillColor;

    /** button 2 rollover fill color */
    private Color button2RolloverFillColor;

    /** button 1 rollover draw color */
    private Color button1RolloverDrawColor;

    /** button 2 rollover draw color */
    private Color button2RolloverDrawColor;

    /** visible flag for button 1 */
    private boolean button1Visible = true;

    /** visible flag for button 2 */
    private boolean button2Visible = true;

    /**
     * create AbstractBarWidget with specified geometry and orientation
     * 
     * @param geometry
     *            bar widget geometry
     * @param barWidgetOrientation
     *            bar widget orientation
     */
    public AbstractBarWidget(AbstractBarGeometry geometry,
            BarWidgetOrientation barWidgetOrientation) {
        super(ID, 80, 18, 2, 100);
        this.barWidgetOrientation = barWidgetOrientation;
        this.geometry = geometry;
    }

    /**
     * create AbstractBarWidget with specified parameters
     * 
     * @param id
     *            widget ID
     * @param width
     *            widget width
     * @param height
     *            widget height
     * @param xIndex
     *            widget x folder index
     * @param yIndex
     *            widget y folder index
     * @param geometry
     *            widget bar geometry
     * @param barWidgetOrientation
     *            widget bat orientation
     */
    public AbstractBarWidget(String id, double width, double height,
            int xIndex, int yIndex, AbstractBarGeometry geometry,
            BarWidgetOrientation barWidgetOrientation) {
        super(id, width, height, xIndex, yIndex);
        this.barWidgetOrientation = barWidgetOrientation;
        this.geometry = geometry;
    }

    /**
     * @return the button1RolloverFillColor
     */
    public Color getButton1RolloverFillColor() {
        return button1RolloverFillColor;
    }

    /**
     * @param button1RolloverFillColor
     *            the button1RolloverFillColor to set
     */
    public void setButton1RolloverFillColor(Color button1RolloverFillColor) {
        this.button1RolloverFillColor = button1RolloverFillColor;
    }

    /**
     * @return the button2RolloverFillColor
     */
    public Color getButton2RolloverFillColor() {
        return button2RolloverFillColor;
    }

    /**
     * @param button2RolloverFillColor
     *            the button2RolloverFillColor to set
     */
    public void setButton2RolloverFillColor(Color button2RolloverFillColor) {
        this.button2RolloverFillColor = button2RolloverFillColor;
    }

    /**
     * set identical button roll over fill color
     * 
     * @param buttonRolloverFillColor
     *            the buttonRolloverFillColor to set
     */
    public void setButtonRolloverFillColor(Color buttonRolloverFillColor) {
        button1RolloverFillColor = buttonRolloverFillColor;
        button2RolloverFillColor = buttonRolloverFillColor;
    }

    /**
     * @return the button1RolloverDrawColor
     */
    public Color getButton1RolloverDrawColor() {
        return button1RolloverDrawColor;
    }

    /**
     * @param button1RolloverDrawColor
     *            the button1RolloverDrawColor to set
     */
    public void setButton1RolloverDrawColor(Color button1RolloverDrawColor) {
        this.button1RolloverDrawColor = button1RolloverDrawColor;
    }

    /**
     * @return the button2RolloverDrawColor
     */
    public Color getButton2RolloverDrawColor() {
        return button2RolloverDrawColor;
    }

    /**
     * @param button2RolloverDrawColor
     *            the button2RolloverDrawColor to set
     */
    public void setButton2RolloverDrawColor(Color button2RolloverDrawColor) {
        this.button2RolloverDrawColor = button2RolloverDrawColor;
    }

    /**
     * set identical button roll over draw color
     * 
     * @param buttonRolloverDrawColor
     *            the buttonRolloverDrawColor to set
     */
    public void setButtonRolloverDrawColor(Color buttonRolloverDrawColor) {
        button1RolloverDrawColor = buttonRolloverDrawColor;
        button2RolloverDrawColor = buttonRolloverDrawColor;
    }

    /**
     * @return the button1FillColor
     */
    public Color getButton1FillColor() {
        return button1FillColor;
    }

    /**
     * @param button1FillColor
     *            the button1FillColor to set
     */
    public void setButton1FillColor(Color button1FillColor) {
        this.button1FillColor = button1FillColor;
    }

    /**
     * @return the button2FillColor
     */
    public Color getButton2FillColor() {
        return button2FillColor;
    }

    /**
     * @param button2FillColor
     *            the button2FillColor to set
     */
    public void setButton2FillColor(Color button2FillColor) {
        this.button2FillColor = button2FillColor;
    }

    /**
     * set identical button fill color
     * 
     * @param buttonFillColor
     *            the buttonFillColor to set
     */
    public void setButtonFillColor(Color buttonFillColor) {
        button1FillColor = buttonFillColor;
        button2FillColor = buttonFillColor;
    }

    /**
     * @return the button1DrawColor
     */
    public Color getButton1DrawColor() {
        return button1DrawColor;
    }

    /**
     * @param button1DrawColor
     *            the button1DrawColor to set
     */
    public void setButton1DrawColor(Color button1DrawColor) {
        this.button1DrawColor = button1DrawColor;
    }

    /**
     * @return the button2DrawColor
     */
    public Color getButton2DrawColor() {
        return button2DrawColor;
    }

    /**
     * @param button2DrawColor
     *            the button2DrawColor to set
     */
    public void setButton2DrawColor(Color button2DrawColor) {
        this.button2DrawColor = button2DrawColor;
    }

    /**
     * set identical button1 and button2 color
     * 
     * @param buttonDrawColor
     *            the buttonDrawColor to set
     */
    public void setButtonDrawColor(Color buttonDrawColor) {
        button1DrawColor = buttonDrawColor;
        button2DrawColor = buttonDrawColor;
    }

    /**
     * @return the outlineColor
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * @param outlineColor
     *            the outlineColor to set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
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

    /**
     * @return the shadeFractions
     */
    public float[] getShadeFractions() {
        return shadeFractions;
    }

    /**
     * @return the shadeColors
     */
    public Color[] getShadeColors() {
        return shadeColors;
    }

    /**
     * @return the outlineStroke
     */
    public Stroke getOutlineStroke() {
        return outlineStroke;
    }

    /**
     * @param outlineStroke
     *            the outlineStroke to set
     */
    public void setOutlineStroke(Stroke outlineStroke) {
        this.outlineStroke = outlineStroke;
    }

    /**
     * set the shadow parameters
     * 
     * @param fractions
     * @param colors
     */
    public void setShader(float[] fractions, Color[] colors) {
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("length array does not match");
        }
        shadeFractions = fractions;
        shadeColors = colors;
    }

    /**
     * set the shader
     * 
     * @param shader
     *            the shader to set
     */
    public void setShader(Shader shader) {
        shadeFractions = shader.getFractions();
        shadeColors = shader.getColors();
    }

    /**
     * @return the barWidgetOrientation
     */
    public BarWidgetOrientation getBarWidgetOrientation() {
        return barWidgetOrientation;
    }

    /**
     * @return the button1Visible
     */
    public boolean isButton1Visible() {
        return button1Visible;
    }

    /**
     * @param button1Visible
     *            the button1Visible to set
     */
    public void setButton1Visible(boolean button1Visible) {
        this.button1Visible = button1Visible;
    }

    /**
     * @return the button2Visible
     */
    public boolean isButton2Visible() {
        return button2Visible;
    }

    /**
     * @param button2Visible
     *            the button2Visible to set
     */
    public void setButton2Visible(boolean button2Visible) {
        this.button2Visible = button2Visible;
    }

    /**
     * @return the geometry
     */
    public AbstractBarGeometry getGeometry() {
        return geometry;
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

        // return if point does not intercept x bar
        if (!getWidgetFolder().getBounds2D().contains(x, y)) {
            geometry.setRollover1(false);
            geometry.setRollover2(false);
            onButton1RolloverOff();
            onButton2RolloverOff();
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
        if (geometry.getRect1() != null && geometry.getRect1().contains(x, y)) {
            if (!geometry.isRollover1()) {
                geometry.setRollover1(true);
                onButton1RolloverOn();
            }
        }
        else {
            if (geometry.isRollover1()) {
                geometry.setRollover1(false);
                onButton1RolloverOff();
            }
        }
        if (geometry.getRect2() != null && geometry.getRect2().contains(x, y)) {
            if (!geometry.isRollover2()) {
                geometry.setRollover2(true);
                onButton2RolloverOn();
            }
        }
        else {
            if (geometry.isRollover2()) {
                geometry.setRollover2(false);
                onButton2RolloverOff();
            }
        }
    }

    /**
     * call when button 1 is roll over only call repaint button 2
     */
    public void onButton1RolloverOn() {
        repaintButton1();
    }

    /**
     * call when button 1 is no longer roll over only call repaint button 1
     */
    public void onButton1RolloverOff() {
        repaintButton1();
    }

    /**
     * call when button 1 is roll over only call repaint button 2
     */
    public void onButton2RolloverOn() {
        repaintButton2();
    }

    /**
     * call when button 2 is no longer roll over
     */
    public void onButton2RolloverOff() {
        repaintButton2();
    }

    /**
     * override this method to handle button 1 pressed
     */
    public void onButton1Press() {
        repaintButton1();
    }

    /**
     * override this method to handle button 2 pressed
     */
    public void onButton2Press() {
        repaintButton2();
    }

    /**
     * override this method to handle button 1 released
     */
    public void onButton1Released() {
        repaintButton1();
    }

    /**
     * override this method to handle button 2 released
     */
    public void onButton2Released() {
        repaintButton2();
    }

    /**
     * repaint only the button1 bounding frame
     */
    public void repaintButton1() {
        if (geometry == null || geometry.getRect1() == null) {
            return;
        }
        getHost().getProjection().getView2D()
                .repaintDevice(geometry.getRect1().getBounds());
    }

    /**
     * repaint only the button2 bounding frame
     */
    public void repaintButton2() {
        if (geometry == null || geometry.getRect1() == null) {
            return;
        }
        getHost().getProjection().getView2D()
                .repaintDevice(geometry.getRect2().getBounds());
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#interceptPress(int, int)
     */
    @Override
    public void interceptPress(int x, int y) {
        super.interceptPress(x, y);

        if (!getHost().isLockSelected() && isOrphanLock()) {
            return;
        }

        if (geometry.getRect1() != null && geometry.getRect1().contains(x, y)) {
            onButton1Press();
        }

        if (geometry.getRect2() != null && geometry.getRect2().contains(x, y)) {
            onButton2Press();
        }

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
        onButton1Released();
        onButton2Released();
    }

    /**
     * call before widget painting operation
     */
    public void onPaintStart() {
    }

    /**
     * call after widget painting operation
     */
    public void onPaintEnd() {
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#paintWidget(com.jensoft.core.view.View2D, java.awt.Graphics2D)
     */
    @Override
    protected void paintWidget(View v2d, Graphics2D g2d) {

        // if(getHost().isLockSelected()){

        if (getWidgetFolder() == null || geometry == null) {
            return;
        }

        onPaintStart();

        WidgetFolder currentFolder = getWidgetFolder();
        Rectangle2D boundFolder = currentFolder.getBounds2D();

        geometry.setSolveRequest(true);
        geometry.solveGeometry(boundFolder);
        setSensibleShapes(geometry.getSensibleShapes());

        // FILL BACKGROUNG
        if (shadeFractions != null && shadeColors != null) {
            Point2D start = null;
            Point2D end = null;
            if (getBarWidgetOrientation() == BarWidgetOrientation.Horizontal) {
                start = new Point2D.Double(boundFolder.getCenterX(),
                                           boundFolder.getY());
                end = new Point2D.Double(boundFolder.getCenterX(),
                                         boundFolder.getY() + boundFolder.getHeight());
            }
            else {
                start = new Point2D.Double(boundFolder.getX(),
                                           boundFolder.getCenterY());
                end = new Point2D.Double(boundFolder.getX()
                        + boundFolder.getWidth(), boundFolder.getCenterY());
            }
            LinearGradientPaint lgp = new LinearGradientPaint(start, end,
                                                              shadeFractions, shadeColors);

            g2d.setPaint(lgp);
            g2d.fill(geometry.getOutlineShape());
        }

        // DRAW OUTLINE
        if (outlineColor != null) {
            g2d.setColor(outlineColor);
            if (outlineStroke != null) {
                g2d.setStroke(outlineStroke);
            }

            g2d.draw(geometry.getOutlineShape());
            g2d.setStroke(basicStroke);
        }

        if (button1Visible) {
            // FILL B1
            g2d.setStroke(defaultStroke);

            if (geometry.isRollover1()) {
                if (button1RolloverFillColor != null) {
                    g2d.setColor(button1RolloverFillColor);
                    g2d.fill(geometry.getButton1());
                }
            }
            else {
                if (button1FillColor != null) {
                    g2d.setColor(button1FillColor);
                    g2d.fill(geometry.getButton1());
                }
            }
        }
        if (button2Visible) {
            // FILL B2
            g2d.setStroke(defaultStroke);

            if (geometry.isRollover2()) {
                if (button2RolloverFillColor != null) {
                    g2d.setColor(button2RolloverFillColor);
                    g2d.fill(geometry.getButton2());
                }
            }
            else {
                if (button2FillColor != null) {
                    g2d.setColor(button2FillColor);
                    g2d.fill(geometry.getButton2());
                }
            }
        }

        if (button1Visible) {
            // DRAW B1
            g2d.setStroke(defaultStroke);

            if (geometry.isRollover1()) {
                if (button1RolloverDrawColor != null) {
                    g2d.setColor(button1RolloverDrawColor);
                    g2d.draw(geometry.getButton1());
                }
            }
            else {
                if (button1DrawColor != null) {
                    g2d.setColor(button1DrawColor);
                    g2d.draw(geometry.getButton1());
                }
            }
        }

        if (button2Visible) {
            // DRAW B2
            g2d.setStroke(defaultStroke);

            if (geometry.isRollover2()) {
                if (button2RolloverDrawColor != null) {
                    g2d.setColor(button2RolloverDrawColor);
                    g2d.draw(geometry.getButton2());
                }
            }
            else {
                if (button2DrawColor != null) {
                    g2d.setColor(button2DrawColor);
                    g2d.draw(geometry.getButton2());
                }
            }
        }
        // DEBUG
        // g2d.setColor(Color.BLUE);
        // g2d.draw(geometry.getRect1());
        // g2d.draw(geometry.getRect2());

        // }
        onPaintEnd();
    }

}
