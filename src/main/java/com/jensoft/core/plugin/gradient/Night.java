/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gradient;

import java.awt.Color;

/**
 * <code>Night</code>
 * 
 * @author sebastien janaud
 */
public class Night extends GradientPlugin {

    private static final Color color1 = new Color(0x202737);
    private static final Color color2 = Color.BLACK;
    private static final float alpha = 1f;

    public Night() {
        super(color1, color2, alpha);
        setPriority(-80);
    }

}
