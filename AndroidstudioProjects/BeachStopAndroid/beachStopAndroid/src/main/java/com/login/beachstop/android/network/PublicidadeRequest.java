package com.login.beachstop.android.network;

import com.login.beachstop.android.models.Publicidade;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.http.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class PublicidadeRequest extends ObjectRequest<Publicidade> {

    public PublicidadeRequest(ResponseListener listener) {
        super(listener);
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {

            List<Publicidade> list = new ArrayList<Publicidade>();
            Publicidade publicidade;

            try {

                JSONObject json;
                JSONArray jsonArray = ((JSONObject) serverResponse.getReturnObject()).getJSONArray("");

                for (int i = 0; i < jsonArray.length(); i++) {

                    json = jsonArray.getJSONObject(i).getJSONObject("publicidade");

                    publicidade = new Publicidade();
                    publicidade.setId(json.has("id") ? json.getLong("id") : null);
                    publicidade.setDescricao(json.has("descricao") ? json.getString("descricao") : "");
                    publicidade.setImagem(json.has("imagem") ? json.getString("imagem") : "");
                    publicidade.setLink(json.has("link") ? json.getString("link") : "");
                    publicidade.setNome(json.has("nome") ? json.getString("nome") : "");

                    list.add(publicidade);
                }


                serverResponse.setReturnObject(list);


            } catch (JSONException e) {

                e.printStackTrace();
                serverResponse.setStatusCode(-1);

            }

        }

    }
    
}
