/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.catalog.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.jensoft.core.catalog.component.DemoTab;
import com.jensoft.core.catalog.component.DemoTabSet;
import com.jensoft.core.catalog.nature.JenSoftDashboard;
import com.jensoft.core.catalog.source.JavaSourcePane;
import com.jensoft.core.palette.color.JennyPalette;
import com.jensoft.core.view.View;

/**
 * <code>DashboardAppletUI</code>
 * 
 * @author sebastien janaud
 * 
 */
public class DashboardAppletUI extends JApplet {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = -4394607925193376209L;

	private String inset;
	private String drawOutline;
	private String cornerRadius;
	private String className;

	/**
	 * in Applet UI
	 */
	public void init() {
		inset = getParameter("inset");
		drawOutline = getParameter("drawOutline");
		cornerRadius = getParameter("cornerRadius");
		className = getParameter("dashboardName");
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					create();
				}
			});
		} catch (Exception e) {
			System.err.println("Applet UI didn't successfully complete");
		}
	}

	

	private void create() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		ImageIcon iconFrame = ImageResource.getInstance().createImageIcon("jensoft.png", "");
		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());

		JPanel masterPane = new JPanel();
		masterPane.setBackground(Color.WHITE);
		masterPane.setLayout(new BorderLayout());
		try {
			StringTokenizer tokenizer = new StringTokenizer(inset, ",");
			masterPane.setBorder(BorderFactory.createEmptyBorder(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
		} catch (Throwable e) {
			masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		}

		

		DemoTabSet tabSet = new DemoTabSet();

		tabSet.setTitle("JenSoft - API");
		if (cornerRadius != null) {
			tabSet.setCornerRadius(Integer.parseInt(cornerRadius));
		}
		if (drawOutline != null) {
			tabSet.setDrawOutline(Boolean.parseBoolean(drawOutline));
		}

		tabSet.setTitle("JenSoft");

		DemoTab demoTab = new DemoTab("Dashboard");
		demoTab.setTabColor(Color.DARK_GRAY);
		ImageIcon icon1 = ImageResource.getInstance().createImageIcon("demo.png", "");
		demoTab.setTabIcon(icon1);

		Dashboard dashboard = null;
		try {
			Class dashboardClass = Class.forName(className);
			dashboard = (Dashboard)dashboardClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		tabSet.addComandTab(demoTab, dashboard);

//		DemoTab uiTab = new DemoTab("Applet UI");
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
				if (View.class.isAssignableFrom(views[i])) {
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
