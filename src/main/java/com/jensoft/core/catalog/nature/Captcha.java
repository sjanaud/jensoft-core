/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.catalog.nature;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jensoft.core.view.background.ViewNoBackground;

/**
 * <code>JenSoftView</code>
 * 
 * @author Sebastien Janaud
 */
@Documented
@Target({  })
@Retention(RetentionPolicy.RUNTIME)
public @interface Captcha {	
	String question() ;
	String anwser();
	Dim dimension() default @Dim(width=600,height=400);
	Class background() default ViewNoBackground.class;
}
