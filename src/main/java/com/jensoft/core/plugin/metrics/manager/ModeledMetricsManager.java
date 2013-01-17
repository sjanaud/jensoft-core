/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.manager;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.window.Window2D;

/**
 * <code>IncubatorMetricsManager</code>
 * 
 * @author sebastien janaud
 */
public class ModeledMetricsManager extends AbstractMetricsManager {

    /** core timing manager */
    private List<MetricsModel> metricsModels;

    /** flag for create minimal model at runtime when a model is registered */
    private boolean createMinimal = true;

    /** model multiplier comparator */
    private MetricsModelComparator modelComparator = new MetricsModelComparator();

    /**
     * <code>MetricsModelCollections</code> tagging interface to provide metrics models
     */
    public interface MetricsModelCollections {

        /**
         * get model list of this model collection
         * @return model collection
         */
        public List<MetricsModel> getModels();
    }

    /**
     * <code>MetricsModelRangeCollections</code>
     */
    public enum MetricsModelRangeCollections implements MetricsModelCollections {
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
        private List<MetricsModel> models = new ArrayList<ModeledMetricsManager.MetricsModel>();

        /**
         * create a range metrics model collections from the negative given base exponent to positive given base
         * exponent
         * 
         * @param exp
         *            the base exponent
         */
        private MetricsModelRangeCollections(int exp) {
            for (int i = -Math.abs(exp); i <= Math.abs(exp); i++) {
                MetricsModel model = ModeledMetricsManager.createExponentModel(i);
                models.add(model);
            }
           
        }

        /**
         * get the metrics models
         * 
         * @return the models
         */
        @Override
        public List<MetricsModel> getModels() {
            return models;
        }
        
    }

    /**
     * <code>MetricsModelStandard</code> defines a collection of standard single model for common given exponent
     */
    public enum MetricsModelStandard implements MetricsModelCollections {
        /**
         * Yocto 'y' defines a model for 1E10-24 or 0.000000000000000000000001 metric system
         */
        Yocto(-24),

        /**
         * Zepto 'z' defines a model for 1E10-21 or 0.000000000000000000001 in the metric system
         */
        Zepto(-21),
        
        /**
         * Atto  'a' defines a model for 1E-18 or 0.000000000000000001 in the metric system 
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
        private List<MetricsModel> models = new ArrayList<ModeledMetricsManager.MetricsModel>();

        /**
         * create a range metrics model collections for the all given exponent array
         * 
         * @param exp
         *            array
         *            the base exponent array
         */
        private MetricsModelStandard(int... exp) {
            for (int i = 0; i < exp.length; i++) {
                MetricsModel model = ModeledMetricsManager.createExponentModel(exp[i]);
                models.add(model);
            }
        }

        /**
         * @return the models
         */
        @Override
        public List<MetricsModel> getModels() {
            return models;
        }

    }

    /**
     * create symmetric list model for given exponent (from -exponent to +exponent list model)
     * 
     * @param exp
     * @return list models
     */
    public static List<MetricsModel> createSymmetricListModel(int exp) {
        List<MetricsModel> models = new ArrayList<ModeledMetricsManager.MetricsModel>();
        for (int i = -exp; i <= exp; i++) {
            MetricsModel m = createExponentModel(i);
            models.add(m);
        }
        return models;
    }

    /**
     * create standard exponent model {@link MetricsModel} with the given exponent
     * 
     * @param exp
     *            the reference exponent model
     * @return new model
     */
    public static MetricsModel createExponentModel(int exp) {
        MetricsModel model = null;
        StringBuffer buffer = new StringBuffer();
        if (exp < 0) {
            buffer.append("0.");
            for (int j = 1; j < Math.abs(exp); j++) {
                buffer.append("0");
            }
            buffer.append("1");
            String multiplier = buffer.toString();
            String pattern = multiplier.replace('1', '0');
            model = new MetricsModel(new BigDecimal(multiplier), new DecimalFormat(pattern));

        }
        else if (exp > 0) {
            buffer.append("1");
            for (int j = 1; j <= Math.abs(exp); j++) {
                buffer.append("0");
            }
            String multiplier = buffer.toString();
            model = new MetricsModel(new BigDecimal(multiplier));

        }
        else if (exp == 0) {
            model = new MetricsModel(new BigDecimal("1"));
        }
        // System.out.println("create exponent model :"+model);
        return model;
    }

    /**
     * create the timing manager
     */
    public ModeledMetricsManager() {
        this.metricsModels = new ArrayList<MetricsModel>();
    }

    /**
     * create the timing manager with the given array of models
     */
    public ModeledMetricsManager(MetricsModel... models) {
        this();
        registerMetricsModels(models);
    }

    public class MetricsModelComparator implements Comparator<MetricsModel> {

        /*
         * (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(MetricsModel m1, MetricsModel m2) {
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
    public void registerMetricsModel(MetricsModel model) {
        MetricsModel clone = model.cloneModel();
        clone.setMetricsManager(this);
        metricsModels.add(clone);
        if (createMinimal) {
            MetricsModel minimalModel = clone.cloneModel();
            minimalModel.setMinimal(true);
            minimalModel.setMetricsManager(this);
            metricsModels.add(minimalModel);
        }
        Collections.sort(metricsModels, modelComparator);
    }

    /**
     * add the given model array
     * 
     * @param models
     */
    public void registerMetricsModels(MetricsModel... models) {
        for (int i = 0; i < models.length; i++) {
            registerMetricsModel(models[i]);
        }
    }

    /**
     * add the given model list
     * 
     * @param models
     */
    public void registerMetricsModels(List<MetricsModel> models) {
        MetricsModel[] ma = models.toArray(new MetricsModel[models.size()]);
        registerMetricsModels(ma);
    }

    /**
     * add the given models collection
     * 
     * @param modelCollections
     */
    public void registerMetricsModels(MetricsModelCollections modelCollections) {
        registerMetricsModels(modelCollections.getModels());
    }

    /**
     * unregister the given model
     * 
     * @param model
     */
    public void unregisterMetricsModel(MetricsModel model) {
        metricsModels.remove(model);
    }

    /**
     * remove the given model array
     * 
     * @param models
     */
    public void unregisterMetricsModels(MetricsModel... models) {
        for (int i = 0; i < models.length; i++) {
            unregisterMetricsModel(models[i]);
        }
    }

    /**
     * remove the given model list
     * 
     * @param models
     */
    public void unregisterMetricsModels(List<MetricsModel> models) {
        MetricsModel[] ma = models.toArray(new MetricsModel[models.size()]);
        unregisterMetricsModels(ma);
    }

    /**
     * remove the given model collection
     * 
     * @param modelCollections
     */
    public void unregisterMetricsModels(MetricsModelCollections modelCollections) {
        unregisterMetricsModels(modelCollections.getModels());
    }

    /**
     * @return the models
     */
    public List<MetricsModel> getMetricsModels() {
        return metricsModels;
    }

    /**
     * create the {@link MetricsModel} applicable sequence
     * 
     * @return applicable models
     */
    public List<MetricsModel> getSequenceMetrics() {
        List<MetricsModel> sequence = new ArrayList<MetricsModel>();
        Collections.sort(metricsModels, modelComparator);
        int rank = 0;
        for (MetricsModel metricsModel : metricsModels) {
            if (metricsModel.isValid()) {
                sequence.add(metricsModel);
            }
        }
        List<MetricsModel> remove = new ArrayList<MetricsModel>();
        for (MetricsModel metricsModel1 : sequence) {
            if (metricsModel1.isMinimal()) {
                for (MetricsModel metricsModel2 : sequence) {
                    if (!metricsModel1.equals(metricsModel2)
                            && metricsModel1.getFactor().compareTo(metricsModel2.getFactor()) == 0) {
                        remove.add(metricsModel1);
                    }
                }
            }
        }
        for (MetricsModel uselessModel : remove) {
            sequence.remove(uselessModel);
        }
        for (MetricsModel mm : sequence) {
            mm.setRank(rank++);
        }
        return sequence;
    }

    /**
     * generate {@link Metrics} for the given value
     * 
     * @param userValue
     *            the user value for this metrics
     * @return metrics
     */
    protected Metrics generateMetrics(double userValue, MetricsModel model) {
        Metrics metrics = new Metrics(getType());
        Window2D window = getRenderContext().getWindow2D();
        double deviceValue = 0;
        double maxPixelValue = 0;
        if (getType() == MetricsType.XMetrics) {
            deviceValue = window.userToPixelX(userValue);
            maxPixelValue = window.getPixelWidth();
        }
        else if (getType() == MetricsType.YMetrics) {
            deviceValue = window.userToPixelY(userValue);
            maxPixelValue = window.getPixelHeight();
        }

        if (deviceValue < 0 || deviceValue > maxPixelValue) {
            return null;
        }

        metrics.setDeviceValue(deviceValue);
        metrics.setUserValue(userValue);
        metrics.setMetricsMarkerColor(getMetricsMarkerColor());
        metrics.setMetricsLabelColor(getMetricsLabelColor());
        metrics.setLockLabel(isLockLabel());
        metrics.setLockMarker(isLockMarker());

        if (model != null && model.isMinimal()) {
            metrics.setNature(Metrics.MINOR);
        }
        else {
            metrics.setNature(Metrics.MAJOR);
            metrics.setMetricsLabel(model.formatValue(userValue));
        }

        if (model != null) {
            if (model.getMetricsLabelColor() != null) {
                metrics.setMetricsLabelColor(model.getMetricsLabelColor());
            }
            if (model.getMetricsMarkerColor() != null) {
                metrics.setMetricsMarkerColor(model.getMetricsMarkerColor());
            }
        }
        return metrics;
    }

    /*
     * (non-Javadoc)
     * @see com.jensoft.sw2d.core.plugin.metrics.manager.MetricsManager#getDeviceMetrics()
     */
    @Override
    public List<Metrics> getDeviceMetrics() {
        return new ArrayList<Metrics>();
    }

    /**
     * <code>TimeModel</code> defines a fragment of time with a specific duration in millisecond and a pixel size holder
     * 
     * @author sebastien janaud
     */
    public static class MetricsModel {

        /** metrics manager */
        private ModeledMetricsManager metricsManager;

        /** decimal format */
        private DecimalFormat decimalFormat = new DecimalFormat();

        /** metrics factor */
        private BigDecimal factor;

        /** the start reference to generate metrics */
        private BigDecimal ref;

        /** the max value to attempt */
        private BigDecimal maxValue;

        /** pixel label holder */
        private int pixelLabelHolder = 20;

        /** rank of this model */
        private int rank;

        /** metrics label color */
        private Color metricsLabelColor;

        /** metrics marker color */
        private Color metricsMarkerColor;

        /** pixelAxisHolder */
        private int pixelAxisHolder = 20;

        /** minimal tag for this domain */
        private boolean minimal = false;

        /**
         * return true if this model is applicable, false otherwise
         * 
         * @return true if this model is applicable, false otherwise
         */
        public boolean isValid() {

            Window2D window = getMetricsManager().getRenderContext().getWindow2D();
            boolean valid = false;
            BigDecimal userSize = null;
            BigDecimal startRef = null;
            BigDecimal pixelSize = null;
            BigDecimal maxUserValue = null;
            if (getMetricsManager().getType() == MetricsType.XMetrics) {
                userSize = new BigDecimal(window.getUserWidth());
                BigDecimal bd1 = new BigDecimal(window.getMinX()).divide(factor, RoundingMode.CEILING);
                BigInteger bi1 = bd1.toBigInteger();
                startRef = new BigDecimal(bi1).multiply(getFactor());
                pixelSize = new BigDecimal(window.getPixelWidth());
                maxUserValue = new BigDecimal(window.getMaxX());
            }
            else if (getMetricsManager().getType() == MetricsType.YMetrics) {
                userSize = new BigDecimal(window.getUserHeight());
                BigDecimal bd1 = new BigDecimal(window.getMinY()).divide(factor, RoundingMode.CEILING);
                BigInteger bi1 = bd1.toBigInteger();
                startRef = new BigDecimal(bi1).multiply(getFactor());
                pixelSize = new BigDecimal(window.getPixelHeight());
                maxUserValue = new BigDecimal(window.getMaxY());
            }

           

            int compare = (userSize.divide(factor, RoundingMode.HALF_EVEN))
                    .multiply(new BigDecimal(getPixelLabelHolder())).compareTo(pixelSize);

            if (compare == -1) {
                setRef(startRef);
                setMaxValue(maxUserValue);
                
                if (minimal) {
                    setPixelLabelHolder(6);
                    setPixelAxisHolder(0);
                }
                else {
                    Font f = getMetricsManager().getRenderContext().getMetricsMajorFont();
                    FontMetrics fm = getMetricsManager().getRenderContext().getGraphics2D().getFontMetrics(f);
                    String typeString = getDecimalFormat().format(startRef);
                    int typeWidth = fm.stringWidth(typeString);
                    setPixelLabelHolder(typeWidth + 12);
                    
                    //nouveau calcul pour la largeur de la chaine
                    double max = startRef.doubleValue();
                    for (double metricsValue = startRef.doubleValue(); metricsValue < maxUserValue.doubleValue(); metricsValue = metricsValue
                            + getFactor().doubleValue()) {
                       max = Math.max(max,metricsValue);
                    }
                    
                    String typeString2 = getDecimalFormat().format(max);
                    int typeWidth2 = fm.stringWidth(typeString2);
                    setPixelLabelHolder(typeWidth2 + 12);
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
         * generates all metrics for this model
         * @return {@link Metrics} list
         */
        public List<Metrics> generateMetrics() {
            List<Metrics> metrics = new ArrayList<Metrics>();
            // System.out.println("generate metrics:");
            // System.out.println("start : "+getRef());
            // System.out.println("to max : "+getMaxValue());
            // System.out.println("factor : "+getFactor());
            int count = 0;
            for (double metricsValue = getRef().doubleValue(); metricsValue < getMaxValue().doubleValue(); metricsValue = metricsValue
                    + getFactor().doubleValue()) {
                // System.out.println("generate metrics iter : "+count++);
                // System.out.println("metrics value : " + metricsValue);
                Metrics m = getMetricsManager().generateMetrics(metricsValue, this);
                if (m != null) {
                    metrics.add(m);
                }
            }
            return metrics;
        }

        /**
         * create a metrics model
         * 
         * @param factor
         */
        public MetricsModel(BigDecimal factor) {
            super();
            this.factor = factor;
        }

        /**
         * create a metrics model
         * 
         * @param factor
         * @param format
         */
        public MetricsModel(BigDecimal factor, DecimalFormat format) {
            super();
            this.factor = factor;
            this.decimalFormat = format;
        }

        /**
         * clone this model
         */
        public MetricsModel cloneModel() {
            MetricsModel clone = new MetricsModel(getFactor(), getDecimalFormat());
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
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
        public ModeledMetricsManager getMetricsManager() {
            return metricsManager;
        }

        /**
         * @param metricsManager
         *            the metricsManager to set
         */
        public void setMetricsManager(ModeledMetricsManager metricsManager) {
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
         * @return the pixelLabelHolder
         */
        public void setPixelLabelHolder(int pixelLabelHolder) {
            this.pixelLabelHolder = pixelLabelHolder;
        }

        /**
         * @return the metricsLabelColor
         */
        public Color getMetricsLabelColor() {
            return metricsLabelColor;
        }

        /**
         * @param metricsLabelColor
         *            the metricsLabelColor to set
         */
        public void setMetricsLabelColor(Color metricsLabelColor) {
            this.metricsLabelColor = metricsLabelColor;
        }

        /**
         * @return the metricsMarkerColor
         */
        public Color getMetricsMarkerColor() {
            return metricsMarkerColor;
        }

        /**
         * @param metricsMarkerColor
         *            the metricsMarkerColor to set
         */
        public void setMetricsMarkerColor(Color metricsMarkerColor) {
            this.metricsMarkerColor = metricsMarkerColor;
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
