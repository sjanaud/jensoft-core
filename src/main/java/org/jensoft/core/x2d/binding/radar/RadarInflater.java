/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.radar;

import org.jensoft.core.plugin.radar.RadarPlugin;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.w3c.dom.Element;

/**
 * @author Sebastien Janaud
 */
public class RadarInflater extends AbstractX2DPluginInflater<RadarPlugin> {

    /**
     * create radar inflater
     */
    public RadarInflater() {
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
     */
    @Override
    public RadarPlugin inflate(Element plugin) {
    	return new RadarPlugin();
    }

   

}
