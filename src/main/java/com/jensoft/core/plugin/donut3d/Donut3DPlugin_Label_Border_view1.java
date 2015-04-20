/*
 * JenSoft API - Charting Framework
 * http://www.jensoftapi.com
 * Copyright (c) JenSoft. All rights reserved.
 * See JenSoft Software License Agreement
 */
package com.jensoft.core.plugin.donut3d;

import java.awt.Color;
import java.awt.Font;

import com.jensoft.core.catalog.nature.JenSoftView;
import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.palette.color.ColorPalette;
import com.jensoft.core.palette.color.FilPalette;
import com.jensoft.core.palette.color.NanoChromatique;
import com.jensoft.core.palette.color.RosePalette;
import com.jensoft.core.palette.color.Spectral;
import com.jensoft.core.plugin.donut3d.painter.label.AbstractDonut3DSliceLabel.Style;
import com.jensoft.core.plugin.donut3d.painter.label.Donut3DBorderLabel;
import com.jensoft.core.plugin.legend.title.TitleLegend;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendAlignment;
import com.jensoft.core.plugin.legend.title.TitleLegendConstraints.LegendPosition;
import com.jensoft.core.plugin.legend.title.TitleLegendPlugin;
import com.jensoft.core.plugin.legend.title.painter.fil.TitleLegendGradientFill;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.background.ViewNoBackground;

/**
 * <code>Donut3DPlugin_Label_Border_view1</code>
 * 
 * @author JenSoftAPI
 */
@JenSoftView(background=ViewNoBackground.class,description="Show how to use border label with line style on donut3D slice.")
public class Donut3DPlugin_Label_Border_view1 extends View {

	private static final long serialVersionUID = 2180485799535868986L;

	
	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new Donut3DPlugin_Label_Border_view1());
	}
	/**
	 * Create a Donut3D with border label demo
	 */
	public Donut3DPlugin_Label_Border_view1() {
		super(0);
		Projection proj = new Projection.Linear(-1, 1, -1, 1);
		proj.setName("donut3D proj");
		registerProjection(proj);

		Donut3DPlugin donut3DPlugin = new Donut3DPlugin();
		proj.registerPlugin(donut3DPlugin);

		Donut3D donut3d = Donut3DToolkit.createDonut3D("myDonut", 70, 120, 50, 60, 40);
		donut3DPlugin.addDonut(donut3d);
		donut3d.setStartAngleDegree(20); 

		// create sections
		Donut3DSlice s1 = Donut3DToolkit.createDonut3DSlice("s1", new Color(250, 250, 250), 45);
		Donut3DSlice s2 = Donut3DToolkit.createDonut3DSlice("s2", NanoChromatique.ORANGE, 8);
		Donut3DSlice s3 = Donut3DToolkit.createDonut3DSlice("s3", RosePalette.COALBLACK, 15);
		Donut3DSlice s4 = Donut3DToolkit.createDonut3DSlice("s4", Spectral.SPECTRAL_BLUE1, 30);
		// add section in donut
		Donut3DToolkit.pushSlices(donut3d, s1,s2,s3,s4);

		// LABELS
		float[] fractions = { 0f, 0.5f, 1f };
		Color[] c = { new Color(0, 0, 0, 100), new Color(0, 0, 0, 255), new Color(0, 0, 0, 255) };
		Font f =  new Font("Dialog", Font.PLAIN, 12);
		
		Donut3DBorderLabel label1 = Donut3DToolkit.createBorderLabel("Symbian", RosePalette.COALBLACK, f, 20, Style.Both);
		label1.setLinkColor(RosePalette.LEMONPEEL);
		label1.setLabelColor(ColorPalette.WHITE);
		label1.setOutlineColor(Color.BLACK);
		label1.setShader(fractions, c);
		s1.addSliceLabel(label1);

//		Donut3DBorderLabel label2 = Donut3DToolkit.createBorderLabel("WiMo", RosePalette.COALBLACK, f, 20, Style.Both);
//		label2.setLinkColor(RosePalette.LEMONPEEL);
//		label2.setLinkExtends(30);
//		label2.setLabelColor(ColorPalette.WHITE);
//		label2.setOutlineColor(Color.BLACK);
//		label2.setShader(fractions, c);
//		s2.addSliceLabel(label2);
//
//		Donut3DBorderLabel label3 = Donut3DToolkit.createBorderLabel("iPhone", RosePalette.COALBLACK, f, 20, Style.Both);
//		label3.setLinkColor(RosePalette.LEMONPEEL);
//		label3.setLinkExtends(30);
//		label3.setLabelColor(ColorPalette.WHITE);
//		label3.setOutlineColor(Color.BLACK);
//		label3.setShader(fractions, c);
//		s3.addSliceLabel(label3);
//
//		Donut3DBorderLabel label4 = Donut3DToolkit.createBorderLabel("Android", RosePalette.COALBLACK, f, 20, Style.Both);
//		label4.setLinkColor(RosePalette.LEMONPEEL);
//		label4.setLinkExtends(30);
//		label4.setLabelColor(ColorPalette.WHITE);
//		label4.setOutlineColor(Color.BLACK);
//		label4.setShader(fractions, c);
//		s4.addSliceLabel(label4);

		TitleLegend legend = new TitleLegend("Slice Border Label");
		legend.setLegendFill(new TitleLegendGradientFill(Color.WHITE, FilPalette.GREEN5));
		legend.setFont(f);
		legend.setConstraints(new TitleLegendConstraints(LegendPosition.South, 0.8f, LegendAlignment.Rigth));
		TitleLegendPlugin legendPlg = new TitleLegendPlugin();
		legendPlg.addLegend(legend);
		proj.registerPlugin(legendPlg);
	}
}
