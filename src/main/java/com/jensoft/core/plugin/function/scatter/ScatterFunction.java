/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.scatter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.function.FunctionPlugin.ScatterFunctionPlugin;
import com.jensoft.core.plugin.function.core.Function;
import com.jensoft.core.plugin.function.scatter.morphe.ScatterMorphe;
import com.jensoft.core.plugin.function.scatter.painter.ScatterDraw;
import com.jensoft.core.plugin.function.scatter.painter.ScatterFill;
import com.jensoft.core.plugin.function.source.SourceFunction;

/**
 * <code>ScatterFunction</code>
 * 
 * @see ScatterPoint
 * @see ScatterFunctionPlugin
 * @author Sebastien Janaud
 */
public class ScatterFunction extends Function {

    /** scatter draw */
    private ScatterDraw scatterDraw;

    /** scatter fill */
    private ScatterFill scatterFill;

    /** scatter morphe */
    private ScatterMorphe scatterMorphe;

    /** scatter points */
    private List<ScatterPoint> scatters;

    /**
     * create scatter curve for the specified source
     * 
     * @param source
     */
    public ScatterFunction(SourceFunction source) {
        super("scatter", source);
        scatters = new ArrayList<ScatterPoint>();
    }

    /**
     * get scatter draw
     * 
     * @return scatter draw
     */
    public ScatterDraw getScatterDraw() {
        return scatterDraw;
    }

    /**
     * set scatter draw
     * 
     * @param scatterDraw
     *            the scatter draw to set
     */
    public void setScatterDraw(ScatterDraw scatterDraw) {
        this.scatterDraw = scatterDraw;
    }

    /**
     * get the scatter morphe
     * 
     * @return scatter morphe
     */
    public ScatterMorphe getScatterMorphe() {
        return scatterMorphe;
    }

    /**
     * set the scatter morphe
     * 
     * @param scatterMorphe
     *            teh scatter morphe to set
     */
    public void setScatterMorphe(ScatterMorphe scatterMorphe) {
        this.scatterMorphe = scatterMorphe;
    }

    private BufferedImage createPrimitive(ScatterFunction scatterCurve, Shape shape) {
        Rectangle rect = shape.getBounds();
        BufferedImage buffer = new BufferedImage((int) rect.getWidth(),
                                                 (int) rect.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = buffer.createGraphics();
        getHost().configureGraphics(g2d);
        ScatterFill fill = scatterCurve.getScatterFill();
        ScatterDraw draw = scatterCurve.getScatterDraw();
        ScatterPoint scatter = new ScatterPoint();
        scatter.setPrimitiveShape(shape);
        scatter.setThemeColor(scatterCurve.getThemeColor());
        if (fill != null) {
            fill.paintScatter(g2d, scatter);
        }
        if (draw != null) {
            draw.paintScatter(g2d, scatter);
        }

        return buffer;
    }

    public void solveScatter() {
        clearScatter();

        ScatterMorphe morphe = getScatterMorphe();

        Shape morpheShape = morphe.getMorphe();
        BufferedImage primitive = createPrimitive(this, morpheShape);
        for (Point2D sourcePoint : getSourceFunction().getCurrentFunction()) {
            ScatterPoint scatter = new ScatterPoint();
            addScatter(scatter);
            scatter.setParent(this);
            scatter.setUserPoint(sourcePoint);

            Point2D deviceScatterPoint = getHost().getWindow2D().userToPixel(sourcePoint);

            scatter.setDevicePoint(deviceScatterPoint);

            scatter.setThemeColor(getThemeColor());
            scatter.setPrimitive(primitive);
        }
    }

    /**
     * get scatters
     * 
     * @return the curve scatter
     */
    public List<ScatterPoint> getScatters() {
        return scatters;
    }

    /**
     * add scatter in this curve
     * 
     * @param scatter
     *            the scatter to add
     */
    private void addScatter(ScatterPoint scatter) {
        scatters.add(scatter);
    }

    /**
     * remove scatter
     * 
     * @param scatter
     *            the scatter to remove
     */
    public void removeScatter(ScatterPoint scatter) {
        scatters.remove(scatter);
    }

    /**
     * clear scatter
     */
    public void clearScatter() {
        scatters.clear();
    }

    /**
     * get scatter fill
     * 
     * @return scatter fill
     */
    public ScatterFill getScatterFill() {
        return scatterFill;
    }

    /**
     * set scatter fill
     * 
     * @param scatterFill
     *            the scatter fill to set
     */
    public void setScatterFill(ScatterFill scatterFill) {
        this.scatterFill = scatterFill;
    }

    /**
     * Scatter defines a point on scatter curve
     * 
     * @see ScatterFunction
     * @see ScatterFunctionPlugin
     * @author Sebastien Janaud
     */
    public static class ScatterPoint {

        /** parent scatter curve for this scatter */
        private ScatterFunction parent;

        /** user scatter point in user coordinate */
        private Point2D userPoint;

        /** device scatter point in device coordinate */
        private Point2D devicePoint;

        /** theme color */
        private Color themeColor;

        /** primitive buffer */
        private BufferedImage primitiveBuffer;

        /** primitive shape */
        private Shape primitiveShape;

        /**
         * create empty scatter
         */
        public ScatterPoint() {
        }

        /**
         * @return the scatterShape
         */
        public Shape getPrimitiveShape() {
            return primitiveShape;
        }

        /**
         * @param scatterShape
         *            the scatterShape to set
         */
        public void setPrimitiveShape(Shape scatterShape) {
            primitiveShape = scatterShape;
        }

        /**
         * get theme color
         * 
         * @return theme color
         */
        public Color getThemeColor() {
            return themeColor;
        }

        /**
         * set color
         * 
         * @param themeColor
         *            the theme color to set
         */
        public void setThemeColor(Color themeColor) {
            this.themeColor = themeColor;
        }

        /**
         * @return the primitive
         */
        public BufferedImage getPrimitive() {
            return primitiveBuffer;
        }

        /**
         * @param primitive
         *            the primitive to set
         */
        public void setPrimitive(BufferedImage primitive) {
            primitiveBuffer = primitive;
        }

        /**
         * @return the userPoint
         */
        public Point2D getUserPoint() {
            return userPoint;
        }

        /**
         * @param userPoint
         *            the userPoint to set
         */
        public void setUserPoint(Point2D userPoint) {
            this.userPoint = userPoint;
        }

        /**
         * @return the devicePoint
         */
        public Point2D getDevicePoint() {
            return devicePoint;
        }

        /**
         * @param devicePoint
         *            the devicePoint to set
         */
        public void setDevicePoint(Point2D devicePoint) {
            this.devicePoint = devicePoint;
        }

        /**
         * get parent curve
         * 
         * @return scatter parent curve
         */
        public ScatterFunction getParent() {
            return parent;
        }

        /**
         * set parent curve
         * 
         * @param parent
         *            the parent scatter curve to set
         */
        public void setParent(ScatterFunction parent) {
            this.parent = parent;
        }

    }

}
