/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.view;

/**
 * <code>ViewPart</code> defines view part component name.
 * 
 * @since 1.0
 * 
 * @author Sebastien Janaud
 */
public enum ViewPart {
    North, South, West, East, Device, View;

    /**
     * get device part
     * 
     * @return device window part
     */
    public static ViewPart inner() {
        return Device;
    }

    /**
     * get all parts, device, south, north, east , west
     * 
     * @return all window parts
     */
    public static ViewPart[] all() {
        ViewPart[] w = new ViewPart[5];
        w[0] = North;
        w[1] = South;
        w[2] = East;
        w[3] = West;
        w[4] = Device;
        return w;
    }

    /**
     * get view outer (north, south, east, west) parts
     * 
     * @return outer part
     */
    public static ViewPart[] outers() {
        ViewPart[] w = new ViewPart[4];
        w[0] = North;
        w[1] = South;
        w[2] = East;
        w[3] = West;
        return w;
    }
}