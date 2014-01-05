/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.catalog.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jensoft.core.catalog.component.DemoTab;
import com.jensoft.core.catalog.component.DemoTabSet;
import com.jensoft.core.catalog.nature.JenSoftDashboard;
import com.jensoft.core.catalog.source.JavaSourcePane;
import com.jensoft.core.palette.FilPalette;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.view.View2D;

/**
 * <code>DashboardFrameUI</code>
 * 
 * @author sebastien janaud
 * 
 */
public class DashboardFrameUI extends JFrame {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 984563357638775171L;

	/** the dash board */
	private Dashboard dashboard;

	public DashboardFrameUI(Dashboard demo) {
		this.dashboard = demo;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				create();
			}
		});
		pack();
		setSize(1024, 700);
	}
	
	public static void main(String[] args) {
		System.out.println("FRAME UI ARGS : "+args);
		try {
			Class viewClass = Class.forName(args[0]);
			Dashboard d = (Dashboard)viewClass.newInstance();
			DashboardFrameUI ui = new DashboardFrameUI(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void create() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		ImageIcon iconFrame = ImageResource.getInstance().createImageIcon("jensoft.png", "");
		setIconImage(iconFrame.getImage());
		setTitle("JenSoft Demo");
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel masterPane = new JPanel();
		masterPane.setBackground(Color.BLACK);

		masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		masterPane.setLayout(new BorderLayout());

		DemoTabSet tabSet = new DemoTabSet();
		tabSet.setTitle("JenSoft");

		DemoTab demoTab = new DemoTab("Demo");
		demoTab.setTabColor(Color.DARK_GRAY);
		ImageIcon icon1 = ImageResource.getInstance().createImageIcon("demo.png", "");
		demoTab.setTabIcon(icon1);
		tabSet.addComandTab(demoTab, dashboard);

//		DemoTab uiTab = new DemoTab("Frame UI");
//		uiTab.setTabColor(FilPalette.GREEN3);
//		ImageIcon icon2 = ImageResource.getInstance().createImageIcon("source.png", "");
//		uiTab.setTabIcon(icon2);
//		JavaSourcePane uiSourcePane = new JavaSourcePane();
//		tabSet.addComandTab(uiTab, uiSourcePane);
//		uiSourcePane.loadSource(this.getClass());

		DemoTab dashboardTab = new DemoTab("Dashboard");
		dashboardTab.setTabColor(JennyPalette.JENNY6);
		ImageIcon icon = ImageResource.getInstance().createImageIcon("source.png", "");
		dashboardTab.setTabIcon(icon);
		JavaSourcePane dashboardSourcePane = new JavaSourcePane();
		tabSet.addComandTab(dashboardTab, dashboardSourcePane);
		dashboardSourcePane.loadSource(dashboard.getClass());

		if (dashboard.getClass().isAnnotationPresent(JenSoftDashboard.class)) {
			JenSoftDashboard dashboardAnnotation = dashboard.getClass().getAnnotation(JenSoftDashboard.class);
			Class[] views = dashboardAnnotation.views();
			for (int i = 0; i < views.length; i++) {
				if (View2D.class.isAssignableFrom(views[i])) {
					DemoTab viewContributorTab = new DemoTab(views[i].getSimpleName());
					viewContributorTab.setTabColor(JennyPalette.JENNY6);
					ImageIcon vIcon = ImageResource.getInstance().createImageIcon("source.png", "");
					viewContributorTab.setTabIcon(vIcon);
					JavaSourcePane viewSourcePane = new JavaSourcePane();
					tabSet.addComandTab(viewContributorTab, viewSourcePane);
					viewSourcePane.loadSource(views[i]);
				}
			}
		}

		demoTab.setSelected(true);

		masterPane.add(tabSet, BorderLayout.CENTER);

		getContentPane().add(masterPane, BorderLayout.CENTER);
		setVisible(true);
	}

}
