/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.plot.spline;

/**
 * This is just like a natural cubic spline except there is also a cubic curve
 * joining the last and first control points.
 * 
 * @author Sebastien Janaud
 */
public class NatCubicClosed extends NatCubic {

    /*
     * calculates the closed natural cubic spline that interpolates x[0], x[1],
     * ... x[n] The first segment is returned as C[0].a + C[0].b*u + C[0].c*u^2
     * + C[0].d*u^3 0<=u <1 the other segments are in C[1], C[2], ... C[n]
     */

    @Override
    Cubic[] calcNaturalCubic(int n, double[] x) {
    	double[] w = new double[n + 1];
    	double[] v = new double[n + 1];
    	double[] y = new double[n + 1];
    	double[] D = new double[n + 1];
    	double z, F, G, H;
        int k;
        /*
         * We solve the equation [4 1 1] [D[0]] [3(x[1] - x[n]) ] |1 4 1 |
         * |D[1]| |3(x[2] - x[0]) | | 1 4 1 | | . | = | . | | ..... | | . | | .
         * | | 1 4 1| | . | |3(x[n] - x[n-2])| [1 1 4] [D[n]] [3(x[0] - x[n-1])]
         * by decomposing the matrix into upper triangular and lower matrices
         * and then back sustitution. See Spath "Spline Algorithms for Curves
         * and Surfaces" pp 19--21. The D[i] are the derivatives at the knots.
         */
        w[1] = v[1] = z = 1.0f / 4.0f;
        y[0] = z * 3 * (x[1] - x[n]);
        H = 4;
        F = 3 * (x[0] - x[n - 1]);
        G = 1;
        for (k = 1; k < n; k++) {
            v[k + 1] = z = 1 / (4 - v[k]);
            w[k + 1] = -z * w[k];
            y[k] = z * (3 * (x[k + 1] - x[k - 1]) - y[k - 1]);
            H = H - G * w[k];
            F = F - G * y[k - 1];
            G = -v[k] * G;
        }
        H = H - (G + 1) * (v[n] + w[n]);
        y[n] = F - (G + 1) * y[n - 1];

        D[n] = y[n] / H;
        D[n - 1] = y[n - 1] - (v[n] + w[n]) * D[n]; /*
                                                     * This equation is WRONG!
                                                     * in my copy of Spath
                                                     */
        for (k = n - 2; k >= 0; k--) {
            D[k] = y[k] - v[k + 1] * D[k + 1] - w[k + 1] * D[n];
        }

        /* now compute the coefficients of the cubics */
        Cubic[] C = new Cubic[n + 1];
        for (k = 0; k < n; k++) {
            C[k] = new Cubic(x[k], D[k], 3 * (x[k + 1] - x[k]) - 2
                    * D[k] - D[k + 1], 2 * (x[k] - x[k + 1]) + D[k] + D[k + 1]);
        }
        C[n] = new Cubic(x[n], D[n], 3 * (x[0] - x[n]) - 2 * D[n]
                - D[0], 2 * (x[n] - x[0]) + D[n] + D[0]);
        return C;
    }

}
