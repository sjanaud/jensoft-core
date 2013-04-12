/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.plugin.function.analysis;

/**
 * MathException
 */
public class AnalysisException extends Exception {

    /** serial version UID */
    private static final long serialVersionUID = 2408507244771260618L;

    public AnalysisException() {
        super();
    }

    public AnalysisException(String message, Throwable cause) {
        super(message, cause);

    }

    public AnalysisException(String message) {
        super(message);

    }

    public AnalysisException(Throwable cause) {
        super(cause);

    }

}
