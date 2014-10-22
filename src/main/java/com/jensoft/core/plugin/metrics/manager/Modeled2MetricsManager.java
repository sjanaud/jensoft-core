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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
public class Modeled2MetricsManager extends AbstractMetricsManager {

    /** core timing manager */
    private List<MetricsModel2> metricsModels;

    
    /** model multiplier comparator */
    private MetricsModelComparator modelComparator = new MetricsModelComparator();

    /**
     * create symmetric list model for given exponent (from -exponent to +exponent list model)
     * 
     * @param exp
     * @return list models
     */
    public static List<MetricsModel2> createSymmetricListModel(int exp) {
        List<MetricsModel2> models = new ArrayList<Modeled2MetricsManager.MetricsModel2>();
        for (int i = -exp; i <= exp; i++) {
            MetricsModel2 m = createExponentModel(i);
            models.add(m);
        }
        return models;
    }

    /**
     * create standard exponent model {@link MetricsModel2} with the given exponent
     * @param exp
     *            the reference exponent model
     * @return new model
     */
    public static MetricsModel2 createExponentModel(int exp) {
        MetricsModel2 model = null;
        StringBuffer buffer = new StringBuffer();
        if (exp < 0) {
            buffer.append("0.");
            for (int j = 1; j < Math.abs(exp); j++){buffer.append("0");}
            buffer.append("1");
            String multiplier = buffer.toString();
            String pattern = multiplier.replace('1', '0');
            model = new MetricsModel2(exp,new BigDecimal(multiplier),pattern, new DecimalFormat(pattern));
        }
        else if (exp > 0) {
            buffer.append("1");
            for (int j = 1; j <= Math.abs(exp); j++){buffer.append("0");}
            String multiplier = buffer.toString();
            model = new MetricsModel2(exp,new BigDecimal(multiplier));
        }
        else if (exp == 0) {
            model = new MetricsModel2(exp,new BigDecimal("1"));
        }
        return model;
    }

    /**
     * create the timing manager
     */
    public Modeled2MetricsManager() {
      this.metricsModels = new ArrayList<MetricsModel2>();
      int exp = 24;
      for (int i = -Math.abs(exp); i <= Math.abs(exp); i++) {
          MetricsModel2 model = Modeled2MetricsManager.createExponentModel(i);
          model.setMetricsManager(this);
          metricsModels.add(model);
      }   
      Collections.sort(metricsModels, modelComparator);
    }

   

    public class MetricsModelComparator implements Comparator<MetricsModel2> {
        /*
         * (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(MetricsModel2 m1, MetricsModel2 m2) {
        	return m1.getFactor().compareTo(m2.getFactor());
        }
    }

    /**
     * @return the models
     */
    public List<MetricsModel2> getMetricsModels() {
        return metricsModels;
    }
   
    /**
     * generate {@link Metrics} for the given value
     * @param userValue
     *            the user value for this metrics
     * @return metrics
     */
    protected Metrics generateMetrics(BigDecimal userValue, MetricsModel2 model) {
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
        metrics.setMetricsLabel(userValue+"");
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
    public List<Metrics> getDeviceMetrics() {
    	List<Metrics> metrics= new ArrayList<Metrics>();
    	for (int i = 0; i < metricsModels.size(); i++) {
    		MetricsModel2 model = metricsModels.get(i);
    		model.solve();
    		//System.out.println("check "+model.getExponent()+ "for metrics "+ getType() +" density : "+model.getDensity());
			boolean valid = model.isValid();
			if(valid){
				System.out.println("Apply model : "+model.getExponent());
				System.out.println("apprx density / real density : "+model.getDensity()+"/"+model.getRealDensity());
				
				//System.out.println(model.getExponent()+ "for metrics "+ getType() +" density : "+model.getDensity());
				double density = model.getDensity();
				List<Metrics> majors = model.generateMetrics();
				metrics.addAll(majors);
				
//				if(density < 50){
//					List<Metrics> medians = model.generateMedianMetrics();
//					for (Metrics median : medians) {
//						if(!exists(median, metrics)){
//							metrics.add(median);
//						}
//					}
//				 }
				
				if(density < 80){
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
     * <code>TimeModel</code> defines a fragment of time with a specific duration in millisecond and a pixel size holder
     * 
     * @author sebastien janaud
     */
    public static class MetricsModel2 {

        /** metrics manager */
        private Modeled2MetricsManager metricsManager;

        /** decimal format */
        private DecimalFormat decimalFormat = new DecimalFormat();

        /** model exponent */
        private int exponent;
        
        /**decimal pattern*/
        private String decimalPattern;
        
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
         * solve manager based on exponent model.
         * <p>
         * solve reference
         * user size
         * pixel size
         * max value
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

            //TODO : get major and median font size.
            //TODO, change font size from metrics render context
            //TODO, change this block in appropriate call method
            
            int s = (this.ref.toString()).length();
            this.pixelLabelHolder = 4/5d*s*12;
        }
        
        
        /**
         * return approximative density for major metrics
         * @return approximative density
         */
        public double getDensity(){
        	 int s = (this.ref.toString()).length();
        	 double commonHolder =  4/5d*s*12;
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
             int compare = (userSize.divide(factor, RoundingMode.HALF_EVEN)) .multiply(new BigDecimal(getPixelLabelHolder())).compareTo(pixelSize);
        	 return (compare == -1) ? true: false;
        }
        
        /**
         * return real density for major metrics
         * @return real density
         */
        public double getRealDensity() {
        	 List<Metrics> majors = generateMetrics();
        	 String total="";
        	 for (Metrics metrics : majors) {
				total=total+metrics.getMetricsLabel();
			 }
        	 int s = total.length();
             double pixelTotalMetrics = 4/5d*s*12;
        	 System.out.println("test model exp : "+getExponent()+" : total pixel metrics : "+pixelTotalMetrics+" : "+total+" and pixel size : "+pixelSize);
             
        	 return pixelTotalMetrics*100/pixelSize.doubleValue();
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
         * generates all median metrics for this model
         * @return {@link Metrics} list
         */
        public List<Metrics> generateMedianMetrics() {
           BigDecimal originFactor = this.factor;
           this.factor = this.factor.multiply(new BigDecimal("0.5"));
           List<Metrics> metrics = generateMetrics();
           this.factor = originFactor;
           for (Metrics median : metrics) {
        	   median.setNature(Metrics.MEDIAN);
        	   if(getDecimalPattern() != null){ 
					DecimalFormat format = new DecimalFormat(getDecimalPattern()+"0");
					median.setMetricsLabel(format.format(median.getUserValue()));
				}
           }
           return metrics;
        }
        
        /**
         * generates all minor metrics for this model
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
         * create a metrics model
         * 
         * @param factor
         */
        public MetricsModel2(int exponent,BigDecimal factor) {
            super();
            this.exponent = exponent;
            this.factor = factor;
        }

        /**
         * create a metrics model
         * 
         * @param factor
         * @param format
         */
        public MetricsModel2(int exponent, BigDecimal factor, String decimalPattern, DecimalFormat format) {
            super();
            this.exponent = exponent;
            this.factor = factor;
            this.decimalPattern = decimalPattern;
            this.decimalFormat = format;
        }


        /**
         * @return the metricsManager
         */
        public Modeled2MetricsManager getMetricsManager() {
            return metricsManager;
        }

        /**
         * @param metricsManager
         *            the metricsManager to set
         */
        public void setMetricsManager(Modeled2MetricsManager metricsManager) {
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

        /**
         * get decimal pattern
         * @return decimal pattern
         */
		public String getDecimalPattern() {
			return decimalPattern;
		}

		/**
		 * set decimal pattern
		 * @param decimalPattern
		 */
		public void setDecimalPattern(String decimalPattern) {
			this.decimalPattern = decimalPattern;
		}
    }
}