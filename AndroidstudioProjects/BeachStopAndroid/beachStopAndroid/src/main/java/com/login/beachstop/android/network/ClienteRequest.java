package com.login.beachstop.android.network;

import com.login.beachstop.android.models.Cliente;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.http.ResponseListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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
        nameValuePairs.add(new BasicNameValuePair("datanascimento", cliente.getDataNascimento()));
        nameValuePairs.add(new BasicNameValuePair("email", cliente.getEmail()));
        nameValuePairs.add(new BasicNameValuePair("nome", cliente.getNome()));
        nameValuePairs.add(new BasicNameValuePair("tokenandroid", cliente.getToken()));
        return nameValuePairs;

    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {

            Cliente cliente;
            JSONObject jsonCliente;

            try {

                if (((JSONObject) serverResponse.getReturnObject()).has("cliente")) {

                    jsonCliente = ((JSONObject) serverResponse.getReturnObject()).getJSONObject("cliente");

                    cliente = new Cliente();
                    cliente.setId(jsonCliente.has("id") ? jsonCliente.getLong("id") : null);
                    cliente.setCelular((jsonCliente.has("celular") ? jsonCliente.getLong("celular") : "").toString());
                    cliente.setDataNascimento((jsonCliente.has("dataNascimento") ? jsonCliente.getString("dataNascimento") : "").toString());
                    cliente.setEmail(jsonCliente.has("email") ? jsonCliente.getString("email") : "");
                    cliente.setNome(jsonCliente.has("nome") ? jsonCliente.getString("nome") : "");

                    serverResponse.setReturnObject(cliente);

                } else {

                    serverResponse.setReturnObject(null);

                }


            } catch (JSONException e) {

                e.printStackTrace();
                serverResponse.setStatusCode(-1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
