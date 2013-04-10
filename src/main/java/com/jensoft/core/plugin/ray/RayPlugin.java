/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.ray;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.ray.Ray.RayNature;
import com.jensoft.core.plugin.ray.Ray.ThicknessType;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

/**
 * RayPlugin knows how to register and draw {@link Ray}
 */
public class RayPlugin extends AbstractPlugin implements
        AbstractPlugin.OnClickListener, AbstractPlugin.OnEnterListener,
        AbstractPlugin.OnExitListener, AbstractPlugin.OnPressListener,
        AbstractPlugin.OnReleaseListener, AbstractPlugin.OnMoveListener,
        AbstractPlugin.OnDragListener {

    /** the ray registry */
    private List<Ray> rays;

    /** listeners */
    private EventListenerList rayListenerList;

    private enum PaintRequest {
        RayLayer, LabelLayer;
    };

    /**
     * create new Ray Plug in
     */
    public RayPlugin() {
        setName("RayPlugin");
        rays = new ArrayList<Ray>();
        rayListenerList = new EventListenerList();
        setOnMoveListener(this);
        setOnClickListener(this);
        setOnReleaseListener(this);
        setOnPressListener(this);
        setOnDragListener(this);
        setPriority(500);
    }

    /**
     * register the specified ray
     * 
     * @param ray
     *            the ray to register
     */
    public void addRay(Ray ray) {
        ray.setHost(this);
        rays.add(ray);
    }

    public void removeRay(Ray ray) {
        ray.setHost(null);
        rays.remove(ray);
    }

    private void checkRay(Ray ray) {
        if (ray.getRayNature() == null) {
            throw new IllegalStateException("Ray nature should be supplied");
        }
        // other check
        // value, thickness, ray, etc
    }

    /**
     * resolve ray registry geometry
     */
    private void resolveRayGeometry() {
        for (Ray ray : rays) {

            checkRay(ray);

            if (ray instanceof StackedRay) {
                resolveStackedRayGeometry((StackedRay) ray);
            }
            else if (ray instanceof RayGroup) {
                resolveRayGroupGeometry((RayGroup) ray);
            }
            else {
                resolveRayGeometry(ray);
            }
        }
    }

    /**
     * resolve ray registry geometry
     */
    public void resolveRayComponent(Ray ray) {

        if (ray instanceof StackedRay) {
            resolveStackedRayGeometry((StackedRay) ray);
        }
        else if (ray instanceof RayGroup) {
            resolveRayGroupGeometry((RayGroup) ray);
        }
        else {
            resolveRayGeometry(ray);
        }

    }

    /**
     * resolve the specified rayGroup geometry
     * 
     * @param rayGroup
     *            the ray group geometry to resolve
     */
    private void resolveRayGroupGeometry(RayGroup rayGroup) {
        rayGroup.copyToRays();
        List<Ray> rays = rayGroup.getRays();
        for (Ray ray : rays) {
            resolveRayGeometry(ray);
        }
    }

    /**
     * resolve the specified ray geometry
     * 
     * @param ray
     *            the ray geometry to resolve
     */
    private void resolveRayGeometry(Ray ray) {
        Window2D w2d = getWindow2D();
        if (ray.getRayNature() == RayNature.XRay) {

            double centerUserX = ray.getRay();
            double centerDeviceX = w2d.userToPixel(
                                                   new Point2D.Double(centerUserX, 0)).getX();

            double deviceRayWidth = 0;
            if (ray.getThicknessType() == ThicknessType.Device) {
                deviceRayWidth = ray.getThickness();
            }
            else {
                double left = centerUserX - ray.getThickness() / 2;
                Point2D pLeft = w2d.userToPixel(new Point2D.Double(left, 0));

                double right = centerUserX + ray.getThickness() / 2;
                Point2D pRight = w2d.userToPixel(new Point2D.Double(right, 0));

                deviceRayWidth = pRight.getX() - pLeft.getX();
            }

            double yUserRayBase = 0;
            if (ray.isAscent()) {
                yUserRayBase = ray.getRayBase();
            }
            if (ray.isDescent()) {
                yUserRayBase = ray.getRayBase() - ray.getRayValue();
            }

            double yDeviceRayBase = w2d.userToPixel(
                                                    new Point2D.Double(0, yUserRayBase)).getY();

            double yUserRayFleche = 0;
            if (ray.isAscent()) {
                yUserRayFleche = ray.getRayBase() + ray.getRayValue();
            }
            if (ray.isDescent()) {
                yUserRayFleche = ray.getRayBase();
            }

            double yDeviceRayFleche = w2d.userToPixel(
                                                      new Point2D.Double(0, yUserRayFleche)).getY();

            double x = centerDeviceX - deviceRayWidth / 2;
            double y = yDeviceRayFleche;
            double width = deviceRayWidth;
            double height = Math.abs(yDeviceRayFleche - yDeviceRayBase);

            Rectangle2D rayShape = new Rectangle2D.Double(x, y, width, height);
            ray.setRayShape(rayShape);

        }
        else if (ray.getRayNature() == RayNature.YRay) {

            double centerUserY = ray.getRay();
            double centerDeviceY = w2d.userToPixel(
                                                   new Point2D.Double(0, centerUserY)).getY();

            double deviceRayHeight = 0;
            if (ray.getThicknessType() == ThicknessType.Device) {
                deviceRayHeight = ray.getThickness();
            }
            else {
                double top = centerUserY - ray.getThickness() / 2;
                Point2D pTop = w2d.userToPixel(new Point2D.Double(0, top));

                double bottom = centerUserY + ray.getThickness() / 2;
                Point2D pBottom = w2d
                        .userToPixel(new Point2D.Double(0, bottom));

                deviceRayHeight = Math.abs(pTop.getY() - pBottom.getY());
            }

            double xUserRayBase = 0;
            if (ray.isAscent()) {
                xUserRayBase = ray.getRayBase();
            }
            if (ray.isDescent()) {
                xUserRayBase = ray.getRayBase() - ray.getRayValue();
            }

            double xDeviceRayBase = w2d.userToPixel(
                                                    new Point2D.Double(xUserRayBase, 0)).getX();

            double xUserRayFleche = 0;
            if (ray.isAscent()) {
                xUserRayFleche = ray.getRayBase() - ray.getRayValue();
            }
            if (ray.isDescent()) {
                xUserRayFleche = ray.getRayBase();
            }

            double xDeviceRayFleche = w2d.userToPixel(
                                                      new Point2D.Double(xUserRayFleche, 0)).getX();

            double x = xDeviceRayBase;
            double y = centerDeviceY - deviceRayHeight / 2;
            double width = Math.abs(xDeviceRayFleche - xDeviceRayBase);
            double height = deviceRayHeight;

            Rectangle2D rayShape = new Rectangle2D.Double(x, y, width, height);
            ray.setRayShape(rayShape);

        }
    }

    /**
     * resolve specified stacked ray geometry
     * 
     * @param stackedRay
     *            the stacked ray geometry to resolve
     */
    private void resolveStackedRayGeometry(StackedRay stackedRay) {
        Window2D w2d = getWindow2D();

        stackedRay.normalize();

        if (stackedRay.getRayNature() == RayNature.XRay) {

            double centerUserX = stackedRay.getRay();
            double centerDeviceX = w2d.userToPixel(
                                                   new Point2D.Double(centerUserX, 0)).getX();

            double deviceRayWidth = 0;
            if (stackedRay.getThicknessType() == ThicknessType.Device) {
                deviceRayWidth = stackedRay.getThickness();
            }
            else {
                double left = centerUserX - stackedRay.getThickness() / 2;
                Point2D pLeft = w2d.userToPixel(new Point2D.Double(left, 0));

                double right = centerUserX + stackedRay.getThickness() / 2;
                Point2D pRight = w2d.userToPixel(new Point2D.Double(right, 0));

                deviceRayWidth = pRight.getX() - pLeft.getX();
            }

            double yUserRayBase = 0;
            if (stackedRay.isAscent()) {
                yUserRayBase = stackedRay.getRayBase();
            }
            if (stackedRay.isDescent()) {
                yUserRayBase = stackedRay.getRayBase()
                        - stackedRay.getRayValue();
            }

            double yDeviceRayBase = w2d.userToPixel(
                                                    new Point2D.Double(0, yUserRayBase)).getY();

            double yUserRayFleche = 0;
            if (stackedRay.isAscent()) {
                yUserRayFleche = stackedRay.getRayBase()
                        + stackedRay.getRayValue();
            }
            if (stackedRay.isDescent()) {
                yUserRayFleche = stackedRay.getRayBase();
            }

            double yDeviceRayFleche = w2d.userToPixel(
                                                      new Point2D.Double(0, yUserRayFleche)).getY();

            double x = centerDeviceX - deviceRayWidth / 2;
            double y = yDeviceRayFleche;
            double width = deviceRayWidth;
            double height = Math.abs(yDeviceRayFleche - yDeviceRayBase);

            Rectangle2D rayShape = new Rectangle2D.Double(x, y, width, height);
            stackedRay.setRayShape(rayShape);

            // stacks
            for (RayStack s : stackedRay.getStacks()) {

                Ray rayStack = new Ray();
                rayStack.setName(s.getStackName());
                rayStack.setRayNature(stackedRay.getRayNature());
                rayStack.setThickness(stackedRay.getThickness());
                rayStack.setThicknessType(stackedRay.getThicknessType());
                rayStack.setRay(stackedRay.getRay());
                rayStack.setRayBase(stackedRay.getStackBase(s));
                rayStack.setThemeColor(s.getThemeColor());
                rayStack.setRayFill(s.getRayFill());
                rayStack.setRayDraw(s.getRayDraw());
                rayStack.setRayEffect(s.getRayEffect());
                if (stackedRay.isAscent()) {
                    rayStack.setAscentValue(s.getNormalizedValue());
                }
                else if (stackedRay.isDescent()) {
                    rayStack.setDescentValue(s.getNormalizedValue());
                }

                double yUserStackRayBase = 0;
                if (stackedRay.isAscent()) {
                    yUserStackRayBase = stackedRay.getStackBase(s);
                }
                if (stackedRay.isDescent()) {
                    yUserStackRayBase = stackedRay.getStackBase(s)
                            - s.getNormalizedValue();
                }

                double yDeviceStackRayBase = w2d.userToPixel(
                                                             new Point2D.Double(0, yUserStackRayBase)).getY();

                double yUserStackRayFleche = 0;
                if (stackedRay.isAscent()) {
                    yUserStackRayFleche = stackedRay.getStackBase(s)
                            + s.getNormalizedValue();
                }
                if (stackedRay.isDescent()) {
                    yUserStackRayFleche = stackedRay.getStackBase(s);
                }

                double yDeviceStackRayFleche = w2d.userToPixel(
                                                               new Point2D.Double(0, yUserStackRayFleche)).getY();

                double stackx = centerDeviceX - deviceRayWidth / 2;
                double stacky = yDeviceStackRayFleche;
                double stackwidth = deviceRayWidth;
                double stackheight = Math.abs(yDeviceStackRayFleche
                        - yDeviceStackRayBase);

                Rectangle2D stackRayShape = new Rectangle2D.Double(stackx,
                                                                   stacky, stackwidth, stackheight);
                rayStack.setRayShape(stackRayShape);

                s.setRay(rayStack);
            }

        }
        else if (stackedRay.getRayNature() == RayNature.YRay) {

            double centerUserY = stackedRay.getRay();
            double centerDeviceY = w2d.userToPixel(
                                                   new Point2D.Double(0, centerUserY)).getY();

            double deviceRayHeight = 0;
            if (stackedRay.getThicknessType() == ThicknessType.Device) {
                deviceRayHeight = stackedRay.getThickness();
            }
            else {
                double top = centerUserY - stackedRay.getThickness() / 2;
                Point2D pTop = w2d.userToPixel(new Point2D.Double(0, top));

                double bottom = centerUserY + stackedRay.getThickness() / 2;
                Point2D pBottom = w2d
                        .userToPixel(new Point2D.Double(0, bottom));

                deviceRayHeight = Math.abs(pTop.getY() - pBottom.getY());
            }

            double xUserRayBase = 0;
            if (stackedRay.isAscent()) {
                xUserRayBase = stackedRay.getRayBase();
            }
            if (stackedRay.isDescent()) {
                xUserRayBase = stackedRay.getRayBase()
                        - stackedRay.getRayValue();
            }

            double xDeviceRayBase = w2d.userToPixel(
                                                    new Point2D.Double(xUserRayBase, 0)).getX();

            double xUserRayFleche = 0;
            if (stackedRay.isAscent()) {
                xUserRayFleche = stackedRay.getRayBase()
                        - stackedRay.getRayValue();
            }
            if (stackedRay.isDescent()) {
                xUserRayFleche = stackedRay.getRayBase();
            }

            double xDeviceRayFleche = w2d.userToPixel(
                                                      new Point2D.Double(xUserRayFleche, 0)).getX();

            double x = xDeviceRayBase;
            double y = centerDeviceY - deviceRayHeight / 2;
            double width = Math.abs(xDeviceRayFleche - xDeviceRayBase);
            double height = deviceRayHeight;

            Rectangle2D rayShape = new Rectangle2D.Double(x, y, width, height);
            stackedRay.setRayShape(rayShape);

            // stacks
            for (RayStack s : stackedRay.getStacks()) {

                Ray rayStack = new Ray();
                rayStack.setName(s.getStackName());
                rayStack.setRayNature(stackedRay.getRayNature());
                rayStack.setThickness(stackedRay.getThickness());
                rayStack.setThicknessType(stackedRay.getThicknessType());
                rayStack.setRay(stackedRay.getRay());
                rayStack.setRayBase(stackedRay.getStackBase(s));
                rayStack.setThemeColor(s.getThemeColor());
                rayStack.setRayFill(s.getRayFill());
                rayStack.setRayDraw(s.getRayDraw());
                rayStack.setRayEffect(s.getRayEffect());

                if (stackedRay.isAscent()) {
                    rayStack.setAscentValue(s.getNormalizedValue());
                }
                else if (stackedRay.isDescent()) {
                    rayStack.setDescentValue(s.getNormalizedValue());
                }

                double xUserStackRayBase = 0;
                if (stackedRay.isAscent()) {
                    xUserStackRayBase = stackedRay.getStackBase(s);
                }
                if (stackedRay.isDescent()) {
                    xUserStackRayBase = stackedRay.getStackBase(s)
                            - s.getNormalizedValue();
                }

                double xDeviceStackRayBase = w2d.userToPixel(
                                                             new Point2D.Double(xUserStackRayBase, 0)).getX();

                double xUserStackRayFleche = 0;
                if (stackedRay.isAscent()) {
                    xUserStackRayFleche = stackedRay.getStackBase(s)
                            - s.getNormalizedValue();
                }
                if (stackedRay.isDescent()) {
                    xUserStackRayFleche = stackedRay.getStackBase(s);
                }

                double xDeviceStackRayFleche = w2d.userToPixel(
                                                               new Point2D.Double(xUserStackRayFleche, 0)).getX();

                double stackx = xDeviceStackRayBase;
                double stacky = centerDeviceY - deviceRayHeight / 2;
                double stackwidth = Math.abs(xDeviceStackRayFleche
                        - xDeviceStackRayBase);
                double stackheight = deviceRayHeight;

                Rectangle2D stackRayShape = new Rectangle2D.Double(stackx,
                                                                   stacky, stackwidth, stackheight);
                rayStack.setRayShape(stackRayShape);

                s.setRay(rayStack);

            }

        }
    }

    /**
     * paint the specified ray
     * 
     * @param v2d
     *            the host view
     * @param g2d
     *            graphics context
     * @param ray
     *            the ray to paint
     */
    private void paintRay(View2D v2d, Graphics2D g2d, Ray ray,
            WindowPart windowPart, PaintRequest paintRequest) {

        ray.setHost(this);

        if (paintRequest == PaintRequest.RayLayer) {
            if (ray.getRayFill() != null) {
                ray.getRayFill().paintRay(g2d, ray, windowPart);
            }

            if (ray.getRayEffect() != null) {
                ray.getRayEffect().paintRay(g2d, ray, windowPart);
            }

            if (ray.getRayDraw() != null) {
                ray.getRayDraw().paintRay(g2d, ray, windowPart);
            }
        }
        else {
            if (ray.getRayLabel() != null) {
                ray.getRayLabel().paintRay(g2d, ray, windowPart);
            }
        }

    }

    /**
     * paint the specified stacked ray
     * 
     * @param v2d
     *            the host view
     * @param g2d
     *            graphics context
     * @param stackedRay
     *            the stackedRay to paint
     */
    private void paintStackedRay(View2D v2d, Graphics2D g2d,
            StackedRay stackedRay, WindowPart windowPart,
            PaintRequest paintRequest) {

        for (RayStack s : stackedRay.getStacks()) {
            Ray stackRay = s.getRay();
            paintRay(v2d, g2d, stackRay, windowPart, paintRequest);
        }

        paintRay(v2d, g2d, stackedRay, windowPart, paintRequest);

    }

    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
        g2d.setComposite(AlphaComposite
                .getInstance(AlphaComposite.SRC_OVER, 1f));

        resolveRayGeometry();

        if (windowPart == WindowPart.Device) {

            for (Ray ray : rays) {
                if (ray instanceof RayGroup) {
                    RayGroup group = (RayGroup) ray;
                    List<Ray> rays = group.getRays();
                    for (Ray r : rays) {
                        if (r instanceof StackedRay
                                && !(ray instanceof RayGroup)) {
                            paintStackedRay(v2d, g2d, (StackedRay) r,
                                            windowPart, PaintRequest.RayLayer);
                        }
                        else if (r instanceof Ray && !(r instanceof RayGroup)) {
                            paintRay(v2d, g2d, r, windowPart,
                                     PaintRequest.RayLayer);
                        }
                    }
                }
                else if (ray instanceof StackedRay) {
                    paintStackedRay(v2d, g2d, (StackedRay) ray, windowPart,
                                    PaintRequest.RayLayer);
                }
                else {
                    paintRay(v2d, g2d, ray, windowPart, PaintRequest.RayLayer);
                }
            }

            for (Ray ray : rays) {
                if (ray instanceof RayGroup) {
                    RayGroup group = (RayGroup) ray;
                    List<Ray> rays = group.getRays();
                    for (Ray r : rays) {
                        if (r instanceof StackedRay
                                && !(ray instanceof RayGroup)) {
                            paintStackedRay(v2d, g2d, (StackedRay) r,
                                            windowPart, PaintRequest.LabelLayer);
                        }
                        else if (r instanceof Ray && !(r instanceof RayGroup)) {
                            paintRay(v2d, g2d, r, windowPart,
                                     PaintRequest.LabelLayer);
                        }
                    }
                }
                else if (ray instanceof StackedRay) {
                    paintStackedRay(v2d, g2d, (StackedRay) ray, windowPart,
                                    PaintRequest.LabelLayer);
                }
                else {
                    paintRay(v2d, g2d, ray, windowPart, PaintRequest.LabelLayer);
                }
            }
        }
        else {
            paintRayAxisLabel(g2d, windowPart);
        }
    }

    /**
     * paint rays axis symbols
     * 
     * @param g2d
     *            the graphics context to paint
     * @param windowPart
     *            to window part to paint
     */
    protected void paintRayAxisLabel(Graphics2D g2d, WindowPart windowPart) {

        for (Ray ray : rays) {
            ray.setHost(this);

            if (ray instanceof RayGroup) {
                RayGroup group = (RayGroup) ray;

                if (group.getRayAxisLabel() != null) {
                    group.getRayAxisLabel().paintRay(g2d, ray, windowPart);
                }

                List<Ray> rays = group.getRays();
                for (Ray r : rays) {
                    ray.setHost(this);
                    if (!(r instanceof RayGroup)) {
                        if (r.getRayAxisLabel() != null) {
                            r.getRayAxisLabel().paintRay(g2d, r, windowPart);
                        }
                    }
                }
            }
            else {
                if (ray.getRayAxisLabel() != null) {
                    ray.getRayAxisLabel().paintRay(g2d, ray, windowPart);
                }
            }
        }

    }

    /**
     * add ray listener
     * 
     * @param listener
     *            the ray listener to add
     */
    public void addRayListener(RayListener listener) {
        rayListenerList.add(RayListener.class, listener);
    }

    /**
     * remove ray listener
     * 
     * @param listener
     *            the ray listener to remove
     */
    public void removeRayListener(RayListener listener) {
        rayListenerList.remove(RayListener.class, listener);
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
        for (Ray ray : rays) {
            if (ray.getRayShape() != null
                    && ray.getRayShape().contains(me.getX(), me.getY())) {
                fireRayReleased(ray);
            }
        }
    }

 
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {
        for (Ray ray : rays) {
            if (ray.getRayShape() != null
                    && ray.getRayShape().contains(me.getX(), me.getY())) {
                fireRayPressed(ray);
            }
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnExitListener#onExit(java.awt.event.MouseEvent)
     */
    @Override
    public void onExit(MouseEvent me) {
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnEnterListener#onEnter(java.awt.event.MouseEvent)
     */
    @Override
    public void onEnter(MouseEvent me) {
    }


    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnClickListener#onClick(java.awt.event.MouseEvent)
     */
    @Override
    public void onClick(MouseEvent me) {
        for (Ray ray : rays) {
            if (ray.getRayShape().contains(me.getX(), me.getY())) {
                fireRayClicked(ray);
            }
        }
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {
        for (Ray ray : rays) {
            if (ray instanceof StackedRay) {
                rayEnterExitTracker(ray, me.getX(), me.getY());
                StackedRay stackedRay = (StackedRay) ray;
                List<RayStack> rayStacks = stackedRay.getStacks();
                for (RayStack rayStack : rayStacks) {
                    Ray subRay = rayStack.getRay();
                    rayEnterExitTracker(subRay, me.getX(), me.getY());
                }
            }
            else {
                rayEnterExitTracker(ray, me.getX(), me.getY());
            }

        }
    }

    /**
     * track ray enter or exit for the specified ray for device location x,y
     * 
     * @param ray
     *            the ray to track
     * @param x
     *            the x in device coordinate
     * @param y
     *            the y in device coordinate
     */
    private void rayEnterExitTracker(Ray ray, int x, int y) {

        if (ray.getRayShape() == null) {
            return;
        }

        if (ray.getRayShape().contains(x, y) && !ray.isLockEnter()) {
            ray.lockEnter();
            fireRayEntered(ray);
        }
        else if (!ray.getRayShape().contains(x, y) && ray.isLockEnter()) {
            ray.unlockEnter();
            fireRayExited(ray);
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
        for (Ray ray : rays) {
            if (ray instanceof StackedRay) {
                rayEnterExitTracker(ray, me.getX(), me.getY());
                StackedRay stackedRay = (StackedRay) ray;
                List<RayStack> rayStacks = stackedRay.getStacks();
                for (RayStack rayStack : rayStacks) {
                    Ray subRay = rayStack.getRay();
                    rayEnterExitTracker(subRay, me.getX(), me.getY());
                }
            }
            else {
                rayEnterExitTracker(ray, me.getX(), me.getY());
            }

        }

    }

    /**
     * fire ray entered
     * 
     * @param ray
     *            the ray entered to fire
     */
    public void fireRayEntered(Ray ray) {
        Object[] listeners = rayListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == RayListener.class) {
                    ((RayListener) listeners[i + 1]).rayEntered(new RayEvent(
                                                                             ray));
                }
            }
        }
    }

    /**
     * fire ray exited
     * 
     * @param ray
     *            the ray exited to fire
     */
    public void fireRayExited(Ray ray) {
        Object[] listeners = rayListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == RayListener.class) {
                    ((RayListener) listeners[i + 1])
                            .rayExited(new RayEvent(ray));
                }
            }
        }
    }

    /**
     * fire ray clicked
     * 
     * @param ray
     *            the ray clicked to fire
     */
    public void fireRayClicked(Ray ray) {
        Object[] listeners = rayListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == RayListener.class) {
                    ((RayListener) listeners[i + 1]).rayClicked(new RayEvent(
                                                                             ray));
                }
            }
        }
    }

    /**
     * fire ray pressed
     * 
     * @param ray
     *            the ray pressed to fire
     */
    public void fireRayPressed(Ray ray) {
        Object[] listeners = rayListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == RayListener.class) {
                    ((RayListener) listeners[i + 1]).rayPressed(new RayEvent(
                                                                             ray));
                }
            }
        }
    }

    /**
     * fire ray released
     * 
     * @param ray
     *            the ray released to fire
     */
    public void fireRayReleased(Ray ray) {
        Object[] listeners = rayListenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == RayListener.class) {
                    ((RayListener) listeners[i + 1]).rayReleased(new RayEvent(
                                                                              ray));
                }
            }
        }
    }

}
