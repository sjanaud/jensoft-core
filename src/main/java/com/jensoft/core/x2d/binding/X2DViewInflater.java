/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding;

import static com.jensoft.core.x2d.binding.InflaterUtil.elementColor;
import static com.jensoft.core.x2d.binding.InflaterUtil.elementDouble;
import static com.jensoft.core.x2d.binding.InflaterUtil.elementInteger;
import static com.jensoft.core.x2d.binding.InflaterUtil.elementText;
import static com.jensoft.core.x2d.binding.InflaterUtil.getType;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jensoft.core.graphics.Shader;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.PluginPlatform;
import com.jensoft.core.view.View2D;
import com.jensoft.core.view.background.BackgroundPainter;
import com.jensoft.core.view.background.RoundViewFill;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.x2d.X2DException;
import com.jensoft.core.x2d.lang.X2DView2DElement;

/**
 * <code>X2DInflater</code>
 * <p>
 * takes the responsibility to inflate X2D XML document into {@link View2D}
 * </p>
 * 
 * @author sebastien janaud
 */
public class X2DViewInflater implements X2DView2DElement {

	/** x2d template document */
	private Document x2dDocument;

	/** view2D X2D root element */
	private Element viewRoot;

	/** the view2D */
	private View2D view2D;

	/** inflaters */
	private List<AbstractX2DPluginInflater<?>> inflaters = new ArrayList<AbstractX2DPluginInflater<?>>();

	/**
	 * create X2D inflater
	 */
	public X2DViewInflater() {
		System.err.println("--X2DViewInflater--");
		// initCoreInflater();
	}

	/**
	 * register X2D document for this inflater
	 * 
	 * @param x2dDocument
	 *            the x2d to inflate
	 */
	public void setX2D(Document x2dDocument) {
		this.x2dDocument = x2dDocument;
		this.viewRoot = x2dDocument.getDocumentElement();
	}

	/**
	 * inflate
	 */
	public View2D inflate() throws X2DException {
		this.view2D = generateView2D();
		return view2D;
	}

	/**
	 * register default plug-ins inflaters
	 */
	protected void initCoreInflater() {
		System.err.println("--initCoreInflater--");
		List<Class<?>> inflaters = PluginPlatform.scanX2DInflater(X2DBinding.class.getPackage().getName());
		for (Class<?> inflaterClass : inflaters) {
			try {
				AbstractX2DPluginInflater<?> inflater = (AbstractX2DPluginInflater<?>) inflaterClass.newInstance();
				registerInflater(inflater);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		// // outline
		// registerInflater(new OutlineInflater());
		//
		// // legend
		// registerInflater(new LegendInflater());
		//
		// // pie - donut2D - donut3D
		// registerInflater(new Donut3DInflater());
		// registerInflater(new Donut2DInflater());
		// registerInflater(new PieInflater());
		//
		// // metrics
		// registerInflater(new AxisMetricsInflater.FreeMetricsInflater());
		// registerInflater(new AxisMetricsInflater.FlowMetricsInflater());
		// registerInflater(new AxisMetricsInflater.StaticMetricsInflater());
		// registerInflater(new AxisMetricsInflater.ModeledMetricsInflater());
		// registerInflater(new AxisMetricsInflater.TimeMetricsInflater());
		// registerInflater(new
		// AxisMetricsInflater.MultiplierMetricsInflater());
		// registerInflater(new
		// AxisMetricsInflater.MultiMultiplierMetricsInflater());
		//
		// // grids
		// registerInflater(new GridInflater.FreeGridInflater());
		// registerInflater(new GridInflater.FlowGridInflater());
		// registerInflater(new GridInflater.StaticGridInflater());
		// registerInflater(new GridInflater.ModeledGridInflater());
		// registerInflater(new GridInflater.MultiplierGridInflater());
		// // registerInflater(new GridInflater.TimeGridInflater());
		//
		// // functions
		// registerInflater(new CurveFunctionInflater());
		// registerInflater(new AreaFunctionInflater());
		// registerInflater(new ScatterCurveInflater());
		//
		// // zooms
		// registerInflater(new ZoomWheelInflater());
		// registerInflater(new ZoomBoxInflater());
		// registerInflater(new ZoomObjectifInflater());
		//
		// // translate
		// registerInflater(new TranslateInflater());

		// TODO create X2D inflater
		// registerInflater(new SymbolInflater());
		// registerInflater(new GridInflater());

		// registerInflater(new BubbleInflater());
		// registerInflater(new StripeInflater());
		// registerInflater(new RadarInflater());

	}

	/**
	 * @return the inflaters
	 */
	public List<AbstractX2DPluginInflater<?>> getInflaters() {
		return inflaters;
	}

	/**
	 * @param inflaters
	 *            the inflaters to set
	 */
	public void setInflaters(List<AbstractX2DPluginInflater<?>> inflaters) {
		this.inflaters = inflaters;
	}

	/**
	 * register plugin inflater for this view emitter
	 * 
	 * @param inflater
	 *            the inflater to register
	 */
	public void registerInflater(AbstractX2DPluginInflater<?> inflater) {
		if (inflater.getXSIType() == null) {
			throw new IllegalArgumentException("XSI Type for Inflater :" + inflater.getClass() + " is null. it should be provided");
		}
		System.err.println("--registerInflater::" + inflater.getClass());
		inflaters.add(inflater);
	}

	/**
	 * get view key
	 * 
	 * @return view key
	 */
	public String getViewKey() {
		Element e = (Element) viewRoot.getElementsByTagName(ELEMENT_VIEW_KEY).item(0);
		String imageID = e.getTextContent().trim();
		return imageID;
	}

	/**
	 * get API key
	 * 
	 * @return API key
	 */
	public String getAPIKey() {
		Element e = (Element) viewRoot.getElementsByTagName(ELEMENT_API_KEY).item(0);
		String apiKey = e.getTextContent().trim();
		return apiKey;
	}

	/**
	 * get view width
	 * 
	 * @return view width
	 */
	public int getWidth() {
		Element e = (Element) viewRoot.getElementsByTagName(ELEMENT_VIEW_WIDTH).item(0);
		String width = e.getTextContent().trim();
		return Integer.parseInt(width);
	}

	/**
	 * get view height
	 * 
	 * @return view height
	 */
	public int getHeight() {
		Element e = (Element) viewRoot.getElementsByTagName(ELEMENT_VIEW_HEIGHT).item(0);
		String height = e.getTextContent().trim();
		return Integer.parseInt(height);
	}

	/**
	 * lookup inflater for the specified plugin class
	 * 
	 * @param xsiType
	 *            the XSI type
	 * @return plugin inflater
	 */
	protected AbstractX2DPluginInflater<?> lookupType(String xsiType) {
		for (AbstractX2DPluginInflater<?> inflater : inflaters) {
			if (inflater.getXSIType() != null && inflater.getXSIType().equals(xsiType)) {
				return inflater;
			}
		}
		return null;
	}

	/**
	 * lookup inflater for the specified plugin class
	 * 
	 * @param xsiType
	 *            the XSI type
	 * @param typeClass
	 *            the plugin class
	 * @return plugin inflater
	 */
	protected AbstractX2DPluginInflater<?> lookupTypeClass(String xsiType, String typeClass) {
		for (AbstractX2DPluginInflater<?> inflater : inflaters) {
			if (inflater.getXSIType() != null && inflater.getXSIType().equals(xsiType) && inflater.getBinding().plugin().getName().equals(typeClass)) {
				return inflater;
			}
		}
		return null;
	}

	/**
	 * inflate view background
	 * 
	 * @param background
	 *            the background element view to inflate
	 * @return the background painter
	 */
	public BackgroundPainter parseBackground(Element background) {
		if (background == null) {
			return null;
		}

		RoundViewFill roundView = null;
		try {

			roundView = new RoundViewFill();

			String outlineround = background.getElementsByTagName(ELEMENT_VIEW_BACKGROUND_OUTLINEROUND).item(0).getTextContent();

			Element outlinecolorElement = (Element) background.getElementsByTagName(ELEMENT_VIEW_BACKGROUND_COLOR).item(0);

			Element shaderElement = (Element) background.getElementsByTagName(ELEMENT_VIEW_BACKGROUND_SHADER).item(0);
			Element strokeElement = (Element) background.getElementsByTagName(ELEMENT_VIEW_BACKGROUND_STROKE).item(0);

			if (shaderElement != null) {
				Shader shader = InflaterUtil.elementShader(shaderElement);
				if (shader != null) {
					roundView.setShader(shader);
				}
			}

			if (strokeElement != null) {
				Stroke stroke = InflaterUtil.elementStroke(strokeElement);
				if (stroke != null) {
					roundView.setOutlineStroke(stroke);
				}
			}
			roundView.setOutlineRound(Integer.parseInt(outlineround));

			roundView.setOutlineColor(InflaterUtil.elementColor(outlinecolorElement));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return roundView;

	}

	/**
	 * create new view instance
	 * 
	 * @return a new view instance
	 */
	private View2D generateView2D() throws X2DException {
		View2D view2D = new View2D();

		view2D.setSize(new Dimension(getWidth(), getHeight()));

		Integer holderWest = elementInteger(viewRoot, ELEMENT_VIEW_HOLDER_WEST);
		Integer holderSouth = elementInteger(viewRoot, ELEMENT_VIEW_HOLDER_SOUTH);
		Integer holderEast = elementInteger(viewRoot, ELEMENT_VIEW_HOLDER_EAST);
		Integer holderNorth = elementInteger(viewRoot, ELEMENT_VIEW_HOLDER_NORTH);

		view2D.setPlaceHolderAxisWest(holderWest);
		view2D.setPlaceHolderAxisSouth(holderSouth);
		view2D.setPlaceHolderAxisEast(holderEast);
		view2D.setPlaceHolderAxisNorth(holderNorth);

		Element viewBackground = (Element) viewRoot.getElementsByTagName(ELEMENT_VIEW_BACKGROUND_BACKGROUND).item(0);
		view2D.setBackgroundPainter(parseBackground(viewBackground));

		NodeList windowsElements = viewRoot.getElementsByTagName(ELEMENT_VIEW_WINDOW2D);
		for (int i = 0; i < windowsElements.getLength(); i++) {

			Element w2dElement = (Element) windowsElements.item(i);

			try {

				String windowID = elementText(w2dElement, ELEMENT_VIEW_WINDOW2D_ID);
				String name = elementText(w2dElement, ELEMENT_VIEW_WINDOW2D_NAME);
				Double minx = elementDouble(w2dElement, ELEMENT_VIEW_WINDOW2D_MIN_X);
				Double maxx = elementDouble(w2dElement, ELEMENT_VIEW_WINDOW2D_MAX_X);
				Double miny = elementDouble(w2dElement, ELEMENT_VIEW_WINDOW2D_MIN_Y);
				Double maxy = elementDouble(w2dElement, ELEMENT_VIEW_WINDOW2D_MAX_Y);
				Color themeColor = elementColor(w2dElement, ELEMENT_VIEW_WINDOW2D_THEME_COLOR);

				Window2D w2d = null;
				String windowType = getType(w2dElement);
				if (windowType.equals(ELEMENT_VIEW_WINDOW2D_TYPE_LINEAR)) {
					w2d = new Window2D.Linear(minx, maxx, miny, maxy);
				} else if (windowType.equals(ELEMENT_VIEW_WINDOW2D_TYPE_LOGX)) {
					w2d = new Window2D.LogX(minx, maxx, miny, maxy);
				} else if (windowType.equals(ELEMENT_VIEW_WINDOW2D_TYPE_LOGY)) {
					w2d = new Window2D.LogY(minx, maxx, miny, maxy);
				} else if (windowType.equals(ELEMENT_VIEW_WINDOW2D_TYPE_LOG)) {
					w2d = new Window2D.Log(minx, maxx, miny, maxy);
				} else if (windowType.equals(ELEMENT_VIEW_WINDOW2D_TYPE_TIMEX)) {
					String minXDate = elementText(w2dElement, ELEMENT_VIEW_WINDOW2D_TIMEX_MIN_X);
					String maxXDate = elementText(w2dElement, ELEMENT_VIEW_WINDOW2D_TIMEX_MAX_X);
					Date minDate = DatatypeConverter.parseDateTime(minXDate).getTime();
					Date maxDate = DatatypeConverter.parseDateTime(maxXDate).getTime();
					w2d = new Window2D.TimeX(minDate, maxDate, miny, maxy);
				} else if (windowType.equals(ELEMENT_VIEW_WINDOW2D_TYPE_TIMEY)) {
					String minYDate = elementText(w2dElement, ELEMENT_VIEW_WINDOW2D_TIMEY_MIN_Y);
					String maxYDate = elementText(w2dElement, ELEMENT_VIEW_WINDOW2D_TIMEY_MAX_Y);
					Date minDate = DatatypeConverter.parseDateTime(minYDate).getTime();
					Date maxDate = DatatypeConverter.parseDateTime(maxYDate).getTime();
					w2d = new Window2D.TimeY(minx, maxx, minDate, maxDate);
				}

				w2d.setName(name);
				w2d.setWindowID(windowID);
				w2d.setThemeColor(themeColor);

				view2D.registerWindow2D(w2d);

				NodeList pluginsElements = w2dElement.getElementsByTagName(ELEMENT_VIEW_PLUGIN);
				for (int j = 0; j < pluginsElements.getLength(); j++) {

					Element pluginElement = (Element) pluginsElements.item(j);

					String pluginType = getType(pluginElement);

					String pluginID = elementText(pluginElement, ELEMENT_VIEW_PLUGIN_ID);
					String pluginName = elementText(pluginElement, ELEMENT_VIEW_PLUGIN_NAME);
					String pluginClass = elementText(pluginElement, ELEMENT_VIEW_PLUGIN_CLASS);

					AbstractX2DPluginInflater<?> inflater = null;
					if (pluginClass == null) {
						inflater = lookupType(pluginType);
					} else {
						inflater = lookupTypeClass(pluginType, pluginClass);
					}

					if (inflater != null) {
						AbstractPlugin plugin = inflater.inflate(pluginElement);
						plugin.setPluginID(pluginID);
						plugin.setName(pluginName);
						w2d.registerPlugin(plugin);
					} else {
						if (pluginClass == null) {
							System.err.println("Plugin inflater type not found : " + pluginType + "\n");
							throw new X2DException("Plugin inflater type not found : " + pluginType + "\n");
						}
						if (pluginClass != null) {
							System.err.println("Plugin inflater type not found : " + pluginType + " for plugin class " + pluginClass + "\n");
							throw new X2DException("Plugin inflater type not found : " + pluginType + " for plugin class " + pluginClass + "\n");
						}
					}

				}

			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

		}
		return view2D;
	}
}
