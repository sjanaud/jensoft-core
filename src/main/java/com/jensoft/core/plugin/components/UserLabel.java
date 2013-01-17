/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.components;

import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class UserLabel extends JLabel implements DeviceComponent {

    private Point2D userLocation;

    public UserLabel(Point2D userLocation) {
        this.userLocation = userLocation;
    }

    public UserLabel() {
    }

    public int getUserX() {
        return (int) userLocation.getX();
    }

    public void setUserX(int userX) {
        userLocation.setLocation(userX, getUserY());
    }

    public int getUserY() {
        return (int) userLocation.getY();
    }

    public void setUserY(int userY) {
        userLocation.setLocation(getUserX(), userY);
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
