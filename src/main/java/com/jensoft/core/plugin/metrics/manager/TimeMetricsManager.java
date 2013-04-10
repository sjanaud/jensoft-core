/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.metrics.manager;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.jensoft.core.plugin.metrics.geom.Metrics;
import com.jensoft.core.plugin.metrics.geom.Metrics.MetricsType;
import com.jensoft.core.window.Window2D;
import com.jensoft.core.window.Window2D.Linear;
import com.jensoft.core.window.Window2D.Time;

/**
 * <code>TimeMetricsManager</code> takes the responsibility to manage timing metrics
 * 
 * @author sebastien janaud
 */
public class TimeMetricsManager extends AbstractMetricsManager {

    private static long SECOND_MILLIS = 1000L;
    private static long MINUTE_MILLIS = SECOND_MILLIS * 60L;
    private static long HOUR_MILLIS = MINUTE_MILLIS * 60L;
    private static long DAY_MILLIS = HOUR_MILLIS * 24L;
    private static long WEEK_MILLIS = DAY_MILLIS * 7L;
    private static long MONTH_MILLIS = WEEK_MILLIS * 4L;
    private static long YEAR_MILLIS = MONTH_MILLIS * 12L;
    private static long DECENNARY_MILLIS = YEAR_MILLIS * 10L;
    private static long CENTURY_MILLIS = DECENNARY_MILLIS * 10L;

    public static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat monthFormat = new SimpleDateFormat("MMMMMMMMMMM");
    public static SimpleDateFormat monthShortFormat = new SimpleDateFormat("MMM");
    public static SimpleDateFormat secondFormat = new SimpleDateFormat("ss");
    public static SimpleDateFormat minuteFormat = new SimpleDateFormat("mm ' m'");
    public static SimpleDateFormat hourFormat = new SimpleDateFormat("HH ' h'");
    public static SimpleDateFormat dayNumberFormat = new SimpleDateFormat("dd");
    public static SimpleDateFormat dayLabelFormat = new SimpleDateFormat("EEEEEEEEE, dd");
    public static SimpleDateFormat dayLongLabelFormat = new SimpleDateFormat("EEEEEEEEE, dd ,MMMM");
    public static SimpleDateFormat weekFormat = new SimpleDateFormat("'W.' ww");

    // public static TimeModel[] MinutesModels = new TimeModel[] { new Minute1Model(), new Minute1ModelMinimal(), new
    // Minute10Model(), new Minute10ModelMinimal(),
    // new Minute20Model(), new Minute20ModelMinimal(), };

    // /**
    // * <code>TimingMetricsModelCollections</code>
    // * tagging interface to provide timing metrics models
    // *
    // */
    // public interface TimingMetricsModelCollections {
    // public List<TimeModel> getModels();
    // }
    //
    // /**
    // * <code>TimingMetricsStandardModel</code>
    // */
    // public enum TimingMetricsStandardModel implements TimingMetricsModelCollections {
    // All(),
    // Tot;
    // /** model list for timing model collections */
    // private List<TimeModel> models = new ArrayList<TimeModel>();
    //
    // private TimingMetricsStandardModel(TimeModel...model){
    // for (int i = 0; i < model.length; i++) {
    // models.add(model[i]);
    // }
    // }
    //
    // public List<TimeModel> getModels(){
    // return models;
    // }
    // }

    /** core timing manager */
    private List<TimeModel> timingModels;

    /** flag for create minimal model at runtime when a model is registered */
    private boolean createMinimal = true;

    /** time model comparator */
    private TimeModelComparator modelComparator = new TimeModelComparator();

    /**
     * create the timing manager
     */
    public TimeMetricsManager() {
        this.timingModels = new ArrayList<TimeModel>();
    }

    /**
     * create the timing manager with the given array of models
     * 
     * @param models
     *            the model array to register
     */
    public TimeMetricsManager(TimeModel... models) {
        this();
        registerTimingModels(models);
    }

    /**
     * create the timing manager with the given array of models
     * 
     * @param models
     *            the model array to register
     */
    public TimeMetricsManager(List<TimeModel> models) {
        this();
        registerTimingModels(models);
    }

   
    /* (non-Javadoc)
     * @see com.jensoft.core.plugin.metrics.manager.MetricsManager#getDeviceMetrics()
     */
    @Override
    public List<Metrics> getDeviceMetrics() {
        return new ArrayList<Metrics>();
    }

    /**
     * <code>TimeModelComparator</code>
     */
    public class TimeModelComparator implements Comparator<TimeModel> {

        /*
         * (non-Javadoc)
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(TimeModel tm1, TimeModel tm2) {
            if (tm1.getMillis() > tm2.getMillis()) {
                return 1;
            }
            else if (tm1.getMillis() > tm2.getMillis()) {
                return -1;
            }
            else {
                if (!tm1.isMinimal() && tm2.isMinimal()) {
                    return 1;
                }
                else if (tm1.isMinimal() && !tm2.isMinimal()) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        }

    }

    /**
     * add the given timing model
     * 
     * @param model
     */
    public void registerTimingModel(TimeModel model) {

        TimeModel clone = model.cloneModel();

        clone.setTimingManager(this);
        timingModels.add(clone);

        if (createMinimal) {
            TimeModel minimalModel = clone.cloneModel();
            minimalModel.setMinimal(true);
            minimalModel.setTimingManager(this);
            timingModels.add(minimalModel);
        }

        Collections.sort(timingModels, modelComparator);
    }

    /**
     * add the given timing model array
     * 
     * @param models
     */
    public void registerTimingModels(TimeModel... models) {
        for (int i = 0; i < models.length; i++) {
            registerTimingModel(models[i]);
        }
    }

    /**
     * add the given model list
     * 
     * @param models
     */
    public void registerTimingModels(List<TimeModel> models) {
        TimeModel[] ma = models.toArray(new TimeModel[models.size()]);
        registerTimingModels(ma);
    }

    /**
     * unregister the given timing model
     * 
     * @param model
     */
    public void unregisterTimingModel(TimeModel model) {
        timingModels.remove(model);
    }

    /**
     * remove the given timing model array
     * 
     * @param models
     */
    public void unregisterTimingModels(TimeModel... models) {
        for (int i = 0; i < models.length; i++) {
            unregisterTimingModel(models[i]);
        }
    }

    /**
     * remove the given model array
     * 
     * @param models
     */
    public void unregisterTimingModels(List<TimeModel> models) {
        TimeModel[] ma = models.toArray(new TimeModel[models.size()]);
        unregisterTimingModels(ma);
    }

    /**
     * @return the timingModels
     */
    public List<TimeModel> getTimingModels() {
        return timingModels;
    }

    /**
     * get the applicable sequence metrics timing according to managers timing models
     * 
     * @return timing sequence
     */
    public List<TimeModel> getTimingSequence() {
        Window2D.Time timeWindow = getTimingWindow();
        Collections.sort(timingModels, modelComparator);
        List<TimeModel> sequence = new ArrayList<TimeModel>();
        int rank = 0;
        for (TimeModel timingManager : timingModels) {
            long windowTimeMillis = timeWindow.durationMillis();
            long modelTimeMillis = timingManager.getMillis();
            if ((windowTimeMillis / modelTimeMillis) * timingManager.getPixelLabelHolder() < timeWindow
                    .getTimeDurationPixel()) {
                timingManager.setRank(rank++);
                sequence.add(timingManager);
            }
        }
        return sequence;
    }

    /**
     * <code>TimeModel</code> defines a fragment of time with a specific duration in millisecond and a pixel size holder
     * 
     * @author sebastien janaud
     */
    public abstract static class TimeModel {

        private TimeMetricsManager timingManager;

        /** time millis duration */
        private long millis;

        /** pixel label holder */
        private int pixelLabelHolder = 6;

        /** family */
        private String familyName;

        /** name of model */
        private String name;

        /** unit */
        private String unit;

        /** rank of this model */
        private int rank;

        /** metrics label color */
        private Color metricsLabelColor;

        /** metrics marker color */
        private Color metricsMarkerColor;

        /** pixelAxisHolder */
        private int pixelAxisHolder = 20;

        /** minimal tag */
        private boolean minimal = false;

        /**
         * generate the metrics for this model
         * 
         * @return generated metrics for this model
         */
        public abstract List<Metrics> generateMetrics();

        /**
         * clone this model
         */
        public abstract TimeModel cloneModel();

        /**
         * create a time model with given duration and holder for the given family
         * 
         * @param millis
         * @param pixelLabelHolder
         * @param famillyName
         * @param name
         * @param unit
         */
        public TimeModel(long millis, int pixelLabelHolder, String famillyName, String name, String unit,
                int pixelAxisHolder) {
            super();
            this.millis = millis;
            this.pixelLabelHolder = pixelLabelHolder;
            this.familyName = famillyName;
            this.name = name;
            this.unit = unit;
            this.pixelAxisHolder = pixelAxisHolder;
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
         * @return the timingManager
         */
        public TimeMetricsManager getTimingManager() {
            return timingManager;
        }

        /**
         * @param timingManager
         *            the timingManager to set
         */
        public void setTimingManager(TimeMetricsManager timingManager) {
            this.timingManager = timingManager;
        }

        /**
         * @return the millis
         */
        public long getMillis() {
            return millis;
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
         * @return the famillyName
         */
        public String getFamilyName() {
            return familyName;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @return the unit
         */
        public String getUnit() {
            return unit;
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
         * @return the minimal
         */
        public boolean isMinimal() {
            return minimal;
        }

        /**
         * tag model as minimal
         */
        public void setMinimal(boolean minimal) {
            this.minimal = minimal;
            if (minimal) {
                setPixelLabelHolder(6);
                setPixelAxisHolder(0);
            }
            else {
                setPixelLabelHolder(20);
                setPixelAxisHolder(18);
            }
        }

        /*
         * (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "TimeModel [millis=" + millis + ", pixelHolder=" + pixelLabelHolder + ", familyName=" + familyName
                    + ", name=" + name + ", unit=" + unit + "]";
        }

    }

    /**
     * get the window worker
     * 
     * @return
     */
    public Window2D.Time getTimingWindow() {
        Window2D window = getRenderContext().getWindow2D();
        if (window instanceof Linear) {
            if (getType() == MetricsType.XMetrics) {
                Window2D.TimeX timeX = new Window2D.TimeX(new Date(new Double(window.getMinX()).longValue()),
                                                          new Date(new Double(window.getMaxX()).longValue()),
                                                          window.getMinY(), window.getMaxY());
                timeX.setDevice2D(window.getDevice2D());
                timeX.setView2D(window.getView2D());
                return timeX;
            }
            else if (getType() == MetricsType.YMetrics) {
                Window2D.TimeY timeY = new Window2D.TimeY(window.getMinX(), window.getMaxX(),
                                                          new Date(new Double(window.getMinY()).longValue()),
                                                          new Date(new Double(window.getMaxY()).longValue()));
                timeY.setDevice2D(window.getDevice2D());
                timeY.setView2D(window.getView2D());
                return timeY;
            }
        }
        if (window instanceof Time) {
            return (Window2D.Time) window;
        }
        return null;
    }

    /**
     * generate a metrics for the given calendar
     * 
     * @param time
     *            the time for this metrics
     * @return metrics
     */
    protected TimePointMetrics generateMetricsPoint(Calendar time, TimeModel model) {
        TimePointMetrics metrics = new TimePointMetrics(getType());
        metrics.setTime(time.getTime());
        double userValue = new Long(time.getTimeInMillis()).doubleValue();
        Window2D.Time timingWindow = getTimingWindow();
        double deviceValue = timingWindow.timeToPixel(time.getTime());
        double max = timingWindow.getTimeDurationPixel();

        if (deviceValue < 0 || deviceValue > max)
            return null;

        metrics.setDeviceValue(deviceValue);
        metrics.setUserValue(userValue);
        metrics.setMetricsMarkerColor(getMetricsMarkerColor());
        metrics.setMetricsLabelColor(getMetricsLabelColor());
        metrics.setLockLabel(isLockLabel());
        metrics.setLockMarker(isLockMarker());

        if (model.isMinimal()) {
            metrics.setNature(Metrics.MINOR);
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

    /**
     * generate metrics duration for the given start and end time
     * 
     * @param startTime
     *            the start time of the duration
     * @param endTime
     *            the end time of the duration
     * @return time duration
     */
    protected TimeDurationMetrics generateMetricsDuration(Calendar startTime, Calendar endTime, TimeModel model) {
        TimePointMetrics pointStart = generateMetricsPoint(startTime, model);
        TimePointMetrics pointEnd = generateMetricsPoint(endTime, model);

        long centerMillis = startTime.getTimeInMillis() + (endTime.getTimeInMillis() - startTime.getTimeInMillis()) / 2;
        Calendar middleTime = Calendar.getInstance();
        middleTime.setTimeInMillis(centerMillis);

        TimeDurationMetrics durationMetrics = new TimeDurationMetrics(getType());
        double userValue = new Long(middleTime.getTimeInMillis()).doubleValue();
        // Window2D.Time timingWindow = (Window2D.Time) getRenderContext().getWindow2D();
        Window2D.Time timingWindow = getTimingWindow();
        double deviceValue = timingWindow.timeToPixel(middleTime.getTime());
        double max = timingWindow.getTimeDurationPixel();

        if (deviceValue < 0 || deviceValue > max)
            return null;

        durationMetrics.setDeviceValue(deviceValue);
        durationMetrics.setUserValue(userValue);
        durationMetrics.setMetricsMarkerColor(getMetricsMarkerColor());
        durationMetrics.setMetricsLabelColor(getMetricsLabelColor());
        durationMetrics.setLockLabel(isLockLabel());
        durationMetrics.setLockMarker(isLockMarker());

        durationMetrics.setMetricsStart(pointStart);
        durationMetrics.setMetricsEnd(pointEnd);
        durationMetrics.setTimeStart(startTime.getTime());
        durationMetrics.setTimeEnd(endTime.getTime());

        if (model != null) {
            if (model.getMetricsLabelColor() != null) {
                durationMetrics.setMetricsLabelColor(model.getMetricsLabelColor());
            }
            if (model.getMetricsMarkerColor() != null) {
                durationMetrics.setMetricsMarkerColor(model.getMetricsMarkerColor());
            }
        }

        return durationMetrics;
    }

    /**
     * format time points
     * 
     * @param points
     *            the time points to format
     * @param formater
     *            the date formatter
     * @return formated metrics
     */
    public List<Metrics> formatMetricsPoint(List<TimePointMetrics> points, SimpleDateFormat formater) {
        List<Metrics> metrics = new ArrayList<Metrics>();
        for (TimePointMetrics point : points) {
            point.setMetricsLabel(formater.format(point.getTime()));
            metrics.add(point);
        }
        return metrics;
    }

    /**
     * generate seconds from reference for the given duration and seconds increment
     * 
     * @param ref
     * @param duration
     * @param secondIncrement
     * @return seconds metrics
     */
    protected List<TimePointMetrics> generateSecondsPoint(Calendar ref, long duration, int secondIncrement,
            TimeModel model) {
        List<TimePointMetrics> seconds = new ArrayList<TimePointMetrics>();
        for (int i = 0; i <= new Long(duration).intValue() + 1; i = i + secondIncrement) {
            Calendar c = (Calendar) ref.clone();
            c.set(ref.get(Calendar.YEAR), ref.get(Calendar.MONTH), ref.get(Calendar.DATE),
                  ref.get(Calendar.HOUR_OF_DAY), ref.get(Calendar.MINUTE), ref.get(Calendar.SECOND) + i);

            TimePointMetrics m = generateMetricsPoint(c, model);
            if (m != null) {
                seconds.add(m);
            }
        }
        return seconds;
    }

    /**
     * generate minutes from reference for the given duration and minute increment
     * 
     * @param ref
     * @param durationMinutes
     * @param minuteIncrement
     * @return minutes metrics
     */
    protected List<TimePointMetrics> generateMinutesPoint(Calendar ref, long durationMinutes, int minuteIncrement,
            TimeModel model) {
        List<TimePointMetrics> minutes = new ArrayList<TimePointMetrics>();
        int count = new Long(durationMinutes + 60).intValue();
        for (int i = 0; i <= count; i = i + minuteIncrement) {
            Calendar c = (Calendar) ref.clone();
            c.set(ref.get(Calendar.YEAR), ref.get(Calendar.MONTH), ref.get(Calendar.DATE),
                  ref.get(Calendar.HOUR_OF_DAY), ref.get(Calendar.MINUTE) + i, 0);
            TimePointMetrics m = generateMetricsPoint(c, model);
            if (m != null) {
                minutes.add(m);
            }
        }
        return minutes;
    }

    /**
     * generate hours from reference for the given duration and hours increment
     * 
     * @param ref
     * @param durationHours
     * @param minuteIncrement
     * @return hours metrics
     */
    protected List<TimePointMetrics> generateHoursPoint(Calendar ref, long durationHours, int hoursIncrement,
            TimeModel model) {
        List<TimePointMetrics> hours = new ArrayList<TimePointMetrics>();
        for (int i = 0; i <= new Long(durationHours + 24).intValue() + 1; i = i + hoursIncrement) {
            Calendar c = (Calendar) ref.clone();
            c.set(ref.get(Calendar.YEAR), ref.get(Calendar.MONTH), ref.get(Calendar.DATE),
                  ref.get(Calendar.HOUR_OF_DAY) + i, ref.get(Calendar.MINUTE), 0);
            TimePointMetrics m = generateMetricsPoint(c, model);
            if (m != null) {
                hours.add(m);
            }
        }
        return hours;
    }

    /**
     * generate days from reference for the given duration and days increment
     * 
     * @param ref
     * @param durationDays
     * @param daysIncrement
     * @return days metrics
     */
    protected List<TimePointMetrics> generateDaysPoint(Calendar ref, long durationDays, int daysIncrement,
            TimeModel model) {
        List<TimePointMetrics> days = new ArrayList<TimePointMetrics>();
        for (int i = 0; i <= new Long(durationDays + 31).intValue() + 1; i = i + daysIncrement) {
            Calendar c = (Calendar) ref.clone();
            c.set(ref.get(Calendar.YEAR), ref.get(Calendar.MONTH), ref.get(Calendar.DATE) + i,
                  ref.get(Calendar.HOUR_OF_DAY), 0, 0);
            TimePointMetrics m = generateMetricsPoint(c, model);
            if (m != null) {
                days.add(m);
            }
        }
        return days;
    }

    /**
     * generate months from reference for the given duration and months increment
     * 
     * @param ref
     * @param durationMonth
     * @param monthsIncrement
     * @return months metrics
     */
    protected List<TimePointMetrics> generateMonthsPoint(Calendar ref, long durationMonth, int monthsIncrement,
            TimeModel model) {
        List<TimePointMetrics> days = new ArrayList<TimePointMetrics>();
        for (int i = 0; i <= new Long(durationMonth + 12).intValue() + 1; i = i + monthsIncrement) {
            Calendar c = (Calendar) ref.clone();
            c.set(ref.get(Calendar.YEAR), ref.get(Calendar.MONTH), ref.get(Calendar.DATE),
                  ref.get(Calendar.HOUR_OF_DAY), ref.get(Calendar.MINUTE), ref.get(Calendar.SECOND));
            c.add(Calendar.MONTH, i);
            TimePointMetrics m = generateMetricsPoint(c, model);
            if (m != null) {
                days.add(m);
            }
        }
        return days;
    }

    /**
     * <code>Minute1Model</code> takes the responsibility to manage minute label
     * 
     * @author sebastien janaud
     */
    public static class Minute1Model extends TimeModel {

        /**
         * create basic 1-minute label timing model
         */
        public Minute1Model() {
            super(MINUTE_MILLIS, 40, "minute familly", "one minute", "minute", 18);
        }

        /**
         * create model with 1-minute label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public Minute1Model(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with 1-minute label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public Minute1Model(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

        
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new Minute1Model();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), 0);
            List<TimePointMetrics> points = getTimingManager().generateMinutesPoint(ref, time.durationMinutes(), 1,
                                                                                    this);

            return getTimingManager().formatMetricsPoint(points, minuteFormat);
        }
    }

    /**
     * <code>Minute10Model</code> takes the responsibility to manage 10-minutes segment label
     * 
     * @author sebastien janaud
     */
    public static class Minute10Model extends TimeModel {

        /**
         * create basic 10-minutes timing model
         */
        public Minute10Model() {
            super(MINUTE_MILLIS * 10, 40, "minute familly", "ten minutes", "minute", 18);
        }

        /**
         * create model with 10-minutes label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public Minute10Model(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with 10-minutes label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public Minute10Model(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new Minute10Model();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

        
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    cal.get(Calendar.HOUR_OF_DAY), 0, 0);
            List<TimePointMetrics> minutesPoints = getTimingManager().generateMinutesPoint(ref, time.durationMinutes(),
                                                                                           10, this);
            return getTimingManager().formatMetricsPoint(minutesPoints, minuteFormat);
        }
    }

    /**
     * <code>Minute15Model</code> takes the responsibility to manage 15-minutes segment label
     * 
     * @author sebastien janaud
     */
    public static class Minute15Model extends TimeModel {

        /**
         * create basic 15-minutes timing model
         */
        public Minute15Model() {
            super(MINUTE_MILLIS * 15, 40, "minute familly", "fifteen minutes", "minute", 18);
        }

        /**
         * create model with 15-minutes label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public Minute15Model(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with 15-minutes label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public Minute15Model(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

        
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new Minute15Model();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

      
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    cal.get(Calendar.HOUR_OF_DAY), 0, 0);
            List<TimePointMetrics> minutesPoints = getTimingManager().generateMinutesPoint(ref, time.durationMinutes(),
                                                                                           15, this);
            return getTimingManager().formatMetricsPoint(minutesPoints, minuteFormat);
        }

    }

    /**
     * <code>Minute20Model</code> takes the responsibility to manage 20-minutes segment label
     * 
     * @author sebastien janaud
     */
    public static class Minute20Model extends TimeModel {

        /**
         * create 20-minutes timing model
         */
        public Minute20Model() {
            super(MINUTE_MILLIS * 20, 40, "minute familly", "twenty minutes", "minute", 18);
        }

        /**
         * create model with 20-minutes label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public Minute20Model(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with 20-minutes label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public Minute20Model(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new Minute20Model();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    cal.get(Calendar.HOUR_OF_DAY), 0, 0);
            List<TimePointMetrics> minutesPoints = getTimingManager().generateMinutesPoint(ref, time.durationMinutes(),
                                                                                           20, this);
            return getTimingManager().formatMetricsPoint(minutesPoints, minuteFormat);
        }

    }

    /**
     * <code>HourModel</code> takes the responsibility to manage hour number label
     * 
     * @author sebastien janaud
     */
    public static class HourModel extends TimeModel {

        /**
         * create basic hour model
         */
        public HourModel() {
            super(HOUR_MILLIS, 40, "hour familly", "one hour", "hour", 18);
        }

        /**
         * create model with hour label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public HourModel(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with hour label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public HourModel(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

      
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new HourModel();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

     
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    cal.get(Calendar.HOUR_OF_DAY), 0, 0);
            List<TimePointMetrics> hoursPoints = getTimingManager().generateHoursPoint(ref, time.durationHours(), 1,
                                                                                       this);
            return getTimingManager().formatMetricsPoint(hoursPoints, hourFormat);
        }

    }

    /**
     * <code>Hour3Model</code> takes the responsibility to manage 3-hours segment label
     * 
     * @author sebastien janaud
     */
    public static class Hour3Model extends TimeModel {

        /**
         * create basic 3-hours segment timing model
         */
        public Hour3Model() {
            super(HOUR_MILLIS * 3, 40, "hour familly", "three hours", "hour", 18);
        }

        /**
         * create model with 3-hours label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public Hour3Model(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with 3-hours label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public Hour3Model(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new Hour3Model();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    0, 0, 0);
            List<TimePointMetrics> hoursPoints = getTimingManager().generateHoursPoint(ref, time.durationHours(), 3,
                                                                                       this);
            return getTimingManager().formatMetricsPoint(hoursPoints, hourFormat);
        }

    }

    /**
     * <code>DayNumberModel</code> takes the responsibility to manage day number label
     * 
     * @author sebastien janaud
     */
    public static class DayNumberModel extends TimeModel {

        /**
         * create basic day number model
         */
        public DayNumberModel() {
            super(DAY_MILLIS, 40, "day familly", "one day", "day - 24 hours", 18);
        }

        /**
         * create model with day number label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public DayNumberModel(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with day number label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public DayNumberModel(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new DayNumberModel();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

      
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    0, 0, 0);
            List<TimePointMetrics> daysPoints = getTimingManager().generateDaysPoint(ref, time.durationDays(), 1, this);
            return getTimingManager().formatMetricsPoint(daysPoints, dayNumberFormat);
        }

    }

    /**
     * <code>DayShortTextModel</code> takes the responsibility to manage day with short text label
     * 
     * @author sebastien janaud
     */
    public static class DayShortTextModel extends TimeModel {

        /**
         * create basic short text timing model
         */
        public DayShortTextModel() {
            super(DAY_MILLIS, 80, "day familly", "one day with text label", "day - 24 hours", 18);
        }

        /**
         * create model with short text label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public DayShortTextModel(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with short text label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public DayShortTextModel(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new DayShortTextModel();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    12, 0, 0);
            List<TimePointMetrics> daysPoints = getTimingManager().generateDaysPoint(ref, time.durationDays(), 1, this);
            return getTimingManager().formatMetricsPoint(daysPoints, dayLabelFormat);
        }

    }

    /**
     * <code>DayLongTextModel</code> takes the responsibility to manage day with long text label
     * 
     * @author sebastien janaud
     */
    public static class DayLongTextModel extends TimeModel {

        /**
         * create model with long text label
         */
        public DayLongTextModel() {
            super(DAY_MILLIS, 160, "day familly", "one day with  text day/month label", "day - 24 hours", 18);
        }

        /**
         * create model with long text label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public DayLongTextModel(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with long text label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public DayLongTextModel(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

      
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new DayLongTextModel();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    12, 0, 0);
            List<TimePointMetrics> daysPoints = getTimingManager().generateDaysPoint(ref, time.durationDays(), 1, this);
            return getTimingManager().formatMetricsPoint(daysPoints, dayLongLabelFormat);
        }
    }

    /**
     * <code>WeekModel</code> takes the responsibility to manage week label
     * 
     * @author sebastien janaud
     */
    public static class WeekModel extends TimeModel {

        /**
         * create basic week timing model
         */
        public WeekModel() {
            super(DAY_MILLIS * 7, 40, "week familly", "one week", "week - 7 days", 18);
        }

        /**
         * create model with week label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public WeekModel(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with week label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public WeekModel(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

        
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new WeekModel();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

        
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    12, 0, 0);
            ref.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            List<TimePointMetrics> daysPoints = getTimingManager().generateDaysPoint(ref, time.durationDays(), 7, this);
            return getTimingManager().formatMetricsPoint(daysPoints, weekFormat);
        }

    }

    /**
     * <code>WeekDurationDurationModel</code> takes the responsibility to manage week duration segment label
     * 
     * @author sebastien janaud
     */
    public static class WeekDurationDurationModel extends TimeModel {

        /**
         * create basic week duration timing model
         */
        public WeekDurationDurationModel() {
            super(DAY_MILLIS * 7, 80, "week familly", "one week duration", "week - 7 days", 18);
        }

        /**
         * create model with week duration segment label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public WeekDurationDurationModel(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with week duration segment label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public WeekDurationDurationModel(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

        
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new WeekDurationDurationModel();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

        
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                    0, 0, 0);
            ref.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            List<TimeDurationMetrics> weeksDuration = new ArrayList<TimeDurationMetrics>();
            List<TimePointMetrics> mondayPoints = getTimingManager().generateDaysPoint(ref, time.durationDays(), 7,
                                                                                       this);
            for (TimePointMetrics point : mondayPoints) {
                Calendar mondayCal = Calendar.getInstance();
                mondayCal.setTime(point.getTime());

                Calendar sundayCal = (Calendar) mondayCal.clone();
                sundayCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

                TimeDurationMetrics duration = getTimingManager().generateMetricsDuration(mondayCal, sundayCal, this);
                if (duration != null) {
                    weeksDuration.add(duration);
                }
            }
            return formatWeekDuration(weeksDuration);
        }

        /**
         * format month duration
         * 
         * @param durations
         *            the time points to format
         * @param formater
         *            the date formatter
         * @return formated metrics
         */
        public List<Metrics> formatWeekDuration(List<TimeDurationMetrics> durations) {
            List<Metrics> metrics = new ArrayList<Metrics>();
            for (TimeDurationMetrics duration : durations) {
                duration.setMetricsLabel(monthShortFormat.format(duration.getTimeCenter())
                        + "(" + dayNumberFormat.format(duration.getTimeStart()) + " - "
                        + dayNumberFormat.format(duration.getTimeEnd()) + ")"
                        );
                metrics.add(duration);
            }
            return metrics;
        }

    }

    /**
     * <code>MonthModel</code> takes the responsibility to manage month label
     * 
     * @author sebastien janaud
     */
    public static class MonthModel extends TimeModel {

        /**
         * create basic month label timing model
         */
        public MonthModel() {
            super(MONTH_MILLIS, 80, "month familly", "one month", "month", 18);
        }

        /**
         * create model with month label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public MonthModel(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with month label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public MonthModel(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

       
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new MonthModel();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

        
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 0, 0, 0, 0);
            List<TimePointMetrics> monthsPoints = getTimingManager().generateMonthsPoint(ref, time.durationMonth(), 1,
                                                                                         this);
            return getTimingManager().formatMetricsPoint(monthsPoints, monthFormat);
        }

    }

    /**
     * <code>MonthDurationModel</code> takes the responsibility to manage month duration segment label
     * 
     * @author sebastien janaud
     */
    public static class MonthDurationModel extends TimeModel {

        /**
         * create basic month duration timing model
         */
        public MonthDurationModel() {
            super(MONTH_MILLIS, 80, "month familly", "one month duration", "month", 18);
        }

        /**
         * create model with month duration segment label with the given theme color
         * 
         * @param themeColor
         *            the theme color to set for marker and label
         */
        public MonthDurationModel(Color themeColor) {
            this(themeColor, themeColor);
        }

        /**
         * create model with month duration segment label with the given theme color
         * 
         * @param markerColor
         *            the theme color to set for marker
         * @param labelColor
         *            the theme color to set for text
         */
        public MonthDurationModel(Color markerColor, Color labelColor) {
            this();
            setMetricsLabelColor(labelColor);
            setMetricsMarkerColor(markerColor);
        }

        
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#cloneModel()
         */
        @Override
        public TimeModel cloneModel() {
            TimeModel clone = new MonthDurationModel();
            clone.setMinimal(isMinimal());
            clone.setMetricsMarkerColor(getMetricsMarkerColor());
            clone.setMetricsLabelColor(getMetricsLabelColor());
            return clone;
        }

        
        /* (non-Javadoc)
         * @see com.jensoft.core.plugin.metrics.manager.TimeMetricsManager.TimeModel#generateMetrics()
         */
        @Override
        public List<Metrics> generateMetrics() {
            Calendar cal = Calendar.getInstance();
            Window2D.Time time = getTimingManager().getTimingWindow();
            cal.setTime(time.getMinDate());
            Calendar ref = (Calendar) cal.clone();
            ref.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 0, 0, 0, 0);
            ref.set(Calendar.DAY_OF_MONTH, 1);

            List<TimeDurationMetrics> monthsDuration = new ArrayList<TimeDurationMetrics>();

            List<TimePointMetrics> monthsPoints = getTimingManager().generateMonthsPoint(ref, time.durationMonth(), 1,
                                                                                         this);

            for (TimePointMetrics point : monthsPoints) {

                Calendar firstDayInMonth = Calendar.getInstance();
                firstDayInMonth.setTime(point.getTime());

                int maxDay = firstDayInMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
                Calendar lastDayInMonth = (Calendar) firstDayInMonth.clone();
                lastDayInMonth.set(Calendar.DAY_OF_MONTH, maxDay);

                TimeDurationMetrics duration = getTimingManager().generateMetricsDuration(firstDayInMonth,
                                                                                          lastDayInMonth, this);
                if (duration != null) {
                    monthsDuration.add(duration);
                }
            }
            return formatMonthDuration(monthsDuration);
        }

        /**
         * format month duration
         * 
         * @param points
         *            the time points to format
         * @param formater
         *            the date formatter
         * @return formated metrics
         */
        public List<Metrics> formatMonthDuration(List<TimeDurationMetrics> points) {
            List<Metrics> metrics = new ArrayList<Metrics>();
            for (TimeDurationMetrics point : points) {

                point.setMetricsLabel(monthFormat.format(point.getTimeCenter()) + " - "
                        + yearFormat.format(point.getTimeCenter()));

                metrics.add(point);
            }
            return metrics;
        }

    }

    /**
     * <code>TimingMetrics</code> is an abstract definition of a timing metrics
     * 
     * @author sebastien janaud
     */
    public static abstract class TimingMetrics extends Metrics {

        public TimingMetrics() {
            super();
        }

        public TimingMetrics(MetricsType type) {
            super(type);
        }
    }

    /**
     * <code>TimePointMetrics</code> defines time metrics for point
     * 
     * @author sebastien janaud
     */
    public class TimePointMetrics extends TimingMetrics {

        /** time */
        private Date time;

        /**
         * create untyped metric point
         */
        public TimePointMetrics() {
            super();
        }

        /**
         * create time point
         * 
         * @param type
         */
        public TimePointMetrics(MetricsType type) {
            super(type);
        }

        /**
         * @return the time
         */
        public Date getTime() {
            return time;
        }

        /**
         * @param time
         *            the time to set
         */
        public void setTime(Date time) {
            this.time = time;
        }

    }

    /**
     * <code>TimeDurationMetrics</code> defines time metrics for duration
     * 
     * @author sebastien janaud
     */
    public class TimeDurationMetrics extends TimingMetrics {

        private Date timeStart;
        private Date timeEnd;
        private Metrics metricsStart;
        private Metrics metricsEnd;

        /**
         * create untyped metric duration
         */
        public TimeDurationMetrics() {
            super();
        }

        /**
         * create time duration metric
         * 
         * @param type
         */
        public TimeDurationMetrics(MetricsType type) {
            super(type);
        }

        /**
         * @return the metricsStart
         */
        public Metrics getMetricsStart() {
            return metricsStart;
        }

        /**
         * @param metricsStart
         *            the metricsStart to set
         */
        public void setMetricsStart(Metrics metricsStart) {
            this.metricsStart = metricsStart;
        }

        /**
         * @return the metricsEnd
         */
        public Metrics getMetricsEnd() {
            return metricsEnd;
        }

        /**
         * @param metricsEnd
         *            the metricsEnd to set
         */
        public void setMetricsEnd(Metrics metricsEnd) {
            this.metricsEnd = metricsEnd;
        }

        /**
         * @return the timeStart
         */
        public Date getTimeCenter() {
            long diff = timeEnd.getTime() - timeStart.getTime();
            long centerTime = timeStart.getTime() + diff / 2L;
            return new Date(centerTime);
        }

        /**
         * @return the timeStart
         */
        public Date getTimeStart() {
            return timeStart;
        }

        /**
         * @param timeStart
         *            the timeStart to set
         */
        public void setTimeStart(Date timeStart) {
            this.timeStart = timeStart;
        }

        /**
         * @return the timeEnd
         */
        public Date getTimeEnd() {
            return timeEnd;
        }

        /**
         * @param timeEnd
         *            the timeEnd to set
         */
        public void setTimeEnd(Date timeEnd) {
            this.timeEnd = timeEnd;
        }

    }

}
