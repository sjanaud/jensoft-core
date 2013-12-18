/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.compass.c2;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jensoft.core.device.PartBuffer;
import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.core.painter.BodyGaugePainter;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.GlyphUtil;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.palette.PetalPalette;
import com.jensoft.core.palette.TangoPalette;

public class C2Body extends BodyGaugePainter {

    public C2Body() {
    	
    	 pathManager = new GeneralMetricsPath();
         pathManager.setProjectionNature(ProjectionNature.DEVICE);
         pathManager.setAutoReverseGlyph(false);
         pathManager.setReverseAll(true);
         pathManager.setMin(0);
         pathManager.setMax(360);

         GlyphMetric metric = new GlyphMetric();
         metric.setValue(0);
         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("E");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE, Color.ORANGE));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.BUTTER3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 34));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(30);
         metric.setStylePosition(StylePosition.Tangent);
         metric.setMetricsNature(GlyphMetricsNature.Median);
         metric.setMetricsLabel("3O");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(60);

         metric.setStylePosition(StylePosition.Tangent);
         metric.setMetricsNature(GlyphMetricsNature.Median);
         metric.setMetricsLabel("6O");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(90);
         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("N");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 JennyPalette.JENNY6));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(JennyPalette.JENNY6));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 34));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(120);

         metric.setStylePosition(StylePosition.Tangent);
         metric.setMetricsNature(GlyphMetricsNature.Median);
         metric.setMetricsLabel("12O");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(150);

         metric.setStylePosition(StylePosition.Tangent);
         metric.setMetricsNature(GlyphMetricsNature.Median);
         metric.setMetricsLabel("15O");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
         pathManager.addMetric(metric);

         // metric = new GlyphMetric();
         // metric.setUserValue(150);
         //
         // metric.setStylePosition(StylePosition.Default);
         // metric.setMetricsNature(MetricsNature.Median);
         // metric.setMetricsLabel("15O");
         // metric.setDivergence(15);
         // metric.setGlyphMetricFill(new GradientFill(Color.WHITE,
         // TangoPalette.CHAMELEON1));
         // metric.setGlyphMetricMarkerPainter(new TicTacMarker(
         // TangoPalette.CHAMELEON1));
         // //metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         // metric.setFont(InputFonts.getFont(InputFonts.ELEMENT,18));
         // pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(180);
         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("W");
         metric.setDivergence(-30);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.ORANGE3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.ORANGE3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 34));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(210);
         metric.setStylePosition(StylePosition.Tangent);
         metric.setMetricsNature(GlyphMetricsNature.Median);
         metric.setMetricsLabel("210");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(240);
         metric.setStylePosition(StylePosition.Tangent);
         metric.setMetricsNature(GlyphMetricsNature.Median);
         metric.setMetricsLabel("240");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(270);

         metric.setStylePosition(StylePosition.Default);
         metric.setMetricsNature(GlyphMetricsNature.Major);
         metric.setMetricsLabel("S");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON1));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON1));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 34));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(300);

         metric.setStylePosition(StylePosition.Tangent);
         metric.setMetricsNature(GlyphMetricsNature.Median);
         metric.setMetricsLabel("300");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
         pathManager.addMetric(metric);

         metric = new GlyphMetric();
         metric.setValue(330);

         metric.setStylePosition(StylePosition.Tangent);
         metric.setMetricsNature(GlyphMetricsNature.Median);
         metric.setMetricsLabel("330");
         metric.setDivergence(-20);
         metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                 TangoPalette.CHAMELEON3));
         metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                             TangoPalette.CHAMELEON3));
         // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
         metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 18));
         pathManager.addMetric(metric);
         
         needle = new C2Needle();
         needle.setPathManager(pathManager);
         
        createCompasse();
    }

    public GeneralMetricsPath getPathManager() {
		return pathManager;
	}

	public void setPathManager(GeneralMetricsPath pathManager) {
		this.pathManager = pathManager;
	}

	private GeneralMetricsPath pathManager;
    private Arc2D arc2d;
    private int startAngleDegreee = 0;
    private int endAngleDegree = 360;
    private PartBuffer metricsPart;
    private C2Needle needle;

    private SailCompass compass;

    public void createCompasse() {

        compass = new SailCompass(0, 0, 120);
        compass.setPaint(Color.DARK_GRAY);

        for (int i = 0; i <= 360; i += 30) {
            Cap needlenorth = new Cap(i);
            needlenorth.setNature(Cap.MAJOR);
            compass.addNeedle(needlenorth);
        }

        for (int i = 0; i <= 360; i += 10) {
            Cap needlenorth = new Cap(i);
            needlenorth.setNature(Cap.MEDIAN);
            compass.addNeedle(needlenorth);
        }

        for (int i = 0; i <= 360; i += 5) {
            Cap needlenorth = new Cap(i);
            needlenorth.setNature(Cap.MINOR);
            compass.addNeedle(needlenorth);
        }
        for (double i = 0; i <= 360; i += 2.5) {
            Cap needlenorth = new Cap(i);
            needlenorth.setNature(Cap.MILI);
            compass.addNeedle(needlenorth);
        }

    }

    protected void paintCompass(Graphics2D g2d) {

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();
        int radius = getGauge().getRadius() - 10;

        compass.setCenterX((int) centerX);
        compass.setCenterY((int) centerY);
        compass.setBaseRadius(radius - 50);
        compass.builCompass();
        Shape baseShape = compass.getBaseShape();
        // g2d.setColor(getWindow2D().getThemeColor().darker());

        // g2d.setColor(new Color(255,255,255,50));
        // g2d.fill(compass.getBaseShape());

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    0.6f));

        // g2d.fill(baseShape);
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
        List<Cap> needles = compass.getNeedles();
        // g2d.setStroke(new BasicStroke(0.5f));

        g2d.setColor(getGauge().getWindow2D().getThemeColor().darker());
        g2d.setStroke(new BasicStroke(1f));
        Color blue = new Color(68, 155, 180);
        Color green = new Color(162, 189, 66);
        // Color orange = new Color(237,162,44);
        Color orange = PetalPalette.PETAL2_HC;

        for (Cap n : needles) {

            Line2D needlePath = n.getNeedlePath();

            // g2d.setColor(Color.WHITE);

            if (n.getNature() == Cap.MAJOR) {
                g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
                                              BasicStroke.JOIN_ROUND));
                g2d.setColor(blue);
            }
            if (n.getNature() == Cap.MEDIAN) {
                g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
                                              BasicStroke.JOIN_ROUND));
                g2d.setColor(ColorPalette.brighter(TangoPalette.CHAMELEON1,
                                                   0.8f));
            }
            if (n.getNature() == Cap.MINOR) {
                g2d.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT,
                                              BasicStroke.JOIN_ROUND));
                g2d.setColor(TangoPalette.CHAMELEON3);
            }
            if (n.getNature() == Cap.MILI) {
                g2d.setColor(TangoPalette.CHAMELEON3);
                g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT,
                                              BasicStroke.JOIN_BEVEL));
            }

            g2d.draw(needlePath);

            GeometryPath geoPath = new GeometryPath(n.getBaseLine());
            GeometryPath geoPath2 = new GeometryPath(n.getNeedleArc());
            int offset = 10;
            AffineTransform af = new AffineTransform();
            AffineTransform af2 = new AffineTransform();
            if (n.getTheta() == 0 || n.getTheta() == 90 || n.getTheta() == 180
                    || n.getTheta() == 270) {

                String cardinal = null;
                String cap = null;
                if (n.getTheta() == 0) {
                    cardinal = "East";
                    cap = "90�";
                }
                else if (n.getTheta() == 90) {
                    cardinal = "North";
                    cap = "0�";
                    // g2d.draw(n.getNeedleArc());
                    // //g2d.draw(n.getBaseLine());
                    // g2d.draw(n.getNeedlePath());
                    //
                    // Ellipse2D e = new
                    // Ellipse2D.Double(n.getRefPoint().getX()-1,n.getRefPoint().getY()-1,2,2);
                    // g2d.draw(e);
                }
                else if (n.getTheta() == 180) {
                    cardinal = "West";
                    cap = "270�";
                }
                else if (n.getTheta() == 270) {
                    cardinal = "South";
                    cap = "180�";

                }

                // g2d.draw(n.getBaseLine());
                Font f = new Font("Tahoma", Font.PLAIN, 14);
                Font f2 = new Font("Tahoma", Font.PLAIN, 14);
                FontRenderContext frc = g2d.getFontRenderContext();
                GlyphVector glyphVector = f.createGlyphVector(frc, cardinal);
                GlyphVector glyphVector2 = f2.createGlyphVector(frc, cap);

                float gv2Width = GlyphUtil.getGlyphWidth(glyphVector2);
                float angleOffset = geoPath2.lengthOfPath() / 2 - gv2Width / 2;

                for (int j = 0; j < glyphVector2.getNumGlyphs(); j++) {

                    Point2D p = glyphVector2.getGlyphPosition(j);
                    float px = (float) p.getX();
                    float py = (float) p.getY();

                    Point2D pointGlyph = geoPath2.pointAtLength(angleOffset
                            + GlyphUtil.getGlyphWidthAtToken(glyphVector2, j));

                    Shape glyph = glyphVector2.getGlyphOutline(j);
                    // Shape glyphBound2D =
                    // glyphVector2.getGlyphOutline(j).getBounds2D();

                    float angle = geoPath2.angleAtLength(angleOffset
                            + GlyphUtil.getGlyphWidthAtToken(glyphVector2, j));
                    af2.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
                    af2.rotate(angle);
                    af2.translate(-px, -py + 4);

                    Shape ts = af2.createTransformedShape(glyph);

                    g2d.setColor(Color.orange);
                    // Shape tsBound2D =
                    // af2.createTransformedShape(glyphBound2D);
                    // g2d.draw(ts);

                    g2d.setColor(getGauge().getWindow2D().getThemeColor());

                    // item.addGlyphOutline(ts);
                    // g2d.fill(ts);

                }

                for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

                    Point2D p = glyphVector.getGlyphPosition(j);
                    float px = (float) p.getX();
                    float py = (float) p.getY();

                    Point2D pointGlyph = geoPath.pointAtLength(offset
                            + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));

                    Shape glyph = glyphVector.getGlyphOutline(j);
                    // Shape glyphBound2D =
                    // glyphVector.getGlyphOutline(j).getBounds2D();

                    float angle = geoPath.angleAtLength(offset
                            + GlyphUtil.getGlyphWidthAtToken(glyphVector, j));
                    af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
                    af.rotate(angle);
                    af.translate(-px, -py + 4);

                    Shape ts = af.createTransformedShape(glyph);

                    g2d.setColor(Color.orange);
                    // Shape tsBound2D =
                    // af.createTransformedShape(glyphBound2D);
                    // g2d.draw(ts);

                    g2d.setColor(getGauge().getWindow2D().getThemeColor());

                    // item.addGlyphOutline(ts);
                    // g2d.fill(ts);

                }

            }

        }

    }

    private void paintMetrics(Graphics2D g2d) {

        double centerX = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(getGauge().getX(), 0)).getX();
        double centerY = getGauge().getWindow2D()
                .userToPixel(new Point2D.Double(0, getGauge().getY())).getY();

        int radius = getGauge().getRadius() - 10;

        GeneralMetricsPath pathManager = getPathManager();
        pathManager.setWindow2d(getGauge().getWindow2D());
        pathManager.resetPath();
        pathManager.setFontRenderContext(g2d.getFontRenderContext());
        arc2d = new Arc2D.Double(centerX - radius, centerY - radius,
                                 2 * radius, 2 * radius, startAngleDegreee, endAngleDegree
                                         - startAngleDegreee, Arc2D.OPEN);

        radius = getGauge().getRadius();
        pathManager.append(arc2d);
        g2d.setColor(Color.RED);
       // g2d.drawRect((int) (centerX - radius), (int) (centerY - radius),
         //            2 * radius, 2 * radius);
        if (metricsPart == null) {

            metricsPart = new PartBuffer(centerX - radius, centerY - radius,
                                         2 * radius, 2 * radius);
//            g2d.setColor(Color.RED);
//            g2d.drawRect((int) (centerX - radius), (int) (centerY - radius),
//                         2 * radius, 2 * radius);
            Graphics2D g2dPart = metricsPart.getBuffer().createGraphics();
            g2dPart.setRenderingHints(g2d.getRenderingHints());
            g2dPart.translate(-centerX + radius, -centerY + radius);
            g2dPart.setStroke(new BasicStroke(0.4f));
            g2dPart.setColor(Color.BLACK);

            pathManager.setFontRenderContext(g2dPart.getFontRenderContext());

            List<GlyphMetric> metrics = pathManager.getMetrics();
            for (GlyphMetric m : metrics) {

                if (m.getGlyphMetricMarkerPainter() != null) {
                    m.getGlyphMetricMarkerPainter()
                            .paintGlyphMetric(g2dPart, m);
                }

                if (m.getGlyphMetricFill() != null) {
                    m.getGlyphMetricFill().paintGlyphMetric(g2dPart, m);
                }
                if (m.getGlyphMetricDraw() != null) {
                    m.getGlyphMetricDraw().paintGlyphMetric(g2dPart, m);
                }
                if (m.getGlyphMetricEffect() != null) {
                    m.getGlyphMetricEffect().paintGlyphMetric(g2dPart, m);
                }

            }

            g2d.drawImage(metricsPart.getBuffer(), (int) centerX - radius,
                          (int) centerY - radius, 2 * radius, 2 * radius, null);

        }
        else {

            g2d.drawImage(metricsPart.getBuffer(), (int) centerX - radius,
                          (int) centerY - radius, 2 * radius, 2 * radius, null);
        }

    }

    @Override
    public void paintBody(Graphics2D g2d, RadialGauge radialGauge) {
        paintCompass(g2d);
        paintMetrics(g2d);
		paintNeedle(g2d);
	}

	private void paintNeedle(Graphics2D g2d) {
		needle.setGauge(getGauge());
		needle.paintGauge(g2d, getGauge());
	}

}
