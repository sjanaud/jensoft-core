/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.highway;

import java.awt.Graphics2D;

public interface HighwayGroupRenderer {

    public boolean paintHighwayGroup(Graphics2D g2d, HighwayGroup highwayGroup);
}
