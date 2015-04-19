/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.jensoft.core.plugin.function.analysis.AnalysisException;
import com.jensoft.core.plugin.function.analysis.SimpleRegression;
import com.jensoft.core.plugin.function.analysis.SplineInterpolator;
import com.jensoft.core.plugin.function.analysis.UnivariateRealFunction;
import com.jensoft.core.plugin.function.analysis.UnivariateRealInterpolator;

/**
 * <code>UserSourceFunction</code> defines kind of sources with user inputs like
 * x or y values and sample increment to make some interpolation.
 * <p>
 * You can use 3 type of user source function
 * </p>
 * <ul>
 * <li>link {@link LineSource}</li>
 * <li>link {@link SplineSource}</li>
 * <li>link {@link RegressionSource}</li>
 * </ul>
 * 
 * @author sebastien janaud
 * 
 */
public abstract class UserSourceFunction extends AbstractSourceFunction {

	/**
	 * create user source x function
	 */
	public UserSourceFunction() {
		super();
	}

	/**
	 * create user source function with the given nature
	 * 
	 * @param nature
	 *            the function nature
	 */
	public UserSourceFunction(FunctionNature nature) {
		super(nature);
	}

	/**
	 * <code>LineSourceFunction</code> defines a source which is a sorted points
	 * set.
	 * 
	 * @author sebastien janaud
	 */
	public static class LineSource extends UserSourceFunction {

		/** source */
		private List<Point2D> source;

		/** comparator by x or y coordinate */
		private ValueComparator valueComparator = new ValueComparator();

		/**
		 * create source function with {@link FunctionNature#XFunction} nature.
		 * 
		 * @param source
		 *            the source function data points
		 */
		public LineSource(List<Point2D> source) {
			super();
			this.source = source;
			sortFunction();
		}

		/**
		 * create source function with {@link FunctionNature#XFunction} nature
		 * and specified points array
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values
		 */
		public LineSource(double[] xValues, double[] yValues) {
			super();
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function with {@link FunctionNature#XFunction} nature
		 * and specified points array
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values
		 */
		public LineSource(Date[] xValues, double[] yValues) {
			super();
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function with {@link FunctionNature#XFunction} nature
		 * and specified points array
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values
		 */
		public LineSource(double[] xValues, Date[] yValues) {
			super();
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function with {@link FunctionNature#XFunction} nature
		 * and specified points array
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values
		 */
		public LineSource(Double[] xValues, Double[] yValues) {
			super();
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function with {@link FunctionNature#XFunction} nature
		 * and specified points array
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values
		 */
		public LineSource(Date[] xValues, Double[] yValues) {
			super();
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function with {@link FunctionNature#XFunction} nature
		 * and specified points array
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values
		 */
		public LineSource(Double[] xValues, Date[] yValues) {
			super();
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function for given nature
		 * 
		 * @param source
		 *            the function source
		 * @param nature
		 *            the x or y function nature
		 */
		public LineSource(List<Point2D> source, FunctionNature nature) {
			super(nature);
			this.source = source;
			sortFunction();
		}

		/**
		 * create source function for given nature
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values *
		 * @param nature
		 *            the x or y function nature
		 */
		public LineSource(double[] xValues, double[] yValues, FunctionNature nature) {
			super(nature);
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function for given nature
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values *
		 * @param nature
		 *            the x or y function nature
		 */
		public LineSource(Date[] xValues, double[] yValues, FunctionNature nature) {
			super(nature);
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function for given nature
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values *
		 * @param nature
		 *            the x or y function nature
		 */
		public LineSource(double[] xValues, Date[] yValues, FunctionNature nature) {
			super(nature);
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function for given nature
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values *
		 * @param nature
		 *            the x or y function nature
		 */
		public LineSource(Double[] xValues, Double[] yValues, FunctionNature nature) {
			super(nature);
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function for given nature
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values *
		 * @param nature
		 *            the x or y function nature
		 */
		public LineSource(Date[] xValues, Double[] yValues, FunctionNature nature) {
			super(nature);
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * create source function for given nature
		 * 
		 * @param xValues
		 *            the given x array function values
		 * @param yValues
		 *            the given y array function values *
		 * @param nature
		 *            the x or y function nature
		 */
		public LineSource(Double[] xValues, Date[] yValues, FunctionNature nature) {
			super(nature);
			this.source = createPointsFromArray(xValues, yValues);
			sortFunction();
		}

		/**
		 * set source
		 * 
		 * @param source
		 *            the source to set
		 */
		public void setSource(List<Point2D> source) {
			this.source = source;
			clearCurrentFunction();
			sortFunction();
		}

		/**
		 * @return the source
		 */
		public List<Point2D> getSource() {
			return source;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.function.source.SourceFunction#select(double,
		 * double)
		 */
		@Override
		public List<Point2D> solveFunction(double start, double end) {
			List<Point2D> newFunction = new ArrayList<Point2D>();
			List<Point2D> source = getSource();
			if (FunctionNature.XFunction == getNature()) {
				for (Point2D p : source) {
					if (p.getX() >= start && p.getX() <= end) {
						newFunction.add(p);
					}
				}
			} else {
				for (Point2D p : source) {
					if (p.getY() >= start && p.getY() <= end) {
						newFunction.add(p);
					}
				}
			}

			if (newFunction.size() >= 1) {
				Point2D previous;
				Point2D next;
				if (FunctionNature.XFunction == getNature()) {
					previous = previous(newFunction.get(0).getX());
					next = next(newFunction.get(newFunction.size() - 1).getX());

				} else {
					previous = previous(newFunction.get(0).getY());
					next = next(newFunction.get(newFunction.size() - 1).getY());
				}
				if (previous != null && !previous.equals(newFunction.get(0))) {
					newFunction.add(0, previous);
				}
				if (next != null && !next.equals(newFunction.get(newFunction.size() - 1))) {
					newFunction.add(next);
				}
			} else {
				Point2D previous = previous(start);
				Point2D next = next(end);

				if (previous != null) {
					newFunction.add(0, previous);
				}
				if (next != null) {
					newFunction.add(next);
				}
			}

			return newFunction;
		}

		private Point2D next(double value) {
			List<Point2D> functionPoints = getSource();

			for (int i = 0; i < functionPoints.size(); i++) {
				Point2D p = functionPoints.get(i);
				if (FunctionNature.XFunction == getNature()) {
					if (p.getX() > value) {
						return p;
					}
				} else {
					if (p.getY() > value) {
						return p;
					}
				}
			}
			return null;
		}

		private Point2D previous(double value) {
			List<Point2D> functionPoints = getSource();
			for (int i = functionPoints.size() - 1; i >= 0; i--) {
				Point2D p = functionPoints.get(i);
				if (FunctionNature.XFunction == getNature()) {
					if (p.getX() < value) {
						return p;
					}
				} else {
					if (p.getY() < value) {
						return p;
					}
				}
			}

			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.function.source.SourceFunction#evaluate(double
		 * )
		 */
		@Override
		public Point2D evaluate(double value) {
			Point2D previous = previous(value);
			Point2D next = next(value);
			if (previous != null && next != null) {
				double coefficient = 0;
				double constant = 0;
				if (getNature() == FunctionNature.XFunction) {
					coefficient = (next.getY() - previous.getY()) / (next.getX() - previous.getX());
					constant = previous.getY() - coefficient * previous.getX();
					return new Point2D.Double(value, coefficient * value + constant);
				} else {
					coefficient = (next.getX() - previous.getX()) / (next.getY() - previous.getY());
					constant = previous.getX() - coefficient * previous.getY();
					return new Point2D.Double(coefficient * value + constant, value);
				}
			} else {
				return null;
			}
		}

		// /*
		// * (non-Javadoc)
		// *
		// * @see
		// com.jensoft.core.plugin.function.source.SourceFunction#getFunction()
		// */
		// @Override
		// public List<Point2D> getFunction() {
		// return source;
		// }

		/**
		 * sort function
		 */
		public void sortFunction() {
			if (source != null) {
				Collections.sort(source, valueComparator);
			}
		}

		/**
		 * value comparator
		 * 
		 * @author Sebastien Janaud
		 */
		class ValueComparator implements Comparator<Point2D> {

			public ValueComparator() {
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Comparator#compare(java.lang.Object,
			 * java.lang.Object)
			 */
			@Override
			public int compare(Point2D p2d1, Point2D p2d2) {
				if (FunctionNature.XFunction == getNature()) {
					if (p2d1.getX() > p2d2.getX()) {
						return 1;
					} else if (p2d1.getX() < p2d2.getX()) {
						return -1;
					}
					return 0;
				} else {
					if (p2d1.getY() > p2d2.getY()) {
						return 1;
					} else if (p2d1.getY() < p2d2.getY()) {
						return -1;
					}
					return 0;
				}

			}

		}

	}

	/**
	 * <code>SplineSourceFunction</code> defines a spline source function which
	 * is interpolate from source
	 * 
	 * @author sebastien janaud
	 */
	public static class SplineSource extends LineSource {

		/** evaluation spline function */
		private UnivariateRealFunction evaluateFunction = null;

		/** the interpolate delta x segment */
		private double delta;

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public SplineSource(Date[] xValues, double[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public SplineSource(Date[] xValues, Double[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public SplineSource(Date[] xValues, double[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public SplineSource(Date[] xValues, Double[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public SplineSource(double[] xValues, Date[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public SplineSource(Double[] xValues, Date[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public SplineSource(double[] xValues, Date[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public SplineSource(Double[] xValues, Date[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public SplineSource(double[] xValues, double[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public SplineSource(Double[] xValues, Double[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public SplineSource(double[] xValues, double[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public SplineSource(Double[] xValues, Double[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param source
		 * @param nature
		 * @param delta
		 */
		public SplineSource(List<Point2D> source, FunctionNature nature, double delta) {
			super(source, nature);
			this.delta = delta;
		}

		/**
		 * create spline source
		 * 
		 * @param source
		 * @param delta
		 */
		public SplineSource(List<Point2D> source, double delta) {
			super(source);
			this.delta = delta;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.function.source.AffineSourceFunction#setSource
		 * (java.util.List)
		 */
		@Override
		public void setSource(List<Point2D> source) {
			super.setSource(source);
			evaluateFunction = null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.function.source.AffineSourceFunction#evaluate
		 * (double)
		 */
		@Override
		public Point2D evaluate(double value) {
			if (evaluateFunction == null) {
				createInterpolateFunction();
			}
			Point2D evaluatePoint = null;
			try {
				if (getNature() == FunctionNature.XFunction) {
					evaluatePoint = new Point2D.Double(value, evaluateFunction.value(value));
				} else {
					evaluatePoint = new Point2D.Double(evaluateFunction.value(value), value);
				}
			} catch (AnalysisException e) {
			}
			return evaluatePoint;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.function.source.LineSourceFunction#solveFunction
		 * (double, double)
		 */
		@Override
		public List<Point2D> solveFunction(double start, double end) {
			sortFunction();
			List<Point2D> interpolateSource = new ArrayList<Point2D>();
			List<Point2D> superSource = getSource();

			if (evaluateFunction == null) {
				createInterpolateFunction();
			}
			if (evaluateFunction == null) {
				return getSource();
			}

			Point2D pd2Min = superSource.get(0);
			Point2D pd2Max = superSource.get(superSource.size() - 1);
			if (getNature() == FunctionNature.XFunction) {
				for (double x = pd2Min.getX(); x <= pd2Max.getX(); x = x + delta) {
					try {
						if (x > pd2Min.getX() && x < pd2Max.getX()) {
							interpolateSource.add(new Point2D.Double(x, evaluateFunction.value(x)));
						}

					} catch (AnalysisException e) {
						return getSource();
					}
				}
			} else {
				for (double y = pd2Min.getY(); y <= pd2Max.getY(); y = y + delta) {
					try {
						if (y > pd2Min.getY() && y < pd2Max.getY()) {
							interpolateSource.add(new Point2D.Double(evaluateFunction.value(y), y));
						}

					} catch (AnalysisException e) {
						return getSource();
					}
				}
			}

			return interpolateSource;
		}

		/**
		 * create interpolate function for given source.
		 */
		private void createInterpolateFunction() {
			try {
				List<Point2D> suserSource = getSource();
				int len = suserSource.size();
				double[] xValues = new double[len];
				double[] yValues = new double[len];
				for (int i = 0; i < suserSource.size(); i++) {
					Point2D p2dUser = suserSource.get(i);
					xValues[i] = p2dUser.getX();
					yValues[i] = p2dUser.getY();
				}
				UnivariateRealInterpolator interpolator = new SplineInterpolator();
				if (getNature() == FunctionNature.XFunction) {
					evaluateFunction = interpolator.interpolate(xValues, yValues);
				} else {
					evaluateFunction = interpolator.interpolate(yValues, xValues);
				}
			} catch (AnalysisException e) {
			}
		}

	}

	/**
	 * <code>LinearRegressionSourceFunction</code> defines the user data input
	 * with linear regression segment.
	 * 
	 * @author sebastien janaud
	 */
	public static class RegressionSource extends LineSource {

		/** evaluation regression function */
		private SimpleRegression evaluateFunction = null;

		/** the interpolate increment */
		private double delta;



		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public RegressionSource(Date[] xValues, double[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public RegressionSource(Date[] xValues, Double[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public RegressionSource(Date[] xValues, double[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public RegressionSource(Date[] xValues, Double[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public RegressionSource(double[] xValues, Date[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public RegressionSource(Double[] xValues, Date[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public RegressionSource(double[] xValues, Date[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public RegressionSource(Double[] xValues, Date[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public RegressionSource(double[] xValues, double[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param nature
		 * @param delta
		 */
		public RegressionSource(Double[] xValues, Double[] yValues, FunctionNature nature, double delta) {
			super(xValues, yValues, nature);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public RegressionSource(double[] xValues, double[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param xValues
		 * @param yValues
		 * @param delta
		 */
		public RegressionSource(Double[] xValues, Double[] yValues, double delta) {
			super(xValues, yValues);
			this.delta = delta;
		}

		/**
		 * create regression source
		 * 
		 * @param source
		 * @param nature
		 * @param delta
		 */
		public RegressionSource(List<Point2D> source, FunctionNature nature, double delta) {
			super(source, nature);
			this.delta = delta;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.function.source.AffineSourceFunction#setSource
		 * (java.util.List)
		 */
		@Override
		public void setSource(List<Point2D> source) {
			super.setSource(source);
			evaluateFunction = null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.function.source.LineSourceFunction#solveFunction
		 * (double, double)
		 */
		@Override
		public List<Point2D> solveFunction(double start, double end) {
			if (evaluateFunction == null) {
				createInterpolateFunction();
			}
			if (evaluateFunction == null) {
				return getSource();
			}
			List<Point2D> newFunction = new ArrayList<Point2D>();
			Point2D pd2Min = getSource().get(0);
			Point2D pd2Max = getSource().get(getSource().size() - 1);
			if (getNature() == FunctionNature.XFunction) {
				for (double x = pd2Min.getX(); x <= pd2Max.getX(); x = x + delta) {
					newFunction.add(new Point2D.Double(x, evaluateFunction.predict(x)));
				}
			} else {
				for (double y = pd2Min.getY(); y <= pd2Max.getY(); y = y + delta) {
					newFunction.add(new Point2D.Double(evaluateFunction.predict(y), y));
				}
			}

			return newFunction;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.jensoft.core.plugin.function.source.AffineSourceFunction#evaluate
		 * (double)
		 */
		@Override
		public Point2D evaluate(double value) {
			if (evaluateFunction == null) {
				createInterpolateFunction();
			}
			Point2D evaluatePoint = null;
			try {
				if (getNature() == FunctionNature.XFunction) {
					evaluatePoint = new Point2D.Double(value, evaluateFunction.predict(value));
				} else {
					evaluatePoint = new Point2D.Double(evaluateFunction.predict(value), value);
				}
			} catch (Exception e) {
			}
			return evaluatePoint;
		}

		/**
		 * create linear regression for the source
		 */
		public void createInterpolateFunction() {
			evaluateFunction = new SimpleRegression();
			List<Point2D> source = getSource();
			for (int i = 0; i < source.size(); i++) {
				Point2D p2d = source.get(i);
				evaluateFunction.addData(p2d.getX(), p2d.getY());
			}
		}

	}

	/**
	 * create list of points from x and y arrays.
	 * 
	 * @param xValues
	 * @param yValues
	 * @return list of points
	 */
	public static List<Point2D> createPointsFromArray(double[] xValues, double[] yValues) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		List<Point2D> source = new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			source.add(new Point2D.Double(xValues[i], yValues[i]));
		}
		return source;
	}

	/**
	 * create list of points from x and y arrays.
	 * 
	 * @param xValues
	 * @param yValues
	 * @return list of points
	 */
	public static List<Point2D> createPointsFromArray(Double[] xValues, Double[] yValues) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		List<Point2D> source = new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			source.add(new Point2D.Double(xValues[i], yValues[i]));
		}
		return source;
	}

	/**
	 * create list of point from x date array and y double array
	 * 
	 * @param xValues
	 *            the dates as x values, the are transform in millisecond
	 * @param yValues
	 *            the y double values
	 * @return list of points
	 */
	public static List<Point2D> createPointsFromArray(Date[] xValues, double[] yValues) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		List<Point2D> source = new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			source.add(new Point2D.Double(new Long(xValues[i].getTime()).doubleValue(), yValues[i]));
		}
		return source;
	}

	/**
	 * create list of point from x date array and y double array
	 * 
	 * @param xValues
	 * @param yValues
	 * @return list of points
	 */
	public static List<Point2D> createPointsFromArray(Date[] xValues, Double[] yValues) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		List<Point2D> source = new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			source.add(new Point2D.Double(new Long(xValues[i].getTime()).doubleValue(), yValues[i]));
		}
		return source;
	}

	/**
	 * create list of point from x date array and y double array
	 * 
	 * @param xValues
	 * @param yValues
	 * @return list of points
	 */
	public static List<Point2D> createPointsFromArray(Double[] xValues, Date[] yValues) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		List<Point2D> source = new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			source.add(new Point2D.Double(xValues[i], new Long(yValues[i].getTime()).doubleValue()));
		}
		return source;
	}

	/**
	 * create list of point from x double array and y date array
	 * 
	 * @param xValues
	 * @param yValues
	 * @return list of points
	 */
	public static List<Point2D> createPointsFromArray(double[] xValues, Date[] yValues) {
		if (xValues.length != yValues.length) {
			throw new IllegalArgumentException(" x and y  array values length does not match");
		}
		List<Point2D> source = new ArrayList<Point2D>();
		for (int i = 0; i < xValues.length; i++) {
			source.add(new Point2D.Double(xValues[i], new Long(yValues[i].getTime()).doubleValue()));
		}
		return source;
	}
}
