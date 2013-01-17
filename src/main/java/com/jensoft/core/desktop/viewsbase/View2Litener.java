/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.desktop.viewsbase;

import java.util.EventListener;

public interface View2Litener extends EventListener {

    public void viewSelect(View2Event groupEvent);
}
