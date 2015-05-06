package org.jensoft.core.plugin.plot.applet;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public class Bezier extends ControlCurve {

  // the basis function for a Bezier spline
  static float b(int i, float t) {
    switch (i) {
    case 0:
      return (1-t)*(1-t)*(1-t);
    case 1:
      return 3*t*(1-t)*(1-t);
    case 2:
      return 3*t*t*(1-t);
    case 3:
      return t*t*t;
    }
    return 0; //we only get here if an invalid i is specified
  }
  
  //evaluate a point on the B spline
  Point p(int i, float t) {
    float px=0;
    float py=0;
    for (int j = 0; j<=3; j++){
      px += b(j,t)*pts.xpoints[i+j];
      py += b(j,t)*pts.ypoints[i+j];
    }
    return new Point((int)Math.round(px),(int)Math.round(py));
  }

  final int STEPS = 12;

  public void paint(Graphics g) {
    super.paint(g);
    Polygon pol = new Polygon ();
    Point q = p(0,0);
    pol.addPoint(q.x,q.y);
    for (int i = 0; i < pts.npoints-3; i+=3) {
      for (int j = 1; j <= STEPS; j++) {
	q = p(i,j/(float)STEPS);
	pol.addPoint(q.x,q.y);
      }
    }
    g.drawPolyline(pol.xpoints, pol.ypoints, pol.npoints);
  }

}

