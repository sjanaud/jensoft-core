/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie;

import java.util.EventListener;

/**
 * The listener interface for receiving "interesting" pie events (press,
 * release, click, enter, and exit) on a pie.
 * <P>
 * The class that is interested in processing a pie event either implements this interface (and all the methods it
 * contains) or extends the abstract <code>PieAdapter</code> class (overriding only the methods of interest).
 * <P>
 * The listener object created from that class is then registered with a component using the component's
 * <code>addPieListener</code> method. A pie event is generated when the mouse is pressed, released clicked (pressed and
 * released) on a pie. A pie event is also generated when the mouse cursor enters or leaves a pie symbol. When a pie
 * event occurs, the relevant method in the listener object is invoked, and the <code>PieEvent</code> is passed to it.
 * 
 * @see PieAdapter
 * @see PieEvent
 */
public interface PieListener extends EventListener {

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on
     * a pie slice.
     */
    public void pieSliceClicked(PieEvent e);

    /**
     * Invoked when a mouse button has been pressed on a pie slice.
     */
    public void pieSlicePressed(PieEvent e);

    /**
     * Invoked when a mouse button has been released on a pie slice.
     */
    public void pieSliceReleased(PieEvent e);

    /**
     * Invoked when the mouse enters a pie slice.
     */
    public void pieSliceEntered(PieEvent e);

    /**
     * Invoked when the mouse exits a pie slice.
     */
    public void pieSliceExited(PieEvent e);

}
