/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.demo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jensoft.core.demo.component.DemoTab;
import com.jensoft.core.demo.component.DemoTabSet;
import com.jensoft.core.view.View2D;

public abstract class DashboardAppletUI extends JApplet {

	private static final long serialVersionUID = 156889765687899L;

	
	private String inset;
	private String drawOutline;
	private String cornerRadius;
	
	/**
	 * in Applet UI
	 */
	public void init() {
		inset = getParameter("inset");
		drawOutline = getParameter("drawOutline");
		cornerRadius = getParameter("cornerRadius");
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
	
	/**
	 * get the view demo class
	 * 
	 * @return view class
	 */
	public abstract Class getDemoClass();
	

	private void create() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());

		JPanel masterPane = new JPanel();
		masterPane.setBackground(Color.WHITE);

		try {
			StringTokenizer tokenizer = new StringTokenizer(inset,",");
			masterPane.setBorder(BorderFactory.createEmptyBorder(Integer.parseInt(tokenizer.nextToken()),Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
		} catch (Throwable e) {			
			masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		}
		
		
		masterPane.setLayout(new BorderLayout());

		DemoTabSet tabSet = new DemoTabSet();
		tabSet.setTitle("JenSoft - API");
		if(cornerRadius != null){
			tabSet.setCornerRadius(Integer.parseInt(cornerRadius));
		}
		if(drawOutline != null){
			tabSet.setDrawOutline(Boolean.parseBoolean(drawOutline));
		}

		DemoTabSet cbar = new DemoTabSet();

		cbar.setTitle("JenSoft");

		DemoTab c1 = new DemoTab("Dashboard");
		c1.setTabColor(Color.DARK_GRAY);
		ImageIcon icon1 = ImageResource.getInstance().createImageIcon("demo.png", "");
		c1.setTabIcon(icon1);

		Dashboard dashboard = null;
		try {
			dashboard = (Dashboard) getDemoClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		cbar.addComandTab(c1, dashboard);

		c1.setSelected(true);

		masterPane.add(cbar, BorderLayout.CENTER);

		getContentPane().add(masterPane, BorderLayout.CENTER);
		setVisible(true);
	}

}
