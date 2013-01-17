/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <code>AffineSourceFunction</code> defines a basic source with collection of (x,y) sort by X
 * 
 * @author sebastien janaud
 */
public class AffineSourceFunction implements SourceFunction {

    /** source */
    private List<Point2D> source;

    /** serie id */
    private String id;

    /** serie name */
    private String name;

    /** comparator by x coordinate */
    private XComparator xComparator = new XComparator();
    
    /**
     * create empty serie
     */
    public AffineSourceFunction() {
    }

    /**
     * create serie for source point
     * @param source
     */
    public AffineSourceFunction(List<Point2D> source) {
        this.source = source;
        sortByX();
    }

    /**
     * set source of this serie
     * 
     * @param source
     *            the source to set
     */
    public void setSource(List<Point2D> source) {
        this.source = source;
        sortByX();
    }

    /**
     * get the serie name
     * 
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * set the serie name
     * 
     * @param name
     *            the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#select(double, double)
     */
    @Override
    public List<Point2D> select(double startX, double endX) {
        List<Point2D> select = new ArrayList<Point2D>();
        for (Point2D p : getSource()) {
            if (p.getX() >= startX && p.getX() <= endX) {
                select.add(p);
            }
        }
        return select;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#next(double)
     */
    @Override
    public Point2D next(double x) {
        for (Point2D p : getSource()) {
            if (p.getX() >= x) {
                return p;
            }
        }
        return null;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#previous(double)
     */
    @Override
    public Point2D previous(double x) {
        List<Point2D> src = getSource();
        for (int i = src.size() - 1; i >= 0; i--) {
            Point2D p = src.get(i);
            if (p.getX() <= x) {
                return p;
            }
        }
        return null;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#evaluate(double)
     */
    @Override
    public Point2D evaluate(double x) {
        //TODO evaluate the point x
        return null;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#min()
     */
    @Override
    public Point2D min() {
        return source.get(0);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#max()
     */
    @Override
    public Point2D max() {
        return source.get(source.size() - 1);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#minY()
     */
    @Override
    public Point2D minY() {
        Point2D minY = getSource().get(0);
        for (Point2D p : getSource()) {
            if (p.getY() < minY.getY()) {
                minY = p;
            }
        }
        return minY;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.function.source.SourceFunction#getSource()
     */
    @Override
    public List<Point2D> getSource() {
        return source;
    }

    public void sortByX() {
        Collections.sort(source, xComparator);
    }

    /**
     * comparator by x
     * 
     * @author Sebastien Janaud
     */
    class XComparator implements Comparator<Point2D> {

        public XComparator() {
        }

        /*
         * (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(Point2D p2d1, Point2D p2d2) {
            if (p2d1.getX() > p2d2.getX()) {
                return 1;
            }
            else if (p2d1.getX() < p2d2.getX()) {
                return -1;
            }
            return 0;
        }

    }

}
