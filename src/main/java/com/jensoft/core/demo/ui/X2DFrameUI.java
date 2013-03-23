/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.demo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.jensoft.core.demo.component.DemoTab;
import com.jensoft.core.demo.component.DemoTabSet;
import com.jensoft.core.demo.source.JavaSourcePane;
import com.jensoft.core.demo.source.X2DSourcePane;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.view.View2D;
import com.jensoft.core.x2d.X2D;
import com.jensoft.core.x2d.X2DException;

/**
 * <code>X2DFrameUI</code><br>
 * 
 * @author sebastien janaud
 */
public abstract class X2DFrameUI extends JFrame {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = -7404344591674106627L;

	/** x2d resource */
	private String x2dResourcePath;

	public X2DFrameUI(String x2dResourcePath) {
		this.x2dResourcePath = x2dResourcePath;
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
	 * create x2d frame UI
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
		tabSet.setTitle("JenSoft - API");

		DemoTab demoTab = new DemoTab("X2D");

		demoTab.setTabColor(Color.DARK_GRAY);
		ImageIcon icon1 = ImageResource.getInstance().createImageIcon("demo.png", "");
		demoTab.setTabIcon(icon1);

		DemoTab sourceTab = new DemoTab("X2D");
		sourceTab.setTabColor(JennyPalette.JENNY6);
		ImageIcon icon = ImageResource.getInstance().createImageIcon("source.png", "");
		sourceTab.setTabIcon(icon);

		DemoTab uisourceTab = new DemoTab("Frame UI");
		uisourceTab.setTabColor(RosePalette.LEMONPEEL.brighter());
		ImageIcon icon2 = ImageResource.getInstance().createImageIcon("source.png", "");
		uisourceTab.setTabIcon(icon2);

		X2D x2d = null;
		try {
			InputStream inputStream = getClass().getResourceAsStream(x2dResourcePath);
			x2d = new X2D();
			x2d.registerX2D(inputStream);
		} catch (X2DException e) {
			e.printStackTrace();
		}
		View2D view2d = x2d.getView2D();

		tabSet.addComandTab(demoTab, view2d);
		final X2D fx2d = x2d;

		JavaSourcePane uiSourcePane = new JavaSourcePane();
		tabSet.addComandTab(uisourceTab, uiSourcePane);
		X2DSourcePane x2dSourcePane = new X2DSourcePane();
		tabSet.addComandTab(sourceTab, x2dSourcePane);		

		uiSourcePane.loadSource(this.getClass());
		x2dSourcePane.loadX2DSource(fx2d);

		demoTab.setSelected(true);
		masterPane.add(tabSet, BorderLayout.CENTER);

		getContentPane().add(masterPane, BorderLayout.CENTER);
		setVisible(true);
	}

	/**
	 * get x2d file from class path
	 * 
	 * @param x2dFileName
	 * @return x2d
	 * @throws IOException
	 */
	protected X2D getX2DFromClasspath(String x2dFileName) throws X2DException {
		InputStream inputStream = getClass().getResourceAsStream(x2dFileName);
		X2D x2d = new X2D();
		x2d.registerX2D(inputStream);
		return x2d;
	}

}
