/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class View extends JPanel {

    private final static long serialVersionUID = 1L;

    private JPanel editor;
    private JPanel footer;
    private JPanel header;
    private JLabel labelHeader;
    private Perspective perspective;

    public View(String title) {
        this(null, title);
    }

    public View(ImageIcon icon, String title) {
        setOpaque(false);
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);
        Border b = BorderFactory.createLineBorder(Color.GRAY);

        setBorder(new ViewRoundedBorder());
        header = new JPanel();
        header.setMaximumSize(new Dimension(1000, 25));
        header.setMinimumSize(new Dimension(100, 25));
        header.setPreferredSize(new Dimension(100, 25));
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        labelHeader = new JLabel();
        header.setBackground(Color.LIGHT_GRAY);
        if (icon != null) {
            labelHeader.setIcon(icon);
        }
        labelHeader.setFont(new Font("Dialog", Font.PLAIN, 12));
        labelHeader.setText(title);
        header.add(Box.createHorizontalStrut(5));
        header.add(labelHeader);
        header.add(Box.createHorizontalGlue());

        add(header, BorderLayout.NORTH);

        footer = new JPanel();
        footer.setMaximumSize(new Dimension(1000, 5));
        footer.setMinimumSize(new Dimension(100, 5));
        footer.setPreferredSize(new Dimension(100, 5));
        footer.setBackground(Color.LIGHT_GRAY);
        footer.setLayout(new BoxLayout(footer, BoxLayout.X_AXIS));

        add(footer, BorderLayout.SOUTH);

        editor = new JPanel();
        editor.setLayout(new BorderLayout());
        // Border emptyBdr = BorderFactory.createEmptyBorder(10,10,10,10);
        Border lineBorder = BorderFactory.createLineBorder(Color.GRAY);
        editor.setBorder(lineBorder);
        editor.setBackground(Color.WHITE);

        JPanel center = new JPanel();
        center.setLayout(new BorderLayout());
        center.setBackground(Color.LIGHT_GRAY);
        Border emptyBdrCenter = BorderFactory.createEmptyBorder(0, 4, 0, 4);
        center.setBorder(emptyBdrCenter);

        JScrollPane verticalPane = new JScrollPane(editor, // component
                                                   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, // vertical
                                                                                                     // bar
                                                   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED); // assuming
                                                                                                        // you
                                                                                                        // only

        verticalPane.setBorder(null);
        center.add(verticalPane, BorderLayout.CENTER);
        // center.add(editor,BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    public JPanel getEditor() {
        return editor;
    }

    public JPanel getFooter() {
        return footer;
    }

    public JPanel getHeader() {
        return header;
    }

    public JLabel getLabelHeader() {
        return labelHeader;
    }

    public Perspective getPerspective() {
        return perspective;
    }

    public void setPerspective(Perspective perspective) {
        this.perspective = perspective;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

}
