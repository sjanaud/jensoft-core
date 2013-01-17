/**
 * 
 */
package com.jensoft.core.window;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import com.jensoft.core.palette.ColorPalette;
import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;

/**
 * <code>DebugPaintWindowPartPlugin</code> is a simple debug painter to see each window part component
 * 
 * @author sebastien janaud
 */
public class DebugPaintWindowPartPlugin extends AbstractPlugin {

    private Map<WindowPart, Color> colorMap = new HashMap<WindowPart, Color>();

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.sw2d.core.view.View2D,
     * java.awt.Graphics2D, com.jensoft.sw2d.core.window.WindowPart)
     */
    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {
        JComponent component = v2d.getWindowComponent(windowPart);
        Rectangle2D rect2DPart = new Rectangle2D.Double(0, 0, component.getWidth() - 1, component.getHeight() - 1);

        if (colorMap.get(windowPart) == null) {
            colorMap.put(windowPart, ColorPalette.getRandomColor());
        }

        g2d.setColor(colorMap.get(windowPart));
        g2d.draw(rect2DPart);
    }

}
