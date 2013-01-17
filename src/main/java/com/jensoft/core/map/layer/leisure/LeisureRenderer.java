/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.leisure;

import java.awt.Graphics2D;

public interface LeisureRenderer {

    public boolean paintLeisure(Graphics2D g2d, Leisure leisure);
}
