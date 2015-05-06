/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.highway;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.map.layer.AbstractMapLayer;
import org.jensoft.core.map.projection.Map2D;

/**
 *
 *
 */
public class HighwayLayer extends AbstractMapLayer {

    private List<Highway> registeredHighways;

    private HighwayGroup serviceGroup;
    private HighwayGroup residentialGroup;
    private HighwayGroup tertiaryGroup;
    private HighwayGroup secondaryGroup;
    private HighwayGroup primaryGroup;
    private HighwayGroup unclassifiedGroup;
    private HighwayGroup roadGroup;
    private HighwayGroup motorwayGroup;
    private HighwayGroup motorwayLinkGroup;
    private HighwayGroup pedestrianGroup;
    private HighwayGroup trunkGroup;
    private HighwayGroup trunkLinkGroup;

    private PrimaryGroupRenderer primaryGroupRenderer;
    private SecondaryGroupRenderer secondaryGroupRenderer;
    private TertiaryGroupRenderer tertiaryGroupRenderer;
    private MotorwayGroupRenderer motorwayGroupRenderer;
    private PedestrianGroupRenderer pedestrianGroupRenderer;
    private TrunkGroupRenderer trunkGroupRenderer;

    // properties
    private Color skeletBackground = Color.WHITE;

    public HighwayLayer() {

        registeredHighways = new ArrayList<Highway>();

        serviceGroup = new HighwayGroup(HighwayNature.SERVICE);
        residentialGroup = new HighwayGroup(HighwayNature.RESIDENTIAL);
        unclassifiedGroup = new HighwayGroup(HighwayNature.UNCLASSIFIED);
        tertiaryGroup = new HighwayGroup(HighwayNature.TERTIARY);
        secondaryGroup = new HighwayGroup(HighwayNature.SECONDARY);
        primaryGroup = new HighwayGroup(HighwayNature.PRIMARY);
        roadGroup = new HighwayGroup(HighwayNature.ROAD);
        motorwayGroup = new HighwayGroup(HighwayNature.MOTORWAY);
        motorwayLinkGroup = new HighwayGroup(HighwayNature.MOTORWAY_LINK);
        pedestrianGroup = new HighwayGroup(HighwayNature.PEDESTRIAN);
        trunkGroup = new HighwayGroup(HighwayNature.TRUNK);
        trunkLinkGroup = new HighwayGroup(HighwayNature.TRUNK_LINK);

        registerGroup(serviceGroup);
        registerGroup(residentialGroup);
        registerGroup(unclassifiedGroup);
        registerGroup(tertiaryGroup);
        registerGroup(secondaryGroup);
        registerGroup(primaryGroup);
        registerGroup(roadGroup);
        registerGroup(motorwayGroup);
        registerGroup(motorwayLinkGroup);
        registerGroup(pedestrianGroup);
        registerGroup(trunkGroup);
        registerGroup(trunkLinkGroup);

        primaryGroupRenderer = new PrimaryGroupRenderer();
        secondaryGroupRenderer = new SecondaryGroupRenderer();
        tertiaryGroupRenderer = new TertiaryGroupRenderer();
        motorwayGroupRenderer = new MotorwayGroupRenderer();
        pedestrianGroupRenderer = new PedestrianGroupRenderer();
        trunkGroupRenderer = new TrunkGroupRenderer();
    }

    public void registerHighway(Highway highway) {
        if (!isAlreadyRegister(highway)) {
            registeredHighways.add(highway);
            super.registerPrimitive(highway.getPrimitive());
        }
    }

    @Override
    public void clearLayer() {

        // clear
        super.clearLayer();

        // clear groups
        serviceGroup.clearGroup();
        residentialGroup.clearGroup();
        unclassifiedGroup.clearGroup();
        tertiaryGroup.clearGroup();
        secondaryGroup.clearGroup();
        primaryGroup.clearGroup();
        roadGroup.clearGroup();
        motorwayGroup.clearGroup();
        motorwayLinkGroup.clearGroup();
        pedestrianGroup.clearGroup();
        trunkGroup.clearGroup();
        trunkLinkGroup.clearGroup();

        // clear highways
        registeredHighways.clear();

    }

    public void registerHighways(List<Highway> highways) {
        for (Highway h : highways) {
            registerHighway(h);
        }
    }

    private boolean isAlreadyRegister(Highway h) {
        for (Highway highway : registeredHighways) {
            if (highway.equals(h)) {
                return true;
            }
        }
        return false;
    }

    private List<HighwayGroup> groups = new ArrayList<HighwayGroup>();

    private void registerGroup(HighwayGroup group) {
        groups.add(group);
    }

    public List<Highway> getRegisteredHighways() {
        return registeredHighways;
    }

    public void resolveGroup() {

        // clear groups
        serviceGroup.clearGroup();
        residentialGroup.clearGroup();
        unclassifiedGroup.clearGroup();
        tertiaryGroup.clearGroup();
        secondaryGroup.clearGroup();
        primaryGroup.clearGroup();
        roadGroup.clearGroup();
        motorwayGroup.clearGroup();
        motorwayLinkGroup.clearGroup();
        pedestrianGroup.clearGroup();
        trunkGroup.clearGroup();
        trunkLinkGroup.clearGroup();

        for (Highway h : registeredHighways) {
            String nature = h.getNature();
            if (nature.equals(HighwayNature.SERVICE)) {
                serviceGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.RESIDENTIAL)) {
                residentialGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.UNCLASSIFIED)) {
                unclassifiedGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.TERTIARY)) {
                tertiaryGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.SECONDARY)) {
                secondaryGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.PRIMARY)) {
                primaryGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.ROAD)) {
                roadGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.MOTORWAY)) {
                motorwayGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.MOTORWAY_LINK)) {
                motorwayLinkGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.PEDESTRIAN)) {
                pedestrianGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.TRUNK)) {
                trunkGroup.addHighway(h);
            }
            if (nature.equals(HighwayNature.TRUNK_LINK)) {
                trunkLinkGroup.addHighway(h);
            }

        }
    }

    public HighwayGroup getGroup(String nature) {
        for (HighwayGroup hg : groups) {
            if (hg.getNature().equals(nature)) {
                return hg;
            }
        }
        return null;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.map.layer.AbstractMapLayer#doPaintMap(com.jensoft.core.map.projection.Map2D)
     */
    @Override
    public void doPaintMap(Map2D map2D) {
        project();
        resolveGroup();
        paintSkelet(map2D.getGraphics2D());
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.map.layer.AbstractMapLayer#doPaint(java.awt.Graphics2D)
     */
    @Override
    public void doPaint(Graphics2D g2d) {
        project();
        resolveGroup();
        paintSkelet(g2d);
    }

    public void paintSkelet(Graphics2D g2d) {

        FontRenderContext frc = g2d.getFontRenderContext();
        Skelet skelet = new Skelet(frc);
        skelet.addHighwayGroup(serviceGroup);
        skelet.addHighwayGroup(residentialGroup);
        skelet.addHighwayGroup(unclassifiedGroup);
        skelet.addHighwayGroup(tertiaryGroup);
        skelet.addHighwayGroup(secondaryGroup);
        skelet.addHighwayGroup(primaryGroup);
        skelet.addHighwayGroup(roadGroup);
        skelet.addHighwayGroup(motorwayGroup);
        skelet.addHighwayGroup(motorwayLinkGroup);
        skelet.addHighwayGroup(pedestrianGroup);
        skelet.addHighwayGroup(trunkGroup);
        skelet.addHighwayGroup(trunkLinkGroup);

        skelet.makeOutlineSkelet();

        // STREET BACKGROUND
        g2d.setColor(skeletBackground);
        g2d.fill(skelet.getSkeletPath());

        // HIGHWAY RENDERING
        pedestrianGroupRenderer.paintHighwayGroup(g2d, pedestrianGroup);
        tertiaryGroupRenderer.paintHighwayGroup(g2d, tertiaryGroup);
        secondaryGroupRenderer.paintHighwayGroup(g2d, secondaryGroup);
        primaryGroupRenderer.paintHighwayGroup(g2d, primaryGroup);
        trunkGroupRenderer.paintHighwayGroup(g2d, trunkGroup);
        trunkGroupRenderer.paintHighwayGroup(g2d, trunkLinkGroup);
        motorwayGroupRenderer.paintHighwayGroup(g2d, motorwayGroup);
        motorwayGroupRenderer.paintHighwayGroup(g2d, motorwayLinkGroup);

        // STEET BORDER
        g2d.setColor(new Color(171, 158, 137));
        Stroke s = new BasicStroke(1.4f);
        g2d.setStroke(s);
        g2d.draw(skelet.getSkeletPath());

         // GLYPHS
         skelet.makeGlyphSkelet();
         List<GlyphLayout> glayouts = skelet.getGlyphLayouts();
         for (int i = 0; i < glayouts.size(); i++) {
         GlyphLayout gl = glayouts.get(i);
            gl.drawGlyph(g2d);
         }

    }

    public Color getSkeletBackground() {
        return skeletBackground;
    }

    public void setSkeletBackground(Color skeletBackground) {
        this.skeletBackground = skeletBackground;
    }

}
