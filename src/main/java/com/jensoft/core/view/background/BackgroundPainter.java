/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view.background;

import java.awt.Graphics2D;

import com.jensoft.core.view.View;

/**
 * @author Sebastien Janaud
 */
public abstract class BackgroundPainter {

    public abstract void paintViewBackground(View view,int viewWidth,int viewHeight, Graphics2D g2d);

}
