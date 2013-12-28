/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.capacity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.capacity.CapacityPlugin.CapacityMode;

/**
 * <code>CapacityUnit</code>
 * 
 * @since 1.0
 * @author sebastien janaud
 * 
 */
public class CapacityUnit {

	/** theme color */
	private Color color;

	/** host plugin */
	private CapacityPlugin host;

	/** start index */
	private int startIndex;

	/** count */
	private int count = 0;

	/** cells */
	private List<CapacityCell> cells = new ArrayList<CapacityCell>();

	/**
	 * create unit with given count cells
	 * 
	 * @param count
	 *            the number of cells that represent this unit
	 */
	public CapacityUnit(int count) {
		this.count = count;
	}

	/**
	 * build the unit
	 * <p>
	 * create cells for this unit according to configuration
	 * </p>
	 */
	public void buildUnit() {
		clearCells();
		for (int i = startIndex; i < startIndex + count; i++) {
			if (getHost().getMode() == CapacityMode.Horizontal) {
				cells.add(createCellByHorizontalIndex(i));
			} else {
				cells.add(createCellByVerticalIndex(i));
			}
		}
	}

	/**
	 * create capacity cell for the given index for the vertical mode
	 * 
	 * @param vIndex
	 * @return new capacity cell
	 */
	private CapacityCell createCellByVerticalIndex(int vIndex) {
		int[] rc = new int[2];
		rc[0] = vIndex / getHost().getRow();
		rc[1] = vIndex % getHost().getRow();
		return new CapacityCell(rc[1], rc[0]);
	}

	/**
	 * create capacity cell for the given index for the horizontal mode
	 * 
	 * @param hIndex
	 * @return new capacity cell
	 */
	private CapacityCell createCellByHorizontalIndex(int hIndex) {
		int[] rc = new int[2];
		rc[0] = hIndex / getHost().getCol();
		rc[1] = hIndex % getHost().getCol();
		return new CapacityCell(rc[0], rc[1]);
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex
	 *            the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	/**
	 * @param cells
	 *            the cells to set
	 */
	public void setCells(List<CapacityCell> cells) {
		this.cells = cells;
	}

	/**
	 * @return the host
	 */
	public CapacityPlugin getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(CapacityPlugin host) {
		this.host = host;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * clear unit cells
	 */
	public void clearCells() {
		cells.clear();
	}

	/**
	 * get unit cells
	 * 
	 * @return unit cells
	 */
	public List<CapacityCell> getCells() {
		return cells;
	}

	/**
	 * get theme color
	 * 
	 * @return theme color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * set theme color
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

}
