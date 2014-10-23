/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.legend.title;

/**
 * <code>LegendConstraints</code> define the constraints associated to the legend position
 * 
 * @see LegendPlugin
 * @see Legend
 * @author Sebastien Janaud
 */
public class LegendConstraints {

    /** legend depth fraction */
    private float depth;

    /** legend position */
    private LegendPosition position;

    /** legend alignment */
    private LegendAlignment alignment = LegendAlignment.Middle;

    /**
     * defines legend part position
     */
    public enum LegendPosition {
        South("south"), North("north"), West("west"), East("east");

        private String legendPosition;

        private LegendPosition(String position) {
            legendPosition = position;
        }

        /**
         * @return the legendPosition
         */
        public String getLegendPosition() {
            return legendPosition;
        }

        public static LegendPosition parse(String position) {
            if (South.getLegendPosition().equalsIgnoreCase(position)) {
                return South;
            }
            if (North.getLegendPosition().equalsIgnoreCase(position)) {
                return North;
            }
            if (West.getLegendPosition().equalsIgnoreCase(position)) {
                return West;
            }
            if (East.getLegendPosition().equalsIgnoreCase(position)) {
                return East;
            }
            return null;
        }

    };

    /**
     * defines legend alignment
     */
    public enum LegendAlignment {
        Left("left"), Middle("middle"), Rigth("right");

        private String legendAlignment;

        private LegendAlignment(String alignment) {
            legendAlignment = alignment;
        }

        /**
         * @return the legendAlignment
         */
        public String getLegendAlignment() {
            return legendAlignment;
        }

        public static LegendAlignment parse(String alignment) {
            if (Left.getLegendAlignment().equalsIgnoreCase(alignment)) {
                return Left;
            }
            if (Middle.getLegendAlignment().equalsIgnoreCase(alignment)) {
                return Middle;
            }
            if (Rigth.getLegendAlignment().equalsIgnoreCase(alignment)) {
                return Rigth;
            }
            return null;
        }
    };

    /**
     * define the constraints with the specified position, depth and alignment
     * 
     * @param position
     * @param depth
     * @param alignment
     */
    public LegendConstraints(LegendPosition position, float depth,
            LegendAlignment alignment) {
        setPosition(position);
        setDepth(depth);
        setAlignement(alignment);

    }

    /**
     * get legend position
     * 
     * @return legend position
     */
    public LegendPosition getPosition() {
        return position;
    }

    /**
     * set legend position
     * 
     * @param position
     *            the position to set
     */
    public void setPosition(LegendPosition position) {
        this.position = position;
    }

    /**
     * set legend depth
     * 
     * @param depth
     *            the depth to set
     */
    public void setDepth(float depth) {
        if (depth < 0 || depth > 1) {
            throw new IllegalArgumentException(
                                               "Illegal depth, supply value [0...1]");
        }
        this.depth = depth;
    }

    /**
     * get legend depth
     * 
     * @return legend depth
     */
    public float getDepth() {
        return depth;
    }

    /**
     * get legend alignment
     * 
     * @return legend alignment
     */
    public LegendAlignment getAlignement() {
        return alignment;
    }

    /**
     * set legend alignment
     * 
     * @param alignement
     */
    public void setAlignement(LegendAlignment alignement) {
        alignment = alignement;
    }

}
