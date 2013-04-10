/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d.painter.label;

import java.awt.AlphaComposite;
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
import java.text.DecimalFormat;

import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.plugin.donut3d.Donut3D;
import com.jensoft.core.plugin.donut3d.Donut3D.Donut3DNature;
import com.jensoft.core.plugin.donut3d.Donut3DPlugin;
import com.jensoft.core.plugin.donut3d.Donut3DSlice;
import com.jensoft.core.plugin.donut3d.Donut3DToolkit;
import com.jensoft.core.plugin.donut3d.painter.Donut3DSlicePainter;

/**
 * <code>Donut3DBorderLabel</code>
 * <p>
 * Donut3D Border label lay out donut3D slice labels on donut border side, on left or right side<br>
 * <p>
 * <br>
 * <br>
 * <img src="doc-files/Donut3DBorderLabel.png"> <br>
 * <br>
 * Create a Donut3D Border Label:
 * 
 * <pre>
 * Donut3DBorderLabel label1 = Donut3DToolkit.createBorderLabel(&quot;Symbian&quot;, ColorPalette.WHITE, InputFonts.getNeuropol(12),
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
 * @see Donut3D
 * @see Donut3DSlice
 * @see Donut3DPlugin
 * @see Donut3DSlicePainter
 * @see AbstractDonut3DSliceLabel
 * @see Donut3DToolkit
 * @author Sebastien Janaud
 */
public class Donut3DBorderLabel extends AbstractDonut3DSliceLabel {

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
    private Stroke linkStroke;

    /** link alignment */
    private LinkAlignment linkAlignment = LinkAlignment.Top;

    /** link extends */
    private int linkExtends = 20;

    /** link style, line is default */
    private LinkStyle linkStyle = LinkStyle.Line;

    /**
     * link alignment for link defines the end point vertical alignment on donut3d Slice
     * 
     * @author Sebastien Janaud
     */
    public enum LinkAlignment {
        Top("top"), Middle("middle"), Bottom("bottom");

        /** link alignment */
        private String linkAlignment;

        /**
         * create link alignment
         * 
         * @param linkAlignment
         *            the link Alignment name
         */
        private LinkAlignment(String linkAlignment) {
            this.linkAlignment = linkAlignment;
        }

        /**
         * @return the style
         */
        public String getLinkAlignment() {
            return linkAlignment;
        }

        /**
         * parse the specified string alignment into {@link LinkAlignment}
         * 
         * @param linkAlignment
         *            the string alignment to parse
         * @return the link alignment
         */
        public static LinkAlignment parseLinkAlignment(String linkAlignment) {
            if (Top.getLinkAlignment().equalsIgnoreCase(linkAlignment)) {
                return Top;
            }
            if (Middle.getLinkAlignment().equalsIgnoreCase(linkAlignment)) {
                return Middle;
            }
            if (Bottom.getLinkAlignment().equalsIgnoreCase(linkAlignment)) {
                return Bottom;
            }
            return Top;
        }
    }

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

    public Donut3DBorderLabel() {
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
    public Donut3DBorderLabel(String label, Color labelColor, Font labelFont) {
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
    public Donut3DBorderLabel(String label, Color labelColor) {
        this(label, labelColor, null);
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     */
    public Donut3DBorderLabel(String label) {
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
     * @return the linkAlignment
     */
    public LinkAlignment getLinkAlignment() {
        return linkAlignment;
    }

    /**
     * @param linkAlignment
     *            the linkAlignment to set
     */
    public void setLinkAlignment(LinkAlignment linkAlignment) {
        this.linkAlignment = linkAlignment;
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
     * @param margin
     *            the margin to set
     */
    public void setMargin(int margin) {
        this.margin = margin;
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

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel#paintDonut3DSliceLabel(java.awt.Graphics2D, com.jensoft.core.plugin.donut3d.Donut3D, com.jensoft.core.plugin.donut3d.Donut3DSlice)
     */
    @Override
    protected void paintDonut3DSliceLabel(Graphics2D g2d, Donut3D donut3D,
            Donut3DSlice donutSection) {

        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));

        g2d.setColor(donutSection.getThemeColor());

        double percent = donutSection.getNormalizedValue() * 100;

        DecimalFormat df = new DecimalFormat("##.##");
        String s = df.format(percent) + "%";

        if (getLabel() != null) {
            s = getLabel();
        }

        Font f = InputFonts.getSansation(12);

        if (getLabelFont() != null) {
            f = getLabelFont();
        }

        g2d.setFont(f);

        FontMetrics fm = g2d.getFontMetrics();
        int widthText = fm.stringWidth(s);

        int ascentText = fm.getAscent();
        int descentText = fm.getDescent();

        double centerX = donut3D.getCenterX();
        double centerY = donut3D.getCenterY();

        Point2D c = null;
        if (donut3D.getDonut3DNature() == Donut3DNature.Donut3DUser) {
            c = donut3D.getHostPlugin().getWindow2D()
                    .userToPixel(new Point2D.Double(centerX, centerY));
        }
        if (donut3D.getDonut3DNature() == Donut3DNature.Donut3DDevice) {
            c = new Point2D.Double(centerX, centerY);
        }
        centerX = c.getX();
        centerY = c.getY();

        double medianDegree = donutSection.getMedianDegree();
        if (medianDegree >= 360) {
            medianDegree = medianDegree - 360;
        }

        // tilt 0--> 90
        double oneDegreeTiltDivergence = new Double(
                                                    donutSection.getDivergence()) / 90d;
        double tiltedDivergence = oneDegreeTiltDivergence * donut3D.getTilt();
        // double exploseOffsetRadius = new Double(offsetRadius) / 90d *
        // donut3D.getTilt();

        // double px = c.getX() + (donut3D.getOuterA() +
        // donutSection.getDivergence() + offsetRadius)*
        // Math.cos(Math.toRadians(medianDegree));
        // double py = c.getY() - (donut3D.getOuterB() + tiltedDivergence +
        // exploseOffsetRadius)* Math.sin(Math.toRadians(medianDegree));

        // Rectangle2D r = new Rectangle2D.Double(px,py,2,2);
        // g2d.draw(r);
        double pt = donut3D.getProjectionThickness();

        double px1 = c.getX()
                + (donut3D.getOuterA() + donutSection.getDivergence())
                * Math.cos(Math.toRadians(medianDegree));
        double py1 = c.getY() - (donut3D.getOuterB() + tiltedDivergence)
                * Math.sin(Math.toRadians(medianDegree));

        double divTilted = new Double(linkExtends) / 90d * donut3D.getTilt();
        double px2 = c.getX()
                + (donut3D.getOuterA() + donutSection.getDivergence() + linkExtends)
                * Math.cos(Math.toRadians(medianDegree));
        double py2 = c.getY()
                - (donut3D.getOuterB() + tiltedDivergence + divTilted)
                * Math.sin(Math.toRadians(medianDegree));

        double px3 = 0;
        double py3 = py2;
        if (medianDegree >= 270 && medianDegree <= 360
                || medianDegree >= 0 && medianDegree <= 90) {
            px3 = c.getX() + donut3D.getOuterA() + margin - getLabelPaddingX() - 5;
        }
        else {// 90-->270
            px3 = c.getX() - donut3D.getOuterA() - margin + getLabelPaddingX() + 5;
        }

        double px4 = 0;
        double py4 = py2;
        if (medianDegree >= 270 && medianDegree <= 360
                || medianDegree >= 0 && medianDegree <= 90) {
            px4 = c.getX() + donut3D.getOuterA() + margin;
        }
        else {// 90-->270
            px4 = c.getX() - donut3D.getOuterA() - margin - widthText;
        }

        if (isLink()) {
            if (donutSection.getMedianDegree() > 180
                    && donutSection.getMedianDegree() < 360) {
                if (linkAlignment == LinkAlignment.Top) {

                }
                else if (linkAlignment == LinkAlignment.Middle) {
                    py1 = py1 + pt / 2;
                    py2 = py2 + pt / 2;
                    py3 = py3 + pt / 2;
                    py4 = py4 + pt / 2;

                }
                else if (linkAlignment == LinkAlignment.Bottom) {
                    py1 = py1 + pt;
                    py2 = py2 + pt;
                    py3 = py3 + pt;
                    py4 = py4 + pt;
                }

            }

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
                if (donutSection.getMedianDegree() > 180 && donutSection
                        .getMedianDegree() < 360
                        || linkAlignment == LinkAlignment.Top) {

                    int markerExtends = 10;
                    // double pxMarker = c.getX() +
                    // (radius+markerExtends+donutSection.getDivergence())*Math.cos(Math.toRadians(medianDegree));
                    // double pyMarker = c.getY() -
                    // (radius+markerExtends+donutSection.getDivergence())*Math.sin(Math.toRadians(medianDegree));
                    double divMarkerTilted = new Double(markerExtends) / 90d
                            * donut3D.getTilt();
                    double pxMarker = c.getX()
                            + (donut3D.getOuterA()
                                    + donutSection.getDivergence() + markerExtends)
                            * Math.cos(Math.toRadians(medianDegree));
                    double pyMarker = c.getY()
                            - (donut3D.getOuterB() + tiltedDivergence + divMarkerTilted)
                            * Math.sin(Math.toRadians(medianDegree));
                    if (linkAlignment == LinkAlignment.Middle) {
                        pyMarker = pyMarker + pt / 2;
                    }
                    else if (linkAlignment == LinkAlignment.Bottom) {
                        pyMarker = pyMarker + pt;
                    }
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
                            g2d.setColor(donutSection.getThemeColor());
                        }
                    }
                    g2d.fill(e);

                    if (linkMarkerDrawColor != null) {
                        g2d.setColor(linkMarkerDrawColor);
                        g2d.draw(e);
                    }
                }
                g2d.setColor(donutSection.getThemeColor());
            }

        }

        g2d.setColor(donutSection.getThemeColor());
        g2d.setStroke(getDefaultStroke());

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
                g2d.setColor(donutSection.getThemeColor());
                g2d.fill(rect);
            }

            Point2D start2 = new Point2D.Double(rect.getX(), rect.getY()
                    + rect.getHeight());
            Point2D end2 = new Point2D.Double(rect.getX(), rect.getY());

            if (getShadeFractions() != null && getShadeColors() != null) {

                if (!start2.equals(end2)) {
                    LinearGradientPaint p2 = new LinearGradientPaint(start2,
                                                                     end2, getShadeFractions(), getShadeColors());

                    g2d.setPaint(p2);
                    g2d.fill(rect);
                }
            }

        }

        if (getStyle() == Style.Stroke || getStyle() == Style.Both) {
            if (getOutlineColor() != null) {
                g2d.setColor(getOutlineColor());
            }
            else {
                g2d.setColor(donutSection.getThemeColor().brighter());
            }
            if (getOutlineStroke() != null) {
                g2d.setStroke(getOutlineStroke());
            }

            g2d.draw(rect);
        }

        g2d.setColor(donutSection.getThemeColor());
        g2d.setStroke(getDefaultStroke());

        g2d.setColor(donutSection.getThemeColor().brighter());
        if (getLabelColor() != null) {
            g2d.setColor(getLabelColor());
        }
        g2d.drawString(s, x, y);

    }

}
