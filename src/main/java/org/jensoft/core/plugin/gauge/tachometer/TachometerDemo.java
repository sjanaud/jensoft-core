/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge.tachometer;

import org.jensoft.core.catalog.ui.ViewFrameUI;
import org.jensoft.core.plugin.gauge.RadialGaugePlugin;
import org.jensoft.core.plugin.translate.TranslateDefaultDeviceContext;
import org.jensoft.core.plugin.translate.TranslatePlugin;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.view.View;

public class TachometerDemo extends View {

	private static final long serialVersionUID = 156889765687899L;

	public TachometerDemo() {
		super();

		Projection w2d = new Projection.Linear.Identity();
		registerProjection(w2d);
		
		Tachometer gauge = new Tachometer();
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
		final ViewFrameUI demoFrame = new ViewFrameUI(new TachometerDemo());
	}

}
