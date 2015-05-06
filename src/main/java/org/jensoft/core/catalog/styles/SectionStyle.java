/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.catalog.styles;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;

/**
 * <code>SectionStyle</code>
 * 
 * @author sebastien janaud
 */
public class SectionStyle {

    private Style style;
    private String patternStart;
    private String patternEnd;

    public SectionStyle(JTextComponent comp, final String patternStart, String patternEnd, Style style) {
        this.comp = comp;
        this.patternStart = patternStart;
        this.patternEnd = patternEnd;
        this.style = style;
    }

    public void apply() {
        String content = null;
        try {
            Document d = comp.getDocument();
            content = d.getText(0, d.getLength()).toLowerCase();
        }
        catch (BadLocationException e) {

        }

        int lastStartIndex = 0;
        int lastEndIndex = 0;
        boolean flag = true;
        while (flag) {
            lastStartIndex = content.indexOf(patternStart, lastStartIndex);
            if (lastStartIndex == -1) {
                flag = false;
            }
            else {
                lastEndIndex = content.indexOf(patternEnd, lastStartIndex+patternStart.length());
                if (lastEndIndex != -1) {
                    try {
                        DefaultStyledDocument d = (DefaultStyledDocument) comp.getDocument();
                        d.setCharacterAttributes(lastStartIndex, (lastEndIndex - lastStartIndex) + patternEnd.length(),
                                                 style, true);
                        lastStartIndex = lastEndIndex+patternEnd.length();
                    }
                    catch (Exception e) {
                    }
                }
                else {
                    break;
                }
            }

        }

    }

    protected JTextComponent comp;

}
