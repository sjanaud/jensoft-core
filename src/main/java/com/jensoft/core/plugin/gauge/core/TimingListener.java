/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.core;

import java.util.EventListener;

/**
 * @version 1.0
 */
public interface TimingListener extends EventListener {

    public void engineStart();

    public void engineStop();

    public void startCycle();

    public void stopCycle();

    public void timingEvent(float fraction);
}
