/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.donut3d.painter.paint;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.jensoft.core.glyphmetrics.GeometryPath;
import com.jensoft.core.plugin.donut3d.Donut3D;
import com.jensoft.core.plugin.donut3d.Donut3D.Donut3DNature;
import com.jensoft.core.plugin.donut3d.Donut3DSlice;
import com.jensoft.core.plugin.donut3d.Donut3DSlice.Type;

/**
 * <code>Donut3DDefaultPaint</code> Default Paint for donut3D
 * 
 * @author Sebastien Janaud
 */
public class Donut3DDefaultPaint extends AbstractDonut3DPaint {

    /** incidence angle degree */
    private int incidenceAngleDegree = 90;

    /** paint flag top effect */
    private boolean paintTopEffect = true;

    /** paint flag inner effect */
    private boolean paintInnerEffect = true;

    /** paint flag outer effect */
    private boolean paintOuterEffect = true;

    /** alpha use to paint top effect */
    private float alphaTop = 0.8f;

    /** alpha use to paint inner effect */
    private float alphaInner = 1f;

    /** alpha use to paint outer effect */
    private float alphaOuter = 1f;

    /** alpha use to fill */
    private float alphaFill = 0.7f;

    // fill back flag
    private boolean fillBackBottom = true;
    private boolean fillBackOuter = true;
    private boolean fillBackInner = true;
    private boolean fillBackTop = true;
    private boolean fillBackStart = true;
    private boolean fillBackEnd = true;

    // front back flag
    private boolean fillFrontBottom = true;
    private boolean fillFrontOuter = true;
    private boolean fillFrontInner = true;
    private boolean fillFrontTop = true;
    private boolean fillFrontStart = true;
    private boolean fillFrontEnd = true;

    /**
     * create default effect
     */
    public Donut3DDefaultPaint() {
    }

    /**
     * create effect with the specified given parameter
     * 
     * @param incidenceAngleDegree
     *            the incidence angle degree to set
     */
    public Donut3DDefaultPaint(int incidenceAngleDegree) {
        super();
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * @return the paintTopEffect
     */
    public boolean isPaintTopEffect() {
        return paintTopEffect;
    }

    /**
     * @param paintTopEffect
     *            the paintTopEffect to set
     */
    public void setPaintTopEffect(boolean paintTopEffect) {
        this.paintTopEffect = paintTopEffect;
    }

    /**
     * @return the paintInnerEffect
     */
    public boolean isPaintInnerEffect() {
        return paintInnerEffect;
    }

    /**
     * @param paintInnerEffect
     *            the paintInnerEffect to set
     */
    public void setPaintInnerEffect(boolean paintInnerEffect) {
        this.paintInnerEffect = paintInnerEffect;
    }

    /**
     * @return the paintOuterEffect
     */
    public boolean isPaintOuterEffect() {
        return paintOuterEffect;
    }

    /**
     * @param paintOuterEffect
     *            the paintOuterEffect to set
     */
    public void setPaintOuterEffect(boolean paintOuterEffect) {
        this.paintOuterEffect = paintOuterEffect;
    }

    /**
     * @return the alphaTop
     */
    public float getAlphaTop() {
        return alphaTop;
    }

    /**
     * @param alphaTop
     *            the alphaTop to set
     */
    public void setAlphaTop(float alphaTop) {
        this.alphaTop = alphaTop;
    }

    /**
     * @return the alphaInner
     */
    public float getAlphaInner() {
        return alphaInner;
    }

    /**
     * @param alphaInner
     *            the alphaInner to set
     */
    public void setAlphaInner(float alphaInner) {
        this.alphaInner = alphaInner;
    }

    /**
     * @return the alphaOuter
     */
    public float getAlphaOuter() {
        return alphaOuter;
    }

    /**
     * @param alphaOuter
     *            the alphaOuter to set
     */
    public void setAlphaOuter(float alphaOuter) {
        this.alphaOuter = alphaOuter;
    }

    /**
     * @return the alphaFill
     */
    public float getAlphaFill() {
        return alphaFill;
    }

    /**
     * @param alphaFill
     *            the alphaFill to set
     */
    public void setAlphaFill(float alphaFill) {
        this.alphaFill = alphaFill;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.jensoft.sw2d.core.plugin.donut3d.painter.Donut3DPaint#paintDonut3D
     * (java.awt.Graphics2D, com.jensoft.sw2d.core.plugin.donut3d.Donut3D)
     */
    @Override
    public final void paintDonut3D(Graphics2D g2d, Donut3D donut3d) {
        // System.out.println("-paint donut-");
        List<Donut3DSlice> sections = donut3d.getPaintOrder();
        for (Donut3DSlice section : sections) {

            // System.out.println("paint order : "+section.getName());
            paintDonut3DFill(g2d, donut3d, section);

            if (isPaintTopEffect()) {
                paintTopEffect(g2d, donut3d, section);
            }

            if (isPaintOuterEffect()) {
                paintOuterEffect(g2d, donut3d, section);
            }

            if (isPaintInnerEffect()) {
                paintInnerEffect(g2d, donut3d, section);
            }
            section.setPainted(true);

        }

    }

    public void paintDonut3DFill(Graphics2D g2d, Donut3D donut3d,
            Donut3DSlice s) {

        // System.out.println("paint fill : " + s.getName());

        /**
         * Back fragment outer face
         */
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alphaFill));

        if (s == null) {
            return;
        }

        List<Donut3DSlice> fragments = s.getFragments();
        if (fragments == null) {
            return;
        }

        if (fillBackOuter) {
            for (Donut3DSlice donut3dSection : fragments) {
                if (donut3dSection.getType() == Type.Back) {
                    g2d.setColor(s.getThemeColor());
                    g2d.fill(donut3dSection.getOuterFace());
                }
            }
        }

        /**
         * Back fragment bottom face
         */
        if (fillBackBottom) {
            for (Donut3DSlice donut3dSection : fragments) {
                if (donut3dSection.getType() == Type.Back) {
                    g2d.setColor(s.getThemeColor());
                    g2d.fill(donut3dSection.getBottomFace());
                }
            }
        }

        /**
         * Back fragment inner face
         */
        if (fillBackInner) {
            for (Donut3DSlice fragment : fragments) {
                if (fragment.getType() == Type.Back) {
                    g2d.setColor(s.getThemeColor());

                    Area visibleInnerBackFace = new Area(
                                                         fragment.getInnerFace());
                    visibleInnerBackFace
                            .subtract(new Area(donut3d.getTopFace()));

                    visibleInnerBackFace.subtract(new Area(s
                            .getFrontInnerFace()));
                    // ??
                    // visibleInnerBackFace.subtract(new
                    // Area(donut3dSection.getStartFace()));
                    // visibleInnerBackFace.subtract(new
                    // Area(donut3dSection.getEndFace()));

                    // g2d.setColor(Color.RED);
                    // g2d.draw(visibleInnerBackFace);
                    g2d.fill(visibleInnerBackFace);
                }
            }
        }

        /**
         * Back fragment start and end face
         */

        if (fillBackStart) {
            for (Donut3DSlice fragment : fragments) {
                if (fragment.getType() == Type.Back) {
                    if (s.isFirst(fragment)) {
                        g2d.setColor(s.getThemeColor());
                        g2d.fill(fragment.getStartFace());
                    }

                }
            }
        }
        if (s.getStartAngleDegree() <= 90 && s.getStartAngleDegree() >= 270) {
            paintStartEffect(g2d, donut3d, s);
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alphaFill));
        if (fillBackEnd) {
            for (Donut3DSlice fragment : fragments) {
                if (fragment.getType() == Type.Back) {
                    if (s.isLast(fragment)) {
                        g2d.setColor(s.getThemeColor());
                        g2d.fill(fragment.getEndFace());
                    }
                }
            }
        }
        if (s.getEndAngleDegree() >= 90 && s.getEndAngleDegree() <= 270) {
            paintEndEffect(g2d, donut3d, s);
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alphaFill));

        /**
         * Back fragment top face
         */
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alphaFill));
        if (fillBackTop) {
            for (Donut3DSlice donut3dSection : fragments) {
                if (donut3dSection.getType() == Type.Back) {
                    g2d.setColor(s.getThemeColor());
                    g2d.fill(donut3dSection.getTopFace());
                }
            }
        }

        /***
         * FRONT
         */

        /**
         * Front fragment inner face
         */
        if (fillFrontInner) {
            for (Donut3DSlice fragment : fragments) {
                if (fragment.getType() == Type.Front) {
                    g2d.setColor(s.getThemeColor());
                    // g2d.fill(donut3dSection.getInnerFace());

                    // g2d.setColor(Color.BLACK);
                    Area a = new Area(fragment.getInnerFace());
                    // g2d.draw(a);
                    // a.intersect(new Area(s.getBackInnerFace()));

                    g2d.fill(a);

                }
            }
        }
        /**
         * Front fragment bottom face
         */
        if (fillFrontBottom) {
            for (Donut3DSlice donut3dSection : fragments) {
                if (donut3dSection.getType() == Type.Front) {
                    g2d.setColor(s.getThemeColor());
                    g2d.fill(donut3dSection.getBottomFace());
                }
            }
        }
        /**
         * Front fragment start and end face
         */
        if (fillFrontStart) {
            for (Donut3DSlice fragment : fragments) {
                if (fragment.getType() == Type.Front) {
                    if (s.isFirst(fragment)) {
                        g2d.setColor(s.getThemeColor());
                        g2d.fill(fragment.getStartFace());
                    }

                }
            }
        }
        if (s.getStartAngleDegree() < 90 || s.getStartAngleDegree() > 270) {
            paintStartEffect(g2d, donut3d, s);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alphaFill));
        if (fillFrontEnd) {
            for (Donut3DSlice fragment : fragments) {
                if (fragment.getType() == Type.Front) {
                    if (s.isLast(fragment)) {
                        g2d.setColor(s.getThemeColor());
                        g2d.fill(fragment.getEndFace());
                    }
                }
            }
        }

        if (s.getEndAngleDegree() > 90 && s.getEndAngleDegree() < 270) {
            paintEndEffect(g2d, donut3d, s);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alphaFill));

        /**
         * Front fragment outer face
         */
        if (fillFrontOuter) {
            for (Donut3DSlice donut3dSection : fragments) {
                if (donut3dSection.getType() == Type.Front) {
                    g2d.setColor(s.getThemeColor());
                    g2d.fill(donut3dSection.getOuterFace());
                }
            }
        }
        /**
         * Front fragment top face
         */
        if (fillFrontTop) {
            for (Donut3DSlice donut3dSection : fragments) {
                if (donut3dSection.getType() == Type.Front) {
                    g2d.setColor(s.getThemeColor());
                    g2d.fill(donut3dSection.getTopFace());
                }
            }
        }
    }

    /**
     * @return the incidenceAngleDegree
     */
    public int getIncidenceAngleDegree() {
        return incidenceAngleDegree;
    }

    /**
     * @param incidenceAngleDegree
     *            the incidenceAngleDegree to set
     */
    public void setIncidenceAngleDegree(int incidenceAngleDegree) {
        this.incidenceAngleDegree = incidenceAngleDegree;
    }

    /**
     * paint top effect
     * 
     * @param g2d
     *            the graphics context
     * @param donut3d
     *            the pie
     */
    public void paintTopEffect(Graphics2D g2d, Donut3D donut3d,
            Donut3DSlice section) {

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alphaTop));

        double centerX = donut3d.getCenterX();
        double centerY = donut3d.getCenterY();

        Point2D c = null;
        if (donut3d.getDonut3DNature() == Donut3DNature.Donut3DUser) {
            c = donut3d.getHostPlugin().getWindow2D()
                    .userToPixel(new Point2D.Double(centerX, centerY));
        }
        if (donut3d.getDonut3DNature() == Donut3DNature.Donut3DDevice) {
            c = new Point2D.Double(centerX, centerY);
        }

        centerX = c.getX();
        centerY = c.getY();

        // tilt 0--> 90
        // start Section
        Donut3DSlice startSection = donut3d
                .getSliceOnAngle(incidenceAngleDegree);
        if (startSection == null) {
            return;
        }
        double exploseStartTiltRadius = new Double(startSection.getDivergence()) / 90d;
        double exploseStartRadius = exploseStartTiltRadius * donut3d.getTilt();

        double exploseStartA = startSection.getDivergence();
        double exploseStartB = exploseStartRadius;

        double centerStartX = centerX
                + exploseStartA
                * Math.cos(Math.toRadians(startSection.getStartAngleDegree()
                        + startSection.getExtendsDegree() / 2));
        double centerStartY = centerY
                - exploseStartB
                * Math.sin(Math.toRadians(startSection.getStartAngleDegree()
                        + startSection.getExtendsDegree() / 2));

        double startX = centerStartX + donut3d.getOuterA()
                * Math.cos(Math.toRadians(incidenceAngleDegree));
        double startY = centerStartY - donut3d.getOuterB()
                * Math.sin(Math.toRadians(incidenceAngleDegree));

        // end section

        Donut3DSlice endSection = donut3d
                .getSliceOnAngle(incidenceAngleDegree + 180);
        if (endSection == null) {
            return;
        }
        double exploseEndTiltRadius = new Double(endSection.getDivergence()) / 90d;
        double exploseEndRadius = exploseEndTiltRadius * donut3d.getTilt();

        double exploseEndA = endSection.getDivergence();
        double exploseEndB = exploseEndRadius;

        double centerEndX = centerX
                + exploseEndA
                * Math.cos(Math.toRadians(endSection.getStartAngleDegree()
                        + endSection.getExtendsDegree() / 2));
        double centerEndY = centerY
                - exploseEndB
                * Math.sin(Math.toRadians(endSection.getStartAngleDegree()
                        + endSection.getExtendsDegree() / 2));

        double endX = centerEndX + donut3d.getOuterA()
                * Math.cos(Math.toRadians(incidenceAngleDegree + 180));
        double endY = centerEndY - donut3d.getOuterB()
                * Math.sin(Math.toRadians(incidenceAngleDegree + 180));

        // effect 1: inner
        Point2D start1 = new Point2D.Double(startX, startY);
        Point2D end1 = new Point2D.Double(endX, endY);

        float[] dist1 = { 0f, 0.45f, 0.55f, 1.0f };

        Color cStart1 = new Color(40, 40, 40, 150);
        Color cStart1bis = new Color(40, 40, 40, 0);
        Color cEnd1bis = new Color(255, 255, 255, 0);
        Color cEnd1 = new Color(255, 255, 255, 200);

        Color[] colors1 = { cStart1, cStart1bis, cEnd1bis, cEnd1 };

        LinearGradientPaint p1 = new LinearGradientPaint(start1, end1, dist1,
                                                         colors1);

        g2d.setPaint(p1);

        if (section != null && section.getTopFace() != null) {
            g2d.fill(section.getTopFace());
        }

        // g2d.setColor(Color.BLACK);
        // g2d.drawRect((int)startX, (int)startY, 4, 4);
        // g2d.drawRect((int)endX, (int)endY, 4, 4);

    }

    /**
     * paint outer face
     * 
     * @param g2d
     *            the graphics context
     * @param donut3d
     *            the donut3d
     */
    public void paintOuterEffect(Graphics2D g2d, Donut3D donut3d,
            Donut3DSlice section) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alphaOuter));
        Area outerEffect = new Area();
        for (Donut3DSlice s : donut3d.getSlices()) {
            List<Donut3DSlice> fragments = s.getFragments();
            for (Donut3DSlice fragment : fragments) {
                if (fragment.getType() == Type.Front) {
                    outerEffect.add(new Area(fragment.getOuterFace()));
                }
            }
        }

        Area outerEffect2 = new Area();
        if (section == null || section.getFragments() == null) {
            return;
        }

        List<Donut3DSlice> fragments = section.getFragments();
        for (Donut3DSlice fragment : fragments) {
            if (fragment.getType() == Type.Front) {
                outerEffect2.add(new Area(fragment.getOuterFace()));
            }
        }

        // outerEffect.subtract(new Area(donut3d.getTopFace()));
        // outerEffect.subtract(new Area(donut3d.getStartFace()));
        // outerEffect.subtract(new Area(donut3d.getEndFace()));

        Rectangle2D bound2D = outerEffect.getBounds2D();

        double startX2 = bound2D.getX();
        double startY2 = bound2D.getY();

        double endX2 = bound2D.getX() + bound2D.getWidth();
        double endY2 = bound2D.getY();

        // effect 1: inner
        Point2D start2 = new Point2D.Double(startX2, startY2);
        Point2D end2 = new Point2D.Double(endX2, endY2);

        float[] dist2 = { 0f, 0.4f, 0.8f, 1.0f };

        Color cStart2 = new Color(40, 40, 40, 180);
        Color cStart2bis = new Color(40, 40, 40, 10);
        Color cEnd2bis = new Color(255, 255, 255, 10);
        Color cEnd2 = new Color(255, 255, 255, 180);

        Color[] colors2 = { cStart2, cStart2bis, cEnd2bis, cEnd2 };

        if (!start2.equals(end2)) {
            LinearGradientPaint p2 = new LinearGradientPaint(start2, end2,
                                                             dist2, colors2);

            g2d.setPaint(p2);

            g2d.fill(outerEffect2);
        }
        // g2d.setColor(Color.BLACK);
        // g2d.draw(outerEffect);
    }

    public void paintEndEffect(Graphics2D g2d, Donut3D donut3d,
            Donut3DSlice section) {

        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));

        Line2D lineBottom = section.getEndBottomLine();
        Line2D lineTop = section.getEndTopLine();

        double a = (lineTop.getY1() - lineTop.getY2())
                / (lineTop.getX1() - lineTop.getX2());
        double bTop = lineTop.getY1() - 2 * a * lineTop.getX1();
        double bBottom = lineBottom.getY1() - 2 * a * lineBottom.getX1();

        double distanceLineTop = Math.abs(bBottom - bTop)
                / Math.sqrt(a * a + 1);

        double cxBottom = (lineBottom.getX1() + lineBottom.getX2()) / 2d;
        double cyBottom = (lineBottom.getY1() + lineBottom.getY2()) / 2d;

        double cxTop = (lineTop.getX1() + lineTop.getX2()) / 2d;
        double cyTop = (lineTop.getY1() + lineTop.getY2()) / 2d;

        GeometryPath path = new GeometryPath(lineBottom);
        float topLength = (float) Math.sqrt((lineTop.getX2() - lineTop.getX1())
                * (lineTop.getX2() - lineTop.getX1())
                + (lineTop.getY2() - lineTop.getY1())
                * (lineTop.getY2() - lineTop.getY1()));
        double angleRadian = path.angleAtLength(topLength / 2f);

        double px = cxBottom + distanceLineTop
                * Math.cos(angleRadian + Math.PI / 2);
        double py = cyBottom + distanceLineTop
                * Math.sin(angleRadian + Math.PI / 2);

        Point2D start2 = new Point2D.Double(cxBottom, cyBottom);
        Point2D end2 = new Point2D.Double(px, py);

        float[] dist2 = { 0f, 0.4f, 0.6f, 1.0f };

        Color cStart2 = new Color(40, 40, 40, 140);
        Color cStart2bis = new Color(40, 40, 40, 10);
        Color cEnd2bis = new Color(255, 255, 255, 10);
        Color cEnd2 = new Color(240, 240, 240, 140);

        Color[] colors2 = { cStart2, cStart2bis, cEnd2bis, cEnd2 };

        if (!start2.equals(end2)) {
            LinearGradientPaint p2 = new LinearGradientPaint(start2, end2,
                                                             dist2, colors2);

            g2d.setPaint(p2);

            g2d.fill(section.getEndFace());
        }

    }

    public void paintStartEffect(Graphics2D g2d, Donut3D donut3d,
            Donut3DSlice section) {

        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));
        Line2D lineBottom = section.getStartBottomLine();
        Line2D lineTop = section.getStartTopLine();

        double a = (lineTop.getY1() - lineTop.getY2())
                / (lineTop.getX1() - lineTop.getX2());
        double bTop = lineTop.getY1() - 2 * a * lineTop.getX1();
        double bBottom = lineBottom.getY1() - 2 * a * lineBottom.getX1();

        double distanceLineTop = Math.abs(bBottom - bTop)
                / Math.sqrt(a * a + 1);

        double cxBottom = (lineBottom.getX1() + lineBottom.getX2()) / 2d;
        double cyBottom = (lineBottom.getY1() + lineBottom.getY2()) / 2d;

        double cxTop = (lineTop.getX1() + lineTop.getX2()) / 2d;
        double cyTop = (lineTop.getY1() + lineTop.getY2()) / 2d;

        GeometryPath path = new GeometryPath(lineBottom);
        float topLength = (float) Math.sqrt((lineTop.getX2() - lineTop.getX1())
                * (lineTop.getX2() - lineTop.getX1())
                + (lineTop.getY2() - lineTop.getY1())
                * (lineTop.getY2() - lineTop.getY1()));
        double angleRadian = path.angleAtLength(topLength / 2f);

        double px = cxBottom - distanceLineTop
                * Math.cos(angleRadian + Math.PI / 2);
        double py = cyBottom - distanceLineTop
                * Math.sin(angleRadian + Math.PI / 2);

        Point2D start2 = new Point2D.Double(cxBottom, cyBottom);
        Point2D end2 = new Point2D.Double(px, py);

        float[] dist2 = { 0f, 0.4f, 0.6f, 1.0f };

        Color cStart2 = new Color(40, 40, 40, 140);
        Color cStart2bis = new Color(40, 40, 40, 10);
        Color cEnd2bis = new Color(255, 255, 255, 10);
        Color cEnd2 = new Color(255, 255, 255, 140);

        Color[] colors2 = { cStart2, cStart2bis, cEnd2bis, cEnd2 };

        if (!start2.equals(end2)) {
            LinearGradientPaint p2 = new LinearGradientPaint(start2, end2,
                                                             dist2, colors2);

            g2d.setPaint(p2);

            g2d.fill(section.getStartFace());
        }

    }

    /**
     * paint inner effect
     * 
     * @param g2d
     *            the graphics context
     * @param donut3d
     *            the pie
     */
    public void paintInnerEffect(Graphics2D g2d, Donut3D donut3d,
            Donut3DSlice section) {
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    alphaInner));

        if (section == null || section.getFragments() == null) {
            return;
        }

        Area innerEffect = new Area();
        for (Donut3DSlice s : donut3d.getSlices()) {
            List<Donut3DSlice> fragments = s.getFragments();
            for (Donut3DSlice donut3dSection : fragments) {
                if (donut3dSection.getType() == Type.Back) {
                    g2d.setColor(s.getThemeColor());
                    // g2d.fill(donut3dSection.getInnerFace());
                    Area visibleInnerBackFace = new Area(
                                                         donut3dSection.getInnerFace());
                    visibleInnerBackFace
                            .subtract(new Area(donut3d.getTopFace()));
                    // visibleInnerBackFace.subtract(new
                    // Area(section.getFrontInnerFace()));
                    // g2d.setColor(Color.RED);
                    // g2d.draw(a);
                    innerEffect.add(visibleInnerBackFace);
                }
            }

        }
        // g2d.draw(innerEffect);
        Rectangle2D bound2DInner = innerEffect.getBounds2D();

        double startXInner = bound2DInner.getX() + bound2DInner.getWidth();
        double startYInner = bound2DInner.getY() + bound2DInner.getHeight();

        double endXInner = bound2DInner.getX();
        double endYInner = bound2DInner.getY();

        // effect 1: inner
        Point2D startInner = new Point2D.Double(startXInner, startYInner);
        Point2D endInner = new Point2D.Double(endXInner, endYInner);

        float[] distInner = { 0f, 0.4f, 0.8f, 1.0f };

        Color cStartInner = new Color(40, 40, 40, 160);
        Color cStartInnerbis = new Color(40, 40, 40, 80);
        Color cEndInnerbis = new Color(240, 240, 240, 80);
        Color cEndInner = new Color(240, 240, 240, 160);

        Color[] colorsInner = { cStartInner, cStartInnerbis, cEndInnerbis,
                cEndInner };

        if (!startInner.equals(endInner)) {
            LinearGradientPaint paintInner = new LinearGradientPaint(
                                                                     startInner, endInner, distInner, colorsInner);
            Area a = new Area();

            List<Donut3DSlice> fragments = section.getFragments();
            for (Donut3DSlice donut3dSection : fragments) {
                if (donut3dSection.getType() == Type.Back) {

                    Area visibleInnerBackFace = new Area(
                                                         donut3dSection.getInnerFace());
                    visibleInnerBackFace
                            .subtract(new Area(donut3d.getTopFace()));
                    visibleInnerBackFace.subtract(new Area(section
                            .getFrontInnerFace()));
                    // g2d.setColor(Color.RED);
                    // g2d.draw(a);
                    a.add(visibleInnerBackFace);
                }
            }
            g2d.setPaint(paintInner);

            g2d.fill(a);

        }

    }

}
