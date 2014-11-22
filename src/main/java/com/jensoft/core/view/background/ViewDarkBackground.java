/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view.background;

import java.awt.BasicStroke;
import java.awt.Color;

import com.jensoft.core.graphics.Shader;

/**
 * <code>ViewDarkBackground</code> defines a dark view background
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 *
 */
public class ViewDarkBackground extends ViewDefaultBackground {

	/**
	 * create dark background
	 */
	public ViewDarkBackground() {
		Shader sb = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
		setShader(sb);
		setOutlineStroke(new BasicStroke(2.5f));
	}

}
