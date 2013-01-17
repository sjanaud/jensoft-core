/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d;

import java.util.EventListener;

/**
 * The listener interface for receiving "interesting" donut3D events (press,
 * release, click, enter, and exit) on a donut3D slice.
 * <P>
 * The class that is interested in processing a donut3D event either implements this interface (and all the methods it
 * contains) or extends the abstract <code>Donut3DAdapter</code> class (overriding only the methods of interest).
 * <P>
 * The listener object created from that class is then registered with a component using the component's
 * <code>addDonut3DListener</code> method. A donut3D event is generated when the mouse is pressed, released clicked
 * (pressed and released) on a donut3D slice. A donut3D event is also generated when the mouse cursor enters or leaves a
 * slice symbol. When a donut3D slice event occurs, the relevant method in the listener object is invoked, and the
 * <code>Donut3DEvent</code> is passed to it.
 * 
 * @see Donut3DAdapter
 * @see Donut3DEvent
 * @author Sebastien Janaud
 */
public interface Donut3DListener extends EventListener {

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on
     * a donut3d slice.
     */
    public void donut3DSliceClicked(Donut3DEvent e);

    /**
     * Invoked when a mouse button has been pressed on a donut3d slice.
     */
    public void donut3DSlicePressed(Donut3DEvent e);

    /**
     * Invoked when a mouse button has been released on a donut3d slice.
     */
    public void donut3DSliceReleased(Donut3DEvent e);

    /**
     * Invoked when the mouse enters a donut3d slice.
     */
    public void donut3DSliceEntered(Donut3DEvent e);

    /**
     * Invoked when the mouse exits a donut3d slice.
     */
    public void donut3DSliceExited(Donut3DEvent e);

}
