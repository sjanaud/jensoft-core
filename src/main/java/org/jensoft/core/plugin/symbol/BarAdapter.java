/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol;

/**
 * An abstract adapter class for receiving mouse events. The methods in this
 * class are empty. This class exists as convenience for creating listener
 * objects.
 * <P>
 * Mouse events let you track when a mouse is pressed, released, clicked, moved, dragged, when it enters a bar
 * <P>
 * Extend this class to create a {@code BarEvent} listener and override the methods for the events of interest. (If you
 * implement the {@code BarListener} , interface, you have to define all of the methods in it. This abstract class
 * defines null methods for them all, so you can only have to define methods for events you care about.)
 * <P>
 * Create a listener object using the extended class and then register it with a bar plug in using the bar plug in's
 * {@code addBarListener} methods. The relevant method in the listener object is invoked and the {@code BarEvent} is
 * passed to it in following cases:
 * <p>
 * <ul>
 * <li>when a mouse button is pressed, released, or clicked (pressed and released)
 * <li>when the mouse cursor enters or exits the bar
 * </ul>
 * 
 * @see BarEvent
 * @see BarListener
 * @author Sebastien Janaud
 */
public abstract class BarAdapter implements BarListener {

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.BarListener#barSymbolClicked(com.jensoft.core.plugin.symbol.BarEvent)
     */
    @Override
    public void barSymbolClicked(BarEvent e) {
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.BarListener#barSymbolPressed(com.jensoft.core.plugin.symbol.BarEvent)
     */
    @Override
    public void barSymbolPressed(BarEvent e) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.BarListener#barSymbolReleased(com.jensoft.core.plugin.symbol.BarEvent)
     */
    @Override
    public void barSymbolReleased(BarEvent e) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.BarListener#barSymbolEntered(com.jensoft.core.plugin.symbol.BarEvent)
     */
    @Override
    public void barSymbolEntered(BarEvent e) {
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.BarListener#barSymbolExited(com.jensoft.core.plugin.symbol.BarEvent)
     */
    @Override
    public void barSymbolExited(BarEvent e) {
    }

}
