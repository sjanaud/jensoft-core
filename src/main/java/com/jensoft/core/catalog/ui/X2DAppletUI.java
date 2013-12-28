/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.catalog.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.jensoft.core.catalog.component.DemoTab;
import com.jensoft.core.catalog.component.DemoTabSet;
import com.jensoft.core.catalog.source.JavaSourcePane;
import com.jensoft.core.catalog.source.X2DSourcePane;
import com.jensoft.core.palette.JennyPalette;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.view.View2D;
import com.jensoft.core.x2d.X2D;
import com.jensoft.core.x2d.X2DException;

/**
 * <code>X2DAppletUI</code>
 * 
 * @author sebastien janaud
 */
public abstract class X2DAppletUI extends JApplet {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 8128875887813664194L;

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
			System.err.println("Applet UI didn't successfully complete, " + e.getMessage());
		}
	}

	public abstract String getX2DResourcePath() throws X2DException;

	/**
	 * create x2d Applet UI
	 * 
	 */
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
			StringTokenizer tokenizer = new StringTokenizer(inset, ",");
			masterPane.setBorder(BorderFactory.createEmptyBorder(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
		} catch (Throwable e) {
			masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		}
		masterPane.setLayout(new BorderLayout());

		DemoTabSet tabSet = new DemoTabSet();
		tabSet.setTitle("JenSoft - API");
		if (cornerRadius != null) {
			tabSet.setCornerRadius(Integer.parseInt(cornerRadius));
		}
		if (drawOutline != null) {
			tabSet.setDrawOutline(Boolean.parseBoolean(drawOutline));
		}
		DemoTab demoTab = new DemoTab("Demo");

		demoTab.setTabColor(Color.DARK_GRAY);
		ImageIcon icon1 = ImageResource.getInstance().createImageIcon("demo.png", "");
		demoTab.setTabIcon(icon1);

		DemoTab sourceTab = new DemoTab("X2D");
		sourceTab.setTabColor(JennyPalette.JENNY6);
		ImageIcon icon = ImageResource.getInstance().createImageIcon("source.png", "");
		sourceTab.setTabIcon(icon);

		DemoTab uisourceTab = new DemoTab("Applet UI");
		uisourceTab.setTabColor(RosePalette.EMERALD);
		ImageIcon icon2 = ImageResource.getInstance().createImageIcon("source.png", "");
		uisourceTab.setTabIcon(icon2);

//		X2D x2d = null;
//		try {
//			InputStream inputStream = getClass().getResourceAsStream(getX2DResourcePath());
//			x2d = new X2D();
//			x2d.registerX2D(inputStream);
//		} catch (X2DException e) {
//			e.printStackTrace();
//		}
//		View2D view2d = x2d.getView2D();
//
//		tabSet.addComandTab(demoTab, view2d);
//		final X2D fx2d = x2d;

		JavaSourcePane uiSourcePane = new JavaSourcePane();
		tabSet.addComandTab(uisourceTab, uiSourcePane);
		X2DSourcePane x2dSourcePane = new X2DSourcePane();
		tabSet.addComandTab(sourceTab, x2dSourcePane);
		

		uiSourcePane.loadSource(this.getClass());
		//x2dSourcePane.loadX2DSource(fx2d);

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
