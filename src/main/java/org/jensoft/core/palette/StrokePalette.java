/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.palette;

import java.awt.BasicStroke;
import java.awt.Stroke;

/**
 * <code>StrokePalette</code>
 * 
 * @author Sebastien Janaud
 */
public class StrokePalette {

    public static Stroke drawingStroke1 = new BasicStroke(3,
                                                          BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                                                          new float[] { 9 },
                                                          0);
    public static Stroke drawingStroke2 = new BasicStroke(3,
                                                          BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 50,
                                                          new float[] { 9 }, 0);
    public static Stroke drawingStroke3 = new BasicStroke(1,
                                                          BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0,
                                                          new float[] { 9 },
                                                          0);
    public static Stroke drawingStroke4 = new BasicStroke(3,
                                                          BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
                                                          new float[] { 9 }, 0);

    public static Stroke drawingStroke5 = new BasicStroke(1,
                                                          BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
                                                          new float[] { 21,
                                                                  9, 3, 9 }, 0);

    public static Stroke drawingStroke6 = new BasicStroke(1,
                                                          BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
                                                          new float[] { 10,
                                                                  5, 3, 5 }, 0);

    public static Stroke drawingStroke7 = new BasicStroke(3,
                                                          BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
                                                          new float[] { 10,
                                                                  5, 3, 5 }, 0);
    public static Stroke drawingStroke8 = new BasicStroke(1.5f,
                                                          BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0,
                                                          new float[] { 20,
                                                                  5, 10, 5, 5, 5, 2, 5 }, 0);

}
