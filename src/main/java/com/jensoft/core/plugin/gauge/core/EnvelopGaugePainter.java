/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.gauge.RadialGauge;

/**
 * EnvelopGaugePainter
 */
public abstract class EnvelopGaugePainter extends AbstractGaugePainter {

    public EnvelopGaugePainter() {
    }

    public abstract void paintEnvelop(Graphics2D g2d, RadialGauge radialGauge);

    @Override
    public final void paintGauge(Graphics2D g2d, RadialGauge radialGauge) {
        paintEnvelop(g2d, radialGauge);
    }

}
