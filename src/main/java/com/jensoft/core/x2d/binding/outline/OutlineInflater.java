/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding.outline;

import static com.jensoft.core.x2d.binding.outline.X2DOutlineElement.ELEMENT_OUTLINE_COLOR;

import java.awt.Color;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import com.jensoft.core.x2d.binding.X2DInflater;

/**
 * <code>OutlineInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DInflater(xsi="OutlinePlugin")
public class OutlineInflater extends AbstractX2DPluginInflater<OutlinePlugin> {

    /**
     * create outline inflater
     */
    public OutlineInflater() {
        setPlugin(new OutlinePlugin());
        setXSIType("OutlinePlugin");
    }

   
   
    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public void inflate(Element outlinePluginElement) {
        Element tc = (Element) outlinePluginElement.getElementsByTagName(ELEMENT_OUTLINE_COLOR).item(0);

        Color themeColor = elementColor(tc);
        getPlugin().setThemeColor(themeColor);
    }

}
