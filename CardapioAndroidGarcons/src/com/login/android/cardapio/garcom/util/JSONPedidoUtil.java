package com.login.android.cardapio.garcom.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.login.android.cardapio.garcom.business.Observable;
import com.login.android.cardapio.garcom.model.Pedido;
import com.login.android.cardapio.garcom.model.PedidoItem;
import com.login.android.cardapio.garcom.model.ServerResponse;

public class JSONPedidoUtil extends AsyncTask<Object, Void, ServerResponse> {

	private Observable observable;

	public JSONPedidoUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(Object... params) {
		Pedido pedido = params == null || params.length == 0 ? null : (Pedido) params[0];
		Boolean isInsert = params == null || params.length == 0 ? null : (Boolean) params[1];

		if (isInsert)
			return new HttpUtil().getJSONFromURLPost(getUrl(), parseToNameValuePair(pedido));
		else
			return new HttpUtil().getJSONFromURLPut(getUrlUpdate(pedido), parseToNameValuePair(pedido));
	}

	private List<NameValuePair> parseToNameValuePair(Pedido pedido) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("observacao", pedido.getObservacao()));
		nameValuePairs.add(new BasicNameValuePair("conta.mesa", pedido.getMesa().toString()));
		nameValuePairs.add(new BasicNameValuePair("conta.empresa.keymobile", Constantes.KEYMOBILE));
		nameValuePairs.add(new BasicNameValuePair("usuario", pedido.getUsuario().getId().toString()));

		PedidoItem pedidoItem;
		for (int i = 0; i < pedido.getListPedidoItem().size(); i++) {
			pedidoItem = pedido.getListPedidoItem().get(i);

			if (pedidoItem.getId() != null && pedidoItem.getId() != 0) {
				nameValuePairs.add(new BasicNameValuePair("listPedidoSubItem[" + i + "].id", pedidoItem.getId().toString()));
			}

			nameValuePairs.add(new BasicNameValuePair("listPedidoSubItem[" + i + "].quantidade", pedidoItem.getQuantidade().toString()));
			nameValuePairs.add(new BasicNameValuePair("listPedidoSubItem[" + i + "].subitem", pedidoItem.getIdSubItem().toString()));
		}

		return nameValuePairs;
	}

	private String getUrl() {
		return Constantes.URL_WS + "/pedidos";
	}

	private String getUrlUpdate(Pedido pedido) {
		return Constantes.URL_WS + "/pedidos/" + pedido.getId().toString();
	}

	@Override
	protected void onPostExecute(ServerResponse result) {
		try {
			observable.observe(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
