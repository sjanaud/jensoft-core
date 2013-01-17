/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.glyphmetrics;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>GeometryPath</code>
 * <ul>
 * <li>length of path</li>
 * <li>point at a path length</li>
 * <li>tangent path angle</li>
 * </ul>
 * 
 * @author Sebastien Janaud
 */
public class GeometryPath {

    /** geometry path */
    protected Shape path;

    /** flattened path segments */
    protected List<PathSegment> segments;

    /** segment indexes */
    protected int[] segmentIndexes;

    /** path length */
    protected float pathLength;

    /** initialized flag */
    protected boolean initialised;

    /**
     * create geometry for the specified shape
     * 
     * @param path
     */
    public GeometryPath(Shape path) {
        setPath(path);
    }

    /**
     * get path of this geometry
     * 
     * @return geometry path
     */
    public Shape getPath() {
        return path;
    }

    /**
     * set geometry path
     * 
     * @param path
     *            the path to set in this geometry
     */
    public void setPath(Shape path) {
        this.path = path;
        initialised = false;
    }

    /**
     * get length of path
     * 
     * @return length of path
     */
    public float lengthOfPath() {
        if (!initialised) {
            initialise();
        }
        return pathLength;
    }

    /**
     * Flattens the path and solve path length.
     */
    protected void initialise() {
        pathLength = 0f;
        if (path == null) {
            return;
        }
        PathIterator pi = path.getPathIterator(new AffineTransform());
        SingleSegmentPathIterator sspi = new SingleSegmentPathIterator();
        segments = new ArrayList<PathSegment>(20);
        List<Integer> indexes = new ArrayList<Integer>(20);
        int index = 0;
        int origIndex = -1;
        float lastMoveX = 0f;
        float lastMoveY = 0f;
        float currentX = 0f;
        float currentY = 0f;
        float[] seg = new float[6];
        int segType;

        segments.add(new PathSegment(PathIterator.SEG_MOVETO, 0f, 0f, 0f,
                                     origIndex));

        while (!pi.isDone()) {
            origIndex++;
            indexes.add(new Integer(index));
            segType = pi.currentSegment(seg);
            switch (segType) {
                case PathIterator.SEG_MOVETO:
                    segments.add(new PathSegment(segType, seg[0], seg[1],
                                                 pathLength, origIndex));
                    currentX = seg[0];
                    currentY = seg[1];
                    lastMoveX = currentX;
                    lastMoveY = currentY;
                    index++;
                    pi.next();
                    break;
                case PathIterator.SEG_LINETO:
                    pathLength += Point2D.distance(currentX, currentY, seg[0],
                                                   seg[1]);
                    segments.add(new PathSegment(segType, seg[0], seg[1],
                                                 pathLength, origIndex));
                    currentX = seg[0];
                    currentY = seg[1];
                    index++;
                    pi.next();
                    break;
                case PathIterator.SEG_CLOSE:
                    pathLength += Point2D.distance(currentX, currentY, lastMoveX,
                                                   lastMoveY);
                    segments.add(new PathSegment(PathIterator.SEG_LINETO,
                                                 lastMoveX, lastMoveY, pathLength, origIndex));
                    currentX = lastMoveX;
                    currentY = lastMoveY;
                    index++;
                    pi.next();
                    break;
                default:
                    sspi.setPathIterator(pi, currentX, currentY);
                    FlatteningPathIterator fpi = new FlatteningPathIterator(sspi,
                                                                            0.01f);
                    while (!fpi.isDone()) {
                        segType = fpi.currentSegment(seg);
                        if (segType == PathIterator.SEG_LINETO) {
                            pathLength += Point2D.distance(currentX, currentY,
                                                           seg[0], seg[1]);
                            segments.add(new PathSegment(segType, seg[0], seg[1],
                                                         pathLength, origIndex));
                            currentX = seg[0];
                            currentY = seg[1];
                            index++;
                        }
                        fpi.next();
                    }
            }
        }
        segmentIndexes = new int[indexes.size()];
        for (int i = 0; i < segmentIndexes.length; i++) {
            segmentIndexes[i] = indexes.get(i).intValue();
        }
        initialised = true;
    }

    /**
     * Returns the number of segments in the path.
     */
    public int getNumberOfSegments() {
        if (!initialised) {
            initialise();
        }
        return segmentIndexes.length;
    }

    /**
     * Returns the length from path begin to segment defines by index
     */
    public float getLengthAtSegment(int index) {
        if (!initialised) {
            initialise();
        }
        if (index <= 0) {
            return 0;
        }
        if (index >= segmentIndexes.length) {
            return pathLength;
        }
        PathSegment seg = segments.get(segmentIndexes[index]);
        return seg.getLength();
    }

    /**
     * returns the index of the segment at the given length
     * 
     * @param length
     *            the path length
     */
    public int segmentAtLength(float length) {
        int upperIndex = findUpperIndex(length);
        if (upperIndex == -1) {
            return -1;
        }

        if (upperIndex == 0) {
            PathSegment upper = segments.get(upperIndex);
            return upper.getIndex();
        }

        PathSegment lower = segments.get(upperIndex - 1);
        return lower.getIndex();
    }

    /**
     * return the point which is defined by the given fraction of the path
     * segment for the specified index.
     * 
     * @param index
     *            the segment index
     * @param fraction
     *            segment fraction
     */
    public Point2D pointAtLength(int index, float fraction) {
        if (!initialised) {
            initialise();
        }
        if (index < 0 || index >= segmentIndexes.length) {
            return null;
        }
        PathSegment seg = segments.get(segmentIndexes[index]);
        float start = seg.getLength();
        float end;
        if (index == segmentIndexes.length - 1) {
            end = pathLength;
        }
        else {
            seg = segments.get(segmentIndexes[index + 1]);
            end = seg.getLength();
        }
        return pointAtLength(start + (end - start) * fraction);
    }

    /**
     * return the point defines by the specified length
     * 
     * @param length
     *            The length along the path
     * @return The point at the specified length
     */
    public Point2D pointAtLength(float length) {
        int upperIndex = findUpperIndex(length);
        if (upperIndex == -1) {
            return null;
        }

        PathSegment upper = segments.get(upperIndex);

        if (upperIndex == 0) {
            return new Point2D.Float(upper.getX(), upper.getY());
        }

        PathSegment lower = segments.get(upperIndex - 1);

        float offset = length - lower.getLength();

        double theta = Math.atan2(upper.getY() - lower.getY(), upper.getX()
                - lower.getX());

        float xPoint = (float) (lower.getX() + offset * Math.cos(theta));
        float yPoint = (float) (lower.getY() + offset * Math.sin(theta));

        return new Point2D.Float(xPoint, yPoint);
    }

    /**
     * returns the angle radians of the path at the specified length.
     * 
     * @param index
     *            segment index
     * @param fraction
     *            segment fraction
     * @return the angle in radians
     */
    public float angleAtLength(int index, float fraction) {
        if (!initialised) {
            initialise();
        }
        if (index < 0 || index >= segmentIndexes.length) {
            return 0f;
        }
        PathSegment seg = segments.get(segmentIndexes[index]);
        float start = seg.getLength();
        float end;
        if (index == segmentIndexes.length - 1) {
            end = pathLength;
        }
        else {
            seg = segments.get(segmentIndexes[index + 1]);
            end = seg.getLength();
        }
        return angleAtLength(start + (end - start) * fraction);
    }

    /**
     * return the angle radians of the path at the specified length.
     * 
     * @param length
     *            The fragment path length
     * @return the angle in radians
     */
    public float angleAtLength(float length) {
        int upperIndex = findUpperIndex(length);
        if (upperIndex == -1) {
            return 0f;
        }

        PathSegment upper = segments.get(upperIndex);

        if (upperIndex == 0) {
            upperIndex = 1;
        }

        PathSegment lower = segments.get(upperIndex - 1);

        return (float) Math.atan2(upper.getY() - lower.getY(), upper.getX()
                - lower.getX());
    }

    /**
     * return index of the path segment that bounds the specified length along
     * the path.
     * 
     * @param length
     *            path length fragment
     * @return The path segment index, or -1 if there is not such segment
     */
    public int findUpperIndex(float length) {
        if (!initialised) {
            initialise();
        }

        if (length < 0 || length > pathLength) {
            return -1;
        }

        int lb = 0;
        int ub = segments.size() - 1;
        while (lb != ub) {
            int curr = lb + ub >> 1;
            PathSegment ps = segments.get(curr);
            if (ps.getLength() >= length) {
                ub = curr;
            }
            else {
                lb = curr + 1;
            }
        }
        for (;;) {
            PathSegment ps = segments.get(ub);
            if (ps.getSegType() != PathIterator.SEG_MOVETO
                    || ub == segments.size() - 1) {
                break;
            }
            ub++;
        }

        int upperIndex = -1;
        int currentIndex = 0;
        int numSegments = segments.size();
        while (upperIndex <= 0 && currentIndex < numSegments) {
            PathSegment ps = segments.get(currentIndex);
            if (ps.getLength() >= length
                    && ps.getSegType() != PathIterator.SEG_MOVETO) {
                upperIndex = currentIndex;
            }
            currentIndex++;
        }
        return upperIndex;
    }

    /**
     * A {@link PathIterator} that returns only the next path segment from
     * another {@link PathIterator}.
     */
    protected static class SingleSegmentPathIterator implements PathIterator {

        /**
         * The path iterator being wrapped.
         */
        protected PathIterator it;

        /**
         * Whether the single segment has been passed.
         */
        protected boolean done;

        /**
         * Whether the generated move command has been returned.
         */
        protected boolean moveDone;

        /**
         * The x coordinate of the next move command.
         */
        protected double x;

        /**
         * The y coordinate of the next move command.
         */
        protected double y;

        /**
         * Sets the path iterator to use and the initial SEG_MOVETO command to
         * return before it.
         */
        public void setPathIterator(PathIterator it, double x, double y) {
            this.it = it;
            this.x = x;
            this.y = y;
            done = false;
            moveDone = false;
        }

        @Override
        public int currentSegment(double[] coords) {
            int type = it.currentSegment(coords);
            if (!moveDone) {
                coords[0] = x;
                coords[1] = y;
                return SEG_MOVETO;
            }
            return type;
        }

        @Override
        public int currentSegment(float[] coords) {
            int type = it.currentSegment(coords);
            if (!moveDone) {
                coords[0] = (float) x;
                coords[1] = (float) y;
                return SEG_MOVETO;
            }
            return type;
        }

        @Override
        public int getWindingRule() {
            return it.getWindingRule();
        }

        @Override
        public boolean isDone() {
            return done || it.isDone();
        }

        @Override
        public void next() {
            if (!done) {
                if (!moveDone) {
                    moveDone = true;
                }
                else {
                    it.next();
                    done = true;
                }
            }
        }
    }

    /**
     * path segment that store segment item
     */
    protected static class PathSegment {

        /**
         * The path segment type.
         */
        protected final int segType;

        /**
         * The x coordinate of the path segment.
         */
        protected float x;

        /**
         * The y coordinate of the path segment.
         */
        protected float y;

        /**
         * The length of the path segment, accumulated from the start.
         */
        protected float length;

        /**
         * The index of the original path segment this flattened segment is a
         * part of.
         */
        protected int index;

        /**
         * Creates a new PathSegment with the specified parameters.
         * 
         * @param segType
         *            The segment type
         * @param x
         *            The x coordinate
         * @param y
         *            The y coordinate
         * @param len
         *            The segment length
         * @param idx
         *            The index of the original path segment this flattened
         *            segment is a part of
         */
        PathSegment(int segType, float x, float y, float len, int idx) {
            this.segType = segType;
            this.x = x;
            this.y = y;
            length = len;
            index = idx;
        }

        /**
         * Returns the segment type.
         */
        public int getSegType() {
            return segType;
        }

        /**
         * Returns the x coordinate of the path segment.
         */
        public float getX() {
            return x;
        }

        /**
         * Sets the x coordinate of the path segment.
         */
        public void setX(float v) {
            x = v;
        }

        /**
         * Returns the y coordinate of the path segment.
         */
        public float getY() {
            return y;
        }

        /**
         * Sets the y coordinate of the path segment.
         */
        public void setY(float v) {
            y = v;
        }

        /**
         * Returns the length of the path segment.
         */
        public float getLength() {
            return length;
        }

        /**
         * Sets the length of the path segment.
         */
        public void setLength(float v) {
            length = v;
        }

        /**
         * Returns the segment index.
         */
        public int getIndex() {
            return index;
        }

        /**
         * Sets the segment index.
         */
        public void setIndex(int v) {
            index = v;
        }
    }
}
