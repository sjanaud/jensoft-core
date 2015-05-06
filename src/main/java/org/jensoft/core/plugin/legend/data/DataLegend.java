/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.legend.data;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.legend.data.painter.AbstractDataItemSymbolPainter;
import org.jensoft.core.plugin.legend.data.painter.AbstractDataLegendBackgroundPainter;
import org.jensoft.core.plugin.legend.data.painter.DefaultDataLegendBackgroundPainter;
import org.jensoft.core.plugin.legend.data.painter.LineSymbolPainter;
import org.jensoft.core.view.ViewPart;

/**
 * <code>DataLegend</code> defines a group of item based on texts and colors
 * 
 * @since 1.1
 * @author sebastien janaud
 */
public class DataLegend {

	/** legend registry */
	private List<Item> items;
	
	/**data legend background painter*/
	private AbstractDataLegendBackgroundPainter backgroundPainter = new DefaultDataLegendBackgroundPainter();

	/** legend font */
	private Font font;

	/** data legend orientation, default column */
	private Orientation orientation = Orientation.Column;

	/**
	 * defines the interval between square colored marker and legend text,
	 * default is 10 pixels
	 */
	private int markerTextInterval = 5;

	/**
	 * defines the location of legend, east, south, west, north or device,
	 * Default is device location
	 */
	private ViewPart part = ViewPart.Device;

	/** data legend margin x from upper corner left of part component */
	private int marginX = 30;

	/** data legend margin y from upper corner left of part component */
	private int marginY = 30;
	
	
	/**padding*/
	private int paddingTop;
	private int paddingBottom;
	private int paddingLeft;
	private int paddingRight;

	/**
	 * symbol bound width is the region width for painting item symbol before
	 * text legend
	 */
	private int symbolBoundWidth = 20;

	/**
	 * delta wrap line between two legend line if need to wrap line, default in
	 * 5 pixels more over font height
	 */
	private int deltaWrapLine = 5;

	/**
	 * Defines data legend orientation, Row or column.
	 * 
	 */
	public enum Orientation {
		Row, Column;
	}

	/**
	 * Create Data legend
	 */
	public DataLegend() {
		items = new ArrayList<DataLegend.Item>();
	}

	/**
	 * Create Data legend on the given location
	 * 
	 * @param part
	 *            legend location
	 */
	public DataLegend(ViewPart part) {
		this();
		this.part = part;
	}
	
	/**
	 * Create Data legend on the given location and orientation
	 * @param orientation
	 * @param part
	 */
	public DataLegend(Orientation orientation, ViewPart part) {
		this(part);
		this.orientation = orientation;
	}
	
	
	public AbstractDataLegendBackgroundPainter getBackgroundPainter() {
		return backgroundPainter;
	}

	public void setBackgroundPainter(AbstractDataLegendBackgroundPainter backgroundPainter) {
		this.backgroundPainter = backgroundPainter;
	}

	public int getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}

	public int getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(int paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	public int getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public int getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}

	/**
	 * set data legend padding
	 * @param padding
	 */
	public void setPadding(int padding) {
		this.paddingTop 	= padding;
		this.paddingBottom 	= padding;
		this.paddingLeft 	= padding;
		this.paddingRight	= padding;
	}

	/**
	 * add legend item
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		items.add(item);
	}

	/**
	 * get symbol bound width
	 * 
	 * @return symbol bound width
	 */
	public int getSymbolBoundWidth() {
		return symbolBoundWidth;
	}

	/**
	 * set symbol bound width
	 * 
	 * @param symbolBoundWidth
	 */
	public void setSymbolBoundWidth(int symbolBoundWidth) {
		this.symbolBoundWidth = symbolBoundWidth;
	}

	/**
	 * get delta wrap line
	 * 
	 * @return delta wrap line
	 */
	public int getDeltaWrapLine() {
		return deltaWrapLine;
	}

	/**
	 * set delta wrap line
	 * 
	 * @param deltaWrapLine
	 */
	public void setDeltaWrapLine(int deltaWrapLine) {
		this.deltaWrapLine = deltaWrapLine;
	}

	/**
	 * get marker and text interval
	 * 
	 * @return interval
	 */
	public int getMarkerTextInterval() {
		return markerTextInterval;
	}

	/**
	 * set maker and text interval
	 * 
	 * @param markerTextInterval
	 */
	public void setMarkerTextInterval(int markerTextInterval) {
		this.markerTextInterval = markerTextInterval;
	}

	/**
	 * get data legend orientation
	 * 
	 * @return orientation
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * set data legend orientation
	 * 
	 * @param orientation
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * get data legend font
	 * 
	 * @return font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * set data legend font
	 * 
	 * @param font
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * get data legend items
	 * 
	 * @return items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * get legend location part
	 * 
	 * @return part location
	 */
	public ViewPart getPart() {
		return part;
	}

	/**
	 * set legend part location
	 * 
	 * @param part
	 */
	public void setPart(ViewPart part) {
		this.part = part;
	}

	/**
	 * get the margin x of data legend from upper left corner part component
	 * 
	 * @return margin x
	 */
	public int getMarginX() {
		return marginX;
	}

	/**
	 * set the margin x of data legend from upper left corner part component
	 * 
	 * @param marginX
	 *            margin x
	 */
	public void setMarginX(int marginX) {
		this.marginX = marginX;
	}

	/**
	 * get the margin y of data legend from upper left corner part component
	 * 
	 * @return margin y
	 */
	public int getMarginY() {
		return marginY;
	}

	/**
	 * set the margin y of data legend from upper left corner part component
	 * 
	 * @param marginX
	 *            margin y
	 */
	public void setMarginY(int marginY) {
		this.marginY = marginY;
	}

	/**
	 * Defines the data legend item, simple structure with color, text , and
	 * text color.
	 * 
	 * @author sebastien janaud
	 */
	public static class Item {
		private Color color;
		private Color textColor;
		private String text;
		private AbstractDataItemSymbolPainter symbolPainter = new LineSymbolPainter();

		/**
		 * create item with given parameters
		 * 
		 * @param color
		 * @param text
		 */
		public Item(Color color, String text) {
			super();
			this.color = color;
			this.text = text;
		}

		/**
		 * create item with given parameters
		 * 
		 * @param color
		 * @param text
		 * @param textColor
		 */
		public Item(Color color, String text, Color textColor) {
			this(color, text);
			this.textColor = textColor;
		}

		/**
		 * get item theme color
		 * 
		 * @return
		 */
		public Color getColor() {
			return color;
		}

		/**
		 * set item theme color
		 * 
		 * @param color
		 */
		public void setColor(Color color) {
			this.color = color;
		}

		/**
		 * get item text
		 * 
		 * @return item text
		 */
		public String getText() {
			return text;
		}

		/**
		 * set item text
		 * 
		 * @param text
		 */
		public void setText(String text) {
			this.text = text;
		}

		/**
		 * get text color
		 * 
		 * @return text color
		 */
		public Color getTextColor() {
			return textColor;
		}

		/**
		 * set text color if change text color is required from base color, for
		 * example make all text same color
		 * 
		 * @param textColor
		 */
		public void setTextColor(Color textColor) {
			this.textColor = textColor;
		}

		/**
		 * get symbol painter
		 * 
		 * @return symbol painter
		 */
		public AbstractDataItemSymbolPainter getSymbolPainter() {
			return symbolPainter;
		}

		/**
		 * set symbol painter
		 * 
		 * @param symbolPainter
		 */
		public void setSymbolPainter(AbstractDataItemSymbolPainter symbolPainter) {
			this.symbolPainter = symbolPainter;
		}
	}
}
