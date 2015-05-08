/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol;

/**
 * An abstract adapter class for receiving mouse events. The methods in this
 * class are empty. This class exists as convenience for creating listener
 * objects.
 * <P>
 * Mouse events let you track when a mouse is pressed, released, clicked, moved, dragged, when it enters a point
 * <P>
 * Extend this class to create a {@code PointEvent} listener and override the methods for the events of interest. (If
 * you implement the {@code PointListener} , interface, you have to define all of the methods in it. This abstract class
 * defines null methods for them all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with a bar plug in using the bar plug in's
 * {@code addPointListener} methods. The relevant method in the listener object is invoked and the {@code PointEvent} is
 * passed to it in following cases:
 * <p>
 * <ul>
 * <li>when a mouse button is pressed, released, or clicked (pressed and released)
 * <li>when the mouse cursor enters or exits the point
 * </ul>
 * 
 * @see PointEvent
 * @see PointListener
 * @author Sebastien Janaud
 */
public abstract class PointAdapter implements PointListener {

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.symbol.PointListener#pointSymbolClicked(org.jensoft.core.plugin.symbol.PointEvent)
     */
    @Override
    public void pointSymbolClicked(PointEvent e) {
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.symbol.PointListener#pointSymbolPressed(org.jensoft.core.plugin.symbol.PointEvent)
     */
    @Override
    public void pointSymbolPressed(PointEvent e) {
    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.symbol.PointListener#pointSymbolReleased(org.jensoft.core.plugin.symbol.PointEvent)
     */
    @Override
    public void pointSymbolReleased(PointEvent e) {
    }

  
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.symbol.PointListener#pointSymbolEntered(org.jensoft.core.plugin.symbol.PointEvent)
     */
    @Override
    public void pointSymbolEntered(PointEvent e) {
    }

 
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.symbol.PointListener#pointSymbolExited(org.jensoft.core.plugin.symbol.PointEvent)
     */
    @Override
    public void pointSymbolExited(PointEvent e) {
    }

}
