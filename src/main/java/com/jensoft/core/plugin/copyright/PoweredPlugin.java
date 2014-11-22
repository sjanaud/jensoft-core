/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.copyright;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

public class PoweredPlugin extends AbstractPlugin {

    private Color textColor;
    private String copyright;

    public PoweredPlugin(String copyright) {
        setName(getClass().getSimpleName());
        setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        setPriority(1000);
        this.copyright = copyright;
        textColor = ColorPalette.getRandomColor();
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }
        g2d.setFont(new Font("Verdana", Font.PLAIN, 10));
        g2d.setColor(textColor);
        FontMetrics fm = g2d.getFontMetrics();
        fm.stringWidth("Powered by JenSoft");
        g2d.drawString(copyright, 10, 10);

    }

}
