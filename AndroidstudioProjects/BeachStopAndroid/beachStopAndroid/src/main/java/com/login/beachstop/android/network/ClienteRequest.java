package com.login.beachstop.android.network;

import com.login.beachstop.android.models.Cliente;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.http.ResponseListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public class ClienteRequest extends ObjectRequest<Cliente> {

    public ClienteRequest(ResponseListener listener) {
        super(listener);
    }

    @Override
    protected List<NameValuePair> createParameters(Cliente cliente) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("celular", cliente.getCelular()));
        nameValuePairs.add(new BasicNameValuePair("dataNascimento", cliente.getDataNascimento()));
        nameValuePairs.add(new BasicNameValuePair("email", cliente.getEmail()));
        nameValuePairs.add(new BasicNameValuePair("nome", cliente.getNome()));
        nameValuePairs.add(new BasicNameValuePair("tokenAndroid", cliente.getToken()));
        return nameValuePairs;

    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if(serverResponse.isOK()){

        }

    }


}
