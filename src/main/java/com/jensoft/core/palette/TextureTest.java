package com.jensoft.core.palette;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.jensoft.core.catalog.ui.ViewFrameUI;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.WindowPart;

public class TextureTest extends View2D {

	private static final long serialVersionUID = -2875434568800741512L;


	public TextureTest(){
		super();
		Window2D w = new Window2D.Linear(-1,1, -1, 1);
		registerWindow2D(w);
		AbstractPlugin plugin = new AbstractPlugin() {
			
			@Override
			protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
				if(windowPart == WindowPart.Device){
					System.out.println("paint textured");
					
					
					g2d.setPaint(TexturePalette.getBeeCarbonTexture0());
					Rectangle2D rect0 = new Rectangle2D.Double(0,0, getDevice2D().getWidth(), getDevice2D().getHeight()/4);
					g2d.fill(rect0);
					
					g2d.setPaint(TexturePalette.getBeeCarbonTexture1());
					Rectangle2D rect1 = new Rectangle2D.Double(0,getDevice2D().getHeight()/4, getDevice2D().getWidth(), getDevice2D().getHeight()/4);
					g2d.fill(rect1);
					
					g2d.setPaint(TexturePalette.getBeeCarbonTexture2());
					Rectangle2D rect2 = new Rectangle2D.Double(0,2*getDevice2D().getHeight()/4, getDevice2D().getWidth(), getDevice2D().getHeight()/4);
					g2d.fill(rect2);
					
					g2d.setPaint(TexturePalette.getBeeCarbonTexture3());
					Rectangle2D rect3 = new Rectangle2D.Double(0,3*getDevice2D().getHeight()/4, getDevice2D().getWidth(), getDevice2D().getHeight()/4);
					g2d.fill(rect3);
		        			        	
				}
				
			}
		};
		
		w.registerPlugin(plugin);
	}
	
	
	public static void main(String[] args) {
		ViewFrameUI ui = new ViewFrameUI(new TextureTest());
	}
}
