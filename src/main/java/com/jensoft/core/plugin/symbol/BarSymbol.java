/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.symbol;

import java.awt.Color;
import java.awt.Shape;

import com.jensoft.core.device.PartBuffer;
import com.jensoft.core.plugin.symbol.SymbolPlugin.SymbolNature;
import com.jensoft.core.plugin.symbol.painter.axis.AbstractBarAxisLabel;
import com.jensoft.core.plugin.symbol.painter.draw.AbstractBarDraw;
import com.jensoft.core.plugin.symbol.painter.effect.AbstractBarEffect;
import com.jensoft.core.plugin.symbol.painter.fill.BarFill;
import com.jensoft.core.plugin.symbol.painter.label.AbstractBarLabel;
import com.jensoft.core.view.View2D.DeviceBand;

/**
 * BarSymbol is a simple bar <br>
 * 
 * @see SymbolPlugin
 * @see BarSymbolGroup
 * @see StackedBarSymbol
 * 
 * @author Sebastien Janaud
 */
public class BarSymbol extends SymbolComponent {

	/** the bar value */
	private double value;

	/** the bar base */
	private double base;

	/** base set flag */
	private boolean baseSet;

	/** bar theme color */
	private Color themeColor;

	/** bar symbol */
	private String symbol;

	/** round constant */
	private int round = 5;

	/** ascent bar type */
	private boolean ascent = false;

	/** descent bar type */
	private boolean descent = false;

	/** morphe style, default is Rectangle */
	private MorpheStyle morpheStyle = MorpheStyle.Rectangle;

	/** bar draw */
	private AbstractBarDraw barDraw;

	/** bar fill */
	private BarFill barFill;

	/** bar effect */
	private AbstractBarEffect barEffect;

	/** bar label */
	private AbstractBarLabel barLabel;

	/** axis label */
	private AbstractBarAxisLabel axisLabel;

	/** bar shape */
	private Shape barShape;

	/** part buffer */
	private PartBuffer part;

	/** boolean inflating operation flag */
	private boolean inflating = false;

	/** deflating operation flag */
	private boolean deflating = false;

	/** current inflate */
	private Inflate inflate;

	/** current deflate */
	private Deflate deflate;

	/**
	 * Morphe style
	 */
	public enum MorpheStyle {
		Round, Rectangle;
	}

	public enum SymbolInflate {
		Ascent, Descent;
	}

	/**
	 * create bar symbol
	 */
	public BarSymbol() {
	}

	/**
	 * create bar symbol
	 * 
	 * @param name
	 *            the name and symbol to set
	 */
	public BarSymbol(String name) {
		super();
		setName(name);
		setSymbol(name);
	}

	/**
	 * create bar symbol
	 * 
	 * @param name
	 * @param symbol
	 */
	public BarSymbol(String name, String symbol) {
		super();
		setName(name);
		setSymbol(symbol);
	}

	/**
	 * get the bar label
	 * 
	 * @return the bar Label
	 */
	public AbstractBarLabel getBarLabel() {
		return barLabel;
	}

	/**
	 * set the bar label
	 * 
	 * @param barLabel
	 *            the bar label to set
	 */
	public void setBarLabel(AbstractBarLabel barLabel) {
		this.barLabel = barLabel;
	}

	/**
	 * @return the axisLabel
	 */
	public AbstractBarAxisLabel getAxisLabel() {
		return axisLabel;
	}

	/**
	 * @param axisLabel
	 *            the axisLabel to set
	 */
	public void setAxisLabel(AbstractBarAxisLabel axisLabel) {
		this.axisLabel = axisLabel;
	}

	/**
	 * get the part buffer
	 * 
	 * @return the part buffer
	 */
	public PartBuffer getPart() {
		return part;
	}

	/**
	 * set the bar part buffer
	 * 
	 * @param part
	 */
	public void setPart(PartBuffer part) {
		this.part = part;
	}

	/**
	 * get the round
	 * 
	 * @return the round
	 */
	public int getRound() {
		return round;
	}

	/**
	 * set round constant to set
	 * 
	 * @param round
	 *            the round to set
	 */
	public void setRound(int round) {
		this.round = round;
	}

	/**
	 * get the morphe style
	 * 
	 * @return morphe style
	 */
	public MorpheStyle getMorpheStyle() {
		return morpheStyle;
	}

	/**
	 * set the morphe style
	 * 
	 * @param morpheStyle
	 *            the morphe style to set
	 */
	public void setMorpheStyle(MorpheStyle morpheStyle) {
		this.morpheStyle = morpheStyle;
	}

	/**
	 * get bar shape
	 * 
	 * @return the bar shape
	 */
	public Shape getBarShape() {
		return barShape;
	}

	/**
	 * set bar shape
	 * 
	 * @param barShape
	 *            the bar shpae to set
	 */
	public void setBarShape(Shape barShape) {
		this.barShape = barShape;
	}

	/**
	 * get symbol
	 * 
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * set symbol
	 * 
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * get theme color
	 * 
	 * @return theme color
	 */
	public Color getThemeColor() {
		return themeColor;
	}

	/**
	 * set the theme color
	 * 
	 * @param themeColor
	 *            the theme color to set
	 */
	public void setThemeColor(Color themeColor) {
		this.themeColor = themeColor;
	}

	public double getValue() {
		return value;
	}

	public void setAscentValue(double value) {
		ascent = true;
		descent = false;
		if (value < 0) {
			throw new IllegalArgumentException("bar value should be greater than 0");
		}
		this.value = value;
	}

	/**
	 * @return the inflating
	 */
	public boolean isInflating() {
		return inflating;
	}

	/**
	 * @param inflating
	 *            the inflating to set
	 */
	public void setInflating(boolean inflating) {
		this.inflating = inflating;
	}

	/**
	 * @return the deflating
	 */
	public boolean isDeflating() {
		return deflating;
	}

	/**
	 * @param deflating
	 *            the deflating to set
	 */
	public void setDeflating(boolean deflating) {
		this.deflating = deflating;
	}

	/**
	 * interrupt current inflating
	 */
	public void interruptInflating() {
		if (inflate != null && !inflate.isInterrupted()) {
			inflate.interrupt();
			try {
				inflate.join();
			} catch (InterruptedException e) {
			}
		}
		if (deflate != null && !deflate.isInterrupted()) {
			deflate.interrupt();
			try {
				deflate.join();
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * inflate bar
	 * 
	 * @param deltaValue
	 *            the delta value to inflate
	 * @param waitBeforeStarting
	 *            the wait delay before starting
	 * @param delay
	 *            the transition delay
	 * @param step
	 *            the inflate step count for the specified delay
	 */
	public void inflate(double deltaValue, int waitBeforeStarting, int delay, int step) {
		if (isInflating() || isDeflating()) {
			return;
		}
		inflate = new Inflate(deltaValue, waitBeforeStarting, delay, step);
		inflate.start();
	}

	/**
	 * inflate thread animator
	 */
	class Inflate extends Thread {

		/** wait before starting inflate */
		private int waitBeforeStarting;

		/** inflate step number */
		private int step;

		/** inflate transition drlay */
		private int delay;

		/** inflate vale */
		private double deltaValue;

		/**
		 * create inflate
		 * 
		 * @param deltaValue
		 * @param waitBeforeStarting
		 * @param delay
		 * @param step
		 */
		public Inflate(double deltaValue, int waitBeforeStarting, int delay, int step) {
			this.waitBeforeStarting = waitBeforeStarting;
			this.deltaValue = deltaValue;
			this.delay = delay;
			this.step = step;
		}

		@Override
		public void run() {
			setInflating(true);
			try {
				Thread.sleep(waitBeforeStarting);
				double val = getValue();
				double valueByStep = deltaValue / step;
				int delayByStep = delay / step;
				for (int i = 0; i < step; i++) {
					val = val + valueByStep;

					if (isAscent()) {
						setAscentValue(val);
					} else if (isDescent()) {
						setDescentValue(val);
					}
					if (getHost() == null || getHost().getWindow2D() == null) {
						interrupt();
					}
					if (getHost() != null) {
						if (getLayer() == null) {
							return;
						} else {
							((BarSymbolLayer) getLayer()).solveSymbolComponent(BarSymbol.this);
						}
					}
					if (getNature() == SymbolNature.Vertical) {
						getHost().getWindow2D().getView2D().repaintDeviceBand(DeviceBand.XBand, (int) getBarShape().getBounds().getX(), (int) getBarShape().getBounds().getWidth() + 1);
					} else if (getNature() == SymbolNature.Horizontal) {
						getHost().getWindow2D().getView2D().repaintDeviceBand(DeviceBand.YBand, (int) getBarShape().getBounds().getY(), (int) getBarShape().getBounds().getHeight() + 1);
					}

					Thread.sleep(delayByStep);

				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
				setInflating(false);
			}
		}
	}

	/**
	 * deflate bar
	 * 
	 * @param deltaValue
	 * @param waitBeforeStarting
	 * @param delay
	 * @param step
	 */
	public void deflate(double deltaValue, int waitBeforeStarting, int delay, int step) {
		if (isInflating() || isDeflating()) {
			return;
		}
		deflate = new Deflate(deltaValue, waitBeforeStarting, delay, step);
		deflate.start();
	}

	/**
	 * Deflate Thread Animator
	 */
	class Deflate extends Thread {

		/** wait before starting deflate */
		private int waitBeforeStarting;

		/** deflate step number */
		private int step;

		/** delay for deflate */
		private int delay;

		/** deflate value */
		private double deltaValue;

		/**
		 * create Deflate
		 * 
		 * @param deltaValue
		 * @param waitBeforeStarting
		 * @param delay
		 * @param step
		 */
		public Deflate(double deltaValue, int waitBeforeStarting, int delay, int step) {
			this.waitBeforeStarting = waitBeforeStarting;
			this.deltaValue = deltaValue;
			this.delay = delay;
			this.step = step;

		}

		@Override
		public void run() {
			setDeflating(true);
			try {

				Thread.sleep(waitBeforeStarting);
				double val = getValue();
				double valueByStep = deltaValue / step;
				int delayByStep = delay / step;
				for (int i = 0; i < step; i++) {

					val = val - valueByStep;
					if (isAscent()) {

						setAscentValue(val);
					} else if (isDescent()) {

						setDescentValue(val);
					}
					if (getHost() == null || getHost().getWindow2D() == null) {
						interrupt();
					}
					if (getHost() != null) {
						((BarSymbolLayer) getLayer()).solveSymbolComponent(BarSymbol.this);
					}

					if (getNature() == SymbolNature.Vertical) {
						getHost().getWindow2D().getView2D().repaintDeviceBand(DeviceBand.XBand, (int) getBarShape().getBounds().getX(), (int) getBarShape().getBounds().getWidth() + 1);
					} else if (getNature() == SymbolNature.Horizontal) {
						getHost().getWindow2D().getView2D().repaintDeviceBand(DeviceBand.YBand, (int) getBarShape().getBounds().getY(), (int) getBarShape().getBounds().getHeight() + 1);
					}

					// oldBound = newBound;
					Thread.sleep(delayByStep);

				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} finally {
				setDeflating(false);
			}
		}
	}

	/**
	 * set the descent value
	 * 
	 * @param value
	 *            the descent value
	 */
	public void setDescentValue(double value) {
		ascent = false;
		descent = true;
		if (value < 0) {
			throw new IllegalArgumentException("bar value should be greater than 0");
		}
		this.value = value;
	}

	/**
	 * get bar base
	 * 
	 * @return teh bar base
	 */
	public double getBase() {
		return base;
	}

	/**
	 * set bar base
	 * 
	 * @param base
	 *            the base to set
	 */
	public void setBase(double base) {
		baseSet = true;
		this.base = base;
	}

	/**
	 * return true if the base has been set, false otherwise
	 * 
	 * @return true if the base has been set, false otherwise
	 */
	public boolean isBaseSet() {
		return baseSet;
	}

	/**
	 * return true is the bar is ascent, false otherwise
	 * 
	 * @return true is the bar is ascent, false otherwise
	 */
	public boolean isAscent() {
		return ascent;
	}

	/**
	 * return true is the bar is descent, false otherwise
	 * 
	 * @return true is the bar is descent, false otherwise
	 */
	public boolean isDescent() {
		return descent;
	}

	/**
	 * true if the bar symbol descent or ascent value is set, false otherwise
	 * 
	 * @return true if the bar symbol descent or ascent value is set, false
	 *         otherwise
	 */
	public boolean isValueSet() {
		return ascent || descent;
	}

	/**
	 * get the bar draw
	 * 
	 * @return the bar draw
	 */
	public AbstractBarDraw getBarDraw() {
		return barDraw;
	}

	/**
	 * set the bar draw
	 * 
	 * @param barDraw
	 *            the bar draw to set
	 */
	public void setBarDraw(AbstractBarDraw barDraw) {
		this.barDraw = barDraw;
	}

	/**
	 * get bar fill
	 * 
	 * @return the bar fill
	 */
	public BarFill getBarFill() {
		return barFill;
	}

	/**
	 * set the bar fill
	 * 
	 * @param barFill
	 *            the bar fill to ser
	 */
	public void setBarFill(BarFill barFill) {
		this.barFill = barFill;
	}

	/**
	 * get bar effect
	 * 
	 * @return the bar effect
	 */
	public AbstractBarEffect getBarEffect() {
		return barEffect;
	}

	/**
	 * set the bar effect
	 * 
	 * @param barEffect
	 *            the bar effect to set
	 */
	public void setBarEffect(AbstractBarEffect barEffect) {
		this.barEffect = barEffect;
	}

}
