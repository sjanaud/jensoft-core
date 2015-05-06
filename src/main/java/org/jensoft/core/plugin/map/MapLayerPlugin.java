/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.map;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.map.layer.AbstractMapLayer;
import org.jensoft.core.map.projection.GeoPosition;
import org.jensoft.core.map.projection.Projection2D;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

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
     * get map projection
     * 
     * @return map projection
     */
    public Projection.Map getMapProjection() {
        return (Projection.Map) getProjection();
    }

    /**
     * the projection adapter for map layer
     */
    private Projection2D projectionAdapter = new Projection2D() {

        @Override
        public double pixelToLongitude(double pixelX) {
            return getMapProjection().pixelToUserX(pixelX);
        }

        @Override
        public double pixelToLatitude(double pixelY) {
            return getMapProjection().pixelToUserY(pixelY);
        }

        @Override
        public GeoPosition pixelToGeo(Point2D pixel) {
            Point2D geo = getMapProjection().pixelToUser(pixel);
            return new GeoPosition(geo.getY(), geo.getX());
        }

        @Override
        public double longitudeToPixel(double longitude) {
            return getMapProjection().userToPixelX(longitude);
        }

        @Override
        public double latitudeToPixel(double latitude) {
            return getMapProjection().userToPixelY(latitude);
        }

        @Override
        public Point2D geoToPixel(GeoPosition position) {
            return getMapProjection().userToPixel(new Point2D.Double(position.getLongitude(), position.getLatitude()));
        }
    };

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
        for (AbstractMapLayer layer : mapLayers) {
            layer.setProjection2D(projectionAdapter);
            layer.doPaint(g2d);
        }
    }

}
