/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>DataLegend</code> defines a group of item based on texts and colors
 * 
 * @author sebastien janaud
 * 
 */
public class DataLegend {
	
	/** legend registry */
    private List<Item> items;
    
    /**legend font*/
    private Font font;
    
    /**data legend orientation, default column*/
    private Orientation orientation = Orientation.Column;
    
    /**defines the interval between square colored marker and legend text, default is 10 pixels*/
    private int markerTextInterval = 10;
    
    /**
     * Defines data legend orientation, Row or column.
     * 
     */
    public enum Orientation{
    	Row,
    	Column;
    }

    /**
     * Create Data legend
     */
	public DataLegend(){
		items = new ArrayList<DataLegend.Item>();
	}
	
	/**
	 * add legend item
	 * @param item
	 */
	public void addItem(Item item){
		items.add(item);
	}
	
	/**
	 * get marker and text interval
	 * @return interval
	 */
	public int getMarkerTextInterval() {
		return markerTextInterval;
	}

	/**
	 * set maker and text interval
	 * @param markerTextInterval
	 */
	public void setMarkerTextInterval(int markerTextInterval) {
		this.markerTextInterval = markerTextInterval;
	}

	/**
	 * get data legend orientation
	 * @return orientation
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * set data legend orientation
	 * @param orientation
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * get data legend font
	 * @return font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * set data legend font
	 * @param font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * get data legend items
	 * @return items
	 */
	public List<Item> getItems() {
		return items;
	}


	/**
	 * Defines the data legend item, simple structure with color, text , and text color.
	 *
	 */
	public static class Item{
		private Color color;
		private Color textColor;
		private String text;
		public Item(Color color, String text) {
			super();
			this.color = color;
			this.text = text;
		}
		
		public Item(Color color, Color textColor, String text) {
			super();
			this.color = color;
			this.textColor = textColor;
			this.text = text;
		}
		public Color getColor() {
			return color;
		}
		public void setColor(Color color) {
			this.color = color;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}

		public Color getTextColor() {
			return textColor;
		}
		public void setTextColor(Color textColor) {
			this.textColor = textColor;
		}
	}

}
