/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.landuse;

import java.awt.Graphics2D;

public interface LanduseRenderer {

    public boolean paintLanduse(Graphics2D g2d, Landuse landuse);
}
