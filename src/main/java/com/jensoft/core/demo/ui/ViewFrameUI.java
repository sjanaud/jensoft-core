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
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jensoft.core.demo.component.DemoTab;
import com.jensoft.core.demo.component.DemoTabSet;
import com.jensoft.core.demo.source.JavaSourcePane;
import com.jensoft.core.palette.FilPalette;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.view.View2D;

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

	/**
	 * show the given demo in the demo frame
	 * 
	 * @param demo
	 *            the demo to show
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

		DemoTab frameUITab = new DemoTab("UI");
		frameUITab.setTabColor(FilPalette.GREEN3);
		ImageIcon icon2 = ImageResource.getInstance().createImageIcon("source.png", "");
		frameUITab.setTabIcon(icon2);
		JavaSourcePane frameUISourcePane = new JavaSourcePane();
		tabSet.addComandTab(frameUITab, frameUISourcePane);
		frameUISourcePane.loadSource(this.getClass());

		DemoTab viewSourceTab = new DemoTab(view.getClass().getSimpleName());
		viewSourceTab.setTabColor(JennyPalette.JENNY6);
		ImageIcon icon = ImageResource.getInstance().createImageIcon("source.png", "");
		viewSourceTab.setTabIcon(icon);
		JavaSourcePane viewSource = new JavaSourcePane();
		tabSet.addComandTab(viewSourceTab, viewSource);
		viewSource.loadSource(view.getClass());

		demoTab.setSelected(true);

		masterPane.add(tabSet, BorderLayout.CENTER);

		getContentPane().add(masterPane, BorderLayout.CENTER);
		setVisible(true);
	}

}
