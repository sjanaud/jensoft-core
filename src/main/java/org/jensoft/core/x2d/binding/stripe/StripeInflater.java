/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.stripe;

import java.awt.Color;

import org.jensoft.core.plugin.stripe.Stripe;
import org.jensoft.core.plugin.stripe.StripePlugin;
import org.jensoft.core.plugin.stripe.StripePlugin.StripeOrientation;
import org.jensoft.core.plugin.stripe.manager.FlowStripeManager;
import org.jensoft.core.plugin.stripe.manager.FreeStripeManager;
import org.jensoft.core.plugin.stripe.manager.MultiplierStripeManager;
import org.jensoft.core.plugin.stripe.painter.StripePaint;
import org.jensoft.core.plugin.stripe.painter.StripePalette;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>BandInflater</code>
 * 
 * @author Sebastien Janaud
 */
public class StripeInflater extends AbstractX2DPluginInflater<StripePlugin> {

	/**
	 * create Stripe inflater
	 */
	public StripeInflater() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c
	 * .dom.Element)
	 */
	@Override
	public StripePlugin inflate(Element pluginElement) {
		
		Element managersElement = (Element) pluginElement.getElementsByTagName("managers").item(0);
		NodeList managerElements = managersElement.getElementsByTagName("manager");

		for (int i = 0; i < managerElements.getLength(); i++) {
			Element managerElement = (Element) managerElements.item(0);
			String type = elementText(managerElement, "type");
			if (type == null) {
				continue;
			}
			if (type.equals("dynamic")) {
				inflateDynamicManager(managerElement);
			} else if (type.equals("flow")) {
				inflateFlowManager(managerElement);
			} else if (type.equals("free")) {
				inflateFreeManager(managerElement);
			}

		}

		return null;
	}

	/**
	 * inflate stripe palette
	 * 
	 * @param paletteElement
	 *            the palette element to inflate
	 * @return band palette
	 */
	public StripePalette parsePalette(Element paletteElement) {
		if (paletteElement == null) {
			return null;
		}
		StripePalette palette = new StripePalette();
		NodeList colorElements = paletteElement.getElementsByTagName("c");
		for (int i = 0; i < colorElements.getLength(); i++) {
			Element colorElement = (Element) colorElements.item(i);
			Color c = elementColor(colorElement);
			if (c != null) {
				palette.addPaint(c);
			}
		}

		return palette;
	}

	/**
	 * inflate a {@link MultiplierStripeManager}
	 * 
	 * @param managerElement
	 *            the manager element to inflate
	 */
	public void inflateDynamicManager(Element managerElement) {
		String orientation = elementText(managerElement, "orientation");
		String ref = elementText(managerElement, "ref");
		String interval = elementText(managerElement, "interval");

		Element paletteElement = (Element) managerElement.getElementsByTagName("palette");

		StripeOrientation bo = null;
		Double bandRef = null;
		Double bandInterval = null;
		StripePalette palette = null;
		if (orientation != null && !orientation.equals("undefined")) {
			bo = StripeOrientation.parse(orientation);
		}

		if (ref != null && !ref.equals("undefined")) {
			bandRef = Double.parseDouble(ref);
		}
		if (interval != null && !interval.equals("undefined")) {
			bandInterval = Double.parseDouble(interval);
		}
		palette = parsePalette(paletteElement);

		if (bo != null && bandRef != null && bandInterval != null && palette != null) {
			// DynamicStripeManager manager = new DynamicStripeManager(bo,
			// bandRef,
			// bandInterval);
			// manager.setBandPalette(palette);
			// getPlugin().addManager(manager);
		}
	}

	/**
	 * inflate {@link FlowStripeManager}
	 * 
	 * @param managerElement
	 *            the manager element to inflate
	 */
	public void inflateFlowManager(Element managerElement) {
		String orientation = elementText(managerElement, "orientation");
		String startband = elementText(managerElement, "startband");
		String endband = elementText(managerElement, "endband");
		String interval = elementText(managerElement, "interval");
		Element paletteElement = (Element) managerElement.getElementsByTagName("palette");

		StripeOrientation bo = null;
		Double bandStart = null;
		Double bandEnd = null;
		Double bandInterval = null;
		StripePalette palette;

		if (orientation != null && !orientation.equals("undefined")) {
			bo = StripeOrientation.parse(orientation);
		}
		if (startband != null && !startband.equals("undefined")) {
			bandStart = Double.parseDouble(startband);
		}
		if (endband != null && !endband.equals("undefined")) {
			bandEnd = Double.parseDouble(endband);
		}
		if (interval != null && !interval.equals("undefined")) {
			bandInterval = Double.parseDouble(interval);
		}
		palette = parsePalette(paletteElement);

		if (bo != null && bandStart != null && bandEnd != null && bandInterval != null) {
			FlowStripeManager manager = new FlowStripeManager(bo, bandStart, bandEnd, bandInterval);
			manager.setStripePalette(palette);
			// getPlugin().addManager(manager);
		}
	}

	/**
	 * inflate {@link FreeStripeManager}
	 * 
	 * @param managerElement
	 *            the manager element to inflate
	 */
	public void inflateFreeManager(Element managerElement) {
		String orientation = elementText(managerElement, "orientation");

		StripeOrientation go = null;

		if (orientation != null && !orientation.equals("undefined")) {
			go = StripeOrientation.parse(orientation);
		}

		FreeStripeManager freeBandManager = null;
		if (go != null) {
			freeBandManager = new FreeStripeManager(go);
			// getPlugin().addManager(freeBandManager);
		}

		Element bandsElement = (Element) managerElement.getElementsByTagName("bands");
		NodeList bandElements = bandsElement.getElementsByTagName("band");
		for (int i = 0; i < bandElements.getLength(); i++) {
			Element bandElement = (Element) bandElements.item(i);

			Element colorElement = (Element) bandElement.getElementsByTagName("color").item(0);
			String startband = elementText(bandElement, "start");
			String endband = elementText(bandElement, "end");

			Double bandStart = null;
			Double bandEnd = null;
			Color bandColor = null;

			if (startband != null && !startband.equals("undefined")) {
				bandStart = Double.parseDouble(startband);
			}
			if (endband != null && !endband.equals("undefined")) {
				bandEnd = Double.parseDouble(endband);
			}
			if (colorElement != null) {
				bandColor = elementColor(colorElement);
			}

			if (freeBandManager != null && bandStart != null && bandEnd != null && bandColor != null) {
				Stripe b = new Stripe();
				b.setUserStart(bandStart);
				b.setUserEnd(bandEnd);
				b.setStripePaint(new StripePaint(bandColor));
				freeBandManager.addStripe(b);
			}
		}

	}

}
