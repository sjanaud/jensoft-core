package com.jensoft.core.plugin.plot.applet;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**  This applet allows users to experiment with splines.
@author Tim Lambert
  */

public class SplineApplet extends Applet {
  protected ControlCurve curve;
  protected Color bgcolor;
  protected MouseEvent mouseEvent;

  public void init() {
    Color bgcolor = getColorParameter("bgcolor",null);
    if (bgcolor != null){
      setBackground(bgcolor);
      }
    
    /*dynamically load the class for the curve basis*/
 //   try {
//      String curveName = getParameter("basis");
//      if (curveName == null) {
//	curveName = "ControlCurve";
//      }
      curve = new BezierG1();//(ControlCurve) Class.forName(curveName).newInstance();
//    } catch (ClassNotFoundException e) {
//      System.err.println("Class not found: "+e.getMessage());
//    } catch (InstantiationException e) {
//      System.err.println("Couldn't Instance "+e.getMessage());
//    } catch (IllegalAccessException e) {
//      System.err.println("Couldn't Instance "+e.getMessage());
//    }
    /* initial control points from parameter controlPoints */
    String s = getParameter("controlPoints");
    if (s != null) {
      StringTokenizer st = new StringTokenizer(s, " \t");
      try {
	while (st.hasMoreTokens()){
	  int x = Integer.parseInt(st.nextToken());
	  int y = Integer.parseInt(st.nextToken());
	  curve.addPoint(x,y);
	}
      } 
      catch (NoSuchElementException e) {
	System.err.println("Bad controlPoints parameter "+e.getMessage());
      } catch (NumberFormatException e) {
	System.err.println("Bad controlPoints parameter "+e.getMessage());
      }
    }

    setLayout(new BorderLayout(0,0));
    mouseEvent = new MouseEvent();
    CurveCanvas canvas = new CurveCanvas(mouseEvent,curve);
    add("Center",canvas);
    Thread canvasThread = new Thread(canvas);
    canvasThread.start();
    canvasThread.setPriority(Thread.MIN_PRIORITY);
  }  
    
  public boolean handleEvent(Event e) {
    if (e.id==Event.MOUSE_DOWN ||
        e.id==Event.MOUSE_DRAG || 
        e.id==Event.MOUSE_UP) {
      mouseEvent.put(e);
      return true;
    } else {
     return false;
    }
  }
    

  // Read the specified parameter.  Interpret it as a hexadecimal
  // number of the form RRGGBB and convert it to a color.
  protected Color getColorParameter(String name, Color dfault) {
    String value = getParameter(name);
    int intvalue;
    try {
      intvalue = Integer.parseInt(value, 16);
    } catch (NumberFormatException e) { 
      return dfault;
    }
    return new Color(intvalue);
    }
 
}
