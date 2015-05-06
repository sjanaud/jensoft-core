/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.tile;

import java.awt.image.BufferedImage;
import java.util.List;

import org.jensoft.core.map.layer.highway.Highway;
import org.jensoft.core.map.layer.landuse.Landuse;
import org.jensoft.core.map.layer.leisure.Leisure;
import org.jensoft.core.map.layer.manmade.ManMade;
import org.jensoft.core.map.layer.natural.Natural;
import org.jensoft.core.map.layer.railway.Railway;
import org.jensoft.core.map.layer.waterway.Waterway;

public class Tile {

    private int x;
    private int y;
    private int zoom;
    private BufferedImage tileImage;
    private TileKey key;

    private int squareTileSize;

    private List<Highway> highways;
    private List<Railway> railways;
    private List<Waterway> waterways;
    private List<Landuse> landuses;
    private List<Leisure> leisures;
    private List<Natural> naturals;
    private List<ManMade> manmades;

    public Tile(int x, int y, int zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
        key = new TileKey(x, y, zoom);

    }

    public TileKey getKey() {
        return key;
    }

    public void setKey(TileKey key) {
        this.key = key;
    }

    public int getSquareTileSize() {
        return squareTileSize;
    }

    public void setSquareTileSize(int squareTileSize) {
        this.squareTileSize = squareTileSize;
    }

    public List<Highway> getHighways() {
        return highways;
    }

    public void setHighways(List<Highway> highways) {
        this.highways = highways;
    }

    public List<Railway> getRailways() {
        return railways;
    }

    public void setRailways(List<Railway> railways) {
        this.railways = railways;
    }

    public List<Waterway> getWaterways() {
        return waterways;
    }

    public void setWaterways(List<Waterway> waterways) {
        this.waterways = waterways;
    }

    public List<Landuse> getLanduses() {
        return landuses;
    }

    public void setLanduses(List<Landuse> landuses) {
        this.landuses = landuses;
    }

    public List<Leisure> getLeisures() {
        return leisures;
    }

    public void setLeisures(List<Leisure> leisures) {
        this.leisures = leisures;
    }

    public List<Natural> getNaturals() {
        return naturals;
    }

    public void setNaturals(List<Natural> naturals) {
        this.naturals = naturals;
    }

    public List<ManMade> getManmades() {
        return manmades;
    }

    public void setManmades(List<ManMade> manmades) {
        this.manmades = manmades;
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

    public BufferedImage getTileImage() {
        return tileImage;
    }

    public void setTileImage(BufferedImage tileImage) {
        this.tileImage = tileImage;
    }

}
