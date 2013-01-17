/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.effect;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.pie.Pie;

/**
 * <code>PieCompoundEffect</code>
 * <p>
 * Allow to make a compound of donut2D effect.
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class PieCompoundEffect extends AbstractPieEffect {

    private AbstractPieEffect[] effects;

    /**
     * create compound effect with the given effects array
     */
    public PieCompoundEffect(AbstractPieEffect... effects) {
        this.effects = effects;
    }

    /**
     * @return the effects
     */
    public AbstractPieEffect[] getEffects() {
        return effects;
    }

    /**
     * @param effects
     *            the effects to set
     */
    public void setEffects(AbstractPieEffect[] effects) {
        this.effects = effects;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.pie.painter.effect.AbstractPieEffect#paintPieEffect(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.pie.Pie)
     */
    @Override
    protected final void paintPieEffect(Graphics2D g2d, Pie pie) {
        if (effects == null) {
            return;
        }

        for (int i = 0; i < effects.length; i++) {
            effects[i].paintPie(g2d, pie);
        }
    }

}
