/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.texture;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>TexturePlugin</code>
 * <p>
 * knows how to paint texture in the device background.
 * </p>
 */
public class TexturePlugin extends AbstractPlugin {

    /** texture paint */
    private TexturePaint texture;

    /** texture alpha */
    private float textureAlpha = 1f;

    public TexturePlugin(TexturePaint texture) {
        this.texture = texture;
    }

    /**
     * @return the texture
     */
    public TexturePaint getTexture() {
        return texture;
    }

    /**
     * @param texture
     *            the texture to set
     */
    public void setTexture(TexturePaint texture) {
        this.texture = texture;
    }

    /**
     * @return the textureAlpha
     */
    public float getTextureAlpha() {
        return textureAlpha;
    }

    /**
     * @param textureAlpha
     *            the textureAlpha to set
     */
    public void setTextureAlpha(float textureAlpha) {
        this.textureAlpha = textureAlpha;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
     */
    @Override
    public void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (windowPart != WindowPart.Device) {
            return;
        }

        JComponent comp = v2d.getWindowComponent(windowPart);
        Rectangle2D device = new Rectangle2D.Double(0, 0, comp.getWidth(),
                                                    comp.getHeight());

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    textureAlpha));
        g2d.setPaint(texture);
        g2d.fill(device);
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
    }

}
