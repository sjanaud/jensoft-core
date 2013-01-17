/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.map.primitive;

import java.util.Vector;

public class Relation {

    private int id;

    private Vector<Member> members = new Vector<Member>();
    private Vector<Tag> tags = new Vector<Tag>();

    public Relation(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Vector<Tag> getTags() {
        return tags;
    }

    public void setTags(Vector<Tag> tags) {
        this.tags = tags;
    }

    public void addMember(Member m) {
        members.add(m);
    }

    public Vector<Member> getMembers() {
        return members;
    }

    public Member getMember(int index) {
        return members.get(index);
    }

}
