/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.catalog.xml;

import javax.swing.text.StyledEditorKit;
import javax.swing.text.ViewFactory;

/**
 * <code>XmlEditorKit</code>
 * 
 * @author Sebastien Janaud
 */
public class XmlEditorKit extends StyledEditorKit {

    private static final long serialVersionUID = 6227871279791905224L;
    private ViewFactory xmlViewFactory;
 
    public XmlEditorKit() {
        xmlViewFactory = new XmlViewFactory();
    }
     
    @Override
    public ViewFactory getViewFactory() {
        return xmlViewFactory;
    }
 
    @Override
    public String getContentType() {
        return "text/xml";
    }
}
