/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.oil;

import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.plugin.gauge.RadialGaugePlugin;
import com.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import com.jensoft.core.plugin.translate.TranslatePlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;

public class GaugeOil2Demo extends View2D {

	private static final long serialVersionUID = 156889765687899L;

	public GaugeOil2Demo() {
		super(10);

		Window2D w2d = new Window2D.Linear.Identity();
		registerWindow2D(w2d);
		
		GaugeOil2 gauge = new GaugeOil2();
		RadialGaugePlugin gaugePlugin = new RadialGaugePlugin(gauge);
		w2d.registerPlugin(gaugePlugin);

		TranslatePlugin translate = new TranslatePlugin();
		translate.registerContext(new TranslateDefaultDeviceContext());
		w2d.registerPlugin(translate);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		final ViewFrameUI demoFrame = new ViewFrameUI(new GaugeOil2Demo());
	}

}
