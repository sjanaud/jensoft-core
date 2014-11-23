/**
 * 
 */
package com.jensoft.core.projection;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View;
import com.jensoft.core.view.ViewPart;

/**
 * <code>DebugPaintProjectionPartPlugin</code> is a simple debug painter to see each view part component
 * 
 * @author sebastien janaud
 */
public class DebugPaintProjectionPartPlugin extends AbstractPlugin {

    private Map<ViewPart, Color> colorMap = new HashMap<ViewPart, Color>();

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View, java.awt.Graphics2D, com.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View v2d, Graphics2D g2d, ViewPart viewPart) {
        JComponent component = v2d.getViewPartComponent(viewPart);
        Rectangle2D rect2DPart = new Rectangle2D.Double(0, 0, component.getWidth() - 1, component.getHeight() - 1);

        if (colorMap.get(viewPart) == null) {
            colorMap.put(viewPart, ColorPalette.getRandomColor());
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g2d.setColor(colorMap.get(viewPart));
        g2d.draw(rect2DPart);
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
        g2d.fill(rect2DPart);
    }

}
