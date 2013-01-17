/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.copyright;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

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

    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (windowPart != WindowPart.Device) {
            return;
        }

        g2d.setFont(InputFonts.getNoMove(9));
        g2d.setColor(textColor);
        FontMetrics fm = g2d.getFontMetrics();
        fm.stringWidth("Powered by JenSoft");
        g2d.drawString(copyright, 10, 10);

    }

}
