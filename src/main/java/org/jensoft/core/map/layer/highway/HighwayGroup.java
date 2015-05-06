/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.highway;

import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

public class HighwayGroup {

    private List<Highway> highways;
    private String nature;
    private HighwayGroupRenderer groupRenderer;
    private GeneralPath skelet;
    private GroupRenderingProperties renderingProperties;
    private List<GlyphLayout> glyphLayouts;

    public HighwayGroup(String nature) {
        this();
        this.nature = nature;
    }

    public List<GlyphLayout> getGlyphLayouts() {
        return glyphLayouts;
    }

    public void setGlyphLayouts(List<GlyphLayout> glyphLayouts) {
        this.glyphLayouts = glyphLayouts;
    }

    public GroupRenderingProperties getRenderingProperties() {
        return renderingProperties;
    }

    public void setRenderingProperties(
            GroupRenderingProperties renderingPropoerties) {
        renderingProperties = renderingPropoerties;
    }

    public HighwayGroup() {
        highways = new ArrayList<Highway>();
    }

    public void clearGroup() {
        highways.clear();
    }

    public void addHighway(Highway highway) {
        highways.add(highway);
    }

    public HighwayGroupRenderer getGroupRenderer() {
        return groupRenderer;
    }

    public void setGroupRenderer(HighwayGroupRenderer groupRenderer) {
        this.groupRenderer = groupRenderer;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public int countHighway() {
        return highways.size();
    }

    public Highway getHighway(int index) {
        return highways.get(index);
    }

    public List<Highway> getHighways() {
        return highways;
    }

    public GeneralPath getSkelet() {
        return skelet;
    }

    public void setSkelet(GeneralPath skelet) {
        this.skelet = skelet;
    }

}
