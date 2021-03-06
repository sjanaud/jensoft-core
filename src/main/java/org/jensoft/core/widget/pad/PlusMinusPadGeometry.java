/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.widget.pad;

import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

/**
 * @author Sebastien Janaud
 */
/**
 * @author sebastien
 *
 */
public class PlusMinusPadGeometry extends AbstractPadGeometry {

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadGeometry#solveButtonNorthGeometry(java.awt.geom.Rectangle2D)
     */
    @Override
    void solveButtonNorthGeometry(Rectangle2D buttonNorthBound) {
        GeneralPath northButton = new GeneralPath();
        northButton.moveTo(
                           buttonNorthBound.getX() + buttonNorthBound.getWidth() / 2,
                           buttonNorthBound.getY());
        northButton.lineTo(
                           buttonNorthBound.getX() + buttonNorthBound.getWidth() / 2,
                           buttonNorthBound.getY() + buttonNorthBound.getHeight());
        northButton.moveTo(buttonNorthBound.getX(), buttonNorthBound.getY()
                + buttonNorthBound.getHeight() / 2);
        northButton.lineTo(
                           buttonNorthBound.getX() + buttonNorthBound.getWidth(),
                           buttonNorthBound.getY() + buttonNorthBound.getHeight() / 2);
        setNorthButton(northButton);

    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadGeometry#solveButtonSouthGeometry(java.awt.geom.Rectangle2D)
     */
    @Override
    void solveButtonSouthGeometry(Rectangle2D buttonSouthBound) {
        GeneralPath southButton = new GeneralPath();
        southButton.moveTo(buttonSouthBound.getX(), buttonSouthBound.getY()
                + buttonSouthBound.getHeight() / 2);
        southButton.lineTo(
                           buttonSouthBound.getX() + buttonSouthBound.getWidth(),
                           buttonSouthBound.getY() + buttonSouthBound.getHeight() / 2);
        setSouthButton(southButton);
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadGeometry#solveButtonWestGeometry(java.awt.geom.Rectangle2D)
     */
    @Override
    void solveButtonWestGeometry(Rectangle2D buttonWestBound) {
        GeneralPath westButton = new GeneralPath();
        westButton.moveTo(buttonWestBound.getX(), buttonWestBound.getY()
                + buttonWestBound.getHeight() / 2);
        westButton.lineTo(buttonWestBound.getX() + buttonWestBound.getWidth(),
                          buttonWestBound.getY() + buttonWestBound.getHeight() / 2);
        setWestButton(westButton);

    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.widget.pad.AbstractPadGeometry#solveButtonEastGeometry(java.awt.geom.Rectangle2D)
     */
    @Override
    void solveButtonEastGeometry(Rectangle2D buttonEastBound) {
        GeneralPath northButton = new GeneralPath();
        northButton.moveTo(buttonEastBound.getX() + buttonEastBound.getWidth()
                / 2, buttonEastBound.getY());
        northButton.lineTo(buttonEastBound.getX() + buttonEastBound.getWidth()
                / 2, buttonEastBound.getY() + buttonEastBound.getHeight());
        northButton.moveTo(buttonEastBound.getX(), buttonEastBound.getY()
                + buttonEastBound.getHeight() / 2);
        northButton.lineTo(buttonEastBound.getX() + buttonEastBound.getWidth(),
                           buttonEastBound.getY() + buttonEastBound.getHeight() / 2);
        setEastButton(northButton);

    }

}
