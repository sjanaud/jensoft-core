/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.catalog.nature;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *<code>New/code>
 * 
 * @author Sebastien Janaud
 */
@Documented
@Target({ ElementType.PACKAGE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface New {
	String comment();
}
