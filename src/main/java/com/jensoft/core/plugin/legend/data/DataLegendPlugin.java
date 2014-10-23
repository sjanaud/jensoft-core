/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.data;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.legend.data.DataLegend.Orientation;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>DataLegendPlugin</code> takes the responsibility to paint a legend for an item group based on texts and colors
 * @author sebastien janaud
 *
 */
public class DataLegendPlugin extends AbstractPlugin {

	/** legend */
    private DataLegend legend;

    /**
     * create data legend plugin
     */
    public DataLegendPlugin() {
    	setAntialiasing(Antialiasing.On);
        setTextAntialising(TextAntialiasing.On);
        setDithering(Dithering.On);
        setName(DataLegendPlugin.class.getCanonicalName());
    }
    
    /**
     * create legend plugin
     */
    public DataLegendPlugin(DataLegend legend) {
    	this();
    	this.legend = legend;
    }
    
    /**
     * set data legend 
     * @param legend
     */
    public void setLegend(DataLegend legend){
    	this.legend = legend;
    }
    
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
	 */
	@Override
	protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
		if(windowPart != legend.getPart()) return;
		
			if(legend.getFont() != null){
				g2d.setFont(legend.getFont());
			}
			FontMetrics metrics = g2d.getFontMetrics();
			int  height = metrics.getHeight();
			int descent = metrics.getDescent();
			
			int startX = legend.getMarginX();
			int startY = legend.getMarginY();
			int symbolBoundWidth  = legend.getSymbolBoundWidth();
			int deltaWrapLine = legend.getDeltaWrapLine();
			
			int currentX = startX;
			int currentY = startY;
			
			Component legendHolder = getWindow2D().getView2D().getWindowComponent(legend.getPart());
			int partWidth = legendHolder.getWidth();
			int partHeight = legendHolder.getHeight();
			
			if(legend.getOrientation() == Orientation.Column){
				List<DataLegend.Item> items = legend.getItems();
				int maxWidth = 0;
				for (DataLegend.Item item : items) {
					maxWidth = Math.max(maxWidth, metrics.stringWidth(item.getText()));
					if((currentY + height) < partHeight){
						g2d.setColor(item.getColor());
						
						Rectangle2D bound = new Rectangle2D.Double(currentX, currentY - height + descent, symbolBoundWidth, height);
						item.getSymbolPainter().paintSymbol(g2d, bound, item);
						
						if(item.getTextColor() != null)
						g2d.setColor(item.getTextColor());
						
						g2d.drawString(item.getText(), currentX+ symbolBoundWidth + legend.getMarkerTextInterval(), currentY);
					}
					else{
						currentX = currentX + maxWidth + symbolBoundWidth + 40;
						currentY = startY;
						
						g2d.setColor(item.getColor());
						
						Rectangle2D bound = new Rectangle2D.Double(currentX, currentY - height + descent, symbolBoundWidth, height);
						item.getSymbolPainter().paintSymbol(g2d, bound, item);
						
						if(item.getTextColor() != null)
						g2d.setColor(item.getTextColor());
						g2d.drawString(item.getText(), currentX + symbolBoundWidth + legend.getMarkerTextInterval(), currentY);
					}
					
					currentY =  currentY + height + deltaWrapLine;
				}
			}
			else if(legend.getOrientation() == Orientation.Row){
				List<DataLegend.Item> items = legend.getItems();
				int itemHInterval = 20;
				for (DataLegend.Item item : items) {
					if((currentX + legend.getMarkerTextInterval() + metrics.stringWidth(item.getText())) < partWidth){
						g2d.setColor(item.getColor());
						
						Rectangle2D bound = new Rectangle2D.Double(currentX, currentY - height + descent, symbolBoundWidth, height);
						item.getSymbolPainter().paintSymbol(g2d, bound, item);
						
						if(item.getTextColor() != null)
						g2d.setColor(item.getTextColor());
						
						g2d.drawString(item.getText(), currentX + symbolBoundWidth + legend.getMarkerTextInterval(), currentY);
					}
					else{
						currentX = startX ;
						currentY = currentY + height + deltaWrapLine;
						g2d.setColor(item.getColor());
						
						Rectangle2D bound = new Rectangle2D.Double(currentX, currentY - height + descent, symbolBoundWidth, height);
						item.getSymbolPainter().paintSymbol(g2d, bound, item);
						
						if(item.getTextColor() != null)
						g2d.setColor(item.getTextColor());
						
						g2d.drawString(item.getText(), currentX + symbolBoundWidth + legend.getMarkerTextInterval(), currentY);
					}
					currentX =  currentX + symbolBoundWidth +legend.getMarkerTextInterval() + metrics.stringWidth(item.getText()) + itemHInterval;
				}
			}
	}
}
