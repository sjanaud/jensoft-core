/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.manmade;

import java.awt.Graphics2D;

public interface ManMadeRenderer {

    public boolean paintManMade(Graphics2D g2d, ManMade manMade);
}
