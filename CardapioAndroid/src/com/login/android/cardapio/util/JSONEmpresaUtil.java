package com.login.android.cardapio.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.login.android.cardapio.business.Observable;
import com.login.android.cardapio.model.Empresa;
import com.login.android.cardapio.model.ServerResponse;

public class JSONEmpresaUtil extends AsyncTask<String, Void, ServerResponse> {

	private Observable observable;

	public JSONEmpresaUtil(Observable observable) {
		this.observable = observable;
	}

	@Override
	protected ServerResponse doInBackground(String... params) {

		ServerResponse serverResponse = new HttpUtil().getJSONFromURL(Constantes.URL_WS + "/empresas/keymobile/" + Constantes.KEYMOBILE + "/keyCardapio/qtdMesa/dadosEmpresa");

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

	private Empresa parseToObj(JSONObject jsonObject) throws JSONException {

		String keyCardapio = jsonObject.getJSONObject("empresa").getString(Constantes.KEY_CARDAPIO);
		Long qtdMesa = jsonObject.getJSONObject("empresa").getLong(Constantes.QTD_MESA);
		String dados = jsonObject.getJSONObject("empresa").getString(Constantes.DADOS_EMPRESA);
		return new Empresa(keyCardapio, qtdMesa, dados);
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
