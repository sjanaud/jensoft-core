/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut3d;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel;

/**
 * <code>Donut3DSlice</code>
 * <p>
 * defines a Donut3D fragment
 * </p>
 * <br>
 * <img src="doc-files/Donut3D_1.png">
 * <img src="doc-files/Donut3D_2.png"> <br>
 * 
 * @author Sebastien Janaud
 */
public class Donut3DSlice {

    /** slice name */
    private String name;

    /** value */
    private double value;

    /** percent normalize value */
    private double normalizedValue;

    /** start angle degree */
    private double startAngleDegree;

    /** end angle degree */
    private double endAngleDegree;

    /** theme color */
    private Color themeColor;

    /** divergence radius */
    private double divergence = 0;

    /** outer arc top */
    private Arc2D outerArcTop;

    /** inner arc top */
    private Arc2D innerArcTop;

    /** outer arc bottom */
    private Arc2D outerArcBottom;

    /** inner Arc bottom */
    private Arc2D innerArcBottom;

    /** top face */
    private Shape topFace;

    /** bottom face */
    private Shape bottomFace;

    /** start face */
    private Shape startFace;

    /** end face */
    private Shape endFace;

    /** inner face */
    private Shape innerFace;

    /** outer face */
    private Shape outerFace;

    /** start bottom line */
    private Line2D startBottomLine;

    /** start top line */
    private Line2D startTopLine;

    /** start inner line */
    private Line2D startInnerLine;

    /** start outer line */
    private Line2D startOuterLine;

    /** end bottom line */
    private Line2D endBottomLine;

    /** end top line */
    private Line2D endTopLine;

    /** end inner line */
    private Line2D endInnerLine;

    /** end outer line */
    private Line2D endOuterLine;

    /** slice label */
    private List<AbstractDonut3DSliceLabel> sliceLabels;

    /** fragment type */
    private Type type;
    
    /**fragment slice flag*/
    private boolean isFragment;
    
    /**slice parent for this fragment*/
    private Donut3DSlice parentSlice;

    /** inner model */
    private Ellipse2D innerModel;

    /** center x */
    private double centerX;

    /** center y */
    private double centerY;

    /** paint flag */
    private boolean painted = false;

    /** enter flag */
    private boolean lockEnter = false;

    /** host donut 3D */
    private Donut3D host;

    /**
     * defines fragment type
     */
    public enum Type {
        Front, Back;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Donut3DSlice [name=" + name + ", startAngleDegree="
                + startAngleDegree + ", endAngleDegree=" + endAngleDegree
                + ", painted=" + painted + "]";
    }

    /** fragment registry */
    private List<Donut3DSlice> fragments = new ArrayList<Donut3DSlice>();

    /**
     * create new slice
     * 
     * @param name
     *            the name to set
     * @param themeColor
     *            the theme color to set
     */
    public Donut3DSlice(String name, Color themeColor) {
        this.name = name;
        this.themeColor = themeColor;
        divergence = 0;
        sliceLabels = new ArrayList<AbstractDonut3DSliceLabel>();
    }

    /**
     * @return the host donut3D
     */
    public Donut3D getHost() {
        return host;
    }

    /**
     * @param host
     *            the donut3D host to set
     */
    public void setHost(Donut3D host) {
        this.host = host;
    }

    /**
     * return true if mouse has just enter in this slice, false otherwise
     * 
     * @return enter flag
     */
    public boolean isLockEnter() {
        return lockEnter;
    }

    /**
     * lock slice enter flag
     */
    public void lockEnter() {
        if (!isLockEnter()) {
            lockEnter = true;
        }
    }

    /**
     * unlock slice enter
     */
    public void unlockEnter() {
        if (isLockEnter()) {
            lockEnter = false;
        }
    }

    /**
     * clear fragment registry
     */
    public void clearFragment() {
        fragments.clear();
    }

    /**
     * add fragment in registry
     * 
     * @param fragment
     *            the fragment to add
     */
    public void addFragment(Donut3DSlice fragment) {
        fragments.add(fragment);
    }

    /**
     * return true if the specified fragment is the first fragment of this
     * slice, false otherwise
     * 
     * @param fragment
     * @return true if the specified fragment is the first fragment of this
     *         slice, false otherwise
     */
    public boolean isFirst(Donut3DSlice fragment) {
        return fragments.get(0).equals(fragment);
    }

    /**
     * return true if the specified fragment is the last fragment of this
     * slice, false otherwise
     * 
     * @param fragment
     * @return true if the specified fragment is the last fragment of this
     *         slice, false otherwise
     */
    public boolean isLast(Donut3DSlice fragment) {
        return fragments.get(fragments.size() - 1).equals(fragment);
    }

    /**
     * get the front inner face of this slice
     * 
     * @return front inner face of this slice
     */
    public Area getFrontInnerFace() {
        Area frontInnerFace = new Area();
        for (Donut3DSlice fragment : fragments) {
            if (fragment.getType() == Type.Front) {
                frontInnerFace.add(new Area(fragment.getInnerFace()));
            }
        }
        return frontInnerFace;
    }

    /**
     * get the back inner face of this slice
     * 
     * @return back inner face of this slice
     */
    public Area getBackInnerFace() {
        Area frontInnerFace = new Area();
        for (Donut3DSlice fragment : fragments) {
            if (fragment.getType() == Type.Back) {
                frontInnerFace.add(new Area(fragment.getInnerFace()));
            }
        }
        return frontInnerFace;
    }

    /**
     * @return the fragments of this slice
     */
    public List<Donut3DSlice> getFragments() {
        return fragments;
    }

    /**
     * @param fragments
     *            the subslices to set
     */
    public void setFragments(List<Donut3DSlice> fragments) {
        this.fragments = fragments;
    }

    /**
     * @return the centerX
     */
    public double getCenterX() {
        return centerX;
    }

    /**
     * @param centerX
     *            the centerX to set
     */
    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    /**
     * @return the centerY
     */
    public double getCenterY() {
        return centerY;
    }

    /**
     * @param centerY
     *            the centerY to set
     */
    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    /**
     * @return the innerModel
     */
    public Ellipse2D getInnerModel() {
        return innerModel;
    }

    /**
     * @param innerModel
     *            the innerModel to set
     */
    public void setInnerModel(Ellipse2D innerModel) {
        this.innerModel = innerModel;
    }

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * get start angle degree of this slice
     * 
     * @return start angle degree
     */
    public double getStartAngleDegree() {
        return startAngleDegree;
    }

    /**
     * set start angle degree
     * 
     * @param startAngleDegree
     *            the start angle degree to set
     */
    public void setStartAngleDegree(double startAngleDegree) {
        this.startAngleDegree = startAngleDegree;
    }

    /**
     * get end angle degree of this slice
     * 
     * @return end angle degree
     */
    public double getEndAngleDegree() {
        return endAngleDegree;
    }

    /**
     * set end angle degree of this slice
     * 
     * @param endAngleDegree
     *            the end angle degree to set
     */
    public void setEndAngleDegree(double endAngleDegree) {
        this.endAngleDegree = endAngleDegree;
    }

    /**
     * get extends degree of this slice
     * 
     * @return extends degree of this slice
     */
    public double getExtendsDegree() {
        return Math.abs(getEndAngleDegree() - getStartAngleDegree());
    }

    /**
     * get the slice median degree
     * 
     * @return the slice median degree
     */
    public double getMedianDegree() {
        return getStartAngleDegree() + getExtendsDegree() / 2;
    }

    /**
     * get all slice label that have been registered on this slice
     * 
     * @return the slice Label collection of this slice
     */
    public List<AbstractDonut3DSliceLabel> getSliceLabels() {
        return sliceLabels;
    }

    /**
     * @param sliceLabel
     *            the slice label to add
     */
    public void addSliceLabel(AbstractDonut3DSliceLabel sliceLabel) {
        sliceLabels.add(sliceLabel);
    }

    /**
     * remove specified label of this slice
     * 
     * @param sliceLabel
     *            the slice label to remove
     */
    public void removeSliceLabel(AbstractDonut3DSliceLabel sliceLabel) {
        sliceLabels.remove(sliceLabel);
    }

    /**
     * return true if the specified slice label is already register in this
     * slice, false otherwise
     * 
     * @param sliceLabel
     *            the sliceLabel to add
     */
    public boolean containsSliceLabel(AbstractDonut3DSliceLabel sliceLabel) {
        return sliceLabels.contains(sliceLabel);
    }

    /**
     * remove all registered slice labels
     */
    public void removeAllSliceLabels() {
        sliceLabels.clear();
    }

    /**
     * get theme color
     * 
     * @return theme color
     */
    public Color getThemeColor() {
        return themeColor;
    }

    /**
     * set theme color
     * 
     * @param themeColor
     *            the theme color to set
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * get slice value
     * 
     * @return slice value
     */
    public double getValue() {
        return value;
    }

    /**
     * set the slice value
     * 
     * @param value
     *            the slice value to set
     * @throws IllegalArgumentException if value is strictly negative
     * 
     */
    public void setValue(double value) {
    	if(value < 0)
    		throw new IllegalArgumentException("Slice value should be greater than or equal to 0");
        this.value = value;
    }

    /**
     * get slice name
     * 
     * @return slice name
     */
    public String getName() {
        return name;
    }

    /**
     * set slice name
     * 
     * @param name
     *            the slice name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the normalized value
     * 
     * @return normalized value
     */
    public double getNormalizedValue() {
        return normalizedValue;
    }

    /**
     * set normalized value
     * 
     * @param normalizedValue
     *            the normalized value to set
     */
    public void setNormalizedValue(double normalizedValue) {
        this.normalizedValue = normalizedValue;
    }

    /**
     * get outer arc top
     * 
     * @return outer arc top
     */
    public Arc2D getOuterArcTop() {
        return outerArcTop;
    }

    /**
     * set outer arc top
     * 
     * @param outerArcTop
     *            the outer arc top to set
     */
    public void setOuterArcTop(Arc2D outerArcTop) {
        this.outerArcTop = outerArcTop;
    }

    /**
     * get inner arc top
     * 
     * @return inner arc top
     */
    public Arc2D getInnerArcTop() {
        return innerArcTop;
    }

    /**
     * set inner arc top
     * 
     * @param innerArcTop
     *            the inner arc top to set
     */
    public void setInnerArcTop(Arc2D innerArcTop) {
        this.innerArcTop = innerArcTop;
    }

    /**
     * get outer arc bottom
     * 
     * @return outer arc top
     */
    public Arc2D getOuterArcBottom() {
        return outerArcBottom;
    }

    /**
     * set outer arc bottom
     * 
     * @param outerArcBottom
     *            the outer arc bottom to set
     */
    public void setOuterArcBottom(Arc2D outerArcBottom) {
        this.outerArcBottom = outerArcBottom;
    }

    /**
     * get inner arc bottom
     * 
     * @return inner arc bottom
     */
    public Arc2D getInnerArcBottom() {
        return innerArcBottom;
    }

    /**
     * set inner arc bottom
     * 
     * @param innerArcBottom
     *            the inner arc bottom
     */
    public void setInnerArcBottom(Arc2D innerArcBottom) {
        this.innerArcBottom = innerArcBottom;
    }

    /**
     * get top face
     * 
     * @return top face
     */
    public Shape getTopFace() {
        return topFace;
    }

    /**
     * set top face
     * 
     * @param topFace
     *            the top face to set
     */
    public void setTopFace(Shape topFace) {
        this.topFace = topFace;
    }

    /**
     * get bottom face
     * 
     * @return the bottom face to set
     */
    public Shape getBottomFace() {
        return bottomFace;
    }

    /**
     * set the bottom face
     * 
     * @param bottomFace
     *            the bottom face to set
     */
    public void setBottomFace(Shape bottomFace) {
        this.bottomFace = bottomFace;
    }

    /**
     * get start face
     * 
     * @return start face
     */
    public Shape getStartFace() {
        return startFace;
    }

    /**
     * set the start face
     * 
     * @param startFace
     *            the start face to set
     */
    public void setStartFace(Shape startFace) {
        this.startFace = startFace;
    }

    /**
     * get end face
     * 
     * @return end face
     */
    public Shape getEndFace() {
        return endFace;
    }

    /**
     * set end face
     * 
     * @param endFace
     *            the end face to set
     */
    public void setEndFace(Shape endFace) {
        this.endFace = endFace;
    }

    /**
     * get inner face
     * 
     * @return inner face
     */
    public Shape getInnerFace() {
        return innerFace;
    }

    /**
     * set inner face
     * 
     * @param innerFace
     *            the inner face to set
     */
    public void setInnerFace(Shape innerFace) {
        this.innerFace = innerFace;
    }

    /**
     * get outer face
     * 
     * @return outer face
     */
    public Shape getOuterFace() {
        return outerFace;
    }

    /**
     * set outer face
     * 
     * @param outerFace
     *            the outer face to set
     */
    public void setOuterFace(Shape outerFace) {
        this.outerFace = outerFace;
    }

    /**
     * get divergence
     * 
     * @return divergence
     */
    public double getDivergence() {
        return divergence;
    }

    /**
     * set divergence
     * 
     * @param divergence
     *            the divergence to set
     */
    public void setDivergence(double divergence) {
        this.divergence = divergence;
    }

    /**
     * get start bottom line
     * 
     * @return start bottom line
     */
    public Line2D getStartBottomLine() {
        return startBottomLine;
    }

    /**
     * set start bottom line
     * 
     * @param startBottomLine
     *            the start bottom line to set
     */
    public void setStartBottomLine(Line2D startBottomLine) {
        this.startBottomLine = startBottomLine;
    }

    /**
     * get start top line
     * 
     * @return start top line
     */
    public Line2D getStartTopLine() {
        return startTopLine;
    }

    /**
     * set start top line
     * 
     * @param startTopLine
     *            the start top line to set
     */
    public void setStartTopLine(Line2D startTopLine) {
        this.startTopLine = startTopLine;
    }

    /**
     * get start inner line
     * 
     * @return start inner line
     */
    public Line2D getStartInnerLine() {
        return startInnerLine;
    }

    /**
     * set start inner line
     * 
     * @param startInnerLine
     *            the start inner line to set
     */
    public void setStartInnerLine(Line2D startInnerLine) {
        this.startInnerLine = startInnerLine;
    }

    /**
     * get start outer line
     * 
     * @return start outer line
     */
    public Line2D getStartOuterLine() {
        return startOuterLine;
    }

    /**
     * set start outer line
     * 
     * @param startOuterLine
     *            the start outer line to set
     */
    public void setStartOuterLine(Line2D startOuterLine) {
        this.startOuterLine = startOuterLine;
    }

    /**
     * get end bottom line
     * 
     * @return end bottom line
     */
    public Line2D getEndBottomLine() {
        return endBottomLine;
    }

    /**
     * set end bottom line
     * 
     * @param endBottomLine
     *            set end bottom line
     */
    public void setEndBottomLine(Line2D endBottomLine) {
        this.endBottomLine = endBottomLine;
    }

    /**
     * get end top line
     * 
     * @return end top line
     */
    public Line2D getEndTopLine() {
        return endTopLine;
    }

    /**
     * set end top line
     * 
     * @param endTopLine
     *            the end top line to set
     */
    public void setEndTopLine(Line2D endTopLine) {
        this.endTopLine = endTopLine;
    }

    /**
     * get end inner line
     * 
     * @return end inner line
     */
    public Line2D getEndInnerLine() {
        return endInnerLine;
    }

    /**
     * set end inner line
     * 
     * @param endInnerLine
     *            the end inner line to set
     */
    public void setEndInnerLine(Line2D endInnerLine) {
        this.endInnerLine = endInnerLine;
    }

    /**
     * get end outer line
     * 
     * @return end inner line
     */
    public Line2D getEndOuterLine() {
        return endOuterLine;
    }

    /**
     * set end outer line
     * 
     * @param endOuterLine
     *            the end outer line to set
     */
    public void setEndOuterLine(Line2D endOuterLine) {
        this.endOuterLine = endOuterLine;
    }

    /**
     * @return the painted
     */
    public boolean isPainted() {
        return painted;
    }

    /**
     * @param painted
     *            the painted to set
     */
    public void setPainted(boolean painted) {
        this.painted = painted;
    }

    /**
     * get sensible area
     * 
     * @return sensible area
     */
    public Area getSensibleArea() {
        Area sensibleArea = new Area();

        if (getTopFace() != null) {
            sensibleArea.add(new Area(getTopFace()));
        }

        for (Donut3DSlice fragment : getFragments()) {
            if (fragment.getType() == Type.Back) {
                if (getInnerFace() != null) {
                    sensibleArea.add(new Area(getInnerFace()));
                }
            }
            else {
                if (getOuterFace() != null) {
                    sensibleArea.add(new Area(getOuterFace()));
                }
            }
        }

        // sensibleArea.add(new Area(getEndFace()));
        // sensibleArea.add(new Area(getStartFace()));

        return sensibleArea;
    }

	/**
	 * @return the isFragment
	 */
	public boolean isFragment() {
		return isFragment;
	}

	/**
	 * @param isFragment the isFragment to set
	 */
	public void setFragment(boolean isFragment) {
		this.isFragment = isFragment;
	}

	/**
	 * @return the parentSlice
	 */
	public Donut3DSlice getParentSlice() {
		return parentSlice;
	}

	/**
	 * @param parentSlice the parentSlice to set
	 */
	public void setParentSlice(Donut3DSlice parentSlice) {
		this.parentSlice = parentSlice;
	}
    
    

}
