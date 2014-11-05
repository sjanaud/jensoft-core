/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.manager;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.window.Window2D;

/**
 * <code>ModeledMetricsManager</code>
 * <p>
 * Modeled metrics is based on exponent model collection
 * </p>
 * 
 * @author sebastien janaud
 */
public class ModeledMetricsManager extends AbstractMetricsManager {

    /** core timing manager */
    private List<MetricsModel> metricsModels;
    
    /** model multiplier comparator */
    private MetricsModelComparator modelComparator = new MetricsModelComparator();
    
    /**median metrics option*/
    private boolean medianMetricsOption = true;
    
    /**density threshold for median metrics have to be shown. Default is 50.0*/
    private double medianMetricsDensityThreshold = 50.0d;
    
    /**minor metrics option*/
    private boolean minorMetricsOption = true;
    
    /**density threshold for minor metrics have to be shown. Default is 80.0*/
    private double minorMetricsDensityThreshold = 80.0d;
    
    /**density factor. Density is an adjust parameter that can be make switch the model to next model by invalidate the size pixel holder.*/
    private int densityFactor = 0;

    
    /**
     * create the modeled manager.
     */
    public ModeledMetricsManager() {
      this.metricsModels =new ArrayList<ModeledMetricsManager.MetricsModel>();
      List<MetricsModel> models = createDefaultRangeExponentModel(24);
      metricsModels.addAll(models);
      Collections.sort(metricsModels, modelComparator);
    }
    
    
    /**
     * set locale to this modeled exponent model
     * @param locale 
     */
    public void applyLocalizedMetrics(Locale locale){
    	super.applyLocalizedMetrics(locale);
    	this.metricsModels.clear();
    	List<MetricsModel> models = createDefaultRangeExponentModel(24);
        metricsModels.addAll(models);
        Collections.sort(metricsModels, modelComparator);
    }
    
    
    /**
     * true if the median metrics options is enabled, false otherwise
     * @return median metrics option
     */
    public boolean isMedianMetricsOption() {
		return medianMetricsOption;
	}

    /**
     * set median metrics options
     * @param medianMetricsOption
     */
	public void setMedianMetricsOption(boolean medianMetricsOption) {
		this.medianMetricsOption = medianMetricsOption;
	}
	
	
	/**
	 * get median metrics density threshold
	 * @return median threshold
	 */
	public double getMedianMetricsDensityThreshold() {
		return medianMetricsDensityThreshold;
	}


	/**
	 * set median metrics density threshold
	 * @param medianMetricsDensityThreshold
	 */
	public void setMedianMetricsDensityThreshold(double medianMetricsDensityThreshold) {
		this.medianMetricsDensityThreshold = medianMetricsDensityThreshold;
	}


	/**
	 * true if the minor metrics options is enabled, false otherwise
	 * @return minor metrics option
	 */
	public boolean isMinorMetricsOption() {
		return minorMetricsOption;
	}

	/**
	 * set minor metrics option
	 * @param minorMetricsOption
	 */
	public void setMinorMetricsOption(boolean minorMetricsOption) {
		this.minorMetricsOption = minorMetricsOption;
	}
	
	
	/**
	 * get minor density threshold
	 * @return minor threshold
	 */
	public double getMinorMetricsDensityThreshold() {
		return minorMetricsDensityThreshold;
	}

	/**
	 * set minor density threshold
	 * @param minorMetricsDensityThreshold
	 */
	public void setMinorMetricsDensityThreshold(double minorMetricsDensityThreshold) {
		this.minorMetricsDensityThreshold = minorMetricsDensityThreshold;
	}

	/**
	 * get density factor
	 * @return density factor
	 */
	public int getDensityFactor() {
		return densityFactor;
	}

	/**
	 * set density factor
	 * @param densityFactor
	 */
	public void setDensityFactor(int densityFactor) {
		this.densityFactor = densityFactor;
	}


	/**
     * create the Default range model. 
     * <p>
     * 	create a default symmetric range model for 24 based exponent models collection by calling {@link #createModel(int)}
     * or the range exponent -24 to +24.
     * <p>
     * <p>
     * 	Override this method to provide your own range model, else override {@link #createModel(int)} for A base (-24,24) range exponent.
     * <p>
     */
    protected List<MetricsModel> createDefaultRangeExponentModel(int exponentLimit){
    	List<MetricsModel> models = new ArrayList<ModeledMetricsManager.MetricsModel>();
    	int exp = exponentLimit;
        for (int i = -Math.abs(exp); i <= Math.abs(exp); i++) {
            MetricsModel model = createModel(i);
            model.setMetricsManager(this);
            models.add(model);
        }  
        return models;
    }
    
    /**
     * <p>
     * create the default model for the given exponent.
     * <p>
     * <p>
     * Note : Override this method to create your own model provider for the given exponent.
     * </p>
     * @param exponent
     * @return the metrics model for the given exponents
     */
    protected MetricsModel createModel(int exponent){
    	return createDefaultExponentModel(exponent);
    }
    
    /**
     * default exponent model factory : create standard exponent model {@link MetricsModel} with the given exponent
     * 
     * <p>
     *  for -24, 0, the base decimal format associated with model is the simple pattern to get the real not rounded decimal.
     *  else, for exponent greater than zero, default decimal format is used.
     *  </p>
     *  <p>
     *  0.000000000000000000000001 (10 EXP -24)<br>
     *  0.00000000000000000000001  (10 EXP -23)<br>
     *  0.0000000000000000000001   (10 EXP -22)<br>
     *  0.000000000000000000001    (10 EXP -22)<br>
     *  <br>
     *  ...<br>
     *  <br>
     *  0.01						 (10 EXP -2)<br>
     *  0.1							 (10 EXP -1)<br>
     *  1                            (10 EXP 0)<br>
     *  10							 (10 EXP 1)<br>
     *  100							 (10 EXP 2)<br>
     *  <br>
     *  ...<br>
     *  <br>
     *  1000000000000000000000    (10 EXP 22)<br>
     *  10000000000000000000000   (10 EXP 22)<br>
     *  100000000000000000000000  (10 EXP 23)<br>
     *  1000000000000000000000000 (10 EXP 24)<br>
     * </p>
     * @param exp
     *            the reference exponent model
     * @return new model
     */
    public MetricsModel createDefaultExponentModel(int exp) {
        MetricsModel model = null;
        StringBuffer buffer = new StringBuffer();
        if (exp < 0) {
        	buffer.append("0.");for (int j = 1; j < Math.abs(exp); j++){buffer.append("0");}buffer.append("1");
            String multiplier = buffer.toString();
            DecimalFormat formater = (DecimalFormat)NumberFormat.getInstance(getLocale());
            //force decimal format digits to exact decimal precision
            formater.setMinimumFractionDigits(Math.abs(exp));
            formater.setMaximumFractionDigits(Math.abs(exp));
            model = new MetricsModel(exp,new BigDecimal(multiplier),formater);
        }
        else if (exp > 0) {
        	buffer.append("1");for (int j = 1; j <= Math.abs(exp); j++){buffer.append("0");}
            String multiplier = buffer.toString();
            DecimalFormat formater = (DecimalFormat)NumberFormat.getInstance(getLocale());
            model = new MetricsModel(exp,new BigDecimal(multiplier),formater);
        }
        else if (exp == 0) {
             DecimalFormat formater = (DecimalFormat)NumberFormat.getInstance(getLocale());
             model = new MetricsModel(exp, new BigDecimal("1"),formater);
        }
        return model;
    }

   
    /**
     * base model comparator
     *
     */
    public class MetricsModelComparator implements Comparator<MetricsModel> {
        /*
         * (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(MetricsModel m1, MetricsModel m2) {
        	return m1.getFactor().compareTo(m2.getFactor());
        }
    }

    /**
     * @return the models
     */
    public List<MetricsModel> getMetricsModels() {
        return metricsModels;
    }
   
    /**
     * generate {@link Metrics} for the given value
     * @param userValue
     *            the user value for this metrics
     * @param model
     * 			the given model associated to the given metrics value
     * @return metrics
     */
    protected Metrics generateMetrics(BigDecimal userValue, MetricsModel model) {
    	Metrics metrics = new Metrics(getType());
        Window2D window = getRenderContext().getWindow2D();
        double deviceValue = 0;
        double maxPixelValue = 0;
        if (getType() == MetricsType.XMetrics) {
            deviceValue = window.userToPixelX(userValue.doubleValue());
            maxPixelValue = window.getPixelWidth();
        }
        else if (getType() == MetricsType.YMetrics) {
            deviceValue = window.userToPixelY(userValue.doubleValue());
            maxPixelValue = window.getPixelHeight();
        }

        if (deviceValue < 0 || deviceValue > maxPixelValue) {
            return null;
        }
        
        metrics.setDeviceValue(deviceValue);
        metrics.setUserValueAsBigDecimal(userValue);
        metrics.setUserValue(userValue.doubleValue());
        metrics.setMetricsMarkerColor(getMetricsMarkerColor());
        metrics.setMetricsLabelColor(getMetricsLabelColor());
        metrics.setLockLabel(isLockLabel());
        metrics.setLockMarker(isLockMarker());
        metrics.setMetricsLabel(model.formatValue(userValue.doubleValue()));

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

    /**
     * check if the given metric exists in the given collection
     * @param m
     * @param metrics
     * @return true if metrics already exist in collection, false otherwise
     */
    private boolean exists(Metrics m, List<Metrics> metrics){
    	for (Metrics metrics2 : metrics) {
    		if(m.getUserValueAsBigDecimal().compareTo(metrics2.getUserValueAsBigDecimal()) == 0)
    				return true;
		}
    	return false;
    }
    
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#getDeviceMetrics()
     */
    @Override
    public final List<Metrics> getDeviceMetrics() {
    	List<Metrics> metrics= new ArrayList<Metrics>();
    	for (int i = 0; i < metricsModels.size(); i++) {
    		MetricsModel model = metricsModels.get(i);
    		model.solve();
			boolean valid = model.isValid();
			if(valid){
				
				//System.out.println(model.getExponent()+ "for metrics "+ getType() +" density : "+model.getDensity());
				
				double density = model.getDensity();
				
				List<Metrics> majors = model.generateMetrics();
				metrics.addAll(majors);
				
				if(isMedianMetricsOption() && density < getMedianMetricsDensityThreshold()){
					List<Metrics> medians = model.generateMedianMetrics();
					for (Metrics median : medians) {
						if(!exists(median, metrics)){
							metrics.add(median);
						}
					}
				 }
				
				if(isMinorMetricsOption() && density < getMinorMetricsDensityThreshold()){
					List<Metrics> minors = model.generateMinorMetrics();
					for (Metrics minor : minors) {
						if(!exists(minor, metrics)){
							metrics.add(minor);
						}
					}
				 }
				 
				return metrics;
			}
		}
    	return metrics;
    }

    /**
     * <code>MetricsModel</code> defines an exponent model.
     * 
     * @author sebastien janaud
     */
    public static class MetricsModel {

        /** metrics manager */
        private ModeledMetricsManager metricsManager;

        /** decimal format */
        private DecimalFormat decimalFormat = new DecimalFormat();

        /** model exponent */
        private int exponent;
        
        /** metrics factor */
        private BigDecimal factor;

        /** user size  */
        private BigDecimal userSize;
        
        /** pixel size  */
        private BigDecimal pixelSize;
        
        /** the start reference to generate metrics */
        private BigDecimal ref;

        /** the max value to attempt */
        private BigDecimal maxValue;

        /** pixel label holder */
        private double pixelLabelHolder;

        /** metrics label color */
        private Color metricsLabelColor;

        /** metrics marker color */
        private Color metricsMarkerColor;
        
        
        /**
         * create a metrics model with given exponent and factor
         * @param exponent
         * @param factor
         */
        public MetricsModel(int exponent,BigDecimal factor) {
            super();
            this.exponent = exponent;
            this.factor = factor;
        }

        /**
         * create a metrics model with given exponent, factor and formater
         * @param exponent
         * @param factor
         * @param format
         */
        public MetricsModel(int exponent, BigDecimal factor, DecimalFormat format) {
            super();
            this.exponent = exponent;
            this.factor = factor;
            this.decimalFormat = format;
        }


        /**
         * solve manager based on exponent model.
         * <p>
         * solve reference
         * solve user size
         * solve pixel size
         * solve max value
         * </p>
         * 
         * @return true if this model is applicable, false otherwise
         */
        public void solve() {
            Window2D window = getMetricsManager().getRenderContext().getWindow2D();
            if (getMetricsManager().getType() == MetricsType.XMetrics) {
                this.userSize = new BigDecimal(window.getUserWidth());
                BigDecimal bd1 = new BigDecimal(window.getMinX()).divide(factor, RoundingMode.CEILING);
                BigInteger bi1 = bd1.toBigInteger();
                this.ref = new BigDecimal(bi1).multiply(getFactor());
                this.ref = ref.subtract(getFactor());
                if(this.ref.equals(new BigDecimal("0"))){
                	this.ref = this.ref.subtract(this.factor);
                }
                this.pixelSize = new BigDecimal(window.getPixelWidth());
                this.maxValue = new BigDecimal(window.getMaxX());
            }
            else if (getMetricsManager().getType() == MetricsType.YMetrics) {
                this.userSize = new BigDecimal(window.getUserHeight());
                BigDecimal bd1 = new BigDecimal(window.getMinY()).divide(factor, RoundingMode.CEILING);
                BigInteger bi1 = bd1.toBigInteger();
                this.ref = new BigDecimal(bi1).multiply(getFactor());
                this.ref = ref.subtract(getFactor());
                if(this.ref.equals(new BigDecimal("0"))){
                	this.ref = this.ref.subtract(this.factor);
                }
                this.pixelSize = new BigDecimal(window.getPixelHeight());
                this.maxValue = new BigDecimal(window.getMaxY());
            }

            int metricsSize = metricsManager.getMetricsMajorFont().getSize();
            System.out.println("get size : "+metricsSize);
            int s = (this.ref.toString()).length();
            this.pixelLabelHolder = 4/5d*s*metricsSize+metricsManager.getDensityFactor();
        }
        
        
        /**
         * return approximative density for major metrics
         * @return approximative density
         */
        public double getDensity(){
        	 int s = (this.ref.toString()).length();
        	 int metricsSize = metricsManager.getMetricsMajorFont().getSize();
        	 double commonHolder =  4/5d*s*metricsSize+metricsManager.getDensityFactor();
        	 double commonSize = userSize.divide(factor, RoundingMode.HALF_EVEN).multiply(new BigDecimal(commonHolder)).doubleValue();
        	 return commonSize*100/pixelSize.doubleValue();
        }
        
        /**
         * return true if this model is applicable, false otherwise
         * <p>
         * valid model means that number of metrics, for the common pixel holder value an be include in the pixel size.
         * </p>
         * @return true if this model is applicable, false otherwise
         */
        public boolean isValid() {
             int compare = (userSize.divide(factor, RoundingMode.HALF_EVEN)).multiply(new BigDecimal(getPixelLabelHolder())).compareTo(pixelSize);
        	 return (compare == -1) ? true: false;
        }
        

        /**
         * return the formated given value with the decimal format associated with this model
         * 
         * @param value
         *            the value to format
         * @return formated value
         */
        protected String formatValue(double value) {
            return decimalFormat.format(value);
        }
       
        /**
         * generates all median metrics for this model
         * <p>
         * median metrics is the median metrics between two majors metrics according to this model with reference and factor. 
         * the median factor is the result of multiplication of the current factor by 0.5
         * </p>
         * @return {@link Metrics} list
         */
        public List<Metrics> generateMedianMetrics() {
	    	DecimalFormat medianFormater= (DecimalFormat)NumberFormat.getInstance(metricsManager.getLocale());
	    	if(exponent < 0){
	     	   medianFormater.setMaximumFractionDigits(Math.abs(exponent)+1);
	     	   medianFormater.setMinimumFractionDigits(Math.abs(exponent)+1);
	 	   	}
	    	BigDecimal originFactor = this.factor;
	    	this.factor = this.factor.multiply(new BigDecimal("0.5"));
	    	List<Metrics> metrics = generateMetrics();
	    	this.factor = originFactor;
	    	for (Metrics median : metrics) {
        	   median.setNature(Metrics.MEDIAN);
        	   median.setMetricsLabel(medianFormater.format(median.getUserValue()));
	    	}
	    	return metrics;
        }
        
        /**
         * generates all minor metrics for this model.
         * <p>
         * minor metrics is the 0.1 fraction metrics between two majors metrics according to this model with reference and factor. 
         * the minor factor is the result of multiplication of the current factor by 0.1
         * </p>
         * @return {@link Metrics} list
         */
        public List<Metrics> generateMinorMetrics() {
           BigDecimal originFactor = this.factor;
           this.factor = this.factor.multiply(new BigDecimal("0.1"));
           List<Metrics> metrics = generateMetrics();
           this.factor = originFactor;
           for (Metrics metrics2 : metrics) {
        	   metrics2.setNature(Metrics.MINOR);
        	   metrics2.setMetricsLabel(null);
           }
           return metrics;
        }
        
        /**
         * generates all major metrics for this model
         * @return {@link Metrics} list
         */
        public List<Metrics> generateMetrics() {
            List<Metrics> metrics = new ArrayList<Metrics>();
            boolean flag = true;
            BigDecimal metricsValue = this.ref;
            Metrics m0 = this.getMetricsManager().generateMetrics(metricsValue, this);
            if (m0 != null) {
            	 metrics.add(m0);
            }
            while(flag){
            	metricsValue = metricsValue.add(this.factor);
            	 Metrics m = this.getMetricsManager().generateMetrics(metricsValue, this);
                 if (m != null) {
                     metrics.add(m);
                 }
                 if(metricsValue.compareTo(this.maxValue) == 1)
                	 flag = false;
            }
            return metrics;
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
         * get exponent value
         * @return exponent
         */
        public int getExponent() {
			return exponent;
		}

        /**
         * set exponent value
         * @param exponent
         */
		public void setExponent(int exponent) {
			this.exponent = exponent;
		}

		/**
         * @return the ref
         */
        public BigDecimal getRef() {
            return ref;
        }

        /**
         * @param ref
         *            the reference to set
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
        public double getPixelLabelHolder() {
            return pixelLabelHolder;
        }

        /**
         *@param pixelLabelHolder
         */
        public void setPixelLabelHolder(double pixelLabelHolder) {
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

    }
}