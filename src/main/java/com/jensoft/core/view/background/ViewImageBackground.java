/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view.background;

import java.awt.Graphics2D;
import java.awt.Image;

import com.jensoft.core.view.View;

/**
 * <code>ViewImageBackground</code> paint's the given image in the view background.
 * 
 * @author sebastien janaud
 */
public class ViewImageBackground extends ViewBackgroundPainter {

    /** the background image */
    private Image backgroundImage;

    /** rescale image, default is true */
    private boolean rescale = true;

    /**
     * create the image background with the specified image
     */
    public ViewImageBackground(Image backgroundImage) {
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
    public ViewImageBackground(Image backgroundImage, boolean rescale) {
        this.backgroundImage = backgroundImage;
        this.rescale = rescale;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.view.background.BackgroundPainter#paintViewBackground(com.jensoft.core.view.View2D, java.awt.Graphics2D)
     */
    @Override
    public void paintViewBackground(View view,int viewWidth,int viewHeight, Graphics2D g2d) {
        if (rescale) {
            g2d.drawImage(backgroundImage, 0, 0, viewWidth, viewHeight, null);
        }
        else {
            g2d.drawImage(backgroundImage, viewWidth / 2 - backgroundImage.getWidth(null) / 2, viewHeight
                    / 2 - backgroundImage.getHeight(null) / 2, null);
        }
    }

}
