/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.gauge.velocity.v1;

import java.awt.Color;
import java.util.Random;

import com.jensoft.core.gauge.core.RadialGauge;
import com.jensoft.core.gauge.envelop.CiseroEnvelop;
import com.jensoft.core.gauge.glass.Glass1;
import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;
import com.jensoft.core.glyphmetrics.GeneralMetricsPath;
import com.jensoft.core.glyphmetrics.GlyphMetric;
import com.jensoft.core.glyphmetrics.GlyphMetricsNature;
import com.jensoft.core.glyphmetrics.StylePosition;
import com.jensoft.core.glyphmetrics.painter.fill.GlyphFill;
import com.jensoft.core.glyphmetrics.painter.marker.TicTacMarker;
import com.jensoft.core.palette.InputFonts;
import com.jensoft.core.palette.PetalPalette;

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
