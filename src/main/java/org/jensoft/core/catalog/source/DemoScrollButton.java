/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.catalog.source;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.plaf.metal.MetalScrollButton;

class DemoScrollButton extends MetalScrollButton {

	private static Icon BUTTON_WEST;
	private static Icon BUTTON_EAST;
	private static Icon BUTTON_NORTH;
	private static Icon BUTTON_SOUTH;

	static {
		BUTTON_WEST = new ImageIcon(DemoScrollbarUI.class.getResource("scrollbar-button-west.png"));
		BUTTON_EAST = new ImageIcon(DemoScrollbarUI.class.getResource("scrollbar-button-east.png"));
		BUTTON_NORTH = new ImageIcon(DemoScrollbarUI.class.getResource("scrollbar-button-north.png"));
		BUTTON_SOUTH = new ImageIcon(DemoScrollbarUI.class.getResource("scrollbar-button-south.png"));
	}

	DemoScrollButton(int direction, int width, boolean freeStanding) {
		super(direction, width, freeStanding);
		setOpaque(false);
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(17, 17);
	}

	@Override
	public void paint(Graphics g) {
		switch (getDirection()) {
		case SwingConstants.WEST:
			BUTTON_WEST.paintIcon(null, g, 0, 0);
			break;
		case SwingConstants.EAST:
			BUTTON_EAST.paintIcon(null, g, 0, 0);
			break;
		case SwingConstants.NORTH:
			BUTTON_NORTH.paintIcon(null, g, 0, 0);
			break;
		case SwingConstants.SOUTH:
			BUTTON_SOUTH.paintIcon(null, g, 0, 0);
			break;
		}
	}
}
