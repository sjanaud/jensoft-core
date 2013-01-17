/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray;

import java.util.EventListener;

/**
 * The listener interface for receiving "interesting" ray events (press,
 * release, click, enter, and exit) on a ray.
 * <p>
 * The class that is interested in processing a ray event either implements this interface (and all the methods it
 * contains) or extends the abstract <code>RayAdapter</code> class (overriding only the methods of interest).
 * </p>
 * The listener object created from that class is then registered with a
 * component using the component's <code>addRayListener</code> method. A ray
 * event is generated when the mouse is pressed, released clicked (pressed and
 * released) on a ray. A ray event is also generated when the mouse cursor
 * enters or leaves a ray. When a ray event occurs, the relevant method in the
 * listener object is invoked, and the <code>RayEvent</code> is passed to it.
 * 
 * @see RayAdapter
 * @see RayEvent
 */
public interface RayListener extends EventListener {

    /**
     * Invoked when the ray button has been clicked (pressed and released) on a
     * ray.
     */
    public void rayClicked(RayEvent e);

    /**
     * Invoked when a mouse button has been pressed on a ray.
     */
    public void rayPressed(RayEvent e);

    /**
     * Invoked when a mouse button has been released on a ray.
     */
    public void rayReleased(RayEvent e);

    /**
     * Invoked when the mouse enters a ray.
     */
    public void rayEntered(RayEvent e);

    /**
     * Invoked when the mouse exits a ray.
     */
    public void rayExited(RayEvent e);

}
