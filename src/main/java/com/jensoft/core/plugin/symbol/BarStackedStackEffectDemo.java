/*
 * JenSoft API - Charting Framework
 * http://www.jensoft.org
 * Copyright (c) JenSoft. All rights reserved.
 * See JenSoft Software License Agreement
 */
package com.jensoft.core.plugin.symbol;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import com.jensoft.core.catalog.nature.JenSoftView;
import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.graphics.Shader;
import com.jensoft.core.palette.color.ColorPalette;
import com.jensoft.core.palette.color.FilPalette;
import com.jensoft.core.palette.color.JennyPalette;
import com.jensoft.core.palette.color.PetalPalette;
import com.jensoft.core.palette.color.TangoPalette;
import com.jensoft.core.plugin.grid.Grid.GridOrientation;
import com.jensoft.core.plugin.grid.GridPlugin;
import com.jensoft.core.plugin.grid.GridPlugin.FreeGrid;
import com.jensoft.core.plugin.legend.title.TitleLegend;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendAlignment;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendPosition;
import com.jensoft.core.plugin.legend.title.TitleLegendPlugin;
import com.jensoft.core.plugin.legend.title.painter.fil.TitleLegendGradientFill;
import com.jensoft.core.plugin.stripe.StripePlugin;
import com.jensoft.core.plugin.stripe.painter.StripePalette;
import com.jensoft.core.plugin.symbol.BarSymbol.SymbolInflate;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import com.jensoft.core.plugin.symbol.painter.effect.BarEffect1;
import com.jensoft.core.plugin.symbol.painter.effect.BarEffect2;
import com.jensoft.core.plugin.symbol.painter.effect.BarEffect3;
import com.jensoft.core.plugin.symbol.painter.fill.BarDefaultFill;
import com.jensoft.core.plugin.symbol.painter.label.BarSymbolRelativeLabel;
import com.jensoft.core.plugin.symbol.painter.label.BarSymbolRelativeLabel.HorizontalAlignment;
import com.jensoft.core.plugin.symbol.painter.label.BarSymbolRelativeLabel.VerticalAlignment;
import com.jensoft.core.plugin.translate.TranslatePlugin;
import com.jensoft.core.plugin.zoom.wheel.ZoomWheelPlugin;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.Portfolio;
import com.jensoft.core.view.View;
import com.jensoft.core.view.background.ViewDarkBackground;
import com.jensoft.core.view.background.ViewDefaultBackground;

@JenSoftView(background=ViewDarkBackground.class)
public class BarStackedStackEffectDemo extends View {

	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new BarStackedStackEffectDemo());
	}
	
	@Portfolio(name = "SimpleVerticalBarStackedDemo22", width = 500, height = 250)
	public static View getPortofolio() {
		BarStackedStackEffectDemo demo = new BarStackedStackEffectDemo();

		ViewDefaultBackground viewBackground = new ViewDefaultBackground();
		Shader s = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
		viewBackground.setShader(s);
		viewBackground.setOutlineStroke(new BasicStroke(2.5f));

		demo.setBackgroundPainter(viewBackground);
		return demo;
	}

	public BarStackedStackEffectDemo() {

		Projection w2d = new Projection.Linear(0, 0, -30, 120);
		registerProjection(w2d);

		SymbolPlugin barPlugin = new SymbolPlugin();
		barPlugin.setNature(SymbolNature.Vertical);
		w2d.registerPlugin(barPlugin);

		
		Stack s1b1 = SymbolToolkit.createStack("s1", TangoPalette.CHAMELEON1, 12);
		Stack s2b1 = SymbolToolkit.createStack("s2", TangoPalette.CHAMELEON2, 20);
		Stack s3b1 = SymbolToolkit.createStack("s3", TangoPalette.CHAMELEON3, 40);
		StackedBarSymbol b1 = SymbolToolkit.createStackedBarSymbol("b1", 0, 30, SymbolInflate.Ascent, 74, s1b1, s2b1, s3b1);
		
		//global bar, no draw
		b1.setBarDraw(null);
		
		//global fill, but can set on each stack in case different fill need on stack	
		b1.setBarFill(new BarDefaultFill());
		
		//no effect on global bar
		b1.setBarEffect(null);
		
		//stacks
		s3b1.setBarEffect(new BarEffect3());
		s2b1.setBarEffect(new BarEffect2());
		s1b1.setBarEffect(new BarEffect1());
		
		
//		s3b1.setBarEffect(new AbstractBarEffect() {
//			@Override
//			protected void paintBarEffect(Graphics2D g2d, BarSymbol bar) {
//				g2d.setColor(Color.BLACK);
//		        g2d.draw(bar.getBarShape());
//			}
//		});
		
//		Stack s1b2 = SymbolToolkit.createStack("s1", FilPalette.BLUE3, 20);
//		Stack s2b2 = SymbolToolkit.createStack("s2", FilPalette.BLUE2, 40);
//		Stack s3b2 = SymbolToolkit.createStack("s3", FilPalette.BLUE1, 20);
//		StackedBarSymbol b2 = SymbolToolkit.createStackedBarSymbol("b2", 0, 30, SymbolInflate.Ascent, 43, s1b2, s2b2, s3b2);
//		
//		b2.setBarEffect(null);
//		// Alternative creation method
//		// List<Stack> stacks = BarFactory.createStacks(new
//		// String[]{"s1","s2","s3"}, new
//		// Color[]{TangoPalette.CHAMELEON1,TangoPalette.CHAMELEON2,TangoPalette.CHAMELEON3},new
//		// double[]{20d,40d,20d});
//		// Stack[] sa = stacks.toArray(new Stack[0]);
//		// StackedBarSymbol b2g1 = BarFactory.createStackedSmbol("b1g1",
//		// SymbolInflate.Ascent,74,sa);
//
//		// symbol 3
//		Stack s1b3 = SymbolToolkit.createStack("s1", TangoPalette.BUTTER1, 12);
//		Stack s2b3 = SymbolToolkit.createStack("s2", TangoPalette.BUTTER2, 28);
//		Stack s3b3 = SymbolToolkit.createStack("s3", TangoPalette.BUTTER3, 13);
//		StackedBarSymbol b3 = SymbolToolkit.createStackedBarSymbol("b3", 0, 30, SymbolInflate.Ascent, 73, s1b3, s2b3, s3b3);
//
//		// symbol 4
//		Stack s1b4 = SymbolToolkit.createStack("s1", FilPalette.PINK3, 10);
//		Stack s2b4 = SymbolToolkit.createStack("s2", FilPalette.PINK2, 28);
//		Stack s3b4 = SymbolToolkit.createStack("s3", FilPalette.PINK1, 40);
//		StackedBarSymbol b4 = SymbolToolkit.createStackedBarSymbol("b3", 0, 30, SymbolInflate.Ascent, 38, s1b4, s2b4, s3b4);
//
//		// symbol 5
//		Stack s1b5 = SymbolToolkit.createStack("s1", TangoPalette.ORANGE1, 17);
//		Stack s2b5 = SymbolToolkit.createStack("s2", TangoPalette.ORANGE2, 6);
//		Stack s3b5 = SymbolToolkit.createStack("s3", TangoPalette.ORANGE3, 39);
//		StackedBarSymbol b5 = SymbolToolkit.createStackedBarSymbol("b3", 0, 30, SymbolInflate.Ascent, 63, s1b5, s2b5, s3b5);
//
//		// symbol 5
//		Stack s1b6 = SymbolToolkit.createStack("s1", FilPalette.COPPER1, 12);
//		Stack s2b6 = SymbolToolkit.createStack("s2", FilPalette.COPPER2, 30);
//		Stack s3b6 = SymbolToolkit.createStack("s3", FilPalette.COPPER3, 18);
//		StackedBarSymbol b6 = SymbolToolkit.createStackedBarSymbol("b3", 0, 30, SymbolInflate.Ascent, 46, s1b6, s2b6, s3b6);

		BarSymbolLayer barLayer = new BarSymbolLayer();
		barPlugin.addLayer(barLayer);

		barLayer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));
		barLayer.addSymbol(b1);
		barLayer.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 20));
//		barLayer.addSymbol(b2);
//		barLayer.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 20));
//		barLayer.addSymbol(b3);
//		barLayer.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 20));
//		barLayer.addSymbol(b4);
//		barLayer.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 20));
//		barLayer.addSymbol(b5);
//		barLayer.addSymbol(SymbolComponent.createStrut(BarSymbol.class, 20));
//		barLayer.addSymbol(b6);
		barLayer.addSymbol(SymbolComponent.createGlue(BarSymbol.class));

		StripePlugin stripePlugin = new StripePlugin.MultiplierStripe.H(0, 20);
		StripePalette bp = new StripePalette();
		bp.addPaint(new Color(255, 255, 255, 40));
		bp.addPaint(ColorPalette.alpha(FilPalette.GREEN5, 120));
		stripePlugin.setStripePalette(bp);
		stripePlugin.setAlpha(0.3f);
		w2d.registerPlugin(stripePlugin);

		GridPlugin gridLayout = new GridPlugin.MultiplierGrid(0, 20, GridOrientation.Horizontal);
		gridLayout.setGridColor(new Color(255, 255, 255, 60));
		w2d.registerPlugin(gridLayout);

		FreeGrid grids = new GridPlugin.FreeGrid(GridOrientation.Horizontal);
		grids.getGridManager().addGrid(-32, "JenSoft", PetalPalette.PETAL4_HC, 0.001f);
		w2d.registerPlugin(grids);

		TitleLegend legend = new TitleLegend("Simple Stacked Bar");
		legend.setLegendFill(new TitleLegendGradientFill(Color.WHITE, JennyPalette.JENNY8));
		Font f = new Font("Dialog", Font.PLAIN, 12);
		legend.setFont(f);
		legend.setConstraints(new TitleLegendConstraints(LegendPosition.South, 0.8f, LegendAlignment.Rigth));
		TitleLegendPlugin lgendL = new TitleLegendPlugin();
		lgendL.addLegend(legend);
		w2d.registerPlugin(lgendL);

		barLayer.addBarListener(new BarListener() {

			@Override
			public void barSymbolReleased(BarEvent e) {
				System.out.println("bar released : " + e.getBarSymbol().getName());
			}

			@Override
			public void barSymbolPressed(BarEvent e) {
				System.out.println("bar pressed : " + e.getBarSymbol().getName());
			}

			@Override
			public void barSymbolExited(BarEvent e) {
				System.out.println("bar exited : " + e.getBarSymbol().getName());
			}

			@Override
			public void barSymbolEntered(BarEvent e) {
				System.out.println("bar entered : " + e.getBarSymbol().getName());
			}

			@Override
			public void barSymbolClicked(BarEvent e) {

				if (!(e.getBarSymbol() instanceof StackedBarSymbol)) {
					System.out.println("bar clicked : " + e.getBarSymbol().getClass());
					if (e.getBarSymbol().getBarLabel() == null) {
						BarSymbol symbol = e.getBarSymbol();
						BarSymbolRelativeLabel rl = new BarSymbolRelativeLabel(VerticalAlignment.Middle, HorizontalAlignment.Middle, Color.WHITE, symbol.getThemeColor(), Color.BLACK);
						symbol.setBarLabel(rl);
						symbol.getHost().getProjection().getView().repaintDevice();
					} else {
						BarSymbol symbol = e.getBarSymbol();
						symbol.setBarLabel(null);
						symbol.getHost().getProjection().getView().repaintDevice();
					}
				}
			}

		});

		TranslatePlugin toolTranslate = new TranslatePlugin();
		w2d.registerPlugin(toolTranslate);

		ZoomWheelPlugin zoomWheel = new ZoomWheelPlugin();
		w2d.registerPlugin(zoomWheel);

	}

}
