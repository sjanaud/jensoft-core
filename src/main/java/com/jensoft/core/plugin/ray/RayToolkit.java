/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.Toolkit;
import com.jensoft.core.plugin.ray.Ray.RayInflate;
import com.jensoft.core.plugin.ray.Ray.RayNature;
import com.jensoft.core.plugin.ray.Ray.ThicknessType;
import com.jensoft.core.plugin.ray.painter.draw.RayDefaultDraw;
import com.jensoft.core.plugin.ray.painter.effect.RayEffect1;
import com.jensoft.core.plugin.ray.painter.fill.RayFill1;

/**
 * a factory to create ray objects
 */
public class RayToolkit extends Toolkit {

    /**
     * create compatible ray view
     * 
     * @param minx
     *            the minimum x to set
     * @param maxx
     *            the maximum x to set
     * @param miny
     *            the minimum y to set
     * @param maxy
     *            the maximum y to set
     * @return the compatible view
     */
    public static RayView createCompatibleView(double minx, double maxx,
            double miny, double maxy) {
        RayView rayView = new RayView(minx, maxx, miny, maxy);
        return rayView;
    }

    /**
     * create ray with specified given parameters
     * 
     * @param name
     *            the name to set
     * @param themeColor
     *            the theme color to set
     * @param rayNature
     *            the ray nature to set, should be an XRay nature or YRay nature
     * @param rayLocation
     *            the ray location defines the x or y location according to ray
     *            nature, the ray location is the center of the ray
     * @param rayInflate
     *            the ray inflate defines ascent or descent style
     * @param rayBase
     *            the ray base is the base of the ray in x or y dimension
     * @param rayInflateValue
     *            the ray inflate value, should be greater than zero
     * @param thicknessType
     *            the thickness type, should be in device (pixel) or user
     *            coordinate system
     * @param thickness
     *            the thickness value
     * @return the new created ray
     */
    public static Ray createRay(String name, Color themeColor,
            RayNature rayNature, double rayLocation, RayInflate rayInflate,
            double rayBase, double rayInflateValue,
            ThicknessType thicknessType, double thickness) {
        Ray ray = new Ray();
        ray.setName(name);

        ray.setRayNature(rayNature);
        ray.setRay(rayLocation);

        ray.setThicknessType(thicknessType);
        ray.setThickness(thickness);

        ray.setRayBase(rayBase);
        if (rayInflate == RayInflate.Ascent) {
            ray.setAscentValue(rayInflateValue);
        }
        if (rayInflate == RayInflate.Descent) {
            ray.setDescentValue(rayInflateValue);
        }

        ray.setThemeColor(themeColor);

        ray.setRayDraw(new RayDefaultDraw(Color.WHITE));
        ray.setRayFill(new RayFill1());
        ray.setRayEffect(new RayEffect1());
        return ray;
    }

    /**
     * create a ray with specified given parameters and preset properties<br>
     * <ul>
     * <li>XRay nature</li>
     * <li>Ascent Inflate style</li> *
     * </ul>
     * 
     * @param name
     *            the name to set
     * @param themeColor
     *            the theme color to set
     * @param rayLocation
     *            the ray location defines the x or y location according to ray
     *            nature, the ray location is the center of the ray
     * @param rayBase
     *            the ray base is the base of the ray in x or y dimension
     * @param ascentValue
     *            the ray ascent value, should be greater than zero
     * @param thicknessType
     *            the thickness type, should be in device (pixel) or user
     *            coordinate system
     * @param thickness
     *            the thickness value
     * @return the new created ray
     */
    public static Ray createXRayAscent(String name, Color themeColor,
            double rayLocation, double rayBase, double ascentValue,
            ThicknessType thicknessType, double thickness) {
        return createRay(name, themeColor, RayNature.XRay, rayLocation,
                         RayInflate.Ascent, rayBase, ascentValue, thicknessType,
                         thickness);
    }

    /**
     * create a ray with specified given parameters and preset properties<br>
     * <ul>
     * <li>XRay nature</li>
     * <li>Descent Inflate style</li> *
     * </ul>
     * 
     * @param name
     *            the name to set
     * @param themeColor
     *            the theme color to set
     * @param rayLocation
     *            the ray location defines the x or y location according to ray
     *            nature, the ray location is the center of the ray
     * @param rayBase
     *            the ray base is the base of the ray in x or y dimension
     * @param descentValue
     *            the ray inflate value, should be greater than zero
     * @param thicknessType
     *            the thickness type, should be in device (pixel) or user
     *            coordinate system
     * @param thickness
     *            the thickness value
     * @return the new created ray
     */
    public static Ray createXRayDescent(String name, Color themeColor,
            double rayLocation, double rayBase, double descentValue,
            ThicknessType thicknessType, double thickness) {
        return createRay(name, themeColor, RayNature.XRay, rayLocation,
                         RayInflate.Descent, rayBase, descentValue, thicknessType,
                         thickness);
    }

    /**
     * create a ray with specified given parameters and preset properties<br>
     * <ul>
     * <li>YRay nature</li>
     * <li>Ascent Inflate style</li> *
     * </ul>
     * 
     * @param name
     *            the name to set
     * @param themeColor
     *            the theme color to set
     * @param rayLocation
     *            the ray location defines the x or y location according to ray
     *            nature, the ray location is the center of the ray
     * @param rayBase
     *            the ray base is the base of the ray in x or y dimension
     * @param ascentValue
     *            the ray inflate value, should be greater than zero
     * @param thicknessType
     *            the thickness type, should be in device (pixel) or user
     *            coordinate system
     * @param thickness
     *            the thickness value
     * @return the new created ray
     */
    public static Ray createYRayAscent(String name, Color themeColor,
            double rayLocation, double rayBase, double ascentValue,
            ThicknessType thicknessType, double thickness) {
        return createRay(name, themeColor, RayNature.YRay, rayLocation,
                         RayInflate.Ascent, rayBase, ascentValue, thicknessType,
                         thickness);
    }

    /**
     * create a ray with specified given parameters and preset properties<br>
     * <ul>
     * <li>YRay nature</li>
     * <li>Descent Inflate style</li> *
     * </ul>
     * 
     * @param name
     *            the name to set
     * @param themeColor
     *            the theme color to set
     * @param rayLocation
     *            the ray location defines the x or y location according to ray
     *            nature, the ray location is the center of the ray
     * @param rayBase
     *            the ray base is the base of the ray in x or y dimension
     * @param descentValue
     *            the ray inflate value, should be greater than zero
     * @param thicknessType
     *            the thickness type, should be in device (pixel) or user
     *            coordinate system
     * @param thickness
     *            the thickness value
     * @return the new created ray
     */
    public static Ray createYRayDescent(String name, Color themeColor,
            double rayLocation, double rayBase, double descentValue,
            ThicknessType thicknessType, double thickness) {
        return createRay(name, themeColor, RayNature.YRay, rayLocation,
                         RayInflate.Descent, rayBase, descentValue, thicknessType,
                         thickness);
    }

    /**
     * create empty stacked ray with specified given parameters
     * 
     * @param name
     *            the name to set
     * @param themeColor
     *            the theme color to set
     * @param rayNature
     *            the ray nature to set, should be an XRay nature or YRay nature
     * @param rayLocation
     *            the ray location defines the x or y location according to ray
     *            nature, the ray location is the center of the ray
     * @param rayInflate
     *            the ray inflate defines ascent or descent style
     * @param rayBase
     *            the ray base is the base of the ray in x or y dimension
     * @param rayInflateValue
     *            the ray inflate value, should be greater than zero
     * @param thicknessType
     *            the thickness type, should be in device (pixel) or user
     *            coordinate system
     * @param thickness
     *            the thickness value
     * @return the new created stacked ray
     */
    public static StackedRay createStackedRay(String name, Color themeColor,
            RayNature rayNature, double rayLocation, RayInflate rayInflate,
            double rayBase, double rayInflateValue,
            ThicknessType thicknessType, double thickness) {

        StackedRay sray = new StackedRay();
        sray.setName(name);

        sray.setRayNature(rayNature);
        sray.setRay(rayLocation);

        sray.setThicknessType(thicknessType);
        sray.setThickness(thickness);

        sray.setRayBase(rayBase);
        if (rayInflate == RayInflate.Ascent) {
            sray.setAscentValue(rayInflateValue);
        }
        if (rayInflate == RayInflate.Descent) {
            sray.setDescentValue(rayInflateValue);
        }

        sray.setThemeColor(themeColor);

        sray.setRayDraw(new RayDefaultDraw(Color.WHITE));

        return sray;
    }

    /**
     * create empty stacked ray with specified given parameters
     * 
     * @param name
     *            the name to set
     * @param themeColor
     *            the theme color to set
     * @param rayNature
     *            the ray nature to set, should be an XRay nature or YRay nature
     * @param rayLocation
     *            the ray location defines the x or y location according to ray
     *            nature, the ray location is the center of the ray
     * @param rayInflate
     *            the ray inflate defines ascent or descent style
     * @param rayBase
     *            the ray base is the base of the ray in x or y dimension
     * @param rayInflateValue
     *            the ray inflate value, should be greater than zero
     * @param thicknessType
     *            the thickness type, should be in device (pixel) or user
     *            coordinate system
     * @param thickness
     *            the thickness value
     * @param stacks
     *            the stacks array to add in ray
     * @return the new created stacked ray
     */
    public static StackedRay createStackedRay(String name, Color themeColor,
            RayNature rayNature, double rayLocation, RayInflate rayInflate,
            double rayBase, double rayInflateValue,
            ThicknessType thicknessType, double thickness, RayStack... stacks) {
        StackedRay sray = createStackedRay(name, themeColor, rayNature,
                                           rayLocation, rayInflate, rayBase, rayInflateValue,
                                           thicknessType, thickness);

        for (int i = 0; i < stacks.length; i++) {
            sray.addStack(stacks[i]);
        }

        return sray;
    }

    /**
     * create empty stacked ray with specified given parameters
     * 
     * @param name
     *            the name to set
     * @param themeColor
     *            the theme color to set
     * @param rayNature
     *            the ray nature to set, should be an XRay nature or YRay nature
     * @param rayLocation
     *            the ray location defines the x or y location according to ray
     *            nature, the ray location is the center of the ray
     * @param rayInflate
     *            the ray inflate defines ascent or descent style
     * @param rayBase
     *            the ray base is the base of the ray in x or y dimension
     * @param rayInflateValue
     *            the ray inflate value, should be greater than zero
     * @param thicknessType
     *            the thickness type, should be in device (pixel) or user
     *            coordinate system
     * @param thickness
     *            the thickness value
     * @param stacks
     *            the stacks list to add in ray
     * @return the new created stacked ray
     */
    public static StackedRay createStackedRay(String name, Color themeColor,
            RayNature rayNature, double rayLocation, RayInflate rayInflate,
            double rayBase, double rayInflateValue,
            ThicknessType thicknessType, double thickness, List<RayStack> stacks) {
        StackedRay sray = createStackedRay(name, themeColor, rayNature,
                                           rayLocation, rayInflate, rayBase, rayInflateValue,
                                           thicknessType, thickness);

        for (int i = 0; i < stacks.size(); i++) {
            sray.addStack(stacks.get(i));
        }

        return sray;
    }

    /**
     * create new ray stack
     * 
     * @param name
     *            the ray stack name to set
     * @param themeColor
     *            the ray stack theme color to set
     * @param proportionValue
     *            the ray stack proportion value
     * @return new ray stack
     */
    public final static RayStack createStack(String name, Color themeColor,
            double proportionValue) {
        if (proportionValue < 0) {
            throw new IllegalArgumentException(
                                               "stack proportion value should be greater than 0");
        }
        RayStack stack = new RayStack(name, themeColor, proportionValue);
        return stack;
    }

    /**
     * create new ray stack list
     * 
     * @param names
     *            the ray stack name array
     * @param themeColor
     *            the ray stack theme colors array
     * @param proportionValue
     *            the ray stack proportion values array
     * @return new ray stack list
     */
    public final static List<RayStack> createStacks(String[] names,
            Color[] themeColors, double[] proportionValues) {
        if (names.length != themeColors.length
                || names.length != proportionValues.length) {
            throw new IllegalArgumentException(
                                               "properties array length does not match !");
        }
        List<RayStack> stacks = new ArrayList<RayStack>();
        int len = names.length;
        for (int i = 0; i < len; i++) {
            stacks.add(createStack(names[i], themeColors[i],
                                   proportionValues[i]));
        }
        return stacks;
    }
}
