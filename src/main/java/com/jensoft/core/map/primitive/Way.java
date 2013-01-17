/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.primitive;

import java.util.ArrayList;
import java.util.List;

public class Way {

    private int id;
    private String timestamp;
    private String user;

    private List<Integer> nodesRef = new ArrayList<Integer>();
    private List<Node> nodes = new ArrayList<Node>();
    private List<Tag> tags = new ArrayList<Tag>();

    public Way() {
    }

    public Way(int id) {
        super();
        this.id = id;
    }

    public void addNodeRef(int n) {
        nodesRef.add(n);
    }

    public void addTag(String key, String value) {
        tags.add(new Tag(key, value));
    }

    public void addTag(Tag t) {
        tags.add(t);
    }

    public Tag getTag(int index) {
        return tags.get(index);
    }

    public Tag getTag(String key) {
        for (Tag t : tags) {
            if (t.getKey().equals(key)) {
                return t;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Integer> getNodesRef() {
        return nodesRef;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public boolean isClosed() {
        if (nodes != null && nodes.size() > 2) {
            Node start = nodes.get(0);
            Node end = nodes.get(nodes.size() - 1);

            if (start.equals(end)) {
                return true;
            }
        }
        return false;
    }

    public Node getFirstNode() {
        return nodes.get(0);
    }

    public Node getLastNode() {
        return nodes.get(nodes.size() - 1);
    }

}
