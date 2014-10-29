package com.login.beachstop.android.network;

import com.login.beachstop.android.models.Item;
import com.login.beachstop.android.models.ServerRequest;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.models.SubItem;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.Utilitarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class ItemRequest extends ObjectRequest<Item> {

    public ItemRequest(ResponseListener listener) {
        super(listener);
    }

    public void getItemByCategorias(Long categoria) {

        List<Long> categorias = new ArrayList<Long>();
        categorias.add(categoria);

        getItemByCategorias(categoria);

    }

    public void getItemByCategorias(List<Long> categorias) {

        String url = Constantes.URL_WS + "/" + new Item().getServiceName() + "/categorias/" + Utilitarios.joinToString(categorias, ",");

        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, url, null);

        this.execute(serverRequest);
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {

            List<Item> itens = new ArrayList<Item>();
            JSONArray jsonArray = null;
            JSONObject jsonObjectItem;
            JSONArray jsonArraySubItem;
            JSONObject jsonObjectSubItem;
            Item item;
            SubItem subItem;

            try {

                jsonArray = ((JSONObject) serverResponse.getReturnObject()).getJSONArray("");

                for (int i = 0; i < jsonArray.length(); i++) {

                    item = new Item();
                    jsonObjectItem = jsonArray.getJSONObject(i).getJSONObject("item");

                    item.setId(jsonObjectItem.getLong("id"));

                    if (jsonObjectItem.has("imagem")) {
                        item.setImagem(jsonObjectItem.getString("imagem"));
                    } else {
                        item.setImagem("");
                    }

                    if (jsonObjectItem.has("nome")) {
                        item.setNome(jsonObjectItem.getString("nome"));
                    } else {
                        item.setNome("");
                    }

                    if (jsonObjectItem.has("descricao")) {
                        item.setDescricao(jsonObjectItem.getString("descricao"));
                    } else {
                        item.setDescricao("");
                    }

                    if (jsonObjectItem.has("ingrediente")) {
                        item.setIngrediente(jsonObjectItem.getString("ingrediente"));
                    } else {
                        item.setIngrediente("");
                    }

                    if (jsonObjectItem.has("tempopreparo")) {
                        Long tempo = jsonObjectItem.getLong("tempopreparo");
                        item.setTempoPreparo(tempo.toString());
                    } else {
                        item.setTempoPreparo("0");
                    }

                    if (jsonObjectItem.has("ordem")) {
                        Long ordem = jsonObjectItem.getLong("ordem");
                        item.setOrdem(ordem.toString());
                    } else {
                        item.setTempoPreparo("0");
                    }

                    item.setCategoriaId(jsonObjectItem.getJSONObject("categoria").getLong("id"));

                    item.setSubItens(new ArrayList<SubItem>());
                    jsonArraySubItem = Utilitarios.getAlwaysJsonArray(jsonObjectItem, "subItens");

                    for (int j = 0; j < jsonArraySubItem.length(); j++) {

                        subItem = new SubItem();
                        jsonObjectSubItem = jsonArraySubItem.getJSONObject(j);

                        subItem.setId(jsonObjectSubItem.has("id") ? jsonObjectSubItem.getLong("id") : null);
                        subItem.setCodigo((jsonObjectSubItem.has("codigo") ? jsonObjectSubItem.getLong("codigo") : "").toString());
                        subItem.setDescricao(jsonObjectSubItem.has("descricao") ? jsonObjectSubItem.getString("descricao") : "");
                        subItem.setItemId(jsonObjectSubItem.has("item") ? jsonObjectSubItem.getJSONObject("item").getLong("id") : null);
                        subItem.setNome(jsonObjectSubItem.has("nome") ? jsonObjectSubItem.getString("nome") : "");
                        subItem.setOrdem(jsonObjectSubItem.has("ordem") ? jsonObjectSubItem.getLong("ordem") : null);
                        subItem.setValor((jsonObjectSubItem.has("codigo") ? jsonObjectSubItem.getDouble("codigo") : null).toString());

                        item.getSubItens().add(subItem);
                    }

                    itens.add(item);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }
}
