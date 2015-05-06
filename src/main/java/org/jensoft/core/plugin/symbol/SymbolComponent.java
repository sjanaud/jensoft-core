/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.symbol;

import org.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;

/**
 * <code>SymbolComponent</code>
 * 
 * @author Sebastien janaud
 */
public class SymbolComponent {

    /** filler flag */
    private boolean isFiller = false;

    /** filler type */
    private FillerType fillerType;

    /** symbol host layer */
    private SymbolLayer<? extends SymbolComponent> layer;

    /**
     * FillerType
     */
    public enum FillerType {
        Glue, Strut;
    };

    /** bar nature */
    private SymbolNature nature;

    /** bar opaque */
    private boolean opaque;

    /** bar thickness, default is 0 */
    private double thickness = 0;

    /** bar name */
    private String name;

    /** bar host */
    private SymbolPlugin host;

    /** enter flag */
    private boolean lockEnter = false;

    /** visible flag */
    private boolean visible = true;

    /** user object */
    private Object userObject;

    /**
     * create base bar component
     */
    public SymbolComponent() {
    }

    /**
     * get the host layer for this symbol
     * 
     * @return the layer
     */
    public SymbolLayer<? extends SymbolComponent> getLayer() {
        return layer;
    }

    /**
     * set the host layer for this symbol
     * 
     * @param layer
     *            the layer to set
     */
    public void setLayer(SymbolLayer<? extends SymbolComponent> layer) {
        this.layer = layer;
    }

    /**
     * @return the isFiller
     */
    public boolean isFiller() {
        return isFiller;
    }

    /**
     * @param isFiller
     *            the isFiller to set
     */
    public void setFiller(boolean isFiller) {
        this.isFiller = isFiller;
    }

    /**
     * create glue component for the specified symbol raw type
     * 
     * @return glue component
     */
    public static <T extends SymbolComponent> T createGlue(Class<T> c) {
        try {
            T glue = c.newInstance();
            glue.setName("SymbolComponent.Glue");
            glue.setFillerType(FillerType.Glue);
            glue.setOpaque(false);
            glue.setFiller(true);
            return glue;
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends SymbolComponent> T createStrut(Class<T> c,
            double thickness) {
        try {
            T strut = c.newInstance();
            strut.setName("SymbolComponent.Strut." + strut);
            strut.setOpaque(false);
            strut.setThickness(thickness);
            strut.setFillerType(FillerType.Strut);
            strut.setFiller(true);
            return strut;
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get filler type
     * 
     * @return filler type
     */
    public FillerType getFillerType() {
        return fillerType;
    }

    /**
     * set filler type
     * 
     * @param fillerType
     */
    public void setFillerType(FillerType fillerType) {
        this.fillerType = fillerType;
    }

    /**
     * get the host of this bar component
     * 
     * @return the host
     */
    public SymbolPlugin getHost() {
        return host;
    }

    /**
     * set the host for this bar component
     * 
     * @param host
     *            the host to set
     */
    public void setHost(SymbolPlugin host) {
        this.host = host;
    }

    /**
     * get the component name
     * 
     * @return the name
     */
    public String getName() {
        if (name == null) {
            if (getLayer() != null) {
                name = getClass().getSimpleName() + "_"
                        + getLayer().getSymbolIndex(this);
            }
            else {
                name = getClass().getSimpleName();
            }
        }
        return name;
    }

    /**
     * set the name
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the component thickness in device coordinate
     * 
     * @return the bar thickness
     */
    public double getThickness() {
        return thickness;
    }

    /**
     * set the component thickness
     * 
     * @param thickness
     *            the thickness to set
     */
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    /**
     * return true if the component is opaque, false otherwise
     * 
     * @return true if the component is opaque, false otherwise
     */
    public boolean isOpaque() {
        return opaque;
    }

    /**
     * set the component opacity
     * 
     * @param opaque
     *            the opacity
     */
    public void setOpaque(boolean opaque) {
        this.opaque = opaque;
    }

    /**
     * get symbol location x in device coordinate
     * 
     * @return the location X
     */
    public double getLocationX() {
        if (layer == null) {
            return 0;
        }
        if (nature == SymbolNature.Horizontal) {
            throw new IllegalStateException(
                                            "Horizontal symbol has no location x");
        }

        return layer.getComponentXLocation(this);
    }

    /**
     * get symbol center x in device coordinate
     * 
     * @return the center X
     */
    public double getCenterX() {
        if (layer == null) {
            return 0;
        }
        return getLocationX() + getThickness() / 2;
    }

    /**
     * get location y in device coordinate
     * 
     * @return the location Y
     */
    public double getLocationY() {
        if (layer == null) {
            return 0;
        }
        if (nature == SymbolNature.Vertical) {
            throw new IllegalStateException("Vertical symbol has no location y");
        }
        return layer.getComponentYLocation(this);
    }

    /**
     * get symbol center y in device coordinate
     * 
     * @return the center X
     */
    public double getCenterY() {
        if (layer == null) {
            return 0;
        }
        return getLocationY() - getThickness() / 2;
    }

    /**
     * get the component nature
     * 
     * @return bar nature
     */
    public SymbolNature getNature() {
        return nature;
    }

    /**
     * set nature
     * 
     * @param nature
     *            the nature to set
     */
    public void setNature(SymbolNature nature) {
        this.nature = nature;
    }

    /**
     * return true if mouse has just enter in this ray, false otherwise
     * 
     * @return enter flag
     */
    public boolean isLockEnter() {
        return lockEnter;
    }

    /**
     * lock ray enter flag
     */
    public void lockEnter() {
        if (!isLockEnter()) {
            lockEnter = true;
        }
    }

    /**
     * unlock ray enter
     */
    public void unlockEnter() {
        if (isLockEnter()) {
            lockEnter = false;
        }
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible
     *            the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return the userObject
     */
    public Object getUserObject() {
        return userObject;
    }

    /**
     * @param userObject
     *            the userObject to set
     */
    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }

}
