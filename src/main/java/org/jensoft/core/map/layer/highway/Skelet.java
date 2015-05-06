/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.highway;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 
 *
 */
public class Skelet {

    private Vector<HighwayGroup> higwaysGroup;

    private List<GlyphLayout> glyphLayouts;
    private GeneralPath skeletPath;
    private Area skeletArea;
    private Color skeletBackground = Color.WHITE;
    private FontRenderContext frc;

    public Skelet(FontRenderContext frc) {
        this.frc = frc;
        higwaysGroup = new Vector<HighwayGroup>();
        glyphLayouts = new Vector<GlyphLayout>();
        skeletArea = new Area();
        skeletPath = new GeneralPath();
    }

    public Color getSkeletBackground() {
        return skeletBackground;
    }

    public void setSkeletBackground(Color skeletBackground) {
        this.skeletBackground = skeletBackground;
    }

    public void addHighwayGroup(HighwayGroup highwayGroup) {
        higwaysGroup.add(highwayGroup);
    }

    public HighwayGroup getGroup(String nature) {

        for (HighwayGroup hg : higwaysGroup) {
            if (hg.getNature().equals(nature)) {
                return hg;
            }
        }
        return null;
    }

    public void makeOutlineSkelet() {

        if (getGroup(HighwayNature.SERVICE) != null) {
            GeneralPath serviceGroupPath = buildGroupPath(getGroup(HighwayNature.SERVICE));
            BasicStroke serviceStroke = new BasicStroke(6f,
                                                        BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape serviceShape = serviceStroke
                    .createStrokedShape(serviceGroupPath);
            Area serviceArea = new Area(serviceShape);
            skeletArea.add(serviceArea);
        }

        if (getGroup(HighwayNature.RESIDENTIAL) != null) {
            HighwayGroup residentialGroup = getGroup(HighwayNature.RESIDENTIAL);
            GeneralPath residentialGroupPath = buildGroupPath(residentialGroup);
            // BasicStroke residentialStroke = new
            // BasicStroke(14f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            BasicStroke residentialStroke = new BasicStroke(residentialGroup
                    .getRenderingProperties().getSkeletWidth(),
                                                            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape residentialShape = residentialStroke
                    .createStrokedShape(residentialGroupPath);
            Area residentialArea = new Area(residentialShape);
            skeletArea.add(residentialArea);
        }

        if (getGroup(HighwayNature.UNCLASSIFIED) != null) {
            GeneralPath unclassifiedGroupPath = buildGroupPath(getGroup(HighwayNature.UNCLASSIFIED));
            BasicStroke unclassifiedStroke = new BasicStroke(14f,
                                                             BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape unclassifiedShape = unclassifiedStroke
                    .createStrokedShape(unclassifiedGroupPath);
            Area unclassifiedArea = new Area(unclassifiedShape);
            skeletArea.add(unclassifiedArea);
        }
        if (getGroup(HighwayNature.ROAD) != null) {
            GeneralPath roadGroupPath = buildGroupPath(getGroup(HighwayNature.ROAD));
            BasicStroke roadStroke = new BasicStroke(14f,
                                                     BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape roadShape = roadStroke.createStrokedShape(roadGroupPath);
            Area roadArea = new Area(roadShape);
            skeletArea.add(roadArea);
        }
        if (getGroup(HighwayNature.TERTIARY) != null) {
            HighwayGroup group = getGroup(HighwayNature.TERTIARY);
            GeneralPath tertiaryGroupPath = buildGroupPath(group);
            // BasicStroke tertiaryStroke = new
            // BasicStroke(16f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            BasicStroke groupStroke = new BasicStroke(group
                    .getRenderingProperties().getSkeletWidth(),
                                                      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape tertiaryShape = groupStroke
                    .createStrokedShape(tertiaryGroupPath);
            Area tertiaryArea = new Area(tertiaryShape);
            skeletArea.add(tertiaryArea);

            GeneralPath tertiarySkelet = new GeneralPath();
            tertiarySkelet.append(tertiaryArea.getPathIterator(null), false);
            getGroup(HighwayNature.TERTIARY).setSkelet(tertiarySkelet);
        }

        if (getGroup(HighwayNature.SECONDARY) != null) {
            HighwayGroup group = getGroup(HighwayNature.SECONDARY);
            GeneralPath secondaryGroupPath = buildGroupPath(group);
            // BasicStroke secondaryStroke = new
            // BasicStroke(17f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            BasicStroke groupStroke = new BasicStroke(group
                    .getRenderingProperties().getSkeletWidth(),
                                                      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape secondaryShape = groupStroke
                    .createStrokedShape(secondaryGroupPath);
            Area secondaryArea = new Area(secondaryShape);
            skeletArea.add(secondaryArea);

            GeneralPath secondarySkelet = new GeneralPath();
            secondarySkelet.append(secondaryArea.getPathIterator(null), false);
            getGroup(HighwayNature.SECONDARY).setSkelet(secondarySkelet);
        }

        if (getGroup(HighwayNature.PRIMARY) != null) {
            HighwayGroup group = getGroup(HighwayNature.PRIMARY);
            GeneralPath primaryGroupPath = buildGroupPath(group);
            // BasicStroke primaryStroke = new
            // BasicStroke(18f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            BasicStroke groupStroke = new BasicStroke(group
                    .getRenderingProperties().getSkeletWidth(),
                                                      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape primaryShape = groupStroke
                    .createStrokedShape(primaryGroupPath);
            Area primaryArea = new Area(primaryShape);
            skeletArea.add(primaryArea);

            GeneralPath primarySkelet = new GeneralPath();
            primarySkelet.append(primaryArea.getPathIterator(null), false);
            getGroup(HighwayNature.PRIMARY).setSkelet(primarySkelet);
        }
        if (getGroup(HighwayNature.TRUNK) != null) {

            HighwayGroup group = getGroup(HighwayNature.TRUNK);
            GeneralPath trunkGroupPath = buildGroupPath(group);
            // BasicStroke trunkStroke = new
            // BasicStroke(18f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            BasicStroke groupStroke = new BasicStroke(group
                    .getRenderingProperties().getSkeletWidth(),
                                                      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape trunkShape = groupStroke.createStrokedShape(trunkGroupPath);
            Area trunkArea = new Area(trunkShape);
            skeletArea.add(trunkArea);

            GeneralPath trunkSkelet = new GeneralPath();
            trunkSkelet.append(trunkArea.getPathIterator(null), false);
            getGroup(HighwayNature.TRUNK).setSkelet(trunkSkelet);
        }
        if (getGroup(HighwayNature.TRUNK_LINK) != null) {
            HighwayGroup group = getGroup(HighwayNature.TRUNK_LINK);
            GeneralPath trunkLinkGroupPath = buildGroupPath(group);
            // BasicStroke trunkLinkStroke = new
            // BasicStroke(18f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            BasicStroke groupStroke = new BasicStroke(group
                    .getRenderingProperties().getSkeletWidth(),
                                                      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape trunkLinkShape = groupStroke
                    .createStrokedShape(trunkLinkGroupPath);
            Area trunkLinkArea = new Area(trunkLinkShape);
            skeletArea.add(trunkLinkArea);

            GeneralPath trunkLinkSkelet = new GeneralPath();
            trunkLinkSkelet.append(trunkLinkArea.getPathIterator(null), false);
            getGroup(HighwayNature.TRUNK_LINK).setSkelet(trunkLinkSkelet);
        }

        if (getGroup(HighwayNature.MOTORWAY) != null) {
            HighwayGroup group = getGroup(HighwayNature.MOTORWAY);
            GeneralPath motorwayGroupPath = buildGroupPath(getGroup(HighwayNature.MOTORWAY));
            // BasicStroke motorwayStroke = new
            // BasicStroke(18f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            BasicStroke groupStroke = new BasicStroke(group
                    .getRenderingProperties().getSkeletWidth(),
                                                      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape motorwayShape = groupStroke
                    .createStrokedShape(motorwayGroupPath);
            Area motowayArea = new Area(motorwayShape);
            skeletArea.add(motowayArea);

            GeneralPath motorwaySkelet = new GeneralPath();
            motorwaySkelet.append(motowayArea.getPathIterator(null), false);
            getGroup(HighwayNature.MOTORWAY).setSkelet(motorwaySkelet);
        }

        if (getGroup(HighwayNature.MOTORWAY_LINK) != null) {
            HighwayGroup group = getGroup(HighwayNature.MOTORWAY_LINK);
            GeneralPath motorwayLinkGroupPath = buildGroupPath(group);
            // BasicStroke motorwayLinkStroke = new
            // BasicStroke(18f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            BasicStroke groupStroke = new BasicStroke(group
                    .getRenderingProperties().getSkeletWidth(),
                                                      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape motorwayLinkShape = groupStroke
                    .createStrokedShape(motorwayLinkGroupPath);
            Area motorwayLinkArea = new Area(motorwayLinkShape);
            skeletArea.add(motorwayLinkArea);

            GeneralPath motorwayLinkSkelet = new GeneralPath();
            motorwayLinkSkelet.append(motorwayLinkArea.getPathIterator(null),
                                      false);
            getGroup(HighwayNature.MOTORWAY_LINK).setSkelet(motorwayLinkSkelet);
        }

        if (getGroup(HighwayNature.PEDESTRIAN) != null) {
            HighwayGroup group = getGroup(HighwayNature.PEDESTRIAN);
            GeneralPath pedestrianGroupPath = buildGroupPath(getGroup(HighwayNature.PEDESTRIAN));
            // BasicStroke pedestrianStroke = new
            // BasicStroke(8f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
            BasicStroke groupStroke = new BasicStroke(group
                    .getRenderingProperties().getSkeletWidth(),
                                                      BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            Shape pedestrianShape = groupStroke
                    .createStrokedShape(pedestrianGroupPath);
            Area pedestrianArea = new Area(pedestrianShape);
            skeletArea.add(pedestrianArea);
            GeneralPath pedestrianSkelet = new GeneralPath();
            pedestrianSkelet
                    .append(pedestrianArea.getPathIterator(null), false);
            getGroup(HighwayNature.PEDESTRIAN).setSkelet(pedestrianSkelet);
        }

        skeletPath.append(skeletArea.getPathIterator(null), false);

    }

    private GeneralPath buildGroupPath(HighwayGroup group) {
        GeneralPath groupPath = new GeneralPath();

        List<Highway> ghighways = group.getHighways();

        for (int i = 0; i < ghighways.size(); i++) {

            Highway highway = ghighways.get(i);

            highway.calculatePath();

            groupPath.append(highway.getHighwayPath(), false);

        }

        return groupPath;
    }

    public GeneralPath getSkeletPath() {
        return skeletPath;
    }

    public Area getSkeletArea() {
        return skeletArea;
    }

    public void makeGlyphSkelet() {
        glyphLayouts.clear();
        for (HighwayGroup group : higwaysGroup) {
            List<GlyphLayout> glg = new ArrayList<GlyphLayout>();
            group.setGlyphLayouts(glg);

            if (!group.getRenderingProperties().isLockGlyph()) {
                continue;
            }

            for (Highway h : group.getHighways()) {

                GeneralPath path = null;
                if (h.needToReverse()) {
                    path = h.getReversePath();
                }
                else {
                    path = h.getHighwayPath();
                }

                GlyphLayout glyphLayout = new GlyphLayout(h.getName(), path,
                                                          frc, group.getRenderingProperties().getGlyphFont());
                glyphLayout.calculateGlyph();
                glyphLayouts.add(glyphLayout);
                glg.add(glyphLayout);
            }
        }

        resolveLayout();
    }

    private void resolveLayout() {

        for (GlyphLayout gl : glyphLayouts) {

            Vector<Glyph> items = gl.getGlyphItems();

            for (Glyph gi : items) {
                if (gi.islock() && intersects(gi)) {
                    gi.unlock();
                }
            }

        }
    }

    private boolean intersects(Glyph item) {

        for (GlyphLayout gl : glyphLayouts) {
            Vector<Glyph> items = gl.getGlyphItems();
            for (Glyph gi : items) {
                if (!item.equals(gi)) {
                    if (gi.intersect(item) && item.islock()) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public List<GlyphLayout> getGlyphLayouts() {
        return glyphLayouts;
    }

}
