/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.railway.tramway;

import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.layer.railway.Railway;
import com.jensoft.core.map.primitive.Node;

public class Tram extends Railway {

    public Tram(int id, String nature) {
        super(id, nature);
    }

    private List<TramStop> tramStops = new ArrayList<TramStop>();

    public void addStop(Node node, String name) {
        tramStops.add(new TramStop(name, node));
    }

    public List<TramStop> getTramStops() {
        return tramStops;
    }

    public TramStop getTramStops(int index) {
        return tramStops.get(index);
    }

    public int countStop() {
        return tramStops.size();
    }

}
