/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray.painter;

import java.awt.Graphics2D;

import com.jensoft.core.plugin.ray.Ray;
import com.jensoft.core.window.WindowPart;

/**
 * base interface to define a operation ray painting
 */
public interface RayPainter {

	/**
	 * paint ray
	 * 
	 * @param g2d
	 * @param ray
	 * @param windowPart
	 */
	public void paintRay(Graphics2D g2d, Ray ray, WindowPart windowPart);

}
