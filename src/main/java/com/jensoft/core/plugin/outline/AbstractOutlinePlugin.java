/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.outline;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * The abstract definition of a delegate that takes the responsibility of
 * painting a window2D Outline Layout.<BR>
 */
public abstract class AbstractOutlinePlugin extends AbstractPlugin {

    public abstract void doPaintOutline(View2D v2d, Graphics2D g2d,
            WindowPart windowPart);

    @Override
    public final void paintPlugin(View2D v2d, Graphics2D g2d,
            WindowPart windowPart) {
        doPaintOutline(v2d, g2d, windowPart);
    }

}
