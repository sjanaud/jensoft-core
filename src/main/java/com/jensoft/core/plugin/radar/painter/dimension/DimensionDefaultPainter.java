/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar.painter.dimension;

import java.awt.Graphics2D;

import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.painter.path.MetricsPathDefaultDraw;
import com.jensoft.core.plugin.radar.Radar;
import com.jensoft.core.plugin.radar.RadarDimension;

/**
 * @author Sebosaure
 */
public class DimensionDefaultPainter extends AbstractDimensionPainter {

    private MetricsPathDefaultDraw pathPainter;

    /**
	 * 
	 */
    public DimensionDefaultPainter() {
        pathPainter = new MetricsPathDefaultDraw();
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.radar.painter.dimension.AbstractDimensionPainter#paintDimension(java.awt.Graphics2D, com.jensoft.core.plugin.radar.Radar, com.jensoft.core.plugin.radar.RadarDimension)
     */
    @Override
    protected void paintDimension(Graphics2D g2d, Radar radar,
            RadarDimension radarDimension) {
        // paint path
        GeneralMetricsPath metricsPath = radarDimension.getMetricsPath();

        metricsPath.setWindow2d(radar.getHost().getProjection());
        metricsPath.setFontRenderContext(g2d.getFontRenderContext());
        metricsPath.setSolveGeometryRequest(true);
        if (radarDimension.getLineColor() != null) {
            pathPainter.setPathDrawColor(radarDimension.getLineColor());
        }
        if (radarDimension.getLineColor() == null) {
            pathPainter.setPathDrawColor(radarDimension.getThemeColor());
        }
        pathPainter.setPathStroke(radarDimension.getLineStroke());
        pathPainter.paintPath(g2d, metricsPath);

    }

}
