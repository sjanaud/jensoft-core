/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.outline;

import java.awt.Color;

import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;

import com.jensoft.core.plugin.outline.OutlinePlugin;

/**
 * <code>OutlineInflater</code>
 * 
 * @author Sebastien Janaud
 */
@X2DBinding(xsi="OutlinePlugin",plugin=OutlinePlugin.class)
public class OutlineInflater extends AbstractX2DPluginInflater<OutlinePlugin> implements X2DOutlineElement{

   
   
    /* (non-Javadoc)
     * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public OutlinePlugin inflate(Element outlinePluginElement) {
    	OutlinePlugin outline = new OutlinePlugin();
        Element tc = (Element) outlinePluginElement.getElementsByTagName(ELEMENT_OUTLINE_COLOR).item(0);

        Color themeColor = elementColor(tc);
        outline.setThemeColor(themeColor);
        return outline;
    }

}
