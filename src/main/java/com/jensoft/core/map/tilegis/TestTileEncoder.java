/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.tilegis;

import com.jensoft.core.map.tile.Tile;
import com.jensoft.core.map.tile.XMLTileEncoder;

public class TestTileEncoder {

    public TestTileEncoder() {
        try {
            GisRestOSMBridge t = new GisRestOSMBridge();
            // OSMRestBridgeEngine engine = new OSMRestBridgeEngine();
            XMLTileEncoder encoder = new XMLTileEncoder("c:/map/test");
            long t1 = System.currentTimeMillis();
            //
            // dumpTile(t14);

            Tile t15 = t.getTile(9089, 13946, 15);

            dumpTile(t15);

            Tile t16 = t.getTile(18179, 27893, 16);
            dumpTile(t16);

            Tile t17 = t.getTile(36359, 55786, 17);
            dumpTile(t17);

            encoder.encode(t15);
            encoder.encode(t16);
            encoder.encode(t17);

            long t2 = System.currentTimeMillis();

            System.out.println("time millis :" + (t2 - t1));
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TestTileEncoder gis = new TestTileEncoder();
    }

    private void dumpTile(Tile t) {
        System.out.println("Tile :" + t.toString());
        System.out.println("Highways :" + t.getHighways().size());
        System.out.println("Railways :" + t.getRailways().size());
        System.out.println("Waterways :" + t.getWaterways().size());
        System.out.println("Landuses :" + t.getLanduses().size());
        System.out.println("Leisures :" + t.getLeisures().size());
        System.out.println("Naturals :" + t.getNaturals().size());
    }
}
