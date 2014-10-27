package com.login.beachstop.android.network.http;

import com.login.beachstop.android.models.ServerResponse;

/**
 * Created by Argus on 23/10/2014.
 */
public interface ResponseListener {

    void onResult(ServerResponse serverResponse);
}
