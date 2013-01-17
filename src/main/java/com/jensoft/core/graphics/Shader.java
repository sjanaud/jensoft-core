/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.graphics;

import java.awt.Color;

public class Shader {

    private float[] fractions;
    private Color[] colors;

    public static Shader Night = new Shader(new float[] { 0f, 1f },
                                            new Color[] { new Color(32, 39, 55), Color.BLACK });

    public Shader(float[] fractions, Color[] colors) {
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException(
                                               "shader length array does not match");
        }
        this.fractions = fractions;
        this.colors = colors;
    }

    /**
     * @return the fractions
     */
    public float[] getFractions() {
        return fractions;
    }

    /**
     * @return the colors
     */
    public Color[] getColors() {
        return colors;
    }

    public static void main(String[] args) {
        Color c = new Color(0x202737);
        System.out.println(c.getRed() + "." + c.getGreen() + "." + c.getBlue());
    }
}
