package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.Acao;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.network.http.ResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public class AcaoRequest extends ObjectRequest<Acao> {

    public AcaoRequest(ResponseListener listener) {
        super(listener);
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {
        if (serverResponse.isOK()) {
            List<Acao> acoes = new ArrayList<Acao>();
            Acao acao;

            try {
                JSONObject jsonAcao;
                JSONArray jsonArrayAcao = ((JSONObject) serverResponse.getReturnObject()).getJSONArray("");

                for (int i = 0; i < jsonArrayAcao.length(); i++) {
                    jsonAcao = jsonArrayAcao.getJSONObject(i).getJSONObject("acao");

                    acao = new Acao();
                    acao.setId(jsonAcao.getLong("id"));
                    acao.setDescricao(jsonAcao.getString("descricao"));

                    acoes.add(acao);
                }
                serverResponse.setReturnObject(acoes);
            } catch (JSONException e) {
                e.printStackTrace();
                serverResponse.setStatusCode(-1);
            }
        }
    }
}
