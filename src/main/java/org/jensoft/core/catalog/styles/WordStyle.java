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
 * <code>WordStyle</code>
 * 
 * @author sebastien janaud
 */
public class WordStyle {

    private String[] words;
    private Style style;

    public WordStyle(JTextComponent comp, Style style, String... words) {
        this.comp = comp;
        this.style = style;
        this.words = words;
    }

    public void apply() {
        for (int i = 0; i < words.length; i++) {
            String content = null;
            try {
                Document d = comp.getDocument();
                content = d.getText(0, d.getLength()).toLowerCase();
            }
            catch (BadLocationException e) {
            }

            String word = words[i].toLowerCase();
            int lastIndex = 0;
            int wordSize = word.length();

            while ((lastIndex = content.indexOf(word, lastIndex)) != -1) {
                int endIndex = lastIndex + wordSize;
                try {

                    DefaultStyledDocument d = (DefaultStyledDocument) comp.getDocument();
                    d.setCharacterAttributes(lastIndex, wordSize, style, true);
                }
                catch (Exception e) {
                }

                lastIndex = endIndex;
            }

        }

    }

    protected JTextComponent comp;

}
