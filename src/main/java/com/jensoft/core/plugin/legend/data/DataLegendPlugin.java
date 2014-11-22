/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.data;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.List;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Dithering;
import com.jensoft.core.graphics.TextAntialiasing;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.legend.data.DataLegend.Orientation;
import com.jensoft.core.plugin.legend.data.painter.AbstractDataLegendBackgroundPainter;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>DataLegendPlugin</code> takes the responsibility to paint a legend for an item group based on texts and colors
 * 
 * @since 1.1
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
	 * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
	 */
	@Override
	protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {
		if(viewPart != legend.getPart()) return;
			if(legend.getFont() != null){
				g2d.setFont(legend.getFont());
			}
			FontMetrics metrics = g2d.getFontMetrics();
			int  height = metrics.getHeight();
			int descent = metrics.getDescent();
			
			int startX = legend.getMarginX() + legend.getPaddingLeft();
			int startY = legend.getMarginY() + legend.getPaddingTop();
			int symbolBoundWidth  = legend.getSymbolBoundWidth();
			int deltaWrapLine = legend.getDeltaWrapLine();
			
			int currentX = startX;
			int currentY = startY;
			
			Component legendHolder = getProjection().getView2D().getWindowComponent(legend.getPart());
			int partWidth = legendHolder.getWidth();
			int partHeight = legendHolder.getHeight();
			
			int maxCurrentY = 0;
			int maxCurrentX = 0;
			int extendsX = 0;
			int extendsY = 0;
			
			
			
			HashMap<DataLegend.Item, Point2D> map1 = new  HashMap<DataLegend.Item, Point2D>();
			HashMap<DataLegend.Item, Rectangle2D> map2 = new  HashMap<DataLegend.Item, Rectangle2D>();
			List<DataLegend.Item> items = legend.getItems();
			if(legend.getOrientation() == Orientation.Column){
				int maxWidth = 0;
				
				for (DataLegend.Item item : items) {
					int itemTextWidth = metrics.stringWidth(item.getText());
					maxWidth = Math.max(maxWidth, itemTextWidth);
					if((currentY + height) < partHeight){
						Rectangle2D bound = new Rectangle2D.Double(currentX, currentY - height + descent, symbolBoundWidth, height);
						map2.put(item, bound);
						map1.put(item, new Point2D.Double(currentX + symbolBoundWidth + legend.getMarkerTextInterval(),currentY));
					}
					else{
						maxWidth = itemTextWidth;
						currentX = currentX + maxWidth + symbolBoundWidth + 40;
						currentY = startY;
						Rectangle2D bound = new Rectangle2D.Double(currentX, currentY - height + descent, symbolBoundWidth, height);
						map2.put(item, bound);
						map1.put(item,new Point2D.Double(currentX + symbolBoundWidth + legend.getMarkerTextInterval(), currentY));
					}
					
					currentY =  currentY + height + deltaWrapLine;
					maxCurrentY = Math.max(currentY, maxCurrentY );
					extendsX = currentX + symbolBoundWidth + legend.getMarkerTextInterval() + maxWidth;
					extendsY = Math.max(currentY, maxCurrentY );
				}
			}
			else if(legend.getOrientation() == Orientation.Row){
				
				int itemHInterval = 20;
				for (DataLegend.Item item : items) {
					if((currentX + legend.getMarkerTextInterval()+ itemHInterval + metrics.stringWidth(item.getText()) + legend.getPaddingRight()) < partWidth){
						Rectangle2D bound = new Rectangle2D.Double(currentX, currentY - height + descent, symbolBoundWidth, height);
						map2.put(item, bound);
						map1.put(item,new Point2D.Double(currentX + symbolBoundWidth + legend.getMarkerTextInterval(), currentY));
					}
					else{
						currentX = startX ;
						currentY = currentY + height + deltaWrapLine;
						Rectangle2D bound = new Rectangle2D.Double(currentX, currentY - height + descent, symbolBoundWidth, height);
						map2.put(item, bound);
						map1.put(item,new Point2D.Double( currentX + symbolBoundWidth + legend.getMarkerTextInterval(), currentY));
					}
					currentX =  currentX + symbolBoundWidth +legend.getMarkerTextInterval() + metrics.stringWidth(item.getText()) + itemHInterval;
					maxCurrentX = Math.max(currentX, maxCurrentX );
					maxCurrentY = Math.max(currentY, maxCurrentY );
				}
				extendsX  = maxCurrentX  - itemHInterval;
				extendsY = maxCurrentY  + height;
			}
			
			AbstractDataLegendBackgroundPainter painter  = legend.getBackgroundPainter();
			if(painter != null){
				painter.paintBackground(g2d, new Rectangle2D.Double(legend.getMarginX(),legend.getMarginY() - height /*(startY- height)*/, (extendsX-legend.getMarginX()+legend.getPaddingRight()), (extendsY-legend.getMarginY()+legend.getPaddingBottom())), legend);
			}
			
			for (DataLegend.Item item : items) {
				g2d.setColor(item.getColor());
				item.getSymbolPainter().paintSymbol(g2d, map2.get(item), item);
				if(item.getTextColor() != null)
					g2d.setColor(item.getTextColor());
				g2d.drawString(item.getText(),(int)map1.get(item).getX(),(int)map1.get(item).getY());
			}
	}
}
