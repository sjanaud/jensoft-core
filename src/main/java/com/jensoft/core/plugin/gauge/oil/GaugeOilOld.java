/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.gauge.oil;

import com.jensoft.core.plugin.gauge.core.RadialGauge;
import com.jensoft.core.plugin.gauge.core.env.CiseroEnvelop;
import com.jensoft.core.plugin.gauge.core.glass.GaugeGlass;

public class GaugeOilOld extends RadialGauge {

    public GaugeOilOld() {
        super(0, 0, 90);

        CiseroEnvelop e1 = new CiseroEnvelop();
        setEnvelop(e1);

      

        GaugeGlass g1 = new GaugeGlass.Glass1();
        GaugeGlass g2 = new GaugeGlass.Glass2();
        GaugeGlass g3 = new GaugeGlass.Glass3();
        GaugeGlass g4 = new GaugeGlass.Glass4();
        GaugeGlass g5 = new GaugeGlass.Donut2DGlass();
        GaugeGlass g6 = new GaugeGlass.GlassLabel();
        
        GaugeGlass g8 = new GaugeGlass.GlassLinearEffect();
       
        
        addEffect(g1,g8);
       
       
    }


}
