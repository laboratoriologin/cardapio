package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.Categoria;
import com.login.beachstop.garcom.android.models.Cliente;
import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public class CategoriaRequest extends ObjectRequest<Cliente> {

    public CategoriaRequest(ResponseListener listener) {
        super(listener);
    }

    public void getAtivo() {

        String urlgetAtivo = Constantes.URL_WS + "/" + new Categoria().getServiceName() + "/ativo";

        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, urlgetAtivo, null);

        this.execute(serverRequest);
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {

            List<Categoria> categorias = new ArrayList<Categoria>();
            Categoria categoria;

            try {

                JSONObject jsonCategoria;
                JSONArray jsonArrayCategoria = ((JSONObject) serverResponse.getReturnObject()).getJSONArray("");

                for (int i = 0; i < jsonArrayCategoria.length(); i++) {

                    jsonCategoria = jsonArrayCategoria.getJSONObject(i).getJSONObject("categoria");

                    categoria = new Categoria();
                    categoria.setId(jsonCategoria.getLong("id"));
                    categoria.setArea(jsonCategoria.getJSONObject("area").getLong("id"));
                    categoria.setDescricao(jsonCategoria.getString("descricao"));
                    categoria.setFlagAtivo(jsonCategoria.getBoolean("flagAtivo"));
                    categoria.setImagem(jsonCategoria.getString("imagem"));
                    categoria.setOrdem(jsonCategoria.getLong("ordem"));

                    categorias.add(categoria);
                }


                serverResponse.setReturnObject(categorias);


            } catch (JSONException e) {
                e.printStackTrace();

                serverResponse.setStatusCode(-1);
            }

        }

    }


}
