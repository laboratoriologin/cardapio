package com.login.beachstop.android.managers.sqlite.exception;

/**
 * Created by Argus on 29/10/2014.
 */
@SuppressWarnings("serial")
public class PersistException extends Exception {

    public PersistException(Throwable ex) {
        super(ex);
    }

    public PersistException(String msg) {
        super(msg);
    }
}

