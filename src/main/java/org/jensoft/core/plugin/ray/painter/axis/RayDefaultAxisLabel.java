/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.ray.painter.axis;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import org.jensoft.core.plugin.ray.Ray;
import org.jensoft.core.plugin.ray.RayGroup;
import org.jensoft.core.plugin.ray.RayPlugin;
import org.jensoft.core.plugin.ray.Ray.RayNature;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

/**
 * <code>DefaultAxisSymbol<code> know how to paint bar or group symbol in projection axis part<br>
 * 
 * <ul>
 * 	<li>only draw symbol in south projection part for vertical symbol</li>
 *  <li>only draw symbol in projection west part for horizontal symbol</li>
 * </ul>
 */
public class RayDefaultAxisLabel extends AbstractRayAxisLabel {

	/** symbol label */
	private String symbol;

	/** symbol color */
	private Color symbolColor;

	/** symbol font */
	private Font symbolFont;

	/** offset x to add on label x location */
	private int offsetX = 0;

	/** offset y to add on label y location */
	private int offsetY = 0;

	/** internal margin x between label and sticker bound */
	private int internalMarginX = 4;

	/** internal margin y between label and sticker bound */
	private int internalMarginY = 4;

	/** round for round rectangle sticker */
	private int round = 10;

	/** color for draw sticker border */
	private Color drawColor;

	/** color for fill sticker area */
	private Color fillColor;

	/**
	 * default axis symbol label
	 */
	public RayDefaultAxisLabel() {
	}

	/**
	 * create default ray axis label with the specified offset
	 * 
	 * @param offsetX
	 *            the offset x to add on solve label X location
	 * @param offsetY
	 *            the offset y to add on solve label Y location
	 */
	public RayDefaultAxisLabel(int offsetX, int offsetY) {
		super();
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	/**
	 * create the label with specified given symbol properties and default
	 * offset (0,0)
	 * 
	 * @param symbol
	 *            the symbol to set
	 * @param symbolColor
	 *            the color to draw symbol
	 */
	public RayDefaultAxisLabel(String symbol, Color symbolColor) {
		super();
		this.symbol = symbol;
		this.symbolColor = symbolColor;
	}

	/**
	 * create a axis symbol label with specified parameters
	 * 
	 * @param symbol
	 *            the symbol to set
	 * @param symbolColor
	 *            the color to draw symbol
	 * @param offsetX
	 *            the offset on x axis
	 * @param offsetY
	 *            the offset on y axis
	 */
	public RayDefaultAxisLabel(String symbol, Color symbolColor, int offsetX, int offsetY) {
		super();
		this.symbol = symbol;
		this.symbolColor = symbolColor;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the symbolColor
	 */
	public Color getSymbolColor() {
		return symbolColor;
	}

	/**
	 * @param symbolColor
	 *            the symbolColor to set
	 */
	public void setSymbolColor(Color symbolColor) {
		this.symbolColor = symbolColor;
	}

	/**
	 * @return the symbolFont
	 */
	public Font getSymbolFont() {
		return symbolFont;
	}

	/**
	 * @param symbolFont
	 *            the symbolFont to set
	 */
	public void setSymbolFont(Font symbolFont) {
		this.symbolFont = symbolFont;
	}

	/**
	 * @return the offsetX
	 */
	public int getOffsetX() {
		return offsetX;
	}

	/**
	 * @param offsetX
	 *            the offsetX to set
	 */
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	/**
	 * @return the offsetY
	 */
	public int getOffsetY() {
		return offsetY;
	}

	/**
	 * @param offsetY
	 *            the offsetY to set
	 */
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	/**
	 * @return the internalMarginX
	 */
	public int getInternalMarginX() {
		return internalMarginX;
	}

	/**
	 * @param internalMarginX
	 *            the internalMarginX to set
	 */
	public void setInternalMarginX(int internalMarginX) {
		this.internalMarginX = internalMarginX;
	}

	/**
	 * @return the internalMarginY
	 */
	public int getInternalMarginY() {
		return internalMarginY;
	}

	/**
	 * @param internalMarginY
	 *            the internalMarginY to set
	 */
	public void setInternalMarginY(int internalMarginY) {
		this.internalMarginY = internalMarginY;
	}

	/**
	 * @return the round
	 */
	public int getRound() {
		return round;
	}

	/**
	 * @param round
	 *            the round to set
	 */
	public void setRound(int round) {
		this.round = round;
	}

	/**
	 * @return the drawColor
	 */
	public Color getDrawColor() {
		return drawColor;
	}

	/**
	 * @param drawColor
	 *            the drawColor to set
	 */
	public void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
	}

	/**
	 * @return the fillColor
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * @param fillColor
	 *            the fillColor to set
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public void paintRayAxisLabel(Graphics2D g2d, Ray ray, ViewPart viewPart) {

		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		if (ray.getRayNature() == RayNature.XRay) {
			paintXRayAxisLabel(g2d, ray, viewPart);
		}
		if (ray.getRayNature() == RayNature.YRay) {
			paintYRayAxisLabel(g2d, ray, viewPart);
		}
	}

	/**
	 * paint XRay axis label
	 * 
	 * @param g2d
	 * @param ray
	 * @param viewPart
	 */
	private void paintXRayAxisLabel(Graphics2D g2d, Ray ray, ViewPart viewPart) {

		RayPlugin bsp = ray.getHost();
		View v2d = ray.getHost().getProjection().getView();

		if (symbolFont == null) {
			setSymbolFont(new Font("Dialog", Font.PLAIN, 12));
		}

		g2d.setFont(getSymbolFont());
		FontMetrics fm = g2d.getFontMetrics();

		if (viewPart == ViewPart.South) {

			int margin = v2d.getPlaceHolderAxisWest();

			if (ray instanceof Ray && !(ray instanceof RayGroup)) {

				Ray r = ray;

				String symbol = getSymbol();
				if (symbol == null) {
					symbol = r.getName();
				}

				if (symbol != null) {
					double barWidth = ray.getRayShape().getWidth();

					int symbolWidth = fm.stringWidth(symbol);
					float pt = (float) (ray.getRayShape().getX() + margin + barWidth / 2 - symbolWidth / 2);

					int x = (int) (pt + offsetX);
					int y = fm.getAscent() + offsetY;

					RoundRectangle2D roundRect = new RoundRectangle2D.Double(x - internalMarginX, y - fm.getAscent() - internalMarginY, symbolWidth + 2 * internalMarginX, fm.getHeight() + 2 * internalMarginY, round, round);

					// g2d.setStroke(new BasicStroke(1));
					// Rectangle2D rec = new Rectangle2D.Double(x,y,2,2);
					// g2d.setColor(Color.RED);
					// g2d.draw(rec);

					if (fillColor != null) {
						g2d.setColor(fillColor);
						g2d.fill(roundRect);
					}

					if (drawColor != null) {
						g2d.setColor(drawColor);
						g2d.draw(roundRect);
					}

					if (getSymbolColor() != null) {
						g2d.setColor(getSymbolColor());
					} else {
						g2d.setColor(ray.getThemeColor());
					}

					// g2d.setColor(Color.BLACK);
					g2d.drawString(symbol, x, y);

				}

			}

		}

	}

	/**
	 *  paint y ray axis label
	 * @param g2d
	 * @param bar
	 * @param viewPart
	 */
	private void paintYRayAxisLabel(Graphics2D g2d, Ray bar, ViewPart viewPart) {

		View v2d = bar.getHost().getProjection().getView();

		if (symbolFont == null) {
			setSymbolFont(new Font("Dialog", Font.PLAIN, 12));
		}

		g2d.setFont(getSymbolFont());
		FontMetrics fm = g2d.getFontMetrics();

		if (viewPart == ViewPart.West) {

			int margin = v2d.getPlaceHolderAxisWest();

			if (bar instanceof Ray && !(bar instanceof RayGroup)) {

				String symbol = getSymbol();
				if (symbol == null) {
					symbol = bar.getName();
				}

				double barHeight = bar.getRayShape().getHeight();

				g2d.setColor(bar.getThemeColor());
				if (symbol != null) {

					int symbolWidth = fm.stringWidth(symbol);
					float pt = (float) (bar.getRayShape().getY() + barHeight / 2 + fm.getAscent() / 2);

					int x = margin - symbolWidth - internalMarginX + offsetX - 4;
					int y = (int) (pt + offsetY);

					RoundRectangle2D roundRect = new RoundRectangle2D.Double(x - internalMarginX, y - fm.getAscent() - internalMarginY, symbolWidth + 2 * internalMarginX, fm.getHeight() + 2 * internalMarginY, round, round);

					if (fillColor != null) {
						g2d.setColor(fillColor);
						g2d.fill(roundRect);
					}

					if (drawColor != null) {
						g2d.setColor(drawColor);
						g2d.draw(roundRect);
					}

					if (getSymbolColor() != null) {
						g2d.setColor(getSymbolColor());
					} else {
						g2d.setColor(bar.getThemeColor());
					}

					g2d.drawString(symbol, x, y);

				}

			}

		}

	}
}
