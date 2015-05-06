/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.catalog.ui;

import javax.swing.ImageIcon;

/**
 * <code>ImageResource</code>
 * 
 * @author sebastien janaud
 */
public class ImageResource {

	protected static ImageResource resource = null;

	public ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new javax.swing.ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	protected ImageResource() {
		// load file etc...
	}

	public static ImageResource getInstance() {
		if (resource == null) {
			resource = new ImageResource();
		}
		return resource;
	}

}
