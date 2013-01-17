/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.morphe;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

public class PrimitivePlugin extends AbstractPlugin {

    public PrimitivePlugin() {
        setAntialiasing(Antialiasing.On);
    }

    private List<Primitive> primitives = new ArrayList<Primitive>();

    public void registerPrimitive(Primitive primitive) {
        primitives.add(primitive);
    }

    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (windowPart != WindowPart.Device) {
            return;
        }

        for (Primitive p : primitives) {
            p.setHost(this);
            p.draw(g2d);
        }
    }

}
