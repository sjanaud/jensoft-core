/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.velocity.v2;

import java.awt.Color;
import java.util.Random;

import com.jensoft.core.gauge.background.RoundGradientBackground;
import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.envelop.CiseroEnvelop;
import com.jensoft.core.gauge.glass.Glass1;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.TangoPalette;

public class V2Gauge extends RadialGauge {

    public V2Gauge() {
        super(0, 0, 100);

        GeneralMetricsPath pathManager = getMetricsManager();
        pathManager.setProjectionNature(ProjectionNature.DEVICE);

        pathManager.setMin(0);
        pathManager.setMax(8);

        GlyphMetric metric = new GlyphMetric();
        metric.setValue(0);
        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("0");
        metric.setDivergence(20);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                TangoPalette.CHAMELEON1));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            TangoPalette.CHAMELEON1));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(1);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("1");
        metric.setDivergence(25);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                TangoPalette.CHAMELEON2));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            TangoPalette.CHAMELEON2));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(2);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("2");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                TangoPalette.CHAMELEON3));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            TangoPalette.CHAMELEON3));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(3);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("3");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                TangoPalette.BUTTER1));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            TangoPalette.BUTTER1));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(4);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("4");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                TangoPalette.BUTTER2));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            TangoPalette.BUTTER2));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(5);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("5");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                TangoPalette.BUTTER3));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            TangoPalette.BUTTER3));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(6);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("6");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                TangoPalette.SCARLETRED3));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            TangoPalette.SCARLETRED3));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(7);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("7");
        metric.setDivergence(25);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                TangoPalette.SCARLETRED3));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            TangoPalette.SCARLETRED3));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(8);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("8");
        metric.setDivergence(20);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                TangoPalette.SCARLETRED3));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            TangoPalette.SCARLETRED3));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 24));
        pathManager.addMetric(metric);

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        RoundGradientBackground bg1 = new RoundGradientBackground();
        setBackground(bg1);

        V2Body b1 = new V2Body();
        setBody(b1);

        Glass1 effect = new Glass1();
        setEffect(effect);

        needle = new V2Needle();
        setNeedle(needle);

        V2Constructor constructor = new V2Constructor();
        setConstructor(constructor);

        // Thread demo = new Thread(needleAnimator,"needle animator");
        // demo.start();

        needle.setCurentValue(6);

    }

    private V2Needle needle;
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
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    };

}
