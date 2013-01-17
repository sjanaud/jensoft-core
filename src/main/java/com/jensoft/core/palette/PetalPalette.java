/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette;

import java.awt.Color;

/**
 * <code>PetalPalette</code>
 * 
 * @author sebastien janaud
 */
public class PetalPalette extends ColorPalette {

    public static Color PETAL1_LC = new Color(53, 121, 170);
    public static Color PETAL2_LC = new Color(124, 159, 93);
    public static Color PETAL3_LC = new Color(223, 172, 63);
    public static Color PETAL4_LC = new Color(226, 141, 74);
    public static Color PETAL5_LC = new Color(170, 58, 46);
    public static Color PETAL6_LC = new Color(143, 92, 111);
    public static Color PETAL7_LC = new Color(120, 112, 136);
    public static Color PETAL8_LC = new Color(64, 70, 84);

    public static Color PETAL1_HC = new Color(61, 166, 244);
    public static Color PETAL2_HC = new Color(172, 227, 123);
    public static Color PETAL3_HC = new Color(255, 246, 77);
    public static Color PETAL4_HC = new Color(255, 198, 94);
    public static Color PETAL5_HC = new Color(244, 69, 49);
    public static Color PETAL6_HC = new Color(201, 121, 151);
    public static Color PETAL7_HC = new Color(166, 153, 190);
    public static Color PETAL8_HC = new Color(79, 88, 110);

    private static PetalPalette palette;

    private PetalPalette() {
        // registerColor(PETAL1_LC);
        // registerColor(PETAL2_LC);
        // registerColor(PETAL3_LC);
        // registerColor(PETAL4_LC);
        // registerColor(PETAL5_LC);
        // registerColor(PETAL6_LC);
        // registerColor(PETAL7_LC);
        // registerColor(PETAL8_LC);

        registerColor(PETAL1_HC);
        registerColor(PETAL2_HC);
        registerColor(PETAL3_HC);
        registerColor(PETAL4_HC);
        registerColor(PETAL5_HC);
        registerColor(PETAL6_HC);
        registerColor(PETAL7_HC);
        registerColor(PETAL8_HC);
    }

    public static ColorPalette getInstance() {
        if (palette == null) {
            palette = new PetalPalette();
        }
        return palette;
    }
}
