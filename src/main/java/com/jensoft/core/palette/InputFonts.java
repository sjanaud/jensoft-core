/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * <code>InputFonts</code> is a cache of the dynamically loaded fonts found in
 * the fonts directory.
 * 
 * <p>
 * WARNING : some physicals fonts crash JVM
 * </p>
 * 
 * <p>
 * better way is to use logical fonts :
 * <p>
 *  <ul>
 *   <li>Dialog</li>
 *   <li>DialogInput</li>
 *   <li>Monospaced</li>
 *   <li>Serif</li>
 *   <li>SansSerif</li>
 *  </ul>
 *   Font font = new Font("Dialog", Font.PLAIN, 12);
 * 
 * @author sebastien janaud
 */
public class InputFonts {

	public static String ELEMENT = "Elements.ttf";
	public static String NEUROPOL = "NEUROPOL.ttf";
	public static String NO_MOVE = "No-move.ttf";
	

	private static String[] names = {ELEMENT, NEUROPOL, NO_MOVE};

	private static Map<String, Font> cache = new ConcurrentHashMap<String, Font>(names.length);

	static {
		for (String name : names) {
			cache.put(name, getFont(name));
		}
	}

	
	public static Font getFont(String name, float size) {
		Font font = null;
		if (cache != null) {
			if ((font = cache.get(name)) != null) {
				return font.deriveFont(size);
			}
		}
		String fName = "font/" + name;
		try {
			InputStream is = InputFonts.class.getResourceAsStream(fName);

			font = Font.createFont(Font.TRUETYPE_FONT, is);
			cache.put(name, font);
		} catch (Exception ex) {

			System.err.println(fName + " not loaded.  Using Dialog");
			font = new Font("Dialog", Font.PLAIN, 12);
		}
		return font;
	}

	private static Font getFont(String name) {
		Font font = null;
		if (cache != null) {
			if ((font = cache.get(name)) != null) {
				return font;
			}
		}
		String fName = "font/" + name;
		try {
			InputStream is = InputFonts.class.getResourceAsStream(fName);

			font = Font.createFont(Font.TRUETYPE_FONT, is);
			cache.put(name, font);
			
			//register font to local system
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    ge.registerFont(font);
			
		} catch (Exception ex) {
			// ex.printStackTrace();
			System.err.println(fName + " not loaded.   Using Dialog");
			font = new Font("Dialog", Font.PLAIN, 12);
		}
		return font;
	}
}