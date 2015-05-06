/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter.effect;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.donut2d.Donut2D;

/**
 * <code>AbstractDonut2DEffect</code>
 * <p>
 * Allow to make a compound of donut2D effect.
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class Donut2DCompoundEffect extends AbstractDonut2DEffect {

    private AbstractDonut2DEffect[] effects;

    /**
     * create compound effect with the given effects array
     */
    public Donut2DCompoundEffect(AbstractDonut2DEffect... effects) {
        this.effects = effects;
    }

    /**
     * @return the effects
     */
    public AbstractDonut2DEffect[] getEffects() {
        return effects;
    }

    /**
     * @param effects
     *            the effects to set
     */
    public void setEffects(AbstractDonut2DEffect[] effects) {
        this.effects = effects;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.effect.AbstractDonut2DEffect#paintDonut2DEffect(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D)
     */
    @Override
    protected final void paintDonut2DEffect(Graphics2D g2d, Donut2D donut2D) {
        if (effects == null) {
            return;
        }

        for (int i = 0; i < effects.length; i++) {
            effects[i].paintDonut2D(g2d, donut2D);
        }
    }

}
