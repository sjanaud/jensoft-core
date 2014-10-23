package com.jensoft.core.plugin.legend.data.painter;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.plugin.legend.data.DataLegend;
import com.jensoft.core.plugin.legend.data.DataLegend.Item;

public class BoundSymbolPainter extends AbstractDataItemSymbolPainter {

	@Override
	public void paintItemSymbol(Graphics2D g2d,Rectangle2D symbolBound, Item item) {
		g2d.setColor(item.getColor());
		g2d.draw(symbolBound);
	}

}
