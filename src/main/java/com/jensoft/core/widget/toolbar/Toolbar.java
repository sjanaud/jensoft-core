/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.widget.toolbar;

import java.awt.Graphics2D;

import com.jensoft.core.view.View2D;
import com.jensoft.core.widget.Widget;

/**
 * @author Sebastien Janaud
 */
public class Toolbar extends Widget {

    public Toolbar() {
        // TODO Auto-generated constructor stub
    }

    public Toolbar(String id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    public Toolbar(String id, double width, double height, int xIndex,
            int yIndex) {
        super(id, width, height, xIndex, yIndex);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void paintWidget(View2D v2d, Graphics2D g2d) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isCompatiblePlugin() {
        // TODO Auto-generated method stub
        return false;
    }

}
