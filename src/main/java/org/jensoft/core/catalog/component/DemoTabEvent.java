/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.catalog.component;

import java.util.EventObject;

public class DemoTabEvent extends EventObject {

    public DemoTabEvent(DemoTab demoTab) {
        super(demoTab);
    }
}
