/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.gauge.oil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

import org.jensoft.core.drawable.text.TextPath.PathSide;
import org.jensoft.core.drawable.text.TextPath.TextPosition;
import org.jensoft.core.glyphmetrics.GlyphMetric;
import org.jensoft.core.glyphmetrics.Side;
import org.jensoft.core.glyphmetrics.StylePosition;
import org.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import org.jensoft.core.glyphmetrics.painter.marker.DefaultMarker;
import org.jensoft.core.glyphmetrics.painter.marker.TriangleMarker;
import org.jensoft.core.glyphmetrics.painter.marker.TriangleMarker.TriangleDirection;
import org.jensoft.core.palette.InputFonts;
import org.jensoft.core.palette.TexturePalette;
import org.jensoft.core.palette.color.NanoChromatique;
import org.jensoft.core.palette.color.TangoPalette;
import org.jensoft.core.plugin.gauge.core.GaugeBackground;
import org.jensoft.core.plugin.gauge.core.GaugeBody;
import org.jensoft.core.plugin.gauge.core.GaugeEnvelope;
import org.jensoft.core.plugin.gauge.core.GaugeGlass;
import org.jensoft.core.plugin.gauge.core.GaugeMetricsPath;
import org.jensoft.core.plugin.gauge.core.GaugeTextPath;
import org.jensoft.core.plugin.gauge.core.RadialGauge;
import org.jensoft.core.plugin.gauge.core.binder.AnchorBinder;
import org.jensoft.core.plugin.gauge.core.binder.PathBinder;
import org.jensoft.core.plugin.gauge.core.binder.anchor.AnchorBaseBinder;
import org.jensoft.core.plugin.gauge.core.binder.path.PathArcManualBinder;
import org.jensoft.core.plugin.gauge.core.needle.GaugeNeedleClassicPainter;

/**
 * <code>GaugeOil</code> base model helps developer to learn gauge modeling.
 * 
 * @since1.0
 * @author sebastien janaud
 * 
 */
public class GaugeOil2 extends RadialGauge {

	
	private static int gaugeRadius = 90;
	private static int centerUserX = 0;
	private static int centerUserY = 0;
	
	private GaugeBody body;
	
	/** metrics manager to manage value and metrics */
	private GaugeMetricsPath metricsPath1;

	/** metrics manager to manage value and metrics */
	private GaugeMetricsPath metricsPath2;

	private GaugeTextPath legendTop;

	private GaugeTextPath legendBottom;

	private GaugeTextPath legend1;

	private GaugeTextPath legend2;

	public GaugeOil2() {
		super(centerUserX, centerUserY, gaugeRadius);

		GaugeEnvelope cisero = new GaugeEnvelope.Cisero();
		setEnvelop(cisero);

		
		GaugeBackground bg = new GaugeBackground.Circular.Texture(TexturePalette.getInterlacedCarbon1());
		addBackground(bg);
		
		GaugeGlass g3 = new GaugeGlass.GlassIncubator();
		GaugeGlass g5 = new GaugeGlass.Donut2DGlass();
		GaugeGlass g6 = new GaugeGlass.JenSoftAPILabel();

		GaugeGlass linearGlass = new GaugeGlass.GlassLinearEffect();

		addGlass(linearGlass);

		
		body = new GaugeBody();
		addBody(body);
		
		createStyle();
		createPath1();
		createPath2();
		createLegends();

	}

	GlyphFill gfblue;
	GlyphFill gfred;
	GlyphFill gforange;
	GlyphFill gfyellow;

	TriangleMarker rmblue;
	TriangleMarker rmred;
	TriangleMarker rmyellow;
	TriangleMarker rmorange;

	TriangleMarker rmblue2;
	TriangleMarker rmred2;
	TriangleMarker rmyellow2;
	TriangleMarker rmorange2;

	Font f12;
	Font f10;

	private void createStyle() {

		gfblue = new GlyphFill(Color.WHITE, NanoChromatique.BLUE.brighter());
		gfred = new GlyphFill(Color.WHITE, NanoChromatique.RED.brighter());
		gforange = new GlyphFill(Color.WHITE, NanoChromatique.ORANGE.brighter());
		gfyellow = new GlyphFill(Color.WHITE, NanoChromatique.YELLOW.brighter());
		// TicTacMarker ttm = new TicTacMarker(NanoChromatique.GREEN);
		DefaultMarker dmred = new DefaultMarker(NanoChromatique.RED.brighter());
		DefaultMarker dmorange = new DefaultMarker(NanoChromatique.ORANGE.brighter());
		DefaultMarker dmyellow = new DefaultMarker(NanoChromatique.YELLOW.brighter());
		DefaultMarker dmblue = new DefaultMarker(NanoChromatique.BLUE.brighter());

		rmblue = new TriangleMarker(Color.WHITE, NanoChromatique.BLUE);
		rmblue.setGlobalRadialShift(5);
		rmblue.setDivergenceRadial(6);
		rmblue.setDirection(TriangleDirection.Out);

		rmblue2 = new TriangleMarker(Color.WHITE, NanoChromatique.BLUE);
		rmblue2.setGlobalRadialShift(-5);
		rmblue2.setDivergenceRadial(6);
		rmblue2.setDirection(TriangleDirection.In);

		rmred = new TriangleMarker(Color.WHITE, NanoChromatique.RED);
		rmred.setGlobalRadialShift(5);
		rmred.setDivergenceRadial(6);
		rmred.setDirection(TriangleDirection.Out);

		rmred2 = new TriangleMarker(Color.WHITE, NanoChromatique.RED);
		rmred2.setGlobalRadialShift(-5);
		rmred2.setDivergenceRadial(6);
		rmred2.setDirection(TriangleDirection.In);

		rmorange = new TriangleMarker(Color.WHITE, NanoChromatique.ORANGE);
		rmorange.setGlobalRadialShift(5);
		rmorange.setDivergenceRadial(6);
		rmorange.setDirection(TriangleDirection.Out);

		rmorange2 = new TriangleMarker(Color.WHITE, NanoChromatique.ORANGE);
		rmorange2.setGlobalRadialShift(-5);
		rmorange2.setDivergenceRadial(6);
		rmorange2.setDirection(TriangleDirection.In);

		rmyellow = new TriangleMarker(Color.WHITE, NanoChromatique.YELLOW);
		rmyellow.setGlobalRadialShift(5);
		rmyellow.setDivergenceRadial(6);
		rmyellow.setDirection(TriangleDirection.Out);

		rmyellow2 = new TriangleMarker(Color.WHITE, NanoChromatique.YELLOW);
		rmyellow2.setGlobalRadialShift(-5);
		rmyellow2.setDivergenceRadial(6);
		rmyellow2.setDirection(TriangleDirection.In);

		f12 = InputFonts.getFont(InputFonts.ELEMENT, 12);
		f10 = InputFonts.getFont(InputFonts.ELEMENT, 12);
	}

	private void createLegends() {
		
				
		legend1 = new GaugeTextPath();
		legend1.setPathBinder(new PathBinder() {
			
			@Override
			public Shape bindPath(RadialGauge gauge) {
				double centerX = getCenterDevice().getX();
				double centerY = getCenterDevice().getY();
				int radius = getRadius();
				Arc2D arc2TextPath1 = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 170, -140, Arc2D.OPEN);
				return arc2TextPath1;
			}
		});
		Font f = new Font("Dialog", Font.PLAIN, 12);
		float[] fractions = new float[] { 0f, 0.8f, 1f };
		Color[] colors = new Color[] { NanoChromatique.WHITE, NanoChromatique.RED.brighter(), NanoChromatique.RED };
		legend1.setLabelFont(f);
		legend1.setPathSide(PathSide.Below);
		// legend1.setLockReverse(false);
		
		legend1.setDivergence(2);
		legend1.setOffsetLeft(0);
		legend1.setOffsetRight(0);
		legend1.setShader(fractions, colors);
		legend1.setLabel("PSI");
		// legend1.setTextPosition(TextPosition.Right);
		
		body.registerGaugeTextPath(legend1);

		legend2 = new GaugeTextPath();
		legend2.setPathBinder(new PathBinder() {
			
			@Override
			public Shape bindPath(RadialGauge gauge) {
				double centerX = getCenterDevice().getX();
				double centerY = getCenterDevice().getY();
				int radius = getRadius();
				Arc2D arc2TextPath2 = new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, 270, 140, Arc2D.OPEN);
				return arc2TextPath2;
			}
		});
		float[] fractions2 = new float[] { 0f, 1f };
		Color[] colors2 = new Color[] { NanoChromatique.WHITE, NanoChromatique.BLUE };
		legend2.setLabelFont(f);
		legend2.setLabel("CELSIUS");
		legend2.setPathSide(PathSide.Above);

		legend2.setShader(fractions2, colors2);
		legend2.setDivergence(4);
		legend2.setOffsetLeft(0);
		legend2.setOffsetRight(0);
		body.registerGaugeTextPath(legend2);

		legendTop = new GaugeTextPath();
		legendTop.setPathBinder(new PathBinder() {
			
			@Override
			public Shape bindPath(RadialGauge gauge) {
				double centerX = getCenterDevice().getX();
				double centerY = getCenterDevice().getY();
				int radius = getRadius();
				int topRadius = radius+2;
				Arc2D arc2TextPathTop = new Arc2D.Double(centerX - topRadius, centerY - topRadius, 2 * topRadius, 2 * topRadius, 0, 180, Arc2D.OPEN);
				return arc2TextPathTop;
			}
		});
		float[] fractions3 = new float[] { 0f, 1f };
		Color[] colors3 = new Color[] { NanoChromatique.WHITE, NanoChromatique.ORANGE };
		legendTop.setLabelFont(f);
		legendTop.setTextPosition(TextPosition.Middle);
		legendTop.setPathSide(PathSide.Below);
		legendTop.setDivergence(0);
		legendTop.setLockReverse(true);
		legendTop.setOffsetLeft(0);
		legendTop.setOffsetRight(0);
		legendTop.setShader(fractions3, colors3);
		legendTop.setLabel("*** jensoft avionics ***");
		body.registerGaugeTextPath(legendTop);

		legendBottom = new GaugeTextPath();
		legendBottom.setPathBinder(new PathBinder() {
			
			@Override
			public Shape bindPath(RadialGauge gauge) {
				double centerX = getCenterDevice().getX();
				double centerY = getCenterDevice().getY();
				int radius = getRadius();
				int bottomRadius = radius-10;
				Arc2D arc2TextPathBottom = new Arc2D.Double(centerX - bottomRadius, centerY - bottomRadius, 2 * bottomRadius, 2 * bottomRadius,180, 180, Arc2D.OPEN);
				return arc2TextPathBottom;
			}
		});
		float[] fractions4 = new float[] { 0f, 1f };
		Color[] colors4 = new Color[] { NanoChromatique.WHITE, NanoChromatique.PURPLE };
		legendBottom.setLabelFont(f);
		legendBottom.setDivergence(5);
		legendBottom.setPathSide(PathSide.Over);
		legendBottom.setTextPosition(TextPosition.Middle);
		legendBottom.setOffsetLeft(0);
		legendBottom.setOffsetRight(0);
		legendBottom.setShader(fractions4, colors4);
		legendBottom.setLabel("OIL");
		//registerGaugeTextPath(legendBottom);
	}

	private void createPath1() {
		
		metricsPath1 = new GaugeMetricsPath();
		metricsPath1.setAutoReverseGlyph(true);
		metricsPath1.setMin(0);
		metricsPath1.setMax(120);
		metricsPath1.setCurrentValue(47);
		//metricsPath1.setDebugPath(true);
		metricsPath1.setPathBinder(new PathArcManualBinder(2*gaugeRadius - gaugeRadius/4, -70, 50, 2*gaugeRadius, 135));

		metricsPath1.setNeedleBaseAnchorBinder(new AnchorBaseBinder(getRadius()-20, 135));
		
		metricsPath1.setNeedleValueAnchorBinder(new AnchorBinder() {
			@Override
			public Point2D bindAnchor(RadialGauge gauge) {
				Point2D anchorValue = metricsPath1.getRadialPoint(metricsPath1.getCurrentValue(), 20, Side.SideLeft);
				return anchorValue;
			}
		});

		body.registerGaugeMetricsPath(metricsPath1);

		
		GlyphFill fill = new GlyphFill(Color.WHITE, TangoPalette.CHOCOLATE3);
		TriangleMarker marker = new TriangleMarker(Color.WHITE, TangoPalette.CHOCOLATE3);
		marker.setGlobalRadialShift(5);
		marker.setDivergenceRadial(6);
		marker.setDirection(TriangleDirection.Out);
		
		GlyphMetric metric;

		metric = new GlyphMetric();
		metric.setValue(20);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(20 + "");
		//metric.setLockReverse(true);
		metric.setDivergence(-5);
		metric.setGlyphMetricFill(fill);
		metric.setGlyphMetricMarkerPainter(marker);
		metric.setFont(f10);
		metricsPath1.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(40);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(40 + "");
		//metric.setLockReverse(true);
		metric.setDivergence(-5);
		metric.setGlyphMetricFill(fill);
		metric.setGlyphMetricMarkerPainter(marker);
		metric.setFont(f10);
		metricsPath1.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(80);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(80 + "");
		//metric.setLockReverse(true);
		metric.setDivergence(-5);
		metric.setGlyphMetricFill(fill);
		metric.setGlyphMetricMarkerPainter(marker);
		metric.setFont(f10);
		metricsPath1.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(100);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(100 + "");
		//metric.setLockReverse(true);
		metric.setDivergence(-5);
		metric.setGlyphMetricFill(fill);
		metric.setGlyphMetricMarkerPainter(marker);
		metric.setFont(f10);
		metricsPath1.addMetric(metric);
		
		metricsPath1.setGaugeNeedlePainter(new GaugeNeedleClassicPainter());

	}

	private void createPath2() {
		metricsPath2 = new GaugeMetricsPath();
		metricsPath2.setMin(0);
		metricsPath2.setMax(120);
		metricsPath2.setCurrentValue(80);
		
		metricsPath2.setPathBinder(new PathArcManualBinder(2*gaugeRadius - gaugeRadius/4, 160, -50, 2*gaugeRadius, 315));
		metricsPath2.setNeedleBaseAnchorBinder(new AnchorBaseBinder(getRadius()-20, -45));
		
		metricsPath2.setNeedleValueAnchorBinder(new AnchorBinder() {
			@Override
			public Point2D bindAnchor(RadialGauge gauge) {
				Point2D anchorValue = metricsPath2.getRadialPoint(metricsPath2.getCurrentValue(), 20, Side.SideRight);
				return anchorValue;
			}
		});
		
		metricsPath2.setGaugeNeedlePainter(new GaugeNeedleClassicPainter());

		//metricsPath2.setDebugPath(true);
		body.registerGaugeMetricsPath(metricsPath2);

		GlyphFill fill = new GlyphFill(Color.WHITE, TangoPalette.ORANGE3);
		TriangleMarker marker = new TriangleMarker(Color.WHITE, TangoPalette.ORANGE3);
		marker.setGlobalRadialShift(-5);
		marker.setDivergenceRadial(6);
		marker.setDirection(TriangleDirection.In);
		
		GlyphMetric metric;

		metric = new GlyphMetric();
		metric.setValue(20);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(20 + "");
		// metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(fill);
		metric.setGlyphMetricMarkerPainter(marker);
		metric.setFont(f10);
		metricsPath2.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(40);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(40 + "");
		// metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(fill);
		metric.setGlyphMetricMarkerPainter(marker);
		metric.setFont(f10);
		metricsPath2.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(80);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(80 + "");
		// metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(fill);
		metric.setGlyphMetricMarkerPainter(marker);
		metric.setFont(f10);
		metricsPath2.addMetric(metric);

		metric = new GlyphMetric();
		metric.setValue(100);
		metric.setStylePosition(StylePosition.Tangent);
		metric.setMetricsLabel(100 + "");
		// metric.setLockReverse(true);
		metric.setDivergence(5);
		metric.setGlyphMetricFill(fill);
		metric.setGlyphMetricMarkerPainter(marker);
		metric.setFont(f10);
		metricsPath2.addMetric(metric);

	}

}
