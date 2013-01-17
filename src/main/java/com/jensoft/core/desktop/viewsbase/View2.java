/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.desktop.viewsbase;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.event.EventListenerList;

public class View2 extends JComponent {

    private String comandName;
    private int startX;
    private int widthMetrics;
    private Color tabColor = Color.DARK_GRAY;
    private boolean selected = false;
    private Shape tabShape;
    private Shape tabSensibleShape;
    private ImageIcon tabIcon;
    private int iconWidth;

    private JComponent component;

    public JComponent getComponent() {
        return component;
    }

    public void setComponent(JComponent component) {
        this.component = component;
    }

    private Shape cubicDeco;

    public Shape getCubicDeco() {
        return cubicDeco;
    }

    public void setCubicDeco(Shape cubicDeco) {
        this.cubicDeco = cubicDeco;
    }

    public int getIconWidth() {
        return iconWidth;
    }

    public void setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
    }

    public ImageIcon getTabIcon() {
        return tabIcon;
    }

    public void setTabIcon(ImageIcon tabIcon) {
        this.tabIcon = tabIcon;
    }

    private EventListenerList listenersList = new EventListenerList();

    public void addComandGroupListener(View2Litener listener) {
        listenersList.add(View2Litener.class, listener);
    }

    /**
     * fire listener of the window has changed
     */
    private void fireCommandGroupSelect() {
        View2Event cge = new View2Event(this);
        Object[] listeners = listenersList.getListenerList();

        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == View2Litener.class) {
                ((View2Litener) listeners[i + 1]).viewSelect(cge);
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
    }

    public Shape getSensibleTabShape() {
        return tabSensibleShape;
    }

    public void setSensibleTabShape(Shape tabSensibleShape) {
        this.tabSensibleShape = tabSensibleShape;
    }

    public Shape getTabShape() {
        return tabShape;
    }

    public void setTabShape(Shape tabShape) {
        this.tabShape = tabShape;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;

        if (selected) {
            fireCommandGroupSelect();
        }
    }

    public Color getTabColor() {
        return tabColor;
    }

    public void setTabColor(Color tabColor) {
        this.tabColor = tabColor;
    }

    public int getWidthMetrics() {
        return widthMetrics;
    }

    public void setWidthMetrics(int widthMetrics) {
        this.widthMetrics = widthMetrics;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    private int endX;

    public String getComandName() {
        return comandName;
    }

    public View2(String comandName) {
        this.comandName = comandName;
    }

}
