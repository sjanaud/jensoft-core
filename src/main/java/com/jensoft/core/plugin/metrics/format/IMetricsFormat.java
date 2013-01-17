/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.format;

/**
 * IMetricsFormat format the double model to pretty print convenience
 * 
 * @author sebastien janaud
 */
public interface IMetricsFormat {

    /**
     * format the given double value
     * @param value
     * @return
     */
    public String format(double value);

}
