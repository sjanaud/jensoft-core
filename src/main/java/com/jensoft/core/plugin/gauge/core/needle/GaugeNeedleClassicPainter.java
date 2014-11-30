/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.needle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.jensoft.core.palette.color.ColorPalette;
import com.jensoft.core.palette.color.NanoChromatique;
import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;

/**
 * <code>GaugeNeedleClassicPainter</code>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class GaugeNeedleClassicPainter extends GaugeNeedlePainter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.needle.GaugeNeedlePainter#paintNeedle
	 * (java.awt.Graphics2D,
	 * com.jensoft.core.plugin.gauge.core.GaugeMetricsPath)
	 */
	@Override
	public void paintNeedle(Graphics2D g2d, GaugeMetricsPath gaugeMetricsPath) {

		Point2D needleBase = gaugeMetricsPath.getNeedleBaseAnchorBinder().bindAnchor(gaugeMetricsPath.getBody().getGauge());
		Point2D needleValue = gaugeMetricsPath.getNeedleValueAnchorBinder().bindAnchor(gaugeMetricsPath.getBody().getGauge());

		Line2D lNeedle = new Line2D.Double(needleBase, needleValue);

		BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke stroke2 = new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		Shape tShape = stroke.createStrokedShape(lNeedle);
		Shape tShape2 = stroke2.createStrokedShape(lNeedle);

		Area area = new Area(tShape);
		Area area2 = new Area(tShape2);

		int w = 10;
		Ellipse2D cacheCenter = new Ellipse2D.Double(needleBase.getX() - w, needleBase.getY() - w, 2 * w, 2 * w);
		// Area area3 = new Area(cacheCenter);

		g2d.setColor(ColorPalette.alpha(NanoChromatique.RED, 120));

		g2d.fill(area2);

		g2d.setColor(NanoChromatique.RED);

		g2d.fill(area);

		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(needleBase.getX(), needleBase.getY()), 20, new float[] { 0f, 1f }, new Color[] { Color.BLACK, Color.BLACK.darker() });
		g2d.setPaint(rgp);
		g2d.fill(cacheCenter);
		// g2d.setStroke(new BasicStroke(2f));
		// g2d.setColor(FilPalette.FIL_GRIS4);
		Color PINNACLE4 = new Color(105, 90, 168);
		g2d.setColor(PINNACLE4);

		g2d.draw(cacheCenter);

	}

}
