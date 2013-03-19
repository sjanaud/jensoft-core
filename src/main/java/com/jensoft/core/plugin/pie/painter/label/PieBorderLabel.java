/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.pie.painter.label;

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

import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.plugin.pie.Pie;
import com.jensoft.core.plugin.pie.Pie.PieNature;
import com.jensoft.core.plugin.pie.PiePlugin;
import com.jensoft.core.plugin.pie.PieSlice;
import com.jensoft.core.plugin.pie.PieToolkit;
import com.jensoft.core.plugin.pie.painter.AbstractPieSlicePainter;
import com.jensoft.core.plugin.pie.painter.PieSlicePainter;

/**
 * <code>PieBorderLabel</code>
 * <p>
 * Pie Border label lay out pie slice labels on pie border side, on left or right side<br>
 * <p>
 * <br>
 * <br>
 * <img src="doc-files/PieBorderLabel.png"> <br>
 * <br>
 * create border label on slice
 * 
 * <pre>
 * float[] fractions = { 0f, 0.5f, 1f };
 * Color[] colors = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255), new Color(0, 0, 0, 255) };
 * Stroke s = new BasicStroke(2);
 * PieBorderLabel label = PieToolkit.createBorderLabel(&quot;View&quot;, ColorPalette.WHITE, f, 30);
 * label.setStyle(Style.Both);
 * label.setOutlineStroke(s);
 * label.setShader(fractions, colors);
 * label.setOutlineColor(RosePalette.REDWOOD);
 * label.setOutlineRound(20);
 * label.setLinkColor(RosePalette.REDWOOD);
 * label.setLinkStyle(LinkStyle.Quad);
 * label.setLinkExtends(40);
 * label.setMargin(50);
 * slice.addSliceLabel(label);
 * </pre>
 * 
 * @see PieToolkit
 * @see AbstractPieSliceLabel
 * @see AbstractPieSlicePainter
 * @see PieSlicePainter
 * @see Pie
 * @see PiePlugin
 * @author Sebastien Janaud
 */
public class PieBorderLabel extends AbstractPieSliceLabel {

    /** link */
    private boolean link = true;

    /** margin */
    private int margin = 50;

    /** link style */
    private LinkStyle linkStyle = LinkStyle.Quad;

    /** link extends */
    private int linkExtends = 20;

    /** link color */
    private Color linkColor;

    /** link stroke */
    private Stroke linkStroke;

    /** link marker */
    private boolean linkMarker = true;

    /** link marker fill color */
    private Color linkMarkerFillColor;

    /** link marker draw color */
    private Color linkMarkerDrawColor;

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
            if (LinkStyle.Quad.getStyle().equals(style)) {
                return Quad;
            }
            if (LinkStyle.Line.getStyle().equals(style)) {
                return Line;
            }
            return Quad;
        }
    }

    /**
     * create default border label
     */
    public PieBorderLabel() {
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
    public PieBorderLabel(String label, Color labelColor, Font labelFont) {
        super();
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
    public PieBorderLabel(String label, Color labelColor) {
        this(label, labelColor, null);
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     */
    public PieBorderLabel(String label) {
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
     * @return the linkColor
     */
    public Color getLinkColor() {
        return linkColor;
    }

    /**
     * @param linkColor
     *            the linkColor to set
     */
    public void setLinkColor(Color linkColor) {
        this.linkColor = linkColor;
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
     * @return the margin
     */
    public int getMargin() {
        return margin;
    }

    /**
     * @param margin
     *            the margin to set
     */
    public void setMargin(int margin) {
        this.margin = margin;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.pie.painter.PieLabel#paintPieLabel(java.
     * awt.Graphics2D, com.jensoft.sw2d.core.plugin.pie.Pie,
     * com.jensoft.sw2d.core.plugin.pie.PieSection)
     */
    @Override
    protected void paintPieLabel(Graphics2D g2d, Pie pie, PieSlice pieSection) {

        g2d.setColor(pieSection.getThemeColor());

        double percent = pieSection.getPercent() * 100;

        String s = getDecimalFormat().format(percent) + "%";

        if (getLabel() != null) {
            s = getLabel();
        }

        if (getLabelFont() != null) {
            g2d.setFont(getLabelFont());
        }

        FontMetrics fm = g2d.getFontMetrics();
        int widthText = fm.stringWidth(s);
        int ascentText = fm.getAscent();
        int descentText = fm.getDescent();

        double centerX = pie.getCenterX();
        double centerY = pie.getCenterY();

        Point2D c = null;
        if (pie.getPieNature() == PieNature.PieUser) {
            c = pie.getHostPlugin().getWindow2D()
                    .userToPixel(new Point2D.Double(centerX, centerY));
        }
        if (pie.getPieNature() == PieNature.PieDevice) {
            c = new Point2D.Double(centerX, centerY);
        }
        centerX = c.getX();
        centerY = c.getY();
        double radius = pie.getRadius();

        double medianDegree = pieSection.getMedianAngleDegree();
        if (medianDegree >= 360) {
            medianDegree = medianDegree - 360;
        }

        double px1 = c.getX() + (radius + pieSection.getDivergence())
                * Math.cos(Math.toRadians(medianDegree));
        double py1 = c.getY() - (radius + pieSection.getDivergence())
                * Math.sin(Math.toRadians(medianDegree));

        double px2 = c.getX()
                + (radius + linkExtends + pieSection.getDivergence())
                * Math.cos(Math.toRadians(medianDegree));
        double py2 = c.getY()
                - (radius + linkExtends + pieSection.getDivergence())
                * Math.sin(Math.toRadians(medianDegree));

        double px3 = 0;
        double py3 = py2;
        if (medianDegree >= 270 && medianDegree <= 360
                || medianDegree >= 0 && medianDegree <= 90) {
            px3 = c.getX() + pie.getRadius() + margin - getLabelPaddingX() - 5;
        }
        else {// 90-->270
            px3 = c.getX() - pie.getRadius() - margin + getLabelPaddingX() + 5;
        }

        double px4 = 0;
        double py4 = py2;
        if (medianDegree >= 270 && medianDegree <= 360
                || medianDegree >= 0 && medianDegree <= 90) {
            px4 = c.getX() + pie.getRadius() + margin;
        }
        else {// 90-->270
            px4 = c.getX() - pie.getRadius() - margin - widthText;
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

            g2d.setColor(pieSection.getThemeColor());
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
                        + (radius + markerExtends + pieSection.getDivergence())
                        * Math.cos(Math.toRadians(medianDegree));
                double pyMarker = c.getY()
                        - (radius + markerExtends + pieSection.getDivergence())
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
                        g2d.setColor(pieSection.getThemeColor());
                    }
                }
                g2d.fill(e);

                if (linkMarkerDrawColor != null) {
                    g2d.setColor(linkMarkerDrawColor);
                    g2d.draw(e);
                }

            }

        }

        g2d.setColor(pieSection.getThemeColor());
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
                g2d.setColor(pieSection.getThemeColor());
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
                g2d.setColor(pieSection.getThemeColor().brighter());
            }
            if (getOutlineStroke() != null) {
                g2d.setStroke(getOutlineStroke());
            }

            g2d.draw(rect);
        }

        g2d.setColor(pieSection.getThemeColor());
        g2d.setStroke(getDefaultStroke());

        g2d.setColor(pieSection.getThemeColor().brighter());
        if (getLabelColor() != null) {
            g2d.setColor(getLabelColor());
        }
        g2d.drawString(s, x, y);

    }

}
