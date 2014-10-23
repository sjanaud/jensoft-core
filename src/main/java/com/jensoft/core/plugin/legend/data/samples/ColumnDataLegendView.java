/*
 * JenSoft API - Charting Framework
 * http://www.jensoftapi.com
 * Copyright (c) JenSoft. All rights reserved.
 * See JenSoft Software License Agreement
 */
package com.jensoft.core.plugin.legend.data.samples;

import java.awt.BasicStroke;
import java.awt.Color;

import com.jensoft.core.catalog.nature.JenSoftView;
import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.graphics.Shader;
import com.jensoft.core.palette.RosePalette;
import com.jensoft.core.plugin.legend.data.DataLegend;
import com.jensoft.core.plugin.legend.data.DataLegendPlugin;
import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.view.background.DarkViewBackground;
import com.jensoft.core.view.background.RoundViewFill;
import com.jensoft.core.window.Window2D;


/**
 * <code>ColumnDataLegendView</code>
 * 
 * @author JenSoft API
 */
@JenSoftView(background=DarkViewBackground.class,description="Show column data legend")
public class ColumnDataLegendView extends View2D {


private static final long serialVersionUID = -8150525550869567793L;

public ColumnDataLegendView() {
	super();
	setPlaceHolder(100);
	Window2D w2d = new Window2D.Linear(-10, 10, -10, 10);
	registerWindow2D(w2d);
	w2d.registerPlugin(new OutlinePlugin());

	DataLegendPlugin legendPlugin = new DataLegendPlugin();
	w2d.registerPlugin(legendPlugin);

	 DataLegend legend = new DataLegend();
	 legendPlugin.setLegend(legend);
     
	 
     legend.addItem(new DataLegend.Item(RosePalette.CALYPSOBLUE, "legend 1"));
     legend.addItem(new DataLegend.Item(RosePalette.CORALRED, "legend 2"));
     legend.addItem(new DataLegend.Item(RosePalette.LAVENDER, "legend 3"));
     legend.addItem(new DataLegend.Item(RosePalette.LEMONPEEL, "legend 4"));
     legend.addItem(new DataLegend.Item(RosePalette.LIGHTBROWN, "legend 5"));
     legend.addItem(new DataLegend.Item(RosePalette.LIME, "legend 6"));
	
	
	
	RoundViewFill viewBackground = new RoundViewFill();
	Shader s = new Shader(new float[] { 0f, 1f }, new Color[] { new Color(32, 39, 55), Color.BLACK });
	viewBackground.setShader(s);
	viewBackground.setOutlineStroke(new BasicStroke(2.5f));
	setBackgroundPainter(viewBackground);

	
}

	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new ColumnDataLegendView());
	}

}
