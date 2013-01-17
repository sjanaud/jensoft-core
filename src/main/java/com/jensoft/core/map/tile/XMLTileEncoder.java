/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.tile;

import java.io.File;

import org.w3c.dom.Document;

public class XMLTileEncoder {

    String tileRootDir = "c:" + File.separator + "mapbase" + File.separator
            + "tiles";

    public XMLTileEncoder(String tileRootDir) {
        this.tileRootDir = tileRootDir;
    }

    private Document document;

    public void encode(Tile t) {
        // document = DocumentHelper.createDocument();
        // document.setXMLEncoding("ISO-8859-1");
        //
        // File f = new File(tileRootDir);
        // if (!f.exists())
        // f.mkdirs();
        //
        // String tilePath = tileRootDir + File.separator + "tile_"
        // + t.getKey().getZoom() + "_" + t.getKey().getX() + "_"
        // + t.getKey().getY() + ".tile";
        // Element tileRoot = document.addElement("tile");
        //
        // Element keyElement = tileRoot.addElement("key");
        // keyElement.addElement("level").addText("" + t.getKey().getZoom());
        // keyElement.addElement("x").addText("" + t.getKey().getX());
        // keyElement.addElement("y").addText("" + t.getKey().getY());
        //
        // encodeHighways(t, tileRoot);
        // encodeLanduses(t, tileRoot);
        // encodeLeisures(t, tileRoot);
        // encodeNaturals(t, tileRoot);
        // encodeRailways(t, tileRoot);
        //
        // OutputFormat format = OutputFormat.createPrettyPrint();
        // format.setEncoding("ISO-8859-1");
        //
        // try {
        // XMLWriter writer = new XMLWriter(new FileWriter(tilePath), format);
        // writer.write(document);
        // writer.close();
        // } catch (IOException e) {
        //
        // e.printStackTrace();
        // }

    }

    // private void encodeHighways(Tile t, Element tileRoot) {
    // Element highwayRoot = tileRoot.addElement("highways");
    // for (Highway h : t.getHighways()) {
    // Element highwayNode = highwayRoot.addElement("highway");
    // highwayNode.addAttribute("nature", "" + h.getNature());
    // highwayNode.addAttribute("name", "" + h.getName());
    //
    // encodePrimitive(h.getPrimitive(), highwayNode);
    // }
    // }
    //
    // private void encodeLanduses(Tile t, Element tileRoot) {
    // Element landuseRoot = tileRoot.addElement("landuses");
    // for (Landuse l : t.getLanduses()) {
    // Element landuseNode = landuseRoot.addElement("landuse");
    // landuseNode.addAttribute("nature", "" + l.getNature());
    // landuseNode.addAttribute("name", "" + l.getName());
    //
    // encodePrimitive(l.getPrimitive(), landuseNode);
    // }
    // }
    //
    // private void encodeLeisures(Tile t, Element tileRoot) {
    // Element leisureRoot = tileRoot.addElement("leisures");
    // for (Leisure l : t.getLeisures()) {
    // Element leisureNode = leisureRoot.addElement("leisure");
    // leisureNode.addAttribute("nature", "" + l.getNature());
    // leisureNode.addAttribute("name", "" + l.getName());
    //
    // encodePrimitive(l.getPrimitive(), leisureNode);
    // }
    // }
    //
    // private void encodeNaturals(Tile t, Element tileRoot) {
    // Element naturalRoot = tileRoot.addElement("naturals");
    // for (Natural n : t.getNaturals()) {
    // Element naturalNode = naturalRoot.addElement("natural");
    // naturalNode.addAttribute("nature", "" + n.getNature());
    // naturalNode.addAttribute("name", "" + n.getName());
    //
    // encodePrimitive(n.getPrimitive(), naturalNode);
    // }
    // }
    //
    // private void encodeRailways(Tile t, Element tileRoot) {
    // Element railwayRoot = tileRoot.addElement("railways");
    // for (Railway r : t.getRailways()) {
    // Element railwayNode = railwayRoot.addElement("railway");
    // railwayNode.addAttribute("nature", "" + r.getNature());
    // railwayNode.addAttribute("name", "" + r.getName());
    //
    // encodePrimitive(r.getPrimitive(), railwayNode);
    // }
    // }
    //
    // // private void encodeManmade(Tile t,Element tileRoot){
    // // Element manmadeRoot = tileRoot.addElement( "manmades" );
    // // for(ManMade l : t.get)){
    // // Element manmadeNode = manmadeRoot.addElement( "manmade" );
    // // manmadeNode.addAttribute("nature", ""+l.getNature());
    // // manmadeNode.addAttribute("name", ""+l.getName());
    // //
    // // encodePrimitive(l.getPrimitive(), manmadeNode);
    // // }
    // // }
    //
    // private void encodePrimitive(Primitive primitive, Element parentElement) {
    // Way way = primitive.getWay();
    // List<Node> nodes = primitive.getNodes();
    // Element primitiveNode = parentElement.addElement("way");
    //
    // primitiveNode.addAttribute("id", "" + way.getId());
    //
    // for (Tag tag : way.getTags()) {
    // Element tagElement = primitiveNode.addElement("tag");
    // tagElement.addAttribute("key", tag.getKey());
    // tagElement.addAttribute("value", tag.getValue());
    // }
    //
    // for (Node n : nodes) {
    // Element nodeElement = primitiveNode.addElement("node");
    // nodeElement.addAttribute("id", "" + n.getId());
    // nodeElement.addAttribute("lat", "" + n.getLat());
    // nodeElement.addAttribute("lon", "" + n.getLon());
    //
    // for (Tag tag : n.getTags()) {
    // Element tagElement = nodeElement.addElement("tag");
    // tagElement.addAttribute("key", tag.getKey());
    // tagElement.addAttribute("value", tag.getValue());
    // }
    // }
    // }

}
