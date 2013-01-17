/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.velocity.v1;

import java.awt.Color;
import java.util.Random;

import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.envelop.CiseroEnvelop;
import com.jensoft.core.gauge.glass.Glass1;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.PetalPalette;

public class V1Gauge extends RadialGauge {

    public V1Gauge() {
        super(0, 0, 100);

        GeneralMetricsPath pathManager = getMetricsManager();
        pathManager.setProjectionNature(ProjectionNature.DEVICE);

        pathManager.setMin(0);
        pathManager.setMax(280);

        GlyphMetric metric = new GlyphMetric();
        metric.setValue(20);
        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("20");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                PetalPalette.PETAL1_HC));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            PetalPalette.PETAL1_HC));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(60);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("60");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                PetalPalette.PETAL1_HC));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            PetalPalette.PETAL1_HC));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(120);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("120");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                PetalPalette.PETAL1_HC));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            PetalPalette.PETAL1_HC));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(180);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("180");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                PetalPalette.PETAL1_HC));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            PetalPalette.PETAL1_HC));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(220);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("220");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                PetalPalette.PETAL1_HC));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            PetalPalette.PETAL1_HC));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
        pathManager.addMetric(metric);

        metric = new GlyphMetric();
        metric.setValue(260);

        metric.setStylePosition(StylePosition.Default);
        metric.setMetricsNature(GlyphMetricsNature.Major);
        metric.setMetricsLabel("260");
        metric.setDivergence(30);
        metric.setGlyphMetricFill(new GlyphFill(Color.WHITE,
                                                PetalPalette.PETAL1_HC));
        metric.setGlyphMetricMarkerPainter(new TicTacMarker(
                                                            PetalPalette.PETAL1_HC));
        // metric.setGlyphMetricDraw(new ClassicGlyphDraw(Color.WHITE));
        metric.setFont(InputFonts.getFont(InputFonts.ELEMENT, 20));
        pathManager.addMetric(metric);

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        V1Body b1 = new V1Body();
        setBody(b1);

        Glass1 effect = new Glass1();
        setEffect(effect);

        needle = new V1Needle();
        setNeedle(needle);

        V1Constructor v1c = new V1Constructor();
        setConstructor(v1c);
        // Thread demo = new Thread(needleAnimator,"needle animator");
        // demo.start();

        needle.setCurentValue(180);

    }

    private V1Needle needle;
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
