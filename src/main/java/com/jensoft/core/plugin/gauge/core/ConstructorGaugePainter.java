/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.gauge.RadialGauge;

public abstract class ConstructorGaugePainter extends AbstractGaugePainter {

    public abstract void paintConstructor(Graphics2D g2d,
            RadialGauge radialGauge);

    @Override
    public final void paintGauge(Graphics2D g2d, RadialGauge radialGauge) {
        paintConstructor(g2d, radialGauge);

    }

}
