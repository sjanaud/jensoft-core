/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.painter.fill;

/**
 * fill all sections with radial effect
 * 
 * @author Sebastien Janaud
 */
public class PieRadialFill extends PieCompatibleFill {

    /**
     * create pie radial fill
     */
    public PieRadialFill() {
        super(new PieSliceRadialFill());
    }
}
