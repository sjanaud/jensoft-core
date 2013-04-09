/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jensoft.core.plugin.Toolkit;

/**
 * <code>SourceFunctionToolkit</code>
 * 
 * @author Sebastien Janaud
 */
public class SourceFunctionToolkit extends Toolkit {

    /**
     * create list of points from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @return list of points
     */
    public static List<Point2D> createPointsFromArray(double[] xValues,
            double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        List<Point2D> serie = new ArrayList<Point2D>();
        for (int i = 0; i < xValues.length; i++) {
            serie.add(new Point2D.Double(xValues[i], yValues[i]));
        }
        return serie;
    }

    /**
     * create list of points from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @return list of points
     */
    public static List<Point2D> createPointsFromArray(Double[] xValues,
            Double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        List<Point2D> serie = new ArrayList<Point2D>();
        for (int i = 0; i < xValues.length; i++) {
            serie.add(new Point2D.Double(xValues[i], yValues[i]));
        }
        return serie;
    }

    /**
     * create list of point from x date array and y double array
     * 
     * @param xValues
     *            the dates as x values, the are transform in millisecond
     * @param yValues
     *            the y double values
     * @return list of points
     */
    public static List<Point2D> createPointsFromArray(Date[] xValues,
            double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        List<Point2D> serie = new ArrayList<Point2D>();
        for (int i = 0; i < xValues.length; i++) {
            serie.add(new Point2D.Double(new Long(xValues[i].getTime()).doubleValue(), yValues[i]));
        }
        return serie;
    }

    /**
     * create list of point from x date array and y double array
     * 
     * @param xValues
     * @param yValues
     * @return list of points
     */
    public static List<Point2D> createPointsFromArray(Date[] xValues,
            Double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        List<Point2D> serie = new ArrayList<Point2D>();
        for (int i = 0; i < xValues.length; i++) {
            serie.add(new Point2D.Double(new Long(xValues[i].getTime()).doubleValue(), yValues[i]));
        }
        return serie;
    }

    /**
     * create list of point from x double array and y date array
     * 
     * @param xValues
     * @param yValues
     * @return list of points
     */
    public static List<Point2D> createPointsFromArray(double[] xValues,
            Date[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        List<Point2D> serie = new ArrayList<Point2D>();
        for (int i = 0; i < xValues.length; i++) {
            serie.add(new Point2D.Double(xValues[i], new Long(yValues[i].getTime()).doubleValue()));
        }
        return serie;
    }

    /**
     * create list of point from x double array and y date array
     * 
     * @param xValues
     * @param yValues
     * @return list of points
     */
    public static List<Point2D> createPointsFromArray(Double[] xValues,
            Date[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        List<Point2D> serie = new ArrayList<Point2D>();
        for (int i = 0; i < xValues.length; i++) {
            serie.add(new Point2D.Double(xValues[i], new Long(yValues[i].getTime()).doubleValue()));
        }
        return serie;
    }

    /**
     * create default x function from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @return affine source X function
     */
    public static AffineSourceFunction createSourceFunction(double[] xValues,
            double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new AffineSourceFunction(createPointsFromArray(xValues, yValues));
    }

    /**
     * create serie from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @return serie
     */
    public static AffineSourceFunction createSourceFunction(Date[] xValues,
            double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new AffineSourceFunction(createPointsFromArray(xValues, yValues));
    }

    /**
     * create serie from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @return serie
     */
    public static AffineSourceFunction createSourceFunction(Date[] xValues,
            Double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new AffineSourceFunction(createPointsFromArray(xValues, yValues));
    }

    /**
     * create serie from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @return serie
     */
    public static AffineSourceFunction createSourceFunction(double[] xValues,
            Date[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new AffineSourceFunction(createPointsFromArray(xValues, yValues));
    }

    /**
     * create serie from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @return serie
     */
    public static AffineSourceFunction createSourceFunction(Double[] xValues,
            Date[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new AffineSourceFunction(createPointsFromArray(xValues, yValues));
    }

    /**
     * create serie from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @return serie
     */
    public static AffineSourceFunction createSourceFunction(Double[] xValues,
            Double[] yValues) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new AffineSourceFunction(createPointsFromArray(xValues, yValues));
    }

    /**
     * create interpolate serie from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @param delta
     *            step segment length for evaluate curve
     * @return the interpolate serie
     */
    public static SplineSourceFunction createInterpolateSourceFunction(
            double[] xValues, double[] yValues, double delta) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new SplineSourceFunction(createPointsFromArray(xValues, yValues), delta);
    }

    /**
     * create interpolate source function from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @param delta
     *            step segment length for evaluate curve
     * @return the interpolate source function
     */
    public static SplineSourceFunction createInterpolateSourceFunction(
            Date[] xValues, double[] yValues, double delta) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new SplineSourceFunction(createPointsFromArray(xValues, yValues), delta);
    }

    /**
     * create interpolate source function from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @param delta
     *            step segment length for evaluate curve
     * @return the interpolate source function
     */
    public static SplineSourceFunction createInterpolateSourceFunction(
            double[] xValues, Date[] yValues, double delta) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new SplineSourceFunction(createPointsFromArray(xValues, yValues), delta);
    }

    /**
     * create interpolate source function from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @param delta
     *            step segment length for evaluate curve
     * @return the interpolate source function
     */
    public static SplineSourceFunction createInterpolateSourceFunction(
            Date[] xValues, Double[] yValues, Double delta) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new SplineSourceFunction(createPointsFromArray(xValues, yValues), delta);
    }

    /**
     * create interpolate source function from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @param delta
     *            step segment length for evaluate curve
     * @return the interpolate source function
     */
    public static SplineSourceFunction createInterpolateSourceFunction(
            Double[] xValues, Date[] yValues, Double delta) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new SplineSourceFunction(createPointsFromArray(xValues, yValues), delta);
    }

    /**
     * create interpolate source function from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @param delta
     *            step segment length for evaluate curve
     * @return the interpolate source function
     */
    public static SplineSourceFunction createInterpolateSourceFunction(
            Double[] xValues, Double[] yValues, Double delta) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new SplineSourceFunction(createPointsFromArray(xValues, yValues), delta);
    }

    /**
     * create linear regression source function from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @param delta
     *            step segment length for evaluate curve
     * @return the interpolate source function
     */
    public static LinearRegressionSourceFunction createLinearRegressionSourceFunction(
            double[] xValues, double[] yValues, double delta) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new LinearRegressionSourceFunction(createPointsFromArray(xValues, yValues), delta);
    }

    /**
     * create linear regression source function from x and y arrays.
     * 
     * @param xValues
     * @param yValues
     * @param delta
     *            step segment length for evaluate curve
     * @return the interpolate source function
     */
    public static LinearRegressionSourceFunction createLinearRegressionSourceFunction(
            Double[] xValues, Double[] yValues, Double delta) {
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException(
                                               " x and y  array values length does not match");
        }
        return new LinearRegressionSourceFunction(createPointsFromArray(xValues, yValues), delta);
    }

}
