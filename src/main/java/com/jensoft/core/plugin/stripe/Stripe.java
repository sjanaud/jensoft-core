/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe;

import com.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import com.jensoft.core.plugin.stripe.manager.DynamicStripeManager;
import com.jensoft.core.plugin.stripe.manager.FlowStripeManager;
import com.jensoft.core.plugin.stripe.manager.FreeStripeManager;
import com.jensoft.core.plugin.stripe.manager.StripeManager;
import com.jensoft.core.plugin.stripe.painter.AbstractStripePainter;
import com.jensoft.core.plugin.stripe.painter.StripeDefaultPainter;
import com.jensoft.core.plugin.stripe.painter.StripePaint;
import com.jensoft.core.plugin.stripe.painter.StripePalette;

/**
 * define a stripe band<br>
 * <br>
 * <center><img src="doc-files/band1.png"></center> <br>
 * <br>
 * 
 * @see AbstractStripePlugin
 * @see StripePlugin
 * @see StripePalette
 * @see StripePaint
 * @see StripeDefaultPainter
 * @see AbstractStripePainter
 * @see StripeManager
 * @see DynamicStripeManager
 * @see FlowStripeManager
 * @see FreeStripeManager
 * @author Sebastien Janaud
 */
public class Stripe {

    /** the band orientation */
    public StripeOrientation bandOrientation;

    /** the band start in device coordinate */
    public double deviceStart;

    /** the band end in device coordinate */
    public double deviceEnd;

    /** the band interval in device coordinate */
    public double deviceInterval;

    /** the band start in user coordinate */
    public double userStart;

    /** the band end in user coordinate */
    public double userEnd;

    /** the band interval in user coordinate */
    public double userInterval;

    /** the band paint */
    private StripePaint bandPaint;

    /** the band annotation */
    private String annotation;

    /**
     * create band define with the specified orientation
     * 
     * @param orientation
     */
    public Stripe(StripeOrientation orientation) {
        bandOrientation = orientation;
    }

    /**
     * create empty band
     */
    public Stripe() {
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Stripe other = (Stripe) obj;
        if (bandOrientation != other.bandOrientation) {
            return false;
        }
        if (Double.doubleToLongBits(deviceStart) != Double
                .doubleToLongBits(other.deviceStart)) {
            return false;
        }
        if (Double.doubleToLongBits(deviceInterval) != Double
                .doubleToLongBits(other.deviceInterval)) {
            return false;
        }
        return true;
    }

    /**
     * @return the bandOrientation
     */
    public StripeOrientation getBandOrientation() {
        return bandOrientation;
    }

    /**
     * @param bandOrientation
     *            the bandOrientation to set
     */
    public void setBandOrientation(StripeOrientation bandOrientation) {
        this.bandOrientation = bandOrientation;
    }

    /**
     * @return the band start in device coordinate
     */
    public double getDeviceStart() {
        return deviceStart;
    }

    /**
     * @param deviceStart
     *            the band start in device coordinate to set
     */
    public void setDeviceStart(double deviceStart) {
        this.deviceStart = deviceStart;
    }

    /**
     * @return the deviceEnd
     */
    public double getDeviceEnd() {
        return deviceEnd;
    }

    /**
     * @param deviceEnd
     *            the deviceEnd to set
     */
    public void setDeviceEnd(double deviceEnd) {
        this.deviceEnd = deviceEnd;
    }

    /**
     * get the band interval in device coordinate
     * 
     * @return the band interval in device coordinate
     */
    public double getDeviceInterval() {
        return deviceInterval;
    }

    /**
     * set the band interval in device coordinate
     * 
     * @param deviceInterval
     *            the band interval in device coordinate
     */
    public void setDeviceInterval(double deviceInterval) {
        this.deviceInterval = deviceInterval;
    }

    /**
     * @return the userStart
     */
    public double getUserStart() {
        return userStart;
    }

    /**
     * @param userStart
     *            the userStart to set
     */
    public void setUserStart(double userStart) {
        this.userStart = userStart;
    }

    /**
     * @return the userEnd
     */
    public double getUserEnd() {
        return userEnd;
    }

    /**
     * @param userEnd
     *            the userEnd to set
     */
    public void setUserEnd(double userEnd) {
        this.userEnd = userEnd;
    }

    /**
     * @return the userInterval
     */
    public double getUserInterval() {
        return userInterval;
    }

    /**
     * @param userInterval
     *            the userInterval to set
     */
    public void setUserInterval(double userInterval) {
        this.userInterval = userInterval;
    }

    /**
     * @return the bandPaint
     */
    public StripePaint getBandPaint() {
        return bandPaint;
    }

    /**
     * @param bandPaint
     *            the bandPaint to set
     */
    public void setBandPaint(StripePaint bandPaint) {
        this.bandPaint = bandPaint;
    }

    /**
     * get the paint
     * 
     * @return the paint
     */
    public StripePaint getPaint() {
        return bandPaint;
    }

    /**
     * set the paint
     * 
     * @param p
     *            the paint to set
     */
    public void setPaint(StripePaint p) {
        bandPaint = p;
    }

    /**
     * get the band annotation
     * 
     * @return the band annotation
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * set the band annotation
     * 
     * @param annotation
     */
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

}
