/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d.painter.label;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.ParseException;

import com.jensoft.core.drawable.text.TextPath;
import com.jensoft.core.drawable.text.TextPath.PathSide;
import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.donut3d.Donut3D;
import com.jensoft.core.plugin.donut3d.Donut3DPlugin;
import com.jensoft.core.plugin.donut3d.Donut3DSlice;
import com.jensoft.core.plugin.donut3d.Donut3DToolkit;
import com.jensoft.core.plugin.donut3d.painter.Donut3DSlicePainter;

/**
 * <code>Donut3DPathLabel</code>
 * <p>
 * Donut3D Path label lay out donut3D slice labels on donut3D slice path fragments like external arc or<br>
 * start and end line slice
 * <p>
 * <br>
 * <br>
 * <img src="doc-files/Donut3DPathLabel.png"> <br>
 * <br>
 * create path label on slice
 * 
 * @see Donut3D
 * @see Donut3DSlice
 * @see Donut3DPlugin
 * @see Donut3DSlicePainter
 * @see AbstractDonut3DSliceLabel
 * @see Donut3DToolkit
 * @author Sebastien Janaud
 */
public class Donut3DPathLabel extends AbstractDonut3DSliceLabel {

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
    private Donut3DFacetPathName facetPathName = Donut3DFacetPathName.OuterArcTop;

    /** lock the reverse mode every time, default is false */
    private boolean lockReverse = false;

    /** auto reverse mode make reverse if needed ,default is true */
    private boolean autoReverse = true;

    /** offset left */
    private float offsetLeft = 10;

    /** offset right */
    private float offsetRight = 10;

    /**
     * path name define the path fragment<br>
     * start, arc or end path fragment
     * 
     * @author Sebastien Janaud
     */
    public enum Donut3DFacetPathName {
        StartLineTop("start"), OuterArcTop("outerArcTop"), InnerArcTop("innerArcTop"), OuterArcBottom("outerArcBottom"), InnerArcBottom(
                "innerArcBottom"), EndLineTop("endLineTop"), StartLineOuter("startLineOuter"), EndLineOuter(
                "endLineOuter");

        /** path name */
        private String pathName;

        /**
         * create path name
         * 
         * @param path
         *            the path name to set
         */
        private Donut3DFacetPathName(String path) {
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
        public static Donut3DFacetPathName parse(String path) {
            if (StartLineTop.getPathName().equalsIgnoreCase(path)) {
                return StartLineTop;
            }
            if (OuterArcTop.getPathName().equalsIgnoreCase(path)) {
                return OuterArcTop;
            }
            if (EndLineTop.getPathName().equalsIgnoreCase(path)) {
                return EndLineTop;
            }
            return OuterArcTop;
        }
    }

    /**
     * create empty path label
     */
    public Donut3DPathLabel() {
    }

    /**
     * create path label with specified parameters
     * 
     * @param textPosition
     */
    public Donut3DPathLabel(TextPosition textPosition) {
        this.textPosition = textPosition;
    }

    /**
     * create path label with specified parameters
     * 
     * @param label
     */
    public Donut3DPathLabel(String label) {
        super.setLabel(label);
    }

    /**
     * create path label with specified parameters
     * 
     * @param label
     * @param labelColor
     */
    public Donut3DPathLabel(String label, Color labelColor) {
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
    public Donut3DPathLabel(String label, Color labelColor, Font labelFont) {
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
    public Donut3DPathLabel(TextPosition textPosition, String label) {
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
    public Donut3DPathLabel(TextPosition textPosition, String label,
            Color labelColor) {
        this.textPosition = textPosition;
        super.setLabel(label);
        super.setLabelColor(labelColor);
    }

    /**
     * @return the facet Path Name
     */
    public Donut3DFacetPathName getFacetPathName() {
        return facetPathName;
    }

    /**
     * @param facetPathName
     *            the facet Path Name to set
     */
    public void setFacetPathName(Donut3DFacetPathName facetPathName) {
        this.facetPathName = facetPathName;
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
     * parse the specified divergence string into int value
     * 
     * @param divergence
     *            the divergence to parse
     * @return divergence int value, 10 is default return value if {@link ParseException} occurs.
     */
    public static int parseDivergence(String divergence) {
        try {
            return Integer.parseInt(divergence);
        }
        catch (NumberFormatException e) {
        }
        return 10;
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
    
    /**
     * get new shader from {@link Donut3DPathLabel#fractions} and {@link Donut3DPathLabel#colors}
     * @return new shader from reference
     */
    public Shader getTextShader(){
    	if(fractions != null && colors != null){
    		return new Shader(fractions, colors);
    	}
    	return null;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel#paintDonut3DSliceLabel(java.awt.Graphics2D, com.jensoft.core.plugin.donut3d.Donut3D, com.jensoft.core.plugin.donut3d.Donut3DSlice)
     */
    @Override
    protected void paintDonut3DSliceLabel(Graphics2D g2d, Donut3D donut3d,
            Donut3DSlice donutSlice) {
        TextPath pt = null;
        if (facetPathName == Donut3DFacetPathName.OuterArcTop) {
            pt = new TextPath(donutSlice.getOuterArcTop());
        }
        if (facetPathName == Donut3DFacetPathName.InnerArcTop) {
            pt = new TextPath(donutSlice.getInnerArcTop());
        }
        if (facetPathName == Donut3DFacetPathName.OuterArcBottom) {
            pt = new TextPath(donutSlice.getOuterArcBottom());
        }
        if (facetPathName == Donut3DFacetPathName.InnerArcBottom) {
            pt = new TextPath(donutSlice.getInnerArcBottom());
        }
        if (facetPathName == Donut3DFacetPathName.StartLineTop) {
            pt = new TextPath(donutSlice.getStartTopLine());
        }
        if (facetPathName == Donut3DFacetPathName.EndLineTop) {
            pt = new TextPath(donutSlice.getEndTopLine());
        }
        if (facetPathName == Donut3DFacetPathName.StartLineOuter) {
            pt = new TextPath(donutSlice.getStartOuterLine());
        }
        if (facetPathName == Donut3DFacetPathName.EndLineOuter) {
            pt = new TextPath(donutSlice.getEndOuterLine());
        }

        if (pt != null) {
            pt.setTextPosition(textPosition);
            pt.setLockReverse(isLockReverse());
            pt.setAutoReverse(isAutoReverse());
            pt.setLabel(getLabel());
            pt.setTextColor(getLabelColor());
            pt.setLabelFont(getLabelFont());
            pt.setOffsetLeft(offsetLeft);
            pt.setOffsetRight(offsetRight);
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
