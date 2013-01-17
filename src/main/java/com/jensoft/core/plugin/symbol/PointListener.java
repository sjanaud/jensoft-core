/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.util.EventListener;

/**
 * The listener interface for receiving "interesting" point events (press,
 * release, click, enter, and exit) on a point symbol.
 * <P>
 * The class that is interested in processing a point event either implements this interface (and all the methods it
 * contains) or extends the abstract <code>PointAdapter</code> class (overriding only the methods of interest).
 * <P>
 * The listener object created from that class is then registered with a component using the component's
 * <code>addPointListener</code> method. A bar event is generated when the mouse is pressed, released clicked (pressed
 * and released) on a bar. A bar event is also generated when the mouse cursor enters or leaves a point symbol. When a
 * point event occurs, the relevant method in the listener object is invoked, and the <code>PointEvent</code> is passed
 * to it.
 * 
 * @see PointAdapter
 * @see PointEvent
 * @author Sebastien Janaud
 */
public interface PointListener extends EventListener {

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on
     * a point.
     */
    public void pointSymbolClicked(PointEvent e);

    /**
     * Invoked when a mouse button has been pressed on a point.
     */
    public void pointSymbolPressed(PointEvent e);

    /**
     * Invoked when a mouse button has been released on a point.
     */
    public void pointSymbolReleased(PointEvent e);

    /**
     * Invoked when the mouse enters a point.
     */
    public void pointSymbolEntered(PointEvent e);

    /**
     * Invoked when the mouse exits a point.
     */
    public void pointSymbolExited(PointEvent e);

}
