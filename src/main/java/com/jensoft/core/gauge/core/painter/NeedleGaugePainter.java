/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.core.painter;

import java.awt.Graphics2D;

import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;

public abstract class NeedleGaugePainter extends AbstractGaugePainter {

	
	private GeneralMetricsPath pathManager;
	private int curentValue;
	
	
    public int getCurentValue() {
		return curentValue;
	}

	public void setCurentValue(int curentValue) {
		this.curentValue = curentValue;
	}

	public GeneralMetricsPath getPathManager() {
		return pathManager;
	}

	public void setPathManager(GeneralMetricsPath pathManager) {
		this.pathManager = pathManager;
	}

	public abstract void paintNeedle(Graphics2D g2d, RadialGauge radialGauge);

    @Override
    public final void paintGauge(Graphics2D g2d, RadialGauge radialGauge) {
        paintNeedle(g2d, radialGauge);

    }

}
