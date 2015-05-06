/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.tilegis;

import org.jensoft.core.map.primitive.Stream;
import org.jensoft.core.map.tile.Tile;

public class GisRestOSMBridge {

    public GisRestOSMBridge() {

    }

    /**
     * stream a tile with the specified index
     * 
     * @param x
     * @param y
     * @param zoom
     *            [ 15,16,17 ]
     * @return the stream of this tile
     */
    public Tile getTile(int x, int y, int zoom) {
        Tile tile = new Tile(x, y, zoom);
        Stream stream = new Stream();
        // try {
        // double longLeft = MapUtil.tile2long(x, zoom);
        // double longRight = MapUtil.tile2long(x + 1, zoom);
        // double latNorth = MapUtil.tile2lat(y, zoom);
        // double latSouth = MapUtil.tile2lat(y + 1, zoom);
        // String bounding = "bbox=" + Double.toString(longLeft) + ","
        // + Double.toString(latSouth) + ","
        // + Double.toString(longRight) + ","
        // + Double.toString(latNorth);
        // String url = "http://api.openstreetmap.org/api/0.6/map?" + bounding;
        // URL load = new URL(url);
        // SAXReader reader = new SAXReader();
        // Document d = reader.read(load);
        // Element root = d.getRootElement();
        // System.out.println("READ TILE STREAM [" + x + ";" + y + "]" + url);
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
        // } else if (wayNode.getName().equals("tag")) {
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
        //
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        //
        // tile.setHighways(MapObjectFactory.createHighways(stream));
        // tile.setRailways(MapObjectFactory.createRailways(stream));
        // tile.setWaterways(MapObjectFactory.createWaterways(stream));
        // tile.setLanduses(MapObjectFactory.createLanduses(stream));
        // tile.setLeisures(MapObjectFactory.createLeisures(stream));
        // tile.setNaturals(MapObjectFactory.createNatural(stream));

        return tile;
    }

}
