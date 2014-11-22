/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.zoom.percent;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>ZoomPercentPlugin</code>
 * 
 * @author sebastien janaud
 */
public class ZoomPercentPlugin extends AbstractPlugin implements
        AbstractPlugin.OnMoveListener, AbstractPlugin.OnPressListener,
        AbstractPlugin.OnReleaseListener {

    public enum ZoomPercent {
        Init(-1), Zoom10(0.1f), Zoom25(0.25f), Zoom50(0.5f), Zoom200(2f), Zoom400(
                4f), Zoom800(8f);

        private float percent;

        private ZoomPercent(float percent) {
            this.percent = percent;
        }

        /**
         * @return the percent
         */
        public float getPercent() {
            return percent;
        }

    }

    public ZoomPercentPlugin() {
        setName("ZoomPercentPlugin");
        setPriority(100);
        setAntialiasing(Antialiasing.On);
    }

    public void zoomInit() {
        if (getProjection() instanceof Projection.Linear) {
            Projection.Linear wl = (Projection.Linear) getProjection();
            double minx = wl.getInitialMinX();
            double maxx = wl.getInitialMaxX();

            double miny = wl.getInitialMinY();
            double maxy = wl.getInitialMaxY();

            wl.bound(minx, maxx, miny, maxy);
        }

    }

    /**
     * get a zoom initialize action
     * 
     * @return zoom initialize action
     */
    public ZoomInitAction getZoomInitAction() {
        return new ZoomInitAction();
    }

    class ZoomInitAction implements ActionListener {

        public ZoomInitAction() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    zoomInit();
                }
            });
        }

    }

    /**
     * get new zoom percent action with specified percent
     * 
     * @param zoomPercent
     *            the zoom percent to set
     * @return new zoom percent action
     */
    public ZoomPercentAction getZoomPercentAction(ZoomPercent zoomPercent) {
        return new ZoomPercentAction(zoomPercent);
    }

    /**
     * ZoomPercentAction
     */
    public class ZoomPercentAction implements ActionListener {

        /** zoom percent */
        private ZoomPercent zoomPercent;

        /**
         * create zoom percent action with specified percent
         * 
         * @param zoomPercent
         *            the percent to set
         */
        public ZoomPercentAction(ZoomPercent zoomPercent) {
            this.zoomPercent = zoomPercent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (getProjection() instanceof Projection.Linear) {
                Projection.Linear wl = (Projection.Linear) getProjection();
                if (zoomPercent != ZoomPercent.Init) {

                    float percentFactor = zoomPercent.getPercent();

                    Point2D center = wl.getUserCenter();

                    double width = getProjection().getUserWidth();
                    double partX = width / 2;
                    double minx = center.getX() - partX / percentFactor;
                    double maxx = center.getX() + partX / percentFactor;

                    double height = getProjection().getUserHeight();
                    double partY = height / 2;
                    double miny = center.getY() - partY / percentFactor;
                    double maxy = center.getY() + partY / percentFactor;

                    wl.bound(minx, maxx, miny, maxy);
                    getProjection().getView2D().repaint();
                }
                else {
                    zoomInit();
                    getProjection().getView2D().repaint();
                }
            }

        }

    }

    @Override
    public void onRelease(MouseEvent me) {
        percent25BoxRollover = false;
        percent25BoxLock = false;
        percent50BoxRollover = false;
        percent50BoxLock = false;
        percent100BoxRollover = false;
        percent100BoxLock = false;

        getView().repaintDevice();

    }

    @Override
    public void onPress(MouseEvent me) {
        Point2D p2d = new Point2D.Double(me.getX(), me.getY());

        if (percent25Box.contains(p2d) && percent25BoxRollover) {
            percent25BoxLock = true;
        }
        else {
            percent25BoxLock = false;
        }

        if (percent50Box.contains(p2d) && percent50BoxRollover) {
            percent50BoxLock = true;
        }
        else {
            percent50BoxLock = false;
        }

        if (percent100Box.contains(p2d) && percent100BoxRollover) {
            percent100BoxLock = true;
        }
        else {
            percent100BoxLock = false;
        }

        getView().repaintDevice();

    }

    @Override
    public void onMove(MouseEvent me) {
        Point2D p2d = new Point2D.Double(me.getX(), me.getY());
        if (percent25Box.contains(p2d)) {
            percent25BoxRollover = true;
        }
        else {
            percent25BoxRollover = false;
        }

        if (percent50Box.contains(p2d)) {
            percent50BoxRollover = true;
        }
        else {
            percent50BoxRollover = false;
        }

        if (percent100Box.contains(p2d)) {
            percent100BoxRollover = true;
        }
        else {
            percent100BoxRollover = false;
        }

        getView().repaintDevice();

    }

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }
    }

    private Rectangle2D dragBox;
    int dragBoxXmargin = 4;
    int dragBoxYmargin = 2;
    private double dragBoxX;
    private double dragBoxY;
    Rectangle2D percent25Box;
    boolean percent25BoxRollover = false;
    boolean percent25BoxLock = false;
    Rectangle2D percent50Box;
    boolean percent50BoxRollover = false;
    boolean percent50BoxLock = false;
    Rectangle2D percent100Box;
    boolean percent100BoxRollover = false;
    boolean percent100BoxLock = false;

    private void drawDragBox(Graphics2D g2d) {
        int dw = getProjection().getDevice2D().getDeviceWidth();
        int dh = getProjection().getDevice2D().getDeviceHeight();

        int dragBoxW = 100;
        int dragBoxH = 40;

        dragBox = new Rectangle2D.Double(dragBoxXmargin, dh - dragBoxH
                - dragBoxYmargin, dragBoxW, dragBoxH);
        g2d.setColor(getThemeColor().darker());
        // g2d.draw(dragBox);

        int widthBar = 8;
        int interCell = 2;
        percent25Box = new Rectangle2D.Double(dragBoxXmargin, dh - 10
                - dragBoxYmargin, widthBar, 10);
        if (percent25BoxRollover) {
            g2d.setColor(getThemeColor().brighter());
        }
        else {
            g2d.setColor(getThemeColor().darker());
        }
        g2d.fill(percent25Box);

        percent50Box = new Rectangle2D.Double(dragBoxXmargin + widthBar
                + interCell, dh - 20 - dragBoxYmargin, widthBar, 20);
        if (percent50BoxRollover) {
            g2d.setColor(getThemeColor().brighter());
        }
        else {
            g2d.setColor(getThemeColor().darker());
        }
        g2d.fill(percent50Box);

        percent100Box = new Rectangle2D.Double(dragBoxXmargin + 2 * widthBar
                + 2 * interCell, dh - 30 - dragBoxYmargin, widthBar, 30);
        if (percent100BoxRollover) {
            g2d.setColor(getThemeColor().brighter());
        }
        else {
            g2d.setColor(getThemeColor().darker());
        }
        g2d.fill(percent100Box);

    }

}
