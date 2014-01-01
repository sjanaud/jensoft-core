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
 * <code>Plugin</code> defines the painting plug in operation
 * 
 * @since 1.O
 * @author Sebastien Janaud
 */
public interface Plugin {

	/**
	 * paint the plugin
	 * @param v2d
	 * @param g2d
	 * @param windowPart
	 */
    public void paint(View2D v2d, Graphics2D g2d, WindowPart windowPart);
}
