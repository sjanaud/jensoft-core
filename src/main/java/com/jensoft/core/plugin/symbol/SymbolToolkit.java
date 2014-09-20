/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.Toolkit;
import com.jensoft.core.plugin.symbol.BarSymbol.MorpheStyle;
import com.jensoft.core.plugin.symbol.BarSymbol.SymbolInflate;
import com.jensoft.core.plugin.symbol.painter.axis.AbstractBarAxisLabel;
import com.jensoft.core.plugin.symbol.painter.draw.AbstractBarDraw;
import com.jensoft.core.plugin.symbol.painter.draw.BarDefaultDraw;
import com.jensoft.core.plugin.symbol.painter.effect.AbstractBarEffect;
import com.jensoft.core.plugin.symbol.painter.effect.BarEffect1;
import com.jensoft.core.plugin.symbol.painter.fill.BarDefaultFill;
import com.jensoft.core.plugin.symbol.painter.fill.AbstractBarFill;
import com.jensoft.core.plugin.symbol.painter.fill.BarFill1;
import com.jensoft.core.plugin.symbol.painter.fill.BarFill2;
import com.jensoft.core.plugin.symbol.painter.label.AbstractBarLabel;

/**
 * BarToolkit is a factory to create commons bar components like :
 * <ul>
 * <li>compatible view with bar plug in
 * <li>
 * <li>bar symbol
 * <li>
 * <li>stacked bar symbol
 * <li>
 * <li>stack and stacks collection
 * <li>
 * <li>group symbol
 * <li>
 * </ul>
 * group symbol, stacked or simple bar symbol,stack.
 * 
 * @see BarSymbol
 * @see StackedBarSymbol
 * @see BarSymbolGroup
 * @see BarSymbolLayer
 * @see AbstractBarFill
 * @see AbstractBarDraw
 * @see AbstractBarAxisLabel
 * @see AbstractBarLabel
 * @author Sebastien Janaud
 */
public class SymbolToolkit extends Toolkit {

   
    /**
     * create a group with specified properties and additional properties like :
     * <ul>
     * <li>Draw
     * <li>
     * <li>Fill
     * <li>
     * <li>Effect
     * <li>
     * <li>Round Style
     * <li>
     * </ul>
     * 
     * @param nameSymbol
     * @param base
     * @param thickness
     * @return bar symbol group
     */
    public final static BarSymbolGroup createBarGroup(String nameSymbol,
            double base, double thickness) {

        BarSymbolGroup group = new BarSymbolGroup(nameSymbol, nameSymbol);
        group.setBase(base);
        group.setThickness(thickness);

        // additional properties
        group.setRound(8);
        group.setMorpheStyle(MorpheStyle.Round);
        group.setBarDraw(new BarDefaultDraw(Color.WHITE));
        group.setBarFill(new BarFill2());
        group.setBarEffect(new BarEffect1());

        return group;

    }

    /**
     * create a group with specified properties and additional properties like :
     * <ul>
     * <li>Draw
     * <li>
     * <li>Fill
     * <li>
     * <li>Effect
     * <li>
     * </ul>
     * 
     * @param nameSymbol
     * @param base
     * @param thickness
     * @return bar symbol group
     */
    public final static BarSymbolGroup createBarGroup(String nameSymbol,
            double base, double thickness, int round) {

        BarSymbolGroup group = new BarSymbolGroup(nameSymbol, nameSymbol);
        group.setBase(base);
        group.setThickness(thickness);
        group.setRound(round);

        // additional properties
        group.setMorpheStyle(MorpheStyle.Round);
        group.setBarDraw(new BarDefaultDraw(Color.WHITE));
        group.setBarFill(new BarFill2());
        group.setBarEffect(new BarEffect1());

        return group;

    }

    /**
     * create a group with specified properties and additional properties.
     * 
     * @param barDraw
     *            the bar draw to set
     * @param barFill
     *            the bar fill to set
     * @param barEffect
     *            the bar effect to set
     * @param nameSymbol
     *            the name symbol to set
     * @param base
     *            the base to set
     * @param thickness
     *            the thickness to set
     * @return bar symbol group
     */
    public final static BarSymbolGroup createBarGroup(String nameSymbol,
            double base, double thickness, AbstractBarDraw barDraw, AbstractBarFill barFill,
            AbstractBarEffect barEffect) {

        BarSymbolGroup group = new BarSymbolGroup(nameSymbol, nameSymbol);
        group.setBase(base);
        group.setThickness(thickness);
        group.setBarDraw(barDraw);
        group.setBarFill(barFill);
        group.setBarEffect(barEffect);

        // additional properties
        group.setRound(8);
        group.setMorpheStyle(MorpheStyle.Round);

        return group;

    }

    /**
     * create a new empty stacked symbol with specified parameters.
     * <p>
     * Use this method without group symbol (base and thickness specified by arguments) that will take the
     * responsibility to set another properties like draw,fill,effect and morphe style
     * </p>
     * 
     * @param nameSymbol
     *            the name symbol to set
     * @param base
     *            the symbol base
     * @param thickness
     *            the symbol thickness
     * @param symbolInflate
     *            the inflate type
     * @param symbolInflateValue
     *            the inflate value, than should be greater than 0
     * @return new stacked symbol
     */
    public final static StackedBarSymbol createStackedBarSymbol(String nameSymbol,
            double base, double thickness, SymbolInflate symbolInflate,
            double symbolInflateValue) {
        StackedBarSymbol stackedSymbol = new StackedBarSymbol(nameSymbol,
                                                              nameSymbol);
        stackedSymbol.setThemeColor(new Color(255, 255, 255));

        stackedSymbol.setBase(base);
        stackedSymbol.setThickness(thickness);

        if (symbolInflate == SymbolInflate.Ascent) {
            stackedSymbol.setAscentValue(symbolInflateValue);
        }
        if (symbolInflate == SymbolInflate.Descent) {
            stackedSymbol.setDescentValue(symbolInflateValue);
        }

        // additional properties
        stackedSymbol.setMorpheStyle(MorpheStyle.Round);
        stackedSymbol.setBarDraw(new BarDefaultDraw(Color.WHITE));
        stackedSymbol.setBarFill(new BarFill1());
        stackedSymbol.setBarEffect(new BarEffect1());

        return stackedSymbol;
    }

    /**
     * create a new empty stacked symbol with specified parameters.
     * <p>
     * Use this method with group symbol that will take the responsibility to set another properties like base and
     * thickness
     * </p>
     * 
     * @param nameSymbol
     *            the name symbol to set
     * @param symbolInflate
     *            the inflate type
     * @param symbolInflateValue
     *            the inflate value, than should be greater than 0
     * @return new stacked symbol
     */
    public final static StackedBarSymbol createStackedBarSymbol(String nameSymbol,
            SymbolInflate symbolInflate, double symbolInflateValue) {
        StackedBarSymbol stackedSymbol = new StackedBarSymbol(nameSymbol,
                                                              nameSymbol);
        stackedSymbol.setThemeColor(new Color(255, 255, 255));

        if (symbolInflate == SymbolInflate.Ascent) {
            stackedSymbol.setAscentValue(symbolInflateValue);
        }
        if (symbolInflate == SymbolInflate.Descent) {
            stackedSymbol.setDescentValue(symbolInflateValue);
        }

        return stackedSymbol;
    }

    /**
     * push specified stacks into specified host stacked Symbol
     * 
     * @param stackedSymbol
     *            the host stacked Symbol for given stacks
     * @param stacks
     *            the stacks to push
     */
    public static void pushStacks(StackedBarSymbol stackedSymbol,
            Stack... stacks) {
        for (int i = 0; i < stacks.length; i++) {
            stackedSymbol.addStack(stacks[i]);
        }
    }

    /**
     * push specified stacks into specified host stacked Symbol
     * 
     * @param stackedSymbol
     *            the host stacked Symbol for given stacks
     * @param stacks
     *            the stacks to push
     */
    public static void pushStacks(StackedBarSymbol stackedSymbol,
            List<Stack> stacks) {
        for (Stack s : stacks) {
            stackedSymbol.addStack(s);
        }
    }

    /**
     * create a new stacked symbol with specified parameters.<br>
     * <p>
     * Use this method without group symbol (base and thickness specified by arguments) that will take the
     * responsibility to set another properties like draw,fill,effect and morphe style
     * </p>
     * </p> {@link Stack} should be provided. see method {@link #createStack(String, Color, double)} and
     * {@link #createStacks(String[], Color[], double[])} </p>
     * 
     * @param nameSymbol
     *            the name symbol to set
     * @param base
     *            the symbol base
     * @param thickness
     *            the symbol thickness
     * @param symbolInflate
     *            the inflate type
     * @param symbolInflateValue
     *            the inflate value, than should be greater than 0
     * @param stacks
     *            the stacks to add in this stacked symbol
     * @return new stacked symbol
     */
    public final static StackedBarSymbol createStackedBarSymbol(String nameSymbol,
            double base, double thickness, SymbolInflate symbolInflate,
            double symbolInflateValue, Stack... stacks) {
        StackedBarSymbol stackedSymbol = new StackedBarSymbol(nameSymbol,
                                                              nameSymbol);
        stackedSymbol.setThemeColor(new Color(255, 255, 255));

        stackedSymbol.setBase(base);
        stackedSymbol.setThickness(thickness);

        if (symbolInflate == SymbolInflate.Ascent) {
            stackedSymbol.setAscentValue(symbolInflateValue);
        }
        if (symbolInflate == SymbolInflate.Descent) {
            stackedSymbol.setDescentValue(symbolInflateValue);
        }

        // additional properties
        stackedSymbol.setMorpheStyle(MorpheStyle.Round);
        stackedSymbol.setBarDraw(new BarDefaultDraw(Color.WHITE));
        stackedSymbol.setBarFill(new BarFill1());
        stackedSymbol.setBarEffect(new BarEffect1());

        for (int i = 0; i < stacks.length; i++) {
            stackedSymbol.addStack(stacks[i]);
        }

        return stackedSymbol;
    }

    /**
     * create a new stacked symbol with specified parameters.<br>
     * <p>
     * Use this method without group symbol (base and thickness specified by arguments) that will take the
     * responsibility to set another properties like draw,fill,effect and morphe style
     * </p>
     * </p> {@link Stack} should be provided. see method {@link #createStack(String, Color, double)} and
     * {@link #createStacks(String[], Color[], double[])} </p>
     * 
     * @param nameSymbol
     *            the name symbol to set
     * @param base
     *            the symbol base
     * @param thickness
     *            the symbol thickness
     * @param symbolInflate
     *            the inflate type
     * @param symbolInflateValue
     *            the inflate value, than should be greater than 0
     * @param stacks
     *            the stacks to add in this stacked symbol
     * @return new stacked symbol
     */
    public final static StackedBarSymbol createStackedBarSymbol(String nameSymbol,
            double base, double thickness, SymbolInflate symbolInflate,
            double symbolInflateValue, List<Stack> stacks) {
        StackedBarSymbol stackedSymbol = new StackedBarSymbol(nameSymbol,
                                                              nameSymbol);
        stackedSymbol.setThemeColor(new Color(255, 255, 255));

        stackedSymbol.setBase(base);
        stackedSymbol.setThickness(thickness);

        if (symbolInflate == SymbolInflate.Ascent) {
            stackedSymbol.setAscentValue(symbolInflateValue);
        }
        if (symbolInflate == SymbolInflate.Descent) {
            stackedSymbol.setDescentValue(symbolInflateValue);
        }

        // additional properties
        stackedSymbol.setMorpheStyle(MorpheStyle.Round);
        stackedSymbol.setBarDraw(new BarDefaultDraw(Color.WHITE));
        stackedSymbol.setBarFill(new BarFill1());
        stackedSymbol.setBarEffect(new BarEffect1());

        for (int i = 0; i < stacks.size(); i++) {
            stackedSymbol.addStack(stacks.get(i));
        }

        return stackedSymbol;
    }

    /**
     * create a new stacked symbol with specified parameters.<br>
     * <p>
     * Use this method with group symbol that will take the responsibility to set another properties like base and
     * thickness
     * </p>
     * </p> {@link Stack} should be provided. see method {@link #createStack(String, Color, double)} and
     * {@link #createStacks(String[], Color[], double[])} </p>
     * 
     * @param nameSymbol
     *            the name symbol to set
     * @param symbolInflate
     *            the inflate type
     * @param symbolInflateValue
     *            the inflate value, than should be greater than 0
     * @param stacks
     *            the stacks to add in this stacked symbol
     * @return new stacked symbol
     */
    public final static StackedBarSymbol createStackedBarSymbol(String nameSymbol,
            SymbolInflate symbolInflate, double symbolInflateValue,
            Stack... stacks) {
        StackedBarSymbol stackedSymbol = new StackedBarSymbol(nameSymbol,
                                                              nameSymbol);
        stackedSymbol.setThemeColor(new Color(255, 255, 255));

        if (symbolInflate == SymbolInflate.Ascent) {
            stackedSymbol.setAscentValue(symbolInflateValue);
        }
        if (symbolInflate == SymbolInflate.Descent) {
            stackedSymbol.setDescentValue(symbolInflateValue);
        }

        for (int i = 0; i < stacks.length; i++) {
            stackedSymbol.addStack(stacks[i]);
        }

        return stackedSymbol;
    }

    /**
     * create new stack
     * 
     * @param name
     *            the stack name to set
     * @param themeColor
     *            the stack theme color to set
     * @param proportionValue
     *            the stack proportion value
     * @return new stack
     */
    public final static Stack createStack(String name, Color themeColor,
            double proportionValue) {
        if (proportionValue < 0) {
            throw new IllegalArgumentException(
                                               "stack proportion value should be greater than 0");
        }
        Stack stack = new Stack(name, themeColor, proportionValue);
        return stack;

    }

    /**
     * create new stack list
     * 
     * @param names
     *            the stack name array
     * @param themeColors
     *            the stack theme colors array
     * @param proportionValues
     *            the stack proportion values array
     * @return new stack list
     */
    public final static List<Stack> createStacks(String[] names,
            Color[] themeColors, double[] proportionValues) {
        if (names.length != themeColors.length
                || names.length != proportionValues.length) {
            throw new IllegalArgumentException(
                                               "properties array length does not match !");
        }
        List<Stack> stacks = new ArrayList<Stack>();
        int len = names.length;
        for (int i = 0; i < len; i++) {
            stacks.add(createStack(names[i], themeColors[i],
                                   proportionValues[i]));
        }

        return stacks;

    }

    /**
     * create symbol with specified given parameters and additional properties
     * <ul>
     * <li>Draw</li>
     * <li>Fill</li>
     * <li>Effect</li>
     * <li>Round Morphe style</li>
     * </ul>
     * 
     * @param nameSymbol
     *            the name symbol to set
     * @param themeColor
     *            the theme color to set *
     * @param symbolInflate
     *            the symbol inflate type
     * @param symbolInflateValue
     *            the symbol inflate value
     * @return new bar symbol
     */
    public final static BarSymbol createBarSymbol(String nameSymbol,
            Color themeColor, SymbolInflate symbolInflate,
            double symbolInflateValue) {

        BarSymbol b1 = new BarSymbol(nameSymbol, nameSymbol);
        b1.setThemeColor(themeColor);

        if (symbolInflate == SymbolInflate.Ascent) {
            b1.setAscentValue(symbolInflateValue);
        }
        if (symbolInflate == SymbolInflate.Descent) {
            b1.setDescentValue(symbolInflateValue);
        }

        b1.setMorpheStyle(MorpheStyle.Round);
        b1.setBarDraw(new BarDefaultDraw(new Color(255, 255, 255)));
        b1.setBarFill(new BarDefaultFill());
        b1.setBarEffect(new BarEffect1());

        return b1;
    }

    /**
     * create symbol with specified given parameters and additional properties
     * <ul>
     * <li>Draw</li>
     * <li>Fill</li>
     * <li>Effect</li>
     * <li>Round Morphe style</li>
     * </ul>
     * 
     * @param nameSymbol
     *            the name symbol to set
     * @param themeColor
     *            the theme color to set
     * @param thickness
     *            the thickness pixel to set
     * @param base
     *            the base to set
     * @param symbolInflate
     *            the symbol inflate type
     * @param symbolInflateValue
     *            the symbol inflate value
     * @return new bar symbol
     */
    public final static BarSymbol createBarSymbol(String nameSymbol,
            Color themeColor, double thickness, double base,
            SymbolInflate symbolInflate, double symbolInflateValue) {

        BarSymbol b1 = new BarSymbol(nameSymbol, nameSymbol);
        b1.setThemeColor(themeColor);
        b1.setThickness(thickness);
        b1.setBase(base);

        if (symbolInflate == SymbolInflate.Ascent) {
            b1.setAscentValue(symbolInflateValue);
        }
        if (symbolInflate == SymbolInflate.Descent) {
            b1.setDescentValue(symbolInflateValue);
        }

        b1.setMorpheStyle(MorpheStyle.Round);
        b1.setBarDraw(new BarDefaultDraw(new Color(255, 255, 255)));
        b1.setBarFill(new BarDefaultFill());
        b1.setBarEffect(new BarEffect1());

        return b1;
    }

    /**
     * create symbol with specified given parameters
     * 
     * @param nameSymbol
     *            the name symbol to set
     * @param themeColor
     *            the theme color to set
     * @param thickness
     *            the thickness pixel to set
     * @param base
     *            the base to set
     * @param symbolInflate
     *            the symbol inflate type
     * @param symbolInflateValue
     *            the symbol inflate value
     * @param barDraw
     *            the bar draw to set
     * @param barFill
     *            the bar fill to set
     * @param barEffect
     *            the bar effect to set
     * @return new bar symbol
     */
    public final static BarSymbol createBarSymbol(String nameSymbol,
            Color themeColor, double thickness, double base,
            SymbolInflate symbolInflate, double symbolInflateValue,
            AbstractBarDraw barDraw, AbstractBarFill barFill, AbstractBarEffect barEffect) {

        BarSymbol b1 = new BarSymbol(nameSymbol, nameSymbol);
        b1.setThemeColor(themeColor);
        b1.setThickness(thickness);
        b1.setBase(base);

        if (symbolInflate == SymbolInflate.Ascent) {
            b1.setAscentValue(symbolInflateValue);
        }
        if (symbolInflate == SymbolInflate.Descent) {
            b1.setDescentValue(symbolInflateValue);
        }

        b1.setMorpheStyle(MorpheStyle.Round);

        b1.setBarDraw(barDraw);
        b1.setBarFill(barFill);
        b1.setBarEffect(barEffect);

        return b1;
    }

    /**
     * create the point symbol with specified value
     * 
     * @param value
     *            the point symbol value
     * @return point symbol
     */
    public static PointSymbol createPointSymbol(double value) {
        PointSymbol ps = new PointSymbol(value);
        return ps;
    }

    /**
     * create the polyline point symbol
     * 
     * @return polyline point symbol
     */
    public static PolylinePointSymbol createPolylinePointSymbol() {
        PolylinePointSymbol polyline = new PolylinePointSymbol();
        return polyline;
    }

    /**
     * push specified point into specified polyline symbol
     * 
     * @param polylineSymbol
     *            the host polyline Symbol for given points
     * @param pointSymbols
     *            the point symbols to push
     */
    public static void pushPoints(PolylinePointSymbol polylineSymbol,
            PointSymbol... pointSymbols) {
        for (int i = 0; i < pointSymbols.length; i++) {
            polylineSymbol.addSymbol(pointSymbols[i]);
        }
    }

    /**
     * push specified point into specified polyline symbol
     * 
     * @param polylineSymbol
     *            the host polyline Symbol for given points
     * @param pointSymbols
     *            the point symbols to push
     */
    public static void pushPoints(PolylinePointSymbol polylineSymbol,
            List<PointSymbol> pointSymbols) {
        for (PointSymbol ps : pointSymbols) {
            polylineSymbol.addSymbol(ps);
        }
    }

}
