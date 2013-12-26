/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.needle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;

/**
 * <code>GaugeNeedleClassicWatchMinute</code>
 * 
 * <p>
 * second watch style
 * <p>
 * 
 * @since 1.0
 * @author sebastien janaud
 *
 */
public class GaugeNeedleClassicWatchSecond extends GaugeNeedlePainter {

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.gauge.core.needle.GaugeNeedlePainter#paintNeedle(java.awt.Graphics2D, com.jensoft.core.plugin.gauge.core.GaugeMetricsPath)
	 */
	@Override
	public void paintNeedle(Graphics2D g2d, GaugeMetricsPath gaugeMetricsPath) {

		Point2D needleBase = gaugeMetricsPath.getNeedleBaseAnchorBinder().bindAnchor(gaugeMetricsPath.getGauge());
		Point2D needleValue = gaugeMetricsPath.getNeedleValueAnchorBinder().bindAnchor(gaugeMetricsPath.getGauge());
		
		Line2D needleLineBase = new Line2D.Double(needleBase, needleValue);
		GeometryPath geomPath1 = new GeometryPath(needleLineBase);
		double centerX = needleBase.getX();
		double centerY = needleBase.getY();
		double px,py;
		px = centerX + 10 * Math.sin(geomPath1.angleAtLength(0)+3*Math.PI/2);
		py = centerY - 10 * Math.cos(geomPath1.angleAtLength(0)+3*Math.PI/2);
		Line2D needleLineBaseExtends = new Line2D.Double(px, py, needleValue.getX(), needleValue.getY());
		GeometryPath geomPath2 = new GeometryPath(needleLineBaseExtends);
		
		//g2d.setColor(NanoChromatique.GREEN);
		//g2d.drawRect((int)px, (int)py,4,4);
	
		double px4,py4;
		px4 = centerX + 20 * Math.sin(geomPath1.angleAtLength(0)+3*Math.PI/2);
		py4 = centerY - 20 * Math.cos(geomPath1.angleAtLength(0)+3*Math.PI/2);
		//g2d.setColor(NanoChromatique.GREEN);
		//g2d.drawRect((int)px4, (int)py4,4,4);
		
		GeneralPath path1 = new GeneralPath();
		
		
		Point2D p1l = geomPath1.orthoLeftPointAtLength(2,4);
		Point2D p1r = geomPath1.orthoRightPointAtLength(2,4);
		
//		g2d.setColor(NanoChromatique.BLUE);
//		g2d.drawRect((int)p1l.getX(), (int)p1l.getY(),4,4);
//		g2d.setColor(NanoChromatique.RED);
//		g2d.drawRect((int)p1r.getX(), (int)p1r.getY(),4,4);
		
		Point2D p2l = geomPath2.orthoLeftPointAtLength(2, 6);
		Point2D p2r = geomPath2.orthoRightPointAtLength(2,6);
		Point2D p3l = geomPath1.orthoLeftPointAtLength(geomPath1.lengthOfPath()-4, 3);
		Point2D p3r = geomPath1.orthoRightPointAtLength(geomPath1.lengthOfPath()-4,3);
		
		path1.moveTo(p1l.getX(), p1l.getY());
		//path1.lineTo(p2l.getX(), p2l.getY());
		path1.quadTo(px,py,p2l.getX(), p2l.getY());
		path1.quadTo(px4, py4, p2r.getX(), p2r.getY());
		//path1.lineTo(p2r.getX(), p2r.getY());
		//path1.lineTo(p1r.getX(), p1r.getY());
		path1.quadTo(px,py,p1r.getX(), p1r.getY());
		path1.lineTo(p3r.getX(), p3r.getY());
		path1.lineTo(needleValue.getX(),needleValue.getY());
		path1.lineTo(p3l.getX(), p3l.getY());
		path1.closePath();
		
		
		
		Line2D l = new Line2D.Double(p1l, p1r);
		Line2D l2 = new Line2D.Double(p2l,p2r);
		
		
		
		
		
		
		//g2d.draw(needleLineBase);
		//g2d.draw(l);
		//g2d.draw(l2);
		//g2d.draw(needleLineBaseExtends);
	
		g2d.setColor(ColorPalette.alpha(NanoChromatique.BLUE.brighter(),200));
		Point2D start = new Point2D.Double(px4, py4);
		Point2D end = new Point2D.Double(needleValue.getX(), needleValue.getY());

		
		LinearGradientPaint shader = new LinearGradientPaint(start, end, new float[]{0,1}, new Color[]{Color.WHITE,NanoChromatique.BLUE});
		g2d.setPaint(shader);
		//g2d.fill(path1);
		
		
		g2d.setColor(NanoChromatique.YELLOW.brighter());
		g2d.setStroke(new BasicStroke(3));
		g2d.draw(needleLineBaseExtends);
		//g2d.fill(path1);
		
		//g2d.setColor(NanoChromatique.BLUE);
		//g2d.drawRect((int)p1l.getX(), (int)p1l.getY(),4,4);
		
		//g2d.drawRect((int)p1l.getX(), (int)p1l.getY(),4,4);
		g2d.setColor(NanoChromatique.BLUE.darker());
		g2d.fillOval((int)centerX-2, (int)centerY-2, 4, 4);

	}

}
