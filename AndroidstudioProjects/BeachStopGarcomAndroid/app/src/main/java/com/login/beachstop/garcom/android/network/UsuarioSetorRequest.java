package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.Setor;
import com.login.beachstop.garcom.android.models.Usuario;
import com.login.beachstop.garcom.android.models.UsuarioSetor;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;
import com.login.beachstop.garcom.android.utils.Utilitarios;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public class UsuarioSetorRequest extends ObjectRequest<UsuarioSetor> {

    public UsuarioSetorRequest(ResponseListener listener) {
        super(listener);
    }

    public void get(Usuario usuario) {
        String url = String.format("%s/%s/usuario/%d", Constantes.URL_WS, new UsuarioSetor().getServiceName(), usuario.getId());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, url, null);
        this.execute(serverRequest);
    }

    public void save(UsuarioSetor usuarioSetor) {
        String url = String.format("%s/%s/save", Constantes.URL_WS, new UsuarioSetor().getServiceName());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, url, createParameters(usuarioSetor));
        this.execute(serverRequest);
    }

    @Override
    protected List<NameValuePair> createParameters(UsuarioSetor usuarioSetor) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("usuario", usuarioSetor.getUsuarioId().toString()));

        Setor setor;

        for (int i = 0; i < usuarioSetor.getSetores().size(); i++) {
            setor = usuarioSetor.getSetores().get(i);
            nameValuePairs.add(new BasicNameValuePair("setores[" + i + "].id", setor.getId().toString()));
            nameValuePairs.add(new BasicNameValuePair("setores[" + i + "].descricao", setor.getDescricao()));
        }
        return nameValuePairs;
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {
        if (serverResponse.isOK()) {
            List<UsuarioSetor> list = new ArrayList<UsuarioSetor>();
            UsuarioSetor usuarioSetor;

            try {
                JSONObject json;
                JSONArray jsonArray = Utilitarios.getAlwaysJsonArray(((JSONObject) serverResponse.getReturnObject()), "");

                for (int i = 0; i < jsonArray.length(); i++) {
                    json = jsonArray.getJSONObject(i).getJSONObject("usuariosetor");

                    usuarioSetor = new UsuarioSetor();
                    usuarioSetor.setId(json.getLong("id"));
                    usuarioSetor.setSetorId(json.getJSONObject("setor").getLong("id"));
                    usuarioSetor.setUsuarioId(json.getJSONObject("usuario").getLong("id"));

                    list.add(usuarioSetor);
                }
                serverResponse.setReturnObject(list);
            } catch (JSONException e) {
                e.printStackTrace();
                serverResponse.setStatusCode(-1);
            }
        }
    }
}
