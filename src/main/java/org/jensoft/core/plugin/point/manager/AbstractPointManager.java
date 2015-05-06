/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.point.manager;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import org.jensoft.core.plugin.point.Point;
import org.jensoft.core.projection.Projection;

/**
 * <code>AbstractPointManager</code>
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 *
 */
public abstract class AbstractPointManager implements PointLayoutManager {

	/**projection */
    private Projection projection;
    
    /**point type*/
    private int type;
    
    /**point color*/
    private Color color;

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.point.manager.PointLayoutManager#getPoints()
     */
    @Override
    public List<Point> getPoints() {
        return Collections.EMPTY_LIST;
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.point.manager.PointLayoutManager#setProjection(com.jensoft.core.projection.Projection)
     */
    @Override
    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.point.manager.PointLayoutManager#getProjection()
     */
    @Override
    public Projection getProjection() {
        return projection;
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.point.manager.PointLayoutManager#setType(int)
     */
    @Override
    public void setType(int type) {
        this.type = type;
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.point.manager.PointLayoutManager#getType()
     */
    @Override
    public int getType() {
        return type;
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.point.manager.PointLayoutManager#setPointColor(java.awt.Color)
     */
    @Override
    public void setPointColor(Color color) {
        this.color = color;
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.point.manager.PointLayoutManager#getPointColor()
     */
    @Override
    public Color getPointColor() {
        return color;
    }

}
