/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette;

import java.awt.Color;

/**
 * <code>TangoPalette</code>
 * 
 * @author Sebastien Janaud
 */
public class TangoPalette extends ColorPalette {

    public static Color BUTTER1 = new Color(249, 235, 113);
    public static Color BUTTER2 = new Color(236, 216, 59);
    public static Color BUTTER3 = new Color(199, 174, 47);

    public static Color ORANGE1 = new Color(240, 187, 91);
    public static Color ORANGE2 = new Color(231, 143, 45);
    public static Color ORANGE3 = new Color(191, 118, 41);

    public static Color CHOCOLATE1 = new Color(229, 197, 136);
    public static Color CHOCOLATE2 = new Color(193, 145, 47);
    public static Color CHOCOLATE3 = new Color(148, 109, 30);

    public static Color CHAMELEON1 = new Color(176, 224, 88);
    public static Color CHAMELEON2 = new Color(156, 210, 62);
    public static Color CHAMELEON3 = new Color(121, 163, 39);

    public static Color SKYBLUE1 = new Color(143, 177, 214);
    public static Color SKYBLUE2 = new Color(83, 122, 177);
    public static Color SKYBLUE3 = new Color(58, 95, 150);

    public static Color PLUM1 = new Color(179, 150, 181);
    public static Color PLUM2 = new Color(130, 104, 141);
    public static Color PLUM3 = new Color(102, 76, 121);

    public static Color SCARLETRED1 = new Color(216, 79, 63);
    public static Color SCARLETRED2 = new Color(190, 50, 27);
    public static Color SCARLETRED3 = new Color(161, 40, 21);

    public static Color ALUMINIUM1 = new Color(241, 241, 241);
    public static Color ALUMINIUM2 = new Color(221, 222, 216);
    public static Color ALUMINIUM3 = new Color(199, 200, 195);
    public static Color ALUMINIUM4 = new Color(154, 155, 150);
    public static Color ALUMINIUM5 = new Color(104, 106, 103);
    public static Color ALUMINIUM6 = new Color(62, 67, 71);

    private static TangoPalette palette;

    private TangoPalette() {
        registerColor(BUTTER1);
        registerColor(BUTTER2);
        registerColor(BUTTER3);

        registerColor(ORANGE1);
        registerColor(ORANGE2);
        registerColor(ORANGE3);

        registerColor(CHOCOLATE1);
        registerColor(CHOCOLATE2);
        registerColor(CHOCOLATE3);

        registerColor(CHAMELEON1);
        registerColor(CHAMELEON2);
        registerColor(CHAMELEON3);

        registerColor(SKYBLUE1);
        registerColor(SKYBLUE2);
        registerColor(SKYBLUE3);

        registerColor(PLUM1);
        registerColor(PLUM2);
        registerColor(PLUM3);

        registerColor(SCARLETRED1);
        registerColor(SCARLETRED2);
        registerColor(SCARLETRED3);

        registerColor(ALUMINIUM1);
        registerColor(ALUMINIUM2);
        registerColor(ALUMINIUM3);
        registerColor(ALUMINIUM4);
        registerColor(ALUMINIUM5);
        registerColor(ALUMINIUM6);

    }

    public static ColorPalette getInstance() {
        if (palette == null) {
            palette = new TangoPalette();
        }
        return palette;
    }

}
