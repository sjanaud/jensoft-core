/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.function.analysis.SimpleRegression;

/**
 * <code>LinearRegressionSourceFunction</code> defines the user data input with linear regression
 * segment.
 * <p>
 * min , max of segment is given by the bounded interval specified
 * parameters<br>
 * assume that index 0 is the min x and last index is the maximum x.
 * <p>
 * 
 * @author sebastien janaud
 */
public class LinearRegressionSourceFunction extends AffineSourceFunction {

    /**simple regression*/
    private SimpleRegression regression = null;

    /**the interpolate x segment*/
    private double delta;

    /**
     * create the regression with given based source and delta 
     * @param userSource
     * @param delta
     */
    public LinearRegressionSourceFunction(List<Point2D> userSource, double delta) {
        super(userSource);
        this.delta = delta;
        regression();
    }
    
    /**
     * set the source and call {@link #createInterpolateFunction()}
     */
    @Override
    public void setSource(List<Point2D> source) {
        super.setSource(source);
        regression();
    }

    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.serie.Serie2D#getSource()
     */
    @Override
    public List<Point2D> getSource() {
        List<Point2D> regressionSegment = new ArrayList<Point2D>();
        Point2D pd2Min = min();
        Point2D pd2Max = max();
        for (double x = pd2Min.getX(); x <= pd2Max.getX(); x = x + delta) {
            regressionSegment.add(new Point2D.Double(x, regression.predict(x)));
        }
        return regressionSegment;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plot.serie.Serie2D#evaluate(double)
     */
    @Override
    public Point2D evaluate(double x) {
        Point2D evaluatePoint = null;
        try {
            evaluatePoint = new Point2D.Double(x, regression.predict(x));
        }
        catch (Exception e) {
        }
        return evaluatePoint;
    }

    /**
     * create linear regression for the source
     */
    public void regression() {
        regression = new SimpleRegression();
        List<Point2D> userSource = super.getSource();
        for (int i = 0; i < userSource.size(); i++) {
            Point2D p2d = userSource.get(i);
            regression.addData(p2d.getX(), p2d.getY());
        }
    }

}
