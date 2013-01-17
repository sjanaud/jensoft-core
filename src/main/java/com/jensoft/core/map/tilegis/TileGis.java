/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.tilegis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.layer.highway.Highway;
import com.jensoft.core.map.layer.landuse.Landuse;
import com.jensoft.core.map.layer.leisure.Leisure;
import com.jensoft.core.map.layer.natural.Natural;
import com.jensoft.core.map.layer.railway.Railway;
import com.jensoft.core.map.layer.waterway.Waterway;
import com.jensoft.core.map.primitive.Node;
import com.jensoft.core.map.primitive.Primitive;
import com.jensoft.core.map.primitive.Way;
import com.jensoft.core.map.tile.Tile;
import com.jensoft.core.map.tile.TileKey;

public class TileGis {

    private PreparedStatement nodeStatement;
    private PreparedStatement wayStatement;
    private PreparedStatement waynodeStatement;

    private PreparedStatement tileStatement;

    private PreparedStatement insertHighwayStatement;
    private PreparedStatement tilehighwayStatement;
    private PreparedStatement updateHighwayStatement;

    private PreparedStatement landuseStatement;
    private PreparedStatement tilelanduseStatement;

    private PreparedStatement leisureStatement;
    private PreparedStatement tileleisureStatement;

    private PreparedStatement waterwayStatement;
    private PreparedStatement tilewaterwayStatement;

    private PreparedStatement railwayStatement;
    private PreparedStatement tilerailwayStatement;

    private PreparedStatement naturalStatement;
    private PreparedStatement tilenaturalStatement;

    private Connection db;

    public TileGis() {

        openTileGis();

        try {

            nodeStatement = db
                    .prepareStatement("INSERT INTO tilegis.node  (id,latitude,longitude) VALUES (?,?,?)");
            wayStatement = db
                    .prepareStatement("INSERT INTO tilegis.way  (id) VALUES (?)");
            waynodeStatement = db
                    .prepareStatement("INSERT INTO tilegis.way_node  (way_id,node_id) VALUES (?,?)");
            tileStatement = db
                    .prepareStatement("INSERT INTO tilegis.tile  (x,y) VALUES (?,?)");

            insertHighwayStatement = db
                    .prepareStatement("INSERT INTO tilegis.highway  (id,nature,name) VALUES (?,?,?)");
            tilehighwayStatement = db
                    .prepareStatement("INSERT INTO tilegis.tile_highway  (highway_id,x,y) VALUES (?,?,?)");

            landuseStatement = db
                    .prepareStatement("INSERT INTO tilegis.landuse  (id,nature) VALUES (?,?)");
            tilelanduseStatement = db
                    .prepareStatement("INSERT INTO tilegis.tile_landuse  (landuse_id,x,y) VALUES (?,?,?)");

            leisureStatement = db
                    .prepareStatement("INSERT INTO tilegis.leisure  (id,nature) VALUES (?,?)");
            tileleisureStatement = db
                    .prepareStatement("INSERT INTO tilegis.tile_leisure  (leisure_id,x,y) VALUES (?,?,?)");

            waterwayStatement = db
                    .prepareStatement("INSERT INTO tilegis.waterway  (id,nature) VALUES (?,?)");
            tilewaterwayStatement = db
                    .prepareStatement("INSERT INTO tilegis.tile_waterway  (waterway_id,x,y) VALUES (?,?,?)");

            railwayStatement = db
                    .prepareStatement("INSERT INTO tilegis.railway  (id,nature) VALUES (?,?)");
            tilerailwayStatement = db
                    .prepareStatement("INSERT INTO tilegis.tile_railway  (railway_id,x,y) VALUES (?,?,?)");

            naturalStatement = db
                    .prepareStatement("INSERT INTO tilegis.natural  (id,nature) VALUES (?,?)");

            selectTileStatement = db
                    .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                     ResultSet.CONCUR_READ_ONLY);
            selectPrimitiveStatement = db
                    .createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                     ResultSet.CONCUR_READ_ONLY);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Statement selectTileStatement;
    private Statement selectPrimitiveStatement;

    public Tile selectTileHighways(int x, int y, int zoom) {
        Tile t = new Tile(x, y, zoom);
        List<Highway> highways = new ArrayList<Highway>();
        try {

            ResultSet highwaysSet = selectTileStatement
                    .executeQuery("SELECT highway_id,nature,name from tilegis.tile_highway,tilegis.highway WHERE  tilegis.tile_highway.x = "
                            + x
                            + " AND  tilegis.tile_highway.y = "
                            + y
                            + " AND tilegis.tile_highway.highway_id=tilegis.highway.id"
                            + ";");
            while (highwaysSet.next()) {
                int id = highwaysSet.getInt("highway_id");
                String name = highwaysSet.getString("name");
                String nature = highwaysSet.getString("nature");

                // System.out.println("NEW HIGHWAY : highway id :"
                // +id+";"+name+";"+nature);

                Highway h = new Highway(id, nature);
                h.setName(name);
                highways.add(h);

                selectPrimitive(id);
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return t;

    }

    private Primitive selectPrimitive(int id) {
        try {

            ResultSet primitiveSet = selectPrimitiveStatement
                    .executeQuery("SELECT tilegis.way.id as way_id,tilegis.way_node.node_id as node_id,tilegis.node.latitude as latitude,tilegis.node.longitude as longitude from tilegis.way,tilegis.way_node,tilegis.node WHERE tilegis.way.id = tilegis.way_node.way_id AND tilegis.way_node.node_id = tilegis.node.id AND tilegis.way.id = "
                            + id + ";");
            while (primitiveSet.next()) {
                int wayid = primitiveSet.getInt("way_id");
                int nodeid = primitiveSet.getInt("node_id");
                double latitude = primitiveSet.getDouble("latitude");
                double longitude = primitiveSet.getDouble("longitude");
                // System.out.println("primitive : "
                // +wayid+";"+nodeid+";"+latitude+";"+longitude);

            }

            primitiveSet.close();

        }
        catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    public void insertTile(Tile tile) {

        insertKey(tile.getKey());

        for (Highway highway : tile.getHighways()) {
            insertHighway(tile.getKey(), highway);
        }

        for (Railway railway : tile.getRailways()) {
            insertRailway(railway);
        }

        for (Waterway waterway : tile.getWaterways()) {
            insertWaterway(waterway);
        }

        for (Landuse landuse : tile.getLanduses()) {
            insertLanduse(landuse);
        }
        for (Leisure leisure : tile.getLeisures()) {
            insertLeisure(leisure);
        }

        for (Natural natural : tile.getNaturals()) {
            insertNatural(natural);
        }

    }

    private void insertKey(TileKey key) {
        // key
        try {
            tileStatement.setInt(1, key.getX());
            tileStatement.setInt(2, key.getY());
            tileStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPrimitive(Primitive primitive) {
        Way w = primitive.getWay();

        try {
            wayStatement.setInt(1, w.getId());
            wayStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        // node
        List<Node> nodes = primitive.getNodes();
        for (Node n : nodes) {
            try {
                nodeStatement.setInt(1, n.getId());
                nodeStatement.setDouble(2, n.getLatitudeAsDouble());
                nodeStatement.setDouble(3, n.getLongitudeAsDouble());
                nodeStatement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                waynodeStatement.setInt(1, w.getId());
                waynodeStatement.setInt(2, n.getId());
                waynodeStatement.executeUpdate();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void insertHighway(TileKey key, Highway h) {

        // highway
        try {
            insertHighwayStatement.setInt(1, h.getId());
            insertHighwayStatement.setString(2, h.getNature());
            insertHighwayStatement.setString(3, h.getName());

            insertHighwayStatement.executeUpdate();

            tilehighwayStatement.setInt(1, h.getId());
            tilehighwayStatement.setInt(2, key.getX());
            tilehighwayStatement.setInt(3, key.getY());

            tilehighwayStatement.executeUpdate();

        }
        catch (SQLException e) {

            // try {
            // updateHighwayStatement.setInt(3, h.getId());
            // updateHighwayStatement.setString(1, h.getNature());
            // updateHighwayStatement.setString(2, h.getName());
            //
            // updateHighwayStatement.executeUpdate();
            // } catch (SQLException e1) {
            // // TODO Auto-generated catch block
            // e1.printStackTrace();
            // }
            // update
        }

        insertPrimitive(h.getPrimitive());

    }

    public void insertRailway(Railway h) {
        // railway
        try {
            railwayStatement.setInt(1, h.getId());
            railwayStatement.setString(2, h.getNature());
            railwayStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        insertPrimitive(h.getPrimitive());
    }

    public void insertWaterway(Waterway h) {
        // waterway
        try {
            waterwayStatement.setInt(1, h.getId());
            waterwayStatement.setString(2, h.getNature());
            waterwayStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        insertPrimitive(h.getPrimitive());
    }

    public void insertLanduse(Landuse h) {
        // landuse
        try {
            landuseStatement.setInt(1, h.getId());
            landuseStatement.setString(2, h.getNature());
            landuseStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        insertPrimitive(h.getPrimitive());
    }

    public void insertLeisure(Leisure h) {
        // leisure
        try {
            leisureStatement.setInt(1, h.getId());
            leisureStatement.setString(2, h.getNature());
            leisureStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        insertPrimitive(h.getPrimitive());
    }

    public void insertNatural(Natural h) {
        // natural
        try {
            naturalStatement.setInt(1, h.getId());
            naturalStatement.setString(2, h.getNature());
            naturalStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        insertPrimitive(h.getPrimitive());
    }

    public void openTileGis() {
        try {
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection(
                                             "jdbc:postgresql://localhost:5432/mposm", "postgres",
                                             "password");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeTileGis() {
        try {
            db.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
