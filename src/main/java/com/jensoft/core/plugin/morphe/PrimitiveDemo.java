/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.morphe;

import java.awt.Color;

import com.jensoft.core.demo.nature.JenSoftAPIDemo;
import com.jensoft.core.demo.ui.ViewFrameUI;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.Axis;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin.MultiMultiplierMetrics;
import com.jensoft.core.plugin.minidevice.MiniDevicePlugin;
import com.jensoft.core.plugin.morphe.Primitive.PrimitiveNature;
import com.jensoft.core.plugin.translate.TranslatePlugin;
import com.jensoft.core.plugin.zoom.box.ZoomBoxPlugin;
import com.jensoft.core.plugin.zoom.objectif.ZoomObjectifPlugin;
import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

@JenSoftAPIDemo
public class PrimitiveDemo extends View2D {

	private static final long serialVersionUID = 156889765687899L;

	public PrimitiveDemo() {
		super();

		setPlaceHolderAxisSouth(80);
		setPlaceHolderAxisWest(120);
		setPlaceHolderAxisEast(120);

		setDeviceBackground(Color.BLACK);

		Window2D w2d = new Window2D.Linear(-3000, 3000, -2500, 2500);
		w2d.setName("primitive window");

		PrimitivePlugin primitiveLayout = new PrimitivePlugin();

		Ellipse circle = new Ellipse(1000, 1000, 400, 400);
		circle.setNature(PrimitiveNature.USER);

		primitiveLayout.registerPrimitive(circle);

		w2d.registerPlugin(primitiveLayout);

		registerWindow2D(w2d);

		MultiMultiplierMetrics miliWest = new AxisMetricsPlugin.MultiMultiplierMetrics(0, Axis.AxisWest);
		miliWest.setMajor(1000);
		miliWest.setMedian(500);
		miliWest.setMinor(100);
		w2d.registerPlugin(miliWest);

		MultiMultiplierMetrics miliSouth = new AxisMetricsPlugin.MultiMultiplierMetrics(0, Axis.AxisSouth);
		miliSouth.setMajor(1000);
		miliSouth.setMedian(500);
		miliSouth.setMinor(100);
		w2d.registerPlugin(miliSouth);

		ZoomBoxPlugin zoomTool = new ZoomBoxPlugin();
		w2d.registerPlugin(zoomTool);

		TranslatePlugin toolTranslate = new TranslatePlugin();
		w2d.registerPlugin(toolTranslate);

		MiniDevicePlugin toolMinidevice = new MiniDevicePlugin();
		w2d.registerPlugin(toolMinidevice);

		ZoomWheelPlugin zoomWheel = new ZoomWheelPlugin();
		w2d.registerPlugin(zoomWheel);

		ZoomObjectifPlugin objectif = new ZoomObjectifPlugin();
		w2d.registerPlugin(objectif);

		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		final ViewFrameUI demoFrame = new ViewFrameUI(new PrimitiveDemo());
		

	}

}
