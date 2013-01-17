/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.core;

import com.jensoft.core.gauge.core.painter.AbstractGaugePainter;
import com.jensoft.core.gauge.core.painter.BackgroundGaugePainter;
import com.jensoft.core.gauge.core.painter.BodyGaugePainter;
import com.jensoft.core.gauge.core.painter.ConstructorGaugePainter;
import com.jensoft.core.gauge.core.painter.EnvelopGaugePainter;
import com.jensoft.core.gauge.core.painter.GlassGaugePainter;
import com.jensoft.core.gauge.core.painter.NeedleGaugePainter;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.window.Window2D;

public class RadialGauge {

    private double x;
    private double y;
    private int radius;
    private Window2D window2D;

    // one per gauge
    private EnvelopGaugePainter envelop;
    private GlassGaugePainter effect;
    private ConstructorGaugePainter constructor;

    private BackgroundGaugePainter background;
    private BodyGaugePainter body;
    private NeedleGaugePainter needle;
    private GeneralMetricsPath metricsManager;

    public RadialGauge(double x, double y, int radius) {
        this.radius = radius;
        this.x = x;
        this.y = y;

        metricsManager = new GeneralMetricsPath();
    }

    public Window2D getWindow2D() {
        return window2D;
    }

    public void setWindow2D(Window2D window2d) {
        window2D = window2d;
    }

    public GeneralMetricsPath getMetricsManager() {
        return metricsManager;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public EnvelopGaugePainter getEnvelop() {
        return envelop;
    }

    public void setEnvelop(EnvelopGaugePainter envelop) {
        envelop.setGauge(this);
        this.envelop = envelop;
    }

    public AbstractGaugePainter getBackground() {
        return background;
    }

    public void setBackground(BackgroundGaugePainter background) {
        background.setGauge(this);
        this.background = background;
    }

    public BodyGaugePainter getBody() {
        return body;
    }

    public void setBody(BodyGaugePainter body) {
        body.setGauge(this);
        this.body = body;
    }

    public NeedleGaugePainter getNeedle() {
        return needle;
    }

    public void setNeedle(NeedleGaugePainter needle) {
        needle.setGauge(this);
        this.needle = needle;
    }

    public GlassGaugePainter getEffect() {
        return effect;
    }

    public void setEffect(GlassGaugePainter effect) {
        effect.setGauge(this);
        this.effect = effect;
    }

    public ConstructorGaugePainter getConstructor() {
        return constructor;
    }

    public void setConstructor(ConstructorGaugePainter constructor) {
        constructor.setGauge(this);
        this.constructor = constructor;
    }

}
