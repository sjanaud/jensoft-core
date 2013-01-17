/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.linesymbol.core;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.plugin.linesymbol.LineSymbolPlugin.LineNature;
import com.jensoft.core.window.Window2D;

/**
 * Line symbol geometry
 */
public class LineSymbolGeometry {

    /** the line symbol */
    private LineSymbolComponent lineSymbolComponent;

    /**
     * Constructor, create new line symbol geometry for the specified line
     * symbol
     * 
     * @param lineSymbolComponent
     */
    public LineSymbolGeometry(LineSymbolComponent lineSymbolComponent) {
        this.lineSymbolComponent = lineSymbolComponent;
    }

    /**
     * get the line symbol component for this geometry
     * 
     * @return the line symbol component
     */
    public LineSymbolComponent getLineSymbolComponent() {
        return lineSymbolComponent;
    }

    /**
     * set the line symbol component to this geometry
     * 
     * @param lineSymbolComponent
     */
    public void setLineSymbolComponent(LineSymbolComponent lineSymbolComponent) {
        this.lineSymbolComponent = lineSymbolComponent;
    }

    /**
     * get bounds for this geometry
     * 
     * @return the bound for the specified line symbol component
     */
    public Rectangle2D getBounds() {
        Rectangle2D bound;
        Window2D w2d = lineSymbolComponent.getHost().getWindow2D();
        if (lineSymbolComponent.getLineNature() == LineNature.LineX) {
            double y = lineSymbolComponent.getLocation();
            double thickness = lineSymbolComponent.getThickness();

            bound = new Rectangle2D.Double(0, w2d.getDevice2D()
                    .getDeviceHeight() - y - thickness, w2d.getDevice2D()
                    .getDeviceWidth() - 1, thickness);
            return bound;
        }
        else if (lineSymbolComponent.getLineNature() == LineNature.LineY) {
            double x = lineSymbolComponent.getLocation();
            double thickness = lineSymbolComponent.getThickness();

            bound = new Rectangle2D.Double(x, 0, x + thickness, w2d
                    .getDevice2D().getDeviceHeight() - 1);
            return bound;
        }
        return null;
    }

    /**
     * get the base line for this geometry
     * 
     * @return the base line for the specified line symbol component
     */
    public Line2D getBaseLine() {
        Window2D w2d = lineSymbolComponent.getHost().getWindow2D();
        Line2D baseLine;
        if (lineSymbolComponent.getLineNature() == LineNature.LineX) {
            double y = lineSymbolComponent.getLocation();
            double thickness = lineSymbolComponent.getThickness();

            baseLine = new Line2D.Double(0, w2d.getDevice2D().getDeviceHeight()
                    - y - thickness / 2,
                                         w2d.getDevice2D().getDeviceWidth() - 1, w2d.getDevice2D()
                                                 .getDeviceHeight() - y - thickness / 2);
            return baseLine;
        }
        else if (lineSymbolComponent.getLineNature() == LineNature.LineY) {
            double x = lineSymbolComponent.getLocation();
            double thickness = lineSymbolComponent.getThickness();

            baseLine = new Line2D.Double(x + thickness / 2, 0, x + thickness
                    / 2, w2d.getDevice2D().getDeviceHeight() - 1);
            return baseLine;
        }
        return null;
    }

}
