/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.components;

import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JComponent;

public class UserButton extends JButton implements DeviceComponent {

    private Point2D userLocation;

    public UserButton(Point2D userLocation) {
        this.userLocation = userLocation;
    }

    @Override
    public Point2D getUserLocation() {
        return userLocation;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

}
