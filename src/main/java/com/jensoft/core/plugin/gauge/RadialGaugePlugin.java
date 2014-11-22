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
import com.jensoft.core.plugin.gauge.core.GaugeBackground;
import com.jensoft.core.plugin.gauge.core.GaugeBody;
import com.jensoft.core.plugin.gauge.core.GaugeGlass;
import com.jensoft.core.plugin.gauge.core.GaugePart;
import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.projection.ProjectionEvent;
import com.jensoft.core.projection.ProjectionListener;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>RadialGaugePlugin</code> incubator gauge plugin
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class RadialGaugePlugin extends AbstractPlugin {

	private RadialGauge gauge;

	/**
	 * create radial gauge plugin
	 * @param gauge
	 */
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
	 * invalidate parts
	 */
	private void invalidateParts(){
		if(gauge.getEnvelop() != null){
			gauge.getEnvelop().invalidate();
		}
		
		for(GaugeGlass glass : gauge.getGlasses()){
			glass.invalidate();
		}
		for(GaugeBody body : gauge.getBodies()){
			body.invalidate();
		}
		for(GaugeBackground background : gauge.getBackgrounds()){
			background.invalidate();
		}
	}
	
	/**
	 * on window change or window resized, invalidate part buffer of gauges.
	 * <p>
	 * add a window listener and call {@link #invalidateParts()}
	 * <p>
	 */
	@Override
	public void onProjectionRegister() {
		super.onProjectionRegister();
		getProjection().addProjectionListener(new ProjectionListener() {
			
			@Override
			public void projectionUnlockActive(ProjectionEvent w2dEvent) {
			}
			
			@Override
			public void projectionResized(ProjectionEvent w2dEvent) {
				invalidateParts();
			}
			
			@Override
			public void projectionLockActive(ProjectionEvent w2dEvent) {
			}
			
			@Override
			public void projectionBoundChanged(ProjectionEvent w2dEvent) {
				invalidateParts();				
			}
		});
	}
	
	
	

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
	 */
	@Override
	protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {

		if (viewPart != ViewPart.Device) {
			return;
		}

		gauge.setWindow2D(getProjection());
		gauge.setHost(this);
		if (gauge.getEnvelop() != null) {
			gauge.getEnvelop().paintPart(g2d, gauge);
		}

		if (gauge.getBackgrounds() != null) {
			for (GaugePart background : gauge.getBackgrounds()) {
				background.paintPart(g2d, gauge);
			}
		}

		if (gauge.getGlasses() != null) {
			for (GaugePart glass : gauge.getGlasses()) {
				glass.paintPart(g2d, gauge);
			}
		}
		
		if (gauge.getBodies() != null) {
			for (GaugePart body : gauge.getBodies()) {
				body.paintPart(g2d, gauge);
			}
		}

	}

}
