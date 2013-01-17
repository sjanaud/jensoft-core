/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.minidevice;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.minidevice.MiniDevicePlugin.DimensionType;
import com.jensoft.core.view.View2D;
import com.jensoft.core.widget.Widget;
import com.jensoft.core.window.WindowPart;

/**
 * the minidevice widget display the current activated window in initial scale
 * mode.
 */
public class MiniDeviceWidget extends Widget {

    public final static String ID = "@sw2d/widget/minidevice";

    public MiniDeviceWidget() {
        super(ID);
    }

    @Override
    public final boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof MiniDevicePlugin) {
            return true;
        }
        return false;
    }

    @Override
    protected void paintWidget(View2D v2d, Graphics2D g2d) {

        int miniWidth;
        int miniHeight;

        MiniDevicePlugin host = (MiniDevicePlugin) getHost();

        if (host.getDimentionType() == DimensionType.Ratio) {
            miniWidth = v2d.getDevice2D().getDeviceWidth()
                    / host.getMiniDeviceRatioX();
            miniHeight = v2d.getDevice2D().getDeviceHeight()
                    / host.getMiniDeviceRatioY();
        }
        else {
            miniWidth = host.getMiniDevicePixelX();
            miniHeight = host.getMiniDevicePixelY();
        }

        setWidth(miniWidth);
        setHeight(miniHeight);

        host.getPrivateWindow().setView2D(v2d);

        double minX = host.getPrivateWindow().getMinX();
        double maxX = host.getPrivateWindow().getMaxX();
        double minY = host.getPrivateWindow().getMinY();
        double maxY = host.getPrivateWindow().getMaxY();

        Point2D p2d1 = new Point2D.Double(minX, maxY);
        Point2D p2d2 = new Point2D.Double(minX, minY);
        Point2D p2d3 = new Point2D.Double(maxX, minY);
        Point2D p2d4 = new Point2D.Double(maxX, maxY);

        host.getPrivateWindow().setDevice2D(host);
        // host.getPrivateWindow().setWindowScaleMode(
        // Window2D.WINDOW_SCALEMODE_DEVICE_INITIAL);

        Point2D p2d1D = host.getPrivateWindow().userToPixel(p2d1);
        Point2D p2d2D = host.getPrivateWindow().userToPixel(p2d2);
        Point2D p2d3D = host.getPrivateWindow().userToPixel(p2d3);
        Point2D p2d4D = host.getPrivateWindow().userToPixel(p2d4);
        // host.getPrivateWindow().setWindowScaleMode(
        // Window2D.WINDOW_SCALEMODE_DEFAULT);

        double uWidth = p2d4D.getX() - p2d1D.getX();
        double uHeight = p2d3D.getY() - p2d4D.getY();

        g2d.setStroke(new BasicStroke());
        // INITIAL BOX
        Rectangle2D rect2DMiniDevice = new Rectangle2D.Double(
                                                              host.getOriginX(), host.getOriginY(),
                                                              host.getDeviceWidth(),
                                                              host.getDeviceHeight());

        // g2d.setComposite(java.awt.AlphaComposite.getInstance(
        // java.awt.AlphaComposite.SRC_OVER, 0.3f));
        //
        // if(host.getMiniInitialDeviceDrawColor() != null)
        // g2d.setColor(host.getMiniInitialDeviceDrawColor());
        // else
        // g2d.setColor(getThemeColor().darker());
        //
        //
        // g2d.setComposite(java.awt.AlphaComposite.getInstance(
        // java.awt.AlphaComposite.SRC_OVER, 0f));
        // g2d.fill(rect2DMiniDevice);
        // g2d.setComposite(java.awt.AlphaComposite.getInstance(
        // java.awt.AlphaComposite.SRC_OVER, 1f));
        // g2d.draw(rect2DMiniDevice);

        Rectangle2D rect2DZone = null;

        int offset = 10;
        boolean busy1 = false;
        if (uWidth > Toolkit.getDefaultToolkit().getScreenSize().getWidth()
                && uHeight < Toolkit.getDefaultToolkit().getScreenSize()
                        .getHeight()) {
            rect2DZone = new Rectangle2D.Double(-offset, p2d1D.getY(), v2d
                    .getDevice2D().getDeviceWidth() + 2 * offset, uHeight);
            busy1 = true;
        }
        else if (uWidth < Toolkit.getDefaultToolkit().getScreenSize()
                .getWidth()
                && uHeight > Toolkit.getDefaultToolkit().getScreenSize()
                        .getHeight()) {
            rect2DZone = new Rectangle2D.Double(p2d1D.getX(), -offset, uWidth,
                                                v2d.getDevice2D().getDeviceHeight() + 2 * offset);
            busy1 = true;
        }
        else if (uWidth > Toolkit.getDefaultToolkit().getScreenSize()
                .getWidth()
                && uHeight > Toolkit.getDefaultToolkit().getScreenSize()
                        .getHeight()) {
            rect2DZone = new Rectangle2D.Double(-offset, -offset, v2d
                    .getDevice2D().getDeviceWidth() + 2 * offset, v2d
                    .getDevice2D().getDeviceHeight() + 2 * offset);
            busy1 = true;
        }
        else if (uWidth < Toolkit.getDefaultToolkit().getScreenSize()
                .getWidth()
                && uHeight < Toolkit.getDefaultToolkit().getScreenSize()
                        .getHeight()) {
            rect2DZone = new Rectangle2D.Double(p2d1D.getX(), p2d1D.getY(),
                                                uWidth, uHeight);
            busy1 = false;
        }

        if (rect2DZone == null) {
            return;
        }

        boolean busy2 = false;

        if (rect2DMiniDevice.contains(rect2DZone.getBounds2D())) {

            double t2W = rect2DMiniDevice.getWidth() / rect2DZone.getWidth();
            double t2H = rect2DMiniDevice.getHeight() / rect2DZone.getHeight();
            if (t2W > 20 || t2H > 20) {
                busy2 = true;
            }
        }

        if (!busy1 && !busy2) {
            g2d.setColor(getThemeColor().brighter());

            g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                 java.awt.AlphaComposite.SRC_OVER, 0.1f));
            g2d.fill(rect2DZone);
            g2d.setComposite(java.awt.AlphaComposite.getInstance(
                                                                 java.awt.AlphaComposite.SRC_OVER, 1f));
            g2d.draw(rect2DZone);
            g2d.setColor(Color.GREEN);

        }
        else {

            g2d.setFont(new Font("Tahoma", Font.PLAIN, 10));
            g2d.setColor(Color.WHITE);
            g2d.drawString("BUSY", (int) rect2DMiniDevice.getX() + 5,
                           (int) rect2DMiniDevice.getY() + 10);
        }

        List<AbstractPlugin> layouts = host.getWindow2D().getPluginRegistry();
        for (AbstractPlugin layout : layouts) {
            if (!layout.equals(this)) {
                System.out.println("paint layout in mini device : "
                        + layout.getName());
                host.getPrivateWindow().setDevice2D(host);
                // host.getPrivateWindow().setWindowScaleMode(
                // Window2D.WINDOW_SCALEMODE_DEVICE_INITIAL);
                layout.setWindow2D(host.getPrivateWindow());
                layout.paint(v2d, g2d, WindowPart.Device);
                layout.setWindow2D(v2d.getActiveWindow());
                // host.getPrivateWindow().setWindowScaleMode(
                // Window2D.WINDOW_SCALEMODE_DEFAULT);
            }
        }

    }

}
