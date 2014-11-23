/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.marker.marker;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import com.jensoft.core.view.View;

/**
 * <code>MarkerLabel</code>
 */
public class MarkerLabel extends AbstractMarker {

    private JLabel jlabel;
    private String label;

    /**
     * create default target marker
     */
    public MarkerLabel(String label) {
        this.label = label;
        jlabel = new JLabel();
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.marker.marker.AbstractMarker#paintMarker(com.jensoft.core.view.View, java.awt.Graphics2D)
     */
    @Override
    public final void paintMarker(View view, Graphics2D g2d) {
        view.getDevice2D().remove(jlabel);
        jlabel.setText(label);
        jlabel.setForeground(Color.RED);
        jlabel.setBounds((int) getMarkerPoint().getX(), (int) getMarkerPoint()
                .getY(), (int) jlabel.getPreferredSize().getWidth(),
                         (int) jlabel.getPreferredSize().getHeight());
        view.getDevice2D().add(jlabel);

    }

}
