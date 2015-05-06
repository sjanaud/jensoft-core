/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.map.primitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Stream {

    private Vector<Way> ways;
    private Vector<Node> nodes;
    private Vector<Relation> relations;

    public Stream() {
        ways = new Vector<Way>();
        nodes = new Vector<Node>();
        relations = new Vector<Relation>();
    }

    // //////////////////WAYS////////////////////////////

    public void addWay(Way w) {
        ways.add(w);
    }

    public Way getWay(int wayID) {
        for (Way w : ways) {
            if (w.getId() == wayID) {
                w.setNodes(getNodes(w.getId()));
                return w;
            }
        }
        return null;
    }

    public List<Way> getWays(String tagKey) {
        ArrayList<Way> taggedWays = new ArrayList<Way>();
        for (Way w : ways) {
            for (Tag t : w.getTags()) {
                if (t.getKey().equals(tagKey)) {
                    w.setNodes(getNodes(w.getId()));
                    taggedWays.add(w);
                }
            }
        }
        return taggedWays;
    }

    public List<Way> getWays(String tagKey, String tagValue) {
        ArrayList<Way> filteredTaggedWays = new ArrayList<Way>();
        for (Way w : ways) {
            for (Tag t : w.getTags()) {
                if (t.getKey().equals(tagKey) && t.getValue().equals(tagValue)) {
                    w.setNodes(getNodes(w.getId()));
                    filteredTaggedWays.add(w);
                }
            }
        }
        return filteredTaggedWays;
    }

    public Vector<Way> getWays() {
        for (Way w : ways) {
            w.setNodes(getNodes(w.getId()));
        }
        return ways;

    }

    // ///////////////NODES//////////////////////
    public void addNode(Node n) {
        nodes.add(n);
    }

    public Node getNode(int nodeID) {
        for (Node n : nodes) {
            if (n.getId() == nodeID) {
                return n;
            }
        }
        return null;
    }

    public List<Node> getNodes(int wayID) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (Way w : ways) {
            if (w.getId() == wayID) {
                for (Integer ref : w.getNodesRef()) {
                    Node n = getNode(ref);
                    if (n != null) {
                        nodes.add(n);
                    }
                }
            }
        }

        return nodes;
    }

    public void addRelation(Relation r) {
        relations.add(r);
    }

    public void clear() {
        ways.clear();
        nodes.clear();
        relations.clear();
    }

}
