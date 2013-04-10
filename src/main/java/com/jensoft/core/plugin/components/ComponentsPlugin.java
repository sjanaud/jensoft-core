/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.components;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.plugin.AbstractPlugin;
import com.jensoft.core.view.View2D;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.Window2DAdapter;
import com.jensoft.core.window.Window2DEvent;
import com.jensoft.core.window.WindowPart;

public class ComponentsPlugin extends AbstractPlugin {

    public ComponentsPlugin() {
        setName("ComponentsPlugin");
        userComponents = new ArrayList<DeviceComponent>();
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#onWindowRegister()
     */
    @Override
    public void onWindowRegister() {
        super.onWindowRegister();
        getWindow2D().addWindow2DListener(new Window2DAdapter() {

            @Override
            public void window2DBoundChanged(Window2DEvent w2dEvent) {
                project();
            }
        });
    }

    private void project() {
        for (DeviceComponent uc : userComponents) {
            Window2D w2d = getWindow2D();
            Point2D deviceLocation = w2d.userToPixel(new Point2D.Double(uc
                    .getUserLocation().getX(), uc.getUserLocation().getY()));
            uc.getComponent().setLocation((int) deviceLocation.getX(),
                                          (int) deviceLocation.getY());

        }
    }

    private List<DeviceComponent> userComponents;

    public void addComponent(DeviceComponent label) {
        userComponents.add(label);

    }

    public void removeComponent(DeviceComponent label) {
        userComponents.remove(label);
    }

    boolean init = false;
    int count = 0;

    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.AbstractPlugin#paintPlugin(com.jensoft.core.view.View2D, java.awt.Graphics2D, com.jensoft.core.window.WindowPart)
     */
    @Override
    protected void paintPlugin(View2D v2d, Graphics2D g2d, WindowPart windowPart) {

        if (!init) {
            System.out.println("init paint layout :" + count++);
            project();
            for (DeviceComponent component : userComponents) {
                // System.out.println("add user component");
                component.getComponent().setBounds(
                                                   (int) component.getComponent().getLocation().getX(),
                                                   (int) component.getComponent().getLocation().getY(),
                                                   (int) component.getComponent().getPreferredSize()
                                                           .getWidth(),
                                                   (int) component.getComponent().getPreferredSize()
                                                           .getHeight());
                getWindow2D().getView2D().getDevice2D()
                        .remove(component.getComponent());
                getWindow2D().getView2D().getDevice2D()
                        .add(component.getComponent());
            }

            // init = true;
        }

    }

}
