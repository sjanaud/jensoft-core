/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.map;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.layer.AbstractMapLayer;
import com.jensoft.core.map.projection.GeoPosition;
import com.jensoft.core.map.projection.Projection2D;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>MapLayerPlugin</code> takes the responsibility to paint map layers
 * 
 * @author sebastien Janaud
 */
public class MapLayerPlugin extends AbstractPlugin {

    /** map layers */
    private List<AbstractMapLayer> mapLayers = new ArrayList<AbstractMapLayer>();

    /**
     * create a map layer plugin
     */
    public MapLayerPlugin() {
        setName("MapLayerPlugin");
    }

    /**
     * register layer in this paving projection
     * 
     * @param mapLayer
     *            the map layer to register
     */
    public void registerLayer(AbstractMapLayer mapLayer) {
        mapLayers.add(mapLayer);
    }

    /**
     * get map window
     * 
     * @return map window
     */
    public Window2D.Map getWindowMap() {
        return (Window2D.Map) getWindow2D();
    }

    /**
     * the projection adapter for map layer
     */
    private Projection2D projectionAdapter = new Projection2D() {

        @Override
        public double pixelToLongitude(double pixelX) {
            return getWindowMap().pixelToUserX(pixelX);
        }

        @Override
        public double pixelToLatitude(double pixelY) {
            return getWindowMap().pixelToUserY(pixelY);
        }

        @Override
        public GeoPosition pixelToGeo(Point2D pixel) {
            Point2D geo = getWindowMap().pixelToUser(pixel);
            return new GeoPosition(geo.getY(), geo.getX());
        }

        @Override
        public double longitudeToPixel(double longitude) {
            return getWindowMap().userToPixelX(longitude);
        }

        @Override
        public double latitudeToPixel(double latitude) {
            return getWindowMap().userToPixelY(latitude);
        }

        @Override
        public Point2D geoToPixel(GeoPosition position) {
            return getWindowMap().userToPixel(new Point2D.Double(position.getLongitude(), position.getLatitude()));
        }
    };

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
     */
    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
        for (AbstractMapLayer layer : mapLayers) {
            layer.setProjection2D(projectionAdapter);
            layer.doPaint(g2d);
        }
    }

}
