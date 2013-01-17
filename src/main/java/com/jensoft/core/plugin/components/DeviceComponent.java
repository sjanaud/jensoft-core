/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.components;

import java.awt.geom.Point2D;

import javax.swing.JComponent;

public interface DeviceComponent {

    public Point2D getUserLocation();

    public JComponent getComponent();

}
