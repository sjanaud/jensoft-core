/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.drawable.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import com.jensoft.core.drawable.Drawable;
import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.glyphmetrics.GlyphUtil;

/**
 * <code>TextPath</code> TextPath lay out text along a path
 * 
 * @author Sebastien janaud
 */
public class TextPath implements Drawable {

    /** geometry path */
    private GeometryPath geometryPath;

    /** label */
    private String label = "label";

    /** glyph vector */
    private GlyphVector glyphVector;

    /** label font */
    private Font labelFont;

    /** glyph wdith */
    private float glyphWidth;

    /** text divergence */
    private int divergence = 10;

    /** text position default left */
    private TextPosition textPosition = TextPosition.Left;

    /** offset left */
    private float offsetLeft = 10;

    /** offset right */
    private float offsetRight = 10;

    /** text color */
    private Color textColor;

    /** fractions */
    private float[] fractions;

    /** shader color */
    private Color[] colors;

    /** affine transform */
    private AffineTransform af;

    /** path side */
    private PathSide pathSide = PathSide.Above;

    /** lock the reverse mode every time */
    private boolean lockReverse = false;

    /** auto reverse mode make reverse if needed */
    private boolean autoReverse = true;

    /**
     * text position
     */
    public enum TextPosition {
        Left("left"), Middle("middle"), Right("right");

        private String textPosition;

        private TextPosition(String position) {
            textPosition = position;
        }

        /**
         * @return the textPosition
         */
        public String getTextPosition() {
            return textPosition;
        }

        /**
         * parse position string into Text position
         * 
         * @param position
         *            the position to parse
         * @return text position, Middle is default
         */
        public static TextPosition parse(String position) {
            if (Left.getTextPosition().equalsIgnoreCase(position)) {
                return Left;
            }
            if (Middle.getTextPosition().equalsIgnoreCase(position)) {
                return Middle;
            }
            if (Right.getTextPosition().equalsIgnoreCase(position)) {
                return Right;
            }
            return Middle;
        }

    }

    public enum PathSide {
        Above("above"), Over("over"), Below("below");

        private String pathSide;

        private PathSide(String side) {
            pathSide = side;
        }

        /**
         * @return the pathSide
         */
        public String getPathSide() {
            return pathSide;
        }

        /**
         * parse the side string into path side
         * 
         * @param side
         *            the side to parse
         * @return path side, Below is default
         */
        public static PathSide parse(String side) {
            if (Above.getPathSide().equals(side)) {
                return Above;
            }
            if (Over.getPathSide().equals(side)) {
                return Over;
            }
            if (Below.getPathSide().equals(side)) {
                return Below;
            }
            return Below;
        }
    }

    /**
     * create the text path with the specified shape
     * 
     * @param shape
     */
    public TextPath(Shape shape) {
        geometryPath = new GeometryPath(shape);
    }

    /**
     * @return the lockReverse
     */
    public boolean isLockReverse() {
        return lockReverse;
    }

    /**
     * @param lockReverse
     *            the lockReverse to set
     */
    public void setLockReverse(boolean lockReverse) {
        this.lockReverse = lockReverse;
    }

    /**
     * @return the autoReverse
     */
    public boolean isAutoReverse() {
        return autoReverse;
    }

    /**
     * @param autoReverse
     *            the autoReverse to set
     */
    public void setAutoReverse(boolean autoReverse) {
        this.autoReverse = autoReverse;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the pathSide
     */
    public PathSide getPathSide() {
        return pathSide;
    }

    /**
     * @param pathSide
     *            the pathSide to set
     */
    public void setPathSide(PathSide pathSide) {
        this.pathSide = pathSide;
    }

    /**
     * @return the textPosition
     */
    public TextPosition getTextPosition() {
        return textPosition;
    }

    /**
     * @param textPosition
     *            the textPosition to set
     */
    public void setTextPosition(TextPosition textPosition) {
        this.textPosition = textPosition;
    }

    /**
     * @return the divergence
     */
    public int getDivergence() {
        return divergence;
    }

    /**
     * @param divergence
     *            the divergence to set
     */
    public void setDivergence(int divergence) {
        this.divergence = divergence;
    }

    /**
     * @return the labelFont
     */
    public Font getLabelFont() {
        return labelFont;
    }

    /**
     * @param labelFont
     *            the labelFont to set
     */
    public void setLabelFont(Font labelFont) {
        this.labelFont = labelFont;
    }

    /**
     * set gradient shader parameters
     * 
     * @param fractions
     *            the step fractions to set
     * @param colors
     *            the colors step
     */
    public void setShader(float[] fractions, Color[] colors) {
        if (fractions == null || colors == null) {
            throw new IllegalArgumentException(
                                               "fractions and/or colors array should be not null");
        }
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("length array does not match");
        }

        this.fractions = fractions;
        this.colors = colors;
    }

    /**
     * @return the offsetLeft
     */
    public float getOffsetLeft() {
        return offsetLeft;
    }

    /**
     * @param offsetLeft
     *            the offsetLeft to set
     */
    public void setOffsetLeft(float offsetLeft) {
        this.offsetLeft = offsetLeft;
    }

    /**
     * @return the offsetRight
     */
    public float getOffsetRight() {
        return offsetRight;
    }

    /**
     * @param offsetRight
     *            the offsetRight to set
     */
    public void setOffsetRight(float offsetRight) {
        this.offsetRight = offsetRight;
    }

    /**
     * @return the textColor
     */
    public Color getTextColor() {
        return textColor;
    }

    /**
     * @param textColor
     *            the textColor to set
     */
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.drawable.Drawable#draw(java.awt.Graphics2D)
     */
    @Override
    public void draw(Graphics2D g2d) {
        if (label == null) {
            return;
        }
        if (labelFont != null) {
            g2d.setFont(labelFont);
        }
        else {
            labelFont = g2d.getFont();
        }

        if (glyphVector == null) {
            glyphVector = labelFont.createGlyphVector(
                                                      g2d.getFontRenderContext(), label);
            glyphWidth = GlyphUtil.getGlyphWidth(glyphVector);
        }

        af = new AffineTransform();

        float startLength = 0;
        float endLength = 0;
        Point2D pointStart = null;
        Point2D pointEnd = null;

        if (textPosition == TextPosition.Left) {
            startLength = offsetLeft;
            endLength = startLength + glyphWidth;
            pointStart = geometryPath.pointAtLength(startLength);
            pointEnd = geometryPath.pointAtLength(endLength);
        }
        else if (textPosition == TextPosition.Right) {
            startLength = geometryPath.lengthOfPath() - glyphWidth
                    - offsetRight;
            endLength = startLength + glyphWidth;
            pointStart = geometryPath.pointAtLength(startLength);
            pointEnd = geometryPath.pointAtLength(endLength);
        }
        else if (textPosition == TextPosition.Middle) {
            startLength = geometryPath.lengthOfPath() / 2 - glyphWidth / 2;
            endLength = startLength + glyphWidth;
            pointStart = geometryPath.pointAtLength(startLength);
            pointEnd = geometryPath.pointAtLength(endLength);
        }

        if (pointStart == null || pointEnd == null) {
            return;
        }

        boolean reverse = lockReverse;
        if (autoReverse) {
            if (pointStart.getX() > pointEnd.getX()) {
                reverse = true;
            }
        }

        for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

            Point2D p = glyphVector.getGlyphPosition(j);
            float px = (float) p.getX();
            float py = (float) p.getY();
            Point2D pointGlyph;

            // pointGlyph = geometryPath.pointAtLength(startLength
            // + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
            if (!reverse) {
                pointGlyph = geometryPath.pointAtLength(startLength
                        + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
            }
            else {
                pointGlyph = geometryPath.pointAtLength(endLength
                        - GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
            }

            if (pointGlyph == null) {
                continue;
            }

            af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
            float angle = 0;

            // angle = geometryPath.angleAtLength(startLength
            // + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
            //
            // af.rotate(angle);

            if (!reverse) {
                angle = geometryPath.angleAtLength(startLength
                        + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
            }
            else {
                angle = geometryPath.angleAtLength(endLength
                        - GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
            }

            if (!reverse) {
                af.rotate(angle);
            }
            else {
                af.rotate(angle + Math.PI);
            }

            // af.translate(-px, -py - divergence);
            if (!reverse) {

                // af.shear(-0.3, 0);

                if (pathSide == PathSide.Above) {
                    af.translate(-px, -py - divergence);
                }
                if (pathSide == PathSide.Over) {
                    af.translate(-px, -py
                            + glyphVector.getVisualBounds().getHeight() / 2);
                }
                if (pathSide == PathSide.Below) {
                    af.translate(-px, -py
                            + glyphVector.getVisualBounds().getHeight()
                            + divergence);
                }
            }
            else {

                // af.shear(0.3, 0);

                if (pathSide == PathSide.Above) {
                    af.translate(-px, -py
                            + glyphVector.getVisualBounds().getHeight()
                            + divergence);
                }
                if (pathSide == PathSide.Over) {
                    af.translate(-px, -py
                            + glyphVector.getVisualBounds().getHeight() / 2);
                }
                if (pathSide == PathSide.Below) {
                    af.translate(-px, -py - divergence);
                }
            }
            Shape glyph = glyphVector.getGlyphOutline(j);
            Shape glyphTransformed = af.createTransformedShape(glyph);

            Shape glyphBound2D = glyphVector.getGlyphOutline(j).getBounds2D();

            Point2D ptSrcNorth = new Point2D.Double(glyphBound2D.getBounds2D()
                    .getCenterX(), glyphBound2D.getBounds2D().getY());
            Point2D ptDstNorth = new Point2D.Double();
            af.transform(ptSrcNorth, ptDstNorth);

            Point2D ptSrcSouth = new Point2D.Double(glyphBound2D.getBounds2D()
                    .getCenterX(), glyphBound2D.getBounds2D().getY()
                    + glyphBound2D.getBounds2D().getHeight());
            Point2D ptDstSouth = new Point2D.Double();
            af.transform(ptSrcSouth, ptDstSouth);

            if (textColor != null) {
                g2d.setColor(textColor);
            }
            else {
                g2d.setColor(Color.BLACK);
            }

            if (fractions != null) {

                if (ptDstNorth != null && ptDstSouth != null
                        && !ptDstNorth.equals(ptDstSouth)) {
                    LinearGradientPaint p2 = new LinearGradientPaint(
                                                                     ptDstNorth, ptDstSouth, fractions, colors);
                    g2d.setPaint(p2);
                }
            }
            g2d.fill(glyphTransformed);

        }

    }

}
