/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.widget.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

/**
 * @author Sebastien Janaud
 */
public class PullItem {

    private int order = 0;

    private String text;
    private ImageIcon pullIcon;
    private ActionListener listener;

    private JMenuItem pullEntry;

    private PullDownButtonWidget pullDownWidget;

    /**
     * @param text
     */
    public PullItem(String text, ImageIcon pullIcon) {
        this.text = text;
        this.pullIcon = pullIcon;
    }

    /**
     * @return the order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * @return the pullDownWidget
     */
    public PullDownButtonWidget getPullDownWidget() {
        return pullDownWidget;
    }

    /**
     * @param pullDownWidget
     *            the pullDownWidget to set
     */
    public void setPullDownWidget(PullDownButtonWidget pullDownWidget) {
        this.pullDownWidget = pullDownWidget;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the pullIcon
     */
    public ImageIcon getPullIcon() {
        return pullIcon;
    }

    /**
     * @param pullIcon
     *            the pullIcon to set
     */
    public void setPullIcon(ImageIcon pullIcon) {
        this.pullIcon = pullIcon;
    }

    /**
     * @return the listener
     */
    public ActionListener getListener() {
        return listener;
    }

    private void onActionPerformed() {
        pullDownWidget.updatePull(this);
        pullDownWidget.getHost().getWindow2D().getView2D()
                .repaintDevice(pullDownWidget.getWidgetFolder().getBounds());
    }

    /**
     * @param listener
     *            the listener to set
     */
    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    private ActionListener handler = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            onActionPerformed();
        }
    };

    /**
     * create pull entry
     */
    public JMenuItem getPullEntry() {

        if (pullEntry != null) {
            pullEntry.removeActionListener(handler);
            pullEntry.setIcon(null);
        }

        pullEntry = new JMenuItem(text);
        pullEntry.addActionListener(handler);

        if (pullIcon != null) {
            pullEntry.setIcon(pullIcon);
        }

        if (listener != null) {
            pullEntry.addActionListener(listener);
        }

        return pullEntry;
    }

}
