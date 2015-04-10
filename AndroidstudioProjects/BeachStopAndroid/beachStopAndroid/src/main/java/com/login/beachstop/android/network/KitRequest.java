package com.login.beachstop.android.network;

import com.login.beachstop.android.models.Kit;
import com.login.beachstop.android.models.KitSubItem;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Utilitarios;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 29/10/2014.
 */
public class KitRequest extends ObjectRequest<Kit> {

    public KitRequest(ResponseListener listener) {
        super(listener);
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {

            List<Kit> itens = new ArrayList<Kit>();
            JSONArray jsonArray;
            JSONObject jsonObjectKit;
            JSONArray jsonArrayKitSubItem;
            JSONObject jsonObjectSubKit;
            Kit kit;
            KitSubItem kitSubItem;

            try {

                jsonArray = ((JSONObject) serverResponse.getReturnObject()).getJSONArray("");

                for (int i = 0; i < jsonArray.length(); i++) {

                    kit = new Kit();
                    jsonObjectKit = jsonArray.getJSONObject(i).getJSONObject("kit");

                    kit.setId(jsonObjectKit.getLong("id"));
                    kit.setImagem(jsonObjectKit.has("imagem") ? jsonObjectKit.getString("imagem") : "");
                    kit.setNome(jsonObjectKit.has("nome") ? jsonObjectKit.getString("nome") : "");
                    kit.setDescricao(jsonObjectKit.has("descricao") ? jsonObjectKit.getString("descricao") : "");
                    kit.setDesconto(jsonObjectKit.has("desconto") ? jsonObjectKit.getString("desconto") : "");
                    kit.setOrdem(jsonObjectKit.has("ordem") ? Long.valueOf(jsonObjectKit.getLong("ordem")) : 0l);

                    kit.setKitSubItens(new ArrayList<KitSubItem>());
                    jsonArrayKitSubItem = Utilitarios.getAlwaysJsonArray(jsonObjectKit, "kitSubItens");

                    for (int j = 0; j < jsonArrayKitSubItem.length(); j++) {

                        kitSubItem = new KitSubItem();
                        jsonObjectSubKit = jsonArrayKitSubItem.getJSONObject(j);

                        kitSubItem.setId(jsonObjectSubKit.has("id") ? jsonObjectSubKit.getLong("id") : null);
                        kitSubItem.setKitId(jsonObjectSubKit.has("kit") ? jsonObjectSubKit.getJSONObject("kit").getLong("id") : null);
                        kitSubItem.setSubItemId(jsonObjectSubKit.has("subItem") ? jsonObjectSubKit.getJSONObject("subItem").getLong("id") : null);
                        kitSubItem.setQuantidade(jsonObjectSubKit.has("qtd") ? jsonObjectSubKit.getLong("qtd") : 0l);

                        kit.getKitSubItens().add(kitSubItem);
                    }
                    itens.add(kit);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            serverResponse.setReturnObject(itens);
        }
    }
}