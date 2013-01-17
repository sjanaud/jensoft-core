/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.desktop.viewsbase;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.jensoft.core.palette.PetalPalette;

public class View2Demo extends JFrame {

    public View2Demo() {

        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel masterPane = new JPanel();
        masterPane.setOpaque(false);

        masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        masterPane.setLayout(new BorderLayout());

        // FunkyView fv = new FunkyView();
        // fv.setOpaque(true);
        // fv.setBackground(PetalPalette.PETAL1_HC);

        View2Group cbar = new View2Group();

        View2 c1 = new View2("Demo");
        c1.setTabColor(PetalPalette.PETAL4_HC);
        ImageIcon icon1 = ImageResource.getInstance().createImageIcon(
                                                                      "constructors.png", "");
        c1.setTabIcon(icon1);
        View2 c2 = new View2("Source");
        c2.setTabColor(PetalPalette.PETAL2_HC);
        // /c2.setSelected(true);
        ImageIcon icon = ImageResource.getInstance().createImageIcon(
                                                                     "source.png", "");
        c2.setTabIcon(icon);
        View2 c3 = new View2("About");
        c3.setTabColor(PetalPalette.PETAL8_HC);
        ImageIcon icon3 = ImageResource.getInstance().createImageIcon(
                                                                      "bubble_help_16.png", "");
        c3.setTabIcon(icon3);
        // ComandGroup c4 =new ComandGroup("Help");
        // c4.setTabColor(PetalPalette.PETAL4_HC);
        // ImageIcon icon4 =
        // ImageResource.getInstance().createImageIcon("partition.png", "");
        // c4.setTabIcon(icon4);

        SScrollPane scroll = new SScrollPane();
        cbar.addView(c1, null);
        cbar.addView(c2, scroll);
        cbar.addView(c3, null);

        cbar.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        // panel.setBackground(Color.DARK_GRAY);

        scroll.getArea().setOpaque(false);
        scroll.getArea().setText("hello source e! ");
        scroll.getArea().setFont(new Font("tahoma", Font.PLAIN, 10));
        // cbar.add(area,BorderLayout.CENTER);
        // cbar.addComandTab(c4);

        System.out.println(cbar.getInsets());

        // cbar.setLayout(new BorderLayout());

        JTabbedPane tab = new JTabbedPane();
        tab.addTab("ytr", new JPanel());

        masterPane.add(cbar, BorderLayout.CENTER);

        getContentPane().add(masterPane, BorderLayout.CENTER);

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        View2Demo demo = new View2Demo();
        demo.pack();
        demo.setSize(500, 400);
        demo.setVisible(true);
    }

}
