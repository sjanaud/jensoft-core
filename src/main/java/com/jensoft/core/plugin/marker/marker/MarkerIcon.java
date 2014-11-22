/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.marker.marker;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import com.jensoft.core.view.View;

/**
 * <code>MarkerIcon</code>
 */
public class MarkerIcon extends AbstractMarker {

    private ImageIcon icon;

    /**
     * create default marker icon
     */
    public MarkerIcon() {
    }

    /**
     * create marker with given image icon
     * @param icon
     */
    public MarkerIcon(ImageIcon icon) {
        super();
        this.icon = icon;
    }

    /**
     * @return the icon
     */
    public ImageIcon getIcon() {
        return icon;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.marker.marker.AbstractMarker#paintMarker(com.jensoft.core.view.View2D, java.awt.Graphics2D)
     */
    @Override
    public final void paintMarker(View view2d, Graphics2D g2d) {

        if (icon != null) {
            g2d.drawImage(icon.getImage(),
                          (int) (getMarkerPoint().getX() - icon.getIconWidth() / 2),
                          (int) (getMarkerPoint().getY() - icon.getIconHeight()),
                          null);
        }

    }

}
