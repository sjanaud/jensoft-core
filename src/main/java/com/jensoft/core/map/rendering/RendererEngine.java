/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.rendering;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.layer.AbstractMapLayer;
import com.jensoft.core.map.layer.background.BackgroundLayer;
import com.jensoft.core.map.layer.highway.GroupRenderingProperties;
import com.jensoft.core.map.layer.highway.HighwayGroup;
import com.jensoft.core.map.layer.highway.HighwayLayer;
import com.jensoft.core.map.layer.highway.HighwayNature;
import com.jensoft.core.map.layer.landuse.LanduseLayer;
import com.jensoft.core.map.layer.leisure.LeisureLayer;
import com.jensoft.core.map.layer.manmade.ManMadeLayer;
import com.jensoft.core.map.layer.natural.NaturalLayer;
import com.jensoft.core.map.layer.railway.RailwayLayer;
import com.jensoft.core.map.layer.waterway.WaterwayLayer;
import com.jensoft.core.map.projection.DalleProjection;
import com.jensoft.core.map.projection.Map2D;

/**
 * 
 *
 */
public class RendererEngine {

    /** all dalles projections */
    // private List<Projection2D> projections;

    /** all map layers */
    private List<AbstractMapLayer> layers;

    /** dalle projection 20 */
    private DalleProjection D21_256;
    private DalleProjection D21_128;

    /** dalle projection 20 */
    private DalleProjection D20_256;
    private DalleProjection D20_128;

    /** dalle projection 19 */
    private DalleProjection D19_256;
    private DalleProjection D19_128;

    /** dalle projection 18 */
    private DalleProjection D18_256;
    private DalleProjection D18_128;

    /** dalle projection 17 */
    private DalleProjection D17_256;
    private DalleProjection D17_128;

    /** dalle projection 15 */
    private DalleProjection D16_256;
    private DalleProjection D16_128;

    /** dalle projection 15 */
    private DalleProjection D15_256;
    private DalleProjection D15_128;

    /** dalle projection 14 */
    private DalleProjection D14;

    /** dalle projection 13 */
    private DalleProjection D13;

    /** dalle projection 12 */
    private DalleProjection D12;

    /** painters */
    private BackgroundLayer backgroundLayer;
    private HighwayLayer highwayLayer;
    private NaturalLayer naturalLayer;
    private LeisureLayer leisureLayer;
    private LanduseLayer landuseLayer;
    private RailwayLayer railwayLayer;
    private ManMadeLayer manmadeLayer;
    private WaterwayLayer waterwayLayer;

    /**
     * the renderer engine
     */
    public RendererEngine() {

        D21_256 = new DalleProjection(21, 256);
        D21_128 = new DalleProjection(21, 128);

        D20_256 = new DalleProjection(20, 256);
        D20_128 = new DalleProjection(20, 128);

        D19_256 = new DalleProjection(19, 256);
        D19_128 = new DalleProjection(19, 128);

        D18_256 = new DalleProjection(18, 256);
        D18_128 = new DalleProjection(18, 128);

        D17_256 = new DalleProjection(17, 256);
        D17_128 = new DalleProjection(17, 128);

        D16_256 = new DalleProjection(16, 256);
        D16_128 = new DalleProjection(16, 128);

        D15_256 = new DalleProjection(15, 256);
        D15_128 = new DalleProjection(15, 128);

        D14 = new DalleProjection(14, 256);
        D13 = new DalleProjection(13, 256);
        D12 = new DalleProjection(12, 256);

        // projections = new ArrayList<Projection2D>();
        // registerProjection(D17_256);
        // registerProjection(D16_256);
        // registerProjection(D15_256);
        // registerProjection(D14);
        // registerProjection(D13);
        // registerProjection(D12);

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
        //registerLayer(naturalLayer);
        registerLayer(leisureLayer);
        registerLayer(landuseLayer);
        registerLayer(manmadeLayer);
        registerLayer(highwayLayer);
        registerLayer(railwayLayer);
        registerLayer(waterwayLayer);

    }

    public List<AbstractMapLayer> getLayers() {
        return layers;
    }

    public HighwayLayer getHighwayLayer() {
        return highwayLayer;
    }

    public NaturalLayer getNaturalLayer() {
        return naturalLayer;
    }

    public LeisureLayer getLeisureLayer() {
        return leisureLayer;
    }

    public LanduseLayer getLanduseLayer() {
        return landuseLayer;
    }

    public RailwayLayer getRailwayLayer() {
        return railwayLayer;
    }

    public ManMadeLayer getManmadeLayer() {
        return manmadeLayer;
    }

    public WaterwayLayer getWaterwayLayer() {
        return waterwayLayer;
    }

    private void registerLayer(AbstractMapLayer layer) {
        layers.add(layer);
    }

    public Map2D createMapD21_256(int startX, int endX, int startY, int endY) {

        Map2D map2D = D21_256.createMap2D(startX, endX, startY, endY);

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

        for (AbstractMapLayer layer : layers) {
            map2D.paint(layer);
        }

        return map2D;

    }

    public Map2D createMapD20_256(int startX, int endX, int startY, int endY) {

        Map2D map2D = D20_256.createMap2D(startX, endX, startY, endY);

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

        for (AbstractMapLayer layer : layers) {
            map2D.paint(layer);
        }

        return map2D;

    }

    public Map2D createMapD19_256(int startX, int endX, int startY, int endY) {

        Map2D map2D = D19_256.createMap2D(startX, endX, startY, endY);

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

        for (AbstractMapLayer layer : layers) {
            map2D.paint(layer);
        }

        return map2D;

    }

    public Map2D createMapD18_256(int startX, int endX, int startY, int endY) {

        Map2D map2D = D18_256.createMap2D(startX, endX, startY, endY);

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

        for (AbstractMapLayer layer : layers) {
            map2D.paint(layer);
        }

        return map2D;

    }

    public Map2D createMapD17_256(int startX, int endX, int startY, int endY) {

        Map2D map2D = D17_256.createMap2D(startX, endX, startY, endY);

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

        for (AbstractMapLayer layer : layers) {
            map2D.paint(layer);
        }

        return map2D;

    }

    public Map2D createMapD17_128(int startX, int endX, int startY, int endY) {

        Map2D map2D = D17_128.createMap2D(startX, endX, startY, endY);

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

        for (AbstractMapLayer layer : layers) {
            map2D.paint(layer);
        }

        return map2D;

    }

    public Map2D createMapD16_256(int startX, int endX, int startY, int endY) {

        Map2D map2D = D16_256.createMap2D(startX, endX, startY, endY);

        // configure map layers for this level
        HighwayGroup residentialGroup = highwayLayer
                .getGroup(HighwayNature.RESIDENTIAL);
        GroupRenderingProperties residentialProperties = new GroupRenderingProperties(
                                                                                      10, Color.WHITE);
        residentialGroup.setRenderingProperties(residentialProperties);

        HighwayGroup unclassifiedGroup = highwayLayer
                .getGroup(HighwayNature.UNCLASSIFIED);
        GroupRenderingProperties unclassifiedProperties = new GroupRenderingProperties(
                                                                                       10, Color.WHITE);
        unclassifiedGroup.setRenderingProperties(unclassifiedProperties);

        HighwayGroup roadGroup = highwayLayer.getGroup(HighwayNature.ROAD);
        GroupRenderingProperties roadProperties = new GroupRenderingProperties(
                                                                               10, Color.WHITE);
        roadGroup.setRenderingProperties(roadProperties);

        HighwayGroup primaryGroup = highwayLayer
                .getGroup(HighwayNature.PRIMARY);
        GroupRenderingProperties primaryProperties = new GroupRenderingProperties(
                                                                                  12, Color.WHITE);
        primaryGroup.setRenderingProperties(primaryProperties);

        HighwayGroup secondaryGroup = highwayLayer
                .getGroup(HighwayNature.SECONDARY);
        GroupRenderingProperties secondaryProperties = new GroupRenderingProperties(
                                                                                    12, Color.WHITE);
        secondaryGroup.setRenderingProperties(secondaryProperties);

        HighwayGroup tertiaryGroup = highwayLayer
                .getGroup(HighwayNature.TERTIARY);
        GroupRenderingProperties tertiaryProperties = new GroupRenderingProperties(
                                                                                   12, Color.WHITE);
        tertiaryGroup.setRenderingProperties(tertiaryProperties);

        HighwayGroup serviceGroup = highwayLayer
                .getGroup(HighwayNature.SERVICE);
        GroupRenderingProperties serviceProperties = new GroupRenderingProperties(
                                                                                  4, Color.WHITE);
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

        for (AbstractMapLayer layer : layers) {
            map2D.paint(layer);
        }

        return map2D;

    }

    public Map2D createMapD16_128(int startX, int endX, int startY, int endY) {

        Map2D map2D = D16_256.createMap2D(startX, endX, startY, endY);

        // configure map layers for this level
        HighwayGroup residentialGroup = highwayLayer
                .getGroup(HighwayNature.RESIDENTIAL);
        GroupRenderingProperties residentialProperties = new GroupRenderingProperties(
                                                                                      10, Color.WHITE);
        residentialGroup.setRenderingProperties(residentialProperties);

        HighwayGroup unclassifiedGroup = highwayLayer
                .getGroup(HighwayNature.UNCLASSIFIED);
        GroupRenderingProperties unclassifiedProperties = new GroupRenderingProperties(
                                                                                       10, Color.WHITE);
        unclassifiedGroup.setRenderingProperties(unclassifiedProperties);

        HighwayGroup roadGroup = highwayLayer.getGroup(HighwayNature.ROAD);
        GroupRenderingProperties roadProperties = new GroupRenderingProperties(
                                                                               10, Color.WHITE);
        roadGroup.setRenderingProperties(roadProperties);

        HighwayGroup primaryGroup = highwayLayer
                .getGroup(HighwayNature.PRIMARY);
        GroupRenderingProperties primaryProperties = new GroupRenderingProperties(
                                                                                  12, Color.WHITE);
        primaryGroup.setRenderingProperties(primaryProperties);

        HighwayGroup secondaryGroup = highwayLayer
                .getGroup(HighwayNature.SECONDARY);
        GroupRenderingProperties secondaryProperties = new GroupRenderingProperties(
                                                                                    12, Color.WHITE);
        secondaryGroup.setRenderingProperties(secondaryProperties);

        HighwayGroup tertiaryGroup = highwayLayer
                .getGroup(HighwayNature.TERTIARY);
        GroupRenderingProperties tertiaryProperties = new GroupRenderingProperties(
                                                                                   12, Color.WHITE);
        tertiaryGroup.setRenderingProperties(tertiaryProperties);

        HighwayGroup serviceGroup = highwayLayer
                .getGroup(HighwayNature.SERVICE);
        GroupRenderingProperties serviceProperties = new GroupRenderingProperties(
                                                                                  4, Color.WHITE);
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

        for (AbstractMapLayer layer : layers) {
            map2D.paint(layer);
        }

        return map2D;

    }

    public Map2D createMapD15_256(int startX, int endX, int startY, int endY) {

        Map2D map2D = D15_256.createMap2D(startX, endX, startY, endY);

        // configure map layers for this level

        Font streetCompressed12 = new Font("Street - Compressed", Font.PLAIN,
                                           12);
        Font streetCompressed10 = new Font("Street - Compressed", Font.PLAIN,
                                           10);

        Font mentone12 = new Font("Mentone", Font.PLAIN, 12);
        Font mentone10 = new Font("Mentone", Font.PLAIN, 10);

        Font yorkville12 = new Font("yorkville", Font.PLAIN, 12);
        Font yorkville10 = new Font("yorkville", Font.PLAIN, 10);

        Font roadway12 = new Font("Roadway", Font.PLAIN, 12);
        Font roadway10 = new Font("Roadway", Font.PLAIN, 10);

        Font ptfNordic12 = new Font("PTF NORDIC Rnd", Font.PLAIN, 12);
        Font ptfNordic10 = new Font("PTF NORDIC Rnd", Font.PLAIN, 10);

        Font noMovement12 = new Font("N.O.- Movement", Font.PLAIN, 12);
        Font noMovement10 = new Font("N.O.- Movement", Font.PLAIN, 10);

        HighwayGroup residentialGroup = highwayLayer
                .getGroup(HighwayNature.RESIDENTIAL);
        GroupRenderingProperties residentialProperties = new GroupRenderingProperties(
                                                                                      8, Color.WHITE);
        residentialProperties.setGlyphFont(mentone10);
        residentialGroup.setRenderingProperties(residentialProperties);

        // Acens : peu visible, mais cool
        // Street - Compressed : cool
        // Sansation : cool
        // Surface : cool super
        // Sansumi
        // mentone super
        // yorkville

        HighwayGroup unclassifiedGroup = highwayLayer
                .getGroup(HighwayNature.UNCLASSIFIED);
        GroupRenderingProperties unclassifiedProperties = new GroupRenderingProperties(
                                                                                       8, Color.WHITE);
        unclassifiedProperties.setGlyphFont(noMovement12);
        unclassifiedGroup.setRenderingProperties(unclassifiedProperties);

        HighwayGroup roadGroup = highwayLayer.getGroup(HighwayNature.ROAD);
        GroupRenderingProperties roadProperties = new GroupRenderingProperties(
                                                                               8, Color.WHITE);
        unclassifiedProperties.setGlyphFont(noMovement12);
        roadGroup.setRenderingProperties(roadProperties);

        HighwayGroup primaryGroup = highwayLayer
                .getGroup(HighwayNature.PRIMARY);
        GroupRenderingProperties primaryProperties = new GroupRenderingProperties(
                                                                                  10, Color.WHITE);
        primaryProperties.setGlyphFont(noMovement12);
        primaryGroup.setRenderingProperties(primaryProperties);

        HighwayGroup secondaryGroup = highwayLayer
                .getGroup(HighwayNature.SECONDARY);
        GroupRenderingProperties secondaryProperties = new GroupRenderingProperties(
                                                                                    10, Color.WHITE);
        secondaryProperties.setGlyphFont(noMovement12);
        secondaryGroup.setRenderingProperties(secondaryProperties);

        HighwayGroup tertiaryGroup = highwayLayer
                .getGroup(HighwayNature.TERTIARY);
        GroupRenderingProperties tertiaryProperties = new GroupRenderingProperties(
                                                                                   10, Color.WHITE);
        tertiaryProperties.setGlyphFont(noMovement12);
        tertiaryGroup.setRenderingProperties(tertiaryProperties);

        HighwayGroup serviceGroup = highwayLayer
                .getGroup(HighwayNature.SERVICE);
        GroupRenderingProperties serviceProperties = new GroupRenderingProperties(
                                                                                  6, Color.WHITE);
        serviceGroup.setRenderingProperties(serviceProperties);

        HighwayGroup motorwayGroup = highwayLayer
                .getGroup(HighwayNature.MOTORWAY);
        GroupRenderingProperties motorwayProperties = new GroupRenderingProperties(
                                                                                   10, Color.WHITE);
        motorwayGroup.setRenderingProperties(motorwayProperties);

        HighwayGroup motorwayLinkGroup = highwayLayer
                .getGroup(HighwayNature.MOTORWAY_LINK);
        GroupRenderingProperties motorwayLinkProperties = new GroupRenderingProperties(
                                                                                       10, Color.WHITE);
        motorwayLinkGroup.setRenderingProperties(motorwayLinkProperties);

        HighwayGroup trunkGroup = highwayLayer.getGroup(HighwayNature.TRUNK);
        GroupRenderingProperties trunkProperties = new GroupRenderingProperties(
                                                                                10, Color.WHITE);
        trunkGroup.setRenderingProperties(trunkProperties);

        HighwayGroup trunkLinkGroup = highwayLayer
                .getGroup(HighwayNature.TRUNK_LINK);
        GroupRenderingProperties trunkLinkProperties = new GroupRenderingProperties(
                                                                                    10, Color.WHITE);
        trunkLinkGroup.setRenderingProperties(trunkLinkProperties);

        HighwayGroup pedestrianGroup = highwayLayer
                .getGroup(HighwayNature.PEDESTRIAN);
        GroupRenderingProperties pedestrianProperties = new GroupRenderingProperties(
                                                                                     10, Color.WHITE);
        pedestrianGroup.setRenderingProperties(pedestrianProperties);

        for (AbstractMapLayer layer : layers) {
            map2D.paint(layer);
        }

        return map2D;

    }

    public Map2D createMapD15_128(int startX, int endX, int startY, int endY) {

        Map2D map2D = D15_128.createMap2D(startX, endX, startY, endY);

        // configure map layers for this level

        Font streetCompressed12 = new Font("Street - Compressed", Font.PLAIN,
                                           12);
        Font streetCompressed10 = new Font("Street - Compressed", Font.PLAIN,
                                           10);

        Font mentone12 = new Font("Mentone", Font.PLAIN, 12);
        Font mentone10 = new Font("Mentone", Font.PLAIN, 10);
        Font mentone8 = new Font("Mentone", Font.PLAIN, 8);

        Font yorkville12 = new Font("yorkville", Font.PLAIN, 12);
        Font yorkville10 = new Font("yorkville", Font.PLAIN, 10);
        Font yorkville8 = new Font("yorkville", Font.PLAIN, 10);

        Font roadway12 = new Font("Roadway", Font.PLAIN, 12); // attention
                                                              // lettre
                                                              // minuscule
        Font roadway10 = new Font("Roadway", Font.PLAIN, 10);

        Font ptfNordic12 = new Font("PTF NORDIC Rnd", Font.PLAIN, 12); // lettre
                                                                       // capital
        Font ptfNordic10 = new Font("PTF NORDIC Rnd", Font.PLAIN, 10);// tres
                                                                      // lisible
        Font ptfNordic8 = new Font("PTF NORDIC Rnd", Font.PLAIN, 8);

        Font noMovement12 = new Font("N.O.- Movement", Font.PLAIN, 12);
        Font noMovement10 = new Font("N.O.- Movement", Font.PLAIN, 10); // bien,
                                                                        // tres
                                                                        // noir
                                                                        // !
        Font noMovement8 = new Font("N.O.- Movement", Font.PLAIN, 8); // bien,
                                                                      // assez
                                                                      // visible

        HighwayGroup residentialGroup = highwayLayer
                .getGroup(HighwayNature.RESIDENTIAL);
        GroupRenderingProperties residentialProperties = new GroupRenderingProperties(
                                                                                      4, Color.WHITE);
        residentialProperties.setGlyphFont(mentone10);
        // residentialProperties.unlockGlyph();
        residentialGroup.setRenderingProperties(residentialProperties);

        HighwayGroup unclassifiedGroup = highwayLayer
                .getGroup(HighwayNature.UNCLASSIFIED);
        GroupRenderingProperties unclassifiedProperties = new GroupRenderingProperties(
                                                                                       4, Color.WHITE);
        unclassifiedProperties.setGlyphFont(mentone10);
        unclassifiedGroup.setRenderingProperties(unclassifiedProperties);

        HighwayGroup serviceGroup = highwayLayer
                .getGroup(HighwayNature.SERVICE);
        GroupRenderingProperties serviceProperties = new GroupRenderingProperties(
                                                                                  2, Color.WHITE);
        serviceGroup.setRenderingProperties(serviceProperties);

        HighwayGroup roadGroup = highwayLayer.getGroup(HighwayNature.ROAD);
        GroupRenderingProperties roadProperties = new GroupRenderingProperties(
                                                                               4, Color.WHITE);
        unclassifiedProperties.setGlyphFont(mentone10);
        roadGroup.setRenderingProperties(roadProperties);

        HighwayGroup primaryGroup = highwayLayer
                .getGroup(HighwayNature.PRIMARY);
        GroupRenderingProperties primaryProperties = new GroupRenderingProperties(
                                                                                  6, Color.WHITE);
        primaryProperties.setGlyphFont(mentone12);
        primaryGroup.setRenderingProperties(primaryProperties);

        HighwayGroup secondaryGroup = highwayLayer
                .getGroup(HighwayNature.SECONDARY);
        GroupRenderingProperties secondaryProperties = new GroupRenderingProperties(
                                                                                    8, Color.WHITE);
        secondaryProperties.setGlyphFont(mentone12);
        secondaryGroup.setRenderingProperties(secondaryProperties);

        HighwayGroup tertiaryGroup = highwayLayer
                .getGroup(HighwayNature.TERTIARY);
        GroupRenderingProperties tertiaryProperties = new GroupRenderingProperties(
                                                                                   8, Color.WHITE);
        tertiaryProperties.setGlyphFont(mentone12);
        tertiaryGroup.setRenderingProperties(tertiaryProperties);

        HighwayGroup motorwayGroup = highwayLayer
                .getGroup(HighwayNature.MOTORWAY);
        GroupRenderingProperties motorwayProperties = new GroupRenderingProperties(
                                                                                   8, Color.WHITE);
        motorwayGroup.setRenderingProperties(motorwayProperties);

        HighwayGroup motorwayLinkGroup = highwayLayer
                .getGroup(HighwayNature.MOTORWAY_LINK);
        GroupRenderingProperties motorwayLinkProperties = new GroupRenderingProperties(
                                                                                       6, Color.WHITE);
        motorwayLinkGroup.setRenderingProperties(motorwayLinkProperties);

        HighwayGroup trunkGroup = highwayLayer.getGroup(HighwayNature.TRUNK);
        GroupRenderingProperties trunkProperties = new GroupRenderingProperties(
                                                                                6, Color.WHITE);
        trunkGroup.setRenderingProperties(trunkProperties);

        HighwayGroup trunkLinkGroup = highwayLayer
                .getGroup(HighwayNature.TRUNK_LINK);
        GroupRenderingProperties trunkLinkProperties = new GroupRenderingProperties(
                                                                                    6, Color.WHITE);
        trunkLinkGroup.setRenderingProperties(trunkLinkProperties);

        HighwayGroup pedestrianGroup = highwayLayer
                .getGroup(HighwayNature.PEDESTRIAN);
        GroupRenderingProperties pedestrianProperties = new GroupRenderingProperties(
                                                                                     6, Color.WHITE);
        pedestrianGroup.setRenderingProperties(pedestrianProperties);

        for (AbstractMapLayer layer : layers) {
            map2D.paint(layer);
        }

        return map2D;

    }

    public Map2D createMapD15_128_HIGHWAYS(int startX, int endX, int startY,
            int endY) {

        Map2D map2D = D15_128.createMap2D(startX, endX, startY, endY);

        // configure map layers for this level

        Font streetCompressed12 = new Font("Street - Compressed", Font.PLAIN,
                                           12);
        Font streetCompressed10 = new Font("Street - Compressed", Font.PLAIN,
                                           10);

        Font mentone12 = new Font("Mentone", Font.PLAIN, 12);
        Font mentone10 = new Font("Mentone", Font.PLAIN, 10);
        Font mentone8 = new Font("Mentone", Font.PLAIN, 8);

        Font yorkville12 = new Font("yorkville", Font.PLAIN, 12);
        Font yorkville10 = new Font("yorkville", Font.PLAIN, 10);
        Font yorkville8 = new Font("yorkville", Font.PLAIN, 10);

        Font roadway12 = new Font("Roadway", Font.PLAIN, 12); // attention
                                                              // lettre
                                                              // minuscule
        Font roadway10 = new Font("Roadway", Font.PLAIN, 10);

        Font ptfNordic12 = new Font("PTF NORDIC Rnd", Font.PLAIN, 12); // lettre
                                                                       // capital
        Font ptfNordic10 = new Font("PTF NORDIC Rnd", Font.PLAIN, 10);// tres
                                                                      // lisible
        Font ptfNordic8 = new Font("PTF NORDIC Rnd", Font.PLAIN, 8);

        Font noMovement12 = new Font("N.O.- Movement", Font.PLAIN, 12);
        Font noMovement10 = new Font("N.O.- Movement", Font.PLAIN, 10); // bien,
                                                                        // tres
                                                                        // noir
                                                                        // !
        Font noMovement8 = new Font("N.O.- Movement", Font.PLAIN, 8); // bien,
                                                                      // assez
                                                                      // visible

        backgroundLayer.setOpaque(true);

        HighwayGroup residentialGroup = highwayLayer
                .getGroup(HighwayNature.RESIDENTIAL);
        GroupRenderingProperties residentialProperties = new GroupRenderingProperties(
                                                                                      4, Color.WHITE);
        residentialProperties.setGlyphFont(mentone10);
        // residentialProperties.unlockGlyph();
        residentialGroup.setRenderingProperties(residentialProperties);

        HighwayGroup unclassifiedGroup = highwayLayer
                .getGroup(HighwayNature.UNCLASSIFIED);
        GroupRenderingProperties unclassifiedProperties = new GroupRenderingProperties(
                                                                                       4, Color.WHITE);
        unclassifiedProperties.setGlyphFont(mentone10);
        unclassifiedGroup.setRenderingProperties(unclassifiedProperties);

        HighwayGroup serviceGroup = highwayLayer
                .getGroup(HighwayNature.SERVICE);
        GroupRenderingProperties serviceProperties = new GroupRenderingProperties(
                                                                                  2, Color.WHITE);
        serviceGroup.setRenderingProperties(serviceProperties);

        HighwayGroup roadGroup = highwayLayer.getGroup(HighwayNature.ROAD);
        GroupRenderingProperties roadProperties = new GroupRenderingProperties(
                                                                               4, Color.WHITE);
        unclassifiedProperties.setGlyphFont(mentone10);
        roadGroup.setRenderingProperties(roadProperties);

        HighwayGroup primaryGroup = highwayLayer
                .getGroup(HighwayNature.PRIMARY);
        GroupRenderingProperties primaryProperties = new GroupRenderingProperties(
                                                                                  6, Color.WHITE);
        primaryProperties.setGlyphFont(mentone12);
        primaryGroup.setRenderingProperties(primaryProperties);

        HighwayGroup secondaryGroup = highwayLayer
                .getGroup(HighwayNature.SECONDARY);
        GroupRenderingProperties secondaryProperties = new GroupRenderingProperties(
                                                                                    8, Color.WHITE);
        secondaryProperties.setGlyphFont(mentone12);
        secondaryGroup.setRenderingProperties(secondaryProperties);

        HighwayGroup tertiaryGroup = highwayLayer
                .getGroup(HighwayNature.TERTIARY);
        GroupRenderingProperties tertiaryProperties = new GroupRenderingProperties(
                                                                                   8, Color.WHITE);
        tertiaryProperties.setGlyphFont(mentone12);
        tertiaryGroup.setRenderingProperties(tertiaryProperties);

        HighwayGroup motorwayGroup = highwayLayer
                .getGroup(HighwayNature.MOTORWAY);
        GroupRenderingProperties motorwayProperties = new GroupRenderingProperties(
                                                                                   8, Color.WHITE);
        motorwayGroup.setRenderingProperties(motorwayProperties);

        HighwayGroup motorwayLinkGroup = highwayLayer
                .getGroup(HighwayNature.MOTORWAY_LINK);
        GroupRenderingProperties motorwayLinkProperties = new GroupRenderingProperties(
                                                                                       6, Color.WHITE);
        motorwayLinkGroup.setRenderingProperties(motorwayLinkProperties);

        HighwayGroup trunkGroup = highwayLayer.getGroup(HighwayNature.TRUNK);
        GroupRenderingProperties trunkProperties = new GroupRenderingProperties(
                                                                                6, Color.WHITE);
        trunkGroup.setRenderingProperties(trunkProperties);

        HighwayGroup trunkLinkGroup = highwayLayer
                .getGroup(HighwayNature.TRUNK_LINK);
        GroupRenderingProperties trunkLinkProperties = new GroupRenderingProperties(
                                                                                    6, Color.WHITE);
        trunkLinkGroup.setRenderingProperties(trunkLinkProperties);

        HighwayGroup pedestrianGroup = highwayLayer
                .getGroup(HighwayNature.PEDESTRIAN);
        GroupRenderingProperties pedestrianProperties = new GroupRenderingProperties(
                                                                                     6, Color.WHITE);
        pedestrianGroup.setRenderingProperties(pedestrianProperties);

        map2D.paint(backgroundLayer);
        map2D.paint(highwayLayer);

        return map2D;

    }

}
