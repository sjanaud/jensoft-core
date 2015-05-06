/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.grid.manager;

import java.awt.Color;
import java.awt.Stroke;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jensoft.core.plugin.PluginException;
import org.jensoft.core.plugin.grid.Grid;
import org.jensoft.core.plugin.grid.Grid.GridOrientation;
import org.jensoft.core.projection.Projection;

/**
 * <code>IncubatorMetricsManager</code>
 * 
 * @author sebastien janaud
 */
public class ModeledGridManager extends AbstractGridManager {

    /** core timing manager */
    private List<GridModel> gridModels;

    /** flag for create minimal model at runtime when a model is registered */
    private boolean createMinimal = true;

    /** model multiplier comparator */
    private GridModelComparator modelComparator = new GridModelComparator();

    /**
     * <code>MetricsModelCollections</code> tagging interface to provide metrics models
     */
    public interface GridModelCollections {

        /**
         * get model list of this model collection
         * 
         * @return model collection
         */
        public List<GridModel> getModels();
    }

    /**
     * <code>MetricsModelRangeCollections</code>
     */
    public enum GridModelRangeCollections implements GridModelCollections {
        YoctoYotta(24),
        ZeptoZeta(21),
        AttoExa(18),
        FemptoPeta(15),
        PicoTera(12),
        NanoGiga(9),
        MicroMega(6),
        
        MilliKilo(3),
        CentiHecto(2),
        DeciDeca(1),
        Identity(0);

        /** model list for range model collections */
        private List<GridModel> models = new ArrayList<ModeledGridManager.GridModel>();

        /**
         * create a range metrics model collections from the negative given base exponent to positive given base
         * exponent
         * 
         * @param exp
         *            the base exponent
         */
        private GridModelRangeCollections(int exp) {
            for (int i = -Math.abs(exp); i <= Math.abs(exp); i++) {
                GridModel model = ModeledGridManager.createExponentModel(i);
                models.add(model);
            }

        }

        /**
         * get the metrics models
         * 
         * @return the models
         */
        @Override
        public List<GridModel> getModels() {
            return models;
        }

    }

    /**
     * <code>GridModelStandard</code> defines a collection of standard single model for common given exponent
     */
    public enum GridModelStandard implements GridModelCollections {
        /**
         * Yocto 'y' defines a model for 1E10-24 or 0.000000000000000000000001 metric system
         */
        Yocto(-24),

        /**
         * Zepto 'z' defines a model for 1E10-21 or 0.000000000000000000001 in the metric system
         */
        Zepto(-21),

        /**
         * Atto 'a' defines a model for 1E-18 or 0.000000000000000001 in the metric system
         */
        Atto(-18),
        Fempto(-15),
        Pico(-12),
        Nano(-9),
        Micro(-6),
        Milli(-3),
        Centi(-2),
        Deci(-1),
        Identity(0),
        Deca(1),
        Hecto(2),
        Kilo(3),
        Mega(6),
        Giga(9),
        Tera(12),
        Peta(15),
        Exa(18),
        Zeta(21),
        Yotta(24);

        /** model list for range model collections */
        private List<GridModel> models = new ArrayList<ModeledGridManager.GridModel>();

        /**
         * create a range metrics model collections for the all given exponent array
         * 
         * @param exp
         *            array
         *            the base exponent array
         */
        private GridModelStandard(int... exp) {
            for (int i = 0; i < exp.length; i++) {
                GridModel model = ModeledGridManager.createExponentModel(exp[i]);
                models.add(model);
            }
        }

        /**
         * @return the models
         */
        @Override
        public List<GridModel> getModels() {
            return models;
        }

    }

    /**
     * create symmetric list model for given exponent (from -exponent to +exponent list model)
     * 
     * @param exp
     * @return list models
     */
    public static List<GridModel> createSymmetricListModel(int exp) {
        List<GridModel> models = new ArrayList<ModeledGridManager.GridModel>();
        for (int i = -exp; i <= exp; i++) {
            GridModel m = createExponentModel(i);
            models.add(m);
        }
        return models;
    }

    /**
     * create standard exponent model {@link GridModel} with the given exponent
     * 
     * @param exp
     *            the reference exponent model
     * @return new model
     */
    public static GridModel createExponentModel(int exp) {
        GridModel model = null;
        StringBuffer buffer = new StringBuffer();
        if (exp < 0) {
            buffer.append("0.");
            for (int j = 1; j < Math.abs(exp); j++) {
                buffer.append("0");
            }
            buffer.append("1");
            String multiplier = buffer.toString();
            String pattern = multiplier.replace('1', '0');
            model = new GridModel(new BigDecimal(multiplier), new DecimalFormat(pattern));

        }
        else if (exp > 0) {
            buffer.append("1");
            for (int j = 1; j <= Math.abs(exp); j++) {
                buffer.append("0");
            }
            String multiplier = buffer.toString();
            model = new GridModel(new BigDecimal(multiplier));

        }
        else if (exp == 0) {
            model = new GridModel(new BigDecimal("1"));
        }
        // System.out.println("create exponent model :"+model);
        return model;
    }

    /**
     * create the modeled manager
     * @param gridOrientation
     */
    public ModeledGridManager(GridOrientation gridOrientation) {
        super(gridOrientation);
        this.gridModels = new ArrayList<GridModel>();
    }

    /**
     * create the modeled manager with the given array of models
     */
    public ModeledGridManager(GridOrientation orientation,GridModel... models) {
        this(orientation);
       
        registerGridModels(models);
    }

    public class GridModelComparator implements Comparator<GridModel> {

        /*
         * (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(GridModel m1, GridModel m2) {
            int i = m1.getFactor().compareTo(m2.getFactor());
            if (i != 0) {
                return i;
            }
            else {
                if (!m1.isMinimal() && m2.isMinimal()) {
                    return 1;
                }
                else if (m1.isMinimal() && m2.isMinimal()) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        }

    }

    /**
     * add the given model
     * 
     * @param model
     */
    public void registerGridModel(GridModel model) {
        GridModel clone = model.cloneModel();
        clone.setMetricsManager(this);
        gridModels.add(clone);
         if (createMinimal) {
         GridModel minimalModel = clone.cloneModel();
         minimalModel.setMinimal(true);
         minimalModel.setMetricsManager(this);
         gridModels.add(minimalModel);
         }
        Collections.sort(gridModels, modelComparator);
    }

    /**
     * add the given {@link GridModel} array
     * 
     * @param models
     */
    public void registerGridModels(GridModel... models) {
        for (int i = 0; i < models.length; i++) {
            registerGridModel(models[i]);
        }
    }

    /**
     * add the given {@link GridModel} list
     * 
     * @param models
     */
    public void registerGridModels(List<GridModel> models) {
        GridModel[] ma = models.toArray(new GridModel[models.size()]);
        registerGridModels(ma);
    }

    /**
     * add the given {@link GridModelCollections}
     * 
     * @param modelCollections
     */
    public void registerGridModels(GridModelCollections modelCollections) {
        registerGridModels(modelCollections.getModels());
    }

    /**
     * unregister the given {@link GridModel}
     * 
     * @param model
     */
    public void unregisterGridModel(GridModel model) {
        gridModels.remove(model);
    }

    /**
     * remove the given {@link GridModel} array
     * 
     * @param models
     */
    public void unregisterGridModels(GridModel... models) {
        for (int i = 0; i < models.length; i++) {
            unregisterGridModel(models[i]);
        }
    }

    /**
     * remove the given {@link GridModel} list
     * 
     * @param models
     */
    public void unregisterGridModels(List<GridModel> models) {
        GridModel[] ma = models.toArray(new GridModel[models.size()]);
        unregisterGridModels(ma);
    }

    /**
     * remove the given {@link GridModelCollections} collection
     * 
     * @param modelCollections
     */
    public void unregisterGridModels(GridModelCollections modelCollections) {
        unregisterGridModels(modelCollections.getModels());
    }

    /**
     * get all registered {@link GridModel}
     * 
     * @return the models
     */
    public List<GridModel> getGridModels() {
        return gridModels;
    }

    /**
     * create the {@link GridModel} applicable sequence
     * 
     * @return applicable models
     */
    public List<GridModel> getSequenceGrid() {
        List<GridModel> sequence = new ArrayList<GridModel>();
        Collections.sort(gridModels, modelComparator);
        int rank = 0;
        for (GridModel metricsModel : gridModels) {
        	
            if (metricsModel.isValid()) {
            	
                sequence.add(metricsModel);
            }
        }
        List<GridModel> remove = new ArrayList<GridModel>();
        for (GridModel model1 : sequence) {
            if (model1.isMinimal()) {
                for (GridModel model2 : sequence) {
                    if (!model1.equals(model2)
                            && model1.getFactor().compareTo(model2.getFactor()) == 0) {
                        remove.add(model1);
                    }
                }
            }
        }
        for (GridModel uselessModel : remove) {
            sequence.remove(uselessModel);
        }
        for (GridModel mm : sequence) {
            mm.setRank(rank++);
        }
        //System.out.println("grid size list :"+sequence.size());
        return sequence;
    }

    /**
     * generate {@link Grid} for the given value
     * 
     * @param userValue
     *            the user grid x or y value
     * @return grid
     */
    protected Grid generateGrid(double userValue, GridModel model) {
        double deviceValue = 0;
        double maxPixelValue = 0;
        if (getGridOrientation() == GridOrientation.Vertical) {
            deviceValue = getProjection().userToPixelX(userValue);
            maxPixelValue = getProjection().getPixelWidth();
        }
        else if (getGridOrientation() == GridOrientation.Horizontal) {
            deviceValue = getProjection().userToPixelY(userValue);
            maxPixelValue = getProjection().getPixelHeight();
        }

        if (deviceValue < 0 || deviceValue > maxPixelValue) {
            return null;
        }
        Grid grid = new Grid(getGridOrientation());
        grid.setGridDeviceValue(deviceValue);
        if (getGridColor() != null) {
            grid.setGridColor(getGridColor());
        }
        else {
            grid.setGridColor(GridManager.DEFAULT_BLACKCOLOR);
        }

        if (getGridStroke() != null) {
            grid.setGridStroke(getGridStroke());
        }
        else {
            grid.setGridStroke(GridManager.DEFAULT_BASICSTROKE);
        }

        if (model != null) {
            if (model.getGridColor() != null) {
                grid.setGridColor(model.getGridColor());
            }
            if (model.getGridStroke() != null) {
                grid.setGridStroke(model.getGridStroke());
            }
        }
        return grid;

    }

    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.grid.manager.GridManager#getGrids()
     */
    @Override
    public List<Grid> getGrids() {
        return new ArrayList<Grid>();
    }

    /**
     * <code>TimeModel</code> defines a fragment of time with a specific duration in millisecond and a pixel size holder
     * 
     * @author sebastien janaud
     */
    public static class GridModel {

        /** metrics manager */
        private ModeledGridManager metricsManager;

        /** decimal format */
        private DecimalFormat decimalFormat = new DecimalFormat();

        /** metrics factor */
        private BigDecimal factor;

        /** the start reference to generate metrics */
        private BigDecimal ref;

        /** the max value to attempt */
        private BigDecimal maxValue;

        /** pixel label holder */
        private int pixelLabelHolder = 6;

        /** rank of this model */
        private int rank;

        /** model grid color */
        private Color gridColor;

        /** model grid stroke */
        private Stroke gridStroke;

        /** pixelAxisHolder */
        private int pixelAxisHolder = 4;

        /** minimal tag for this domain */
        private boolean minimal = false;

        /**
         * return true if this model is applicable, false otherwise
         * 
         * @return true if this model is applicable, false otherwise
         */
        public boolean isValid() {

            Projection projection = metricsManager.getProjection();
            boolean valid = false;
            BigDecimal userSize = null;
            BigDecimal startRef = null;
            BigDecimal pixelSize = null;
            BigDecimal maxUserValue = null;
            if (getMetricsManager().getGridOrientation() == GridOrientation.Vertical) {
                userSize = new BigDecimal(projection.getUserWidth());
                BigDecimal bd1 = new BigDecimal(projection.getMinX()).divide(factor, RoundingMode.CEILING);
                BigInteger bi1 = bd1.toBigInteger();
                startRef = new BigDecimal(bi1).multiply(getFactor());
                pixelSize = new BigDecimal(projection.getPixelWidth());
                maxUserValue = new BigDecimal(projection.getMaxX());
            }
            else if (getMetricsManager().getGridOrientation() == GridOrientation.Horizontal) {
                userSize = new BigDecimal(projection.getUserHeight());
                BigDecimal bd1 = new BigDecimal(projection.getMinY()).divide(factor, RoundingMode.CEILING);
                BigInteger bi1 = bd1.toBigInteger();
                startRef = new BigDecimal(bi1).multiply(getFactor());
                pixelSize = new BigDecimal(projection.getPixelHeight());
                maxUserValue = new BigDecimal(projection.getMaxY());
            }
            else{
                throw new PluginException("Grid orientation should be supply.");
            }

           // System.out.println("holder : "+getPixelLabelHolder());
            int compare = (userSize.divide(factor, RoundingMode.HALF_EVEN))
                    .multiply(new BigDecimal(getPixelLabelHolder())).compareTo(pixelSize);

            if (compare == -1) {
                setRef(startRef);
                setMaxValue(maxUserValue);

                if (minimal) {
                    setPixelLabelHolder(10);
                    setPixelAxisHolder(0);
                }
                else {
                    // Font f = getMetricsManager().getRenderContext().getMetricsMajorFont();
                    // FontMetrics fm = getMetricsManager().getRenderContext().getGraphics2D().getFontMetrics(f);
                    // String typeString = getDecimalFormat().format(startRef);
                    // int typeWidth = fm.stringWidth(typeString);
                    // setPixelLabelHolder(typeWidth + 12);
                    //
                    // //nouveau calcul pour la largeur de la chaine
                    // double max = startRef.doubleValue();
                    // for (double metricsValue = startRef.doubleValue(); metricsValue < maxUserValue.doubleValue();
                    // metricsValue = metricsValue
                    // + getFactor().doubleValue()) {
                    // max = Math.max(max,metricsValue);
                    // }
                    //
                    // String typeString2 = getDecimalFormat().format(max);
                    // int typeWidth2 = fm.stringWidth(typeString2);
                    // setPixelLabelHolder(typeWidth2 + 12);

                    setPixelLabelHolder(20);
                }
                valid = true;
                // System.out.println("start ref : "+startRef);
            }
            else {
                valid = false;
            }
            return valid;
        }

        /**
         * return the formated given value
         * 
         * @param value
         *            the value to format
         * @return formated value
         */
        protected String formatValue(double value) {
            return decimalFormat.format(value);
        }

        /**
         * @param minimal
         *            the minimal to set
         */
        public void setMinimal(boolean minimal) {
            this.minimal = minimal;
        }

        /**
         * @return the minimal
         */
        public boolean isMinimal() {
            return minimal;
        }

        /**
         * generates all grids for this model
         * 
         * @return {@link Grid} list
         */
        public List<Grid> generateGrids() {
            List<Grid> grids = new ArrayList<Grid>();
            // System.out.println("generate grids:");
            // System.out.println("start : "+getRef());
            // System.out.println("to max : "+getMaxValue());
            // System.out.println("factor : "+getFactor());
            int count = 0;
            for (double metricsValue = getRef().doubleValue(); metricsValue < getMaxValue().doubleValue(); metricsValue = metricsValue
                    + getFactor().doubleValue()) {
                // System.out.println("generate metrics iter : "+count++);
                // System.out.println("metrics value : " + metricsValue);
                Grid m = getMetricsManager().generateGrid(metricsValue, this);
                if (m != null) {
                    grids.add(m);
                }
            }
            return grids;
        }

        /**
         * create a metrics model
         * 
         * @param factor
         */
        public GridModel(BigDecimal factor) {
            super();
            this.factor = factor;
        }

        /**
         * create a metrics model
         * 
         * @param factor
         * @param format
         */
        public GridModel(BigDecimal factor, DecimalFormat format) {
            super();
            this.factor = factor;
            this.decimalFormat = format;
        }

        /**
         * clone this model
         */
        public GridModel cloneModel() {
            GridModel clone = new GridModel(getFactor(), getDecimalFormat());
            clone.setMinimal(isMinimal());
            clone.setGridStroke(getGridStroke());
            clone.setGridColor(getGridColor());
            return clone;
        }

        /**
         * get the model rank
         * 
         * @return the rank
         */
        public int getRank() {
            return rank;
        }

        /**
         * set model rank
         * 
         * @param rank
         *            the rank to set
         */
        public void setRank(int rank) {
            this.rank = rank;
        }

        /**
         * @return the pixelAxisHolder
         */
        public int getPixelAxisHolder() {
            return pixelAxisHolder;
        }

        /**
         * @param pixelAxisHolder
         *            the pixelAxisHolder to set
         */
        public void setPixelAxisHolder(int pixelAxisHolder) {
            this.pixelAxisHolder = pixelAxisHolder;
        }

        /**
         * @return the metricsManager
         */
        public ModeledGridManager getMetricsManager() {
            return metricsManager;
        }

        /**
         * @param metricsManager
         *            the metricsManager to set
         */
        public void setMetricsManager(ModeledGridManager metricsManager) {
            this.metricsManager = metricsManager;
        }

        /**
         * @return the ref
         */
        public BigDecimal getRef() {
            return ref;
        }

        /**
         * @param ref
         *            the ref to set
         */
        public void setRef(BigDecimal ref) {
            this.ref = ref;
        }

        /**
         * @return the maxValue
         */
        public BigDecimal getMaxValue() {
            return maxValue;
        }

        /**
         * @param maxValue
         *            the maxValue to set
         */
        public void setMaxValue(BigDecimal maxValue) {
            this.maxValue = maxValue;
        }

        /**
         * @return the decimalFormat
         */
        public DecimalFormat getDecimalFormat() {
            return decimalFormat;
        }

        /**
         * @param decimalFormat
         *            the decimalFormat to set
         */
        public void setDecimalFormat(DecimalFormat decimalFormat) {
            this.decimalFormat = decimalFormat;
        }

        /**
         * @return the factor
         */
        public BigDecimal getFactor() {
            return factor;
        }

        /**
         * @return the pixelLabelHolder
         */
        public int getPixelLabelHolder() {
            return pixelLabelHolder;
        }

        /**
         * @param pixelLabelHolder
         */
        public void setPixelLabelHolder(int pixelLabelHolder) {
            this.pixelLabelHolder = pixelLabelHolder;
        }

        /**
         * @return the gridColor
         */
        public Color getGridColor() {
            return gridColor;
        }

        /**
         * @param gridColor
         *            the gridColor to set
         */
        public void setGridColor(Color gridColor) {
            this.gridColor = gridColor;
        }

        /**
         * @return the gridStroke
         */
        public Stroke getGridStroke() {
            return gridStroke;
        }

        /**
         * @param stroke
         *            the stroke to set
         */
        public void setGridStroke(Stroke stroke) {
            this.gridStroke = stroke;
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "MetricsModel [factor=" + factor + ", pixelLabelHolder=" + pixelLabelHolder + ", pixelAxisHolder="
                    + pixelAxisHolder + ", decimalFormat=" + decimalFormat.toPattern() + "]";
        }

    }

}
