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
import com.jensoft.core.catalog.source.JavaSourcePane;
import com.jensoft.core.catalog.source.X2DSourcePane;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.view.View2D;
import com.jensoft.core.x2d.X2D;

/**
 * <code>ViewDemoFrameUI</code>
 * 
 * @author sebastien janaud
 */
public class ViewFrameUI extends JFrame {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 416596322068922672L;

	/** view 2D */
	private View2D view;

	/**
	 * Create and Show frame UI with the given view
	 * 
	 * @param view
	 */
	public ViewFrameUI(View2D view) {
		this.view = view;
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
			View2D v = (View2D)viewClass.newInstance();
			ViewFrameUI ui = new ViewFrameUI(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * show the given demo in the demo frame
	 * 
	 */
	private void create() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}

		ImageIcon iconFrame = ImageResource.getInstance().createImageIcon("jensoft.png", "");
		setIconImage(iconFrame.getImage());
		setTitle("JenSoft API");
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
		tabSet.addComandTab(demoTab, view);

//		DemoTab frameUITab = new DemoTab("Frame UI");
//		frameUITab.setTabColor(FilPalette.GREEN3);
//		ImageIcon icon2 = ImageResource.getInstance().createImageIcon("source.png", "");
//		frameUITab.setTabIcon(icon2);
//		JavaSourcePane frameUISourcePane = new JavaSourcePane();
//		tabSet.addComandTab(frameUITab, frameUISourcePane);
//		frameUISourcePane.loadSource(this.getClass());

		DemoTab viewSourceTab = new DemoTab("View");
		viewSourceTab.setTabColor(JennyPalette.JENNY6);
		ImageIcon icon = ImageResource.getInstance().createImageIcon("source.png", "");
		viewSourceTab.setTabIcon(icon);
		JavaSourcePane viewSource = new JavaSourcePane();
		tabSet.addComandTab(viewSourceTab, viewSource);
		viewSource.loadSource(view.getClass());
		
		
		DemoTab x2dSourceTab = new DemoTab("X2D");
		x2dSourceTab.setTabColor(JennyPalette.JENNY6);
		ImageIcon icon3 = ImageResource.getInstance().createImageIcon("source.png", "");
		viewSourceTab.setTabIcon(icon3);
		X2DSourcePane x2dSourcePane = new X2DSourcePane();
		tabSet.addComandTab(x2dSourceTab, x2dSourcePane);		
		X2D x2d = new X2D();
		try {
			x2d.registerView(view);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		x2dSourcePane.loadX2DSource(x2d);
		
		

		demoTab.setSelected(true);

		masterPane.add(tabSet, BorderLayout.CENTER);

		getContentPane().add(masterPane, BorderLayout.CENTER);
		setVisible(true);
	}

}
