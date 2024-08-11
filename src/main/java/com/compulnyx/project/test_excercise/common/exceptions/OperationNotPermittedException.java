package com.compulnyx.project.test_excercise.common.exceptions;/*
*
@author ameda
@project Books
*
*/

public class OperationNotPermittedException extends RuntimeException{
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public OperationNotPermittedException(String message) {
        super(message);
    }
}
