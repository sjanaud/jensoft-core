/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.linesymbol.iconline;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.jensoft.core.palette.color.TangoPalette;
import com.jensoft.core.plugin.linesymbol.core.LineSymbol;
import com.jensoft.core.plugin.linesymbol.painter.LinePainter;

public class IconLineSymbol extends LineSymbol {

    private ImageIcon iconSymbol;
    private List<LineEntry> entries;

    public IconLineSymbol() {
        super();
        entries = new ArrayList<LineEntry>();
        setSymbolPainter(new IconLinePainter());
        setLinePainter(new LinePainter(TangoPalette.BUTTER1));
    }

    public void addEntry(LineEntry entry) {
        entries.add(entry);
    }

    public void removeEntry(LineEntry entry) {
        entries.remove(entry);
    }

    public ImageIcon getIconSymbol() {
        return iconSymbol;
    }

    public void setIconSymbol(ImageIcon iconSymbol) {
        this.iconSymbol = iconSymbol;

    }

    public List<LineEntry> getEntries() {
        return entries;
    }

}
