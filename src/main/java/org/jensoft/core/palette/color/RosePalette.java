/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.palette.color;

import java.awt.Color;

/**
 * <rose>RosePalette</code>
 * 
 * @author sebastien janaud
 */
public class RosePalette extends ColorPalette {

    public static Color BORDEAUX = new Color(168, 45, 69);
    public static Color PRIMROSE = new Color(216, 90, 122);
    public static Color PLUMWINE = new Color(115, 51, 92);
    public static Color AMETHYST = new Color(170, 72, 134);
    public static Color LAVENDER = new Color(168, 151, 183);
    public static Color FOXGLOWE = new Color(136, 82, 152);
    public static Color FLANNELGRAY = new Color(109, 81, 75);
    public static Color STONEGRAY = new Color(166, 145, 140);
    public static Color INDIGO = new Color(61, 44, 105);
    public static Color COALBLACK = new Color(37, 38, 41);
    public static Color LAPISBLUE = new Color(88, 84, 141);
    public static Color COBALT = new Color(35, 56, 158);
    public static Color TURQUOISE = new Color(23, 130, 187);
    public static Color AEGEANBLUE = new Color(22, 125, 218);
    public static Color NEPTUNE = new Color(128, 182, 191);
    public static Color CALYPSOBLUE = new Color(91, 151, 168);
    public static Color JADE = new Color(143, 184, 175);
    public static Color DEEPHARBOR = new Color(44, 114, 97);
    public static Color EMERALD = new Color(62, 142, 78);
    public static Color LEAFGREEN = new Color(83, 133, 52);
    public static Color PINE = new Color(140, 187, 89);
    public static Color LIME = new Color(197, 208, 89);
    public static Color PALMLEAF = new Color(190, 168, 99);
    public static Color SAGE = new Color(203, 207, 148);
    public static Color LEMONPEEL = new Color(247, 239, 100);
    public static Color SAFFRON = new Color(235, 214, 92);
    public static Color SANDALWOOD = new Color(216, 204, 165);
    public static Color MELON = new Color(230, 193, 153);
    public static Color SUNBURST = new Color(235, 204, 131);
    public static Color CHOCOLATE = new Color(86, 51, 41);
    public static Color LIGHTBROWN = new Color(190, 94, 61);
    public static Color SIENNA = new Color(168, 72, 36);
    public static Color HENNA = new Color(219, 65, 32);
    public static Color MANDARIN = new Color(255, 136, 83);
    public static Color CORALRED = new Color(208, 58, 47);
    public static Color REDWOOD = new Color(203, 71, 52);
    public static Color FLAMINGO = new Color(225, 185, 197);
    public static Color CARDINAL = new Color(194, 37, 37);
    public static Color PINGPIZZAZZ = new Color(218, 118, 153);
    public static Color AZALEA = new Color(204, 74, 84);

    private static RosePalette palette;

    private RosePalette() {

        registerColor(BORDEAUX);
        registerColor(PRIMROSE);
        registerColor(PLUMWINE);
        registerColor(AMETHYST);
        registerColor(LAVENDER);
        registerColor(FOXGLOWE);
        registerColor(FLANNELGRAY);
        registerColor(STONEGRAY);
        registerColor(INDIGO);
        registerColor(COALBLACK);
        registerColor(LAPISBLUE);
        registerColor(COBALT);
        registerColor(TURQUOISE);
        registerColor(AEGEANBLUE);
        registerColor(NEPTUNE);
        registerColor(CALYPSOBLUE);
        registerColor(JADE);
        registerColor(DEEPHARBOR);
        registerColor(EMERALD);
        registerColor(LEAFGREEN);
        registerColor(PINE);
        registerColor(LIME);
        registerColor(PALMLEAF);
        registerColor(SAGE);
        registerColor(LEMONPEEL);
        registerColor(SAFFRON);
        registerColor(SANDALWOOD);
        registerColor(MELON);
        registerColor(SUNBURST);
        registerColor(CHOCOLATE);
        registerColor(LIGHTBROWN);
        registerColor(SIENNA);
        registerColor(HENNA);
        registerColor(MANDARIN);
        registerColor(CORALRED);
        registerColor(REDWOOD);
        registerColor(FLAMINGO);
        registerColor(CARDINAL);
        registerColor(PINGPIZZAZZ);
        registerColor(AZALEA);

    }

    public static ColorPalette getInstance() {
        if (palette == null) {
            palette = new RosePalette();
        }
        return palette;

    }

}
