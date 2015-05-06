/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.restbridge;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jensoft.core.map.layer.background.BackgroundLayer;
import org.jensoft.core.map.layer.highway.Highway;
import org.jensoft.core.map.layer.highway.HighwayLayer;
import org.jensoft.core.map.layer.highway.HighwayNature;
import org.jensoft.core.map.layer.landuse.Landuse;
import org.jensoft.core.map.layer.landuse.LanduseLayer;
import org.jensoft.core.map.layer.landuse.LanduseNature;
import org.jensoft.core.map.layer.leisure.Leisure;
import org.jensoft.core.map.layer.leisure.LeisureLayer;
import org.jensoft.core.map.layer.leisure.LeisureNature;
import org.jensoft.core.map.layer.manmade.ManMade;
import org.jensoft.core.map.layer.manmade.ManMadeLayer;
import org.jensoft.core.map.layer.manmade.ManMadeNature;
import org.jensoft.core.map.layer.natural.Natural;
import org.jensoft.core.map.layer.natural.NaturalLayer;
import org.jensoft.core.map.layer.natural.NaturalNature;
import org.jensoft.core.map.layer.railway.Railway;
import org.jensoft.core.map.layer.railway.RailwayLayer;
import org.jensoft.core.map.layer.railway.RailwayNature;
import org.jensoft.core.map.layer.railway.tramway.Tram;
import org.jensoft.core.map.layer.waterway.Waterway;
import org.jensoft.core.map.layer.waterway.WaterwayLayer;
import org.jensoft.core.map.layer.waterway.WaterwayNature;
import org.jensoft.core.map.primitive.Node;
import org.jensoft.core.map.primitive.Primitive;
import org.jensoft.core.map.primitive.Stream;
import org.jensoft.core.map.primitive.Tag;
import org.jensoft.core.map.primitive.Way;
import org.jensoft.core.map.projection.DalleProjection;
import org.jensoft.core.map.projection.MapUtil;
import org.jensoft.core.map.projection.Projection2D;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class OSMRestBridgeEngine {

    /** document builder factory */
    private DocumentBuilderFactory factory;

    /** document builder */
    private DocumentBuilder templateBuilder;

    /** XML document */
    private Document document;

    public OSMRestBridgeEngine() {
        try {
            factory = DocumentBuilderFactory.newInstance();
            templateBuilder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void start() {

        DalleProjection d = new DalleProjection(17);

        // bordeaux
        // d.setStartX(65318);
        // d.setEndX(65334);
        // d.setStartY(47230);
        // d.setEndY(47236);

        int startX = 36335;
        int endX = 36350;
        int startY = 55805;
        int endY = 55820;

        // miami
        // d.setStartX(startX);
        // d.setEndX(endX);
        // d.setStartY(startY);
        // d.setEndY(endY);

        // Area area = new Area(65318,65334,47230,47236);
        double longWest = MapUtil.tile2long(startX, 17);
        double longEast = MapUtil.tile2long(endX + 1, 17);
        double latNorth = MapUtil.tile2lat(startY, 17);
        double latSouth = MapUtil.tile2lat(endY + 1, 17);

        // area.setLongWest(longWest);
        // area.setLongEast(longEast);
        // area.setLatNorth(latNorth);
        // area.setLatSouth(latSouth);
        // area.setName("test area");

        // gare
        // d.setStartX(65330);
        // d.setEndX(65334);
        // d.setStartY(47243);
        // d.setEndY(47247);

        BackgroundLayer backgroundLayer = new BackgroundLayer();
        // d.registerLayer(backgroundLayer);

        NaturalLayer naturalLayer = new NaturalLayer();
        d.registerLayer(naturalLayer);

        WaterwayLayer waterwayLayer = new WaterwayLayer();
        d.registerLayer(waterwayLayer);

        LeisureLayer leisureLayer = new LeisureLayer();
        d.registerLayer(leisureLayer);

        LanduseLayer landuseLayer = new LanduseLayer();
        d.registerLayer(landuseLayer);

        ManMadeLayer manMadeLayer = new ManMadeLayer();
        d.registerLayer(manMadeLayer);

        HighwayLayer highwayLayer = new HighwayLayer();
        // d.registerLayer(highwayLayer);

        RailwayLayer railwayLayer = new RailwayLayer();
        d.registerLayer(railwayLayer);

        // for (int x = startX; x <= endX; x++) {
        // for (int y = startY; y <= endY; y++) {
        //
        // Stream s = streamBounding(x, y);
        //
        // List<Way> leisureWays = s.getWays(LeisureNature.NATURE);
        // List<Leisure> leisures = makeLeisures(leisureWays, d);
        // leisureLayer.registerLeisures(leisures);
        //
        // List<Way> landuseWays = s.getWays(LanduseNature.NATURE);
        // List<Landuse> landuses = makeLanduses(landuseWays, d);
        // landuseLayer.registerLanduses(landuses);
        //
        // List<Way> highwaysWays = s.getWays(HighwayNature.NATURE);
        // List<Highway> highways = makeHighways(highwaysWays, d);
        // highwayLayer.registerHighways(highways);
        //
        // List<Way> manMadesWays = s.getWays(ManMadeNature.NATURE);
        // List<ManMade> manmades = makeManMades(manMadesWays, d);
        // manMadeLayer.registerManMades(manmades);
        //
        // List<Way> manMadesWays2 = s
        // .getWays(ManMadeNature.BUILDING_NATURE); // fuckin
        // // buildings
        // // !
        // List<ManMade> manmades2 = makeBuildingManMades(manMadesWays2, d);
        // manMadeLayer.registerManMades(manmades2);
        //
        // List<Way> waterwaysWays = s.getWays(WaterwayNature.NATURE);
        // List<Waterway> waterways = makeWaterways(waterwaysWays, d);
        // waterwayLayer.registerWaterway(waterways);
        //
        // List<Way> railwaysWays = s.getWays(RailwayNature.NATURE);
        // List<Railway> railways = makeRailways(railwaysWays, d);
        // railwayLayer.registerRailways(railways);
        //
        // List<Way> naturalsWays = s.getWays(NaturalNature.NATURE);
        // List<Natural> naturals = makeNatural(naturalsWays, d);
        // // naturalLayer.registerNatural(naturals);
        //
        // }
        // }

        // MPGis gis = new MPGis();
        // area.registerHighway(highwayLayer.getRegisteredHighways());
        // gis.insertArea(area);
        // d.render(startX,endX,startY,endY);

    }

    private List<ManMade> makeBuildingManMades(List<Way> ways, Projection2D proj) {

        List<ManMade> manMades = new ArrayList<ManMade>();
        for (Way w : ways) {

            String nature = w.getTag(ManMadeNature.BUILDING_NATURE).getValue();
            ManMade manMade = new ManMade(w.getId(), nature);

            Primitive primitive = new Primitive(w);
            manMade.setPrimitive(primitive);
            manMades.add(manMade);
            if (w.getTag("name") != null)
            {
                manMade.setName(w.getTag("name").getValue());
               
            }
        }
        return manMades;
    }

    private List<Waterway> makeWaterways(List<Way> ways, Projection2D proj) {

        List<Waterway> waterways = new ArrayList<Waterway>();
        for (Way w : ways) {

            String nature = w.getTag(WaterwayNature.NATURE).getValue();
            Waterway ww = new Waterway(w.getId(), nature);

            Primitive primitive = new Primitive(w);
            ww.setPrimitive(primitive);

            waterways.add(ww);
            if (w.getTag("name") != null) {
                ww.setName(w.getTag("name").getValue());
            }

            // for(Node n : w.getNodes()){
            //
            // GeoPosition nodePosition = new
            // GeoPosition(n.getLatitudeAsDouble(),n.getLongitudeAsDouble());
            // ww.addLimitPosition(nodePosition);
            // ww.addLimitPosition(proj.geoToPixel(nodePosition));
            //
            // }
        }
        return waterways;
    }

    private List<Railway> makeRailways(List<Way> ways, Projection2D proj) {

        List<Railway> railways = new ArrayList<Railway>();
        for (Way w : ways) {

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

    private List<Natural> makeNatural(List<Way> ways, Projection2D proj) {

        List<Natural> naturals = new ArrayList<Natural>();
        for (Way w : ways) {

            String nature = w.getTag(NaturalNature.NATURE).getValue();
            Natural natural = new Natural(w.getId(), nature);

            naturals.add(natural);
            if (w.getTag("name") != null) {
                natural.setName(w.getTag("name").getValue());
            }

            Primitive primitive = new Primitive(w);

            natural.setPrimitive(primitive);
            // for(Node n : w.getNodes()){
            //
            // GeoPosition nodePosition = new
            // GeoPosition(n.getLatitudeAsDouble(),n.getLongitudeAsDouble());
            // natural.addLimitPosition(nodePosition);
            // natural.addLimitPosition(proj.geoToPixel(nodePosition));
            //
            // }
        }
        return naturals;
    }

    private List<ManMade> makeManMades(List<Way> ways, Projection2D proj) {

        List<ManMade> manMades = new ArrayList<ManMade>();
        for (Way w : ways) {

            String nature = w.getTag(ManMadeNature.NATURE).getValue();
            ManMade manMade = new ManMade(w.getId(), nature);

            manMades.add(manMade);
            if (w.getTag("name") != null) {
                manMade.setName(w.getTag("name").getValue());
            }

            // for(Node n : w.getNodes()){
            //
            // GeoPosition nodePosition = new
            // GeoPosition(n.getLatitudeAsDouble(),n.getLongitudeAsDouble());
            // manMade.addLimitPosition(nodePosition);
            // manMade.addLimitPosition(proj.geoToPixel(nodePosition));
            //
            // }
        }
        return manMades;
    }

    private List<Leisure> makeLeisures(List<Way> ways, Projection2D proj) {

        List<Leisure> leisures = new ArrayList<Leisure>();
        for (Way w : ways) {

            String nature = w.getTag(LeisureNature.NATURE).getValue();
            Leisure leisure = new Leisure(w.getId(), nature);

            leisures.add(leisure);
            if (w.getTag("name") != null) {
                leisure.setName(w.getTag("name").getValue());
            }

            // for(Node n : w.getNodes()){
            //
            // GeoPosition nodePosition = new
            // GeoPosition(n.getLatitudeAsDouble(),n.getLongitudeAsDouble());
            // leisure.addLimitPosition(nodePosition);
            // leisure.addLimitPosition(proj.geoToPixel(nodePosition));
            //
            // }
        }
        return leisures;
    }

    private List<Landuse> makeLanduses(List<Way> ways, Projection2D proj) {

        List<Landuse> landuses = new ArrayList<Landuse>();
        for (Way w : ways) {

            String nature = w.getTag(LanduseNature.NATURE).getValue();
            Landuse landuse = new Landuse(w.getId(), nature);

            landuses.add(landuse);
            if (w.getTag("name") != null) {
                landuse.setName(w.getTag("name").getValue());
            }

            // for(Node n : w.getNodes()){
            //
            // GeoPosition nodePosition = new
            // GeoPosition(n.getLatitudeAsDouble(),n.getLongitudeAsDouble());
            // landuse.addLimitPosition(nodePosition);
            // landuse.addLimitPosition(proj.geoToPixel(nodePosition));
            //
            // }
        }
        return landuses;
    }

    private List<Highway> makeHighways(List<Way> ways, Projection2D proj) {

        List<Highway> highways = new ArrayList<Highway>();
        for (Way w : ways) {

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

    /**
     * trim specified children element value from specified parent
     * 
     * @param parent
     *            the parent element
     * @param childName
     *            the children name
     * @return children element string trim value
     */
    public String elementTextTrim(Element parent, String childName) {
        Element childElement = (Element) parent.getElementsByTagName(childName).item(0);
        if (childElement != null) {
            return childElement.getTextContent();
        }
        return null;
    }
    
    
    
    /**
     * stream a tile with the specified index
     * @param longitudeWest
     * @param longEast
     * @param latitudeNorth
     * @param latitudeSouth
     * @return stream
     */
     Stream streamBoundBox(double longitudeWest, double longEast,double latitudeNorth,double latitudeSouth) {
        Stream stream = new Stream();
        try {
           
            String bounding = "bbox=" + Double.toString(longitudeWest) + ","
                    + Double.toString(latitudeSouth) + ","
                    + Double.toString(longEast) + ","
                    + Double.toString(latitudeNorth);
            String url = "http://api.openstreetmap.org/api/0.6/map?" + bounding;
            URL load = new URL(url);
           

            document = templateBuilder.parse(load.openStream());
            
            Element root = document.getDocumentElement();

            NodeList wayList = root.getElementsByTagName("way");
            //System.out.println("way list size : " + wayList.getLength());
            
            for (int i = 0; i < wayList.getLength(); i++) {

                org.w3c.dom.Node wayNode = wayList.item(i);
                Way way = new Way();
                stream.addWay(way);
                
                NamedNodeMap wayAttributes = wayNode.getAttributes();

                for (int a = 0; a < wayAttributes.getLength(); a++) {
                    org.w3c.dom.Node theAttribute = wayAttributes.item(a);
                    //System.out.println(theAttribute.getNodeName() + "=" + theAttribute.getNodeValue());
                    String attName = theAttribute.getNodeName();
                    String attValue = theAttribute.getNodeValue();
                   

                    if (attName != null && attName.equals("id")) {
                        way.setId(Integer.parseInt(attValue));
                    }
                    if (attName != null && attName.equals("timestamp")) {
                        way.setTimestamp(attValue);
                    }
                    if (attName != null && attName.equals("user")) {
                        way.setUser(attValue);
                    }
                   
                }
                
               
                NodeList nodeWayList = wayNode.getChildNodes();
                for (int j = 0; j < nodeWayList.getLength(); j++) {
                    org.w3c.dom.Node wayChild = nodeWayList.item(j);
                    String nodeName = wayChild.getNodeName();
                    
                    if(nodeName.equals("nd")){                       
                        NamedNodeMap childAttributes = wayChild.getAttributes();                        
                        for (int a = 0; a < childAttributes.getLength(); a++) {
                            org.w3c.dom.Node theAttribute = childAttributes.item(a);
                            //System.out.println(theAttribute.getNodeName() + "=" + theAttribute.getNodeValue());
                            String attName = theAttribute.getNodeName();
                            String attValue = theAttribute.getNodeValue();
                            if (attName != null && attName.equals("ref")) {
                                way.addNodeRef(Integer.parseInt(attValue));
                            }
                        }
                    }
                    else if(nodeName.equals("tag")){
                        NamedNodeMap childAttributes = wayChild.getAttributes();
                        Tag t = new Tag();
                        for (int a = 0; a < childAttributes.getLength(); a++) {
                            org.w3c.dom.Node theAttribute = childAttributes.item(a);                            
                            String attName = theAttribute.getNodeName();
                            String attValue = theAttribute.getNodeValue();
                            if (attName != null && attName.equals("k")) {
                                t.setKey(attValue);
                            }
                            if (attName != null && attName.equals("v")) {
                                t.setValue(attValue);
                            }
                        }
                        way.addTag(t);
                    }                    
                }

            }

            NodeList nodeList = root.getElementsByTagName("node");
           
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = new Node();
                stream.addNode(node);
                org.w3c.dom.Node nodeNode = nodeList.item(i);
                
                NamedNodeMap nodeAttributes = nodeNode.getAttributes();

                for (int a = 0; a < nodeAttributes.getLength(); a++) {
                    org.w3c.dom.Node theAttribute = nodeAttributes.item(a);
                    
                    String attName = theAttribute.getNodeName();
                    String attValue = theAttribute.getNodeValue();
                    

                    if (attName != null && attName.equals("id")) {
                        node.setId(Integer.parseInt(attValue));
                    }
                    if (attName != null && attName.equals("lat")) {
                        node.setLat(attValue);
                    }
                    if (attName != null && attName.equals("lon")) {
                        node.setLon(attValue);
                    }
                    
                }
            }

            NodeList relationList = root.getElementsByTagName("relation");
            //System.out.println("relation list size : " + relationList.getLength());

            // Iterator it1 = root.elementIterator();
            // while (it1.hasNext()) {
            // Element elem = (Element) it1.next();
            // // System.out.println(elem.getName());
            // if (elem.getName().equals("way")) {
            // // System.out.println("found way :"+countWay);
            //
            // String wayID = elem.attribute("id").getText();
            // Way way = new Way(Integer.parseInt(wayID));
            //
            // if (elem.attribute("timestamp") != null) {
            // String wayTimesatmp = elem.attribute("timestamp")
            // .getText();
            // way.setTimestamp(wayTimesatmp);
            // }
            // if (elem.attribute("user") != null) {
            // String wayUser = elem.attribute("user").getText();
            // way.setUser(wayUser);
            // }
            // stream.addWay(way);
            // Iterator itWay = elem.elementIterator();
            // while (itWay.hasNext()) {
            // Element wayNode = (Element) itWay.next();
            // // System.out.println("node way :"+wayNode.getName());
            // if (wayNode.getName().equals("nd")) {
            // String nodeRef = wayNode.attribute("ref").getText();
            // way.addNodeRef(Integer.parseInt(nodeRef));
            // }
            // else if (wayNode.getName().equals("tag")) {
            // String tagKey = wayNode.attribute("k").getText();
            // String tagValue = wayNode.attribute("v").getText();
            // Tag t = new Tag(tagKey, tagValue);
            // way.addTag(t);
            //
            // }
            //
            // }
            //
            // }
            // if (elem.getName().equals("node")) {
            // // System.out.println("xapi node : ");
            // // <node id="255670438" lat="44.8335323" lon="-0.5949750"
            // // user="moore33" osmxapi:users="moore33"
            // // timestamp="2008-04-04T10:00:01Z">
            //
            // String nodeID = elem.attribute("id").getText();
            // String nodeLat = elem.attribute("lat").getText();
            // String nodeLon = elem.attribute("lon").getText();
            // Node node = new Node(Integer.parseInt(nodeID));
            // node.setLat(nodeLat);
            // node.setLon(nodeLon);
            // stream.addNode(node);
            // Iterator itNode = elem.elementIterator();
            // while (itNode.hasNext()) {
            // Element nodeNode = (Element) itNode.next();
            //
            // if (nodeNode.getName().equals("tag")) {
            // String tagKey = nodeNode.attribute("k").getText();
            // String tagValue = nodeNode.attribute("v").getText();
            // Tag t = new Tag(tagKey, tagValue);
            // node.addTag(t);
            //
            // }
            //
            // }
            //
            // }
            // if (elem.getName().equals("relation")) {
            // // System.out.println("relation node");
            // String relationID = elem.attribute("id").getText();
            //
            // Relation relation = new Relation(
            // Integer.parseInt(relationID));
            // stream.addRelation(relation);
            //
            // Iterator itWay = elem.elementIterator();
            // while (itWay.hasNext()) {
            // Element relationEntry = (Element) itWay.next();
            // // System.out.println("node way :"+wayNode.getName());
            // if (relationEntry.getName().equals("tag")) {
            // String tagKey = relationEntry.attribute("k")
            // .getText();
            // String tagValue = relationEntry.attribute("v")
            // .getText();
            // Tag t = new Tag(tagKey, tagValue);
            // relation.addTag(t);
            // }
            // if (relationEntry.getName().equals("member")) {
            // // <member type="way" ref="343" role="from" />
            //
            // String type = relationEntry.attribute("type")
            // .getText();
            // String ref = relationEntry.attribute("ref")
            // .getText();
            // String role = relationEntry.attribute("role")
            // .getText();
            // Member member = new Member(ref, role, type);
            // relation.addMember(member);
            // }
            //
            // }
            // }
            //
            // }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return stream;
    }

    /**
     * stream a tile with the specified index
     * 
     * @param x
     * @param y
     * @param zoom
     * @return the stream of this tile
     */
    public Stream streamTile(int x, int y, int zoom) {
        Stream stream = new Stream();
        try {
            double longLeft = MapUtil.tile2long(x, zoom);
            double longRight = MapUtil.tile2long(x + 1, zoom);
            double latNorth = MapUtil.tile2lat(y, zoom);
            double latSouth = MapUtil.tile2lat(y + 1, zoom);
            String bounding = "bbox=" + Double.toString(longLeft) + ","
                    + Double.toString(latSouth) + ","
                    + Double.toString(longRight) + ","
                    + Double.toString(latNorth);
            String url = "http://api.openstreetmap.org/api/0.6/map?" + bounding;
            URL load = new URL(url);
           

            document = templateBuilder.parse(load.openStream());
            System.out.println("READ TILE STREAM [" + x + ";" + y + "]" + url);
            Element root = document.getDocumentElement();

            NodeList wayList = root.getElementsByTagName("way");
            //System.out.println("way list size : " + wayList.getLength());
            
            for (int i = 0; i < wayList.getLength(); i++) {

                org.w3c.dom.Node wayNode = wayList.item(i);
                Way way = new Way();
                stream.addWay(way);
                
                NamedNodeMap wayAttributes = wayNode.getAttributes();

                for (int a = 0; a < wayAttributes.getLength(); a++) {
                    org.w3c.dom.Node theAttribute = wayAttributes.item(a);
                    //System.out.println(theAttribute.getNodeName() + "=" + theAttribute.getNodeValue());
                    String attName = theAttribute.getNodeName();
                    String attValue = theAttribute.getNodeValue();
                   

                    if (attName != null && attName.equals("id")) {
                        way.setId(Integer.parseInt(attValue));
                    }
                    if (attName != null && attName.equals("timestamp")) {
                        way.setTimestamp(attValue);
                    }
                    if (attName != null && attName.equals("user")) {
                        way.setUser(attValue);
                    }
                   
                }
                
               
                NodeList nodeWayList = wayNode.getChildNodes();
                for (int j = 0; j < nodeWayList.getLength(); j++) {
                    org.w3c.dom.Node wayChild = nodeWayList.item(j);
                    String nodeName = wayChild.getNodeName();
                    
                    if(nodeName.equals("nd")){                       
                        NamedNodeMap childAttributes = wayChild.getAttributes();                        
                        for (int a = 0; a < childAttributes.getLength(); a++) {
                            org.w3c.dom.Node theAttribute = childAttributes.item(a);
                            //System.out.println(theAttribute.getNodeName() + "=" + theAttribute.getNodeValue());
                            String attName = theAttribute.getNodeName();
                            String attValue = theAttribute.getNodeValue();
                            if (attName != null && attName.equals("ref")) {
                                way.addNodeRef(Integer.parseInt(attValue));
                            }
                        }
                    }
                    else if(nodeName.equals("tag")){
                        NamedNodeMap childAttributes = wayChild.getAttributes();
                        Tag t = new Tag();
                        for (int a = 0; a < childAttributes.getLength(); a++) {
                            org.w3c.dom.Node theAttribute = childAttributes.item(a);                            
                            String attName = theAttribute.getNodeName();
                            String attValue = theAttribute.getNodeValue();
                            if (attName != null && attName.equals("k")) {
                                t.setKey(attValue);
                            }
                            if (attName != null && attName.equals("v")) {
                                t.setValue(attValue);
                            }
                        }
                        way.addTag(t);
                    }                    
                }

            }

            NodeList nodeList = root.getElementsByTagName("node");
           
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = new Node();
                stream.addNode(node);
                org.w3c.dom.Node nodeNode = nodeList.item(i);
                
                NamedNodeMap nodeAttributes = nodeNode.getAttributes();

                for (int a = 0; a < nodeAttributes.getLength(); a++) {
                    org.w3c.dom.Node theAttribute = nodeAttributes.item(a);
                    
                    String attName = theAttribute.getNodeName();
                    String attValue = theAttribute.getNodeValue();
                    

                    if (attName != null && attName.equals("id")) {
                        node.setId(Integer.parseInt(attValue));
                    }
                    if (attName != null && attName.equals("lat")) {
                        node.setLat(attValue);
                    }
                    if (attName != null && attName.equals("lon")) {
                        node.setLon(attValue);
                    }
                    
                }
            }

            NodeList relationList = root.getElementsByTagName("relation");
            //System.out.println("relation list size : " + relationList.getLength());

            // Iterator it1 = root.elementIterator();
            // while (it1.hasNext()) {
            // Element elem = (Element) it1.next();
            // // System.out.println(elem.getName());
            // if (elem.getName().equals("way")) {
            // // System.out.println("found way :"+countWay);
            //
            // String wayID = elem.attribute("id").getText();
            // Way way = new Way(Integer.parseInt(wayID));
            //
            // if (elem.attribute("timestamp") != null) {
            // String wayTimesatmp = elem.attribute("timestamp")
            // .getText();
            // way.setTimestamp(wayTimesatmp);
            // }
            // if (elem.attribute("user") != null) {
            // String wayUser = elem.attribute("user").getText();
            // way.setUser(wayUser);
            // }
            // stream.addWay(way);
            // Iterator itWay = elem.elementIterator();
            // while (itWay.hasNext()) {
            // Element wayNode = (Element) itWay.next();
            // // System.out.println("node way :"+wayNode.getName());
            // if (wayNode.getName().equals("nd")) {
            // String nodeRef = wayNode.attribute("ref").getText();
            // way.addNodeRef(Integer.parseInt(nodeRef));
            // }
            // else if (wayNode.getName().equals("tag")) {
            // String tagKey = wayNode.attribute("k").getText();
            // String tagValue = wayNode.attribute("v").getText();
            // Tag t = new Tag(tagKey, tagValue);
            // way.addTag(t);
            //
            // }
            //
            // }
            //
            // }
            // if (elem.getName().equals("node")) {
            // // System.out.println("xapi node : ");
            // // <node id="255670438" lat="44.8335323" lon="-0.5949750"
            // // user="moore33" osmxapi:users="moore33"
            // // timestamp="2008-04-04T10:00:01Z">
            //
            // String nodeID = elem.attribute("id").getText();
            // String nodeLat = elem.attribute("lat").getText();
            // String nodeLon = elem.attribute("lon").getText();
            // Node node = new Node(Integer.parseInt(nodeID));
            // node.setLat(nodeLat);
            // node.setLon(nodeLon);
            // stream.addNode(node);
            // Iterator itNode = elem.elementIterator();
            // while (itNode.hasNext()) {
            // Element nodeNode = (Element) itNode.next();
            //
            // if (nodeNode.getName().equals("tag")) {
            // String tagKey = nodeNode.attribute("k").getText();
            // String tagValue = nodeNode.attribute("v").getText();
            // Tag t = new Tag(tagKey, tagValue);
            // node.addTag(t);
            //
            // }
            //
            // }
            //
            // }
            // if (elem.getName().equals("relation")) {
            // // System.out.println("relation node");
            // String relationID = elem.attribute("id").getText();
            //
            // Relation relation = new Relation(
            // Integer.parseInt(relationID));
            // stream.addRelation(relation);
            //
            // Iterator itWay = elem.elementIterator();
            // while (itWay.hasNext()) {
            // Element relationEntry = (Element) itWay.next();
            // // System.out.println("node way :"+wayNode.getName());
            // if (relationEntry.getName().equals("tag")) {
            // String tagKey = relationEntry.attribute("k")
            // .getText();
            // String tagValue = relationEntry.attribute("v")
            // .getText();
            // Tag t = new Tag(tagKey, tagValue);
            // relation.addTag(t);
            // }
            // if (relationEntry.getName().equals("member")) {
            // // <member type="way" ref="343" role="from" />
            //
            // String type = relationEntry.attribute("type")
            // .getText();
            // String ref = relationEntry.attribute("ref")
            // .getText();
            // String role = relationEntry.attribute("role")
            // .getText();
            // Member member = new Member(ref, role, type);
            // relation.addMember(member);
            // }
            //
            // }
            // }
            //
            // }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return stream;
    }

    

    /**
     * @param args
     */
    public static void main(String[] args) {
        OSMRestBridgeEngine rendering = new OSMRestBridgeEngine();
        rendering.start();

    }

}
