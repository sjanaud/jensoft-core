/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.function.analysis.FunctionEvaluationException;
import com.jensoft.core.plugin.function.analysis.MathException;
import com.jensoft.core.plugin.function.analysis.SplineInterpolator;
import com.jensoft.core.plugin.function.analysis.UnivariateRealFunction;
import com.jensoft.core.plugin.function.analysis.UnivariateRealInterpolator;

/**
 * <code>InterpolateSourceFunction</code> defines the user data input with interpolation segment.
 * <p>
 * Min , max of segment is given by the bounded interval specified parameters<br>
 * assume that index 0 is the minimum x and last index is the maximum x.
 * </p>
 * 
 * @author sebastien janaud
 */
public class InterpolateSourceFunction extends AffineSourceFunction {

    /** the univariate real function */
    private UnivariateRealFunction function = null;

    /** the univariate real interpolator */
    private UnivariateRealInterpolator interpolator;

    /** interpolate segment */
    private List<Point2D> interpolateSource = new ArrayList<Point2D>();

    /** the interpolate delta x segment */
    private double delta;

    /**
     * create an interpolate serie for specified source and delta
     * 
     * @param userSource
     *            the serie to interpolate
     * @param delta
     *            the delta to make interpolation
     */
    public InterpolateSourceFunction(List<Point2D> userSource, double delta) {
        super(userSource);
        interpolator = new SplineInterpolator();
        this.delta = delta;
        interpolateSource = new ArrayList<Point2D>();
        createInterpolateFunction();
    }

    /**
     * set the source and call {@link #createInterpolateFunction()}
     */
    @Override
    public void setSource(List<Point2D> source) {
        super.setSource(source);
        interpolateSource.clear();
        createInterpolateFunction();
    }

    /**
     * evaluate point for the specified x
     * 
     * @return the evaluate point at the given x
     */
    @Override
    public Point2D evaluate(double x) {
        Point2D evaluatePoint = null;
        try {
            evaluatePoint = new Point2D.Double(x, function.value(x));
        }
        catch (FunctionEvaluationException e) {
        }
        return evaluatePoint;
    }

    /**
     * override method to get interpolate source
     */
    @Override
    public List<Point2D> getSource() {
        List<Point2D> superSource = super.getSource();
        if (function == null) {
            return super.getSource();
        }

        if (interpolateSource.size() > 0) {
            return interpolateSource;
        }

        Point2D pd2Min = superSource.get(0);
        Point2D pd2Max = superSource.get(superSource.size() - 1);
        for (double x = pd2Min.getX(); x <= pd2Max.getX(); x = x + delta) {
            try {
                interpolateSource
                        .add(new Point2D.Double(x, function.value(x)));

            }
            catch (FunctionEvaluationException e) {
                return super.getSource();
            }
        }

        return interpolateSource;
    }

    /**
     * create interpolate function for given source.
     */
    private void createInterpolateFunction() {

        try {
            List<Point2D> userSource = super.getSource();

            int len = userSource.size();
            double[] xValues = new double[len];
            double[] yValues = new double[len];
            for (int i = 0; i < userSource.size(); i++) {
                Point2D p2dUser = userSource.get(i);
                xValues[i] = p2dUser.getX();
                yValues[i] = p2dUser.getY();
            }
            function = interpolator.interpolate(xValues, yValues);
        }
        catch (MathException e) {
        }
    }

}
