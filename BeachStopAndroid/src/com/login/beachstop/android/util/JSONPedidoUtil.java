package com.login.beachstop.android.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.login.beachstop.android.business.Observable;
import com.login.beachstop.android.model.Pedido;
import com.login.beachstop.android.model.PedidoItem;
import com.login.beachstop.android.model.ServerResponse;

public class JSONPedidoUtil extends AsyncTask<Pedido, Void, ServerResponse> {

	private Observable observable;

	public JSONPedidoUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(Pedido... params) {
		Pedido pedido = params == null || params.length == 0 ? null : params[0];
		return new HttpUtil().getJSONFromURLPost(getUrl(), parseToNameValuePair(pedido));
	}

	private List<NameValuePair> parseToNameValuePair(Pedido pedido) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("observacao", pedido.getObservacao()));

		nameValuePairs.add(new BasicNameValuePair("conta.id", pedido.getIdConta().toString()));

		PedidoItem pedidoItem;
		for (int i = 0; i < pedido.getListPedidoItem().size(); i++) {
			pedidoItem = pedido.getListPedidoItem().get(i);

			nameValuePairs.add(new BasicNameValuePair("listPedidoSubItem[" + i + "].quantidade", pedidoItem.getQuantidade().toString()));
			nameValuePairs.add(new BasicNameValuePair("listPedidoSubItem[" + i + "].subitem", pedidoItem.getIdSubItem().toString()));
		}

		return nameValuePairs;
	}

	private String getUrl() {
		return Constantes.URL_WS + "/pedidos";
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
