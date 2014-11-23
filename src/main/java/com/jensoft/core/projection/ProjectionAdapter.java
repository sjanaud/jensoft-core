/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.projection;

/**
 * <code>ProjectionAdapter</codes>
 * 
 * @since 1.0
 * @author sebastien janaud
 *
 */
public class ProjectionAdapter implements ProjectionListener {
    
    /* (non-Javadoc)
     * @see com.jensoft.core.projection.ProjectionListener#projectionLockActive(com.jensoft.core.projection.ProjectionEvent)
     */
    @Override
    public void projectionLockActive(ProjectionEvent w2dEvent) {
    }

    /* (non-Javadoc)
     * @see com.jensoft.core.projection.ProjectionListener#projectionBoundChanged(com.jensoft.core.projection.ProjectionEvent)
     */
    @Override
    public void projectionBoundChanged(ProjectionEvent w2dEvent) {
    }
    
    /* (non-Javadoc)
     * @see com.jensoft.core.projection.ProjectionListener#projectionUnlockActive(com.jensoft.core.projection.ProjectionEvent)
     */
    @Override
    public void projectionUnlockActive(ProjectionEvent w2dEvent) {
    }
   
    /* (non-Javadoc)
     * @see com.jensoft.core.projection.ProjectionListener#projectionResized(com.jensoft.core.projection.ProjectionEvent)
     */
    @Override
    public void projectionResized(ProjectionEvent w2dEvent) {
    }
    
}
