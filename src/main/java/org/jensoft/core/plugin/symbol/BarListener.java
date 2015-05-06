/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol;

import java.util.EventListener;

/**
 * The listener interface for receiving "interesting" bar events (press,
 * release, click, enter, and exit) on a bar.
 * <P>
 * The class that is interested in processing a bar event either implements this interface (and all the methods it
 * contains) or extends the abstract <code>BarAdapter</code> class (overriding only the methods of interest).
 * <P>
 * The listener object created from that class is then registered with a component using the component's
 * <code>addBarListener</code> method. A bar event is generated when the mouse is pressed, released clicked (pressed and
 * released) on a bar. A bar event is also generated when the mouse cursor enters or leaves a bar symbol. When a bar
 * event occurs, the relevant method in the listener object is invoked, and the <code>BarEvent</code> is passed to it.
 * 
 * @see BarAdapter
 * @see BarEvent
 * @author Sebastien Janaud
 */
public interface BarListener extends EventListener {

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on
     * a bar.
     */
    public void barSymbolClicked(BarEvent e);

    /**
     * Invoked when a mouse button has been pressed on a bar.
     */
    public void barSymbolPressed(BarEvent e);

    /**
     * Invoked when a mouse button has been released on a bar.
     */
    public void barSymbolReleased(BarEvent e);

    /**
     * Invoked when the mouse enters a bar.
     */
    public void barSymbolEntered(BarEvent e);

    /**
     * Invoked when the mouse exits a bar.
     */
    public void barSymbolExited(BarEvent e);

}
