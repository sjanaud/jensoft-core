/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.view.View;
import org.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.X2DViewDeflater;
import org.jensoft.core.x2d.binding.X2DViewInflater;
import org.jensoft.core.x2d.binding.donut2d.Donut2DDeflater;
import org.jensoft.core.x2d.binding.donut2d.Donut2DInflater;
import org.jensoft.core.x2d.binding.donut3d.Donut3DDeflater;
import org.jensoft.core.x2d.binding.donut3d.Donut3DInflater;
import org.jensoft.core.x2d.binding.function.AreaFunctionInflater;
import org.jensoft.core.x2d.binding.function.CurveFunctionInflater;
import org.jensoft.core.x2d.binding.function.ScatterFunctionInflater;
import org.jensoft.core.x2d.binding.grid.GridInflater;
import org.jensoft.core.x2d.binding.legend.LegendInflater;
import org.jensoft.core.x2d.binding.metrics.AxisMetricsInflater;
import org.jensoft.core.x2d.binding.outline.OutlineDeflater;
import org.jensoft.core.x2d.binding.outline.OutlineInflater;
import org.jensoft.core.x2d.binding.pie.PieDeflater;
import org.jensoft.core.x2d.binding.pie.PieInflater;
import org.jensoft.core.x2d.binding.translate.TranslateInflater;
import org.jensoft.core.x2d.binding.zoom.ZoomBoxInflater;
import org.jensoft.core.x2d.binding.zoom.ZoomObjectifInflater;
import org.jensoft.core.x2d.binding.zoom.ZoomWheelInflater;
import org.jensoft.core.x2d.lang.X2DSchema;
import org.jensoft.core.x2d.lang.X2DSchemaErrorHandler;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * <code>X2D</code>
 * <p>
 * takes the responsibility to register X2D source and produce {@link View}
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class X2D {

	/** observer clients errors handlers */
	private List<X2DErrorHandler> errorHandlers;

	/** x2d template document */
	private Document x2dDocument;

	/** X2D inflater */
	private X2DViewInflater x2dInflater;

	/** X2D deflater */
	private X2DViewDeflater x2dDeflater;

	/** the view */
	private View view;

	/** core annotated inflaters */
	private List<AbstractX2DPluginInflater<?>> coreInflaters = new ArrayList<AbstractX2DPluginInflater<?>>();

	/** core annotated deflaters */
	private List<AbstractX2DPluginDeflater<? extends AbstractPlugin>> coreDeflaters = new ArrayList<AbstractX2DPluginDeflater<? extends AbstractPlugin>>();

	/** user inflaters */
	private List<AbstractX2DPluginInflater<? extends AbstractPlugin>> inflaters = new ArrayList<AbstractX2DPluginInflater<? extends AbstractPlugin>>();

	/** user deflaters */
	private List<AbstractX2DPluginDeflater<? extends AbstractPlugin>> deflaters = new ArrayList<AbstractX2DPluginDeflater<? extends AbstractPlugin>>();

	/** lookup native core inflaters */
	private boolean lookupCoreInflaters = true;

	/** lookup native core deflaters */
	private boolean lookupCoreDeflaters = true;

	/**
	 * create <code>X2D</code>
	 */
	public X2D() {
		initHandlers();
		initCoreInflaters();
		initCoreDeflaters();
	}

	/**
	 * initialize errors handlers related objects
	 */
	private void initHandlers() {
		errorHandlers = new ArrayList<X2DErrorHandler>();
	}

	/**
	 * initialize core inflaters
	 */
	private void initCoreInflaters() {
		// System.err.println("--X2D initCoreInflater--");
		// List<Class<?>> inflaters =
		// PluginPlatform.scanX2DInflater(X2DInflater.class.getPackage().getName());
		// System.err.println("--X2D found --:"+inflaters.size());
		// for (Class<?> inflaterClass : inflaters) {
		// try {
		// AbstractX2DPluginInflater<?> inflater =
		// (AbstractX2DPluginInflater<?>) inflaterClass.newInstance();
		// coreInflaters.add(inflater);
		// }
		// catch (InstantiationException e) {
		// e.printStackTrace();
		// }
		// catch (IllegalAccessException e) {
		// e.printStackTrace();
		// }
		// }

		// scan trows file permission exception in applet mode, via findClasses
		// in patlformPlugin
		// TODO find proper way to scan annotation in applet mode (via url and
		// not file maybe!)

		coreInflaters.add(new OutlineInflater());

		// legend
		coreInflaters.add(new LegendInflater());

		// pie - donut2D - donut3D
		coreInflaters.add(new Donut3DInflater());
		coreInflaters.add(new Donut2DInflater());
		coreInflaters.add(new PieInflater());

		// metrics
		coreInflaters.add(new AxisMetricsInflater.FreeMetricsInflater());
		coreInflaters.add(new AxisMetricsInflater.FlowMetricsInflater());
		coreInflaters.add(new AxisMetricsInflater.StaticMetricsInflater());
		coreInflaters.add(new AxisMetricsInflater.ModeledMetricsInflater());
		coreInflaters.add(new AxisMetricsInflater.TimeMetricsInflater());

		// grids
		coreInflaters.add(new GridInflater.FreeGridInflater());
		coreInflaters.add(new GridInflater.FlowGridInflater());
		coreInflaters.add(new GridInflater.StaticGridInflater());
		coreInflaters.add(new GridInflater.ModeledGridInflater());
		coreInflaters.add(new GridInflater.MultiplierGridInflater());
		// registerInflater(new GridInflater.TimeGridInflater());

		// functions
		coreInflaters.add(new CurveFunctionInflater());
		coreInflaters.add(new AreaFunctionInflater());
		coreInflaters.add(new ScatterFunctionInflater());

		// zooms
		coreInflaters.add(new ZoomWheelInflater());
		coreInflaters.add(new ZoomBoxInflater());
		coreInflaters.add(new ZoomObjectifInflater());

		// translate
		coreInflaters.add(new TranslateInflater());
	}

	/**
	 * initialize core deflaters
	 */
	private void initCoreDeflaters() {
		coreDeflaters.add(new OutlineDeflater());
		coreDeflaters.add(new PieDeflater());
		coreDeflaters.add(new Donut2DDeflater());
		coreDeflaters.add(new Donut3DDeflater());
		
	}

	/**
	 * @return the lookupCoreInflaters
	 */
	public boolean isLookupCoreInflaters() {
		return lookupCoreInflaters;
	}

	/**
	 * @param lookupCoreInflaters
	 *            the lookupCoreInflaters to set
	 */
	public void setLookupCoreInflaters(boolean lookupCoreInflaters) {
		this.lookupCoreInflaters = lookupCoreInflaters;
	}

	/**
	 * @return the lookupCoreDeflaters
	 */
	public boolean isLookupCoreDeflaters() {
		return lookupCoreDeflaters;
	}

	/**
	 * @param lookupCoreDeflaters
	 *            the lookupCoreDeflaters to set
	 */
	public void setLookupCoreDeflaters(boolean lookupCoreDeflaters) {
		this.lookupCoreDeflaters = lookupCoreDeflaters;
	}

	/**
	 * add jet error handler
	 * 
	 * @param handler
	 *            the handler to add
	 */
	public void addX2DErrorHandler(X2DErrorHandler handler) {
		errorHandlers.add(handler);
	}

	/**
	 * register the source template
	 * 
	 * @param xmlSource
	 *            the xml source as string
	 */
	public void registerX2DSource(String xmlSource) throws X2DException {

		X2DSchemaErrorHandler errorHandler = new X2DSchemaErrorHandler();
		try {

			X2DSchema.validX2D(xmlSource, errorHandler);
			if (!errorHandler.hasErrors()) {
				x2dDocument = X2DSchema.parseX2D(xmlSource);
				x2dDocument.normalize();
				x2dInflater = new X2DViewInflater();
				x2dInflater.getInflaters().addAll(inflaters);
				if (lookupCoreInflaters) {
					x2dInflater.getInflaters().addAll(coreInflaters);
				}
				x2dInflater.setX2D(x2dDocument);
				view = x2dInflater.inflate();
			} else {
				X2DException x2dException = new X2DException("X2D could not register template source , see exception errors.");

				x2dException.setErrors(errorHandler.getErrors());
				throw x2dException;
			}

		} catch (SAXException e) {
			throw new X2DException(e);
		} catch (IOException e) {
			throw new X2DException(e);
		} catch (ParserConfigurationException e) {
			throw new X2DException(e);
		}
	}

	/**
	 * register the source template
	 * 
	 * @param xmlDocument
	 *            the xml source document
	 */
	public void registerX2DDocument(Document xmlDocument) throws X2DException {
		//System.out.println("register document");
		X2DSchemaErrorHandler errorHandler = new X2DSchemaErrorHandler();

		X2DSchema.validX2D(xmlDocument, errorHandler);
		if (!errorHandler.hasErrors()) {
			x2dDocument = xmlDocument;
			x2dDocument.normalize();
			x2dInflater = new X2DViewInflater();
			x2dInflater.getInflaters().addAll(inflaters);
			if (lookupCoreInflaters) {
				x2dInflater.getInflaters().addAll(coreInflaters);
			}
			x2dInflater.setX2D(x2dDocument);
			view = x2dInflater.inflate();
		} else {
			X2DException x2dException = new X2DException("X2D could not register template source , see exception errors.");
			x2dException.setErrors(errorHandler.getErrors());
			throw x2dException;
		}

	}

	/**
	 * register the new x2d file template
	 * 
	 * @param x2dFile
	 */
	public void registerX2DFile(File x2dFile) throws X2DException {

		X2DSchemaErrorHandler errorHandler = new X2DSchemaErrorHandler();
		try {

			X2DSchema.validX2D(x2dFile, errorHandler);
			if (!errorHandler.hasErrors()) {
				x2dDocument = X2DSchema.parseX2D(x2dFile);
				x2dDocument.normalize();
				x2dInflater = new X2DViewInflater();
				x2dInflater.getInflaters().addAll(inflaters);
				if (lookupCoreInflaters) {
					x2dInflater.getInflaters().addAll(coreInflaters);
				}
				x2dInflater.setX2D(x2dDocument);
				view = x2dInflater.inflate();
			} else {
				X2DException x2dException = new X2DException("X2D could not register template file " + x2dFile.getName() + ", see exception errors.");
				x2dException.setErrors(errorHandler.getErrors());
				throw x2dException;
			}

		} catch (SAXException e) {
			throw new X2DException(e);
		} catch (IOException e) {
			throw new X2DException(e);
		} catch (ParserConfigurationException e) {
			throw new X2DException(e);
		}

		//System.out.println("X2D : register x2d template : " + x2dFile.getName() + " completed");
	}

	/**
	 * register the view in x2d
	 * 
	 * @param view
	 */
	public void registerView(View view) throws X2DException {
		this.view = view;
		x2dDeflater = new X2DViewDeflater(view);
		x2dDeflater.getDeflaters().addAll(deflaters);
		if (lookupCoreDeflaters) {
			x2dDeflater.getDeflaters().addAll(coreDeflaters);
		}
		x2dDeflater.setView(view);
		x2dDocument=x2dDeflater.deflate();
	}

	/**
	 * register the new x2d input stream
	 * 
	 * @param x2dInputStream
	 */
	public void registerX2D(InputStream x2dInputStream) throws X2DException {
		if (x2dInputStream == null)
			throw new X2DException("X2D input stream cannot be null.");

		X2DSchemaErrorHandler errorHandler = new X2DSchemaErrorHandler();
		try {

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = x2dInputStream.read(buffer)) > -1) {
				baos.write(buffer, 0, len);
			}
			baos.flush();

			InputStream is1 = new ByteArrayInputStream(baos.toByteArray());
			InputStream is2 = new ByteArrayInputStream(baos.toByteArray());

			x2dInputStream.close();
			baos.close();

			X2DSchema.validX2D(is1, errorHandler);
			if (!errorHandler.hasErrors()) {
				x2dDocument = X2DSchema.parseX2D(is2);
				x2dDocument.normalize();
				x2dInflater = new X2DViewInflater();
				x2dInflater.getInflaters().addAll(inflaters);
				if (lookupCoreInflaters) {
					x2dInflater.getInflaters().addAll(coreInflaters);
				}
				x2dInflater.setX2D(x2dDocument);
				view = x2dInflater.inflate();
				is1.close();
				is2.close();
			} else {
				X2DException x2dException = new X2DException("X2D could not register input stream template file " + ", see exception errors.");
				x2dException.setErrors(errorHandler.getErrors());
				throw x2dException;
			}

		} catch (SAXException e) {
			throw new X2DException(e);
		} catch (IOException e) {
			throw new X2DException(e);
		} catch (ParserConfigurationException e) {
			throw new X2DException(e);
		}

		//System.out.println("X2D : register x2d input stream template  completed");
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
		inflaters.add(inflater);
	}

	/**
	 * @return the x2dDocument
	 */
	public Document getX2dDocument() {
		return x2dDocument;
	}

	/**
	 * @return the view2D
	 */
	public View getView() {
		return view;
	}
	
	/**
	 * @return the view2D width
	 */
	public int getX2DViewWidth() {
		return x2dInflater.getWidth();
	}
	
	/**
	 * @return the view2D height
	 */
	public int getX2DViewHeight() {
		return x2dInflater.getHeight();
	}

	/**
	 * get view key
	 * 
	 * @return view key
	 */
	public String getViewKey() {
		return x2dInflater.getViewKey();
	}

	/**
	 * get view key
	 * 
	 * @return view key
	 */
	public String getApiKey() {
		return x2dInflater.getAPIKey();
	}

}
