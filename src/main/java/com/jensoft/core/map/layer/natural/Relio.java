/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.layer.natural;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Relio {

    private double W;
    private double E;
    private double N;
    private double S;

    private Point2D input;
    private Point2D output;

    public Relio(double W, double E, double N, double S) {

        this.W = W;
        this.E = E;
        this.N = N;
        this.S = S;

    }

    private Point2D getInput(LineString ls) {
        Coordinate[] C = ls.getCoordinates();
        Coordinate cStart = C[0];
        return new Point2D.Double(cStart.x, cStart.y);
    }

    private Point2D getOutput(LineString ls) {
        Coordinate[] C = ls.getCoordinates();
        Coordinate cEnd = C[C.length - 1];
        return new Point2D.Double(cEnd.x, cEnd.y);
    }

    private boolean isS(Point2D p2d) {
        double y = p2d.getY();
        return y == S;
    }

    private boolean isN(Point2D p2d) {
        double y = p2d.getY();
        return y == N;
    }

    private boolean isW(Point2D p2d) {
        double x = p2d.getX();
        return x == W;
    }

    private boolean isE(Point2D p2d) {
        double x = p2d.getX();
        return x == E;
    }

    private boolean isSW(Point2D p2d) {
        double x = p2d.getX();
        double y = p2d.getY();
        return y == S && x == W;
    }

    private boolean isNW(Point2D p2d) {
        double x = p2d.getX();
        double y = p2d.getY();
        return y == N && x == W;
    }

    private boolean isNE(Point2D p2d) {
        double x = p2d.getX();
        double y = p2d.getY();
        return x == E && y == N;
    }

    private boolean isSE(Point2D p2d) {
        double x = p2d.getX();
        double y = p2d.getY();
        return x == E && y == S;
    }

    private List<LineString> lines = new ArrayList<LineString>();

    public void addInputOutput(LineString ls) {
        lines.add(ls);
    }

    public void reliableIO() {
        for (LineString ls : lines) {

            if (isFree(ls)) {
                ClosedLine cl = new ClosedLine();
                cl.append(ls);
                closedLines.add(cl);

                close(cl);
            }

        }
    }

    public List<GeneralPath> getClosedPaths() {
        List<GeneralPath> closedPathCoastlines = new ArrayList<GeneralPath>();
        for (ClosedLine cl : closedLines) {
            closedPathCoastlines.add(cl.getClosePath());
        }

        return closedPathCoastlines;

    }

    private void close(ClosedLine cl) {
        LineString ls = cl.getFirstLine();
        Point2D output = getOutput(ls);

        boolean flag = true;
        int count = 0;
        while (flag) {
           // (count++);
            Object compatibleInput = getCompatibleInput(output);

            if (compatibleInput != null) {

                if (compatibleInput instanceof LineString
                        && ((LineString) compatibleInput).equals(cl
                                .getFirstLine())) {
                    flag = false;
                }

                if (flag) {
                    if (compatibleInput instanceof Point2D) {
                        cl.append((Point2D) compatibleInput);
                        output = (Point2D) compatibleInput;
                    }
                    else if (compatibleInput instanceof LineString) {
                        cl.append((LineString) compatibleInput);
                        output = getOutput((LineString) compatibleInput);

                        // if(((LineString)compatibleInput).equals(cl.getFirstLine()))
                        // flag = false;
                    }
                }

            }

        }
        // try to close this line
    }

    private LineString getReliableWest(Point2D output) {
        List<LineString> reliableWest = new ArrayList<LineString>();
        for (LineString ls : lines) {
            if (isW(getInput(ls)) && getInput(ls).getY() > output.getY()) {
                reliableWest.add(ls);
            }
        }

        LineString reliable = null;
        for (LineString lw : reliableWest) {
            if (reliable == null) {
                reliable = lw;
            }

            Point2D reliableInput = getInput(reliable);
            Point2D curentInput = getInput(lw);

            if (curentInput.getY() < reliableInput.getY()) {
                reliable = lw;
            }

        }

        return reliable;
    }

    private LineString getReliableNorth(Point2D output) {
        List<LineString> reliableNorth = new ArrayList<LineString>();
        for (LineString ls : lines) {
            if (isN(getInput(ls)) && getInput(ls).getX() < output.getX()) {
                reliableNorth.add(ls);
            }
        }

        LineString reliable = null;
        for (LineString ln : reliableNorth) {
            if (reliable == null) {
                reliable = ln;
            }

            Point2D reliableInput = getInput(reliable);
            Point2D curentInput = getInput(ln);

            if (curentInput.getX() > reliableInput.getX()) {
                reliable = ln;
            }

        }

        return reliable;
    }

    private LineString getReliableSouth(Point2D output) {
        List<LineString> reliableSouth = new ArrayList<LineString>();
        for (LineString ls : lines) {
            if (isS(getInput(ls)) && getInput(ls).getX() > output.getX()) {
                reliableSouth.add(ls);
            }
        }

        LineString reliable = null;
        for (LineString ls : reliableSouth) {
            if (reliable == null) {
                reliable = ls;
            }

            Point2D reliableInput = getInput(reliable);
            Point2D curentInput = getInput(ls);

            if (curentInput.getX() < reliableInput.getX()) {
                reliable = ls;
            }

        }

        return reliable;
    }

    private LineString getReliableEast(Point2D output) {
        List<LineString> reliableEast = new ArrayList<LineString>();
        for (LineString ls : lines) {
            if (isE(getInput(ls)) && getInput(ls).getY() < output.getY()) {
                reliableEast.add(ls);
            }
        }

        LineString reliable = null;
        for (LineString le : reliableEast) {
            if (reliable == null) {
                reliable = le;
            }

            Point2D reliableInput = getInput(reliable);
            Point2D curentInput = getInput(le);

            if (curentInput.getY() > reliableInput.getY()) {
                reliable = le;
            }

        }

        return reliable;
    }

    private Object getCompatibleInput(Point2D output) {

        if (isN(output)) {
            if (isNW(output)) {
                // for(LineString ls : lines){
                // if(isW(getInput(ls)) && getInput(ls).getY() > output.getY())
                // return ls;
                // }
                LineString l = getReliableWest(output);
                if (l != null) {
                    return l;
                }
                return new Point2D.Double(W, S);
            }
            else {
                // for(LineString ls : lines){
                // if(isN(getInput(ls)) && getInput(ls).getX() < output.getX())
                // return ls;
                // }
                LineString l = getReliableNorth(output);
                if (l != null) {
                    return l;
                }
                return new Point2D.Double(W, N);
            }
        }
        else if (isW(output)) {

            if (isSW(output)) {
                // for(LineString ls : lines){
                // if(isS(getInput(ls)) && getInput(ls).getX() > output.getX())
                // return ls;
                // }
                LineString l = getReliableSouth(output);
                if (l != null) {
                    return l;
                }
                return new Point2D.Double(E, S);
            }
            else {
                // for(LineString ls : lines){
                // if(isW(getInput(ls)) && getInput(ls).getY() > output.getY())
                // return ls;
                // }
                LineString l = getReliableWest(output);
                if (l != null) {
                    return l;
                }
                return new Point2D.Double(W, S);
            }

        }
        else if (isS(output)) {
            if (isSE(output)) {
                // for(LineString ls : lines){
                // if(isE(getInput(ls)) && getInput(ls).getY() < output.getY())
                // return ls;
                // }
                LineString l = getReliableEast(output);
                if (l != null) {
                    return l;
                }
                return new Point2D.Double(E, N);
            }
            else {
                // for(LineString ls : lines){
                // if(isS(getInput(ls)) && getInput(ls).getX() > output.getX())
                // // sqqsdqsdqsdq
                // return ls;
                // }
                LineString l = getReliableSouth(output);
                if (l != null) {
                    return l;
                }
                return new Point2D.Double(E, S);
            }
        }
        else if (isE(output)) {
            if (isNE(output)) {
                // for(LineString ls : lines){
                // if(isN(getInput(ls)) && getInput(ls).getX() < output.getX())
                // return ls;
                // }
                LineString l = getReliableNorth(output);
                if (l != null) {
                    return l;
                }
                return new Point2D.Double(W, N);
            }
            else {
                // for(LineString ls : lines){
                // if(isE(getInput(ls)) & getInput(ls).getY() < output.getY())
                // return ls;
                // }
                LineString l = getReliableEast(output);
                if (l != null) {
                    return l;
                }
                return new Point2D.Double(E, N);
            }
        }
        return null;
    }

    private List<ClosedLine> closedLines = new ArrayList<ClosedLine>();

    private boolean isFree(LineString ls) {
        for (ClosedLine cl : closedLines) {
            if (cl.isRegister(ls)) {
                return false;
            }
        }

        return true;
    }

    class ClosedLine {

        private List<LineString> lss = new ArrayList<LineString>();
        private GeneralPath closedPath;

        public ClosedLine() {

        }

        public GeneralPath getClosePath() {
            return closedPath;
        }

        public void append(LineString ls) {
            addLine(ls);
            Coordinate[] C = ls.getCoordinates();
            if (closedPath == null) {
                closedPath = new GeneralPath();
                Coordinate cStart = C[0];
                closedPath.moveTo(cStart.x, cStart.y);
                for (int i = 0; i < C.length; i++) {
                    closedPath.lineTo(C[i].x, C[i].y);
                }
            }
            else {
                for (int i = 0; i < C.length; i++) {
                    closedPath.lineTo(C[i].x, C[i].y);
                }
            }

        }

        public void append(Point2D p2d) {
            closedPath.lineTo(p2d.getX(), p2d.getY());
        }

        public LineString getFirstLine() {
            return lss.get(0);
        }

        public LineString getLastLine() {
            return lss.get(lss.size() - 1);
        }

        private void addLine(LineString ls) {
            lss.add(ls);
        }

        private boolean isRegister(LineString ls) {
            for (LineString locallLS : lss) {
                if (locallLS.equals(ls)) {
                    return true;
                }
            }
            return false;
        }
    }

}
