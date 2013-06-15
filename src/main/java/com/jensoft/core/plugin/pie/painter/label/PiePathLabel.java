/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.label;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.jensoft.core.drawable.text.TextPath;
import com.jensoft.core.drawable.text.TextPath.PathSide;
import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.PieToolkit;
import com.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter;
import com.jensoft.core.plugin.pie.painter.PieSlicePainter;

/**
 * <code>PiePathLabel</code>
 * <p>
 * Pie Path label lay out pie slice labels on pie slice path fragments like external arc or<br>
 * start and end line slice
 * <p>
 * <br>
 * <br>
 * <img src="doc-files/PiePathLabel.png"> <br>
 * <br>
 * create path label on slice
 * 
 * <pre>
 * PiePathLabel ppl12 = PieToolkit.createPathLabel(&quot;JenSoft API&quot;,
 *                                                 RosePalette.INDIGO,
 *                                                 InputFonts.getFont(InputFonts.NO_MOVE, 12),
 *                                                 TextPosition.Middle);
 * 
 * slice.addSliceLabel(ppl12);
 * </pre>
 * 
 * @see PieToolkit
 * @see AbstractPieSliceLabel
 * @see AbstractPieSlicePainter
 * @see PieSlicePainter
 * @see Pie
 * @see PiePlugin
 * @author Sebastien Janaud
 */
public class PiePathLabel extends AbstractPieSliceLabel {

    /** text position along the slice path entity, default is left */
    private TextPosition textPosition = TextPosition.Left;

    /** side of the path, default is above */
    private PathSide pathSide = PathSide.Above;

    /** label shader fractions */
    private float[] fractions;

    /** label shader colors */
    private Color[] colors;

    /** label divergence from path */
    private int divergence = 10;

    /** path name, default is arc */
    private PathName pathName = PathName.Arc;

    /** lock the reverse mode every time */
    private boolean lockReverse = false;

    /** auto reverse mode make reverse if needed ,default is true */
    private boolean autoReverse = true;

    /** offset right */
    private int offsetRight = 10;

    /** offset left */
    private int offsetLeft = 10;

    /**
     * path name define the path fragment<br>
     * start, arc or end path fragment
     * 
     * @author Sebastien Janaud
     */
    public enum PathName {
        Start("Start"), Arc("Arc"), End("End");

        /** path name */
        private String pathName;

        /**
         * create path name
         * 
         * @param path
         *            the path name to set
         */
        private PathName(String path) {
            pathName = path;
        }

        /**
         * @return the pathName
         */
        public String getPathName() {
            return pathName;
        }

        /**
         * parse the specified path string into path name.
         * 
         * @param path
         *            the path name to parse
         * @return the path name, Arc is default
         */
        public static PathName parse(String path) {
            if (Start.getPathName().equals(path)) {
                return Start;
            }
            if (Arc.getPathName().equals(path)) {
                return Arc;
            }
            if (End.getPathName().equals(path)) {
                return End;
            }
            return Arc;
        }
    }

    /**
     * create empty path label
     */
    public PiePathLabel() {
    }

    /**
     * create path label with specified parameters
     * 
     * @param textPosition
     */
    public PiePathLabel(TextPosition textPosition) {
        this.textPosition = textPosition;
    }

    /**
     * create path label with specified parameters
     * 
     * @param label
     */
    public PiePathLabel(String label) {
        super.setLabel(label);
    }

    /**
     * create path label with specified parameters
     * 
     * @param label
     * @param labelColor
     */
    public PiePathLabel(String label, Color labelColor) {
        super.setLabel(label);
        super.setLabelColor(labelColor);
    }

    /**
     * create path label with specified parameters
     * 
     * @param label
     * @param labelColor
     * @param labelFont
     */
    public PiePathLabel(String label, Color labelColor, Font labelFont) {
        super.setLabel(label);
        super.setLabelColor(labelColor);
        super.setLabelFont(labelFont);
    }

    /**
     * create path label with specified parameters
     * 
     * @param textPosition
     * @param label
     */
    public PiePathLabel(TextPosition textPosition, String label) {
        this.textPosition = textPosition;
        super.setLabel(label);
    }

    /**
     * create path label with specified parameters
     * 
     * @param textPosition
     * @param label
     * @param labelColor
     */
    public PiePathLabel(TextPosition textPosition, String label,
            Color labelColor) {
        this.textPosition = textPosition;
        super.setLabel(label);
        super.setLabelColor(labelColor);
    }

    /**
     * true if the path is lock reverse, false otherwise
     * 
     * @return the lockReverse
     */
    public boolean isLockReverse() {
        return lockReverse;
    }

    /**
     * force the reverse every time if set to true
     * 
     * @param lockReverse
     *            the lockReverse to set
     */
    public void setLockReverse(boolean lockReverse) {
        this.lockReverse = lockReverse;
    }

    /**
     * true if the path label is auto reverse, false otherwise
     * 
     * @return the autoReverse
     */
    public boolean isAutoReverse() {
        return autoReverse;
    }

    /**
     * set auto reverse to given auto reverse flag.
     * 
     * @param autoReverse
     *            the autoReverse to set
     */
    public void setAutoReverse(boolean autoReverse) {
        this.autoReverse = autoReverse;
    }

    /**
     * @return the offsetRight
     */
    public int getOffsetRight() {
        return offsetRight;
    }

    /**
     * @param offsetRight
     *            the offsetRight to set
     */
    public void setOffsetRight(int offsetRight) {
        this.offsetRight = offsetRight;
    }

    /**
     * @return the offsetLeft
     */
    public int getOffsetLeft() {
        return offsetLeft;
    }

    /**
     * @param offsetLeft
     *            the offsetLeft to set
     */
    public void setOffsetLeft(int offsetLeft) {
        this.offsetLeft = offsetLeft;
    }

    /**
     * @return the pathName
     */
    public PathName getPathName() {
        return pathName;
    }

    /**
     * @param pathName
     *            the pathName to set
     */
    public void setPathName(PathName pathName) {
        this.pathName = pathName;
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
     * set gradient shader parameters
     * 
     * @param fractions
     *            the step fractions to set
     * @param colors
     *            the colors step
     */
    public void setTextShader(float[] fractions, Color[] colors) {
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("colors and fractions length array does not match");
        }
        this.fractions = fractions;
        this.colors = colors;
    }

    /**
     * set the shadow parameters
     * 
     * @param shader
     */
    public void setTextShader(Shader shader) {
        if (shader != null) {
            fractions = shader.getFractions();
            colors = shader.getColors();
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.painter.label.AbstractPieSliceLabel#paintPieLabel(java.awt.Graphics2D, com.jensoft.core.plugin.pie.Pie, com.jensoft.core.plugin.pie.PieSlice)
     */
    @Override
    protected void paintPieLabel(Graphics2D g2d, Pie pie, PieSlice pieSection) {

        TextPath pt = null;
        if (pathName == PathName.Arc) {
            pt = new TextPath(pieSection.getExternalArc());
        }
        if (pathName == PathName.Start) {
            pt = new TextPath(pieSection.getLineStart());
        }
        if (pathName == PathName.End) {
            pt = new TextPath(pieSection.getLineEnd());
        }

        if (pt != null) {
            pt.setTextPosition(textPosition);
            pt.setLockReverse(isLockReverse());
            pt.setAutoReverse(isAutoReverse());
            pt.setLabel(getLabel());
            pt.setTextColor(getLabelColor());
            pt.setOffsetRight(getOffsetRight());
            pt.setOffsetLeft(getOffsetLeft());
            pt.setLabelFont(getLabelFont());
            pt.setPathSide(pathSide);
            pt.setDivergence(divergence);
            if (fractions != null && colors != null
                    && fractions.length == colors.length) {
                pt.setShader(fractions, colors);
            }
            pt.draw(g2d);
        }

    }

}
