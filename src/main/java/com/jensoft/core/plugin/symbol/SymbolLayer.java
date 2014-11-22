/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.symbol.SymbolComponent.FillerType;
import com.jensoft.core.plugin.symbol.SymbolPlugin.PaintRequest;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>SymbolLayer</code> defines abstract layer for {@link SymbolComponent}
 * 
 * @author sebastien janaud
 */
public abstract class SymbolLayer<S extends SymbolComponent> {

    /** host symbol plug in */
    private SymbolPlugin host;

    /** layer symbols */
    private List<S> symbols = new ArrayList<S>();

    /**
     * create empty layer
     */
    public SymbolLayer() {
    }

    /**
     * @return the host
     */
    public SymbolPlugin getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(SymbolPlugin host) {
        this.host = host;
    }

    /**
     * add specified symbol in this layer
     * 
     * @param symbol
     *            the symbol to add
     */
    public void addSymbol(S symbol) {
        symbol.setLayer(this);
        symbols.add(symbol);
    }

    /**
     * remove specified symbol in this layer
     * 
     * @param symbol
     *            the symbol to remove
     */
    public void removeSymbol(S symbol) {
        symbol.setLayer(null);
        symbols.remove(symbol);
    }

    /**
     * count the symbols in this layer
     * 
     * @return the symbol number items
     */
    public int countSymbols() {
        return symbols.size();
    }

    /**
     * get symbol at the specified index
     * 
     * @param index
     * @return symbol at the given index
     */
    public S getSymbol(int index) {
        return symbols.get(index);
    }

    /**
     * get all registered symbol in this layer
     * 
     * @return the symbols
     */
    public List<S> getSymbols() {
        return symbols;
    }

    /**
     * set symbol collection in this layer
     * 
     * @param symbols
     *            the symbols to set
     */
    public void setSymbols(List<S> symbols) {
        this.symbols = symbols;
    }

    /**
     * get the symbol index for the specified symbol
     * 
     * @param symbol
     * @return the symbol index, -1 otherwise
     */
    public int getSymbolIndex(SymbolComponent symbol) {
        if (symbol == null) {
            return -1;
        }
        for (int i = 0; i < symbols.size(); i++) {
            SymbolComponent s = symbols.get(i);
            if (s.equals(symbol)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * solve this layer geometry
     */
    public abstract void paintLayer(View v2d, Graphics2D g2d,
                                    ViewPart viewPart, PaintRequest paintRequest);

    /**
     * return flatten symbol components in the windowBar2D
     * flatten mean include symbol registered in group
     * 
     * @return the flattened list of symbol components
     */
    public abstract List<S> getFlattenSymbolComponents();

    /**
     * solve this layer geometry
     */
    public final void solveGeometry() {
        List<S> symbols = getSymbols();
        for (S symbol : symbols) {
            if (!symbol.isFiller()) {
                solveSymbolComponent(symbol);
            }
        }
    }

    /**
     * solve the specified component
     * 
     * @param symbol
     */
    public abstract void solveSymbolComponent(S symbol);

    /**
     * call on mouse move
     * 
     * @param me
     */
    protected void onMove(MouseEvent me) {
    }

    /**
     * call on mouse click
     * 
     * @param me
     */
    protected void onClick(MouseEvent me) {
    }

    /**
     * call on mouse exit
     * 
     * @param me
     */
    protected void onExit(MouseEvent me) {
    }

    /**
     * call on mouse enter
     * 
     * @param me
     */
    protected void onEnter(MouseEvent me) {
    }

    /**
     * call on mouse press
     * 
     * @param me
     */
    protected void onPress(MouseEvent me) {
    }

    /**
     * call on mouse released
     * 
     * @param me
     */
    protected void onRelease(MouseEvent me) {
    }

    /**
     * call on mouse drag
     * 
     * @param me
     */
    public void onDrag(MouseEvent me) {
    }

    /**
     * get symbol x Location
     * 
     * @param symbol
     *            the bar component
     * @return component x location
     */
    protected double getComponentXLocation(SymbolComponent symbol) {
    	//System.out.println("symbol class : "+symbol.getClass().getSimpleName());
    	if(symbol instanceof Stack){
    		//System.out.println("location for stack"+((Stack)symbol).getHostSymbol());
    		symbol = ((Stack)symbol).getHostSymbol();
    	}
        List<? extends SymbolComponent> flattenSymbols = getFlattenSymbolComponents();
        double total = 0;
        List<SymbolComponent> glues = new ArrayList<SymbolComponent>();
        for (SymbolComponent bc : flattenSymbols) {
            if (bc.isFiller() && bc.getFillerType() == FillerType.Glue) {
                glues.add(bc);
            }
            else {
                total = total + bc.getThickness();
            }
        }
        if (getHost().getProjection().getDevice2D().getDeviceWidth() > total) {
            double reste = getHost().getProjection().getDevice2D()
                    .getDeviceWidth()
                           - total;
            int gluesCount = glues.size();
            if (gluesCount > 0) {
                for (SymbolComponent glue : glues) {
                    glue.setThickness(reste / gluesCount);
                }
            }
        }
        double positionX = 0;
        for (SymbolComponent bc : flattenSymbols) {
            if (!bc.equals(symbol)) {
                positionX = positionX + bc.getThickness();
            }
            else {
                return positionX;
            }
        }
        return positionX;
    }

    /**
     * get symbol y location
     * 
     * @param symbol
     * @return component y location
     */
    protected double getComponentYLocation(SymbolComponent symbol) {
        List<? extends SymbolComponent> flattenSymbols = getFlattenSymbolComponents();
        double total = 0;
        List<SymbolComponent> glues = new ArrayList<SymbolComponent>();
        for (SymbolComponent bc : flattenSymbols) {
            if (bc.isFiller() && bc.getFillerType() == FillerType.Glue) {
                glues.add(bc);
            }
            else {
                total = total + bc.getThickness();
            }
        }
        if (getHost().getProjection().getDevice2D().getDeviceHeight() > total) {
            double reste = getHost().getProjection().getDevice2D()
                    .getDeviceHeight()
                           - total;
            int gluesCount = glues.size();
            if (gluesCount > 0) {
                for (SymbolComponent glue : glues) {
                    glue.setThickness(reste / gluesCount);
                }
            }
        }
        double positionY = 0;
        for (SymbolComponent bc : flattenSymbols) {
            if (!bc.equals(symbol)) {
                positionY = positionY + bc.getThickness();
            }
            else {
                return positionY;
            }
        }
        return positionY;
    }

}
