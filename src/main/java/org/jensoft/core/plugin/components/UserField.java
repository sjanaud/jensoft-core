/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.components;

import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JTextField;

public class UserField extends JTextField implements DeviceComponent {

    private Point2D userLocation;

    public UserField(Point2D userLocation) {
        this.userLocation = userLocation;
        setOpaque(false);
    }

    public UserField() {
        setOpaque(false);
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
