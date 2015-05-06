/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.layer.highway;

import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

/**
 * <code>Glyph</code>
 * 
 * @author Sebastien Janaud
 */
public class Glyph {

    private String glyphText;
    private int glyphIndex;
    private Vector<Shape> glyphOutlines = new Vector<Shape>();
    private boolean lock = true;
    private Area glyphAreaBound;
    private Vector<Shape> glyphBoundTransforms = new Vector<Shape>();

    public Glyph(String glyphText, int glyphIndex) {
        this.glyphText = glyphText;
        this.glyphIndex = glyphIndex;
    }

    public String getGlyphText() {
        return glyphText;
    }

    public int getGlyphIndex() {
        return glyphIndex;
    }

    public boolean intersect(Glyph gi) {

        Vector<Shape> giShapes = gi.getGlyphOutlines();
        for (int i = 0; i < giShapes.size(); i++) {
            Shape s = giShapes.get(i);

            for (int j = 0; j < glyphOutlines.size(); j++) {
                if (s.intersects(glyphOutlines.get(j).getBounds2D())) {
//                    System.out.println("this : " + glyphText + "|" + glyphIndex
//                            + " intersect with" + gi.getGlyphText() + "|"
//                            + gi.getGlyphIndex());
                    return true;
                }
            }

        }
        return false;
    }

    public void addGlyphOutline(Shape glyphCharacter) {
        glyphOutlines.add(glyphCharacter);
    }

    public void addGlyphTransformBound(Shape glyphBoundTransform) {
        glyphBoundTransforms.add(glyphBoundTransform);
    }

    public void lock() {
        lock = true;
    }

    public void unlock() {
        lock = false;
    }

    public boolean islock() {
        return lock;
    }

    private Area calculateBound() {
        Area area = new Area();
        for (Shape s : glyphBoundTransforms) {
            Area a = new Area(s);
            area.add(a);
        }
        return area;
    }

    public Area getGlyphAreaBound() {
        if (glyphAreaBound == null) {
            glyphAreaBound = calculateBound();
        }
        return glyphAreaBound;
    }

    public Vector<Shape> getGlyphOutlines() {
        return glyphOutlines;
    }

    public Shape getGlyphOutline(int index) {
        return glyphOutlines.get(index);
    }

    public Rectangle2D getGlyphBound2D() {
        double minX = getGlyphBound2D(0).getMinX();
        double maxX = getGlyphBound2D(0).getMaxX();
        double minY = getGlyphBound2D(0).getMinY();
        double maxY = getGlyphBound2D(0).getMaxY();

        for (int i = 0; i < glyphOutlines.size(); i++) {
            Rectangle2D b2d = getGlyphBound2D(i);

            double minx = b2d.getMinX();
            if (minx < minX) {
                minX = minx;
            }
            double maxx = b2d.getMaxX();
            if (maxx > maxX) {
                maxX = maxx;
            }
            double miny = b2d.getMinY();
            if (miny < minY) {
                minY = miny;
            }
            double maxy = b2d.getMaxY();
            if (maxy > maxY) {
                maxY = maxy;
            }

        }
        return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
    }

    public Rectangle2D getGlyphBound2D(int index) {
        return glyphOutlines.get(index).getBounds2D();
    }

    public Vector<Shape> getGlyphBoundTransforms() {
        return glyphBoundTransforms;
    }

    public Shape getGlyphBoundTransform(int index) {
        return glyphBoundTransforms.get(index);
    }

}
