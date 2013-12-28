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
import com.jensoft.core.plugin.gauge.core.bg.GaugeBackground;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlassPainter;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2DEvent;
import com.jensoft.core.window.Window2DListener;
import com.jensoft.core.window.WindowPart;

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
	 * invalidate part buffer of gauges metrics paths, text paths and glasses
	 */
	private void invalidateParts(){
		for(GaugeMetricsPath path : gauge.getMetricsPaths()){
			path.setPartBuffer(null);
		}
		
		for(GaugeTextPath path : gauge.getTextPaths()){
			path.setPartBuffer(null);
		}
		
		for(GaugeGlassPainter glass : gauge.getGlasses()){
			glass.setPartBuffer(null);
		}
	}
	
	/**
	 * on window change or window resized, invalidate part buffer of gauges.
	 * <p>
	 * add a window listener and call {@link #invalidateParts()}
	 * <p>
	 */
	@Override
	public void onWindowRegister() {
		super.onWindowRegister();
		getWindow2D().addWindow2DListener(new Window2DListener() {
			
			@Override
			public void window2DUnlockActive(Window2DEvent w2dEvent) {
			}
			
			@Override
			public void window2DResized(Window2DEvent w2dEvent) {
				invalidateParts();
			}
			
			@Override
			public void window2DLockActive(Window2DEvent w2dEvent) {
			}
			
			@Override
			public void window2DBoundChanged(Window2DEvent w2dEvent) {
				invalidateParts();				
			}
		});
	}
	
	
	/**
	 * draw given buffer in the given graphics context
	 * @param g2d
	 * @param buffer
	 */
	private void paintPart(Graphics2D g2d,GaugePartBuffer buffer){
		g2d.drawImage(buffer.getBuffer(),(int)buffer.getX(),(int)buffer.getY(),(int)buffer.getWidth(),(int)buffer.getHeight(),null);
	}

	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
	 */
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
			for (GaugeBackground background : gauge.getGaugeBackgrounds()) {
				background.paintBackground(g2d, gauge);
			}
		}

	

		if (gauge.getGlasses() != null) {
			for (GaugeGlassPainter glass : gauge.getGlasses()) {
				glass.paintGlass(g2d, gauge);
			}
		}

		for(GaugeMetricsPath path : gauge.getMetricsPaths()){
			if(path.getPartBuffer() == null){
				path.setWindow2d(getWindow2D());
				path.resetPath();
				path.append(path.getPathBinder().bindPath(gauge));
				path.createPartBuffer(g2d);
			}
			paintPart(g2d, path.getPartBuffer());
		}
		
		for(GaugeTextPath path : gauge.getTextPaths()){
			if(path.getPartBuffer() == null){
				path.setPath(path.getPathBinder().bindPath(gauge));
				path.createPartBuffer(g2d);
			}
			paintPart(g2d, path.getPartBuffer());
		}
		
		for(GaugeMetricsPath path : gauge.getMetricsPaths()){
			if(path.getGaugeNeedlePainter() != null){
				path.getGaugeNeedlePainter().paintNeedle(g2d, path);
			}
		}
	}

}
