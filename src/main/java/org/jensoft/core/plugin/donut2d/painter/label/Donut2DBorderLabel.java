/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter.label;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.RoundRectangle2D;

import org.jensoft.core.glyphmetrics.GeometryPath;
import org.jensoft.core.plugin.donut2d.Donut2D;
import org.jensoft.core.plugin.donut2d.Donut2DPlugin;
import org.jensoft.core.plugin.donut2d.Donut2DSlice;
import org.jensoft.core.plugin.donut2d.Donut2DToolkit;
import org.jensoft.core.plugin.donut2d.Donut2D.Donut2DNature;
import org.jensoft.core.plugin.donut2d.painter.Donut2DSlicePainter;

/**
 * <code>Donut2DBorderLabel</code>
 * <p>
 * Donut2D Border label lay out donut2D slice labels on donut border side, on left or right side<br>
 * <p>
 * <br>
 * <br>
 * <img src="doc-files/Donut3DBorderLabel.png"> <br>
 * <br>
 * Create a Donut3D Border Label:
 * 
 * <pre>
 * Donut2DBorderLabel label1 = Donut2DToolkit.createBorderLabel(&quot;Symbian&quot;, ColorPalette.WHITE, InputFonts.getNeuropol(12),
 *                                                              50);
 * label1.setStyle(Style.Both);
 * label1.setOutlineStroke(s);
 * label1.setShader(fractions, colors);
 * label1.setOutlineColor(RosePalette.REDWOOD);
 * label1.setOutlineRound(20);
 * label1.setLinkColor(RosePalette.REDWOOD);
 * label1.setLinkExtends(30);
 * label1.setLinkStyle(LinkStyle.Quad);
 * s1.setSliceLabel(label1);
 * </pre>
 * 
 * @see Donut2D
 * @see Donut2DSlice
 * @see Donut2DPlugin
 * @see Donut2DSlicePainter
 * @see AbstractDonut2DSliceLabel
 * @see Donut2DToolkit
 * @author Sebastien Janaud
 */
public class Donut2DBorderLabel extends AbstractDonut2DSliceLabel {

    /** margin */
    private int margin = 50;

    /** link */
    private boolean link = true;

    /** link marker */
    private boolean linkMarker = true;

    /** link color */
    private Color linkColor;

    /** link fill color */
    private Color linkMarkerFillColor;

    /** link draw color */
    private Color linkMarkerDrawColor;

    /** link stroke */
    private Stroke linkStroke = new BasicStroke();

    /** link extends */
    private int linkExtends = 20;

    /** link style, line is default */
    private LinkStyle linkStyle = LinkStyle.Line;

    /**
     * defines link geometry style, should be quadratic curve, line curve, etc.
     * 
     * @author Sebastien Janaud
     */
    public enum LinkStyle {
        Quad("quad"),
        Line("line");

        /** link curve style */
        private String style;

        /**
         * create link curve style
         * 
         * @param style
         *            the style name
         */
        private LinkStyle(String style) {
            this.style = style;
        }

        /**
         * @return the style
         */
        public String getStyle() {
            return style;
        }

        /**
         * parse the specified style into {@link LinkStyle}
         * 
         * @param style
         *            the string style to parse
         * @return the curve link style
         */
        public static LinkStyle parseStyle(String style) {
            if (LinkStyle.Quad.getStyle().equalsIgnoreCase(style)) {
                return Quad;
            }
            if (LinkStyle.Line.getStyle().equalsIgnoreCase(style)) {
                return Line;
            }
            return Quad;
        }
    }

    public Donut2DBorderLabel() {
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     * @param labelColor
     *            the color to set
     * @param labelFont
     *            the font to set
     */
    public Donut2DBorderLabel(String label, Color labelColor, Font labelFont) {
        super.setLabel(label);
        super.setLabelColor(labelColor);
        super.setLabelFont(labelFont);
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     * @param labelColor
     *            the color to set
     */
    public Donut2DBorderLabel(String label, Color labelColor) {
        this(label, labelColor, null);
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     */
    public Donut2DBorderLabel(String label) {
        this(label, null, null);
    }

    /**
     * @return the linkStyle
     */
    public LinkStyle getLinkStyle() {
        return linkStyle;
    }

    /**
     * @param linkStyle
     *            the linkStyle to set
     */
    public void setLinkStyle(LinkStyle linkStyle) {
        this.linkStyle = linkStyle;
    }

    /**
     * @return the link
     */
    public boolean isLink() {
        return link;
    }

    /**
     * @param link
     *            the link to set
     */
    public void setLink(boolean link) {
        this.link = link;
    }

    /**
     * @return the linkMarker
     */
    public boolean isLinkMarker() {
        return linkMarker;
    }

    /**
     * @param linkMarker
     *            the linkMarker to set
     */
    public void setLinkMarker(boolean linkMarker) {
        this.linkMarker = linkMarker;
    }

    /**
     * @return the linkMarkerFillColor
     */
    public Color getLinkMarkerFillColor() {
        return linkMarkerFillColor;
    }

    /**
     * @param linkMarkerFillColor
     *            the linkMarkerFillColor to set
     */
    public void setLinkMarkerFillColor(Color linkMarkerFillColor) {
        this.linkMarkerFillColor = linkMarkerFillColor;
    }

    /**
     * @return the linkMarkerDrawColor
     */
    public Color getLinkMarkerDrawColor() {
        return linkMarkerDrawColor;
    }

    /**
     * @param linkMarkerDrawColor
     *            the linkMarkerDrawColor to set
     */
    public void setLinkMarkerDrawColor(Color linkMarkerDrawColor) {
        this.linkMarkerDrawColor = linkMarkerDrawColor;
    }

    /**
     * @return the linkExtends
     */
    public int getLinkExtends() {
        return linkExtends;
    }

    /**
     * @param linkExtends
     *            the linkExtends to set
     */
    public void setLinkExtends(int linkExtends) {
        this.linkExtends = linkExtends;
    }

    /**
     * @return the linkStroke
     */
    public Stroke getLinkStroke() {
        return linkStroke;
    }

    /**
     * @param linkStroke
     *            the linkStroke to set
     */
    public void setLinkStroke(Stroke linkStroke) {
        this.linkStroke = linkStroke;
    }

    /**
     * @return the margin
     */
    public int getMargin() {
        return margin;
    }

    /**
     * set margin in pixel
     * 
     * @param margin
     *            the margin to set
     */
    public void setMargin(int margin) {
        this.margin = margin;
    }

    /**
     * get link color
     * 
     * @return the linkColor
     */
    public Color getLinkColor() {
        return linkColor;
    }

    /**
     * set link color
     * 
     * @param linkColor
     *            the linkColor to set
     */
    public void setLinkColor(Color linkColor) {
        this.linkColor = linkColor;
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut2d.painter.label.AbstractDonut2DSliceLabel#paintDonut2DSliceLabel(java.awt.Graphics2D, com.jensoft.core.plugin.donut2d.Donut2D, com.jensoft.core.plugin.donut2d.Donut2DSlice)
     */
    @Override
    protected void paintDonut2DSliceLabel(Graphics2D g2d, Donut2D donut2D,
            Donut2DSlice donut2DSlice) {
        g2d.setColor(donut2DSlice.getThemeColor());

        if (getLabel() == null) {
            return;
        }

        if (getLabelFont() != null) {
            g2d.setFont(getLabelFont());
        }

        FontMetrics fm = g2d.getFontMetrics();
        int widthText = fm.stringWidth(getLabel());
        int ascentText = fm.getAscent();
        int descentText = fm.getDescent();

        double centerX = donut2D.getCenterX();
        double centerY = donut2D.getCenterY();

        Point2D c = null;
        if (donut2D.getNature() == Donut2DNature.User) {
            c = donut2D.getHostPlugin().getProjection()
                    .userToPixel(new Point2D.Double(centerX, centerY));
        }
        if (donut2D.getNature() == Donut2DNature.Device) {
            c = new Point2D.Double(centerX, centerY);
        }
        centerX = c.getX();
        centerY = c.getY();
        double radius = donut2D.getOuterRadius();

        double medianDegree = donut2DSlice.getMedianAngleDegree();
        if (medianDegree >= 360) {
            medianDegree = medianDegree - 360;
        }

        double px1 = c.getX() + (radius + donut2DSlice.getDivergence())
                * Math.cos(Math.toRadians(medianDegree));
        double py1 = c.getY() - (radius + donut2DSlice.getDivergence())
                * Math.sin(Math.toRadians(medianDegree));

        double px2 = c.getX()
                + (radius + linkExtends + donut2DSlice.getDivergence())
                * Math.cos(Math.toRadians(medianDegree));
        double py2 = c.getY()
                - (radius + linkExtends + donut2DSlice.getDivergence())
                * Math.sin(Math.toRadians(medianDegree));

        double px3 = 0;
        double py3 = py2;
        if (medianDegree >= 270 && medianDegree <= 360
                || medianDegree >= 0 && medianDegree <= 90) {
            px3 = c.getX() + donut2D.getOuterRadius() + margin - getLabelPaddingX() - 5;
        }
        else {// 90-->270
            px3 = c.getX() - donut2D.getOuterRadius() - margin + getLabelPaddingX() + 5;
        }

        double px4 = 0;
        double py4 = py2;
        if (medianDegree >= 270 && medianDegree <= 360
                || medianDegree >= 0 && medianDegree <= 90) {
            px4 = c.getX() + donut2D.getOuterRadius() + margin;
        }
        else {// 90-->270
            px4 = c.getX() - donut2D.getOuterRadius() - margin - widthText;
        }

        // debug points

        // g2d.setColor(Color.RED);
        // Rectangle2D r1= new Rectangle2D.Double(px1,py1,2,2);
        // g2d.draw(r1);
        //
        // g2d.setColor(Color.BLACK);
        // Rectangle2D r2 = new Rectangle2D.Double(px2,py2,2,2);
        // g2d.draw(r2);
        //
        // g2d.setColor(Color.BLUE);
        // Rectangle2D r3 = new Rectangle2D.Double(px3,py3,2,2);
        // g2d.draw(r3);
        //
        // g2d.setColor(Color.YELLOW);
        // Rectangle2D r4 = new Rectangle2D.Double(px4,py4,2,2);
        // g2d.draw(r4);

        if (isLink()) {

            g2d.setColor(donut2DSlice.getThemeColor());
            if (linkColor != null) {
                g2d.setColor(linkColor);
            }
            if (linkStroke != null) {
                g2d.setStroke(linkStroke);
            }

            if (linkStyle == LinkStyle.Line) {
                Line2D l1 = new Line2D.Double(px1, py1, px2, py2);
                Line2D l2 = new Line2D.Double(px2, py2, px3, py3);
                g2d.draw(l1);
                g2d.draw(l2);
            }
            else if (linkStyle == LinkStyle.Quad) {
                QuadCurve2D quad = new QuadCurve2D.Double(px1, py1, px2, py2,
                                                          px3, py3);
                g2d.draw(quad);
            }

            if (isLinkMarker()) {
                int markerExtends = 10;
                double pxMarker = c.getX()
                        + (radius + markerExtends + donut2DSlice.getDivergence())
                        * Math.cos(Math.toRadians(medianDegree));
                double pyMarker = c.getY()
                        - (radius + markerExtends + donut2DSlice.getDivergence())
                        * Math.sin(Math.toRadians(medianDegree));

                GeometryPath geometry = new GeometryPath(new Line2D.Double(
                                                                           pxMarker, pyMarker, px1, py1));

                double pxRight;
                double pyRight;
                double pxLeft;
                double pyLeft;

                float angle = geometry.angleAtLength(0);
                int markerOrtho = 3;

                pxRight = pxMarker - markerOrtho * Math.sin(angle);
                pyRight = pyMarker + markerOrtho * Math.cos(angle);

                pxLeft = pxMarker + markerOrtho * Math.sin(angle);
                pyLeft = pyMarker - markerOrtho * Math.cos(angle);

                GeneralPath e = new GeneralPath();
                e.moveTo(pxRight, pyRight);
                e.lineTo(pxLeft, pyLeft);
                e.lineTo(px1, py1);
                e.closePath();

                // Ellipse2D e = new Ellipse2D.Double(px1-4,py1-4,8,8);

                if (linkMarkerFillColor != null) {
                    g2d.setColor(linkMarkerFillColor);
                }
                else {
                    if (linkColor != null) {
                        g2d.setColor(linkColor);
                    }
                    else {
                        g2d.setColor(donut2DSlice.getThemeColor());
                    }
                }
                g2d.fill(e);

                if (linkMarkerDrawColor != null) {
                    g2d.setColor(linkMarkerDrawColor);
                    g2d.draw(e);
                }

            }

        }

        g2d.setColor(donut2DSlice.getThemeColor());
        g2d.setStroke(getDefaultStroke());

        // //debug
        // g2d.setColor(Color.RED);
        // Rectangle2D r1 = new Rectangle2D.Double(px1,py1,2,2);
        // //g2d.draw(r1);
        // Rectangle2D r2 = new Rectangle2D.Double(px2,py2,2,2);
        // //g2d.draw(r2);
        // Rectangle2D r3 = new Rectangle2D.Double(px3,py3,2,2);
        // g2d.draw(r3);

        float x, y;
        x = (float) px4;
        y = (float) py4;

        RoundRectangle2D rect = new RoundRectangle2D.Double(x - getLabelPaddingX(),
                                                            y - ascentText - getLabelPaddingY(), widthText + 2
                                                                    * getLabelPaddingX(),
                                                            ascentText + descentText + 2 * getLabelPaddingY(),
                                                            getOutlineRound(),
                                                            getOutlineRound());

        if (getStyle() == Style.Fill || getStyle() == Style.Both) {

            if (getFillColor() != null && getShadeFractions() == null) {
                g2d.setColor(getFillColor());
                g2d.fill(rect);
            }

            if (getFillColor() == null && getShadeFractions() == null) {
                g2d.setColor(donut2DSlice.getThemeColor());
                g2d.fill(rect);
            }

            Point2D start2 = new Point2D.Double(rect.getX(), rect.getY()
                    + rect.getHeight());
            Point2D end2 = new Point2D.Double(rect.getX(), rect.getY());

            float[] dist2 = { 0f, 0.4f, 0.6f, 1.0f };
            Color cStart2 = new Color(40, 40, 40, 80);
            Color cStart2bis = new Color(40, 40, 40, 10);
            Color cEnd2bis = new Color(255, 255, 255, 10);
            Color cEnd2 = new Color(240, 240, 240, 80);

            Color[] colors2 = { cStart2, cStart2bis, cEnd2bis, cEnd2 };

            if (getShadeFractions() != null && getShadeColors() != null) {
                colors2 = getShadeColors();
                dist2 = getShadeFractions();
            }

            if (!start2.equals(end2)) {
                LinearGradientPaint p2 = new LinearGradientPaint(start2, end2,
                                                                 dist2, colors2);

                g2d.setPaint(p2);
                g2d.fill(rect);
            }
        }

        if (getStyle() == Style.Stroke || getStyle() == Style.Both) {
            if (getOutlineColor() != null) {
                g2d.setColor(getOutlineColor());
            }
            else {
                g2d.setColor(donut2DSlice.getThemeColor().brighter());
            }
            if (getOutlineStroke() != null) {
                g2d.setStroke(getOutlineStroke());
            }

            g2d.draw(rect);
        }

        g2d.setColor(donut2DSlice.getThemeColor());
        g2d.setStroke(getDefaultStroke());

        g2d.setColor(donut2DSlice.getThemeColor().brighter());
        if (getLabelColor() != null) {
            g2d.setColor(getLabelColor());
        }
        g2d.drawString(getLabel(), x, y);

    }

}
