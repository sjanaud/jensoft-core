/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d;

import java.util.EventListener;

/**
 * <code>Donut2DListener</code>
 * <p>
 * The listener interface for receiving "interesting" donut2D events (press, release, click, enter, and exit) on a
 * donut2D slice.
 * </p>
 * <p>
 * The class that is interested in processing a donut2D event either implements this interface (and all the methods it
 * contains) or extends the abstract <code>donut2DAdapter</code> class (overriding only the methods of interest).
 * </p>
 * <p>
 * The listener object created from that class is then registered with a component using the component's
 * <code>addDonut2DListener</code> method. A donut2D event is generated when the mouse is pressed, released clicked
 * (pressed and released) on a donut2D slice. A donut2D event is also generated when the mouse cursor enters or leaves a
 * slice symbol. When a donut2D slice event occurs, the relevant method in the listener object is invoked, and the
 * <code>Donut2DEvent</code> is passed to it.
 * </p>
 * 
 * @see Donut2DAdapter
 * @see Donut2DEvent
 * @author Sebastien Janaud
 */
public interface Donut2DListener extends EventListener {

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on
     * a donut2D slice.
     */
    public void donut2DSliceClicked(Donut2DEvent e);

    /**
     * Invoked when a mouse button has been pressed on a donut2D slice.
     */
    public void donut2DSlicePressed(Donut2DEvent e);

    /**
     * Invoked when a mouse button has been released on a donut2D slice.
     */
    public void donut2DSliceReleased(Donut2DEvent e);

    /**
     * Invoked when the mouse enters a donut2D slice.
     */
    public void donut2DSliceEntered(Donut2DEvent e);

    /**
     * Invoked when the mouse exits a donut2D slice.
     */
    public void donut2DSliceExited(Donut2DEvent e);

}
