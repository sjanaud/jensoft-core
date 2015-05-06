/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.analysis;

import java.io.Serializable;
import java.util.Arrays;

/**
 * <code>PolynomialFunction</code>
 */
public class PolynomialFunction implements
        DifferentiableUnivariateRealFunction, Serializable {

    /**
     * Serialization identifier
     */
    private static final long serialVersionUID = -7726511984200295583L;

    /**
     * The coefficients of the polynomial, ordered by degree -- i.e.,
     * coefficients[0] is the constant term and coefficients[n] is the
     * coefficient of x^n where n is the degree of the polynomial.
     */
    private final double coefficients[];

    /**
     * Construct a polynomial with the given coefficients. The first element of
     * the coefficients array is the constant term. Higher degree coefficients
     * follow in sequence. The degree of the resulting polynomial is the index
     * of the last non-null element of the array, or 0 if all elements are null.
     * <p>
     * The constructor makes a copy of the input array and assigns the copy to the coefficients property.
     * </p>
     * 
     * @param c
     *            polynomial coefficients
     * @throws NullPointerException
     *             if c is null
     */
    public PolynomialFunction(double c[]) {
        super();
        int n = c.length;
        if (n == 0) {
            throw new IllegalArgumentException(
                                               "coefficients should be supplied");
        }
        while (n > 1 && c[n - 1] == 0) {
            --n;
        }
        coefficients = new double[n];
        System.arraycopy(c, 0, coefficients, 0, n);
    }

    /**
     * Compute the value of the function for the given argument.
     * <p>
     * The value returned is <br>
     * <code>coefficients[n] * x^n + ... + coefficients[1] * x  + coefficients[0]</code>
     * </p>
     * 
     * @param x
     *            the argument for which the function value should be computed
     * @return the value of the polynomial at the given point
     * @see UnivariateRealFunction#value(double)
     */
    @Override
    public double value(double x) {
        return evaluate(coefficients, x);
    }

    /**
     * Returns the degree of the polynomial
     * 
     * @return the degree of the polynomial
     */
    public int degree() {
        return coefficients.length - 1;
    }

    /**
     * Returns a copy of the coefficients array.
     * <p>
     * Changes made to the returned copy will not affect the coefficients of the polynomial.
     * </p>
     * 
     * @return a fresh copy of the coefficients array
     */
    public double[] getCoefficients() {
        return coefficients.clone();
    }

    /**
     * Uses Horner's Method to evaluate the polynomial with the given
     * coefficients at the argument.
     * 
     * @param coefficients
     *            the coefficients of the polynomial to evaluate
     * @param argument
     *            the input value
     * @return the value of the polynomial
     * @throws NullPointerException
     *             if coefficients is null
     */
    protected static double evaluate(double[] coefficients, double argument) {
        int n = coefficients.length;
        if (n == 0) {
            throw new IllegalArgumentException(
                                               "coefficients should be supplied");
        }
        double result = coefficients[n - 1];
        for (int j = n - 2; j >= 0; j--) {
            result = argument * result + coefficients[j];
        }
        return result;
    }

    /**
     * Add a polynomial to the instance.
     * 
     * @param p
     *            polynomial to add
     * @return a new polynomial which is the sum of the instance and p
     */
    public PolynomialFunction add(final PolynomialFunction p) {

        // identify the lowest degree polynomial
        final int lowLength = min(coefficients.length, p.coefficients.length);
        final int highLength = max(coefficients.length, p.coefficients.length);

        // build the coefficients array
        double[] newCoefficients = new double[highLength];
        for (int i = 0; i < lowLength; ++i) {
            newCoefficients[i] = coefficients[i] + p.coefficients[i];
        }
        System.arraycopy(
                         coefficients.length < p.coefficients.length ? p.coefficients
                                 : coefficients, lowLength, newCoefficients, lowLength,
                         highLength - lowLength);

        return new PolynomialFunction(newCoefficients);

    }

    /**
     * Subtract a polynomial from the instance.
     * 
     * @param p
     *            polynomial to subtract
     * @return a new polynomial which is the difference the instance minus p
     */
    public PolynomialFunction subtract(final PolynomialFunction p) {

        // identify the lowest degree polynomial
        int lowLength = min(coefficients.length, p.coefficients.length);
        int highLength = max(coefficients.length, p.coefficients.length);

        // build the coefficients array
        double[] newCoefficients = new double[highLength];
        for (int i = 0; i < lowLength; ++i) {
            newCoefficients[i] = coefficients[i] - p.coefficients[i];
        }
        if (coefficients.length < p.coefficients.length) {
            for (int i = lowLength; i < highLength; ++i) {
                newCoefficients[i] = -p.coefficients[i];
            }
        }
        else {
            System.arraycopy(coefficients, lowLength, newCoefficients,
                             lowLength, highLength - lowLength);
        }

        return new PolynomialFunction(newCoefficients);

    }

    /**
     * Negate the instance.
     * 
     * @return a new polynomial
     */
    public PolynomialFunction negate() {
        double[] newCoefficients = new double[coefficients.length];
        for (int i = 0; i < coefficients.length; ++i) {
            newCoefficients[i] = -coefficients[i];
        }
        return new PolynomialFunction(newCoefficients);
    }

    /**
     * Multiply the instance by a polynomial.
     * 
     * @param p
     *            polynomial to multiply by
     * @return a new polynomial
     */
    public PolynomialFunction multiply(final PolynomialFunction p) {

        double[] newCoefficients = new double[coefficients.length
                + p.coefficients.length - 1];

        for (int i = 0; i < newCoefficients.length; ++i) {
            newCoefficients[i] = 0.0;
            for (int j = max(0, i + 1 - p.coefficients.length); j < min(
                                                                        coefficients.length, i + 1); ++j) {
                newCoefficients[i] += coefficients[j] * p.coefficients[i - j];
            }
        }

        return new PolynomialFunction(newCoefficients);

    }

    /**
     * Returns the coefficients of the derivative of the polynomial with the
     * given coefficients.
     * 
     * @param coefficients
     *            the coefficients of the polynomial to differentiate
     * @return the coefficients of the derivative or null if coefficients has
     *         length 1.
     * @throws NullPointerException
     *             if coefficients is null
     */
    protected static double[] differentiate(double[] coefficients) {
        int n = coefficients.length;
        if (n == 0) {
            throw new IllegalArgumentException(
                                               "coefficients should be supplied");
        }
        if (n == 1) {
            return new double[] { 0 };
        }
        double[] result = new double[n - 1];
        for (int i = n - 1; i > 0; i--) {
            result[i - 1] = i * coefficients[i];
        }
        return result;
    }

    /**
     * Returns the derivative as a PolynomialRealFunction
     * 
     * @return the derivative polynomial
     */
    public PolynomialFunction polynomialDerivative() {
        return new PolynomialFunction(differentiate(coefficients));
    }

    /**
     * Returns the derivative as a UnivariateRealFunction
     * 
     * @return the derivative function
     */
    @Override
    public UnivariateRealFunction derivative() {
        return polynomialDerivative();
    }

    /**
     * Returns a string representation of the polynomial.
     * <p>
     * The representation is user oriented. Terms are displayed lowest degrees first. The multiplications signs,
     * coefficients equals to one and null terms are not displayed (except if the polynomial is 0, in which case the 0
     * constant term is displayed). Addition of terms with negative coefficients are replaced by subtraction of terms
     * with positive coefficients except for the first displayed term (i.e. we display <code>-3</code> for a constant
     * negative polynomial, but <code>1 - 3 x + x^2</code> if the negative coefficient is not the first one displayed).
     * </p>
     * 
     * @return a string representation of the polynomial
     */
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        if (coefficients[0] == 0.0) {
            if (coefficients.length == 1) {
                return "0";
            }
        }
        else {
            s.append(Double.toString(coefficients[0]));
        }

        for (int i = 1; i < coefficients.length; ++i) {

            if (coefficients[i] != 0) {

                if (s.length() > 0) {
                    if (coefficients[i] < 0) {
                        s.append(" - ");
                    }
                    else {
                        s.append(" + ");
                    }
                }
                else {
                    if (coefficients[i] < 0) {
                        s.append("-");
                    }
                }

                double absAi = abs(coefficients[i]);
                if (absAi - 1 != 0) {
                    s.append(Double.toString(absAi));
                    s.append(' ');
                }

                s.append("x");
                if (i > 1) {
                    s.append('^');
                    s.append(Integer.toString(i));
                }
            }

        }

        return s.toString();

    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(coefficients);
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PolynomialFunction)) {
            return false;
        }
        PolynomialFunction other = (PolynomialFunction) obj;
        if (!Arrays.equals(coefficients, other.coefficients)) {
            return false;
        }
        return true;
    }

    /**
     * Compute the minimum of two values
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return a if a is lesser or equal to b, b otherwise
     */
    public static int min(final int a, final int b) {
        return a <= b ? a : b;
    }

    /**
     * Compute the minimum of two values
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return a if a is lesser or equal to b, b otherwise
     */
    public static long min(final long a, final long b) {
        return a <= b ? a : b;
    }

    /**
     * Compute the minimum of two values
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return a if a is lesser or equal to b, b otherwise
     */
    public static float min(final float a, final float b) {
        if (a > b) {
            return b;
        }
        if (a < b) {
            return a;
        }
        /* if either arg is NaN, return NaN */
        if (a != b) {
            return Float.NaN;
        }
        /* min(+0.0,-0.0) == -0.0 */
        /* 0x80000000 == Float.floatToRawIntBits(-0.0d) */
        int bits = Float.floatToRawIntBits(a);
        if (bits == 0x80000000) {
            return a;
        }
        return b;
    }

    /**
     * Compute the minimum of two values
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return a if a is lesser or equal to b, b otherwise
     */
    public static double min(final double a, final double b) {
        if (a > b) {
            return b;
        }
        if (a < b) {
            return a;
        }
        /* if either arg is NaN, return NaN */
        if (a != b) {
            return Double.NaN;
        }
        /* min(+0.0,-0.0) == -0.0 */
        /* 0x8000000000000000L == Double.doubleToRawLongBits(-0.0d) */
        long bits = Double.doubleToRawLongBits(a);
        if (bits == 0x8000000000000000L) {
            return a;
        }
        return b;
    }

    /**
     * Compute the maximum of two values
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return b if a is lesser or equal to b, a otherwise
     */
    public static int max(final int a, final int b) {
        return a <= b ? b : a;
    }

    /**
     * Compute the maximum of two values
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return b if a is lesser or equal to b, a otherwise
     */
    public static long max(final long a, final long b) {
        return a <= b ? b : a;
    }

    /**
     * Compute the maximum of two values
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return b if a is lesser or equal to b, a otherwise
     */
    public static float max(final float a, final float b) {
        if (a > b) {
            return a;
        }
        if (a < b) {
            return b;
        }
        /* if either arg is NaN, return NaN */
        if (a != b) {
            return Float.NaN;
        }
        /* min(+0.0,-0.0) == -0.0 */
        /* 0x80000000 == Float.floatToRawIntBits(-0.0d) */
        int bits = Float.floatToRawIntBits(a);
        if (bits == 0x80000000) {
            return b;
        }
        return a;
    }

    /**
     * Compute the maximum of two values
     * 
     * @param a
     *            first value
     * @param b
     *            second value
     * @return b if a is lesser or equal to b, a otherwise
     */
    public static double max(final double a, final double b) {
        if (a > b) {
            return a;
        }
        if (a < b) {
            return b;
        }
        /* if either arg is NaN, return NaN */
        if (a != b) {
            return Double.NaN;
        }
        /* min(+0.0,-0.0) == -0.0 */
        /* 0x8000000000000000L == Double.doubleToRawLongBits(-0.0d) */
        long bits = Double.doubleToRawLongBits(a);
        if (bits == 0x8000000000000000L) {
            return b;
        }
        return a;
    }

    /**
     * Absolute value.
     * 
     * @param x
     *            number from which absolute value is requested
     * @return abs(x)
     */
    public static int abs(final int x) {
        return x < 0 ? -x : x;
    }

    /**
     * Absolute value.
     * 
     * @param x
     *            number from which absolute value is requested
     * @return abs(x)
     */
    public static long abs(final long x) {
        return x < 0l ? -x : x;
    }

    /**
     * Absolute value.
     * 
     * @param x
     *            number from which absolute value is requested
     * @return abs(x)
     */
    public static float abs(final float x) {
        return x < 0.0f ? -x : x == 0.0f ? 0.0f : x; // -0.0 => +0.0
    }

    /**
     * Absolute value.
     * 
     * @param x
     *            number from which absolute value is requested
     * @return abs(x)
     */
    public static double abs(double x) {
        return x < 0.0 ? -x : x == 0.0 ? 0.0 : x; // -0.0 => +0.0
    }

}
