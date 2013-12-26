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

import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;

public class GaugeNeedleClassicPainter extends GaugeNeedlePainter {

	@Override
	public void paintNeedle(Graphics2D g2d, GaugeMetricsPath gaugeMetricsPath) {
//		int radius = gaugeMetricsPath.getGauge().getRadius();
//		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX() - radius + radius / 4;// (int)getGauge().getX();
//		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
//
//		metricsPath1.setFontRenderContext(g2d.getFontRenderContext());
//
//		metricsPath1.setWindow2d(getGauge().getWindow2D());
//
//		Point2D center = new Point2D.Double(centerX, centerY);
//
//		Point2D pNeedle2 = metricsPath1.getRadialPoint(50, 20, Side.SideLeft);
		// Point2D pNeedle2TT = getGauge().getWindow2D().userToPixel(pNeedle2);
		//Line2D lNeedle = new Line2D.Double(center.getX(), center.getY(), pNeedle2.getX(), pNeedle2.getY());
		
		
		Point2D needleBase = gaugeMetricsPath.getNeedleBaseAnchorBinder().bindAnchor(gaugeMetricsPath.getGauge());
		Point2D needleValue = gaugeMetricsPath.getNeedleValueAnchorBinder().bindAnchor(gaugeMetricsPath.getGauge());
		
		Line2D lNeedle = new Line2D.Double(needleBase, needleValue);

		BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke stroke2 = new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		Shape tShape = stroke.createStrokedShape(lNeedle);
		Shape tShape2 = stroke2.createStrokedShape(lNeedle);

		Area area = new Area(tShape);
		Area area2 = new Area(tShape2);

		int w = 10;
		Ellipse2D cacheCenter = new Ellipse2D.Double(needleBase.getX() - w, needleBase.getY() - w, 2 * w, 2 * w);
		//Area area3 = new Area(cacheCenter);

		g2d.setColor(ColorPalette.alpha(NanoChromatique.RED, 120));

		g2d.fill(area2);

		g2d.setColor(NanoChromatique.RED);

		g2d.fill(area);

		// deco gradient
//		int w0 = 25;
//		int r = NanoChromatique.PINK.getRed();
//		int g = NanoChromatique.PINK.getGreen();
//		int b = NanoChromatique.PINK.getBlue();

		//Ellipse2D cacheCenter0 = new Ellipse2D.Double(needleBase.getX() - w0, needleBase.getY() - w0, 2 * w0, 2 * w0);

		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(needleBase.getX(), needleBase.getY()), 20, new float[] { 0f, 1f }, new Color[] { Color.BLACK, Color.BLACK.darker() });
		g2d.setPaint(rgp);
		g2d.fill(cacheCenter);
		//g2d.setStroke(new BasicStroke(2f));
		// g2d.setColor(FilPalette.FIL_GRIS4);
		//Color PINNACLE4 = new Color(105, 90, 168);
		//g2d.setColor(PINNACLE4);

		// g2d.draw(cacheCenter);

	}

}
