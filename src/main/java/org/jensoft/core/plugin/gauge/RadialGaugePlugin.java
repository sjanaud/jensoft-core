/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge;

import java.awt.Graphics2D;

import org.jensoft.core.graphics.AlphaInterpolation;
import org.jensoft.core.graphics.Antialiasing;
import org.jensoft.core.graphics.Dithering;
import org.jensoft.core.graphics.Fractional;
import org.jensoft.core.graphics.Interpolation;
import org.jensoft.core.graphics.TextAntialiasing;
import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.plugin.gauge.core.GaugeBackground;
import org.jensoft.core.plugin.gauge.core.GaugeBody;
import org.jensoft.core.plugin.gauge.core.GaugeGlass;
import org.jensoft.core.plugin.gauge.core.GaugePart;
import org.jensoft.core.plugin.gauge.core.RadialGauge;
import org.jensoft.core.projection.ProjectionEvent;
import org.jensoft.core.projection.ProjectionListener;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

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
	 * on projection change or projection resized, invalidate part buffer of gauges.
	 * <p>
	 * add a projection listener and call {@link #invalidateParts()}
	 * <p>
	 */
	@Override
	public void onProjectionRegister() {
		super.onProjectionRegister();
		getProjection().addProjectionListener(new ProjectionListener() {
			
			/* (non-Javadoc)
			 * @see org.jensoft.core.projection.ProjectionListener#projectionUnlockActive(org.jensoft.core.projection.ProjectionEvent)
			 */
			@Override
			public void projectionUnlockActive(ProjectionEvent w2dEvent) {
			}
			
			/* (non-Javadoc)
			 * @see org.jensoft.core.projection.ProjectionListener#projectionResized(org.jensoft.core.projection.ProjectionEvent)
			 */
			@Override
			public void projectionResized(ProjectionEvent w2dEvent) {
				invalidateParts();
			}
			
			/* (non-Javadoc)
			 * @see org.jensoft.core.projection.ProjectionListener#projectionLockActive(org.jensoft.core.projection.ProjectionEvent)
			 */
			@Override
			public void projectionLockActive(ProjectionEvent w2dEvent) {
			}
			
			/* (non-Javadoc)
			 * @see org.jensoft.core.projection.ProjectionListener#projectionBoundChanged(org.jensoft.core.projection.ProjectionEvent)
			 */
			@Override
			public void projectionBoundChanged(ProjectionEvent w2dEvent) {
				invalidateParts();				
			}
		});
	}
	
	
	

	/* (non-Javadoc)
	 * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
	 */
	@Override
	protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {

		if (viewPart != ViewPart.Device) {
			return;
		}

		gauge.setProjection(getProjection());
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
