/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.grid.painter;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.List;

import com.jensoft.core.plugin.grid.Grid;
import com.jensoft.core.plugin.grid.Grid.GridOrientation;
import com.jensoft.core.plugin.grid.manager.GridManager;

public class GridDefaultPainter extends AbstractGridPainter {

    public GridDefaultPainter() {

    }

    @Override
    public void paintGrids(Graphics2D g2d, List<Grid> grids) {
        GridManager manager = getLayoutManager();
        if (manager == null) {
            return;
        }

        if (manager.getGridOrientation() == GridOrientation.Vertical) {

            for (int i = 0; i < grids.size(); i++) {
                Grid grid = grids.get(i);
                double gd = grid.getGridDeviceValue();
                g2d.setStroke(grid.getStroke());

                if (grid.getGridColor() != null) {
                    g2d.setPaint(grid.getGridColor());
                }
                else {
                    g2d.setColor(getLayoutManager().getProjection()
                            .getThemeColor());
                }

                Shape grid2DShape = new Line2D.Double(gd, getLayoutManager()
                        .getProjection().getDevice2D().getOriginY(), gd,
                                                      getLayoutManager().getProjection().getDevice2D()
                                                              .getDeviceHeight()
                                                              + getLayoutManager().getProjection()
                                                                      .getDevice2D().getOriginY());

                g2d.draw(grid2DShape);

                if (grid.getAnnotation() != null) {

                    g2d.drawString(grid.getAnnotation(), (int) gd + 2,
                                   getLayoutManager().getProjection().getDevice2D()
                                           .getDeviceHeight()
                                           * grid.getAnnotationFraction());
                }
            }

        }

        if (manager.getGridOrientation() == GridOrientation.Horizontal) {

            for (int i = 0; i < grids.size(); i++) {
                Grid grid = grids.get(i);
                double gd = grid.getGridDeviceValue();

                g2d.setStroke(grid.getStroke());

                if (grid.getGridColor() != null) {
                    g2d.setPaint(grid.getGridColor());
                }
                else {
                    g2d.setColor(getLayoutManager().getProjection()
                            .getThemeColor());
                }

                Shape grid2DShape = new Line2D.Double(getLayoutManager()
                        .getProjection().getDevice2D().getOriginX(), gd,
                                                      getLayoutManager().getProjection().getDevice2D()
                                                              .getDeviceWidth()
                                                              + getLayoutManager().getProjection()
                                                                      .getDevice2D().getOriginX(), gd);

                g2d.draw(grid2DShape);

                if (grid.getAnnotation() != null) {
                    g2d.drawString(grid.getAnnotation(), getLayoutManager()
                            .getProjection().getDevice2D().getDeviceWidth()
                            * grid.getAnnotationFraction(), (int) gd - 2);
                }
            }

        }
    }

}
