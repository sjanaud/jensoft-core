/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.tile;

import java.util.HashMap;
import java.util.Map;

public class TileCache {

    private Map<String, Tile> tilemap = new HashMap<String, Tile>();
    private int cacheSize = 0;

    public TileCache() {

    }

    public void addToCache(Tile t) {

        tilemap.put(t.getKey().getKey(), t);
    }

    public Tile getTile(TileKey key) {

        Tile t = tilemap.get(key.getKey());
        return t;
    }

    public int countCacheTile() {
        return tilemap.size();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        Tile t1 = new Tile(12, 17, 12);
        Tile t2 = new Tile(12, 20, 12);

        TileCache cache = new TileCache();
        cache.addToCache(t1);
        cache.addToCache(t2);

        System.out.println("stop");

        Tile tc1 = cache.getTile(t1.getKey());
        System.out.println(tc1);

        Tile tc2 = cache.getTile(new TileKey(12, 20, 12));
        System.out.println(tc2);
        System.out.println("size :" + cache.countCacheTile());

        System.out.println("stop");

    }

}
