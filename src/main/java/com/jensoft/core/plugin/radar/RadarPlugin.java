/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.radar;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

public class RadarPlugin extends AbstractPlugin {

    private List<Radar> radars;

    public RadarPlugin() {
        setName(this.getClass().getSimpleName());
        setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        radars = new ArrayList<Radar>();
    }

    public void addRadar(Radar radar) {
        radar.setHost(this);
        radars.add(radar);
    }

    public void removeRadar(Radar radar) {
        radar.setHost(null);
        radars.remove(radar);
    }

    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
        if (windowPart != WindowPart.Device) {
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
