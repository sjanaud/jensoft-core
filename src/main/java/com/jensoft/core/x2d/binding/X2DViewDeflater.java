/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.binding;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.view.background.BackgroundPainter;
import com.jensoft.core.view.background.RoundViewFill;
import com.jensoft.core.view.deflater.AbstractViewDeflater;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.x2d.lang.X2DView2DElement;

/**
 * <code>X2DViewDeflater</code>
 * <p>
 * takes the responsibility to transform a view in X2D XML Document.
 * </p>
 * 
 * @author sebastien janaud
 */
public class X2DViewDeflater extends AbstractViewDeflater  implements X2DView2DElement{

	/** X2D document */
	private Document x2dDocument;

	private List<AbstractX2DPluginDeflater<? extends AbstractPlugin>> deflaters = new ArrayList<AbstractX2DPluginDeflater<? extends AbstractPlugin>>();

	/**
	 * create default view deflater
	 */
	public X2DViewDeflater() {
	}

	/**
	 * create X2D Deflater
	 * 
	 * @param view2d
	 */
	public X2DViewDeflater(View2D view2d) {
		super(view2d);
	}

	/**
	 * @return the deflaters
	 */
	public List<AbstractX2DPluginDeflater<? extends AbstractPlugin>> getDeflaters() {
		return deflaters;
	}

	/**
	 * @param deflaters
	 *            the deflaters to set
	 */
	public void setDeflaters(List<AbstractX2DPluginDeflater<? extends AbstractPlugin>> deflaters) {
		this.deflaters = deflaters;
	}

    /**
     * lookup deflater for the specified plugin class
     * 
     * @param plugin
     *            the plugin class
     * @return plugin inflater
     */
    protected AbstractX2DPluginDeflater<? extends AbstractPlugin> lookupType(AbstractPlugin plugin) {
        for (AbstractX2DPluginDeflater<? extends AbstractPlugin> deflater : deflaters) {
        	if (deflater.getBinding().xsi() != null && deflater.getBinding().xsi().equals(plugin.getClass().getSimpleName())) {
                return deflater;
            }
        }
        return null;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jensoft.core.view.deflater.AbstractViewDeflater#deflate()
	 */
	@Override
	public Document deflate() {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			x2dDocument = documentBuilder.newDocument();
			for(AbstractX2DPluginDeflater<?> deflater : deflaters){
				deflater.setX2dDocument(x2dDocument);
			}
			
			Element view2DElement = x2dDocument.createElement(ELEMENT_VIEW_ROOT);
			x2dDocument.appendChild(view2DElement);

			view2DElement.setAttribute("xmlns", "http://www.jensoft.org/jensoft/schema/x2d");
			view2DElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			//view2DElement.setAttribute("xsi:schemaLocation", "http://www.jensoft.org/jensoft/schema/x2d ../../schema/x2d.xsd");

			view2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_KEY, getView2D().getViewKey()));
			view2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_API_KEY, getView2D().getApiKey()));
			view2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_WIDTH, getView2D().getWidth()));
			view2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_HEIGHT, getView2D().getHeight()));
			view2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_HOLDER_WEST, getView2D().getPlaceHolderAxisWest()));
			view2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_HOLDER_EAST, getView2D().getPlaceHolderAxisEast()));
			view2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_HOLDER_NORTH, getView2D().getPlaceHolderAxisNorth()));
			view2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_HOLDER_SOUTH, getView2D().getPlaceHolderAxisSouth()));

			BackgroundPainter painter = getView2D().getBackgroundPainter();
			if (painter instanceof RoundViewFill) {
				RoundViewFill rvf = (RoundViewFill) painter;
				Element view2DBackgroundElement = x2dDocument.createElement(ELEMENT_VIEW_BACKGROUND_BACKGROUND);
				if(rvf.getOutlineRound() >0){
					view2DBackgroundElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_BACKGROUND_OUTLINEROUND, rvf.getOutlineRound()));
				}
				if(rvf.getOutlineColor() != null){
					view2DBackgroundElement.appendChild(DeflaterUtil.createColorElement(x2dDocument,ELEMENT_VIEW_BACKGROUND_COLOR, rvf.getOutlineColor()));
				}
				if(rvf.getOutlineStroke() != null){
					view2DBackgroundElement.appendChild(DeflaterUtil.createStrokeElement(x2dDocument,ELEMENT_VIEW_BACKGROUND_STROKE, rvf.getOutlineStroke()));
				}
				if(rvf.getShader() != null){
					view2DBackgroundElement.appendChild(DeflaterUtil.createShaderElement(x2dDocument,ELEMENT_VIEW_BACKGROUND_SHADER, rvf.getShader()));
				}
			}

			List<Window2D> windows = getView2D().getRegisterWindow();
			
			for (Window2D window2d : windows) {
				Element window2DElement = x2dDocument.createElement(ELEMENT_VIEW_WINDOW2D);
				if(window2d.getClass().getName().equals(Window2D.Linear.class.getName())){
					window2DElement.setAttribute("xsi:type", ELEMENT_VIEW_WINDOW2D_TYPE_LINEAR);
				}
				else if(window2d.getClass().getName().equals(Window2D.LogX.class.getName())){
					window2DElement.setAttribute("xsi:type", ELEMENT_VIEW_WINDOW2D_TYPE_LOGX);
				}
				else if(window2d.getClass().getName().equals(Window2D.LogY.class.getName())){
					window2DElement.setAttribute("xsi:type", ELEMENT_VIEW_WINDOW2D_TYPE_LOGY);
				}
				else if(window2d.getClass().getName().equals(Window2D.Log.class.getName())){
					window2DElement.setAttribute("xsi:type", ELEMENT_VIEW_WINDOW2D_TYPE_LOG);
				}
				else if(window2d.getClass().getName().equals(Window2D.TimeX.class.getName())){
					window2DElement.setAttribute("xsi:type", ELEMENT_VIEW_WINDOW2D_TYPE_TIMEX);
				}
				else if(window2d.getClass().getName().equals(Window2D.TimeY.class.getName())){
					window2DElement.setAttribute("xsi:type", ELEMENT_VIEW_WINDOW2D_TYPE_TIMEY);
				}
				
				
				view2DElement.appendChild(window2DElement);
				if(window2d.getWindowID() == null){
					window2d.setWindowID("window-"+windows.indexOf(window2d));
				}
				
				
				window2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_WINDOW2D_ID, window2d.getWindowID()));
				
				if(window2d.getName() != null){
					window2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_WINDOW2D_NAME, window2d.getName()));
				}
				
				
				if(window2d.getThemeColor() != null){
					window2DElement.appendChild(DeflaterUtil.createColorElement(x2dDocument,ELEMENT_VIEW_WINDOW2D_THEME_COLOR, window2d.getThemeColor()));
				}
				
				
				List<AbstractPlugin> plugins = window2d.getPluginRegistry();
				for (AbstractPlugin abstractPlugin : plugins) {
					AbstractX2DPluginDeflater deflater = lookupType(abstractPlugin);
					if(deflater != null){
						deflater.setPlugin(abstractPlugin);
						Element pluginElement = deflater.deflate(abstractPlugin);
						if(pluginElement != null){
							window2DElement.appendChild(pluginElement);
						}
					}
				}
				
				window2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_WINDOW2D_MIN_X, window2d.getMinX()));
				window2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_WINDOW2D_MAX_X, window2d.getMaxX()));
				window2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_WINDOW2D_MIN_Y, window2d.getMinY()));
				window2DElement.appendChild(DeflaterUtil.createSingleElement(x2dDocument,ELEMENT_VIEW_WINDOW2D_MAX_Y, window2d.getMaxY()));
				
			}

			return x2dDocument;
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) {
		try {
			View2D view2D = new View2D();
			view2D.setSize(new Dimension(300, 200));

			Window2D w = new Window2D.Linear(-12.4, 22.9, -123, 234);
			w.setWindowID("myID");
			w.setName("myWindow name");

			view2D.registerWindow2D(w);

			X2DViewDeflater deflater = new X2DViewDeflater(view2D);

			Document x2d = deflater.deflate();

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			// transformerFactory.setAttribute("indent-number", 2);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(x2d);
			StreamResult result = new StreamResult(new File("C:/usr/x2d/view.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
