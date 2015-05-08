/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.device;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.plugin.message.Message;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewEvent;
import org.jensoft.core.view.ViewListener;
import org.jensoft.core.view.ViewPart;

/**
 * <code>DevicePartComponent</code> is the view part center component.
 * 
 * @author Sebastien Janaud
 * 
 * @since 1.0
 */
public class DevicePartComponent extends JComponent implements Device,
        MouseListener, MouseMotionListener, MouseWheelListener, ViewListener,
        FocusListener {

    /** serial version UID */
    private static final long serialVersionUID = -4082926837715398005L;

    /** parent view component */
    private View view;

    /** device menu manager */
    private DeviceMenuManager deviceMenuManager;

    /**
     * create device part component
     */
    public DevicePartComponent() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        setFocusable(true);
        deviceMenuManager = new DeviceMenuManager(this);
    }

    public void removeMessage(Message message) {
        remove(message);
    }

    /**
     * get the view
     * 
     * @return the view
     */
    public View getView() {
        return view;
    }

    /**
     * set the view
     * 
     * @param view
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * get the device menu manager
     * 
     * @return the device menu manager
     */
    @Override
    public DeviceMenuManager getDeviceMenuManager() {
        return deviceMenuManager;
    }

    /**
     * set the device menu manager
     * 
     * @param deviceMenuManager
     */
    public void setDeviceMenuManager(DeviceMenuManager deviceMenuManager) {
        this.deviceMenuManager = deviceMenuManager;
    }

    /**
     * check if the popup menu should be show
     * 
     * @param e
     *            the mouse evnt to check
     */
    private void checkPopup(MouseEvent e) {

        if (view.getActiveProjection() != null) {
            List<AbstractPlugin> layouts = view.getActiveProjection()
                    .getPluginRegistry();
            deviceMenuManager.setPlugins(layouts);
        }

        if (e.isPopupTrigger()) {
            deviceMenuManager.show(e.getX(), e.getY());
        }
    }

    /**
     * Disable paints component's border.
     * 
     * @param g
     *            the <code>Graphics</code> context in which to paint
     */
    @Override
    protected void paintBorder(Graphics g) {
        // no border for device
    }

    /**
     * get the device part height
     * 
     * @return the device Height
     */
    @Override
    public int getDeviceHeight() {
        return getHeight();
    }

    /**
     * get device part width
     * 
     * @return the device width
     */
    @Override
    public int getDeviceWidth() {
        return getWidth();
    }

    /**
     * @return the origin X
     */
    @Override
    public int getOriginX() {
        return 0;
    }

    /**
     * @return the origin Y
     */
    @Override
    public int getOriginY() {
        return 0;
    }

    /**
     * repaint the device
     */
    @Override
    public void repaintDevice() {
        repaint();
    }

    @Override
    public void repaintDevice(int x, int y, int width, int height) {
        repaint(x, y, width, height);
    }


    /**
     * paint the device component
     * 
     * @param g
     *            graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        if (!isVisible()) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g.create();
        paintBackground(g2d);
        paintPlugins(g2d);
    }

    /**
     * Paint device backdrop
     * 
     * @param g2d
     *            the graphics2D context
     */
    private void paintBackground(Graphics2D g2d) {

        if (!isOpaque()) {
            return;
        }

        if (getBackground() != null) {
            g2d.setColor(getBackground());
            g2d.fillRect(0, 0, getDeviceWidth(), getDeviceHeight());
        }

    }

    /** registered projections */
    private Vector<Projection> projections = new Vector<Projection>();

    /**
     * Register the new projections in device2D
     * 
     * @param proj
     *            the projection to register in device
     */
    public void registerProjection(Projection proj) {
        projections.add(proj);
    }

    /**
     * Unregister the  projection from device2D
     * 
     * @param proj
     *            the proj to remove in device
     */
    public void unregisterProjection(Projection proj) {
        projections.remove(proj);
    }

    /**
     * Paint plug in objects in device
     * 
     * @param g2d
     *            the graphics2D context
     */
    private void paintPlugins(Graphics2D g2d) {

        // paint passives projections first
        List<Projection> w2ds = view.getProjections();
        for (Projection w2d : w2ds) {

            if (!w2d.isVisible()) {
                continue;
            }

            if (!w2d.equals(view.getActiveProjection())) {
                List<AbstractPlugin> plugins = w2d.getPluginRegistry();
                Collections.sort(plugins,
                                 AbstractPlugin.getPriorityComparator());
                if (plugins != null) {
                    for (int j = 0; j < plugins.size(); j++) {
                        AbstractPlugin plugin = plugins.get(j);
                        try {
                            plugin.paint(getView(), g2d, ViewPart.Device);
                        }
                        catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        // paint the active projection last
        if (view.getActiveProjection() != null
                && view.getActiveProjection().isVisible()) {

            List<AbstractPlugin> plugins = view.getActiveProjection()
                    .getPluginRegistry();
            Collections.sort(plugins, AbstractPlugin.getPriorityComparator());
            if (plugins != null) {

                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    try {
                        plugin.paint(getView(), g2d, ViewPart.Device);

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        try {
            view.getWidgetPlugin().paint(view, g2d, ViewPart.Device);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * device mouse clicked
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        checkPopup(e);
        if (view.getActiveProjection() != null) {

            List<AbstractPlugin> plugins = view.getActiveProjection()
                    .getPluginRegistry();
            if (plugins != null) {
                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    AbstractPlugin.OnClickListener ocl = plugin
                            .getOnClickListener();
                    if (ocl != null) {
                        ocl.onClick(e);
                    }
                }
            }
        }
    }

    /**
     * device mouse entered
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (view.getActiveProjection() != null) {

            List<AbstractPlugin> plugins = view.getActiveProjection()
                    .getPluginRegistry();
            if (plugins != null) {
                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    AbstractPlugin.OnEnterListener oml = plugin
                            .getOnEnterListener();
                    if (oml != null) {
                        oml.onEnter(e);
                    }
                }
            }
        }
    }

    /**
     * device mouse exited
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (view.getActiveProjection() != null) {

            List<AbstractPlugin> plugins = view.getActiveProjection()
                    .getPluginRegistry();
            if (plugins != null) {
                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    AbstractPlugin.OnExitListener oml = plugin
                            .getOnExitListener();
                    if (oml != null) {
                        oml.onExit(e);
                    }
                }
            }
        }
    }

    /**
     * device mouse pressed
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        checkPopup(e);
        if (view.getActiveProjection() != null) {

            List<AbstractPlugin> plugins = view.getActiveProjection()
                    .getPluginRegistry();
            if (plugins != null) {
                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    AbstractPlugin.OnPressListener oml = plugin
                            .getOnPressListener();
                    if (oml != null) {
                        oml.onPress(e);
                    }
                }
            }
        }

        view.getWidgetPlugin().getOnPressListener().onPress(e);

    }

    /**
     * device mouse released
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        checkPopup(e);
        if (view.getActiveProjection() != null) {

            List<AbstractPlugin> plugins = view.getActiveProjection()
                    .getPluginRegistry();
            if (plugins != null) {
                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    AbstractPlugin.OnReleaseListener oml = plugin
                            .getOnReleaseListener();
                    if (oml != null) {
                        oml.onRelease(e);
                    }
                }
            }
        }

        view.getWidgetPlugin().getOnReleaseListener().onRelease(e);

    }

    /**
     * device mouse dragged
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (view.getActiveProjection() != null) {

            List<AbstractPlugin> plugins = view.getActiveProjection()
                    .getPluginRegistry();
            if (plugins != null) {
                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    AbstractPlugin.OnDragListener oml = plugin
                            .getOnDragListener();
                    if (oml != null) {
                        oml.onDrag(e);
                    }
                }
            }
        }

        // other projection needs traversal event?

        view.getWidgetPlugin().getOnDragListener().onDrag(e);
    }

    /**
     * device mouse moved
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (view.getActiveProjection() != null) {

            List<AbstractPlugin> layouts = view.getActiveProjection()
                    .getPluginRegistry();
            if (layouts != null) {
                for (int j = 0; j < layouts.size(); j++) {
                    AbstractPlugin plugin = layouts.get(j);
                    AbstractPlugin.OnMoveListener oml = plugin
                            .getOnMoveListener();
                    if (oml != null) {
                        oml.onMove(e);
                    }
                }
            }
        }
        // other projections needs event?

      
        view.getWidgetPlugin().getOnMoveListener().onMove(e);
    }

    /**
     * device mouse wheel
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (view.getActiveProjection() != null) {

            List<AbstractPlugin> plugins = view.getActiveProjection()
                    .getPluginRegistry();
            if (plugins != null) {
                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    AbstractPlugin.OnWheelListener oml = plugin
                            .getOnWheelListener();
                    if (oml != null) {
                        oml.onWheel(e);
                    }
                }
            }
        }
    }

   
  
    /* (non-Javadoc)
     * @see org.jensoft.core.view.ViewListener#viewProjectionSelected(org.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewProjectionSelected(ViewEvent event) {
    }

    
   
    /* (non-Javadoc)
     * @see org.jensoft.core.view.ViewListener#viewMoved(org.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewMoved(ViewEvent event) {
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.view.ViewListener#viewResized(org.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewResized(ViewEvent event) {
    }

    /* (non-Javadoc)
     * @see org.jensoft.core.view.ViewListener#viewHidden(org.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewHidden(ViewEvent event) {
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.view.ViewListener#viewShown(org.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewShown(ViewEvent event) {
    }

    
    /* (non-Javadoc)
     * @see org.jensoft.core.view.ViewListener#viewFocusGained(org.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewFocusGained(ViewEvent event) {
    }

    /* (non-Javadoc)
     * @see org.jensoft.core.view.ViewListener#viewFocusLost(org.jensoft.core.view.ViewEvent)
     */
    @Override
    public void viewFocusLost(ViewEvent event) {
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     */
    @Override
    public void focusGained(FocusEvent e) {
    }

    /*
     * (non-Javadoc)
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     */
    @Override
    public void focusLost(FocusEvent e) {
    }

}
