/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.components;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jensoft.core.plugin.AbstractPlugin;
import org.jensoft.core.projection.Projection;
import org.jensoft.core.projection.ProjectionAdapter;
import org.jensoft.core.projection.ProjectionEvent;
import org.jensoft.core.view.View;
import org.jensoft.core.view.ViewPart;

public class ComponentsPlugin extends AbstractPlugin {

    public ComponentsPlugin() {
        setName("ComponentsPlugin");
        userComponents = new ArrayList<DeviceComponent>();
    }

   
   
    /* (non-Javadoc)
     * @see org.jensoft.core.plugin.AbstractPlugin#onProjectionRegister()
     */
    @Override
    public void onProjectionRegister() {
        super.onProjectionRegister();
        getProjection().addProjectionListener(new ProjectionAdapter() {

            @Override
            public void projectionBoundChanged(ProjectionEvent w2dEvent) {
                project();
            }
        });
    }

    private void project() {
        for (DeviceComponent uc : userComponents) {
            Projection w2d = getProjection();
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
     * @see org.jensoft.core.plugin.AbstractPlugin#paintPlugin(org.jensoft.core.view.View, java.awt.Graphics2D, org.jensoft.core.view.ViewPart)
     */
    @Override
    protected void paintPlugin(View view, Graphics2D g2d, ViewPart viewPart) {

        if (!init) {
            //System.out.println("init paint layout :" + count++);
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
                getProjection().getView().getDevice2D()
                        .remove(component.getComponent());
                getProjection().getView().getDevice2D()
                        .add(component.getComponent());
            }

            // init = true;
        }

    }

}
