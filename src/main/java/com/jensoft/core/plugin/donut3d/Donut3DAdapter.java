/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d;

/**
 * An abstract adapter class for receiving mouse events. The methods in this
 * class are empty. This class exists as convenience for creating listener
 * objects.
 * <P>
 * Mouse events let you track when a mouse is pressed, released, clicked, moved, dragged, when it enters a donut3D slice
 * <P>
 * Extend this class to create a {@code Donut3DEvent} listener and override the methods for the events of interest. (If
 * you implement the {@code Donut3DListener}, interface, you have to define all of the methods in it. This abstract
 * class defines null methods for them all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with a donut3D plug in using the donut3D plug
 * in's {@code addDonut3DListener} methods. The relevant method in the listener object is invoked and the
 * {@code Donut3DEvent} is passed to it in following cases:
 * <p>
 * <ul>
 * <li>when a mouse button is pressed, released, or clicked (pressed and released)
 * <li>when the mouse cursor enters or exits the donut3D slice
 * </ul>
 * 
 * @see Donut3D
 * @see Donut3DSlice
 * @see Donut3DEvent
 * @see Donut3DListener
 * @author Sebastien Janaud
 */
public abstract class Donut3DAdapter implements Donut3DListener {

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.donut3d.Donut3DListener#donut3DSliceClicked(com.jensoft.sw2d.core.plugin.donut3d
     * .Donut3DEvent)
     */
    @Override
    public void donut3DSliceClicked(Donut3DEvent e) {
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.donut3d.Donut3DListener#donut3DSlicePressed(com.jensoft.sw2d.core.plugin.donut3d
     * .Donut3DEvent)
     */
    @Override
    public void donut3DSlicePressed(Donut3DEvent e) {
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.donut3d.Donut3DListener#donut3DSliceReleased(com.jensoft.sw2d.core.plugin.donut3d
     * .Donut3DEvent)
     */
    @Override
    public void donut3DSliceReleased(Donut3DEvent e) {
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.donut3d.Donut3DListener#donut3DSliceEntered(com.jensoft.sw2d.core.plugin.donut3d
     * .Donut3DEvent)
     */
    @Override
    public void donut3DSliceEntered(Donut3DEvent e) {
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.donut3d.Donut3DListener#donut3DSliceExited(com.jensoft.sw2d.core.plugin.donut3d.
     * Donut3DEvent)
     */
    @Override
    public void donut3DSliceExited(Donut3DEvent e) {
    }

}
