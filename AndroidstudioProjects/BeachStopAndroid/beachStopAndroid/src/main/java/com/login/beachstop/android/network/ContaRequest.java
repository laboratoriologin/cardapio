package com.login.beachstop.android.network;

import com.login.beachstop.android.models.Cliente;
import com.login.beachstop.android.models.Conta;
import com.login.beachstop.android.models.Pedido;
import com.login.beachstop.android.models.PedidoSubItem;
import com.login.beachstop.android.models.ServerRequest;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.http.ResponseListener;
import com.login.beachstop.android.utils.Constantes;
import com.login.beachstop.android.utils.Utilitarios;

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
public class ContaRequest extends ObjectRequest<Conta> {

    public ContaRequest(ResponseListener listener) {
        super(listener);
    }

    @Override
    public void get(Conta conta) {
        String urlgetAtivo = String.format("%s/%s/%s", Constantes.URL_WS, new Conta().getServiceName(), conta.getSistemaId().toString());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, urlgetAtivo, null);
        this.execute(serverRequest);
    }

    public void getNumeroConta(Conta conta) {
        String urlgetAtivo = String.format("%s/%s/%s/numero", Constantes.URL_WS, new Conta().getServiceName(), conta.getSistemaId().toString());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, urlgetAtivo, null);
        this.execute(serverRequest);
    }

    public void verificarContaAberta(Conta conta) {
        String urlgetAtivo = String.format("%s/%s/aberto/%s", Constantes.URL_WS, new Conta().getServiceName(), conta.getSistemaId().toString());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, urlgetAtivo, null);
        this.execute(serverRequest);
    }

    public void associarCliente(Long mesa, Long clienteId) {
        String url = String.format("%s/%s/associar/%s/cliente/%s", Constantes.URL_WS, new Conta().getServiceName(), mesa, clienteId);
        ServerRequest serverRequest = new ServerRequest(ServerRequest.PUT, url, new ArrayList<NameValuePair>());
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
                    conta.setDataAbertura((jsonObject.has("dataAbertura") ? jsonObject.getString("dataAbertura") : "").toString());
                    conta.setDataFechamento((jsonObject.has("dataFechamento") ? jsonObject.getString("dataFechamento") : "").toString());
                    conta.setNumero(jsonObject.has("numero") ? jsonObject.getLong("numero") : null);
                    conta.setValorTotal((jsonObject.has("valor") ? jsonObject.getString("valor") : ""));
                    conta.setValorTotalPago((jsonObject.has("valorPago") ? jsonObject.getString("valorPago") : "0.0"));

                    if (jsonObject.has("pedidoSubItens")) {
                        JSONArray jsonArrayPedidoSubItem = Utilitarios.getAlwaysJsonArray(jsonObject, "pedidoSubItens");

                        Pedido pedido = new Pedido();
                        pedido.setPedidoSubItens(new ArrayList<PedidoSubItem>());
                        PedidoSubItem pedidoSubItem;

                        for (int i = 0; i < jsonArrayPedidoSubItem.length(); i++) {
                            pedidoSubItem = new PedidoSubItem();
                            pedidoSubItem.setQuantidade(jsonArrayPedidoSubItem.getJSONObject(i).getLong("quantidade"));
                            pedidoSubItem.setValorTotal(jsonArrayPedidoSubItem.getJSONObject(i).getString("valorCalculado"));
                            pedidoSubItem.setValorUnitario(jsonArrayPedidoSubItem.getJSONObject(i).getString("valorUnitario"));
                            pedidoSubItem.setSubItemId(jsonArrayPedidoSubItem.getJSONObject(i).getJSONObject("subItem").getLong("id"));
                            pedido.getPedidoSubItens().add(pedidoSubItem);
                        }

                        if (conta.getPedidos() == null) {
                            conta.setPedidos(new ArrayList<Pedido>());
                        }

                        conta.getPedidos().add(pedido);
                    } else {
                        conta.setPedidos(new ArrayList<Pedido>());
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