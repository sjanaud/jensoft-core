/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.radar;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.graphics.Antialiasing;
import org.jensoft.core.graphics.TextAntialiasing;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;


/**
 * <code>RadarPlugin</code>
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 *
 */
public class RadarPlugin extends AbstractPlugin {

	/**radars*/
    private List<Radar> radars;

    
    /**
     * create radar plugin
     */
    public RadarPlugin() {
        setName(this.getClass().getSimpleName());
        setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        radars = new ArrayList<Radar>();
    }

    /**
     * add radar
     * @param radar
     */
    public void addRadar(Radar radar) {
        radar.setHost(this);
        radars.add(radar);
    }

    /**
     * remove radar
     * @param radar
     */
    public void removeRadar(Radar radar) {
        radar.setHost(null);
        radars.remove(radar);
    }

    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }
        for (Radar radar : radars) {
            radar.solveGeometry();
            if (radar.getPainter() != null) {
                radar.getPainter().paintRadar(g2d, radar);
            }
        }
    }
}
