/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * <code>PieLinearEffect</code>
 * 
 * @author Sebastien Janaud
 */
public class PieLinearEffect extends AbstractPieEffect {

    /** start color */
    private Color startColor;

    /** end color */
    private Color endColor;

    /** offset radius */
    private int offsetRadius = 10;

    /** incidence angle degree */
    private int incidenceAngleDegree = 90;

    /** section effect */
    private PieSliceLinearEffect pieSliceLinearEffect;

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
    public PieLinearEffect() {
    }

    /**
     * @param incidenceAngleDegree
     */
    public PieLinearEffect(int incidenceAngleDegree) {
        super();
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * @param incidenceAngleDegree
     */
    public PieLinearEffect(int incidenceAngleDegree, int offsetRadius) {
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
    public PieLinearEffect(Color startColor, Color endColor, int offsetRadius) {
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
    public PieLinearEffect(Color startColor, Color endColor, int offsetRadius,
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
    public PieLinearEffect(Color startColor, Color endColor) {
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
     * shift incidence angle degree for the embedded effect in specified pie
     * 
     * @param pie
     */
    public static Effect1ShiftIncidence shiftIncidence(Pie pie) {
        AbstractPieEffect effect = pie.getPieEffect();
        if (effect != null && (effect instanceof PieLinearEffect || effect instanceof PieCompoundEffect)) {
            Effect1ShiftIncidence shift = new Effect1ShiftIncidence(pie);
            shift.start();
            return shift;
        }
        return null;
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

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.pie.painter.effect.AbstractPieEffect#paintPieEffect(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.pie.Pie)
     */
    @Override
    public final void paintPieEffect(Graphics2D g2d, Pie pie) {
        if (pieSliceLinearEffect == null || reload) {
            pieSliceLinearEffect = new PieSliceLinearEffect(startColor, endColor,
                                                            offsetRadius, incidenceAngleDegree);

            if (shadeFractions != null && shadeColors != null) {
                pieSliceLinearEffect.setShader(shadeFractions, shadeColors);
            }
        }

        for (PieSlice slice : pie.getSlices()) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, slice.getAlpha()));
            pieSliceLinearEffect.paintPieSlice(g2d, pie, slice);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

    }

    /**
     * shift effect incidence
     */
    public static class Effect1ShiftIncidence extends Thread {

        private Pie pie;
        private PieLinearEffect effect1;

        public Effect1ShiftIncidence(Pie pie) {
            this.pie = pie;
        }

        @Override
        public void run() {

            try {

                if (pie.getPieEffect() instanceof PieLinearEffect) {
                    effect1 = (PieLinearEffect) pie.getPieEffect();
                    if (effect1.isShifting()) {
                        throw new InterruptedException("effect is already shifting.");
                    }
                    pie.getHostPlugin().getWindow2D().getView2D().repaintDevice();
                    effect1.setShifting(true);
                    while (true) {
                        for (int i = 0; i < 90; i++) {
                            effect1.setIncidenceAngleDegree(i * 4);
                            Thread.sleep(20);
                            pie.getHostPlugin().getWindow2D().getView2D()
                                    .repaintDevice();
                        }
                    }
                }
                else if (pie.getPieEffect() instanceof PieCompoundEffect) {
                    PieCompoundEffect pc = (PieCompoundEffect) pie.getPieEffect();
                    AbstractPieEffect[] effects = pc.getEffects();
                    for (int i = 0; i < effects.length; i++) {
                        if (effects[i] instanceof PieLinearEffect) {
                            System.out.println("ok, effect found in compound");
                            effect1 = (PieLinearEffect) effects[i];
                            if (effect1.isShifting()) {
                                throw new InterruptedException("effect is already shifting.");
                            }
                            pie.getHostPlugin().getWindow2D().getView2D().repaintDevice();
                            while (true) {
                                for (int j = 0; j < 90; j++) {
                                    System.out.println("j");
                                    effect1.setIncidenceAngleDegree(j * 4);
                                    Thread.sleep(20);
                                    pie.getHostPlugin().getWindow2D().getView2D()
                                            .repaintDevice();
                                }
                            }
                        }
                    }
                }

            }
            catch (InterruptedException e) {
                effect1.setShifting(false);
                Thread.currentThread().interrupt();
            }
            finally {
                effect1.setShifting(false);
            }

        }
    }
}
