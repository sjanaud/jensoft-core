/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.tachometer.v1;

import com.jensoft.core.plugin.gauge.RadialGauge;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.Glass1;

public class V1Gauge extends RadialGauge {

    public V1Gauge() {
        super(0, 0, 100);

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

        V1Body b1 = new V1Body();
        setBody(b1);

        Glass1 effect = new Glass1();
        setEffect(effect);

        V1Constructor v1c = new V1Constructor();
        setConstructor(v1c);
        

    }



}
