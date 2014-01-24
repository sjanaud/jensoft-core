/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.jensoft.core.x2d.lang;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.jensoft.core.x2d.X2DException;

/**
 * <code>X2DSchema</code>
 * 
 * @author Sebastien Janaud
 */
public class X2DSchema {

    /** private static x2d instance */
    private static X2DSchema schema;

    /** x2d schema */
    private Schema x2dSchema;

    private X2DSchema() throws X2DException {
        try {
            String lang = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(lang);
            x2dSchema = factory.newSchema(new StreamSource(X2DSchema.class.getResourceAsStream("x2d.xsd")));
        }
        catch (SAXException e) {
            throw new X2DException("X2D initialization Error", e);
        }
    }

    /**
     * return the X2D singleton instance
     * 
     * @return x2d
     */
    public static X2DSchema getInstance() throws X2DException {
        if (schema == null) {
            schema = new X2DSchema();
        }
        return schema;
    }

    /**
     * get x2d schema
     * 
     * @return x2d schema
     */
    public Schema getX2DSchema() {
        return x2dSchema;
    }

    /**
     * valid the specified XML view file and call back warning, fatal and error in x2d error handler
     * 
     * @param x2dViewFile
     *            the x2d file
     * @throws SAXException
     * @throws IOException
     */
    public static void validX2D(File x2dViewFile, X2DSchemaErrorHandler handler) throws X2DException {
      
            try {
				Source source = new StreamSource(x2dViewFile);
				Validator validator = getInstance().getX2DSchema().newValidator();
				validator.setErrorHandler(handler);
				validator.validate(source);
			} catch (SAXException e) {
				throw new X2DException("valid failed with exception : "+e.getMessage());
			} catch (IOException e) {
				throw new X2DException("valid failed with exception : "+e.getMessage());
			}
      

    }

    /**
     * valid the specified XML view file and call back warning, fatal and error in x2d error handler
     * 
     * @param x2dInputStream
     *            the x2d input stream
     * @throws SAXException
     * @throws IOException
     */
    public static void validX2D(InputStream x2dInputStream, X2DSchemaErrorHandler handler) throws X2DException {
        try {
            Source source = new StreamSource(x2dInputStream);
            Validator validator = getInstance().getX2DSchema().newValidator();
            validator.setErrorHandler(handler);
            validator.validate(source);
        } catch (SAXException e) {
			throw new X2DException("valid failed with exception : "+e.getMessage());
		} catch (IOException e) {
			throw new X2DException("valid failed with exception : "+e.getMessage());
		}

    }

    /**
     * valid the specified XML view source and call back warning, fatal and error in x2d error handler
     * 
     * @param xmlSource
     *            the string source
     * @throws SAXException
     * @throws IOException
     */
    public static void validX2D(String xmlSource, X2DSchemaErrorHandler handler) throws X2DException {
        try {
            Source source = new StreamSource(new StringReader(xmlSource));
            Validator validator = getInstance().getX2DSchema().newValidator();
            validator.setErrorHandler(handler);
            validator.validate(source);
        } catch (SAXException e) {
			throw new X2DException("valid failed with exception : "+e.getMessage());
		} catch (IOException e) {
			throw new X2DException("valid failed with exception : "+e.getMessage());
		}
    }
    
    /**
     * valid the specified XML view source and call back warning, fatal and error in x2d error handler
     * 
     * @param xmlDocument
     *            the xml document source
     * @throws SAXException
     * @throws IOException
     */
    public static void validX2D(Document xmlDocument, X2DSchemaErrorHandler handler) throws X2DException {
        try {
            Source source = new DOMSource(xmlDocument.getDocumentElement());
            Validator validator = getInstance().getX2DSchema().newValidator();
            validator.setErrorHandler(handler);
            validator.validate(source);
        } catch (SAXException e) {
			throw new X2DException("valid failed with exception : "+e.getMessage());
		} catch (IOException e) {
			throw new X2DException("valid failed with exception : "+e.getMessage());
		}
    }

    /**
     * parse the x2d source
     * 
     * @param xmlSource
     *            the xml string source
     * @return source as XML {@link Document}
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Document parseX2D(String xmlSource) throws ParserConfigurationException, SAXException,
            IOException {
        Document document = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        document = builder.parse(new InputSource(new StringReader(xmlSource)));

        return document;
    }

    /**
     * parse the x2d XML file
     * 
     * @param x2dFile
     * @return x2d document
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Document parseX2D(File x2dFile) throws ParserConfigurationException, SAXException, IOException {
        Document document = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(x2dFile);

        return document;
    }

    /**
     * parse the x2d XML file
     * 
     * @param x2dInputStream
     * @return x2d document
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Document parseX2D(InputStream x2dInputStream) throws ParserConfigurationException,
            SAXException, IOException {
        Document document = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(x2dInputStream);
        return document;
    }

}
