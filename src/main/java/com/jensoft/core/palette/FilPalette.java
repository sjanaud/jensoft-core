/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette;

import java.awt.Color;

/**
 * <code>FilPalette</code>
 * 
 * @author sebastien janaud
 */
public class FilPalette extends ColorPalette {

    public static Color YELLOW1 = new Color(255, 212, 21);
    public static Color YELLOW2 = new Color(251, 226, 102);
    public static Color ORANGE1 = new Color(250, 131, 53);
    public static Color ORANGE2 = new Color(232, 78, 3);
    public static Color CHESTNUT = new Color(134, 33, 9);
    public static Color PURPLE1 = new Color(156, 46, 110);
    public static Color PURPLE2 = new Color(192, 75, 133);
    public static Color FUSHIA = new Color(250, 69, 100);
    public static Color COPPER1 = new Color(219, 122, 116);
    public static Color COPPER2 = new Color(162, 99, 114);
    public static Color COPPER3 = new Color(123, 51, 80);
    public static Color CHERRY = new Color(195, 32, 58);
    public static Color PINK1 = new Color(247, 176, 181);
    public static Color PINK2 = new Color(253, 232, 219);
    public static Color PINK3 = new Color(231, 206, 208);
    public static Color PINK4 = new Color(222, 160, 187);
    public static Color BLUE1 = new Color(48, 62, 148);
    public static Color BLUE2 = new Color(100, 121, 184);
    public static Color BLUE3 = new Color(139, 156, 207);
    public static Color BLUE4 = new Color(173, 176, 200);
    public static Color BLUE5 = new Color(46, 46, 98);
    public static Color BLUE6 = new Color(43, 62, 147);
    public static Color BLUE7 = new Color(94, 140, 221);
    public static Color BLUE8 = new Color(90, 95, 130);
    public static Color BLUE9 = new Color(131, 130, 153);
    public static Color BLUE10 = new Color(91, 107, 163);
    public static Color BLUE11 = new Color(91, 108, 112);
    public static Color BLUE12 = new Color(49, 138, 186);
    public static Color BLUE13 = new Color(82, 155, 196);
    public static Color BLUE14 = new Color(112, 201, 235);
    public static Color BLUE15 = new Color(146, 202, 213);
    public static Color BLUE16 = new Color(182, 211, 215);
    public static Color BLUE17 = new Color(120, 217, 218);
    public static Color GREEN1 = new Color(45, 98, 106);
    public static Color GREEN2 = new Color(41, 132, 106);
    public static Color GREEN3 = new Color(62, 192, 146);
    public static Color GREEN4 = new Color(133, 204, 128);
    public static Color GREEN5 = new Color(163, 211, 150);
    public static Color GREEN6 = new Color(204, 198, 160);
    public static Color BROWN1 = new Color(68, 39, 21);
    public static Color BROWN2 = new Color(114, 79, 44);
    public static Color BROWN3 = new Color(187, 152, 106);
    public static Color BROWN4 = new Color(175, 136, 75);
    public static Color BROWN5 = new Color(159, 118, 56);
    public static Color BROWN6 = new Color(181, 134, 85);
    public static Color BROWN7 = new Color(224, 179, 137);
    public static Color GRAY1 = new Color(104, 89, 83);
    public static Color GRAY2 = new Color(121, 116, 123);
    public static Color GRAY3 = new Color(146, 126, 113);
    public static Color GRAY4 = new Color(162, 154, 147);
    public static Color GRAY5 = new Color(191, 164, 155);
    public static Color GRAY6 = new Color(200, 183, 173);
    public static Color GRAY7 = new Color(209, 176, 140);

    private static FilPalette palette;

    private FilPalette() {
        registerColor(YELLOW1);
        registerColor(YELLOW2);

        registerColor(ORANGE1);
        registerColor(ORANGE2);

        registerColor(CHESTNUT);

        registerColor(PURPLE1);
        registerColor(PURPLE2);
        registerColor(FUSHIA);
        registerColor(COPPER1);
        registerColor(COPPER2);
        registerColor(COPPER3);
        registerColor(CHERRY);
        registerColor(PINK1);
        registerColor(PINK2);
        registerColor(PINK3);
        registerColor(PINK4);

        registerColor(BLUE1);
        registerColor(BLUE2);
        registerColor(BLUE3);
        registerColor(BLUE4);
        registerColor(BLUE5);
        registerColor(BLUE6);
        registerColor(BLUE7);
        registerColor(BLUE8);
        registerColor(BLUE9);
        registerColor(BLUE10);
        registerColor(BLUE11);
        registerColor(BLUE12);
        registerColor(BLUE13);
        registerColor(BLUE14);
        registerColor(BLUE15);
        registerColor(BLUE16);
        registerColor(BLUE17);

        registerColor(GREEN1);
        registerColor(GREEN2);
        registerColor(GREEN3);
        registerColor(GREEN4);
        registerColor(GREEN5);
        registerColor(GREEN6);

        registerColor(BROWN1);
        registerColor(BROWN2);
        registerColor(BROWN3);
        registerColor(BROWN4);
        registerColor(BROWN5);
        registerColor(BROWN6);
        registerColor(BROWN7);

        registerColor(GRAY1);
        registerColor(GRAY2);
        registerColor(GRAY3);
        registerColor(GRAY4);
        registerColor(GRAY5);
        registerColor(GRAY6);
        registerColor(GRAY7);

    }

    public static ColorPalette getInstance() {
        if (palette == null) {
            palette = new FilPalette();
        }
        return palette;
    }
}
