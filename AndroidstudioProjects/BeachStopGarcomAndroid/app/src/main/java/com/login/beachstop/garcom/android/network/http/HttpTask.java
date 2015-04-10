package com.login.beachstop.garcom.android.network.http;


import android.os.AsyncTask;

import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;

/**
 * Created by Argus on 23/10/2014.
 */
public class HttpTask extends AsyncTask<ServerRequest, Void, ServerResponse> {

    protected ResponseListener listener;

    @Override
    protected ServerResponse doInBackground(ServerRequest... params) {

        ServerRequest serverRequest = params == null || params.length == 0 ? null : params[0];

        ServerResponse serverResponse;

        switch (serverRequest.getTypeMethod()) {

            case ServerRequest.GET:
                serverResponse = Http.getInstance().get(serverRequest.getUrlBase());
                break;
            case ServerRequest.POST:
                serverResponse = Http.getInstance().post(serverRequest.getUrlBase(), serverRequest.getParams());
                break;
            case ServerRequest.PUT:
                serverResponse = Http.getInstance().put(serverRequest.getUrlBase(), serverRequest.getParams());
                break;
            case ServerRequest.DELETE:
                serverResponse = new ServerResponse(500, null);
                break;
            default:
                serverResponse = new ServerResponse(500, null);
                break;
        }

        return serverResponse;
    }
}
