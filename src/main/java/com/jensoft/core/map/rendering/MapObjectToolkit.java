/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.rendering;

import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.layer.highway.Highway;
import com.jensoft.core.map.layer.highway.HighwayNature;
import com.jensoft.core.map.layer.landuse.Landuse;
import com.jensoft.core.map.layer.landuse.LanduseNature;
import com.jensoft.core.map.layer.leisure.Leisure;
import com.jensoft.core.map.layer.leisure.LeisureNature;
import com.jensoft.core.map.layer.manmade.ManMade;
import com.jensoft.core.map.layer.manmade.ManMadeNature;
import com.jensoft.core.map.layer.natural.Natural;
import com.jensoft.core.map.layer.natural.NaturalNature;
import com.jensoft.core.map.layer.railway.Railway;
import com.jensoft.core.map.layer.railway.RailwayNature;
import com.jensoft.core.map.layer.railway.tramway.Tram;
import com.jensoft.core.map.layer.waterway.Waterway;
import com.jensoft.core.map.layer.waterway.WaterwayNature;
import com.jensoft.core.map.primitive.Node;
import com.jensoft.core.map.primitive.Primitive;
import com.jensoft.core.map.primitive.Stream;
import com.jensoft.core.map.primitive.Way;

public class MapObjectToolkit {

    public static List<Highway> createHighways(Stream stream) {

        List<Way> highwaysWays = stream.getWays(HighwayNature.NATURE);

        List<Highway> highways = new ArrayList<Highway>();
        for (Way w : highwaysWays) {

            String nature = w.getTag(HighwayNature.NATURE).getValue();
            Highway highway = new Highway(w.getId(), nature);

            Primitive primitive = new Primitive(w);

            highways.add(highway);
            if (w.getTag("name") != null) {
                highway.setName(w.getTag("name").getValue());
            }

            highway.setPrimitive(primitive);

        }
        return highways;
    }

    public static List<Leisure> createLeisures(Stream stream) {

        List<Way> leisureWays = stream.getWays(LeisureNature.NATURE);

        List<Leisure> leisures = new ArrayList<Leisure>();
        for (Way w : leisureWays) {

            String nature = w.getTag(LeisureNature.NATURE).getValue();
            Leisure leisure = new Leisure(w.getId(), nature);

            leisures.add(leisure);
            if (w.getTag("name") != null) {
                leisure.setName(w.getTag("name").getValue());
            }

            Primitive primitive = new Primitive(w);
            leisure.setPrimitive(primitive);

        }
        return leisures;
    }

    public static List<Landuse> createLanduses(Stream stream) {

        List<Way> landuseWays = stream.getWays(LanduseNature.NATURE);

        List<Landuse> landuses = new ArrayList<Landuse>();
        for (Way w : landuseWays) {

            String nature = w.getTag(LanduseNature.NATURE).getValue();
            Landuse landuse = new Landuse(w.getId(), nature);

            landuses.add(landuse);
            if (w.getTag("name") != null) {
                landuse.setName(w.getTag("name").getValue());
            }

            Primitive primitive = new Primitive(w);
            landuse.setPrimitive(primitive);

        }
        return landuses;
    }

    public static List<Waterway> createWaterways(Stream stream) {

        List<Way> waterwaysWays = stream.getWays(WaterwayNature.NATURE);

        List<Waterway> waterways = new ArrayList<Waterway>();
        for (Way w : waterwaysWays) {

            String nature = w.getTag(WaterwayNature.NATURE).getValue();
            Waterway ww = new Waterway(w.getId(), nature);

            waterways.add(ww);
            if (w.getTag("name") != null) {
                ww.setName(w.getTag("name").getValue());
            }

            Primitive primitive = new Primitive(w);
            ww.setPrimitive(primitive);

        }
        return waterways;
    }

    public static List<Railway> createRailways(Stream stream) {

        List<Way> railwaysWays = stream.getWays(RailwayNature.NATURE);

        List<Railway> railways = new ArrayList<Railway>();
        for (Way w : railwaysWays) {

            String nature = w.getTag(RailwayNature.NATURE).getValue();

            if (nature.equals(RailwayNature.TRAM)) {
                Tram tram = new Tram(w.getId(), nature);

                railways.add(tram);
                if (w.getTag("name") != null) {
                    tram.setName(w.getTag("name").getValue());
                }

                Primitive primitive = new Primitive(w);
                tram.setPrimitive(primitive);

                for (Node n : w.getNodes()) {

                    if (n.getTag("railway") != null
                            && n.getTag("railway").getValue().equals("halt")) {
                        tram.addStop(n, n.getTag("name").getValue());
                    }

                }
            }
        }
        return railways;
    }

    public static List<Natural> createNatural(Stream stream) {

        List<Way> naturalsWays = stream.getWays(NaturalNature.NATURE);

        List<Natural> naturals = new ArrayList<Natural>();
        for (Way w : naturalsWays) {

            String nature = w.getTag(NaturalNature.NATURE).getValue();
            Natural natural = new Natural(w.getId(), nature);

            naturals.add(natural);
            if (w.getTag("name") != null) {
                natural.setName(w.getTag("name").getValue());
            }

            Primitive primitive = new Primitive(w);
            natural.setPrimitive(primitive);

        }
        return naturals;
    }

    public static List<ManMade> createManMade(Stream stream) {

        List<Way> manmadeWays = stream.getWays(ManMadeNature.BUILDING_NATURE);

        List<ManMade> manmades = new ArrayList<ManMade>();
        for (Way w : manmadeWays) {

            String nature = w.getTag(ManMadeNature.BUILDING_NATURE).getValue();
            ManMade manmade = new ManMade(w.getId(), nature);

            manmades.add(manmade);
            if (w.getTag("name") != null) {
                manmade.setName(w.getTag("name").getValue());
            }

            Primitive primitive = new Primitive(w);
            manmade.setPrimitive(primitive);

        }
        return manmades;
    }

}
