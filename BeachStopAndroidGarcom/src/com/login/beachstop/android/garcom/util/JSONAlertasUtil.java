package com.login.beachstop.android.garcom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.beachstop.android.garcom.business.Observable;
import com.login.beachstop.android.garcom.model.Alerta;
import com.login.beachstop.android.garcom.model.Pedido;
import com.login.beachstop.android.garcom.model.PedidoAlertaItem;
import com.login.beachstop.android.garcom.model.ServerResponse;

public class JSONAlertasUtil extends AsyncTask<List<Long>, Void, ServerResponse> {

	private Observable observable;

	public JSONAlertasUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(List<Long>... params) {

		List<Long> mesas = params == null || params.length == 0 ? null : params[0];

		ServerResponse serverResponse = new HttpUtil().getJSONFromURL(getUrl(mesas));

		if (serverResponse.isOK()) {
			try {
				serverResponse.setReturnObject(parseToObj((JSONObject) serverResponse.getReturnObject()));
				return serverResponse;
			} catch (JSONException ex) {
				return null;
			}
		}
		return null;
	}

	private String getUrl(List<Long> mesas) {

		String strMesas;
		if (mesas.size() != 0)
			strMesas = joinToString(mesas, ",");
		else
			strMesas = "0";

		return Constantes.URL_WS + "/alertas/empresa/" + Constantes.KEYMOBILE + "/mesa/" + strMesas;
	}

	public static String joinToString(Collection<?> collection, CharSequence separator) {

		if (collection.isEmpty()) {

			return "";

		} else {

			StringBuilder sepValueBuilder = new StringBuilder();

			for (Object obj : collection) {

				sepValueBuilder.append(obj).append(separator);
			}

			sepValueBuilder.setLength(sepValueBuilder.length() - separator.length());

			return sepValueBuilder.toString();

		}

	}

	private List<PedidoAlertaItem> parseToObj(JSONObject jsonObject) throws JSONException {

		List<PedidoAlertaItem> itens = new ArrayList<PedidoAlertaItem>();

		JSONArray jsonAlertas = null;

		try {

			jsonAlertas = Utilitarios.getAlwaysJsonArray(jsonObject.getJSONObject("pedidos_alertas"), "alertas");

		} catch (JSONException ex) {

			jsonAlertas = new JSONArray();

		}

		JSONArray jsonContas = null;

		try {

			jsonContas = Utilitarios.getAlwaysJsonArray(jsonObject.getJSONObject("pedidos_alertas"), "contas");

		} catch (JSONException ex) {

			jsonContas = new JSONArray();

		}

		PedidoAlertaItem item = null;

		for (int i = 0; i < jsonAlertas.length(); i++) {

			item = new PedidoAlertaItem();

			item.setAlerta(new Alerta(jsonAlertas.getJSONObject(i)));

			itens.add(item);

		}

		for (int i = 0; i < jsonContas.length(); i++) {

			JSONArray jsonPedidos = Utilitarios.getAlwaysJsonArray(jsonContas.getJSONObject(i), "pedido");

			for (int j = 0; j < jsonPedidos.length(); j++) {

				item = new PedidoAlertaItem();

				item.setPedido(new Pedido(jsonPedidos.getJSONObject(j), jsonContas.getJSONObject(i).getInt("mesa")));

				itens.add(item);

			}

		}

		return itens;
	}

	@Override
	protected void onPostExecute(ServerResponse result) {
		try {
			observable.observe(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
