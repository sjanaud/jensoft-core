/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.highway;

import java.awt.Color;
import java.awt.Font;

public class GroupRenderingProperties {

    private Color skeletBackground = Color.WHITE;
    private int skeletWidth = 14;
    //arial
    //Sans-serif
    //monospace
    private Font glyphFont = new Font("Sans-serif", Font.PLAIN, 12);
    private boolean lockGlyph = true;

    public GroupRenderingProperties(int skeletWidth, Color skeletBackground) {
        super();
        this.skeletWidth = skeletWidth;
        this.skeletBackground = skeletBackground;
    }

    public Font getGlyphFont() {
        return glyphFont;
    }

    public void lockGlyph() {
        lockGlyph = true;
    }

    public void unlockGlyph() {
        lockGlyph = false;
    }

    public boolean isLockGlyph() {
        return lockGlyph;
    }

    public void setGlyphFont(Font glyphFont) {
        this.glyphFont = glyphFont;
    }

    public Color getSkeletBackground() {
        return skeletBackground;
    }

    public void setSkeletBackground(Color skeletBackground) {
        this.skeletBackground = skeletBackground;
    }

    public int getSkeletWidth() {
        return skeletWidth;
    }

    public void setSkeletWidth(int skeletWidth) {
        this.skeletWidth = skeletWidth;
    }

}
