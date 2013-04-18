/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.List;

import com.jensoft.core.graphics.ReflectionRenderer;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PieSlice;

/**
 * /**
 * <p>
 * A pie reflection effect create a reflection of a given pie.
 * </p>
 * <h2>Reflection Properties</h2>
 * <p>
 * A reflection is defined by three properties:
 * <ul>
 * <li><i>opacity</i>: the opacity of the reflection. You will usually change this valued according to the background
 * color.</li>
 * <li><i>length</i>: the length of the reflection. The length is a fraction of the height of the source image.</li>
 * <li><i>blur enabled</i>: perfect reflections are hardly natural. You can blur the reflection to make it look a bit
 * more natural.</li>
 * </ul>
 * You can set these properties using the provided mutaters or the appropriate constructor. Here are two ways of
 * creating a blurred reflection, with an opacity of 50% and a length of 30% the height of the original image:
 * 
 * <pre>
 * PieReflectionEffect reflection = new PieReflectionEffect();
 * reflection.setLenght(0.4f);
 * reflection.setOpacity(0.35f);
 * rreflection.setBlurEnabled(true);
 * 
 * pie.setPieEffect(reflection);
 * </pre>
 * 
 * The default constructor provides the following default values:
 * <ul>
 * <li><i>opacity</i>: 35%</li>
 * <li><i>length</i>: 40%</li>
 * <li><i>blur enabled</i>: false</li>
 * </ul>
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class PieReflectionEffect extends AbstractPieEffect {

    /** opacity */
    private float opacity = 0.5f;

    /** length */
    private float length = 0.3f;

    /** blur reflection */
    private boolean blurEnabled = true;

    /** pie image */
    private BufferedImage pieImage;

    /** private reflection renderer */
    private ReflectionRenderer renderer;

    /** pie reflection */
    private BufferedImage reflection;

    /**
     * create Pie Reflection Effect
     */
    public PieReflectionEffect() {
        renderer = new ReflectionRenderer();
        renderer.setOpacity(opacity);
        renderer.setLength(length);
        renderer.setBlurEnabled(blurEnabled);
    }

    /**
     * @return the opacity
     */
    public float getOpacity() {
        return opacity;
    }

    /**
     * opacity [0,1]
     * @param opacity
     *            the opacity to set
     */
    public void setOpacity(float opacity) {
        if (opacity < 0.0f) {
            opacity = 0.0f;
        }
        else if (opacity > 1.0f) {
            opacity = 1.0f;
        }
        this.opacity = opacity;
        renderer.setOpacity(opacity);
    }

    /**
     * @return the length
     */
    public float getLength() {
        return length;
    }

    /**
     * length [0,1]
     * @param lenght
     *            the length to set
     */
    public void setLength(float length) {

        if (length < 0.0f) {
            length = 0.0f;
        }
        else if (length > 1.0f) {
            length = 1.0f;
        }

        this.length = length;
        renderer.setLength(length);
    }

    /**
     * @return the blurEnabled
     */
    public boolean isBlurEnabled() {
        return blurEnabled;
    }

    /**
     * @param blurEnabled
     *            the blurEnabled to set
     */
    public void setBlurEnabled(boolean blurEnabled) {
        this.blurEnabled = blurEnabled;
        renderer.setBlurEnabled(blurEnabled);
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.effect.AbstractPieEffect#paintPieEffect(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie)
     */
    @Override
    protected final void paintPieEffect(Graphics2D g2d, Pie pie) {

        try {
            pieImage = getPieImage(pie);

            reflection = renderer.createReflection(pieImage);

            Area a = pie.getPieArea();
            Rectangle rect = a.getBounds();

            g2d.drawImage(reflection, (int) rect.getX(),
                          (int) (rect.getY() + rect.getHeight()), null);
        }
        catch (Throwable e) {
        }

    }

    /**
     * create pie buffered image
     * 
     * @param pie
     *            the pie to paint as image
     * @return pie image
     */
    public BufferedImage getPieImage(Pie pie) {
        // prepare image with original pie geometry
        Area pieArea = pie.getPieArea();
        Rectangle bound = pieArea.getBounds();
        int width = (int) bound.getWidth();
        int height = (int) bound.getHeight();
        BufferedImage pieImage = new BufferedImage(width, height,
                                                   BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) pieImage.getGraphics();
        pie.getHostPlugin().configureGraphics(g2d);
        g2d.translate(-(int) bound.getX(), -(int) bound.getY());

        // paint pie
        if (pie.getPieFill() != null) {
            pie.getPieFill().paintPie(g2d, pie);
        }
        if (pie.getPieDraw() != null) {
            pie.getPieDraw().paintPie(g2d, pie);
        }
        if (pie.getPieEffect() != null) {
            if (!pie.getPieEffect().equals(this)
                    && !(pie.getPieEffect() instanceof PieCompoundEffect)) {
                pie.getPieEffect().paintPie(g2d, pie);
            }
            if (pie.getPieEffect() instanceof PieCompoundEffect) {
                PieCompoundEffect compound = (PieCompoundEffect) pie
                        .getPieEffect();
                AbstractPieEffect[] effects = compound.getEffects();
                for (int i = 0; i < effects.length; i++) {
                    if (!effects[i].equals(this)) {
                        effects[i].paintPie(g2d, pie);
                    }
                }
            }
        }

        // paint section
        List<PieSlice> slices = pie.getSlices();
        for (PieSlice slice : slices) {
            if (slice.getSliceFill() != null) {
                slice.getSliceFill().paintPieSlice(g2d, pie, slice);
            }
            if (slice.getSliceDraw() != null) {
                slice.getSliceDraw().paintPieSlice(g2d, pie, slice);
            }
            if (slice.getSliceEffect() != null) {
                if (!slice.getSliceEffect().equals(this)) {
                    slice.getSliceEffect()
                            .paintPieSlice(g2d, pie, slice);
                }

            }

            // maybe use label in reflection !
            // for(SectionLabel label : pieSection.getSectionLabels()){
            // label.paintSection(g2d, pie, pieSection);
            // }

        }

        return pieImage;
    }

}
