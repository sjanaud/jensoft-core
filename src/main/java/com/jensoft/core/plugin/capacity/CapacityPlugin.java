/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.capacity;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.graphics.Antialiasing;
import com.jensoft.core.graphics.Fractional;
import com.jensoft.core.palette.Alpha;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.WindowPart;

/**
 * <code>CapacityPlugin</code> draw capacity cell grouped in unit.
 * 
 * <p>
 * this layer is build on a row and column count that define a total number of
 * cell which is the total capacity.
 * </p>
 * <p>
 * Unit is registered on the capacity with a given unit capacity count.
 * <p>
 * <p>
 * An exception is thrown if a unit is exceeded total capacity count,
 * </p>
 * 
 * <p>
 * capacity do not use window projection. it is only design as component.
 * </p>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class CapacityPlugin extends AbstractPlugin {

	/** capacity column count */
	private int col;

	/** capacity row count */
	private int row;

	/** capacity cell width */
	private double cellWidth;

	/** capacity cell height */
	private double cellHeight;

	/** capacity with horizontal or vertical pattern */
	private CapacityMode mode = CapacityMode.Horizontal;

	/** set of unit */
	private List<CapacityUnit> units = new ArrayList<CapacityUnit>();

	/**
	 * define the capacity pattern, horizontal or vertical
	 * 
	 * @author sebastien janaud
	 * 
	 */
	public enum CapacityMode {
		Horizontal, Vertical;
	}

	/**
	 * create capacity for total (row * column) cells
	 * 
	 * @param row
	 * @param col
	 */
	public CapacityPlugin(int row, int col) {
		this.col = col;
		this.row = row;
		setFractionalMetrics(Fractional.On);
		setAntialiasing(Antialiasing.On);
	}

	/**
	 * @return the mode
	 */
	public CapacityMode getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(CapacityMode mode) {
		this.mode = mode;
	}

	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * register a unit in this capacity
	 * 
	 * @param capacityUnit
	 */
	public void registerCapacity(CapacityUnit capacityUnit) {
		// check start index
		int startIndex = 0;
		for (CapacityUnit unit : units) {
			int count = unit.getCount();
			startIndex = startIndex + count;
		}
		if (startIndex + capacityUnit.getCount() > col * row)
			throw new IllegalArgumentException("unit " + capacityUnit.getCount() + ", index out of total capacity. max capacity " + row * col + " cells, unit use index range [" + startIndex + "," + (startIndex + capacityUnit.getCount()) + "]");
		capacityUnit.setHost(this);
		units.add(capacityUnit);
		createIndex();
	}

	/**
	 * paint unit
	 * 
	 * @param g2d
	 * @param unit
	 * 
	 */
	private void paintUnit(Graphics2D g2d, CapacityUnit unit) {
		for (CapacityCell cell : unit.getCells()) {
			Point2D p2d = capacityToPixel(cell);
			Rectangle2D cellUnit = new Rectangle2D.Double(p2d.getX(), p2d.getY(), cellWidth, cellHeight);
			g2d.setColor(new Alpha(unit.getColor(), 200));
			g2d.fill(cellUnit);
			g2d.setStroke(new BasicStroke(0.6f));
			g2d.setColor(new Alpha(unit.getColor().darker(), 200));
			// g2d.setColor(Color.BLACK);
			g2d.draw(cellUnit);
		}
	}

	/**
	 * get the cell left corner coordinate point in device coordinate
	 * 
	 * @param cell
	 * @return
	 */
	private Point2D capacityToPixel(CapacityCell cell) {
		Point2D p2d = new Point2D.Double(new Double(cell.getColumn()) * cellWidth, new Double(cell.getRow()) * cellHeight);
		return p2d;
	}

	public int cellToVIndex(CapacityCell cell) {
		return (cell.getColumn() + 1) * row - 1;
	}

	public int cellToHIndex(CapacityCell cell) {
		return (cell.getRow() + 1) * col - 1;
	}

	/**
	 * create cell in each capacity unit
	 */
	private void createIndex() {
		int startIndex = 0;
		for (CapacityUnit unit : units) {
			int count = unit.getCount();
			unit.setStartIndex(startIndex);
			startIndex = startIndex + count;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view
	 * .View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
	 */
	@Override
	protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

		if (windowPart != WindowPart.Device) {
			return;
		}
		int width = getWindow2D().getDevice2D().getDeviceWidth();
		int height = getWindow2D().getDevice2D().getDeviceHeight();
		cellWidth = new Double(width) / new Double(col);
		cellHeight = new Double(height) / new Double(row);

		createIndex();

		for (CapacityUnit unit : units) {
			unit.buildUnit();
			paintUnit(g2d, unit);
		}

	}

}
