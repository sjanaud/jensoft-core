/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.projection;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jensoft.core.map.layer.AbstractMapLayer;

/**
 * <code>Map2D</code>
 * 
 * @author Sebastien Janaud
 */
public class Map2D {

    /** start x index */
    private int startX;

    /** end x index */
    private int endX;

    /** start y index */
    private int startY;

    /** end y index */
    private int endY;

    /** zoom level */
    private int level;

    /** square tile size */
    private int squareTileSize;

    /** buffered map */
    private BufferedImage bufferedMap;

    /** rendering hints */
    private RenderingHints hints;

    /** graphics context */
    private Graphics2D graphics2D;

    /** geo bounds */
    private GeoBound geoBound;

    /** map projection */
    private Projection2D projection;

    /** longitude west */
    private double longWest;

    /** longitude east */
    private double longEast;

    /** latitude north */
    private double latNorth;

    /** latitude south */
    private double latSouth;

    /**
     * create a map 2D with specified map parameter
     * 
     * @param startX
     *            the start x index
     * @param endX
     *            the end x index
     * @param startY
     *            the start y index
     * @param endY
     *            the end y index
     * @param squareTileSize
     *            the square tile pixel
     * @param level
     *            the zoom level
     * @param projection
     *            the map projection
     */
    public Map2D(int startX, int endX, int startY, int endY,
            int squareTileSize, int level, Projection2D projection) {

        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
        this.squareTileSize = squareTileSize;
        this.level = level;
        this.projection = projection;

        longWest = MapUtil.tile2long(startX, level);
        longEast = MapUtil.tile2long(endX + 1, level);
        latNorth = MapUtil.tile2lat(startY, level);
        latSouth = MapUtil.tile2lat(endY + 1, level);

        geoBound = new GeoBound(longWest, latSouth, longEast, latNorth);
        geoBound.setProjection(projection);

    }

    /**
     * create the map graphics means create the image as buffered image
     */
    private void createGraphics() {

        bufferedMap = new BufferedImage((endX - startX + 1) * squareTileSize,
                                        (endY - startY + 1) * squareTileSize,
                                        BufferedImage.TYPE_INT_ARGB);
        graphics2D = bufferedMap.createGraphics();
        graphics2D.translate(-startX * squareTileSize, -startY * squareTileSize);

        // if(opaque){
        // Rectangle2D fond = new Rectangle2D.Double(startX*squareTileSize,
        // startY*squareTileSize, (endX-startX+1)*squareTileSize,
        // (endY-startY+1)*squareTileSize);
        // graphics2D.setColor(new Color(237,234,226));
        // graphics2D.fill(fond);
        // }
        hints = new RenderingHints(RenderingHints.KEY_RENDERING,
                                   RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_ANTIALIASING,
                  RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_FRACTIONALMETRICS,
                  RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_INTERPOLATION,
                  RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        hints.put(RenderingHints.KEY_STROKE_CONTROL,
                  RenderingHints.VALUE_STROKE_NORMALIZE);
        hints.put(RenderingHints.KEY_COLOR_RENDERING,
                  RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics2D.setRenderingHints(hints);

    }

    /**
     * get the font render context of this map
     * 
     * @return font render context
     */
    public FontRenderContext getFontRenderContext() {
        if (bufferedMap == null) {
            createGraphics();
        }
        return graphics2D.getFontRenderContext();
    }

    /**
     * get geo bound of this map
     * 
     * @return geo bound
     */
    public GeoBound getGeoBound() {
        return geoBound;
    }

    /**
     * write map as image in the given container with the given name
     * 
     * @param mapContainer
     *            the target map directory
     * @param mapName
     *            the map name
     * @throws IOException
     */
    public void writeMap(String mapContainer, String mapName)
            throws IOException {
        if (bufferedMap == null) {
            createGraphics();
        }
        graphics2D.dispose();
        File fileMapContainer = new File(mapContainer);
        fileMapContainer.mkdirs();
        File file = new File(mapContainer + File.separator + mapName);
        try {
            ImageIO.write(bufferedMap, "png", file);
        }
        catch (IOException e) {
            throw new IOException("Map write error ");
        }
    }

    /**
     * write all image tiles of this map in the given directory
     * 
     * @param tilesContainer
     *            the target tiles directory
     * @throws IOException
     */
    public void writeTiles(String tilesContainer) throws IOException {

        if (bufferedMap == null) {
            createGraphics();
        }

        graphics2D.dispose();

        File fileTilesContainer = new File(tilesContainer);
        fileTilesContainer.mkdirs();

        File fileTilesLevelContainer = new File(tilesContainer + File.separator
                + level + "");
        fileTilesLevelContainer.mkdirs();

        for (int x = 0; x <= endX - startX; x++) {

            File fileTilesLevelXContainer = new File(tilesContainer
                    + File.separator + level + "" + File.separator
                    + (startX + x) + "");
            fileTilesLevelXContainer.mkdirs();

            for (int y = 0; y <= endY - startY; y++) {

                BufferedImage tileImage = bufferedMap.getSubimage(x
                        * squareTileSize, y * squareTileSize, squareTileSize,
                                                                  squareTileSize);
                // String tileName = "tile_"+(startX+x)+"_"+(startY+y)+".png";
                String tileName = tilesContainer + File.separator
                        + level + "" + File.separator + (startX + x)
                        + File.separator + (startY + y) + ".png";
                File tileFile = new File(tileName);
                try {
                    ImageIO.write(tileImage, "png", tileFile);
                }
                catch (IOException e) {
                    throw new IOException("Map tile " + tileName
                            + " write error ");
                }
            }

        }

    }

    /**
     * get the start x index
     * 
     * @return start x index
     */
    public int getStartX() {
        return startX;
    }

    /**
     * set the start x index
     * 
     * @param startX
     *            the start x index to set
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * get the end x index
     * 
     * @return end x index
     */
    public int getEndX() {
        return endX;
    }

    /**
     * set the end x index
     * 
     * @param endX
     *            the end x index to set
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * set the start y index
     * 
     * @return start y index
     */
    public int getStartY() {
        return startY;
    }

    /**
     * set the start y index
     * 
     * @param startY
     *            the start y index to set
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * get the end y index
     * 
     * @return end y index
     */
    public int getEndY() {
        return endY;
    }

    /**
     * set the end y index
     * 
     * @param endY
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }

    /**
     * get the square tile size for this map
     * 
     * @return square tile size
     */
    public int getSquareTileSize() {
        return squareTileSize;
    }

    /**
     * set the square tile size for this map
     * 
     * @param squareTileSize
     */
    public void setSquareTileSize(int squareTileSize) {
        this.squareTileSize = squareTileSize;
    }

    /**
     * get zoom level
     * 
     * @return zoom level
     */
    public int getLevel() {
        return level;
    }

    /**
     * get map as buffered image
     * 
     * @return buffered image map
     */
    public BufferedImage getBufferedMap() {
        return bufferedMap;
    }

    /**
     * assign projection to the given layer
     * 
     * @param layer
     *            the layer to assign
     */
    private void assignProjection(AbstractMapLayer layer) {
        layer.setProjection2D(projection);
    }

    /**
     * bound layer
     * 
     * @param layer
     *            the layer to bound
     */
    private void boundLayer(AbstractMapLayer layer) {
        layer.setGeoBound(geoBound);
    }

    /**
     * paint the layer in this map
     * 
     * @param layer
     *            the layer to paint in this map
     */
    public void paint(AbstractMapLayer layer) {
        if (bufferedMap == null) {
            createGraphics();
        }
        boundLayer(layer);
        assignProjection(layer);
        layer.doPaintMap(this);
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    /**
     * get the longitude west
     * 
     * @return longitude west
     */
    public double getLongWest() {
        return longWest;
    }

    /**
     * get the map projection
     * 
     * @return map projection
     */
    public Projection2D getProjection() {
        return projection;
    }

    /**
     * get the longitude east
     * 
     * @return longitude east
     */
    public double getLongEast() {
        return longEast;
    }

    /**
     * get the latitude north
     * 
     * @return latitude north
     */
    public double getLatNorth() {
        return latNorth;
    }

    /**
     * get the latitude south
     * 
     * @return latitude south
     */
    public double getLatSouth() {
        return latSouth;
    }

}
