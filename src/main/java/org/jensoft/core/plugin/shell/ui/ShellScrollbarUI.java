/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.shell.ui;

import java.awt.Adjustable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalScrollBarUI;

import org.jensoft.core.palette.color.Alpha;
import org.jensoft.core.palette.color.Spectral;

/**
 * <code>essageScrollbarUI</code>
 * 
 * @author Sebastien Janaud
 */
public class ShellScrollbarUI extends MetalScrollBarUI {

    private Color scrollbarThemeColor = Spectral.SPECTRAL_BLUE2.brighter();

    /**
     * create default message scroller
     */
    public ShellScrollbarUI() {
        super();
    }

    /**
     * create message scroller with specified color
     * 
     * @param scrollbarThemeColor
     *            the color to set
     */
    public ShellScrollbarUI(Color scrollbarThemeColor) {
        super();
        this.scrollbarThemeColor = scrollbarThemeColor;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.plaf.basic.BasicScrollBarUI#installUI(javax.swing.JComponent)
     */
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalScrollBarUI#createDecreaseButton(int)
     */
    @Override
    protected JButton createDecreaseButton(int orientation) {
        decreaseButton = new ShellScrollButton(orientation, scrollBarWidth,
                                               isFreeStanding, scrollbarThemeColor);
        return decreaseButton;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalScrollBarUI#createIncreaseButton(int)
     */
    @Override
    protected JButton createIncreaseButton(int orientation) {
        increaseButton = new ShellScrollButton(orientation, scrollBarWidth,
                                               isFreeStanding, scrollbarThemeColor);
        return increaseButton;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalScrollBarUI#paintTrack(java.awt.Graphics, javax.swing.JComponent,
     * java.awt.Rectangle)
     */
    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        //
        // if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
        // int width = trackBounds.width - 4;
        // int height = trackBounds.height;
        //
        // g2.translate(trackBounds.x + 2, trackBounds.y);
        //
        // Rectangle2D casing = new Rectangle2D.Double(0, 0, width, height);
        // g2.setColor(Color.WHITE);
        //
        // float alpha = 0.5f;
        // Composite composite = g2.getComposite();
        // if (composite instanceof AlphaComposite) {
        // alpha *= ((AlphaComposite) composite).getAlpha();
        // }
        // g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
        // alpha));
        // //g2.setColor(Color.GREEN);
        // //g2.fill(casing);
        // //g2.setComposite(composite);
        //
        //
        // //g2.setColor(Color.RED);
        // // g2.drawLine(-1, 0, -1, height);
        // // g2.drawLine(-2, 0, -2, height);
        // //
        // // g2.drawLine(width, 0, width, height);
        // // g2.drawLine(width + 1, 0, width + 1, height);
        //
        // RoundRectangle2D roundCasing = new RoundRectangle2D.Double(0, 2,
        // width, height - 4, width, width);
        // Area area = new Area(casing);
        // area.subtract(new Area(roundCasing));
        // //g2.fill(area);
        // //g2.fill(roundCasing);
        //
        // g2.translate(-trackBounds.x - 2, -trackBounds.y);
        // } else {
        // int width = trackBounds.width;
        // int height = trackBounds.height - 4;
        //
        // g2.translate(trackBounds.x, trackBounds.y + 2);
        //
        // Rectangle2D casing = new Rectangle2D.Double(0, 0, width, height);
        // g2.setColor(Color.WHITE);
        //
        // float alpha = 0.5f;
        // Composite composite = g2.getComposite();
        // if (composite instanceof AlphaComposite) {
        // alpha *= ((AlphaComposite) composite).getAlpha();
        // }
        // g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
        // alpha));
        // g2.fill(casing);
        // g2.setComposite(composite);
        //
        // g2.drawLine(0, -1, width, -1);
        // g2.drawLine(0, -2, width, -2);
        //
        // g2.drawLine(0, height, width, height);
        // g2.drawLine(0, height + 1, width, height + 1);
        //
        // RoundRectangle2D roundCasing = new RoundRectangle2D.Double(2, 0,
        // width - 4, height, height, height);
        // Area area = new Area(casing);
        // area.subtract(new Area(roundCasing));
        // g2.fill(area);
        //
        // g2.translate(-trackBounds.x, -trackBounds.y - 2);
        // }

    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        if (scrollbar.getOrientation() == Adjustable.VERTICAL) {
            g2.translate(thumbBounds.x + 1, thumbBounds.y + 2);

            int width = thumbBounds.width - 3;
            int height = thumbBounds.height - 4;

            RoundRectangle2D casing = new RoundRectangle2D.Double(0, 0, width,
                                                                  height, width, width);
            g2.setColor(Color.WHITE);

            // float alpha = 0.7f;
            // Composite composite = g2.getComposite();
            // if (composite instanceof AlphaComposite) {
            // alpha *= ((AlphaComposite) composite).getAlpha();
            // }
            // g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            // alpha));
            Paint paint = g2.getPaint();
            g2.setPaint(new GradientPaint(0, 0, new Color(0x818a9b), 0, height,
                                          new Color(0x3a4252)));

            g2.setColor(new Alpha(scrollbarThemeColor, 200));
            g2.fill(casing);
            // g2.setComposite(composite);
            g2.setPaint(paint);
            // g2.setComposite(composite);

            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2.0f));
            // g2.draw(casing);
            g2.setStroke(stroke);

            g2.translate(-thumbBounds.x - 1, -thumbBounds.y - 2);
        }
        else {
            g2.translate(thumbBounds.x + 2, thumbBounds.y + 1);

            int width = thumbBounds.width - 4;
            int height = thumbBounds.height - 3;

            RoundRectangle2D casing = new RoundRectangle2D.Double(0, 0, width,
                                                                  height, height, height);
            g2.setColor(Color.WHITE);

            // float alpha = 0.7f;
            // Composite composite = g2.getComposite();
            // if (composite instanceof AlphaComposite) {
            // alpha *= ((AlphaComposite) composite).getAlpha();
            // }
            // g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            // alpha));
            Paint paint = g2.getPaint();
            g2.setPaint(new GradientPaint(0, 0, new Color(0x818a9b), 0, height,
                                          new Color(0x3a4252)));
            g2.setColor(scrollbarThemeColor);
            g2.fill(casing);
            g2.setPaint(paint);
            // g2.setComposite(composite);

            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2.0f));
            // g2.draw(casing);
            g2.setStroke(stroke);

            g2.translate(-thumbBounds.x - 2, -thumbBounds.y - 1);
        }
    }
}
