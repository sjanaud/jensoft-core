/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin;

import java.awt.Graphics2D;

import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * tag interface for plugin in
 * 
 * @author Sebastien Janaud
 */
public interface Plugin {

    public void paint(View2D v2d, Graphics2D g2d, WindowPart windowPart);
}
