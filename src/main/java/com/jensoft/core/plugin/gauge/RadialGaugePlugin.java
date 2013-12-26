/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge;

import java.awt.Graphics2D;

import com.jensoft.core.graphics.AlphaInterpolation;
import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.Fractional;
import com.jensoft.core.graphics.Interpolation;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import com.jensoft.core.plugin.gauge.core.GaugePartBuffer;
import com.jensoft.core.plugin.gauge.core.GaugeTextPath;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.bg.BackgroundGaugePainter;
import com.jensoft.core.plugin.gauge.core.glass.GlassGaugePainter;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>RadialGaugePlugin</code> incubator gauge plugin
 * 
 * @author sebastien janaud
 * 
 */
public class RadialGaugePlugin extends AbstractPlugin {

	private RadialGauge gauge;

	public RadialGaugePlugin(RadialGauge gauge) {
		this.gauge = gauge;
		setInterpolation(Interpolation.Bicubic);
		setAlphaInterpolation(AlphaInterpolation.Quality);
		setAntialiasing(Antialiasing.On);
		setFractionalMetrics(Fractional.On);
		setTextAntialising(TextAntialiasing.On);
		setDithering(Dithering.On);
	}
	
	
	/**
	 * draw given buffer in the given graphics context
	 * @param g2d
	 * @param buffer
	 */
	private void paintPart(Graphics2D g2d,GaugePartBuffer buffer){
		g2d.drawImage(buffer.getBuffer(),(int)buffer.getX(),(int)buffer.getY(),(int)buffer.getWidth(),(int)buffer.getHeight(),null);
	}

	@Override
	protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

		if (windowPart != WindowPart.Device) {
			return;
		}

		gauge.setWindow2D(getWindow2D());

		if (gauge.getEnvelop() != null) {
			gauge.getEnvelop().paintEnvelop(g2d, gauge);
		}

		if (gauge.getGaugeBackgrounds() != null) {
			for (BackgroundGaugePainter background : gauge.getGaugeBackgrounds()) {
				background.paintBackground(g2d, gauge);
			}
		}

	

		if (gauge.getEffects() != null) {
			for (GlassGaugePainter glass : gauge.getEffects()) {
				glass.paintGlass(g2d, gauge);
			}
		}

		for(GaugeMetricsPath path : gauge.getGaugeMetricsPath()){
			if(path.getPartBuffer() == null){
				path.setWindow2d(getWindow2D());
				path.resetPath();
				path.append(path.getPathBinder().bindPath(gauge));
				path.createPartBuffer(g2d);
			}
			paintPart(g2d, path.getPartBuffer());
		}
		
		for(GaugeTextPath path : gauge.getGaugeTextPaths()){
			if(path.getPartBuffer() == null){
				path.setPath(path.getPathBinder().bindPath(gauge));
				path.createPartBuffer(g2d);
			}
			paintPart(g2d, path.getPartBuffer());
		}
		
		for(GaugeMetricsPath path : gauge.getGaugeMetricsPath()){
			if(path.getGaugeNeedlePainter() != null){
				path.getGaugeNeedlePainter().paintNeedle(g2d, path);
			}
		}
	}

}
