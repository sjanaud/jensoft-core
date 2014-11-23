/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.rendering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.layer.highway.Highway;
import com.jensoft.core.map.layer.landuse.Landuse;
import com.jensoft.core.map.layer.leisure.Leisure;
import com.jensoft.core.map.layer.manmade.ManMade;
import com.jensoft.core.map.layer.natural.Natural;
import com.jensoft.core.map.layer.railway.Railway;
import com.jensoft.core.map.layer.waterway.Waterway;
import com.jensoft.core.map.primitive.Stream;
import com.jensoft.core.map.projection.GeoPosition;
import com.jensoft.core.map.projection.Map2D;
import com.jensoft.core.map.projection.MapUtil;
import com.jensoft.core.map.restbridge.OSMRestBridgeEngine;
import com.jensoft.core.map.tile.Tile;

public class TotalPauEngine {

    private RendererEngine engine;
    private OSMRestBridgeEngine osmRestBridge;

    public TotalPauEngine() {
        engine = new RendererEngine();
        osmRestBridge = new OSMRestBridgeEngine();
    }

    public void createTotalPauMap() {

        // 1 - Stream Data on zoom 17, all data are fetch ok at this zoom

        // pau total centre du laboratoire
        GeoPosition centerProjection = new GeoPosition(43.318637, -0.312383);

        int zoom = 17;
        int centerXIndex = MapUtil.longToX(centerProjection.getLongitude(), zoom);
        int centerYIndex = MapUtil.latToY(centerProjection.getLatitude(), zoom);

        List<Tile> tiles = new ArrayList<Tile>();

        int tileDelta = 2;
        int startX = centerXIndex - tileDelta;
        int endX = centerXIndex + tileDelta;
        int startY = centerYIndex - tileDelta;
        int endY = centerYIndex + tileDelta;

        for (int x = startX; x <= endX; x++) {

            for (int y = startY; y <= endY; y++) {

                Stream s = osmRestBridge.streamTile(x, y, zoom);

                Tile tile = new Tile(x, y, zoom);
                tiles.add(tile);

                List<Highway> highways = MapObjectToolkit.createHighways(s);
                List<Natural> naturals = MapObjectToolkit.createNatural(s);
                List<Leisure> leisures = MapObjectToolkit.createLeisures(s);
                List<Landuse> landuses = MapObjectToolkit.createLanduses(s);
                List<Railway> railways = MapObjectToolkit.createRailways(s);
                List<ManMade> manmades = MapObjectToolkit.createManMade(s);
                List<Waterway> waterways = MapObjectToolkit.createWaterways(s);

                tile.setHighways(highways);
                tile.setRailways(railways);
                tile.setWaterways(waterways);
                tile.setLanduses(landuses);
                tile.setLeisures(leisures);
                tile.setNaturals(naturals);
                tile.setManmades(manmades);

                // engine.getHighwayLayer().registerHighways(highways);
                // engine.getNaturalLayer().registerNatural(naturals);
                // engine.getLeisureLayer().registerLeisures(leisures);
                // engine.getLanduseLayer().registerLanduses(landuses);
                // engine.getRailwayLayer().registerRailways(railways);
                engine.getManmadeLayer().registerManMades(manmades);

            }
        }

        // String mapTileDirectory = "c:/usr/map-total-pau/tiles";
        // XMLTileEncoder encoder = new XMLTileEncoder(mapTileDirectory);
        //
        // for (Tile tile : tiles) {
        // encoder.encode(tile);
        // }

        try {

            String mapRootDirectoryT256 = "c:/usr/map-total-pau";
            new File(mapRootDirectoryT256).mkdirs();

            // 17
            int level = 17;
            int x = MapUtil.longToX(centerProjection.getLongitude(), level);
            int y = MapUtil.latToY(centerProjection.getLatitude(), level);
            int delta = 2;

            Map2D map2D_17_256 = engine.createMapD17_256(x - delta, x + delta,
                                                         y - delta, y + delta);
            map2D_17_256.writeMap(mapRootDirectoryT256,
                                  "laboratoire-total-pau-17.png");
            map2D_17_256.writeTiles(mapRootDirectoryT256);

            // //18
            level = 18;
            x = MapUtil.longToX(centerProjection.getLongitude(), level);
            y = MapUtil.latToY(centerProjection.getLatitude(), level);
            delta = 4;

            Map2D map2D_18_256 = engine.createMapD18_256(x - delta, x + delta,
                                                         y - delta, y + delta);
            map2D_18_256.writeMap(mapRootDirectoryT256,
                                  "laboratoire-total-pau-18.png");
            map2D_18_256.writeTiles(mapRootDirectoryT256);

            // 19
            level = 19;
            x = MapUtil.longToX(centerProjection.getLongitude(), level);
            y = MapUtil.latToY(centerProjection.getLatitude(), level);
            delta = 8;

            Map2D map2D_19_256 = engine.createMapD19_256(x - delta, x + delta,
                                                         y - delta, y + delta);
            map2D_19_256.writeMap(mapRootDirectoryT256,
                                  "laboratoire-total-pau-19.png");
            map2D_19_256.writeTiles(mapRootDirectoryT256);

            // 20
            level = 20;
            x = MapUtil.longToX(centerProjection.getLongitude(), level);
            y = MapUtil.latToY(centerProjection.getLatitude(), level);
            delta = 16;

            Map2D map2D_20_256 = engine.createMapD20_256(x - delta, x + delta,
                                                         y - delta, y + delta);
            map2D_20_256.writeMap(mapRootDirectoryT256,
                                  "laboratoire-total-pau-20.png");
            map2D_20_256.writeTiles(mapRootDirectoryT256);

            // 21
            level = 21;
            x = MapUtil.longToX(centerProjection.getLongitude(), level);
            y = MapUtil.latToY(centerProjection.getLatitude(), level);
            delta = 14;

            Map2D map2D_21_256 = engine.createMapD21_256(x - delta, x + delta,
                                                         y - delta, y + delta);
            // map2D_21_256.writeMap(mapRootDirectoryT256,
            // "laboratoire-total-pau-21-part1.png");
            map2D_21_256.writeTiles(mapRootDirectoryT256);

            // Map2D map2D_21_256_Q1 = engine.createMapD21_256(x, x+delta, y,
            // y+delta);
            // //map2D_21_256.writeMap(mapRootDirectoryT256,
            // "laboratoire-total-pau-21-part1.png");
            // map2D_21_256_Q1.writeTiles(mapRootDirectoryT256);
            //
            // //21 bis
            // Map2D map2D_21_256_Q2 = engine.createMapD21_256(x-delta, x,
            // y-delta, y);
            // //map2D_21_256_bis.writeMap(mapRootDirectoryT256,
            // "laboratoire-total-pau-21-part2.png");
            // map2D_21_256_Q2.writeTiles(mapRootDirectoryT256);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TotalPauEngine testEngine = new TotalPauEngine();

        testEngine.createTotalPauMap();

    }

}
