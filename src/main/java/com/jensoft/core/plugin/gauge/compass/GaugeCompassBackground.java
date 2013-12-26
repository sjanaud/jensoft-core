/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.compass;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Vector;

import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.bg.BackgroundGaugePainter;

public class GaugeCompassBackground extends BackgroundGaugePainter{

    private int centerX;
    private int centerY;
    // private int r1;
    // private int r2;
    private Paint paint = new Color(0, 0, 0, 40);
    private Color outlineColor = Color.WHITE;
    private Shape baseShape;
    private Shape internalShape;
    private Shape externalShape;

    private double baseRadius = 80;
    private double deltaMajorBaseRadius = 35;
    private double deltaMedianBaseRadius = 20;
    private double deltaMinorBaseRadius = 10;
    private double deltaMiliBaseRadius = 1;

    public GaugeCompassBackground(int centerX, int centerY, int baseRadius) {
        super();
        this.centerX = centerX;
        this.centerY = centerY;
        this.baseRadius = baseRadius;
        for (int i = 0; i <= 360; i += 30) {
			CompassCapTicker needlenorth = new CompassCapTicker(i);
			needlenorth.setNature(CompassCapTicker.MAJOR);
			addNeedle(needlenorth);
		}

		for (int i = 0; i <= 360; i += 10) {
			CompassCapTicker needlenorth = new CompassCapTicker(i);
			needlenorth.setNature(CompassCapTicker.MEDIAN);
			addNeedle(needlenorth);
		}

		for (int i = 0; i <= 360; i += 5) {
			CompassCapTicker needlenorth = new CompassCapTicker(i);
			needlenorth.setNature(CompassCapTicker.MINOR);
			addNeedle(needlenorth);
		}
		for (double i = 0; i <= 360; i += 2.5) {
			CompassCapTicker needlenorth = new CompassCapTicker(i);
			needlenorth.setNature(CompassCapTicker.MILI);
			addNeedle(needlenorth);
		}
    }

    private Vector<CompassCapTicker> caps = new Vector<CompassCapTicker>();

    public void addNeedle(CompassCapTicker needle) {
        if (!isAlreadyRegister(needle)) {
            caps.add(needle);
        }
    }

    private boolean isAlreadyRegister(CompassCapTicker cap) {
        for (CompassCapTicker n : caps) {
            if (n.getTheta() == cap.getTheta()) {
                return true;
            }
        }
        return false;
    }

    public CompassCapTicker getNeedle(int index) {
        return caps.get(index);
    }

    public void builCompass() {

        Ellipse2D ellipseArea1 = new Ellipse2D.Double(centerX - baseRadius,
                                                      centerY - baseRadius, 2 * baseRadius, 2 * baseRadius);
        Ellipse2D ellipseArea2 = new Ellipse2D.Double(centerX - baseRadius
                - deltaMajorBaseRadius, centerY - baseRadius
                - deltaMajorBaseRadius,
                                                      2 * (baseRadius + deltaMajorBaseRadius),
                                                      2 * (baseRadius + deltaMajorBaseRadius));

        Area area1 = new Area(ellipseArea1);
        internalShape = area1;

        Area area2 = new Area(ellipseArea2);
        externalShape = area2;

        area2.subtract(area1);

        baseShape = area2;

        buildNeedles();
    }

    public int countNeedle() {
        return caps.size();
    }

    int delta1 = 40;

    private void buildNeedles() {
        for (int i = 0; i < caps.size(); i++) {
            CompassCapTicker n = caps.get(i);

            // System.out.println("build needle : "+n.getTheta());

            double cornerExternalX = centerX
                    - (baseRadius + deltaMajorBaseRadius + delta1);
            double cornerExternalY = centerY
                    - (baseRadius + deltaMajorBaseRadius + delta1);
            Arc2D needleArc = new Arc2D.Double(cornerExternalX,
                                               cornerExternalY,
                                               2 * (baseRadius + deltaMajorBaseRadius + delta1),
                                               2 * (baseRadius + deltaMajorBaseRadius + delta1),
                                               n.getTheta() + n.getAlphaProjection(), -2
                                                       * n.getAlphaProjection(), Arc2D.OPEN);

            n.setNeedleArc(needleArc);

            // System.out.println("math.cos(90):"+Math.cos(Math.toRadians(n.getTheta())));
            // System.out.println("math.sin(90):"+Math.sin(Math.toRadians(n.getTheta())));

            double Xbase = centerX + new Double(baseRadius)
                    * Math.cos(Math.toRadians(n.getTheta()));
            double Ybase = centerY - new Double(baseRadius)
                    * Math.sin(Math.toRadians(n.getTheta()));

            n.setRefPoint(new Point2D.Double(Xbase, Ybase));

            double deltaRadius = 0;
            if (n.getNature() == CompassCapTicker.MAJOR) {
                deltaRadius = deltaMajorBaseRadius;
            }
            if (n.getNature() == CompassCapTicker.MEDIAN) {
                deltaRadius = deltaMedianBaseRadius;
            }
            if (n.getNature() == CompassCapTicker.MINOR) {
                deltaRadius = deltaMinorBaseRadius;
            }
            if (n.getNature() == CompassCapTicker.MILI) {
                deltaRadius = deltaMiliBaseRadius;
            }

            double XDelta = centerX + new Double(baseRadius + deltaRadius)
                    * Math.cos(Math.toRadians(n.getTheta()));
            double YDelta = centerY - new Double(baseRadius + deltaRadius)
                    * Math.sin(Math.toRadians(n.getTheta()));

            Line2D l = new Line2D.Double(Xbase, Ybase, XDelta, YDelta);

            n.setNeedlePath(l);

            Line2D lBaseLine = new Line2D.Double(Xbase, Ybase, centerX, centerY);
            n.setBaseLine(lBaseLine);

        }
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public Shape getInternalShape() {
        return internalShape;
    }

    public Shape getExternalShape() {
        return externalShape;
    }

    public Shape getBaseShape() {
        return baseShape;
    }

    public void setBaseShape(Shape baseShape) {
        this.baseShape = baseShape;
    }

    public Vector<CompassCapTicker> getNeedles() {
        return caps;
    }

    public void setNeedles(Vector<CompassCapTicker> needles) {
        caps = needles;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public double getBaseRadius() {
        return baseRadius;
    }

    public void setBaseRadius(double baseRadius) {
        this.baseRadius = baseRadius;
    }

	@Override
	public void paintBackground(Graphics2D g2d, RadialGauge radialGauge) {
		double centerX = radialGauge.getCenterDevice().getX();
		double centerY = radialGauge.getCenterDevice().getY();
		int radius = radialGauge.getRadius() - 10;

		setCenterX((int) centerX);
		setCenterY((int) centerY);
		setBaseRadius(radius - 30);
		builCompass();
		

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		List<CompassCapTicker> needles = getNeedles();

		g2d.setColor(radialGauge.getWindow2D().getThemeColor().darker());
		g2d.setStroke(new BasicStroke(1f));
		Color blue = new Color(68, 155, 180);
		

		for (CompassCapTicker n : needles) {

			Line2D needlePath = n.getNeedlePath();

			if (n.getNature() == CompassCapTicker.MAJOR) {
				g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
				g2d.setColor(blue);
			}
			if (n.getNature() == CompassCapTicker.MEDIAN) {
				g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
				g2d.setColor(ColorPalette.brighter(TangoPalette.CHAMELEON1, 0.8f));
			}
			if (n.getNature() == CompassCapTicker.MINOR) {
				g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
				g2d.setColor(TangoPalette.CHAMELEON3);
			}
			if (n.getNature() == CompassCapTicker.MILI) {
				g2d.setColor(TangoPalette.CHAMELEON3);
				g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
			}

			g2d.draw(needlePath);
		}

		
	}
    
	
	public static class CompassCapTicker {

	    private String name;
	    private double theta = 0;
	    private Paint paint = new Color(0, 0, 0, 150);
	    private Color colorTheme = Color.WHITE;
	    private int alphaProjection = 20;

	    private boolean lockNeedle = false;
	    private boolean lockRollover = false;
	    private boolean lockPressed = false;

	    private Arc2D needleArc;
	    private Line2D needlePath;
	    private Line2D baseLine;;
	    private Point2D refPoint;

	    public static int MAJOR = 0;
	    public static int MEDIAN = 1;
	    public static int MINOR = 2;
	    public static int MILI = 3;
	    private int nature = MAJOR;

	    public CompassCapTicker() {

	    }

	    public Point2D getRefPoint() {
	        return refPoint;
	    }

	    public void setRefPoint(Point2D refPoint) {
	        this.refPoint = refPoint;
	    }

	    public int getAlphaProjection() {
	        return alphaProjection;
	    }

	    public void setAlphaProjection(int alphaProjection) {
	        this.alphaProjection = alphaProjection;
	    }

	    public Arc2D getNeedleArc() {
	        return needleArc;
	    }

	    public void setNeedleArc(Arc2D needleArc) {
	        this.needleArc = needleArc;
	    }

	    public int getNature() {
	        return nature;
	    }

	    public void setNature(int nature) {
	        this.nature = nature;
	    }

	    public Line2D getBaseLine() {
	        return baseLine;
	    }

	    public void setBaseLine(Line2D baseLine) {
	        this.baseLine = baseLine;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public boolean contains(Point2D p2d) {
	        return needlePath.contains(p2d);
	    }

	    public CompassCapTicker(double theta) {
	        super();
	        this.theta = theta;
	    }

	    public CompassCapTicker(int alphaProjection, double theta) {
	        super();
	        this.alphaProjection = alphaProjection;
	        this.theta = theta;
	    }

	    public double getTheta() {
	        return theta;
	    }

	    public void setTheta(double theta) {
	        this.theta = theta;
	    }

	    public Paint getPaint() {
	        return paint;
	    }

	    public void setPaint(Paint paint) {
	        this.paint = paint;
	    }

	    public Color getColorTheme() {
	        return colorTheme;
	    }

	    public void setColorTheme(Color colorTheme) {
	        this.colorTheme = colorTheme;
	    }

	    public Line2D getNeedlePath() {
	        return needlePath;
	    }

	    public void setNeedlePath(Line2D needlePath) {
	        this.needlePath = needlePath;
	    }

	    public void lockNeedle() {
	        lockNeedle = true;
	    }

	    public void unlockNeedle() {
	        lockNeedle = false;
	    }

	    public boolean isLockNeedle() {
	        return lockNeedle;
	    }

	    public void lockRollover() {
	        lockRollover = true;
	    }

	    public void unlockRollover() {
	        lockRollover = false;
	    }

	    public boolean isLockRollover() {
	        return lockRollover;
	    }

	    public void lockPressed() {
	        lockPressed = true;
	    }

	    public void unlockPressed() {
	        lockPressed = false;
	    }

	    public boolean isLockPressed() {
	        return lockPressed;
	    }

	}

    

}
