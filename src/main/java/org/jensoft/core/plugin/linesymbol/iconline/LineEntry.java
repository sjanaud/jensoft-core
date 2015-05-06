/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.linesymbol.iconline;

import javax.swing.ImageIcon;

public class LineEntry {

    private double value;
    private ImageIcon iconSymbol;

    public LineEntry(double value) {
        super();
        this.value = value;
    }

    public LineEntry(double value, ImageIcon iconSymbol) {
        super();
        this.value = value;
        this.iconSymbol = iconSymbol;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ImageIcon getIconSymbol() {
        return iconSymbol;
    }

    public void setIconSymbol(ImageIcon iconSymbol) {
        this.iconSymbol = iconSymbol;

    }

}
