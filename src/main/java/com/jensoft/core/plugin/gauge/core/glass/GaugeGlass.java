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
import com.jensoft.core.drawable.text.TextPath;
import com.jensoft.core.drawable.text.TextPath.PathSide;
import com.jensoft.core.drawable.text.TextPath.TextPosition;
import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.glyphmetrics.GlyphUtil;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.PetalPalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.donut2d.Donut2D;
import com.jensoft.core.plugin.donut2d.Donut2DSlice;
import com.jensoft.core.plugin.gauge.core.GaugePartBuffer;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.Pie.PieNature;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.painter.effect.AbstractPieEffect;
import com.jensoft.core.plugin.pie.painter.effect.CubicEffectFrame;
import com.jensoft.core.plugin.pie.painter.effect.CubicEffectKey;
import com.jensoft.core.plugin.pie.painter.effect.PieCubicEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieLinearEffect;
import com.jensoft.core.plugin.pie.painter.effect.PieRadialEffect;

/**
 * <code>GaugeGlass</code>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public abstract class GaugeGlass extends GaugeGlassPainter {

	/**
	 * create abstract gauge glass
	 */
	public GaugeGlass() {
	}

	/**
	 * <code>Glass1</code>
	 * 
	 * @since 1.0
	 * @author sebastien
	 * 
	 */
	public static class Glass1 extends GaugeGlass {

		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {
			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();// (int)radialGauge.getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();// (int)radialGauge.getY();
			int radius = radialGauge.getRadius() - 5;

			if (getPartBuffer() == null) {

				GaugePartBuffer buffer = new GaugePartBuffer(radialGauge);
				setPartBuffer(buffer);
				// partDeco1 = new PartBuffer(centerX - radius / 2, centerY -
				// radius / 2, 2 * radius, 2 * radius);

				Graphics2D g2dPart = buffer.getGraphics();
				g2dPart.setRenderingHints(g2d.getRenderingHints());
				// g2dPart.translate(-centerX + radius, -centerY + radius);

				int startAngleDegreee = 30;
				int endAngleDegree = 175;

				g2dPart.setStroke(new BasicStroke(0.4f));

				g2dPart.setColor(Color.WHITE);

				Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegreee, endAngleDegree - startAngleDegreee, Arc2D.OPEN);

				// DEBUG ARC
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

			g2d.drawImage(getPartBuffer().getBuffer(), (int) getPartBuffer().getX(), (int) getPartBuffer().getY(), (int) getPartBuffer().getWidth(), (int) getPartBuffer().getHeight(), null);

		}

	}

	public static class Glass2 extends GaugeGlass {

		private PartBuffer partDeco2;

		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {
			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();// (int)radialGauge.getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();// (int)radialGauge.getY();
			int radius = radialGauge.getRadius() - 5;

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
			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();// (int)radialGauge.getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();// (int)radialGauge.getY();
			int gaugeRadius = radialGauge.getRadius();

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
			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();// (int)radialGauge.getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();// (int)radialGauge.getY();
			int radius = radialGauge.getRadius() - 5;

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
	 * <code>Donut2DGlass</code> define a donut 2D style glass
	 * 
	 * @since 1.0
	 * @author sebastien janaud
	 * 
	 */
	public static class Donut2DGlass extends GaugeGlass {

		/**
		 * create donut 2D style glass
		 */
		public Donut2DGlass() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.gauge.core.glass.GaugeGlassPainter#paintGlass
		 * (java.awt.Graphics2D, com.jensoft.core.plugin.gauge.core.RadialGauge)
		 */
		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {
			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();// (int)radialGauge.getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();// (int)radialGauge.getY();
			int radius = radialGauge.getRadius() - 5;

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

	/**
	 * create text path on glass layer. text path component draws text on a path
	 * defines by an arc. the arc is defined by start angle degree and extends
	 * degree on gauge frame.
	 * 
	 * <p>
	 * You should define the arc definition (0,360 is default preset ) and
	 * define text position at left, middle or right. You can set offset to
	 * adjust position and divergence from the arc. Do not set large divergence
	 * to keep glyph good accuracy.
	 * </p>
	 * 
	 * <p>
	 * You can choose shader color for label and text font
	 * </p>
	 * 
	 * <p>
	 * To reverse text on demand, use auto reverse or lock reverse option
	 * </p>
	 * 
	 * @author sebastien janaud
	 * 
	 */
	public static class GlassTextPath extends GaugeGlass {

		/** text path */
		private TextPath textPath;

		/** arc start angle degree */
		private int startAngleDegree = 0;

		/** arc extends angle degree */
		private int extendsDegree = 360;

		/**
		 * create text path label on glass layer
		 */
		public GlassTextPath() {
			textPath = new TextPath();
			// do not disturb developer. angle, offset, path side, text
			// position, etc ... can result in
			// headache!
			textPath.setDivergence(0);
			textPath.setOffsetLeft(0);
			textPath.setOffsetRight(0);
		}

		/**
		 * create text path label on glass layer with given text
		 * 
		 * @param text
		 *            the label text
		 */
		public GlassTextPath(String text) {
			this();
			setText(text);
		}

		/**
		 * defines the arc definition for the text path arc shape
		 * 
		 * @param startAngleDegree
		 * @param extendsDegree
		 */
		public void setArcDef(int startAngleDegree, int extendsDegree) {
			this.startAngleDegree = startAngleDegree;
			this.extendsDegree = extendsDegree;
		}

		/**
		 * get text font
		 * 
		 * @return text font
		 */
		public Font getTextFont() {
			return textPath.getLabelFont();
		}

		/**
		 * set text font
		 * 
		 * @param textFont
		 */
		public void setTextFont(Font textFont) {
			this.textPath.setLabelFont(textFont);
		}

		/**
		 * get label text
		 * 
		 * @return label text
		 */
		public String getText() {
			return textPath.getLabel();
		}

		/**
		 * set label text
		 * 
		 * @param text
		 */
		public void setText(String text) {
			this.textPath.setLabel(text);
		}

		/**
		 * get lock reverse flag option
		 * 
		 * @return
		 */
		public boolean isLockReverse() {
			return textPath.isLockReverse();
		}

		/**
		 * for text to be reverse according to the original position
		 * 
		 * @param lockReverse
		 */
		public void setLockReverse(boolean lockReverse) {
			this.textPath.setLockReverse(lockReverse);
		}

		/**
		 * get auto reverse flag option
		 * 
		 * @return auto reverse
		 */
		public boolean isAutoReverse() {
			return textPath.isAutoReverse();
		}

		/**
		 * reverse text for doing it readable.
		 * 
		 * @param autoReverse
		 */
		public void setAutoReverse(boolean autoReverse) {
			this.textPath.setAutoReverse(autoReverse);
		}

		/**
		 * set text position
		 * 
		 * @param textPosition
		 */
		public void setTextPosition(TextPosition textPosition) {
			this.textPath.setTextPosition(textPosition);
		}

		/**
		 * get text position
		 * 
		 * @return text position
		 */
		public TextPosition getTextPosition() {
			return textPath.getTextPosition();
		}

		/**
		 * get text divergence from path
		 * 
		 * @return text divergence
		 */
		public int getDivergence() {
			return textPath.getDivergence();
		}

		/**
		 * set text divergence from path
		 * 
		 * @param divergence
		 */
		public void setDivergence(int divergence) {
			this.textPath.setDivergence(divergence);
		}

		/**
		 * get left offset
		 * 
		 * @return left offset
		 */
		public float getOffsetLeft() {
			return textPath.getOffsetLeft();
		}

		/**
		 * set offset left when text in on the left. text right or middle
		 * position ignore this property.
		 * 
		 * @param offsetLeft
		 */
		public void setOffsetLeft(float offsetLeft) {
			this.textPath.setOffsetLeft(offsetLeft);
		}

		/**
		 * get offset right
		 * 
		 * @return offset right
		 */
		public float getOffsetRight() {
			return textPath.getOffsetRight();
		}

		/**
		 * set offset right when text in on the right. text left or middle
		 * position ignore this property.
		 * 
		 * @param offsetRight
		 */
		public void setOffsetRight(float offsetRight) {
			this.textPath.setOffsetRight(offsetRight);
		}

		/**
		 * simple uniform text color
		 * 
		 * @return text uniform color
		 */
		public Color getTextColor() {
			return textPath.getTextColor();
		}

		/**
		 * set simple uniform text color
		 * 
		 * @param textColor
		 */
		public void setTextColor(Color textColor) {
			this.textPath.setTextColor(textColor);
		}

		/**
		 * get text path side
		 * 
		 * @return pathSide
		 */
		public PathSide getTextPathSide() {
			return textPath.getPathSide();
		}

		/**
		 * set text path side
		 * 
		 * @param pathSide
		 */
		public void setTextPathSide(PathSide pathSide) {
			this.textPath.setPathSide(pathSide);
		}

		/**
		 * set text shader
		 * 
		 * @param fractions
		 * @param colors
		 */
		public void setShader(float[] fractions, Color[] colors) {
			this.textPath.setShader(fractions, colors);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.gauge.core.GlassGaugePainter#paintGlass(java
		 * .awt.Graphics2D, com.jensoft.core.plugin.gauge.RadialGauge)
		 */
		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {

			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();
			int radius = radialGauge.getRadius();

			Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngleDegree, extendsDegree, Arc2D.OPEN);
			textPath.setPath(arc2d);
			textPath.draw(g2d);
		}

	}

	/**
	 * 
	 * <code>JenSoftAPILabel</code>
	 * 
	 * @since 1.0
	 * @author sebastien janaud
	 * 
	 */
	public static class JenSoftAPILabel extends GaugeGlass {

		private String label = "***JenSoft Marine ***";

		/**
		 * create default label
		 */
		public JenSoftAPILabel() {
		}

		/**
		 * create glass with given label
		 * 
		 * @param label
		 */
		public JenSoftAPILabel(String label) {
			this.label = label;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.gauge.core.glass.GaugeGlassPainter#paintGlass
		 * (java.awt.Graphics2D, com.jensoft.core.plugin.gauge.core.RadialGauge)
		 */
		@Override
		public void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {

			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();
			int radius = radialGauge.getRadius();
			int radius2 = radialGauge.getRadius() + 5;

			Arc2D arc2d = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 90, 360, Arc2D.OPEN);

			Arc2D arc2d2 = new Arc2D.Double(centerX - radius2, centerY - radius2, 2 * radius2, 2 * radius2, 90, 360, Arc2D.OPEN);

			g2d.setColor(TangoPalette.WHITE);

			GeometryPath geometry = new GeometryPath(arc2d);
			GeometryPath geometry2 = new GeometryPath(arc2d2);

			// Font f = new Font("Dialog", Font.PLAIN, 10);
			// Font f2 = new Font("Dialog", Font.PLAIN, 8);
			Font f = InputFonts.getSreda(10);

			String copyright = label + " - JENSOFT API";
			GlyphVector glyphVector = f.createGlyphVector(g2d.getFontRenderContext(), copyright);
			AffineTransform af = new AffineTransform();

			float gvWidth = GlyphUtil.getGlyphWidth(glyphVector);

			float startLength = geometry.lengthOfPath() / 2 - gvWidth / 2;

			int c_p = copyright.indexOf("JENSOFT");

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

		}

	}

	public static abstract class PieGaugeGlass extends GaugeGlass {

		public abstract AbstractPieEffect getEffectInstance();

		@Override
		public final void paintGlass(Graphics2D g2d, RadialGauge radialGauge) {

			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();
			int radius = radialGauge.getRadius();

			Pie pie = new Pie();
			pie.setPieNature(PieNature.Device);
			pie.setCenterX(centerX);
			pie.setCenterY(centerY);
			pie.setRadius(radius);

			pie.addSlice(new PieSlice("slice", Color.WHITE));

			PiePlugin piePlugin = new PiePlugin();
			piePlugin.setWindow2D(radialGauge.getWindow2D());
			piePlugin.addPie(pie);

			AbstractPieEffect fx = getEffectInstance();

			pie.setPieEffect(fx);
			pie.build();

			fx.paintPie(g2d, pie);// paintPieEffect(g2d, pie);
		}
	}

	/**
	 * <code>GlassCubicEffect</code>
	 * 
	 */
	public static class GlassLinearEffect extends PieGaugeGlass {

		private float[] fractions = { 0.0f, 0.49f, 0.51f, 1.0f };

		private Color[] colors = { new Color(60, 60, 60, 150), new Color(255, 255, 255, 0), new Color(255, 255, 255, 0), new Color(255, 255, 255, 100) };

		@Override
		public AbstractPieEffect getEffectInstance() {
			PieLinearEffect fx = new PieLinearEffect();
			fx.setShader(fractions, colors);
			fx.setOffsetRadius(2);
			fx.setIncidenceAngleDegree(60);
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
	 * <code>GlassCubicEffect</code> provides a cubic glass effect
	 * 
	 */
	public static class GlassCubicEffect extends PieGaugeGlass {

		/** frame preset key */
		private CubicEffectFrame frame;

		/** key */
		private CubicEffectKey key;

		/** pie cubic effect */
		private PieCubicEffect fx;

		/**
		 * create cubic glass effect
		 */
		public GlassCubicEffect() {
			fx = new PieCubicEffect();
		}

		/**
		 * create effect with the given frame
		 * 
		 * @param frame
		 */
		public GlassCubicEffect(CubicEffectFrame frame) {
			this();
			this.frame = frame;
		}

		/**
		 * create effect with the given key
		 * 
		 * @param key
		 */
		public GlassCubicEffect(CubicEffectKey key) {
			this();
			this.key = key;
		}

		/**
		 * get cubic frame
		 * 
		 * @return cubic frame
		 */
		public CubicEffectFrame getFrame() {
			return frame;
		}

		/**
		 * set key of the given frame
		 * 
		 * @param frame
		 */
		public void setFrame(CubicEffectFrame frame) {
			this.frame = frame;
		}

		/**
		 * get cubic key
		 * 
		 * @return cubic key
		 */
		public CubicEffectKey getKey() {
			return key;
		}

		/**
		 * set cubic key
		 * 
		 * @param key
		 */
		public void setKey(CubicEffectKey key) {
			this.key = key;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.gauge.core.glass.GaugeGlass.PieGaugeGlass
		 * #getEffectInstance()
		 */
		@Override
		public AbstractPieEffect getEffectInstance() {
			if (frame != null) {
				setKey(frame.getKeyFrame());
			}
			if (key != null) {
				fx.setCubicKey(key);
			}
			return fx;
		}

	}

}
