/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.shell;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.jensoft.core.device.DevicePartComponent;
import org.jensoft.core.palette.color.Alpha;
import org.jensoft.core.palette.color.Spectral;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;
import org.jensoft.core.widget.Widget;

/**
 * <code>MessagePlugin</code>
 * 
 * @author sebastien Janaud
 */
public class ShellPlugin extends AbstractPlugin implements
        AbstractPlugin.OnPressListener, AbstractPlugin.OnReleaseListener,
        AbstractPlugin.OnMoveListener, AbstractPlugin.OnDragListener {

    /** list of messages */
    private List<Shell> messages = new ArrayList<Shell>();

    /** current message index */
    private int currentIndex = -1;

    /** the message who is maximized in device 2D */
    private Shell maximizedShell;

    /** volatile message */
    private Shell volatileShell;

    /** sliding lock flag */
    private boolean sliding = false;

    /** current message in message list, exclude volatile message */
    private Shell currentMessage;

    /**
     * create message plug in
     */
    public ShellPlugin() {
        setName("MessagePlugin");
        setOnDragListener(this);
        setOnMoveListener(this);
        setOnPressListener(this);
        setOnReleaseListener(this);
        setPriority(1000000);
    }

    /**
     * add the specified message
     * 
     * @param message
     *            the message to add
     */
    public void registerMessage(Shell message) {
        if (!messages.contains(message)) {
            message.setHost(this);
            messages.add(message);
        }
    }

    /**
     * return true if the specified message is the last message in message list, false otherwise
     * 
     * @param message
     *            the message which is maybe the last message
     * @return true if the specified message is the last message in message list, false otherwise
     */
    public boolean isLastMessage(Shell message) {
        return messages.contains(message) && messages.get(messages.size() - 1).equals(message);
    }

    /**
     * return true if the specified message is the first message in message list,, false otherwise
     * 
     * @param message
     *            the message which is maybe the first message
     * @return true if the specified message is the first message in message list, false otherwise
     */
    public boolean isFirstMessage(Shell message) {
        return messages.contains(message) && messages.get(0).equals(message);
    }

    /**
     * return true if regarding to the current index there are next message in the message list,
     * false if the current index is the last index of message list
     * 
     * @return has next message
     */
    public boolean hasNext() {
        if (currentIndex < messages.size() - 1 && messages.size() >= 1) {
            return true;
        }
        return false;
    }

    /**
     * return true if regarding to the current index there are previous message in the message list,
     * false if the current index is the first index of message list
     * 
     * @return has next message
     */
    public boolean hasPrevious() {
        if (currentIndex > 0 && messages.size() >= 1) {
            return true;
        }
        return false;
    }

    /**
     * return true if the message plugin in in sliding operation, false otherwise
     * 
     * @return the sliding
     */
    public boolean isSliding() {
        return sliding;
    }

    /**
     * maximize the specified message
     * 
     * @param message
     *            the message to maximize
     */
    public void maximizeMessage(final Shell message) {
        int width = getProjection().getDevice2D().getDeviceWidth();
        int height = getProjection().getDevice2D().getDeviceHeight();
        message.setBounds(0, 0, width, height);
        lockPassive();
        for (Widget widget : getWidgets()) {
            widget.unlockWidget();
        }
        maximizedShell = message;

    }

    /**
     * maximize the specified message
     * 
     * @param message
     *            the message to maximize
     */
    public void unMaximizeMessage(Shell message) {
        if (message.equals(maximizedShell)) {
            int width = getProjection().getDevice2D().getDeviceWidth();
            int height = getProjection().getDevice2D().getDeviceHeight();
            int x = (width / 2 - message.getShellWidth() / 2);
            int y = (height / 2 - message.getShellHeight() / 2);
            message.setBounds(x, y, message.getShellWidth() - 10, message.getShellHeight() - 10);
            unlockPassive();
            for (Widget widget : getWidgets()) {
                widget.lockWidget();
            }
            maximizedShell = null;
        }
    }

    /**
     * @return the messages
     */
    public List<Shell> getMessages() {
        return messages;
    }

    /**
     * @param messages
     *            the messages to set
     */
    public void setMessages(List<Shell> messages) {
        this.messages = messages;
    }

    /**
     * @return the currentIndex
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * @param currentIndex
     *            the currentIndex to set
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    /**
     * return message count
     * 
     * @return message count
     */
    public int messageCount() {
        return messages.size();
    }

    /**
     * the slide by right or slide by left
     * 
     * @author sebastien janaud
     */
    public enum Slide {
        NextMessage,
        PreviousMessage;
    }

    /**
     * show the specified message in message list
     * 
     * @param index
     *            the message index
     * @param slide
     *            the slide by right or slide by left
     */
    public void showMessage(int index, Slide slide) {

        DevicePartComponent comp = getProjection().getView().getDevice2D();
        if (volatileShell != null) {
            comp.remove(volatileShell);
        }

        if (messages.isEmpty()) {
            return;
        }

        if (index >= -1 && index <= messages.size()) {
            if (index >= 0 && index <= messages.size() - 1) {
                Shell message = messages.get(index);
                comp.add(message);
                MessageSlideShow ms = new MessageSlideShow(message, slide);
                ms.start();
            }
            else {
                MessageSlideShow ms = new MessageSlideShow(null, slide);
                ms.start();
            }

        }
    }

    /**
     * slide message from to index
     * 
     * @param fromIndex
     * @param toIndex
     */
    public void slideToMessage(int fromIndex, int toIndex) {
        SlideRunner runner = new SlideRunner(fromIndex, toIndex);
        runner.start();
    }

    /**
     * slide message from current index to specified index
     * 
     * @param toIndex
     */
    public void slideToMessage(int toIndex) {

        if (currentIndex < 0) {
            SlideRunner runner = new SlideRunner(0, toIndex);
            runner.start();
        }
        else {
            SlideRunner runner = new SlideRunner(getCurrentIndex(), toIndex);
            runner.start();
        }

    }

    class SlideRunner extends Thread {

        int fromIndex;
        int toIndex;

        public SlideRunner(int fromIndex, int toIndex) {
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        public void run() {
            DevicePartComponent comp = getProjection().getView().getDevice2D();
            try {
                if (toIndex > fromIndex) {
                    for (int i = fromIndex; i <= toIndex; i++) {
                        // System.out.println("i start : "+i);
                        Shell m = messages.get(i);
                        // System.out.println("message : "+m);
                        comp.add(m);
                        MessageSlideShow mssi = new MessageSlideShow(m, Slide.NextMessage);
                        mssi.start();
                        mssi.join();
                    }
                }
                if (toIndex < fromIndex) {
                    for (int i = fromIndex; i >= toIndex; i--) {
                        Shell m = messages.get(i);
                        comp.add(m);
                        MessageSlideShow mssi = new MessageSlideShow(m, Slide.PreviousMessage);
                        mssi.start();
                        mssi.join();
                    }
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private VolatileMessageUnshow unshow;

    /**
     * close current Message
     */
    public void closeCurrentMessage() {
        if (unshow != null && unshow.isAlive()) {
            unshow.interrupt();
            try {
                unshow.join();
            }
            catch (InterruptedException e) {
            }
        }

        Shell msgToClose = null;
        if (volatileShell != null) {
            msgToClose = volatileShell;
            volatileShell = null;
        }
        else if (currentMessage != null) {
            msgToClose = currentMessage;
            currentMessage = null;
        }
        if (msgToClose != null) {
            msgToClose.setLockDeleteBackground(false);
            if (msgToClose.isLockMaximize()) {
                unMaximizeMessage(msgToClose);
                msgToClose.setLockMaximize(false);
            }
            if (msgToClose.isLockMinimize()) {
                msgToClose.setLockMinimize(false);
            }
            unshow = new VolatileMessageUnshow(msgToClose, Slide.NextMessage);
            unshow.start();

        }
    }

    /**
     * show the specified message centered on device component
     * 
     * @param message
     *            the message to show
     */
    public void showVolatileMessage(Shell message) {
        DevicePartComponent comp = getProjection().getView().getDevice2D();

        if (unshow != null && unshow.isAlive()) {
            unshow.interrupt();
            try {
                unshow.join();
            }
            catch (InterruptedException e) {
            }
        }
        volatileShell = message;
        volatileShell.setHost(this);

        for (int i = 0; i < messages.size(); i++) {
            comp.remove(messages.get(i));
        }
        // volatileShell.setText(volatileShell.getText());
        comp.add(message);
        VolatileMessageShow vms = new VolatileMessageShow(volatileShell, Slide.NextMessage);
        vms.start();
    }

    /**
     * show the specified message centered on device component
     * 
     * @param message
     *            the message to show
     */
    public void showVolatileMessage(Shell message, long waitAndUnshow) {
        DevicePartComponent comp = getProjection().getView().getDevice2D();

        if (unshow != null && unshow.isAlive()) {
            unshow.interrupt();
            try {
                unshow.join();
            }
            catch (InterruptedException e) {
            }
        }
        volatileShell = message;
        volatileShell.setHost(this);

        for (int i = 0; i < messages.size(); i++) {
            comp.remove(messages.get(i));
        }
        // volatileShell.setText(volatileShell.getText());
        comp.add(message);
        VolatileMessageShow vms = new VolatileMessageShow(volatileShell, Slide.NextMessage);
        vms.start();

        final long wait = waitAndUnshow;
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    Thread.sleep(wait);
                    closeCurrentMessage();
                }
                catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    /**
     * remove the volatile message showing from device component
     * 
     */
    public void unshowVolatileMessage() {
        if (volatileShell != null) {
            DevicePartComponent comp = getProjection().getView().getDevice2D();
            comp.remove(volatileShell);
            volatileShell = null;
        }
    }

    /**
     * message slide show
     * 
     * @author sebastien janaud
     */
    class VolatileMessageUnshow extends Thread {

        private Shell m;
        private Slide slide;

        public VolatileMessageUnshow(Shell m, Slide slide) {
            this.m = m;
            this.slide = slide;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            sliding = true;
            try {
                if (m != null) {
                    getProjection().getView().repaintDevice();

                    int devicewidth = getProjection().getDevice2D().getDeviceWidth();

                    Rectangle currentInitialBound = null;
                    if (m != null) {
                        currentInitialBound = m.getBounds();
                    }

                    if (slide == Slide.PreviousMessage) {
                        int startX = -m.getShellWidth();
                        int endX = (devicewidth / 2 - m.getShellWidth() / 2);
                        int count = 1;
                        for (int x = startX; x <= endX; x = x + 20) {

                            if (m != null) {
                                m.setBounds((int) currentInitialBound.getX() + count++ * 20,
                                            (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                            }
                            getProjection().getView().repaintDevice();
                            Thread.sleep(10);
                        }

                        getProjection().getView().repaintDevice();
                    }

                    if (slide == Slide.NextMessage) {

                        int startX = devicewidth;
                        int endX = (devicewidth / 2 - m.getShellWidth() / 2);
                        int count = 1;
                        for (int x = startX; x >= endX; x = x - 20) {

                            if (m != null) {
                                m.setBounds((int) currentInitialBound.getX() - count++ * 20,
                                            (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                            }
                            getProjection().getView().repaintDevice();
                            Thread.sleep(10);
                        }

                        getProjection().getView().repaintDevice();
                    }

                }
            }
            catch (Exception e) {
                DevicePartComponent comp = getProjection().getView().getDevice2D();
                comp.remove(m);
                Thread.currentThread().interrupt();
            }
            DevicePartComponent comp = getProjection().getView().getDevice2D();
            comp.remove(m);
            sliding = false;
        }
    }

    /**
     * message slide show
     * 
     * @author sebastien janaud
     */
    class VolatileMessageShow extends Thread {

        private Shell m;
        private Slide slide;

        public VolatileMessageShow(Shell m, Slide slide) {
            this.m = m;
            this.slide = slide;
            if (m != null) {
                DevicePartComponent comp = getProjection().getView().getDevice2D();
                int x = (comp.getWidth() / 2 - m.getShellWidth() / 2);
                int y = (comp.getHeight() / 2 - m.getShellHeight() / 2);
                int w = m.getShellWidth();
                int h = m.getShellHeight();

                if (m.getShellWidth() <= comp.getWidth()) {
                    x = (comp.getWidth() / 2 - m.getShellWidth() / 2);
                    w = m.getShellWidth();
                }
                else {
                    x = 5;
                    w = comp.getWidth() - 10;
                    m.setShellWidth(comp.getWidth() - 10);
                }

                if (m.getShellHeight() <= comp.getHeight()) {
                    y = (comp.getHeight() / 2 - m.getShellHeight() / 2);
                    h = m.getShellHeight();
                }
                else {
                    y = 5;
                    h = comp.getHeight();
                    m.setShellHeight(comp.getHeight() - 10);
                }
                if (slide == Slide.PreviousMessage) {
                    this.m.setBounds(-w - 100, y, w, h);
                }
                else {
                    this.m.setBounds(comp.getWidth() + 100, y, w, h);
                }
            }
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            sliding = true;
            try {

                if (m != null) {
                    m.setLockDeleteBackground(false);
                    getProjection().getView().repaintDevice();
                    // m.setText(m.getText());

                    int devicewidth = getProjection().getDevice2D().getDeviceWidth();
                    Rectangle msgSize = m.getBounds();

                    if (slide == Slide.PreviousMessage) {
                        int startX = (int) -msgSize.getWidth();
                        int endX = (int) (devicewidth / 2 - msgSize.getWidth() / 2);
                        int count = 1;
                        m.setBounds(startX, (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                        getProjection().getView().repaintDevice();
                        for (int x = startX; x <= endX; x = x + 20) {
                            m.setBounds(x, (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                            getProjection().getView().repaintDevice();
                            Thread.sleep(10);
                        }
                        m.setBounds(endX, (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                        // m.setText(m.getText());
                        getProjection().getView().repaintDevice();
                    }

                    if (slide == Slide.NextMessage) {

                        int startX = devicewidth;
                        int endX = (int) (devicewidth / 2 - msgSize.getWidth() / 2);
                        int count = 1;
                        m.setBounds(startX, (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                        getProjection().getView().repaintDevice();
                        for (int x = startX; x >= endX; x = x - 20) {
                            m.setBounds(x, (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                            getProjection().getView().repaintDevice();
                            Thread.sleep(10);
                        }
                        m.setBounds(endX, (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                        // m.setText(m.getText());
                        getProjection().getView().repaintDevice();
                    }

                }

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            catch (Exception e) {
            }
            sliding = false;
        }
    }

    /**
     * message slide show
     * 
     * @author sebastien janaud
     */
    class MessageSlideShow extends Thread {

        private Shell m;
        private Slide slide;

        public MessageSlideShow(Shell m, Slide slide) {
            this.m = m;
            this.slide = slide;
            if (m != null) {
                DevicePartComponent comp = getProjection().getView().getDevice2D();
                int x = (comp.getWidth() / 2 - m.getShellWidth() / 2);
                int y = (comp.getHeight() / 2 - m.getShellHeight() / 2);
                int w = m.getShellWidth();
                int h = m.getShellHeight();

                if (m.getShellWidth() <= comp.getWidth()) {
                    x = (comp.getWidth() / 2 - m.getShellWidth() / 2);
                    w = m.getShellWidth();
                }
                else {
                    x = 5;
                    w = comp.getWidth() - 10;
                    m.setShellWidth(comp.getWidth() - 10);
                }

                if (m.getShellHeight() <= comp.getHeight()) {
                    y = (comp.getHeight() / 2 - m.getShellHeight() / 2);
                    h = m.getShellHeight();
                }
                else {
                    y = 5;
                    h = comp.getHeight();
                    m.setShellHeight(comp.getHeight() - 10);
                }
                this.m.setBounds(-w - 100, y, w, h);
            }
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            sliding = true;
            try {

                if (m != null) {
                    m.setLockDeleteBackground(false);
                    getProjection().getView().repaintDevice();
                    // m.setText(m.getText());

                    int devicewidth = getProjection().getDevice2D().getDeviceWidth();
                    Rectangle msgSize = m.getBounds();
                    Rectangle currentInitialBound = null;
                    if (currentMessage != null) {
                        currentInitialBound = currentMessage.getBounds();
                    }

                    if (slide == Slide.PreviousMessage) {
                        int startX = (int) -msgSize.getWidth();
                        int endX = (int) (devicewidth / 2 - msgSize.getWidth() / 2);
                        int count = 1;
                        for (int x = startX; x <= endX; x = x + 20) {

                            m.setBounds(x, (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                            if (currentMessage != null && currentMessage != m) {
                                currentMessage.setBounds((int) currentInitialBound.getX() + count++ * 20,
                                                         (int) currentMessage.getBounds().getY(),
                                                         currentMessage.getShellWidth(),
                                                         currentMessage.getShellHeight());
                            }
                            getProjection().getView().repaintDevice();
                            Thread.sleep(10);
                        }
                        m.setBounds(endX, (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                        // m.setText(m.getText());
                        getProjection().getView().repaintDevice();
                    }

                    if (slide == Slide.NextMessage) {

                        int startX = devicewidth;
                        int endX = (int) (devicewidth / 2 - msgSize.getWidth() / 2);
                        int count = 1;
                        for (int x = startX; x >= endX; x = x - 20) {
                            m.setBounds(x, (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                            if (currentMessage != null && currentMessage != m) {
                                currentMessage.setBounds((int) currentInitialBound.getX() - count++ * 20,
                                                         (int) currentMessage.getBounds().getY(),
                                                         currentMessage.getShellWidth(),
                                                         currentMessage.getShellHeight());
                            }
                            getProjection().getView().repaintDevice();
                            Thread.sleep(10);
                        }
                        m.setBounds(endX, (int) m.getBounds().getY(), m.getShellWidth(), m.getShellHeight());
                        // m.setText(m.getText());
                        getProjection().getView().repaintDevice();
                    }

                    if (currentMessage != null) {
                        DevicePartComponent comp = getProjection().getView().getDevice2D();
                        comp.remove(currentMessage);
                    }
                    currentMessage = m;
                }
                else {

                    if (currentMessage != null) {
                        getProjection().getView().repaintDevice();

                        int devicewidth = getProjection().getDevice2D().getDeviceWidth();

                        Rectangle currentInitialBound = null;
                        if (currentMessage != null) {
                            currentInitialBound = currentMessage.getBounds();
                        }

                        if (slide == Slide.PreviousMessage) {
                            int startX = -currentMessage.getShellWidth();
                            int endX = (devicewidth / 2 - currentMessage.getShellWidth() / 2);
                            int count = 1;
                            for (int x = startX; x <= endX; x = x + 20) {

                                if (currentMessage != null && currentMessage != m) {
                                    currentMessage.setBounds((int) currentInitialBound.getX() + count++ * 20,
                                                             (int) currentMessage.getBounds().getY(),
                                                             currentMessage.getShellWidth(),
                                                             currentMessage.getShellHeight());
                                }
                                getProjection().getView().repaintDevice();
                                Thread.sleep(10);
                            }

                            getProjection().getView().repaintDevice();
                        }

                        if (slide == Slide.NextMessage) {

                            int startX = devicewidth;
                            int endX = (devicewidth / 2 - currentMessage.getShellWidth() / 2);
                            int count = 1;
                            for (int x = startX; x >= endX; x = x - 20) {

                                if (currentMessage != null && currentMessage != m) {
                                    currentMessage.setBounds((int) currentInitialBound.getX() - count++ * 20,
                                                             (int) currentMessage.getBounds().getY(),
                                                             currentMessage.getShellWidth(),
                                                             currentMessage.getShellHeight());
                                }
                                getProjection().getView().repaintDevice();
                                Thread.sleep(10);
                            }

                            getProjection().getView().repaintDevice();
                        }

                        if (currentMessage != null) {
                            DevicePartComponent comp = getProjection().getView().getDevice2D();
                            comp.remove(currentMessage);
                        }

                        currentMessage = null;
                    }
                }

            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            catch (Exception e) {
            }
            sliding = false;
        }
    }

    /**
     * show next message in stack
     */
    public void nextMessage() {
       // System.out.println("next message with current index : " + getCurrentIndex());
        currentIndex++;
        //System.out.println("increment current index : " + getCurrentIndex());
        if (currentIndex > messages.size() - 1) {
            currentIndex = messages.size() - 1;
        }
        else {
            showMessage(currentIndex, Slide.NextMessage);
        }
    }

    /**
     * show previous message in stack
     */
    public void previousMessage() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = 0;
        }
        else {
            showMessage(currentIndex, Slide.PreviousMessage);
        }
    }

    /**
     * unregister message
     * 
     * @param message
     */
    public void unregisterMessage(Shell message) {
        if (messages.contains(message)) {
            if (message.isLockMaximize()) {
                unlockPassive();
                for (Widget widget : getWidgets()) {
                    widget.lockWidget();
                }
            }
            messages.remove(message);
        }
    }

    /**
     * get a lock/unlock action
     * 
     * @return lock unlock action
     */
    public MessageLockUnlockAction getMessageLockUnlockAction() {
        return new MessageLockUnlockAction();
    }

    /**
     * translate select action
     */
    class MessageLockUnlockAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    if (isLockSelected()) {
                        unlockSelected();
                    }
                    else {
                        lockSelected();
                    }
                }
            });
        }

    }

    /**
     * the component adapter that takes the responsibility to manage message size and location during device on resized
     */
    private ComponentListener messageBoundAdapter = new ComponentAdapter() {

        @Override
        public void componentResized(ComponentEvent e) {
            if (volatileShell != null) {
                if (volatileShell.isLockMaximize()) {
                    int width = getProjection().getDevice2D().getDeviceWidth();
                    int height = getProjection().getDevice2D().getDeviceHeight();
                    volatileShell.setBounds(5, 5, width - 10, height - 10);
                }
                else if (volatileShell.isLockMinimize()) {
                    int width = getProjection().getDevice2D().getDeviceWidth();
                    int height = getProjection().getDevice2D().getDeviceHeight();
                    volatileShell.setBounds(width / 2 - volatileShell.getShellWidth() / 2, height / 2
                            - volatileShell.getShellHeight() / 2,
                                            volatileShell.getShellWidth(),
                                            volatileShell.getShellHeight());
                }
            }
            else if (currentMessage != null) {
                if (currentMessage.isLockMaximize()) {
                    int width = getProjection().getDevice2D().getDeviceWidth();
                    int height = getProjection().getDevice2D().getDeviceHeight();
                    currentMessage.setBounds(5, 5, width - 10, height - 10);
                }
                else if (currentMessage.isLockMinimize()) {
                    int width = getProjection().getDevice2D().getDeviceWidth();
                    int height = getProjection().getDevice2D().getDeviceHeight();
                    currentMessage.setBounds(width / 2 - currentMessage.getShellWidth() / 2, height / 2
                            - currentMessage.getShellHeight() / 2,
                                             currentMessage.getShellWidth(),
                                             currentMessage.getShellHeight());
                }
            }
        }

    };

    // boolean init = true;

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {

        getProjection().getView().removeComponentListener(messageBoundAdapter);
        getProjection().getView().addComponentListener(messageBoundAdapter);

        if (viewPart != ViewPart.Device) {
            return;
        }
        if (!isLockSelected()) {
            return;
        }

        if (isLockPassive()) {
            return;
        }

        double startX = 2;
        double startY = 2;
        double cellWidth = 8;
        double cellHeight = 8;

        for (int i = 0; i < messages.size(); i++) {

            Rectangle2D rec1 = new Rectangle2D.Double(startX, startY, cellWidth, cellHeight);
            Ellipse2D rec = new Ellipse2D.Double(startX, startY, cellWidth, cellHeight);
            Shell msg = messages.get(i);
            msg.setMiniFrame(rec1);

            if (msg.equals(currentMessage)) {

                if (msg.isLockDeleteBackground()) {
                    // g2d.setColor(new Alpha(Spectral.SPECTRAL_RED.brighter(),100));
                    g2d.setColor(new Alpha(Color.red, 200));
                }
                else if (msg.isLockMaximizeBackground()) {
                    g2d.setColor(new Alpha(Spectral.SPECTRAL_BLUE2, 200));
                    // g2d.draw(r);
                }
                else {
                    g2d.setColor(new Alpha(Spectral.SPECTRAL_YELLOW.brighter(), 200));
                }
            }
            else {

                if (msg.isLockDeleteBackground()) {
                    // g2d.setColor(new Alpha(Spectral.SPECTRAL_RED.brighter(),100));
                    g2d.setColor(new Alpha(Color.red, 100));
                }
                else {
                    g2d.setColor(new Color(0, 0, 0, 40));
                }
            }

            if (msg.isLockMiniFrameRollover()) {
                g2d.setColor(new Alpha(Spectral.SPECTRAL_YELLOW.brighter(), 250));
            }

            g2d.fill(rec);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            // Rectangle rMaximize = new Rectangle ((int)(startX + 4 ), (int)(startY+3),16,16);
            // if(msg.equals(currentMessage)){
            // g2d.drawImage(maximizeIcon2.getImage(), (int)(startX + 4 ), (int)(startY+3),24,24, null);
            // cellHeight = 24;
            // }
            // else{
            // cellHeight = 17;
            // g2d.drawImage(maximizeIcon.getImage(), (int)(startX + 4 ), (int)(startY+3),16,16, null);
            // }
            // msg.setMaximizeFrame(rMaximize);

            g2d.setColor(Color.WHITE);
            g2d.draw(rec);

            startY = startY + cellHeight + 4;
        }

    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt.event.MouseEvent)
     */
    @Override
    public void onDrag(MouseEvent me) {
    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt.event.MouseEvent)
     */
    @Override
    public void onMove(MouseEvent me) {
        for (int i = 0; i < messages.size(); i++) {
            Shell msg = messages.get(i);
            if (msg.getMiniFrame() != null) {
                if (msg.getMiniFrame().contains(me.getX(), me.getY())) {
                    msg.setLockMiniFrameRollover(true);
                    getProjection().getView().repaintDevice(msg.getMiniFrame().getBounds());
                    showVolatileMessage(msg);
                }
                else {
                    if (msg.isLockMiniFrameRollover()) {
                        msg.setLockMiniFrameRollover(false);
                        getProjection().getView().repaintDevice();
                    }
                }
            }
        }

    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java.awt.event.MouseEvent)
     */
    @Override
    public void onRelease(MouseEvent me) {
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt.event.MouseEvent)
     */
    @Override
    public void onPress(MouseEvent me) {

        for (int i = 0; i < messages.size(); i++) {
            Shell msg = messages.get(i);
            if (msg.getMiniFrame() != null) {
                if (msg.getMiniFrame().contains(me.getX(), me.getY())) {
                    unshowVolatileMessage();
                    showVolatileMessage(msg);
                }
            }
        }

    }

}
