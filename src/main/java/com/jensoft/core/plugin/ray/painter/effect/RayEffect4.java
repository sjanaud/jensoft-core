/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray.painter.effect;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.plugin.ray.Ray;
import com.jensoft.core.plugin.ray.Ray.RayNature;
import com.jensoft.core.window.Window2D;

public class RayEffect4 extends AbstractRayEffect {

    public RayEffect4() {

    }

    @Override
    public void paintRayEffect(Graphics2D g2d, Ray ray) {
        if (ray.getRayNature() == RayNature.XRay) {
            paintEffectXRay_ef4(g2d, ray);
        }
        if (ray.getRayNature() == RayNature.YRay) {
            paintEffectYRay_ef4(g2d, ray);
        }
    }

    private void paintEffectXRay_ef4(Graphics2D g2d, Ray bar) {

        // Graphics2D partGraphics =bar.getPart().getGraphics2D();

        Window2D w2d = bar.getHost().getWindow2D();

        Point2D p2dUser = null;
        if (bar.isAscent()) {
            p2dUser = new Point2D.Double(0, bar.getRayBase()
                    + bar.getRayValue());
        }
        if (bar.isDescent()) {
            p2dUser = new Point2D.Double(0, bar.getRayBase()
                    - bar.getRayValue());
        }
        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        Point2D p2dUserBase = new Point2D.Double(0, bar.getRayBase());
        Point2D p2ddeviceBase = w2d.userToPixel(p2dUserBase);

        double x = bar.getRayShape().getX();
        double y = (int) p2ddevice.getY();
        if (bar.isDescent()) {
            y = (int) p2ddeviceBase.getY();
        }
        double width = bar.getRayShape().getWidth();
        double height = Math.abs(p2ddeviceBase.getY() - p2ddevice.getY());

        Shape shapeEffect = null;

        // int inset = 2;
        // x = x+inset;
        // y = y+inset;
        // width = width - 2*inset;
        // height = height - 2*inset;
        // if(bar.getMorpheStyle() == MorpheStyle.Round){
        // double round = bar.getRound();
        // if(bar.isAscent()){
        // GeneralPath barPath = new GeneralPath();
        // barPath.moveTo(x, y + round);
        // barPath.quadTo(x+width/2,y+height/2,x, y + height);
        // barPath.lineTo(x + width, y + height);
        // barPath.quadTo(x+width/2,y+height/2,x + width, y + round);
        // barPath.quadTo(x + width, y, x + width - round, y);
        // barPath.lineTo(x + round, y);
        // barPath.quadTo(x, y, x, y + round);
        // barPath.closePath();
        // shapeEffect = barPath;
        // }
        // else if(bar.isDescent()){
        // GeneralPath barPath = new GeneralPath();
        // barPath.moveTo(x, y);
        // barPath.quadTo(x+width/2,y+height/2,x, y + height-round);
        // barPath.quadTo(x , y+height, x + round, y+height);
        // barPath.lineTo(x + width-round, y + height);
        // barPath.quadTo(x + width, y+height, x + width, y+height-round);
        // barPath.quadTo(x+width/2,y+height/2,x + width, y);
        // barPath.closePath();
        // shapeEffect = barPath;
        // }
        // }
        // else if(bar.getMorpheStyle() == MorpheStyle.Rectangle){
        // GeneralPath barPath = new GeneralPath();
        // barPath.moveTo(x, y);
        // barPath.quadTo(x+width/2, y+height/2, x,y+height);
        // barPath.lineTo(x+width, y+height);
        // barPath.quadTo(x+width/2, y+height/2, x+width,y);
        // barPath.closePath();
        // shapeEffect = barPath;
        // }

        GeneralPath barPath = new GeneralPath();
        barPath.moveTo(x, y);
        barPath.quadTo(x + width / 2, y + height / 2, x, y + height);
        barPath.lineTo(x + width, y + height);
        barPath.quadTo(x + width / 2, y + height / 2, x + width, y);
        barPath.closePath();
        shapeEffect = barPath;

        Rectangle2D boun2D2 = shapeEffect.getBounds2D();

        Point2D start = null;
        Point2D end = null;
        if (bar.isAscent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY()
                    + boun2D2.getHeight());
        }
        else if (bar.isDescent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY()
                    + boun2D2.getHeight());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
        }

        float[] dist = { 0.0f, 0.33f, 0.66f, 1.0f };
        Color[] colors = { new Color(255, 255, 255, 180),
                new Color(255, 255, 255, 0), new Color(40, 40, 40, 0),
                new Color(40, 40, 40, 100) };
        LinearGradientPaint p2 = new LinearGradientPaint(start, end, dist,
                                                         colors);

        g2d.setPaint(p2);
        // partGraphics.setPaint(p2);
        g2d.fill(shapeEffect);
        // partGraphics.fill(shapeEffect);

    }

    private void paintEffectYRay_ef4(Graphics2D g2d, Ray bar) {

        Window2D w2d = bar.getHost().getWindow2D();

        Point2D p2dUser = null;
        if (bar.isAscent()) {
            p2dUser = new Point2D.Double(bar.getRayBase() + bar.getRayValue(),
                                         0);
        }
        if (bar.isDescent()) {
            p2dUser = new Point2D.Double(bar.getRayBase() - bar.getRayValue(),
                                         0);
        }

        Point2D p2ddevice = w2d.userToPixel(p2dUser);

        Point2D p2dUserBase = new Point2D.Double(bar.getRayBase(), 0);
        Point2D p2ddeviceBase = w2d.userToPixel(p2dUserBase);

        double y = bar.getRayShape().getY();
        double x = (int) p2ddeviceBase.getX();
        if (bar.isAscent()) {
            x = (int) p2ddeviceBase.getX();
        }
        if (bar.isDescent()) {
            x = (int) p2ddevice.getX();
        }

        double height = bar.getRayShape().getHeight();
        double width = Math.abs(p2ddevice.getX() - p2ddeviceBase.getX());
        Shape shapeEffect = null;

        // int inset = 2;
        // x = x+inset;
        // y = y+inset;
        // width = width - 2*inset;
        // height = height - 2*inset;
        // if(bar.getMorpheStyle() == MorpheStyle.Round){
        // double round = bar.getRound();
        // GeneralPath barPath = new GeneralPath();
        // if(bar.isAscent()){
        // barPath.moveTo(x, y);
        // barPath.quadTo(x+width/2,y+height/2,x + width - round, y);
        // barPath.quadTo(x + width, y, x + width, y + round);
        // barPath.lineTo(x + width, y + height - round);
        // barPath.quadTo(x + width, y + height, x + width - round, y + height);
        // barPath.quadTo(x+width/2,y+height/2,x, y + height);
        // barPath.closePath();
        //
        // }
        // else if(bar.isDescent()){
        //
        // barPath.moveTo(x+round, y);
        // barPath.quadTo(x+width/2,y+height/2,x + width , y);
        // barPath.lineTo(x + width , y+height);
        // barPath.quadTo(x+width/2,y+height/2,x + round , y+height);
        // barPath.quadTo(x , y+height, x , y + height- round);
        // barPath.lineTo(x , y + round);
        // barPath.quadTo(x, y, x+round, y);
        // barPath.closePath();
        //
        // }
        // shapeEffect = barPath;
        // }
        // else{
        //
        // GeneralPath barPath = new GeneralPath();
        // barPath.moveTo(x, y);
        // barPath.quadTo(x+width/2, y+height/2, x+width,y);
        // barPath.lineTo(x+width, y+height);
        // barPath.quadTo(x+width/2, y+height/2, x,y+height);
        // barPath.closePath();
        // shapeEffect = barPath;
        //
        // }

        GeneralPath barPath = new GeneralPath();
        barPath.moveTo(x, y);
        barPath.quadTo(x + width / 2, y + height / 2, x + width, y);
        barPath.lineTo(x + width, y + height);
        barPath.quadTo(x + width / 2, y + height / 2, x, y + height);
        barPath.closePath();
        shapeEffect = barPath;

        Rectangle2D boun2D2 = shapeEffect.getBounds2D();

        Point2D start = null;
        Point2D end = null;
        if (bar.isAscent()) {
            start = new Point2D.Double(boun2D2.getX() + boun2D2.getWidth(),
                                       boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
        }
        else if (bar.isDescent()) {
            start = new Point2D.Double(boun2D2.getX(), boun2D2.getY());
            end = new Point2D.Double(boun2D2.getX() + boun2D2.getWidth(),
                                     boun2D2.getY());
        }

        float[] dist = { 0.0f, 0.33f, 0.66f, 1.0f };
        Color[] colors2 = { new Color(255, 255, 255, 180),
                new Color(255, 255, 255, 0), new Color(40, 40, 40, 0),
                new Color(40, 40, 40, 100) };
        LinearGradientPaint p2 = new LinearGradientPaint(start, end, dist,
                                                         colors2);

        g2d.setPaint(p2);
        g2d.fill(shapeEffect);
    }
}
