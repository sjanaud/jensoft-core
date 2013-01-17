/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut2d.painter.effect;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.List;

import com.jensoft.core.graphics.ReflectionRenderer;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;

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
public class Donut2DReflectionEffect extends AbstractDonut2DEffect {

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
    public Donut2DReflectionEffect() {
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

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.donut2d.painter.effect.AbstractDonut2DEffect#paintDonut2DEffect(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.donut2d.Donut2D)
     */
    @Override
    protected final void paintDonut2DEffect(Graphics2D g2d, Donut2D donut2D) {

        try {
            pieImage = getDonut2DImage(donut2D);

            reflection = renderer.createReflection(pieImage);

            Area a = donut2D.getDonut2DArea();
            Rectangle rect = a.getBounds();

            g2d.drawImage(reflection, (int) rect.getX(),
                          (int) (rect.getY() + rect.getHeight()), null);
        }
        catch (Throwable e) {
        }

    }

    /**
     * create donut2D buffered image
     * 
     * @param donut2D
     *            the pie to paint as image
     * @return pie image
     */
    public BufferedImage getDonut2DImage(Donut2D donut2D) {
        // prepare image with original donut2D geometry
        Area pieArea = donut2D.getDonut2DArea();
        Rectangle bound = pieArea.getBounds();
        int width = (int) bound.getWidth();
        int height = (int) bound.getHeight();
        BufferedImage pieImage = new BufferedImage(width, height,
                                                   BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) pieImage.getGraphics();
        donut2D.getHostPlugin().configureGraphics(g2d);
        g2d.translate(-(int) bound.getX(), -(int) bound.getY());

        // paint donut2D
        if (donut2D.getDonut2DFill() != null) {
            donut2D.getDonut2DFill().paintDonut2D(g2d, donut2D);
        }
        if (donut2D.getDonut2DDraw() != null) {
            donut2D.getDonut2DDraw().paintDonut2D(g2d, donut2D);
        }
        if (donut2D.getDonut2DEffect() != null) {
            if (!donut2D.getDonut2DEffect().equals(this)
                    && !(donut2D.getDonut2DEffect() instanceof Donut2DCompoundEffect)) {
                donut2D.getDonut2DEffect().paintDonut2D(g2d, donut2D);
            }
            if (donut2D.getDonut2DEffect() instanceof Donut2DCompoundEffect) {
                Donut2DCompoundEffect compound = (Donut2DCompoundEffect) donut2D
                        .getDonut2DEffect();
                AbstractDonut2DEffect[] effects = compound.getEffects();
                for (int i = 0; i < effects.length; i++) {
                    if (!effects[i].equals(this)) {
                        effects[i].paintDonut2D(g2d, donut2D);
                    }
                }
            }
        }

        // paint section
        List<Donut2DSlice> slices = donut2D.getSlices();
        for (Donut2DSlice slice : slices) {
            if (slice.getSliceFill() != null) {
                slice.getSliceFill().paintDonut2DSlice(g2d, donut2D, slice);
            }
            if (slice.getSliceDraw() != null) {
                slice.getSliceDraw().paintDonut2DSlice(g2d, donut2D, slice);
            }
            if (slice.getSliceEffect() != null) {
                if (!slice.getSliceEffect().equals(this)) {
                    slice.getSliceEffect()
                            .paintDonut2DSlice(g2d, donut2D, slice);
                }

            }

            // for(SectionLabel label : pieSection.getSectionLabels()){
            // label.paintSection(g2d, pie, pieSection);
            // }

        }

        return pieImage;
    }

}
