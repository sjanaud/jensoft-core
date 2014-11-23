/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.stripe.manager;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.stripe.Stripe;
import com.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import com.jensoft.core.plugin.stripe.painter.StripePaint;

/**
 * <code>FlowStripeManager</code> defines a stripe flow
 * 
 * @see StripeManager
 * @see AbstractStripeManager
 * @see Stripe
 * @see StripeOrientation
 * @author Sebastien Janaud
 */
public class FlowStripeManager extends AbstractStripeManager {

	/** flow interval */
	private double flowInterval;

	/** start stripe flow */
	private double flowStart;

	/** end stripe flow */
	private double flowEnd;

	/** last generated stripes */
	private List<Stripe> deviceStripes = new ArrayList<Stripe>();

	/**
	 * create a flow stripe with the specified parameters.
	 * 
	 * @param stripeOrientation
	 *            the stripe orientation
	 * @param flowStart
	 *            the flow start
	 * @param flowEnd
	 *            the flow end
	 * @param flowInterval
	 *            the flow interval
	 */
	public FlowStripeManager(StripeOrientation stripeOrientation, double flowStart, double flowEnd, double flowInterval) {
		if(flowStart > flowEnd)
			throw new IllegalArgumentException("Flow end should be greater than flow start");
		if(flowInterval < 0)
			throw new IllegalArgumentException("Flow interval should be greater than zero");
		super.setStripeOrientation(stripeOrientation);
		this.flowStart = flowStart;
		this.flowEnd = flowEnd;
		this.flowInterval = flowInterval;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.stripe.manager.StripeManager#getStripes()
	 */
	@Override
	public List<Stripe> getStripes() {
		deviceStripes.clear();
		double interval = flowEnd - flowStart;
		int stripeNumber = (int) (interval / flowInterval);

		int countPalette = 0;
		int maxCount = getStripePalette().getPalette().size() - 1;

		if (stripeNumber > 0) {
			for (int i = 0; i < stripeNumber; i++) {

				double g = flowStart + i * flowInterval;

				if (getStripeOrientation() == StripeOrientation.Vertical) {
					Point2D p2dUser = new Point2D.Double(g, 0);
					Point2D p2dDevice = getProjection().userToPixel(p2dUser);

					Point2D p2dUser2 = new Point2D.Double(g + flowInterval, 0);
					Point2D p2dDevice2 = getProjection().userToPixel(p2dUser2);

					Stripe stripe = new Stripe(StripeOrientation.Vertical);
					stripe.setDeviceInterval(p2dDevice2.getX() - p2dDevice.getX());
					
					stripe.deviceStart = p2dDevice.getX();

					StripePaint p = getStripePalette().getPaintPalette(countPalette);

					stripe.setPaint(p);

					deviceStripes.add(stripe);

					if (countPalette++ == maxCount) {
						countPalette = 0;
					}
				} else if (getStripeOrientation() == StripeOrientation.Horizontal) {

					Point2D p2dUser = new Point2D.Double(0, g);
					Point2D p2dDevice = getProjection().userToPixel(p2dUser);

					Point2D p2dUser2 = new Point2D.Double(0, g + flowInterval);
					Point2D p2dDevice2 = getProjection().userToPixel(p2dUser2);

					Stripe stripe = new Stripe(StripeOrientation.Horizontal);
					stripe.setDeviceInterval(p2dDevice.getY() - p2dDevice2.getY());
					stripe.deviceStart = p2dDevice2.getY();

					StripePaint p = getStripePalette().getPaintPalette(countPalette);
					stripe.setPaint(p);

					deviceStripes.add(stripe);

					if (countPalette++ == maxCount) {
						countPalette = 0;
					}
				}

			}
		}
		return deviceStripes;
	}

}
