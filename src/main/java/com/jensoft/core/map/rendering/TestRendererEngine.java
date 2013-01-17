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
import com.jensoft.core.map.tile.XMLTileEncoder;

public class TestRendererEngine {

    private RendererEngine engine;
    private OSMRestBridgeEngine osmRestBridge;

    public TestRendererEngine() {
        engine = new RendererEngine();
        osmRestBridge = new OSMRestBridgeEngine();
    }

    public void createTotalPauMapD15__() {
        GeoPosition centerWindow = new GeoPosition(43.31793, -0.311088);
        int zoom = 17;
        int centerXIndex = MapUtil.longToX(centerWindow.getLongitude(), zoom);
        int centerYIndex = MapUtil.latToY(centerWindow.getLatitude(), zoom);

        Stream s = osmRestBridge.streamTile(centerXIndex, centerYIndex, 17);

    }

    public void createTotalPauMapD15() {
        // GeoPosition centerWindow = new GeoPosition(43.318637,-0.312383);
        // z = 15

        int zoom = 15;
        int centerXIndex = 16355;
        int centerYIndex = 12000;

        int tileDelta = 2;
        int startX = centerXIndex - tileDelta;
        int endX = centerXIndex + tileDelta;
        int startY = centerYIndex - tileDelta;
        int endY = centerYIndex + tileDelta;

        for (int x = startX; x <= endX; x++) {

            for (int y = startY; y <= endY; y++) {
                // System.out.println("STREAM Y ROW : "+y);
                Stream s = osmRestBridge.streamTile(x, y, 15);

                engine.getHighwayLayer().registerHighways(
                                                          MapObjectToolkit.createHighways(s));
                engine.getNaturalLayer().registerNatural(
                                                         MapObjectToolkit.createNatural(s));
                engine.getLeisureLayer().registerLeisures(
                                                          MapObjectToolkit.createLeisures(s));
                engine.getLanduseLayer().registerLanduses(
                                                          MapObjectToolkit.createLanduses(s));
                engine.getRailwayLayer().registerRailways(
                                                          MapObjectToolkit.createRailways(s));
                engine.getManmadeLayer().registerManMades(
                                                          MapObjectToolkit.createManMade(s));

            }
        }

        try {

            String mapRootDirectoryT256 = "c:/usr/map-total-pau/D15/T256";
            String mapRootTileDirectoryT256 = mapRootDirectoryT256
                    + File.separator + "tile";
            new File(mapRootDirectoryT256).mkdirs();
            new File(mapRootTileDirectoryT256).mkdirs();
            Map2D map2D_15_256 = engine.createMapD15_256(startX, endX, startY,
                                                         endY);
            map2D_15_256.writeMap(mapRootDirectoryT256,
                                  "laboratoire-total-pau-T256.png");
            map2D_15_256.writeTiles(mapRootTileDirectoryT256);

            String mapRootDirectoryT128 = "c:/usr/map-total-pau/D15/T128";
            String mapRootTileDirectoryT128 = mapRootDirectoryT128
                    + File.separator + "tile";
            new File(mapRootDirectoryT128).mkdirs();
            new File(mapRootTileDirectoryT128).mkdirs();
            Map2D map2D_15_128 = engine.createMapD15_128(startX, endX, startY,
                                                         endY);
            map2D_15_128.writeMap(mapRootDirectoryT128,
                                  "laboratoire-total-pau-T128.png");
            map2D_15_128.writeTiles(mapRootTileDirectoryT128);

        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void createTotalPauMapD17() {

        // pau totla centre du laboratoire
        GeoPosition centerWindow = new GeoPosition(43.318637, -0.312383);

        int zoom = 17;
        int centerXIndex = MapUtil.longToX(centerWindow.getLongitude(), zoom);
        int centerYIndex = MapUtil.latToY(centerWindow.getLatitude(), zoom);

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

                engine.getHighwayLayer().registerHighways(highways);
                engine.getNaturalLayer().registerNatural(naturals);
                engine.getLeisureLayer().registerLeisures(leisures);
                engine.getLanduseLayer().registerLanduses(landuses);
                engine.getRailwayLayer().registerRailways(railways);
                engine.getManmadeLayer().registerManMades(manmades);

            }
        }

        try {

            String mapRootDirectoryT256 = "c:/usr/map-total-pau/D17/T256";
            String mapRootTileDirectoryT256 = mapRootDirectoryT256
                    + File.separator + "tile";
            new File(mapRootDirectoryT256).mkdirs();
            new File(mapRootTileDirectoryT256).mkdirs();
            Map2D map2D_17_256 = engine.createMapD17_256(startX, endX, startY,
                                                         endY);
            map2D_17_256.writeMap(mapRootDirectoryT256,
                                  "laboratoire-total-pau-T256.png");
            map2D_17_256.writeTiles(mapRootTileDirectoryT256);

            String mapRootDirectoryT128 = "c:/usr/map-total-pau/D17/T128";
            String mapRootTileDirectoryT128 = mapRootDirectoryT128
                    + File.separator + "tile";
            new File(mapRootDirectoryT128).mkdirs();
            new File(mapRootTileDirectoryT128).mkdirs();
            Map2D map2D_17_128 = engine.createMapD17_128(startX, endX, startY,
                                                         endY);
            map2D_17_128.writeMap(mapRootDirectoryT128,
                                  "laboratoire-total-pau-T128.png");
            map2D_17_128.writeTiles(mapRootTileDirectoryT128);

            String mapTileDirectory = "c:/usr/map-total-pau/tiles";
            XMLTileEncoder encoder = new XMLTileEncoder(mapTileDirectory);

            for (Tile tile : tiles) {
                encoder.encode(tile);
            }

        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void test1() {

        int startX = 65322;
        int endX = 65326;
        int startY = 47231;
        int endY = 47233;

        // for (int x = startX; x <= endX; x++) {
        // //System.out.println("STREAM X COL : "+x);
        // for (int y = startY; y <= endY; y++) {
        // //System.out.println("STREAM Y ROW : "+y);
        // Stream s = osmRestBridge.streamTile(x, y,17);
        //
        // engine.getHighwayLayer().registerHighways(MapObjectFactory.createHighways(s));
        // engine.getNaturalLayer().registerNatural(MapObjectFactory.createNatural(s));
        // engine.getLeisureLayer().registerLeisures(MapObjectFactory.createLeisures(s));
        // engine.getLanduseLayer().registerLanduses(MapObjectFactory.createLanduses(s));
        // engine.getRailwayLayer().registerRailways(MapObjectFactory.createRailways(s));
        // engine.getManmadeLayer().registerManMades(MapObjectFactory.createManMade(s));
        //
        // }
        // }
        //
        // Map2D map2D_17 = engine.createMapD17_256(startX, endX, startY, endY);
        //
        // try {
        // map2D_17.writeMap("c:/usr/rendering2", "level_17.png");
        // map2D_17.writeTiles("c:/usr/rendering2/tile");
        // } catch (IOException e) {
        //
        // e.printStackTrace();
        // }

        startX = 9086;
        endX = 9092;
        startY = 13944;
        endY = 13948;

        // cf bug de coastline
        // startX = 17036;
        // endX = 17038;
        // startY = 11955;
        // endY = 11957;

        for (int x = startX; x <= endX; x++) {

            for (int y = startY; y <= endY; y++) {
                // System.out.println("STREAM Y ROW : "+y);
                Stream s = osmRestBridge.streamTile(x, y, 15);

                engine.getHighwayLayer().registerHighways(
                                                          MapObjectToolkit.createHighways(s));
                engine.getNaturalLayer().registerNatural(
                                                         MapObjectToolkit.createNatural(s));
                engine.getLeisureLayer().registerLeisures(
                                                          MapObjectToolkit.createLeisures(s));
                engine.getLanduseLayer().registerLanduses(
                                                          MapObjectToolkit.createLanduses(s));
                engine.getRailwayLayer().registerRailways(
                                                          MapObjectToolkit.createRailways(s));

            }
        }

        try {
            Map2D map2D_15_256 = engine.createMapD15_256(startX, endX, startY,
                                                         endY);
            map2D_15_256.writeMap("c:/usr/rendering3/D15/T256",
                                  "saint_laurent_var_level_15_256.png");
            map2D_15_256.writeTiles("c:/usr/rendering3/D15/T256/tile");

            Map2D map2D_15_128 = engine.createMapD15_128(startX, endX, startY,
                                                         endY);
            map2D_15_128.writeMap("c:/usr/rendering2/D15/T128",
                                  "level_15_128.png");
            map2D_15_128.writeTiles("c:/usr/rendering2/D15/T128/tile");

            Map2D map2D_15_128_highways = engine.createMapD15_128_HIGHWAYS(
                                                                           startX, endX, startY, endY);
            map2D_15_128_highways.writeMap("c:/usr/rendering3/D15/T128",
                                           "level_15_128_HIGHWAYS.png");
            map2D_15_128_highways
                    .writeTiles("c:/usr/rendering3/D15/T128/tile_highways");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        TestRendererEngine testEngine = new TestRendererEngine();

        // testEngine.createTotalPauMapD17();
        //testEngine.createTotalPauMapD15();
        testEngine.test1();
        

    }

}
