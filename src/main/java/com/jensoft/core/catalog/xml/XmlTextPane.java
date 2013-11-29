/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.catalog.xml;

import javax.swing.JTextPane;

/**
 * <code>XmlTextPane</code>
 * 
 * @author Sebastien Janaud
 */
public class XmlTextPane extends JTextPane {
    
    private static final long serialVersionUID = 2373118348980782259L;

    public XmlTextPane() {
        this.setEditorKitForContentType("text/xml", new XmlEditorKit());
        this.setContentType("text/xml");
    }
     
}