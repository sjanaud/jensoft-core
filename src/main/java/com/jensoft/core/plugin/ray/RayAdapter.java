/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray;

/**
 * An abstract adapter class for receiving mouse events. The methods in this
 * class are empty. This class exists as convenience for creating listener
 * objects.
 * <P>
 * Mouse events let you track when a mouse is pressed, released, clicked, moved, dragged, when it enters a ray
 * <P>
 * Extend this class to create a {@code RayEvent} listener and override the methods for the events of interest. (If you
 * implement the {@code RayListener} , interface, you have to define all of the methods in it. This abstract class
 * defines null methods for them all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with a ray plug in using the ray plug in's
 * {@code addRayListener} methods. The relevant method in the listener object is invoked and the {@code RayEvent} is
 * passed to it in following cases:
 * <p>
 * <ul>
 * <li>when a mouse button is pressed, released, or clicked (pressed and released)
 * <li>when the mouse cursor enters or exits the ray
 * </ul>
 * 
 * @see RayEvent
 * @see RayListener
 */
public abstract class RayAdapter implements RayListener {

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.RayListener#rayClicked(com.jensoft.core.plugin.ray.RayEvent)
     */
    @Override
    public void rayClicked(RayEvent e) {
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.RayListener#rayPressed(com.jensoft.core.plugin.ray.RayEvent)
     */
    @Override
    public void rayPressed(RayEvent e) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.RayListener#rayReleased(com.jensoft.core.plugin.ray.RayEvent)
     */
    @Override
    public void rayReleased(RayEvent e) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.RayListener#rayEntered(com.jensoft.core.plugin.ray.RayEvent)
     */
    @Override
    public void rayEntered(RayEvent e) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.ray.RayListener#rayExited(com.jensoft.core.plugin.ray.RayEvent)
     */
    @Override
    public void rayExited(RayEvent e) {
    }

}
