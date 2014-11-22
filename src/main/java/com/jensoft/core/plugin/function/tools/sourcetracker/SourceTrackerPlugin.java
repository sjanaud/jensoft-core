/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.tools.sourcetracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricFill;
import com.jensoft.core.glyphmetrics.painter.GlyphMetricMarkerPainter;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.DefaultMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.palette.Spectral;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.function.MetricsPathFunction;
import com.jensoft.core.plugin.function.source.FunctionNature;
import com.jensoft.core.plugin.function.source.SourceFunction;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>SourceTrackerPlugin</code> takes the responsibility to draw curve label
 * for the current mouse point coordinate.
 * 
 * @see MetricsPathFunction
 * @see AbstractPlugin
 * @author Sebastien Janaud
 */
public class SourceTrackerPlugin extends AbstractPlugin implements AbstractPlugin.OnMoveListener, AbstractPlugin.OnPressListener, AbstractPlugin.OnDragListener, AbstractPlugin.OnReleaseListener {

	/** the tracked source */
	private SourceFunction trackedSource;

	/** tracked point in device projection system */
	private Point2D currentTrackDevice;

	/** tracked point in user projection system */
	private Point2D currentTrackUser;

	/** curve metrics path */
	private MetricsPathFunction metricsPathFunction;

	/** registered sources */
	private List<SourceFunction> sources;

	/** listeners */
	private EventListenerList trackerListenerList;

	/** default style position */
	private StylePosition defaultStylePosition = StylePosition.Default;

	/** label divergence from path */
	private int divergence = 20;

	/** default glyph metrics fill */
	private GlyphMetricFill glyphMetricsFill = new GlyphFill(Spectral.SPECTRAL_RED, Color.DARK_GRAY);

	/** default marker */
	private GlyphMetricMarkerPainter glyphMarker = new DefaultMarker(NanoChromatique.RED);

	/** default glyph font */
	private Font defaultGlyphFont = InputFonts.getFont(InputFonts.NEUROPOL, 12);

	/**
	 * create source tracker
	 * 
	 */
	public SourceTrackerPlugin() {
		sources = new ArrayList<SourceFunction>();
		metricsPathFunction = new MetricsPathFunction();
		trackerListenerList = new EventListenerList();
		setOnMoveListener(this);
		setOnPressListener(this);
		setOnDragListener(this);
		setOnReleaseListener(this);
		setSelectable(true);
		setPriority(10000);
	}

	/**
	 * add source tracker listener
	 * 
	 * @param listener
	 */
	public void addSourceTrackerListener(SourceTrackerListener listener) {
		trackerListenerList.add(SourceTrackerListener.class, listener);
	}

	/**
	 * remove source tracker listener
	 * 
	 * @param listener
	 */
	public void removeSourceTrackerListener(SourceTrackerListener listener) {
		trackerListenerList.remove(SourceTrackerListener.class, listener);
	}

	/**
	 * fire source being tracked started
	 */
	public void fireSourceTracked() {
		Object[] listeners = trackerListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == SourceTrackerListener.class) {
					((SourceTrackerListener) listeners[i + 1]).sourceTracked(new SourceTrackerEvent(trackedSource));
				}
			}
		}
	}

	/**
	 * fire source registered
	 * 
	 * @param source
	 *            the source that has just been registered
	 */
	public void fireSourceRegistered(SourceFunction source) {
		Object[] listeners = trackerListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == SourceTrackerListener.class) {
					((SourceTrackerListener) listeners[i + 1]).sourceRegistered(new SourceTrackerEvent(source));
				}
			}
		}
	}

	/**
	 * fire source current track
	 * 
	 */
	public void fireCurrentTrack() {
		Object[] listeners = trackerListenerList.getListenerList();
		synchronized (listeners) {
			for (int i = 0; i < listeners.length; i += 2) {
				if (listeners[i] == SourceTrackerListener.class) {
					((SourceTrackerListener) listeners[i + 1]).currentTrack(new SourceTrackerEvent(trackedSource));
				}
			}
		}
	}

	/**
	 * @return the sources
	 */
	public List<SourceFunction> getSources() {
		return sources;
	}

	/**
	 * @param series
	 *            the series to set
	 */
	public void setSources(List<SourceFunction> series) {
		this.sources = series;
	}

	/**
	 * @return the tracked source
	 */
	public SourceFunction getTrackedSource() {
		return trackedSource;
	}

	/**
	 * register source in this tracker
	 * 
	 * @param source
	 *            the source to register
	 */
	public void registerSourceFunction(SourceFunction source) {
		if (!sources.contains(source)) {
			sources.add(source);
			fireSourceRegistered(source);
		}
	}

	/**
	 * track the given source, register source if i does not have been
	 * registered in curve metrics path
	 * 
	 * @param source
	 *            the source to track
	 */
	public void trackSource(SourceFunction source) {
		registerSourceFunction(source);
		trackedSource = source;
		metricsPathFunction.setSource(trackedSource);
		metricsPathFunction.setSolveGeometryRequest(true);
		fireSourceTracked();
	}

	/**
	 * get a lock/unlock action
	 * 
	 * @return lock unlock action
	 */
	public SourceTrackerLockUnlockAction getSerieTrackerLockUnlockAction() {
		return new SourceTrackerLockUnlockAction();
	}

	/**
	 * translate select action
	 */
	class SourceTrackerLockUnlockAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					if (isLockSelected()) {
						unlockSelected();
					} else {
						lockSelected();
					}
				}
			});
		}

	}

	/**
	 * add metric glyph label
	 * 
	 * @param metric
	 *            the glyph metric to add
	 */
	private void addMetrics(GlyphMetric metric) {

		if (trackedSource == null) {
			return;
		}
		if (trackedSource.getNature() == FunctionNature.XFunction) {
			metricsPathFunction.addMetrics(metric);
		} else {
			metricsPathFunction.addMetrics(metric);
		}

	}

	/**
	 * @return the glyphMetricsFill
	 */
	public GlyphMetricFill getGlyphMetricsFill() {
		return glyphMetricsFill;
	}

	/**
	 * @param glyphMetricsFill
	 *            the glyphMetricsFill to set
	 */
	public void setGlyphMetricsFill(GlyphMetricFill glyphMetricsFill) {
		this.glyphMetricsFill = glyphMetricsFill;
	}

	/**
	 * @return the glyphMarker
	 */
	public GlyphMetricMarkerPainter getGlyphMarker() {
		return glyphMarker;
	}

	/**
	 * @param glyphMarker
	 *            the glyphMarker to set
	 */
	public void setGlyphMarker(GlyphMetricMarkerPainter glyphMarker) {
		this.glyphMarker = glyphMarker;
	}

	/**
	 * remove all glyphs
	 */
	private void clearMetrics() {
		metricsPathFunction.clearMetrics();
	}

	/**
	 * return the current tracked point in the device projection system
	 * 
	 * @return the currentTrackDevice
	 */
	public Point2D getCurrentTrackDevice() {
		return currentTrackDevice;
	}

	/**
	 * return the current tracked point in the user projection system
	 * 
	 * @return the currentTrackUser
	 */
	public Point2D getCurrentTrackUser() {
		return currentTrackUser;
	}

	/**
	 * process the metrics
	 * <p>
	 * process the related metrics for the specified device mouse point
	 * coordinate into a point in the user window projection and append it to
	 * the curve metrics path.
	 * </p>
	 * 
	 * @param me
	 *            the mouse event to transform in user metrics.
	 */
	private void processMetrics(MouseEvent me) {

		metricsPathFunction.setSolveGeometryRequest(true);
		int currentX = me.getX();
		int currentY = me.getY();
		Projection w2d = getProjection();

		currentTrackDevice = new Point2D.Double(currentX, currentY);
		currentTrackUser = w2d.pixelToUser(currentTrackDevice);

		clearMetrics();
		GlyphMetric metric = null;
		if (trackedSource.getNature() == FunctionNature.XFunction) {
			Point2D result = trackedSource.evaluate(currentTrackUser.getX());
			if (result != null) {
				metric = new GlyphMetric();
				metric.setValue(currentTrackUser.getX());
				metric.setStylePosition(defaultStylePosition);
				metric.setMetricsNature(GlyphMetricsNature.Median);
				metric.setMetricsLabel(result.getY() + "");
				metric.setDivergence(divergence);
				metric.setGlyphMetricFill(glyphMetricsFill);
				metric.setGlyphMetricMarkerPainter(glyphMarker);
				metric.setFont(defaultGlyphFont);
			}
		} else {
			Point2D result = trackedSource.evaluate(currentTrackUser.getY());
			if (result != null) {
				metric = new GlyphMetric();
				metric.setValue(currentTrackUser.getY());
				metric.setStylePosition(defaultStylePosition);
				metric.setMetricsNature(GlyphMetricsNature.Median);
				metric.setMetricsLabel(result.getX() + "");
				metric.setDivergence(divergence);
				metric.setGlyphMetricFill(glyphMetricsFill);
				metric.setGlyphMetricMarkerPainter(glyphMarker);
				metric.setFont(defaultGlyphFont);
			}

		}
		try {
			if (metric != null) {
				addMetrics(metric);
				fireCurrentTrack();
			}
		} catch (IllegalArgumentException e) {
		}
		getProjection().getDevice2D().repaintDevice();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin.OnDragListener#onDrag(java.awt
	 * .event.MouseEvent)
	 */
	@Override
	public void onDrag(MouseEvent me) {
		if (!isLockSelected()) {
			return;
		}
		processMetrics(me);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin.OnPressListener#onPress(java.awt
	 * .event.MouseEvent)
	 */
	@Override
	public void onPress(MouseEvent me) {
		if (!isLockSelected()) {
			return;
		}
		processMetrics(me);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin.OnMoveListener#onMove(java.awt
	 * .event.MouseEvent)
	 */
	@Override
	public void onMove(MouseEvent me) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin.OnReleaseListener#onRelease(java
	 * .awt.event.MouseEvent)
	 */
	@Override
	public void onRelease(MouseEvent me) {
		if (!isLockSelected()) {
			return;
		}
		clearMetrics();
		getProjection().getDevice2D().repaintDevice();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view
	 * .View2D, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
	 */
	@Override
	protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
		metricsPathFunction.setWindow2d(getProjection());
		metricsPathFunction.setFontRenderContext(g2d.getFontRenderContext());
		metricsPathFunction.setSolveGeometryRequest(true);
		List<GlyphMetric> metrics = metricsPathFunction.getMetrics();
		for (GlyphMetric glyphMetric : metrics) {

			if (glyphMetric.getGlyphMetricMarkerPainter() != null) {
				glyphMetric.getGlyphMetricMarkerPainter().paintGlyphMetric(g2d, glyphMetric);
			}

			if (glyphMetric.getGlyphMetricFill() != null) {
				glyphMetric.getGlyphMetricFill().paintGlyphMetric(g2d, glyphMetric);
			}

			if (glyphMetric.getGlyphMetricDraw() != null) {
				glyphMetric.getGlyphMetricDraw().paintGlyphMetric(g2d, glyphMetric);
			}

		}

	}

}
