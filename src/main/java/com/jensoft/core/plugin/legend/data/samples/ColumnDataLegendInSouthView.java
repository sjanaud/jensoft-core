/*
 * JenSoft API - Charting Framework
 * http://www.jensoftapi.com
 * Copyright (c) JenSoft. All rights reserved.
 * See JenSoft Software License Agreement
 */
package com.jensoft.core.plugin.legend.data.samples;

import java.awt.Font;

import com.jensoft.core.catalog.nature.JenSoftView;
import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.palette.TangoPalette;
import com.jensoft.core.plugin.legend.data.DataLegend;
import com.jensoft.core.plugin.legend.data.DataLegendPlugin;
import com.jensoft.core.plugin.metrics.AxisMetricsPlugin;
import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.view.background.DarkViewBackground;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>ColumnDataLegendView</code>
 * 
 * @author JenSoft API
 */
@JenSoftView(background = DarkViewBackground.class, description = "Show column data legend in south")
public class ColumnDataLegendInSouthView extends View2D {

	private static final long serialVersionUID = -8150525550869567793L;

	public ColumnDataLegendInSouthView() {
		super();

		// -mean south, west = 100 pixel and north and south 100 pixel.
		// -place holders means width for (west and east) and height for (north and south).
		// place holder for outer's parts is a fixed size in pixel for outer components
		setPlaceHolder(100);
		
		// you can use other method to customize each of them, for example : 
		//setPlaceHolderAxisSouth(placeHolderAxisSouth);

		//the user projection
		Window2D w2d = new Window2D.Linear(-10, 10, -10, 10);
		registerWindow2D(w2d);
		w2d.registerPlugin(new OutlinePlugin());
		
		
		//metrics
		Font font = new Font("lucida console", Font.PLAIN, 10);

		// create modeled axis plug-in in south part
		AxisMetricsPlugin.ModeledMetrics southMetrics = new AxisMetricsPlugin.ModeledMetrics.S();
		w2d.registerPlugin(southMetrics);
		southMetrics.setMetricsFont(font);
		southMetrics.setMetricsLabelColor(TangoPalette.SCARLETRED3.brighter());

		
		Font fontLegend = new Font("Verdana", Font.PLAIN, 14);
		
		//register the datalegend plugin
		DataLegendPlugin legendPlugin = new DataLegendPlugin();
		w2d.registerPlugin(legendPlugin);

		//create and add in plugin the data legend south part
		DataLegend legend = new DataLegend(WindowPart.South);
		legend.setFont(fontLegend);
		legendPlugin.setLegend(legend);
		
		// set margin x to 100 pixel, to be align with device
		legend.setMarginX(100);
		
		//set margin y enough to not collide with south metrics
		legend.setMarginY(50);

		
		//add data items to data legend
		legend.addItem(new DataLegend.Item(RosePalette.CALYPSOBLUE, "legend 1"));
		legend.addItem(new DataLegend.Item(RosePalette.CORALRED, "legend 2"));
		legend.addItem(new DataLegend.Item(RosePalette.LAVENDER, "legend 3"));
		legend.addItem(new DataLegend.Item(RosePalette.LEMONPEEL, "legend 4"));
		legend.addItem(new DataLegend.Item(RosePalette.LIGHTBROWN, "legend 5"));
		legend.addItem(new DataLegend.Item(RosePalette.LIME, "legend 6"));

	}

	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new ColumnDataLegendInSouthView());
	}

}
