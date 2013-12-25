/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.speedometer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.NeedleGaugePainter;

public class SpeedometerNeedle extends NeedleGaugePainter {
	

	private void paintNeedle(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
		int radius = getGauge().getRadius() - 10;

		GeneralMetricsPath pathManager = getPathManager();
		
		pathManager.setSolveGeometryRequest(true);
		pathManager.setFontRenderContext(g2d.getFontRenderContext());

		getPathManager().setWindow2d(getGauge().getWindow2D());

		Point2D center = new Point2D.Double(centerX, centerY);

		Point2D pNeedle2 = getPathManager().getRadialPoint(getCurentValue(), 20, Side.SideRight);
		Point2D pNeedle2TT = getGauge().getWindow2D().userToPixel(pNeedle2);
		Line2D lNeedle = new Line2D.Double(center.getX(), center.getY(), pNeedle2.getX(), pNeedle2.getY());

		BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke stroke2 = new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		Shape tShape = stroke.createStrokedShape(lNeedle);
		Shape tShape2 = stroke2.createStrokedShape(lNeedle);

		Area area = new Area(tShape);
		Area area2 = new Area(tShape2);

		int w = 20;
		Ellipse2D cacheCenter = new Ellipse2D.Double(centerX - w, centerY - w, 2 * w, 2 * w);
		Area area3 = new Area(cacheCenter);

		g2d.setColor(ColorPalette.alpha(NanoChromatique.GREEN, 120));

		g2d.fill(area2);

		g2d.setColor(NanoChromatique.GREEN);

		g2d.fill(area);

		// deco gradient
		// int w0 = 25;
		// int r = NanoChromatique.PINK.getRed();
		// int g = NanoChromatique.PINK.getGreen();
		// int b = NanoChromatique.PINK.getBlue();

		// //Ellipse2D cacheCenter0 = new
		// Ellipse2D.Double(centerX-w0,centerY-w0,2*w0,2*w0);
		// RoundGradientPaint rgp0 = new RoundGradientPaint(centerX, centerY,new
		// Color(r,g,b,250), new Point2D.Double(5, 25), new Color(r,g,b,40));
		// //RadialGradientPaint rgp0 = new RadialGradientPaint(new
		// Point2D.Double(centerX,centerY), 25, new float[]{0f,1f},new
		// Color[]{new Color(r,g,b,250),new Color(r,g,b,40)});
		//
		// g2d.setPaint(rgp0);

		// RoundGradientPaint rgp = new RoundGradientPaint(centerX,
		// centerY,Color.BLACK,new Point2D.Double(0, 20),
		// Color.DARK_GRAY.darker());
		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(centerX, centerY), 20, new float[] { 0f, 1f }, new Color[] { ColorPalette.alpha(Color.BLACK,180), ColorPalette.alpha(Color.BLACK,100) });

		g2d.setPaint(rgp);
		g2d.fill(cacheCenter);
		g2d.setStroke(new BasicStroke(2f));

		g2d.setColor(NanoChromatique.GREEN);

		g2d.draw(cacheCenter);

	}

	

	@Override
	public void paintNeedle(Graphics2D g2d, RadialGauge radialGauge) {
		paintNeedle(g2d);
		//paintDeco1(g2d);

	}

}
