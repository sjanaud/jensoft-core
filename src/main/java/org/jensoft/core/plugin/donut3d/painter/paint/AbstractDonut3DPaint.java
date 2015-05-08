/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut3d.painter.paint;

import java.awt.Graphics2D;

import org.jensoft.core.plugin.donut3d.Donut3D;
import org.jensoft.core.plugin.donut3d.painter.Donut3DPainter;

/**
 * <code>AbstractDonut3DPaint</code> Abstract definition for donut3D painting operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractDonut3DPaint implements Donut3DPainter {

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut3d.painter.Donut3DPainter#paintDonut3D(java.awt.Graphics2D, org.jensoft.core.plugin.donut3d.Donut3D)
     */
    @Override
    public void paintDonut3D(Graphics2D g2d, Donut3D donut3D) {
    }

}
