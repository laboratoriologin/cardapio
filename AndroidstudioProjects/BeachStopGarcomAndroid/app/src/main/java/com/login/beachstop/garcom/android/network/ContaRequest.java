package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.Conta;
import com.login.beachstop.garcom.android.models.Pedido;
import com.login.beachstop.garcom.android.models.PedidoSubItem;
import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;
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
 * Created by Argus on 06/01/2015.
 */
public class ContaRequest extends ObjectRequest<Conta> {

    public ContaRequest(ResponseListener listener) {
        super(listener);
    }

    public void get(Long contaId) {
        String urlgetAtivo = String.format("%s/%s/%s", Constantes.URL_WS, new Conta().getServiceName(), contaId.toString());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, urlgetAtivo, null);
        this.execute(serverRequest);
    }

    public void getByNumero(Long numero) {
        String url = String.format("%s/%s/numero/%s", Constantes.URL_WS, new Conta().getServiceName(), numero.toString());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, url, null);
        this.execute(serverRequest);
    }

    @Override
    protected List<NameValuePair> createParameters(Conta conta) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("cliente.id", conta.getClienteId() != null ? conta.getClienteId().toString() : ""));
        nameValuePairs.add(new BasicNameValuePair("numero", conta.getNumero().toString()));
        nameValuePairs.add(new BasicNameValuePair("tipoconta", conta.getTipoConta().toString()));
        return nameValuePairs;

    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {

            Conta conta;
            JSONObject jsonObject;

            try {

                if (((JSONObject) serverResponse.getReturnObject()).has("conta")) {

                    jsonObject = ((JSONObject) serverResponse.getReturnObject()).getJSONObject("conta");

                    conta = new Conta();
                    conta.setId(jsonObject.has("id") ? jsonObject.getLong("id") : null);
                    conta.setClienteId(jsonObject.has("cliente") ? jsonObject.getJSONObject("cliente").getLong("id") : null);
                    conta.setTipoConta(Long.getLong((jsonObject.has("tipoconta") ? jsonObject.getBoolean("tipoconta") : "").toString()));
                    conta.setDataAbertura((jsonObject.has("dataabertura") ? jsonObject.getString("dataabertura") : "").toString());
                    conta.setDataFechamento((jsonObject.has("datafechamento") ? jsonObject.getString("datafechamento") : "").toString());
                    conta.setNumero(jsonObject.has("numero") ? jsonObject.getLong("numero") : null);
                    conta.setValorTotal((jsonObject.has("valor") ? jsonObject.getLong("valor") : "").toString());
                    conta.setValorTotalPago((jsonObject.has("valorPago") ? jsonObject.getBoolean("valorPago") : "0.0").toString());

                    if (jsonObject.has("pedidoSubItens")) {

                        JSONArray jsonArrayPedidoSubItem = Utilitarios.getAlwaysJsonArray(jsonObject, "pedidoSubItens");

                        Pedido pedido = new Pedido();
                        pedido.setPedidoSubItens(new ArrayList<PedidoSubItem>());
                        PedidoSubItem pedidoSubItem;

                        for (int i = 0; i < jsonArrayPedidoSubItem.length(); i++) {

                            pedidoSubItem = new PedidoSubItem();
                            pedidoSubItem.setQuantidade(jsonArrayPedidoSubItem.getJSONObject(i).getInt("quantidade"));
                            pedidoSubItem.setValorTotal(jsonArrayPedidoSubItem.getJSONObject(i).getString("valorCalculado"));
                            pedidoSubItem.setValorUnitario(jsonArrayPedidoSubItem.getJSONObject(i).getString("valorUnitario"));

                            pedidoSubItem.setSubItemId(jsonArrayPedidoSubItem.getJSONObject(i).getJSONObject("subItem").getLong("id"));

                            pedido.getPedidoSubItens().add(pedidoSubItem);

                        }

                        if (conta.getPedidos() == null) {
                            conta.setPedidos(new ArrayList<Pedido>());
                        }

                        conta.getPedidos().add(pedido);

                    }

                    serverResponse.setReturnObject(conta);

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