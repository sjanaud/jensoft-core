/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.metrics.format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat implements IMetricsFormat {

    public static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Override
    public String format(double d) {

        return sdf.format(new Date((long) d));
    }

}
