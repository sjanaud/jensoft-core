/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.x2d.lang;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * <code>X2DErrorHandler<code>
 * 
 * <p>
 * record error during x2d document parsing
 * </p>
 * 
 * @author Sebastien Janaud
 */
public class X2DSchemaErrorHandler implements ErrorHandler {

    /** error list */
    private List<X2DError> errors;

    /**
     * create x2d error handler
     */
    public X2DSchemaErrorHandler() {
        errors = new ArrayList<X2DError>();
    }

    /**
     * @return the errors
     */
    public List<X2DError> getErrors() {
        return errors;
    }

    /**
     * get the error number
     * 
     * @return error number
     */
    public int countError() {
        return errors.size();
    }

    /**
     * return true if the errors handler has errors, false otherwise
     * 
     * @return true if the errors handler has errors, false otherwise
     */
    public boolean hasErrors() {
        return countError() > 0;
    }

    /**
     * record error
     * 
     * @param exception
     */
    private void recordError(SAXParseException exception) {
        X2DError error = new X2DError();
        error.setColumnNumber(exception.getColumnNumber());
        error.setLineNumber(exception.getLineNumber());
       
        if(exception.getCause() != null){
        	error.setMessage(exception.getMessage()+" with cause :"+exception.getCause().getMessage());	
        }else{
        	error.setMessage(exception.getMessage());	
        }
        if(exception.getException() != null){
        	error.setMessage(exception.getMessage()+" with exception :"+exception.getException().getMessage());	
        }else{
        	error.setMessage(exception.getMessage());	
        }   
        error.setPublicId(exception.getPublicId());
        error.setSystemId(exception.getSystemId());
        errors.add(error);
    }
   

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        recordError(exception);
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        recordError(exception);
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        recordError(exception);
    }

}
