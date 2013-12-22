/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core.glass;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.device.PartBuffer;
import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.glyphmetrics.GlyphUtil;
import com.jensoft.core.palette.PetalPalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.GaugePartBuffer;
import com.jensoft.core.plugin.gauge.core.GlassGaugePainter;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.Pie.PieNature;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.painter.effect.AbstractPieEffect;
import com.jensoft.core.plugin.pie.painter.effect.CubicEffectFrame;
import com.jensoft.core.plugin.pie.painter.effect.PieCubicEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieLinearEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieRadialEffect;
import com.jensoft.core.window.Window2D;

/**
 * <code>GaugeGlass</code>
 * 
 * @author sebastien janaud
 * 
 */
public abstract class GaugeGlass extends GlassGaugePainter {

	/** glass part buffer */
	private GaugePartBuffer glassPartBuffer;

	/**
	 * create abstract gauge glass
	 */
	public GaugeGlass() {
	}

	public GaugePartBuffer getGlassPartBuffer() {
		return glassPartBuffer;
	}

	public void setGlassPartBuffer(GaugePartBuffer glassPartBuffer) {
		this.glassPartBuffer = glassPartBuffer;
	}

	public static class Glass1 extends GaugeGlass {


		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {
			double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
			double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
			int radius = getGauge().getRadius() - 5;

			if (getGlassPartBuffer() == null) {
				
				GaugePartBuffer buffer = new GaugePartBuffer(getGauge());
				setGlassPartBuffer(buffer);
				//partDeco1 = new PartBuffer(centerX - radius / 2, centerY - radius / 2, 2 * radius, 2 * radius);

				Graphics2D g2dPart = buffer.getGraphics();
				g2dPart.setRenderingHints(g2d.getRenderingHints());
				//g2dPart.translate(-centerX + radius, -centerY + radius);

				int startAngleDegreee = 30;
				int endAngleDegree = 175;

				g2dPart.setStroke(new BasicStroke(0.4f));

				g2dPart.setColor(Color.WHITE);


				Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, endAngleDegree - startAngleDegreee, Arc2D.OPEN);

				//DEBUG ARC
				// g2d.setColor(Color.RED);
				// g2d.draw(arc2d);

				Point2D p1 = new Point2D.Double(centerX + radius * Math.cos(Math.toRadians(endAngleDegree)), centerY - radius * Math.sin(Math.toRadians(endAngleDegree)));

				Point2D p2 = new Point2D.Double(centerX + radius * Math.cos(Math.toRadians(startAngleDegreee)), centerY - radius * Math.sin(Math.toRadians(startAngleDegreee)));

				Point2D ctrl1 = new Point2D.Double(centerX - radius / 3, centerY - radius / 2);

				Point2D ctrl2 = new Point2D.Double(centerX + radius / 3, centerY);


				CubicCurve2D cubicCurve = new CubicCurve2D.Double(p1.getX(), p1.getY(), ctrl1.getX(), ctrl1.getY(), ctrl2.getX(), ctrl2.getY(), p2.getX(), p2.getY());

				g2dPart.setColor(Color.GREEN);


				GeneralPath path = new GeneralPath();

				path.append(arc2d, false);

				path.append(cubicCurve, true);

				path.closePath();

				Point2D pG1 = new Point2D.Double(path.getBounds().getX() + path.getBounds().getWidth(), path.getBounds().getY() + path.getBounds().getHeight() / 2);
				Point2D pG2 = new Point2D.Double(path.getBounds().getX(), path.getBounds().getY() + path.getBounds().getHeight() / 2);
				
				GradientPaint gPaint = new GradientPaint(

				pG1, new Color(255, 255, 255, 80),

				pG2, new Color(255, 255, 255, 0)

				);

				g2dPart.setPaint(gPaint);

				g2dPart.fill(path);

				

			} 
			
			g2d.drawImage(getGlassPartBuffer().getBuffer(), (int) centerX - radius, (int) centerY - radius, 2 * radius, 2 * radius, null);

		}

	}

	public static class Glass2 extends GaugeGlass {

		private PartBuffer partDeco2;

		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {
			double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
			double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
			int radius = getGauge().getRadius() - 5;

			if (partDeco2 == null) {
				// System.out.println("null");
				partDeco2 = new PartBuffer(centerX - radius / 2, centerY - radius / 2, 2 * radius, 2 * radius);

				Graphics2D g2dPart = partDeco2.getBuffer().createGraphics();
				g2dPart.setRenderingHints(g2d.getRenderingHints());

				Donut2D donut1 = new Donut2D();
				donut1.setCenterX((int) centerX);
				donut1.setCenterY((int) centerY);
				// donut1.setInternalRadius(radius - 30);
				donut1.setInnerRadius(0);
				donut1.setOuterRadius(radius);
				donut1.setStartAngleDegree(130);

				Donut2DSlice s1 = new Donut2DSlice("D1", new Color(255, 255, 255, 80));
				s1.setAlpha(0.2f);
				s1.setValue(10.0);

				donut1.addSlice(s1);

				// donut.buildDonut();
				Point2D center = new Point2D.Double(centerX, centerY);

				float[] dist = { 0.0f, 0.8f, 1.0f };
				Color[] colors = { new Color(255, 255, 255, 50), new Color(255, 255, 255, 20), new Color(255, 255, 255, 0) };
				RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

				// RoundGradientPaint rgp2 = new RoundGradientPaint(centerX,
				// centerY,
				// new Color(255,255,255,255),
				//
				// new Point2D.Double(5, donut1.getExternalRadius()), new
				// Color(255,255,255,0));
				donut1.solveGeometry();
				// g2d.setPaint(rgp2);
				g2dPart.setPaint(p);
				List<Donut2DSlice> sections = donut1.getSlices();

				for (int j = 0; j < sections.size(); j++) {

					Donut2DSlice s = sections.get(j);

					g2dPart.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.6f));

					g2dPart.fill(s.getSlicePath());
					g2dPart.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));

				}

				g2d.drawImage(partDeco2.getBuffer(), (int) centerX - radius, (int) centerY - radius, 2 * radius, 2 * radius, null);
			} else {
				// System.out.println(" paint from cache oart deco2");
				g2d.drawImage(partDeco2.getBuffer(), (int) centerX - radius, (int) centerY - radius, 2 * radius, 2 * radius, null);
			}

		}

	}

	public static class Glass3 extends GaugeGlass {

		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {
			double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
			double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
			int gaugeRadius = getGauge().getRadius();

			Point2D p1 = new Point2D.Double(centerX, centerY + gaugeRadius);
			Ellipse2D e1 = new Ellipse2D.Double(p1.getX() - 2, p1.getY() - 2, 4, 4);
			g2d.setColor(Color.CYAN);
			g2d.draw(e1);

			Point2D center = new Point2D.Double(centerX, centerY + gaugeRadius - 40);
			float radius = 60;
			Point2D focus = new Point2D.Double(centerX, centerY + gaugeRadius - 20);
			float[] dist = { 0.0f, 1.0f };
			Color[] colors = { new Color(255, 255, 255, 100), new Color(255, 255, 255, 0) };
			AffineTransform af = new AffineTransform();
			af.scale(1, 0.5);
			RadialGradientPaint p = new RadialGradientPaint(center, radius, focus, dist, colors, CycleMethod.NO_CYCLE, RadialGradientPaint.ColorSpaceType.SRGB, af);

			g2d.setPaint(p);
			g2d.fillOval((int) (centerX - gaugeRadius), (int) (centerY - gaugeRadius), 2 * gaugeRadius, 2 * gaugeRadius);
			g2d.setColor(Color.YELLOW);
			g2d.drawOval((int) (centerX - gaugeRadius), (int) (centerY - gaugeRadius), 2 * gaugeRadius, 2 * gaugeRadius);

		}

	}

	public static class Glass4 extends GaugeGlass {

		private PartBuffer partDeco4;

		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {
			double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
			double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
			int radius = getGauge().getRadius() - 5;

			if (partDeco4 == null) {
				// System.out.println("null");
				partDeco4 = new PartBuffer(centerX - radius / 2, centerY - radius / 2, 2 * radius, 2 * radius);

				int startAngleDegreee = 190;
				int endAngleDegree = 350;

				g2d.setStroke(new BasicStroke(0.4f));

				g2d.setColor(Color.WHITE);

				Graphics2D g2dPart = partDeco4.getBuffer().createGraphics();
				g2dPart.setRenderingHints(g2d.getRenderingHints());
				g2dPart.translate(-centerX + radius, -centerY + radius);

				Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, endAngleDegree - startAngleDegreee, Arc2D.OPEN);

				// DEBUG ARC
				// g2d.setColor(Color.RED);
				// g2d.draw(arc2d);

				Point2D p1 = new Point2D.Double(centerX + radius * Math.cos(Math.toRadians(endAngleDegree)), centerY - radius * Math.sin(Math.toRadians(endAngleDegree)));

				Point2D p2 = new Point2D.Double(centerX + radius * Math.cos(Math.toRadians(startAngleDegreee)), centerY - radius * Math.sin(Math.toRadians(startAngleDegreee)));

				Point2D ctrl1 = new Point2D.Double(centerX + radius / 2, centerY + radius + 20);

				Point2D ctrl2 = new Point2D.Double(centerX - radius / 2, centerY + radius + 20);

				// Ellipse2D et1 = new
				// Ellipse2D.Double(p1.getX()-1,p1.getY()-1,2,2);

				// g2d.setColor(Color.GREEN);

				// g2d.draw(et1);

				CubicCurve2D cubicCurve = new CubicCurve2D.Double(p1.getX(), p1.getY(), ctrl1.getX(), ctrl1.getY(), ctrl2.getX(), ctrl2.getY(), p2.getX(), p2.getY());

				g2d.setColor(Color.GREEN);

				// g2d.draw(cubicCurve);

				GeneralPath path = new GeneralPath();

				path.append(arc2d, false);

				path.append(cubicCurve, true);

				path.closePath();

				Point2D pG1 = new Point2D.Double(path.getBounds().getX() + path.getBounds().getWidth() / 2, path.getBounds().getY() + path.getBounds().getHeight());
				Point2D pG2 = new Point2D.Double(path.getBounds().getX() + path.getBounds().getWidth() / 2, path.getBounds().getY());

				GradientPaint gPaint = new GradientPaint(pG1, new Color(159, 134, 80, 80), pG2, new Color(255, 255, 255, 0));

				g2dPart.setPaint(gPaint);

				g2dPart.fill(path);

				g2d.drawImage(partDeco4.getBuffer(), (int) centerX - radius, (int) centerY - radius, 2 * radius, 2 * radius, null);
			} else {
				g2d.drawImage(partDeco4.getBuffer(), (int) centerX - radius, (int) centerY - radius, 2 * radius, 2 * radius, null);
			}

		}

	}

	/**
	 * <code>Donut2DGlass</code>
	 * 
	 * @author sebastien janaud
	 *
	 */
	public static class Donut2DGlass extends GaugeGlass {

		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {
			double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();// (int)getGauge().getX();
			double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();// (int)getGauge().getY();
			int radius = getGauge().getRadius() - 5;

			Donut2D donut1 = new Donut2D();
			donut1.setCenterX((int) centerX);
			donut1.setCenterY((int) centerY);
			donut1.setStartAngleDegree(270);
			donut1.setInnerRadius(radius - 20);
			donut1.setOuterRadius(radius);
			donut1.setStartAngleDegree(130);
			donut1.setExplose(5);

			Donut2DSlice s1 = new Donut2DSlice("D1", new Color(255, 255, 255, 120));
			s1.setAlpha(0.4f);
			s1.setValue(10.0);

			Donut2DSlice s2 = new Donut2DSlice("D2", new Color(255, 255, 255, 140));
			s2.setValue(10.0);
			s2.setAlpha(0.2f);

			Donut2DSlice s4 = new Donut2DSlice("D2", new Color(255, 255, 255, 200));
			s4.setValue(10.0);
			s4.setAlpha(0.5f);

			Donut2DSlice s3 = new Donut2DSlice("D3", new Color(255, 255, 255, 0));
			s3.setValue(30.0);
			s3.setAlpha(0.2f);

			Donut2DSlice s5 = new Donut2DSlice("D3", new Color(255, 255, 255, 200));
			s5.setValue(20.0);
			s5.setAlpha(0.6f);

			donut1.addSlice(s1);
			donut1.addSlice(s2);
			donut1.addSlice(s3);
			donut1.addSlice(s4);
			donut1.addSlice(s5);

			Point2D center = new Point2D.Double(centerX, centerY);

			float[] dist = { 0.0f, 0.8f, 1.0f };
			Color[] colors = { new Color(255, 255, 255, 255), new Color(255, 255, 255, 150), new Color(255, 255, 255, 0) };
			RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

			donut1.solveGeometry();
			g2d.setPaint(p);
			List<Donut2DSlice> sections = donut1.getSlices();

			for (int j = 0; j < sections.size(); j++) {
				Donut2DSlice s = sections.get(j);
				g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, s.getAlpha()));
				g2d.fill(s.getSlicePath());
				g2d.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));
			}
		}
	}

	public static class Glass6 extends GaugeGlass {

		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {

			double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
			double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
			int radius = getGauge().getRadius();
			int radius2 = getGauge().getRadius() + 5;

			Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 90, 360, Arc2D.OPEN);

			Arc2D arc2d2 = new Arc2D.Double(centerX - radius2, centerY - radius2, 2 * radius2, 2 * radius2, 90, 360, Arc2D.OPEN);

			g2d.setColor(TangoPalette.PLUM2);

			GeometryPath geometry = new GeometryPath(arc2d);
			GeometryPath geometry2 = new GeometryPath(arc2d2);

			Font f = new Font("Dialog", Font.PLAIN, 10);
			Font f2 = new Font("Dialog", Font.PLAIN, 8);
			String copyright = "- Compass Carbon  *** Sail Instrument ***  all right reserved JENSOFT -";
			String madein = "Stainless steel - Made in France";
			GlyphVector glyphVector = f.createGlyphVector(g2d.getFontRenderContext(), copyright);
			GlyphVector glyphVector2 = f2.createGlyphVector(g2d.getFontRenderContext(), madein);
			AffineTransform af = new AffineTransform();
			AffineTransform af2 = new AffineTransform();

			float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);
			float gvWidth2 = GlyphUtil.getGlyphWidth(glyphVector2);

			float startLength = geometry.lengthOfPath() / 2 - gvWidth / 2;
			float startLength2 = geometry2.lengthOfPath() / 2 - gvWidth2 / 2;

			// int c_p = 49;
			int c_p = copyright.indexOf("JENSOFT");
			int c_p2 = copyright.lastIndexOf("-");

			for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

				Point2D p = glyphVector.getGlyphPosition(j);
				float px = (float) p.getX();
				float py = (float) p.getY();
				Point2D pointGlyph;

				pointGlyph = geometry.pointAtLength(startLength + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));

				if (pointGlyph == null) {
					continue;
				}
				Shape glyph = glyphVector.getGlyphOutline(j);

				float angle = geometry.angleAtLength(startLength + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
				af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
				af.rotate(angle);
				af.translate(-px, -py + glyphVector.getVisualBounds().getHeight() / 2 - 10);

				Shape ts = af.createTransformedShape(glyph);

				g2d.setColor(Color.WHITE);

				if (j == c_p2) {
					g2d.setColor(Color.RED);
				}
				if (j == c_p2 + 1) {
					g2d.setColor(Color.RED);
				}

				if (j == c_p - 6 || j == c_p - 7 || j == c_p - 8 || j == c_p - 9) {
					g2d.setColor(Color.WHITE);
				}
				if (j == c_p - 1 || j == c_p - 2 || j == c_p - 3 || j == c_p - 4 || j == c_p - 5) {
					g2d.setColor(PetalPalette.PETAL4_HC);
				}
				if (j == c_p) {
					g2d.setColor(PetalPalette.PETAL5_HC);
				}
				if (j == c_p + 1) {
					g2d.setColor(PetalPalette.PETAL6_HC);
				}
				if (j == c_p + 2) {
					g2d.setColor(PetalPalette.PETAL7_HC);
				}
				if (j == c_p + 3) {
					g2d.setColor(PetalPalette.PETAL8_HC);
				}
				if (j == c_p + 4) {
					g2d.setColor(PetalPalette.PETAL1_HC);
				}
				if (j == c_p + 5) {
					g2d.setColor(PetalPalette.PETAL2_HC);
				}
				if (j == c_p + 6) {
					g2d.setColor(PetalPalette.PETAL3_HC);
				}
				if (j == c_p + 7 || j == c_p + 8 || j == c_p + 9 || j == c_p + 10) {
					g2d.setColor(Color.WHITE);
				}

				g2d.fill(ts);

			}

			for (int j = 0; j < glyphVector2.getNumGlyphs(); j++) {

				Point2D p = glyphVector2.getGlyphPosition(j);
				float px = (float) p.getX();
				float py = (float) p.getY();
				Point2D pointGlyph;

				pointGlyph = geometry2.pointAtLength(startLength2 + GlyphUtil.getGlyphWidthAtToken(glyphVector2, j));

				if (pointGlyph == null) {
					continue;
				}
				Shape glyph = glyphVector2.getGlyphOutline(j);

				float angle = geometry2.angleAtLength(startLength2 + GlyphUtil.getGlyphWidthAtToken(glyphVector2, j));
				af2.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
				af2.rotate(angle);
				af2.translate(-px, -py + glyphVector2.getVisualBounds().getHeight() / 2 - 10);

				Shape ts = af2.createTransformedShape(glyph);

				g2d.setColor(PetalPalette.PETAL8_HC);

				g2d.fill(ts);

			}

		}

	}
	
	public static abstract class PieGaugeGlass extends GaugeGlass{
		
		public abstract AbstractPieEffect getEffectInstance();
		
		
		@Override
		public final void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {
			
			
			double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
			double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
			int radius = getGauge().getRadius();
			
			Pie pie = new Pie();
			pie.setPieNature(PieNature.Device);
			pie.setCenterX(centerX);
			pie.setCenterY(centerY);
			pie.setRadius(radius);
			
			pie.addSlice(new PieSlice("slice", Color.WHITE));
			
			PiePlugin piePlugin = new PiePlugin();
			piePlugin.setWindow2D(getGauge().getWindow2D());
			piePlugin.addPie(pie);
			
			AbstractPieEffect fx = getEffectInstance();
			
			pie.setPieEffect(fx);
			pie.build();
			
			fx.paintPie(g2d, pie);//paintPieEffect(g2d, pie);
		}
	}
	
	/**
	 * <code>GlassCubicEffect</code>
	 *
	 */
	public static class GlassLinearEffect extends PieGaugeGlass {

		@Override
		public AbstractPieEffect getEffectInstance() {
			PieLinearEffect fx = new PieLinearEffect();
			return fx;
		}
		
	}
	
	/**
	 * <code>GlassRadialEffect</code>
	 *
	 */
	public static class GlassRadialEffect extends PieGaugeGlass {

		@Override
		public AbstractPieEffect getEffectInstance() {
			PieRadialEffect fx = new PieRadialEffect();
			return fx;
		}
		
	}
	
	
	/**
	 * <code>GlassCubicEffect</code>
	 *
	 */
	public static class GlassCubicEffect extends PieGaugeGlass {

		@Override
		public AbstractPieEffect getEffectInstance() {
			PieCubicEffect fx = new PieCubicEffect();
			fx.setCubicKey(CubicEffectFrame.Round4.getKeyFrame());
			return fx;
		}
		
//		@Override
//		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {
//			
//			
//			double centerX = getGauge().getWindow2D().userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
//			double centerY = getGauge().getWindow2D().userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
//			int radius = getGauge().getRadius();
//			
//			Pie pie = new Pie();
//			pie.setPieNature(PieNature.Device);
//			pie.setCenterX(centerX);
//			pie.setCenterY(centerY);
//			pie.setRadius(radius);
//			
//			pie.addSlice(new PieSlice("slice", Color.WHITE));
//			
//			PiePlugin piePlugin = new PiePlugin();
//			piePlugin.setWindow2D(getGauge().getWindow2D());
//			piePlugin.addPie(pie);
//			
//			PieCubicEffect fx = new PieCubicEffect();
//			fx.setCubicKey(CubicEffectFrame.Round4.getKeyFrame());
//			pie.setPieEffect(fx);
//			pie.build();
//			
//			fx.paintPieEffect(g2d, pie);
//		}
		
	}

}
