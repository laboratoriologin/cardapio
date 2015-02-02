package com.login.beachstop.garcom.android.network;

import com.login.beachstop.garcom.android.models.AcaoConta;
import com.login.beachstop.garcom.android.models.Pedido;
import com.login.beachstop.garcom.android.models.PedidoSubItem;
import com.login.beachstop.garcom.android.models.ServerRequest;
import com.login.beachstop.garcom.android.models.ServerResponse;
import com.login.beachstop.garcom.android.models.Usuario;
import com.login.beachstop.garcom.android.network.http.ResponseListener;
import com.login.beachstop.garcom.android.utils.Constantes;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 06/01/2015.
 */
public class PedidoRequest extends ObjectRequest<Pedido> {

    public PedidoRequest(ResponseListener listener) {
        super(listener);
    }

    public void update(Pedido pedido, Usuario usuario, AcaoConta acaoConta) {
        String url = String.format("%s/%s/%s", Constantes.URL_WS, new Pedido().getServiceName(), pedido.getId());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.PUT, url, createParameters(pedido, usuario, acaoConta));
        this.execute(serverRequest);
    }

    public void insert(Pedido pedido, Usuario usuario) {
        String url = String.format("%s/%s", Constantes.URL_WS, new Pedido().getServiceName());
        ServerRequest serverRequest = new ServerRequest(ServerRequest.POST, url, createParameters(pedido, usuario));
        this.execute(serverRequest);
    }

    @Override
    protected List<NameValuePair> createParameters(Pedido pedido) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("observacao", pedido.getObservacao()));
        if (pedido.getContaId() != null)
            nameValuePairs.add(new BasicNameValuePair("conta", pedido.getContaId().toString()));
        if (pedido.getNumero() != null)
            nameValuePairs.add(new BasicNameValuePair("numero", pedido.getNumero().toString()));

        PedidoSubItem pedidoSubItem;

        for (int i = 0; i < pedido.getPedidoSubItens().size(); i++) {
            pedidoSubItem = pedido.getPedidoSubItens().get(i);

            nameValuePairs.add(new BasicNameValuePair("subItens[" + i + "].quantidade", String.valueOf(pedidoSubItem.getQuantidade())));

            if(pedidoSubItem.getId() != null)
                nameValuePairs.add(new BasicNameValuePair("subItens[" + i + "].id", pedidoSubItem.getId().toString()));

            if (pedidoSubItem.getKit() == null)
                nameValuePairs.add(new BasicNameValuePair("subItens[" + i + "].subitem", pedidoSubItem.getSubItemId().toString()));
            else
                nameValuePairs.add(new BasicNameValuePair("subItens[" + i + "].kit", pedidoSubItem.getKit().getId().toString()));
        }
        return nameValuePairs;
    }

    protected List<NameValuePair> createParameters(Pedido pedido, Usuario usuario) {
        List<NameValuePair> nameValuePairs = createParameters(pedido);
        nameValuePairs.add(new BasicNameValuePair("usuario", usuario.getId().toString()));
        return nameValuePairs;
    }

    protected List<NameValuePair> createParameters(Pedido pedido, Usuario usuario, AcaoConta acaoConta) {
        List<NameValuePair> nameValuePairs = createParameters(pedido);
        nameValuePairs.add(new BasicNameValuePair("usuario", usuario.getId().toString()));
        nameValuePairs.add(new BasicNameValuePair("acaoContaId", acaoConta.getId().toString()));
        return nameValuePairs;
    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {
        if (serverResponse.isOK()) {
        }
    }
}