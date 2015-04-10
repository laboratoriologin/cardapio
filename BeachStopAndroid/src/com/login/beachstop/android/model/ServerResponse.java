package com.login.beachstop.android.model;

import android.util.SparseArray;

import com.login.beachstop.android.util.Constantes;

public class ServerResponse {

	public static final int SC_OK = 200;
	public static final int SC_BAD_REQUEST = 400;
	public static final int SC_PRECONDITION_FAILED = 412;
	public static final int SC_INTERNAL_SERVER_ERROR = 500;
	public static final int SC_NOT_NET = 0;

	private SparseArray<String> hasMapErro;

	private int statusCode;
	private Object returnObject;
	private String msgPreConditionFail = "";

	public ServerResponse() {
		configMsg();
	}

	public ServerResponse(int statusCode, Object returnObject) {
		configMsg();
		this.statusCode = statusCode;
		this.returnObject = returnObject;
	}

	private void configMsg() {
		this.hasMapErro = new SparseArray<String>();
		this.hasMapErro.put(SC_BAD_REQUEST, Constantes.MSG_ERRO_VALIDACAO_SISTEMA);
		this.hasMapErro.put(SC_OK, Constantes.MSG_OK);
		this.hasMapErro.put(SC_INTERNAL_SERVER_ERROR, Constantes.MSG_ERRO_GRAVE_SISTEMA);
		this.hasMapErro.put(SC_NOT_NET, Constantes.MSG_ERRO_NET);
		this.hasMapErro.put(SC_PRECONDITION_FAILED, this.msgPreConditionFail);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Object getReturnObject() {
		return returnObject;
	}

	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	/**
	 * @return the msgPreConditionFail
	 */
	public String getMsgPreConditionFail() {
		return msgPreConditionFail;
	}

	/**
	 * @param msgPreConditionFail
	 *            the msgPreConditionFail to set
	 */
	public void setMsgPreConditionFail(String msgPreConditionFail) {
		this.msgPreConditionFail = msgPreConditionFail;
		this.hasMapErro.setValueAt(this.hasMapErro.indexOfKey(SC_PRECONDITION_FAILED), this.msgPreConditionFail);
	}

	@SuppressWarnings("static-access")
	public Boolean isOK() {
		return this.SC_OK == this.statusCode;
	}

	public String getMsgErro() {
		return this.hasMapErro.get(this.statusCode);
	}
}
