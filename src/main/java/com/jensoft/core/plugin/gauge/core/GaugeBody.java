package com.jensoft.core.plugin.gauge.core;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class GaugeBody extends GaugePart {

	/** gauges metrics paths */
	private List<GaugeMetricsPath> gaugeMetricsPaths;

	/** gauges texts paths */
	private List<GaugeTextPath> gaugeTextPaths;

	public GaugeBody() {
		gaugeMetricsPaths = new ArrayList<GaugeMetricsPath>();
		gaugeTextPaths = new ArrayList<GaugeTextPath>();
	}

	/**
	 * register a gauge metrics path in this gauge
	 * 
	 * @param pathMetrics
	 *            path metrics to register
	 */
	public void registerGaugeMetricsPath(GaugeMetricsPath pathMetrics) {
		pathMetrics.setBody(this);
		this.gaugeMetricsPaths.add(pathMetrics);
	}

	/**
	 * get gauge registered path metrics list
	 * 
	 * @return path metrics list
	 */
	public List<GaugeMetricsPath> getMetricsPaths() {
		return gaugeMetricsPaths;
	}

	/**
	 * register gauge metrics path list to this gauge
	 * 
	 * @param gaugePathMetrics
	 *            the gauge path list to register
	 */
	public void setGaugeMetricsPath(List<GaugeMetricsPath> gaugePathMetrics) {
		for (GaugeMetricsPath path : gaugePathMetrics) {
			registerGaugeMetricsPath(path);
		}
	}

	/**
	 * register a gauge text path in this gauge
	 * 
	 * @param textPath
	 *            text path to register
	 */
	public void registerGaugeTextPath(GaugeTextPath textPath) {
		textPath.setBody(this);
		this.gaugeTextPaths.add(textPath);
	}

	/**
	 * get gauge text paths
	 * 
	 * @return gauge text paths
	 */
	public List<GaugeTextPath> getTextPaths() {
		return gaugeTextPaths;
	}

	/**
	 * register gauge text path list
	 * 
	 * @param gaugeTextPaths
	 *            text path list to register
	 */
	public void setGaugeTextPaths(List<GaugeTextPath> gaugeTextPaths) {
		for (GaugeTextPath path : gaugeTextPaths) {
			registerGaugeTextPath(path);
		}
	}
	
	/**
	 * draw given buffer in the given graphics context
	 * @param g2d
	 * @param buffer
	 */
	private void paintPart(Graphics2D g2d,GaugePartBuffer buffer){
		if(buffer != null && buffer.getBuffer() != null)
		g2d.drawImage(buffer.getBuffer(),(int)buffer.getX(),(int)buffer.getY(),(int)buffer.getWidth(),(int)buffer.getHeight(),null);
	}
	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.gauge.core.GaugePart#invalidate()
	 */
	@Override
	public void invalidate() {
		setPartBuffer(null);
		
		for (GaugeMetricsPath path : getMetricsPaths()) {
			path.setPartBuffer(null);
		}

		for (GaugeTextPath path : getTextPaths()) {
			path.setPartBuffer(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.gauge.core.GaugePart#paintPart(java.awt.Graphics2D
	 * , com.jensoft.core.plugin.gauge.core.RadialGauge)
	 */
	@Override
	public final void paintPart(Graphics2D g2d, RadialGauge radialGauge) {
		for(GaugeMetricsPath path : getMetricsPaths()){
			if(path.getPartBuffer() == null){
				path.setWindow2d(getGauge().getWindow2D());
				path.resetPath();
				Shape s = path.getPathBinder().bindPath(radialGauge);
				if(s != null){
					path.append(path.getPathBinder().bindPath(radialGauge));
					path.createPartBuffer(g2d);	
				}
			}
			paintPart(g2d, path.getPartBuffer());
			
			//DEBUG PATH BINDER
			if(path.getPathBinder().isDebug()){
				path.getPathBinder().debug(g2d, radialGauge);
			}
		}
		
		for(GaugeTextPath path : getTextPaths()){
			if(path.getPartBuffer() == null){
				path.setPath(path.getPathBinder().bindPath(radialGauge));
				path.createPartBuffer(g2d);
			}
			paintPart(g2d, path.getPartBuffer());
		}
		
		for(GaugeMetricsPath path : getMetricsPaths()){
			if(path.getGaugeNeedlePainter() != null){
				path.getGaugeNeedlePainter().paintNeedle(g2d, path);
			}
		}
	}

}
