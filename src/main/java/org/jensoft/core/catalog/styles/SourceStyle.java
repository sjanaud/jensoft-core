/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.catalog.styles;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;

/**
 * <code>SourceStyle</code>
 * 
 * @author sebastien janaud
 */
public class SourceStyle {

    private Style style;

    public SourceStyle(JTextComponent comp, Style style) {
        this.comp = comp;
        this.style = style;
    }

    public void apply() {

        try {
            DefaultStyledDocument d = (DefaultStyledDocument) comp.getDocument();
            d.setCharacterAttributes(0, d.getLength(), style, true);
        }
        catch (Exception e) {
        }

    }

    protected JTextComponent comp;

}
