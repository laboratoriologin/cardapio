package com.login.android.cardapio.garcom.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.android.cardapio.garcom.business.Observable;
import com.login.android.cardapio.garcom.model.ChaveCardapioEmpresa;
import com.login.android.cardapio.garcom.model.ServerResponse;

public class JSONChaveCardapioEmpresaUtil extends AsyncTask<String, Void, ServerResponse> {

	private Observable observable;

	public JSONChaveCardapioEmpresaUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(String... params) {

		ServerResponse serverResponse = new HttpUtil().getJSONFromURL(Constantes.URL_WS + "/empresas/keymobile/" + Constantes.KEYMOBILE + "/keyCardapio/qtdMesa/");

		if (serverResponse.isOK()) {
			try {
				serverResponse.setReturnObject(parseToObj((JSONObject) serverResponse.getReturnObject()));
				return serverResponse;
			} catch (JSONException ex) {
				return serverResponse;
			}
		}
		return serverResponse;
	}

	private ChaveCardapioEmpresa parseToObj(JSONObject jsonObject) throws JSONException {

		String keyCardapio = jsonObject.getJSONObject("empresa").getString(Constantes.KEY_CARDAPIO);
		Long qtdMesa = jsonObject.getJSONObject("empresa").getLong("qtdMesa");
		return new ChaveCardapioEmpresa(keyCardapio, qtdMesa);
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
