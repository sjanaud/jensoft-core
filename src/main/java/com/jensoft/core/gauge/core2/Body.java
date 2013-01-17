/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.core2;

import java.awt.geom.Point2D;

import com.jensoft.core.gauge.core.painter.BackgroundGaugePainter;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;

public class Body {

    private GeneralMetricsPath bodyMetrics;
    private Point2D anchor;
    private BackgroundGaugePainter backgroundPainter;

    public Body() {
        bodyMetrics = new GeneralMetricsPath();
    }

}
