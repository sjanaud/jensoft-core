/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view;

import java.awt.Color;

/**
 * ViewToolkit
 * 
 * @author Sebastien Janaud
 */
public class ViewToolkit {

    /**
     * create empty view
     * 
     * @return view
     */
    public static View2D createView() {
        View2D view2D = new View2D();
        return view2D;
    }

    /**
     * create view with specified parameters
     * 
     * @param east
     *            east width
     * @param west
     *            west width
     * @param north
     *            north height
     * @param south
     *            south height
     * @return view
     */
    public static View2D createView(int east, int west, int north, int south) {
        View2D view2D = new View2D();
        view2D.setPlaceHolderAxisNorth(north);
        view2D.setPlaceHolderAxisSouth(south);
        view2D.setPlaceHolderAxisWest(east);
        view2D.setPlaceHolderAxisEast(west);
        return view2D;
    }

    /**
     * create view with specified parameters
     * 
     * @param placeHolder
     *            window border size
     * @return view
     */
    public static View2D createView(int placeHolder) {
        View2D view2D = new View2D();
        view2D.setPlaceHolderAxisNorth(placeHolder);
        view2D.setPlaceHolderAxisSouth(placeHolder);
        view2D.setPlaceHolderAxisWest(placeHolder);
        view2D.setPlaceHolderAxisEast(placeHolder);
        return view2D;
    }

    /**
     * create view with specified parameters
     * 
     * @param placeHolder
     *            window border size
     * @param background
     *            view background color
     * @return view
     */
    public static View2D createView(int placeHolder, Color background) {
        View2D view2D = new View2D();
        view2D.setPlaceHolderAxisNorth(placeHolder);
        view2D.setPlaceHolderAxisSouth(placeHolder);
        view2D.setPlaceHolderAxisWest(placeHolder);
        view2D.setPlaceHolderAxisEast(placeHolder);
        view2D.setBackground(background);
        return view2D;
    }

}
