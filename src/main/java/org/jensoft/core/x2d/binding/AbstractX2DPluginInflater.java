/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.AbstractPlugin;

/**
 * <code>AbstractX2DPluginInflater</code> Abstract inflate plug in operation
 * 
 * @author Sebastien Janaud
 */
public abstract class AbstractX2DPluginInflater<P extends AbstractPlugin> extends InflaterUtil {


	/**
	 * create inflater
	 */
	public AbstractX2DPluginInflater() {
	}

	public X2DBinding getBinding() {
		return getClass().getAnnotation(X2DBinding.class);
	}
	
	public String getXSIType(){
		return getBinding().xsi();
	}
	
	public Class getJavaType(){
		return getBinding().plugin();
	}
	
	protected Object getPluginInstance(){
		try {
			return getJavaType().newInstance();
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}
		return null;
	}

	/**
	 * inflate the T plugin mean read the given plugin root element and
	 * configure plugin
	 * 
	 * @param plugin
	 *            the element to inflate
	 */
	public abstract P inflate(Element plugin);

}
