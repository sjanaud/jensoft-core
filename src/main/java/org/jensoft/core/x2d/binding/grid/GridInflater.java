/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.binding.grid;

import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import org.jensoft.core.plugin.grid.Grid;
import org.jensoft.core.plugin.grid.GridPlugin;
import org.jensoft.core.plugin.grid.Grid.GridOrientation;
import org.jensoft.core.plugin.grid.GridPlugin.FlowGrid;
import org.jensoft.core.plugin.grid.GridPlugin.FreeGrid;
import org.jensoft.core.plugin.grid.GridPlugin.ModeledGrid;
import org.jensoft.core.plugin.grid.GridPlugin.MultiplierGrid;
import org.jensoft.core.plugin.grid.GridPlugin.StaticGrid;
import org.jensoft.core.plugin.grid.manager.ModeledGridManager.GridModelRangeCollections;
import org.jensoft.core.x2d.binding.AbstractX2DPluginInflater;
import org.jensoft.core.x2d.binding.X2DBinding;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <code>GridInflater</code>
 * 
 * @author Sebastien Janaud
 */
public abstract class GridInflater<A extends GridPlugin<?>> extends
        AbstractX2DPluginInflater<A> implements X2DGridElement {

    /**
     * <code>FlowGridInflater</code>
     * 
     * @author sebastien janaud
     */
    @X2DBinding(xsi="FlowGrid",plugin=FlowGrid.class)
    public static class FlowGridInflater extends GridInflater<FlowGrid> {

       
        /* (non-Javadoc)
         * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
         */
        @Override
        public GridPlugin.FlowGrid inflate(Element plugin) {
            Double flowStart = elementDouble(plugin, ELEMENT_GRID_FLOW_START);
            Double flowEnd = elementDouble(plugin, ELEMENT_GRID_FLOW_END);
            Double flowInterval = elementDouble(plugin, ELEMENT_GRID_FLOW_INTERVAL);

            GridPlugin.FlowGrid flow = new GridPlugin.FlowGrid(flowStart, flowEnd, flowInterval,
                                                               getGridOrientation(plugin));
            completeFromAbstract(flow, plugin);
            return flow;
        }

    }

    /**
     * <code>FreeGridInflater</code>
     * 
     * @author sebastien janaud
     */
    @X2DBinding(xsi="FreeGrid",plugin=FreeGrid.class)
    public static class FreeGridInflater extends GridInflater<FreeGrid> {


        /* (non-Javadoc)
         * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
         */
        @Override
        public GridPlugin.FreeGrid inflate(Element plugin) {

            GridPlugin.FreeGrid free = new GridPlugin.FreeGrid(getGridOrientation(plugin));
            NodeList freeMetricsElements = plugin.getElementsByTagName(ELEMENT_GRID_FREE);
            for (int i = 0; i < freeMetricsElements.getLength(); i++) {
                Element element = (Element) freeMetricsElements.item(i);
                Double value = elementDouble(element, ELEMENT_GRID_FREE_VALUE);
                Color gcolor = elementColor(element, ELEMENT_GRID_FREE_GRID_COLOR);
                Stroke stroke = elementStroke(element, ELEMENT_GRID_FREE_GRID_STROKE);
                String text = elementText(element, ELEMENT_GRID_FREE_GRID_TEXT);
                Color tcolor = elementColor(element, ELEMENT_GRID_FREE_GRID_TEXT_COLOR);
                Font font = elementFont(element, ELEMENT_GRID_FREE_GRID_TEXT_FONT);

                Grid g = new Grid(getGridOrientation(plugin));
                g.setGridUserValue(value);
                g.setGridColor(gcolor);
                g.setAnnotation(text);
                g.setGridStroke(stroke);

                free.addGrid(g);

            }

            completeFromAbstract(free, plugin);
            return free;
        }

    }

    /**
     * <code>MultiplierGridInflater</code>
     * 
     * @author sebastien janaud
     */
    @X2DBinding(xsi="MultiplierGrid",plugin=MultiplierGrid.class)
    public static class MultiplierGridInflater extends GridInflater<MultiplierGrid> {


        /* (non-Javadoc)
         * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
         */
        @Override
        public GridPlugin.MultiplierGrid inflate(Element plugin) {
            Double ref = elementDouble(plugin, ELEMENT_GRID_MULTIPLIER_REF);
            Double mul = elementDouble(plugin, ELEMENT_GRID_MULTIPLIER_MULTIPLIER);
            GridPlugin.MultiplierGrid multiplier = new GridPlugin.MultiplierGrid(ref, mul, getGridOrientation(plugin));
            completeFromAbstract(multiplier, plugin);
            return multiplier;

        }

    }

    /**
     * <code>StaticGridInflater</code>
     * 
     * @author sebastien janaud
     */
    @X2DBinding(xsi="StaticGridInflater",plugin=StaticGrid.class)
    public static class StaticGridInflater extends GridInflater<StaticGrid> {


        /* (non-Javadoc)
         * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
         */
        @Override
        public GridPlugin.StaticGrid inflate(Element plugin) {
            int gridCount = elementInteger(plugin, ELEMENT_GRID_STATIC_COUNT);
            GridPlugin.StaticGrid staticMetrics = new GridPlugin.StaticGrid(getGridOrientation(plugin), gridCount);
            completeFromAbstract(staticMetrics, plugin);
            return staticMetrics;
        }

    }

    /**
     * <code>ModeledGridInflater</code>
     * 
     * @author sebastien janaud
     */
    @X2DBinding(xsi="ModeledGrid",plugin=ModeledGrid.class)
    public static class ModeledGridInflater extends GridInflater<ModeledGrid> {

        
        /* (non-Javadoc)
         * @see com.jensoft.core.x2d.inflater.AbstractX2DPluginInflater#inflate(org.w3c.dom.Element)
         */
        @Override
        public GridPlugin.ModeledGrid inflate(Element plugin) {
            GridPlugin.ModeledGrid modeledMetrics = new ModeledGrid(getGridOrientation(plugin));
            modeledMetrics.registerGridModels(GridModelRangeCollections.YoctoYotta);
            completeFromAbstract(modeledMetrics, plugin);
            return modeledMetrics;
        }

    }

    // /**
    // * <code>TimeMetricsInflater</code>
    // *
    // * @author sebastien janaud
    // */
    // public static class TimeMetricsInflater extends GridInflater<TimeMetrics> {
    //
    // /**
    // * create time metrics inflater
    // */
    // public TimeMetricsInflater() {
    // super("AxisTimeMetrics");
    // }
    //
    
    // @Override
    // public void inflate(Element plugin) {
    //
    // // AxisMetricsPlugin.TimeMetrics timingMetrics = new AxisMetricsPlugin.TimeMetrics(getAxis(plugin));
    // //
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.Minute1Model());
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.Minute15Model());
    // //
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.HourModel());
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.Hour3Model());
    // //
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.DayNumberModel());
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.DayShortTextModel());
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.DayLongTextModel());
    // //
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.WeekModel());
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.WeekDurationDurationModel());
    // //
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.MonthModel());
    // // timingMetrics.registerTimeModel(new TimeMetricsManager.MonthDurationModel());
    // //
    // // completeFromAbstract(timingMetrics, plugin);
    // //
    // // setPlugin(timingMetrics);
    // }
    //
    // }

    protected GridOrientation getGridOrientation(Element plugin) {
        String orientation = elementText(plugin, ELEMENT_GRID_ORIENTATION);
        return GridOrientation.parse(orientation);
    }

    /**
     * Complete the grid plug-in by property of super class
     * 
     * @param abstratPlugin
     * @param pluginElement
     */
    protected void completeFromAbstract(GridPlugin<?> abstratPlugin, Element pluginElement) {

        Color gridColor = elementColor(pluginElement, ELEMENT_GRID_COLOR);
        Stroke stroke = elementStroke(pluginElement, ELEMENT_GRID_STROKE);
        Font font = elementFont(pluginElement, ELEMENT_GRID_TEXT_FONT);
        Color textColor = elementColor(pluginElement, ELEMENT_GRID_TEXT_COLOR);

        if (gridColor != null) {
            abstratPlugin.setGridColor(gridColor);
        }
        if (stroke != null) {
            abstratPlugin.setGridStroke(stroke);
        }
        if (font != null) {
            // TODO manage text font for grid
            // abstratPlugin.setTextFont(f);
        }
        if (textColor != null) {
            // TODO manage text color for grid
            // abstratPlugin.setTextColor(c);

        }

    }

   

}
