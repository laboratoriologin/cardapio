package com.login.beachstop.android.network;

import com.login.beachstop.android.model.Base;
import com.login.beachstop.android.model.ServerRequest;
import com.login.beachstop.android.model.ServerResponse;
import com.login.beachstop.android.network.http.HttpTask;
import com.login.beachstop.android.utils.Constantes;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public abstract class ObjectRequest<T extends Base> extends HttpTask {

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

        return Constantes.URL_WS + "/" + object.getServiceName() + "/" + object.getId();

    }

    protected String getUrlGet(T object) {

        return Constantes.URL_WS + "/" + object.getServiceName() + "/" + object.getId();

    }

    @Override
    protected void onPostExecute(ServerResponse result) {
        try {
            handleResponse(result);
            observable.observe(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
