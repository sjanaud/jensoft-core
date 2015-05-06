/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.sharedicon;

import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.ImageIcon;

import org.jensoft.core.sharedicon.common.Common;
import org.jensoft.core.sharedicon.marker.Marker;
import org.jensoft.core.sharedicon.media.Media;
import org.jensoft.core.sharedicon.refresh.Refresh;
import org.jensoft.core.sharedicon.weather.Weather;

/**
 * <code>SharedIcon</code>
 * 
 * @author Sebastien Janaud
 */
public class SharedIcon {

    /** weak cache reference */
    private Map<String, ImageIcon> cache;

    /** shared icon singleton instance */
    private static SharedIcon instance;

    /**
     * @return the cache
     */
    public Map<String, ImageIcon> getCache() {
        return cache;
    }

    /**
     * create sharde icon
     */
    private SharedIcon() {
        cache = new WeakHashMap<String, ImageIcon>();
    }

    /**
     * cache icon or null if icon has never been cached
     * 
     * @param nameSpace
     *            the specified name space
     * @param iconName
     *            the icon name
     * @return cache icon or null if icon has never been cached
     */
    public static ImageIcon getCachedIcon(String nameSpace, String iconName) {
        String key = nameSpace + "/" + iconName;
        Object cacheObject = getInstance().getCache().get(key);
        if (cacheObject != null) {
            return (ImageIcon) cacheObject;
        }
        return null;
    }

    /**
     * cache icon and shared it across plate-form
     * 
     * @param nameSpace
     *            icon name space
     * @param iconName
     *            icon name
     * @param icon
     *            icon to cache
     */
    public static void cacheIcon(String nameSpace, String iconName,
            ImageIcon icon) {
        String key = nameSpace + "/" + iconName;
        getInstance().getCache().put(key, icon);
    }

    /**
     * get shared icon instance
     * 
     * @return shared icon instance
     */
    public static SharedIcon getInstance() {
        if (instance == null) {
            instance = new SharedIcon();
        }
        return instance;
    }

    /**
     * get marker icon
     * 
     * @param marker
     *            the marker icon name
     * @return marker image icon
     */
    public static ImageIcon getMarker(String marker) {
        ImageIcon icon = getCachedIcon(Marker.class.getName(), marker);
        if (icon == null) {
            java.net.URL imgURL = Marker.class.getResource(marker);
            if (imgURL != null) {
                icon = new ImageIcon(imgURL);
                cacheIcon(Marker.class.getName(), marker, icon);
            }
        }
        return icon;
    }

    /**
     * get weather icon
     * 
     * @param weather
     *            the weather icon name
     * @return weather image icon
     */
    public static ImageIcon getWeather(String weather) {
        ImageIcon icon = getCachedIcon(Weather.class.getName(), weather);
        if (icon == null) {
            java.net.URL imgURL = Weather.class.getResource(weather);
            if (imgURL != null) {
                icon = new ImageIcon(imgURL);
                cacheIcon(Weather.class.getName(), weather, icon);
            }
        }
        return icon;
    }

    /**
     * get media icon
     * 
     * @param media
     *            the media icon name
     * @return media image icon
     */
    public static ImageIcon getMedia(String media) {
        ImageIcon icon = getCachedIcon(Media.class.getName(), media);
        if (icon == null) {
            java.net.URL imgURL = Media.class.getResource(media);
            if (imgURL != null) {
                icon = new ImageIcon(imgURL);
                cacheIcon(Media.class.getName(), media, icon);
            }
        }
        return icon;
    }

    /**
     * get refresh icon
     * 
     * @param refresh
     *            the refresh icon name
     * @return refresh image icon
     */
    public static ImageIcon getRefresh(String refresh) {
        ImageIcon icon = getCachedIcon(Refresh.class.getName(), refresh);
        if (icon == null) {
            java.net.URL imgURL = Refresh.class.getResource(refresh);
            if (imgURL != null) {
                icon = new ImageIcon(imgURL);
                cacheIcon(Refresh.class.getName(), refresh, icon);
            }
        }
        return icon;
    }

    /**
     * get shared icon
     * 
     * @param common
     *            icon
     * @return icon
     */
    public static ImageIcon getCommon(String common) {
        ImageIcon icon = getCachedIcon(Common.class.getName(), common);
        if (icon == null) {
            java.net.URL imgURL = Common.class.getResource(common);
            if (imgURL != null) {
                icon = new ImageIcon(imgURL);
                cacheIcon(Common.class.getName(), common, icon);
            }
        }
        return icon;
    }

}
