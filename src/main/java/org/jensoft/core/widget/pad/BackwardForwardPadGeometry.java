/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.widget.pad;

import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

/**
 * BackwardForwardPadGeometry is a pad with button backward and forward
 * 
 * @author Sebastien Janaud
 */
public class BackwardForwardPadGeometry extends AbstractPadGeometry {

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadGeometry#solveButtonNorthGeometry(java.awt.geom.Rectangle2D)
     */
    @Override
    void solveButtonNorthGeometry(Rectangle2D buttonNorthBound) {
        GeneralPath northButton = new GeneralPath();
        northButton.moveTo(buttonNorthBound.getX(), buttonNorthBound.getY()
                + buttonNorthBound.getHeight());
        northButton.lineTo(
                           buttonNorthBound.getX() + buttonNorthBound.getWidth() / 2,
                           buttonNorthBound.getY());
        northButton.lineTo(
                           buttonNorthBound.getX() + buttonNorthBound.getWidth(),
                           buttonNorthBound.getY() + buttonNorthBound.getHeight());
        northButton.closePath();
        setNorthButton(northButton);
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadGeometry#solveButtonSouthGeometry(java.awt.geom.Rectangle2D)
     */
    @Override
    void solveButtonSouthGeometry(Rectangle2D buttonSouthBound) {
        GeneralPath southButton = new GeneralPath();
        southButton.moveTo(buttonSouthBound.getX(), buttonSouthBound.getY());
        southButton.lineTo(
                           buttonSouthBound.getX() + buttonSouthBound.getWidth() / 2,
                           buttonSouthBound.getY() + buttonSouthBound.getHeight());
        southButton.lineTo(
                           buttonSouthBound.getX() + buttonSouthBound.getWidth(),
                           buttonSouthBound.getY());
        southButton.closePath();
        setSouthButton(southButton);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadGeometry#solveButtonWestGeometry(java.awt.geom.Rectangle2D)
     */
    @Override
    void solveButtonWestGeometry(Rectangle2D buttonWestBound) {
        GeneralPath westButton = new GeneralPath();
        westButton.moveTo(buttonWestBound.getX(), buttonWestBound.getY()
                + buttonWestBound.getHeight() / 2);
        westButton.lineTo(buttonWestBound.getX() + buttonWestBound.getWidth(),
                          buttonWestBound.getY());
        westButton.lineTo(buttonWestBound.getX() + buttonWestBound.getWidth(),
                          buttonWestBound.getY() + buttonWestBound.getHeight());
        westButton.closePath();
        setWestButton(westButton);
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.pad.AbstractPadGeometry#solveButtonEastGeometry(java.awt.geom.Rectangle2D)
     */
    @Override
    void solveButtonEastGeometry(Rectangle2D buttonEastBound) {
        GeneralPath eastButton = new GeneralPath();
        eastButton.moveTo(buttonEastBound.getX(), buttonEastBound.getY());
        eastButton.lineTo(buttonEastBound.getX() + buttonEastBound.getWidth(),
                          buttonEastBound.getY() + buttonEastBound.getHeight() / 2);
        eastButton.lineTo(buttonEastBound.getX(), buttonEastBound.getY()
                + buttonEastBound.getHeight());
        eastButton.closePath();
        setEastButton(eastButton);

    }

}
