/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.donut2d.painter.label;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.ParseException;

import org.jensoft.core.drawable.text.TextPath;
import org.jensoft.core.drawable.text.TextPath.PathSide;
import org.jensoft.core.drawable.text.TextPath.TextPosition;
import org.jensoft.core.graphics.Shader;
import org.jensoft.core.plugin.donut2d.Donut2D;
import org.jensoft.core.plugin.donut2d.Donut2DPlugin;
import org.jensoft.core.plugin.donut2d.Donut2DSlice;
import org.jensoft.core.plugin.donut2d.Donut2DToolkit;
import org.jensoft.core.plugin.donut2d.painter.Donut2DSlicePainter;

/**
 * <code>Donut2DPathLabel</code>
 * <p>
 * Donut2D Path label lay out donut3D slice labels on donut3D slice path
 * fragments like external arc or<br>
 * start and end line slice
 * <p>
 * <br>
 * <br>
 * <img src="doc-files/Donut2DPathLabel.png"> <br>
 * <br>
 * create path label on slice
 * 
 * @see Donut2D
 * @see Donut2DSlice
 * @see Donut2DPlugin
 * @see Donut2DSlicePainter
 * @see AbstractDonut2DSliceLabel
 * @see Donut2DToolkit
 * @author Sebastien Janaud
 */
public class Donut2DPathLabel extends AbstractDonut2DSliceLabel {

	/** text position along the slice path entity, default is left */
	private TextPosition textPosition = TextPosition.Left;

	/** side of the path, default is above */
	private PathSide pathSide = PathSide.Above;

	/** label shader fractions */
	private float[] fractions;

	/** label shader colors */
	private Color[] colors;

	/** label divergence from path */
	private int divergence = 10;

	/** path name, default is arc */
	private Donut2DFacetPathName facetPathName = Donut2DFacetPathName.OuterArc;

	/** lock the reverse mode every time */
	private boolean lockReverse = false;

	/** auto reverse mode make reverse if needed ,default is true */
	private boolean autoReverse = true;

	/**
	 * path name define the path fragment<br>
	 * start, arc or end path fragment
	 * 
	 * @author Sebastien Janaud
	 */
	public enum Donut2DFacetPathName {
		Start("start"), OuterArc("outerArc"), InnerArc("innerArc"), End("end");

		/** path name */
		private String pathName;

		/**
		 * create path name
		 * 
		 * @param path
		 *            the path name to set
		 */
		private Donut2DFacetPathName(String path) {
			pathName = path;
		}

		/**
		 * @return the pathName
		 */
		public String getPathName() {
			return pathName;
		}

		/**
		 * parse the specified path string into path name.
		 * 
		 * @param path
		 *            the path name to parse
		 * @return the path name, OuterArc is default
		 */
		public static Donut2DFacetPathName parse(String path) {
			if (Start.getPathName().equalsIgnoreCase(path)) {
				return Start;
			}
			if (OuterArc.getPathName().equalsIgnoreCase(path)) {
				return OuterArc;
			}
			if (InnerArc.getPathName().equalsIgnoreCase(path)) {
				return InnerArc;
			}
			if (End.getPathName().equalsIgnoreCase(path)) {
				return End;
			}
			return OuterArc;
		}
	}

	/**
	 * create empty path label
	 */
	public Donut2DPathLabel() {
	}

	/**
	 * create path label with specified parameters
	 * 
	 * @param textPosition
	 */
	public Donut2DPathLabel(TextPosition textPosition) {
		this.textPosition = textPosition;
	}

	/**
	 * create path label with specified parameters
	 * 
	 * @param label
	 */
	public Donut2DPathLabel(String label) {
		super.setLabel(label);
	}

	/**
	 * create path label with specified parameters
	 * 
	 * @param label
	 * @param labelColor
	 */
	public Donut2DPathLabel(String label, Color labelColor) {
		super.setLabel(label);
		super.setLabelColor(labelColor);
	}

	/**
	 * create path label with specified parameters
	 * 
	 * @param label
	 * @param labelColor
	 * @param labelFont
	 */
	public Donut2DPathLabel(String label, Color labelColor, Font labelFont) {
		super.setLabel(label);
		super.setLabelColor(labelColor);
		super.setLabelFont(labelFont);
	}

	/**
	 * create path label with specified parameters
	 * 
	 * @param textPosition
	 * @param label
	 */
	public Donut2DPathLabel(TextPosition textPosition, String label) {
		this.textPosition = textPosition;
		super.setLabel(label);
	}

	/**
	 * create path label with specified parameters
	 * 
	 * @param textPosition
	 * @param label
	 * @param labelColor
	 */
	public Donut2DPathLabel(TextPosition textPosition, String label, Color labelColor) {
		this.textPosition = textPosition;
		super.setLabel(label);
		super.setLabelColor(labelColor);
	}

	/**
	 * @return the facet Path Name
	 */
	public Donut2DFacetPathName getFacetPathName() {
		return facetPathName;
	}

	/**
	 * @param facetPathName
	 *            the facet Path Name to set
	 */
	public void setFacetPathName(Donut2DFacetPathName facetPathName) {
		this.facetPathName = facetPathName;
	}

	/**
	 * @return the lockReverse
	 */
	public boolean isLockReverse() {
		return lockReverse;
	}

	/**
	 * @param lockReverse
	 *            the lockReverse to set
	 */
	public void setLockReverse(boolean lockReverse) {
		this.lockReverse = lockReverse;
	}

	/**
	 * @return the autoReverse
	 */
	public boolean isAutoReverse() {
		return autoReverse;
	}

	/**
	 * @param autoReverse
	 *            the autoReverse to set
	 */
	public void setAutoReverse(boolean autoReverse) {
		this.autoReverse = autoReverse;
	}

	/**
	 * @return the textPosition
	 */
	public TextPosition getTextPosition() {
		return textPosition;
	}

	/**
	 * @param textPosition
	 *            the textPosition to set
	 */
	public void setTextPosition(TextPosition textPosition) {
		this.textPosition = textPosition;
	}

	/**
	 * @return the pathSide
	 */
	public PathSide getPathSide() {
		return pathSide;
	}

	/**
	 * @param pathSide
	 *            the pathSide to set
	 */
	public void setPathSide(PathSide pathSide) {
		this.pathSide = pathSide;
	}

	/**
	 * @return the divergence
	 */
	public int getDivergence() {
		return divergence;
	}

	/**
	 * @param divergence
	 *            the divergence to set
	 */
	public void setDivergence(int divergence) {
		this.divergence = divergence;
	}

	/**
	 * parse the specified divergence string into int value
	 * 
	 * @param divergence
	 *            the divergence to parse
	 * @return divergence int value, 10 is default return value if
	 *         {@link ParseException} occurs.
	 */
	public static int parseDivergence(String divergence) {
		try {
			return Integer.parseInt(divergence);
		} catch (NumberFormatException e) {
		}
		return 10;
	}

	/**
	 * set gradient shader parameters
	 * 
	 * @param fractions
	 *            the step fractions to set
	 * @param colors
	 *            the colors step
	 */
	public void setTextShader(float[] fractions, Color[] colors) {
		if (fractions.length != colors.length) {
			throw new IllegalArgumentException("colors and fractions length array does not match");
		}
		this.fractions = fractions;
		this.colors = colors;
	}

	/**
	 * set the shadow parameters
	 * 
	 * @param shader
	 */
	public void setTextShader(Shader shader) {
		if (shader != null) {
			fractions = shader.getFractions();
			colors = shader.getColors();
		}
	}

	/**
	 * get the shadow parameters
	 * 
	 */
	public Shader getTextShader() {
		if (fractions != null && colors != null) {
			Shader shader = new Shader(fractions, colors);
			return shader;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jensoft.core.plugin.donut2d.painter.label.AbstractDonut2DSliceLabel
	 * #paintDonut2DSliceLabel(java.awt.Graphics2D,
	 * org.jensoft.core.plugin.donut2d.Donut2D,
	 * org.jensoft.core.plugin.donut2d.Donut2DSlice)
	 */
	@Override
	protected void paintDonut2DSliceLabel(Graphics2D g2d, Donut2D donut3d, Donut2DSlice donutSlice) {
		TextPath pt = null;
		if (facetPathName == Donut2DFacetPathName.OuterArc) {
			pt = new TextPath(donutSlice.getOuterArc());
		}
		if (facetPathName == Donut2DFacetPathName.InnerArc) {
			pt = new TextPath(donutSlice.getInnerArc());
		}
		if (facetPathName == Donut2DFacetPathName.Start) {
			pt = new TextPath(donutSlice.getLineStart());
		}
		if (facetPathName == Donut2DFacetPathName.End) {
			pt = new TextPath(donutSlice.getLineEnd());
		}

		if (pt != null) {
			pt.setTextPosition(textPosition);
			pt.setLockReverse(isLockReverse());
			pt.setAutoReverse(isAutoReverse());
			pt.setLabel(getLabel());
			pt.setTextColor(getLabelColor());
			pt.setLabelFont(getLabelFont());
			pt.setPathSide(pathSide);
			pt.setDivergence(divergence);
			if (fractions != null && colors != null && fractions.length == colors.length) {
				pt.setShader(fractions, colors);
			}
			pt.draw(g2d);
		}

	}

}
