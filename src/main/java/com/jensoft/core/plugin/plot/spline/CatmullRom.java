/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.plot.spline;

/**
 * <code>CatmullRom</code> spline
 * @author Sebastien Janaud
 *
 */
public class CatmullRom extends BSpline {

    
	/**
	 * Catmull-Rom spline is like a B spline, only with a different basis
	 */
    @Override
    float b(int i, float t) {
        switch (i) {
            case -2:
                return ((-t + 2) * t - 1) * t / 2;
            case -1:
                return ((3 * t - 5) * t * t + 2) / 2;
            case 0:
                return ((-3 * t + 4) * t + 1) * t / 2;
            case 1:
                return (t - 1) * t * t / 2;
        }
        return 0; // we only get here if an invalid i is specified
    }

}
