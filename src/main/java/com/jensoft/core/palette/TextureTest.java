package com.jensoft.core.palette;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

public class TextureTest extends View2D {

	private static final long serialVersionUID = -2875434568800741512L;

	private int rowCount = 4;
	private int colCount = 2;
	private Window2D w ;
	public TextureTest(){
		super();
		w = new Window2D.Linear(-1,1, -1, 1);
		registerWindow2D(w);
		AbstractPlugin plugin = new AbstractPlugin() {
			
			@Override
			protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
				if(windowPart == WindowPart.Device){
					paintCell(g2d, TexturePalette.getBeeCarbonTexture0(), 0, 0);
					paintCell(g2d, TexturePalette.getBeeCarbonTexture1(), 0, 1);
					paintCell(g2d, TexturePalette.getBeeCarbonTexture2(), 1, 0);
					paintCell(g2d, TexturePalette.getBeeCarbonTexture3(), 1, 1);
					paintCell(g2d, TexturePalette.getPerforatedCircleSurface(), 2, 0);
					paintCell(g2d, TexturePalette.getPerforatedPolygonSurface(), 2, 1);
					paintCell(g2d, TexturePalette.getSquareCarbonFiber(), 3, 0);
					paintCell(g2d, TexturePalette.getTriangleCarbonFiber(), 3, 1);
					
		        			        	
				}
				
			}
		};
		
		w.registerPlugin(plugin);
	}
	
	private void paintCell(Graphics2D g2d, Paint texture, int row, int col){
		double  cellWidth = w.getDevice2D().getDeviceWidth()/colCount;
		double  cellHeight = w.getDevice2D().getDeviceHeight()/rowCount;
		double px = col*cellWidth;
		double py = row*cellHeight;
		
		Rectangle2D rect = new Rectangle2D.Double(px,py,cellWidth, cellHeight);
		g2d.setPaint(texture);
		g2d.fill(rect);
	}
	
	
	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new TextureTest());
	}
}
