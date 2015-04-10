package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.Setor;
import com.login.beachstop.garcom.android.network.http.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public class SetorRequest extends ObjectRequest<Setor> {

    public SetorRequest(ResponseListener listener) {
        super(listener);
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {
        if (serverResponse.isOK()) {
            List<Setor> setores = new ArrayList<Setor>();
            Setor setor;

            try {
                JSONObject jsonSetor;
                JSONArray jsonArraySetor = ((JSONObject) serverResponse.getReturnObject()).getJSONArray("");

                for (int i = 0; i < jsonArraySetor.length(); i++) {
                    jsonSetor = jsonArraySetor.getJSONObject(i).getJSONObject("setor");

                    setor = new Setor();
                    setor.setId(jsonSetor.getLong("id"));
                    setor.setDescricao(jsonSetor.getString("descricao"));

                    setores.add(setor);
                }
                serverResponse.setReturnObject(setores);
            } catch (JSONException e) {
                e.printStackTrace();
                serverResponse.setStatusCode(-1);
            }
        }
    }
}
