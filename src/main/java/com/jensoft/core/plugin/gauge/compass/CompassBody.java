/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.compass;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.painter.marker.TriangleMarker;
import com.jensoft.core.glyphmetrics.painter.marker.TriangleMarker.TriangleDirection;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.gauge.core.GaugePartBuffer;

/**
 * <code>CompassBody</code>
 * 
 * @author sebastien janaud
 * 
 */
public class CompassBody  {

	private GeneralMetricsPath pathManagerLabel;
	private GeneralMetricsPath pathManager;
	private GeneralMetricsPath pathManagerNeedle;

	private double capDegree = 90;
	
	private GaugePartBuffer metricsPart;
	private SailCompassTick compass;

	/**
	 * create compass body
	 */
	public CompassBody() {

		

		
	}

	public double getCapDegree() {
		return capDegree;
	}

	public void setCapDegree(double capDegree) {
		this.capDegree = capDegree;
	}



	
	

	
	
	
	public void paintNeedle(Graphics2D g2d) {
//		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
//		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
//
//		
//		int startAngleDegreee = 0;
//		int extendsDegree = 360;
//		
//		int radius2 = getGauge().getRadius() - 80;
//		Arc2D arc2d2 = new Arc2D.Double(centerX - radius2, centerY - radius2, 2 * radius2, 2 * radius2, startAngleDegreee,extendsDegree, Arc2D.OPEN);
//
//		pathManagerNeedle.clearMetric();
//		pathManagerNeedle.setWindow2d(getGauge().getWindow2D());
//		pathManagerNeedle.resetPath();
//		pathManagerNeedle.setFontRenderContext(g2d.getFontRenderContext());
//		pathManagerNeedle.append(arc2d2);
//		pathManagerNeedle.setSolveGeometryRequest(true);
//
//		GlyphMetric metric = new GlyphMetric();
//		metric.setValue(capDegree);
//
//		// triangle marker
//		TriangleMarker triangle = new TriangleMarker(Color.WHITE, NanoChromatique.BLUE, 15, 20);
//		triangle.setDirection(TriangleDirection.In);
//		metric.setGlyphMetricMarkerPainter(triangle);
//
//		pathManagerNeedle.addMetric(metric);
//
//		pathManagerNeedle.setFontRenderContext(g2d.getFontRenderContext());
//		pathManagerNeedle.setSolveGeometryRequest(true);
//		List<GlyphMetric> metrics2 = pathManagerNeedle.getMetrics();
//		for (GlyphMetric m : metrics2) {
//
//			if (m.getGlyphMetricMarkerPainter() != null) {
//				m.getGlyphMetricMarkerPainter().paintGlyphMetric(g2d, m);
//			}
//
//		}
	}
	



	

}
