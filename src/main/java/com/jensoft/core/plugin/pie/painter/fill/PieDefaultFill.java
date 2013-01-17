/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.fill;

/**
 * <code>PieDefaultFill</code> fill all sections with {@link PieSliceDefaultFill}
 * 
 * @see PieSliceDefaultFill
 * @see PieCompatibleFill
 * @see AbstractPieFill
 * @author Sebastien Janaud
 */
public class PieDefaultFill extends PieCompatibleFill {

    /**
     * @param compatibleSliceFill
     */
    public PieDefaultFill() {
        super(new PieSliceDefaultFill());
    }

}
