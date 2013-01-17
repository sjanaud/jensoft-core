/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette;

import java.awt.Color;

/**
 * <code>Alpha</code> version for base color
 * 
 * @author Sebastien Janaud
 */
public class Alpha extends Color {

    /** uuid */
    private static final long serialVersionUID = -1457894383166253249L;

    public Alpha(Color c, int alpha) {
        super(c.getRed(), c.getGreen(), c.getBlue(), alpha);
    }
}
