/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.catalog.source;

import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;


public class SourceScrollPane extends JScrollPane {

    private JTextArea area;

    public SourceScrollPane(JComponent area) {

        setBorder(BorderFactory.createEmptyBorder());

        area.setOpaque(false);
        area.setBorder(BorderFactory.createEmptyBorder());

        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setViewport(createViewport());
        getViewport().setOpaque(false);
        // getViewport().setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
        setVerticalScrollBar(createVerticalScrollBar());
        setHorizontalScrollBar(createHorizontalScrollBar());
        getVerticalScrollBar().setUI(new DemoScrollbarUI());
        getHorizontalScrollBar().setUI(new DemoScrollbarUI());

        // setSize(150, 200);
        // setPreferredSize(new Dimension(100,100));

        setViewportView(area);

        if (!getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }

        updateUI();

    }

    public SourceScrollPane() {

        setBorder(BorderFactory.createEmptyBorder());
        area = new JTextArea();
        area.setOpaque(false);
        area.setBorder(BorderFactory.createEmptyBorder());

        setLayout(new ScrollPaneLayout.UIResource());
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        setViewport(createViewport());
        getViewport().setOpaque(false);
        // getViewport().setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
        setVerticalScrollBar(createVerticalScrollBar());
        setHorizontalScrollBar(createHorizontalScrollBar());
        getVerticalScrollBar().setUI(new DemoScrollbarUI());
        getHorizontalScrollBar().setUI(new DemoScrollbarUI());

        // setSize(150, 200);
        // setPreferredSize(new Dimension(100,100));

        setViewportView(area);

        if (!getComponentOrientation().isLeftToRight()) {
            viewport.setViewPosition(new Point(Integer.MAX_VALUE, 0));
        }

        updateUI();

    }

    public JTextArea getArea() {
        return area;
    }

    public void setArea(JTextArea area) {
        this.area = area;
    }

    public void setText(String text) {
        area.setText(text);
    }

}
