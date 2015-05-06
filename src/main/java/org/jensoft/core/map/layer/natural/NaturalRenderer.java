/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.natural;

import java.awt.Graphics2D;

public interface NaturalRenderer {

    public boolean paintNatural(Graphics2D g2d, Natural natural);
}
