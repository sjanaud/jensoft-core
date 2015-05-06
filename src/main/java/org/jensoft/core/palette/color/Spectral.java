/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.palette.color;

import java.awt.Color;

/**
 * <code>Spectral</code>
 * 
 * @author Sebastien Janaud
 */
public class Spectral extends ColorPalette {

    public static Color SPECTRAL_RED = new Color(167, 45, 12);
    public static Color SPECTRAL_YELLOW = new Color(201, 174, 5);
    public static Color SPECTRAL_GREEN = new Color(144, 187, 15);
    public static Color SPECTRAL_BLUE1 = new Color(35, 154, 178);
    public static Color SPECTRAL_BLUE2 = new Color(18, 119, 191);
    public static Color SPECTRAL_PURPLE1 = new Color(64, 69, 132);
    public static Color SPECTRAL_PURPLE2 = new Color(132, 15, 101);

    private static Spectral palette;

    private Spectral() {
        registerColor(SPECTRAL_RED);
        registerColor(SPECTRAL_YELLOW);
        registerColor(SPECTRAL_GREEN);
        registerColor(SPECTRAL_BLUE1);
        registerColor(SPECTRAL_BLUE2);
        registerColor(SPECTRAL_PURPLE1);
        registerColor(SPECTRAL_PURPLE2);

    }

    public static ColorPalette getInstance() {
        if (palette == null) {
            palette = new Spectral();
        }
        return palette;
    }

}
