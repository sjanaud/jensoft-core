/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.copyright;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import org.jensoft.core.graphics.Antialiasing;
import org.jensoft.core.graphics.TextAntialiasing;
import org.jensoft.core.palette.color.ColorPalette;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

public class CopyrightPlugin extends AbstractPlugin {

    private Color textColor = RosePalette.INDIGO;
    private String copyright;

    public CopyrightPlugin() {
        setName(getClass().getSimpleName());
        setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        setPriority(1000);
        this.copyright = "\u00a9"+"JenSoftAPI"+" http://wwww.jensoftapi.com";
        textColor = ColorPalette.getRandomColor();
    }

    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {

        if (viewPart != ViewPart.Device) {
            return;
        }
        g2d.setColor(textColor);
        g2d.setFont(new Font("Verdana", Font.PLAIN, 10));
        g2d.drawString(copyright, 10, 10);

    }

}
