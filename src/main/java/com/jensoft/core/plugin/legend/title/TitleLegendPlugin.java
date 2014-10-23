/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.title;

import java.awt.Color;
import java.awt.Font;
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
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendAlignment;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendPosition;
import com.jensoft.core.plugin.legend.title.painter.fil.LegendGradientFill;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>LegendPlugin</code>
 * 
 * @author Sebastien Janaud
 */
public class TitleLegendPlugin extends AbstractPlugin {
    
    
    

    /** legend registry */
    private List<TitleLegend> legends;

    /**
     * create legend plugin
     */
    public TitleLegendPlugin() {
    	setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        setDithering(Dithering.On);
        setName(TitleLegendPlugin.class.getCanonicalName());
        legends = new ArrayList<TitleLegend>();
    }

    /**
     * create new legend plugin and add the specified legend
     * 
     * @param legend
     *            the legend to add
     */
    public TitleLegendPlugin(TitleLegend legend) {
        this();
        addLegend(legend);
    }

    /**
     * add specified legend to this legend plugin
     * 
     * @param legend
     *            the legend to add
     */
    public void addLegend(TitleLegend legend) {
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
    private boolean isAccessible(WindowPart windowPart, TitleLegend legend) {

        TitleLegendConstraints contraints = legend.getConstraints();
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
    private void solveLegends(View2D view2D, Graphics2D g2d, TitleLegend legend) {
        TitleLegendConstraints contraints = legend.getConstraints();
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

        for (TitleLegend legend : legends) {

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
    
   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
     */
    @Override
    public final void paintPlugin(View2D v2d, Graphics2D g2d,
            WindowPart windowPart) {
        paintLegend(v2d, g2d, windowPart);
    }
    
    
    /**
     * create default black legend with default constraints, south on the right
     * 
     * @param label
     *            the label legend to draw
     * @return legend
     */
    public static TitleLegend createLegend(String label) {
        TitleLegend legend = new TitleLegend(label);
        legend.setLegendFill(new LegendGradientFill());
        legend.setConstraints(new TitleLegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create default black legend with default constraints, south on the right
     * 
     * @param label
     *            the legend label
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static TitleLegend createLegend(String label,
            TitleLegendConstraints constraints) {
        TitleLegend legend = new TitleLegend(label);
        legend.setLegendFill(new LegendGradientFill());
        legend.setConstraints(constraints);
        return legend;
    }

    /**
     * create default black legend with default constraints, south on the right
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @return legend
     */
    public static TitleLegend createLegend(String label, Font font) {
        TitleLegend legend = new TitleLegend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill());
        legend.setConstraints(new TitleLegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create default black legend with default constraints, south on the right
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static TitleLegend createLegend(String label, Font font,
            TitleLegendConstraints constraints) {
        TitleLegend legend = new TitleLegend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill());
        legend.setConstraints(constraints);
        return legend;
    }

    /**
     * create default black legend with default constraints, south on the right
     * 
     * @param label
     *            the legend label
     * @param color
     *            the legend color
     * @return legend
     */
    public static TitleLegend createLegend(String label, Color color) {
        TitleLegend legend = new TitleLegend(label);
        legend.setLegendFill(new LegendGradientFill(color, color));
        legend.setConstraints(new TitleLegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param color
     *            the legend color
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static TitleLegend createLegend(String label, Color color,
            TitleLegendConstraints constraints) {
        TitleLegend legend = new TitleLegend(label);
        legend.setLegendFill(new LegendGradientFill(color, color));
        legend.setConstraints(constraints);
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @param color
     *            the legend color
     * @return legend
     */
    public static TitleLegend createLegend(String label, Font font, Color color) {
        TitleLegend legend = new TitleLegend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill(color, color));
        legend.setConstraints(new TitleLegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @param color
     *            the legend color
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static TitleLegend createLegend(String label, Font font, Color color,
            TitleLegendConstraints constraints) {
        TitleLegend legend = new TitleLegend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill(color, color));
        legend.setConstraints(constraints);
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param color1
     *            the legend shading start color
     * @param color2
     *            the legend shading end color
     * @return legend
     */
    public static TitleLegend createLegend(String label, Color color1, Color color2) {
        TitleLegend legend = new TitleLegend(label);
        legend.setLegendFill(new LegendGradientFill(color1, color2));
        legend.setFont(InputFonts.getNeuropol(12));
        legend.setConstraints(new TitleLegendConstraints(LegendPosition.North, 0.1f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param color1
     *            the legend shading start color
     * @param color2
     *            the legend shading end color
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static TitleLegend createLegend(String label, Color color1, Color color2,
            TitleLegendConstraints constraints) {
        TitleLegend legend = new TitleLegend(label);
        legend.setLegendFill(new LegendGradientFill(color1, color2));
        legend.setConstraints(constraints);
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @param color1
     *            the legend shading start color
     * @param color2
     *            the legend shading end color
     * @return legend
     */
    public static TitleLegend createLegend(String label, Font font, Color color1,
            Color color2) {
        TitleLegend legend = new TitleLegend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill(color1, color2));
        legend.setConstraints(new TitleLegendConstraints(LegendPosition.South, 0.8f,
                                                    LegendAlignment.Rigth));
        return legend;
    }

    /**
     * create legend with specified parameters
     * 
     * @param label
     *            the legend label
     * @param font
     *            the legend font
     * @param color1
     *            the legend shading start color
     * @param color2
     *            the legend shading end color
     * @param constraints
     *            the legend constraints
     * @return legend
     */
    public static TitleLegend createLegend(String label, Font font, Color color1,
            Color color2, TitleLegendConstraints constraints) {
        TitleLegend legend = new TitleLegend(label);
        legend.setFont(font);
        legend.setLegendFill(new LegendGradientFill(color1, color2));
        legend.setConstraints(constraints);
        return legend;
    }

}
