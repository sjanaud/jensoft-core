/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.tile;

public class TileKey {

    private int x;
    private int y;
    private int zoom;

    public TileKey(int x, int y, int zoom) {
        super();
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj instanceof TileKey) {
            TileKey tk = (TileKey) obj;
            if (tk.getX() == x && tk.getY() == y && tk.getZoom() == zoom) {
                return true;
            }
        }
        return false;
    }

    public String getKey() {
        return x + "_" + y + "_" + zoom;
    }

    @Override
    public String toString() {
        return "Tile :[" + getKey() + "]";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

}
