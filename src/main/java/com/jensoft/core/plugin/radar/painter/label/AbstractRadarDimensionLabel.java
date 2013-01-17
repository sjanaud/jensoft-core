/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar.painter.label;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.radar.Radar;
import com.jensoft.core.plugin.radar.RadarDimension;
import com.jensoft.core.plugin.radar.painter.AbstractRadarDimensionPainter;

/**
 * AbstractDimensionLabelPainter
 */
public abstract class AbstractRadarDimensionLabel extends
        AbstractRadarDimensionPainter {

    /**
     * defines a painting style for the label
     * 
     * @author Sebastien Janaud
     */
    public enum Style {
        Nothing, Stroke, Fill, Both;

        public static Style parseStyle(String style) {
            if (style.equalsIgnoreCase("Nothing")) {
                return Style.Nothing;
            }
            if (style.equalsIgnoreCase("Stroke")) {
                return Style.Stroke;
            }
            if (style.equalsIgnoreCase("Fill")) {
                return Style.Fill;
            }
            if (style.equalsIgnoreCase("Both")) {
                return Style.Both;
            }
            return null;
        }
    }

    /**
     * paint dimension label
     * 
     * @param g2d
     * @param radar
     */
    protected abstract void paintDimensionLabel(Graphics2D g2d, Radar radar,
            RadarDimension radarDimension);

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.radar.painter.AbstractRadarDimensionPainter
     * #paintRadarDimension(java.awt.Graphics2D,
     * com.jensoft.sw2d.core.plugin.radar.Radar,
     * com.jensoft.sw2d.core.plugin.radar.RadarDimension)
     */
    @Override
    public final void paintRadarDimension(Graphics2D g2d, Radar radar,
            RadarDimension radarDimension) {
        paintDimensionLabel(g2d, radar, radarDimension);
    }

}
