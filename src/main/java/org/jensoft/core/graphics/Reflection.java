/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


/**
 * 
 * @since 1.0
 * @author sebastien janaud
 *
 */
public final class Reflection {

    public static BufferedImage createReflectedPicture(BufferedImage avatar,
            BufferedImage alphaMask) {
        int avatarWidth = avatar.getWidth();
        int avatarHeight = avatar.getHeight();

        BufferedImage buffer = createReflection(avatar, avatarWidth,
                                                avatarHeight);

        applyAlphaMask(buffer, alphaMask, avatarHeight);

        return buffer;/* .getSubimage(0, 0, avatarWidth, avatarHeight * 3 / 2) */
    }

    private static void applyAlphaMask(BufferedImage buffer,
            BufferedImage alphaMask, int avatarHeight) {

        Graphics2D g2 = buffer.createGraphics();
        g2.setComposite(AlphaComposite.DstOut);
        g2.drawImage(alphaMask, null, 0, avatarHeight);
        g2.dispose();
    }

    private static BufferedImage createReflection(BufferedImage avatar,
            int avatarWidth, int avatarHeight) {

        BufferedImage buffer = new BufferedImage(avatarWidth,
                                                 avatarHeight * 5 / 3, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buffer.createGraphics();

        g.drawImage(avatar, null, null);
        g.translate(0, avatarHeight * 2);

        AffineTransform reflectTransform = AffineTransform.getScaleInstance(
                                                                            1.0, -1.0);
        g.drawImage(avatar, reflectTransform, null);

        g.dispose();

        return buffer;
    }

    public static BufferedImage createGradientMask(int avatarWidth,
            int avatarHeight) {
        return createGradientMask(avatarWidth, avatarHeight, 0.7f, 1.0f);
    }

    public static BufferedImage createGradientMask(int avatarWidth,
            int avatarHeight, float opacityStart, float opacityEnd) {
        BufferedImage gradient = new BufferedImage(avatarWidth, avatarHeight,
                                                   BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gradient.createGraphics();
        GradientPaint painter = new GradientPaint(0.0f, 0.0f, new Color(1.0f,
                                                                        1.0f, 1.0f, opacityStart), 0.0f,
                                                  avatarHeight / 2.0f,
                                                  new Color(1.0f, 1.0f, 1.0f, opacityEnd));
        g.setPaint(painter);
        g.fill(new Rectangle2D.Double(0, 0, avatarWidth, avatarHeight));

        g.dispose();

        return gradient;
    }
}
