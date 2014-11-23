/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import javax.swing.event.EventListenerList;

import com.jensoft.core.device.ContextEntry;
import com.jensoft.core.device.ContextRegistry;
import com.jensoft.core.graphics.AlphaInterpolation;
import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.Fractional;
import com.jensoft.core.graphics.Interpolation;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;
import com.jensoft.core.view.WidgetRegistry;
import com.jensoft.core.widget.Widget;

/**
 * <code>AbstractPlugin</code> AbstractPlugin is the abstract definition that defines projection plug in.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractPlugin implements Plugin {

    /** the view host */
    private View view;

    /** the projection */
    private Projection proj;

    /** lock selected */
    private boolean lockSelected = false;

    /** lock passive */
    private boolean lockPassive = false;

    /** the plug id */
    private String id;

    /** the plug in name */
    private String name;

    /** the theme color for this plug in */
    private Color themeColor;

    /** listeners */
    private EventListenerList listenerList = new EventListenerList();

    /** the plug in ID */
    private String pluginID;

    /** selectable flag indicate that the plugin is selectable */
    private boolean selectable = false;

    /** widget registry for this plugin */
    private WidgetRegistry widgetRegistry;

    /** context action registry */
    private ContextRegistry contextRegistry;

    /** plug in anti aliasing */
    private Antialiasing antialiasing = Antialiasing.On;

    /** plug in text anti aliasing */
    private TextAntialiasing textAntialising = TextAntialiasing.Off;

    /** plug in fractional metrics */
    private Fractional fractionalMetrics = Fractional.Off;

    /** plug in interpolation */
    private Interpolation interpolation = Interpolation.NearestNeighbor;

    /** plug in dithering */
    private Dithering dithering = Dithering.On;

    /** plug in alpha interpolation */
    private AlphaInterpolation alphaInterpolation = AlphaInterpolation.Default;

    /** priority order */
    private int priority = 0;

    /** priority comparator */
    private static PluginOrderComparator priorityComparator = new PluginOrderComparator();

    /** plug in paint behavior */
    private PaintBehavior paintBehavior = PaintBehavior.PaintAlways;

    /** message show on lock */
    private String lockMessage;

    /** show lock message flag */
    private boolean showLockMessage = true;


    /** property file name */
    private String propertyFileName;
    
    /**plugin properties*/
    private Properties properties;

    /**
     * paint behavior of this plug in
     * 
     * @author Sebastien Janaud
     */
    public enum PaintBehavior {
        NoPaint, PaintIfHostProjectionActive, PaintIfPluginIsLockSelected, PaintAlways;
    }

    /**
     * Default constructor
     */
    public AbstractPlugin() {
        PluginPlatform.instance().register(this);
    }

    /**
     * @return the paintBehavior
     */
    public PaintBehavior getPaintBehavior() {
        return paintBehavior;
    }

    /**
     * @param paintBehavior
     *            the paintBehavior to set
     */
    public void setPaintBehavior(PaintBehavior paintBehavior) {
        this.paintBehavior = paintBehavior;
    }

    /**
     * @return the propertyFileName
     */
    public String getPropertyFileName() {
        return propertyFileName;
    }

    /**
     * @param propertyFileName
     *            the propertyFileName to set
     */
    public void setPropertyFileName(String propertyFileName) {
        this.propertyFileName = propertyFileName;
    }
    
    

    
    /**
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

    
    /**
     * @param properties the properties to set
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * get widget registry
     * 
     * @return widget registry
     */
    public WidgetRegistry getWidgetRegistry() {
        if (widgetRegistry == null) {
            widgetRegistry = new WidgetRegistry();
            widgetRegistry.setHost(this);
        }
        return widgetRegistry;
    }

    /**
     * register widget, exclude incompatible widget
     * 
     * @param widget
     *            the widget to register
     */
    public void registerWidget(Widget widget) {
        widget.setHost(this);
        if (widget.isCompatiblePlugin()) {
            widget.onRegister();
            getWidgetRegistry().register(widget);
        }
        else {
            System.err.println("incompatible widget " + widget.getClass() + " with plugin" + getClass());
        }
    }

    /**
     * register widgets, exclude incompatible widget
     * 
     * @param widgets
     *            the widgets to register
     */
    public void registerWidget(Widget... widgets) {
        for (int i = 0; i < widgets.length; i++) {
            Widget widget = widgets[i];
            widget.setHost(this);
            widget.onRegister();
            if (widget.isCompatiblePlugin()) {
                getWidgetRegistry().register(widget);
            }
        }
    }

    /**
     * @return the lockMessage
     */
    public String getLockMessage() {
        return lockMessage;
    }

    /**
     * @param lockMessage
     *            the lockMessage to set
     */
    public void setLockMessage(String lockMessage) {
        this.lockMessage = lockMessage;
    }

    /**
     * @return the showLockMessage
     */
    public boolean isShowLockMessage() {
        return showLockMessage;
    }

    /**
     * @param showLockMessage
     *            the showLockMessage to set
     */
    public void setShowLockMessage(boolean showLockMessage) {
        this.showLockMessage = showLockMessage;
    }

    /**
     * return the widgets that have been register in this plug in
     * 
     * @return the widgets
     */
    public List<Widget> getWidgets() {
        if (widgetRegistry == null) {
            return Collections.EMPTY_LIST;
        }
        return widgetRegistry.getWidgets();
    }

    /**
     * get context registry
     * 
     * @return context registry
     */
    public ContextRegistry getContextRegistry() {
        if (contextRegistry == null) {
            contextRegistry = new ContextRegistry();
            contextRegistry.setHost(this);
        }
        return contextRegistry;
    }

    /**
     * register context, exclude incompatible context entry
     * 
     * @param contextEntry
     *            the context entry to register
     */

    public <T extends AbstractPlugin> void registerContext(ContextEntry<T> contextEntry) {
        contextEntry.setHost((T) this);
        if (contextEntry.isCompatiblePlugin()) {
            getContextRegistry().register(contextEntry);
        }
    }

    /**
     * register context entry array, exclude incompatible entry
     * 
     * @param contextEntries
     *            the context entries to register
     */
    public <T extends AbstractPlugin> void registerContext(ContextEntry<T>... contextEntries) {
        for (int i = 0; i < contextEntries.length; i++) {
            ContextEntry<T> contextEntry = contextEntries[i];
            contextEntry.setHost((T) this);
            if (contextEntry.isCompatiblePlugin()) {
                getContextRegistry().register(contextEntry);
            }
        }
    }

    /**
     * return the context entries that have been register in this plug in
     * 
     * @return the context entries
     */
    public List<ContextEntry<? extends AbstractPlugin>> getContextEntries() {
        if (contextRegistry == null) {
            return Collections.EMPTY_LIST;
        }
        return getContextRegistry().getContextEntries();
    }

    /**
     * @return the priorityComparator
     */
    public static PluginOrderComparator getPriorityComparator() {
        return priorityComparator;
    }

    /**
     * delegate method from projection and view , repaint the device part component
     */
    public void repaintDevice() {
        getProjection().getView().repaintDevice();
    }

    /**
     * return true if this plug in is selectable, false otherwise
     * 
     * @return the selectable state
     */
    public boolean isSelectable() {
        return selectable;
    }

    /**
     * set the selectable state for this plug in.
     * 
     * @param selectable
     */
    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * return the name of this plug in
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * set the name of this plug in
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get the theme color of this plug in. create random color if theme color
     * is not set
     * 
     * @return the theme color
     */
    public Color getThemeColor() {
        if (themeColor == null) {
            themeColor = ColorPalette.getRandomColor();
        }
        return themeColor;
    }

    /**
     * set the theme color for this plug in
     * 
     * @param themeColor
     */
    public void setThemeColor(Color themeColor) {
        this.themeColor = themeColor;
    }

    /**
     * get the plug in ID
     * 
     * @return the plug in ID
     */
    public String getPluginID() {
        return pluginID;
    }

    /**
     * set the plug in ID
     * 
     * @param pluginID
     */
    public void setPluginID(String pluginID) {
        this.pluginID = pluginID;
    }

    /**
     * return view host
     * 
     * @return  view
     */
    protected View getView() {
        return view;
    }

    /**
     * set  view host
     * 
     * @param view
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * return true if plug in is selected , false otherwise
     * 
     * @return the lock selected
     */
    public boolean isLockSelected() {
        return lockSelected;
    }

    /**
     * lock select state of this plug in fire the lock select repaint view
     */
    public void lockSelected() {
        if (!isLockSelected()) {
            lockSelected = true;
            firePluginLockSelected();
            if (getProjection() != null && getProjection().getView() != null) {
                getProjection().getView().repaint();
            }
        }
    }

    /**
     * unlock the selected state of this plug in fire the unlock select repaint
     * view
     */
    public void unlockSelected() {
        if (isLockSelected()) {
            lockSelected = false;
            firePluginUnlockSelected();
            if (getProjection() != null && getProjection().getView() != null) {
                getProjection().getView().repaint();
            }
        }

    }

    /**
     * add a plug in listener of this plug in
     * 
     * @param listener
     */
    public void addPluginListener(PluginListener listener) {
        listenerList.add(PluginListener.class, listener);
    }

    /**
     * remove the listener of this plug in
     * 
     * @param listener
     */
    public void removePluginListener(PluginListener listener) {
        listenerList.remove(PluginListener.class, listener);
    }

    /**
     * fire listener that this plug in is lock selected
     */
    private void firePluginLockSelected() {
        Object[] listeners = listenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PluginListener.class) {
                    ((PluginListener<?>) listeners[i + 1])
                            .pluginSelected(new PluginEvent(this));
                }
            }
        }
    }

    /**
     * fire listener that this plug in is unlock selected
     */
    private void firePluginUnlockSelected() {
        Object[] listeners = listenerList.getListenerList();
        synchronized (listeners) {
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == PluginListener.class) {
                    ((PluginListener) listeners[i + 1])
                            .pluginUnlockSelected(new PluginEvent(this));
                }
            }
        }
    }

    /**
     * lock passive the plug in
     */
    public void lockPassive() {
        lockPassive = true;
    }

    /**
     * unlock passive the plug in
     */
    public void unlockPassive() {
        lockPassive = false;
    }

    /**
     * return true if tool is passive , false otherwise
     * 
     * @return the lock select
     */
    public boolean isLockPassive() {
        return lockPassive;
    }

    /**
     * get the projection
     * 
     * @return projection
     */
    public Projection getProjection() {
        return proj;
    }

    /**
     * set projection
     * 
     * @param projection
     */
    public void setProjection(Projection proj) {
        this.proj = proj;
    }

    /**
     * call on projection register
     */
    public void onProjectionRegister() {
    }

    /**
     * OnPressListener interface take the responsibility of listen mouse press
     * to handle specific plug in behavior in response to a mouse press on a
     * plug in part
     */
    public interface OnPressListener {

        /**
         * on press call back
         * 
         * @param me
         *            the mouse press event on the plug in part
         */
        public void onPress(MouseEvent me);
    }

    /** onPressListener */
    private OnPressListener onPressListener;

    /**
     * get the on press listener
     * 
     * @return onPressListener
     */
    public OnPressListener getOnPressListener() {
        return onPressListener;
    }

    /**
     * set the on press listener
     * 
     * @param onPressListener
     *            the listener to set
     */
    public void setOnPressListener(OnPressListener onPressListener) {
        this.onPressListener = onPressListener;
    }

    /**
     * OnClickListener interface take the responsibility of listen mouse click
     * to handle specific plug in behavior in response to a mouse click on a
     * plug in part
     */
    public interface OnClickListener {

        /**
         * on click call back
         * 
         * @param me
         *            the mouse click event on the plug in part
         */
        public void onClick(MouseEvent me);
    }

    /** onClickListener */
    private OnClickListener onClickListener;

    /**
     * set the on click listener
     * 
     * @param onClickListener
     *            the listener to set
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * get the on click listener
     * 
     * @return onClickListener
     */
    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    /**
     * OnMoveListener interface take the responsibility of listen mouse move to
     * handle specific plug in behavior in response to a mouse move on a plug in
     * part
     */
    public interface OnMoveListener {

        /**
         * on move call back
         * 
         * @param me
         *            the mouse move event on the plug in part
         */
        public void onMove(MouseEvent me);
    }

    /** onMoveListener */
    private OnMoveListener onMoveListener;

    /**
     * set the on move listener
     * 
     * @param onMoveListener
     *            the listener to set
     */
    public void setOnMoveListener(OnMoveListener onMoveListener) {
        this.onMoveListener = onMoveListener;
    }

    /**
     * get the on move listener
     * 
     * @return onMoveListener
     */
    public OnMoveListener getOnMoveListener() {
        return onMoveListener;
    }

    /**
     * OnDragListener interface take the responsibility of listen mouse drag to
     * handle specific plug in behavior in response to a mouse drag on a plug in
     * part
     */
    public interface OnDragListener {

        /**
         * on drag call back
         * 
         * @param me
         *            the mouse drag event on the plug in part
         */
        public void onDrag(MouseEvent me);
    }

    /** onDragListener */
    private OnDragListener onDragListener;

    /**
     * get the on drag listener for this plug in
     * 
     * @return onDragListener
     */
    public OnDragListener getOnDragListener() {
        return onDragListener;
    }

    /**
     * set the on drag listener for this plug in
     * 
     * @param onDragListener
     *            the listener to set
     */
    public void setOnDragListener(OnDragListener onDragListener) {
        this.onDragListener = onDragListener;
    }

    /**
     * OnEnterListener interface take the responsibility of listen mouse enter
     * to handle specific plug in behavior in response to a mouse enter on a
     * plug in part
     */
    public interface OnEnterListener {

        /**
         * on enter call back
         * 
         * @param me
         *            the mouse enter event on the plug in part
         */
        public void onEnter(MouseEvent me);
    }

    /** onEnterListener */
    private OnEnterListener onEnterListener;

    /**
     * get the on enter listener for this plug in
     * 
     * @return onEnterListener
     */
    public OnEnterListener getOnEnterListener() {
        return onEnterListener;
    }

    /**
     * set the on enter listener for this plug in
     * 
     * @param onEnterListener
     *            the listener to set
     */
    public void setOnEnterListener(OnEnterListener onEnterListener) {
        this.onEnterListener = onEnterListener;
    }

    /**
     * OnExitListener interface take the responsibility of listen mouse exit to
     * handle specific plug in behavior in response to a mouse exit on a plug in
     * part
     */
    public interface OnExitListener {

        /**
         * on exit call back
         * 
         * @param me
         *            the mouse exit event on the plug in part
         */
        public void onExit(MouseEvent me);
    }

    /** onExitListener */
    private OnExitListener onExitListener;

    /**
     * get the on exit listener
     * 
     * @return onExitListener
     */
    public OnExitListener getOnExitListener() {
        return onExitListener;
    }

    /**
     * set the on exit listener
     * 
     * @param onExitListener
     *            the listener to set
     */
    public void setOnExitListener(OnExitListener onExitListener) {
        this.onExitListener = onExitListener;
    }

    /**
     * OnReleaseListener interface take the responsibility of listen mouse
     * release to handle specific plug in behavior in response to a mouse
     * release on a plug in part
     */
    public interface OnReleaseListener {

        /**
         * on release call back
         * 
         * @param me
         *            the mouse release event on the plug in part
         */
        public void onRelease(MouseEvent me);
    }

    /** onReleaseListener */
    private OnReleaseListener onReleaseListener;

    /**
     * get the on release listener for this plug in
     * 
     * @return onReleaseListener
     */
    public OnReleaseListener getOnReleaseListener() {
        return onReleaseListener;
    }

    /**
     * set the on release listener for this plug in
     * 
     * @param onReleaseListener
     *            the listener to set
     */
    public void setOnReleaseListener(OnReleaseListener onReleaseListener) {
        this.onReleaseListener = onReleaseListener;
    }

    /**
     * OnWheelListener interface take the responsibility of listen mouse wheel
     * to handle specific plug in behavior in response to a mouse wheel on a
     * plug in part
     */
    public interface OnWheelListener {

        /**
         * on wheel call back
         * 
         * @param mwe
         *            the mouse wheel event on the plug in part
         */
        public void onWheel(MouseWheelEvent mwe);
    }

    /** onWheelListener */
    private OnWheelListener onWheelListener;

    /**
     * get the on wheel listener
     * 
     * @return onWheelListener
     */
    public OnWheelListener getOnWheelListener() {
        return onWheelListener;
    }

    /**
     * set the on wheel listener for this plug in
     * 
     * @param onWheelListener
     *            the listener to set
     */
    public void setOnWheelListener(OnWheelListener onWheelListener) {
        this.onWheelListener = onWheelListener;
    }

    /**
     * get the anti aliasing for this plug in
     * 
     * @return anti aliasing
     */
    public Antialiasing getAntialiasing() {
        return antialiasing;
    }

    /**
     * set the anti aliasing for this plug in
     * 
     * @param antialiasing
     */
    public void setAntialiasing(Antialiasing antialiasing) {
        this.antialiasing = antialiasing;
    }

    /**
     * get the text anti aliasing for this plug in
     * 
     * @return text anti aliasing
     */
    public TextAntialiasing getTextAntialising() {
        return textAntialising;
    }

    /**
     * set the text anti aliasing for this plug in
     * 
     * @param textAntialising
     */
    public void setTextAntialising(TextAntialiasing textAntialising) {
        this.textAntialising = textAntialising;
    }

    /**
     * get the fractional metrics for this plug in
     * 
     * @return fractional metrics
     */
    public Fractional getFractionalMetrics() {
        return fractionalMetrics;
    }

    /**
     * set the fractional metrics for this plug in
     * 
     * @param fractionalMetrics
     */
    public void setFractionalMetrics(Fractional fractionalMetrics) {
        this.fractionalMetrics = fractionalMetrics;
    }

    /**
     * get the interpolation for this plug in
     * 
     * @return interpolation
     */
    public Interpolation getInterpolation() {
        return interpolation;
    }

    /**
     * set the interpolation for this plug in
     * 
     * @param interpolation
     */
    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }

    /**
     * get the dithering for this plug in
     * 
     * @return the dithering
     */
    public Dithering getDithering() {
        return dithering;
    }

    /**
     * set the dithering for this plug in
     * 
     * @param dithering
     */
    public void setDithering(Dithering dithering) {
        this.dithering = dithering;
    }

    /**
     * get the alpha interpolation for this plug in
     * 
     * @return alpha interpolation
     */
    public AlphaInterpolation getAlphaInterpolation() {
        return alphaInterpolation;
    }

    /**
     * set the alpha interpolation for this plug in
     * 
     * @param alphaInterpolation
     */
    public void setAlphaInterpolation(AlphaInterpolation alphaInterpolation) {
        this.alphaInterpolation = alphaInterpolation;
    }

    /**
     * configure graphics context with the specified renderings hints registered
     * on this plug in
     * 
     * @param g2d
     *            the graphics context
     */
    public void configureGraphics(Graphics2D g2d) {
        antialiasing.configureGraphics(g2d);
        textAntialising.configureGraphics(g2d);
        fractionalMetrics.configureGraphics(g2d);
        interpolation.configureGraphics(g2d);
        dithering.configureGraphics(g2d);
        alphaInterpolation.configureGraphics(g2d);
    }

    /**
     * paint the plug-in
     * 
     * @param v2d
     *            the view2D
     * @param g2d
     *            the graphics2D context
     * @param viewPart
     *            the view part to paint
     */
    protected abstract void paintPlugin(View v2d, Graphics2D g2d,
            ViewPart viewPart);

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.Plugin#paint(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    public final void paint(View view, Graphics2D g2d, ViewPart viewPart) {
        configureGraphics(g2d);
        paintPlugin(view, g2d, viewPart);
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority
     *            the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }



    /**
     * return a plugin comparator by priority
     */
    public static class PluginOrderComparator implements
            Comparator<AbstractPlugin> {

        @Override
        public int compare(AbstractPlugin p1, AbstractPlugin p2) {
            return p1.getPriority() - p2.getPriority();

        }
    }

    /**
     * get plug-in properties file from current class package
     * 
     * @param pluginPropertyFile
     * @return properties
     * @throws IOException
     */
    protected Properties getProperties(String pluginPropertyFile) throws IOException {

        Properties props = new Properties();

        // from plugin package
        InputStream inputStream = this.getClass().getResourceAsStream(pluginPropertyFile);

        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + pluginPropertyFile
                    + "' not found in the classpath");
        }

        props.load(inputStream);

        return props;
    }

    /**
     * get plug-in properties file from class path
     * 
     * @param pluginPropertyFile
     * @return properties
     * @throws IOException
     */
    protected Properties getPropertiesFromClasspath(String pluginPropertyFile) throws IOException {

        Properties props = new Properties();

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(pluginPropertyFile);

        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + pluginPropertyFile
                    + "' not found in the classpath");
        }

        props.load(inputStream);

        return props;
    }

}
