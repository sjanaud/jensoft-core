/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.core.painter;

import com.jensoft.core.gauge.core.RadialGauge;

public abstract class AbstractGaugePainter implements GaugePainter {

    private RadialGauge gauge;

    public RadialGauge getGauge() {
        return gauge;
    }

    public void setGauge(RadialGauge gauge) {
        this.gauge = gauge;
    }

}
