/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.effect;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

/**
 * <code>Donut2DLinearEffect</code>
 * 
 * @author Sebastien Janaud
 */
public class Donut2DLinearEffect extends AbstractDonut2DEffect {

    /** start color */
    private Color startColor;

    /** end color */
    private Color endColor;

    /** offset radius */
    private int offsetRadius = 0;

    /** incidence angle degree */
    private int incidenceAngleDegree = 90;

    /** section effect */
    private Donut2DSliceLinearEffect pieSliceLinearEffect;

    /** shade fractions */
    private float[] shadeFractions;

    /** shade colors */
    private Color[] shadeColors;

    /** reload flag */
    private boolean reload = false;

    /** shifting flag */
    private boolean shifting = false;

    /**
     * create default effect
     */
    public Donut2DLinearEffect() {
    }

    /**
     * @param incidenceAngleDegree
     */
    public Donut2DLinearEffect(int incidenceAngleDegree) {
        super();
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * @param incidenceAngleDegree
     */
    public Donut2DLinearEffect(int incidenceAngleDegree, int offsetRadius) {
        super();
        this.incidenceAngleDegree = incidenceAngleDegree;
        this.offsetRadius = offsetRadius;
    }

    /**
     * create effect with the specified given parameters
     * 
     * @param startColor
     *            the start color to set
     * @param endColor
     *            the end color to set
     * @param offsetRadius
     *            the offset radius
     */
    public Donut2DLinearEffect(Color startColor, Color endColor, int offsetRadius) {
        if (offsetRadius < 0) {
            throw new IllegalArgumentException(
                                               "offset radius should be greater than 0");
        }
        this.startColor = startColor;
        this.endColor = endColor;
        this.offsetRadius = offsetRadius;
    }

    /**
     * create effect with the specified given parameters
     * 
     * @param startColor
     *            the start color to set
     * @param endColor
     *            the end color to set
     * @param offsetRadius
     *            the offset radius
     * @param incidenceAngleDegree
     *            the gradient angle degree effect
     */
    public Donut2DLinearEffect(Color startColor, Color endColor, int offsetRadius,
            int incidenceAngleDegree) {
        if (offsetRadius < 0) {
            throw new IllegalArgumentException(
                                               "offset radius should be greater than 0");
        }
        this.startColor = startColor;
        this.endColor = endColor;
        this.offsetRadius = offsetRadius;
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * create effect with the specified given parameters
     * 
     * @param startColor
     *            the start color to set
     * @param endColor
     *            the end color to set
     */
    public Donut2DLinearEffect(Color startColor, Color endColor) {
        this(startColor, endColor, 0);
    }

    /**
     * @return the reload
     */
    public boolean isReload() {
        return reload;
    }

    /**
     * @param reload
     *            the reload to set
     */
    public void setReload(boolean reload) {
        this.reload = reload;
    }

    /**
     * @return the startColor
     */
    public Color getStartColor() {
        return startColor;
    }

    /**
     * @param startColor
     *            the startColor to set
     */
    public void setStartColor(Color startColor) {
        this.startColor = startColor;
        setReload(true);
    }

    /**
     * @return the endColor
     */
    public Color getEndColor() {
        return endColor;
    }

    /**
     * @param endColor
     *            the endColor to set
     */
    public void setEndColor(Color endColor) {
        this.endColor = endColor;
        setReload(true);
    }

    /**
     * @return the offsetRadius
     */
    public int getOffsetRadius() {
        return offsetRadius;
    }

    /**
     * @param offsetRadius
     *            the offsetRadius to set
     */
    public void setOffsetRadius(int offsetRadius) {
        if (offsetRadius < 0) {
            throw new IllegalArgumentException(
                                               "offset radius should be greater than 0");
        }
        this.offsetRadius = offsetRadius;
        setReload(true);
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
     * set the shadow parameters
     * 
     * @param shader
     */
    public void setShader(Shader shader) {
        shadeFractions = shader.getFractions();
        shadeColors = shader.getColors();
    }

    /**
     * @return the incidenceAngleDegree
     */
    public int getIncidenceAngleDegree() {
        return incidenceAngleDegree;
    }

    /**
     * @param incidenceAngleDegree
     *            the incidenceAngleDegree to set
     */
    public void setIncidenceAngleDegree(int incidenceAngleDegree) {
        this.incidenceAngleDegree = incidenceAngleDegree;
        setReload(true);
    }

    /**
     * shift incidence angle degree for the embedded effect in specified donut2D
     * 
     * @param donut2D
     */
    public static void shiftIncidence(Donut2D donut2D) {
        AbstractDonut2DEffect effect = donut2D.getDonut2DEffect();
        if (effect != null && effect instanceof Donut2DLinearEffect) {
            Donut2DShiftIncidence shift = new Donut2DShiftIncidence(donut2D);
            shift.start();
        }
    }

    /**
     * @return the shifting
     */
    public boolean isShifting() {
        return shifting;
    }

    /**
     * @param shifting
     *            the shifting to set
     */
    public void setShifting(boolean shifting) {
        this.shifting = shifting;
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.effect.AbstractDonut2DEffect#paintDonut2DEffect(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D)
     */
    @Override
    public final void paintDonut2DEffect(Graphics2D g2d, Donut2D donut2D) {
        if (pieSliceLinearEffect == null || reload) {
            pieSliceLinearEffect = new Donut2DSliceLinearEffect(startColor, endColor,
                                                                offsetRadius, incidenceAngleDegree);

            if (shadeFractions != null && shadeColors != null) {
                pieSliceLinearEffect.setShader(shadeFractions, shadeColors);
            }
        }

        for (Donut2DSlice slice : donut2D.getSlices()) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, slice.getAlpha()));
            pieSliceLinearEffect.paintDonut2DSlice(g2d, donut2D, slice);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

    }

}

/**
 * shift effect incidence
 * 
 * @author Sebastien Janaud
 */
class Donut2DShiftIncidence extends Thread {

    private Donut2D donut2D;
    private Donut2DLinearEffect effect1;

    public Donut2DShiftIncidence(Donut2D donut2D) {
        this.donut2D = donut2D;
    }

    @Override
    public void run() {

        try {
            effect1 = (Donut2DLinearEffect) donut2D.getDonut2DEffect();
            if (effect1.isShifting()) {
                throw new InterruptedException("effect is already shifting.");
            }
            donut2D.getHostPlugin().getProjection().getView2D().repaintDevice();
            while (true) {

                for (int i = 0; i < 90; i++) {
                    effect1.setIncidenceAngleDegree(i * 4);
                    Thread.sleep(20);
                    donut2D.getHostPlugin().getProjection().getView2D()
                            .repaintDevice();
                }
            }

        }
        catch (InterruptedException e) {
            effect1.setShifting(true);
            Thread.currentThread().interrupt();
        }
        finally {
            effect1.setShifting(true);
        }

    }
}
