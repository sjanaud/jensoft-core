package com.jensoft.core.plugin.gauge.arcautobinder;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.palette.NanoChromatique;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

public class TestCircleIntersection extends View2D {

	public TestCircleIntersection(){
		
		super(50);
		
		Window2D projection = new Window2D.Linear(-4,4,-5,5);
		projection.registerPlugin(new OutlinePlugin());
		registerWindow2D(projection);
		
		AbstractPlugin plugin = new AbstractPlugin() {
			
			@Override
			protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
				if(windowPart != WindowPart.Device)
					return;
				
					//C0(0,0,r=40)
					//C0 in user coordinate = Point(x0,y0) and radius R0
					double r0=80;
					
					double x0 = getWindow2D().userToPixel(new Point2D.Double(0, 0)).getX();
					double y0 = getWindow2D().userToPixel(new Point2D.Double(0, 0)).getY();
					Ellipse2D c0 = new Ellipse2D.Double(x0-r0, y0-r0, 2*r0,2*r0);
					

					//C1 in user coordinate = Point(x1,y1) and radius R1
					double r1=90;
					double x1 = getWindow2D().userToPixel(new Point2D.Double(1, 0)).getX();
					double y1 = getWindow2D().userToPixel(new Point2D.Double(0, 3)).getY();
					Ellipse2D c1 = new Ellipse2D.Double(x1-r1, y1-r1, 2*r1,2*r1);
					
					g2d.setColor(NanoChromatique.RED);
					g2d.draw(c0);
					g2d.setColor(NanoChromatique.BLUE);
					g2d.draw(c1);
					
					
					//r0² = (x0-x)² + (y0-y)²
					//r1² = (x1-x)² + (y1-y)²
					//ax²+bx+c = 0
					if((y0-y1) != 0){
						double N = (r1*r1 - r0*r0 - x1*x1 + x0*x0 - y1*y1 + y0*y0)/(2*(y0-y1));
						System.out.println("N="+N);
						double A = Math.pow((x0-x1)/(y0-y1),2) + 1;
						double B = 2*y0*(x0-x1)/(y0-y1) - 2*N*(x0-x1)/(y0-y1)-2*x0;
						double C = x0*x0 + y0*y0 + N*N-r0*r0 - 2*y0*N;
						
						double delta =  Math.sqrt(B*B - 4*A*C);
						System.out.println("delta : "+delta);
						if(delta < 0){
							System.out.println("no solution");
						}
						else if(delta == 0){
							System.out.println("1 solution double : x=-b/2a");
						}else if(delta > 0){
							System.out.println("2 solutions disctinct ");
							System.out.println(" x = (-b-racine(delta))/2a et x = (-b+racine(delta))/2a");
							
							g2d.setColor(Color.YELLOW);
							//p1
							double p1x = (-B-delta)/(2*A);
							double p1y = N - p1x*(x0-x1)/(y0-y1);
							g2d.drawRect((int)p1x, (int)p1y, 4, 4);
							System.out.println("p1 "+p1x+","+p1y);
							//p2
							double p2x = (-B+delta)/(2*A);
							double p2y = N - p2x*(x0-x1)/(y0-y1);
							g2d.drawRect((int)p2x, (int)p2y, 4, 4);
							System.out.println("p2 "+p2x+","+p2y);
							
							
							double tethaRadian1 = getPolarAngle(x1, y1, p1x, p1y);
							double tethaRadian2 = getPolarAngle(x1, y1, p2x, p2y);
							
							double tx1 = x1 + r1*Math.cos(tethaRadian1);
							double ty1 = y1 - r1*Math.sin(tethaRadian1);
							g2d.setColor(Color.GREEN);
							//g2d.fillRect((int)tx1, (int)ty1, 4, 4);
							
							double tx2 = x1 + r1*Math.cos(tethaRadian2);
							double ty2 = y1 - r1*Math.sin(tethaRadian2);
							g2d.setColor(Color.GREEN);
							//g2d.fillRect((int)tx2, (int)ty2, 4, 4);
							
//							//OK
//							double tx = x0 + r0*Math.cos(Math.toRadians(90));
//							double ty = y0 - r0*Math.sin(Math.toRadians(90));
//							g2d.setColor(Color.GREEN);
//							g2d.drawRect((int)tx, (int)ty, 4, 4);
							
						}
					}else{
						System.out.println("case y0 = y1");
						double x = (r1*r1 - r0*r0 - x1*x1 + x0*x0)/(2*(x0-x1));
						double A = 1;
						double B = -2*y1;
						double C = x1*x1 +x*x - 2*x1*x + y1*y1- r1*r1;
						double delta = Math.sqrt(B*B - 4*A*C);
						
						System.out.println("delta : "+delta);
						if(delta < 0){
							System.out.println("no solution");
						}
						else if(delta == 0){
							System.out.println("1 solution double : x=-b/2a");
						}else if(delta > 0){
							System.out.println("2 solutions disctinct ");
							System.out.println(" x = (-b-racine(delta))/2a et x = (-b+racine(delta))/2a");
							
							g2d.drawRect((int)x, (int)((-B-delta)/2*A), 4, 4);
							g2d.drawRect((int)x, (int)((-B+delta)/2*A), 4, 4);
						}
					}
					
					
			}
		};
		
		projection.registerPlugin(plugin);
		
		
	}
	
	/**
	 * given the polar angle radian of point P(px,py) which is on the circle define by its center C(refX,refY)
	 * @param refX
	 * @param refY
	 * @param px
	 * @param py
	 * @return polar angle radian
	 */
	public double getPolarAngle(double refX,double refY, double px,double py){
		double tethaRadian = -1;
		if((px-refX) > 0 && (refY-py) >= 0){
			tethaRadian = Math.atan((refY-py)/(px-refX));
		}
		else if((px-refX) > 0 && (refY-py) < 0){
			tethaRadian = Math.atan((refY-py)/(px-refX)) + 2*Math.PI;
		}
		else if((px-refX) < 0){
			tethaRadian = Math.atan((refY-py)/(px-refX)) + Math.PI;
		}
		else if((px-refX) == 0 && (refY-py) > 0){
			tethaRadian = Math.PI/2;
		}
		else if((px-refX) == 0 && (refY-py) < 0){
			tethaRadian = 3*Math.PI/2;
		}
		return tethaRadian;
	}

	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new TestCircleIntersection());
	}
}
