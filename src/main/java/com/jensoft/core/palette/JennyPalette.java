/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette;

import java.awt.Color;

/**
 * <code>JennyPalette</code>
 * 
 * @author sebastien janaud
 */
public class JennyPalette extends ColorPalette {

    public static Color JENNY1 = new Color(240, 83, 48);
    public static Color JENNY2 = new Color(212, 79, 84);
    public static Color JENNY3 = new Color(239, 124, 33);
    public static Color JENNY4 = new Color(243, 247, 162);
    public static Color JENNY5 = new Color(91, 203, 131);
    public static Color JENNY6 = new Color(51, 155, 206);
    public static Color JENNY7 = new Color(109, 190, 255);
    public static Color JENNY8 = new Color(70, 115, 218);
    public static Color JENNY9 = new Color(105, 48, 67);
    public static Color JENNY10 = new Color(200, 55, 86);

    private static JennyPalette palette;

    private JennyPalette() {
        registerColor(JENNY1);
        registerColor(JENNY2);
        registerColor(JENNY3);
        registerColor(JENNY4);
        registerColor(JENNY5);
        registerColor(JENNY6);
        registerColor(JENNY7);
        registerColor(JENNY8);
        registerColor(JENNY9);
        registerColor(JENNY10);
    }

    public static ColorPalette getInstance() {
        if (palette == null) {
            palette = new JennyPalette();
        }
        return palette;
    }

}
