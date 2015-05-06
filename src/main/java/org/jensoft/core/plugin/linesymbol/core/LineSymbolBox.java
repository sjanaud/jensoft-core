/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.linesymbol.core;

public class LineSymbolBox extends LineSymbolComponent {

    private FillerAxis fillerAxis;
    private FillerType fillerType;

    public enum FillerAxis {
        YAxis, XAxis;
    };

    public enum FillerType {
        Glue, Strut;
    };

    public FillerAxis getFillerAxis() {
        return fillerAxis;
    }

    public void setFillerAxis(FillerAxis fillerAxis) {
        this.fillerAxis = fillerAxis;
    }

    public FillerType getFillerType() {
        return fillerType;
    }

    public void setFillerType(FillerType fillerType) {
        this.fillerType = fillerType;
    }

    public static LineSymbolBox createHorizontalStrut(double strut) {
        LineSymbolBox boxStrut = new LineSymbolBox();
        boxStrut.setName("LineBox.horizontal.Strut." + strut);
        boxStrut.setThickness(strut);
        boxStrut.setFillerType(FillerType.Strut);
        boxStrut.setFillerAxis(FillerAxis.XAxis);
        return boxStrut;
    }

    public static LineSymbolComponent createHorizontalGlue() {
        LineSymbolBox bGlue = new LineSymbolBox();
        bGlue.setName("LineBox.horizontal.Glue");
        bGlue.setFillerType(FillerType.Glue);
        bGlue.setFillerAxis(FillerAxis.XAxis);
        return bGlue;
    }

    public static LineSymbolComponent createVerticalStrut(double strut) {
        LineSymbolBox boxStrut = new LineSymbolBox();
        boxStrut.setName("LineBox.vertical.Strut." + strut);
        boxStrut.setThickness(strut);
        boxStrut.setFillerType(FillerType.Strut);
        boxStrut.setFillerAxis(FillerAxis.YAxis);
        return boxStrut;
    }

    public static LineSymbolComponent createVerticalGlue() {
        LineSymbolBox bGlue = new LineSymbolBox();
        bGlue.setName("LineBox.vertical.Glue");
        bGlue.setFillerType(FillerType.Glue);
        bGlue.setFillerAxis(FillerAxis.YAxis);
        return bGlue;
    }

}
