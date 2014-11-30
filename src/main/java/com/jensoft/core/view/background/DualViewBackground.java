/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view.background;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import com.jensoft.core.view.View;


/**
 * <code>DualViewBackground</code> defines a background with dual colors or dual textures pr dual color/texture background
 * 
 * @since 2.0
 * @author sebastien Janaud
 *
 */
public class DualViewBackground extends ViewBackgroundPainter {
	
	/** the outline round corner */
	private int outlineRound = 20;

	/** padding x */
	private int paddingX = 2;

	/** padding y */
	private int paddingY = 2;
	
	private Color color1 = Color.BLACK;
	private Color color2 = Color.RED;
	
	private TexturePaint texture1;
	private TexturePaint texture2;
	
	
	/**
	 * @return the outlineRound
	 */
	public int getOutlineRound() {
		return outlineRound;
	}

	/**
	 * @param outlineRound
	 *            the outlineRound to set
	 */
	public void setOutlineRound(int outlineRound) {
		this.outlineRound = outlineRound;
	}

	/**
	 * @return the paddingX
	 */
	public int getPaddingX() {
		return paddingX;
	}

	/**
	 * @param paddingX
	 *            the paddingX to set
	 */
	public void setPaddingX(int paddingX) {
		this.paddingX = paddingX;
	}

	/**
	 * @return the paddingY
	 */
	public int getPaddingY() {
		return paddingY;
	}

	/**
	 * @param paddingY
	 *            the paddingY to set
	 */
	public void setPaddingY(int paddingY) {
		this.paddingY = paddingY;
	}
	
	
	
	

	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

	public TexturePaint getTexture1() {
		return texture1;
	}

	public void setTexture1(TexturePaint texture1) {
		this.texture1 = texture1;
	}

	public TexturePaint getTexture2() {
		return texture2;
	}

	public void setTexture2(TexturePaint texture2) {
		this.texture2 = texture2;
	}

	/* (non-Javadoc)
	 * @see com.jensoft.core.view.background.ViewBackgroundPainter#paintViewBackground(com.jensoft.core.view.View, int, int, java.awt.Graphics2D)
	 */
	@Override
	public void paintViewBackground(View view, int viewWidth, int viewHeight, Graphics2D g2d) {
		int width = view.getWidth();
		int height = view.getHeight();
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		qualityHints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		qualityHints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHints(qualityHints);
		RoundRectangle2D roundBackground = new RoundRectangle2D.Double(paddingX, paddingY, width - 1 - 2 * paddingX, height - 1 - 2 * paddingY, outlineRound, outlineRound);
		
		if(texture1 != null)
			g2d.setPaint(texture1);
		else
			g2d.setColor(color1);
		
		g2d.fill(roundBackground);
		
		if(texture2 != null)
			g2d.setPaint(texture2);
		else
			g2d.setColor(color2);

		Rectangle2D rectDevice = new Rectangle2D.Double(view.getPlaceHolderAxisWest(), view.getPlaceHolderAxisNorth(), view.getDevice2D().getWidth(), view.getDevice2D().getHeight());
		
		g2d.fill(rectDevice);
		
	}

}
