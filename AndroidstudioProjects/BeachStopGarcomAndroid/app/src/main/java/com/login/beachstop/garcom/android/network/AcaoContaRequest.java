package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.Acao;
import com.login.beachstop.garcom.android.models.AcaoConta;
import com.login.beachstop.garcom.android.models.Conta;
import com.login.beachstop.garcom.android.models.Kit;
import com.login.beachstop.garcom.android.models.Pedido;
import com.login.beachstop.garcom.android.models.PedidoSubItem;
import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.Usuario;
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
public class AcaoContaRequest extends ObjectRequest<AcaoConta> {

    public AcaoContaRequest(ResponseListener listener) {
        super(listener);
    }

    public void getAll(Usuario usuario) {
        String url = String.format("%s/%s/empresa/%s/usuario/%s", Constantes.URL_WS, new AcaoConta().getServiceName(), Constantes.KEYMOBILE, usuario.getId().toString());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.GET, url, null);
        this.execute(serverRequest);
    }

    public void revolverAcaoConta(AcaoConta acaoConta) {
        String url = String.format("%s/%s/%s", Constantes.URL_WS, new AcaoConta().getServiceName(), acaoConta.getId().toString());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.PUT, url, createParameters(acaoConta));
        this.execute(serverRequest);
    }

    public void cancelarPedido(AcaoConta acaoConta) {
        String url = String.format("%s/%s/cancelarpedido/%s", Constantes.URL_WS, new AcaoConta().getServiceName(), acaoConta.getId().toString());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.PUT, url, createParameters(acaoConta));
        this.execute(serverRequest);
    }

    public void aprovarPedido(AcaoConta acaoConta) {
        String url = String.format("%s/%s/aprovarpedido/%s", Constantes.URL_WS, new AcaoConta().getServiceName(), acaoConta.getId().toString());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.PUT, url, createParameters(acaoConta));
        this.execute(serverRequest);
    }

    public void fecharConta(AcaoConta acaoConta) {
        String url = String.format("%s/%s/fercharconta", Constantes.URL_WS, new AcaoConta().getServiceName());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, url, createParameters(acaoConta));
        this.execute(serverRequest);
    }

    @Override
    protected List<NameValuePair> createParameters(AcaoConta obj) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (obj.getId() != null)
            nameValuePairs.add(new BasicNameValuePair("id", obj.getId().toString()));
        if (obj.getUsuario() != null)
            nameValuePairs.add(new BasicNameValuePair("usuario", obj.getUsuario().getId().toString()));
        if (obj.getAcao() != null) {
            nameValuePairs.add(new BasicNameValuePair("acao", obj.getAcao().getId().toString()));

            if (Constantes.Acao.PEDIDOS.equals(obj.getAcao().getId()))
                nameValuePairs.add(new BasicNameValuePair("pedido", obj.getPedido().getId().toString()));
        }
        if (obj.getConta() != null)
            nameValuePairs.add(new BasicNameValuePair("conta.id", obj.getConta().getId().toString()));

        return nameValuePairs;
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

                if (jsonArray.length() != 0) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        json = jsonArray.getJSONObject(i).getJSONObject("acaoconta");

                        acaoConta = new AcaoConta();
                        acaoConta.setId(json.getLong("id"));
                        acaoConta.setAcao(json.has("acao") ? new Acao(json.getJSONObject("acao").getLong("id")) : null);
                        acaoConta.setHorarioSolicitacao(json.has("horarioSolicitacao") ? json.getString("horarioSolicitacao") : "");
                        acaoConta.setDiffHorarioSolicitacao(json.has("diffHorarioSolitacao") ? json.getString("diffHorarioSolitacao") : "");

                        if (json.has("conta")) {
                            JSONObject jsonConta = json.getJSONObject("conta");

                            conta = new Conta();
                            conta.setId(jsonConta.getLong("id"));
                            conta.setDataAbertura(jsonConta.has("dataAbertura") ? jsonConta.getString("dataAbertura") : "");
                            conta.setNumero(jsonConta.has("numero") ? jsonConta.getLong("numero") : 0l);
                            conta.setClienteId(jsonConta.has("cliente") ? jsonConta.getJSONObject("cliente").getLong("id") : 0l);

                            acaoConta.setConta(conta);
                        }

                        if (!(json.get("pedido") instanceof String)) {
                            Pedido pedido = new Pedido();
                            pedido.setId(json.getJSONObject("pedido").getLong("id"));
                            pedido.setObservacao(json.getJSONObject("pedido").has("observacao") ? json.getJSONObject("pedido").getString("observacao") : "");

                            if (json.getJSONObject("pedido").has("subItens")) {
                                JSONArray jsonArrayPedidoSubItem = Utilitarios.getAlwaysJsonArray(json.getJSONObject("pedido"), "subItens");
                                PedidoSubItem pedidoSubItem;
                                pedido.setPedidoSubItens(new ArrayList<PedidoSubItem>());
                                JSONObject jsonObjectItem;

                                for (int j = 0; j < jsonArrayPedidoSubItem.length(); j++) {
                                    jsonObjectItem = jsonArrayPedidoSubItem.getJSONObject(j);
                                    pedidoSubItem = new PedidoSubItem();
                                    pedidoSubItem.setId(jsonObjectItem.getLong("id"));
                                    pedidoSubItem.setQuantidade(jsonObjectItem.getInt("quantidade"));
                                    pedidoSubItem.setValorUnitario(jsonObjectItem.getString("valorUnitario"));
                                    pedidoSubItem.setSubItemId(jsonObjectItem.getJSONObject("subItem").getLong("id"));

                                    if (jsonObjectItem.has("kit")) {
                                        try {
                                            Kit kit = new Kit();
                                            kit.setId(jsonObjectItem.getJSONObject("kit").getLong("id"));
                                            kit.setDescricao(jsonObjectItem.getJSONObject("kit").getString("descricao"));

                                            pedidoSubItem.setKit(kit);
                                        } catch (Exception e) {

                                        }
                                    }
                                    pedido.getPedidoSubItens().add(pedidoSubItem);
                                }
                                acaoConta.setPedido(pedido);
                            }
                        }

                        list.add(acaoConta);
                    }
                    serverResponse.setReturnObject(list);
                } else {
                    if (((JSONObject) serverResponse.getReturnObject()).has("acaoconta")) {
                        json = ((JSONObject) serverResponse.getReturnObject()).getJSONObject("acaoconta");

                        acaoConta = new AcaoConta();
                        acaoConta.setId(json.getLong("id"));
                        acaoConta.setAcao(json.has("acao") ? new Acao(json.getJSONObject("acao").getLong("id")) : null);
                        acaoConta.setHorarioSolicitacao(json.has("horarioSolicitacao") ? json.getString("horarioSolicitacao") : "");
                        acaoConta.setDiffHorarioSolicitacao(json.has("diffHorarioSolitacao") ? json.getString("diffHorarioSolitacao") : "");

                        if (json.has("conta")) {
                            json = json.getJSONObject("conta");

                            conta = new Conta();
                            conta.setId(json.getLong("id"));
                            conta.setDataAbertura(json.has("dataAbertura") ? json.getString("dataAbertura") : "");
                            conta.setNumero(json.has("numero") ? json.getLong("numero") : 0l);
                            conta.setClienteId(json.has("cliente") ? json.getJSONObject("cliente").getLong("id") : 0l);

                            acaoConta.setConta(conta);
                        }

                        serverResponse.setReturnObject(acaoConta);
                    } else
                        serverResponse.setReturnObject(list);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                serverResponse.setStatusCode(-1);
            }
        }
    }
}
