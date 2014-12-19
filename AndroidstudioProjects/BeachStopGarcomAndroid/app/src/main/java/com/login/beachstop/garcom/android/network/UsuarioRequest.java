package com.login.beachstop.garcom.android.network;

import android.text.TextUtils;

import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.Usuario;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;
import com.login.beachstop.garcom.android.utils.CryptoUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 18/12/2014.
 */
public class UsuarioRequest extends ObjectRequest<Usuario> {

    public UsuarioRequest(ResponseListener listener) {
        super(listener);
    }

    public void logar(Usuario usuario) {
        String urlLogar = Constantes.URL_WS + "/" + usuario.getServiceName() + "/login";
        try {
            usuario.setSenha(CryptoUtil.gerarHash(usuario.getSenha(), "MD5"));
            ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, urlLogar, createParameters(usuario));
            this.execute(serverRequest);
        } catch (Exception e) {
            e.printStackTrace();
            ServerResponse sr = new ServerResponse();
            sr.setStatusCode(-1);
            sr.setMsgPreConditionFail("Erro na senha!");
            listener.onResult(sr);
        }
    }

    public void LembrarSenha(Usuario usuario) {
        String url = Constantes.URL_WS + "/" + usuario.getServiceName() + "/enviarEmail";
        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, url, createParameters(usuario));
        this.execute(serverRequest);
    }

    @Override
    protected List<NameValuePair> createParameters(Usuario usuario) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        if (TextUtils.isEmpty(usuario.getLogin())) {
            nameValuePairs.add(new BasicNameValuePair("login", usuario.getLogin()));
        }
        if (TextUtils.isEmpty(usuario.getSenha())) {
            nameValuePairs.add(new BasicNameValuePair("senha", usuario.getSenha().toString()));
        }
        if (TextUtils.isEmpty(usuario.getEmail())) {
            nameValuePairs.add(new BasicNameValuePair("email", usuario.getEmail().toString()));
        }

        nameValuePairs.add(new BasicNameValuePair("empresa", Constantes.KEYMOBILE));

        return nameValuePairs;
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {

            Usuario usuario = new Usuario();

            try {

                JSONObject jsonUsuario = ((JSONObject) serverResponse.getReturnObject()).getJSONObject("usuario");

                usuario.setId(jsonUsuario.has("id") ? jsonUsuario.getLong("id") : null);
                usuario.setNome(jsonUsuario.has("nome") ? jsonUsuario.getString("nome") : "");
                usuario.setLogin(jsonUsuario.has("login") ? jsonUsuario.getString("login") : "");
                usuario.setEmail(jsonUsuario.has("email") ? jsonUsuario.getString("email") : "");

            } catch (JSONException e) {
                e.printStackTrace();
                serverResponse.setStatusCode(-1);
            }
        }
    }
}