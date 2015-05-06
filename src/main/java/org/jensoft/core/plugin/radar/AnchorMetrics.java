/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar;

/**
 * <code>AnchorMetrics</code> defines the anchor for a given metrics on a radar dimension
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 */
public final class AnchorMetrics extends RadarMetrics {

    /**
     * create anchor metrics not visible by Default
     */
    public AnchorMetrics() {
        super(RadarMetricsNature.AnchorMetrics);
        setVisible(false);
    }

}
