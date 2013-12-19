package com.jensoft.core.plugin.gauge;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import javax.swing.JFrame;

import com.jensoft.core.glyphmetrics.AbstractMetricsPath.ProjectionNature;

public class ArcsDrawing extends JFrame {
	Arc2D arc1;
	GeneralPath path = new GeneralPath();
	public ArcsDrawing() {
		setTitle("Arcs Drawing");
		arc1 = new Arc2D.Double(60, 70, 200, 200, 0, 180, Arc2D.OPEN);
		
		PathIterator pi = arc1.getPathIterator(null);
		while (pi.isDone() == false) {
			double[] coordinates = new double[6];
			int type = 	pi.currentSegment(coordinates);
			
			switch (type) {
			
			case PathIterator.SEG_MOVETO:
				System.out.println("move to "+coordinates[0]+","+ coordinates[1]);
					Point2D pm = null;
					pm = new Point2D.Double(coordinates[0], coordinates[1]);
				
					path.moveTo(pm.getX(), pm.getY());
				
				break;
			case PathIterator.SEG_LINETO:
				Point2D pl;
				
					pl = new Point2D.Double(coordinates[0], coordinates[1]);
				

				path.lineTo(pl.getX(), pl.getY());
				break;
			case PathIterator.SEG_QUADTO:
				Point2D pq1;
				Point2D pq2;
				
					pq1 = new Point2D.Double(coordinates[0], coordinates[1]);
					pq2 = new Point2D.Double(coordinates[2], coordinates[3]);

				path.quadTo(pq1.getX(), pq1.getY(), pq2.getX(), pq2.getY());
				break;
			case PathIterator.SEG_CUBICTO:
				Point2D pc1;
				Point2D pc2;
				Point2D pc3;
				System.out.println("cubic to "+coordinates[0]+","+coordinates[1]+","+coordinates[2]+","+coordinates[3]+","+coordinates[4]+","+coordinates[5]);

					
					pc1 = new Point2D.Double(coordinates[0], coordinates[1]);
					pc2 = new Point2D.Double(coordinates[2], coordinates[3]);
					pc3 = new Point2D.Double(coordinates[4], coordinates[5]);

				path.curveTo(pc1.getX(), pc1.getY(), pc2.getX(), pc2.getY(), pc3.getX(), pc3.getY());
				break;
			case PathIterator.SEG_CLOSE:
				System.out.println("close to");
				path.closePath();
				break;
			default:
				break;
			}
			
			pi.next();
		}
		
		
		
		setSize(525, 300);
		setVisible(true);
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		
		g2.setColor(Color.BLUE);
		g2.draw(arc1);
		
		
		g2.setColor(Color.RED);
		g2.draw(path);
	}

	public static void main(String args[]) {
		new ArcsDrawing();
	}
}