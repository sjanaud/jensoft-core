/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.translate;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import org.jensoft.core.palette.color.Alpha;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.PluginEvent;
import org.jensoft.core.view.View;
import org.jensoft.core.widget.Widget;
import org.jensoft.core.widget.WidgetFolder;

/**
 * <code>TranslateCompass</code>
 * 
 * @author sebastien janaud
 */
public class TranslateCompassWidget extends Widget<TranslatePlugin> {

    /** the widget id */
    public final static String translateCompassWidgetID = "@widget/translate/compass";

    /** the widget radius */
    private final static int compassSquareSize = 64;

    /** compass geometry */
    private CompassGeometry compassWidget;

    /** compass style */
    private CompassStyle compassStyle = CompassStyle.Merge;

    /** widget name */
    private String name;

    /**
     * compass style
     */
    public enum CompassStyle {
        Merge, Separate;
    }

    /** ring draw color */
    private Color ringDrawColor = RosePalette.LEMONPEEL;

    /** ring draw stroke */
    private Stroke ringDrawStroke = new BasicStroke(1.5f);

    /** ring fill color */
    private Color ringFillColor = new Alpha(RosePalette.COALBLACK, 180);

    /** ring needle draw color */
    private Color ringNeedleDrawColor;

    /** ring needle fill color */
    private Color ringNeedleFillColor;

    /** default stroke */
    private Stroke defaultStroke = new BasicStroke();

    /**
     * create default translate compass, in top right corner of device with 64
     * as square size.
     */
    public TranslateCompassWidget() {
        super(translateCompassWidgetID, compassSquareSize, compassSquareSize,100, 0);
              
        compassWidget = new CompassGeometry(0, 0, compassSquareSize / 2 - 10,
                                            compassSquareSize / 2 - 4);

    }

    /**
     * create translate compass widget with given {@link #ringDrawColor} and {@link #ringFillColor}
     * 
     * @param ringDrawColor
     *            the ring draw color
     * @param ringFillColor
     *            the ring fill color
     */
    public TranslateCompassWidget(Color ringDrawColor, Color ringFillColor) {
        super(translateCompassWidgetID, compassSquareSize, compassSquareSize,
              100, 0);
        compassWidget = new CompassGeometry(0, 0, compassSquareSize / 2 - 6,
                                            compassSquareSize / 2);
        this.ringDrawColor = ringDrawColor;
        this.ringFillColor = ringFillColor;
    }

    /**
     * create translate compass with specified parameters
     * 
     * @param compassSquareSize
     *            compass square size
     * @param xIndex
     *            x folder index
     * @param yIndex
     *            y folder index
     */
    public TranslateCompassWidget(double compassSquareSize, int xIndex, int yIndex) {
        super(translateCompassWidgetID, compassSquareSize, compassSquareSize,
              xIndex, yIndex);

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the compassStyle
     */
    public CompassStyle getCompassStyle() {
        return compassStyle;
    }

    /**
     * @param compassStyle
     *            the compassStyle to set
     */
    public void setCompassStyle(CompassStyle compassStyle) {
        this.compassStyle = compassStyle;
    }

    /**
     * @return the ringDrawColor
     */
    public Color getRingDrawColor() {
        return ringDrawColor;
    }

    /**
     * @param ringDrawColor
     *            the ringDrawColor to set
     */
    public void setRingDrawColor(Color ringDrawColor) {
        this.ringDrawColor = ringDrawColor;
    }

    /**
     * @return the ringDrawStroke
     */
    public Stroke getRingDrawStroke() {
        return ringDrawStroke;
    }

    /**
     * @param ringDrawStroke
     *            the ringDrawStroke to set
     */
    public void setRingDrawStroke(Stroke ringDrawStroke) {
        this.ringDrawStroke = ringDrawStroke;
    }

    /**
     * @return the ringFillColor
     */
    public Color getRingFillColor() {
        return ringFillColor;
    }

    /**
     * @param ringFillColor
     *            the ringFillColor to set
     */
    public void setRingFillColor(Color ringFillColor) {
        this.ringFillColor = ringFillColor;
    }

    /**
     * @return the ringNeedleDrawColor
     */
    public Color getRingNeedleDrawColor() {
        return ringNeedleDrawColor;
    }

    /**
     * @param ringNeedleDrawColor
     *            the ringNeedleDrawColor to set
     */
    public void setRingNeedleDrawColor(Color ringNeedleDrawColor) {
        this.ringNeedleDrawColor = ringNeedleDrawColor;
    }

    /**
     * @return the ringNeedleFillColor
     */
    public Color getRingNeedleFillColor() {
        return ringNeedleFillColor;
    }

    /**
     * @param ringNeedleFillColor
     *            the ringNeedleFillColor to set
     */
    public void setRingNeedleFillColor(Color ringNeedleFillColor) {
        this.ringNeedleFillColor = ringNeedleFillColor;
    }

    private int averageCounter = 0;
    private int maxAverage = 4;
    private double averageDx = 0;
    private double averageDy = 0;

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#onRegister()
     */
    @Override
    public void onRegister() {
        getHost().addTranslateListener(new TranslatePluginListener() {

           
            /**
             * @param pluginEvent
             */
            @Override
            public void pluginSelected(PluginEvent<TranslatePlugin> pluginEvent) {
            }

           
            /**
             * @param pluginEvent
             */
            @Override
            public void pluginUnlockSelected(PluginEvent<TranslatePlugin> pluginEvent) {
            }

           
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.translate.TranslatePluginListener#translated(com.jensoft.core.plugin.translate.TranslatePluginEvent)
             */
            @Override
            public void translated(TranslatePluginEvent pluginEvent) {

                if (averageCounter < maxAverage) {
                    averageCounter++;
                    averageDx = averageDx + getHost().getTranslateDx();
                    averageDy = averageDy + getHost().getTranslateDy();
                }
                else {
                    getCompassGeometry().getNeedle().getNeedleVector().startx = 0;
                    getCompassGeometry().getNeedle().getNeedleVector().endx = averageDx / averageCounter;
                    getCompassGeometry().getNeedle().getNeedleVector().starty = 0;
                    getCompassGeometry().getNeedle().getNeedleVector().endy = averageDy / averageCounter;
                    getHost().getProjection().getView().repaintDevice(getWidgetFolder().getBounds());
                    averageCounter = 0;
                    averageDx = 0;
                    averageDy = 0;
                }
            }

          
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.translate.TranslatePluginListener#translateStoped(com.jensoft.core.plugin.translate.TranslatePluginEvent)
             */
            @Override
            public void translateStoped(TranslatePluginEvent pluginEvent) {
            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.translate.TranslatePluginListener#translateStarted(com.jensoft.core.plugin.translate.TranslatePluginEvent)
             */
            @Override
            public void translateStarted(TranslatePluginEvent pluginEvent) {
            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.translate.TranslatePluginListener#translateL2RChanged(com.jensoft.core.plugin.translate.TranslatePluginEvent)
             */
            @Override
            public void translateL2RChanged(TranslatePluginEvent pluginEvent) {
            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.translate.TranslatePluginListener#translateB2TChanged(com.jensoft.core.plugin.translate.TranslatePluginEvent)
             */
            @Override
            public void translateB2TChanged(TranslatePluginEvent pluginEvent) {
            }
        });
    }

    /**
     * get compass geometry
     * 
     * @return compass geometry
     */
    private CompassGeometry getCompassGeometry() {
        return compassWidget;
    }

    /**
     * paint translate compass
     * 
     * @param g2d
     */
    private void paintTranslateCompass(Graphics2D g2d) {
        WidgetFolder currentFolder = getWidgetFolder();
        int tcx = (int) (currentFolder.getX() + currentFolder.getWidth() / 2);
        int tcy = (int) (currentFolder.getY() + currentFolder.getHeight() / 2);

        getCompassGeometry().setCenterX(tcx);
        getCompassGeometry().setCenterY(tcy);

        double theta = 0;
        double centerX = getCompassGeometry().getNeedle().getNeedleVector().startx;
        double centerY = getCompassGeometry().getNeedle().getNeedleVector().starty;
        double x = getCompassGeometry().getNeedle().getNeedleVector().endx;
        double y = getCompassGeometry().getNeedle().getNeedleVector().endy;

        if (x > centerX && y <= centerY) {
            theta = Math.atan(new Double(centerY - y).doubleValue()
                    / new Double(x - centerX).doubleValue());
        }
        else if (x > centerX && y > centerY) {
            theta = Math.atan(new Double(centerY - y).doubleValue()
                    / new Double(x - centerX).doubleValue())
                    + 2 * Math.PI;
        }
        else if (x < centerX) {
            theta = Math.atan(new Double(centerY - y).doubleValue()
                    / new Double(x - centerX).doubleValue())
                    + Math.PI;
        }
        else if (x == centerX && y < centerY) {
            theta = Math.PI / 2;
        }
        else if (x == centerX && y > centerY) {
            theta = 3 * Math.PI / 2;
        }

        compassWidget.setCenterX(tcx);
        compassWidget.setCenterY(tcy);

        compassWidget.getNeedle().setTheta(Math.toDegrees(theta));
        compassWidget.builCompass();

        Shape baseShape = compassWidget.getRing();
        Shape outerRing = compassWidget.getOuterCircle();
        Shape innerRing = compassWidget.getInnerCircle();
        Shape needlePath = compassWidget.getNeedle().getNeedlePath();

        g2d.setStroke(defaultStroke);

        if (compassStyle == CompassStyle.Merge) {
            Area a = new Area(baseShape);
            a.add(new Area(needlePath));
            // fill ring base shape
            if (ringFillColor != null) {
                g2d.setColor(ringFillColor);
                g2d.fill(a);
            }

            if (ringDrawColor != null) {
                g2d.setColor(ringDrawColor);
                g2d.setStroke(ringDrawStroke);
                g2d.draw(a);
            }
        }
        else {

            // fill ring base shape
            if (ringFillColor != null) {
                g2d.setColor(ringFillColor);
            }
            else {
                g2d.setColor(getHost().getThemeColor());
            }
            g2d.fill(baseShape);

            // draw outer and inner ring circle
            if (ringDrawColor != null) {
                g2d.setColor(ringDrawColor);
                g2d.setStroke(ringDrawStroke);
                g2d.draw(outerRing);
                g2d.draw(innerRing);
            }

            // paint needle
            if (ringNeedleFillColor != null) {
                g2d.setColor(ringNeedleFillColor);
            }
            else {
                g2d.setColor(getHost().getThemeColor());
            }
            g2d.fill(needlePath);

            if (ringNeedleDrawColor != null) {
                g2d.setColor(ringNeedleDrawColor);
                g2d.setStroke(ringDrawStroke);
                g2d.draw(needlePath);
            }
        }

    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#paintWidget(com.jensoft.core.view.View, java.awt.Graphics2D)
     */
    @Override
    protected void paintWidget(View view, Graphics2D g2d) {
        if (getHost().isLockTranslate()) {
            paintTranslateCompass(g2d);
        }
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#isCompatiblePlugin()
     */
    @Override
    public final boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof TranslatePlugin) {
            return true;
        }
        return false;
    }

    class CompassGeometry {

        private int centerX;
        private int centerY;
        private int innerRadius;
        private int outerRadius;
        private Paint paint = new Color(0, 0, 0, 40);
        private Color outlineColor = Color.WHITE;
        private Shape ring;
        private int delta1 = 2;// 10;
        private int delta2 = 10;// 4;
        private NeedleGeometry needle;
        private Ellipse2D innerCircle;
        private Ellipse2D outerCircle;

        public CompassGeometry(int centerX, int centerY, int innerRadius,
                int outerRadius) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.innerRadius = innerRadius;
            this.outerRadius = outerRadius;
            needle = new NeedleGeometry();
        }

        public NeedleGeometry getNeedle() {
            return needle;
        }

        public void builCompass() {

            innerCircle = new Ellipse2D.Double(centerX - innerRadius, centerY
                    - innerRadius, 2 * innerRadius, 2 * innerRadius);
            outerCircle = new Ellipse2D.Double(centerX - outerRadius, centerY
                    - outerRadius, 2 * outerRadius, 2 * outerRadius);

            Area area1 = new Area(innerCircle);
            Area area2 = new Area(outerCircle);

            area2.subtract(area1);

            ring = area2;

            buildNeedles();
        }

        private void buildNeedles() {

            double cornerExternalX = centerX - (innerRadius + delta1);
            double cornerExternalY = centerY - (innerRadius + delta1);

            Arc2D needleArc = new Arc2D.Double(cornerExternalX,
                                               cornerExternalY, 2 * (innerRadius + delta1),
                                               2 * (innerRadius + delta1), needle.getTheta()
                                                       - needle.getAlphaProjection(),
                                               2 * needle.getAlphaProjection(), Arc2D.OPEN);

            needle.setNeedleArc(needleArc);

            double X = (outerRadius + delta2)
                    * Math.cos(Math.toRadians(needle.getTheta()));
            double Y = (outerRadius + delta2)
                    * Math.sin(Math.toRadians(needle.getTheta()));

            GeneralPath needlePath = new GeneralPath();
            needlePath.moveTo(needleArc.getStartPoint().getX(), needleArc
                    .getStartPoint().getY());
            needlePath.lineTo(needleArc.getEndPoint().getX(), needleArc
                    .getEndPoint().getY());

            needlePath.lineTo(centerX + X, centerY - Y);
            needlePath.closePath();

            needle.setNeedlePath(needlePath);

        }

        public int getCenterX() {
            return centerX;
        }

        public void setCenterX(int centerX) {
            this.centerX = centerX;
        }

        public int getCenterY() {
            return centerY;
        }

        public void setCenterY(int centerY) {
            this.centerY = centerY;
        }

        public int getInnerRadius() {
            return innerRadius;
        }

        public void setInnerRadius(int innerRadius) {
            this.innerRadius = innerRadius;
        }

        public int getOuterRadius() {
            return outerRadius;
        }

        public void setOuterRadius(int outerRadius) {
            this.outerRadius = outerRadius;
        }

        public Shape getRing() {
            return ring;
        }

        public void setRing(Shape ring) {
            this.ring = ring;
        }

        /**
         * @return the innerCircle
         */
        public Ellipse2D getInnerCircle() {
            return innerCircle;
        }

        /**
         * @param innerCircle
         *            the innerCircle to set
         */
        public void setInnerCircle(Ellipse2D innerCircle) {
            this.innerCircle = innerCircle;
        }

        /**
         * @return the outerCircle
         */
        public Ellipse2D getOuterCircle() {
            return outerCircle;
        }

        /**
         * @param outerCircle
         *            the outerCircle to set
         */
        public void setOuterCircle(Ellipse2D outerCircle) {
            this.outerCircle = outerCircle;
        }

        public Paint getPaint() {
            return paint;
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }

        public Color getOutlineColor() {
            return outlineColor;
        }

        public void setOutlineColor(Color outlineColor) {
            this.outlineColor = outlineColor;
        }

    }

    class NeedleGeometry {

        private String name;
        private double theta = 0;
        private Paint paint = new Color(0, 0, 0, 150);
        private Color colorTheme = Color.WHITE;
        private int alphaProjection = 18;

        private Arc2D needleArc;
        private GeneralPath needlePath;

        private Point2D refPoint;

        private NeedleVector needleVector;

        class NeedleVector {

            double startx = 0;
            double endx = 0;
            double starty = 0;
            double endy = 0;
        }

        public NeedleGeometry() {
            needleVector = new NeedleVector();
        }

        public NeedleGeometry(int theta) {
            super();
            this.theta = theta;
        }

        public NeedleGeometry(int alphaProjection, int theta) {
            super();
            this.alphaProjection = alphaProjection;
            this.theta = theta;
        }

        public NeedleVector getNeedleVector() {
            return needleVector;
        }

        public void setNeedleVector(NeedleVector needleVector) {
            this.needleVector = needleVector;
        }

        public Point2D getRefPoint() {
            return refPoint;
        }

        public void setRefPoint(Point2D refPoint) {
            this.refPoint = refPoint;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean contains(Point2D p2d) {
            return needlePath.contains(p2d);
        }

        public double getTheta() {
            return theta;
        }

        public void setTheta(double theta) {
            this.theta = theta;
        }

        public Paint getPaint() {
            return paint;
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }

        public Color getColorTheme() {
            return colorTheme;
        }

        public void setColorTheme(Color colorTheme) {
            this.colorTheme = colorTheme;
        }

        public int getAlphaProjection() {
            return alphaProjection;
        }

        public void setAlphaProjection(int alphaProjection) {
            this.alphaProjection = alphaProjection;
        }

        public Arc2D getNeedleArc() {
            return needleArc;
        }

        public void setNeedleArc(Arc2D needleArc) {
            this.needleArc = needleArc;
        }

        public GeneralPath getNeedlePath() {
            return needlePath;
        }

        public void setNeedlePath(GeneralPath needlePath) {
            this.needlePath = needlePath;
        }

    }

}
