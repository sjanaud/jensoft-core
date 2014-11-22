/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Fractional;
import com.jensoft.core.graphics.Interpolation;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * This SymbolPlugin provides symbol support. <br>
 * here is some examples <br>
 * <br>
 * Simple Bar <br>
 * <center><img src="doc-files/simple-bar.png"></center> <br>
 * <br>
 * <br>
 * <br>
 * Bar Group <br>
 * <center><img src="doc-files/group-bar.png"></center> <br>
 * <br>
 * <br>
 * <br>
 * Bar Group <br>
 * <center><img src="doc-files/stacked-bar.png"></center> <br>
 * <br>
 */
public class SymbolPlugin extends AbstractPlugin implements
        AbstractPlugin.OnClickListener, AbstractPlugin.OnEnterListener,
        AbstractPlugin.OnExitListener, AbstractPlugin.OnPressListener,
        AbstractPlugin.OnReleaseListener, AbstractPlugin.OnMoveListener,
        AbstractPlugin.OnDragListener {

    /** symbol nature */
    private SymbolNature symbolNature;

    /** symbol layers */
    private List<SymbolLayer<? extends SymbolComponent>> layers = new ArrayList<SymbolLayer<? extends SymbolComponent>>();

    /**
     * define the symbol nature, vertical or horizontal
     * 
     * @author Sebastien janaud
     */
    public enum SymbolNature {
        Vertical("vertical"), Horizontal("horizontal");

        /** symbol nature */
        private String symbolNature;

        private SymbolNature(String nature) {
            symbolNature = nature;
        }

        /**
         * @return the symbolNature
         */
        public String getBarNature() {
            return symbolNature;
        }

        public static SymbolNature parse(String nature) {
            if (Vertical.getBarNature().equals(nature)) {
                return Vertical;
            }
            if (Horizontal.getBarNature().equals(nature)) {
                return Horizontal;
            }
            return null;
        }
    };

    /**
     * define paint request cycle.
     * when symbol plug in paint his related objects. plug in paints this
     * objects sequentially. the first call paints symbols, the second call
     * paints label.
     * 
     * @author Sebastien janaud
     */
    public enum PaintRequest {
        SymbolLayer, LabelLayer;
    };

    /**
     * Create Symbol Plugin
     */
    public SymbolPlugin() {
        setName(getClass().getSimpleName());
        setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        setFractionalMetrics(Fractional.On);
        setInterpolation(Interpolation.Bicubic);

        setOnMoveListener(this);
        setOnClickListener(this);
        setOnReleaseListener(this);
        setOnPressListener(this);
        setOnDragListener(this);

        setPriority(500);
    }

    /**
     * get the plug in symbol nature
     * 
     * @return the plug in symbol nature
     */
    public SymbolNature getNature() {
        return symbolNature;
    }

    /**
     * set the plug in symbol nature
     * 
     * @param symbolNature
     */
    public void setNature(SymbolNature symbolNature) {
        this.symbolNature = symbolNature;
    }

    /**
     * add the specified symbol layer to this symbol plug in
     * 
     * @param layer
     *            the layer to add
     */
    public void addLayer(SymbolLayer<? extends SymbolComponent> layer) {
        layer.setHost(this);
        layers.add(layer);
    }

    /**
     * remove the specified symbol layer from this symbol plug in
     * 
     * @param layer
     *            the layer to remove
     */
    public void removeLayer(SymbolLayer<? extends SymbolComponent> layer) {
        layer.setHost(null);
        layers.remove(layer);
    }

    /**
     * count the number of layer registered in this symbol plug in
     */
    public int countLayers() {
        return layers.size();
    }

    /**
     * get the layer at the specified index
     * 
     * @param index
     *            the layer index
     * @return layer
     */
    public SymbolLayer<? extends SymbolComponent> getLayer(int index) {
        return layers.get(index);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    public final void paintPlugin(View v2d, Graphics2D g2d,
            ViewPart viewPart) {
        // System.out.println("paint symbol plugin for part "+windowPart.name());
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
        // solve layer
        solveLayers();
        // paint layer
        for (int i = 0; i < countLayers(); i++) {
            SymbolLayer<? extends SymbolComponent> layer = getLayer(i);
            layer.paintLayer(v2d, g2d, viewPart, PaintRequest.SymbolLayer);
            layer.paintLayer(v2d, g2d, viewPart, PaintRequest.LabelLayer);
        }
    }

    /**
     * solve layer
     */
    private void solveLayers() {
        for (int i = 0; i < countLayers(); i++) {
            SymbolLayer<? extends SymbolComponent> layer = getLayer(i);
            layer.solveGeometry();
        }
    }


    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        for (int i = 0; i < countLayers(); i++) {
            SymbolLayer<? extends SymbolComponent> layer = getLayer(i);
            layer.onRelease(me);
        }
    }


    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {
        for (int i = 0; i < countLayers(); i++) {
            SymbolLayer<? extends SymbolComponent> layer = getLayer(i);
            layer.onPress(me);
        }
    }


    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnExitListener#onExit(java.awt.event.MouseEvent)
     */
    @Override
    public void onExit(MouseEvent me) {
        for (int i = 0; i < countLayers(); i++) {
            SymbolLayer<? extends SymbolComponent> layer = getLayer(i);
            layer.onExit(me);
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnEnterListener#onEnter(java.awt.event.MouseEvent)
     */
    @Override
    public void onEnter(MouseEvent me) {
        for (int i = 0; i < countLayers(); i++) {
            SymbolLayer<? extends SymbolComponent> layer = getLayer(i);
            layer.onEnter(me);
        }
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnClickListener#onClick(java.awt.event.MouseEvent)
     */
    @Override
    public void onClick(MouseEvent me) {
        for (int i = 0; i < countLayers(); i++) {
            SymbolLayer<? extends SymbolComponent> layer = getLayer(i);
            layer.onClick(me);
        }

    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {
        for (int i = 0; i < countLayers(); i++) {
            SymbolLayer<? extends SymbolComponent> layer = getLayer(i);
            layer.onMove(me);
        }

    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        for (int i = 0; i < countLayers(); i++) {
            SymbolLayer<? extends SymbolComponent> layer = getLayer(i);
            layer.onDrag(me);
        }
    }

}
