package com.jensoft.core.plugin.window;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jensoft.core.device.ContextEntry;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.projection.Projection;
import com.jensoft.core.sharedicon.SharedIcon;
import com.jensoft.core.sharedicon.common.Common;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

public class WindowShiftPlugin extends AbstractPlugin {

	public WindowShiftPlugin() {
		registerContext(new WindowShiftPluginDeviceContext());
	}
	
	public class WindowShiftPluginDeviceContext extends ContextEntry<WindowShiftPlugin> {

		 /** shift menu */
	    private JMenu shiftMenu;
	    
	    /** shift icon */
	    private ImageIcon shiftIcon;


		
	    /* (non-Javadoc)
		 * @see com.jensoft.core.device.ContextEntry#buildContext()
		 */
		@Override
		public void buildContext() {
			if (getHost() == null) {
	            return;
	        }

	        // create icon
			shiftIcon = SharedIcon.getCommon(Common.TRANSLATE);
			shiftMenu = new JMenu("Windows");
			shiftMenu.setIcon(shiftIcon);
			
			List<Projection> windows =  getHost().getProjection().getView2D().getProjections();
			int count =1;
			for (final Projection window2d : windows) {
				
				if(window2d.getName() == null)
					window2d.setName("window-"+count);
				count++;
				JMenuItem windowSwitch = new JMenuItem(window2d.getName());
				
				if(window2d.isLockActive()){
				     windowSwitch.setIcon(SharedIcon.getCommon(Common.LOCK));
				}
				shiftMenu.add(windowSwitch);
				windowSwitch.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						getHost().getProjection().getView2D().setActiveProjection(window2d);
						getHost().getProjection().getView2D().repaintView();
					}
				});
			}

			setGroup("window");
	        setItem(shiftMenu);
			
		}

		/* (non-Javadoc)
		 * @see com.jensoft.core.device.ContextEntry#isCompatiblePlugin()
		 */
		@Override
		public boolean isCompatiblePlugin() {			
			return true;
		}
		
	}

	@Override
	protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
		
	}

}
