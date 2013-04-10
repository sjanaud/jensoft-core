/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view.deflater.svg;

import org.w3c.dom.Document;

import com.jensoft.core.view.View2D;
import com.jensoft.core.view.deflater.AbstractViewDeflater;

/**
 * <code>SVGDeflater</code>
 * <p>
 * takes the responsibility to transform a view in SVG XML Document.
 * </p>
 * 
 * @author sebastien janaud
 */
public class SVGDeflater  extends AbstractViewDeflater{

    public SVGDeflater(View2D view2d) {
        super(view2d);
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.view.deflater.AbstractViewDeflater#deflate()
     */
    @Override
    public Document deflate() {
        return null;
    }
}
