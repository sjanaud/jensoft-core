/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.grid.manager;

import java.awt.Color;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.grid.Grid;
import com.jensoft.core.plugin.grid.Grid.GridOrientation;
import com.jensoft.core.window.Window2D;

/**
 * <code>CompoundGridManager</code>
 * 
 * @author sebastien janaud
 */
public class CompoundGridManager extends AbstractGridManager {

	/** managers */
	private List<GridManager> managers;

	/** generated grids from all manager */
	private List<Grid> compoundgrids;

	/**
	 * create compound manager with the specified manager array
	 * 
	 * @param gridOrientation
	 *            the grids orientation
	 * @param managers
	 *            the managers
	 */
	public CompoundGridManager(GridOrientation gridOrientation) {
		super(gridOrientation);
		compoundgrids = new ArrayList<Grid>();
	}

	
	
	/**
	 * @param managers
	 *            the managers to set
	 */
	public void addManagers(GridManager...gmanagers) {
		for (int i = 0; i < gmanagers.length; i++) {
			managers.add(gmanagers[i]);
		}
	}
	
		

	/**
	 * @return the managers
	 */
	public List<GridManager> getManagers() {
		return managers;
	}



	/**
	 * @param managers the managers to set
	 */
	public void setManagers(List<GridManager> managers) {
		this.managers = managers;
	}



	/**
	 * create compound manager with the specified manager array
	 * 
	 * @param gridOrientation
	 *            the grids orientation
	 * @param managers
	 *            the managers
	 */
	public CompoundGridManager(GridOrientation gridOrientation, GridManager... managers) {
		super(gridOrientation);
		for (int i = 0; i < managers.length; i++) {
			this.managers.add(managers[i]);
		}
		
		compoundgrids = new ArrayList<Grid>();
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.grid.manager.GridManager#getGrids()
	 */
	@Override
	public List<Grid> getGrids() {
		compoundgrids.clear();
		for (int i = 0; i < managers.size(); i++) {
			managers.get(i).setGridOrientation(super.getGridOrientation());
			managers.get(i).setGridStroke(super.getGridStroke());
			managers.get(i).setGridColor(super.getGridColor());
		}
		for (int i = 0; i < managers.size(); i++) {
			compoundgrids.addAll(managers.get(i).getGrids());
		}
		return compoundgrids;
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.grid.manager.AbstractGridManager#setWindow2D(com.jensoft.core.window.Window2D)
	 */
	@Override
	public void setWindow2D(Window2D w2d) {
		super.setWindow2D(w2d);
		for (int i = 0; i < managers.size(); i++) {
			managers.get(i).setWindow2D(w2d);
		}
	}


	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.grid.manager.AbstractGridManager#setGridOrientation(com.jensoft.core.plugin.grid.Grid.GridOrientation)
	 */
	@Override
	public void setGridOrientation(GridOrientation gridOrientation) {
		super.setGridOrientation(gridOrientation);
		for (int i = 0; i < managers.size(); i++) {
			managers.get(i).setGridOrientation(gridOrientation);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.jensoft.core.plugin.grid.manager.AbstractGridManager#setGridColor(java.awt.Color)
	 */
	@Override
	public void setGridColor(Color c) {
		super.setGridColor(c);
		for (int i = 0; i < managers.size(); i++) {
			managers.get(i).setGridColor(c);
		}
	}

	@Override
	public void setGridStroke(Stroke s) {
		super.setGridStroke(s);
		for (int i = 0; i < managers.size(); i++) {
			managers.get(i).setGridStroke(s);
		}
	}

}
