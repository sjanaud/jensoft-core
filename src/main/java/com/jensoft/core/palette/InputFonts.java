/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.palette;

import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <code>InputFonts</code> is a cache of the dynamically loaded fonts found in
 * the fonts directory.
 * 
 * @author sebastien janaud
 */
public class InputFonts {

	public static String ELEMENT = "Elements.ttf";
	public static String PTF_NORDIC = "PTF-NORDIC-Rnd.ttf";
	public static String FUSION = "fusion.ttf";
	public static String NEUROPOL = "NEUROPOL.ttf";
	public static String NO_MOVE = "No-move.ttf";
	public static String YORKVILLE = "yorkville.ttf";
	public static String SANSATION_REGULAR = "Sansation_Regular.ttf";
	public static String SANSATION_BOLD = "Sansation_Bold.ttf";
	public static String SANSATION_LIGHT = "Sansation_Light.ttf";
	public static String ROADWAY = "ROADWAY_.ttf";
	public static String N_GAGE = "N-Gage.ttf";
	public static String MODE_NINE = "MODENINE.TTF";
	public static String FATSANS = "Fatsans.ttf";
	public static String STASMIC = "stasmic_.ttf";
	public static String STREET = "STREET__.ttf";
	public static String STREET_ITALIC = "STREETI_.ttf";
	public static String SUPREME = "SUPRRG__.TTF";
	public static String X_SCALE = "X-SCALE_.TTF";
	public static String XIROD = "xirod.ttf";
	public static String XOIREQE = "XOIREQE.TTF";
	public static String ZRNIC = "zrnic___.ttf";
	public static String MAC_TYPE = "MacType.TTF";
	public static String PHOENIX = "PHOES___.TTF";
	public static String SAXMONO = "saxmono.ttf";

	public static void main(String[] args) {
		InputFonts.getFont("fusion.ttf");
	}

	private static String[] names = { ELEMENT, PTF_NORDIC, FUSION, NEUROPOL, NO_MOVE, YORKVILLE, SANSATION_REGULAR, SANSATION_BOLD, SANSATION_LIGHT, ROADWAY, N_GAGE, MODE_NINE, FATSANS, STASMIC, STREET, STREET_ITALIC, SUPREME, X_SCALE, XIROD, XOIREQE, ZRNIC, MAC_TYPE, PHOENIX, SAXMONO

	};

	private static Map<String, Font> cache = new ConcurrentHashMap<String, Font>(names.length);

	static {
		for (String name : names) {
			cache.put(name, getFont(name));
		}
	}

	public static Font getSansation(int size) {
		return getFont("Sansation_Regular.ttf").deriveFont(AffineTransform.getScaleInstance(size, size));

	}

	public static Font getElements(int size) {
		return getFont("Elements.ttf").deriveFont(AffineTransform.getScaleInstance(size, size));

	}

	public static Font getYorkville(int size) {
		return getFont("yorkville.ttf").deriveFont(AffineTransform.getScaleInstance(size, size));

	}

	public static Font getNoMove(int size) {
		return getFont("No-move.ttf").deriveFont(AffineTransform.getScaleInstance(size, size));

	}

	public static Font getNeuropol(int size) {
		return getFont("NEUROPOL.ttf").deriveFont(AffineTransform.getScaleInstance(size, size));
	}

	public static Font getPTFNordic(int size) {
		return getFont("PTF-NORDIC-Rnd.ttf").deriveFont(AffineTransform.getScaleInstance(size, size));

	}

	public static Font getFont(String name, int size) {
		Font font = null;
		if (cache != null) {
			if ((font = cache.get(name)) != null) {
				return font.deriveFont(AffineTransform.getScaleInstance(size, size));
			}
		}
		String fName = "font/" + name;
		try {
			InputStream is = InputFonts.class.getResourceAsStream(fName);

			font = Font.createFont(Font.TRUETYPE_FONT, is);
			cache.put(name, font);
		} catch (Exception ex) {

			System.err.println(fName + " not loaded.  Using serif font.");
			font = new Font("serif", Font.PLAIN, 24);
		}
		return font;
	}

	public static Font getFont(String name) {
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
		} catch (Exception ex) {
			// ex.printStackTrace();
			System.err.println(fName + " not loaded.  Using serif font.");
			font = new Font("serif", Font.PLAIN, 12);
		}
		return font;
	}
}