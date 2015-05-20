package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.Pausa;
import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.Usuario;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;
import com.login.beachstop.garcom.android.utils.Utilitarios;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public class PausaRequest extends ObjectRequest<Pausa> {

    public PausaRequest(ResponseListener listener) {
        super(listener);
    }

    public void abrirPausa(Usuario usuario) {
        String url = String.format("%s/%s/%s/%s", Constantes.URL_WS, new Pausa().getServiceName(), "abrirpausa", usuario.getId());
        Pausa pausa = new Pausa();
        pausa.setUsuarioId(usuario.getId());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, url, createParameters(pausa));
        this.execute(serverRequest);
    }

    public void getPausa(Usuario usuario) {
        String url = String.format("%s/%s/%s/%s", Constantes.URL_WS, new Pausa().getServiceName(), "getempausa", usuario.getId());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, url, null);
        this.execute(serverRequest);
    }

    public void fecharPausa(Usuario usuario) {
        String url = String.format("%s/%s/%s/%s", Constantes.URL_WS, new Pausa().getServiceName(), "fecharpausa", usuario.getId());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.PUT, url, createParameters(new Pausa()));
        this.execute(serverRequest);
    }

    @Override
    protected List<NameValuePair> createParameters(Pausa pausa) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id", !Utilitarios.isEmpty(pausa.getId()) ? pausa.getId().toString() : ""));
        nameValuePairs.add(new BasicNameValuePair("usuario", !Utilitarios.isEmpty(pausa.getUsuarioId()) ? pausa.getUsuarioId().toString() : ""));
        nameValuePairs.add(new BasicNameValuePair("horariofinal", !Utilitarios.isEmpty(pausa.getHorarioFinal()) ? pausa.getHorarioFinal() : ""));
        nameValuePairs.add(new BasicNameValuePair("horarioinicial", !Utilitarios.isEmpty(pausa.getHorarioInicial()) ? pausa.getHorarioInicial() : ""));
        return nameValuePairs;
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {
        if (serverResponse.isOK()) {
            try {

                    JSONObject jsonPausa = ((JSONObject) serverResponse.getReturnObject()).getJSONObject("pausa");

                    Pausa pausa = new Pausa();
                    pausa.setId(jsonPausa.getLong("id"));
                    pausa.setUsuarioId(jsonPausa.has("usuario") ? jsonPausa.getJSONObject("usuario").getLong("id") : null);
                    pausa.setHorarioInicial(jsonPausa.has("strHorarioInicial") ? jsonPausa.getString("strHorarioInicial") : "");
                    pausa.setHorarioFinal(jsonPausa.has("strHorarioFinal") ? jsonPausa.getString("strHorarioFinal") : "");
                    pausa.setHorarioDiff((jsonPausa.has("diffMinuto") ? jsonPausa.getLong("diffMinuto") : "").toString());

                    serverResponse.setReturnObject(pausa);

            } catch (JSONException e) {
                e.printStackTrace();
                serverResponse.setStatusCode(-1);
            }
        }
    }
}