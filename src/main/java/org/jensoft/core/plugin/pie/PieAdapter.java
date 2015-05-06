/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.pie;

/**
 * An abstract adapter class for receiving mouse pie events.
 * The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * <P>
 * Mouse events let you track when a mouse is pressed, released, clicked, moved, dragged, when it enters a slice pie
 * <P>
 * Extend this class to create a {@code PieEvent} listener and override the methods for the events of interest. (If you
 * implement the {@code PieListener}, interface, you have to define all of the methods in it. This abstract class
 * defines null methods for them all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with a pie plug in using the pie plug in's
 * {@code addPieListener} methods. The relevant method in the listener object is invoked and the {@code PieEvent} is
 * passed to it in following cases:
 * <p>
 * <ul>
 * <li>when a mouse button is pressed, released, or clicked (pressed and released)
 * <li>when the mouse cursor enters or exits the pie
 * </ul>
 * 
 * @see PieEvent
 * @see PieListener
 * @author Sebastien Janaud
 */
abstract class PieAdapter implements PieListener {

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.PieListener#pieSliceClicked(com.jensoft.core.plugin.pie.PieEvent)
     */
    @Override
    public void pieSliceClicked(PieEvent e) {
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.PieListener#pieSlicePressed(com.jensoft.core.plugin.pie.PieEvent)
     */
    @Override
    public void pieSlicePressed(PieEvent e) {
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.PieListener#pieSliceReleased(com.jensoft.core.plugin.pie.PieEvent)
     */
    @Override
    public void pieSliceReleased(PieEvent e) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.PieListener#pieSliceEntered(com.jensoft.core.plugin.pie.PieEvent)
     */
    @Override
    public void pieSliceEntered(PieEvent e) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.pie.PieListener#pieSliceExited(com.jensoft.core.plugin.pie.PieEvent)
     */
    @Override
    public void pieSliceExited(PieEvent e) {
    }
}
