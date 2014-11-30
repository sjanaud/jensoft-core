/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette.color;

import java.awt.Color;

/**
 * <code>NanoChromatique</code>
 * 
 * @author sebastien janaud
 */
public class NanoChromatique extends ColorPalette {

    public static Color LIGHT_GRAY = new Color(228, 228, 228);
    public static Color DARK_GRAY = new Color(101, 99, 102);
    public static Color PURPLE = new Color(120, 92, 195);
    public static Color BLUE = new Color(8, 148, 207);
    public static Color GREEN = new Color(129, 255, 55);
    public static Color YELLOW = new Color(240, 205, 1);
    public static Color ORANGE = new Color(244, 145, 26);
    public static Color RED = new Color(230, 21, 50);
    public static Color PINK = new Color(237, 54, 162);

    private static NanoChromatique palette;

    private NanoChromatique() {

        registerColor(LIGHT_GRAY);
        registerColor(DARK_GRAY);
        registerColor(PURPLE);
        registerColor(BLUE);
        registerColor(GREEN);
        registerColor(YELLOW);
        registerColor(ORANGE);
        registerColor(RED);
        registerColor(PINK);

    }

    public static ColorPalette getInstance() {
        if (palette == null) {
            palette = new NanoChromatique();
        }
        return palette;
    }
}
