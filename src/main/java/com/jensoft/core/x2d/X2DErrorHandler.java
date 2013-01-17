/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class X2DErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        System.out.println("X2D ERROR XSD WARNING : " + exception.getMessage());
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println("X2D XSD FATAL : " + exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        System.out.println("X2D XSD ERROR : " + exception.getMessage());
    }

    public void errorIllegalArgumentException(IllegalArgumentException exception) throws SAXException {
        System.out.println("X2D IllegalArgumentException ERROR : " + exception.getMessage());
    }

    public void errorParserConfigurationException(ParserConfigurationException exception) throws SAXException {
        System.out.println("X2D ParserConfigurationException ERROR : " + exception.getMessage());
    }

    public void errorSAXException(SAXException exception) throws SAXException {
        System.out.println("X2D SAX ERROR : " + exception.getMessage());
    }

    public void errorIOException(IOException exception) throws SAXException {
        System.out.println("X2D IO ERROR : " + exception.getMessage());
    }

}
