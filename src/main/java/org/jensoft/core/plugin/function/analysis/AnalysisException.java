/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin.function.analysis;

/**
 * <code>AnalysisException</code>
 * 
 * @author sebastien janaud
 */
public class AnalysisException extends Exception {

	/** uid */
	private static final long serialVersionUID = -8953469060102183297L;

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
