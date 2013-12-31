/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import com.jensoft.core.device.PartBuffer;
import com.jensoft.core.drawable.screw.Posidrive;
import com.jensoft.core.drawable.screw.Split;
import com.jensoft.core.drawable.screw.Torx;
import com.jensoft.core.palette.NanoChromatique;

/**
 * <code>GaugeEnvelope</code> defines an envelop that extends around the gauge.
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public abstract class GaugeEnvelope extends GaugePart{

	/**
	 * <code>Classic</code> is a classic envelope
	 * 
	 * @since 1.0
	 * 
	 */
	public static class Classic extends GaugeEnvelope {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.gauge.core.env.GaugeEnvelope#paintEnvelop
		 * (java.awt.Graphics2D, com.jensoft.core.plugin.gauge.core.RadialGauge)
		 */
		@Override
		public void paintEnvelope(Graphics2D g2d, RadialGauge radialGauge) {
			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();
			int radius = radialGauge.getRadius();
			int deltaInternal = 4;
			int deltaExternal = 40;
			int radiusInternal = radius + deltaInternal;
			int radiusExternal = radius + deltaExternal;

			radius = radiusExternal;
			if (getPartBuffer() == null) {

				// System.out.println("null");
				// envelopPart = new PartBuffer(centerX - radius / 2, centerY -
				// radius / 2, 2 * radius, 2 * radius);
				GaugePartBuffer envelopPart = new GaugePartBuffer(radialGauge);
				setPartBuffer(new GaugePartBuffer(radialGauge));
				Graphics2D g2dPart = envelopPart.getGraphics();
				g2dPart.setRenderingHints(g2d.getRenderingHints());
				// base

				Ellipse2D eInternal = new Ellipse2D.Double(centerX - radiusInternal, centerY - radiusInternal, 2 * radiusInternal, 2 * radiusInternal);
				Ellipse2D eExternal = new Ellipse2D.Double(centerX - radiusExternal, centerY - radiusExternal, 2 * radiusExternal, 2 * radiusExternal);

				Point2D start = new Point2D.Double(centerX, centerY - radius);
				Point2D end = new Point2D.Double(centerX, centerY + radius);
				float[] dist = { 0.0f, 0.5f, 1.0f };
				Color[] colors = { Color.DARK_GRAY, Color.LIGHT_GRAY, Color.BLACK };
				LinearGradientPaint p = new LinearGradientPaint(start, end, dist, colors);

				g2dPart.setPaint(p);
				g2dPart.fill(eExternal);

				Point2D start2 = new Point2D.Double(centerX, centerY - radius);
				Point2D end2 = new Point2D.Double(centerX, centerY + radius);
				float[] dist2 = { 0.0f, 1.0f };
				Color[] colors2 = { Color.BLACK, Color.WHITE };
				LinearGradientPaint p2 = new LinearGradientPaint(start2, end2, dist2, colors2);

				g2dPart.setPaint(p2);
				g2dPart.fill(eInternal);

				// cerclage
				int deltaInternal2 = 0;
				int deltaExternal2 = 4;
				int radiusInternal2 = radiusInternal + deltaInternal2;
				int radiusExternal2 = radiusExternal - deltaExternal2;
				Ellipse2D eInternal2 = new Ellipse2D.Double(centerX - radiusInternal2, centerY - radiusInternal2, 2 * radiusInternal2, 2 * radiusInternal2);
				Ellipse2D eExternal2 = new Ellipse2D.Double(centerX - radiusExternal2, centerY - radiusExternal2, 2 * radiusExternal2, 2 * radiusExternal2);

				Area aI = new Area(eInternal2);
				Area aE = new Area(eExternal2);

				aE.subtract(aI);

				g2dPart.setColor(Color.RED);
				// g2d.draw(aE);

				double p1IX = centerX + radiusInternal2 * Math.cos(Math.toRadians(-10));
				double p1IY = centerY - radiusInternal2 * Math.sin(Math.toRadians(-10));

				double p1EX = centerX + radiusExternal2 * Math.cos(Math.toRadians(-10));
				double p1EY = centerY - radiusExternal2 * Math.sin(Math.toRadians(-10));
				// Line2D eTestP1 = new
				// Line2D.Double(p1IX-2,p1IY-2,p1EX-2,p1EY-2);
				// g2d.draw(eTestP1);

				double p2IX = centerX + radiusInternal2 * Math.cos(Math.toRadians(190));
				double p2IY = centerY - radiusInternal2 * Math.sin(Math.toRadians(190));

				double p2EX = centerX + radiusExternal2 * Math.cos(Math.toRadians(190));
				double p2EY = centerY - radiusExternal2 * Math.sin(Math.toRadians(190));
				// Line2D eTestP2 = new
				// Line2D.Double(p2IX-2,p2IY-2,p2EX-2,p2EY-2);
				// g2d.draw(eTestP2);

				Arc2D a1 = new Arc2D.Double(centerX - radiusInternal2, centerY - radiusInternal2, 2 * radiusInternal2, 2 * radiusInternal2, -10, 200, Arc2D.OPEN);
				// g2d.draw(a1);

				Arc2D a2 = new Arc2D.Double(centerX - radiusExternal2, centerY - radiusExternal2, 2 * radiusExternal2, 2 * radiusExternal2, 190, -200, Arc2D.OPEN);
				// g2d.draw(a2);

				GeneralPath path = new GeneralPath();
				path.append(a1, false);
				path.lineTo(p2IX, p2IY);
				path.append(a2, true);
				path.closePath();

				Point2D start3 = new Point2D.Double(centerX, centerY - radiusExternal2);
				Point2D end3 = new Point2D.Double(centerX, centerY + radiusExternal2);
				float[] dist3 = { 0.0f, 1.0f };
				Color[] colors3 = { Color.WHITE, Color.BLACK.brighter() };
				LinearGradientPaint p3 = new LinearGradientPaint(start3, end3, dist3, colors3);
				g2dPart.setPaint(p3);
				g2dPart.fill(aE);

				// g2d.setColor(Color.LIGHT_GRAY);
				Point2D start4 = new Point2D.Double(centerX, centerY - radiusExternal2);
				Point2D end4 = new Point2D.Double(centerX, centerY);
				float[] dist4 = { 0.0f, 1.0f };
				Color[] colors4 = { Color.LIGHT_GRAY, Color.DARK_GRAY };
				LinearGradientPaint p4 = new LinearGradientPaint(start4, end4, dist4, colors4);
				g2dPart.setPaint(p4);
				g2dPart.fill(path);

				int deltaRadius = radiusExternal2 - radiusInternal2;
				double starPositionRadius = radiusInternal2 + deltaRadius / 2;

				g2dPart.setPaint(null);
				double pxstar1 = centerX + starPositionRadius * Math.cos(Math.toRadians(90));
				double pystar1 = centerY - starPositionRadius * Math.sin(Math.toRadians(90));
				Torx t1 = new Torx(pxstar1, pystar1, 12);
				t1.draw(g2dPart);

				double pxstar2 = centerX + starPositionRadius * Math.cos(Math.toRadians(225));
				double pystar2 = centerY - starPositionRadius * Math.sin(Math.toRadians(225));
				Torx t2 = new Torx(pxstar2, pystar2, 12);
				t2.draw(g2dPart);

				double pxstar3 = centerX + starPositionRadius * Math.cos(Math.toRadians(-45));
				double pystar3 = centerY - starPositionRadius * Math.sin(Math.toRadians(-45));
				Torx t3 = new Torx(pxstar3, pystar3, 12);
				t3.draw(g2dPart);

				double pxstar4 = centerX + starPositionRadius * Math.cos(Math.toRadians(45));
				double pystar4 = centerY - starPositionRadius * Math.sin(Math.toRadians(45));
				Torx c4 = new Torx(pxstar4, pystar4, 12);
				c4.draw(g2dPart);

				double pxstar5 = centerX + starPositionRadius * Math.cos(Math.toRadians(135));
				double pystar5 = centerY - starPositionRadius * Math.sin(Math.toRadians(135));
				Torx f5 = new Torx(pxstar5, pystar5, 12);
				f5.draw(g2dPart);

				double pxstar6 = centerX + starPositionRadius * Math.cos(Math.toRadians(270));
				double pystar6 = centerY - starPositionRadius * Math.sin(Math.toRadians(270));
				Torx b6 = new Torx(pxstar6, pystar6, 12);
				b6.draw(g2dPart);

				double pxstar7 = centerX + starPositionRadius * Math.cos(Math.toRadians(0));
				double pystar7 = centerY - starPositionRadius * Math.sin(Math.toRadians(0));
				Posidrive b7 = new Posidrive(pxstar7, pystar7, 12);
				b7.draw(g2dPart);

				double pxstar8 = centerX + starPositionRadius * Math.cos(Math.toRadians(180));
				double pystar8 = centerY - starPositionRadius * Math.sin(Math.toRadians(180));
				Torx b8 = new Torx(pxstar8, pystar8, 12);
				b8.draw(g2dPart);

			}
			g2d.drawImage(getPartBuffer().getBuffer(), (int) getPartBuffer().getX(), (int) getPartBuffer().getY(), (int) getPartBuffer().getWidth(), (int) getPartBuffer().getHeight(), null);
		}

	}

	/**
	 * <code>Cisero</code> is define by an extends ratio. this ratio defines the
	 * radius divide factor to get the thickness of the Cisero envelop.
	 * 
	 * @since 1.0
	 * 
	 */
	public static class Cisero extends GaugeEnvelope {

		/** extends ratio */
		private double extendsRatio = 3;

		/** alpha angle degree to crew place holder */
		private double alpha = 10;

		/**
		 * create default Cisero envelop
		 */
		public Cisero() {
		}

		/**
		 * create Cisero with given parameters
		 * 
		 * @param extendsRatio
		 */
		public Cisero(double extendsRatio) {
			this.extendsRatio = extendsRatio;
		}

		/**
		 * @return the extendsRatio
		 */
		public double getExtendsRatio() {
			return extendsRatio;
		}

		/**
		 * @param extendsRatio
		 *            the extendsRatio to set
		 */
		public void setExtendsRatio(double extendsRatio) {
			this.extendsRatio = extendsRatio;
		}

		/**
		 * @return the alpha
		 */
		public double getAlpha() {
			return alpha;
		}

		/**
		 * @param alpha
		 *            the alpha to set
		 */
		public void setAlpha(double alpha) {
			this.alpha = alpha;
		}

		/**
		 * create cisero fragment
		 * 
		 * @param theta
		 * @param alpha
		 * @param internalRadius
		 * @param externalRadius
		 * @return cisero fragment
		 */
		private Shape createFragment(double theta, double alpha, double internalRadius, double externalRadius, RadialGauge radialGauge) {
			GeneralPath path = new GeneralPath();

			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();
			int radius = radialGauge.getRadius();

			Point2D p1, p2, p3, p4, pc1, pc2, pc3, pc4;

			// points
			p1 = new Point2D.Double(centerX + internalRadius * Math.cos(Math.toRadians(theta - alpha - alpha / 2)), centerY - internalRadius * Math.sin(Math.toRadians(theta - alpha - alpha / 2)));

			p2 = new Point2D.Double(centerX + externalRadius * Math.cos(Math.toRadians(theta - alpha / 2)), centerY - externalRadius * Math.sin(Math.toRadians(theta - alpha / 2)));

			p3 = new Point2D.Double(centerX + externalRadius * Math.cos(Math.toRadians(theta + alpha / 2)), centerY - externalRadius * Math.sin(Math.toRadians(theta + alpha / 2)));

			p4 = new Point2D.Double(centerX + internalRadius * Math.cos(Math.toRadians(theta + alpha + alpha / 2)), centerY - internalRadius * Math.sin(Math.toRadians(theta + alpha + alpha / 2)));

			// control
			pc1 = new Point2D.Double(centerX + internalRadius * Math.cos(Math.toRadians(theta - alpha)), centerY - internalRadius * Math.sin(Math.toRadians(theta - alpha)));

			pc2 = new Point2D.Double(centerX + externalRadius * Math.cos(Math.toRadians(theta - alpha)), centerY - externalRadius * Math.sin(Math.toRadians(theta - alpha)));

			pc3 = new Point2D.Double(centerX + externalRadius * Math.cos(Math.toRadians(theta + alpha)), centerY - externalRadius * Math.sin(Math.toRadians(theta + alpha)));

			pc4 = new Point2D.Double(centerX + internalRadius * Math.cos(Math.toRadians(theta + alpha)), centerY - internalRadius * Math.sin(Math.toRadians(theta + alpha)));

			path.moveTo(p1.getX(), p1.getY());
			path.curveTo(pc1.getX(), pc1.getY(), pc2.getX(), pc2.getY(), p2.getX(), p2.getY());

			Arc2D arc1 = new Arc2D.Double(centerX - externalRadius, centerY - externalRadius, 2 * externalRadius, 2 * externalRadius, theta - alpha / 2, alpha, Arc2D.OPEN);
			path.append(arc1, true);

			path.curveTo(pc3.getX(), pc3.getY(), pc4.getX(), pc4.getY(), p4.getX(), p4.getY());
			return path;
		}

		/**
		 * create cisero arc fragment
		 * 
		 * @param theta1
		 * @param theta2
		 * @param alpha
		 * @param internalRadius
		 * @param externalRadius
		 * @return cisero arc
		 */
		private Shape createArcFragment(double theta1, double theta2, double alpha, double internalRadius, double externalRadius, RadialGauge radialGauge) {
			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();
			Arc2D a = new Arc2D.Double(centerX - internalRadius, centerY - internalRadius, 2 * internalRadius, 2 * internalRadius, theta1 + alpha + alpha / 2, theta2 - theta1 - 3 * alpha, Arc2D.OPEN);
			return a;
		}

		/* (non-Javadoc)
		 * @see com.jensoft.core.plugin.gauge.core.env.GaugeEnvelope#paintEnvelop(java.awt.Graphics2D, com.jensoft.core.plugin.gauge.core.RadialGauge)
		 */
		@Override
		public void paintEnvelope(Graphics2D g2d, RadialGauge radialGauge) {
			// System.out.println("paint cisero env.");
			double centerX = radialGauge.getWindow2D().userToPixel(new Point2D.Double(radialGauge.getX(), 0)).getX();
			double centerY = radialGauge.getWindow2D().userToPixel(new Point2D.Double(0, radialGauge.getY())).getY();

			int deltaExternal = (int) (radialGauge.getRadius() / extendsRatio);
			GaugePartBuffer part = getPartBuffer();
			if (part == null) {

				int radiusExternal = radialGauge.getRadius() + deltaExternal;

				part = new GaugePartBuffer(radialGauge);
				setPartBuffer(part);
				Graphics2D g2dPart = part.getGraphics();
				g2dPart.setRenderingHints(g2d.getRenderingHints());

				// base
				// Ellipse2D eInternal = new Ellipse2D.Double(centerX -
				// radialGauge.getRadius(), centerY - radialGauge.getRadius(), 2 *
				// radialGauge.getRadius(), 2 * radialGauge.getRadius());
				Ellipse2D eExternal = new Ellipse2D.Double(centerX - radiusExternal, centerY - radiusExternal, 2 * radiusExternal, 2 * radiusExternal);

				Point2D start = new Point2D.Double(centerX, centerY - radiusExternal);
				Point2D end = new Point2D.Double(centerX, centerY + radiusExternal);
				float[] dist = { 0.0f, 0.5f, 1.0f };
				Color[] colors = { Color.DARK_GRAY, Color.LIGHT_GRAY, Color.BLACK };
				LinearGradientPaint p = new LinearGradientPaint(start, end, dist, colors);

				g2dPart.setPaint(p);
				g2dPart.fill(eExternal);

				// Point2D start2 = new Point2D.Double(centerX, centerY -
				// radiusExternal);
				// Point2D end2 = new Point2D.Double(centerX, centerY +
				// radiusExternal);
				// float[] dist2 = { 0.0f, 1.0f };
				// Color[] colors2 = { Color.BLACK, Color.WHITE };
				// LinearGradientPaint p2 = new LinearGradientPaint(start2, end2,
				// dist2, colors2);
				//
				// g2dPart.setPaint(p2);
				// g2dPart.fill(eInternal);

				int epsilonPixel = 2;

				int baseRadius = radialGauge.getRadius() + deltaExternal / 2;
				// int radiusExternal2 = radialGauge.getRadius() + deltaExternal2;
				int extendsRadius = radialGauge.getRadius() + deltaExternal - epsilonPixel;

				Shape s0 = createFragment(30, alpha, baseRadius, extendsRadius, radialGauge);
				Shape s1 = createFragment(90, alpha, baseRadius, extendsRadius, radialGauge);
				Shape s2 = createFragment(90 + 60, alpha, baseRadius, extendsRadius, radialGauge);
				Shape s3 = createFragment(180 + 30, alpha, baseRadius, extendsRadius, radialGauge);
				Shape s4 = createFragment(270, alpha, baseRadius, extendsRadius, radialGauge);
				Shape s5 = createFragment(270 + 60, alpha, baseRadius, extendsRadius, radialGauge);

				Shape a0 = createArcFragment(-30, 30, alpha, baseRadius, extendsRadius, radialGauge);
				Shape a1 = createArcFragment(30, 90, alpha, baseRadius, extendsRadius, radialGauge);
				Shape a2 = createArcFragment(90, 90 + 60, alpha, baseRadius, extendsRadius, radialGauge);
				Shape a3 = createArcFragment(90 + 60, 180 + 30, alpha, baseRadius, extendsRadius, radialGauge);
				Shape a4 = createArcFragment(180 + 30, 270, alpha, baseRadius, extendsRadius, radialGauge);
				Shape a5 = createArcFragment(270, 270 + 60, alpha, baseRadius, extendsRadius, radialGauge);

				GeneralPath path1 = new GeneralPath();
				path1.append(s0, false);
				path1.append(a1, true);
				path1.append(s1, true);
				path1.append(a2, true);
				path1.append(s2, true);
				path1.append(a3, true);
				path1.append(s3, true);
				path1.append(a4, true);
				path1.append(s4, true);
				path1.append(a5, true);
				path1.append(s5, true);
				path1.append(a0, true);

				g2dPart.setColor(Color.WHITE);
				path1.closePath();

				Area area1 = new Area(path1);

				int radiusBase = radialGauge.getRadius() + epsilonPixel;
				Area area2 = new Area(new Ellipse2D.Double(centerX - radiusBase, centerY - radiusBase, 2 * radiusBase, 2 * radiusBase));
				// g2dPart.draw(area1);
				// g2dPart.draw(area2);

				area1.subtract(area2);

				Point2D center = new Point2D.Double(centerX, centerY);
				float radiusGradient = extendsRadius;
				float[] dist3 = { 0.0f, 1.0f };
				Color[] colors3 = { Color.GRAY, Color.DARK_GRAY };
				RadialGradientPaint p3 = new RadialGradientPaint(center, radiusGradient, dist3, colors3);

				Point2D start4 = new Point2D.Double(centerX, centerY - extendsRadius);
				Point2D end4 = new Point2D.Double(centerX, centerY + extendsRadius);
				float[] dist4 = { 0.0f, 0.5f, 1.0f };
				Color[] colors4 = { Color.BLACK, Color.GRAY, Color.BLACK };
				LinearGradientPaint p4 = new LinearGradientPaint(start4, end4, dist4, colors4);

				// g2dPart.setPaint(p3);
				// g2dPart.setPaint(Color.GRAY);
				g2dPart.setPaint(p4);
				g2dPart.fill(area1);
				g2dPart.setStroke(new BasicStroke(1f));
				g2dPart.setColor(NanoChromatique.GREEN);
				// g2dPart.draw(path1);

				// test blurr
				PartBuffer envelopPart2 = new PartBuffer(centerX - radiusExternal, centerY - radiusExternal, 2 * radiusExternal, 2 * radiusExternal);
				Graphics2D g2dPart2 = envelopPart2.getBuffer().createGraphics();
				g2dPart2.setRenderingHints(g2d.getRenderingHints());
				g2dPart2.translate(-centerX + radiusExternal, -centerY + radiusExternal);

				g2dPart2.setStroke(new BasicStroke(1.2f));
				g2dPart.setColor(NanoChromatique.BLACK);
				g2dPart.draw(path1);

				float blur[] = { 0.0625f, 0.125f, 0.0625f, 0.125f, 0.25f, 0.125f, 0.0625f, 0.125f, 0.0625f };
				float sharpen[] = { -1.0f, -1.0f, -1.0f, -1.0f, 9.0f, -1.0f, -1.0f, -1.0f, -1.0f };

				Kernel kernel = new Kernel(3, 3, blur);

				BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

				BufferedImage imgBlurr = op.filter(envelopPart2.getBuffer(), null);
				// g2dPart.drawImage(imgBlurr, (int)centerX-imgBlurr.getWidth()/2,
				// (int)centerY-imgBlurr.getHeight()/2, imgBlurr.getWidth(),
				// imgBlurr.getHeight(), null);

				// test bkur end

				double splitRadius = (new Double(deltaExternal) - 2d * new Double(epsilonPixel)) / 2.8d;

				double x, y;
				Split s;

				x = centerX + (baseRadius) * Math.cos(Math.toRadians(90));
				y = centerY - (baseRadius) * Math.sin(Math.toRadians(90));
				s = new Split(x, y, splitRadius, 90);
				s.draw(g2dPart);

				x = centerX + (baseRadius) * Math.cos(Math.toRadians(30));
				y = centerY - (baseRadius) * Math.sin(Math.toRadians(30));
				s = new Split(x, y, splitRadius, 30);
				s.draw(g2dPart);

				x = centerX + (baseRadius) * Math.cos(Math.toRadians(90 + 60));
				y = centerY - (baseRadius) * Math.sin(Math.toRadians(90 + 60));
				s = new Split(x, y, splitRadius, 90 + 60);
				s.draw(g2dPart);

				x = centerX + (baseRadius) * Math.cos(Math.toRadians(180 + 30));
				y = centerY - (baseRadius) * Math.sin(Math.toRadians(180 + 30));
				s = new Split(x, y, splitRadius, 180 + 30);
				s.draw(g2dPart);

				x = centerX + (baseRadius) * Math.cos(Math.toRadians(270 + 60));
				y = centerY - (baseRadius) * Math.sin(Math.toRadians(270 + 60));
				s = new Split(x, y, splitRadius, 270 + 60);
				s.draw(g2dPart);

				x = centerX + (baseRadius) * Math.cos(Math.toRadians(270));
				y = centerY - (baseRadius) * Math.sin(Math.toRadians(270));
				s = new Split(x, y, splitRadius, 270);
				s.draw(g2dPart);
				g2dPart.dispose();

			}
			//if(getPartBuffer() != null && getPartBuffer().getBuffer() != null){
				g2d.drawImage(part.getBuffer(), (int) part.getX(), (int) part.getY(), (int) part.getWidth(), (int) part.getHeight(), null);
			//}
		}

	}

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.gauge.core.GaugePart#invalidate()
	 */
	@Override
	public void invalidate() {
		setPartBuffer(null);		
	}

	/**
	 * implements to paint gauge envelop
	 * 
	 * @param g2d
	 * @param radialGauge
	 */
	protected abstract void paintEnvelope(Graphics2D g2d, RadialGauge radialGauge);

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.gauge.core.GaugePart#paintPart(java.awt.Graphics2D, com.jensoft.core.plugin.gauge.core.RadialGauge)
	 */
	@Override
	public final void paintPart(Graphics2D g2d, RadialGauge radialGauge) {
		paintEnvelope(g2d, radialGauge);		
	}
	
}
