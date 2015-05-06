/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.catalog.source;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalSliderUI;

public class DemoSliderUI extends MetalSliderUI {

    private static Image GLOW;
    static {
        try {
            GLOW = ImageIO.read(DemoSliderUI.class
                    .getResource("slider-glow.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static final Icon GHOST_THUMB_ICON = new GhostThumbIcon();

    @Override
    public void installUI(JComponent c) {
        UIManager.put("Slider.verticalThumbIcon", GHOST_THUMB_ICON);
        super.installUI(c);
        c.setOpaque(false);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // BufferedImage image = new BufferedImage(slider.getWidth(),
        // slider.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // Graphics2D g2 = (Graphics2D) image.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        int width = trackRect.width - 3;
        int height = trackRect.height + width;

        if (slider.getMousePosition() != null) {
            g2.drawImage(GLOW, 0, 0, null);
        }

        g2.translate(trackRect.x + 1, trackRect.y - width / 2);

        RoundRectangle2D casing = new RoundRectangle2D.Double(0, 0, width,
                                                              height, width, width);
        g2.setColor(Color.WHITE);

        Composite composite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                   0.5f));
        g2.fill(casing);
        g2.setComposite(composite);

        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(2.0f));
        g2.draw(casing);
        g2.setStroke(stroke);

        g2.translate(-trackRect.x - 1, -trackRect.y + width / 2);

        // g2.dispose();
        // try {
        // ImageIO.write(image, "PNG", new File("D:\\slider.png"));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    private static final class GhostThumbIcon implements Icon {

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g;

            Ellipse2D thumb = new Ellipse2D.Double(1, 2, 17, 17);

            g2.setColor(Color.WHITE);

            // Composite composite = g2.getComposite();
            // g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            // 0.7f));
            // g2.fill(thumb);
            // g2.setComposite(composite);

            Paint paint = g2.getPaint();
            g2.setPaint(new GradientPaint(0, 2, new Color(0x818a9b), 0, 17,
                                          new Color(0x3a4252)));
            g2.fill(thumb);
            g2.setPaint(paint);

            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(thumb);
            g2.setStroke(stroke);
        }

        @Override
        public int getIconWidth() {
            return 20;
        }

        @Override
        public int getIconHeight() {
            return 20;
        }
    }
}
