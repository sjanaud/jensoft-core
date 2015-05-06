/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d;

import java.util.List;

import org.jensoft.core.x2d.lang.X2DError;

/**
 * <code>X2DException</code>
 * 
 * @author Sebastien Janaud
 */
public class X2DException extends Exception {

    /** UUID */
    private static final long serialVersionUID = -8922423020869370289L;

    /** x2d errors */
    private List<X2DError> errors;

    /**
     * create empty jet exception
     */
    public X2DException() {
    }

    /**
     * @param message
     * @param cause
     */
    public X2DException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public X2DException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public X2DException(Throwable cause) {
        super(cause);
    }

    /**
     * @return the errors
     */
    public List<X2DError> getErrors() {
        return errors;
    }

    /**
     * @param errors
     *            the errors to set
     */
    public void setErrors(List<X2DError> errors) {
        this.errors = errors;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(super.getMessage() + "\n");
        stringBuffer.append("X2D errors :{");
        if (errors != null) {
            for (X2DError error : errors) {
                stringBuffer.append(error.toString());
            }
        }
        stringBuffer.append("}");
        if(getCause() != null){
        	 stringBuffer.append(" with cause : "+getCause());
        }
        if(super.getCause() != null){
       	 stringBuffer.append(" with root cause : "+super.getCause());
       }
        return stringBuffer.toString();
    }

}
