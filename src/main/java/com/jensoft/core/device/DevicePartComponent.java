/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.device;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JComponent;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.message.Message;
import com.jensoft.core.view.View2D;
import com.jensoft.core.view.View2DEvent;
import com.jensoft.core.view.View2DListener;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>DevicePartComponent</code> is the view part center component.
 * 
 * @author Sebastien Janaud
 * @since 1.0
 */
/**
 * @author sebastien
 *
 */
/**
 * @author sebastien
 *
 */
public class DevicePartComponent extends JComponent implements Device2D,
        MouseListener, MouseMotionListener, MouseWheelListener, View2DListener,
        FocusListener {

    /** serial version UID */
    private static final long serialVersionUID = -4082926837715398005L;

    /** parent view component */
    private View2D view2D;

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
     * get the view2D
     * 
     * @return the view
     */
    public View2D getView2D() {
        return view2D;
    }

    /**
     * set the view2d
     * 
     * @param view2D
     */
    public void setView2D(View2D view2D) {
        this.view2D = view2D;
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

        if (view2D.getActiveWindow() != null) {
            List<AbstractPlugin> layouts = view2D.getActiveWindow()
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

    boolean shoot = false;
    boolean right = true;

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

        if (!shoot) {
            paintBackground(g2d);
            paintPlugins(g2d);

        }
        else {
            Rectangle2D deRect = new Rectangle2D.Double(0, 0, getWidth(),
                                                        getHeight());
            Area a = new Area(deRect);
            // RoundRectangle2D clip = new
            // RoundRectangle2D.Double(100,100,getWidth()-200,getHeight()-200,40,40);

            int startx = 100;
            int starty = 100;
            int width = 160;
            int height = 120;
            int round = 20;
            GeneralPath path = new GeneralPath();

            if (right) {
                path.moveTo(startx, starty);
                path.lineTo(startx + width - round, starty);
                path.quadTo(startx + width, starty, startx + width, starty
                        + round);
                path.lineTo(startx + width, starty + height - round);
                path.quadTo(startx + width, starty + height, startx + width
                        - round, starty + height);
                path.lineTo(startx, starty + height);
            }

            if (!right) {
                path.moveTo(startx + round, starty);
                path.lineTo(startx + width, starty);
                // pathLeft.quadTo(startx+width, starty,startx+width,
                // starty+round);
                path.lineTo(startx + width, starty + height);
                // pathLeft.quadTo(startx+width,
                // starty+height,startx+width-round, starty+height);
                path.lineTo(startx + round, starty + height);
                path.quadTo(startx, starty + height, startx, starty + height
                        - round);
                path.lineTo(startx, starty + round);
                path.quadTo(startx, starty, startx + round, starty);
            }
            path.closePath();

            Shape clip = path;
            Antialiasing aa = Antialiasing.On;
            aa.configureGraphics(g2d);
            a.subtract(new Area(clip));
            g2d.setClip(deRect);
            g2d.setColor(Color.WHITE);
            g2d.fill(a);

            g2d.setClip(clip);
            Point2D start = new Point2D.Double(startx, starty);
            Point2D end = new Point2D.Double(startx, starty + height);
            float[] dist = { 0.0f, 1.0f };
            Color[] colors = { new Color(0x202737), Color.BLACK };
            // Color[] colors = {Color.WHITE,Color.red};

            LinearGradientPaint p = new LinearGradientPaint(start, end, dist,
                                                            colors);

            g2d.setPaint(p);

            g2d.fill(path);

            g2d.setColor(Color.BLACK);
            // g2d.setStroke(new BasicStroke(4f));
            // g2d.draw(path);

            paintPlugins(g2d);

        }
        // ??
        // g2d.dispose();
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

    /** registered windows */
    private Vector<Window2D> windows = new Vector<Window2D>();

    /**
     * Allow to register the new window in device2D
     * 
     * @param w2d
     *            the window to register in device
     */
    public void registerWindow(Window2D w2d) {
        windows.add(w2d);
    }

    /**
     * Allow to register the new window in device2D
     * 
     * @param w2d
     *            the window to remove in device
     */
    public void unregisterWindow(Window2D w2d) {
        windows.remove(w2d);
    }

    /**
     * Paint plug in objects in device
     * 
     * @param g2d
     *            the graphics2D context
     */
    private void paintPlugins(Graphics2D g2d) {

        // paint passives windows first
        List<Window2D> w2ds = view2D.getRegisterWindow();
        for (Window2D w2d : w2ds) {

            if (!w2d.isVisible()) {
                continue;
            }

            if (!w2d.equals(view2D.getActiveWindow())) {
                List<AbstractPlugin> plugins = w2d.getPluginRegistry();
                Collections.sort(plugins,
                                 AbstractPlugin.getPriorityComparator());
                if (plugins != null) {
                    for (int j = 0; j < plugins.size(); j++) {
                        AbstractPlugin plugin = plugins.get(j);
                        try {
                            plugin.paint(getView2D(), g2d, WindowPart.Device);
                        }
                        catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        // paint the active window last
        if (view2D.getActiveWindow() != null
                && view2D.getActiveWindow().isVisible()) {

            List<AbstractPlugin> plugins = view2D.getActiveWindow()
                    .getPluginRegistry();
            Collections.sort(plugins, AbstractPlugin.getPriorityComparator());
            if (plugins != null) {

                for (int j = 0; j < plugins.size(); j++) {
                    AbstractPlugin plugin = plugins.get(j);
                    try {
                        plugin.paint(getView2D(), g2d, WindowPart.Device);

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        try {
            view2D.getWidgetPlugin().paint(view2D, g2d, WindowPart.Device);
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
        if (view2D.getActiveWindow() != null) {

            List<AbstractPlugin> plugins = view2D.getActiveWindow()
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
        if (view2D.getActiveWindow() != null) {

            List<AbstractPlugin> plugins = view2D.getActiveWindow()
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
        if (view2D.getActiveWindow() != null) {

            List<AbstractPlugin> plugins = view2D.getActiveWindow()
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
        if (view2D.getActiveWindow() != null) {

            List<AbstractPlugin> plugins = view2D.getActiveWindow()
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

        view2D.getWidgetPlugin().getOnPressListener().onPress(e);

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
        if (view2D.getActiveWindow() != null) {

            List<AbstractPlugin> plugins = view2D.getActiveWindow()
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

        view2D.getWidgetPlugin().getOnReleaseListener().onRelease(e);

    }

    /**
     * device mouse dragged
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (view2D.getActiveWindow() != null) {

            List<AbstractPlugin> plugins = view2D.getActiveWindow()
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

        // other window needs event?

        // List<Window2D> w2ds = view2D.getRegisterWindow();
        // for(Window2D w2d : w2ds){
        // if(view2D.getActiveWindow() != null &&
        // !w2d.equals(view2D.getActiveWindow())){
        // List<AbstractPlugin> layouts = w2d.getPluginRegistry();
        // if(layouts != null){
        // for (int j = 0; j < layouts.size(); j++) {
        // AbstractPlugin layout = layouts.get(j);
        // AbstractPlugin.OnDragListener oml = layout.getOnDragListener();
        // if(oml != null) oml.onDrag(e);
        // }
        // }
        // }
        //
        // }

        view2D.getWidgetPlugin().getOnDragListener().onDrag(e);
    }

    /**
     * device mouse moved
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (view2D.getActiveWindow() != null) {

            List<AbstractPlugin> layouts = view2D.getActiveWindow()
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
        // other window needs event?

        // List<Window2D> windows = view2D.getRegisterWindow();
        // for(Window2D window: windows){
        // List<AbstractPlugin> layouts = window.getPluginRegistry();
        // if(layouts != null){
        // for (int j = 0; j < layouts.size(); j++) {
        // AbstractPlugin layout = layouts.get(j);
        // AbstractPlugin.OnMoveListener oml = layout.getOnMoveListener();
        // if(oml != null) oml.onMove(e);
        // }
        // }
        // }

        view2D.getWidgetPlugin().getOnMoveListener().onMove(e);
    }

    /**
     * device mouse wheel
     * 
     * @param e
     *            the mouse event
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (view2D.getActiveWindow() != null) {

            List<AbstractPlugin> plugins = view2D.getActiveWindow()
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
     * @see com.jensoft.core.view.View2DListener#viewWindow2DSelected(com.jensoft.core.view.View2DEvent)
     */
    @Override
    public void viewWindow2DSelected(View2DEvent view2dEvent) {
        // System.out.println("Device2D - windowSelected");
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewMoved(com.jensoft.core.view.View2DEvent)
     */
    @Override
    public void viewMoved(View2DEvent view2dEvent) {
        // TODO Auto-generated method stub
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewResized(com.jensoft.core.view.View2DEvent)
     */
    @Override
    public void viewResized(View2DEvent view2dEvent) {
        // TODO Auto-generated method stub
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewHidden(com.jensoft.core.view.View2DEvent)
     */
    @Override
    public void viewHidden(View2DEvent view2dEvent) {
        // TODO Auto-generated method stub
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewShown(com.jensoft.core.view.View2DEvent)
     */
    @Override
    public void viewShown(View2DEvent view2dEvent) {
        // TODO Auto-generated method stub

    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewFocusGained(com.jensoft.core.view.View2DEvent)
     */
    @Override
    public void viewFocusGained(View2DEvent view2dEvent) {
        // TODO Auto-generated method stub

    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.view.View2DListener#viewFocusLost(com.jensoft.core.view.View2DEvent)
     */
    @Override
    public void viewFocusLost(View2DEvent view2dEvent) {
        // TODO Auto-generated method stub

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
