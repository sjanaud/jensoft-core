/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.jensoft.core.plugin.symbol.SymbolPlugin.PaintRequest;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import com.jensoft.core.plugin.symbol.painter.point.AbstractPointSymbolPainter;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * Point layer handles point symbol layout and related point symbol properties
 * 
 * @author Sebastien janaud
 */
public class PointSymbolLayer extends SymbolLayer<PointSymbol> {

    /** symbol listeners */
    private EventListenerList symbolListenerList;

    /**
     * create empty point symbol layer
     */
    public PointSymbolLayer() {
        symbolListenerList = new EventListenerList();
    }

    /**
     * add point listener
     * 
     * @param listener
     *            the point listener to add
     */
    public void addPointListener(PointListener listener) {
        symbolListenerList.add(PointListener.class, listener);
    }

    /**
     * remove point listener
     * 
     * @param listener
     *            the point listener to remove
     */
    public void removePointListener(PointListener listener) {
        symbolListenerList.remove(PointListener.class, listener);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#getFlattenSymbolComponents()
     */
    @Override
    public List<PointSymbol> getFlattenSymbolComponents() {
        List<PointSymbol> flattenSymbolComponents = new ArrayList<PointSymbol>();

        // polyline does not contribute to contraints
        for (PointSymbol comp : getSymbols()) {
            if (comp instanceof PointSymbol
                    && !(comp instanceof PolylinePointSymbol)) {
                flattenSymbolComponents.add(comp);
            }
        }

        return flattenSymbolComponents;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#paintLayer(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart, com.jensoft.core.plugin.symbol.SymbolPlugin.PaintRequest)
     */
    @Override
    public void paintLayer(View v2d, Graphics2D g2d, ViewPart viewPart,
            PaintRequest paintRequest) {

        if (viewPart == ViewPart.Device && paintRequest == PaintRequest.SymbolLayer) {
            for (PointSymbol ps : getSymbols()) {

                if (!ps.isFiller()) {

                    if (ps instanceof PointSymbol && !(ps instanceof PolylinePointSymbol)) {
                        if (ps.getPointSymbolPainters() != null) {
                            for (AbstractPointSymbolPainter painter : ps.getPointSymbolPainters()) {
                                painter.paintSymbol(g2d, ps, viewPart);
                            }

                        }
                    }
                    else if (ps instanceof PolylinePointSymbol) {
                        if (((PolylinePointSymbol) ps).getPolylinePainter() != null) {
                            ((PolylinePointSymbol) ps).getPolylinePainter().paintSymbol(g2d, ps, viewPart);
                        }
                    }

                }
            }
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#solveSymbolComponent(com.jensoft.core.plugin.symbol.SymbolComponent)
     */
    @Override
    public void solveSymbolComponent(PointSymbol symbol) {

        if (symbol.isFiller()) {
            return;
        }
        if (getHost().getNature() == SymbolNature.Vertical) {
            solveVSymbolComponent(symbol);
        }
        if (getHost().getNature() == SymbolNature.Horizontal) {
            solveHSymbolComponent(symbol);
        }

    }

    /**
     * solve vertical component geometry
     * 
     * @param symbol
     *            the component to solve
     */
    private void solveVSymbolComponent(SymbolComponent symbol) {
        symbol.setNature(SymbolNature.Vertical);
        if (symbol instanceof PointSymbol && !(symbol instanceof PolylinePointSymbol)) {
            solveVPointSymbol((PointSymbol) symbol);
        }
        else if (symbol instanceof PolylinePointSymbol) {
            // does not solving for polyline
        }
    }

    /**
     * solve the specified horizontal component
     * 
     * @param symbol
     *            the bar component to solve
     */
    private void solveHSymbolComponent(SymbolComponent symbol) {
        symbol.setNature(SymbolNature.Horizontal);
        if (symbol instanceof PointSymbol && !(symbol instanceof PolylinePointSymbol)) {
            solveHPointSymbol((PointSymbol) symbol);
        }
        else if (symbol instanceof PolylinePointSymbol) {
            // does not solving for polyline
        }

    }

    /**
     * solve symbol points for vertical nature
     * 
     * @param pointSymbol
     *            the symbol point to solve
     */
    private void solveVPointSymbol(PointSymbol pointSymbol) {
        Projection w2d = getHost().getProjection();
        pointSymbol.setHost(getHost());
        Point2D p2dUser = new Point2D.Double(0, pointSymbol.getValue());
        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        pointSymbol.setDeviceValue(p2ddevice.getY());

        double x = getComponentXLocation(pointSymbol);

        Point2D devicePoint = new Point2D.Double(x, p2ddevice.getY());
        Rectangle2D rectangle = new Rectangle.Double(devicePoint.getX() - pointSymbol.getSensibleRadius(),
                                                     devicePoint.getY() - pointSymbol.getSensibleRadius(),
                                                     2 * pointSymbol.getSensibleRadius(),
                                                     2 * pointSymbol.getSensibleRadius());
        pointSymbol.setSensibleShape(rectangle);
        pointSymbol.setDevicePoint(devicePoint);
    }

    /**
     * solve symbol points for horizontal nature
     * 
     * @param pointSymbol
     *            the symbol point to solve
     */
    private void solveHPointSymbol(PointSymbol pointSymbol) {
        Projection w2d = getHost().getProjection();
        pointSymbol.setHost(getHost());
        Point2D p2dUser = new Point2D.Double(pointSymbol.getValue(), 0);
        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        pointSymbol.setDeviceValue(p2ddevice.getX());

        double y = getComponentYLocation(pointSymbol);

        pointSymbol.setDevicePoint(new Point2D.Double(p2ddevice.getX(),
                                                      pointSymbol.getLocationY()));
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        List<PointSymbol> points = getSymbols();
        for (SymbolComponent symbolComponent : points) {
            if (symbolComponent instanceof PointSymbol) {
                if (((PointSymbol) symbolComponent).getSensibleShape() != null
                        && ((PointSymbol) symbolComponent).getSensibleShape()
                                .contains(me.getX(), me.getY())
                        && ((PointSymbol) symbolComponent).isLockEnter()) {
                    fireBarReleased((PointSymbol) symbolComponent);
                }
            }

        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {
        List<PointSymbol> points = getSymbols();
        for (SymbolComponent symbolComponent : points) {
            if (symbolComponent instanceof PointSymbol) {
                if (((PointSymbol) symbolComponent).getSensibleShape() != null
                        && ((PointSymbol) symbolComponent).getSensibleShape()
                                .contains(me.getX(), me.getY())
                        && ((PointSymbol) symbolComponent).isLockEnter()) {
                    fireBarPressed((PointSymbol) symbolComponent);
                }
            }

        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onExit(java.awt.event.MouseEvent)
     */
    @Override
    public void onExit(MouseEvent me) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onEnter(java.awt.event.MouseEvent)
     */
    @Override
    public void onEnter(MouseEvent me) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onClick(java.awt.event.MouseEvent)
     */
    @Override
    public void onClick(MouseEvent me) {
        List<PointSymbol> bars = getSymbols();
        for (SymbolComponent symbolComponent : bars) {
            if (symbolComponent instanceof PointSymbol) {
                if (((PointSymbol) symbolComponent).getSensibleShape() != null
                        && ((PointSymbol) symbolComponent).getSensibleShape()
                                .contains(me.getX(), me.getY())
                        && ((PointSymbol) symbolComponent).isLockEnter()) {
                    fireBarClicked((PointSymbol) symbolComponent);
                }
            }

        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {

        List<PointSymbol> bars = getSymbols();
        for (SymbolComponent symbolComponent : bars) {
            if (symbolComponent instanceof PointSymbol) {
                barEnterExitTracker((PointSymbol) symbolComponent, me.getX(),
                                    me.getY());
            }

        }
    }

    /**
     * track bar enter or exit for the specified bar for device location x,y
     * 
     * @param point
     *            the point to track
     * @param x
     *            the x in device coordinate
     * @param y
     *            the y in device coordinate
     */
    private void barEnterExitTracker(PointSymbol point, int x, int y) {

        if (point.getSensibleShape() == null) {
            return;
        }

        if (point.getSensibleShape().contains(x, y) && !point.isLockEnter()) {
            point.lockEnter();
            fireBarEntered(point);
        }
        else if (!point.getSensibleShape().contains(x, y) && point.isLockEnter()) {
            point.unlockEnter();
            fireBarExited(point);
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        List<PointSymbol> bars = getSymbols();
        for (SymbolComponent symbolComponent : bars) {
            if (symbolComponent instanceof PointSymbol) {
                barEnterExitTracker((PointSymbol) symbolComponent, me.getX(), me.getY());
            }
        }
    }

    /**
     * fire point entered
     * 
     * @param point
     *            the point entered to fire
     */
    private void fireBarEntered(PointSymbol point) {
        Object[] listeners = symbolListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PointListener.class) {
                    ((PointListener) listeners[i + 1])
                            .pointSymbolEntered(new PointEvent(point));
                }
            }
        }
    }

    /**
     * fire point exited
     * 
     * @param point
     *            the point exited to fire
     */
    private void fireBarExited(PointSymbol point) {
        Object[] listeners = symbolListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PointListener.class) {
                    ((PointListener) listeners[i + 1])
                            .pointSymbolExited(new PointEvent(point));
                }
            }
        }
    }

    /**
     * fire point clicked
     * 
     * @param point
     *            the point clicked to fire
     */
    private void fireBarClicked(PointSymbol point) {
        Object[] listeners = symbolListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PointListener.class) {
                    ((PointListener) listeners[i + 1])
                            .pointSymbolClicked(new PointEvent(point));
                }
            }
        }
    }

    /**
     * fire point pressed
     * 
     * @param point
     *            the point pressed to fire
     */
    private void fireBarPressed(PointSymbol point) {
        Object[] listeners = symbolListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PointListener.class) {
                    ((PointListener) listeners[i + 1])
                            .pointSymbolPressed(new PointEvent(point));
                }
            }
        }
    }

    /**
     * fire point released
     * 
     * @param point
     *            the point released to fire
     */
    private void fireBarReleased(PointSymbol point) {
        Object[] listeners = symbolListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PointListener.class) {
                    ((PointListener) listeners[i + 1])
                            .pointSymbolReleased(new PointEvent(point));
                }
            }
        }
    }

}
