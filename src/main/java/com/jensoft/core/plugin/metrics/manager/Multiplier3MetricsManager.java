/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.manager;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.WidgetPlugin.PushingBehavior;

/***
 * <code>MultiMultiplierMetricsManager</code> takes the responsibility to manage metrics with 3-multipliers
 * <p style="color : red;">
 * WARNING : can be reboot on to much iteration.
 * </p>
 * <p>
 * You can use this manager when you build some static views without projection bound change<br>
 * it is discourage if you use tools that dynamically change the projection bounds because the manager can be reboot.
 * <p>
 * 
 * @since 1.0
 * @author sebastien janaud
 */
@Deprecated
public class Multiplier3MetricsManager extends AbstractMetricsManager {

    /** the metrics start reference */
    private double metricsRef;

    /** major multiplier */
    private double major = 0;

    /** initial major multiplier */
    private double initialMajor = 0;

    /** major set flag */
    private boolean majorSet = false;

    /** volatile major multiplier */
    private double volatileMajor;

    /** median multiplier */
    private double median = 0;

    /** initial median multiplier */
    private double initialMedian = 0;

    /** median set flag */
    private boolean medianSet = false;

    /** volatile median */
    private double volatileMedian;

    /** minor multiplier */
    private double minor = 0;

    /** initial minor */
    private double initialMinor = 0;

    /** minor set flag */
    private boolean minorSet = false;

    /** volatile minor */
    private double volatileMinor;

    /** device metrics */
    private List<Metrics> deviceMetrics;

    /** internal multiplier factor */
    private int factor = 2;

    /** max iteration after reboot */
    private int maxIteration = 20000;

    /**
     * create empty milli metrics manager
     */
    public Multiplier3MetricsManager() {
        deviceMetrics = new ArrayList<Metrics>();
    }

    /**
     * create milli metrics manager with specified start reference
     * 
     * @param metricsRef
     *            the metrics start reference
     */
    public Multiplier3MetricsManager(double metricsRef) {
        this.metricsRef = metricsRef;
        deviceMetrics = new ArrayList<Metrics>();
    }

    /**
     * get the metrics reference
     * 
     * @return the metrics start reference
     */
    public double getMetricsRef() {
        return metricsRef;
    }

    /**
     * set the metrics reference
     * 
     * @param metricsRef
     */
    public void setMetricsRef(double metricsRef) {
        this.metricsRef = metricsRef;
    }

    /**
     * get the factor multiplier
     * 
     * @return factor multiplier
     */
    public int getFactor() {
        return factor;
    }

    /**
     * set factor multiplier
     * 
     * @param factor
     *            the factor multiplier
     */
    public void setFactor(int factor) {
        this.factor = factor;
    }

    /**
     * get the median metrics
     * 
     * @return the median metrics value
     */
    public double getMedian() {
        return median;
    }

    /**
     * set the median metrics for this manager
     * 
     * @param median
     */
    public void setMedian(double median) {
        if (median < 0) {
            throw new IllegalArgumentException(
                                               "median should be greater than 0");
        }
        this.median = median;
        this.initialMedian = median;
        this.medianSet = true;
    }

    /**
     * get the major metrics for this manager
     * 
     * @return the major metrics value
     */
    public double getMajor() {
        return major;
    }

    /**
     * set the major metrics for this manager
     * 
     * @param major
     */
    public void setMajor(double major) {
        if (major < 0) {
            throw new IllegalArgumentException("major should be greater than 0");
        }
        this.major = major;
        this.initialMajor = major;
        this.majorSet = true;
    }

    /**
     * get the minor metrics for this manager
     * 
     * @return the minor metrics value
     */
    public double getMinor() {
        return minor;
    }

    /**
     * set the major metrics for this manager
     * 
     * @param minor
     */
    public void setMinor(double minor) {
        if (minor < 0) {
            throw new IllegalArgumentException("minor should be greater than 0");
        }
        this.minor = minor;
        this.initialMinor = minor;
        this.minorSet = true;
    }

    /**
     * create the metrics for the specified user value and the specified nature
     * 
     * @param userValue
     * @param nature
     *            the nature is major, median or minor
     * @return the metrics
     */
    private Metrics create(double userValue, int nature) {
        Projection w2d = getRenderContext().getProjection();
        Metrics metrics = new Metrics(getType());
        metrics.setNature(nature);
        double deviceValue = 0;
        if (getType() == MetricsType.XMetrics) {
            Point2D p2dMetricsXUser = new Point2D.Double(userValue, 0);
            Point2D p2dDevice = w2d.userToPixel(p2dMetricsXUser);
            deviceValue = p2dDevice.getX();
        }
        else if (getType() == MetricsType.YMetrics) {

            Point2D p2dMetricsYUser = new Point2D.Double(0, userValue);
            Point2D p2dDevice = w2d.userToPixel(p2dMetricsYUser);
            deviceValue = p2dDevice.getY();
        }

        metrics.setDeviceValue(deviceValue);
        metrics.setUserValue(userValue);
        metrics.setMetricsMarkerColor(getMetricsMarkerColor());
        metrics.setMetricsLabelColor(getMetricsLabelColor());
        metrics.setLockLabel(isLockLabel());
        metrics.setLockMarker(isLockMarker());

        if (getMetricsFormat() == null) {
            metrics.setMetricsLabel(getDefaultFormat().format(userValue));
        }
        else {
            metrics.setMetricsLabel(getMetricsFormat().format(userValue));
        }

        return metrics;

    }

    public void reboot() {
        Projection w2d = getRenderContext().getProjection();
        w2d.getView()
                .getWidgetPlugin()
                .pushMessage("MILLIMETRICS REBOOT", 0, null,
                             PushingBehavior.Fast, InputFonts.getElements(14));
        major = initialMajor;
        median = initialMedian;
        minor = initialMinor;
    }

    /**
     * create the metrics collection for the specified major, median and minor
     * 
     * @param major
     * @param median
     * @param minor
     * @return the volatile metrics collection
     */
    private List<Metrics> createMetrics(double major, double median,
            double minor) {
        long startMillis = System.currentTimeMillis();

        List<Metrics> volatileMetrics = new ArrayList<Metrics>();
        List<Double> reference = new ArrayList<Double>();

        // System.out.println("major  : "+major);
        // System.out.println("median : "+median);
        // System.out.println("minor  : "+minor);

        Projection w2d = getRenderContext().getProjection();

        int globalCount = 0;

        int countUp = 0;
        boolean flagUp = true;

        int countDown = 0;
        boolean flagDown = true;

        BigDecimal bdref = new BigDecimal(new Double(metricsRef).toString());

        BigDecimal bdmajor = new BigDecimal(new Double(major).toString());
        BigDecimal bdmedian = new BigDecimal(new Double(median).toString());
        BigDecimal bdminor = new BigDecimal(new Double(minor).toString());

        BigDecimal bddif = new BigDecimal("0");
        if (getType() == MetricsType.XMetrics) {
            BigDecimal bdMinX = new BigDecimal(
                                               new Double(w2d.getMinX()).toString());
            bddif = bdref.subtract(bdMinX).abs();
        }

        if (getType() == MetricsType.YMetrics) {
            BigDecimal bdMinY = new BigDecimal(
                                               new Double(w2d.getMinY()).toString());
            bddif = bdref.subtract(bdMinY).abs();
        }

        BigDecimal majorLocalRef = new BigDecimal("0");
        if (majorSet && major != 0) {

            BigDecimal bdMajorFactor = bddif.divide(bdmajor,
                                                    RoundingMode.HALF_EVEN);
            BigInteger biMajorFactor = bdMajorFactor.toBigInteger();
            if (getType() == MetricsType.XMetrics) {
                if (metricsRef <= w2d.getMinX()) {
                    majorLocalRef = bdref.add(new BigDecimal(biMajorFactor)
                            .multiply(bdmajor));
                }
                else if (metricsRef >= w2d.getMaxX()) {
                    majorLocalRef = bdref
                            .subtract(new BigDecimal(biMajorFactor)
                                    .multiply(bdmajor));
                }
                else {
                    majorLocalRef = bdref;
                }
            }
            if (getType() == MetricsType.YMetrics) {
                if (metricsRef <= w2d.getMinY()) {
                    majorLocalRef = bdref.add(new BigDecimal(biMajorFactor)
                            .multiply(bdmajor));
                }
                else if (metricsRef >= w2d.getMaxY()) {
                    majorLocalRef = bdref
                            .subtract(new BigDecimal(biMajorFactor)
                                    .multiply(bdmajor));
                }
                else {
                    majorLocalRef = bdref;
                }
            }

        }

        BigDecimal medianLocalRef = new BigDecimal("0");
        if (medianSet && median != 0) {
            BigDecimal bdMedianFactor = bddif.divide(bdmedian,
                                                     RoundingMode.CEILING);
            BigInteger biMedianFactor = bdMedianFactor.toBigInteger();
            // medianLocalRef = bdref.add(new
            // BigDecimal(biMedianFactor).multiply(bdmedian));
            if (getType() == MetricsType.XMetrics) {
                if (metricsRef <= w2d.getMinX()) {
                    medianLocalRef = bdref.add(new BigDecimal(biMedianFactor)
                            .multiply(bdmedian));
                }
                else if (metricsRef >= w2d.getMaxX()) {
                    medianLocalRef = bdref.subtract(new BigDecimal(
                                                                   biMedianFactor).multiply(bdmedian));
                }
                else {
                    medianLocalRef = bdref;
                }
            }
            if (getType() == MetricsType.YMetrics) {
                if (metricsRef <= w2d.getMinY()) {
                    medianLocalRef = bdref.add(new BigDecimal(biMedianFactor).multiply(bdmedian));
                }
                else if (metricsRef >= w2d.getMaxY()) {
                    medianLocalRef = bdref.subtract(new BigDecimal(biMedianFactor).multiply(bdmedian));
                }
                else {
                    medianLocalRef = bdref;
                }
            }
        }

        BigDecimal minorLocalRef = new BigDecimal("0");
        if (minorSet && minor != 0) {
            BigDecimal bdMinorFactor = bddif.divide(bdminor,
                                                    RoundingMode.CEILING);
            BigInteger biMinorFactor = bdMinorFactor.toBigInteger();
            // minorLocalRef = bdref.add(new
            // BigDecimal(biMinorFactor).multiply(bdminor));
            if (getType() == MetricsType.XMetrics) {
                if (metricsRef <= w2d.getMinX()) {
                    minorLocalRef = bdref.add(new BigDecimal(biMinorFactor)
                            .multiply(bdminor));
                }
                else if (metricsRef >= w2d.getMaxX()) {
                    minorLocalRef = bdref
                            .subtract(new BigDecimal(biMinorFactor)
                                    .multiply(bdminor));
                }
                else {
                    minorLocalRef = bdref;
                }
            }
            if (getType() == MetricsType.YMetrics) {
                if (metricsRef <= w2d.getMinY()) {
                    minorLocalRef = bdref.add(new BigDecimal(biMinorFactor)
                            .multiply(bdminor));
                }
                else if (metricsRef >= w2d.getMaxY()) {
                    minorLocalRef = bdref
                            .subtract(new BigDecimal(biMinorFactor)
                                    .multiply(bdminor));
                }
                else {
                    minorLocalRef = bdref;
                }
            }
        }

        BigDecimal bdcount;
        if (getType() == MetricsType.XMetrics) {

            // UP REF

            if (majorSet && major != 0) {

                while (flagUp) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }

                    bdcount = new BigDecimal(countUp);
                    BigDecimal uvalue = majorLocalRef.add(bdcount.multiply(bdmajor));
                    double userMetricsXValue = uvalue.doubleValue();
                    if (userMetricsXValue >= w2d.getMinX()
                            && userMetricsXValue <= w2d.getMaxX()) {
                        if (!reference.contains(userMetricsXValue)) {
                            Metrics m = create(userMetricsXValue, Metrics.MAJOR);

                            volatileMetrics.add(m);
                            reference.add(userMetricsXValue);
                        }
                    }

                    if (userMetricsXValue > w2d.getMaxX()) {
                        flagUp = false;
                    }
                    countUp++;
                    globalCount++;
                    // System.out.println("major up count :" + countUp);
                }
                // System.out.println("major final up count :"+countUp);
            }

            countUp = 0;
            flagUp = true;

            if (medianSet && median != 0) {

                while (flagUp) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countUp);
                    BigDecimal uvalue = medianLocalRef.add(bdcount
                            .multiply(bdmedian));
                    double userMetricsXValue = uvalue.doubleValue();
                    if (userMetricsXValue >= w2d.getMinX()
                            && userMetricsXValue <= w2d.getMaxX()) {
                        if (!reference.contains(userMetricsXValue)) {
                            Metrics m = create(userMetricsXValue,
                                               Metrics.MEDIAN);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            volatileMetrics.add(m);
                            reference.add(userMetricsXValue);
                        }
                    }

                    if (userMetricsXValue > w2d.getMaxX()) {
                        flagUp = false;
                    }
                    countUp++;
                    globalCount++;
                    // System.out.println("median  up count :"+countUp);
                }

                // System.out.println("median final up count :"+countUp);
            }

            countUp = 0;
            flagUp = true;

            if (minorSet && minor != 0) {

                while (flagUp) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countUp);
                    BigDecimal uvalue = minorLocalRef.add(bdcount
                            .multiply(bdminor));
                    double userMetricsXValue = uvalue.doubleValue();
                    if (userMetricsXValue >= w2d.getMinX()
                            && userMetricsXValue <= w2d.getMaxX()) {
                        if (!reference.contains(userMetricsXValue)) {
                            Metrics m = create(userMetricsXValue, Metrics.MINOR);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            volatileMetrics.add(m);
                            reference.add(userMetricsXValue);
                        }
                    }
                    if (userMetricsXValue > w2d.getMaxX()) {
                        flagUp = false;
                    }
                    countUp++;
                    globalCount++;
                    // System.out.println("minor up count :"+countUp);
                }
                // System.out.println("minor final up count :"+countUp);
            }

            // DOWN REF

            if (majorSet && major != 0) {
                while (flagDown) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countDown);
                    BigDecimal uvalue = majorLocalRef.subtract(bdcount
                            .multiply(bdmajor));
                    double userMetricsXValue = uvalue.doubleValue();
                    // System.out.println("min x "+w2d.getMinX());
                    // System.out.println("max x "+w2d.getMaxX());
                    // System.out.println("major user value "+userMetricsXValue
                    // );
                    // System.out.println("major "+major);
                    if (userMetricsXValue <= w2d.getMaxX()
                            && userMetricsXValue >= w2d.getMinX()) {
                        if (!reference.contains(userMetricsXValue)) {
                            Metrics m = create(userMetricsXValue, Metrics.MAJOR);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            volatileMetrics.add(m);
                            reference.add(userMetricsXValue);
                        }
                    }
                    if (userMetricsXValue < w2d.getMinX()) {
                        flagDown = false;
                    }
                    countDown++;
                    globalCount++;
                    // System.out.println("major down count :"+countDown);
                }
                // System.out.println("major final down count :"+countDown);
            }

            countDown = 0;
            flagDown = true;

            if (medianSet && median != 0) {
                while (flagDown) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countDown);
                    BigDecimal uvalue = medianLocalRef.subtract(bdcount
                            .multiply(bdmedian));
                    double userMetricsXValue = uvalue.doubleValue();
                    if (userMetricsXValue <= w2d.getMaxX()
                            && userMetricsXValue >= w2d.getMinX()) {
                        if (!reference.contains(userMetricsXValue)) {
                            Metrics m = create(userMetricsXValue,
                                               Metrics.MEDIAN);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            volatileMetrics.add(m);
                            reference.add(userMetricsXValue);
                        }
                    }
                    if (userMetricsXValue < w2d.getMinX()) {
                        flagDown = false;
                    }
                    countDown++;
                    globalCount++;
                    // System.out.println("median down count :"+countDown);
                }

                // System.out.println("median final down count :"+countDown);
            }

            countDown = 0;
            flagDown = true;

            if (minorSet && minor != 0) {

                while (flagDown) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countDown);
                    BigDecimal uvalue = minorLocalRef.subtract(bdcount
                            .multiply(bdminor));
                    double userMetricsXValue = uvalue.doubleValue();
                    if (userMetricsXValue <= w2d.getMaxX()
                            && userMetricsXValue >= w2d.getMinX()) {
                        if (!reference.contains(userMetricsXValue)) {
                            Metrics m = create(userMetricsXValue, Metrics.MINOR);
                            volatileMetrics.add(m);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            reference.add(userMetricsXValue);
                        }
                    }
                    if (userMetricsXValue < w2d.getMinX()) {
                        flagDown = false;
                    }
                    countDown++;
                    globalCount++;
                    // System.out.println("minor down count :"+countDown);
                }
                // System.out.println("minor final down count :"+countDown);
            }
        }

        // Y

        if (getType() == MetricsType.YMetrics) {

            // UP REF

            if (majorSet && major != 0) {

                while (flagUp) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countUp);
                    BigDecimal uvalue = majorLocalRef.add(bdcount
                            .multiply(bdmajor));
                    double userMetricsYValue = uvalue.doubleValue();
                    if (userMetricsYValue >= w2d.getMinY()
                            && userMetricsYValue <= w2d.getMaxY()) {
                        if (!reference.contains(userMetricsYValue)) {
                            Metrics m = create(userMetricsYValue, Metrics.MAJOR);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            volatileMetrics.add(m);
                            reference.add(userMetricsYValue);
                        }
                    }
                    if (userMetricsYValue > w2d.getMaxY()) {
                        flagUp = false;
                    }
                    countUp++;
                    globalCount++;
                }
            }

            countUp = 0;
            flagUp = true;

            if (medianSet && median != 0) {
                while (flagUp) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countUp);
                    BigDecimal uvalue = medianLocalRef.add(bdcount
                            .multiply(bdmedian));
                    double userMetricsYValue = uvalue.doubleValue();
                    if (userMetricsYValue >= w2d.getMinY()
                            && userMetricsYValue <= w2d.getMaxY()) {
                        if (!reference.contains(userMetricsYValue)) {
                            Metrics m = create(userMetricsYValue,
                                               Metrics.MEDIAN);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            volatileMetrics.add(m);
                            reference.add(userMetricsYValue);
                        }

                    }
                    if (userMetricsYValue > w2d.getMaxY()) {
                        flagUp = false;
                    }
                    countUp++;
                    globalCount++;
                }
            }

            countUp = 0;
            flagUp = true;

            if (minorSet && minor != 0) {
                while (flagUp) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countUp);
                    BigDecimal uvalue = minorLocalRef.add(bdcount
                            .multiply(bdminor));
                    double userMetricsYValue = uvalue.doubleValue();
                    if (userMetricsYValue >= w2d.getMinY()
                            && userMetricsYValue <= w2d.getMaxY()) {
                        if (!reference.contains(userMetricsYValue)) {
                            Metrics m = create(userMetricsYValue, Metrics.MINOR);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            volatileMetrics.add(m);
                            reference.add(userMetricsYValue);
                        }

                    }

                    if (userMetricsYValue > w2d.getMaxY()) {
                        flagUp = false;
                    }
                    countUp++;
                    globalCount++;
                }
            }

            // DOWN REF

            if (majorSet && major != 0) {
                bdcount = new BigDecimal(countDown);
                while (flagDown) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countDown);
                    BigDecimal uvalue = majorLocalRef.subtract(bdcount
                            .multiply(bdmajor));
                    double userMetricsYValue = uvalue.doubleValue();
                    if (userMetricsYValue <= w2d.getMaxY()
                            && userMetricsYValue >= w2d.getMinY()) {
                        if (!reference.contains(userMetricsYValue)) {
                            Metrics m = create(userMetricsYValue, Metrics.MAJOR);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            volatileMetrics.add(m);
                            reference.add(userMetricsYValue);
                        }
                    }

                    if (userMetricsYValue < w2d.getMinY()) {
                        flagDown = false;
                    }
                    countDown++;
                    globalCount++;
                }
            }

            countDown = 0;
            flagDown = true;

            if (medianSet && median != 0) {
                while (flagDown) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countDown);
                    BigDecimal uvalue = medianLocalRef.subtract(bdcount
                            .multiply(bdmedian));
                    double userMetricsYValue = uvalue.doubleValue();
                    if (userMetricsYValue <= w2d.getMaxY()
                            && userMetricsYValue >= w2d.getMinY()) {
                        if (!reference.contains(userMetricsYValue)) {
                            Metrics m = create(userMetricsYValue,
                                               Metrics.MEDIAN);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            volatileMetrics.add(m);
                            reference.add(userMetricsYValue);
                        }

                    }
                    if (userMetricsYValue < w2d.getMinY()) {
                        flagDown = false;
                    }
                    countDown++;
                    globalCount++;
                }
            }

            countDown = 0;
            flagDown = true;

            if (minorSet && minor != 0) {
                while (flagDown) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }
                    bdcount = new BigDecimal(countDown);
                    BigDecimal uvalue = minorLocalRef.subtract(bdcount
                            .multiply(bdminor));
                    double userMetricsYValue = uvalue.doubleValue();
                    if (userMetricsYValue <= w2d.getMaxY()
                            && userMetricsYValue >= w2d.getMinY()) {
                        if (!reference.contains(userMetricsYValue)) {
                            Metrics m = create(userMetricsYValue, Metrics.MINOR);
                            // m.setMetricsLabel(uvalue.toPlainString());
                            volatileMetrics.add(m);
                            reference.add(userMetricsYValue);
                        }
                    }

                    if (userMetricsYValue < w2d.getMinY()) {
                        flagDown = false;
                    }

                    countDown++;
                    globalCount++;
                }
            }

        }

        // System.out.println("volatile sizes : "+volatileMetrics.size());
        // long endMillis = System.currentTimeMillis();
        // System.out.println("create metrics time  second : "+((endMillis-startMillis)/1000L));
        return volatileMetrics;
    }

    /**
     * count the volatile metrics
     * 
     * @return the number of resolved metrics
     */
    public int countMetrics(List<Metrics> volatileMetrics) {
        return volatileMetrics.size();

    }

    /**
     * count the volatile metrics for the specified metrics nature
     * 
     * @param volatileMetrics
     * @param metricsNature
     * @return the number of resolved metrics
     */
    public int countMetrics(List<Metrics> volatileMetrics, int metricsNature) {
        int count = 0;
        for (Metrics m : volatileMetrics) {
            if (m.getNature() == metricsNature) {
                count++;
            }
        }
        return count;

    }

    /**
     * get the metrics width of the specified volatile metrics
     * 
     * @param volatileMetrics
     *            volatile metrics to test.
     * @return the width of the resolve metrics
     */
    private int metricsWidth(List<Metrics> volatileMetrics) {
        int metricsWidth = 0;
        for (Metrics m : volatileMetrics) {
            metricsWidth = metricsWidth + getRenderContext().metricsWidth(m);
        }

        return metricsWidth;

        // int minorWeighting = 0;
        // if (majorSet || medianSet) {
        // minorWeighting = countMetrics(volatileMetrics, Metrics.MINOR) * 2; // 2
        // // pixels
        // // per
        // // minor
        // }
        // else {
        // minorWeighting = countMetrics(volatileMetrics, Metrics.MINOR) * 6; // 6
        // // pixels
        // // per
        // // minor
        // }
        //
        // return metricsWidth
        // + (countMetrics(volatileMetrics)
        // - countMetrics(volatileMetrics, Metrics.MINOR) - 1)
        // * guardInterval + minorWeighting;
    }

    /**
     * get the metrics height of the specified volatile metrics
     * 
     * @param volatileMetrics
     * @return the height of the resolve metrics
     */
    private int metricsHeight(List<Metrics> volatileMetrics) {
        int metricsHeight = 0;
        for (Metrics m : volatileMetrics) {
            metricsHeight = metricsHeight + getRenderContext().metricsHeight(m);
        }

        return metricsHeight;
        // int cm = countMetrics(volatileMetrics)
        // - countMetrics(volatileMetrics, Metrics.MINOR);
        //
        // int minorPonderation = 0;
        // if (majorSet || medianSet) {
        // minorPonderation = countMetrics(volatileMetrics, Metrics.MINOR) * 2; // 2
        // // pixels
        // // per
        // // minor
        // }
        // else {
        // minorPonderation = countMetrics(volatileMetrics, Metrics.MINOR) * 6; // 6
        // // pixels
        // // per
        // // minor
        // }
        //
        // if (volatileMetrics.size() == 0) {
        // return 0;
        // }
        //
        // return cm * getRenderContext().metricsHeight() + (cm - 1)
        // * guardInterval + minorPonderation;
    }

    /**
     * resolve the metrics for this manager
     */
    private void solveMetrics() {
        // System.out.println("solve metrics !");
        double testWidth = getRenderContext().getProjection().getDevice2D()
                .getDeviceWidth();
        double testHeight = getRenderContext().getProjection().getDevice2D()
                .getDeviceHeight();
        if (testWidth < 50 || testHeight < 50) {
            deviceMetrics.clear();
            return;
        }

        List<Metrics> volatileMetrics = createMetrics(major, median, minor);
        deviceMetrics = volatileMetrics;

        if (getType() == MetricsType.XMetrics) {

            double mWidth = metricsWidth(volatileMetrics);
            double deviceWidth = getRenderContext().getProjection().getDevice2D()
                    .getDeviceWidth();

            if (deviceWidth < 0) {
                return;
            }

            if (mWidth >= deviceWidth) {

                boolean acceptDecrease = false;
                volatileMajor = major;
                volatileMedian = median;
                volatileMinor = minor;

                while (!acceptDecrease) {
                    // System.out.println("accept decrease");
                    volatileMajor = volatileMajor * factor;
                    volatileMedian = volatileMedian * factor;
                    volatileMinor = volatileMinor * factor;

                    List<Metrics> volatileMetrics2 = createMetrics(
                                                                   volatileMajor, volatileMedian, volatileMinor);

                    double mWidth2 = metricsWidth(volatileMetrics2);

                    if (mWidth2 <= deviceWidth) {
                        acceptDecrease = true;
                        major = volatileMajor;
                        median = volatileMedian;
                        minor = volatileMinor;
                        deviceMetrics = volatileMetrics2;
                    }
                }

            }
            else {

                boolean acceptIncrease = false;
                volatileMajor = major;
                volatileMedian = median;
                volatileMinor = minor;

                while (!acceptIncrease) {

                    List<Metrics> volatileMetrics3 = createMetrics(
                                                                   volatileMajor, volatileMedian, volatileMinor);
                    volatileMajor = volatileMajor / factor;
                    volatileMedian = volatileMedian / factor;
                    volatileMinor = volatileMinor / factor;

                    double mWidth2 = metricsWidth(volatileMetrics3);

                    if (mWidth2 <= deviceWidth) {
                        acceptIncrease = true;
                        major = volatileMajor;
                        median = volatileMedian;
                        minor = volatileMinor;
                        deviceMetrics = volatileMetrics3;
                    }

                }

            }

        }

        if (getType() == MetricsType.YMetrics) {

            double mWidth = metricsWidth(volatileMetrics);
            double deviceHeight = getRenderContext().getProjection().getDevice2D()
                    .getDeviceHeight();

            if (deviceHeight < 0) {
                return;
            }


            if (mWidth >= deviceHeight) {

                boolean acceptDecrease = false;
                volatileMajor = major;
                volatileMedian = median;
                volatileMinor = minor;
                while (!acceptDecrease) {

                    volatileMajor = volatileMajor * factor;
                    volatileMedian = volatileMedian * factor;
                    volatileMinor = volatileMinor * factor;

                    List<Metrics> volatileMetrics2 = createMetrics(
                                                                   volatileMajor, volatileMedian, volatileMinor);
                    double mHeight2 = metricsHeight(volatileMetrics2);

                    if (mHeight2 <= deviceHeight) {
                        acceptDecrease = true;

                        major = volatileMajor;
                        median = volatileMedian;
                        minor = volatileMinor;

                        // metricsRef =
                        // getMajorMetricsAsRef(volatileMetrics2).getUserValue();

                        deviceMetrics = volatileMetrics2;
                    }
                }

            }
            else {

                boolean acceptIncrease = false;
                volatileMajor = major;
                volatileMedian = median;
                volatileMinor = minor;

                while (!acceptIncrease) {
                    // System.out.println("accept increase");
                    List<Metrics> volatileMetrics3 = createMetrics(
                                                                   volatileMajor, volatileMedian, volatileMinor);

                    volatileMajor = volatileMajor / factor;
                    volatileMedian = volatileMedian / factor;
                    volatileMinor = volatileMinor / factor;

                    double mHeight2 = metricsHeight(volatileMetrics3);

                    if (mHeight2 <= deviceHeight) {
                        acceptIncrease = true;
                        major = volatileMajor;
                        median = volatileMedian;
                        minor = volatileMinor;

                        deviceMetrics = volatileMetrics3;
                    }

                }

            }

        }

    }

    /***
     * get the device metrics for this millimetrics manager
     * 
     * @return the metrics collection
     */
    @Override
    public List<Metrics> getDeviceMetrics() {
        solveMetrics();
        return deviceMetrics;
    }

}
