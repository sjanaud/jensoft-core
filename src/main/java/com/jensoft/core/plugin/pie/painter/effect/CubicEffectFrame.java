package com.jensoft.core.plugin.pie.painter.effect;

public enum CubicEffectFrame {
    Cubic1(30, 1f, 120, 1f), Cubic2(30, 0.8f, 120, 0.8f), Cubic3(20, 0.6f,
            120, 0.6f), Cubic4(20, 0.4f, 120, 0.4f), Cubic5(20, 0.2f, 120,
            0.2f), Cubic6(20, 0f, 120, 0f),

    Moon1(30, 1f, 30, 1f), Moon2(30, 0.8f, 30, 0.8f), Moon3(30, 0.6f, 30,
            0.6f), Moon4(30, 0.4f, 30, 0.4f),

    Round1(30, 0.2f, 30, 0.2f), Round2(50, 0.2f, 50, 0.2f), Round3(100,
            0.2f, 100, 0.2f), Round4(100, 0.8f, 100, 0.8f);

    /** key start angle */
    private int startAngle;

    /** key start fraction */
    private float startFraction;

    /** key end degree */
    private int endAngle;

    /** key end fraction */
    private float endFraction;

    /** build cubic key */
    private CubicEffectKey keyframe;

    /**
     * create a cubic key frame with given key parameters
     * 
     * @param startAngle
     *            the key start angle
     * @param startFraction
     *            the key start radius fraction
     * @param endAngle
     *            the key end angle
     * @param endFraction
     *            the key end radius fraction
     */
    private CubicEffectFrame(int startAngle, float startFraction, int endAngle,
            float endFraction) {
        this.startAngle = startAngle;
        this.startFraction = startFraction;
        this.endAngle = endAngle;
        this.endFraction = endFraction;
        keyframe = new CubicEffectKey(startAngle, startFraction, endAngle,
                                      endFraction);
    }

    /**
     * get the cubic built key
     * 
     * @return cubic built key
     */
    public CubicEffectKey getKeyFrame() {
        return keyframe;
    }

    /**
     * @return the startAngle
     */
    public int getStartAngle() {
        return startAngle;
    }

    /**
     * @return the startFraction
     */
    public float getStartFraction() {
        return startFraction;
    }

    /**
     * @return the endAngle
     */
    public int getEndAngle() {
        return endAngle;
    }

    /**
     * @return the endFraction
     */
    public float getEndFraction() {
        return endFraction;
    }

}
