/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.palette.color.ColorPalette;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import com.jensoft.core.plugin.symbol.painter.draw.AbstractBarDraw;
import com.jensoft.core.plugin.symbol.painter.effect.AbstractBarEffect;
import com.jensoft.core.plugin.symbol.painter.fill.AbstractBarFill;

/**
 * <code>BarSymbolGroup</code> is a symbol that host other symbol, it has two
 * main responsibility:
 * <ul>
 * <li>diffuse properties to hosted symbol</li>
 * <li>lay out hosted symbol</li>
 * </ul>
 * 
 * @author Sebastien Janaud
 */
public class BarSymbolGroup extends BarSymbol {

    /** bar members */
    private List<BarSymbol> symbolComponents = new ArrayList<BarSymbol>();

    /** group symbol */
    public String symbol;

    /** theme color */
    private Color themeColor;

    /** bar thickness */
    private double barThickness;

    /** flag on theme color set */
    private boolean isThemeColorSet = false;

    /** group base */
    private double base;

    /** flag on base set */
    private boolean isBaseSet = false;

    /** round */
    private int round = 5;

    /** flag on round set */
    private boolean isRoundSet = false;

    /** Morphe style */
    private MorpheStyle morpheStyle = MorpheStyle.Rectangle;

    /** flag on round set */
    private boolean isThicknessSet = false;

    /** Bar draw */
    private AbstractBarDraw barDraw;

    /** flag on bar draw set */
    private boolean isBarDrawSet = false;

    /** Bar fill */
    private AbstractBarFill barFill;

    /** flag on bar fill set */
    private boolean isBarFillSet = false;

    /** Bar effect */
    private AbstractBarEffect barEffect;

    /** flag on bar effect set */
    private boolean isBarEffectSet = false;

    /**
     * Bar Symbol Group
     */
    public BarSymbolGroup() {
    }

    /**
     * Bar Symbol Group
     * 
     * @param symbol
     *            the symbol and name to set
     */
    public BarSymbolGroup(String symbol) {
        setName(symbol);
        setSymbol(symbol);
    }

    /**
     * Bar Symbol Group
     * 
     * @param name
     *            name to set
     * @param symbol
     *            symbol to set
     */
    public BarSymbolGroup(String name, String symbol) {
        setName(name);
        setSymbol(symbol);
    }

    /**
     * copy all set property to bar members.
     */
    public void copyToBar() {
        copyBaseToBar(getBase());
        copyDrawToBar(getBarDraw());
        copyEffectStyleToBar(getBarEffect());
        copyFillStyleToBar(getBarFill());
        copyMorpheStyleToBar(getMorpheStyle());
        copyRoundToBar(getRound());
        copyThemeColorToBar(getThemeColor());
        copyBarThicknessToBar(getBarThickness());
    }

    @Override
    public SymbolNature getNature() {
        return getHost().getNature();
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * get the theme color
     */
    @Override
    public Color getThemeColor() {
        if (themeColor == null) {
            themeColor = ColorPalette.getRandomColor();
        }
        return themeColor;
    }

    /**
     * @return the isThemeColorSet
     */
    public boolean isThemeColorSet() {
        return isThemeColorSet;
    }

    /**
     * @param isThemeColorSet
     *            the isThemeColorSet to set
     */
    public void setThemeColorSet(boolean isThemeColorSet) {
        this.isThemeColorSet = isThemeColorSet;
    }

    /**
     * set theme color and flag {@link #isThemeColorSet} to true
     * 
     * @param themeColor
     *            the theme color to set
     */
    @Override
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
        setThemeColorSet(true);
    }

    /**
     * copy the theme color to bar members if {@link #isThemeColorSet()} get
     * true
     * 
     * @param themeColor
     *            the theme color to copy
     */
    public void copyThemeColorToBar(Color themeColor) {
        if (!isThemeColorSet()) {
            return;
        }
        for (SymbolComponent b : symbolComponents) {
            if (b instanceof BarSymbol) {
                ((BarSymbol) b).setThemeColor(themeColor);
            }
        }
    }

    /**
     * get the base
     */
    @Override
    public double getBase() {
        return base;
    }

    /**
     * @return the isBaseSet
     */
    @Override
    public boolean isBaseSet() {
        return isBaseSet;
    }

    /**
     * @param isBaseSet
     *            the isBaseSet to set
     */
    public void setBaseSet(boolean isBaseSet) {
        this.isBaseSet = isBaseSet;
    }

    /**
     * set base for this group and flag {@link #isBaseSet} to true
     */
    @Override
    public void setBase(double base) {
        this.base = base;
        setBaseSet(true);
    }

    /**
     * copy base property for each bar member if {@link #isBaseSet()} get true.
     * 
     * @param base
     *            the base to copy.
     */
    public void copyBaseToBar(double base) {
        if (!isBaseSet()) {
            return;
        }
        for (SymbolComponent b : symbolComponents) {
            if (b instanceof BarSymbol) {
                ((BarSymbol) b).setBase(base);
            }
        }
    }

    /**
     * return the thickness of this group
     * the thickness for this group is the sum of children bar symbol
     */
    @Override
    public double getThickness() {
        double groupThickness = 0;
        for (SymbolComponent b : symbolComponents) {
            groupThickness = groupThickness + b.getThickness();
        }
        return groupThickness;
    }

    /**
     * get max value in device coordinate for this group in the scalar dimension
     * 
     * @return the max value in device coordinate
     */
    public double getMaxValue() {
        double max = -1;
        boolean setMax = false;
        for (BarSymbol b : getSymbolComponents()) {
            if (!b.isFiller()) {
                Rectangle2D rect2D = b.getBarShape().getBounds2D();
                if (getNature() == SymbolNature.Vertical) {
                    if (!setMax) {
                        max = rect2D.getMaxY();
                        setMax = true;
                    }
                    max = Math.max(max, rect2D.getMaxY());
                }
                else if (getNature() == SymbolNature.Horizontal) {
                    if (!setMax) {
                        max = rect2D.getMaxX();
                        setMax = true;
                    }
                    max = Math.max(max, rect2D.getMaxX());
                }
            }
        }
        return max;
    }

    /**
     * get min value in device coordinate for this group in the scalar dimension
     * 
     * @return the min value in device coordinate
     */
    public double getMinValue() {
        double min = -1;
        boolean setMin = false;
        for (BarSymbol b : getSymbolComponents()) {
            if (!b.isFiller()) {
                Rectangle2D rect2D = b.getBarShape().getBounds2D();
                if (getNature() == SymbolNature.Vertical) {
                    if (!setMin) {
                        min = rect2D.getMinY();
                        setMin = true;
                    }
                    min = Math.min(min, rect2D.getMinY());
                }
                else if (getNature() == SymbolNature.Horizontal) {
                    if (!setMin) {
                        min = rect2D.getMinX();
                        setMin = true;
                    }
                    min = Math.min(min, rect2D.getMinX());
                }
            }
        }
        return min;
    }

    /**
     * get center value in device coordinate for this group in the scalar
     * dimension
     * 
     * @return the center value in device coordinate
     */
    public double getCenterValue() {
        double max = getMaxValue();
        double min = getMinValue();
        return min + Math.abs(max - min) / 2;
    }

    /**
     * get the bar group virtual shape the bar shape for this group is the
     * bounding rectangle which contains all bar symbol
     */
    @Override
    public Shape getBarShape() {
        Area a = new Area();
        double max = getMaxValue();
        double min = getMinValue();

        for (BarSymbol b : getSymbolComponents()) {
            if (!b.isFiller()) {
                a.add(new Area(b.getBarShape()));
            }
            else if (b.isFiller() && b.getFillerType() == FillerType.Strut) {
                Rectangle2D strutShape = null;
                if (getNature() == SymbolNature.Vertical) {
                    strutShape = new Rectangle2D.Double(b.getLocationX(), max,
                                                        b.getThickness(), max - min);
                }
                else if (getNature() == SymbolNature.Horizontal) {
                    strutShape = new Rectangle2D.Double(min, b.getLocationY(),
                                                        max - min, b.getThickness());
                }
                // a.add(new Area(strutShape));
            }
        }
        return a.getBounds2D();
    }

    /**
     * @return the isThicknessSet
     */
    public boolean isThicknessSet() {
        return isThicknessSet;
    }

    /**
     * @param isThicknessSet
     *            the isThicknessSet to set
     */
    public void setThicknessSet(boolean isThicknessSet) {
        this.isThicknessSet = isThicknessSet;
    }

    /**
     * @return the barThickness
     */
    private double getBarThickness() {
        return barThickness;
    }

    @Override
    public void setThickness(double thickness) {
        barThickness = thickness;
        setThicknessSet(true);
    }

    /**
     * copy specified bar thickness to registered bar symbol, exclude filler
     * 
     * @param thickness
     *            the thickness to set for each registered in this group
     */
    public void copyBarThicknessToBar(double thickness) {
        if (!isThicknessSet()) {
            return;
        }
        for (SymbolComponent b : symbolComponents) {
            if (b instanceof BarSymbol && !b.isFiller()) {
                ((BarSymbol) b).setThickness(thickness);
            }
        }
    }

    /**
     * get the round of round shape style
     */
    @Override
    public int getRound() {
        return round;
    }

    /**
     * set round for morphe style round
     * 
     * @param round
     *            the round to set
     */
    @Override
    public void setRound(int round) {
        this.round = round;
        setRoundSet(true);
    }

    /**
     * @return the isRoundSet
     */
    public boolean isRoundSet() {
        return isRoundSet;
    }

    /**
     * @param isRoundSet
     *            the isRoundSet to set
     */
    public void setRoundSet(boolean isRoundSet) {
        this.isRoundSet = isRoundSet;
    }

    /**
     * copy specified round to bar members if {@link #isRoundSet()} get true
     * 
     * @param round
     *            the round to copy
     */
    public void copyRoundToBar(int round) {
        if (!isRoundSet()) {
            return;
        }
        for (SymbolComponent b : symbolComponents) {
            if (b instanceof BarSymbol) {
                ((BarSymbol) b).setRound(round);
            }
        }
    }

    @Override
    public MorpheStyle getMorpheStyle() {
        return morpheStyle;
    }

    @Override
    public void setMorpheStyle(MorpheStyle morpheStyle) {
        this.morpheStyle = morpheStyle;
        copyMorpheStyleToBar(morpheStyle);
    }

    public void copyMorpheStyleToBar(MorpheStyle morpheStyle) {
        for (SymbolComponent b : symbolComponents) {
            if (b instanceof BarSymbol) {
                ((BarSymbol) b).setMorpheStyle(morpheStyle);
            }
        }
    }

    @Override
    public AbstractBarDraw getBarDraw() {
        return barDraw;
    }

    /**
     * @return the isBarDrawSet
     */
    public boolean isBarDrawSet() {
        return isBarDrawSet;
    }

    /**
     * @param isBarDrawSet
     *            the isBarDrawSet to set
     */
    public void setBarDrawSet(boolean isBarDrawSet) {
        this.isBarDrawSet = isBarDrawSet;
    }

    /**
     * set bar draw and flag {@link #isBarDrawSet} to true
     */
    @Override
    public void setBarDraw(AbstractBarDraw barDraw) {
        this.barDraw = barDraw;
        setBarDrawSet(true);
    }

    /**
     * copy draw to bar members if {@link #isBarDrawSet()} get true
     * 
     * @param barDraw
     *            the bar draw to copy
     */
    public void copyDrawToBar(AbstractBarDraw barDraw) {
        if (!isBarDrawSet()) {
            return;
        }
        for (SymbolComponent b : symbolComponents) {
            if (b instanceof BarSymbol) {
                ((BarSymbol) b).setBarDraw(barDraw);
            }
        }
    }

    /**
     * get the bar fill
     */
    @Override
    public AbstractBarFill getBarFill() {
        return barFill;
    }

    /**
     * @return the isBarFillSet
     */
    public boolean isBarFillSet() {
        return isBarFillSet;
    }

    /**
     * @param isBarFillSet
     *            the isBarFillSet to set
     */
    public void setBarFillSet(boolean isBarFillSet) {
        this.isBarFillSet = isBarFillSet;
    }

    /**
     * set bar fill
     * 
     * @param barFill
     */
    @Override
    public void setBarFill(AbstractBarFill barFill) {
        this.barFill = barFill;
        setBarFillSet(true);
    }

    /**
     * copy fill to bar members if {@link #isBarFillSet()} get true
     * 
     * @param barFill
     *            the bar fill to copy
     */
    public void copyFillStyleToBar(AbstractBarFill barFill) {
        if (!isBarFillSet()) {
            return;
        }
        for (SymbolComponent b : symbolComponents) {
            if (b instanceof BarSymbol) {
                ((BarSymbol) b).setBarFill(barFill);
            }
        }
    }

    @Override
    public AbstractBarEffect getBarEffect() {
        return barEffect;
    }

    /**
     * set bar effect
     * 
     * @param barEffect
     *            the bar effect to set
     */
    @Override
    public void setBarEffect(AbstractBarEffect barEffect) {
        this.barEffect = barEffect;
        setBarEffectSet(true);
    }

    /**
     * @return the isBarEffectSet
     */
    public boolean isBarEffectSet() {
        return isBarEffectSet;
    }

    /**
     * @param isBarEffectSet
     *            the isBarEffectSet to set
     */
    public void setBarEffectSet(boolean isBarEffectSet) {
        this.isBarEffectSet = isBarEffectSet;
    }

    /**
     * copy the bar effect to bar members if {@link #isBarEffectSet()} get true
     * 
     * @param barEffect
     *            the barEffect to set
     */
    public void copyEffectStyleToBar(AbstractBarEffect barEffect) {
        if (!isBarEffectSet()) {
            return;
        }
        for (SymbolComponent b : symbolComponents) {
            if (b instanceof BarSymbol) {
                ((BarSymbol) b).setBarEffect(barEffect);
            }
        }
    }

    /**
     * add symbol component
     * 
     * @param symbol
     *            the symbol to add
     * @throws IllegalArgumentException
     *             if glue is add in this group
     */
    public void addSymbol(BarSymbol symbol) {
        if (symbol.isFiller() && symbol.getFillerType() == FillerType.Glue) {
            throw new IllegalArgumentException("Glue can not be add in group.");
        }
        symbolComponents.add(symbol);
    }

    /**
     * remove symbol component
     * 
     * @param symbol
     *            the symbol to remove
     */
    public void removeSymbolComponent(SymbolComponent symbol) {
        symbolComponents.remove(symbol);
    }

    /**
     * get symbol components
     * 
     * @return symbol components
     */
    public List<BarSymbol> getSymbolComponents() {
        return symbolComponents;
    }

    /**
     * set symbols components to set
     * 
     * @param symbolComponents
     */
    public void setSymbolComponents(List<BarSymbol> symbolComponents) {
        this.symbolComponents = symbolComponents;
    }

}
