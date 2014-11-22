/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view;

/**
 * <code>ViewAdapter</code>
 * 
 * @since 1.0
 * 
 * @author sebastien janaud
 *
 */
public class ViewAdapter implements ViewListener {

   
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewWindow2DSelected(com.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewProjectionSelected(ViewEvent event) {
    }


    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewMoved(com.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewMoved(ViewEvent event) {
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewResized(com.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewResized(ViewEvent event) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewHidden(com.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewHidden(ViewEvent event) {
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewShown(com.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewShown(ViewEvent event) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewFocusGained(com.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewFocusGained(ViewEvent event) {
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewFocusLost(com.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewFocusLost(ViewEvent event) {
    }

}
