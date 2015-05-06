/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.zoom.box;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;

import org.jensoft.core.palette.color.Alpha;
import org.jensoft.core.palette.color.RosePalette;
import org.jensoft.core.plugin.PluginEvent;
import org.jensoft.core.plugin.donut2d.Donut2D;
import org.jensoft.core.plugin.donut2d.Donut2DSlice;
import org.jensoft.core.plugin.zoom.box.ZoomBoxPlugin.BoundBox;
import org.jensoft.core.plugin.zoom.box.ZoomBoxPlugin.ZoomPlayerCallback;
import org.jensoft.core.view.View;
import org.jensoft.core.widget.Widget;
import org.jensoft.core.widget.WidgetFolder;

/**
 * <code>ZoomBoxDonutWidget</code>
 * 
 * @author sebastien janaud
 */
public class ZoomBoxDonutWidget extends Widget<ZoomBoxPlugin> {

    /** widget id */
    public final static String ID = "@widget/donutzoombox";

    /** donut radius */
    private final static int RADIUS = 32;

    /** donut 2D geometry */
    private Donut2D donut2D;

    /** play path */
    private GeneralPath playPath;

    /** center play control roll over flag */
    private boolean playRollover = false;

    /** donut fill color */
    private Color fillColor = new Alpha(Color.BLACK, 140);

    /** donut draw color */
    private Color drawColor = new Alpha(RosePalette.EMERALD, 255);

    /** shift effect enabled/disabled flag */
    private boolean shiftEffect = true;

    /** shift thread */
    private ShiftWidget shiftWidget;

    /**
     * create zoom box donut
     */
    public ZoomBoxDonutWidget() {
        super(ID, 2 * RADIUS, 2 * RADIUS, 100, 100);
        donut2D = new Donut2D();
    }

    /**
     * @return the fillColor
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * @param fillColor
     *            the fillColor to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * @return the drawColor
     */
    public Color getDrawColor() {
        return drawColor;
    }

    /**
     * @param drawColor
     *            the drawColor to set
     */
    public void setDrawColor(Color drawColor) {
        this.drawColor = drawColor;
    }

    /**
     * @return the shiftEffect
     */
    public boolean isShiftEffect() {
        return shiftEffect;
    }

    /**
     * @param shiftEffect
     *            the shiftEffect to set
     */
    public void setShiftEffect(boolean shiftEffect) {
        this.shiftEffect = shiftEffect;
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#onRegister()
     */
    @Override
    public void onRegister() {
        getHost().addZoomBoxListener(new ZoomBoxListener() {

            
            /**
             * @param pluginEvent
             */
            @Override
            public void pluginSelected(PluginEvent<ZoomBoxPlugin> pluginEvent) {
            }

           
            /**
             * @param pluginEvent
             */
            @Override
            public void pluginUnlockSelected(PluginEvent<ZoomBoxPlugin> pluginEvent) {
            }

          
           
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomIn(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
             */
            @Override
            public void zoomIn(ZoomBoxEvent pluginEvent) {
                if (shiftWidget != null && shiftWidget.isAlive())
                    shiftWidget.interrupt();

                ZoomBoxPlugin zoomPlugin = getHost();

                List<BoundBox> boxes = zoomPlugin.getZoomHistory();
                for (BoundBox boundBox : boxes) {
                    if (getDonutBoundBoxSection(boundBox) == null) {

                        ZoomBoxPlugin zoomBox = getHost();
                        final DonutBoundBoxSection ds = new DonutBoundBoxSection(boundBox.toString(),
                                                                                 zoomBox.getThemeColor());
                        ds.setBoundBox(boundBox);
                        ds.setValue(1);
                        // ds.setAlpha(0f);
                        ds.setDivergence(50);
                        donut2D.addSlice(ds);

                        Thread t = new Thread() {

                            @Override
                            public void run() {
                                try {
                                    for (int i = 50; i >= 0; i = i - 3) {
                                        ds.setDivergence(i);
                                        getHost().repaintDevice();
                                        Thread.sleep(10);
                                    }
                                    ds.setDivergence(0);

                                }
                                catch (InterruptedException e) {
                                }
                            }
                        };
                        t.start();

                    }
                }
            }

           
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomOut(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
             */
            @Override
            public void zoomOut(ZoomBoxEvent pluginEvent) {
                if (shiftWidget != null && shiftWidget.isAlive()) {
                    shiftWidget.interrupt();
                }
            }

         
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomClearHistory(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
             */
            @Override
            public void zoomClearHistory(ZoomBoxEvent pluginEvent) {
                donut2D.clearSlices();
            }

           
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomStart(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
             */
            @Override
            public void zoomStart(ZoomBoxEvent pluginEvent) {
                if (shiftWidget != null && shiftWidget.isAlive()) {
                    shiftWidget.interrupt();
                }
                shiftWidget = new ShiftWidget();
                shiftWidget.start();
            }

            
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomHistory(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
             */
            @Override
            public void zoomHistory(ZoomBoxEvent pluginEvent) {
                if (shiftWidget != null && shiftWidget.isAlive()) {
                    shiftWidget.interrupt();
                }
            }

           
            /* (non-Javadoc)
             * @see com.jensoft.core.plugin.zoom.box.ZoomBoxListener#zoomBounded(com.jensoft.core.plugin.zoom.box.ZoomBoxEvent)
             */
            @Override
            public void zoomBounded(ZoomBoxEvent pluginEvent) {
            }
        });

    }

    class ShiftWidget extends Thread {

        @Override
        public void run() {
            while (!interrupted() && shiftEffect) {
                try {
                    donut2D.setStartAngleDegree(donut2D.getStartAngleDegree() + 12);
                    Thread.sleep(50);
                    repaintWidget();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#isCompatiblePlugin()
     */
    @Override
    public final boolean isCompatiblePlugin() {
        if (getHost() != null && getHost() instanceof ZoomBoxPlugin) {
            return true;
        }
        return false;
    }

  
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#interceptMove(int, int)
     */
    @Override
    public void interceptMove(int x, int y) {

        if (getWidgetFolder() == null) {
            return;
        }

        if (!getWidgetFolder().getBounds2D().contains(x, y)) {
            return;
        }

        if (getHost().isSequencePlaying()) {
            return;
        }

        if (getWidgetFolder().getBounds2D().contains(new Point2D.Double(x, y))) {
            getHost().lockPassive();
        }
        else {
            getHost().unlockPassive();
        }

        List<Donut2DSlice> sections = donut2D.getSlices();

        for (Donut2DSlice donut2dSection : sections) {
            if (donut2dSection.contains(new Point2D.Double(x, y))) {
                donut2dSection.lockRollover();
                repaintWidget();
            }
            else {
                if (donut2dSection.isLockRollover()) {
                    donut2dSection.unlockRollover();
                    repaintWidget();
                }
            }
        }

        if (playPath != null && playPath.contains(new Point2D.Double(x, y))) {
            playRollover = true;            
            repaintWidget();
        }
        else {
            if (playRollover) {
                playRollover = false;
                repaintWidget();
            }
        }

    }

    @Override
    public void interceptPress(int x, int y) {

        if (getHost().isSequencePlaying()) {
            return;
        }

        if (getWidgetFolder().getBounds2D().contains(new Point2D.Double(x, y))) {
            getHost().lockPassive();
        }
        else {
            getHost().unlockPassive();
        }

        List<Donut2DSlice> sections = donut2D.getSlices();

        for (Donut2DSlice donut2dSection : sections) {
            if (donut2dSection.contains(new Point2D.Double(x, y))) {
                DonutBoundBoxSection dbbs = (DonutBoundBoxSection) donut2dSection;
                BoundBox boundBox = dbbs.getBoundBox();
                int index = ((ZoomBoxPlugin) getHost()).getHistoryIndex(boundBox);
                getHost().processZoomHistory(index);
                getHost().fireZoomHistory();
            }

        }

        if (playPath != null && playPath.contains(new Point2D.Double(x, y))) {
            getHost().playCurrentSequence(new DonutPlayerCallback());
        }

    }

    class DonutPlayerCallback implements ZoomPlayerCallback {

      
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.zoom.box.ZoomBoxPlugin.ZoomPlayerCallback#play(com.jensoft.core.plugin.zoom.box.ZoomBoxPlugin.BoundBox)
         */
        @Override
        public void play(BoundBox bounbdBox) {
            unlockAllSection();
            DonutBoundBoxSection dbbs = getDonutBoundBoxSection(bounbdBox);
            dbbs.lockRollover();
            getHost().getProjection().getView().getDevice2D().repaint();

        }

    }

    private void unlockAllSection() {
        List<Donut2DSlice> dss = donut2D.getSlices();
        for (Donut2DSlice donut2dSection : dss) {
            DonutBoundBoxSection dbbs = (DonutBoundBoxSection) donut2dSection;
            dbbs.unlockRollover();
        }
    }

    class DonutBoundBoxSection extends Donut2DSlice {

        private BoundBox boundBox;

        public DonutBoundBoxSection(String name, Color themeColor) {
            super(name, themeColor);
        }

        public BoundBox getBoundBox() {
            return boundBox;
        }

        public void setBoundBox(BoundBox boundBox) {
            this.boundBox = boundBox;
        }

    }

    public DonutBoundBoxSection getDonutBoundBoxSection(BoundBox boundBox) {
        List<Donut2DSlice> dss = donut2D.getSlices();

        for (Donut2DSlice donut2dSection : dss) {
            DonutBoundBoxSection dbbs = (DonutBoundBoxSection) donut2dSection;
            if (dbbs.getBoundBox().equals(boundBox)) {
                return dbbs;
            }
        }
        return null;
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.widget.Widget#paintWidget(com.jensoft.core.view.View, java.awt.Graphics2D)
     */
    @Override
    protected void paintWidget(View v2d, Graphics2D g2d) {

        if (!getHost().isLockSelected()) {
            return;
        }

        WidgetFolder currentFolder = getWidgetFolder();

        double centerX = currentFolder.getX() + RADIUS;
        double centerY = currentFolder.getY() + RADIUS;

        playPath = new GeneralPath();
        int deltaHL = 4;
        int deltaHR = 8;
        int deltaV = 10;
        playPath.moveTo(centerX - deltaHL, centerY);
        playPath.lineTo(centerX - deltaHL, centerY - deltaV);
        playPath.lineTo(centerX + deltaHR, centerY);
        playPath.lineTo(centerX - deltaHL, centerY + deltaV);
        playPath.closePath();

        g2d.setColor(fillColor);
        if (playRollover) {
            g2d.setColor(drawColor);
        }
        g2d.fill(playPath);

        g2d.setColor(drawColor);
        g2d.setStroke(new BasicStroke(2f));
        g2d.draw(playPath);

        donut2D.setCenterX((int) currentFolder.getX() + RADIUS);
        donut2D.setCenterY((int) currentFolder.getY() + RADIUS);
        donut2D.setOuterRadius(RADIUS);
        donut2D.setInnerRadius(RADIUS - 10);

        donut2D.solveGeometry();

        List<Donut2DSlice> dss = donut2D.getSlices();
        for (Donut2DSlice donut2dSection : dss) {
            g2d.setColor(fillColor);
            if (donut2dSection.isLockRollover()) {
                g2d.setColor(drawColor);
            }
            g2d.fill(donut2dSection.getSlicePath());
            g2d.setColor(drawColor);
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.draw(donut2dSection.getSlicePath());
        }

    }

}
