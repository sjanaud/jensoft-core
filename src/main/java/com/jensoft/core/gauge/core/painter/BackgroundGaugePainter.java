/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.core.painter;

import java.awt.Graphics2D;

import com.jensoft.core.gauge.core.RadialGauge;

public abstract class BackgroundGaugePainter extends AbstractGaugePainter {

    public abstract void paintBackground(Graphics2D g2d, RadialGauge radialGauge);

    @Override
    public final void paintGauge(Graphics2D g2d, RadialGauge radialGauge) {
        paintBackground(g2d, radialGauge);

    }

}
