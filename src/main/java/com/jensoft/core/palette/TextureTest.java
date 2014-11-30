package com.jensoft.core.palette;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

public class TextureTest extends View {

	private static final long serialVersionUID = -2875434568800741512L;

	private int rowCount = 5;
	private int colCount = 2;
	private Projection w ;
	public TextureTest(){
		super();
		w = new Projection.Linear(-1,1, -1, 1);
		registerProjection(w);
		AbstractPlugin plugin = new AbstractPlugin() {
			
			@Override
			protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
				if(viewPart == ViewPart.Device){
					paintCell(g2d, TexturePalette.getBeeCarbonTexture0(), 0, 0);
					paintCell(g2d, TexturePalette.getBeeCarbonTexture1(), 0, 1);
					paintCell(g2d, TexturePalette.getBeeCarbonTexture2(), 1, 0);
					paintCell(g2d, TexturePalette.getBeeCarbonTexture3(), 1, 1);
					paintCell(g2d, TexturePalette.getPerforatedCircleSurface(), 2, 0);
					paintCell(g2d, TexturePalette.getPerforatedPolygonSurface(), 2, 1);
					paintCell(g2d, TexturePalette.getSquareCarbonFiber(), 3, 0);
					paintCell(g2d, TexturePalette.getTriangleCarbonFiber(), 3, 1);
					paintCell(g2d, TexturePalette.getInterlacedCarbon1(), 4, 0);
					paintCell(g2d, TexturePalette.getInterlacedCarbon2(), 4, 1);
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
