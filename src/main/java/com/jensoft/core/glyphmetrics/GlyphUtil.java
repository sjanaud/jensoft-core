/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics;

import java.awt.font.GlyphVector;

/**
 * GlyphUtil help to get glyph properties
 * 
 * @see GlyphMetric
 * @see AbstractMetricsPath
 * @see GeneralMetricsPath
 * @author Sebastien Janaud
 */
public class GlyphUtil {

    /***
     * get the tokens width for the glyph vector
     * 
     * @param glyphVector
     * @return the tokens width
     */
    public static float[] getTokenWidths(GlyphVector glyphVector) {

        float[] widths = new float[glyphVector.getNumGlyphs()];
        for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

            float width = (float) glyphVector.getGlyphLogicalBounds(j)
                    .getBounds2D().getWidth();
            // float width =
            // (float)glyphVector.getGlyphVisualBounds(j).getBounds2D().getWidth();

            widths[j] = width;
        }
        return widths;
    }

    /***
     * get the token width for the glyph vector
     * 
     * @param glyphVector
     * @param tokenIndex
     *            the token index
     * @return the tokens width
     */
    public static float getTokenWidth(GlyphVector glyphVector, int tokenIndex) {

        return (float) glyphVector.getGlyphLogicalBounds(tokenIndex)
                .getBounds2D().getWidth();

    }

    /***
     * get the glyph width
     * 
     * @param glyphVector
     * @return the glyph width
     */
    public static float getGlyphWidth(GlyphVector glyphVector) {

        float w = 0;
        float[] widths = getTokenWidths(glyphVector);
        for (int i = 0; i < widths.length; i++) {
            w = w + widths[i];
        }
        return w;
    }

    /***
     * get the fraction width of the glyph at the specified token
     * 
     * @param glyphVector
     * @return the fraction glyph width
     */
    public static float getGlyphWidthAtToken(GlyphVector glyphVector,
            int tokenIndex) {

        if (tokenIndex < 0) {
            return 0;
        }
        float w = 0;
        float[] widths = getTokenWidths(glyphVector);
        for (int i = 0; i < tokenIndex; i++) {
            w = w + widths[i];
        }
        return w;
    }

}
