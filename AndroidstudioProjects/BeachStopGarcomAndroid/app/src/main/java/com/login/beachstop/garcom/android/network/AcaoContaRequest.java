package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.Acao;
import com.login.beachstop.garcom.android.models.AcaoConta;
import com.login.beachstop.garcom.android.models.Conta;
import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.Setor;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;
import com.login.beachstop.garcom.android.utils.Utilitarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public class AcaoContaRequest extends ObjectRequest<AcaoConta> {

    public AcaoContaRequest(ResponseListener listener) {
        super(listener);
    }

    public void getAll(List<Setor> setores) {
        String url = String.format("%s%s/empresa/%s/setor/%s", Constantes.URL_WS, new AcaoConta().getServiceName(), Constantes.KEYMOBILE, Utilitarios.joinToString(setores, ","));
        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, url, null);
        this.execute(serverRequest);
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {
        if (serverResponse.isOK()) {
            List<AcaoConta> list = new ArrayList<AcaoConta>();
            AcaoConta acaoConta;
            Conta conta;

            try {
                JSONObject json;
                JSONArray jsonArray = Utilitarios.getAlwaysJsonArray(((JSONObject) serverResponse.getReturnObject()), "");

                for (int i = 0; i < jsonArray.length(); i++) {
                    json = jsonArray.getJSONObject(i).getJSONObject("acaoconta");

                    acaoConta = new AcaoConta();
                    acaoConta.setId(json.getLong("id"));
                    acaoConta.setAcao(json.has("acao") ? new Acao(json.getJSONObject("acao").getLong("id")) : null);
                    acaoConta.setHorarioSolicitacao(json.has("horarioSolicitacao") ? json.getString("horarioSolicitacao") : "");

                    if (json.has("conta")) {
                        json = json.getJSONObject("conta");

                        conta = new Conta();
                        conta.setId(json.getLong("id"));
                        conta.setDataAbertura(json.has("dataAbertura") ? json.getString("dataAbertura") : "");
                        conta.setNumero(json.has("numero") ? json.getLong("numero") : 0l);
                        conta.setClienteId(json.has("cliente") ? json.getJSONObject("cliente").getLong("id") : 0l);

                        acaoConta.setConta(conta);
                    }

                    list.add(acaoConta);
                }
                serverResponse.setReturnObject(list);
            } catch (JSONException e) {
                e.printStackTrace();
                serverResponse.setStatusCode(-1);
            }
        }
    }
}
