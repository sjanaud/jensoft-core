/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.morphe;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.AbstractPlugin;

public abstract class Primitive {

    public enum PrimitiveNature {
        USER, DEVICE
    }

    private PrimitiveNature nature;

    public PrimitiveNature getNature() {
        return nature;
    }

    public void setNature(PrimitiveNature nature) {
        this.nature = nature;
    }

    private AbstractPlugin host;

    public AbstractPlugin getHost() {
        return host;
    }

    public void setHost(AbstractPlugin host) {
        this.host = host;
    }

    public abstract void draw(Graphics2D g2d);

}
