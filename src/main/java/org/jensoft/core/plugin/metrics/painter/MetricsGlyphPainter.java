/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.metrics.painter;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

import org.jensoft.core.glyphmetrics.GlyphUtil;
import org.jensoft.core.plugin.metrics.Metrics;
import org.jensoft.core.plugin.metrics.Metrics.Gravity;
import org.jensoft.core.plugin.metrics.Metrics.MarkerPosition;
import org.jensoft.core.plugin.metrics.Metrics.MetricsNature;
import org.jensoft.core.plugin.metrics.Metrics.MetricsType;
import org.jensoft.core.view.ViewPart;

/**
 * <code>MetricsGlyphPainter<code> takes the responsibility to paint metrics
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public class MetricsGlyphPainter extends AbstractMetricsPainter {

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.metrics.painter.AbstractMetricsPainter#doPaintLineMetrics(java.awt.Graphics2D, java.awt.geom.Point2D, java.awt.geom.Point2D, java.awt.Color)
     */
    @Override
    public void doPaintLineMetrics(Graphics2D g2d, Point2D start, Point2D end,  Color axisBaseColor) {
        g2d.setColor(axisBaseColor);
        Line2D lineMetrics = new Line2D.Double(start, end);
        g2d.draw(lineMetrics);
    }

    /**
     * paint metrics marker
     * 
     * @param g2d
     * @param metric
     */
    protected void paintMetricsMarker(Graphics2D g2d, Metrics metric) {
        
    	//System.out.println("paint marker "+metric.getUserValue());
    	g2d.setFont(getMetricsPlugin().getMetricsTextFont(metric));
        int markerSize = getMetricsPlugin().getMetricsMarkerSize(metric);

        //marker color
        if (metric.getMetricsMarkerColor() != null) {
            g2d.setColor(metric.getMetricsMarkerColor());
        }
        else if (getMetricsPlugin().getMetricsMarkerColor(metric) != null) {
            g2d.setColor(getMetricsPlugin().getMetricsMarkerColor(metric));
        }
        else {
            g2d.setColor(getMetricsPlugin().getProjection().getThemeColor());
        }
        
        //marker stroke
        if (metric.getMetricsMarkerStroke() != null) {
            g2d.setColor(metric.getMetricsMarkerColor());
        }
        else if (getMetricsPlugin().getMetricsMarkerStroke(metric) != null) {
            g2d.setStroke(getMetricsPlugin().getMetricsMarkerStroke(metric));
        }
        else {
            g2d.setStroke(new BasicStroke());
        }

        Shape metricsShapeIndicator = null;

        Point2D position = metric.getMarkerLocation();
        if (metric.getMetricsType() == MetricsType.XMetrics) {

            if (metric.getMarkerPosition() == MarkerPosition.S) {
                metricsShapeIndicator = new Line2D.Double(position.getX(),
                                                          position.getY() + 2, position.getX(),
                                                          position.getY() + markerSize + 2);
            }
            if (metric.getMarkerPosition() == MarkerPosition.N) {
                metricsShapeIndicator = new Line2D.Double(position.getX(),
                                                          position.getY() - 2, position.getX(),
                                                          position.getY() - markerSize - 2);
            }
           
        }
        if (metric.getMetricsType() == MetricsType.YMetrics) {
            if (metric.getMarkerPosition() == MarkerPosition.W) {
                metricsShapeIndicator = new Line2D.Double(position.getX()
                        - markerSize - 2, position.getY(),
                                                          position.getX() - 2, position.getY());
            }
            if (metric.getMarkerPosition() == MarkerPosition.E) {
                metricsShapeIndicator = new Line2D.Double(
                                                          position.getX() + 2, position.getY(),
                                                          position.getX() + markerSize + 2, position.getY());
            }
            
        }

        if (metricsShapeIndicator != null && metric.isLockMarker()) {
            g2d.draw(metricsShapeIndicator);
        }

    }
    
    
    /**
     * paint north metrics label
     * @param g2d
     * @param metric
     */
    protected void paintNorthMetricsLabel(Graphics2D g2d, Metrics metric){
        Point2D position = metric.getMarkerLocation();
        Font f = getMetricsPlugin().getMetricsTextFont(metric);
        g2d.setFont(f);
        int markerSize = getMetricsPlugin().getMetricsMarkerSize(metric);
        int tickLabelWidth =  g2d.getFontMetrics(f).stringWidth(metric.getMetricsLabel());
        
        g2d.drawString(
                       metric.getMetricsLabel(),
                       (int) (position.getX() - tickLabelWidth / 2),
                       (int) (position.getY() - markerSize - 4));
    }
    
    
    /**
     * paint south metrics label
     * @param g2d
     * @param metric
     */
    protected void paintSouthMetricsLabel2(Graphics2D g2d, Metrics metric){
        Point2D position = metric.getMarkerLocation();
        Font f = getMetricsPlugin().getMetricsTextFont(metric);
        g2d.setFont(f);
        int markerSize = getMetricsPlugin().getMetricsMarkerSize(metric);
        int tickLabelWidth =  g2d.getFontMetrics(f).stringWidth(metric.getMetricsLabel());
        
        
        AffineTransform af = new AffineTransform();
        
        //Look for this solution instead of pure glyph
        //af.translate(tx, ty);
        af.rotate(-Math.PI/4, position.getX(), position.getY()+markerSize +10);
        
        g2d.setTransform(af);
        g2d.drawString(
                       metric.getMetricsLabel(),
                       (int) (position.getX() - tickLabelWidth / 2),
                       (int) (position.getY() + markerSize
                               + g2d.getFontMetrics(f).getHeight() + 2));
        g2d.setTransform(new AffineTransform());
    }
    
    /**
     * paint south metrics label
     * @param g2d
     * @param metric
     */
    protected void paintSouthMetricsLabel(Graphics2D g2d, Metrics metric){
    //	System.out.println("paint south : "+metric.getUserValue());
        Point2D position = metric.getMarkerLocation();
        int markerSize = getMetricsPlugin().getMetricsMarkerSize(metric);
        Font f = getMetricsPlugin().getMetricsTextFont(metric);
        g2d.setFont(f);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector legendGlyphVector = f.createGlyphVector(frc,metric.getMetricsLabel());
        float legendWidth = GlyphUtil.getGlyphWidth(legendGlyphVector);
        AffineTransform af = new AffineTransform();
        for (int g = 0; g < legendGlyphVector.getNumGlyphs(); g++) {
            Point2D p = legendGlyphVector.getGlyphPosition(g);
            float px = (float) p.getX();
            float py = (float) p.getY();

            Point2D pointGlyph = null;
           
            Shape  glyph = legendGlyphVector.getGlyphOutline(g);
            //boolean paintFlag = false;
            if(getMetricsPlugin().getGravity() == Gravity.Rotate){
            	 pointGlyph = new Point2D.Double(position.getX() + g2d.getFontMetrics().getAscent()/2,
							position.getY()  + markerSize + getMetricsPlugin().getMetricsTextOffset(metric) + legendWidth  - GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,g));
				af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
				af.rotate(-Math.PI/2);
				af.translate(-px,-py);
	//			int tickLabelWidth = renderContext.metricsWidth(metric);
	//			int height = getMetricsRenderContext().getProjection().getDevice2D().getDeviceHeight();
	//				if (position.getY() < height - tickLabelWidth/2 && position.getY() > tickLabelWidth / 2) {
	//				paintFlag = true;
	//				}
	//				if(paintFlag){
				Shape ts = af.createTransformedShape(glyph);
				g2d.fill(ts);
				//}
            }else{
            	 pointGlyph = new Point2D.Double(
            			position.getX() - legendWidth/2 + GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,g) ,
     					position.getY() + markerSize + getMetricsPlugin().getMetricsTextOffset(metric) + g2d.getFontMetrics().getHeight());                                    

                 af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
                 af.translate(-px, -py);
//                 int westPartHeight = getMetricsRenderContext().getView().getViewPartComponent(ViewPart.West).getHeight();
//                 if (position.getY() > g2d.getFontMetrics().getAscent()/2 && position.getY() < westPartHeight - g2d.getFontMetrics().getAscent()/2) {
//                   paintFlag = true;
//                 }
//                 if(paintFlag){
                	Shape ts = af.createTransformedShape(glyph);
           	  	 	g2d.fill(ts);
                // }
            }
            	
          
        }
    }
    
    
    /**
     * paint west metrics label
     * @param g2d
     * @param metric
     */
    protected void paintWestMetricsLabel(Graphics2D g2d, Metrics metric){
        Point2D position = metric.getMarkerLocation();
        int markerSize = getMetricsPlugin().getMetricsMarkerSize(metric);
        Font f = getMetricsPlugin().getMetricsTextFont(metric);
        g2d.setFont(f);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector legendGlyphVector = f.createGlyphVector(frc,metric.getMetricsLabel());
        float legendWidth = GlyphUtil.getGlyphWidth(legendGlyphVector);
        AffineTransform af = new AffineTransform();
        for (int g = 0; g < legendGlyphVector.getNumGlyphs(); g++) {
            Point2D p = legendGlyphVector.getGlyphPosition(g);
            float px = (float) p.getX();
            float py = (float) p.getY();

            Point2D pointGlyph = null;
           
            Shape  glyph = legendGlyphVector.getGlyphOutline(g);
            boolean paintFlag = false;
            if(getMetricsPlugin().getGravity() == Gravity.Rotate){
              pointGlyph = new Point2D.Double(position.getX() - markerSize - getMetricsPlugin().getMetricsTextOffset(metric) - g2d.getFontMetrics(f).getHeight()/2,
            		  							position.getY()   + legendWidth / 2 - GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,g));
              af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
              af.rotate(-Math.PI/2);
              af.translate(-px,-py);
              int tickLabelWidth = g2d.getFontMetrics(f).stringWidth(metric.getMetricsLabel());
              int height = getMetricsPlugin().getProjection().getDevice2D().getDeviceHeight();
              if (position.getY() < height - tickLabelWidth/2 && position.getY() > tickLabelWidth / 2) {
                  paintFlag = true;
              }
              if(paintFlag){
            	  Shape ts = af.createTransformedShape(glyph);
            	  g2d.fill(ts);
              }
            }else{
            	 pointGlyph = new Point2D.Double(position.getX() - legendWidth - markerSize - getMetricsPlugin().getMetricsTextOffset(metric) + GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,g) ,
     					position.getY() + g2d.getFontMetrics().getAscent()/2);                                    

                 af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
                 af.translate(-px, -py);
                 int westPartHeight = getMetricsPlugin().getProjection().getView().getViewPartComponent(ViewPart.West).getHeight();
                 if (position.getY() > g2d.getFontMetrics().getAscent()/2 && position.getY() < westPartHeight - g2d.getFontMetrics().getAscent()/2) {
                   paintFlag = true;
                 }
                 if(paintFlag){
                	Shape ts = af.createTransformedShape(glyph);
           	  	 	g2d.fill(ts);
                 }
            }
            	
          
        }
    }
    

    /**
     * paint east metrics label
     * @param g2d
     * @param metric
     */
    protected void paintEastMetricsLabel(Graphics2D g2d, Metrics metric){
        Point2D position = metric.getMarkerLocation();
        int markerSize = getMetricsPlugin().getMetricsMarkerSize(metric);
        Font f = getMetricsPlugin().getMetricsTextFont(metric);
        g2d.setFont(f);
        FontRenderContext frc = g2d.getFontRenderContext();
        GlyphVector legendGlyphVector = f.createGlyphVector(frc, metric.getMetricsLabel());
                
                                  
        float legendWidth = GlyphUtil.getGlyphWidth(legendGlyphVector);
        AffineTransform af = new AffineTransform();
        for (int g = 0; g < legendGlyphVector.getNumGlyphs(); g++) {

            Point2D p = legendGlyphVector.getGlyphPosition(g);
            float px = (float) p.getX();
            float py = (float) p.getY();

            Point2D pointGlyph = null;
            
            Shape  glyph = legendGlyphVector.getGlyphOutline(g);
            boolean paintFlag = false;
            if(getMetricsPlugin().getGravity() == Gravity.Rotate){
              pointGlyph = new Point2D.Double(position.getX() + markerSize + g2d.getFontMetrics(f).getHeight(),
            		  position.getY() + legendWidth  / 2 - GlyphUtil   .getGlyphWidthAtToken(legendGlyphVector,   g));
              af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
              af.rotate(-Math.PI/2);
              af.translate(-px,-py);
              int tickLabelWidth = g2d.getFontMetrics(f).stringWidth(metric.getMetricsLabel());
              int height = getMetricsPlugin().getProjection().getDevice2D().getDeviceHeight();
              if (position.getY() < height - tickLabelWidth/2 && position.getY() > tickLabelWidth / 2) {
                  paintFlag = true;
              }
              if(paintFlag){
            	  Shape ts = af.createTransformedShape(glyph);
            	  g2d.fill(ts);
              }
            }else{
            	 pointGlyph = new Point2D.Double(position.getX() + markerSize+ 10 + GlyphUtil.getGlyphWidthAtToken(legendGlyphVector,g) ,
     					position.getY() + g2d.getFontMetrics().getAscent()/2);                                    

                 af.setToTranslation(pointGlyph.getX(), pointGlyph.getY());
                 af.translate(-px, -py);
                 int westPartHeight = getMetricsPlugin().getProjection().getView().getViewPartComponent(ViewPart.West).getHeight();
                 if (position.getY() > g2d.getFontMetrics().getAscent()/2 && position.getY() < westPartHeight - g2d.getFontMetrics().getAscent()/2) {
                   paintFlag = true;
                 }
                 if(paintFlag){
                	Shape ts = af.createTransformedShape(glyph);
           	  	 	g2d.fill(ts);
                 }
            }
        }
    }

    /**
     * paint  metrics label
     * 
     * @param g2d
     * @param metric
     */
    protected void paintMetricsText(Graphics2D g2d, Metrics metric) {
    	Font f = getMetricsPlugin().getMetricsTextFont(metric);
        g2d.setFont(f);
        if ((metric.getNature() == MetricsNature.Major || metric.getNature() == MetricsNature.Median)  && metric.isLockLabel()) {
            
        	//label color
        	if (metric.getMetricsLabelColor() != null) {
                g2d.setColor(metric.getMetricsLabelColor());
            }
            else if(getMetricsPlugin().getMetricsTextColor(metric) != null) {
                g2d.setColor(getMetricsPlugin().getMetricsTextColor(metric));
            }
            else {
                g2d.setColor(getMetricsPlugin().getProjection().getThemeColor());
            }
            
            
            if (metric.getMetricsLabel() != null) {
                if (metric.getMetricsType() == MetricsType.XMetrics) {
                    if (metric.getMarkerPosition() == MarkerPosition.S) {
                       paintSouthMetricsLabel(g2d, metric);
                    }
                    if (metric.getMarkerPosition() == MarkerPosition.N) {
                       paintNorthMetricsLabel(g2d, metric);
                    }
                }
                if (metric.getMetricsType() == MetricsType.YMetrics) {
                    if (metric.getMarkerPosition() == MarkerPosition.W) {                        
                        paintWestMetricsLabel(g2d, metric);
                    }
                    if (metric.getMarkerPosition() == MarkerPosition.E) {
                         paintEastMetricsLabel(g2d, metric);
                    }
                }
            }
        }
    }

   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.metrics.painter.AbstractMetricsPainter#doPaintMetrics(java.awt.Graphics2D, java.util.List)
     */
    @Override
    public void doPaintMetrics(Graphics2D g2d, List<Metrics> metrics) {
        g2d.setStroke(new BasicStroke());
        for (int i = 0; i < metrics.size(); i++) {
        	
            Metrics metric = metrics.get(i);
           // System.out.println("paint metrics : "+metric.getUserValue());
            if (!metric.isVisible()) {
                continue;
            }

            paintMetricsMarker(g2d, metric);
            paintMetricsText(g2d, metric);
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                
    }

}
