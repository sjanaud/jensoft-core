/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.watch;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.glyphmetrics.Side;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.NeedleGaugePainter;

public class WatchNeedle extends NeedleGaugePainter {
	

	private int hour = 2;
	private int minutes;
	private int second;
	
	private GeneralMetricsPath hourMetricsManager;
	private GeneralMetricsPath minuteMetricsManager;
	private GeneralMetricsPath secondMetricsManager;
	
	private void paintNeedle(Graphics2D g2d,GeneralMetricsPath path,double value,int radialDivergence, int thickness) {
		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
		

		//GeneralMetricsPath pathManager = path;		
		path.setSolveGeometryRequest(true);
		path.setFontRenderContext(g2d.getFontRenderContext());
		path.setWindow2d(getGauge().getWindow2D());

		Point2D center = new Point2D.Double(centerX, centerY);
		Point2D pNeedle2 = path.getRadialPoint(value, radialDivergence, Side.SideRight);
		
		Line2D lNeedle = new Line2D.Double(center.getX(), center.getY(), pNeedle2.getX(), pNeedle2.getY());

		BasicStroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		BasicStroke stroke2 = new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		Shape tShape = stroke.createStrokedShape(lNeedle);
		Shape tShape2 = stroke2.createStrokedShape(lNeedle);

		Area area = new Area(tShape);
		Area area2 = new Area(tShape2);

		int w = 5;
		Ellipse2D cacheCenter = new Ellipse2D.Double(centerX - w, centerY - w, 2 * w, 2 * w);
		

		g2d.setColor(ColorPalette.alpha(NanoChromatique.YELLOW.brighter(), 160));

		g2d.fill(area2);

		g2d.setColor(NanoChromatique.YELLOW);

		g2d.fill(area);
		RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(centerX, centerY), 20, new float[] { 0f, 1f }, new Color[] { ColorPalette.alpha(Color.BLACK,180), ColorPalette.alpha(Color.BLACK,100) });

		g2d.setPaint(rgp);
		g2d.fill(cacheCenter);
		g2d.setStroke(new BasicStroke(2f));

		g2d.setColor(NanoChromatique.BLUE.brighter());

		g2d.draw(cacheCenter);
	}
	
	
	private void paintDeco1(Graphics2D g2d) {

		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
		int radius = 15;

		Donut2D donut1 = new Donut2D();
		donut1.setCenterX((int) centerX);
		donut1.setCenterY((int) centerY);
		donut1.setInnerRadius(10);
		donut1.setOuterRadius(15);
		donut1.setStartAngleDegree(32);
		donut1.setExplose(0);

		Donut2DSlice s1 = new Donut2DSlice("D1", new Color(255, 255, 255, 80));
		s1.setAlpha(1f);
		s1.setValue(10);

		Donut2DSlice s2 = new Donut2DSlice("D2", new Color(255, 255, 255, 80));
		s2.setValue(5);
		s2.setAlpha(0f);

		Donut2DSlice s3 = new Donut2DSlice("D3", new Color(255, 255, 255, 0));
		s3.setValue(10);
		s3.setAlpha(1f);
		Donut2DSlice s4 = new Donut2DSlice("D4", new Color(255, 255, 255, 0));
		s4.setValue(5);
		s4.setAlpha(0f);
		Donut2DSlice s5 = new Donut2DSlice("D5", new Color(255, 255, 255, 0));
		s5.setValue(10);
		s5.setAlpha(1f);
		Donut2DSlice s6 = new Donut2DSlice("D6", new Color(255, 255, 255, 0));
		s6.setValue(5);
		s6.setAlpha(0f);
		Donut2DSlice s7 = new Donut2DSlice("D7", new Color(255, 255, 255, 0));
		s7.setValue(10);
		s7.setAlpha(1f);
		Donut2DSlice s8 = new Donut2DSlice("D8", new Color(255, 255, 255, 0));
		s8.setValue(5);
		s8.setAlpha(0f);

		donut1.addSlice(s1);
		donut1.addSlice(s2);
		donut1.addSlice(s3);
		donut1.addSlice(s4);
		donut1.addSlice(s5);
		donut1.addSlice(s6);
		donut1.addSlice(s7);
		donut1.addSlice(s8);

		// donut.buildDonut();

		Point2D gcenter = new Point2D.Double(centerX, centerY);
		float gradius = (float) donut1.getOuterRadius();
		float[] dist = { 0.0f, 0.5f, 1.0f };
		int r = Color.BLACK.getRed();
		int g = Color.BLACK.getGreen();
		int b = Color.BLACK.getBlue();
		Color[] colors = { new Color(r, g, b, 100), new Color(r, g, b, 250), new Color(r, g, b, 100) };
		RadialGradientPaint p = new RadialGradientPaint(gcenter, gradius, dist, colors);

		// RoundGradientPaint rgp2 = new RoundGradientPaint(centerX, centerY,
		// new Color(255,255,255,200),
		//
		// new Point2D.Double(5, donut1.getExternalRadius()), new
		// Color(255,255,255,0));
		donut1.solveGeometry();
		g2d.setPaint(p);
		List<Donut2DSlice> sections = donut1.getSlices();

		for (int j = 0; j < sections.size(); j++) {

			Donut2DSlice s = sections.get(j);

			// section

			g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, s.getAlpha()));
			// g2d.draw(s.getPath());

			// g2d.setComposite(java.awt.AlphaComposite.getInstance(
			// java.awt.AlphaComposite.SRC_OVER, s.getAlphaTransparence()));

			g2d.fill(s.getSlicePath());
			g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));

		}
	}
	
	
	public void paintHourNeedle(Graphics2D g2d, RadialGauge radialGauge) {
		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
		
		GeneralMetricsPath path = getHourMetricsManager();
		
		path.setSolveGeometryRequest(true);
		path.setFontRenderContext(g2d.getFontRenderContext());
		path.setWindow2d(getGauge().getWindow2D());

		Point2D center = new Point2D.Double(centerX, centerY);
		Point2D needle = path.getRadialPoint(2, 50, Side.SideRight);
		
		Line2D needleLineBase = new Line2D.Double(center.getX(), center.getY(), needle.getX(), needle.getY());
		GeometryPath geomPath1 = new GeometryPath(needleLineBase);
		
		double px,py;
		px = centerX + 10 * Math.sin(geomPath1.angleAtLength(0)+3*Math.PI/2);
		py = centerY - 10 * Math.cos(geomPath1.angleAtLength(0)+3*Math.PI/2);
		Line2D needleLineBaseExtends = new Line2D.Double(px, py, needle.getX(), needle.getY());
		GeometryPath geomPath2 = new GeometryPath(needleLineBaseExtends);
		
		//g2d.setColor(NanoChromatique.GREEN);
		//g2d.drawRect((int)px, (int)py,4,4);
	
		double px4,py4;
		px4 = centerX + 20 * Math.sin(geomPath1.angleAtLength(0)+3*Math.PI/2);
		py4 = centerY - 20 * Math.cos(geomPath1.angleAtLength(0)+3*Math.PI/2);
		//g2d.setColor(NanoChromatique.GREEN);
		//g2d.drawRect((int)px4, (int)py4,4,4);
		
		GeneralPath path1 = new GeneralPath();
		
		
		Point2D p1l = geomPath1.orthoLeftPointAtLength(2, 6);
		Point2D p1r = geomPath1.orthoRightPointAtLength(2,6);
		
//		g2d.setColor(NanoChromatique.BLUE);
//		g2d.drawRect((int)p1l.getX(), (int)p1l.getY(),4,4);
//		g2d.setColor(NanoChromatique.RED);
//		g2d.drawRect((int)p1r.getX(), (int)p1r.getY(),4,4);
		
		Point2D p2l = geomPath2.orthoLeftPointAtLength(2, 8);
		Point2D p2r = geomPath2.orthoRightPointAtLength(2,8);
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
		path1.lineTo(needle.getX(),needle.getY());
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
		Point2D end = new Point2D.Double(needle.getX(), needle.getY());

		
		LinearGradientPaint shader = new LinearGradientPaint(start, end, new float[]{0,1}, new Color[]{Color.WHITE,NanoChromatique.BLUE});
		g2d.setPaint(shader);
		g2d.fill(path1);
		g2d.setColor(NanoChromatique.BLUE);
		g2d.draw(path1);
		//g2d.fill(path1);
		
		//g2d.setColor(NanoChromatique.BLUE);
		//g2d.drawRect((int)p1l.getX(), (int)p1l.getY(),4,4);
		
		//g2d.drawRect((int)p1l.getX(), (int)p1l.getY(),4,4);
		g2d.setColor(NanoChromatique.BLUE.darker());
		g2d.fillOval((int)centerX-2, (int)centerY-2, 4, 4);
	}
	
	public void paintMinuteNeedle(Graphics2D g2d, RadialGauge radialGauge) {
		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
		
		GeneralMetricsPath path = getMinuteMetricsManager();
		
		path.setSolveGeometryRequest(true);
		path.setFontRenderContext(g2d.getFontRenderContext());
		path.setWindow2d(getGauge().getWindow2D());

		Point2D center = new Point2D.Double(centerX, centerY);
		Point2D needle = path.getRadialPoint(30, 20, Side.SideRight);
		
		Line2D needleLineBase = new Line2D.Double(center.getX(), center.getY(), needle.getX(), needle.getY());
		GeometryPath geomPath1 = new GeometryPath(needleLineBase);
		
		double px,py;
		px = centerX + 10 * Math.sin(geomPath1.angleAtLength(0)+3*Math.PI/2);
		py = centerY - 10 * Math.cos(geomPath1.angleAtLength(0)+3*Math.PI/2);
		Line2D needleLineBaseExtends = new Line2D.Double(px, py, needle.getX(), needle.getY());
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
		path1.lineTo(needle.getX(),needle.getY());
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
		Point2D end = new Point2D.Double(needle.getX(), needle.getY());

		
		LinearGradientPaint shader = new LinearGradientPaint(start, end, new float[]{0,1}, new Color[]{Color.WHITE,NanoChromatique.BLUE});
		g2d.setPaint(shader);
		g2d.fill(path1);
		
		g2d.setColor(NanoChromatique.WHITE);
		g2d.draw(path1);
		//g2d.fill(path1);
		
		//g2d.setColor(NanoChromatique.BLUE);
		//g2d.drawRect((int)p1l.getX(), (int)p1l.getY(),4,4);
		
		//g2d.drawRect((int)p1l.getX(), (int)p1l.getY(),4,4);
		g2d.setColor(NanoChromatique.BLUE.darker());
		g2d.fillOval((int)centerX-2, (int)centerY-2, 4, 4);
	}
	
	public void paintSecondeNeedle(Graphics2D g2d, RadialGauge radialGauge) {
		double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
		double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
		
		GeneralMetricsPath path = getSecondMetricsManager();
		
		path.setSolveGeometryRequest(true);
		path.setFontRenderContext(g2d.getFontRenderContext());
		path.setWindow2d(getGauge().getWindow2D());

		Point2D center = new Point2D.Double(centerX, centerY);
		Point2D needle = path.getRadialPoint(48, 20, Side.SideRight);
		
		Line2D needleLineBase = new Line2D.Double(center.getX(), center.getY(), needle.getX(), needle.getY());
		GeometryPath geomPath1 = new GeometryPath(needleLineBase);
		
		double px,py;
		px = centerX + 10 * Math.sin(geomPath1.angleAtLength(0)+3*Math.PI/2);
		py = centerY - 10 * Math.cos(geomPath1.angleAtLength(0)+3*Math.PI/2);
		Line2D needleLineBaseExtends = new Line2D.Double(px, py, needle.getX(), needle.getY());
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
		path1.lineTo(needle.getX(),needle.getY());
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
		Point2D end = new Point2D.Double(needle.getX(), needle.getY());

		
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

	@Override
	public void paintNeedle(Graphics2D g2d, RadialGauge radialGauge) {
		
		hour = 2;
		minutes = 55;
		second = 27;
		
		//paintNeedle(g2d, getHourMetricsManager(), hour, 60,10);
		//paintNeedle(g2d, getMinuteMetricsManager(), minutes,40,6);
		//paintNeedle(g2d, getSecondMetricsManager(), second,20,2);
		
		paintHourNeedle(g2d, radialGauge);
		paintMinuteNeedle(g2d, radialGauge);
		paintSecondeNeedle(g2d, radialGauge);
	}


	public GeneralMetricsPath getHourMetricsManager() {
		return hourMetricsManager;
	}


	public void setHourMetricsManager(GeneralMetricsPath hourMetricsManager) {
		this.hourMetricsManager = hourMetricsManager;
	}


	public GeneralMetricsPath getMinuteMetricsManager() {
		return minuteMetricsManager;
	}


	public void setMinuteMetricsManager(GeneralMetricsPath minuteMetricsManager) {
		this.minuteMetricsManager = minuteMetricsManager;
	}


	public GeneralMetricsPath getSecondMetricsManager() {
		return secondMetricsManager;
	}


	public void setSecondMetricsManager(GeneralMetricsPath secondMetricsManager) {
		this.secondMetricsManager = secondMetricsManager;
	}
	
	

}
