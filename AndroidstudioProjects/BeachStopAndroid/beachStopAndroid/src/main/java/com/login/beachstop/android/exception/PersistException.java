package com.login.beachstop.android.exception;

@SuppressWarnings("serial")
public class PersistException extends Exception {

	public PersistException(Throwable ex) {
		super(ex);
	}
	
	public PersistException(String msg) {
		super(msg);
	}
}
