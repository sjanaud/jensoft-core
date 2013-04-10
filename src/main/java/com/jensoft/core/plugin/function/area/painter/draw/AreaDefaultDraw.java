/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.area.painter.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import com.jensoft.core.plugin.function.area.AreaFunction;

/**
 * <code>AreaDefaultDraw</code> defines 4 draw properties<br>
 * <ul>
 * <li>top area function draw color</li>
 * <li>top area function draw stroke</li>
 * <li>base area function draw color</li>
 * <li>base area function draw stroke</li>
 * </ul>
 * 
 * @author Sebastien Janaud
 * @since 1.0
 */
public class AreaDefaultDraw extends AbstractAreaDraw {

    /** curve color */
    private Color curveColor;

    /** curve stroke */
    private Stroke curveStroke = new BasicStroke();

    /** base color */
    private Color baseColor;

    /** base stroke */
    private Stroke baseStroke = new BasicStroke();

    /**
     * create default draw
     */
    public AreaDefaultDraw() {
    }

    /**
     * create area draw with specified parameter
     * 
     * @param outlineColor
     */
    public AreaDefaultDraw(Color outlineColor) {
        curveColor = outlineColor;
    }

    /**
     * create area draw with specified parameter
     * 
     * @param outlineColor
     * @param outlineStroke
     */
    public AreaDefaultDraw(Color outlineColor, Stroke outlineStroke) {
        super();
        curveColor = outlineColor;
        curveStroke = outlineStroke;
    }

    /**
     * create area draw with specified parameter
     * 
     * @param curveColor
     * @param baseColor
     */
    public AreaDefaultDraw(Color curveColor, Color baseColor) {
        super();
        this.curveColor = curveColor;
        this.baseColor = baseColor;
    }

    /**
     * create area draw with specified parameter
     * 
     * @param curveColor
     * @param curveStroke
     * @param baseColor
     * @param baseStroke
     */
    public AreaDefaultDraw(Color curveColor, Stroke curveStroke,
            Color baseColor, Stroke baseStroke) {
        super();
        this.curveColor = curveColor;
        this.curveStroke = curveStroke;
        this.baseColor = baseColor;
        this.baseStroke = baseStroke;
    }

    /**
     * @return the curveColor
     */
    public Color getCurveColor() {
        return curveColor;
    }

    /**
     * @param curveColor
     *            the curveColor to set
     */
    public void setCurveColor(Color curveColor) {
        this.curveColor = curveColor;
    }

    /**
     * @return the curveStroke
     */
    public Stroke getCurveStroke() {
        return curveStroke;
    }

    /**
     * @param curveStroke
     *            the curveStroke to set
     */
    public void setCurveStroke(Stroke curveStroke) {
        this.curveStroke = curveStroke;
    }

    /**
     * @return the baseColor
     */
    public Color getBaseColor() {
        return baseColor;
    }

    /**
     * @param baseColor
     *            the baseColor to set
     */
    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    }

    /**
     * @return the baseStroke
     */
    public Stroke getBaseStroke() {
        return baseStroke;
    }

    /**
     * @param baseStroke
     *            the baseStroke to set
     */
    public void setBaseStroke(Stroke baseStroke) {
        this.baseStroke = baseStroke;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.function.area.painter.draw.AbstractAreaDraw#paintAreaDraw(java.awt.Graphics2D, com.jensoft.core.plugin.function.area.AreaFunction)
     */
    @Override
    public void paintAreaDraw(Graphics2D g2d, AreaFunction areaCurve) {

        if (areaCurve.getPathFunction() == null || areaCurve.getPathFunction().getOrCreateGeometry() == null
                || areaCurve.getPathFunction().getOrCreateGeometry().getPath() == null) {
            return;
        }

        g2d.setStroke(new BasicStroke(1f));

        if (getCurveColor() != null) {
            g2d.setColor(getCurveColor());
        }
        else if (areaCurve.getThemeColor() != null) {
            g2d.setColor(areaCurve.getThemeColor());
        }
        else if (areaCurve.getHost().getWindow2D().getThemeColor() != null) {
            g2d.setColor(areaCurve.getHost().getWindow2D().getThemeColor());
        }

        if (getCurveStroke() != null) {
            g2d.setStroke(getCurveStroke());
        }

        g2d.draw(areaCurve.getPathFunction().getOrCreateGeometry().getPath());

        if (getBaseColor() != null) {
            g2d.setColor(getBaseColor());
        }
        else if (areaCurve.getThemeColor() != null) {
            g2d.setColor(areaCurve.getThemeColor());
        }
        else if (areaCurve.getHost().getWindow2D().getThemeColor() != null) {
            g2d.setColor(areaCurve.getHost().getWindow2D().getThemeColor());
        }

        if (getBaseStroke() != null) {
            g2d.setStroke(getBaseStroke());
        }

        g2d.draw(areaCurve.getBaseLine());
    }

}
