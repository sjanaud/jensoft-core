/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.rendering;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.map.layer.AbstractMapLayer;
import org.jensoft.core.map.layer.background.BackgroundLayer;
import org.jensoft.core.map.layer.highway.GroupRenderingProperties;
import org.jensoft.core.map.layer.highway.HighwayGroup;
import org.jensoft.core.map.layer.highway.HighwayLayer;
import org.jensoft.core.map.layer.highway.HighwayNature;
import org.jensoft.core.map.layer.landuse.LanduseLayer;
import org.jensoft.core.map.layer.leisure.LeisureLayer;
import org.jensoft.core.map.layer.manmade.ManMadeLayer;
import org.jensoft.core.map.layer.natural.NaturalLayer;
import org.jensoft.core.map.layer.railway.RailwayLayer;
import org.jensoft.core.map.layer.waterway.WaterwayLayer;
import org.jensoft.core.map.primitive.Stream;
import org.jensoft.core.map.projection.DalleProjection;
import org.jensoft.core.map.projection.Map2D;
import org.jensoft.core.map.projection.MapUtil;
import org.jensoft.core.map.restbridge.OSMRestBridgeEngine;

public class BordeauxRendedering {

    /**OSM rest bridge*/
    private OSMRestBridgeEngine osmRestBridge;
    
    /** map */
    private Map2D map2D;

    /** all map layers */
    private List<AbstractMapLayer> layers;
    
    //place gambetta
    private static double longitude = -0.580577;
    private static double latitude = 44.841229;
    private static int zoom = 18;

    /** layers */
    private BackgroundLayer backgroundLayer;
    private HighwayLayer highwayLayer;
    private NaturalLayer naturalLayer;
    private LeisureLayer leisureLayer;
    private LanduseLayer landuseLayer;
    private RailwayLayer railwayLayer;
    private ManMadeLayer manmadeLayer;
    private WaterwayLayer waterwayLayer;

    public BordeauxRendedering() {
        osmRestBridge = new OSMRestBridgeEngine();
    }

    /**
     * lookup way from OSM for the given index
     * @param startX
     * @param endX
     * @param startY
     * @param endY
     */
    private void lookupOSM(int startX, int endX, int startY, int endY) {
        System.out.println("stream count:+" + (endX - startX) * (endY - startY));
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                Stream s = osmRestBridge.streamTile(x, y, 18);

                highwayLayer.registerHighways(
                        MapObjectToolkit.createHighways(s));
                // engine.getNaturalLayer().registerNatural(
                // MapObjectFactory.createNatural(s));
                leisureLayer.registerLeisures(
                        MapObjectToolkit.createLeisures(s));
                landuseLayer.registerLanduses(
                        MapObjectToolkit.createLanduses(s));
                railwayLayer.registerRailways(
                        MapObjectToolkit.createRailways(s));
                manmadeLayer.registerManMades(
                        MapObjectToolkit.createManMade(s));

            }
        }
    }

    /**
     * create a map 2D for bordeaux rendering
     * 
     * @param startX
     * @param endX
     * @param startY
     * @param endY
     * @return bordeaux map
     */
    public Map2D createMap(int startX, int endX, int startY, int endY) {
        DalleProjection proj = new DalleProjection(zoom, 256);
        map2D = proj.createMap2D(startX, endX, startY, endY);

        backgroundLayer = new BackgroundLayer();
        highwayLayer = new HighwayLayer();
        naturalLayer = new NaturalLayer();
        leisureLayer = new LeisureLayer();
        landuseLayer = new LanduseLayer();
        railwayLayer = new RailwayLayer();
        manmadeLayer = new ManMadeLayer();
        waterwayLayer = new WaterwayLayer();

        layers = new ArrayList<AbstractMapLayer>();
        registerLayer(backgroundLayer);
        registerLayer(leisureLayer);
        registerLayer(landuseLayer);
        registerLayer(manmadeLayer);
        registerLayer(highwayLayer);
        registerLayer(railwayLayer);
        registerLayer(waterwayLayer);

        return map2D;
    }

    /**
     * example of map and tile generation generation
     */
    public void generateMap() {
        try {
            for (AbstractMapLayer layer : layers) {
                map2D.paint(layer);
            }

            String mapRootDirectoryT256 = "c:/usr/map-bordeaux/D18/T256";
            String mapRootTileDirectoryT256 = mapRootDirectoryT256
                    + File.separator + "tile";
            new File(mapRootDirectoryT256).mkdirs();
            new File(mapRootTileDirectoryT256).mkdirs();
            map2D.writeMap(mapRootDirectoryT256, "bordeaux-T256.png");
            map2D.writeTiles(mapRootTileDirectoryT256);
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void configureMapRendering() {
        // configure map layers for this level
        HighwayGroup residentialGroup = highwayLayer
                .getGroup(HighwayNature.RESIDENTIAL);
        GroupRenderingProperties residentialProperties = new GroupRenderingProperties(
                                                                                      14, Color.WHITE);
        residentialGroup.setRenderingProperties(residentialProperties);

        HighwayGroup unclassifiedGroup = highwayLayer
                .getGroup(HighwayNature.UNCLASSIFIED);
        GroupRenderingProperties unclassifiedProperties = new GroupRenderingProperties(
                                                                                       14, Color.WHITE);
        unclassifiedGroup.setRenderingProperties(unclassifiedProperties);

        HighwayGroup roadGroup = highwayLayer.getGroup(HighwayNature.ROAD);
        GroupRenderingProperties roadProperties = new GroupRenderingProperties(
                                                                               14, Color.WHITE);
        roadGroup.setRenderingProperties(roadProperties);

        HighwayGroup primaryGroup = highwayLayer
                .getGroup(HighwayNature.PRIMARY);
        GroupRenderingProperties primaryProperties = new GroupRenderingProperties(
                                                                                  14, Color.WHITE);
        primaryGroup.setRenderingProperties(primaryProperties);

        HighwayGroup secondaryGroup = highwayLayer
                .getGroup(HighwayNature.SECONDARY);
        GroupRenderingProperties secondaryProperties = new GroupRenderingProperties(
                                                                                    14, Color.WHITE);
        secondaryGroup.setRenderingProperties(secondaryProperties);

        HighwayGroup tertiaryGroup = highwayLayer
                .getGroup(HighwayNature.TERTIARY);
        GroupRenderingProperties tertiaryProperties = new GroupRenderingProperties(
                                                                                   14, Color.WHITE);
        tertiaryGroup.setRenderingProperties(tertiaryProperties);

        HighwayGroup serviceGroup = highwayLayer
                .getGroup(HighwayNature.SERVICE);
        GroupRenderingProperties serviceProperties = new GroupRenderingProperties(
                                                                                  6, Color.WHITE);
        serviceGroup.setRenderingProperties(serviceProperties);

        HighwayGroup motorwayGroup = highwayLayer
                .getGroup(HighwayNature.MOTORWAY);
        GroupRenderingProperties motorwayProperties = new GroupRenderingProperties(
                                                                                   14, Color.WHITE);
        motorwayGroup.setRenderingProperties(motorwayProperties);

        HighwayGroup motorwayLinkGroup = highwayLayer
                .getGroup(HighwayNature.MOTORWAY_LINK);
        GroupRenderingProperties motorwayLinkProperties = new GroupRenderingProperties(
                                                                                       14, Color.WHITE);
        motorwayLinkGroup.setRenderingProperties(motorwayLinkProperties);

        HighwayGroup trunkGroup = highwayLayer.getGroup(HighwayNature.TRUNK);
        GroupRenderingProperties trunkProperties = new GroupRenderingProperties(
                                                                                14, Color.WHITE);
        trunkGroup.setRenderingProperties(trunkProperties);

        HighwayGroup trunkLinkGroup = highwayLayer
                .getGroup(HighwayNature.TRUNK_LINK);
        GroupRenderingProperties trunkLinkProperties = new GroupRenderingProperties(
                                                                                    14, Color.WHITE);
        trunkLinkGroup.setRenderingProperties(trunkLinkProperties);

        HighwayGroup pedestrianGroup = highwayLayer
                .getGroup(HighwayNature.PEDESTRIAN);
        GroupRenderingProperties pedestrianProperties = new GroupRenderingProperties(
                                                                                     6, Color.WHITE);
        pedestrianGroup.setRenderingProperties(pedestrianProperties);
    }

    private void registerLayer(AbstractMapLayer layer) {
        layers.add(layer);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        BordeauxRendedering renderer = new BordeauxRendedering();

        

        // tile for this geo position with this zoom level is the take as the reference tile
        int centerXIndex = MapUtil.longToX(longitude, zoom); // shift on right
        int centerYIndex = MapUtil.latToY(latitude, zoom);

        int tileDelta = 2;
        int startX = centerXIndex - tileDelta;
        int endX = centerXIndex + tileDelta;
        int startY = centerYIndex - tileDelta;
        int endY = centerYIndex + tileDelta;

        renderer.createMap(startX, endX, startY, endY);
        renderer.configureMapRendering();
        renderer.lookupOSM(startX, endX, startY, endY);
        renderer.generateMap();

    }

}
