/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import com.jensoft.core.plugin.gauge.RadialGauge;

public abstract class AbstractGaugePainter implements GaugePainter {

    private RadialGauge gauge;

    public RadialGauge getGauge() {
        return gauge;
    }

    public void setGauge(RadialGauge gauge) {
        this.gauge = gauge;
    }

}
