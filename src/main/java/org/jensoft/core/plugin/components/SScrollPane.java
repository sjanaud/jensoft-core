/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.components;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import org.jensoft.core.catalog.source.DemoScrollbarUI;

public class SScrollPane extends JScrollPane implements DeviceComponent {

    private Point2D userLocation;
    private JTextArea area;

    public SScrollPane(Point2D userLocation) {
        this.userLocation = userLocation;
        area = new JTextArea();
        area.setOpaque(false);
        area.setText("ububbb  hhhuhhhu ");

        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setViewport(createViewport());
        getViewport().setOpaque(false);
        setOpaque(false);
        setVerticalScrollBar(createVerticalScrollBar());
        setHorizontalScrollBar(createHorizontalScrollBar());
        getVerticalScrollBar().setUI(new DemoScrollbarUI());
        getHorizontalScrollBar().setUI(new DemoScrollbarUI());

        // setSize(150, 40);
        setPreferredSize(new Dimension(100, 100));

        setViewportView(area);

        if (!getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }

        updateUI();

    }

    public void setUserLocation(Point userLocation) {
        this.userLocation = userLocation;
    }

    public JTextArea getArea() {
        return area;
    }

    public void setArea(JTextArea area) {
        this.area = area;
    }

    public void setText(String text) {
        area.setText(text);
    }

    public int getUserX() {
        return (int) userLocation.getX();
    }

    public void setUserX(int userX) {
        userLocation.setLocation(userX, getUserY());
    }

    public int getUserY() {
        return (int) userLocation.getY();
    }

    public void setUserY(int userY) {
        userLocation.setLocation(getUserX(), userY);
    }

    @Override
    public Point2D getUserLocation() {
        return userLocation;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

}
