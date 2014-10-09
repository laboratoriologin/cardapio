package com.login.beachstop.android.garcom.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.beachstop.android.garcom.business.Observable;
import com.login.beachstop.android.garcom.model.Alerta;
import com.login.beachstop.android.garcom.model.Pedido;
import com.login.beachstop.android.garcom.model.PedidoAlertaItem;
import com.login.beachstop.android.garcom.model.ServerResponse;
import com.login.beachstop.android.garcom.model.Usuario;

public class JSONUsuarioMesa extends AsyncTask<Object, Void, ServerResponse> {

	private Observable observable;

	public JSONUsuarioMesa(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(Object... params) {
		Usuario usuario = params == null || params.length == 0 ? null : (Usuario) params[0];
		List<Long> nMesa = params == null || params.length == 0 ? null : (List<Long>) params[1];
		ServerResponse serverResponse;

		if (nMesa != null) {
			serverResponse = new HttpUtil().getJSONFromURLPost(getUrlInsert(), parseToNameValuePair(usuario, nMesa));
		} else {
			serverResponse = new HttpUtil().getJSONFromURL(getUrlGetAll(usuario));
		}

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

	private List<NameValuePair> parseToNameValuePair(Usuario usuario, List<Long> nMesa) {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("usuario.id", usuario.getId().toString()));

		StringBuilder sb = new StringBuilder();

		if (nMesa.size() != 0) {
			for (int i = 0; i < nMesa.size(); i++) {
				sb.append(nMesa.get(i));
				sb.append(",");
			}

			sb.deleteCharAt(sb.length() - 1);
		}

		nameValuePairs.add(new BasicNameValuePair("listanumeromesa", sb.toString()));

		return nameValuePairs;
	}

	private List<Long> parseToObj(JSONObject jsonObject) throws JSONException {

		List<Long> itens = new ArrayList<Long>();
		JSONObject jsonObj;

		for (int i = 0; i < jsonObject.getJSONArray("").length(); i++) {
			jsonObj = jsonObject.getJSONArray("").getJSONObject(i);
			itens.add(jsonObj.getJSONObject("usuariosmesas").getLong("numeroMesa"));
		}

		return itens;
	}

	private String getUrlInsert() {
		return Constantes.URL_WS + "/usuarios_mesas";
	}

	private String getUrlGetAll(Usuario usuario) {
		return Constantes.URL_WS + "/usuarios_mesas/byusuario/" + usuario.getId().toString() + "/numeroMesa";
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
