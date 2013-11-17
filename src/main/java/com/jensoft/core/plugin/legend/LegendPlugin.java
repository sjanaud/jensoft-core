/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.jensoft.core.glyphmetrics.GlyphGeometry;
import com.jensoft.core.glyphmetrics.GlyphUtil;
import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.legend.LegendConstraints.LegendAlignment;
import com.jensoft.core.plugin.legend.LegendConstraints.LegendPosition;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>LegendPlugin</code>
 * 
 * @author Sebastien Janaud
 */
public class LegendPlugin extends AbstractPlugin {
    
    
    

    /** legend registry */
    private List<Legend> legends;

    /**
     * create legend plugin
     */
    public LegendPlugin() {
    	setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        setDithering(Dithering.On);
        setName(LegendPlugin.class.getCanonicalName());
        legends = new ArrayList<Legend>();
    }

    /**
     * create new legend plugin and add the specified legend
     * 
     * @param legend
     *            the legend to add
     */
    public LegendPlugin(Legend legend) {
        this();
        addLegend(legend);
    }

    /**
     * add specified legend to this legend plugin
     * 
     * @param legend
     *            the legend to add
     */
    public void addLegend(Legend legend) {
        legend.setHost(this);
        if (!legends.contains(legend)) {
            legends.add(legend);
        }
    }


    /**
     * true if the context is accessible for this legend, false otherwise
     * 
     * @param windowPart
     *            the window part
     * @param legend
     *            the legend
     * @return true if the context is accessible, false otherwise
     */
    private boolean isAccessible(WindowPart windowPart, Legend legend) {

        LegendConstraints contraints = legend.getConstraints();
        if (contraints == null) {
            return false;
        }
        if (contraints.getPosition() == LegendPosition.South
                && windowPart != WindowPart.South) {
            return false;
        }
        if (contraints.getPosition() == LegendPosition.North
                && windowPart != WindowPart.North) {
            return false;
        }
        if (contraints.getPosition() == LegendPosition.West
                && windowPart != WindowPart.West) {
            return false;
        }
        if (contraints.getPosition() == LegendPosition.East
                && windowPart != WindowPart.East) {
            return false;
        }

        if (windowPart == WindowPart.Device) {
            return false;
        }

        return true;
    }

    /**
     * solve legend
     * 
     * @param view2D
     * @param g2d
     * @param legend
     */
    private void solveLegends(View2D view2D, Graphics2D g2d, Legend legend) {
        LegendConstraints contraints = legend.getConstraints();
        legend.clearLegendGlyph();
        String labelLegend = legend.getText();
        g2d.setFont(legend.getFont());
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector legendGlyphVector = legend.getFont().createGlyphVector(frc,
                                                                           labelLegend);
        float legendWidth = GlyphUtil.getGlyphWidth(legendGlyphVector);

        View2D v2d = legend.getHost().getWindow2D().getView2D();

        if (contraints.getPosition() == LegendPosition.West
                || contraints.getPosition() == LegendPosition.East) {

            JComponent windowComponent = null;
            double depth = 0;
            if (contraints.getPosition() == LegendPosition.West) {
                windowComponent = v2d.getWindowComponent(WindowPart.West);
                depth = windowComponent.getWidth()
                        * (1 - contraints.getDepth());
            }
            if (contraints.getPosition() == LegendPosition.East) {
                windowComponent = v2d.getWindowComponent(WindowPart.East);
                depth = windowComponent.getWidth() * contraints.getDepth();
            }

            int height = windowComponent.getHeight();

            AffineTransform af = new AffineTransform();
            for (int i = 0; i < legendGlyphVector.getNumGlyphs(); i++) {

                Point2D p = legendGlyphVector.getGlyphPosition(i);
                float px = (float) p.getX();
                float py = (float) p.getY();

                Point2D pointGlyph = null;
                if (contraints.getAlignement() == LegendAlignment.Middle) {
                    pointGlyph = new Point2D.Double(depth, height
                            / 2
                            + legendWidth
                            / 2
                            - GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,
                                                             i));
                }
                else if (contraints.getAlignement() == LegendAlignment.Left) {
                    pointGlyph = new Point2D.Double(depth, height
                            - GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,
                                                             i));
                }
                else if (contraints.getAlignement() == LegendAlignment.Rigth) {
                    pointGlyph = new Point2D.Double(depth, legendWidth
                            - GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,
                                                             i));
                }

                Shape glyph = legendGlyphVector.getGlyphOutline(i);
                af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
                af.rotate(-Math.PI / 2);
                af.translate(-px, -py);
                Shape ts = af.createTransformedShape(glyph);

                Point2D srcNorth = new Point2D.Double(glyph.getBounds2D()
                        .getCenterX(), glyph.getBounds2D().getY());
                Point2D dstNorth = new Point2D.Double();

                Point2D srcSouth = new Point2D.Double(glyph.getBounds2D()
                        .getCenterX(), glyph.getBounds2D().getY()
                        + glyph.getBounds2D().getHeight());
                Point2D dstSouth = new Point2D.Double();

                Point2D srcEast = new Point2D.Double(glyph.getBounds2D().getX()
                        + glyph.getBounds2D().getWidth(), glyph.getBounds2D()
                        .getCenterY());
                Point2D dstEast = new Point2D.Double();

                Point2D srcWest = new Point2D.Double(
                                                     glyph.getBounds2D().getX(), glyph.getBounds2D()
                                                             .getCenterY());
                Point2D dstWest = new Point2D.Double();

                af.transform(srcNorth, dstNorth);
                af.transform(srcSouth, dstSouth);
                af.transform(srcEast, dstEast);
                af.transform(srcWest, dstWest);

                GlyphGeometry legendGlyph = new GlyphGeometry(ts, dstNorth,
                                                              dstSouth, dstWest, dstEast);

                legend.addLegendGlyph(legendGlyph);
            }

        }

        if (contraints.getPosition() == LegendPosition.South
                || contraints.getPosition() == LegendPosition.North) {

            JComponent windowComponent = null;
            int startX = 0;
            int width = 0;
            double depth = 0;
            if (contraints.getPosition() == LegendPosition.South) {
                windowComponent = v2d.getWindowComponent(WindowPart.South);
                depth = windowComponent.getHeight() * contraints.getDepth();
            }
            else if (contraints.getPosition() == LegendPosition.North) {
                windowComponent = v2d.getWindowComponent(WindowPart.North);
                depth = windowComponent.getHeight()
                        * (1 - contraints.getDepth());
            }

            startX = v2d.getPlaceHolderAxisWest();
            width = windowComponent.getWidth() - v2d.getPlaceHolderAxisEast()
                    - v2d.getPlaceHolderAxisWest();

            AffineTransform af = new AffineTransform();

            for (int i = 0; i < legendGlyphVector.getNumGlyphs(); i++) {

                Point2D p = legendGlyphVector.getGlyphPosition(i);
                float px = (float) p.getX();
                float py = (float) p.getY();

                Point2D pointGlyph = null;

                if (contraints.getAlignement() == LegendAlignment.Middle) {
                    pointGlyph = new Point2D.Double(startX
                            + width
                            / 2
                            - legendWidth
                            / 2
                            + GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,
                                                             i), depth);
                }
                else if (contraints.getAlignement() == LegendAlignment.Rigth) {
                    pointGlyph = new Point2D.Double(startX
                            + width
                            - legendWidth
                            + GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,
                                                             i), depth);
                }
                else if (contraints.getAlignement() == LegendAlignment.Left) {
                    pointGlyph = new Point2D.Double(startX
                            + GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,
                                                             i), depth);
                }

                Shape glyph = legendGlyphVector.getGlyphOutline(i);
                af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
                af.translate(-px, -py);
                Shape ts = af.createTransformedShape(glyph);

                Point2D srcNorth = new Point2D.Double(glyph.getBounds2D()
                        .getCenterX(), glyph.getBounds2D().getY());
                Point2D dstNorth = new Point2D.Double();

                Point2D srcSouth = new Point2D.Double(glyph.getBounds2D()
                        .getCenterX(), glyph.getBounds2D().getY()
                        + glyph.getBounds2D().getHeight());
                Point2D dstSouth = new Point2D.Double();

                Point2D srcEast = new Point2D.Double(glyph.getBounds2D().getX()
                        + glyph.getBounds2D().getWidth(), glyph.getBounds2D()
                        .getCenterY());
                Point2D dstEast = new Point2D.Double();

                Point2D srcWest = new Point2D.Double(
                                                     glyph.getBounds2D().getX(), glyph.getBounds2D()
                                                             .getCenterY());
                Point2D dstWest = new Point2D.Double();

                af.transform(srcNorth, dstNorth);
                af.transform(srcSouth, dstSouth);
                af.transform(srcEast, dstEast);
                af.transform(srcWest, dstWest);

                GlyphGeometry legendGlyph = new GlyphGeometry(ts, dstNorth,
                                                              dstSouth, dstWest, dstEast);

                legend.addLegendGlyph(legendGlyph);

            }

        }
    }

    /**
     * paint legend
     * 
     * @param v2d
     * @param g2d
     * @param windowPart
     */
    public void paintLegend(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        for (Legend legend : legends) {

            if (isAccessible(windowPart, legend)) {

                solveLegends(v2d, g2d, legend);

                if (legend.getLegendFill() != null) {
                    legend.getLegendFill().paintLegend(g2d, legend);
                }
                if (legend.getLegendDraw() != null) {
                    legend.getLegendDraw().paintLegend(g2d, legend);
                }

            }
        }

    }
    
   
    @Override
    public final void paintPlugin(View2D v2d, Graphics2D g2d,
            WindowPart windowPart) {
        paintLegend(v2d, g2d, windowPart);
    }

}
