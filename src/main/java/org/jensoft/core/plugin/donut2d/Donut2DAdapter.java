/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d;

/**
 * <code>Donut2DAdapter</code>
 * <p>
 * An abstract adapter class for receiving mouse events. The methods in this class are empty. This class exists as
 * convenience for creating listener objects.
 * </p>
 * <p>
 * Mouse events let you track when a mouse is pressed, released, clicked, moved, dragged, when it enters a donut2D slice
 * </p>
 * <p>
 * Extend this class to create a {@code Donut2DEvent} listener and override the methods for the events of interest. (If
 * you implement the {@code Donut2DListener}, interface, you have to define all of the methods in it. This abstract
 * class defines null methods for them all, so you can only have to define methods for events you care about.)
 * </p>
 * <p>
 * Create a listener object using the extended class and then register it with a donut3D plug in using the donut3D plug
 * in's {@code addDonut3DListener} methods. The relevant method in the listener object is invoked and the
 * {@code Donut2DEvent} is passed to it in following cases:
 * </p>
 * <p>
 * <ul>
 * <li>when a mouse button is pressed, released, or clicked (pressed and released)
 * <li>when the mouse cursor enters or exits the donut2D slice
 * </ul>
 * </p>
 * 
 * @see Donut2D
 * @see Donut2DSlice
 * @see Donut2DEvent
 * @see Donut2DListener
 * @author Sebastien Janaud
 */
public abstract class Donut2DAdapter implements Donut2DListener {

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.Donut2DListener#donut2DSliceClicked(org.jensoft.core.plugin.donut2d.Donut2DEvent)
     */
    @Override
    public void donut2DSliceClicked(Donut2DEvent e) {
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.Donut2DListener#donut2DSlicePressed(org.jensoft.core.plugin.donut2d.Donut2DEvent)
     */
    @Override
    public void donut2DSlicePressed(Donut2DEvent e) {
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.Donut2DListener#donut2DSliceReleased(org.jensoft.core.plugin.donut2d.Donut2DEvent)
     */
    @Override
    public void donut2DSliceReleased(Donut2DEvent e) {
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.Donut2DListener#donut2DSliceEntered(org.jensoft.core.plugin.donut2d.Donut2DEvent)
     */
    @Override
    public void donut2DSliceEntered(Donut2DEvent e) {
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.donut2d.Donut2DListener#donut2DSliceExited(org.jensoft.core.plugin.donut2d.Donut2DEvent)
     */
    @Override
    public void donut2DSliceExited(Donut2DEvent e) {
    }

}
