package com.jensoft.core.plugin.function.source;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * {@link #solveFunction(double, double)} method is called when the inherits method
 * {@link #getCurrentFunction()} is building function on first call. Then, you can 
 * current function build or solve on a specified interval as your choice {@link #solveFunction(double, double)}
 * 
 */
public class CustomSource extends AbstractSourceFunction {

	public CustomSource() {
	}

	public CustomSource(FunctionNature nature) {
		super(nature);
	}

	@Override
	public List<Point2D> solveFunction(double start, double end) {
		List<Point2D> newFunction = new ArrayList<Point2D>();

		// if you need window projection...
		getHost().getHost().getWindow2D();

		// with evaluate function and step increment (that you can provide in
		// this custom source)
		// or other solver you have to provide a new function with sample points
		// on the given interval [start,end]

		// for example create a sample with epsilon step increment
		double epsilon = 0.1;
		for (double v = start; v <= end; v = v + epsilon) {
			Point2D functionEntry = evaluate(v);
			newFunction.add(functionEntry);
		}

		return newFunction;
	}

	@Override
	public Point2D evaluate(double value) {
		// Put evaluation code, example square power.

		// if you need window projection...
		getHost().getHost().getWindow2D();

		if (getNature() == FunctionNature.XFunction) {
			// the value x is the domain, y=f(x)
			return new Point2D.Double(value, value * value);
		} else if (getNature() == FunctionNature.YFunction) {
			// the value y is the domain, x=f(y)
			return new Point2D.Double(value * value, value);
		}
		return null;
	}

}
