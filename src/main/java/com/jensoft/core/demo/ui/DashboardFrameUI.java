/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.demo.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.jensoft.core.demo.component.DemoTab;
import com.jensoft.core.demo.component.DemoTabSet;

public class DashboardFrameUI extends JFrame {

    private static final long serialVersionUID = 156889765687899L;

    private Dashboard demo;
    
    public void show(Dashboard demo) {
        this.demo = demo;
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {

        }

        ImageIcon iconFrame = ImageResource.getInstance().createImageIcon(
                                                                          "jensoft.png", "");
        setIconImage(iconFrame.getImage());
        setTitle("JenSoft Demo");
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel masterPane = new JPanel();
        masterPane.setBackground(Color.BLACK);

        masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        masterPane.setLayout(new BorderLayout());

        DemoTabSet cbar = new DemoTabSet();

        cbar.setTitle("JenSoft");

        DemoTab c1 = new DemoTab("Dashboard");
        c1.setTabColor(Color.DARK_GRAY);
        ImageIcon icon1 = ImageResource.getInstance().createImageIcon("demo.png", "");
        c1.setTabIcon(icon1);

        cbar.addComandTab(c1, demo);
        

        c1.setSelected(true);

        

        masterPane.add(cbar, BorderLayout.CENTER);

        getContentPane().add(masterPane, BorderLayout.CENTER);
        setVisible(true);
    }

}
