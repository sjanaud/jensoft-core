/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <code>ColorPalette</code> provides abstract definition and utility methods for color.
 * 
 * @author sebastien janaud
 */
public abstract class ColorPalette {

    public static Color WHITE = Color.WHITE;
    public static Color BLACK = Color.BLACK;
    public static Color DARK_GRAY = Color.DARK_GRAY;
    public static Color GRAY = Color.GRAY;
    public static Color LIGHT_GRAY = Color.LIGHT_GRAY;

    /** randomize generator */
    private static Random random = new Random();

    /** color index */
    private int curentColor = 0;

    /** color palette registry */
    private List<Color> palette;

    /**
     * create the palette
     */
    public ColorPalette() {
        palette = new ArrayList<Color>();
    }

    /**
     * get a randomized color
     * 
     * @return randomized color
     */
    public static Color getRandomColor() {
        Color themeColor = new Color(random.nextInt(255), random.nextInt(255),
                                     random.nextInt(255));
        return themeColor;
    }

    /**
     * get the specified color with alpha transparency
     * 
     * @param c
     *            the color
     * @param alpha
     *            [0,255]
     * @return the color
     */
    public static Color alpha(Color c, int alpha) {
        return new Color(c.getRed(), c.getGreen(), c.getBlue(), alpha);
    }

    /**
     * make the specified color brighter
     * 
     * @param c
     * @param factor
     * @return the brighter color
     */
    public static Color brighter(Color c, float factor) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();

        int i = (int) (1.0 / (1.0 - factor));

        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i);
        }
        if (r > 0 && r < i) {
            r = i;
        }
        if (g > 0 && g < i) {
            g = i;
        }
        if (b > 0 && b < i) {
            b = i;
        }

        return new Color(Math.min((int) (r / factor), 255), Math.min(
                                                                     (int) (g / factor), 255),
                         Math.min((int) (b / factor), 255));
    }

    /**
     * get red of the specified color
     * 
     * @param color
     * @return red
     */
    public static int getRed(Color color) {
        return color.getRed();
    }

    /**
     * get greed of the specified color
     * 
     * @param color
     * @return green
     */
    public static int getGreen(Color color) {
        return color.getGreen();
    }

    /**
     * get blue of the specified color
     * 
     * @param color
     * @return blue
     */
    public static int getBlue(Color color) {
        return color.getBlue();
    }

    /**
     * make the specified color darker
     * 
     * @param c
     * @param factor
     * @return darker color
     */
    public static Color darker(Color c, float factor) {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        return new Color(Math.max((int) (r * factor), 0), Math.max(
                                                                   (int) (g * factor), 0), Math.max((int) (b * factor),
                                                                                                    0));
    }

    /**
     * register the specified color in the palette
     * 
     * @param color
     *            the color to register
     */
    public void registerColor(Color color) {
        palette.add(color);
    }

    /**
     * get a color from this palette.
     * 
     * @return
     *         a color from this palette
     */
    public Color getPaletteRandomColor() {
        return palette.get(random.nextInt(palette.size()));
    }

    /**
     * get the entire palette as a color list
     * 
     * @return the palette
     */
    public List<Color> getPaletteColors() {
        return palette;
    }

    /**
     * get the color at the specified index of this palette
     * 
     * @param index
     *            the color index
     * @return color
     */
    public Color getColor(int index) {
        return palette.get(index);
    }

    /**
     * get the next color in this palette regarding the internal index
     * 
     * @return next color
     */
    public Color nextColor() {
        if (curentColor++ >= palette.size() - 1) {
            curentColor = 0;
        }
        return palette.get(curentColor);
    }

    /**
     * get the previous color in this palette regarding the internal index
     * 
     * @return previous color
     */
    public Color previousColor() {
        if (curentColor-- == 0) {
            curentColor = palette.size() - 1;
        }
        return palette.get(curentColor);
    }

}
