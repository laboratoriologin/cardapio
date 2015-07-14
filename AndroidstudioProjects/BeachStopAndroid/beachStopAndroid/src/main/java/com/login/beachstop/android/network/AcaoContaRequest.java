package com.login.beachstop.android.network;

import com.login.beachstop.android.models.AcaoConta;
import com.login.beachstop.android.models.Conta;
import com.login.beachstop.android.models.ServerRequest;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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

    public void chamarGgarcom(AcaoConta acaoConta) {
        String url = Constantes.URL_WS + "/" + new AcaoConta().getServiceName() + "/chamargarcom";
        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, url, createParameters(acaoConta));
        this.execute(serverRequest);
    }

    public void fecharConta(AcaoConta acaoConta) {
        String url = Constantes.URL_WS + "/" + new AcaoConta().getServiceName() + "/fercharconta";
        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, url, createParameters(acaoConta));
        this.execute(serverRequest);
    }

    public void solicitarAutorizacao(String numero) {
        String url = String.format("%s/%s/autorizacao/%s", Constantes.URL_WS, new AcaoConta().getServiceName(), numero);
        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, url, new ArrayList<NameValuePair>());
        this.execute(serverRequest);
    }

    public void isAuthorized(AcaoConta acaoConta) {
        String url = String.format("%s/%s/isautorizado/%s", Constantes.URL_WS, new AcaoConta().getServiceName(), acaoConta.getId());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, url, null);
        this.execute(serverRequest);
    }

    @Override
    protected List<NameValuePair> createParameters(AcaoConta acaoContao) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("acao", acaoContao.getAcaoId().toString()));
        nameValuePairs.add(new BasicNameValuePair("conta.id", acaoContao.getConta().getSistemaId().toString()));

        return nameValuePairs;
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {
        if (serverResponse.isOK()) {
            AcaoConta acaoConta;
            JSONObject jsonObject;

            try {
                if (((JSONObject) serverResponse.getReturnObject()).has("acaoconta")) {
                    jsonObject = ((JSONObject) serverResponse.getReturnObject()).getJSONObject("acaoconta");

                    acaoConta = new AcaoConta();
                    acaoConta.setId(jsonObject.has("id") ? jsonObject.getLong("id") : null);
                    acaoConta.setAcaoId(jsonObject.has("acao") ? jsonObject.getJSONObject("acao").getLong("id") : null);
                    acaoConta.setContaId(jsonObject.has("conta") ? jsonObject.getJSONObject("conta").getLong("id") : null);
                    acaoConta.setNumero(jsonObject.has("numero") ? jsonObject.getLong("numero") : null);
                    acaoConta.setIsAutorizado(jsonObject.has("autorizacao") ? jsonObject.getBoolean("autorizacao") : false);
                    acaoConta.setConta(new Conta());

                    serverResponse.setReturnObject(acaoConta);

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