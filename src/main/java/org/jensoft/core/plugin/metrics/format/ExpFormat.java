/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.metrics.format;

import java.text.DecimalFormat;

public class ExpFormat implements IMetricsFormat {

    @Override
    public String format(double d) {
        DecimalFormat dc = new DecimalFormat("0.###E0");
        return dc.format(d);
    }

}
