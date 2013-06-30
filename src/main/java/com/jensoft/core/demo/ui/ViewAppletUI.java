/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.demo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.StringTokenizer;

import javax.jnlp.ClipboardService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JPanel;
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
public abstract class ViewAppletUI extends JApplet {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = -7872212203014369306L;

	/** JNLP clip board service */
	private ClipboardService cs = null;

	/**
	 * get the view demo class
	 * 
	 * @return view class
	 */
	public abstract Class getDemoClass();

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
			e.printStackTrace();
			System.err.println("Applet UI didn't successfully complete : "+e.getMessage());
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
		try {
			cs = (ClipboardService) ServiceManager.lookup("javax.jnlp.ClipboardService");
		} catch (UnavailableServiceException e) {
		}

		getContentPane().removeAll();
		getContentPane().setLayout(new BorderLayout());

		JPanel masterPane = new JPanel();
		masterPane.setBackground(Color.BLACK);

		masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		masterPane.setLayout(new BorderLayout());
		
		try {
			StringTokenizer tokenizer = new StringTokenizer(inset, ",");
			masterPane.setBorder(BorderFactory.createEmptyBorder(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken())));
		} catch (Throwable e) {
			masterPane.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		}

		DemoTabSet tabSet = new DemoTabSet();
		tabSet.setTitle("JenSoft");

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

		View2D view = null;
		try {
			view = (View2D) getDemoClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tabSet.addComandTab(demoTab, view);

		DemoTab appletUITab = new DemoTab("Applet UI");
		appletUITab.setTabColor(FilPalette.GREEN3);
		ImageIcon icon2 = ImageResource.getInstance().createImageIcon("source.png", "");
		appletUITab.setTabIcon(icon2);
		JavaSourcePane appletUISourcePane = new JavaSourcePane();
		tabSet.addComandTab(appletUITab, appletUISourcePane);
		appletUISourcePane.loadSource(this.getClass());

		DemoTab viewSourceTab = new DemoTab("View");
		viewSourceTab.setTabColor(JennyPalette.JENNY6);
		ImageIcon icon = ImageResource.getInstance().createImageIcon("source.png", "");
		viewSourceTab.setTabIcon(icon);
		JavaSourcePane viewSourcePane = new JavaSourcePane();
		tabSet.addComandTab(viewSourceTab, viewSourcePane);
		viewSourcePane.loadSource(view.getClass());

		demoTab.setSelected(true);

		masterPane.add(tabSet, BorderLayout.CENTER);

		getContentPane().add(masterPane, BorderLayout.CENTER);
		setVisible(true);
	}

}
