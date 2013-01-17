/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.view;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * <code>Portfolio</code> annotation help developer to produce image on static
 * method which returns {@link View2D}
 * 
 * @author Sebastien Janaud
 */
@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Portfolio {

    public enum ImageType {
        PNG, GIF, JPEG
    };

    ImageType imageType() default ImageType.PNG;

    String name();

    int width() default 400;

    int height() default 300;
}
