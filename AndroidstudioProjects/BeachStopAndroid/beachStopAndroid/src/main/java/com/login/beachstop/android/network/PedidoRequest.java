package com.login.beachstop.android.network;

import com.login.beachstop.android.models.Pedido;
import com.login.beachstop.android.models.PedidoSubItem;
import com.login.beachstop.android.models.ServerResponse;
import com.login.beachstop.android.network.http.ResponseListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Argus on 24/10/2014.
 */
public class PedidoRequest extends ObjectRequest<Pedido> {

    public PedidoRequest(ResponseListener listener) {
        super(listener);
    }

    @Override
    protected List<NameValuePair> createParameters(Pedido pedido) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("observacao", pedido.getObservacao()));

        nameValuePairs.add(new BasicNameValuePair("conta.id", pedido.getContaId().toString()));

        PedidoSubItem pedidoSubItem;
        for (int i = 0; i < pedido.getPedidoSubItens().size(); i++) {

            pedidoSubItem = pedido.getPedidoSubItens().get(i);

            nameValuePairs.add(new BasicNameValuePair("subItens[" + i + "].quantidade", pedidoSubItem.getQuantidade().toString()));
            nameValuePairs.add(new BasicNameValuePair("subItens[" + i + "].subitem.id", pedidoSubItem.getPedidoId().toString()));

        }

        return nameValuePairs;

    }

    @Override
    protected void handleResponse(ServerResponse serverResponse) {

        if (serverResponse.isOK()) {


        }
    }


}