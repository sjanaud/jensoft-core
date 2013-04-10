/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.background;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.map.layer.AbstractMapLayer;
import com.jensoft.core.map.projection.Map2D;

/**
 * 
 *
 */
public class BackgroundLayer extends AbstractMapLayer {

    private boolean opaque = true;

    private Color background = new Color(237, 234, 226); // default gray

    public BackgroundLayer() {

    }

    public boolean isOpaque() {
        return opaque;
    }

    public void setOpaque(boolean opaque) {
        this.opaque = opaque;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    @Override
    public void doPaintMap(Map2D map2D) {

        int startX = map2D.getStartX();
        int startY = map2D.getStartY();
        int endX = map2D.getEndX();
        int endY = map2D.getEndY();
        int squareTileSize = map2D.getSquareTileSize();

        Graphics2D g2d = map2D.getGraphics2D();
        Rectangle2D mapBackground = new Rectangle2D.Double(startX
                * squareTileSize, startY * squareTileSize, (endX - startX + 1)
                * squareTileSize, (endY - startY + 1) * squareTileSize);

        if (opaque) {
            g2d.setColor(background);
        }
        else {
            g2d.setColor(new Color(0, 0, 0, 0));
        }

        g2d.fill(mapBackground);

    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.map.layer.AbstractMapLayer#doPaint(java.awt.Graphics2D)
     */
    @Override
    public void doPaint(Graphics2D g2d) {

        // int startX = map2D.getStartX();
        // int startY = map2D.getStartY();
        // int endX = map2D.getEndX();
        // int endY = map2D.getEndY();
        // int squareTileSize = map2D.getSquareTileSize();
        //
        // Graphics2D g2d = map2D.getGraphics2D();
        // Rectangle2D mapBackground = new Rectangle2D.Double(startX
        // * squareTileSize, startY * squareTileSize, (endX - startX + 1)
        // * squareTileSize, (endY - startY + 1) * squareTileSize);
        //
        // if (opaque)
        // g2d.setColor(background);
        // else
        // g2d.setColor(new Color(0, 0, 0, 0));
        //
        // g2d.fill(mapBackground);

    }

}
