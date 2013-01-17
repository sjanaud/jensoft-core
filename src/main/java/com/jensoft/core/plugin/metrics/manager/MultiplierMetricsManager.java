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
import com.jensoft.core.view.WidgetPlugin.PushingBehavior;
import com.jensoft.core.window.Window2D;

/***
 * <code>MultiplierMetricsManager</code> takes the responsibility to manage metrics with one multiplier
 * <p style="color : red;">
 * WARNING : can be reboot on to much iteration.
 * </p>
 * <p>
 * You can use this manager when you build some static views without window bound change<br>
 * it is discourage if you use tools that dynamically change the window bounds because the manager can be reboot.
 * <p>
 * @author sebastien janaud
 */
public class MultiplierMetricsManager extends AbstractMetricsManager {

    /** the metrics start reference */
    private double metricsRef;

    /** multiplier */
    private double multiplier = 0;

    /** initial major multiplier */
    private double initialMultiplier = 0;

    /** multiplier set flag */
    private boolean multiplierSet = false;

    /** volatile multiplier */
    private double volatileMultiplier;

    /** device metrics */
    private List<Metrics> deviceMetrics;

    /** guard interval */
    private int guardInterval = 2;

    /** internal multiplier factor */
    private int factor = 2;

    /** max iteration after reboot */
    private int maxIteration = 20000;

    /**
     * create empty milli metrics manager
     */
    public MultiplierMetricsManager() {
        this.deviceMetrics = new ArrayList<Metrics>();
    }

    /**
     * create dynamic metrics manager with specified start reference
     * 
     * @param metricsRef
     *            the metrics start reference
     * @param multiplier
     *            the multiplier on axis
     */
    public MultiplierMetricsManager(double metricsRef, double multiplier) {
        this.metricsRef = metricsRef;
        setMultiplier(multiplier);
        this.deviceMetrics = new ArrayList<Metrics>();
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
     * get guard interval
     * 
     * @return guard interval
     */
    public int getGuardInterval() {
        return guardInterval;
    }

    /**
     * set guard interval
     * 
     * @param guardInterval
     *            the guard interval to set
     */
    public void setGuardInterval(int guardInterval) {
        this.guardInterval = guardInterval;
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
     * get the multiplier metrics for this manager
     * 
     * @return the metrics multiplier
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * set the major metrics for this manager
     * 
     * @param multiplier
     */
    public void setMultiplier(double multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException("multiplier should be greater than 0");
        }
        this.multiplier = multiplier;
        this.initialMultiplier = multiplier;
        this.multiplierSet = true;
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
        Window2D w2d = getRenderContext().getWindow2D();
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

    /**
     * reboot this manager
     */
    public void reboot() {
        Window2D w2d = getRenderContext().getWindow2D();
        w2d.getView2D()
                .getWidgetPlugin()
                .pushMessage("REBOOT", 0, null,
                             PushingBehavior.Fast, InputFonts.getElements(14));
        multiplier = initialMultiplier;
    }

    /**
     * create the metrics collection for the specified major, median and minor
     * 
     * @param major
     * @param median
     * @param minor
     * @return the volatile metrics collection
     */
    private List<Metrics> createMetrics(double major) {
        List<Metrics> volatileMetrics = new ArrayList<Metrics>();
        List<Double> reference = new ArrayList<Double>();

        Window2D w2d = getRenderContext().getWindow2D();

        int globalCount = 0;

        int countUp = 0;
        boolean flagUp = true;

        int countDown = 0;
        boolean flagDown = true;

        BigDecimal bdref = new BigDecimal(new Double(metricsRef).toString());

        BigDecimal bdmajor = new BigDecimal(new Double(major).toString());

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
        if (multiplierSet && major != 0) {
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

        BigDecimal bdcount;
        if (getType() == MetricsType.XMetrics) {

            // UP REF

            if (multiplierSet && major != 0) {

                while (flagUp) {
                    if (globalCount > maxIteration) {
                        reboot();
                        volatileMetrics.clear();
                        return volatileMetrics;
                    }

                    bdcount = new BigDecimal(countUp);
                    BigDecimal uvalue = majorLocalRef.add(bdcount
                            .multiply(bdmajor));
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

                }
            }

            // DOWN REF

            if (multiplierSet && major != 0) {
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

                    if (userMetricsXValue <= w2d.getMaxX()
                            && userMetricsXValue >= w2d.getMinX()) {
                        if (!reference.contains(userMetricsXValue)) {
                            Metrics m = create(userMetricsXValue, Metrics.MAJOR);
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

        }

        if (getType() == MetricsType.YMetrics) {

            // UP REF

            if (multiplierSet && major != 0) {

                while (flagUp) {
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
                }
            }

            // DOWN REF

            if (multiplierSet && major != 0) {
                bdcount = new BigDecimal(countDown);
                while (flagDown) {
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
                }
            }

        }

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

//        return metricsWidth
//                + (countMetrics(volatileMetrics) - 1)
//                * guardInterval;
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
//        int cm = countMetrics(volatileMetrics);
//        if (volatileMetrics.size() == 0) {
//            return 0;
//        }
//        return cm * getRenderContext().metricsHeight() + (cm - 1)
//                * guardInterval;
    }

    /**
     * resolve the metrics for this manager
     */
    private void solveMetrics() {

        double testWidth = getRenderContext().getWindow2D().getDevice2D()
                .getDeviceWidth();
        double testHeight = getRenderContext().getWindow2D().getDevice2D()
                .getDeviceHeight();
        if (testWidth < 50 || testHeight < 50) {
            deviceMetrics.clear();
            return;
        }

        List<Metrics> volatileMetrics = createMetrics(multiplier);
        deviceMetrics = volatileMetrics;

        if (getType() == MetricsType.XMetrics) {

            double mWidth = metricsWidth(volatileMetrics);
            double deviceWidth = getRenderContext().getWindow2D().getDevice2D()
                    .getDeviceWidth();

            if (deviceWidth < 0) {
                return;
            }

            if (mWidth >= deviceWidth) {

                boolean acceptDecrease = false;
                volatileMultiplier = multiplier;

                while (!acceptDecrease) {
                    volatileMultiplier = volatileMultiplier * factor;

                    List<Metrics> volatileMetrics2 = createMetrics(volatileMultiplier);

                    double mWidth2 = metricsWidth(volatileMetrics2);

                    if (mWidth2 <= deviceWidth) {
                        acceptDecrease = true;
                        multiplier = volatileMultiplier;
                        deviceMetrics = volatileMetrics2;
                    }
                }

            }
            else {

                boolean acceptIncrease = false;
                volatileMultiplier = multiplier;

                while (!acceptIncrease) {
                    List<Metrics> volatileMetrics3 = createMetrics(volatileMultiplier);
                    volatileMultiplier = volatileMultiplier / factor;

                    double mWidth2 = metricsWidth(volatileMetrics3);

                    if (mWidth2 <= deviceWidth) {
                        acceptIncrease = true;
                        multiplier = volatileMultiplier;
                        deviceMetrics = volatileMetrics3;
                    }

                }

            }

        }

        if (getType() == MetricsType.YMetrics) {

            double mHeight = metricsHeight(volatileMetrics);

            double deviceHeight = getRenderContext().getWindow2D()
                    .getDevice2D().getDeviceHeight();

            if (deviceHeight < 0) {
                return;
            }
            if (mHeight >= deviceHeight) {

                boolean acceptDecrease = false;
                volatileMultiplier = multiplier;
                while (!acceptDecrease) {
                    volatileMultiplier = volatileMultiplier * factor;

                    List<Metrics> volatileMetrics2 = createMetrics(volatileMultiplier);
                    double mHeight2 = metricsHeight(volatileMetrics2);

                    if (mHeight2 <= deviceHeight) {
                        acceptDecrease = true;
                        multiplier = volatileMultiplier;
                        deviceMetrics = volatileMetrics2;
                    }
                }

            }
            else {

                boolean acceptIncrease = false;
                volatileMultiplier = multiplier;

                while (!acceptIncrease) {

                    List<Metrics> volatileMetrics3 = createMetrics(volatileMultiplier);

                    volatileMultiplier = volatileMultiplier / factor;

                    double mHeight2 = metricsHeight(volatileMetrics3);

                    if (mHeight2 <= deviceHeight) {
                        acceptIncrease = true;
                        multiplier = volatileMultiplier;
                        deviceMetrics = volatileMetrics3;
                    }

                }

            }

        }

    }

    /***
     * get the device metrics for this dynamic metrics manager
     * 
     * @return the metrics collection
     */
    @Override
    public List<Metrics> getDeviceMetrics() {
        solveMetrics();
        return deviceMetrics;
    }

}
