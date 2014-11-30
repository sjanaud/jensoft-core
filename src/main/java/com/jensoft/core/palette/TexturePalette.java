/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * <code>TexturePalette</code>
 * 
 * @author Sebastien Janaud
 */
public class TexturePalette {

	/**
	 * get textured carbon fiber with triangle pattern
	 * 
	 * @return carbon texture
	 */
	public static TexturePaint getTriangleCarbonFiber() {
		int width = 10;
		int height = 10;
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHints(hints);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, width, height);
		Polygon p1 = new Polygon();
		p1.addPoint(width / 2, 0);
		p1.addPoint(width / 2, height);
		p1.addPoint(0, height / 2);
		Polygon p2 = new Polygon();
		p2.addPoint(width / 2, 0);
		p2.addPoint(width, 0);
		p2.addPoint(width, height / 2);
		Polygon p3 = new Polygon();
		p3.addPoint(width, height / 2);
		p3.addPoint(width, height);
		p3.addPoint(width / 2, height);
		g2d.setColor(Color.BLACK);
		g2d.fill(p1);
		g2d.fill(p2);
		g2d.fill(p3);
		Rectangle r = new Rectangle(0, 0, width, height);
		g2d.dispose();
		return new TexturePaint(bi, r);
	}

	/**
	 * get textured carbon fiber with square pattern
	 * 
	 * @return carbon texture
	 */
	public static TexturePaint getSquareCarbonFiber() {
		int width = 120;
		int height = 120;
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHints(hints);
		g2d.setColor(new Color(60, 60, 60));
		g2d.fillRect(0, 0, width, height);
		int w = 10;
		int h = 10;
		int x = 0;
		int y;
		for (int i = -110; i < 200; i = i + 2 * h) {
			y = i;
			x = -w / 2;
			for (int j = 0; j < 30; j++) {
				Rectangle2D rect1 = new Rectangle2D.Double(x, y, w, h);
				Point2D start1 = new Point2D.Float(x + w / 2, y);
				Point2D end1 = new Point2D.Float(x + w / 2, y + h);
				float[] dist1 = { 0.0f, 1.0f };
				Color[] colors1 = { Color.BLACK, Color.DARK_GRAY };
				LinearGradientPaint p1 = new LinearGradientPaint(start1, end1, dist1, colors1);
				g2d.setPaint(p1);
				g2d.fill(rect1);
				Rectangle2D rect0 = new Rectangle2D.Double(x, y, w, h / 2);
				Point2D start0 = new Point2D.Float(x, y / 4);
				Point2D end0 = new Point2D.Float(x + w, y / 4);
				float[] dist0 = { 0.0f, 1.0f };
				Color[] colors0 = { Color.BLACK, Color.DARK_GRAY };
				LinearGradientPaint p0 = new LinearGradientPaint(start0, end0, dist0, colors0);
				g2d.setPaint(p0);
				g2d.fill(rect0);
				x = x + w / 2;
				y = y + h / 2;
			}
		}

		Rectangle r = new Rectangle(0, 0, width, height);
		g2d.dispose();
		return new TexturePaint(bi, r);

	}

	/**
	 * get textured carbon fiber with circle perforated surface pattern
	 * 
	 * @return carbon texture
	 */
	public static TexturePaint getPerforatedCircleSurface() {
		int width = 6;
		int height = 6;
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHints(hints);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, width, height);
		g2d.setColor(Color.BLACK);
		Ellipse2D e2D = new Ellipse2D.Double(width / 8, height / 8, 6 * width / 8, 6 * height / 8);
		g2d.fill(e2D);
		Rectangle r = new Rectangle(0, 0, width, height);
		g2d.dispose();
		return new TexturePaint(bi, r);
	}

	/**
	 * get textured carbon fiber with polygon perforated surface pattern
	 * 
	 * @return carbon texture
	 */
	public static TexturePaint getPerforatedPolygonSurface() {
		int width = 8;
		int height = 8;
		int centerX = width / 2;
		int centerY = height / 2;
		double totalDegree = 360;
		float radius = new Float(width) / 2f - new Float(width) / 8f;
		double count = 6;
		double startAngleDegree = 0;
		double angleDegree = totalDegree / count;
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHints(hints);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(0, 0, width, height);
		GeneralPath path = new GeneralPath();
		for (int i = 0; i < count; i++) {
			double angle = startAngleDegree + i * angleDegree;
			double px = centerX + radius * Math.cos(Math.toRadians(angle));
			double py = centerY - radius * Math.sin(Math.toRadians(angle));
			if (i == 0) {
				path.moveTo(px, py);
			} else {
				path.lineTo(px, py);
			}
		}
		path.closePath();
		g2d.setColor(Color.black);
		g2d.fill(path);
		Rectangle r = new Rectangle(0, 0, width, height);
		g2d.dispose();
		return new TexturePaint(bi, r);
	}

	/**
	 * get textured carbon fiber with bee cell surface pattern
	 * 
	 * @return carbon texture
	 */
	public static TexturePaint getBeeCarbonTexture0() {
		return getBeeCarbonTexture1Base(6);
	}

	/**
	 * get textured carbon fiber with bee cell surface pattern
	 * 
	 * @return carbon texture
	 */
	public static TexturePaint getBeeCarbonTexture1() {
		return getBeeCarbonTexture1Base(4);
	}

	/**
	 * get textured carbon fiber with bee cell surface pattern
	 * 
	 * @param radius
	 *            the texture pattern radius
	 * @return carbon texture
	 */
	private static TexturePaint getBeeCarbonTexture1Base(float radius) {
		int width = (int) (2 * radius * Math.sqrt(3));
		int height = (int) (2 * radius);
		radius = radius - radius / 6;
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(new Color(84, 84, 92));
		g2d.fillRect(0, 0, width, height);
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHints(hints);
		Point2D centerTopLeft = new Point2D.Double(0, 0);
		Point2D centerTopRight = new Point2D.Double(width, 0);
		Point2D centerCenter = new Point2D.Double(width / 2, height / 2);
		Point2D centerBottomLeft = new Point2D.Double(0, height);
		Point2D centerBottomRight = new Point2D.Double(width, height);
		Point2D centers[] = new Point2D[] { centerTopLeft, centerTopRight, centerCenter, centerBottomLeft, centerBottomRight };
		for (int i = 0; i < centers.length; i++) {
			GeneralPath path = new GeneralPath();
			double angle = 0;
			for (int j = 0; j <= 5; j++) {
				double px, py;
				px = centers[i].getX() + radius * Math.cos(angle);
				py = centers[i].getY() - radius * Math.sin(angle);
				if (angle == 0) {
					path.moveTo(px, py);
				} else {
					path.lineTo(px, py);
				}
				angle = angle + Math.PI / 3;
			}
			path.closePath();
			Point2D center = new Point2D.Double(centers[i].getX(), centers[i].getY());
			float focusRadius = radius + radius / 3;
			Point2D focus = new Point2D.Double(centers[i].getX(), centers[i].getY() + 10);
			float[] dist = { 0.0f, 0.8f, 1.0f };
			Color[] colors = { new Color(71, 72, 77), new Color(46, 47, 49), new Color(21, 22, 26).darker() };
			RadialGradientPaint p = new RadialGradientPaint(center, focusRadius, focus, dist, colors, CycleMethod.NO_CYCLE);
			g2d.setPaint(p);
			g2d.fill(path);
			Point2D start = new Point2D.Double(centers[i].getX(), centers[i].getY() - radius);
			Point2D end = new Point2D.Double(centers[i].getX(), centers[i].getY() + radius);
			float[] dist2 = { 0.0f, 0.2f, 0.3f, 1.0f };
			Color[] colors2 = { new Color(0, 0, 0, 255), new Color(0, 0, 0, 200), new Color(0, 0, 0, 0), new Color(0, 0, 0, 0) };
			LinearGradientPaint lgp = new LinearGradientPaint(start, end, dist2, colors2);
			g2d.setPaint(lgp);
			g2d.fill(path);
		}
		Rectangle2D r = new Rectangle(0, 0, width, height);
		g2d.dispose();
		return new TexturePaint(bi, r);
	}

	/**
	 * get textured carbon fiber with bee cell surface pattern
	 * 
	 * @return carbon texture
	 */
	public static TexturePaint getBeeCarbonTexture2() {
		return getBeeCarbonTexture2Base(4);
	}

	/**
	 * get textured carbon fiber with bee cell surface pattern
	 * 
	 * @return carbon texture
	 */
	public static TexturePaint getBeeCarbonTexture3() {
		return getBeeCarbonTexture2Base(6);
	}

	/**
	 * get textured carbon fiber with bee cell surface pattern
	 * 
	 * @param radius
	 *            the texture pattern radius
	 * @return carbon texture
	 */
	private static TexturePaint getBeeCarbonTexture2Base(float radius) {
		int width = (int) (2 * radius * Math.sqrt(3));
		int height = (int) (2 * radius);
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHints(hints);
		g2d.setColor(new Color(84, 84, 92));
		g2d.fillRect(0, 0, width, height);
		Point2D centerTopLeft = new Point2D.Double(0, 0);
		Point2D centerTopRight = new Point2D.Double(width, 0);
		Point2D centerCenter = new Point2D.Double(width / 2, height / 2);
		Point2D centerBottomLeft = new Point2D.Double(0, height);
		Point2D centerBottomRight = new Point2D.Double(width, height);
		Point2D centers[] = new Point2D[] { centerTopLeft, centerTopRight, centerCenter, centerBottomLeft, centerBottomRight };
		for (int i = 0; i < centers.length; i++) {
			GeneralPath path = new GeneralPath();
			double angle = 0;
			for (int j = 0; j <= 5; j++) {
				double px, py;
				px = centers[i].getX() + radius * Math.cos(angle);
				py = centers[i].getY() - radius * Math.sin(angle);
				if (angle == 0) {
					path.moveTo(px, py);
				} else {
					path.lineTo(px, py);
				}
				angle = angle + Math.PI / 3;
			}
			path.closePath();
			Point2D center = new Point2D.Double(centers[i].getX(), centers[i].getY());
			float focusRadius = radius - radius / 3;
			Point2D focus = new Point2D.Double(centers[i].getX() + radius - radius / 3, centers[i].getY() + radius - radius / 3);
			float[] dist = { 0.0f, 0.2f, 1.0f };
			Color[] colors = { Color.GRAY, Color.DARK_GRAY, Color.BLACK };
			RadialGradientPaint p = new RadialGradientPaint(center, focusRadius, focus, dist, colors, CycleMethod.NO_CYCLE);
			g2d.setPaint(p);
			g2d.fill(path);
		}
		Rectangle2D r = new Rectangle(0, 0, width, height);
		g2d.dispose();
		return new TexturePaint(bi, r);
	}

	/**
	 * get interlaced carbon fiber texture with default thickness and colors
	 * 
	 * @return interlaced carbon fiber
	 */
	public static TexturePaint getInterlacedCarbon1() {
		Color vstart = new Color(71, 72, 76);
		Color vend = new Color(21, 22, 26);
		return getInterlacedCarbonTextureBase(5, vstart, vend, vstart, vend);
	}

	/**
	 * get interlaced carbon fiber texture with default thickness and colors
	 * 
	 * @return interlaced carbon fiber
	 */
	public static TexturePaint getInterlacedCarbon2() {
		Color vstart = new Color(71, 72, 76);
		Color vend = new Color(21, 22, 26);
		return getInterlacedCarbonTextureBase(10, vstart, vend, vstart, vend);
	}
	
	/**
	 * get interlaced carbon fiber texture with given thickness and default colors
	 * 
	 * @return interlaced carbon fiber
	 */
	public static TexturePaint getInterlacedCarbon(double thicknessPattern) {
		Color vstart = new Color(71, 72, 76);
		Color vend = new Color(21, 22, 26);
		return getInterlacedCarbonTextureBase(thicknessPattern, vstart, vend, vstart, vend);
	}

	/**
	 * get interlaced carbon fiber texture with default thickness and colors
	 * 
	 * @return interlaced carbon fiber
	 */
	public static TexturePaint getInterlacedCarbonTextureBase(double thicknessPattern, Color hstart, Color hend, Color vstart, Color vend) {

		int width = (int) (4 * thicknessPattern);
		int height = (int) (4 * thicknessPattern);
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bi.createGraphics();
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHints(hints);

		// horizontal

		paintHInterlacedFiber(g2d, thicknessPattern, -thicknessPattern, 0, hstart, hend);
		paintHInterlacedFiber(g2d, thicknessPattern, 0, 3 * thicknessPattern, hstart, hend);
		paintHInterlacedFiber(g2d, thicknessPattern, thicknessPattern, 2 * thicknessPattern, hstart, hend);
		paintHInterlacedFiber(g2d, thicknessPattern, 2 * thicknessPattern, thicknessPattern, hstart, hend);
		paintHInterlacedFiber(g2d, thicknessPattern, 3 * thicknessPattern, 0, hstart, hend);

		// vertical

		paintVInterlacedFiber(g2d, thicknessPattern, 0, thicknessPattern, vstart, vend);
		paintVInterlacedFiber(g2d, thicknessPattern, thicknessPattern, 0, vstart, vend);
		paintVInterlacedFiber(g2d, thicknessPattern, 2 * thicknessPattern, -thicknessPattern, vstart, vend);
		paintVInterlacedFiber(g2d, thicknessPattern, 2 * thicknessPattern, 3 * thicknessPattern, vstart, vend);
		paintVInterlacedFiber(g2d, thicknessPattern, 3 * thicknessPattern, 2 * thicknessPattern, vstart, vend);

		Rectangle2D model = new Rectangle(0, 0, width, height);
		g2d.dispose();
		return new TexturePaint(bi, model);
	}

	/**
	 * paint horizontal interlaced fiber
	 * @param g2d
	 * @param thicknessPattern
	 * @param px
	 * @param py
	 * @param start
	 * @param end
	 */
	private static void paintHInterlacedFiber(Graphics2D g2d, double thicknessPattern, double px, double py, Color start, Color end) {
		double hw = 2 * thicknessPattern;
		double hh = thicknessPattern;
		Rectangle2D r = new Rectangle2D.Double(px, py, hw, hh);
		LinearGradientPaint p = new LinearGradientPaint(new Point2D.Double(px, py), new Point2D.Double(px + 2 * thicknessPattern, py), new float[] { 0f, 1f }, new Color[] { start, end });
		g2d.setPaint(p);
		g2d.fill(r);
	}

	/**
	 * paint vertical interlaced fiber
	 * @param g2d
	 * @param thicknessPattern
	 * @param px
	 * @param py
	 * @param start
	 * @param end
	 */
	private static void paintVInterlacedFiber(Graphics2D g2d, double thicknessPattern, double px, double py, Color start, Color end) {
		double vw = thicknessPattern;
		double vh = 2 * thicknessPattern;
		Rectangle2D r = new Rectangle2D.Double(px, py, vw, vh);
		LinearGradientPaint p = new LinearGradientPaint(new Point2D.Double(px, py), new Point2D.Double(px, py + 2 * thicknessPattern), new float[] { 0f, 1f }, new Color[] { start, end });
		g2d.setPaint(p);
		g2d.fill(r);
	}

}
