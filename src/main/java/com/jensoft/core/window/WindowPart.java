/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.window;

/**
 * defines window part component
 * 
 * @author Sebastien Janaud
 */
public enum WindowPart {
    North, South, West, East, Device;

    /**
     * get device part
     * 
     * @return device window part
     */
    public static WindowPart getIn() {
        return Device;
    }

    /**
     * get all parts, device, south, north, east , west
     * 
     * @return all window parts
     */
    public static WindowPart[] getAll() {
        WindowPart[] w = new WindowPart[5];
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
    public static WindowPart[] getOut() {
        WindowPart[] w = new WindowPart[4];
        w[0] = North;
        w[1] = South;
        w[2] = East;
        w[3] = West;
        return w;
    }
}