package com.login.beachstop.android.network;

import com.login.beachstop.android.models.Conta;
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
public class ContaRequest extends ObjectRequest<Conta> {

    public ContaRequest(ResponseListener listener) {
        super(listener);
    }

    @Override
    protected List<NameValuePair> createParameters(Conta conta) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("cliente.id", conta.getClienteId() != null ? conta.getClienteId().toString() : ""));
        nameValuePairs.add(new BasicNameValuePair("numero", conta.getNumero().toString()));
        nameValuePairs.add(new BasicNameValuePair("tipoconta", conta.getTipoConta().toString()));
        return nameValuePairs;

    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {

            Conta conta;
            JSONObject jsonObject;

            try {

                if (((JSONObject) serverResponse.getReturnObject()).has("conta")) {

                    jsonObject = ((JSONObject) serverResponse.getReturnObject()).getJSONObject("conta");

                    conta = new Conta();
                    conta.setId(jsonObject.has("id") ? jsonObject.getLong("id") : null);
                    conta.setClienteId(jsonObject.has("cliente") ? jsonObject.getJSONObject("cliente").getLong("id") : null);
                    conta.setTipoConta(Long.getLong((jsonObject.has("tipoconta") ? jsonObject.getBoolean("tipoconta") : "").toString()));
                    conta.setDataAbertura((jsonObject.has("dataabertura") ? jsonObject.getString("dataabertura") : "").toString());
                    conta.setDataFechamento((jsonObject.has("datafechamento") ? jsonObject.getString("datafechamento") : "").toString());
                    conta.setNumero(jsonObject.has("numero") ? jsonObject.getLong("numero") : null);

                    serverResponse.setReturnObject(conta);

                } else {

                    serverResponse.setReturnObject(null);

                }


            } catch (JSONException e) {

                e.printStackTrace();
                serverResponse.setStatusCode(-1);
            }

        }
    }


}
