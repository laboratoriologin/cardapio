package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.Base;
import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.network.http.HttpTask;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;


import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public abstract class ObjectRequest<T extends Base> extends HttpTask {

    public ObjectRequest(ResponseListener listener) {
        this.listener = listener;
    }

    public void post(T obj) {

        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, getUrlPost(obj), createParameters(obj));

        this.execute(serverRequest);
    }

    public void put(T obj) {

        ServerRequest serverRequest = new ServerRequest(ServerRequest.PUT, getUrlPut(obj), createParameters(obj));

        this.execute(serverRequest);
    }

    public void get(T obj) {

        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, getUrlGet(obj), createParameters(obj));

        this.execute(serverRequest);
    }

    protected List<NameValuePair> createParameters(T object) {
        return new ArrayList<NameValuePair>();
    }

    protected abstract void handleResponse(ServerResponse serverResponse);

    protected String getUrlPost(T object) {

        return Constantes.URL_WS + "/" + object.getServiceName();

    }

    protected String getUrlPut(T object) {

        return Constantes.URL_WS + "/" + object.getServiceName() + "/" + (object.getId() == null ? "" : object.getId());

    }

    protected String getUrlGet(T object) {

        return Constantes.URL_WS + "/" + object.getServiceName() + "/" + (object.getId() == null ? "" : object.getId());

    }

    @Override
    protected void onPostExecute(ServerResponse result) {
        try {
            handleResponse(result);
            listener.onResult(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}