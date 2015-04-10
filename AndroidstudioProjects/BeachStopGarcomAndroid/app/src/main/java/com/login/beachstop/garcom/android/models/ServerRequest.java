package com.login.beachstop.garcom.android.models;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by Argus on 23/10/2014.
 */
public class ServerRequest {

    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int GET = 3;
    public static final int DELETE = 4;

    private int typeMethod;
    private String urlBase;
    private List<NameValuePair> params;
    private Class clazz;

    public ServerRequest(int typeMethod, String urlBase, List<NameValuePair> params) {
        this.typeMethod = typeMethod;
        this.urlBase = urlBase;
        this.params = params;
    }

    public int getTypeMethod() {
        return typeMethod;
    }

    public void setTypeMethod(int typeMethod) {
        this.typeMethod = typeMethod;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }

    public List<NameValuePair> getParams() {
        return params;
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }
}
