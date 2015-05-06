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

import org.jensoft.core.view.background.ViewNoBackground;

/**
 * <code>JenSoftView</code>
 * 
 * @since 1.0
 * @author sebastien janaud
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface JenSoftView {	
	Dim dimension() default @Dim(width=600,height=400);
	Captcha[] captchas() default {};
	Class<?> background() default ViewNoBackground.class;
	Class<?>[] see() default {};
	String description() default "";
	boolean ignore() default false;
}
