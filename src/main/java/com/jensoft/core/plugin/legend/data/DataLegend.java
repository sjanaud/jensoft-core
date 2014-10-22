/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.data;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.window.WindowPart;

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
    
    /**defines the location of legend, east, south, west, north or device, Default is device location*/
    private WindowPart part = WindowPart.Device;
    
    /**data legend margin x from upper corner left of part component*/
    private int marginX = 30;
    
    /**data legend margin y from upper corner left of part component*/
    private int marginY = 30;
    
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
     * Create Data legend on the given location
     * @param part legend location
     */
	public DataLegend(WindowPart part){
		this();
		this.part = part;
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
	 * get legend location part
	 * @return part location
	 */
	public WindowPart getPart() {
		return part;
	}

	/**
	 * set legend part location
	 * @param part
	 */
	public void setPart(WindowPart part) {
		this.part = part;
	}

	/**
	 * get the margin x of data legend from upper left corner part component
	 * @return margin x
	 */
	public int getMarginX() {
		return marginX;
	}

	/**
	 * set the margin x of data legend from upper left corner part component
	 * @param marginX margin x
	 */
	public void setMarginX(int marginX) {
		this.marginX = marginX;
	}

	/**
	 * get the margin y of data legend from upper left corner part component
	 * @return margin y
	 */
	public int getMarginY() {
		return marginY;
	}

	/**
	 * set the margin y of data legend from upper left corner part component
	 * @param marginX margin y
	 */
	public void setMarginY(int marginY) {
		this.marginY = marginY;
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
