/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.Fractional;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.color.ColorPalette;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.PluginListener;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.widget.Widget;
import com.jensoft.core.widget.WidgetFolder;

/**
 * <code>WidgetPlugin</code>
 * <p>
 * internal view plugin for managing widget this plugin has no host window
 * </p>
 * 
 * @since 1.0
 * 
 * @author Sebastien Janaud
 */
public class WidgetPlugin extends AbstractPlugin implements
        AbstractPlugin.OnPressListener, AbstractPlugin.OnReleaseListener,
        AbstractPlugin.OnMoveListener, AbstractPlugin.OnDragListener,
        AbstractPlugin.OnWheelListener {

    /** about folder message flag enabled, default is true */
    private boolean lockAboutFolderMessage = true;

    /** font for display info */
    private Font font = new Font("Tahoma", Font.PLAIN, 10);

    /**
     * create widget plugin
     */
    public WidgetPlugin() {
        setName("WidgetPlugin");
        setOnPressListener(this);
        setOnMoveListener(this);
        setOnReleaseListener(this);
        setOnDragListener(this);
        setOnWheelListener(this);

        setTextAntialising(TextAntialiasing.On);
        setAntialiasing(Antialiasing.On);
        setDithering(Dithering.On);
        setFractionalMetrics(Fractional.On);
        setPriority(2000);

    }

    @Override
    public View getView() {
        return super.getView();
    }

    public boolean isLockAboutFolderMessage() {
        return lockAboutFolderMessage;
    }

    public void lockPostFolderMessage() {
        lockAboutFolderMessage = true;
    }

    public void unlockPostFolderMessage() {
        lockAboutFolderMessage = false;
    }

    class PushingMessage extends Thread {

        /** message */
        private String message;
        /** start delay */
        private int startDelay = 0;
        /** message behavior */
        private PushingBehavior behavior;
        /** message color */
        private Color color;
        /** message font */
        private Font font;
        /** message alpha */
        private float alpha = 1f;
        /** global pushing step */
        private int countBaseEffect = 300;
        /** current pushing step */
        private int countCurentEffect = 0;

        private boolean lockAlpha = true;

        private boolean effect = true;

        public PushingMessage(String message, PushingBehavior behavior,
                Color color, Font font) {
            this.message = message;
            this.behavior = behavior;
            this.color = color;
            this.font = font;

        }

        public PushingMessage(String message, int startDelay,
                PushingBehavior behavior, Color color, Font font) {
            this.message = message;
            this.startDelay = startDelay;
            this.behavior = behavior;
            this.color = color;
            this.font = font;
        }

        public PushingMessage(String message, int startDelay,
                PushingBehavior behavior, Color color, Font font, boolean lockAlpha) {
            this.message = message;
            this.startDelay = startDelay;
            this.behavior = behavior;
            this.color = color;
            this.font = font;
            this.lockAlpha = lockAlpha;
        }

        @Override
        public void run() {
            try {
                getView().getViewPartComponent(ViewPart.North).repaint();
                Thread.sleep(startDelay);

                getView().getViewPartComponent(ViewPart.North).repaint();
                while (effect) {

                    if (alpha < 0) {
                        effect = false;
                    }
                    else {

                        if (lockAlpha) {
                            alpha = alpha - 0.005f;
                        }
                        else {
                            alpha = 1f;
                        }
                        countCurentEffect = countCurentEffect + 1;
                        if (alpha >= 0 && alpha <= 1) {

                            getView().getViewPartComponent(ViewPart.North)
                                    .repaint();
                            Thread.sleep(behavior.getBehavior());
                        }
                    }

                }

                getView().getViewPartComponent(ViewPart.North).repaint();

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                message = null;
                getView().getViewPartComponent(ViewPart.North).repaint();

            }
        }
    }

    public enum PushingBehavior {
        // VerySlow(100),Slow(80), Default(60), Fast(40),VeryFast(20);
        VerySlow(40), Slow(30), Default(20), Fast(10), VeryFast(5);

        private int behavior;

        PushingBehavior(int behavior) {
            this.behavior = behavior;
        }

        public int getBehavior() {
            return behavior;
        }
    }

    private boolean destroyMessage = false;

    /**
     * @return the destroyMessage
     */
    public boolean isDestroyMessage() {
        return destroyMessage;
    }

    /**
     * @param destroyMessage
     *            the destroyMessage to set
     */
    public void setDestroyMessage(boolean destroyMessage) {
        this.destroyMessage = destroyMessage;
    }

    private List<PushingMessage> pushingMessages = new ArrayList<WidgetPlugin.PushingMessage>();

    /**
     * destroy {@link PushingBehavior} message
     */
    public void destroyMessage() {
        if (!destroyMessage) {
            return;
        }
        synchronized (pushingMessages) {
            for (PushingMessage pushingMessage : pushingMessages) {
                if (pushingMessage.isAlive()) {
                    pushingMessage.interrupt();
                }
            }
        }

    }

    public void pushMessage(String message, AbstractPlugin plugin,
            PushingBehavior pushingBehavior) {
        PushingMessage pm = null;
        Font f = new Font("Dialog", Font.PLAIN, 12);
        if (plugin != null) {
            pm = new PushingMessage(message, pushingBehavior,
                                    plugin.getThemeColor(), f);
        }
        else {
            if (getView().getActiveProjection() != null) {

                pm = new PushingMessage(message, pushingBehavior, getView()
                        .getActiveProjection().getThemeColor(),f);
            }
        }

        if (pm != null) {
            destroyMessage();
            synchronized (pushingMessages) {
                pushingMessages.add(pm);
            }
            pm.start();
        }
    }

    public void pushMessage(String message, AbstractPlugin plugin,
            PushingBehavior pushingBehavior, Color color) {
        PushingMessage pm = null;
        Font f = new Font("Dialog", Font.PLAIN, 12);
        pm = new PushingMessage(message, pushingBehavior,
                                color, f);

        if (pm != null) {
            destroyMessage();
            synchronized (pushingMessages) {
                pushingMessages.add(pm);
            }
            pm.start();
        }
    }

    public void pushMessage(String message, AbstractPlugin plugin,
            PushingBehavior pushingBehavior, Font f) {
        PushingMessage pm = null;
        if (plugin != null) {
            pm = new PushingMessage(message, pushingBehavior,
                                    plugin.getThemeColor(), f);
        }
        else {
            if (getView().getActiveProjection() != null) {

                pm = new PushingMessage(message, pushingBehavior, getView()
                        .getActiveProjection().getThemeColor(), f);
            }
        }
        if (pm != null) {
            destroyMessage();
            pushingMessages.add(pm);
            pm.start();
        }

    }

    public void pushMessage(String message, int startDelay,
            AbstractPlugin plugin, PushingBehavior pushingBehavior, Font f) {
        PushingMessage pm = null;
        if (plugin != null) {
            pm = new PushingMessage(message, startDelay, pushingBehavior,
                                    plugin.getThemeColor(), f);
        }
        else {
            if (getView().getActiveProjection() != null) {

                pm = new PushingMessage(message, startDelay, pushingBehavior,
                                        getView().getActiveProjection().getThemeColor(), f);
            }
        }
        if (pm != null) {
            destroyMessage();
            pushingMessages.add(pm);
            pm.start();
        }

    }

    public void pushMessage(String message, int startDelay,
            AbstractPlugin plugin, PushingBehavior pushingBehavior, Font f, Color c) {
        PushingMessage pm = null;

        pm = new PushingMessage(message, startDelay, pushingBehavior,
                                c, f);

        if (pm != null) {
            destroyMessage();
            pushingMessages.add(pm);
            pm.start();
        }

    }

    public void pushMessage(String message, int startDelay,
            AbstractPlugin plugin, PushingBehavior pushingBehavior, Font f, Color c, boolean alphaTransition) {
        PushingMessage pm = null;

        pm = new PushingMessage(message, startDelay, pushingBehavior,
                                c, f, alphaTransition);

        if (pm != null) {
            destroyMessage();
            pushingMessages.add(pm);
            pm.start();
        }

    }

    private String northMessage;

    public void setNorthMessage(String msg) {

        northMessage = msg;
        // destroyMessage();
        getView().getViewPartComponent(ViewPart.North).repaint();

        // if(messageCleaner != null && messageCleaner.isAlive())
        // return;
        //
        //
        // messageCleaner = new MessageCleaner(delay);
        // messageCleaner.start();

    }

    /**
     * paint north message, in north window part component
     * 
     * @param g2d
     *            the graphics context
     */
    private void paintNorthMessage(Graphics2D g2d) {

        if (northMessage == null) {
            return;
        }

        if (getView().getActiveProjection() == null) {
            return;
        }

        g2d.setColor(getView().getActiveProjection().getThemeColor().darker());
        g2d.setFont(font);
        JComponent comp = getView().getViewPartComponent(ViewPart.North);
        g2d.drawString(northMessage, getView().getPlaceHolderAxisWest(),
                       comp.getHeight() - 5);

    }

    /**
     * message cleaner for north message
     */
    class MessageCleaner extends Thread {

        private int delay;

        public MessageCleaner(int delay) {
            this.delay = delay;
        }

        @Override
        public void run() {
            try {
                getView().getViewPartComponent(ViewPart.North).repaint();
                Thread.sleep(delay);
                northMessage = null;
                getView().getViewPartComponent(ViewPart.North).repaint();
            }
            catch (InterruptedException e) {
                northMessage = null;
                getView().getViewPartComponent(ViewPart.North).repaint();
                Thread.currentThread().interrupt();

            }
        }
    }

    /**
     * paint north message in shift mode
     * 
     * @param g2d
     */
    private void paintPushingMessage(Graphics2D g2d) {
        Antialiasing a = Antialiasing.On;
        a.configureGraphics(g2d);
        int northHeight = getView().getViewPartComponent(ViewPart.North)
                .getHeight();
        synchronized (pushingMessages) {
            for (PushingMessage pm : pushingMessages) {
                if (pm.message == null || !pm.isAlive()) {
                    continue;
                }

                g2d.setFont(pm.font);

                if (pm.alpha < 0) {
                    pm.alpha = 0;
                }

                g2d.setColor(ColorPalette.alpha(pm.color,
                                                (int) (250 * pm.alpha)));
                g2d.setComposite(AlphaComposite.getInstance(
                                                            AlphaComposite.SRC_OVER, pm.alpha));
                g2d.drawString(pm.message, getView().getPlaceHolderAxisWest()
                        + pm.countCurentEffect
                        * getView().getDevice2D().getDeviceWidth()
                        / pm.countBaseEffect, northHeight - 5);
            }
        }

    }

    private void moveWidgetOperationCheckMove(MouseEvent me) {

        Projection w2d = getView().getActiveProjection();
        for (AbstractPlugin plugin : w2d.getPluginRegistry()) {

            if (plugin.isSelectable() && !plugin.isLockSelected()) {
                continue;
            }

            for (Widget widget : plugin.getWidgets()) {
                WidgetFolder widgetFolder = widget.getWidgetFolder();

                if (widgetFolder == null) {
                    continue;
                }

                if (widgetFolder.intercept(me.getX(), me.getY())) {
                    widgetFolder.setLockRollover(true);
                    getView().repaintDevice();
                }
                else {

                    widgetFolder.interruptPress();
                    if (widgetFolder.isLockRollover()) {
                        widgetFolder.setLockRollover(false);
                        getView().repaintDevice();
                    }
                }
            }
        }

    }

    @Override
    public void addPluginListener(PluginListener listener) {
        super.addPluginListener(listener);
    }

    /**
     * move animator
     */
    class MoveAnimator extends Thread {

        private WidgetFolder df;

        public MoveAnimator(WidgetFolder df) {
            this.df = df;

        }

        @Override
        public void run() {
            try {

                double folderCenterX = df.getX() + df.getWidth() / 2;
                double folderCenterY = df.getY() + df.getHeight() / 2;

                int curentMouseX = df.getCurrentDragX();
                int curentMouseY = df.getCurrentDragY();

                int stepCount = 20;

                double deltaX = (curentMouseX - folderCenterX) / stepCount;
                double deltaY = (curentMouseY - folderCenterY) / stepCount;

                double x = df.getX();
                double y = df.getY();

                for (int i = 0; i < stepCount; i++) {

                    df.setX(x + stepCount * deltaX);
                    df.setY(y + stepCount * deltaY);
                    getView().repaintDevice();
                    Thread.sleep(300);
                }

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    /**
     * check pressed event for widget move operation
     * 
     * @param me
     *            the mouse pressed event
     */
    private void moveWidgetOperationCheckPress(MouseEvent me) {
        if (getView().getActiveProjection() == null) {
            return;
        }

        // from all plugins
        Projection w2d = getView().getActiveProjection();
        for (final AbstractPlugin plugin : w2d.getPluginRegistry()) {

            if (plugin.isSelectable() && !plugin.isLockSelected()) {
                continue;
            }

            for (final Widget widget : plugin.getWidgets()) {

                if (!widget.isLockWidget()) {
                    continue;
                }

                final WidgetFolder widgetFolder = widget.getWidgetFolder();
                if (widgetFolder == null) {
                    continue;
                }
                if (widgetFolder.intercept(me.getX(), me.getY())
                        && !widget.isNoMoveOperation()) {
                    widgetFolder.setCurrentDragX(me.getX());
                    widgetFolder.setCurrentDragY(me.getY());

                    widgetFolder.startPress(800,
                                            new WidgetFolder.AsyncPressWidgetCallback() {

                                                @Override
                                                public void folderPress() {
                                                    getView().repaintDevice();
                                                    setNorthMessage("LOCK WIDGET/"
                                                            + widgetFolder.getId());
                                                    passivePlugins();
                                                    // TODO : move animator
                                                    // MoveAnimator ma = new MoveAnimator(df);
                                                    // ma.start();
                                                }
                                            });
                    break;
                }
                else {
                    widgetFolder.interruptPress();
                    activePlugins();
                }
            }
        }

        getView().repaintDevice();

    }

    /**
     * check drag event for widget move operation
     * 
     * @param me
     *            the mouse drag event
     */
    private void moveWidgetOperationCheckDrag(MouseEvent me) {
        if (getView().getActiveProjection() == null) {
            return;
        }

        // all widget
        Projection w2d = getView().getActiveProjection();
        for (AbstractPlugin plugin : w2d.getPluginRegistry()) {

            for (Widget widget : plugin.getWidgets()) {

                if (!widget.isLockWidget()) {
                    continue;
                }

                WidgetFolder widgetFolder = widget.getWidgetFolder();

                if (widgetFolder != null) {
                    if (widgetFolder.isLockPress()) {

                        // Rectangle r1 = widgetFolder.getCurentDragBound();
                        // Rectangle r2 = null;
                        WidgetFolder wf = widgetFolder.getPotentialFolder();
                        // if(wf != null)
                        // r2 = wf.getBounds();

                        widgetFolder.setCurrentDragX(me.getX());
                        widgetFolder.setCurrentDragY(me.getY());

                        // if(r1!=null)
                        // getView2D().repaintDevice((int)(r1.getX()-1),(int)(r1.getY()-1),(int)(r1.getWidth()+2),(int)(r1.getHeight()+2));
                        // if(r2!=null)
                        // getView2D().repaintDevice((int)(r2.getX()-1),(int)(r2.getY()-1),(int)(r2.getWidth()+2),(int)(r2.getHeight()+2));

                        getView().repaintDevice();
                    }
                }

            }
        }

    }

    /**
     * check released event for widget move operation
     * 
     * @param me
     *            the mouse realeased event
     */
    private void moveWidgetOperationCheckRelease(MouseEvent me) {
        if (getView().getActiveProjection() == null) {
            return;
        }

        setNorthMessage(null);
        Projection w2d = getView().getActiveProjection();
        for (AbstractPlugin plugin : w2d.getPluginRegistry()) {

            for (Widget widget : plugin.getWidgets()) {

                if (!widget.isLockWidget()) {
                    continue;
                }

                WidgetFolder widgetFolder = widget.getWidgetFolder();

                if (widgetFolder == null) {
                    continue;
                }

                if (widgetFolder.isLockPress()) {

                    if (widgetFolder.getTargetFolder() != null) {
                        widgetFolder.getOnPostListener().onPostWidget();
                        activePlugins();
                        if (isLockAboutFolderMessage()) {
                            pushMessage("POST/"
                                    + widgetFolder.getId()
                                    + "/"
                                    + widgetFolder.getTargetFolder()
                                            .getxIndex()
                                    + "/"
                                    + widgetFolder.getTargetFolder()
                                            .getyIndex(), plugin,
                                        PushingBehavior.Slow, font);
                        }

                    }

                }
                widgetFolder.interruptPress();
            }
        }

        getView().repaintDevice();

    }

    @Override
    public void onMove(MouseEvent me) {

        // handle widget drag for move operation
        // moveWidgetOperationCheckMove(me);

        // dispatch move on widget for functional operation
        dispatchMove(me);
    }

    /**
     * dispatch move event to widget
     * 
     * @param me
     *            the event to dispatch
     */
    private void dispatchMove(MouseEvent me) {

        // dispatch for all plugins in active window
        Projection w2d = getView().getActiveProjection();
        if (w2d == null) {
            return;
        }

        for (final AbstractPlugin plugin : w2d.getPluginRegistry()) {

            if (plugin.isSelectable() && plugin.isLockSelected()) {

                for (final Widget widget : plugin.getWidgets()) {

                    if (!widget.isLockWidget()) {
                        continue;
                    }

                    widget.interceptMove(me.getX(), me.getY());
                }
            }
            else {
                for (final Widget widget : plugin.getWidgets()) {

                    if (!widget.isLockWidget()) {
                        continue;
                    }

                    widget.interceptMove(me.getX(), me.getY());
                }
            }
        }

    }

    @Override
    public void onWheel(MouseWheelEvent mwe) {
        dispatchWheel(mwe);
    }

    /**
     * dispatch wheel event to widget
     * 
     * @param mwe
     *            the event to dispatch
     */
    private void dispatchWheel(MouseWheelEvent mwe) {

        Projection w2d = getView().getActiveProjection();
        if (w2d == null) {
            return;
        }
        for (final AbstractPlugin plugin : w2d.getPluginRegistry()) {

            if (plugin.isSelectable() && plugin.isLockSelected()) {
                for (final Widget widget : plugin.getWidgets()) {

                    if (!widget.isLockWidget()) {
                        continue;
                    }

                    widget.interceptWheel(mwe.getWheelRotation());
                }
            }
            else {
                for (final Widget widget : plugin.getWidgets()) {

                    if (!widget.isLockWidget()) {
                        continue;
                    }

                    widget.interceptWheel(mwe.getWheelRotation());
                }
            }
        }
    }

    @Override
    public void onDrag(MouseEvent me) {
        // handle widget drag for move operation
        moveWidgetOperationCheckDrag(me);

        // dispatch drag on widget for functional operation
        dispatchDrag(me);
    }

    /**
     * dispatch drag event to widget
     * 
     * @param me
     *            the event to dispatch
     */
    private void dispatchDrag(MouseEvent me) {

        // dispatch for all plugins in active window
        Projection w2d = getView().getActiveProjection();
        if (w2d == null) {
            return;
        }

        for (final AbstractPlugin plugin : w2d.getPluginRegistry()) {
            if (plugin.isSelectable() && plugin.isLockSelected()) {
                for (final Widget widget : plugin.getWidgets()) {

                    if (!widget.isLockWidget()) {
                        continue;
                    }

                    widget.interceptDrag(me.getX(), me.getY());
                }
            }
            else {
                for (final Widget widget : plugin.getWidgets()) {

                    if (!widget.isLockWidget()) {
                        continue;
                    }

                    widget.interceptDrag(me.getX(), me.getY());
                }
            }
        }

    }

    @Override
    public void onPress(MouseEvent me) {

        // handle widget press for move operation
        moveWidgetOperationCheckPress(me);

        // dispatch press on widget for functional operation
        dispatchPress(me);
    }

    /**
     * dispatch pressed event to widget
     * 
     * @param me
     *            the event to dispatch
     */
    private void dispatchPress(MouseEvent me) {

        // dispatch for all plugins in active window
        Projection w2d = getView().getActiveProjection();
        if (w2d == null) {
            return;
        }

        for (final AbstractPlugin plugin : w2d.getPluginRegistry()) {

            if (plugin.isSelectable() && plugin.isLockSelected()) {
                for (final Widget widget : plugin.getWidgets()) {

                    if (!widget.isLockWidget()) {
                        continue;
                    }

                    widget.interceptPress(me.getX(), me.getY());
                }
            }
            else {
                for (final Widget widget : plugin.getWidgets()) {

                    if (!widget.isLockWidget()) {
                        continue;
                    }

                    widget.interceptPress(me.getX(), me.getY());
                }
            }
        }

    }

    @Override
    public void onRelease(MouseEvent me) {

        // handle widget released for move operation
        moveWidgetOperationCheckRelease(me);

        // dispatch released on widget for functional operation
        dispatchRelease(me);
    }

    /**
     * dispatch release event to widget
     * 
     * @param me
     *            the event to dispatch
     */
    private void dispatchRelease(MouseEvent me) {

        // dispatch for all plugins in active window
        Projection w2d = getView().getActiveProjection();
        if (w2d == null) {
            return;
        }

        for (final AbstractPlugin plugin : w2d.getPluginRegistry()) {

            if (plugin.isSelectable() && plugin.isLockSelected()) {
                for (final Widget widget : plugin.getWidgets()) {

                    if (!widget.isLockWidget()) {
                        continue;
                    }

                    widget.interceptReleased(me.getX(), me.getY());
                }
            }
            else {
                for (final Widget widget : plugin.getWidgets()) {

                    if (!widget.isLockWidget()) {
                        continue;
                    }

                    widget.interceptReleased(me.getX(), me.getY());
                }
            }
        }

    }

    /**
     * true if the potential folder is empty, false otherwise.
     * 
     * @param widget
     *            the widget
     * @param potentialFolder
     *            the potential folder to check
     * @return true if the potential folder is empty, false otherwise.
     */
    private boolean isEmptyFolder(Widget widget, WidgetFolder potentialFolder) {
        Rectangle2D rec0 = new Rectangle2D.Double(potentialFolder.getX(),
                                                  potentialFolder.getY(), potentialFolder.getWidth(),
                                                  potentialFolder.getHeight());

        AbstractPlugin hostPlugin = widget.getHost();
        // compare with other widget register in plugin
        // widget other plugin non selectable (widget allways visible )
        for (Widget hostedPluginWidget : hostPlugin.getWidgets()) {
            if (hostedPluginWidget != widget) {
                WidgetFolder widgetFolder = hostedPluginWidget
                        .getWidgetFolder();
                if (rec0.intersects(widgetFolder.getBounds2D())) {
                    return false;
                }
            }
        }

        for (AbstractPlugin plugin : getView().getActiveProjection()
                .getPluginRegistry()) {

            if (hostPlugin == plugin
                    || plugin.isSelectable() && !plugin.isLockSelected()) {
                continue;
            }

            for (Widget pluginWidget : plugin.getWidgets()) {

                WidgetFolder widgetFolder = pluginWidget.getWidgetFolder();

                if (widgetFolder.getId().equals(potentialFolder.getId())) {
                    continue;
                }

                Rectangle2D rec1 = new Rectangle2D.Double(widgetFolder.getX(),
                                                          widgetFolder.getY(), widgetFolder.getWidth(),
                                                          widgetFolder.getHeight());
                if (rec0.intersects(rec1)) {
                   // System.out.println("drag widget intercept other widget : "+ pluginWidget.getId());
                    return false;

                }
            }
        }

        return true;
    }

    /**
     * passive plugins
     */
    private void passivePlugins() {

        for (AbstractPlugin l : getView().getActiveProjection()
                .getPluginRegistry()) {
            l.lockPassive();
        }
    }

    /**
     * active plugins
     */
    private void activePlugins() {
        // TODO check which one? only active or all plugins
        for (AbstractPlugin l : getView().getActiveProjection()
                .getPluginRegistry()) {
            l.unlockPassive();
        }
    }

    private void paintRolloverWidget(View v2d, Graphics2D g2d,
            ViewPart viewPart) {

        Projection w2d = getView().getActiveProjection();
        for (AbstractPlugin layout : w2d.getPluginRegistry()) {
            for (Widget widget : layout.getWidgets()) {

                WidgetFolder widgetFolder = widget.getWidgetFolder();
                if (widgetFolder.isLockRollover()) {
                    g2d.setColor(Color.WHITE);
                    g2d.draw(widgetFolder.getSensible());
                }
            }
        }

    }

    /**
     * paint phantom during drag widget operation
     * 
     * @param v2d
     * @param g2d
     * @param windowPart
     */
    private void paintDragPhantomWidget(View v2d, Graphics2D g2d,
            ViewPart viewPart) {

        Projection w2d = getView().getActiveProjection();
        if (w2d == null) {
            return;
        }

        for (AbstractPlugin plugin : w2d.getPluginRegistry()) {
            for (Widget widget : plugin.getWidgets()) {

                WidgetFolder widgetFolder = widget.getWidgetFolder();

                if (widgetFolder != null && widgetFolder.isLockPress()) {
                    createPotential(g2d, widget, plugin);
                }
            }
        }

    }

    /**
     * create potential widget folder
     * 
     * @param g2d
     *            the graphics context
     * @param widget
     *            the widget
     * @param plugin
     *            the host plugin for the specified widget
     */
    private void createPotential(Graphics2D g2d, Widget widget,
            AbstractPlugin plugin) {

        WidgetFolder widgetFolder = widget.getWidgetFolder();
        Rectangle2D r = new Rectangle2D.Double(widgetFolder.getCurrentDragX()
                - widgetFolder.getWidth() / 2, widgetFolder.getCurrentDragY()
                - widgetFolder.getHeight() / 2, widgetFolder.getWidth(),
                                               widgetFolder.getHeight());
        g2d.setColor(Color.GREEN);
        g2d.draw(r);

        //widgetFolder.setCurentDragBound(r.getBounds());

        WidgetFolder widgetPotentialFolder = getView()
                .newFolderIntanceByPosition(widgetFolder.getId(),
                                            widgetFolder.getWidth(), widgetFolder.getHeight(),
                                            widgetFolder.getCurrentDragX(),
                                            widgetFolder.getCurrentDragY());

        if (widgetPotentialFolder != null) {
            if (isEmptyFolder(widget, widgetPotentialFolder)) {
                widgetFolder.setPotentialFolder(widgetPotentialFolder);

                setNorthMessage(widgetFolder.getId() + "/"
                        + widgetPotentialFolder.getxIndex() + "/"
                        + widgetPotentialFolder.getyIndex() + "/valid");
                Rectangle2D rpotential = new Rectangle2D.Double(
                                                                widgetPotentialFolder.getX(),
                                                                widgetPotentialFolder.getY(),
                                                                widgetPotentialFolder.getWidth(),
                                                                widgetPotentialFolder.getHeight());

                if (plugin == null) {
                    g2d.setColor(new Color(255, 255, 255, 180));
                }
                else {
                    g2d.setColor(ColorPalette.alpha(plugin.getThemeColor(), 180));
                }
                g2d.fill(rpotential);
                widgetFolder.setTargetFolder(widgetPotentialFolder);
            }
            else {
                setNorthMessage(widgetFolder.getId() + "/"
                        + widgetPotentialFolder.getxIndex() + "/"
                        + widgetPotentialFolder.getyIndex() + "/invalid");
                Rectangle2D rpotential = new Rectangle2D.Double(
                                                                widgetPotentialFolder.getX(),
                                                                widgetPotentialFolder.getY(),
                                                                widgetPotentialFolder.getWidth(),
                                                                widgetPotentialFolder.getHeight());
                g2d.setColor(new Color(Color.RED.getRed(),
                                       Color.RED.getGreen(), Color.RED.getBlue(), 150));
                g2d.fill(rpotential);
            }

        }

    }

    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
        if (viewPart == ViewPart.Device) {
            // paint all widget for active window
            if (v2d.getActiveProjection() != null) {
                List<AbstractPlugin> plugins = v2d.getActiveProjection().getPluginRegistry();
                for (AbstractPlugin plugin : plugins) {
                    List<Widget> widgets = plugin.getWidgets();
                    for (Widget widget : widgets) {
                        if (widget.isLockWidget()) {
                            widget.paint(v2d, g2d);
                        }
                    }
                }
            }
            // paint phantom during drag operation
            paintDragPhantomWidget(v2d, g2d, viewPart);
        }
        if (viewPart == ViewPart.North) {
            paintPushingMessage(g2d);
            paintNorthMessage(g2d);
        }

    }

}
