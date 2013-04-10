/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe;

import com.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import com.jensoft.core.plugin.stripe.manager.FlowStripeManager;
import com.jensoft.core.plugin.stripe.manager.FreeStripeManager;
import com.jensoft.core.plugin.stripe.manager.MultiplierStripeManager;
import com.jensoft.core.plugin.stripe.manager.StripeManager;
import com.jensoft.core.plugin.stripe.painter.AbstractStripePainter;
import com.jensoft.core.plugin.stripe.painter.StripeDefaultPainter;
import com.jensoft.core.plugin.stripe.painter.StripePaint;
import com.jensoft.core.plugin.stripe.painter.StripePalette;

/**
 * 
 * <code>Stripe</code>
 * 
 * @see AbstractStripePlugin
 * @see StripePlugin
 * @see StripePalette
 * @see StripePaint
 * @see StripeDefaultPainter
 * @see AbstractStripePainter
 * @see StripeManager
 * @see MultiplierStripeManager
 * @see FlowStripeManager
 * @see FreeStripeManager
 * 
 * @author Sebastien Janaud
 */
public class Stripe {

	/** the stripe orientation */
	public StripeOrientation stripeOrientation;

	/** the stripe start in device coordinate */
	public double deviceStart;

	/** the stripe end in device coordinate */
	public double deviceEnd;

	/** the strip interval in device coordinate */
	public double deviceInterval;

	/** the stripe start in user coordinate */
	public double userStart;

	/** the stripe end in user coordinate */
	public double userEnd;

	/** the stripe interval in user coordinate */
	public double userInterval;

	/** the stripe paint */
	private StripePaint stripePaint;

	/** the stripe annotation */
	private String annotation;

	/**
	 * create band define with the specified orientation
	 * 
	 * @param orientation
	 */
	public Stripe(StripeOrientation orientation) {
		stripeOrientation = orientation;
	}

	/**
	 * create empty band
	 */
	public Stripe() {
	}

	/*
	 * (non-Javadoc)
	 * 
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
		if (stripeOrientation != other.stripeOrientation) {
			return false;
		}
		if (Double.doubleToLongBits(deviceStart) != Double.doubleToLongBits(other.deviceStart)) {
			return false;
		}
		if (Double.doubleToLongBits(deviceInterval) != Double.doubleToLongBits(other.deviceInterval)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the bandOrientation
	 */
	public StripeOrientation getBandOrientation() {
		return stripeOrientation;
	}

	/**
	 * @param bandOrientation
	 *            the bandOrientation to set
	 */
	public void setBandOrientation(StripeOrientation bandOrientation) {
		this.stripeOrientation = bandOrientation;
	}

	/**
	 * @return the stripe start in device coordinate
	 */
	public double getDeviceStart() {
		return deviceStart;
	}

	/**
	 * @param deviceStart
	 *            the stripe start in device coordinate to set
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
	 * get the stripe interval in device coordinate
	 * 
	 * @return the stripe interval in device coordinate
	 */
	public double getDeviceInterval() {
		return deviceInterval;
	}

	/**
	 * set the stripe interval in device coordinate
	 * 
	 * @param deviceInterval
	 *            the stripe interval in device coordinate
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
	 * @return the stripe paint
	 */
	public StripePaint getStripePaint() {
		return stripePaint;
	}

	/**
	 * @param bandPaint
	 *            the bandPaint to set
	 */
	public void setStripePaint(StripePaint stripePaint) {
		this.stripePaint = stripePaint;
	}

	/**
	 * get the paint
	 * 
	 * @return the paint
	 */
	public StripePaint getPaint() {
		return stripePaint;
	}

	/**
	 * set the paint
	 * 
	 * @param p
	 *            the paint to set
	 */
	public void setPaint(StripePaint p) {
		stripePaint = p;
	}

	/**
	 * get the stripe annotation
	 * 
	 * @return the stripe annotation
	 */
	public String getAnnotation() {
		return annotation;
	}

	/**
	 * set the stripe annotation
	 * 
	 * @param annotation
	 */
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

}
