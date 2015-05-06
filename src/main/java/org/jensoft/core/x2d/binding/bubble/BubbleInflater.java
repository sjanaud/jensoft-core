/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.bubble;

import java.awt.Color;

import org.jensoft.core.plugin.bubble.Bubble;
import org.jensoft.core.plugin.bubble.BubblePlugin;
import org.jensoft.core.plugin.bubble.painter.draw.BubbleDefaultDraw;
import org.jensoft.core.plugin.bubble.painter.effect.BubbleEffect1;
import org.jensoft.core.plugin.bubble.painter.effect.BubbleEffect2;
import org.jensoft.core.plugin.bubble.painter.effect.BubbleEffect3;
import org.jensoft.core.plugin.bubble.painter.effect.BubbleEffect4;
import org.jensoft.core.plugin.bubble.painter.fill.BubbleDefaultFill;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>BubbleInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi = "BubblePlugin", plugin = BubblePlugin.class)
public class BubbleInflater extends AbstractX2DPluginInflater<BubblePlugin> {


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c
	 * .dom.Element)
	 */
	@Override
	public BubblePlugin inflate(Element pluginElement) {
		BubblePlugin bp = new BubblePlugin();
		Element bubblesElement = (Element) pluginElement.getElementsByTagName("bubbles").item(0);

		NodeList bubbleElements = bubblesElement.getElementsByTagName("bubble");
		for (int i = 0; i < bubbleElements.getLength(); i++) {
			Element element = (Element) bubbleElements.item(0);
			Bubble b = inflateBubble(element);
			bp.addBubble(b);
		}
		return bp;
	}

	/**
	 * inflate {@link Bubble}
	 * 
	 * @param bubbleElement
	 *            the bubble element to inflate
	 * @return bubble
	 */
	public Bubble inflateBubble(Element bubbleElement) {
		String x = elementText(bubbleElement, "x");
		String y = elementText(bubbleElement, "y");
		String r = elementText(bubbleElement, "radius");
		Element dc = (Element) bubbleElement.getElementsByTagName("drawcolor").item(0);
		Element fc = (Element) bubbleElement.getElementsByTagName("fillcolor").item(0);
		String fx = elementText(bubbleElement, "effect");

		Double centerX = Double.parseDouble(x);
		Double centerY = Double.parseDouble(y);
		Double radius = Double.parseDouble(r);

		Bubble bubble = new Bubble(centerX, centerY, radius, null);

		Color drawColor = null;
		if (dc != null && !dc.equals("undefined")) {
			drawColor = elementColor(dc);
			bubble.setBubbleDraw(new BubbleDefaultDraw(drawColor));
		}

		Color fillColor = null;
		if (fc != null) {
			fillColor = elementColor(fc);
			bubble.setBubbleFill(new BubbleDefaultFill(fillColor));
		}

		if (fx != null && !fx.equals("undefined")) {
			if (fx.equals("fx1")) {
				bubble.setBubbleEffect(new BubbleEffect1());
			} else if (fx.equals("fx2")) {
				bubble.setBubbleEffect(new BubbleEffect2());
			} else if (fx.equals("fx3")) {
				bubble.setBubbleEffect(new BubbleEffect3());
			} else if (fx.equals("fx4")) {
				bubble.setBubbleEffect(new BubbleEffect4());
			}
		}

		return bubble;

	}
}
