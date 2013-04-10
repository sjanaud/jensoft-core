/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view.background;

import java.awt.Graphics2D;
import java.awt.Image;

import com.jensoft.core.view.View2D;

/**
 * <code>ImageBackground</code> paint's the given image in the background.
 * 
 * @author sebastien janaud
 */
public class ImageBackground extends BackgroundPainter {

    /** the background image */
    private Image backgroundImage;

    /** rescale image, default is true */
    private boolean rescale = true;

    /**
     * create the image background with the specified image
     */
    public ImageBackground(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    /**
     * @return the backgroundImage
     */
    public Image getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * @param backgroundImage
     *            the backgroundImage to set
     */
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    /**
     * @return the rescale
     */
    public boolean isRescale() {
        return rescale;
    }

    /**
     * @param rescale
     *            the rescale to set
     */
    public void setRescale(boolean rescale) {
        this.rescale = rescale;
    }

    /**
     * create image background with given image and scale option.
     * <p>
     * if rescale is true which is the default option, the image is scale to the entire view.<br>
     * else, the image is drawn centered to the view.
     * </p>
     * 
     * @param backgroundImage
     *            the image to paint
     * @param rescale
     *            the scale option.
     */
    public ImageBackground(Image backgroundImage, boolean rescale) {
        this.backgroundImage = backgroundImage;
        this.rescale = rescale;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.view.background.BackgroundPainter#paintViewBackground(com.jensoft.core.view.View2D, java.awt.Graphics2D)
     */
    @Override
    public void paintViewBackground(View2D view, Graphics2D g2d) {
        if (rescale) {
            g2d.drawImage(backgroundImage, 0, 0, view.getWidth(), view.getHeight(), null);
        }
        else {
            g2d.drawImage(backgroundImage, view.getWidth() / 2 - backgroundImage.getWidth(null) / 2, view.getHeight()
                    / 2 - backgroundImage.getHeight(null) / 2, null);
        }
    }

}
