/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.highway;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Vector;

public class GlyphLayout {

    private GlyphVector glyphVector;

    private GeneralPath path;
    private String text;
    private HighwayPath highwayPath;
    private FontRenderContext frc;
    // private Font font = new Font("C&C Red Alert",Font.PLAIN,11);
    // private Font font = new Font("Share-TechMono",Font.PLAIN,11);
    // private Font font = new Font("Arista 2.0 Light",Font.PLAIN,12);
    private Font font;// = new Font("Sansation",Font.PLAIN,12);

    private int minOffset = 30;

    private Vector<Glyph> glyphItems = new Vector<Glyph>();

    public String getText() {
        return text;
    }

    public Vector<Glyph> getGlyphItems() {
        return glyphItems;
    }

    public GlyphLayout(String text, GeneralPath path, FontRenderContext frc,
            Font font) {
        this.text = text;
        this.font = font;
        this.frc = frc;
        this.path = path;
        highwayPath = new HighwayPath(path);
        glyphVector = font.createGlyphVector(frc, text);

    }

    private float[] getTokenWidths() {

        float[] widths = new float[glyphVector.getNumGlyphs()];
        for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {
            glyphVector.getGlyphMetrics(j).getAdvanceX();
            float width = (float) glyphVector.getGlyphLogicalBounds(j)
                    .getBounds2D().getWidth();
            widths[j] = width;
        }
        return widths;
    }

    private float getGlyphWidth() {

        float w = 0;
        float[] widths = getTokenWidths();
        for (int i = 0; i < widths.length; i++) {
            w = w + widths[i];
        }
        return w;
    }

    private float getGlyphWidthAtToken(int tokenIndex) {

        float w = 0;
        float[] widths = getTokenWidths();
        for (int i = 0; i < tokenIndex; i++) {
            w = w + widths[i];
        }
        return w;
    }

    public void calculateGlyph() {

        float place = getGlyphWidth() + 2 * minOffset;
        float pl = highwayPath.lengthOfPath();

        int number = (int) (pl / place);
        if (number > 2) {
            number = 2;
        }
        float realPlace = pl / number;

        float realOffset = (realPlace - getGlyphWidth()) / 2;

        AffineTransform af = new AffineTransform();
        for (int i = 0; i < number; i++) {

            Glyph item = new Glyph(text, i);
            glyphItems.add(item);

            for (int j = 0; j < glyphVector.getNumGlyphs(); j++) {

                Point2D p = glyphVector.getGlyphPosition(j);
                float px = (float) p.getX();
                float py = (float) p.getY();

                Point2D pointGlyph = highwayPath.pointAtLength(i * realPlace
                        + realOffset + getGlyphWidthAtToken(j));

                Shape glyph = glyphVector.getGlyphOutline(j);
                Shape glyphBound2D = glyphVector.getGlyphOutline(j)
                        .getBounds2D();

                float angle = highwayPath.angleAtLength(i * realPlace
                        + realOffset + getGlyphWidthAtToken(j));
                af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
                af.rotate(angle);
                af.translate(-px, -py + 4);

                Shape ts = af.createTransformedShape(glyph);
                item.addGlyphOutline(ts);

                Shape tsBound2D = af.createTransformedShape(glyphBound2D);
                item.addGlyphTransformBound(tsBound2D);

            }

        }

    }

    public int countItemLock() {
        int count = 0;
        for (Glyph gi : glyphItems) {
            if (gi.islock()) {
                count++;
            }
        }
        return count;
    }

    public int countItem() {
        return glyphItems.size();
    }

    public void drawGlyph(Graphics2D g2d) {
        //System.out.println("PAINT GLYPH LAYOUT :" + text);
        //System.out.println("ITEM COUNT : :" + countItem());
        //System.out.println("LOCK ITEM COUNT : :" + countItemLock());

        g2d.setFont(font);
        g2d.setColor(Color.DARK_GRAY);
        for (Glyph gi : glyphItems) {

            // Vector<Shape> boundTransforms = gi.getGlyphBoundTransforms();
            // g2d.setColor(Color.ORANGE);
            // //g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            // 0.7f));
            // for(Shape s : boundTransforms){
            //
            // g2d.draw(s);
            // }

            Vector<Shape> shapes = gi.getGlyphOutlines();
            if (gi.islock()) {

                // g2d.draw(gi.getGlyphBound2D());

                g2d.setComposite(AlphaComposite.getInstance(
                                                            AlphaComposite.SRC_OVER, 1f));
                for (Shape s : shapes) {

                    //g2d.setColor(Color.WHITE);
                    //g2d.draw(s);
                    g2d.setColor(Color.BLACK);
                    g2d.fill(s);
                }
            }
            // else
            // System.out.println("NO PAINT :"+text);

            // Area bound = gi.getGlyphBound();
            //
            // g2d.setColor(Color.ORANGE);
            // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            // 0.4f));
            // g2d.fill(bound);
            // g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
            // 1f));
        }

    }



}
