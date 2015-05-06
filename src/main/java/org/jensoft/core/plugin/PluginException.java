/*
 * Copyright (c) JenSoft API
 * This source file is part of JenSoft API, All rights reserved.
 * JENSOFT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.jensoft.core.plugin;

/**
 * <code>PluginException</code> is the superclass of those
 * plugin exceptions that can be thrown during the normal operation of the
 * Java Virtual Machine.
 * <p>
 * A method is not required to declare in its <code>throws</code> clause any subclasses of <code>PluginException</code>
 * that might be thrown during the execution of the method but not caught.
 * 
 * @since 1.0
 * @author sebastien janaud
 */
public class PluginException extends RuntimeException {

    private static final long serialVersionUID = -8761479158358303093L;

    /**
     * Constructs a new runtime exception with <code>null</code> as its
     * detail message. The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public PluginException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     * 
     * @param message
     *            the detail message. The detail message is saved for
     *            later retrieval by the {@link #getMessage()} method.
     */
    public PluginException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt> (which typically contains the class and detail
     * message of <tt>cause</tt>). This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     * 
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
     *            value is
     *            permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     * @since 1.0
     */
    public PluginException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.
     * <p>
     * Note that the detail message associated with <code>cause</code> is <i>not</i> automatically incorporated in this
     * runtime exception's detail message.
     * 
     * @param message
     *            the detail message (which is saved for later retrieval
     *            by the {@link #getMessage()} method).
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt>
     *            value is
     *            permitted, and indicates that the cause is nonexistent or
     *            unknown.)
     * @since 1.0
     */
    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }

}
