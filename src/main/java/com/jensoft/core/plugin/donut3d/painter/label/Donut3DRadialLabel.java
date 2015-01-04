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
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;

import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.plugin.donut3d.Donut3D;
import com.jensoft.core.plugin.donut3d.Donut3D.Donut3DNature;
import com.jensoft.core.plugin.donut3d.Donut3DPlugin;
import com.jensoft.core.plugin.donut3d.Donut3DSlice;
import com.jensoft.core.plugin.donut3d.Donut3DToolkit;
import com.jensoft.core.plugin.donut3d.painter.Donut3DSlicePainter;

/**
 * <code>Donut3DRadialLabel</code>
 * <p>
 * Donut3D Radial label lay out donut slice labels on donut slice median section extends degree<br>
 * <p>
 * <br>
 * <br>
 * <img src="doc-files/Donut3DRadialLabel.png"> <br>
 * <br>
 * Create a Donut3D Radial Label:
 * 
 * <pre>
 * float[] fractions = { 0f, 0.3f, 0.7f, 1f };
 * Color[] c = { new Color(0, 0, 0, 20), new Color(0, 0, 0, 150),
 *         new Color(0, 0, 0, 150), new Color(0, 0, 0, 20) };
 * Donut3DRadialLabel label1 = Donut3DToolkit.createRadialLabel(&quot;Symbian&quot;,
 *                                                              RosePalette.COALBLACK, InputFonts.getNeuropol(12), 30, 20,
 *                                                              Style.Both);
 * label1.setLabelColor(ColorPalette.WHITE);
 * label1.setOutlineColor(RosePalette.REDWOOD);
 * label1.setShader(fractions, c);
 * s1.setSliceLabel(label1);
 * </pre>
 * 
 * @see Donut3D
 * @see Donut3DPlugin
 * @see Donut3DSlicePainter
 * @see AbstractDonut3DSliceLabel
 * @see Donut3DToolkit
 * @author Sebastien Janaud
 */
public class Donut3DRadialLabel extends AbstractDonut3DSliceLabel {

    /** offset radius */
    private int offsetRadius = 30;

    public Donut3DRadialLabel() {
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
    public Donut3DRadialLabel(String label, Color labelColor, Font labelFont) {
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
    public Donut3DRadialLabel(String label, Color labelColor) {
        this(label, labelColor, null);
    }

    /**
     * create default pie label with given parameters
     * 
     * @param label
     *            the label to set
     */
    public Donut3DRadialLabel(String label) {
        this(label, null, null);
    }

    /**
     * @return the offsetRadius
     */
    public int getOffsetRadius() {
        return offsetRadius;
    }

    /**
     * @param offsetRadius
     *            the offsetRadius to set
     */
    public void setOffsetRadius(int offsetRadius) {
        this.offsetRadius = offsetRadius;
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

        Font f = new Font("Dialog", Font.PLAIN, 12);

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
            c = donut3D.getHostPlugin().getProjection()
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
        double exploseOffsetRadius = new Double(offsetRadius) / 90d
                * donut3D.getTilt();

        double px = c.getX()
                + (donut3D.getOuterA() + donutSection.getDivergence() + offsetRadius)
                * Math.cos(Math.toRadians(medianDegree));
        double py = c.getY()
                - (donut3D.getOuterB() + tiltedDivergence + exploseOffsetRadius)
                * Math.sin(Math.toRadians(medianDegree));

        // Rectangle2D r = new Rectangle2D.Double(px,py,2,2);
        // g2d.draw(r);

        double pt = donut3D.getProjectionThickness();
        float x, y;
        if (medianDegree >= 270 && medianDegree <= 360
                || medianDegree >= 0 && medianDegree <= 90) {
            if (medianDegree >= 270 && medianDegree <= 360) {
                x = (float) px;
                y = (float) (py + fm.getAscent() / 2 + pt);
            }
            else {
                x = (float) px;
                y = (float) (py + fm.getAscent() / 2);
            }
        }
        else {// 90-->270
            if (medianDegree > 180) {
                x = (float) (px - widthText);
                y = (float) (py + fm.getAscent() / 2 + pt);
            }
            else {
                x = (float) (px - widthText);
                y = (float) (py + fm.getAscent() / 2);

            }
        }

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

            if (getShadeFractions() != null && getShadeColors() != null) {
                Point2D start2 = new Point2D.Double(rect.getX(), rect.getY()
                        + rect.getHeight());
                Point2D end2 = new Point2D.Double(rect.getX(), rect.getY());
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
