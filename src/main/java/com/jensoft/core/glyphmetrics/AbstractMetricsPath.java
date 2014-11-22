/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics;

import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.GeneralPath;
import java.util.List;

import com.jensoft.core.glyphmetrics.painter.MetricsPathPainter;
import com.jensoft.core.projection.Projection;

/**
 * <code>AbstractMetricsPath</code> is an abstract definition of a path with
 * metrics associated to this path.
 * 
 * @since 1.0
 * @author Sebastien Janaud
 */
public abstract class AbstractMetricsPath {

	/**
	 * nature of the path
	 */
	public enum ProjectionNature {
		USER, DEVICE
	}

	/** default nature is the user space */
	private ProjectionNature nature = ProjectionNature.USER;

	/** path painter */
	private MetricsPathPainter pathPainter;

	/** reverse mode */
	private boolean autoReverseGlyph = false;

	/** the window 2D */
	private Projection window2d;

	/** the geometry path */
	private GeometryPath geometryPath;

	/** shape of the geometry path */
	private Shape metricsPathShape;

	/** font render contecxt */
	private FontRenderContext fontRenderContext;

	/** solve request */
	private boolean solveGeometryRequest = true;

	/**
	 * get the {@link FontRenderContext} for this path manager
	 * 
	 * @return font render context
	 */
	public FontRenderContext getFontRenderContext() {
		return fontRenderContext;
	}

	/**
	 * set the font render context for this path manager
	 * 
	 * @param fontRenderContext
	 */
	public void setFontRenderContext(FontRenderContext fontRenderContext) {
		this.fontRenderContext = fontRenderContext;
	}

	/**
	 * get the nature of path metrics
	 * 
	 * @return nature
	 */
	public ProjectionNature getProjectionNature() {
		return nature;
	}

	/**
	 * set the nature of the path metrics
	 * 
	 * @param nature
	 */
	public void setProjectionNature(ProjectionNature nature) {
		this.nature = nature;
	};

	/**
	 * true if the path metrics geometry should be solve again, false otherwise
	 * 
	 * @return the solveGeometryRequest
	 */
	public boolean isSolveGeometryRequest() {
		return solveGeometryRequest;
	}

	/**
	 * set to solve request to true will be order a new geometry solve
	 * 
	 * @param solveGeometryRequest
	 *            the solveGeometryRequest to set
	 */
	public void setSolveGeometryRequest(boolean solveGeometryRequest) {
		this.solveGeometryRequest = solveGeometryRequest;
	}

	/**
	 * get the window2D
	 */
	public Projection getWindow2d() {
		return window2d;
	}

	/**
	 * set the window2D
	 * 
	 * @param window2d
	 */
	public void setWindow2d(Projection window2d) {
		this.window2d = window2d;
	}

	/**
	 * set the path painter for this Metrics Path
	 * 
	 * @param pathPainter
	 *            the painter of this metrics path
	 */
	public void setPathPainter(MetricsPathPainter pathPainter) {
		this.pathPainter = pathPainter;
	}

	/**
	 * get the painter of this metrics path
	 * 
	 * @return the path painter
	 */
	public MetricsPathPainter getPathPainter() {
		return pathPainter;
	}

	/**
	 * return true if the reverse glyph is lock
	 * 
	 * @return the reverse lock
	 */
	public boolean isAutoReverseGlyph() {
		return autoReverseGlyph;
	}

	/**
	 * set the reverse glyph
	 * 
	 * @param autoReverseGlyph
	 */
	public void setAutoReverseGlyph(boolean autoReverseGlyph) {
		this.autoReverseGlyph = autoReverseGlyph;
	}

	/**
	 * solve the geometry path {@link GeometryPath} if the
	 * {@link #solveGeometryRequest} is set to true by set
	 * {@link #setSolveGeometryRequest(boolean)} method
	 * 
	 * @return the geometry {@link GeometryPath} of this metrics path
	 */
	public GeometryPath getOrCreateGeometry() {
		if (isSolveGeometryRequest()) {
			// System.out.println("---->create geometry");
			metricsPathShape = createPathMetrics();
			geometryPath = new GeometryPath(metricsPathShape);
			setSolveGeometryRequest(false);
		} else {
			// System.out.println("cached geometry");
		}
		return geometryPath;
	}

	/**
	 * create path for geometry
	 */
	protected abstract GeneralPath createPathMetrics();

	/**
	 * get the glyph metrics
	 * 
	 * @return the list of metrics for this metrics path
	 */
	public abstract List<GlyphMetric> getMetrics();

}
