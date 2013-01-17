/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.natural;

import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.jensoft.core.map.primitive.Node;
import com.jensoft.core.map.projection.GeoBound;
import com.jensoft.core.map.projection.GeoPosition;
import com.jensoft.core.map.projection.Projection2D;

public class CoastlineSkelet {

    private GeoBound geoBound;
    private Projection2D projection;

    public CoastlineSkelet(GeoBound geoBound, Projection2D projection) {
        this.geoBound = geoBound;
        this.projection = projection;
    }

    private List<Natural> naturalsCoastlineSegments = new ArrayList<Natural>();

    public void register(Natural natural) {
        naturalsCoastlineSegments.add(natural);
    }

    public void createSkelet() {

        // dump
        dumpBaseCoastline();

        // connecter les coastline
        initCoastline();
        makeLineCoastline();
        makeClosedCoastline();
        // dump made coastline
        dumpMadeCoastline();

        // intersection
        createIntersection();
        // make closed polygon

        createClosedPolygon();

    }

    private List<GeneralPath> closedPathCoastlines = new ArrayList<GeneralPath>();

    public List<GeneralPath> getClosedPathCoastlines() {
        return closedPathCoastlines;
    }

    public Area getCoastlineSkelet() {

        double latNorth = geoBound.getLatitudeNorth();
        double latSouth = geoBound.getLatitudeSouth();
        double longWest = geoBound.getLongitudeWest();
        double longEast = geoBound.getLongitudeEast();

        Point2D p1 = projection.geoToPixel(new GeoPosition(latNorth, longWest));
        Point2D p2 = projection.geoToPixel(new GeoPosition(latNorth, longEast));
        Point2D p3 = projection.geoToPixel(new GeoPosition(latSouth, longWest));
        Point2D p4 = projection.geoToPixel(new GeoPosition(latSouth, longEast));

        GeneralPath gp = new GeneralPath();
        gp.moveTo(p1.getX(), p1.getY());
        gp.lineTo(p2.getX(), p2.getY());
        gp.lineTo(p4.getX(), p4.getY());
        gp.lineTo(p3.getX(), p3.getY());
        gp.closePath();

        Area area = new Area(gp);
        List<GeneralPath> closedCoastlinePath = getClosedPathCoastlines();

        for (int i = 0; i < closedCoastlinePath.size(); i++) {
            //System.out.println("SUBSTRACT !");
            Area sa = new Area(closedCoastlinePath.get(i));
            area.subtract(sa);
        }

        return area;
    }

    private void createClosedPolygon() {
        double latNorth = geoBound.getLatitudeNorth();
        double latSouth = geoBound.getLatitudeSouth();
        double longWest = geoBound.getLongitudeWest();
        double longEast = geoBound.getLongitudeEast();

        Point2D p1 = projection.geoToPixel(new GeoPosition(latNorth, longWest));
        Point2D p2 = projection.geoToPixel(new GeoPosition(latNorth, longEast));
        Point2D p3 = projection.geoToPixel(new GeoPosition(latSouth, longWest));
        Point2D p4 = projection.geoToPixel(new GeoPosition(latSouth, longEast));

        Relio relio = new Relio(p1.getX(), p2.getX(), p1.getY(), p3.getY());

        // create closed polygon for line coast line (intersection between render
        // rectangle and line coast line)
        List<LineString> lss = getIntersection();

        for (LineString ls : lss) {
            relio.addInputOutput(ls);
        }
        relio.reliableIO();
        closedPathCoastlines.addAll(relio.getClosedPaths());

        // add the closed coastline
        for (Coastline c : closedCoastline) {
            LineString l = c.getGeometry();

            GeneralPath path = new GeneralPath();
            Coordinate[] C = l.getCoordinates();
            Coordinate cStart = C[0];
            path.moveTo(cStart.x, cStart.y);
            for (int i = 0; i < C.length; i++) {
                path.lineTo(C[i].x, C[i].y);
            }
            path.closePath();

            closedPathCoastlines.add(path);
        }

    }

    private void createIntersection() {
        // Polygon polygon = (Polygon)getGeoBoundGeometry();
        Rectangle2D polygon = getGeoBoundGeometry();

        // for(Coastline c: lineCoastline){
        //
        // LineString ls = (LineString)c.getGeometry();
        //
        // Geometry intersectionGeometry = polygon.intersection(ls);
        // //System.out.println("INTERSECTION TYPE :"+intersectionGeometry.getGeometryType());
        // if(intersectionGeometry instanceof MultiLineString){
        // MultiLineString mls = (MultiLineString)intersectionGeometry;
        // for (int i = 0; i < mls.getNumGeometries(); i++) {
        // Geometry gls = mls.getGeometryN(i);
        // //System.out.println("MLS TYPE ITEM : "+gls.getGeometryType());
        // if(gls instanceof LineString){
        // linesIntersection.add((LineString)gls);
        // }
        // }
        // }
        // else if(intersectionGeometry instanceof LineString){
        // linesIntersection.add((LineString)intersectionGeometry);
        // }
        // }
    }

    private List<LineString> linesIntersection = new ArrayList<LineString>();

    public List<LineString> getIntersection() {
        return linesIntersection;
    }

    private Rectangle2D getGeoBoundGeometry() {

        double latNorth = geoBound.getLatitudeNorth();
        double latSouth = geoBound.getLatitudeSouth();
        double longWest = geoBound.getLongitudeWest();
        double longEast = geoBound.getLongitudeEast();

        Point2D p1 = projection.geoToPixel(new GeoPosition(latNorth, longWest));
        Point2D p2 = projection.geoToPixel(new GeoPosition(latNorth, longEast));
        Point2D p3 = projection.geoToPixel(new GeoPosition(latSouth, longWest));
        Point2D p4 = projection.geoToPixel(new GeoPosition(latSouth, longEast));

        GeneralPath path = new GeneralPath();
        path.moveTo(p1.getX(), p1.getY());
        path.lineTo(p2.getX(), p2.getY());
        path.lineTo(p3.getX(), p3.getY());
        path.lineTo(p4.getX(), p4.getY());

        path.getBounds2D();

        return path.getBounds2D();
    }

    private void dumpBaseCoastline() {
        for (Natural n : naturalsCoastlineSegments) {
            System.out.println("COASTLINE SEGMENT :" + n.getId()
                    + "; nodeStart="
                    + n.getPrimitive().getWay().getFirstNode().getId()
                    + " nodeEnd="
                    + n.getPrimitive().getWay().getLastNode().getId()
                    + "; closed :" + n.getPrimitive().getWay().isClosed()
                    + "; primary segment :" + isPrimarySegment(n));
        }

        // cyclic test
       // System.out.println("CYCLIC TEST !");
        for (Natural n : naturalsCoastlineSegments) {
            System.out.println("CYCLIC SEGMENT :" + n.getId() + " :"
                    + isCyclic(n));
        }
    }

    private List<Coastline> lineCoastline = new ArrayList<Coastline>();
    private List<Coastline> closedCoastline = new ArrayList<Coastline>();

    
    private void initCoastline() {

        for (Natural n : naturalsCoastlineSegments) {

            if (n.getPrimitive().getWay().isClosed()) {
                Coastline cClosed = new Coastline();
                cClosed.addSegment(n);
                closedCoastline.add(cClosed);
            }
            else {
                if (isPrimarySegment(n)) {
                    Coastline c = new Coastline();
                    c.addSegment(n);

                    lineCoastline.add(c);
                }
            }

        }

    }

    private void makeLineCoastline() {

        for (Coastline c : lineCoastline) {
            boolean flag = true;
            Natural curentSegment = c.getLastSegment();
            while (flag) {

                curentSegment = getNextSegment(curentSegment);
                if (curentSegment != null) {
                    c.addSegment(curentSegment);
                }
                else {
                    flag = false;
                }
            }
        }
    }

    private void makeClosedCoastline() {
        // cyclic
        for (Natural n : naturalsCoastlineSegments) {

            if (isCyclic(n)) {
                System.out.println("CYCLED MAKING :" + n.getId() + " cycled?:"
                        + isAlreadyCycled(n));
                if (!isAlreadyCycled(n)) {
                    Coastline c = new Coastline();
                    c.addSegment(n);

                    closedCoastline.add(c);

                    makeCycle(c);
                }
            }
        }
    }

    private boolean isAlreadyCycled(Natural n) {
        for (Coastline c : closedCoastline) {

            for (Natural ns : c.getSegments()) {
                if (ns.equals(n)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void makeCycle(Coastline c) {
        Natural curentSegment = c.getFirstSegment();

        while (true) {
            curentSegment = getNextSegment(curentSegment);

            if (curentSegment.equals(c.getFirstSegment())) {
                return;
            }

            c.addSegment(curentSegment);
        }
    }

    private boolean isCyclic(Natural n) {

        Natural curentSegment = n;
        while (true) {
            curentSegment = getNextSegment(curentSegment);
            if (curentSegment == null) {
                return false;
            }
            if (curentSegment.equals(n)) {
                return true;
            }
        }

    }

    private Natural getNextSegment(Natural n) {
        Node lastNodeSegment = n.getPrimitive().getWay().getLastNode();

        for (Natural nc : naturalsCoastlineSegments) {
            if (nc.equals(n)) {
                continue;
            }
            Node ln = nc.getPrimitive().getWay().getFirstNode();
            if (ln.equals(lastNodeSegment)) {
                return nc;
            }
        }
        return null;
    }

    private void dumpMadeCoastline() {
        for (Coastline c : closedCoastline) {
            System.out.println("CLOSED COASTLINE : " + c.countSegment());
            for (int i = 0; i < c.countSegment(); i++) {
                System.out.println("SEG :"
                        + c.getSegment(i).getPrimitive().getWay().getId());

            }
        }

        for (Coastline c : lineCoastline) {
            System.out.println("LINE COASTLINE : " + c.countSegment());
            for (int i = 0; i < c.countSegment(); i++) {
                System.out.println("SEG :"
                        + c.getSegment(i).getPrimitive().getWay().getId());

            }
        }
    }

    private boolean isPrimarySegment(Natural coastline) {

        // the node start of this coast line fragment
        Node startNode = coastline.getPrimitive().getWay().getNodes().get(0);
        boolean primary = true;
        for (Natural n : naturalsCoastlineSegments) {
            if (n.equals(coastline)) {
                continue;
            }

            Node lastNode = n.getPrimitive().getWay().getLastNode();

            if (lastNode.equals(startNode)) {
                primary = false;
            }
        }

        return primary;
    }

}
