/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.background;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.graphics.AlphaInterpolation;
import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>DeviceGradientPlugin</code>
 * 
 * @author sebastien janaud
 */
public class DeviceGradientBackgroundPlugin extends AbstractPlugin {

    private Color color1 = new Color(0x202737);
    private Color color2 = Color.BLACK;
    private float alpha = 1f;

    public DeviceGradientBackgroundPlugin() {
        setDithering(Dithering.On);
        setAntialiasing(Antialiasing.On);
        setAlphaInterpolation(AlphaInterpolation.Quality);
    }

    public DeviceGradientBackgroundPlugin(Color color1, Color color2) {
        this();
        this.color1 = color1;
        this.color2 = color2;
    }

    public DeviceGradientBackgroundPlugin(Color color1, Color color2, float alpha) {
        this();
        this.color1 = color1;
        this.color2 = color2;
        this.alpha = alpha;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {

        if (viewPart != ViewPart.Device) {
            return;
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alpha));
        int width = getProjection().getDevice2D().getDeviceWidth();
        int height = getProjection().getDevice2D().getDeviceHeight();

        Point2D start = new Point2D.Double(width / 2, 0);
        Point2D end = new Point2D.Double(width / 2, height);
        float[] dist = { 0.0f, 1.0f };
        Color[] colors = { color1, color2 };

        LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
                                                        colors);

        g2d.setPaint(p);
        Rectangle2D r = new Rectangle2D.Double(0, 0, width, height);

        g2d.fill(r);
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
