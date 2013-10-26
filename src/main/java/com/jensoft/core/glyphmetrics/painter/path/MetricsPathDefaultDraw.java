/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics.painter.path;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;

import com.jensoft.core.glyphmetrics.AbstractMetricsPath;
import com.jensoft.core.glyphmetrics.painter.AbstractPathPainter;

/**
 * default draw for metrics path
 * 
 * @author Sebastien Janaud
 */
public class MetricsPathDefaultDraw extends AbstractPathPainter {

    /** path color */
    private Color pathDrawColor;

    /** path stroke */
    private Stroke pathStroke;

    /**
     * create default path draw
     */
    public MetricsPathDefaultDraw() {
        pathDrawColor = Color.BLACK;
    }

    /**
     * create default path draw with specified color
     * 
     * @param pathDrawColor
     */
    public MetricsPathDefaultDraw(Color pathDrawColor) {
        this.pathDrawColor = pathDrawColor;
    }

    /**
     * create default path draw with specified path color and path stroke
     * 
     * @param pathDrawColor
     * @param pathStroke
     */
    public MetricsPathDefaultDraw(Color pathDrawColor, Stroke pathStroke) {
        super();
        this.pathDrawColor = pathDrawColor;
        this.pathStroke = pathStroke;
    }

    /**
     * get path draw color
     * 
     * @return path draw color
     */
    public Color getPathDrawColor() {
        return pathDrawColor;
    }

    /**
     * set path draw color
     * 
     * @param pathDrawColor
     *            the path draw color to set
     */
    public void setPathDrawColor(Color pathDrawColor) {
        this.pathDrawColor = pathDrawColor;
    }

    /**
     * get path stroke
     * 
     * @return path stroke
     */
    public Stroke getPathStroke() {
        return pathStroke;
    }

    /**
     * set path stroke
     * 
     * @param pathStroke
     *            the path stroke
     */
    public void setPathStroke(Stroke pathStroke) {
        this.pathStroke = pathStroke;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.glyphmetrics.painter.AbstractPathPainter#paintPath(java.awt.Graphics2D, com.jensoft.core.glyphmetrics.AbstractMetricsPath)
     */
    @Override
    public void paintPath(Graphics2D g2d, AbstractMetricsPath metricsPath) {

        g2d.setStroke(new BasicStroke(1f));
        g2d.setColor(pathDrawColor);

        if (getPathStroke() != null) {
            g2d.setStroke(getPathStroke());
        }

        Shape pathShape = metricsPath.getOrCreateGeometry().getPath();
        g2d.draw(pathShape);
    }

}
