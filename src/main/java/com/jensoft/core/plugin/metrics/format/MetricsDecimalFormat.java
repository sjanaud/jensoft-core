/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.format;

import java.text.DecimalFormat;

/**
 * <code>MetricsDecimalFormat</code>
 * 
 * @see DecimalFormat
 * @author sebastien janaud
 */
public class MetricsDecimalFormat implements IMetricsFormat {

    private DecimalFormat decimalFormater = new DecimalFormat();

    /**
     * create default metrics decimal format
     */
    public MetricsDecimalFormat() {
    }

    /**
     * create decimal format with the given {@link DecimalFormat}
     * 
     * @param decimalFormater
     *            the formater to set
     */
    public MetricsDecimalFormat(DecimalFormat decimalFormater) {
        super();
        this.decimalFormater = decimalFormater;
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.format.IMetricsFormat#format(double)
     */
    @Override
    public String format(double d) {
        return decimalFormater.format(d);
    }

}
