/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.lang;

/**
 * <code>X2DError</code> wrap x2d exception
 * @author sebastien janaud
 *
 */
public class X2DError {

    private String publicId;
    private String systemId;
    private int lineNumber;
    private int columnNumber;
    private String message;

    public X2DError() {
    }

    /**
     * @return the publicId
     */
    public String getPublicId() {
        return publicId;
    }

    /**
     * @param publicId
     *            the publicId to set
     */
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    /**
     * @return the systemId
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * @param systemId
     *            the systemId to set
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * @return the lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * @param lineNumber
     *            the lineNumber to set
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * @return the columnNumber
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * @param columnNumber
     *            the columnNumber to set
     */
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "X2DError [publicId=" + publicId + ", systemId=" + systemId
                + ", lineNumber=" + lineNumber + ", columnNumber="
                + columnNumber + ", message=" + message + "]";
    }

}
