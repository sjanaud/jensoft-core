/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.marker.marker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.View;

/**
 * <code>CrossMarker</code>
 */
public class CrossMarker extends AbstractMarker {

    /** cross marker color */
    private Color crossMarkerColor;

    /** cross vertical shape */
    private Shape shapeMarkerV;

    /** cross horizontal shape */
    private Shape shapeMarkerH;

    /**
     * create default cross marker
     */
    public CrossMarker() {
    }

    /**
     * get marker horizontal shape
     * 
     * @return horizontal cross shape
     */
    public Shape getShapeMarkerV() {
        return shapeMarkerV;
    }

    /**
     * set marker vertical shape
     * 
     * @param shapeMarkerV
     *            cross shape
     */
    public void setShapeMarkerV(Shape shapeMarkerV) {
        this.shapeMarkerV = shapeMarkerV;
    }

    /**
     * get marker horizontal shape
     * 
     * @return horizontal cross shape
     */
    public Shape getShapeMarkerH() {
        return shapeMarkerH;
    }

    /**
     * set marker horizontal shape
     * 
     * @param shapeMarkerH
     *            cross shape
     */
    public void setShapeMarkerH(Shape shapeMarkerH) {
        this.shapeMarkerH = shapeMarkerH;
    }

    /**
     * get cross color
     * 
     * @return cross color
     */
    public Color getCrossMarkerColor() {
        return crossMarkerColor;
    }

    /**
     * set cross color
     * 
     * @param crossMarkerColor
     *            the cross color to set
     */
    public void setCrossMarkerColor(Color crossMarkerColor) {
        this.crossMarkerColor = crossMarkerColor;
    }

    /**
     * paint cross marker
     * 
     * @param g2d
     *            graphics context
     */
    protected void paintCross(Graphics2D g2d) {

        if (getHost() == null || !getHost().isLockSelected()) {
            return;
        }

        Projection proj = getHost().getProjection();

        g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                             java.awt.AlphaComposite.SRC_OVER, 1.0f));

        Point2D targetP2dDevice = new Point2D.Double(getMarkerX(), getMarkerY());

        Point2D targetP2dUser = proj.pixelToUser(targetP2dDevice);

        g2d.setStroke(new BasicStroke());

        g2d.setColor(Color.RED);

        g2d.drawLine((int) getMarkerX(), 0, (int) getMarkerX(), proj
                .getDevice2D().getDeviceHeight());
        g2d.drawLine(0, (int) getMarkerY(), proj.getDevice2D()
                .getDeviceWidth(), (int) getMarkerY());

        g2d.drawString("X = " + (int) targetP2dUser.getX() + " , Y = "
                + (int) targetP2dUser.getY(), (int) getMarkerX() + 10,
                       (int) getMarkerY() - 10);

    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.marker.marker.AbstractMarker#paintMarker(org.jensoft.core.view.View, java.awt.Graphics2D)
     */
    @Override
    public final void paintMarker(View view, Graphics2D g2d) {
        paintCross(g2d);

    }

}
