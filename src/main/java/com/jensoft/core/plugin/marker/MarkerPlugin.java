/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.marker;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.marker.marker.AbstractMarker;
import com.jensoft.core.plugin.marker.marker.CrossMarker;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;
import com.jensoft.core.view.WidgetPlugin.PushingBehavior;

/**
 * CroosMarker plugin manage marker
 */
public class MarkerPlugin extends AbstractPlugin implements
        AbstractPlugin.OnDragListener, AbstractPlugin.OnMoveListener,
        AbstractPlugin.OnPressListener, AbstractPlugin.OnReleaseListener,
        AbstractPlugin.OnEnterListener, AbstractPlugin.OnExitListener {

    private List<AbstractMarker> markers;
    private AbstractMarker floatingMarker;
    private boolean lockCross = false;
    private Stroke bs = new BasicStroke();

    public MarkerPlugin() {
        setSelectable(true);
        setName("MarkerPlugin");

        markers = new ArrayList<AbstractMarker>();
        floatingMarker = new CrossMarker();
        floatingMarker.setHost(this);

        setOnDragListener(this);
        setOnEnterListener(this);
        setOnExitListener(this);
        setOnMoveListener(this);
        setOnPressListener(this);
        setOnReleaseListener(this);

        setPriority(10000);

        setTextAntialising(TextAntialiasing.On);
    }

    /**
     * add the specified marker
     * 
     * @param marker
     *            the marker to add
     */
    public void addMarker(AbstractMarker marker) {
        marker.setHost(this);
        markers.add(marker);
    }

    /**
     * remove the specified marker
     * 
     * @param marker
     *            the marker to remove
     */
    public void removeMarker(AbstractMarker marker) {
        marker.setHost(null);
        markers.remove(marker);
    }

    /**
     * @return the floatingMarker
     */
    public AbstractMarker getFloatingMarker() {
        return floatingMarker;
    }

    /**
     * @param floatingMarker
     *            the floatingMarker to set
     */
    public void setFloatingMarker(AbstractMarker floatingMarker) {
        this.floatingMarker = floatingMarker;
    }

    /**
     * lock select the box tool
     */
    @Override
    public void lockSelected() {
        super.lockSelected();
        getProjection()
                .getView()
                .getWidgetPlugin()
                .pushMessage("LOCK " + getName().toUpperCase(), this,
                             PushingBehavior.Fast);

    }

    @Override
    public void unlockSelected() {
        super.unlockSelected();
        getProjection().getDevice2D().repaintDevice();

    }

    /**
     * get marker lock unlock action
     * 
     * @return lock unlock action
     */
    public MarkerLockUnlockAction getMarkerLockUnlockAction() {
        return new MarkerLockUnlockAction();
    }

    /**
     * lock action, use to lock the marker tool
     */
    public class MarkerLockUnlockAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isLockSelected()) {
                unlockSelected();
            }
            else {
                lockSelected();
            }
        }

    }

    /**
     * get PasteMarker Action
     */
    public CreateMarkerAction getCreateMarkerAction(AbstractMarker marker) {
        return new CreateMarkerAction(marker);
    }

    /**
     * create marker action
     */
    public class CreateMarkerAction implements ActionListener {

        /** the marker to create on click */
        private AbstractMarker marker;

        public CreateMarkerAction(AbstractMarker marker) {
            this.marker = marker;
        }

        /**
         * @return the marker
         */
        public AbstractMarker getMarker() {
            return marker;
        }

        /**
         * @param marker
         *            the marker to set
         */
        public void setMarker(AbstractMarker marker) {
            this.marker = marker;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    pasteNewMarker(marker);
                }
            });
        }

    }

    /**
     * get PasteMarker Action
     */
    public MarkerDeleteAllAction getMarkerDeleteAllAction() {
        return new MarkerDeleteAllAction();
    }

    public class MarkerDeleteAllAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    deleteAllMarkers();
                }
            });
        }

    }

    class DeleteMarkerAction implements ActionListener {

        private CrossMarker marker;

        public DeleteMarkerAction(CrossMarker marker) {
            this.marker = marker;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    deleteMarker(marker);
                }
            });
        }

    }

    /**
     * remove specified marker
     * 
     * @param marker
     *            the marker to remove
     */
    public void deleteMarker(CrossMarker marker) {
        markers.remove(marker);
    }

    int count = 1;

    private void pasteNewMarker(AbstractMarker marker) {
        marker.setHost(this);
        Projection proj = getProjection();
        Point2D p2dCross = new Point2D.Double(floatingMarker.getMarkerX(),
                                              floatingMarker.getMarkerY());
        Point2D p2dCrossUser = proj.pixelToUser(p2dCross);

        marker.setMarkerX(p2dCrossUser.getX());
        marker.setMarkerY(p2dCrossUser.getY());

        count++;

        markers.add(marker);

        getProjection().getDevice2D().repaintDevice();
    }

    private void deleteAllMarkers() {
        markers.clear();
        getProjection().getDevice2D().repaintDevice();
    }

    public void lockCross() {
        lockCross = true;
    }

    public void unlockCross() {
        lockCross = false;
    }

    public boolean isLockCross() {
        return lockCross;
    }

    @Override
    public void onExit(MouseEvent me) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEnter(MouseEvent me) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRelease(MouseEvent me) {
        for (int i = 0; i < markers.size(); i++) {
            AbstractMarker cm = markers.get(i);
            if (cm.isLockMove()) {
                cm.unlockMove();
            }
        }
    }

    @Override
    public void onPress(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }

        for (int i = 0; i < markers.size(); i++) {
            AbstractMarker cm = markers.get(i);
            if (cm.isLockIntercept()) {
                cm.lockMove();
                unlockCross(); // passive lockCross
                return;
            }
        }

        if (me.getModifiers() == InputEvent.BUTTON3_MASK) {
            return;
        }

        else if (me.getModifiers() == InputEvent.BUTTON1_MASK) {
            int currentX = me.getX();
            int currentY = me.getY();
            floatingMarker.setMarkerX(currentX);
            floatingMarker.setMarkerY(currentY);
            lockCross();
            getProjection().getDevice2D().repaintDevice();
        }

    }

    @Override
    public void onMove(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }
        interceptCrossMarker(me);

    }

    @Override
    public void onDrag(MouseEvent me) {
        if (!isLockSelected()) {
            return;
        }
        if (!isLockCross()) {
            return;
        }

        int currentX = me.getX();
        int currentY = me.getY();
        Projection w2d = getProjection();
        // MANAGE MOVE MARKERS
        for (int i = 0; i < markers.size(); i++) {

            AbstractMarker cm = markers.get(i);
            if (cm.isLockMove()) {
                Point2D curentDevice = new Point2D.Double(currentX, currentY);
                Point2D curentUser = w2d.pixelToUser(curentDevice);
                cm.setMarkerX(curentUser.getX());
                cm.setMarkerY(curentUser.getY());
                getProjection().getDevice2D().repaintDevice();

            }
        }

        // MANAGE FLOTABLE CROSS
        if (!isLockCross()) {
            return;
        }

        // floatable is in device coordinate.// take care
        floatingMarker.setMarkerX(currentX);
        floatingMarker.setMarkerY(currentY);
        getProjection().getDevice2D().repaintDevice();

    }

    private int SENSITIVITY = 5;

    private void interceptCrossMarker(MouseEvent e) {

        Projection window = getProjection();
        for (int i = 0; i < markers.size(); i++) {

            AbstractMarker cm = markers.get(i);

            Point2D targetP2dUser = new Point2D.Double(cm.getMarkerX(),
                                                       cm.getMarkerY());

            Point2D targetP2dDevice = window.userToPixel(targetP2dUser);

            if (e.getX() < targetP2dDevice.getX() + SENSITIVITY
                    && e.getX() > targetP2dDevice.getX() - SENSITIVITY
                    && e.getY() < targetP2dDevice.getY() + SENSITIVITY
                    && e.getY() > targetP2dDevice.getY() - SENSITIVITY) {
                cm.lockIntercept();

            }
            else {
                cm.unlockIntercept();

            }

        }
        getProjection().getDevice2D().repaintDevice();
    }

    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart != ViewPart.Device) {
            return;
        }

        floatingMarker.paintMarker(v2d, g2d);

        for (AbstractMarker marker : markers) {
            marker.paintMarker(v2d, g2d);
        }

    }

}
