/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.point;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * The abstract definition of a delegate that takes the responsibility of
 * painting a window2D Point Layout.<BR>
 */
public abstract class AbstractPointPlugin extends AbstractPlugin {

    public abstract void doPaintPoints(View2D v2d, Graphics2D g2d);

    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
        if (windowPart != WindowPart.Device) {
            return;
        }

        doPaintPoints(v2d, g2d);

    }

}
