/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.jensoft.core.plugin.symbol.BarSymbol.MorpheStyle;
import com.jensoft.core.plugin.symbol.SymbolPlugin.PaintRequest;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * Bar layer handles bar symbol layout and related bar properties
 * 
 * @author Sebastien janaud
 */
public class BarSymbolLayer extends SymbolLayer<BarSymbol> {

    /** symbol listeners */
    private EventListenerList symbolListenerList;

    /**
     * create bar symbol layer
     */
    public BarSymbolLayer() {
        symbolListenerList = new EventListenerList();
    }

    /**
     * add bar listener
     * 
     * @param listener
     *            the bar listener to add
     */
    public void addBarListener(BarListener listener) {
        symbolListenerList.add(BarListener.class, listener);
    }

    /**
     * remove bar listener
     * 
     * @param listener
     *            the bar listener to remove
     */
    public void removeBarListener(BarListener listener) {
        symbolListenerList.remove(BarListener.class, listener);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#getFlattenSymbolComponents()
     */
    @Override
    public List<BarSymbol> getFlattenSymbolComponents() {
        List<BarSymbol> flattenSymbolComponents = new ArrayList<BarSymbol>();
        for (BarSymbol comp : getSymbols()) {
            if (comp instanceof BarSymbol && !(comp instanceof BarSymbolGroup)) {
                flattenSymbolComponents.add(comp);
            }
            else if (comp instanceof BarSymbolGroup) {
                flattenSymbolComponents.addAll(((BarSymbolGroup) comp).getSymbolComponents());
            }
        }
        return flattenSymbolComponents;
    }


    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#paintLayer(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart, com.jensoft.core.plugin.symbol.SymbolPlugin.PaintRequest)
     */
    @Override
    public final void paintLayer(View v2d, Graphics2D g2d,
            ViewPart viewPart, PaintRequest paintRequest) {
        paintSymbols(g2d, getSymbols(), viewPart, paintRequest);
    }

    /**
     * paint bars symbols
     * 
     * @param g2d
     *            the graphics context to paint
     * @param symbols
     *            the symbols components to paint
     */
    private void paintSymbols(Graphics2D g2d, List<BarSymbol> symbols,
            ViewPart viewPart, PaintRequest paintRequest) {

        solveGeometry();
        if (viewPart == ViewPart.Device) {
            for (BarSymbol symbol : symbols) {
                if (symbol instanceof BarSymbol) {
                    if (symbol instanceof BarSymbolGroup) {
                        paintGroup(g2d, (BarSymbolGroup) symbol, viewPart,
                                   paintRequest);
                    }
                    else if (symbol instanceof StackedBarSymbol) {
                        paintBarStacked(g2d, (StackedBarSymbol) symbol,
                                        viewPart, paintRequest);
                    }
                    else {
                        paintBar(g2d, symbol, viewPart,
                                 paintRequest);
                    }
                }
            }
        }

        if (viewPart != ViewPart.Device
                && paintRequest == PaintRequest.LabelLayer) {
            paintSymbolsAxisLabel(g2d, symbols, viewPart);
        }
    }

    /**
     * paint group
     * 
     * @param g2d
     *            the graphics to paint
     * @param barGroup
     *            the bar group to paint
     */
    private void paintGroup(Graphics2D g2d, BarSymbolGroup barGroup,
            ViewPart viewPart, PaintRequest paintRequest) {
        barGroup.setHost(getHost());
        barGroup.setLayer(this);

        // paint only the label for group
        if (paintRequest == PaintRequest.LabelLayer) {
            paintBar(g2d, barGroup, viewPart, paintRequest);
        }

        // paint children of this group
        List<BarSymbol> barSymbolComponents = barGroup.getSymbolComponents();
        paintSymbols(g2d, barSymbolComponents, viewPart, paintRequest);
    }

    /**
     * paint bars axis symbols
     * 
     * @param g2d
     *            the graphics context to paint
     * @param barSymbolComponents
     *            the symbols components to paint
     * @param viewPart
     *            the window zone to paint
     */
    private void paintSymbolsAxisLabel(Graphics2D g2d,
            List<BarSymbol> barSymbolComponents, ViewPart viewPart) {

        for (SymbolComponent barComponent : barSymbolComponents) {
            barComponent.setHost(getHost());
            if (barComponent instanceof BarSymbol) {
                if (barComponent instanceof BarSymbolGroup) {
                    BarSymbolGroup barGroup = (BarSymbolGroup) barComponent;
                    List<BarSymbol> groupBarSymbolComponents = barGroup
                            .getSymbolComponents();

                    paintSymbolsAxisLabel(g2d, groupBarSymbolComponents,
                                          viewPart);

                    if (barGroup.getAxisLabel() != null) {
                        barGroup.getAxisLabel().paintSymbol(g2d, barGroup,
                                                            viewPart);
                    }
                }
                else {// simple symbol or stackedSymbol
                    paintBarAxisLabel(g2d, (BarSymbol) barComponent, viewPart);
                }
            }

        }
    }

    /**
     * paint bars axis symbols
     * 
     * @param g2d
     *            the graphics context to paint
     * @param barSymbol
     *            the symbol component
     * @param viewPart
     */
    private void paintBarAxisLabel(Graphics2D g2d, BarSymbol barSymbol,
            ViewPart viewPart) {
        if (barSymbol.getAxisLabel() != null) {
            barSymbol.getAxisLabel().paintSymbol(g2d, barSymbol, viewPart);
        }
    }

    /**
     * paint bar
     * 
     * @param g2d
     *            the graphics context to paint
     * @param bar
     *            the bar to paint
     */
    private void paintBar(Graphics2D g2d, BarSymbol bar, ViewPart viewPart,
            PaintRequest paintRequest) {

        bar.setHost(getHost());
        bar.setLayer(this);

        if (paintRequest == PaintRequest.SymbolLayer) {
            if (bar.getBarFill() != null) {
                bar.getBarFill().paintSymbol(g2d, bar, viewPart);
            }
            if (bar.getBarEffect() != null) {
                bar.getBarEffect().paintSymbol(g2d, bar, viewPart);
            }
            if (bar.getBarDraw() != null) {
                bar.getBarDraw().paintSymbol(g2d, bar, viewPart);
            }
        }
        else if (paintRequest == PaintRequest.LabelLayer) {
            if (bar.getBarLabel() != null) {
                bar.getBarLabel().paintSymbol(g2d, bar, viewPart);
            }
        }

    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#solveSymbolComponent(com.jensoft.core.plugin.symbol.SymbolComponent)
     */
    @Override
    public void solveSymbolComponent(BarSymbol symbol) {

        if (symbol.isFiller()) {
            return;
        }
        symbol.setLayer(this);
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

        if (symbol.isFiller()) {
            return;
        }

        symbol.setNature(SymbolNature.Vertical);
        symbol.setLayer(this);
        if (symbol instanceof BarSymbolGroup) {
            solveVBarGroup((BarSymbolGroup) symbol);
        }
        else if (symbol instanceof StackedBarSymbol) {
            solveVStackedBar((StackedBarSymbol) symbol);
        }
        else {
            solveVBarSymbol((BarSymbol) symbol);
        }
    }

    /**
     * solve vertical bar
     * 
     * @param bar
     *            the bar to solve
     */
    private void solveVBarSymbol(BarSymbol bar) {
        if (getHost() == null || getHost().getProjection() == null) {
            return;
        }
        bar.setHost(getHost());
        Projection w2d = getHost().getProjection();
        Point2D p2dUser = null;
        if (bar.isAscent()) {
            p2dUser = new Point2D.Double(0, bar.getBase() + bar.getValue());
        }
        if (bar.isDescent()) {
            p2dUser = new Point2D.Double(0, bar.getBase() - bar.getValue());
        }
        if (!bar.isValueSet()) {
            throw new IllegalArgumentException(
                                               "bar symbol ascent or descent value should be supplied.");
        }
        if (!bar.isBaseSet()) {
            throw new IllegalArgumentException(
                                               "bar symbol base value should be supplied.");
        }

        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        Point2D p2dUserBase = new Point2D.Double(0, bar.getBase());
        Point2D p2ddeviceBase = w2d.userToPixel(p2dUserBase);

        double x = getComponentXLocation(bar);
        double y = p2ddevice.getY();
        if (bar.isAscent()) {
            y = p2ddevice.getY();
        }
        if (bar.isDescent()) {
            y = p2ddeviceBase.getY();
        }
        double width = bar.getThickness();
        double height = Math.abs(p2ddeviceBase.getY() - p2ddevice.getY());

        if (bar.getMorpheStyle() == MorpheStyle.Round) {
            double round = bar.getRound();
            GeneralPath barPath = new GeneralPath();
            if (bar.isAscent()) {
                barPath.moveTo(x, y + round);
                barPath.lineTo(x, y + height);
                barPath.lineTo(x + width, y + height);
                barPath.lineTo(x + width, y + round);
                barPath.quadTo(x + width, y, x + width - round, y);
                barPath.lineTo(x + round, y);
                barPath.quadTo(x, y, x, y + round);
                barPath.closePath();

            }
            else if (bar.isDescent()) {
                barPath.moveTo(x, y);
                barPath.lineTo(x, y + height - round);
                barPath.quadTo(x, y + height, x + round, y + height);
                barPath.lineTo(x + width - round, y + height);
                barPath.quadTo(x + width, y + height, x + width, y + height
                        - round);
                barPath.lineTo(x + width, y);
                barPath.closePath();

            }
            bar.setBarShape(barPath);
        }
        else if (bar.getMorpheStyle() == MorpheStyle.Rectangle) {

            Rectangle2D barRec = new Rectangle2D.Double(x, y, width, height);
            bar.setBarShape(barRec);
        }

    }

    /**
     * solve the specified vertical stacked bar
     * 
     * @param stackedBar
     *            the stacked bar to solve
     */
    private void solveVStackedBar(StackedBarSymbol stackedBar) {
        if (getHost() == null || getHost().getProjection() == null) {
            return;
        }
        stackedBar.setHost(getHost());
        stackedBar.normalize();
        Projection w2d = getHost().getProjection();
        Point2D p2dUser = null;
        if (stackedBar.isAscent()) {
            p2dUser = new Point2D.Double(0, stackedBar.getBase()
                    + stackedBar.getValue());
        }
        if (stackedBar.isDescent()) {
            p2dUser = new Point2D.Double(0, stackedBar.getBase()
                    - stackedBar.getValue());
        }
        if (!stackedBar.isValueSet()) {
            throw new IllegalArgumentException(
                                               "stacked bar symbol ascent or descent value should be supplied.");
        }
        if (!stackedBar.isBaseSet()) {
            throw new IllegalArgumentException(
                                               "stacked bar symbol base value should be supplied.");
        }

        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        Point2D p2dUserBase = new Point2D.Double(0, stackedBar.getBase());
        Point2D p2ddeviceBase = w2d.userToPixel(p2dUserBase);

        double x = getComponentXLocation(stackedBar);
        double y = p2ddevice.getY();
        if (stackedBar.isAscent()) {
            y = p2ddevice.getY();
        }
        if (stackedBar.isDescent()) {
            y = p2ddeviceBase.getY();
        }

        double width = stackedBar.getThickness();
        double height = Math.abs(p2ddeviceBase.getY() - p2ddevice.getY());

        if (stackedBar.getMorpheStyle() == MorpheStyle.Round) {
            double round = stackedBar.getRound();
            if (stackedBar.isAscent()) {
                GeneralPath barPath = new GeneralPath();
                barPath.moveTo(x, y + round);
                barPath.lineTo(x, y + height);
                barPath.lineTo(x + width, y + height);
                barPath.lineTo(x + width, y + round);
                barPath.quadTo(x + width, y, x + width - round, y);
                barPath.lineTo(x + round, y);
                barPath.quadTo(x, y, x, y + round);
                barPath.closePath();
                stackedBar.setBarShape(barPath);
            }
            else if (stackedBar.isDescent()) {
                GeneralPath barPath = new GeneralPath();
                barPath.moveTo(x, y);
                barPath.lineTo(x, y + height - round);
                barPath.quadTo(x, y + height, x + round, y + height);
                barPath.lineTo(x + width - round, y + height);
                barPath.quadTo(x + width, y + height, x + width, y + height
                        - round);
                barPath.lineTo(x + width, y);
                barPath.closePath();
                stackedBar.setBarShape(barPath);
            }
        }
        else if (stackedBar.getMorpheStyle() == MorpheStyle.Rectangle) {

            Rectangle2D barRec = new Rectangle2D.Double(x, y, width, height);
            stackedBar.setBarShape(barRec);
        }

        List<Stack> stacks = stackedBar.getStacks();

        int count = 0;
        for (Stack stack : stacks) {

            // data from host stacked bar
            stack.setThickness(stackedBar.getThickness());
            stack.setBase(stackedBar.getStackBase(stack));
            stack.setNature(stackedBar.getNature());
            stack.setBarFill(stackedBar.getBarFill());

            if (stackedBar.isAscent()) {
                stack.setAscentValue(stack.getNormalizedValue());
            }
            else if (stackedBar.isDescent()) {
                stack.setDescentValue(stack.getNormalizedValue());
            }

            Point2D stackedp2dUser = null;
            if (stackedBar.isAscent()) {
                stackedp2dUser = new Point2D.Double(0,
                                                    stackedBar.getStackBase(stack)
                                                            + stack.getNormalizedValue());
            }
            else if (stackedBar.isDescent()) {
                stackedp2dUser = new Point2D.Double(0,
                                                    stackedBar.getStackBase(stack)
                                                            - stack.getNormalizedValue());
            }

            Point2D stackedp2ddevice = w2d.userToPixel(stackedp2dUser);

            Point2D stackedp2dUserBase = new Point2D.Double(0,
                                                            stackedBar.getStackBase(stack));
            Point2D stackedp2ddeviceBase = w2d.userToPixel(stackedp2dUserBase);

            double stackedx = getComponentXLocation(stackedBar);
            double stackedy = stackedp2ddevice.getY();
            if (stackedBar.isAscent()) {
                stackedy = stackedp2ddevice.getY();
            }
            if (stackedBar.isDescent()) {
                stackedy = stackedp2ddeviceBase.getY();
            }
            double stackedwidth = stackedBar.getThickness();
            double stackedheight = Math.abs(stackedp2ddeviceBase.getY()
                    - stackedp2ddevice.getY());

            if (stackedBar.getMorpheStyle() == MorpheStyle.Round) {
                if (count == stacks.size() - 1) {
                    double round = stackedBar.getRound();
                    GeneralPath barPath = new GeneralPath();
                    if (stackedBar.isAscent()) {
                        barPath.moveTo(stackedx, stackedy + round);
                        barPath.lineTo(stackedx, stackedy + stackedheight);
                        barPath.lineTo(stackedx + stackedwidth, stackedy
                                + stackedheight);
                        barPath.lineTo(stackedx + stackedwidth, stackedy
                                + round);
                        barPath.quadTo(stackedx + stackedwidth, stackedy,
                                       stackedx + stackedwidth - round, stackedy);
                        barPath.lineTo(stackedx + round, stackedy);
                        barPath.quadTo(stackedx, stackedy, stackedx, stackedy
                                + round);
                        barPath.closePath();
                    }
                    else if (stackedBar.isDescent()) {
                        barPath.moveTo(stackedx, stackedy);
                        barPath.lineTo(stackedx, stackedy + stackedheight
                                - round);
                        barPath.quadTo(stackedx, stackedy + stackedheight,
                                       stackedx + round, stackedy + stackedheight);
                        barPath.lineTo(stackedx + stackedwidth - round,
                                       stackedy + stackedheight);
                        barPath.quadTo(stackedx + stackedwidth, stackedy
                                + stackedheight, stackedx + stackedwidth,
                                       stackedy + stackedheight - round);
                        barPath.lineTo(stackedx + stackedwidth, stackedy);
                        barPath.closePath();
                    }
                    stack.setBarShape(barPath);
                }
                else {
                    Rectangle2D barRec = new Rectangle2D.Double(stackedx,
                                                                stackedy, stackedwidth, stackedheight);
                    stack.setBarShape(barRec);
                }
            }
            else if (stackedBar.getMorpheStyle() == MorpheStyle.Rectangle) {
                Rectangle2D barRec = new Rectangle2D.Double(stackedx, stackedy,
                                                            stackedwidth, stackedheight);
                stack.setBarShape(barRec);
            }

            count++;
        }
    }

    /**
     * solve horizontal group
     * 
     * @param barGroup
     *            the bar group to solve
     */
    private void solveHBarGroup(BarSymbolGroup barGroup) {
        barGroup.setHost(getHost());
        barGroup.copyToBar();
        List<BarSymbol> bars = barGroup.getSymbolComponents();
        for (SymbolComponent bc : bars) {
            bc.setLayer(this);
            solveHSymbolComponent(bc);
        }
    }

    /**
     * solve the specified horizontal component
     * 
     * @param symbol
     *            the bar component to solve
     */
    private void solveHSymbolComponent(SymbolComponent symbol) {

        if (symbol.isFiller()) {
            return;
        }

        symbol.setNature(SymbolNature.Horizontal);
        if (symbol instanceof BarSymbolGroup) {
            solveHBarGroup((BarSymbolGroup) symbol);
        }
        else if (symbol instanceof StackedBarSymbol) {
            solveHStackedBar((StackedBarSymbol) symbol);
        }
        else {
            solveHBarSymbol((BarSymbol) symbol);
        }
    }

    /**
     * paint stacked bar
     * 
     * @param g2d
     *            the graphics context to paint
     * @param stackedBar
     *            the stacked bar to paint
     */
    private void paintBarStacked(Graphics2D g2d, StackedBarSymbol stackedBar,
            ViewPart viewPart, PaintRequest paintRequest) {
        stackedBar.setHost(getHost());
        stackedBar.setLayer(this);
        List<Stack> stacks = stackedBar.getStacks();
        if (paintRequest == PaintRequest.SymbolLayer) {
            for (Stack s : stacks) {
                paintBar(g2d, s, viewPart, paintRequest);
            }
            if (stackedBar.getBarEffect() != null) {
                stackedBar.getBarEffect().paintSymbol(g2d, stackedBar,
                                                      viewPart);
            }
            if (stackedBar.getBarDraw() != null) {
                stackedBar.getBarDraw()
                        .paintSymbol(g2d, stackedBar, viewPart);
            }
        }
        else if (paintRequest == PaintRequest.LabelLayer) {
            if (stackedBar.getBarLabel() != null) {
                stackedBar.getBarLabel().paintSymbol(g2d, stackedBar,
                                                     viewPart);
            }
            for (Stack s : stacks) {
                if (s.getBarLabel() != null) {
                    s.getBarLabel().paintSymbol(g2d, s, viewPart);
                }
            }
        }
    }

    /**
     * solve bar horizontal group
     * 
     * @param barGroup
     *            the specified bar group to solve
     */
    private void solveVBarGroup(BarSymbolGroup barGroup) {
        barGroup.setHost(getHost());
        barGroup.copyToBar();
        List<BarSymbol> bars = barGroup.getSymbolComponents();
        for (SymbolComponent bc : bars) {
            bc.setLayer(this);
            bc.setHost(getHost());
            solveVSymbolComponent(bc);
        }
    }

    /**
     * solve horizontal bar
     * 
     * @param bar
     *            the bar to solve
     */
    private void solveHBarSymbol(BarSymbol bar) {
        if (getHost() == null || getHost().getProjection() == null) {
            return;
        }
        bar.setHost(getHost());
        Projection w2d = getHost().getProjection();

        Point2D p2dUser = null;
        if (bar.isAscent()) {
            p2dUser = new Point2D.Double(bar.getBase() + bar.getValue(), 0);
        }
        if (bar.isDescent()) {
            p2dUser = new Point2D.Double(bar.getBase() - bar.getValue(), 0);
        }
        if (!bar.isValueSet()) {
            throw new IllegalArgumentException(
                                               "bar symbol ascent or descent value should be supplied.");
        }
        if (!bar.isBaseSet()) {
            throw new IllegalArgumentException(
                                               "stacked bar symbol base value should be supplied.");
        }

        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        Point2D p2dUserBase = new Point2D.Double(bar.getBase(), 0);
        Point2D p2ddeviceBase = w2d.userToPixel(p2dUserBase);

        double y = getComponentYLocation(bar);
        double x = p2ddeviceBase.getX();
        if (bar.isAscent()) {
            x = p2ddeviceBase.getX();
        }
        if (bar.isDescent()) {
            x = p2ddevice.getX();
        }

        double height = bar.getThickness();
        double width = Math.abs(p2ddevice.getX() - p2ddeviceBase.getX());

        if (bar.getMorpheStyle() == MorpheStyle.Round) {
            double round = bar.getRound();
            GeneralPath barPath = new GeneralPath();
            if (bar.isAscent()) {
                barPath.moveTo(x, y);
                barPath.lineTo(x + width - round, y);
                barPath.quadTo(x + width, y, x + width, y + round);
                barPath.lineTo(x + width, y + height - round);
                barPath.quadTo(x + width, y + height, x + width - round, y
                        + height);
                barPath.lineTo(x, y + height);
                barPath.closePath();
            }
            else if (bar.isDescent()) {

                barPath.moveTo(x + round, y);
                barPath.lineTo(x + width, y);
                barPath.lineTo(x + width, y + height);
                barPath.lineTo(x + round, y + height);
                barPath.quadTo(x, y + height, x, y + height - round);
                barPath.lineTo(x, y + round);
                barPath.quadTo(x, y, x + round, y);
                barPath.closePath();
            }
            bar.setBarShape(barPath);
        }
        else {
            Rectangle2D barRec = new Rectangle2D.Double(x, y, width, height);
            bar.setBarShape(barRec);
        }

    }

    /**
     * solve the horizontal stacked bar
     * 
     * @param stackedBar
     *            the stacked bar top solve
     */
    private void solveHStackedBar(StackedBarSymbol stackedBar) {
        if (getHost() == null || getHost().getProjection() == null) {
            return;
        }
        stackedBar.setHost(getHost());
        stackedBar.normalize();
        Projection w2d = getHost().getProjection();
        Point2D p2dUser = null;
        if (stackedBar.isAscent()) {
            p2dUser = new Point2D.Double(stackedBar.getBase()
                    + stackedBar.getValue(), 0);
        }
        if (stackedBar.isDescent()) {
            p2dUser = new Point2D.Double(stackedBar.getBase()
                    - stackedBar.getValue(), 0);
        }
        if (!stackedBar.isValueSet()) {
            throw new IllegalArgumentException(
                                               "stacked bar symbol ascent or descent value should be supplied.");
        }
        if (!stackedBar.isBaseSet()) {
            throw new IllegalArgumentException(
                                               "stacked bar symbol base value should be supplied.");
        }
        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        Point2D p2dUserBase = new Point2D.Double(stackedBar.getBase(), 0);
        Point2D p2ddeviceBase = w2d.userToPixel(p2dUserBase);

        double y = getComponentYLocation(stackedBar);
        double x = p2ddeviceBase.getX();
        if (stackedBar.isAscent()) {
            x = p2ddeviceBase.getX();
        }
        if (stackedBar.isDescent()) {
            x = p2ddevice.getX();
        }

        double height = stackedBar.getThickness();
        double width = Math.abs(p2ddevice.getX() - p2ddeviceBase.getX());

        if (stackedBar.getMorpheStyle() == MorpheStyle.Round) {
            double round = stackedBar.getRound();
            GeneralPath barPath = new GeneralPath();
            if (stackedBar.isAscent()) {
                barPath.moveTo(x, y);
                barPath.lineTo(x + width - round, y);
                barPath.quadTo(x + width, y, x + width, y + round);
                barPath.lineTo(x + width, y + height - round);
                barPath.quadTo(x + width, y + height, x + width - round, y
                        + height);
                barPath.lineTo(x, y + height);
                barPath.closePath();
            }
            else if (stackedBar.isDescent()) {
                barPath.moveTo(x + round, y);
                barPath.lineTo(x + width, y);
                barPath.lineTo(x + width, y + height);
                barPath.lineTo(x + round, y + height);
                barPath.quadTo(x, y + height, x, y + height - round);
                barPath.lineTo(x, y + round);
                barPath.quadTo(x, y, x + round, y);
                barPath.closePath();
            }
            stackedBar.setBarShape(barPath);
        }
        else {
            Rectangle2D barRec = new Rectangle2D.Double(x, y, width, height);
            stackedBar.setBarShape(barRec);
        }

        // stack Fill
        List<Stack> stacks = stackedBar.getStacks();

        int count = 0;
        for (Stack stack : stacks) {

            // data from host bar
            stack.setThickness(stackedBar.getThickness());
            stack.setBase(stackedBar.getStackBase(stack));
            stack.setNature(stackedBar.getNature());
            stack.setBarFill(stackedBar.getBarFill());

            if (stackedBar.isAscent()) {
                stack.setAscentValue(stack.getNormalizedValue());
            }
            else if (stackedBar.isDescent()) {
                stack.setDescentValue(stack.getNormalizedValue());
            }

            Point2D stackedp2dUser = null;
            if (stackedBar.isAscent()) {
                stackedp2dUser = new Point2D.Double(
                                                    stackedBar.getStackBase(stack)
                                                            + stack.getNormalizedValue(), 0);
            }
            else if (stackedBar.isDescent()) {
                stackedp2dUser = new Point2D.Double(
                                                    stackedBar.getStackBase(stack)
                                                            - stack.getNormalizedValue(), 0);
            }
            Point2D stackedp2ddevice = w2d.userToPixel(stackedp2dUser);

            Point2D stackedp2dUserBase = new Point2D.Double(
                                                            stackedBar.getStackBase(stack), 0);
            Point2D stackedp2ddeviceBase = w2d.userToPixel(stackedp2dUserBase);

            double stackedy = getComponentYLocation(stackedBar);
            double stackedx = stackedp2ddeviceBase.getX();
            if (stackedBar.isAscent()) {
                stackedx = stackedp2ddeviceBase.getX();
            }
            if (stackedBar.isDescent()) {
                stackedx = stackedp2ddevice.getX();
            }
            double stackedheight = stackedBar.getThickness();
            double stackedwidth = Math.abs(stackedp2ddevice.getX()
                    - stackedp2ddeviceBase.getX());

            if (stackedBar.getMorpheStyle() == MorpheStyle.Round) {
                if (count == stacks.size() - 1) {
                    double round = stackedBar.getRound();
                    GeneralPath barPath = new GeneralPath();
                    if (stackedBar.isAscent()) {
                        barPath.moveTo(stackedx, stackedy);
                        barPath.lineTo(stackedx + stackedwidth - round,
                                       stackedy);
                        barPath.quadTo(stackedx + stackedwidth, stackedy,
                                       stackedx + stackedwidth, stackedy + round);
                        barPath.lineTo(stackedx + stackedwidth, stackedy
                                + stackedheight - round);
                        barPath.quadTo(stackedx + stackedwidth, stackedy
                                + stackedheight, stackedx + stackedwidth
                                - round, stackedy + stackedheight);
                        barPath.lineTo(stackedx, stackedy + stackedheight);
                        barPath.closePath();
                    }
                    else if (stackedBar.isDescent()) {
                        barPath.moveTo(stackedx + round, stackedy);
                        barPath.lineTo(stackedx + stackedwidth, stackedy);
                        barPath.lineTo(stackedx + stackedwidth, stackedy
                                + stackedheight);
                        barPath.lineTo(stackedx + round, stackedy
                                + stackedheight);
                        barPath.quadTo(stackedx, stackedy + stackedheight,
                                       stackedx, stackedy + stackedheight - round);
                        barPath.lineTo(stackedx, stackedy + round);
                        barPath.quadTo(stackedx, stackedy, stackedx + round,
                                       stackedy);
                        barPath.closePath();
                    }
                    stack.setBarShape(barPath);
                }
                else {
                    Rectangle2D barRec = new Rectangle2D.Double(stackedx,
                                                                stackedy, stackedwidth, stackedheight);
                    stack.setBarShape(barRec);
                }
            }
            else {
                Rectangle2D barRec = new Rectangle2D.Double(stackedx, stackedy,
                                                            stackedwidth, stackedheight);
                stack.setBarShape(barRec);
            }

            // stack.setBar(stack);

            count++;
        }

    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        List<BarSymbol> bars = getSymbols();
        for (SymbolComponent symbolComponent : bars) {
            if (symbolComponent instanceof StackedBarSymbol) {
                StackedBarSymbol stackedBar = (StackedBarSymbol) symbolComponent;
                if (stackedBar.getBarShape() != null
                        && stackedBar.getBarShape().contains(me.getX(),
                                                             me.getY()) && stackedBar.isLockEnter()) {
                    fireBarReleased(stackedBar);
                }

                List<Stack> barStacks = stackedBar.getStacks();
                for (Stack barStack : barStacks) {

                    if (barStack.getBarShape() != null
                            && barStack.getBarShape().contains(me.getX(),
                                                               me.getY()) && barStack.isLockEnter()) {
                        fireBarReleased(barStack);
                    }
                }
            }
            else if (symbolComponent instanceof BarSymbolGroup) {
                BarSymbolGroup group = (BarSymbolGroup) symbolComponent;
                List<BarSymbol> barsGroup = group.getSymbolComponents();

                for (SymbolComponent symbolComponentGroup : barsGroup) {
                    if (symbolComponentGroup instanceof StackedBarSymbol) {

                        StackedBarSymbol stackedBar = (StackedBarSymbol) symbolComponentGroup;
                        if (stackedBar.getBarShape() != null
                                && stackedBar.getBarShape().contains(me.getX(),
                                                                     me.getY()) && stackedBar.isLockEnter()) {
                            fireBarReleased(stackedBar);
                        }

                        List<Stack> barStacks = stackedBar.getStacks();
                        for (Stack barStack : barStacks) {

                            if (barStack.getBarShape() != null
                                    && barStack.getBarShape().contains(
                                                                       me.getX(), me.getY())
                                    && barStack.isLockEnter()) {
                                fireBarReleased(barStack);
                            }
                        }
                    }
                    else if (symbolComponentGroup instanceof BarSymbol) {
                        if (((BarSymbol) symbolComponentGroup).getBarShape() != null
                                && ((BarSymbol) symbolComponentGroup)
                                        .getBarShape().contains(me.getX(),
                                                                me.getY())
                                && ((BarSymbol) symbolComponentGroup)
                                        .isLockEnter()) {
                            fireBarReleased((BarSymbol) symbolComponentGroup);
                        }
                    }
                }
            }
            else if (symbolComponent instanceof BarSymbol) {
                if (((BarSymbol) symbolComponent).getBarShape() != null
                        && ((BarSymbol) symbolComponent).getBarShape()
                                .contains(me.getX(), me.getY())
                        && ((BarSymbol) symbolComponent).isLockEnter()) {
                    fireBarReleased((BarSymbol) symbolComponent);
                }
            }

        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {
        List<BarSymbol> bars = getSymbols();
        for (SymbolComponent symbolComponent : bars) {
            if (symbolComponent instanceof StackedBarSymbol) {
                StackedBarSymbol stackedBar = (StackedBarSymbol) symbolComponent;
                if (stackedBar.getBarShape() != null
                        && stackedBar.getBarShape().contains(me.getX(),
                                                             me.getY()) && stackedBar.isLockEnter()) {
                    fireBarPressed(stackedBar);
                }

                List<Stack> barStacks = stackedBar.getStacks();
                for (Stack barStack : barStacks) {

                    if (barStack.getBarShape() != null
                            && barStack.getBarShape().contains(me.getX(),
                                                               me.getY()) && barStack.isLockEnter()) {
                        fireBarPressed(barStack);
                    }
                }
            }
            else if (symbolComponent instanceof BarSymbolGroup) {
                BarSymbolGroup group = (BarSymbolGroup) symbolComponent;
                List<BarSymbol> barsGroup = group.getSymbolComponents();

                for (SymbolComponent symbolComponentGroup : barsGroup) {
                    if (symbolComponentGroup instanceof StackedBarSymbol) {

                        StackedBarSymbol stackedBar = (StackedBarSymbol) symbolComponentGroup;
                        if (stackedBar.getBarShape() != null
                                && stackedBar.getBarShape().contains(me.getX(),
                                                                     me.getY())) {
                            fireBarPressed(stackedBar);
                        }

                        List<Stack> barStacks = stackedBar.getStacks();
                        for (Stack barStack : barStacks) {

                            if (barStack.getBarShape() != null
                                    && barStack.getBarShape().contains(
                                                                       me.getX(), me.getY())
                                    && barStack.isLockEnter()) {
                                fireBarPressed(barStack);
                            }
                        }
                    }
                    else if (symbolComponentGroup instanceof BarSymbol) {
                        if (((BarSymbol) symbolComponentGroup).getBarShape() != null
                                && ((BarSymbol) symbolComponentGroup)
                                        .getBarShape().contains(me.getX(),
                                                                me.getY())
                                && ((BarSymbol) symbolComponentGroup)
                                        .isLockEnter()) {
                            fireBarPressed((BarSymbol) symbolComponentGroup);
                        }
                    }
                }
            }
            else if (symbolComponent instanceof BarSymbol) {
                if (((BarSymbol) symbolComponent).getBarShape() != null
                        && ((BarSymbol) symbolComponent).getBarShape()
                                .contains(me.getX(), me.getY())
                        && ((BarSymbol) symbolComponent).isLockEnter()) {
                    fireBarPressed((BarSymbol) symbolComponent);
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
        List<BarSymbol> bars = getSymbols();
        for (SymbolComponent symbolComponent : bars) {
            if (symbolComponent instanceof StackedBarSymbol) {
                StackedBarSymbol stackedBar = (StackedBarSymbol) symbolComponent;
                if (stackedBar.getBarShape() != null
                        && stackedBar.getBarShape().contains(me.getX(),
                                                             me.getY()) && stackedBar.isLockEnter()) {
                    fireBarClicked(stackedBar);
                }

                List<Stack> barStacks = stackedBar.getStacks();
                for (Stack barStack : barStacks) {

                    if (barStack.getBarShape() != null
                            && barStack.getBarShape().contains(me.getX(),
                                                               me.getY()) && barStack.isLockEnter()) {
                        fireBarClicked(barStack);
                    }
                }
            }
            else if (symbolComponent instanceof BarSymbolGroup) {
                BarSymbolGroup group = (BarSymbolGroup) symbolComponent;
                List<BarSymbol> barsGroup = group.getSymbolComponents();

                for (SymbolComponent symbolComponentGroup : barsGroup) {
                    if (symbolComponentGroup instanceof StackedBarSymbol) {

                        StackedBarSymbol stackedBar = (StackedBarSymbol) symbolComponentGroup;
                        if (stackedBar.getBarShape() != null
                                && stackedBar.getBarShape().contains(me.getX(),
                                                                     me.getY()) && stackedBar.isLockEnter()) {
                            fireBarClicked(stackedBar);
                        }
                        List<Stack> barStacks = stackedBar.getStacks();
                        for (Stack barStack : barStacks) {

                            if (barStack.getBarShape() != null
                                    && barStack.getBarShape().contains(
                                                                       me.getX(), me.getY())
                                    && barStack.isLockEnter()) {
                                fireBarClicked(barStack);
                            }
                        }
                    }
                    else if (symbolComponentGroup instanceof BarSymbol) {
                        if (((BarSymbol) symbolComponentGroup).getBarShape() != null
                                && ((BarSymbol) symbolComponentGroup)
                                        .getBarShape().contains(me.getX(),
                                                                me.getY())) {
                            fireBarClicked((BarSymbol) symbolComponentGroup);
                        }
                    }
                }
            }
            else if (symbolComponent instanceof BarSymbol) {
                if (((BarSymbol) symbolComponent).getBarShape() != null
                        && ((BarSymbol) symbolComponent).getBarShape()
                                .contains(me.getX(), me.getY())
                        && ((BarSymbol) symbolComponent).isLockEnter()) {
                    fireBarClicked((BarSymbol) symbolComponent);
                }
            }

        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {

        List<BarSymbol> bars = getSymbols();
        for (SymbolComponent symbolComponent : bars) {

            if (symbolComponent instanceof StackedBarSymbol) {

                StackedBarSymbol stackedBar = (StackedBarSymbol) symbolComponent;
                barEnterExitTracker(stackedBar, me.getX(), me.getY());

                List<Stack> barStacks = stackedBar.getStacks();
                for (Stack barStack : barStacks) {
                    barEnterExitTracker(barStack, me.getX(), me.getY());
                }
            }
            else if (symbolComponent instanceof BarSymbolGroup) {
                BarSymbolGroup group = (BarSymbolGroup) symbolComponent;
                List<BarSymbol> barsGroup = group.getSymbolComponents();

                for (SymbolComponent symbolComponentGroup : barsGroup) {
                    if (symbolComponentGroup instanceof StackedBarSymbol) {

                        StackedBarSymbol stackedBar = (StackedBarSymbol) symbolComponentGroup;
                        barEnterExitTracker(stackedBar, me.getX(), me.getY());

                        List<Stack> barStacks = stackedBar.getStacks();
                        for (Stack barStack : barStacks) {

                            barEnterExitTracker(barStack, me.getX(), me.getY());
                            // barStack.setLockEnter(subBar.isLockEnter());
                        }
                    }
                    else if (symbolComponentGroup instanceof BarSymbol) {
                        barEnterExitTracker((BarSymbol) symbolComponentGroup,
                                            me.getX(), me.getY());
                    }
                }
            }
            else if (symbolComponent instanceof BarSymbol) {
                barEnterExitTracker((BarSymbol) symbolComponent, me.getX(),
                                    me.getY());
            }

        }
    }

    /**
     * track bar enter or exit for the specified bar for device location x,y
     * 
     * @param bar
     *            the bar to track
     * @param x
     *            the x in device coordinate
     * @param y
     *            the y in device coordinate
     */
    private void barEnterExitTracker(BarSymbol bar, int x, int y) {

        if (bar.getBarShape() == null) {
            return;
        }

        if (bar.getBarShape().contains(x, y) && !bar.isLockEnter()) {
            bar.lockEnter();
            fireBarEntered(bar);
        }
        else if (!bar.getBarShape().contains(x, y) && bar.isLockEnter()) {
            bar.unlockEnter();
            fireBarExited(bar);
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.symbol.SymbolLayer#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        List<BarSymbol> bars = getSymbols();
        for (SymbolComponent symbolComponent : bars) {
            if (symbolComponent instanceof StackedBarSymbol) {

                StackedBarSymbol stackedBar = (StackedBarSymbol) symbolComponent;
                barEnterExitTracker(stackedBar, me.getX(), me.getY());

                List<Stack> barStacks = stackedBar.getStacks();
                for (Stack barStack : barStacks) {
                    barEnterExitTracker(barStack, me.getX(), me.getY());
                }
            }
            else if (symbolComponent instanceof BarSymbolGroup) {
                BarSymbolGroup group = (BarSymbolGroup) symbolComponent;
                List<BarSymbol> barsGroup = group.getSymbolComponents();

                for (SymbolComponent symbolComponentGroup : barsGroup) {
                    if (symbolComponentGroup instanceof StackedBarSymbol) {

                        StackedBarSymbol stackedBar = (StackedBarSymbol) symbolComponentGroup;
                        barEnterExitTracker(stackedBar, me.getX(), me.getY());

                        List<Stack> barStacks = stackedBar.getStacks();
                        for (Stack barStack : barStacks) {
                            barEnterExitTracker(barStack, me.getX(), me.getY());
                        }
                    }
                    else if (symbolComponentGroup instanceof BarSymbol) {
                        barEnterExitTracker((BarSymbol) symbolComponentGroup,
                                            me.getX(), me.getY());
                    }
                }
            }
            else if (symbolComponent instanceof BarSymbol) {
                barEnterExitTracker((BarSymbol) symbolComponent, me.getX(),
                                    me.getY());
            }

        }

    }

    /**
     * fire bar entered
     * 
     * @param bar
     *            the bar entered to fire
     */
    private void fireBarEntered(BarSymbol bar) {
        Object[] listeners = symbolListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == BarListener.class) {
                    ((BarListener) listeners[i + 1])
                            .barSymbolEntered(new BarEvent(bar));
                }
            }
        }
    }

    /**
     * fire bar exited
     * 
     * @param bar
     *            the bar exited to fire
     */
    private void fireBarExited(BarSymbol bar) {
        Object[] listeners = symbolListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == BarListener.class) {
                    ((BarListener) listeners[i + 1])
                            .barSymbolExited(new BarEvent(bar));
                }
            }
        }
    }

    /**
     * fire bar clicked
     * 
     * @param bar
     *            the bar clicked to fire
     */
    private void fireBarClicked(BarSymbol bar) {
        Object[] listeners = symbolListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == BarListener.class) {
                    ((BarListener) listeners[i + 1])
                            .barSymbolClicked(new BarEvent(bar));
                }
            }
        }
    }

    /**
     * fire bar pressed
     * 
     * @param bar
     *            the bar pressed to fire
     */
    private void fireBarPressed(BarSymbol bar) {
        Object[] listeners = symbolListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == BarListener.class) {
                    ((BarListener) listeners[i + 1])
                            .barSymbolPressed(new BarEvent(bar));
                }
            }
        }
    }

    /**
     * fire bar released
     * 
     * @param bar
     *            the bar released to fire
     */
    private void fireBarReleased(BarSymbol bar) {
        Object[] listeners = symbolListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == BarListener.class) {
                    ((BarListener) listeners[i + 1])
                            .barSymbolReleased(new BarEvent(bar));
                }
            }
        }
    }

}
