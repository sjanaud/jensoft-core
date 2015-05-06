/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie.painter.fill;

/**
 * <code>PieDefaultFill</code> fill all slices with {@link PieSliceDefaultFill}
 * 
 * @author Sebastien Janaud
 */
public class PieDefaultFill extends PieCompatibleFill {

    /**
     * create the pie compatible fill
     */
    public PieDefaultFill() {
        super(new PieSliceDefaultFill());
    }

}
