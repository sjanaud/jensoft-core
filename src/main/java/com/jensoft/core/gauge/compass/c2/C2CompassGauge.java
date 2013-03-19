/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.compass.c2;

import java.awt.Color;
import java.util.Random;

import com.jensoft.core.gauge.background.TextureBackground;
import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.envelop.CiseroEnvelop;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.palette.TexturePalette;

public class C2CompassGauge extends RadialGauge {

    public C2CompassGauge() {
        super(0, 0, 110);

        GeneralMetricsPath pathManager = getMetricsManager();
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

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        TextureBackground textureBackground = new TextureBackground(
                                                                    TexturePalette.getT3());
        setBackground(textureBackground);

        C2Body b1 = new C2Body();
        setBody(b1);

        C2Glass glass = new C2Glass();
        setEffect(glass);

        needle = new C2Needle();
        setNeedle(needle);

        Thread demo = new Thread(needleAnimator, "needle animator");
        // demo.start();

        needle.setCurentValue(180);

    }

    private C2Needle needle;
    Random random = new Random();
    Runnable needleAnimator = new Runnable() {

        @Override
        public void run() {
            while (true) {

                int i = random.nextInt(200) + 20;
                System.out.println("set new value :" + i);
                needle.setCurentValue(i);
                if (getWindow2D() != null) {
                    getWindow2D().getDevice2D().repaintDevice();
                }

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    };

}
